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

package org.laokou.common.log.listener;

import lombok.RequiredArgsConstructor;
import org.laokou.common.log.event.LoginLogEvent;
import org.laokou.common.log.service.SysLoginLogService;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author laokou
 */
@Component
@RequiredArgsConstructor
public class LoginLogListener implements ApplicationListener<LoginLogEvent> {

	private final SysLoginLogService sysLoginLogService;

	@Override
	@Async
	public void onApplicationEvent(LoginLogEvent event) {
		sysLoginLogService.insertLoginLog(event);
	}

}
