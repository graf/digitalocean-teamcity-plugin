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

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.agent.BuildAgentConfigurationEx;
import org.jetbrains.annotations.NotNull;

/**
 * User: graf
 * Date: 11/12/13
 * Time: 18:35
 */
public class DigitalOceanPropertiesPatcher {

  private static final Logger LOG = Logger.getInstance(DigitalOceanPropertiesPatcher.class.getName());

  @NotNull private final BuildAgentConfigurationEx myConfig;

  public DigitalOceanPropertiesPatcher(@NotNull BuildAgentConfigurationEx configuration) {
    this.myConfig = configuration;
  }

  public String getIpAddress() {
    try {
      return IpResolver.getIp();
    } catch (Exception e) {
      LOG.error("Cannot determine IP address.", e);
      return "0.0.0.0";
    }
  }

  public void patchConfiguration() {
    final String imageId = myConfig.getConfigurationParameters().get(BuildAgentConfigurationConstants.IMAGE_ID_PARAM_NAME);
    final String ipAddress = getIpAddress();

    myConfig.setName(NamesFactory.getBuildAgentName(imageId, ipAddress));
    myConfig.addConfigurationParameter(BuildAgentConfigurationConstants.INSTANCE_ID_PARAM_NAME,
            NamesFactory.getInstanceId(ipAddress));
  }
}
