/*
 * Copyright 2009-2013 Cloud Castle Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudcastlegroup.digitaloceanplugin;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;

/**
 * User: graf
 * Date: 11/12/13
 * Time: 18:35
 */
public class DigitalOceanProperties {
  public DigitalOceanProperties(@NotNull EventDispatcher<AgentLifeCycleListener> events,
                                @NotNull final DigitalOceanPropertiesPatcher propertiesPatcher) {
    events.addListener(new AgentLifeCycleAdapter() {
      public void afterAgentConfigurationLoaded(@NotNull BuildAgent agent) {
        propertiesPatcher.patchConfiguration();
      }
    });
  }
}
