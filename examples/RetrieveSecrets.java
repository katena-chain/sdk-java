/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.crypto.Nacl.PrivateKey;
import com.github.katenachain.Transactor;
import com.github.katenachain.entity.api.TxResult;
import com.github.katenachain.entity.api.TxResults;
import com.github.katenachain.entity.certify.SecretNaclBoxV1;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.utils.Crypto;
import common.Settings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static common.Log.printlnJson;

public class RetrieveSecrets {
    public static void main(String[] args) {
        // Bob wants to read a nacl box secret from Alice to decrypt an off-chain data

        // Common Katena network information
        String apiUrl = Settings.apiUrl;

        // Alice Katena network information
        String aliceCompanyBcId = Settings.Company.bcId;

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl);

        // Nacl box information
        Settings.KeyPair bobCryptKeyInfo = Settings.OffChain.x25519Keys.get("bob");
        PrivateKey bobCryptPrivateKey = Crypto.createPrivateKeyX25519FromBase64(bobCryptKeyInfo.privateKeyStr);

        // Secret id Bob wants to retrieve
        String secretId = Settings.secretId;

        try {
            // Retrieve txs related to the secret fqid
            TxResults txResults = transactor.retrieveSecretTxs(aliceCompanyBcId, secretId, 1, Settings.txPerPage);

            System.out.println("Tx list :");
            printlnJson(txResults);

            // Retrieve the last tx related to the secret fqid
            TxResult txResult = transactor.retrieveLastSecretTx(aliceCompanyBcId, secretId);

            System.out.println("Last Tx :");
            printlnJson(txResult);

            SecretNaclBoxV1 txData = (SecretNaclBoxV1) txResult.getTx().getData();
            // Bob will use its private key and the sender's public key (needs to be Alice's) to decrypt a message
            String decryptedContent = new String(bobCryptPrivateKey.open(txData.getContent(), txData.getSender(), txData.getNonce()), StandardCharsets.UTF_8);

            if (decryptedContent.equals("")) {
                decryptedContent = "Unable to decrypt";
            }

            System.out.println(String.format("Decrypted content for last Tx : %s", decryptedContent));
        } catch (IOException | ApiException e) {
            System.out.println(e.getMessage());
        }
    }
}