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

package org.apache.shardingsphere.scaling.core.job.position.resume;

import lombok.SneakyThrows;
import org.apache.shardingsphere.governance.core.yaml.config.YamlGovernanceCenterConfiguration;
import org.apache.shardingsphere.governance.core.yaml.config.YamlGovernanceConfiguration;
import org.apache.shardingsphere.scaling.core.config.ScalingContext;
import org.apache.shardingsphere.scaling.core.config.ServerConfiguration;
import org.apache.shardingsphere.scaling.core.service.RegistryRepositoryHolder;
import org.apache.shardingsphere.scaling.core.utils.ReflectionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class RegistryRepositoryResumeBreakPointManagerTest {

    private RegistryRepositoryResumeBreakPointManager resumeBreakPointManager;

    @Before
    public void setUp() {
        ScalingContext.getInstance().init(mockServerConfiguration());
        resumeBreakPointManager = new RegistryRepositoryResumeBreakPointManager("H2", "/base");
    }

    @Test
    public void assertPersistAndGetPosition() {
        resumeBreakPointManager.persistPosition();
        assertThat(resumeBreakPointManager.getPosition("/base/incremental"), is("{}"));
        assertThat(resumeBreakPointManager.getPosition("/base/inventory"), is("{\"unfinished\":{},\"finished\":[]}"));
    }
    
    private ServerConfiguration mockServerConfiguration() {
        YamlGovernanceConfiguration distributedScalingService = new YamlGovernanceConfiguration();
        distributedScalingService.setName("test");
        YamlGovernanceCenterConfiguration registryCenter = new YamlGovernanceCenterConfiguration();
        registryCenter.setType("REG_FIXTURE");
        registryCenter.setServerLists("");
        distributedScalingService.setRegistryCenter(registryCenter);
        ServerConfiguration result = new ServerConfiguration();
        result.setDistributedScalingService(distributedScalingService);
        return result;
    }

    @After
    @SneakyThrows(ReflectiveOperationException.class)
    public void tearDown() {
        resumeBreakPointManager.close();
        ReflectionUtil.setStaticFieldValue(RegistryRepositoryHolder.class, "available", null);
    }
}
