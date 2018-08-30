/*
 * ------------------------------------------------------------------------
 *  Copyright by KNIME AG, Zurich, Switzerland
 *  Website: http://www.knime.com; Email: contact@knime.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME AG herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ------------------------------------------------------------------------
 *
 * History
 *   Oct 19, 2012 (wiswedel): created
 */
package org.knime.base.node.mine.treeensemble.learner;

import org.apache.commons.math.random.RandomData;
import org.knime.base.node.mine.treeensemble.data.TreeData;
import org.knime.base.node.mine.treeensemble.model.AbstractTreeModel;
import org.knime.base.node.mine.treeensemble.node.learner.TreeEnsembleLearnerConfiguration;
import org.knime.base.node.mine.treeensemble.sample.column.ColumnSampleStrategy;
import org.knime.base.node.mine.treeensemble.sample.row.RowSample;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionMonitor;

/**
 * 
 * @author Bernd Wiswedel, KNIME AG, Zurich, Switzerland
 */
public abstract class AbstractTreeLearner {

    private final TreeEnsembleLearnerConfiguration m_config;

    private final TreeData m_data;

    private final RowSample m_rowSampling;

    private final ColumnSampleStrategy m_colSamplingStrategy;

    /**
     *  */
    public AbstractTreeLearner(final TreeEnsembleLearnerConfiguration config, final TreeData data,
        final RandomData randomData) {
        m_config = config;
        m_data = data;
        m_rowSampling = m_config.createRowSample(m_data.getNrRows(), randomData);
        m_colSamplingStrategy = m_config.createColumnSampleStrategy(m_data, randomData);
    }

    /** @return the rowSampling */
    public final RowSample getRowSampling() {
        return m_rowSampling;
    }

    /** @return the colSamplingStrategy */
    public final ColumnSampleStrategy getColSamplingStrategy() {
        return m_colSamplingStrategy;
    }

    /** @return the data */
    final TreeData getData() {
        return m_data;
    }

    /** @return the config */
    final TreeEnsembleLearnerConfiguration getConfig() {
        return m_config;
    }

    public abstract AbstractTreeModel learnSingleTree(final ExecutionMonitor exec, final RandomData rd)
        throws CanceledExecutionException;

}
