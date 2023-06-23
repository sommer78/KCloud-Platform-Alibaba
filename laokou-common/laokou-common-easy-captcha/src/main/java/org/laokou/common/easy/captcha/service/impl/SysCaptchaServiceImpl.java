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
package org.laokou.common.easy.captcha.service.impl;

import lombok.RequiredArgsConstructor;
import org.laokou.common.i18n.utils.StringUtil;
import org.laokou.common.easy.captcha.service.SysCaptchaService;
import org.laokou.common.redis.utils.RedisKeyUtil;
import org.laokou.common.redis.utils.RedisUtil;
import org.springframework.stereotype.Service;

/**
 * @author laokou
 */
@Service
@RequiredArgsConstructor
public class SysCaptchaServiceImpl implements SysCaptchaService {

	private final RedisUtil redisUtil;

	@Override
	public void setCode(String uuid, String code) {
		// 保存到缓存
		setCache(uuid, code);
	}

	@Override
	public Boolean validate(String uuid, String code) {
		// 获取验证码
		String captcha = getCache(uuid);
		if (StringUtil.isEmpty(captcha)) {
			return null;
		}
		// 效验成功
		return code.equalsIgnoreCase(captcha);
	}

	private void setCache(String key, String value) {
		key = RedisKeyUtil.getUserCaptchaKey(key);
		// 保存五分钟
		redisUtil.set(key, value, 60 * 5);
	}

	private String getCache(String uuid) {
		String key = RedisKeyUtil.getUserCaptchaKey(uuid);
		Object captcha = redisUtil.get(key);
		if (captcha != null) {
			redisUtil.delete(key);
		}
		return captcha != null ? captcha.toString() : "";
	}

}
