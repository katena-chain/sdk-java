/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.entity.api.TxStatus;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Hashtable;

public class SendSecretNaclBox {
    public static void main(String[] args) {
        // Alice wants to send a nacl box secret to Bob to encrypt an off-chain data

        // Common Katena network information
        String apiUrl = "https://nodes.preprod.katena.io/api/v1";
        String chainID = "katena-chain-preprod";

        // Alice Katena network information
        String aliceSignPrivateKeyBase64 = "7C67DeoLnhI6jvsp3eMksU2Z6uzj8sqZbpgwZqfIyuCZbfoPcitCiCsSp2EzCfkY52Mx58xDOyQLb1OhC7cL5A==";
        String aliceCompanyBcid = "abcdef";
        com.github.katenachain.crypto.ED25519.PrivateKey aliceSignPrivateKey = new com.github.katenachain.crypto.ED25519.PrivateKey(aliceSignPrivateKeyBase64);

        // Nacl box information
        String aliceCryptPrivateKeyBase64 = "nyCzhimWnTQifh6ucXLuJwOz3RgiBpo33LcX1NjMAsP1ZkQcdlDq64lTwxaDx0lq6LCQAUeYywyMUtfsvTUEeQ==";
        com.github.katenachain.crypto.Nacl.PrivateKey aliceCryptPrivateKey = new com.github.katenachain.crypto.Nacl.PrivateKey(aliceCryptPrivateKeyBase64);
        String bobCryptPublicKeyBase64 = "KiT9KIwaHOMELcqtPMsMVJLE5Hc9P60DZDrBGQcKlk8=";
        com.github.katenachain.crypto.Nacl.PublicKey bobCryptPublicKey = new com.github.katenachain.crypto.Nacl.PublicKey(bobCryptPublicKeyBase64);

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl, chainID, aliceCompanyBcid, aliceSignPrivateKey);

        // Off-chain information Alice wants to send
        String secretUuid = "2075c941-6876-405b-87d5-13791c0dc53a";
        String content = "off_chain_secret_to_sign_from_java";
        try {
            // Alice will use its private key and Bob's public key to encrypt a message
            Hashtable<String, byte[]> encryptedInfo = aliceCryptPrivateKey.seal(content.getBytes(), bobCryptPublicKey);

            // Send a version 1 of a secret nacl box on Katena
            TxStatus txStatus = transactor.sendSecretNaclBoxV1(secretUuid, aliceCryptPrivateKey.getPublicKey(), encryptedInfo.get("nonce"), encryptedInfo.get("encryptedMessage"));

            System.out.println("Transaction status");
            System.out.println(String.format("  Code    : %d", txStatus.getCode()));
            System.out.println(String.format("  Message : %s", txStatus.getMessage()));

        } catch (IOException | ApiException | InvalidKeyException | SignatureException | NoSuchAlgorithmException | ClientException e) {
            System.out.print(e.getMessage());
        }
    }
}