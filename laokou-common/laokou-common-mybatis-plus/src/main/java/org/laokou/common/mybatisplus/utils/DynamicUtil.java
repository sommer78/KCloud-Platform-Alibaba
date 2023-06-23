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

package org.laokou.common.mybatisplus.utils;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import org.laokou.common.core.utils.SpringContextUtil;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author laokou
 */
@Component
public class DynamicUtil {

	public DynamicRoutingDataSource getDataSource() {
		return SpringContextUtil.getBean(DynamicRoutingDataSource.class);
	}

	public Map<String, DataSource> getDataSources() {
		return getDataSource().getDataSources();
	}

	public DefaultDataSourceCreator getDefaultDataSourceCreator() {
		return SpringContextUtil.getBean(DefaultDataSourceCreator.class);
	}

}
