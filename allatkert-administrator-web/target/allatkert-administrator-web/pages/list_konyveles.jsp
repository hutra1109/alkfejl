<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<html>
<head>
    <jsp:include page="common/common-header.jsp"/>
    <link href="../css/style.css" rel="stylesheet" type="text/css">

    <title>Könyvelés</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<jsp:include page="/KonyvelesController"/>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Név</th>
            <th scope="col">Állat neve</th>
            <th scope="col">Mikor</th>
            <th scope="col">Leírás</th>
            <th scope="col">Támogatás típusa</th>
            <th scope="col">Összeg</th>
            <th scope="col">Mennyiség</th>
            <th scope="col">Gyakoriság</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.konyvelesek}">
            <tr>
                <td>${item.obNev}</td>
                <td>${item.allatNev}</td>
                <td>${item.mikor}</td>
                <td>${item.leiras}</td>
                <td>${item.tamogatasTipus}</td>
                <td>${item.osszeg}</td>
                <td>${item.mennyiseg}</td>
                <td>${item.tamogatasGyakorisag}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>


</body>
</html>

