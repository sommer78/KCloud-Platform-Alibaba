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
package org.laokou.common.openfeign.config.auto;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import jakarta.servlet.http.HttpServletRequest;
import org.laokou.common.core.utils.RequestUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.laokou.common.core.constant.Constant.AUTHORIZATION_HEAD;
import static org.laokou.common.core.constant.Constant.TRACE_ID;

/**
 * openfeign关闭ssl {@link FeignAutoConfiguration}
 *
 * @author laokou
 */
@AutoConfiguration
@ComponentScan("org.laokou.common.openfeign")
public class OpenFeignAutoConfig implements RequestInterceptor {

	@Bean
	public feign.Logger.Level multipartLoggerLevel() {
		return feign.Logger.Level.FULL;
	}

	@Override
	public void apply(RequestTemplate template) {
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		template.header(TRACE_ID, request.getHeader(TRACE_ID));
		template.header(AUTHORIZATION_HEAD, request.getHeader(AUTHORIZATION_HEAD));
	}

	@Bean
	public Retryer retryer() {
		// 最大请求次数为5，初始间隔时间为100ms
		// 下次间隔时间1.5倍递增，重试间最大间隔时间为1s
		return new Retryer.Default();
	}

}
