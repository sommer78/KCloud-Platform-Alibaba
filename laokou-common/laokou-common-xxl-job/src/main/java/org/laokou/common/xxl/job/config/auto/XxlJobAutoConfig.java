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

package org.laokou.common.xxl.job.config.auto;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.laokou.common.xxl.job.config.XxlJobProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author laokou
 */
@AutoConfiguration
@ComponentScan("org.laokou.common.xxl.job")
@ConditionalOnClass(XxlJobProperties.class)
public class XxlJobAutoConfig {

	@Bean
	public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties properties) {
		XxlJobProperties.Admin admin = properties.getAdmin();
		XxlJobProperties.Executor executor = properties.getExecutor();
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		xxlJobSpringExecutor.setAdminAddresses(admin.getAddress());
		xxlJobSpringExecutor.setAppname(executor.getAppName());
		xxlJobSpringExecutor.setIp(executor.getIp());
		xxlJobSpringExecutor.setPort(executor.getPort());
		xxlJobSpringExecutor.setAccessToken(executor.getAccessToken());
		xxlJobSpringExecutor.setLogPath(executor.getLogPath());
		xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());
		return xxlJobSpringExecutor;
	}

}
