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
package org.laokou.common.redis.utils;

/**
 * @author laokou
 */
public final class RedisKeyUtil {

	/**
	 * 验证码Key
	 */
	public static String getUserCaptchaKey(String uuid) {
		return "sys:user:captcha:" + uuid;
	}

	/**
	 * 菜单树Key
	 */
	public static String getResourceTreeKey(Long userId) {
		return "sys:resource:tree:" + userId;
	}

	/**
	 * 用户信息Key
	 */
	public static String getUserInfoKey(String token) {
		return "sys:user:info:" + token;
	}

	/**
	 * 二级缓存Key
	 */
	public static String getDataCacheKey(String name, Long id) {
		return "sys:" + name + ":cache:" + id;
	}

	/**
	 * 布隆过滤器Key
	 */
	public static String getBloomFilterKey() {
		return "sys:bloom:filter";
	}

	/**
	 * OSS配置Key
	 */
	public static String getOssConfigKey(Long tenantId) {
		return "sys:oss:config:" + tenantId;
	}

	/**
	 * 全量同步索引Key
	 */
	public static String getSyncIndexKey(String code) {
		return "sys:resource:sync:" + code;
	}

	/**
	 * 未读消息key
	 */
	public static String getMessageUnReadKey(Long userId) {
		return "sys:message:unread:" + userId;
	}

	/**
	 * 手机验证码Key
	 */
	public static String getMobileCodeKey(String mobile) {
		return getUserCaptchaKey(mobile);
	}

	/**
	 * 邮箱验证码Key
	 */
	public static String getMailCodeKey(String mail) {
		return getUserCaptchaKey(mail);
	}

	/**
	 * 用户踢出Key
	 */
	public static String getUserKillKey(String token) {
		return "sys:user:kill:" + token;
	}

	/**
	 * 接口幂等性令牌Key
	 */
	public static String getIdempotentTokenKey(String token) {
		return "sys:idempotent:token:" + token;
	}

}
