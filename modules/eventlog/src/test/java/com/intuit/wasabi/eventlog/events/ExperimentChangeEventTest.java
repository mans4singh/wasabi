/*******************************************************************************
 * Copyright 2016 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.wasabi.eventlog.events;

import com.intuit.wasabi.authenticationobjects.UserInfo;
import com.intuit.wasabi.eventlog.EventLog;
import com.intuit.wasabi.experimentobjects.Application;
import com.intuit.wasabi.experimentobjects.Experiment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Tests for {@link BucketChangeEvent}.
 */
public class ExperimentChangeEventTest {
    @Mock
    private Experiment experiment;

    @Mock
    private Experiment.Label experimentLabel;

    @Mock
    private UserInfo userInfo;

    @Mock
    private Application.Name appName;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(experiment.getApplicationName()).thenReturn(appName);
    }

    @Test
    public void testConstructor() throws Exception {
        try {
            new ExperimentChangeEvent(null, "state", "PAUSED", "RUNNING");
            Assert.fail("BucketChangeEvent must not be creatable with a null experiment.");
        } catch (IllegalArgumentException ignored) {
        }

        ExperimentChangeEvent systemEvent = new ExperimentChangeEvent(experiment, "samplingPercent", "0.2", "0.3");
        Assert.assertEquals(experiment, systemEvent.getExperiment());
        Assert.assertEquals(appName, systemEvent.getApplicationName());
        AbstractChangeEventTest.testValidSystemEvent(systemEvent, "samplingPercent", "0.2", "0.3");

        ExperimentChangeEvent userEvent = new ExperimentChangeEvent(userInfo, experiment, "state", "RUNNING", "PAUSED");
        Assert.assertEquals(experiment, userEvent.getExperiment());
        Assert.assertEquals(userInfo, userEvent.getUser());
        Assert.assertEquals(appName, systemEvent.getApplicationName());
    }

    @Test
    public void testGetDefaultDescription() throws Exception {
        Mockito.when(experiment.getLabel()).thenReturn(experimentLabel);
        Mockito.when(experimentLabel.toString()).thenReturn("UnitTestExperiment");
        ExperimentChangeEvent systemEvent = new ExperimentChangeEvent(experiment, "state", "RUNNING", "PAUSED");
        Assert.assertEquals(EventLog.SYSTEM_USER.getUsername() + " changed property state of experiment UnitTestExperiment from RUNNING to PAUSED.", systemEvent.getDefaultDescription());
    }
}
