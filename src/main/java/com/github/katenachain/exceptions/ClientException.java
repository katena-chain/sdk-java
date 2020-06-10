/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.exceptions;

/**
 * ClientException allows to wrap transactor errors
 */
public class ClientException extends Exception {

    /**
     * ClientException constructor.
     *
     * @param message
     */
    public ClientException(String message) {
        super(message);
    }
}
