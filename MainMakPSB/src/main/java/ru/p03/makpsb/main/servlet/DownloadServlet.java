/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.makpsb.main.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 003-0818
 */
@WebServlet(name = "DownloadServlet", urlPatterns = {"/download.do"})
public class DownloadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        if (request.getSession().getAttribute("user") == null) {
//            //response.sendRedirect(request.getContextPath() + "/login.do");
//            return;
//        }
        
        String filename = request.getParameter("filename");
        String simplefile = request.getParameter("simplefile");
        
        File f = new File(filename);
        int length   = 0;
        try (ServletOutputStream op = response.getOutputStream()) {
            ServletContext context  = getServletConfig().getServletContext();
            String mimetype = context.getMimeType( filename );
            
            response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
            response.setContentLength((int)f.length() );
            response.setHeader( "Content-Disposition", "attachment; filename=\"" + (simplefile == null ? simplifyFilename(filename) : simplefile) + "\"" );
            
            byte[] bbuf = new byte[4096];
            try (DataInputStream in = new DataInputStream(new FileInputStream(f))) {
                while ((in != null) && ((length = in.read(bbuf)) != -1)){
                    op.write(bbuf,0,length);
                }
            }
            op.flush();
        }
    }
    
    private String simplifyFilename(String filename){
        String[] split = filename.split("\\\\");
        String s = filename;
        if (split.length > 0){
            s = split[split.length - 1];
        }
        return s;
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
