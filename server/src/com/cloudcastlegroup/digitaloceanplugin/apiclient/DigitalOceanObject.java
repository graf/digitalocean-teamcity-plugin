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
 * Date: 09/12/13
 * Time: 16:17
 */
public class DigitalOceanObject implements Comparable {

  private int id;

  public int getId() {
    return id;
  }

  public int compareTo(Object o) {
    if (!(o instanceof DigitalOceanObject)) {
      throw new IllegalArgumentException("Can compare only DigitalOcean objects");
    }

    return id - ((DigitalOceanObject) o).id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DigitalOceanObject that = (DigitalOceanObject) o;

    return id == that.id;

  }

  @Override
  public int hashCode() {
    return id;
  }
}
