/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.processor.model;

import com.sun.tools.internal.ws.wsdl.framework.Entity;
import com.sun.tools.internal.ws.wscompile.ErrorReceiver;

/**
 *
 * @author WS Development Team
 */
public class Request extends Message {

    public Request(com.sun.tools.internal.ws.wsdl.document.Message entity, ErrorReceiver receiver) {
        super(entity, receiver);
    }

    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
