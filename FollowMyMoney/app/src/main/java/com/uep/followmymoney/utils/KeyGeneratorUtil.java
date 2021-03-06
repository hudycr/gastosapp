package com.uep.followmymoney.utils;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyGeneratorUtil {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public byte [] privateKeyBytes;
    public byte [] publicKeyBytes;

    public KeyGeneratorUtil() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("data232v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random); //256
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
            privateKeyBytes = privateKey.getEncoded();
            publicKeyBytes = publicKey.getEncoded();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
