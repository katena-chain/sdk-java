/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.account;

import com.github.katenachain.crypto.ED25519.PublicKey;
import com.google.gson.annotations.SerializedName;

/**
 * KeyV1 is the first version of a key.
 */
public class KeyV1 {

    @SerializedName("fqid")
    private String fqId;

    @SerializedName("public_key")
    private PublicKey publicKey;

    @SerializedName("is_active")
    private boolean isActive;

    private String role;

    /**
     * KeyV1 constructor.
     */
    KeyV1(String fqId, PublicKey publicKey, boolean isActive, String role) {
        this.fqId = fqId;
        this.publicKey = publicKey;
        this.isActive = isActive;
        this.role = role;
    }

    public String getFqId() {
        return this.fqId;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public String getRole() {
        return this.role;
    }
}
