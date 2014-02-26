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
import com.cloudcastlegroup.digitaloceanplugin.apiclient.Droplet;
import com.cloudcastlegroup.digitaloceanplugin.apiclient.Image;
import com.intellij.util.containers.ConcurrentHashSet;
import jetbrains.buildServer.clouds.CloudErrorInfo;
import jetbrains.buildServer.clouds.CloudImage;
import jetbrains.buildServer.clouds.CloudInstance;
import jetbrains.buildServer.clouds.CloudInstanceUserData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * User: graf
 * Date: 10/12/13
 * Time: 17:00
 */
public class DigitalOceanCloudImage implements CloudImage {
  @NotNull private final Map<String, DigitalOceanCloudInstance> myInstances =
          new ConcurrentHashMap<String, DigitalOceanCloudInstance>();
  @NotNull private final Collection<DigitalOceanCloudInstance> myStartingInstances =
          new ConcurrentHashSet<DigitalOceanCloudInstance>();

  private int myInstancesLimit;

  @Nullable private CloudErrorInfo myLastError;

  @NotNull private Image myDigitalOceanImage;

  @NotNull private final DigitalOceanApiProvider myApi;

  public DigitalOceanCloudImage(@NotNull final Image image,
                                final int instancesLimit,
                                DigitalOceanApiProvider api) {
    myDigitalOceanImage = image;
    myInstancesLimit = instancesLimit;
    myApi = api;
  }

  @NotNull
  public String getId() {
    return myDigitalOceanImage.getName();
  }

  @NotNull
  public String getName() {
    return myDigitalOceanImage.getName() + " (" + myDigitalOceanImage.getDistribution() + ")";
  }

  @NotNull
  public synchronized Collection<? extends CloudInstance> getInstances() {
    final Collection<DigitalOceanCloudInstance> runningInstances = myInstances.values();
    final ArrayList<DigitalOceanCloudInstance> result = new ArrayList<DigitalOceanCloudInstance>(myStartingInstances);
    result.addAll(runningInstances);

    return Collections.unmodifiableCollection(result);
  }

  @Nullable
  public DigitalOceanCloudInstance findInstanceById(@NotNull final String instanceId) {
    return myInstances.get(instanceId);
  }

  @Nullable
  public CloudErrorInfo getErrorInfo() {
    return myLastError;
  }

  @NotNull
  public DigitalOceanCloudInstance startNewInstance(@NotNull final DigitalOceanApiProvider api,
                                                    @NotNull CloudInstanceUserData data,
                                                    @NotNull final ExecutorService executor,
                                                    int sshKeyId, int regionId, int sizeId) {
    final DigitalOceanCloudInstance newInstance = new DigitalOceanCloudInstance(api, this, executor,
            sshKeyId, regionId, sizeId);
    myStartingInstances.add(newInstance);
    newInstance.start(data);

    final DigitalOceanCloudImage self = this;
    newInstance.addOnDropletReadyListener(new DropletLifecycleListener() {
      public void onDropletStarted(Droplet droplet) {
        synchronized (self) {
          myInstances.put(newInstance.getInstanceId(), newInstance);
          myStartingInstances.remove(newInstance);
        }
      }

      public void onDropletDestroyed(Droplet droplet) {
        synchronized (self) {
          myInstances.remove(newInstance.getInstanceId());
        }
      }

      public void onDropletError(Droplet droplet) {
        synchronized (self) {
          myStartingInstances.remove(newInstance);
        }
      }
    });

    return newInstance;
  }

  public synchronized boolean canStartNewInstance() {
    return (myStartingInstances.size() + myInstances.size()) < myInstancesLimit;
  }

  void dispose() {
    for (final DigitalOceanCloudInstance instance : myInstances.values()) {
      instance.terminate();
    }

    for (DigitalOceanCloudInstance instances : myStartingInstances) {
      instances.terminate();
    }

    myStartingInstances.clear();
    myInstances.clear();
  }

  @NotNull
  public Image getDigitalOceanImage() {
    final Image image = myApi.getImages().findByName(myDigitalOceanImage.getName());
    if (image != null) {
      myDigitalOceanImage = image;
      myLastError = null;
    } else {
      myLastError = new CloudErrorInfo("Cannot find image with name " + myDigitalOceanImage.getName());
    }

    return myDigitalOceanImage;
  }

  public int getInstancesLimit() {
    return myInstancesLimit;
  }
}
