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

/**
 * User: graf
 * Date: 05/12/13
 * Time: 14:49
 */
public class Droplet extends DigitalOceanObject {

  private String name;
  private int image_id;
  private int size_id;
  private int region_id;
  private boolean backups_active;
  private String ip_address;
  private String private_ip_address;
  private boolean locked;
  private String status;
  private int event_id;

  public Droplet(String name, int image_id, int size_id, int region_id, boolean backups_active, String ip_address, String private_ip_address, boolean locked, String status) {
    this.name = name;
    this.image_id = image_id;
    this.size_id = size_id;
    this.region_id = region_id;
    this.backups_active = backups_active;
    this.ip_address = ip_address;
    this.private_ip_address = private_ip_address;
    this.locked = locked;
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public int getImageId() {
    return image_id;
  }

  public int getSizeId() {
    return size_id;
  }

  public int getRegionId() {
    return region_id;
  }

  public boolean isBackupsActive() {
    return backups_active;
  }

  public String getIpAddress() {
    return ip_address;
  }

  public String getPrivateIpAddress() {
    return private_ip_address;
  }

  public boolean isLocked() {
    return locked;
  }

  public String getStatus() {
    return status;
  }

  public int getEventId() {
    return event_id;
  }

  @Override
  public String toString() {
    return "name: " + name + " ip: " + ip_address;
  }
}
