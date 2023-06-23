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
package org.laokou.common.i18n.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.laokou.common.i18n.utils.MessageUtil;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一返回结果实体类
 *
 * @author laokou
 */
@Data
@Schema(name = "HttpResult", description = "统一返回结果实体类")
public class HttpResult<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = -1286769110881865369L;

	/**
	 * 状态编码
	 */
	@Schema(name = "code", description = "状态编码", example = "200")
	private int code;

	/**
	 * 响应描述
	 */
	@Schema(name = "msg", description = "响应描述", example = "请求成功")
	private String msg;

	/**
	 * 响应结果
	 */
	@Schema(name = "data", description = "响应结果")
	private T data;

	/**
	 * 链路ID
	 */
	@Schema(name = "traceId", description = "链路ID")
	private String traceId;

	public boolean success() {
		return this.code == StatusCode.OK;
	}

	public HttpResult<T> error(int code) {
		this.code = code;
		this.msg = MessageUtil.getMessage(code);
		return this;
	}

	public HttpResult<T> ok(T data) {
		this.code = StatusCode.OK;
		this.msg = MessageUtil.getMessage(StatusCode.OK);
		this.data = data;
		return this;
	}

	public HttpResult<T> error(int code, String msg) {
		this.code = code;
		this.msg = msg;
		return this;
	}

	public HttpResult<T> error(String msg) {
		this.code = StatusCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
		return this;
	}

}
