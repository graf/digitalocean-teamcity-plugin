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

import org.jetbrains.annotations.NotNull;

/**
 * User: graf
 * Date: 05/12/13
 * Time: 12:56
 */
public class ImagesList extends DigitalOceanApiResponse {

  private Image[] images;

  public Image[] getImages() {
    return images;
  }

  public Image findByName(@NotNull String name) {
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }

    if (images == null) {
      return null;
    }

    for (Image image : images) {
      if (name.equals(image.getName())) {
        return image;
      }
    }

    return null;
  }
}
