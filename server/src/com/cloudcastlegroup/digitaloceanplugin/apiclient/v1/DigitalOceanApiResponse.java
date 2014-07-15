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

/**
 * User: graf
 * Date: 10/12/13
 * Time: 18:50
 */
public class DigitalOceanApiResponse {

  private String status;

  private String error_message;

  private String message;

  public String getStatus() {
    return status;
  }

  public String getErrorMessage() {
    return error_message;
  }

  public String getMessage() {
    return message;
  }
}
