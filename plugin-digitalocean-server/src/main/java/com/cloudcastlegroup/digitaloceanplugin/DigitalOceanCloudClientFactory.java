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

import jetbrains.buildServer.clouds.CloudClientFactory;
import jetbrains.buildServer.clouds.CloudClientParameters;
import jetbrains.buildServer.clouds.CloudRegistrar;
import jetbrains.buildServer.clouds.CloudState;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.cloudcastlegroup.digitaloceanplugin.settings.ProfileConfigurationConstants.*;

/**
 * User: graf
 * Date: 10/12/13
 * Time: 17:00
 */
public class DigitalOceanCloudClientFactory implements CloudClientFactory {

  @NotNull private final String myJspPath;
  @NotNull private final PropertiesProcessor myPropertiesProcessor;

  
  public DigitalOceanCloudClientFactory(@NotNull final CloudRegistrar cloudRegistrar,
                                        @NotNull final PluginDescriptor pluginDescriptor) {
    myPropertiesProcessor = new PropertiesProcessor() {
      @NotNull
      public Collection<InvalidProperty> process(@NotNull final Map<String, String> properties) {
        return Collections.emptyList();
      }
    };
    myJspPath = pluginDescriptor.getPluginResourcesPath("profile-settings.jsp");
    cloudRegistrar.registerCloudFactory(this);
  }

  @NotNull
  public String getCloudCode() {
    return TYPE;
  }

  @NotNull
  public String getDisplayName() {
    return "DigitalOcean Cloud";
  }

  @Nullable
  public String getEditProfileUrl() {
    return myJspPath;
  }

  @NotNull
  public Map<String, String> getInitialParameterValues() {
    final HashMap<String, String> map = new HashMap<String, String>();
    map.put(INSTANCES_LIMIT_PROFILE_SETTING, "3");
    map.put(REGION_PROFILE_SETTING, "5");
    map.put(SIZE_PROFILE_SETTING, "66");
    return map;
  }

  @NotNull
  public PropertiesProcessor getPropertiesProcessor() {
    return myPropertiesProcessor;
  }

  /**
   * Detects whether or not the specified agent is a DigitalCould agent
   * @param agentDescription agent's settings
   * @return true if it is actually our agent
   */
  public boolean canBeAgentOfType(@NotNull final AgentDescription agentDescription) {
    final Map<String, String> configParams = agentDescription.getConfigurationParameters();
    return configParams.containsKey(BuildAgentConfigurationConstants.IMAGE_ID_PARAM_NAME)
            && configParams.containsKey(BuildAgentConfigurationConstants.INSTANCE_ID_PARAM_NAME);
  }

  @NotNull
  public DigitalOceanCloudClient createNewClient(@NotNull final CloudState state,
                                                 @NotNull final CloudClientParameters params) {
    return new DigitalOceanCloudClient(params);
  }
}

