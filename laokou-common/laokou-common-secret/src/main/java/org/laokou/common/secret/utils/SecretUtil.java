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
package org.laokou.common.secret.utils;

import org.laokou.common.i18n.core.CustomException;
import org.laokou.common.i18n.utils.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

/**
 * @author laokou
 */
@Component
public class SecretUtil {

	public static final String APP_KEY = "laokou2023";

	public static final String APP_SECRET = "vb05f6c45d67340zaz95v7fa6d49v99zx";

	private static final long TIMEOUT_MILLIS = 30 * 1000L;

	public void verification(String appKey, String appSecret, String sign, String nonce, long timestamp) {
		if (StringUtil.isEmpty(appKey)) {
			throw new CustomException("appKey不为空");
		}
		if (!APP_KEY.equals(appKey)) {
			throw new CustomException("appKey不存在");
		}
		if (StringUtil.isEmpty(appSecret)) {
			throw new CustomException("appSecret不为空");
		}
		if (!APP_SECRET.equals(appSecret)) {
			throw new CustomException("appSecret不存在");
		}
		if (StringUtil.isEmpty(nonce)) {
			throw new CustomException("nonce不为空");
		}
		// 判断时间戳
		long nowTimestamp = System.currentTimeMillis();
		long maxTimestamp = nowTimestamp + TIMEOUT_MILLIS;
		long minTimestamp = nowTimestamp - TIMEOUT_MILLIS;
		if (timestamp > maxTimestamp || timestamp < minTimestamp) {
			throw new CustomException("请求参数不合法");
		}
		// String newSign = sign(timestamp);
		// if (!sign.equals(newSign)) {
		// throw new CustomException("验签失败，请检查配置");
		// }
	}

	/**
	 * MD5(appKey+appSecret+timestamp+userId+username+tenantId)
	 */
	private String sign(long timestamp) {
		String str = APP_KEY + APP_SECRET + timestamp;
		return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
	}

}
