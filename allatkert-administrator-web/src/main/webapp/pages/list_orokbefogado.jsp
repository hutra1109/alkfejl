<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<html>
<head>
    <jsp:include page="common/common-header.jsp"/>
    <link href="../css/style.css" rel="stylesheet" type="text/css">

    <title>Örökbefogadók</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<jsp:include page="/OrokbefogadoController"/>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Név</th>
            <th scope="col">Elérhetőség</th>
            <th scope="col">Rendszerben</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.orokbefogadok}">
            <tr>
                <td>${item.nev}</td>
                <td>${item.elerhetoseg}</td>
                <td>${item.felvetelIdopontja}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>


</body>
</html>

