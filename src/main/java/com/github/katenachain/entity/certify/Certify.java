/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.certify;

/**
 * Certify helper.
 */
public class Certify {

    public static final String NAMESPACE = "certify";
    public static final String TYPE_CERTIFICATE = "certificate";
    public static final String TYPE_SECRET = "secret";
    public static final String TYPE_RAW = "raw";
    public static final String TYPE_ED25519 = "ed25519";
    public static final String TYPE_NACL_BOX = "nacl_box";

    public static String getCertificateIdKey() {
        return String.format("%s.%s", NAMESPACE, TYPE_CERTIFICATE);
    }

    public static String getSecretIdKey() {
        return String.format("%s.%s", NAMESPACE, TYPE_SECRET);
    }

    public static String getCertificateRawV1Type() {
        return String.format("%s.%s.%s", getCertificateIdKey(), TYPE_RAW, "v1");
    }

    public static String getCertificateEd25519V1Type() {
        return String.format("%s.%s.%s", getCertificateIdKey(), TYPE_ED25519, "v1");
    }

    public static String getSecretNaclBoxV1Type() {
        return String.format("%s.%s.%s", getSecretIdKey(), TYPE_NACL_BOX, "v1");
    }
}
