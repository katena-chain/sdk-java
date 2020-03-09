/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity.certify;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.crypto.ED25519.PublicKey;

/**
 * CertificateEd25519V1 is the first version of an ed25519 certificate.
 */
public class CertificateEd25519V1 implements TxData {

    private String id;
    private byte[] signature;
    private PublicKey signer;

    /**
     * CertificateEd25519V1 constructor.
     */
    public CertificateEd25519V1(String id, byte[] signature, PublicKey signer) {
        this.id = id;
        this.signature = signature;
        this.signer = signer;
    }

    @Override
    public String getType() {
        return Certify.getTypeCertificateEd25519V1();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public PublicKey getSigner() {
        return this.signer;
    }

    public byte[] getSignature() {
        return this.signature;
    }

}
