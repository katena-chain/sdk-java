/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.entity.api.TxResult;
import com.github.katenachain.entity.api.TxResults;
import com.github.katenachain.exceptions.ApiException;
import common.Settings;

import java.io.IOException;

import static common.Log.printlnJson;

public class RetrieveCertificate {
    public static void main(String[] args) {
        // Alice wants to retrieve txs related to a certificate

        // Common Katena network information
        String apiUrl = Settings.apiUrl;

        // Alice Katena network information
        String aliceCompanyBcId = Settings.Company.bcId;

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl);

        // Certificate id Alice wants to retrieve
        String certificateId = Settings.certificateId;

        try {
            // Retrieve txs related to the certificate fqid
            TxResults txResults = transactor.retrieveCertificateTxs(aliceCompanyBcId, certificateId, 1, Settings.txPerPage);

            System.out.println("Tx list :");
            printlnJson(txResults);

            // Retrieve the last tx related to the certificate fqid
            TxResult txResult = transactor.retrieveLastCertificateTx(aliceCompanyBcId, certificateId);

            System.out.println("Last Tx :");
            printlnJson(txResult);

        } catch (IOException | ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}