/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.Transactor;
import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.crypto.ED25519.PublicKey;
import com.github.katenachain.entity.account.Account;
import com.github.katenachain.entity.api.TxStatus;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;

public class SendKeyCreate {
    public static void main(String[] args) {
        // Alice wants to create a key for its company

        // Common Katena network information
        String apiUrl = "https://nodes.preprod.katena.io/api/v1";
        String chainID = "katena-chain-preprod";

        // Alice Katena network information
        String aliceSignPrivateKeyBase64 = "7C67DeoLnhI6jvsp3eMksU2Z6uzj8sqZbpgwZqfIyuCZbfoPcitCiCsSp2EzCfkY52Mx58xDOyQLb1OhC7cL5A==";
        String aliceCompanyBcid = "abcdef";
        PrivateKey aliceSignPrivateKey = new PrivateKey(aliceSignPrivateKeyBase64);

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl, chainID, aliceCompanyBcid, aliceSignPrivateKey);

        // Information Alice wants to send
        String keyCreateUuid = "2075c941-6876-405b-87d5-13791c0dc53a";
        String newPublicKeyBase64 = "gaKih+STp93wMuKmw5tF5NlQvOlrGsahpSmpr/KwOiw=";
        PublicKey newPublicKey = new PublicKey(Base64.getDecoder().decode(newPublicKeyBase64));
        String role = Account.DEFAULT_ROLE_ID;

        // Send a version 1 of a key create on Katena
        try {
            TxStatus txStatus = transactor.sendKeyCreateV1(keyCreateUuid, newPublicKey, role);

            System.out.println("Transaction status");
            System.out.println(String.format("  Code    : %d", txStatus.getCode()));
            System.out.println(String.format("  Message : %s", txStatus.getMessage()));
        } catch (ApiException | IOException | NoSuchAlgorithmException | InvalidKeyException | SignatureException | ClientException e) {
            System.out.print(e.getMessage());
        }

    }
}
