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

import jetbrains.buildServer.clouds.web.CloudImageDetailsExtensionBase;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

/**
 * User: graf
 * Date: 10/12/13
 * Time: 17:00
 */
public class DigitalOceanCloudImageDetailsExtension extends CloudImageDetailsExtensionBase<DigitalOceanCloudImage> {
  public DigitalOceanCloudImageDetailsExtension(@NotNull final PagePlaces pagePlaces,
                                                @NotNull final PluginDescriptor pluginDescriptor) {
    super(DigitalOceanCloudImage.class, pagePlaces, pluginDescriptor, "image-details.jsp");
  }
}
