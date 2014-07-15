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

package com.cloudcastlegroup.digitaloceanplugin.settings;

import com.intellij.openapi.util.text.StringUtil;
import jetbrains.buildServer.clouds.CloudClientParameters;
import org.jetbrains.annotations.NotNull;

/**
 * User: graf
 * Date: 09/12/13
 * Time: 20:40
 */
public class Settings {

  private final String clientId;

  private final String apiKey;

  private final String imageName;

  private final String sshKeyName;

  private final int regionId;

  private final int sizeId;

  private final int instancesLimit;

  public Settings(String clientId, String apiKey, String imageName, String sshKeyName,
                  int regionId, int sizeId, int instancesLimit) {
    this.clientId = clientId;
    this.apiKey = apiKey;
    this.imageName = imageName;
    this.sshKeyName = sshKeyName;
    this.regionId = regionId;
    this.sizeId = sizeId;
    this.instancesLimit = instancesLimit;
  }

  @NotNull
  public static Settings parseParams(@NotNull CloudClientParameters params) {
    final String apiKey = getString(params, ProfileConfigurationConstants.API_KEY_PROFILE_SETTING);

    final String clientId = getString(params, ProfileConfigurationConstants.CLIENT_ID_PROFILE_SETTING);

    final String imageName = getString(params, ProfileConfigurationConstants.IMAGE_PROFILE_SETTING);

    final String sshKeyName = getString(params, ProfileConfigurationConstants.SSH_KEY_PROFILE_SETTING);

    final int sizeId = getInt(params, ProfileConfigurationConstants.SIZE_PROFILE_SETTING);

    final int regionId = getInt(params, ProfileConfigurationConstants.REGION_PROFILE_SETTING);

    final int instancesLimit = getInt(params, ProfileConfigurationConstants.INSTANCES_LIMIT_PROFILE_SETTING);

    return new Settings(clientId, apiKey, imageName, sshKeyName, regionId, sizeId, instancesLimit);
  }

  private static String getString(CloudClientParameters params, String paramName) {
    final String result = params.getParameter(paramName);
    if (result == null || result.trim().length() == 0) {
      throw new IllegalArgumentException(paramName + " is not specified");
    }
    return result;
  }

  private static int getInt(CloudClientParameters params, String paramName) {
    final String result = params.getParameter(paramName);
    if (result == null || result.trim().isEmpty()) {
      throw new IllegalArgumentException(StringUtil.capitalize(paramName) + " cannot be null or empty");
    }

    try {
      return Integer.valueOf(result);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Cannot parse " + StringUtil.capitalize(paramName));
    }
  }

  public String getClientId() {
    return clientId;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getImageName() {
    return imageName;
  }

  public String getSshKeyName() {
    return sshKeyName;
  }

  public int getRegionId() {
    return regionId;
  }

  public int getSizeId() {
    return sizeId;
  }

  public int getInstancesLimit() {
    return instancesLimit;
  }
}
