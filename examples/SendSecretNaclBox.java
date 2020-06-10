/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.entity.TxSigner;
import com.github.katenachain.entity.api.SendTxResult;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;
import com.github.katenachain.utils.Common;
import com.github.katenachain.utils.Crypto;
import common.Settings;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Hashtable;

import static common.Log.printlnJson;

public class SendSecretNaclBox {
    public static void main(String[] args) {
        // Alice wants to send a nacl box secret to Bob to encrypt an off-chain data

        // Common Katena network information
        String apiUrl = Settings.apiUrl;
        String chainId = Settings.chainId;

        // Alice Katena network information
        String aliceCompanyBcId = Settings.Company.bcId;
        Settings.Key aliceSignKeyInfo = Settings.Company.ed25519Keys.get("alice");
        PrivateKey aliceSignPrivateKey = Crypto.createPrivateKeyEd25519FromBase64(aliceSignKeyInfo.privateKeyStr);
        String aliceSignPrivateKeyId = aliceSignKeyInfo.id;

        // Nacl box information
        Settings.KeyPair aliceCryptKeyInfo = Settings.OffChain.x25519Keys.get("alice");
        com.github.katenachain.crypto.Nacl.PrivateKey aliceCryptPrivateKey = Crypto.createPrivateKeyX25519FromBase64(aliceCryptKeyInfo.privateKeyStr);
        Settings.KeyPair bobCryptKeyInfo = Settings.OffChain.x25519Keys.get("bob");
        com.github.katenachain.crypto.Nacl.PublicKey bobCryptPublicKey = Crypto.createPublicKeyX25519FromBase64(bobCryptKeyInfo.publicKeyStr);

        // Create a Katena API helper
        TxSigner txSigner = new TxSigner(Common.concatFqId(aliceCompanyBcId, aliceSignPrivateKeyId), aliceSignPrivateKey);
        Transactor transactor = new Transactor(apiUrl, chainId, txSigner);

        // Off-chain information Alice wants to send
        String secretId = Settings.secretId;
        String content = "off_chain_secret_to_crypt_from_java";
        try {
            // Alice will use its private key and Bob's public key to encrypt a message
            Hashtable<String, byte[]> encryptedInfo = aliceCryptPrivateKey.seal(content.getBytes(), bobCryptPublicKey);

            // Send a version 1 of a secret nacl box on Katena
            SendTxResult txResult = transactor.sendSecretNaclBoxV1Tx(secretId, aliceCryptPrivateKey.getPublicKey(), encryptedInfo.get("nonce"), encryptedInfo.get("encryptedMessage"));

            System.out.println("Result :");
            printlnJson(txResult);

        } catch (IOException | ApiException | InvalidKeyException | SignatureException | NoSuchAlgorithmException | ClientException e) {
            System.out.print(e.getMessage());
        }
    }
}