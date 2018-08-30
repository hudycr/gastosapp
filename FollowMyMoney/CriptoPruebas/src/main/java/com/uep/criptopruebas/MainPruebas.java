package com.uep.criptopruebas;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import sun.applet.Main;

public class MainPruebas {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public byte [] privateKeyBytes;
    public byte [] publicKeyBytes;
    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }
    public static void main(String[] args) throws Exception {
        MainPruebas m = new MainPruebas();
        KeyFactory kf = KeyFactory.getInstance("ECDSA","SC");
        PublicKey pu = kf.generatePublic(new X509EncodedKeySpec(m.publicKeyBytes));
        PrivateKey pri = kf.generatePrivate(new PKCS8EncodedKeySpec(m.privateKeyBytes));
        if (pu == m.publicKey){
            System.out.println("publica igual");
        }

        if (pri == m.privateKey){
            System.out.println("Privada igual");
        }
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
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
