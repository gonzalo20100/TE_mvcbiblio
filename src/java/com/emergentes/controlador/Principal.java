
package com.emergentes.controlador;

import com.emergentes.modelo.Libro;
import com.emergentes.modelo.LibroDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.message.callback.PrivateKeyCallback;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Principal", urlPatterns = {"/Principal"})
public class Principal extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses =request.getSession();
        // todos los libros almacentan en el atributo gestor
        LibroDAO gestor = (LibroDAO) ses.getAttribute("gestor");
        // verificamos si el atributo existe
        if (gestor == null) {
            // En caso de que no exista se incluye el atributo
            LibroDAO auxi = new LibroDAO();
            ses.setAttribute("gestor", auxi);
        }
        String op = request.getParameter("op");
        if (op==null) {
            op="";
        }
        
        //el controlador verifica la opcion para redireccionar al recurso correspondiente
        if (op.trim().equals("")) {
            response.sendRedirect("vista/listado.jsp");
        }
        if (op.trim().equals("nuevo")) {
            ses = request.getSession();
            // todos los libors almacentan e el atributo gestor
            Libro obj = new Libro();
                //colocamos el id para que se muestre
                obj.setId(gestor.getCorrelativo()+1);
                request.setAttribute("item",obj);
                request.getRequestDispatcher("vista/nuevo.jsp").forward(request, response);
        }
        if (op.trim().equals("editar")) {
            int pos = gestor.posicion(Integer.parseInt(request.getParameter("id")));
            Libro obj = gestor.getLibros().get(pos);
            
            request.setAttribute("item", obj);
            request.getRequestDispatcher("vista/editar.jsp").forward(request, response);
        }
        if (op.trim().equals("eliminar")) {
            
            int pos = gestor.posicion(Integer.parseInt(request.getParameter("id")));
            gestor.getLibros().remove(pos);
            
            //request.setAtribute ("item", obj)
            response.sendRedirect("vista/listado.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession();
        // todos los libros se almacenan en el atributo gestor
        LibroDAO gestor = (LibroDAO) ses.getAttribute("gestor");
        Libro obj = new Libro();
        
        //incrementando en contador
        gestor.setCorrelativo(gestor.getCorrelativo()+1);
        obj.setId(gestor.getCorrelativo());
        obj.setAutor(request.getParameter("autor"));
        obj.setTitulo(request.getParameter("titulo"));
        obj.setEstado(Integer.parseInt(request.getParameter("estado")));
        
        String tipo = request.getParameter("tipo");
        if (tipo.equals("-1")) {
            gestor.insertar(obj);
        } else {
            gestor.modificar(obj.getId(), obj);
        }
        response.sendRedirect("vista/listado.jsp");
    }
}
