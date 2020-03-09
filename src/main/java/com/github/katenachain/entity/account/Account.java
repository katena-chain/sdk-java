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

    public static String getCategoryKeyCreate() {
        return String.format("%s.%s.%s", NAMESPACE, TYPE_KEY, TYPE_CREATE);
    }

    public static String getCategoryKeyRevoke() {
        return String.format("%s.%s.%s", NAMESPACE, TYPE_KEY, TYPE_REVOKE);
    }

    public static String getTypeKeyCreateV1() {
        return String.format("%s.%s", getCategoryKeyCreate(), "v1");
    }

    public static String getTypeKeyRevokeV1() {
        return String.format("%s.%s", getCategoryKeyRevoke(), "v1");
    }
}
