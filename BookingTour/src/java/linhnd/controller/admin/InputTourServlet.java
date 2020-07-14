/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.controller.admin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnd.daos.ToursDAO;
import linhnd.dtos.ErrorInputTour;
import linhnd.dtos.ToursDTO;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author PC
 */
@WebServlet(name = "InputTourServlet", urlPatterns = {"/InputTourServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
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
        String imageUrl = null;
        boolean foundErr = false;
        ErrorInputTour error = new ErrorInputTour();

        try {

            if (!ServletFileUpload.isMultipartContent(request)) {
                //Khong thuc hien 
                return;
            }
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(new ServletRequestContext(request));
            } catch (FileUploadException e) {
                LOGGER.fatal(e.getMessage());
            }
            Iterator iter = items.iterator();
            Hashtable params = new Hashtable();
            String fileName = null;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    params.put(item.getFieldName(), item.getString());
                } else {
                    try {
                        String itemName = item.getName();
                        fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                        String realPath = getServletContext().getRealPath("/")
                                + "images\\" + fileName;
                        File saveFile = new File(realPath);
                        item.write(saveFile);
                        imageUrl = fileName;
                    } catch (Exception e) {
                        error.setErrorTourImage("Image error !");
                        foundErr = true;
                        LOGGER.fatal(e.getMessage());
                    }
                }
            }
            String tourID = (String) params.get("txtTourId");
            String tourName = (String) params.get("txtTourName");
            String tourPlace = (String) params.get("txtTourPlace");
            String fromDateS = (String) params.get("txtDateFrom");
            String toDateS = (String) params.get("txtDateTo");
            String price = (String) params.get("txtPriceTour");
            String quota = (String) params.get("txtQuota");

            ToursDAO dao = new ToursDAO();
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
