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
package com.intuit.wasabi.repository.impl.database;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.flyway.core.Flyway;
import com.intuit.wasabi.database.TransactionFactory;
import com.intuit.wasabi.experimentobjects.ExperimentValidator;
import com.intuit.wasabi.repository.DatabaseRepository;
import com.intuit.wasabi.repository.ExperimentRepository;
import com.intuit.wasabi.repository.impl.cassandra.AbstractCassandraModule;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Guice module for database experiment module
 */
public class DatabaseExperimentRepositoryModule extends AbstractCassandraModule {

    private static final Logger LOGGER = getLogger(DatabaseExperimentRepositoryModule.class);
    @Override
    protected void configure() {
        LOGGER.debug("installing module: {}", DatabaseExperimentRepositoryModule.class.getSimpleName());

        LOGGER.debug("installed module: {}", DatabaseExperimentRepositoryModule.class.getSimpleName());
    }

    @Inject
    @Provides
    @Singleton
    @DatabaseRepository
    protected ExperimentRepository provideExperimentRepository(TransactionFactory transactionFactory,
                                                               ExperimentValidator validator,
                                                               Flyway flyway) {
        return new DatabaseExperimentRepository(transactionFactory, validator, flyway);
    }
}
