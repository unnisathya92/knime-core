/* -------------------------------------------------------------------
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 * 
 * Copyright, 2003 - 2006
 * Universitaet Konstanz, Germany.
 * Lehrstuhl fuer Angewandte Informatik
 * Prof. Dr. Michael R. Berthold
 * 
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner.
 * -------------------------------------------------------------------
 * 
 * History
 *   07.07.2005 (mb): created
 */
package de.unikn.knime.core.data;

/**
 * Interface supporting fuzzy numbers defined by min and max support, and core. 
 * 
 * @author Michael Berthold, University of Konstanz
 */
public interface FuzzyNumberValue extends DataValue {
    
    /**
     * @return Minimum support value.
     */
    public double getMinSupport();
    
    /**
     * @return Core value.
     */
    public double getCore();
    
    /**
     * @return Maximum support value.
     */
    public double getMaxSupport();
}
