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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * User: graf
 * Date: 11/12/13
 * Time: 18:35
 */
public class IpResolver {

  public static final String CHECK_IP_URL = "http://checkip.amazonaws.com";

  public static String getIp() throws Exception {
    URL whatIsMyIp = new URL(CHECK_IP_URL);
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(whatIsMyIp.openStream()));
      return in.readLine();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
