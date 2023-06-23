/**
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.laokou.admin.client.enums;

import org.laokou.common.i18n.core.CustomException;

/**
 * @author laokou
 */
public enum AuditStatusEnum {

	/**
	 * 待审批
	 */
	INIT,
	/**
	 * 审批中
	 */
	AUDIT,
	/**
	 * 审批拒绝
	 */
	REJECT,
	/**
	 * 审批通过
	 */
	AGREE;

	public static AuditStatusEnum getStatus(int status) {
		for (AuditStatusEnum en : AuditStatusEnum.values()) {
			if (en.ordinal() == status) {
				return en;
			}
		}
		throw new CustomException("审批状态不存在");
	}

}
