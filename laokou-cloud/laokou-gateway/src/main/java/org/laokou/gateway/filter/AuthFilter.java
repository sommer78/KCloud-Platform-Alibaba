/**
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *   http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.laokou.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.laokou.common.core.utils.MapUtil;
import org.laokou.common.i18n.core.StatusCode;
import org.laokou.common.i18n.utils.StringUtil;
import org.laokou.common.jasypt.utils.RsaUtil;
import org.laokou.gateway.constant.GatewayConstant;
import org.laokou.gateway.properties.CustomProperties;
import org.laokou.gateway.utils.ResponseUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;

import static org.laokou.common.core.constant.Constant.AUTHORIZATION_HEAD;
import static org.laokou.gateway.constant.GatewayConstant.OAUTH2_AUTH_URI;

/**
 * 认证Filter
 *
 * @author laokou
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {

	private final CustomProperties customProperties;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 获取request对象
		ServerHttpRequest request = exchange.getRequest();
		// 获取uri
		String requestUri = request.getPath().pathWithinApplication().value();
		// 请求放行，无需验证权限
		if (ResponseUtil.pathMatcher(requestUri, customProperties.getUris())) {
			return chain.filter(exchange);
		}
		// 表单提交
		MediaType mediaType = request.getHeaders().getContentType();
		if (OAUTH2_AUTH_URI.contains(requestUri) && HttpMethod.POST.matches(request.getMethod().name())
				&& MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
			return oauth2Decode(exchange, chain);
		}
		// 获取token
		String token = ResponseUtil.getParamValue(request, AUTHORIZATION_HEAD);
		if (StringUtil.isEmpty(token)) {
			return ResponseUtil.response(exchange, ResponseUtil.error(StatusCode.UNAUTHORIZED));
		}
		// 增加令牌
		return chain
				.filter(exchange.mutate().request(request.mutate().header(AUTHORIZATION_HEAD, token).build()).build());
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 1000;
	}

	/**
	 * OAuth2解密
	 * @param chain chain
	 * @param exchange exchange
	 * @return Mono<Void>
	 */
	private Mono<Void> oauth2Decode(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
		Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(decrypt());
		BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody,
				String.class);
		HttpHeaders headers = new HttpHeaders();
		headers.putAll(exchange.getRequest().getHeaders());
		headers.remove(HttpHeaders.CONTENT_LENGTH);
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
		return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
			ServerHttpRequest decorator = requestDecorator(exchange, headers, outputMessage);
			return chain.filter(exchange.mutate().request(decorator).build());
		}));
	}

	private Function<String, Mono<String>> decrypt() {
		return s -> {
			// 获取请求密码并解密
			Map<String, String> inParamsMap = MapUtil.parseParamMap(s);
			if (inParamsMap.containsKey(GatewayConstant.PASSWORD)
					&& inParamsMap.containsKey(GatewayConstant.USERNAME)) {
				log.info("密码模式认证...");
				try {
					String password = inParamsMap.get(GatewayConstant.PASSWORD);
					String username = inParamsMap.get(GatewayConstant.USERNAME);
					// 返回修改后报文字符
					if (StringUtil.isNotEmpty(password)) {
						inParamsMap.put(GatewayConstant.PASSWORD, RsaUtil.decryptByPrivateKey(password));
					}
					if (StringUtil.isNotEmpty(username)) {
						inParamsMap.put(GatewayConstant.USERNAME, RsaUtil.decryptByPrivateKey(username));
					}
				}
				catch (Exception e) {
					log.error("错误信息：{}", e.getMessage());
				}
			}
			else {
				log.info("非密码模式:{}", s);
			}
			return Mono.just(MapUtil.parseParams(inParamsMap));
		};
	}

	private ServerHttpRequestDecorator requestDecorator(ServerWebExchange exchange, HttpHeaders headers,
			CachedBodyOutputMessage outputMessage) {
		return new ServerHttpRequestDecorator(exchange.getRequest()) {
			@Override
			@NonNull
			public HttpHeaders getHeaders() {
				long contentLength = headers.getContentLength();
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.putAll(super.getHeaders());
				if (contentLength > 0) {
					httpHeaders.setContentLength(contentLength);
				}
				else {
					httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
				}
				return httpHeaders;
			}

			@Override
			@NonNull
			public Flux<DataBuffer> getBody() {
				return outputMessage.getBody();
			}
		};
	}

}
