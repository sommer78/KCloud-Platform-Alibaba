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
package org.laokou.auth.client.utils;

import org.laokou.auth.client.user.UserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author laokou
 */
public class UserUtil {

	public static UserDetail userDetail() {
		return (UserDetail) getAuthentication().getPrincipal();
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 用户ID
	 * @return Long
	 */
	public static Long getUserId() {
		return userDetail().getId();
	}

	/**
	 * 用户名
	 * @return String
	 */
	public static String getUserName() {
		return userDetail().getUsername();
	}

	/**
	 * 部门ID
	 * @return Long
	 */
	public static Long getDeptId() {
		return userDetail().getDeptId();
	}

	/**
	 * 租户ID
	 * @return Long
	 */
	public static Long getTenantId() {
		return userDetail().getTenantId();
	}

	/**
	 * 资源名称
	 * @return String
	 */
	public static String getSourceName() {
		return userDetail().getSourceName();
	}

}
