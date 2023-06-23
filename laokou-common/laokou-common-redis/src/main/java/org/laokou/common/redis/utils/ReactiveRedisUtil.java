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

package org.laokou.common.redis.utils;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonReactiveClient;
import reactor.core.publisher.Mono;

/**
 * @author laokou
 */
@RequiredArgsConstructor
public class ReactiveRedisUtil {

	private final RedissonReactiveClient redissonReactiveClient;

	public Mono<Object> get(String key) {
		return redissonReactiveClient.getBucket(key).get();
	}

}
