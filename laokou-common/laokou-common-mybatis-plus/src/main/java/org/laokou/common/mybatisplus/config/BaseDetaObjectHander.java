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
package org.laokou.common.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.laokou.common.core.holder.UserContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import static org.laokou.common.core.constant.Constant.DEFAULT;

/**
 * @author laokou
 */
@Component
@Slf4j
public class BaseDetaObjectHander implements MetaObjectHandler {

	private static final String CREATE_DATE = "createDate";

	private static final String UPDATE_DATE = "updateDate";

	private static final String DEL_FLAG = "delFlag";

	private static final String VERSION = "version";

	private static final String CREATOR = "creator";

	private static final String EDITOR = "editor";

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("insert fill .........");
		this.strictInsertFill(metaObject, CREATE_DATE, LocalDateTime::now, LocalDateTime.class);
		this.strictInsertFill(metaObject, UPDATE_DATE, LocalDateTime::now, LocalDateTime.class);
		this.strictInsertFill(metaObject, DEL_FLAG, () -> DEFAULT, Integer.class);
		this.strictInsertFill(metaObject, VERSION, () -> DEFAULT, Integer.class);
		this.strictInsertFill(metaObject, CREATOR, UserContextHolder::get, Long.class);
		this.strictInsertFill(metaObject, EDITOR, UserContextHolder::get, Long.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("update fill .......");
		this.strictUpdateFill(metaObject, UPDATE_DATE, LocalDateTime::now, LocalDateTime.class);
		this.strictUpdateFill(metaObject, EDITOR, UserContextHolder::get, Long.class);
	}

}
