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

    final DigitalOceanApiProvider api = new DigitalOceanApiProvider(clientId, apiKey);

    ImagesList myImagesList = api.getImages();

    Assert.assertNotNull(myImagesList);
    Assert.assertEquals(myImagesList.getStatus(), "OK");
    Assert.assertNotNull(myImagesList.getImages());
    Assert.assertTrue(myImagesList.getImages().length >= 0);

    ImagesList imagesList = api.getImages(false);

    Assert.assertNotNull(imagesList);
    Assert.assertEquals(imagesList.getStatus(), "OK");
    Assert.assertNotNull(imagesList.getImages());
    Assert.assertTrue(imagesList.getImages().length > 0);

    Assert.assertTrue(imagesList.getImages().length > myImagesList.getImages().length);

    final Image image0 = imagesList.getImages()[0];
    int id = image0.getId();

    final ImageInstance imageInstance = api.getImage(id);
    Assert.assertNotNull(imageInstance);
    Assert.assertEquals(imageInstance.getStatus(), "OK");

    final Image image = imageInstance.getImage();
    Assert.assertEquals(image.getId(), id);
    Assert.assertEquals(image.getName(), image0.getName());
    Assert.assertEquals(image.getDistribution(), image0.getDistribution());
  }
}
