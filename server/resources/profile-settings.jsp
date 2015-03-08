<%--
  ~ Copyright 2009-2013 Cloud Castle Group
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<%@ page import="com.cloudcastlegroup.digitaloceanplugin.settings.ProfileConfigurationConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="apiKeyParamName" value="<%=ProfileConfigurationConstants.API_KEY_PROFILE_SETTING%>"/>
<c:set var="clientIdParamName" value="<%=ProfileConfigurationConstants.CLIENT_ID_PROFILE_SETTING%>"/>
<c:set var="imageNameParamName" value="<%=ProfileConfigurationConstants.IMAGE_PROFILE_SETTING%>"/>
<c:set var="sshKeyNameParamName" value="<%=ProfileConfigurationConstants.SSH_KEY_PROFILE_SETTING%>"/>
<c:set var="regionIdParamName" value="<%=ProfileConfigurationConstants.REGION_PROFILE_SETTING%>"/>
<c:set var="sizeIdParamName" value="<%=ProfileConfigurationConstants.SIZE_PROFILE_SETTING%>"/>
<c:set var="instancesLimitParamName" value="<%=ProfileConfigurationConstants.INSTANCES_LIMIT_PROFILE_SETTING%>"/>

<tr>
  <th><label for="${apiKeyParamName}">API Key*:</label></th>
  <td><props:textProperty name="${apiKeyParamName}" className="textProperty longField"/></td>
</tr>

<tr>
  <th><label for="${clientIdParamName}">Client ID*:</label></th>
  <td><props:textProperty name="${clientIdParamName}" className="textProperty longField" /></td>
</tr>

<tr>
  <th><label for="${imageNameParamName}">Image name*:</label></th>
  <td>
    <props:textProperty name="${imageNameParamName}" className="textProperty longField" />
    <span class="smallNote">Use your snapshot name from Digital Ocean</span>
  </td>
</tr>

<tr>
  <th><label for="${instancesLimitParamName}">Instances limit*:</label></th>
  <td>
    <props:textProperty name="${instancesLimitParamName}" className="textProperty longField" />
    <span class="smallNote">Maximum number of instances that can be started</span>
  </td>
</tr>

<tr>
  <th><label for="${sshKeyNameParamName}">Ssh key name*:</label></th>
  <td>
    <props:textProperty name="${sshKeyNameParamName}" className="textProperty longField" />
    <span class="smallNote">Use your ssh key name from Digital Ocean</span>
  </td>
</tr>

<tr>
  <th><label for="${regionIdParamName}">Region*:</label></th>
  <td>
    <props:selectProperty name="${regionIdParamName}" className="longField">
      <props:option value="5"><c:out value="Amsterdam 2"/></props:option>
      <props:option value="9"><c:out value="Amsterdam 3"/></props:option>
      <props:option value="7"><c:out value="London 1"/></props:option>
      <props:option value="4"><c:out value="New York 2"/></props:option>
      <props:option value="8"><c:out value="New York 3"/></props:option>
      <props:option value="3"><c:out value="San Francisco 1"/></props:option>
      <props:option value="6"><c:out value="Singapore 1"/></props:option>      
    </props:selectProperty>
</tr>

<tr>
  <th><label for="${sizeIdParamName}">Size*:</label></th>
  <td>
    <props:selectProperty name="${sizeIdParamName}" className="longField">
      <props:option value="66"><c:out value="512 Mb - 1CPU - 20Gb - $5/mo"/></props:option>
      <props:option value="63"><c:out value="1 GB - 1CPU - 30Gb - $10/mo"/></props:option>
      <props:option value="62"><c:out value="2 GB - 2CPU - 40Gb - $20/mo"/></props:option>
      <props:option value="64"><c:out value="4 GB - 2CPU - 60Gb - $40/mo"/></props:option>
      <props:option value="65"><c:out value="8 Gb - 4CPU - 80Gb - $80/mo"/></props:option>
      <props:option value="61"><c:out value="16 Gb - 8CPU - 160Gb - $160/mo"/></props:option>
      <props:option value="60"><c:out value="32 Gb - 12CPU - 320Gb - $320/mo"/></props:option>
      <props:option value="70"><c:out value="48 Gb - 16CPU - 480Gb - $480/mo"/></props:option>
      <props:option value="69"><c:out value="64 Gb - 20CPU - 640Gb - $640/mo"/></props:option>
      <props:option value="68"><c:out value="96 Gb - 24CPU - 960Gb - $960/mo"/></props:option>
    </props:selectProperty>
</tr>

