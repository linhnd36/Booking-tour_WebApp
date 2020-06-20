/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnd.daos.UsersDAO;
import linhnd.dtos.UsersDTO;
import linhnd.utils.APIWrapper;

/**
 *
 * @author PC
 */
@WebServlet(name = "LoginFacebookServlet", urlPatterns = {"/LoginFacebookServlet"})
public class LoginFacebookServlet extends HttpServlet {
    private static String ERROR = "error.jsp";
    private static String SUCESS = "LoadPageSearchServlet";
    
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
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String code = request.getParameter("code");
            String error = request.getParameter("error");
            if (error == null) {
                APIWrapper wrapper = new APIWrapper();

                String accessToken = wrapper.getAccessToken(code);
                wrapper.setAccessToken(accessToken);

                UsersDTO userInfo = wrapper.getUserInfor();
                UsersDAO dao = new UsersDAO();
                boolean userExit = dao.checkFacebookID(userInfo.getFacebookID()) != null;
                if (!userExit) {
                    dao.registerFacebookAccount(userInfo.getName(), userInfo.getFacebookID(), userInfo.getFacebookLink());
                }
                HttpSession session = request.getSession();
                session.setAttribute("USER", userInfo);
                url = SUCESS;
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect("login.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
