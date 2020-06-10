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
import com.github.katenachain.utils.Common;
import com.github.katenachain.utils.Crypto;
import common.Settings;

import static common.Log.printlnJson;

public class SendCertificateEd25519 {
    public static void main(String[] args) {
        // Alice wants to certify an ed25519 signature of an off-chain data

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
            // Off-chain information Alice want to send
            String certificateId = Settings.certificateId;
            Settings.KeyPair davidSignKeyInfo = Settings.OffChain.ed25519Keys.get("david");
            PrivateKey davidSignPrivateKey = Crypto.createPrivateKeyEd25519FromBase64(davidSignKeyInfo.privateKeyStr);
            byte[] dataSignature = davidSignPrivateKey.sign("off_chain_data_to_sign_from_java".getBytes());

            // Send a version 1 of a certificate on Katena
            SendTxResult txResult = transactor.sendCertificateEd25519V1Tx(certificateId, davidSignPrivateKey.getPublicKey(), dataSignature);

            System.out.println("Result :");
            printlnJson(txResult);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}