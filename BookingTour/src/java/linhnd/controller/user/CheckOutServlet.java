/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.controller.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnd.carts.CartObject;
import linhnd.carts.TourInCartDTO;
import linhnd.daos.BookingDAO;
import linhnd.daos.BookingDetailDAO;
import linhnd.daos.DiscountCodeDAO;
import linhnd.daos.UserHaveDiscountDAO;
import linhnd.dtos.DiscountCodeDTO;
import linhnd.dtos.ToursDTO;
import linhnd.dtos.UsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author PC
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    static Logger LOGGER = Logger.getLogger(CheckOutServlet.class);

    private static final String ERROR = "cart.jsp";

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
            HttpSession session = request.getSession();
            String discountCode = request.getParameter("discountCode");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = new Date();

            BookingDAO daoBooking = new BookingDAO();
            BookingDetailDAO daoBookingDetail = new BookingDetailDAO();
            DiscountCodeDAO daoDiscount = new DiscountCodeDAO();
            UserHaveDiscountDAO daoUserHaveDiscount = new UserHaveDiscountDAO();

            CartObject cart = (CartObject) session.getAttribute("CART");
            UsersDTO dtoUser = (UsersDTO) session.getAttribute("USER");
            int totalMoney = (int) session.getAttribute("TOTAL_BOOKING");
            double totalMoneyD = 0;
            if (cart != null) {
                //check quantity tour
                List<ToursDTO> listTourNotEnought = new ArrayList<>();
                Map<String, TourInCartDTO> listTourInCart = cart.getCart();
                for (Map.Entry<String, TourInCartDTO> entry : listTourInCart.entrySet()) {
                    TourInCartDTO value = entry.getValue();
                    if (value.getQuantity() > value.getRestQuantity()) {
                        listTourNotEnought.add(value.getTourDto());
                    }
                }

                if (listTourNotEnought.isEmpty()) {
                    String BookingId = dtoUser.getUserID() + dateFormat.format(date);
                    if (daoDiscount.getOneDiscountCode(discountCode) == null) {
                        discountCode = null;
                    }
                    if (discountCode != null) {
                        daoUserHaveDiscount.newUserUsesDiscount(dtoUser.getUserID(), discountCode);
                        DiscountCodeDTO dtoDiscountCode = daoDiscount.getOneDiscountCode(discountCode);
                        int percenDis = dtoDiscountCode.getPercentDis();
                        totalMoneyD = ((100 - percenDis) * totalMoney) / 100;
                    } else {
                        totalMoneyD = totalMoney;
                    }
                    if (daoBooking.newBooking(BookingId, String.valueOf(totalMoneyD), dtoUser.getUserID(), discountCode)) {
                        for (Map.Entry<String, TourInCartDTO> entry : listTourInCart.entrySet()) {
                            TourInCartDTO value = entry.getValue();
                            daoBookingDetail.newBookingDetail(value.getTourDto().getTourID(), BookingId, String.valueOf(value.getQuantity()));
                        }
                    }

                    session.setAttribute("CART", null);
                    session.setAttribute("TOTAL_BOOKING", null);
                    request.setAttribute("CHECK_OUT_SUCESS", "Check out Sucess !");
                    request.getRequestDispatcher(url).forward(request, response);
                } else {
                    request.setAttribute("ERROR_CHECKOUT", listTourNotEnought);
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
        } catch (Exception e) {
            LOGGER.fatal(e);
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
