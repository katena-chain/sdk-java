/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.certify;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.utils.Common;

import java.util.HashMap;

/**
 * CertificateRawV1 is the first version of a raw certificate.
 */
public class CertificateRawV1 implements TxData {

    private String id;
    private byte[] value;

    /**
     * CertificateRawV1 constructor.
     *
     * @param id
     * @param value
     */
    public CertificateRawV1(String id, byte[] value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public byte[] getValue() {
        return this.value;
    }

    @Override
    public String getType() {
        return Certify.getCertificateRawV1Type();
    }

    @Override
    public String getNamespace() {
        return Certify.NAMESPACE;
    }

    @Override
    public HashMap<String, String> getStateIds(String signerCompanyBcId) {
        String id = this.id;
        return new HashMap<String, String>() {{
            put(Certify.getCertificateIdKey(), Common.concatFqId(signerCompanyBcId, id));
        }};
    }
}
