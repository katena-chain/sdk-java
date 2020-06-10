/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.serializer.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Base64;

public class BytesSerializer implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

    @Override
    public synchronized JsonElement serialize(byte[] data, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(Base64.getEncoder().encodeToString(data));
    }

    @Override
    public synchronized byte[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        return Base64.getDecoder().decode(jsonElement.getAsString());
    }
}
