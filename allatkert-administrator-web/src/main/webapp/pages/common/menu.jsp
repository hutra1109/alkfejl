<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav w-100">
            <li class="nav-item"><a class="nav-link" href="list_allat.jsp">Állat
                listázása</a></li>
            <li class="nav-item"><a class="nav-link" href="list_orokbefogado.jsp">Személyek
                listázása</a></li>
            <li class="nav-item"><a class="nav-link" href="list_konyveles.jsp">Örökbefogadások
                listázása</a></li>
            <li class="nav-item"><a class="nav-link" href="search.jsp">Keresés</a></li>


            <c:if test="${cookie.username != null}">
                <li class="nav-item dropdown ml-auto">
                    <a class='nav-link dropdown-toggle' href='#' id='navbarDropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                        ${cookie.username.value}
                    </a>
                    <div class='dropdown-menu dropdown-menu-right' aria-labelledby='navbarDropdownMenuLink'>
                        <a class='dropdown-item' href='../LogoutController'>Kijelentkezés</a>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>