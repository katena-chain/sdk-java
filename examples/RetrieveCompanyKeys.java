/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.entity.account.KeyV1;
import com.github.katenachain.exceptions.ApiException;
import common.Settings;

import java.io.IOException;

import static common.Log.printlnJson;

public class RetrieveCompanyKeys {
    public static void main(String[] args) {
        // Alice wants to retrieve the keys of its company

        // Common Katena network information
        String apiUrl = Settings.apiUrl;

        // Alice Katena network information
        String aliceCompanyBcId = Settings.Company.bcId;

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl);

        try {
            // Retrieve the keys from Katena
            KeyV1[] keys = transactor.retrieveCompanyKeys(aliceCompanyBcId, 1, Settings.txPerPage);

            System.out.println("Keys list :");
            printlnJson(keys);
        } catch (IOException | ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
