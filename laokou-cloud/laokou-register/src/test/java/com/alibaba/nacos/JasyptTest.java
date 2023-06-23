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

package com.alibaba.nacos;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * @author laokou
 */
public class JasyptTest {

	private static final String PBEWITHMD5ANDDES = "PBEWithMD5AndDES";

	/**
	 * Jasyp2.x 加密（PBEWithMD5AndDES）
	 * @param plainText 待加密的原文
	 * @param factor 加密秘钥
	 * @return java.lang.String
	 */
	public static String encryptWithMD5(String plainText, String factor) {
		// 1. 创建加解密工具实例
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		// 2. 加解密配置
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm(PBEWITHMD5ANDDES);
		config.setPassword(factor);
		encryptor.setConfig(config);
		// 3. 加密
		return encryptor.encrypt(plainText);
	}

	/**
	 * Jaspy2.x 解密（PBEWithMD5AndDES）
	 * @param encryptedText 待解密密文
	 * @param factor 解密秘钥
	 * @return java.lang.String
	 */
	public static String decryptWithMD5(String encryptedText, String factor) {
		// 1. 创建加解密工具实例
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		// 2. 加解密配置
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm(PBEWITHMD5ANDDES);
		config.setPassword(factor);
		encryptor.setConfig(config);
		// 3. 解密
		return encryptor.decrypt(encryptedText);
	}

	public static void main(String[] args) {
		String factor = "slat";
		String plainText = "111";
		String encryptWithMD5Str = encryptWithMD5(plainText, factor);
		String decryptWithMD5Str = decryptWithMD5(encryptWithMD5Str, factor);
		System.out.println("采用MD5加密前原文密文：" + encryptWithMD5Str);
		System.out.println("采用MD5解密后密文原文:" + decryptWithMD5Str);
	}

}
