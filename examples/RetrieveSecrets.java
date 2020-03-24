/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.crypto.Nacl.PrivateKey;
import com.github.katenachain.Transactor;
import com.github.katenachain.entity.api.TxWrapper;
import com.github.katenachain.entity.api.TxWrappers;
import com.github.katenachain.entity.TxData;
import com.github.katenachain.entity.certify.SecretNaclBoxV1;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.utils.Common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RetrieveSecrets {
    public static void main(String[] args) {
        // Bob wants to read a nacl box secret from Alice to decrypt an off-chain data

        // Common Katena network information
        String apiUrl = "https://nodes.test.katena.transchain.io/api/v1";

        // Alice Katena network information
        String aliceCompanyBcid = "abcdef";

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl);

        // Nacl box information
        String bobCryptPrivateKeyBase64 = "quGBP8awD/J3hjSvwGD/sZRcMDks8DPz9Vw0HD4+zecqJP0ojBoc4wQtyq08ywxUksTkdz0/rQNkOsEZBwqWTw==";
        PrivateKey bobCryptPrivateKey = new PrivateKey(bobCryptPrivateKeyBase64);

        // Secret uuid Bob wants to retrieve
        String secretUuid = "2075c941-6876-405b-87d5-13791c0dc53a";

        try {
            // Retrieve version 1 of secrets from Katena
            TxWrappers txWrappers = transactor.retrieveSecrets(aliceCompanyBcid, secretUuid, 1, Common.DEFAULT_PER_PAGE_PARAM);

            for (TxWrapper txWrapper : txWrappers.getTxs()
            ) {

                TxData txData = txWrapper.getTx().getData();

                System.out.println("Transaction status");
                System.out.println(String.format("  Code    : %d", txWrapper.getStatus().getCode()));
                System.out.println(String.format("  Message : %s", txWrapper.getStatus().getMessage()));


                SecretNaclBoxV1 secret = (SecretNaclBoxV1) txData;
                System.out.println("SecretNaclBoxV1");
                System.out.println(String.format("  Id                : %s", secret.getId()));
                System.out.println(String.format("  Data sender       : %s", Base64.getEncoder().encodeToString(secret.getSender().getKey())));
                System.out.println(String.format("  Data nonce        : %s", Base64.getEncoder().encodeToString(secret.getNonce())));
                System.out.println(String.format("  Data content      : %s", Base64.getEncoder().encodeToString(secret.getContent())));

                // Bob will use its private key and the sender's public key (needs to be Alice's) to decrypt a message
                String decryptedContent = new String(bobCryptPrivateKey.open(secret.getContent(), secret.getSender(), secret.getNonce()), StandardCharsets.UTF_8);

                if (decryptedContent.equals("")) {
                    decryptedContent = "Unable to decrypt";
                }

                System.out.println(String.format("  Decrypted content : %s", decryptedContent));
                System.out.println();
            }
        } catch (IOException | ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}