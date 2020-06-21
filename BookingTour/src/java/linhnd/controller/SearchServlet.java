/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnd.daos.BookingDetailDAO;
import linhnd.daos.ToursDAO;
import linhnd.dtos.ToursDTO;

/**
 *
 * @author PC
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final int PAGE_SIZE = 20;
    private static final String SUCESS = "PagingPageSearchServlet";
    private static final String ERROR = "error.jsp";

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
        String priceFrom = "0", priceTo = "999999999";
        String url = ERROR;
        try {
            String dateFrom = request.getParameter("txtDateFrom");
            String dateTo = request.getParameter("txtDateTo");
            String place = request.getParameter("txtPlace");
            String price = request.getParameter("txtPrice");

            HttpSession session = request.getSession();

            if (price.equals("0")) {
                priceFrom = "0";
                priceTo = "100";
            } else if (price.equals("1")) {
                priceFrom = "100";
                priceTo = "200";
            } else if (price.equals("2")) {
                priceFrom = "200";
                priceTo = "300";
            } else if (price.equals("3")) {
                priceFrom = "300";
                priceTo = "999999999";
            } else if (price.equals("4")) {
                priceFrom = "0";
                priceTo = "999999999";
            }
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateFromD = dateFormat.parse(dateFrom);
                Date dateToD = dateFormat.parse(dateTo);
                if (!dateFromD.before(dateToD)) {
                    Date tmp = dateFromD;
                    dateFromD = dateToD;
                    dateToD = tmp;
                }
            } catch (ParseException e) {
                dateFrom = "noSearchDate";
                dateTo = "noSearchDate";
            }
            ToursDAO daoTour = new ToursDAO();
            BookingDetailDAO daoBookingDetail = new BookingDetailDAO();
            List<ToursDTO> result = daoTour.searchTour(place, dateFrom, dateTo, priceFrom, priceTo);
            List<ToursDTO> listSearchValid = new ArrayList<>();
            for (ToursDTO toursDTO : result) {
                if (daoBookingDetail.countQuantityBooking(toursDTO.getTourID()) < Integer.parseInt(toursDTO.getQuota())) {
                    listSearchValid.add(toursDTO);
                }
            }
            int numberOfTour = listSearchValid.size();
            int page = (int) Math.ceil((double) numberOfTour / PAGE_SIZE);
            session.setAttribute("LIST_SEARCH_TOUR", listSearchValid);
            session.setAttribute("PAGE_DETAIL", page);
            url = SUCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
