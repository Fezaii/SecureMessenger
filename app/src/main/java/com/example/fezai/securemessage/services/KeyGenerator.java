package com.example.fezai.securemessage.services;


import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.PrivateKey;
import java.security.PublicKey;

public final class KeyGenerator {

    private PublicKey pk;

    private PrivateKey prk;

    public KeyGenerator() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException{
        KeyPairGenerator kpg;

        KeyPair kp;

        kpg = KeyPairGenerator.getInstance("RSA");

        kpg.initialize(2048);

        kp = kpg.generateKeyPair();

        this.pk= kp.getPublic();
        this.prk=kp.getPrivate();

    }
    public PublicKey getPk() {
        return pk;
    }

    public PrivateKey getPrk() {
        return prk;
    }

}
