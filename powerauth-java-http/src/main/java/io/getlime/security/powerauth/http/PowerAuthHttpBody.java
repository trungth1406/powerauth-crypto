/*
 * Copyright 2016 Lime - HighTech Solutions s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.getlime.security.powerauth.http;

import com.google.common.io.BaseEncoding;

import java.io.UnsupportedEncodingException;

/**
 * Helper class simplifying working with HTTP request body in context of PowerAuth protocol.
 *
 * @author Petr Dvorak
 *
 */
public class PowerAuthHttpBody {

    /**
     * Prepare signature base string ("data to be signed") using request parameters.
     * @param httpMethod HTTP Method (for example "GET", "POST", "PUT", "DELETE", ...)
     * @param requestUri Request URI identifier (for example "/secure/payment", or "SEC_PAYM" - structure of URI ID is lose, but the first approach is suggested)
     * @param nonce Random 16B nonce value.
     * @param data Request data.
     * @return PowerAuth signature base string.
     */
    public static String getSignatureBaseString(String httpMethod, String requestUri, byte[] nonce, byte[] data) {

        String requestUriHash = "";
        if (requestUri != null) {
            byte[] bytes;
            try {
                bytes = requestUri.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                // System must support UTF-8
                return null;
            }
            requestUriHash = BaseEncoding.base64().encode(bytes);
        }

        String dataBase64 = "";
        if (data != null) {
            dataBase64 = BaseEncoding.base64().encode(data);
        }

        String nonceBase64 = "";
        if (nonce != null) {
            nonceBase64 = BaseEncoding.base64().encode(nonce);
        }

        return (httpMethod != null ? httpMethod.toUpperCase() : "GET")
                + "&" + requestUriHash
                + "&" + nonceBase64
                + "&" + dataBase64;
    }

}
