/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package sun.tools.jar;

import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.StreamTokenizer;
import java.util.List;
import java.util.ArrayList;

/**
 * Various utility methods for processing Java tool command line arguments.
 *
 *  <p><b>This is NOT part of any API supported by Oracle.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class CommandLine {
    /**
     * Process Win32-style command files for the specified command line
     * arguments and return the resulting arguments. A command file argument
     * is of the form '@file' where 'file' is the name of the file whose
     * contents are to be parsed for additional arguments. The contents of
     * the command file are parsed using StreamTokenizer and the original
     * '@file' argument replaced with the resulting tokens. Recursive command
     * files are not supported. The '@' character itself can be quoted with
     * the sequence '@@'.
     */
    public static String[] parse(String[] args)
        throws IOException
    {
        List<String> newArgs = new ArrayList<>(args.length);
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.length() > 1 && arg.charAt(0) == '@') {
                arg = arg.substring(1);
                if (arg.charAt(0) == '@') {
                    newArgs.add(arg);
                } else {
                    loadCmdFile(arg, newArgs);
                }
            } else {
                newArgs.add(arg);
            }
        }
        return newArgs.toArray(new String[newArgs.size()]);
    }

    private static void loadCmdFile(String name, List<String> args)
        throws IOException
    {
        Reader r = new BufferedReader(new FileReader(name));
        StreamTokenizer st = new StreamTokenizer(r);
        st.resetSyntax();
        st.wordChars(' ', 255);
        st.whitespaceChars(0, ' ');
        st.commentChar('#');
        st.quoteChar('"');
        st.quoteChar('\'');
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            args.add(st.sval);
        }
        r.close();
    }
}
