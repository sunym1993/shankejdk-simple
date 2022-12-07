/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLHandshakeException;
import sun.security.internal.spec.TlsKeyMaterialParameterSpec;
import sun.security.internal.spec.TlsKeyMaterialSpec;
import sun.security.ssl.CipherSuite.HashAlg;
import static sun.security.ssl.CipherSuite.HashAlg.H_NONE;

enum SSLTrafficKeyDerivation implements SSLKeyDerivationGenerator {
    SSL30       ("kdf_ssl30", new S30TrafficKeyDerivationGenerator()),
    TLS10       ("kdf_tls10", new T10TrafficKeyDerivationGenerator()),
    TLS12       ("kdf_tls12", new T12TrafficKeyDerivationGenerator()),
    TLS13       ("kdf_tls13", new T13TrafficKeyDerivationGenerator());

    final String name;
    final SSLKeyDerivationGenerator keyDerivationGenerator;

    SSLTrafficKeyDerivation(String name,
            SSLKeyDerivationGenerator keyDerivationGenerator) {
        this.name = name;
        this.keyDerivationGenerator = keyDerivationGenerator;
    }

    static SSLTrafficKeyDerivation valueOf(ProtocolVersion protocolVersion) {
        switch (protocolVersion) {
            case SSL30:
                return SSLTrafficKeyDerivation.SSL30;
            case TLS10:
            case TLS11:
                return SSLTrafficKeyDerivation.TLS10;
            case TLS12:
                return SSLTrafficKeyDerivation.TLS12;
            case TLS13:
                return SSLTrafficKeyDerivation.TLS13;
        }

        return null;
    }

    @Override
    public SSLKeyDerivation createKeyDerivation(HandshakeContext context,
            SecretKey secretKey) throws IOException {
        return keyDerivationGenerator.createKeyDerivation(context, secretKey);
    }

    private static final class S30TrafficKeyDerivationGenerator
            implements SSLKeyDerivationGenerator {
        private S30TrafficKeyDerivationGenerator() {
            // blank
        }

        @Override
        public SSLKeyDerivation createKeyDerivation(
            HandshakeContext context, SecretKey secretKey) throws IOException {
            return new LegacyTrafficKeyDerivation(context, secretKey);
        }
    }

    private static final class T10TrafficKeyDerivationGenerator
            implements SSLKeyDerivationGenerator {
        private T10TrafficKeyDerivationGenerator() {
            // blank
        }

        @Override
        public SSLKeyDerivation createKeyDerivation(
            HandshakeContext context, SecretKey secretKey) throws IOException {
            return new LegacyTrafficKeyDerivation(context, secretKey);
        }
    }

    private static final class T12TrafficKeyDerivationGenerator
            implements SSLKeyDerivationGenerator {
        private T12TrafficKeyDerivationGenerator() {
            // blank
        }

        @Override
        public SSLKeyDerivation createKeyDerivation(
            HandshakeContext context, SecretKey secretKey) throws IOException {
            return new LegacyTrafficKeyDerivation(context, secretKey);
        }
    }

    private static final class T13TrafficKeyDerivationGenerator
            implements SSLKeyDerivationGenerator {
        private T13TrafficKeyDerivationGenerator() {
            // blank
        }

        @Override
        public SSLKeyDerivation createKeyDerivation(
                HandshakeContext context,
                SecretKey secretKey) throws IOException {
            return new T13TrafficKeyDerivation(context, secretKey);
        }
    }

    static final class T13TrafficKeyDerivation implements SSLKeyDerivation {
        private final CipherSuite cs;
        private final SecretKey secret;

        T13TrafficKeyDerivation(
                HandshakeContext context, SecretKey secret) {
            this.secret = secret;
            this.cs = context.negotiatedCipherSuite;
        }

        @Override
        public SecretKey deriveKey(String algorithm,
                AlgorithmParameterSpec params) throws IOException {
            KeySchedule ks = KeySchedule.valueOf(algorithm);
            try {
                HKDF hkdf = new HKDF(cs.hashAlg.name);
                byte[] hkdfInfo =
                        createHkdfInfo(ks.label, ks.getKeyLength(cs));
                return hkdf.expand(secret, hkdfInfo,
                        ks.getKeyLength(cs),
                        ks.getAlgorithm(cs, algorithm));
            } catch (GeneralSecurityException gse) {
                throw (SSLHandshakeException)(new SSLHandshakeException(
                    "Could not generate secret").initCause(gse));
            }
        }

        private static byte[] createHkdfInfo(
                byte[] label, int length) throws IOException {
            byte[] info = new byte[4 + label.length];
            ByteBuffer m = ByteBuffer.wrap(info);
            try {
                Record.putInt16(m, length);
                Record.putBytes8(m, label);
                Record.putInt8(m, 0x00);    // zero-length context
            } catch (IOException ioe) {
                // unlikely
                throw new RuntimeException("Unexpected exception", ioe);
            }

            return info;
        }
    }

    private enum KeySchedule {
        // Note that we use enum name as the key/ name.
        TlsKey              ("key", false),
        TlsIv               ("iv",  true),
        TlsUpdateNplus1     ("traffic upd", false);

        private final byte[] label;
        private final boolean isIv;

        private KeySchedule(String label, boolean isIv) {
            this.label = ("tls13 " + label).getBytes();
            this.isIv = isIv;
        }

        int getKeyLength(CipherSuite cs) {
            if (this == KeySchedule.TlsUpdateNplus1)
                return cs.hashAlg.hashLength;
            return isIv ? cs.bulkCipher.ivSize : cs.bulkCipher.keySize;
        }

        String getAlgorithm(CipherSuite cs, String algorithm) {
            return isIv ? algorithm : cs.bulkCipher.algorithm;
        }
    }

    @SuppressWarnings("deprecation")
    static final class LegacyTrafficKeyDerivation implements SSLKeyDerivation {
        private final HandshakeContext context;
        private final SecretKey masterSecret;
        private final TlsKeyMaterialSpec keyMaterialSpec;

        LegacyTrafficKeyDerivation(
                HandshakeContext context, SecretKey masterSecret) {
            this.context = context;
            this.masterSecret = masterSecret;

            CipherSuite cipherSuite = context.negotiatedCipherSuite;
            ProtocolVersion protocolVersion = context.negotiatedProtocol;

            /*
             * For both the read and write sides of the protocol, we use the
             * master to generate MAC secrets and cipher keying material.  Block
             * ciphers need initialization vectors, which we also generate.
             *
             * First we figure out how much keying material is needed.
             */
            int hashSize = cipherSuite.macAlg.size;
            boolean is_exportable = cipherSuite.exportable;
            SSLCipher cipher = cipherSuite.bulkCipher;
            int expandedKeySize = is_exportable ? cipher.expandedKeySize : 0;

            // Which algs/params do we need to use?
            String keyMaterialAlg;
            HashAlg hashAlg;

            byte majorVersion = protocolVersion.major;
            byte minorVersion = protocolVersion.minor;
            if (protocolVersion.id >= ProtocolVersion.TLS12.id) {
                keyMaterialAlg = "SunTls12KeyMaterial";
                hashAlg = cipherSuite.hashAlg;
            } else {
                keyMaterialAlg = "SunTlsKeyMaterial";
                hashAlg = H_NONE;
            }

            // TLS v1.1+ use an explicit IV in CBC cipher suites to
            // protect against the CBC attacks.  AEAD/GCM cipher suites in
            // TLS v1.2 or later use a fixed IV as the implicit part of the
            // partially implicit nonce technique described in RFC 5116.
            int ivSize = cipher.ivSize;
            if (cipher.cipherType == CipherType.AEAD_CIPHER) {
                ivSize = cipher.fixedIvSize;
            } else if (
                    cipher.cipherType == CipherType.BLOCK_CIPHER &&
                    protocolVersion.useTLS11PlusSpec()) {
                ivSize = 0;
            }

            TlsKeyMaterialParameterSpec spec = new TlsKeyMaterialParameterSpec(
                    masterSecret, (majorVersion & 0xFF), (minorVersion & 0xFF),
                    context.clientHelloRandom.randomBytes,
                    context.serverHelloRandom.randomBytes,
                    cipher.algorithm, cipher.keySize, expandedKeySize,
                    ivSize, hashSize,
                    hashAlg.name, hashAlg.hashLength, hashAlg.blockSize);

            try {
                KeyGenerator kg = JsseJce.getKeyGenerator(keyMaterialAlg);
                kg.init(spec);

                this.keyMaterialSpec = (TlsKeyMaterialSpec)kg.generateKey();
            } catch (GeneralSecurityException e) {
                throw new ProviderException(e);
            }
        }

        SecretKey getTrafficKey(String algorithm) {
            switch (algorithm) {
                case "clientMacKey":
                    return keyMaterialSpec.getClientMacKey();
                case "serverMacKey":
                    return keyMaterialSpec.getServerMacKey();
                case "clientWriteKey":
                    return keyMaterialSpec.getClientCipherKey();
                case "serverWriteKey":
                    return keyMaterialSpec.getServerCipherKey();
                case "clientWriteIv":
                    IvParameterSpec cliIvSpec = keyMaterialSpec.getClientIv();
                    return  (cliIvSpec == null) ? null :
                            new SecretKeySpec(cliIvSpec.getIV(), "TlsIv");
                case "serverWriteIv":
                    IvParameterSpec srvIvSpec = keyMaterialSpec.getServerIv();
                    return  (srvIvSpec == null) ? null :
                            new SecretKeySpec(srvIvSpec.getIV(), "TlsIv");
            }

            return null;
        }

        @Override
        public SecretKey deriveKey(String algorithm,
                AlgorithmParameterSpec params) throws IOException {
            return getTrafficKey(algorithm);
        }
    }
}
