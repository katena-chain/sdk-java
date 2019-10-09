/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.serializer;

import com.github.katenachain.crypto.AbstractKey;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.serializer.adapter.ByteArraySerializer;
import com.github.katenachain.serializer.adapter.CryptoKeySerializer;
import com.github.katenachain.serializer.adapter.GsonUTCDateAdapter;
import com.github.katenachain.serializer.adapter.TxDataSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public class Serializer {

    private GsonBuilder builder;

    /**
     * Serializer Constructor
     */
    public Serializer() {
        builder = new GsonBuilder();
        builder.serializeNulls();
        builder.registerTypeAdapter(TxData.class, new TxDataSerializer());
        builder.registerTypeHierarchyAdapter(AbstractKey.class, new CryptoKeySerializer());
        builder.registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
        builder.registerTypeAdapter(byte[].class, new ByteArraySerializer()).create();

        builder.disableHtmlEscaping();
    }

    /**
     * serializes data in the appropriate format.
     */
    public String serialize(Object object) {

        Gson gson = builder.create();
        return gson.toJson(object);
    }

    /**
     * deserializes data into the given type.
     */
    public Object deserialize(String content, Class clazz) {
        Gson gson = builder.create();

        return gson.fromJson(content, clazz);
    }
}
