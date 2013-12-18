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

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * User: graf
 * Date: 12/12/13
 * Time: 14:06
 */
public class NamesFactory {

  @NotNull public static String getBuildAgentName(@NotNull String imageId, @NotNull String ipAddress) {
    return "DO-img:" + imageId + "-ip:" + ipAddress;
  }

  @NotNull public static String getInstanceId(@NotNull String ipAddress) {
    return "DO-" + ipAddress;
  }

  @NotNull public static String getStartingInstanceId(@NotNull Date startTime) {
    return "no-ip-addr-yet-" + Math.abs(startTime.hashCode());
  }

  @NotNull public static String getIpAddressFromInstanceId(@NotNull String instanceId) {
    if (!instanceId.startsWith("DO-")) {
      throw new IllegalArgumentException("Wrong DigitalOcean instance id");
    }

    return instanceId.substring(3, instanceId.length() - 1);
  }
}
