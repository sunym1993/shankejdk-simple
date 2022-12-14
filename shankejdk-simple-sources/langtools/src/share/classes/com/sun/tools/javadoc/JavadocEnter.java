/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.javadoc;

import javax.tools.JavaFileObject;

import com.sun.tools.javac.code.Kinds;
import com.sun.tools.javac.code.Symbol.*;
import com.sun.tools.javac.comp.Enter;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.JCDiagnostic.DiagnosticPosition;
import com.sun.tools.javac.util.List;

/**
 *  Javadoc's own enter phase does a few things above and beyond that
 *  done by javac.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 *
 *  @author Neal Gafter
 */
public class JavadocEnter extends Enter {
    public static JavadocEnter instance0(Context context) {
        Enter instance = context.get(enterKey);
        if (instance == null)
            instance = new JavadocEnter(context);
        return (JavadocEnter)instance;
    }

    public static void preRegister(Context context) {
        context.put(enterKey, new Context.Factory<Enter>() {
               public Enter make(Context c) {
                   return new JavadocEnter(c);
               }
        });
    }

    protected JavadocEnter(Context context) {
        super(context);
        messager = Messager.instance0(context);
        docenv = DocEnv.instance(context);
    }

    final Messager messager;
    final DocEnv docenv;

    @Override
    public void main(List<JCCompilationUnit> trees) {
        // count all Enter errors as warnings.
        int nerrors = messager.nerrors;
        super.main(trees);
        messager.nwarnings += (messager.nerrors - nerrors);
        messager.nerrors = nerrors;
    }

    @Override
    public void visitTopLevel(JCCompilationUnit tree) {
        super.visitTopLevel(tree);
        if (tree.sourcefile.isNameCompatible("package-info", JavaFileObject.Kind.SOURCE)) {
            docenv.makePackageDoc(tree.packge, docenv.getTreePath(tree));
        }
    }

    @Override
    public void visitClassDef(JCClassDecl tree) {
        super.visitClassDef(tree);
        if (tree.sym == null) return;
        if (tree.sym.kind == Kinds.TYP || tree.sym.kind == Kinds.ERR) {
            ClassSymbol c = tree.sym;
            docenv.makeClassDoc(c, docenv.getTreePath(env.toplevel, tree));
        }
    }

    /** Don't complain about a duplicate class. */
    @Override
    protected void duplicateClass(DiagnosticPosition pos, ClassSymbol c) {}

}
