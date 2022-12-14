/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.reader.xmlschema;

import com.sun.tools.internal.xjc.reader.Ring;
import com.sun.tools.internal.xjc.reader.xmlschema.ct.ComplexTypeFieldBuilder;
import com.sun.xml.internal.bind.v2.TODO;
import com.sun.xml.internal.xsom.XSAttGroupDecl;
import com.sun.xml.internal.xsom.XSAttributeDecl;
import com.sun.xml.internal.xsom.XSAttributeUse;
import com.sun.xml.internal.xsom.XSComplexType;
import com.sun.xml.internal.xsom.XSContentType;
import com.sun.xml.internal.xsom.XSElementDecl;
import com.sun.xml.internal.xsom.XSModelGroup;
import com.sun.xml.internal.xsom.XSModelGroupDecl;
import com.sun.xml.internal.xsom.XSParticle;
import com.sun.xml.internal.xsom.XSSimpleType;
import com.sun.xml.internal.xsom.XSWildcard;

/**
 * This is where a binding of a new class starts.
 *
 * @author Kohsuke Kawaguchi
 */
public final class BindRed extends ColorBinder {

    private final ComplexTypeFieldBuilder ctBuilder = Ring.get(ComplexTypeFieldBuilder.class);

    public void complexType(XSComplexType ct) {
        ctBuilder.build(ct);
    }

    public void wildcard(XSWildcard xsWildcard) {
        // TODO: implement this method later
        // I guess we might allow this to be mapped to a generic element property ---
        // not sure exactly how do we do it.
        TODO.checkSpec();
        throw new UnsupportedOperationException();
    }

    public void elementDecl(XSElementDecl e) {
        SimpleTypeBuilder stb = Ring.get(SimpleTypeBuilder.class);
        stb.refererStack.push(e);    // referer is element
        builder.ying(e.getType(),e);
        stb.refererStack.pop();
    }

    public void simpleType(XSSimpleType type) {
        SimpleTypeBuilder stb = Ring.get(SimpleTypeBuilder.class);
        stb.refererStack.push(type);    // referer is itself
        createSimpleTypeProperty(type,"Value");
        stb.refererStack.pop();
    }

/*
    Components that can never be mapped to a class
*/
    public void attGroupDecl(XSAttGroupDecl ag) {
        throw new IllegalStateException();
    }
    public void attributeDecl(XSAttributeDecl ad) {
        throw new IllegalStateException();
    }
    public void attributeUse(XSAttributeUse au) {
        throw new IllegalStateException();
    }
    public void empty(XSContentType xsContentType) {
        throw new IllegalStateException();
    }
    public void modelGroupDecl(XSModelGroupDecl xsModelGroupDecl) {
        throw new IllegalStateException();
    }
    public void modelGroup(XSModelGroup xsModelGroup) {
        throw new IllegalStateException();
    }
    public void particle(XSParticle p) {
        throw new IllegalStateException();
    }
}
