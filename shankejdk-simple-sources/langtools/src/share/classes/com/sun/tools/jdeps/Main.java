/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.jdeps;

import java.io.*;

/**
 *
 * Usage:
 *    jdeps [options] files ...
 * where options include:
 *    -p package-name   restrict analysis to classes in this package
 *                      (may be given multiple times)
 *    -e regex          restrict analysis to packages matching pattern
 *                      (-p and -e are exclusive)
 *    -v                show class-level dependencies
 *                      default: package-level dependencies
 *    -r --recursive    transitive dependencies analysis
 *    -classpath paths  Classpath to locate class files
 *    -all              process all class files in the given classpath
 */
public class Main {
    public static void main(String... args) throws Exception {
        JdepsTask t = new JdepsTask();
        int rc = t.run(args);
        System.exit(rc);
    }


    /**
     * Entry point that does <i>not</i> call System.exit.
     *
     * @param args command line arguments
     * @param out output stream
     * @return an exit code. 0 means success, non-zero means an error occurred.
     */
    public static int run(String[] args, PrintWriter out) {
        JdepsTask t = new JdepsTask();
        t.setLog(out);
        return t.run(args);
    }
}
