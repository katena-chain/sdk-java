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
 * KeyRevokeV1 is the first version of a key revoke message.
 */
public class KeyRevokeV1 implements TxData {
    private String id;

    @SerializedName("public_key")
    private PublicKey publicKey;

    /**
     * KeyCreateV1 constructor.
     * @param publicKey
     * @param id
     */
    public KeyRevokeV1(String id, PublicKey publicKey) {
        this.id = id;
        this.publicKey = publicKey;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getType() {
        return Account.getTypeKeyRevokeV1();
    }

}
