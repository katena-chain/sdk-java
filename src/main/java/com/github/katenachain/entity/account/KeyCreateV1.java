/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.entity.account;

import com.github.katenachain.crypto.ED25519.PublicKey;
import com.github.katenachain.entity.TxData;
import com.google.gson.annotations.SerializedName;

/**
 * KeyCreateV1 is the first version of a key create message.
 */
public class KeyCreateV1 implements TxData {

    private String id;

    @SerializedName("public_key")
    private PublicKey publicKey;

    private String role;

    /**
     * KeyCreateV1 constructor.
     * @param id
     * @param publicKey
     * @param role
     */
    public KeyCreateV1(String id, PublicKey publicKey, String role) {
        this.id = id;
        this.publicKey = publicKey;
        this.role = role;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public String getType() {
        return Account.getTypeKeyCreateV1();
    }
}
