package hu.alkfejl.controller;

import hu.alkfejl.dao.OrokbefogadoDAO;
import hu.alkfejl.dao.OrokbefogadoDAOImpl;
import hu.alkfejl.model.Orokbefogado;
import hu.alkfejl.utils.Utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    OrokbefogadoDAO dao = new OrokbefogadoDAOImpl();

    public LoginController() {
        super();
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        int count = 0;
        for (Orokbefogado o : dao.listAllUser()) {
            if(o.getUsername().equals(request.getParameter("username")) && o.getPassword().equals(request.getParameter("password"))) {
                count = 1;
            }
        }
        if(count==1 || (request.getParameter("username").equals("admin") && request.getParameter("password").equals("admin"))) {
            response.addCookie(new Cookie("username", request.getParameter("username")));
            response.sendRedirect("pages/list_orokbefogado.jsp");
        }else {
            response.sendRedirect("pages/login.jsp");
        }


    }


}
