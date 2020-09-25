<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<html>
<head>
    <jsp:include page="common/common-header.jsp"/>
    <link href="../css/style.css" rel="stylesheet" type="text/css">

    <title>Állatok</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<jsp:include page="/AllatController"/>
<form action="" method="post" name="fileChanged">
    <label for="select">
        <select id="select" name="select" onchange="document.fileChanged.submit()">
            <option selected disabled hidden>Válassz</option>
            <option value="mind">Mind</option>
            <option value="orokbefogadott">Örökbefogadott</option>
            <option value="nemorokbefogadott">Nincs örökbefogadva</option>

        </select>
    </label>
</form>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Név</th>
            <th scope="col">Faj</th>
            <th scope="col">Bemutatkozás</th>
            <th scope="col">Születési éve</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.allatok}">

            <tr>
                <td>${item.nev}</td>
                <td>${item.faj}</td>
                <td>${item.szoveg}</td>
                <td>${item.szulEv}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>




</body>
</html>

