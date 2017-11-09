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

import com.cloudcastlegroup.digitaloceanplugin.apiclient.DigitalOceanApiProvider;
import com.cloudcastlegroup.digitaloceanplugin.settings.PluginConfiguration;
import com.myjeeva.digitalocean.pojo.Droplet;
import com.myjeeva.digitalocean.pojo.Image;
import com.myjeeva.digitalocean.pojo.Key;
import jetbrains.buildServer.clouds.*;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import jetbrains.buildServer.util.NamedDaemonThreadFactory;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

import static com.cloudcastlegroup.digitaloceanplugin.BuildAgentConfigurationConstants.IMAGE_ID_PARAM_NAME;
import static com.cloudcastlegroup.digitaloceanplugin.BuildAgentConfigurationConstants.INSTANCE_ID_PARAM_NAME;

/**
 * User: graf
 * Date: 10/12/13
 * Time: 17:00
 */
public class DigitalOceanCloudClient extends BuildServerAdapter implements CloudClientEx {

  private static final Logger LOG = Logger.getLogger(DigitalOceanCloudClient.class);

  @NotNull
  private final DigitalOceanApiProvider myApi;

  private int myDigitalOceanSshKeyId;
  private String myDigitalOceanSizeId;
  private String myDigitalOceanRegionId;

  @Nullable
  private DigitalOceanCloudImage myImage;
  @Nullable
  private CloudErrorInfo myErrorInfo;

  @NotNull
  private final ExecutorService myExecutor =
          Executors.newCachedThreadPool(new NamedDaemonThreadFactory("digitalocean-cloud-image"));

  public DigitalOceanCloudClient(@NotNull final CloudClientParameters params) {

    final PluginConfiguration settings = PluginConfiguration.parseParams(params);

    myApi = new DigitalOceanApiProvider(settings.getApiKey());

    try {
      final Image image = myApi.getImage(settings.getImageName());
      if (image != null) {
        myImage = new DigitalOceanCloudImage(image, settings.getInstancesLimit(), myApi);
      } else {
        myErrorInfo = new CloudErrorInfo("Cannot find image with name " + settings.getImageName());
      }

      List<Droplet> droplets = myApi.getDroplets();
      for (Droplet droplet : droplets) {
        LOG.info("Found existing droplet " + droplet.getName() + " with image ID " +
          droplet.getImage().getId() + " (looking for " + image.getId() + ")");
        if (droplet.getImage().getId().equals(image.getId())) {
          LOG.info("Creating cloud instance for droplet " + droplet.getName());
          myImage.addExistingDroplet(droplet, myExecutor);
        }
      }

      final Key sshKey = myApi.getSshKey(settings.getSshKeyName());
      if (sshKey != null) {
        myDigitalOceanSshKeyId = sshKey.getId();
      } else {
        myErrorInfo = new CloudErrorInfo("Cannot find ssh key with name " + settings.getSshKeyName());
      }
    } catch (Exception ex) {
      myErrorInfo = new CloudErrorInfo("Cannot connect to DigitalOcean",
              "Check your internet connection and api key", ex);
    }

    myDigitalOceanSizeId = settings.getSizeId();
    myDigitalOceanRegionId = settings.getRegionId();
  }

  public boolean isInitialized() {
    return myImage != null || myErrorInfo != null;
  }

  @Nullable
  public DigitalOceanCloudImage findImageById(@NotNull final String imageId) throws CloudException {
    return myImage == null || !(myImage.getId().equals(imageId)) ? null : myImage;
  }

  @Nullable
  public DigitalOceanCloudInstance findInstanceByAgent(@NotNull final AgentDescription agentDescription) {
    final DigitalOceanCloudImage image = findImage(agentDescription);
    if (image == null) {
      return null;
    }

    final String instanceId = findInstanceId(agentDescription);
    if (instanceId == null) {
      return null;
    }

    return image.findInstanceById(instanceId);
  }

  @NotNull
  public Collection<? extends CloudImage> getImages() throws CloudException {
    if (myImage == null) {
      return Collections.emptyList();
    } else {
      return Collections.singleton(myImage);
    }
  }

  @Nullable
  public CloudErrorInfo getErrorInfo() {
    return myErrorInfo;
  }

  public boolean canStartNewInstance(@NotNull final CloudImage image) {
    return ((DigitalOceanCloudImage) image).canStartNewInstance();
  }

  public String generateAgentName(@NotNull final AgentDescription agentDescription) {
    final DigitalOceanCloudImage image = findImage(agentDescription);
    if (image == null) {
      return null;
    }

    final String instanceId = findInstanceId(agentDescription);
    if (instanceId == null) {
      return null;
    }

    final String ipAddress = NamesFactory.getIpAddressFromInstanceId(instanceId);
    return NamesFactory.getBuildAgentName(image.getId(), ipAddress);
  }

  @NotNull
  public CloudInstance startNewInstance(@NotNull final CloudImage image,
                                        @NotNull final CloudInstanceUserData data) throws QuotaException {
    final DigitalOceanCloudImage cloudImage = (DigitalOceanCloudImage) image;
    return cloudImage.startNewInstance(myApi, data, myExecutor, myDigitalOceanSshKeyId,
            myDigitalOceanRegionId, myDigitalOceanSizeId);
  }

  public void restartInstance(@NotNull final CloudInstance instance) {
    ((DigitalOceanCloudInstance) instance).restart();
  }

  public void terminateInstance(@NotNull final CloudInstance instance) {
    ((DigitalOceanCloudInstance) instance).terminate();
  }

  public void dispose() {
    if (myImage != null) {
      myImage.dispose();
    }

    myExecutor.shutdownNow();
  }

  @Nullable
  private DigitalOceanCloudImage findImage(@NotNull final AgentDescription agentDescription) {
    final String imageId = agentDescription.getConfigurationParameters().get(IMAGE_ID_PARAM_NAME);
    return imageId == null ? null : findImageById(imageId);
  }

  @Nullable
  private String findInstanceId(@NotNull final AgentDescription agentDescription) {
    return agentDescription.getConfigurationParameters().get(INSTANCE_ID_PARAM_NAME);
  }
}
