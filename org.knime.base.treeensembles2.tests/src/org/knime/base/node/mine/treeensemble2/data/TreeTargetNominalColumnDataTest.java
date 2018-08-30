/*
 * ------------------------------------------------------------------------
 *
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
 * ---------------------------------------------------------------------
 *
 * History
 *   10.02.2016 (Adrian Nembach): created
 */
package org.knime.base.node.mine.treeensemble2.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.knime.base.node.mine.treeensemble2.data.memberships.DataMemberships;
import org.knime.base.node.mine.treeensemble2.data.memberships.DefaultDataIndexManager;
import org.knime.base.node.mine.treeensemble2.data.memberships.RootDataMemberships;
import org.knime.base.node.mine.treeensemble2.model.AbstractTreeEnsembleModel.TreeType;
import org.knime.base.node.mine.treeensemble2.node.learner.TreeEnsembleLearnerConfiguration;
import org.knime.base.node.mine.treeensemble2.node.learner.TreeEnsembleLearnerConfiguration.SplitCriterion;
import org.knime.core.node.InvalidSettingsException;

/**
 *
 * @author Adrian Nembach, KNIME.com
 */
public class TreeTargetNominalColumnDataTest {

    private final static double DELTA = 1e-6;

    /**
     * Tests the {@link TreeTargetNominalColumnData#getDistribution(DataMemberships, TreeEnsembleLearnerConfiguration)}
     * and {@link TreeTargetNominalColumnData#getDistribution(double[], TreeEnsembleLearnerConfiguration)} methods.
     * @throws InvalidSettingsException
     */
    @Test
    public void testGetDistribution() throws InvalidSettingsException {
        String targetCSV = "A,A,A,B,B,B,A";
        String attributeCSV = "1,2,3,4,5,6,7";
        TreeEnsembleLearnerConfiguration config = new TreeEnsembleLearnerConfiguration(false);
        TestDataGenerator dataGen = new TestDataGenerator(config);
        TreeTargetNominalColumnData target = TestDataGenerator.createNominalTargetColumn(targetCSV);
        TreeNumericColumnData attribute = dataGen.createNumericAttributeColumn(attributeCSV, "test-col", 0);
        TreeData data = new TreeData(new TreeAttributeColumnData[]{attribute}, target, TreeType.Ordinary);
        double[] weights = new double[7];
        Arrays.fill(weights, 1.0);
        DataMemberships rootMemberships = new RootDataMemberships(weights, data, new DefaultDataIndexManager(data));
        // Gini
        config.setSplitCriterion(SplitCriterion.Gini);
        double expectedGini = 0.4897959184;
        double[] expectedDistribution = new double[]{4.0, 3.0};
        ClassificationPriors giniPriorsDatMem = target.getDistribution(rootMemberships, config);
        assertEquals(expectedGini, giniPriorsDatMem.getPriorImpurity(), DELTA);
        assertArrayEquals(expectedDistribution, giniPriorsDatMem.getDistribution(), DELTA);
        ClassificationPriors giniPriorsWeights = target.getDistribution(weights, config);
        assertEquals(expectedGini, giniPriorsWeights.getPriorImpurity(), DELTA);
        assertArrayEquals(expectedDistribution, giniPriorsWeights.getDistribution(), DELTA);
        // Information Gain
        config.setSplitCriterion(SplitCriterion.InformationGain);
        double expectedEntropy = 0.985228136;
        ClassificationPriors igPriorsDatMem = target.getDistribution(rootMemberships, config);
        assertEquals(expectedEntropy, igPriorsDatMem.getPriorImpurity(), DELTA);
        assertArrayEquals(expectedDistribution, igPriorsDatMem.getDistribution(), DELTA);
        ClassificationPriors igPriorsWeights = target.getDistribution(weights, config);
        assertEquals(expectedEntropy, igPriorsWeights.getPriorImpurity(), DELTA);
        assertArrayEquals(expectedDistribution, igPriorsWeights.getDistribution(), DELTA);
        // Information Gain Ratio
        config.setSplitCriterion(SplitCriterion.InformationGainRatio);
        // prior impurity is the same as IG
        ClassificationPriors igrPriorsDatMem = target.getDistribution(rootMemberships, config);
        assertEquals(expectedEntropy, igrPriorsDatMem.getPriorImpurity(), DELTA);
        assertArrayEquals(expectedDistribution, igrPriorsDatMem.getDistribution(), DELTA);
        ClassificationPriors igrPriorsWeights = target.getDistribution(weights, config);
        assertEquals(expectedEntropy, igrPriorsWeights.getPriorImpurity(), DELTA);
        assertArrayEquals(expectedDistribution, igrPriorsWeights.getDistribution(), DELTA);
    }
}
