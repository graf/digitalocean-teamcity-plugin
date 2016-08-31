<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="image" type="com.cloudcastlegroup.digitaloceanplugin.DigitalOceanCloudImage" scope="request"/>
<c:set var="img" value="${image.digitalOceanImage}"/>
Instances limit: <c:out value="${image.instancesLimit}"/><br/>
<br/>
Digital Ocean's snapshot info: <br/>
Id: <c:out value="${img.id}"/><br/>
Name: <c:out value="${img.name}"/><br/>
Distro: <c:out value="${img.distribution}"/><br/>