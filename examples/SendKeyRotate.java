/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.crypto.ED25519.PublicKey;
import com.github.katenachain.entity.TxSigner;
import com.github.katenachain.entity.api.SendTxResult;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;
import com.github.katenachain.utils.Common;
import com.github.katenachain.utils.Crypto;
import common.Settings;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;

import static common.Log.printlnJson;

public class SendKeyRotate {
    public static void main(String[] args) {
        // Alice wants to rotate a key for its company

        // Common Katena network information
        String apiUrl = Settings.apiUrl;
        String chainId = Settings.chainId;

        // Alice Katena network information
        String aliceCompanyBcId = Settings.Company.bcId;
        Settings.Key aliceSignKeyInfo = Settings.Company.ed25519Keys.get("alice");
        PrivateKey aliceSignPrivateKey = Crypto.createPrivateKeyEd25519FromBase64(aliceSignKeyInfo.privateKeyStr);
        String aliceSignPrivateKeyId = aliceSignKeyInfo.id;

        // Create a Katena API helper
        TxSigner txSigner = new TxSigner(Common.concatFqId(aliceCompanyBcId, aliceSignPrivateKeyId), aliceSignPrivateKey);
        Transactor transactor = new Transactor(apiUrl, chainId, txSigner);

        try {
            // Information Alice wants to send
            String keyId = Settings.keyId;
            PrivateKey newPrivateKey = Crypto.generateNewPrivateKeyEd25519();
            PublicKey newPublicKey = newPrivateKey.getPublicKey();

            // Send a version 1 of a key rotate on Katena
            SendTxResult txResult = transactor.sendKeyRotateV1Tx(keyId, newPublicKey);

            System.out.println("Result :");
            printlnJson(txResult);

            System.out.println("New key info :");
            System.out.println(String.format("  Private key : %s", Base64.getEncoder().encodeToString(newPrivateKey.getKey())));
            System.out.println(String.format("  Public key  : %s", Base64.getEncoder().encodeToString(newPublicKey.getKey())));
        } catch (ApiException | IOException | NoSuchAlgorithmException | InvalidKeyException | SignatureException | ClientException | InvalidAlgorithmParameterException e) {
            System.out.print(e.getMessage());
        }

    }
}
