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

import com.cloudcastlegroup.digitaloceanplugin.apiclient.DigitalOceanApiProvider;
import com.myjeeva.digitalocean.pojo.*;
import org.junit.Ignore;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * User: graf
 * Date: 05/12/13
 * Time: 13:47
 */
@Ignore
public class RestApiProviderTest {

  private static final String apiKey = "3c808863fb03c4bef1645d0a0ed515f84b44cbb37b38bde936ae8af2879eb9a5";

  @Test(enabled = false)
  public void testImagesApi() {

    final DigitalOceanApiProvider api = new DigitalOceanApiProvider(apiKey);

    List<Image> myImages = api.getImages();

    Assert.assertNotNull(myImages);
    Assert.assertTrue(myImages.size() >= 0);

    List<Image> images = api.getAllImages();

    Assert.assertNotNull(images);
    Assert.assertTrue(images.size() > 0);

    Assert.assertTrue(images.size() > myImages.size());

    final Image image0 = images.get(0);
    Integer id = image0.getId();

    final Image image = api.getImage(id);
    Assert.assertEquals(image.getId(), id);
    Assert.assertEquals(image.getName(), image0.getName());
    Assert.assertEquals(image.getDistribution(), image0.getDistribution());
  }
}
