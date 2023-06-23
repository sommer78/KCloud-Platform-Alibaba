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
package org.laokou.auth.server.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.laokou.auth.client.constant.AuthConstant;
import org.laokou.auth.client.vo.IdempotentToken;
import org.laokou.auth.client.vo.SecretInfoVO;
import org.laokou.auth.server.application.service.SysAuthApplicationService;
import org.laokou.common.core.vo.OptionVO;
import org.laokou.common.i18n.core.HttpResult;
import org.laokou.common.trace.annotation.TraceLog;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 系统认证控制器
 *
 * @author laokou
 */
@RestController
@Tag(name = "Sys Auth API", description = "系统认证API")
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class SysAuthApiController {

	private final SysAuthApplicationService sysAuthApplicationService;

	/**
	 * 验证码
	 * @param request 请求参数
	 */
	@TraceLog
	@GetMapping("/captcha")
	@Operation(summary = "系统认证>验证码", description = "系统认证>验证码")
	@Parameter(name = AuthConstant.UUID, description = "唯一标识", example = "1111")
	public HttpResult<String> captcha(HttpServletRequest request) {
		return new HttpResult<String>().ok(sysAuthApplicationService.captcha(request));
	}

	/**
	 * 注销
	 * @param request 请求参数
	 */
	@TraceLog
	@GetMapping("/logout")
	@Operation(summary = "系统认证>注销", description = "系统认证>注销")
	public HttpResult<Boolean> logout(HttpServletRequest request) {
		return new HttpResult<Boolean>().ok(sysAuthApplicationService.logout(request));
	}

	/**
	 * 租户下拉列表
	 */
	@TraceLog
	@GetMapping("/tenant")
	@Operation(summary = "系统认证>租户", description = "系统认证>租户")
	public HttpResult<List<OptionVO>> optionList() {
		return new HttpResult<List<OptionVO>>().ok(sysAuthApplicationService.getOptionList());
	}

	/**
	 * 密钥配置
	 */
	@TraceLog
	@GetMapping("/secret_info")
	@Operation(summary = "系统认证>密钥配置", description = "系统认证>密钥配置")
	public HttpResult<SecretInfoVO> secretInfo() throws IOException {
		return new HttpResult<SecretInfoVO>().ok(sysAuthApplicationService.getSecretInfo());
	}

	/**
	 * 密钥配置
	 */
	@TraceLog
	@GetMapping("/idempotent_token")
	@Operation(summary = "系统认证>接口幂等性令牌", description = "系统认证>接口幂等性令牌")
	public HttpResult<IdempotentToken> idempotentToken() {
		return new HttpResult<IdempotentToken>().ok(sysAuthApplicationService.idempotentToken());
	}

}
