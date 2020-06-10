/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.serializer.adapter;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.account.Account;
import com.github.katenachain.entity.account.KeyCreateV1;
import com.github.katenachain.entity.account.KeyRevokeV1;
import com.github.katenachain.entity.account.KeyRotateV1;
import com.github.katenachain.entity.certify.CertificateEd25519V1;
import com.github.katenachain.entity.certify.CertificateRawV1;
import com.github.katenachain.entity.certify.Certify;
import com.github.katenachain.entity.certify.SecretNaclBoxV1;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class TxDataSerializer implements JsonSerializer<TxData>, JsonDeserializer<TxData> {

    /**
     * Get available certificate type
     *
     * @return
     */
    public static HashMap<String, Class> getAvailableTypes() {
        return new HashMap<String, Class>() {{
            put(Certify.getCertificateEd25519V1Type(), CertificateEd25519V1.class);
            put(Certify.getCertificateRawV1Type(), CertificateRawV1.class);
            put(Certify.getSecretNaclBoxV1Type(), SecretNaclBoxV1.class);
            put(Account.getKeyCreateV1Type(), KeyCreateV1.class);
            put(Account.getKeyRotateV1Type(), KeyRotateV1.class);
            put(Account.getKeyRevokeV1Type(), KeyRevokeV1.class);
        }};
    }

    @Override
    public synchronized JsonElement serialize(TxData txData, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject txDataJsonObject = new JsonObject();
        txDataJsonObject.add("type", new JsonPrimitive(txData.getType()));
        txDataJsonObject.add("value", jsonSerializationContext.serialize(txData));
        return txDataJsonObject;
    }

    @Override
    public synchronized TxData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.getAsJsonObject().has("type") && getAvailableTypes().containsKey(jsonElement.getAsJsonObject().get("type").getAsString())) {
            return jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("value"), getAvailableTypes().get(jsonElement.getAsJsonObject().get("type").getAsString()));
        }
        return null;
    }
}
