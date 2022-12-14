/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * @(#)MultipartDataSource.java       1.6 02/03/27
 */



package com.sun.xml.internal.messaging.saaj.packaging.mime;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;

import javax.activation.DataSource;

/**
 * MultipartDataSource is a <code>DataSource</code> that contains body
 * parts.  This allows "mail aware" <code>DataContentHandlers</code> to
 * be implemented more efficiently by being aware of such
 * <code>DataSources</code> and using the appropriate methods to access
 * <code>BodyParts</code>. <p>
 *
 * Note that the data of a MultipartDataSource is also available as
 * an input stream. <p>
 *
 * This interface will typically be implemented by providers that
 * preparse multipart bodies, for example an IMAP provider.
 *
 * @version     1.6, 02/03/27
 * @author      John Mani
 * @see         javax.activation.DataSource
 */

public interface MultipartDataSource extends DataSource {

    /**
     * Return the number of enclosed MimeBodyPart objects.
     *
     * @return          number of parts
     */
    public int getCount();

    /**
     * Get the specified MimeBodyPart.  Parts are numbered starting at 0.
     *
     * @param index     the index of the desired MimeBodyPart
     * @return          the MimeBodyPart
     * @exception       IndexOutOfBoundsException if the given index
     *                  is out of range.
     * @exception       MessagingException
     */
    public MimeBodyPart getBodyPart(int index) throws MessagingException;

}
