/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/*
 */

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 */

package sun.text.resources.sk;

import java.util.ListResourceBundle;

public class CollationData_sk extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                /* for sk, default sorting except for the following: */

                /* add d<stroke> between d and e. */
                /* add ch "ligature" between h and i */
                /* add l<stroke> between l and m. */
                /* add z<abovedot> after z.       */
                "& \u0361 ; \u0308 = \u030d "
                + "& A < a\u0308 , A\u0308 " // A < a-umlaut
                + "& C < c\u030c , C\u030c " // C < c-caron
                + "& D < \u0111, \u0110 "    // D < d-stroke
                + "& H < ch , cH , Ch , CH " // H < ch ligature
                + "& L < \u0142 , \u0141 "   // L < l-stroke
                + "& O < o\u0302 , O\u0302 " // oe < o-circumflex
                + "& R < r\u030c , R\u030c " // R < r-caron
                + "& S < s\u030c , S\u030c " // S < s-caron
                + "& Z < z\u030c , Z\u030c " // Z < z-caron
                + "< z\u0307 , Z\u0307 "     // z-dot-above
            }
        };
    }
}
