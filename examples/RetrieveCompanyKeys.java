/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.account.KeyV1;
import com.github.katenachain.entity.api.TxWrapper;
import com.github.katenachain.entity.api.TxWrappers;
import com.github.katenachain.entity.certify.CertificateEd25519V1;
import com.github.katenachain.entity.certify.CertificateRawV1;
import com.github.katenachain.entity.certify.Certify;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.utils.Common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class RetrieveCompanyKeys {
    public static void main(String[] args) {
        // Alice wants to retrieve the keys of its company

        // Common Katena network information
        String apiUrl = "https://nodes.preprod.katena.io/api/v1";

        // Alice Katena network information
        String aliceCompanyBcid = "abcdef";

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl);

        try {
            // Retrieve the keys from Katena
            KeyV1[] keys = transactor.retrieveCompanyKeys(aliceCompanyBcid, 1, Common.DEFAULT_PER_PAGE_PARAM);

            for (KeyV1 key : keys
            ) {
                System.out.println("KeyV1");
                System.out.println(String.format("  PublicKey   : %s", Base64.getEncoder().encodeToString(key.getPublicKey().getKey())));
                System.out.println(String.format("  IsActive    : %b", key.isActive()));
                System.out.println(String.format("  CompanyBcid : %s", key.getCompanyBcid()));
                System.out.println(String.format("  Role        : %s", key.getRole()));
                System.out.println();
            }
        } catch (IOException | ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
