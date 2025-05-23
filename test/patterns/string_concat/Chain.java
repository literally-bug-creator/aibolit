// SPDX-FileCopyrightText: Copyright (c) 2019-2025 Aibolit
// SPDX-License-Identifier: MIT
/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.example;

import com.aliyun.mns.model.Message;

import org.springframework.stereotype.Component;

/**
 * @author å¦‚æžœå‘é€çš„çŸ­ä¿¡éœ€è¦æŽ¥æ”¶å¯¹æ–¹å›žå¤çš„çŠ¶æ€æ¶ˆæ¯ï¼Œåªéœ€å®žçŽ°è¯¥æŽ¥å£å¹¶åˆå§‹åŒ–ä¸€ä¸ª Spring Bean å³å¯ã€‚
 */
@Component
public class SmsUpMessageListener
		implements com.alibaba.alicloud.sms.SmsUpMessageListener {

	@Override
	public boolean dealMessage(Message message) {
		System.err.println(this.getClass().getName() + "; " + message.toString());
		return true;
	}

}
