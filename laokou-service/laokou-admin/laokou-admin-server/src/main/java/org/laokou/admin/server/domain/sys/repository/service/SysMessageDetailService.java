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
package org.laokou.admin.server.domain.sys.repository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laokou.admin.server.domain.sys.entity.SysMessageDetailDO;
import org.laokou.common.mybatisplus.service.BatchService;

/**
 * @author laokou
 */
public interface SysMessageDetailService extends BatchService<SysMessageDetailDO>, IService<SysMessageDetailDO> {

	/**
	 * 获取版本号
	 * @param id 主键
	 * @return 返回版本号
	 */
	Integer getVersion(Long id);

	/**
	 * 未读消息数
	 * @param userId
	 * @return
	 */
	Integer unReadCount(Long userId);

}
