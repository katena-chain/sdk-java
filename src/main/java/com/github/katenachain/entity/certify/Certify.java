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

    public static final String NAMESPACE_CERTIFY = "certify";
    public static final String TYPE_CERTIFICATE = "certificate";
    public static final String TYPE_SECRET = "secret";
    public static final String TYPE_RAW = "raw";
    public static final String TYPE_ED25519 = "ed25519";
    public static final String TYPE_NACL_BOX = "nacl_box";

    public static String getNamespaceCertify() {
        return NAMESPACE_CERTIFY;
    }

    public static String getCertificateSubNamespace() {
        return String.format("%s.%s", getNamespaceCertify(), TYPE_CERTIFICATE);
    }

    public static String getSecretSubNamespace() {
        return String.format("%s.%s", getNamespaceCertify(), TYPE_SECRET);
    }

    public static String getTypeCertificateRawV1() {
        return String.format("%s.%s.%s", getCertificateSubNamespace(), TYPE_RAW, "v1");
    }

    public static String getTypeCertificateEd25519V1() {
        return String.format("%s.%s.%s", getCertificateSubNamespace(), TYPE_ED25519, "v1");
    }

    public static String getTypeSecretNaclBoxV1() {
        return String.format("%s.%s.%s", getSecretSubNamespace(), Certify.TYPE_NACL_BOX, "v1");
    }

    public static String formatBcid(String companyChainId, String uuid) {
        return String.format("%s-%s", companyChainId, uuid);
    }
}
