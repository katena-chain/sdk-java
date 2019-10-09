/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.serializer.adapter;

import com.google.gson.*;
import com.github.katenachain.crypto.AbstractKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Base64;

/**
 * The type Crypto key serializer.
 */
public class CryptoKeySerializer implements JsonSerializer<AbstractKey>, JsonDeserializer<AbstractKey> {

    @Override
    public JsonElement serialize(AbstractKey abstractKey, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(Base64.getEncoder().encodeToString(abstractKey.getKey()));
    }

    @Override
    public AbstractKey deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            Class<?> clazz = Class.forName(type.getTypeName());
            Constructor<?> constructor = clazz.getConstructor(byte[].class);

            return (AbstractKey) constructor.newInstance((Object) Base64.getDecoder().decode(jsonElement.getAsString()));

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
