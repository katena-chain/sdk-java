/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.entity.account.KeyV1;
import com.github.katenachain.entity.api.TxResult;
import com.github.katenachain.entity.api.TxResults;
import com.github.katenachain.exceptions.ApiException;
import common.Settings;

import java.io.IOException;

import static common.Log.printlnJson;

public class RetrieveKey {
    public static void main(String[] args) {
        // Alice wants to retrieve a key and its related txs

        // Common Katena network information
        String apiUrl = Settings.apiUrl;

        // Alice Katena network information
        String aliceCompanyBcId = Settings.Company.bcId;

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl);

        // Key id Alice wants to retrieve
        String keyId = Settings.keyId;

        try {
            // Retrieve txs related to the key fqid
            TxResults txResults = transactor.retrieveKeyTxs(aliceCompanyBcId, keyId, 1, Settings.txPerPage);

            System.out.println("Tx list :");
            printlnJson(txResults);

            // Retrieve the last tx related to the key fqid
            TxResult txResult = transactor.retrieveLastKeyTx(aliceCompanyBcId, keyId);

            System.out.println("Last Tx :");
            printlnJson(txResult);

            // Retrieve the last state of a key with that fqid
            KeyV1 key = transactor.retrieveKey(aliceCompanyBcId, keyId);

            System.out.println("Key :");
            printlnJson(key);
        } catch (IOException | ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}