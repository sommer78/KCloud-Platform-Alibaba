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
package org.laokou.common.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laokou.common.easy.excel.suppert.ExcelTemplate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.ResultHandler;
import org.laokou.common.core.utils.ConvertUtil;
import org.laokou.common.log.event.OperateLogEvent;
import org.laokou.common.log.entity.SysOperateLogDO;
import org.laokou.common.log.excel.SysOperateLogExcel;
import org.laokou.common.log.mapper.SysOperateLogMapper;
import org.laokou.common.log.qo.SysOperateLogQo;
import org.laokou.common.log.service.SysOperateLogService;
import org.laokou.common.log.vo.SysOperateLogVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author laokou
 */
@Service
@RequiredArgsConstructor
public class SysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLogDO>
		implements SysOperateLogService {

	private final ExcelTemplate<SysOperateLogQo, SysOperateLogVO> excelTemplate;

	@Override
	public IPage<SysOperateLogVO> getOperateLogList(IPage<SysOperateLogVO> page, SysOperateLogQo qo) {
		return baseMapper.getOperateLogList(page, qo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean insertOperateLog(OperateLogEvent event) {
		SysOperateLogDO logDO = ConvertUtil.sourceToTarget(event, SysOperateLogDO.class);
		return baseMapper.insert(logDO) > 0 ? true : false;
	}

	@Override
	public void exportOperateLog(SysOperateLogQo qo, HttpServletResponse response) {
		excelTemplate.export(500, response, qo, this, SysOperateLogExcel.class);
	}

	@Override
	public void resultList(SysOperateLogQo qo, ResultHandler<SysOperateLogVO> resultHandler) {
		this.baseMapper.resultList(qo, resultHandler);
	}

}
