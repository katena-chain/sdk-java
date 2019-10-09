/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.exceptions;

/**
 * ApiException allows to wrap API errors.
 */
public class ApiException extends Exception {

    private int code;
    private String message;

    /**
     * ApiException constructor.
     */
    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return String.format("api error:\nCode : %s\nMessage : %s", this.code, this.message);
    }
}
