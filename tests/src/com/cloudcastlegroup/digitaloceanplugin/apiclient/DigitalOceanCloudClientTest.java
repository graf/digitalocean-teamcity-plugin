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

package com.cloudcastlegroup.digitaloceanplugin.apiclient;

import com.cloudcastlegroup.digitaloceanplugin.DigitalOceanCloudClient;
import com.cloudcastlegroup.digitaloceanplugin.DigitalOceanCloudImage;
import com.cloudcastlegroup.digitaloceanplugin.DigitalOceanCloudInstance;
import com.cloudcastlegroup.digitaloceanplugin.settings.ProfileConfigurationConstants;
import jetbrains.buildServer.clouds.CloudClientParameters;
import jetbrains.buildServer.clouds.CloudImage;
import jetbrains.buildServer.clouds.CloudInstanceUserData;
import jetbrains.buildServer.clouds.InstanceStatus;
import jetbrains.buildServer.util.WaitFor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * User: graf
 * Date: 11/12/13
 * Time: 13:43
 */
public class DigitalOceanCloudClientTest {

  private static final String apiKey = "";

  private static final String clientId = "";

  private static final String imageName = "";

  private static final String instancesLimit = "";

  private static final String sshKeyName = "";

  private static final String regionId = "";

  private static final String sizeId = "";

  @Test
  public void testInstanceLifeCycle() throws InterruptedException {

    final DigitalOceanApiProvider api = new DigitalOceanApiProvider(clientId, apiKey);
    final DropletsList existedDroplets = api.getDroplets();

    final CloudClientParameters parameters = new CloudClientParameters();
    parameters.setParameter(ProfileConfigurationConstants.API_KEY_PROFILE_SETTING, apiKey);
    parameters.setParameter(ProfileConfigurationConstants.CLIENT_ID_PROFILE_SETTING, clientId);
    parameters.setParameter(ProfileConfigurationConstants.IMAGE_PROFILE_SETTING, imageName);
    parameters.setParameter(ProfileConfigurationConstants.INSTANCES_LIMIT_PROFILE_SETTING, instancesLimit);
    parameters.setParameter(ProfileConfigurationConstants.SSH_KEY_PROFILE_SETTING, sshKeyName);
    parameters.setParameter(ProfileConfigurationConstants.REGION_PROFILE_SETTING, regionId);
    parameters.setParameter(ProfileConfigurationConstants.SIZE_PROFILE_SETTING, sizeId);

    final DigitalOceanCloudClient cloudClient = new DigitalOceanCloudClient(parameters);
    final Collection<? extends CloudImage> cloudImages = cloudClient.getImages();

    Assert.assertEquals(cloudImages.size(), 1);

    final DigitalOceanCloudImage cloudImage = (DigitalOceanCloudImage) cloudImages.iterator().next();
    Assert.assertEquals(cloudImage.getInstances().size(), 0);
    Assert.assertTrue(cloudClient.canStartNewInstance(cloudImage));

    final DigitalOceanCloudInstance newInstance = (DigitalOceanCloudInstance) cloudClient.startNewInstance(cloudImage,
            new CloudInstanceUserData("", "", "", 30 * 1000l, "", new HashMap<String, String>()));
    Assert.assertTrue(newInstance.getStatus() == InstanceStatus.SCHEDULED_TO_START
            || newInstance.getStatus() == InstanceStatus.STARTING);
    Assert.assertEquals(cloudImage.getInstances().size(), 1);

    Thread.sleep(2000);
    Assert.assertEquals(api.getDroplets().getDroplets().length, existedDroplets.getDroplets().length + 1);
    Assert.assertNotNull(newInstance.getDigitalOceanDroplet());

    final int dropletId = newInstance.getDigitalOceanDroplet().getId();

    new WaitFor(15 * 60 * 1000) {
      @Override
      protected boolean condition() {
        return newInstance.getStatus() == InstanceStatus.RUNNING;
      }
    };

    Assert.assertEquals(cloudImage.getInstances().size(), 1);
    Assert.assertEquals(api.getDroplets().getDroplets().length, existedDroplets.getDroplets().length + 1);

    final Droplet droplet = api.getDroplet(dropletId).getDroplet();
    Assert.assertEquals(droplet.getStatus(), "active");
    Assert.assertNotNull(droplet.getIpAddress());

    cloudClient.terminateInstance(newInstance);

    Assert.assertEquals(cloudImage.getInstances().size(), 0);
    Assert.assertTrue(newInstance.getStatus() == InstanceStatus.STOPPED);

    new WaitFor(15 * 60 * 1000) {
      @Override
      protected boolean condition() {
        final String status = api.getDroplet(dropletId).getDroplet().getStatus();
        return "archive".equals(status) || "off".equals(status);
      }
    };

    Assert.assertEquals(api.getDroplets().getDroplets().length, existedDroplets.getDroplets().length);
  }

  @Test
  public void testTwoInstancesSimultaneously() throws InterruptedException {

    final DigitalOceanApiProvider api = new DigitalOceanApiProvider(clientId, apiKey);
    final DropletsList existedDroplets = api.getDroplets();

    final CloudClientParameters parameters = new CloudClientParameters();
    parameters.setParameter(ProfileConfigurationConstants.API_KEY_PROFILE_SETTING, apiKey);
    parameters.setParameter(ProfileConfigurationConstants.CLIENT_ID_PROFILE_SETTING, clientId);
    parameters.setParameter(ProfileConfigurationConstants.IMAGE_PROFILE_SETTING, imageName);
    parameters.setParameter(ProfileConfigurationConstants.INSTANCES_LIMIT_PROFILE_SETTING, instancesLimit);
    parameters.setParameter(ProfileConfigurationConstants.SSH_KEY_PROFILE_SETTING, sshKeyName);
    parameters.setParameter(ProfileConfigurationConstants.REGION_PROFILE_SETTING, regionId);
    parameters.setParameter(ProfileConfigurationConstants.SIZE_PROFILE_SETTING, sizeId);

    final DigitalOceanCloudClient cloudClient = new DigitalOceanCloudClient(parameters);
    final Collection<? extends CloudImage> cloudImages = cloudClient.getImages();

    Assert.assertEquals(cloudImages.size(), 1);

    final DigitalOceanCloudImage cloudImage = (DigitalOceanCloudImage) cloudImages.iterator().next();
    Assert.assertEquals(cloudImage.getInstances().size(), 0);
    Assert.assertTrue(cloudClient.canStartNewInstance(cloudImage));

    Executors.newFixedThreadPool(2).submit(new Runnable() {
      @Override
      public void run() {
        final DigitalOceanCloudInstance newInstance = (DigitalOceanCloudInstance) cloudClient.startNewInstance(cloudImage,
                new CloudInstanceUserData("", "", "", 30 * 1000l, "", new HashMap<String, String>()));
        Assert.assertTrue(newInstance.getStatus() == InstanceStatus.SCHEDULED_TO_START
                || newInstance.getStatus() == InstanceStatus.STARTING);

        Assert.assertNotNull(newInstance.getDigitalOceanDroplet());

        final int dropletId = newInstance.getDigitalOceanDroplet().getId();

        new WaitFor(15 * 60 * 1000) {
          @Override
          protected boolean condition() {
            return newInstance.getStatus() == InstanceStatus.RUNNING;
          }
        };

        final Droplet droplet = api.getDroplet(dropletId).getDroplet();
        Assert.assertEquals(droplet.getStatus(), "active");
        Assert.assertNotNull(droplet.getIpAddress());

        cloudClient.terminateInstance(newInstance);

        Assert.assertTrue(newInstance.getStatus() == InstanceStatus.STOPPED);

        new WaitFor(15 * 60 * 1000) {
          @Override
          protected boolean condition() {
            final String status = api.getDroplet(dropletId).getDroplet().getStatus();
            return "archive".equals(status) || "off".equals(status);
          }
        };
      }
    });

    new WaitFor() {
      @Override
      protected boolean condition() {
        return cloudImage.getInstances().size() == 2;
      }
    };

    Assert.assertEquals(api.getDroplets().getDroplets().length, existedDroplets.getDroplets().length);
  }

}
