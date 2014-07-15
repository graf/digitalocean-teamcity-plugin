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

package com.cloudcastlegroup.digitaloceanplugin.apiclient.v1;

import com.cloudcastlegroup.digitaloceanplugin.apiclient.*;
import org.jetbrains.annotations.NotNull;

/**
 * User: graf
 * Date: 05/12/13
 * Time: 12:20
 */
@Deprecated
public class DigitalOceanApiProvider implements DigitalOceanApi {

  private static final String DIGITAL_OCEAN_API_URL = "https://api.digitalocean.com";

  private static final String DROPLETS_PATH = DIGITAL_OCEAN_API_URL + "/droplets/?client_id=%s&api_key=%s";

  private static final String DROPLET_PATH = DIGITAL_OCEAN_API_URL + "/droplets/%d/?client_id=%s&api_key=%s";

  private static final String DROPLET_SHUTDOWN_PATH = DIGITAL_OCEAN_API_URL + "/droplets/%d/shutdown/?client_id=%s&api_key=%s";

  private static final String DROPLET_POWER_ON_PATH = DIGITAL_OCEAN_API_URL + "/droplets/%d/power_on/?client_id=%s&api_key=%s";

  private static final String DROPLET_DESTROY_PATH = DIGITAL_OCEAN_API_URL + "/droplets/%d/destroy/?client_id=%s&api_key=%s";

  private static final String DROPLET_NEW_PATH = DIGITAL_OCEAN_API_URL + "/droplets/new?client_id=%s&api_key=%s&name=%s&size_id=%d&image_id=%d&region_id=%d&ssh_key_ids=%d";

  private static final String IMAGE_PATH = DIGITAL_OCEAN_API_URL + "/images/%d/?client_id=%s&api_key=%s";

  private static final String IMAGES_PATH = DIGITAL_OCEAN_API_URL + "/images/?client_id=%s&api_key=%s%s";

  private static final String SIZES_PATH = DIGITAL_OCEAN_API_URL + "/sizes/?client_id=%s&api_key=%s";

  private static final String REGIONS_PATH = DIGITAL_OCEAN_API_URL + "/regions/?client_id=%s&api_key=%s";

  private static final String SSH_KEYS_PATH = DIGITAL_OCEAN_API_URL + "/ssh_keys/?client_id=%s&api_key=%s";

  private static final String SSH_KEY_PATH = DIGITAL_OCEAN_API_URL + "/ssh_keys/%d/?client_id=%s&api_key=%s";

  private static final String EVENTS_PATH = DIGITAL_OCEAN_API_URL + "/events/%s/?client_id=%s&api_key=%s";

  @NotNull
  private String clientId;

  @NotNull
  private String apiKey;

  public DigitalOceanApiProvider(@NotNull final String clientId, @NotNull final String apiKey) {
    this.clientId = clientId;
    this.apiKey = apiKey;
  }

  private <T extends DigitalOceanApiResponse> T get(@NotNull final Class<T> classOfT, @NotNull final String url, @NotNull final Object... params) {
    final T result = SimpleRestApiClient.get(classOfT, url, params);

    if (!"OK".equals(result.getStatus())) {
      throw new DigitalOceanApiException(result.getMessage(), result);
    }

    return result;
  }

  @Override
  public Droplet[] getDroplets() {
    return get(DropletsList.class, DROPLETS_PATH, clientId, apiKey).getDroplets();
  }

  @Override
  public Droplet getDroplet(final int id) {
    return get(DropletInstance.class, DROPLET_PATH, id, clientId, apiKey).getDroplet();
  }

  @Override
  public Droplet createDroplet(@NotNull final String name, final int imageId, final int sizeId, final int regionId, final int sshKeyId) {
    final DropletInstance dropletInstance = get(DropletInstance.class, DROPLET_NEW_PATH, clientId, apiKey, name, sizeId, imageId, regionId, sshKeyId);
    return "OK".equals(dropletInstance.getStatus()) ? dropletInstance.getDroplet() : null;
  }

  @Override
  public Event powerOnDroplet(final int id) {
    return SimpleRestApiClient.get(Event.class, DROPLET_POWER_ON_PATH, id, clientId, apiKey);
  }

  @Override
  public Event destroyDroplet(final int id) {
    return SimpleRestApiClient.get(Event.class, DROPLET_DESTROY_PATH, id, clientId, apiKey);
  }

  @Override
  public Event shutdownDroplet(final int id) {
    return SimpleRestApiClient.get(Event.class, DROPLET_SHUTDOWN_PATH, id, clientId, apiKey);
  }

  @Override
  public Image[] getImages() {
    return getImages(true);
  }

  @Override
  public Image[] getImages(boolean onlyMyImages) {
    final String filter = onlyMyImages ? "&filter=my_images" : "";
    return get(ImagesList.class, IMAGES_PATH, clientId, apiKey, filter).getImages();
  }

  @Override
  public Image getImage(final int id) {
    return get(ImageInstance.class, IMAGE_PATH, id, clientId, apiKey).getImage();
  }

  @Override
  public Size[] getSizes() {
    return get(SizesList.class, SIZES_PATH, clientId, apiKey).getSizes();
  }

  @Override
  public Region[] getRegions() {
    return get(RegionsList.class, REGIONS_PATH, clientId, apiKey).getRegions();
  }

  @Override
  public SshKey[] getSshKeys() {
    return get(SshKeysList.class, SSH_KEYS_PATH, clientId, apiKey).getSshKeys();
  }

  @Override
  public SshKey getSshKey(int id) {
    return get(SshKeyInstance.class, SSH_KEY_PATH, id, clientId, apiKey).getSshKey();
  }

  @Override
  public Event getEvent(int id) {
    return SimpleRestApiClient.get(EventInstance.class, EVENTS_PATH, id, clientId, apiKey).getEvent();
  }
}
