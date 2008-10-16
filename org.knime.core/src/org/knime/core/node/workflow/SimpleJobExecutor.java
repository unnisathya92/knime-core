/*
 * ------------------------------------------------------------------ *
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 *
 * Copyright, 2003 - 2008
 * University of Konstanz, Germany
 * Chair for Bioinformatics and Information Mining (Prof. M. Berthold)
 * and KNIME GmbH, Konstanz, Germany
 *
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner or
 * as specified in the license file distributed with this product.
 *
 * If you have any questions please contact the copyright holder:
 * website: www.knime.org
 * email: contact@knime.org
 * ---------------------------------------------------------------------
 *
 * History
 *   Apr 12, 2007 (mb): created
 */
package org.knime.core.node.workflow;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Extremely simple job executor. Just runs everything it get its hand on.
 *
 * @author M. Berthold & B. Wiswedel, University of Konstanz
 */
public class SimpleJobExecutor implements NodeExecutionJobManager {
    /**
     * {@inheritDoc}
     */
    public Future<?> submitJob(final NodeExecutionJob r) {
        FutureTask<?> ft = new FutureTask<Object>(r, null);
        ft.run();
        return ft;
    }

    /**
     * {@inheritDoc}
     */
    public String getID() {
        return "org.knime.core.node.workflow.SimpleJobExecutor";
    }

    /**
     * {@inheritDoc}
     */
    public NodeExecutionJobManagerPanel getSettingsPanelComponent() {
        // this job manager needs no settings
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Sequential Job Manager";
    }
}
