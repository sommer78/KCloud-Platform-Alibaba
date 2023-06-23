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
package org.laokou.common.tenant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.laokou.common.core.vo.OptionVO;
import org.laokou.common.tenant.entity.SysSourceDO;
import org.laokou.common.tenant.qo.SysSourceQo;
import org.laokou.common.tenant.vo.SysSourceVO;

import java.util.List;

/**
 * @author laokou
 */
public interface SysSourceService extends IService<SysSourceDO> {

	/**
	 * 分页查询多租户数据源
	 * @param qo
	 * @param page
	 * @return
	 */
	IPage<SysSourceVO> querySourcePage(IPage<SysSourceVO> page, SysSourceQo qo);

	/**
	 * 获取版本号
	 * @param id
	 * @return
	 */
	Integer getVersion(Long id);

	/**
	 * 删除多租户数据源
	 * @param id
	 */
	void deleteSource(Long id);

	/**
	 * 查询数据源名称
	 * @param tenantId
	 * @return
	 */
	String querySourceName(Long tenantId);

	/**
	 * 查询数据源信息
	 * @param sourceName
	 * @return
	 */
	SysSourceVO querySource(String sourceName);

	/**
	 * 数据源详情
	 * @param id
	 * @return
	 */
	SysSourceVO getSourceById(Long id);

	/**
	 * 获取下拉框
	 * @return
	 */
	List<OptionVO> getOptionList();

}
