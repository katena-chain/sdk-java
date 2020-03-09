/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.Transactor;
import com.github.katenachain.entity.api.TxStatus;
import com.github.katenachain.exceptions.ApiException;
import com.github.katenachain.exceptions.ClientException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class SendCertificateRaw {
    public static void main(String[] args) {
        // Alice wants to certify raw off-chain information

        // Common Katena network information
        String apiUrl = "https://nodes.preprod.katena.io/api/v1";
        String chainID = "katena-chain-preprod";

        // Alice Katena network information
        String aliceSignPrivateKeyBase64 = "7C67DeoLnhI6jvsp3eMksU2Z6uzj8sqZbpgwZqfIyuCZbfoPcitCiCsSp2EzCfkY52Mx58xDOyQLb1OhC7cL5A==";
        String aliceCompanyBcid = "abcdef";
        PrivateKey aliceSignPrivateKey = new PrivateKey(aliceSignPrivateKeyBase64);

        // Create a Katena API helper
        Transactor transactor = new Transactor(apiUrl, chainID, aliceCompanyBcid, aliceSignPrivateKey);

        // Off-chain information Alice wants to send
        String certificateUuid = "2075c941-6876-405b-87d5-13791c0dc53a";
        String dataRawSignature = "off_chain_data_raw_signature_from_java";
        try {
            // Send a version 1 of a certificate raw on Katena
            TxStatus txStatus = transactor.sendCertificateRawV1(certificateUuid, dataRawSignature.getBytes());

            System.out.println("Transaction status");
            System.out.println(String.format("  Code    : %d", txStatus.getCode()));
            System.out.println(String.format("  Message : %s", txStatus.getMessage()));

        } catch (IOException | ApiException | InvalidKeyException | SignatureException | NoSuchAlgorithmException | ClientException e) {
            System.out.print(e.getMessage());
        }
    }
}