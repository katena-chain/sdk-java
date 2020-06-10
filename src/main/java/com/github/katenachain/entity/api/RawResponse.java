/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.api;

/**
 * RawResponse is a okhttp3.Response wrapper.
 */
public class RawResponse {

    private int statusCode;
    private byte[] body;

    /**
     * RawResponse constructor.
     *
     * @param body
     * @param statusCode
     */
    public RawResponse(int statusCode, byte[] body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public byte[] getBody() {
        return this.body;
    }
}
