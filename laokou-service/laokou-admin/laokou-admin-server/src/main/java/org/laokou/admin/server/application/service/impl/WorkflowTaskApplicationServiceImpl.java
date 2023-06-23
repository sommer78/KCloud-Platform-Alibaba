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
package org.laokou.admin.server.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.laokou.admin.server.application.service.WorkflowTaskApplicationService;
import org.laokou.admin.server.infrastructure.feign.workflow.WorkTaskApiFeignClient;
import org.laokou.common.i18n.core.CustomException;
import org.laokou.common.i18n.core.HttpResult;
import org.springframework.stereotype.Service;

/**
 * @author laokou
 */
@Service
@RequiredArgsConstructor
public class WorkflowTaskApplicationServiceImpl implements WorkflowTaskApplicationService {

	private final WorkTaskApiFeignClient workTaskApiFeignClient;

	@Override
	public String diagramProcess(String processInstanceId) {
		HttpResult<String> result = workTaskApiFeignClient.diagram(processInstanceId);
		if (!result.success()) {
			throw new CustomException(result.getCode(), result.getMsg());
		}
		return result.getData();
	}

}
