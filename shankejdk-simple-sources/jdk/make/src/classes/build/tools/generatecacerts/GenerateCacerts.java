/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatecacerts;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Generate cacerts
 *    args[0]: Full path string to the directory that contains CA certs
 *    args[1]: Full path string to the generated cacerts
 */
public class GenerateCacerts {
    public static void main(String[] args) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(args[1])) {
            store(args[0], fos, "changeit".toCharArray());
        }
    }

    // The following code is copied from JavaKeyStore.java.

    private static final int MAGIC = 0xfeedfeed;
    private static final int VERSION_2 = 0x02;

    // This method is a simplified version of JavaKeyStore::engineStore.
    // A new "dir" argument is added. All cert names in "dir" is collected into
    // a sorted array. Each cert is stored with a creation date set to its
    // notBefore value. Thus the output is determined as long as the certs
    // are the same.
    public static void store(String dir, OutputStream stream, char[] password)
            throws IOException, NoSuchAlgorithmException, CertificateException
    {
        byte[] encoded; // the certificate encoding
        CertificateFactory cf = CertificateFactory.getInstance("X509");

        MessageDigest md = getPreKeyedHash(password);
        DataOutputStream dos
                = new DataOutputStream(new DigestOutputStream(stream, md));

        dos.writeInt(MAGIC);
        // always write the latest version
        dos.writeInt(VERSION_2);

        // All file names in dir sorted.
        // README is excluded. Name starting with "." excluded.
        SortedSet<String> entries = new TreeSet<String>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path p : ds) {
                String fName = p.getFileName().toString();
                if (!fName.equals("README") && !fName.startsWith(".")) {
                    entries.add(fName);
                }
            }
        }

        dos.writeInt(entries.size());

        for (String entry : entries) {

            String alias = entry + " [jdk]";
            X509Certificate cert;
            try (InputStream fis = Files.newInputStream(Paths.get(dir, entry))) {
                cert = (X509Certificate) cf.generateCertificate(fis);
            }

            dos.writeInt(2);

            // Write the alias
            dos.writeUTF(alias);

            // Write the (entry creation) date, which is notBefore of the cert
            dos.writeLong(cert.getNotBefore().getTime());

            // Write the trusted certificate
            encoded = cert.getEncoded();
            dos.writeUTF(cert.getType());
            dos.writeInt(encoded.length);
            dos.write(encoded);
        }

        /*
         * Write the keyed hash which is used to detect tampering with
         * the keystore (such as deleting or modifying key or
         * certificate entries).
         */
        byte[] digest = md.digest();

        dos.write(digest);
        dos.flush();
    }

    private static MessageDigest getPreKeyedHash(char[] password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {

        MessageDigest md = MessageDigest.getInstance("SHA");
        byte[] passwdBytes = convertToBytes(password);
        md.update(passwdBytes);
        Arrays.fill(passwdBytes, (byte) 0x00);
        md.update("Mighty Aphrodite".getBytes("UTF8"));
        return md;
    }

    private static byte[] convertToBytes(char[] password) {
        int i, j;
        byte[] passwdBytes = new byte[password.length * 2];
        for (i=0, j=0; i<password.length; i++) {
            passwdBytes[j++] = (byte)(password[i] >> 8);
            passwdBytes[j++] = (byte)password[i];
        }
        return passwdBytes;
    }
}
