/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.serializer;

import com.github.katenachain.crypto.AbstractKey;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.serializer.adapter.BytesSerializer;
import com.github.katenachain.serializer.adapter.CryptoSerializer;
import com.github.katenachain.serializer.adapter.InstantSerializer;
import com.github.katenachain.serializer.adapter.TxDataSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.time.Instant;

public class Serializer {

    private Gson serializer;

    /**
     * Serializer Constructor
     */
    public Serializer(GsonBuilder gsonBuilder) {
        gsonBuilder.serializeNulls();
        gsonBuilder.registerTypeAdapter(TxData.class, new TxDataSerializer());
        gsonBuilder.registerTypeHierarchyAdapter(AbstractKey.class, new CryptoSerializer());
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer()).create();
        gsonBuilder.registerTypeAdapter(byte[].class, new BytesSerializer()).create();
        gsonBuilder.disableHtmlEscaping();
        this.serializer = gsonBuilder.create();
    }

    /**
     * serializes data in the appropriate format.
     *
     * @param object
     * @return
     */
    public String serialize(Object object) {
        return this.serializer.toJson(object);
    }

    /**
     * serializes data in the appropriate format.
     *
     * @param object
     * @param typeOfSrc
     * @return
     */
    public String serialize(Object object, Type typeOfSrc) {
        return this.serializer.toJson(object, typeOfSrc);
    }

    /**
     * deserializes data into the given type.
     *
     * @param clazz
     * @param content
     * @return
     */
    public Object deserialize(String content, Class clazz) {
        return this.serializer.fromJson(content, clazz);
    }
}
