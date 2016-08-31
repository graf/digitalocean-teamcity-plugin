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

import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.exception.DigitalOceanException;
import com.myjeeva.digitalocean.exception.RequestUnsuccessfulException;
import com.myjeeva.digitalocean.impl.DigitalOceanClient;
import com.myjeeva.digitalocean.pojo.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * User: graf
 * Date: 05/12/16
 * Time: 12:20
 */
public class DigitalOceanApiProvider {

  @NotNull
  private DigitalOcean apiClient;

  public DigitalOceanApiProvider(@NotNull final String apiKey) {
    this.apiClient = new DigitalOceanClient(apiKey);
  }

  public List<Droplet> getDroplets() {
    try {
      return this.apiClient.getAvailableDroplets(1, 200).getDroplets();
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Droplet getDroplet(final int id) {
    try {
      return apiClient.getDropletInfo(id);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Droplet createDroplet(@NotNull final String name, final int imageId, final String size, final String regionId, final int sshKeyId) {
    Droplet newDroplet = new Droplet();
    newDroplet.setName(name);
    newDroplet.setImage(new Image(imageId));
    newDroplet.setSize(size);
    newDroplet.setRegion(new Region(regionId));
    newDroplet.setKeys(Collections.singletonList(new Key(sshKeyId)));
    try {
      return apiClient.createDroplet(newDroplet);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Action powerOnDroplet(int id) {
    try {
      return apiClient.powerOnDroplet(id);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Delete destroyDroplet(int id) {
    try {
      return apiClient.deleteDroplet(id);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Action shutdownDroplet(int id) {
    try {
      return apiClient.shutdownDroplet(id);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Image getImage(int id) {
    try {
      return apiClient.getImageInfo(id);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Image getImage(String name) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }

    List<Image> images = getImages();
    for (Image image : images) {
      if (name.equals(image.getName())) {
        return image;
      }
    }

    return null;
  }

  public List<Image> getAllImages() {
    try {
      return apiClient.getAvailableImages(1, 200).getImages();
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public List<Image> getImages() {
    try {
      return apiClient.getUserImages(1, 200).getImages();
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public List<Size> getSizes() {
    return null;
  }

  public List<Region> getRegions() {
    return null;
  }

  public List<Key> getSshKeys() {
    try {
      return apiClient.getAvailableKeys(1).getKeys();
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Key getSshKey(int id) {
    try {
      return apiClient.getKeyInfo(id);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

  public Key getSshKey(String name) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }

    List<Key> keys = getSshKeys();
    for (Key key : keys) {
      if (name.equals(key.getName())) {
        return key;
      }
    }

    return null;
  }

  public Action getEvent(int id) {
    try {
      return apiClient.getActionInfo(id);
    } catch (DigitalOceanException e) {
      throw new DigitalOceanApiException(e);
    } catch (RequestUnsuccessfulException e) {
      throw new DigitalOceanApiException(e);
    }
  }

}
