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

    public static String getCategoryCertificate() {
        return String.format("%s.%s", NAMESPACE, TYPE_CERTIFICATE);
    }

    public static String getCategorySecret() {
        return String.format("%s.%s", NAMESPACE, TYPE_SECRET);
    }

    public static String getTypeCertificateRawV1() {
        return String.format("%s.%s.%s", getCategoryCertificate(), TYPE_RAW, "v1");
    }

    public static String getTypeCertificateEd25519V1() {
        return String.format("%s.%s.%s", getCategoryCertificate(), TYPE_ED25519, "v1");
    }

    public static String getTypeSecretNaclBoxV1() {
        return String.format("%s.%s.%s", getCategorySecret(), TYPE_NACL_BOX, "v1");
    }
}
