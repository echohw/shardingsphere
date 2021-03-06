/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.agent.core.plugin.service;

import org.apache.shardingsphere.agent.core.config.PluginConfiguration;
import org.apache.shardingsphere.agent.core.spi.AgentTypedSPI;

/**
 * Boot service that the lifecycle is from the agent startup to shutdown.
 *
 * @param <T> plugin configuration
 */
public interface BootService<T extends PluginConfiguration> extends AgentTypedSPI {
    
    /**
     * Setup the boot service, like to configure or to initial.
     *
     * @param configuration configuration
     */
    void setup(T configuration);
    
    /**
     * Start up the boot service.
     *
     * @param configuration configuration
     */
    default void start(T configuration) {
    }
    
    /**
     * Cleanup the boot service.
     */
    default void cleanup() {
    }
}
