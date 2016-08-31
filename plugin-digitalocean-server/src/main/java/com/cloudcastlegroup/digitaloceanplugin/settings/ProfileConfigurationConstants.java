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

package com.cloudcastlegroup.digitaloceanplugin.settings;

import org.jetbrains.annotations.NotNull;

/**
 * User: graf
 * Date: 10/12/13
 * Time: 17:00
 */
public interface ProfileConfigurationConstants {

  @NotNull String TYPE = "digoc";

  @NotNull String IMAGE_PROFILE_SETTING = "image_name";

  @NotNull String SSH_KEY_PROFILE_SETTING = "ssh_key_name";

  @NotNull String REGION_PROFILE_SETTING = "region_id";

  @NotNull String SIZE_PROFILE_SETTING = "size_id";

  @NotNull String INSTANCES_LIMIT_PROFILE_SETTING = "instances_limit";

  @NotNull String API_KEY_PROFILE_SETTING = "api_key";
}
