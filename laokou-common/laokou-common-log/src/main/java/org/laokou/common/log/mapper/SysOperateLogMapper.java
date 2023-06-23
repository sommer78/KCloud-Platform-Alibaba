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
package org.laokou.common.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;
import org.laokou.common.log.entity.SysOperateLogDO;
import org.laokou.common.log.qo.SysOperateLogQo;
import org.laokou.common.log.vo.SysOperateLogVO;
import org.springframework.stereotype.Repository;

/**
 * @author laokou
 */
@Mapper
@Repository
public interface SysOperateLogMapper extends BaseMapper<SysOperateLogDO> {

	/**
	 * 分页查询操作日志
	 * @param page
	 * @param qo
	 * @return
	 */
	IPage<SysOperateLogVO> getOperateLogList(IPage<SysOperateLogVO> page, @Param("qo") SysOperateLogQo qo);

	/**
	 * 查询操作日志
	 * @param qo
	 * @param handler @
	 */
	void resultList(@Param("qo") SysOperateLogQo qo, ResultHandler<SysOperateLogVO> handler);

}
