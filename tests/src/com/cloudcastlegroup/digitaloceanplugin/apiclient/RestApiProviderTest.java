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

import com.cloudcastlegroup.digitaloceanplugin.apiclient.v1.DigitalOceanApiProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: graf
 * Date: 05/12/13
 * Time: 13:47
 */
public class RestApiProviderTest {

  private static final String apiKey = "";

  private static final String clientId = "";

  @Test
  public void testImagesApi() {

    final DigitalOceanApi api = new DigitalOceanApiProvider(clientId, apiKey);

    Image[] myImages = api.getImages();

    Assert.assertNotNull(myImages);
    Assert.assertTrue(myImages.length >= 0);

    Image[] images = api.getImages(false);

    Assert.assertNotNull(images);
    Assert.assertTrue(images.length > 0);

    Assert.assertTrue(images.length > myImages.length);

    final Image image0 = images[0];
    int id = image0.getId();

    final Image image = api.getImage(id);
    Assert.assertEquals(image.getId(), id);
    Assert.assertEquals(image.getName(), image0.getName());
    Assert.assertEquals(image.getDistribution(), image0.getDistribution());
  }
}
