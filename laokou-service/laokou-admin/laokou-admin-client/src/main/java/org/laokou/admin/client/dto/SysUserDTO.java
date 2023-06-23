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
package org.laokou.admin.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author laokou
 */
@Data
public class SysUserDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 2478790090537077784L;

	private Long id;

	@NotBlank(message = "用户名不为空")
	private String username;

	@NotNull(message = "请选择用户状态")
	private Integer status;

	private List<Long> roleIds;

	private String password;

	private String avatar;

	private String mail;

	private String mobile;

	private Long editor;

	private Long deptId;

	private Integer version;

}
