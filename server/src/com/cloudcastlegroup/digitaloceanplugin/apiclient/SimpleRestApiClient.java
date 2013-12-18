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

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: graf
 * Date: 05/12/13
 * Time: 13:27
 */
public class SimpleRestApiClient {

  static <T> T get(@NotNull final Class<T> classOfT, @NotNull final String url, @NotNull final Object... params) {
    try {
      HttpURLConnection conn = (HttpURLConnection) new URL(String.format(url, params)).openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      conn.setUseCaches(false);

      final BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      final Gson gson = new Gson();

      final T result = gson.fromJson(br, classOfT);

      conn.disconnect();
      return result;

    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
