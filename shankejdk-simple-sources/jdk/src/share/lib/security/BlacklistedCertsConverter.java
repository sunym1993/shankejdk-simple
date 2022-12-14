/*
 * Copyright (c) 2013, 2020, Oracle and/or its affiliates. All rights reserved.
 */

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

/**
 * This is the tool to convert blacklisted.certs.pem to blacklisted.certs.
 * Every time a new blacklisted certs is added, please append the PEM format
 * to the end of blacklisted.certs.pem (with proper comments) and then use
 * this tool to generate an updated blacklisted.certs. Make sure to include
 * changes to both in a changeset.
 */
public class BlacklistedCertsConverter {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java BlacklistedCertsConverter SHA-256" +
                    " < blacklisted.certs.pem > blacklisted.certs");
            System.exit(1);
        }
        String mdAlg = args[0];
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certs
                = cf.generateCertificates(System.in);
        System.out.println("Algorithm=" + mdAlg);
        Set<String> fingerprints = new TreeSet<>();
        for (Certificate cert: certs) {
            fingerprints.addAll(
                    getCertificateFingerPrints(mdAlg, (X509Certificate)cert));
        }

        for (String s: fingerprints) {
            System.out.println(s);
        }
    }

    /**
     * Converts a byte to hex digit and writes to the supplied buffer
     */
    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /**
     * Gets the requested fingerprints of the certificate.
     */
    private static List<String> getCertificateFingerPrints(
            String mdAlg, X509Certificate cert) throws Exception {
        List<String> fingerprints = new ArrayList<>();
        for (byte[] encoding : altEncodings(cert)) {
            MessageDigest md = MessageDigest.getInstance(mdAlg);
            byte[] digest = md.digest(encoding);
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                byte2hex(digest[i], buf);
            }
            fingerprints.add(buf.toString());
        }
        return fingerprints;
    }

    private static List<byte[]> altEncodings(X509Certificate c)
            throws Exception {
        List<byte[]> result = new ArrayList<>();

        DerValue d = new DerValue(c.getEncoded());
        DerValue[] seq = new DerValue[3];
        // tbsCertificate
        seq[0] = d.data.getDerValue();
        // signatureAlgorithm
        seq[1] = d.data.getDerValue();
        // signature
        seq[2] = d.data.getDerValue();

        List<DerValue> algIds = Arrays.asList(seq[1], altAlgId(seq[1]));

        List<DerValue> sigs;
        PublicKey p = c.getPublicKey();
        if (p instanceof ECPublicKey) {
            ECPublicKey ep = (ECPublicKey) p;
            BigInteger mod = ep.getParams().getOrder();
            sigs = Arrays.asList(seq[2], altSig(mod, seq[2]));
        } else {
            sigs = Arrays.asList(seq[2]);
        }

        for (DerValue algId : algIds) {
            for (DerValue sig : sigs) {
                DerOutputStream tmp = new DerOutputStream();
                tmp.putDerValue(seq[0]);
                tmp.putDerValue(algId);
                tmp.putDerValue(sig);
                DerOutputStream tmp2 = new DerOutputStream();
                tmp2.write(DerValue.tag_Sequence, tmp);
                result.add(tmp2.toByteArray());
            }
        }
        return result;
    }

    private static DerValue altSig(BigInteger mod, DerValue sig)
            throws IOException {
        byte[] sigBits = sig.getBitString();
        DerInputStream in =
            new DerInputStream(sigBits, 0, sigBits.length, false);
        DerValue[] values = in.getSequence(2);
        BigInteger r = values[0].getBigInteger();
        BigInteger s = values[1].getBigInteger();
        BigInteger s2 = s.negate().mod(mod);
        DerOutputStream out = new DerOutputStream();
        out.putInteger(r);
        out.putInteger(s2);
        DerOutputStream tmp = new DerOutputStream();
        tmp.putBitString(new DerValue(DerValue.tag_Sequence,
                out.toByteArray()).toByteArray());
        return new DerValue(tmp.toByteArray());
    }

    private static DerValue altAlgId(DerValue algId) throws IOException {
        DerInputStream in = algId.toDerInputStream();
        DerOutputStream bytes = new DerOutputStream();
        bytes.putOID(in.getOID());
        // encode parameters as NULL if not present or omit if NULL
        if (in.available() == 0) {
            bytes.putNull();
        }
        DerOutputStream tmp = new DerOutputStream();
        tmp.write(DerValue.tag_Sequence, bytes);
        return new DerValue(tmp.toByteArray());
    }
}
