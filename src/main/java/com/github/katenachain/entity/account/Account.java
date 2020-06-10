/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.account;

/**
 * Certify helper.
 */
public class Account {

    public static final String NAMESPACE = "account";
    public static final String TYPE_KEY = "key";
    public static final String DEFAULT_ROLE_ID = "default";
    public static final String COMPANY_ADMIN_ROLE_ID = "company_admin";
    public static final String TYPE_CREATE = "create";
    public static final String TYPE_REVOKE = "revoke";
    public static final String TYPE_ROTATE = "rotate";

    public static String getKeyCreateV1Type() {
        return String.format("%s.%s.%s", getKeyIdKey(), TYPE_CREATE, "v1");
    }

    public static String getKeyRevokeV1Type() {
        return String.format("%s.%s.%s", getKeyIdKey(), TYPE_REVOKE, "v1");
    }

    public static String getKeyRotateV1Type() {
        return String.format("%s.%s.%s", getKeyIdKey(), TYPE_ROTATE, "v1");
    }

    public static String getKeyIdKey() {
        return String.format("%s.%s", NAMESPACE, TYPE_KEY);
    }
}
