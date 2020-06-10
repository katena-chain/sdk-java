/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.entity.account;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.utils.Common;

import java.util.HashMap;

/**
 * KeyRevokeV1 is the first version of a key revoke message.
 */
public class KeyRevokeV1 implements TxData {

    private String id;

    /**
     * KeyCreateV1 constructor.
     *
     * @param id
     */
    public KeyRevokeV1(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return Account.getKeyRevokeV1Type();
    }

    @Override
    public String getNamespace() {
        return Account.NAMESPACE;
    }

    @Override
    public HashMap<String, String> getStateIds(String signerCompanyBcId) {
        String id = this.id;
        return new HashMap<String, String>() {{
            put(Account.getKeyIdKey(), Common.concatFqId(signerCompanyBcId, id));
        }};
    }

}
