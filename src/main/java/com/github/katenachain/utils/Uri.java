/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.github.katenachain.utils;

import okhttp3.HttpUrl;

import java.util.HashMap;
import java.util.Map;

public class Uri {

    /**
     * joins the base path and paths array and adds the query values to return a new uri.
     */
    public static HttpUrl getUri(String basePath, String[] paths, HashMap<String, String> queryValues) {

        HttpUrl url = HttpUrl.get(basePath);
        HttpUrl.Builder builder = url.newBuilder();

        if (paths != null) {
            for (String path : paths) {
                builder.addPathSegments(path);
            }
        }

        if (queryValues != null) {
            for (Map.Entry<String, String> entry : queryValues.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        return builder.build();
    }
}
