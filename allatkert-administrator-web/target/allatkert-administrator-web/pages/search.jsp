<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@page import="java.sql.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="hu.alkfejl.dao.DBConfig" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.awt.*" %>
<html>
<head>
    <jsp:include page="common/common-header.jsp"/>
    <title>List Person</title>
    <link href="../css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<form action="" method="post">
<div class="container">
    <div class="form-group">
        <div class="col-sm-5">
            <div>
                <label for="search">
                    <input type="text" name="search" class="form-control" placeholder="Search here" id="search">
                </label>
            </div>
        </div>
    </div>
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
        <%
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String query = "SELECT * FROM Konyveles where obNev like'%"+ request.getParameter("search")+"%'" +
                    "or allatNev like '%"+request.getParameter("search")+"%'" +
                    "or tamogatasTipus like '%"+request.getParameter("search")+"%'";
            try (Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(query)){

                while (rs.next())
                {
                    %>
                    <tr>
                        <td><%=rs.getString("obNev")%></td>
                        <td><%=rs.getString("allatNev")%></td>
                        <td><%=rs.getDate("mikor")%></td>
                        <td><%=rs.getString("leiras")%></td>
                        <td><%=rs.getString("tamogatasTipus")%></td>
                        <td><%=rs.getInt("osszeg")%></td>
                        <td><%=rs.getInt("mennyiseg")%></td>
                        <td><%=rs.getString("tamogatasGyakorisag")%></td>
                    </tr>

                    <%
                }



            }catch (Exception ex) {
                ex.printStackTrace();
            }
        %>
    </table>
</div>
</form>

</body>
</html>

