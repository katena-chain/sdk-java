/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity.certify;

import com.github.katenachain.entity.TxData;
import com.google.gson.annotations.Expose;

/**
 * CertificateRawV1 is the first version of a raw certificate.
 */
public class CertificateRawV1 implements TxData {

    private String id;
    private byte[] value;

    /**
     * CertificateRawV1 constructor.
     * @param id
     * @param value
     */
    public CertificateRawV1(String id, byte[] value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String getType() {
        return Certify.getTypeCertificateRawV1();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public byte[] getValue() {
        return this.value;
    }

}
