/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnd.daos.ToursDAO;
import linhnd.dtos.ErrorInputTour;
import linhnd.dtos.ToursDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author PC
 */
@WebServlet(name = "InputTourServlet", urlPatterns = {"/InputTourServlet"})
public class InputTourServlet extends HttpServlet {

    static Logger LOGGER = Logger.getLogger(InputTourServlet.class);

    private static final String INPUT_PAGE = "LoadInputTourServlet";
    private static final String SUCESS = "load-insert-page";

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
        String url = INPUT_PAGE;
        try {
            String tourID = request.getParameter("txtTourId");
            String tourName = request.getParameter("txtTourName");
            String tourPlace = request.getParameter("txtTourPlace");
            String fromDateS = request.getParameter("txtDateFrom");
            String toDateS = request.getParameter("txtDateTo");
            String price = request.getParameter("txtPriceTour");
            String quota = request.getParameter("txtQuota");
            String imageUrl = request.getParameter("txtImagerUrl");

            ToursDAO dao = new ToursDAO();
            ErrorInputTour error = new ErrorInputTour();
            boolean foundErr = false;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateFromCheck = null;
            String dateToCheck = null;
            HttpSession session = request.getSession();

            try {
                Date dateFrom = dateFormat.parse(fromDateS);
                Date dateTo = dateFormat.parse(toDateS);

                if (!dateFrom.before(dateTo)) {
                    Date tmp = dateFrom;
                    dateFrom = dateTo;
                    dateTo = tmp;
                    dateFromCheck = dateFormat.format(dateFrom);
                    dateToCheck = dateFormat.format(dateTo);
                } else {
                    dateFromCheck = fromDateS;
                    dateToCheck = toDateS;
                }
            } catch (ParseException e) {
                foundErr = true;
                error.setErrorTourDate("Select date after input ");
            }
            if (tourID.trim().equals("")) {
                foundErr = true;
                error.setErrorTourId("Tour Id can't blank");
            }

            if (dao.checkTourId(tourID.trim())) {
                foundErr = true;
                error.setErrorTourId(tourID + " is existed. Please, input another");
            }

            if (tourName.trim().equals("")) {
                foundErr = true;
                error.setErrorTourName("Please, Input Name Tour");
            }
            if (tourPlace.trim().equals("")) {
                foundErr = true;
                error.setErrorTourPlace("Input Place ");
            }
            if (imageUrl.trim().equals("")) {
                foundErr = true;
                error.setErrorTourImage("Please, Select Imager Tour");
            }
            if (foundErr) {
                request.setAttribute("ERROR_INPUT", error);
                session.setAttribute("INPUT_SUCESS", null);
            } else {
                ToursDTO dto = new ToursDTO();
                dto.setTourID(tourID);
                dto.setTourName(tourName);
                dto.setToDate(dateToCheck);
                dto.setFromDate(dateFromCheck);
                dto.setImageLink(imageUrl);
                dto.setPlace(tourPlace);
                dto.setPrice(price);
                dto.setQuota(quota);
                if (dao.newTour(dto)) {
                    url = SUCESS;
                    session.setAttribute("INPUT_SUCESS", "Input Sucessfull");
                    response.sendRedirect(url);
                } else {
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
        } catch (SQLException | NamingException e) {
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
