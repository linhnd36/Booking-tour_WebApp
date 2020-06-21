/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.controller.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnd.daos.DiscountCodeDAO;
import linhnd.daos.UserHaveDiscountDAO;
import linhnd.dtos.DiscountCodeDTO;
import linhnd.dtos.UsersDTO;

/**
 *
 * @author PC
 */
@WebServlet(name = "CheckDiscountCodeServlet", urlPatterns = {"/CheckDiscountCodeServlet"})
public class CheckDiscountCodeServlet extends HttpServlet {

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
        try {
            HttpSession session = request.getSession();
            String discoutCode = request.getParameter("txtDiscountCode");

            UsersDTO dtoUser = (UsersDTO) session.getAttribute("USER");

            DiscountCodeDAO daoDiscountCode = new DiscountCodeDAO();
            UserHaveDiscountDAO daoUserHaveDiscount = new UserHaveDiscountDAO();
            DiscountCodeDTO dto = daoDiscountCode.getOneDiscountCode(discoutCode);
            if (dto != null) {
                if (daoUserHaveDiscount.checkDiscount(discoutCode, dtoUser.getUserID())) {
                    request.setAttribute("DISCOUNT_CODE", dto);
                } else {
                    request.setAttribute("DISCOUNT_CODE", "ERROR");
                }
            } else {
                request.setAttribute("DISCOUNT_CODE", "ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("cart.jsp").forward(request, response);
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
