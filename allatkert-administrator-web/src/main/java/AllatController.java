
import hu.alkfejl.dao.OrokbefogadoDAO;
import hu.alkfejl.dao.OrokbefogadoDAOImpl;
import hu.alkfejl.model.Allat;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AllatController")
public class AllatController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private OrokbefogadoDAO dao = new OrokbefogadoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("allatok", dao.listAllAllat());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("select"));
        String eredmeny = req.getParameter("select");
        if(eredmeny.equals("orokbefogadott")) {
            req.setAttribute("allatok", adopted());

        }else if(eredmeny.equals("nemorokbefogadott")){
            req.setAttribute("allatok", notAdopted());

        }else if(eredmeny.equals("mind")){
            req.setAttribute("allatok", dao.listAllAllat());

        }
    }
    private List<Allat> adopted() {
        List<Allat> result = new ArrayList<>();
        for(Allat a: dao.listAllAllat()) {
            for(Konyveles k: dao.listAllKonyveles()) {
                if(a.getNev().toLowerCase().equals(k.getAllatNev().toLowerCase())) {
                    result.add(a);
                }
            }

        }


        return result;
    }
    private List<Allat> notAdopted() {
        List<Allat> result = new ArrayList<>();
        for(Allat a: dao.listAllAllat()) {
            int count = 0;
            for (Konyveles k: dao.listAllKonyveles()) {
                if(a.getNev().toLowerCase().equals(k.getAllatNev().toLowerCase())) {
                    count=1;
                }
            }
            if(count==0) {
                result.add(a);
            }
        }
        return result;
    }
}
