/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.controller.user;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnd.carts.CartObject;
import linhnd.carts.TourInCartDTO;

/**
 *
 * @author PC
 */
@WebServlet(name = "DeleteTourInCartServlet", urlPatterns = {"/DeleteTourInCartServlet"})
public class DeleteTourInCartServlet extends HttpServlet {

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
            String tourId = request.getParameter("tourId");

            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart != null) {
                cart.removeTourFromCart(tourId);
                if (cart.getCart() == null) {
                    session.setAttribute("CART", null);
                } else {
                    int totalBooking = 0;
                    Map<String, TourInCartDTO> listTourInCart = cart.getCart();
                    for (Map.Entry<String, TourInCartDTO> entry : listTourInCart.entrySet()) {
                        TourInCartDTO value = entry.getValue();
                        totalBooking = totalBooking + (value.getQuantity() * Integer.parseInt(value.getTourDto().getPrice()));
                    }
                    session.setAttribute("CART", cart);
                    session.setAttribute("TOTAL_BOOKING", totalBooking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect("view-cart");
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
