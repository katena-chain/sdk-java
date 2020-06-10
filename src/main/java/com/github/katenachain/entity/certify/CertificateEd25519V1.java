/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.certify;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.crypto.ED25519.PublicKey;
import com.github.katenachain.utils.Common;

import java.util.HashMap;

/**
 * CertificateEd25519V1 is the first version of an ed25519 certificate.
 */
public class CertificateEd25519V1 implements TxData {

    private String id;
    private byte[] signature;
    private PublicKey signer;

    /**
     * CertificateEd25519V1 constructor.
     *
     * @param id
     * @param signature
     * @param signer
     */
    public CertificateEd25519V1(String id, PublicKey signer, byte[] signature) {
        this.id = id;
        this.signature = signature;
        this.signer = signer;
    }

    public String getId() {
        return this.id;
    }

    public PublicKey getSigner() {
        return this.signer;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    @Override
    public String getType() {
        return Certify.getCertificateEd25519V1Type();
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
