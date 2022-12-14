/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.reader.relaxng;

import java.util.HashSet;
import java.util.Set;

import com.sun.tools.internal.xjc.model.CClassInfo;
import com.sun.tools.internal.xjc.model.CElementPropertyInfo;
import com.sun.tools.internal.xjc.model.CReferencePropertyInfo;
import com.sun.tools.internal.xjc.model.CTypeInfo;
import com.sun.tools.internal.xjc.model.CTypeRef;
import com.sun.tools.internal.xjc.model.Multiplicity;
import com.sun.tools.internal.xjc.reader.RawTypeSet;
import com.sun.xml.internal.bind.v2.model.core.ID;

import com.sun.xml.internal.rngom.digested.DAttributePattern;
import com.sun.xml.internal.rngom.digested.DElementPattern;
import com.sun.xml.internal.rngom.digested.DOneOrMorePattern;
import com.sun.xml.internal.rngom.digested.DPattern;
import com.sun.xml.internal.rngom.digested.DPatternWalker;
import com.sun.xml.internal.rngom.digested.DZeroOrMorePattern;

/**
 * Builds {@link RawTypeSet} for RELAX NG.
 *
 * @author Kohsuke Kawaguchi
 */
public final class RawTypeSetBuilder extends DPatternWalker {
    public static RawTypeSet build( RELAXNGCompiler compiler, DPattern contentModel, Multiplicity mul ) {
        RawTypeSetBuilder builder = new RawTypeSetBuilder(compiler,mul);
        contentModel.accept(builder);
        return builder.create();
    }

    /**
     * Multiplicity of the property.
     */
    private Multiplicity mul;

    /**
     * Accumulates discovered {@link RawTypeSet.Ref}s.
     */
    private final Set<RawTypeSet.Ref> refs = new HashSet<RawTypeSet.Ref>();

    private final RELAXNGCompiler compiler;

    public RawTypeSetBuilder(RELAXNGCompiler compiler,Multiplicity mul) {
        this.mul = mul;
        this.compiler = compiler;
    }

    private RawTypeSet create() {
        return new RawTypeSet(refs,mul);
    }

    public Void onAttribute(DAttributePattern p) {
        // attributes form their own properties
        return null;
    }

    public Void onElement(DElementPattern p) {
        CTypeInfo[] tis = compiler.classes.get(p);
        if(tis!=null) {
            for( CTypeInfo ti : tis )
                refs.add(new CClassInfoRef((CClassInfo)ti));
        } else {
            // TODO
            assert false;
        }
        return null;
    }

    public Void onZeroOrMore(DZeroOrMorePattern p) {
        mul = mul.makeRepeated();
        return super.onZeroOrMore(p);
    }

    public Void onOneOrMore(DOneOrMorePattern p) {
        mul = mul.makeRepeated();
        return super.onOneOrMore(p);
    }

    /**
     * For {@link CClassInfo}s that map to elements.
     */
    private static final class CClassInfoRef extends RawTypeSet.Ref {
        private final CClassInfo ci;
        CClassInfoRef(CClassInfo ci) {
            this.ci = ci;
            assert ci.isElement();
        }

        protected ID id() {
            return ID.NONE;
        }

        protected boolean isListOfValues() {
            return false;
        }

        protected RawTypeSet.Mode canBeType(RawTypeSet parent) {
            return RawTypeSet.Mode.SHOULD_BE_TYPEREF;
        }

        protected void toElementRef(CReferencePropertyInfo prop) {
            prop.getElements().add(ci);
        }

        protected CTypeRef toTypeRef(CElementPropertyInfo ep) {
            return new CTypeRef(ci,ci.getElementName(),ci.getTypeName(),false,null);
        }
    }
}
