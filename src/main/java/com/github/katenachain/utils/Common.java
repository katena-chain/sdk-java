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

public class Common {

    private static final String PAGE_PARAM = "page";
    private static final String PER_PAGE_PARAM = "per_page";
    public static final int DEFAULT_PER_PAGE_PARAM = 10;

    /**
     * joins the base path and paths array and adds the query values to return a new uri.
     *
     * @param basePath
     * @param paths
     * @param queryValues
     * @return
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

    /**
     * concatenates a company bcid and a uuid into a fully qualified id.
     *
     * @param companyBcId
     * @param uuid
     * @return
     */
    public static String concatFqId(String companyBcId, String uuid) {
        return String.format("%s-%s", companyBcId, uuid);
    }

    /**
     * returns the query params array to request a pagination.
     *
     * @param page
     * @param perPage
     * @return
     */
    public static HashMap<String, String> getPaginationQueryParams(int page, int perPage) {
        return new HashMap<String, String>() {{
            put(PAGE_PARAM, String.valueOf(page));
            put(PER_PAGE_PARAM, String.valueOf(perPage));
        }};
    }
}
