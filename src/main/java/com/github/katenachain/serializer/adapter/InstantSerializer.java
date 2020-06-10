/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.katenachain.serializer.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantSerializer implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").withZone(ZoneOffset.UTC);

    @Override
    public synchronized JsonElement serialize(Instant date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(FORMATTER.format(date));
    }

    @Override
    public synchronized Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        return Instant.from(FORMATTER.parse(jsonElement.getAsString()));
    }
}
