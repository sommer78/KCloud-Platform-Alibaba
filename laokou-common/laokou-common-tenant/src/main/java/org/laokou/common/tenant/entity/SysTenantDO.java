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

package org.laokou.common.tenant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.laokou.common.mybatisplus.entity.BaseDO;

import java.io.Serial;

/**
 * @author laokou
 */
@Data
@TableName("boot_sys_tenant")
@Schema(name = "SysTenantDO", description = "系统多租户实体类")
public class SysTenantDO extends BaseDO {

	@Serial
	private static final long serialVersionUID = -6290324458980136421L;

	/**
	 * 多租户名称
	 */
	@Schema(name = "name", description = "多租户名称")
	private String name;

	/**
	 * 数据源id
	 */
	@Schema(name = "sourceId", description = "数据源id")
	private Long sourceId;

	/**
	 * 套餐id
	 */
	@Schema(name = "packageId", description = "套餐id")
	private Long packageId;

}