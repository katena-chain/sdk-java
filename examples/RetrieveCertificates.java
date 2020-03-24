/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.entity.api.TxWrapper;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.api.TxWrappers;
import com.github.katenachain.entity.certify.CertificateRawV1;
import com.github.katenachain.entity.certify.CertificateEd25519V1;
import com.github.katenachain.entity.certify.Certify;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.utils.Common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RetrieveCertificates {
    public static void main(String[] args) {
        // Alice wants to retrieve certificates

        // Common Katena network information
        String apiUrl = "https://nodes.test.katena.transchain.io/api/v1";

        // Alice Katena network information
        String aliceCompanyBcid = "abcdef";

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl);

        // Certificate uuid Alice wants to retrieve
        String certificateUuid = "2075c941-6876-405b-87d5-13791c0dc53a";

        try {
            // Retrieve a version 1 of a certificates from Katena
            TxWrappers txWrappers = transactor.retrieveCertificates(aliceCompanyBcid, certificateUuid, 1, Common.DEFAULT_PER_PAGE_PARAM);

            for (TxWrapper txWrapper : txWrappers.getTxs()
            ) {
                TxData txData = txWrapper.getTx().getData();
                System.out.println("Transaction status");
                System.out.println(String.format("  Code    : %d", txWrapper.getStatus().getCode()));
                System.out.println(String.format("  Message : %s", txWrapper.getStatus().getMessage()));

                if (txData.getType().equals(Certify.getTypeCertificateRawV1())) {
                    CertificateRawV1 certificateRaw = (CertificateRawV1) txData;
                    System.out.println("CertificateRawV1");
                    System.out.println(String.format("  Id    : %s", certificateRaw.getId()));
                    System.out.println(String.format("  Value : %s", new String(certificateRaw.getValue(), StandardCharsets.UTF_8)));
                }
                if (txData.getType().equals(Certify.getTypeCertificateEd25519V1())) {
                    CertificateEd25519V1 certificateEd25519 = (CertificateEd25519V1) txData;
                    System.out.println("CertificateEd25519V1");
                    System.out.println(String.format("  Id             : %s", certificateEd25519.getId()));
                    System.out.println(String.format("  Data signer    : %s", Base64.getEncoder().encodeToString(certificateEd25519.getSigner().getKey())));
                    System.out.println(String.format("  Data signature : %s", Base64.getEncoder().encodeToString(certificateEd25519.getSignature())));
                }
                System.out.println();
            }
        } catch (IOException | ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}