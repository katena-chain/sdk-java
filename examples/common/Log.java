/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package common;

import com.github.katenachain.entity.TxData;
import com.github.katenachain.serializer.Serializer;
import com.google.gson.GsonBuilder;

public class Log {

    public static void printlnJson(Object data) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Serializer serializer = new Serializer(gsonBuilder);
        String encodedData;
        if (data instanceof TxData) {
            encodedData = serializer.serialize(data, TxData.class);
        } else {
            encodedData = serializer.serialize(data);
        }
        System.out.println(String.format("%s\n", encodedData));
    }

}
