/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.source.tree;

/**
 * A tree node for an 'assert' statement.
 *
 * For example:
 * <pre>
 *   assert <em>condition</em> ;
 *
 *   assert <em>condition</em> : <em>detail</em> ;
 * </pre>
 *
 * @jls section 14.10
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface AssertTree extends StatementTree {
    ExpressionTree getCondition();
    ExpressionTree getDetail();
}
