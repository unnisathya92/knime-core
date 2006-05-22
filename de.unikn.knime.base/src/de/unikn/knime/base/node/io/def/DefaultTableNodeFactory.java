/* 
 * --------------------------------------------------------------------- *
 *   This source code, its documentation and all appendant files         *
 *   are protected by copyright law. All rights reserved.                *
 *                                                                       *
 *   Copyright, 2003 - 2006                                              *
 *   Universitaet Konstanz, Germany.                                     *
 *   Lehrstuhl fuer Angewandte Informatik                                *
 *   Prof. Dr. Michael R. Berthold                                       *
 *                                                                       *
 *   You may not modify, publish, transmit, transfer or sell, reproduce, *
 *   create derivative works from, distribute, perform, display, or in   *
 *   any way exploit any of the content, in whole or in part, except as  *
 *   otherwise expressly permitted in writing by the copyright owner.    *
 * --------------------------------------------------------------------- *
 */
package de.unikn.knime.base.node.io.def;


import de.unikn.knime.core.data.DataRow;
import de.unikn.knime.core.data.DataTableSpec;
import de.unikn.knime.core.data.DataType;
import de.unikn.knime.core.node.NodeDialogPane;
import de.unikn.knime.core.node.NodeFactory;
import de.unikn.knime.core.node.NodeModel;
import de.unikn.knime.core.node.NodeView;

/**
 * Its the factory for a DefaultTableNodeModel. 
 * It will produce a NodeModel with a constant DataTable which was defined 
 * by the parameters passed to the factory's constructor.
 * No NodeDialogPane or NodeView is available.
 *   
 * @author ohl University of Konstanz
 */
public class DefaultTableNodeFactory extends NodeFactory {

    /* depending on which constructor was used, we need to store two 
       different sets of parameters. We call them set1 and set2.
    */
    private boolean m_set1;
    private DataRow[] m_rows;
    private DataTableSpec m_spec;

    private boolean m_set2;
    private Object[][] m_data;
    private String[] m_rowHeader;
    private String[] m_colHeader;

    /**
     * we provide the same constructors as the DefaultTable.
     * @see de.unikn.knime.core.data.def.DefaultTable
     * @param rows see DefaultTable constructor
     * @param columnNames see DefaultTable constructor
     * @param columnTypes see DefaultTable constructor
     */
    public DefaultTableNodeFactory(
        final DataRow[] rows,
        final String[] columnNames,
        final DataType[] columnTypes) {
        this(rows, new DataTableSpec(columnNames, columnTypes));
    }
    
    /**
     * Also this constructor is available in <code>DefaultTable</code>.
     * @param rows Passed to constructor of <code>DefaultTable</code>
     * @param spec Passed to constructor of <code>DefaultTable</code>
     * @see de.unikn.knime.core.data.def.DefaultTable#DefaultTable(
     *      DataRow[], DataTableSpec)
     */
    public DefaultTableNodeFactory(
            final DataRow[] rows, final DataTableSpec spec) {
        m_set1 = true;
        m_rows = rows;
        m_spec = spec;
    }
            

    /**
     * we provide the same constructors as the DefaultTable.
     * @see de.unikn.knime.core.data.def.DefaultTable
     * @param data see DefaultTable constructor
     * @param rowHeader see DefaultTable constructor
     * @param colHeader see DefaultTable constructor
     */
    public DefaultTableNodeFactory(
        final Object[][] data,
        final String[] rowHeader,
        final String[] colHeader) {

        m_set2 = true;

        m_data = data;
        m_rowHeader = rowHeader;
        m_colHeader = colHeader;
    }

    /**
     * @see de.unikn.knime.core.node.NodeFactory#createNodeModel()
     */
    public NodeModel createNodeModel() {
        if (m_set1) {
            return new DefaultTableNodeModel(m_rows, m_spec);
        }
        if (m_set2) {
            return new DefaultTableNodeModel(
                m_data,
                m_rowHeader,
                m_colHeader);

        }
        return null;
    }

    /**
     * @see de.unikn.knime.core.node.NodeFactory#getNrNodeViews()
     */
    public int getNrNodeViews() {
        return 0;
    }

    /**
     * @see de.unikn.knime.core.node.NodeFactory#createNodeView(int,NodeModel)
     */
    public NodeView createNodeView(final int i, final NodeModel nodeModel) {
        throw new InternalError();
    }
    
    /**
     * @return <b>false</b>.
     * @see de.unikn.knime.core.node.NodeFactory#hasDialog()
     */
    public boolean hasDialog() {
        return false;
    }
    
    /**
     * @see de.unikn.knime.core.node.NodeFactory#createNodeDialogPane()
     */
    public NodeDialogPane createNodeDialogPane() {
        throw new InternalError();
    }

}

