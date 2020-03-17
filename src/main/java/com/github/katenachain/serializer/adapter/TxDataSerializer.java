/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.serializer.adapter;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.certify.CertificateEd25519V1;
import com.github.katenachain.entity.certify.CertificateRawV1;
import com.github.katenachain.entity.certify.Certify;
import com.github.katenachain.entity.certify.SecretNaclBoxV1;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * The type Tx data serializer.
 */
public class TxDataSerializer implements JsonSerializer<TxData>, JsonDeserializer<TxData> {

    /**
     * Get available certificate type
     * @return
     */
    public static HashMap<String, java.lang.Class> getAvailableTypes() {
        HashMap<String, java.lang.Class> availableTypes = new HashMap<>();

        availableTypes.put(Certify.getTypeCertificateEd25519V1(), CertificateEd25519V1.class);
        availableTypes.put(Certify.getTypeCertificateRawV1(), CertificateRawV1.class);
        availableTypes.put(Certify.getTypeSecretNaclBoxV1(), SecretNaclBoxV1.class);

        return availableTypes;
    }

    @Override
    public JsonElement serialize(TxData txData, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject txDataJsonObject = new JsonObject();
        txDataJsonObject.add("type", new JsonPrimitive(txData.getType()));
        txDataJsonObject.add("value", jsonSerializationContext.serialize(txData));

        return txDataJsonObject;
    }

    @Override
    public TxData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.getAsJsonObject().has("type") && getAvailableTypes().containsKey(jsonElement.getAsJsonObject().get("type").getAsString())) {
            return jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("value"), getAvailableTypes().get(jsonElement.getAsJsonObject().get("type").getAsString()));
        }

        return null;
    }
}
