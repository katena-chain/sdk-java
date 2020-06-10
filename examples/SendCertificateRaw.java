/*
 * Copyright (c) 2018, TransChain.
 *
 * This source code is licensed under the Apache 2.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.github.katenachain.crypto.ED25519.PrivateKey;
import com.github.katenachain.Transactor;
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

import static common.Log.printlnJson;

public class SendCertificateRaw {
    public static void main(String[] args) {
        // Alice wants to certify raw off-chain information

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

        // Off-chain information Alice wants to send
        String certificateId = Settings.certificateId;
        String dataRawSignature = "off_chain_data_raw_signature_from_java";

        try {
            // Send a version 1 of a certificate on Katena
            SendTxResult txResult = transactor.sendCertificateRawV1Tx(certificateId, dataRawSignature.getBytes());

            System.out.println("Result :");
            printlnJson(txResult);

        } catch (IOException | ApiException | InvalidKeyException | SignatureException | NoSuchAlgorithmException | ClientException e) {
            System.out.print(e.getMessage());
        }
    }
}