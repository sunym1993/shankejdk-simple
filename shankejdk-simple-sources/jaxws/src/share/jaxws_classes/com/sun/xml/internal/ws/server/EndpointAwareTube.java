/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.server;

import com.sun.xml.internal.ws.api.pipe.Tube;
import com.sun.xml.internal.ws.api.server.WSEndpoint;

/**
 * Tubes that implement this interface will receive notification of the WSEndpoint
 * holding the tubeline after successful endpoint creation.
 *
 * @since 2.2.6
 */
public interface EndpointAwareTube extends Tube {
        /**
         * Setter for WSEndpoint holding this tube
         * @param endpoint WSEndpoint holding this tube
         */
        public void setEndpoint(WSEndpoint<?> endpoint);
}
