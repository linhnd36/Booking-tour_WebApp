<%-- 
    Document   : insert
    Created on : Jun 17, 2020, 9:36:16 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Page</title>
        <meta charset="UTF-8">   
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="load-search-page">Booking Tour - Admin</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="load-search-page">Search Tour</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="insert">Insert Tour</a>
                        </li>
                        <li class="nav-item ml-5">
                            <span class="text-danger">Welcome ${sessionScope.USER.name} </span>
                        </li>
                    </ul>
                    <div>
                        <a class="btn btn-outline-secondary" href="logout" >
                            Log Out
                        </a>
                    </div>
                </div>
            </nav>
        </header>

        <div class="container">
            <div class="bgcolor">
                <c:set var="error" value="${requestScope.ERROR_INPUT}"/>
                <form action="input-tour" method="POST"  enctype="multipart/form-data">
                    <div class="container">
                        <h3 class="p-5">Input Tour :</h3>
                        <div class=" row ml-1">
                            <div class="input-group mb-3 w-25">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Tour ID:</span>
                                </div>
                                <input type="text" class="form-control" name="txtTourId"  value="${param.txtTourId}" >                              
                            </div>
                            <div class="input-group mb-3 ml-5 w-25">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Quota :</span>
                                </div>
                                <input type="number" min="1" step="1" max="100" class="form-control" name="txtQuota" 
                                       <c:if test="${not empty param.txtQuota}">
                                           value="${param.txtQuota}"
                                       </c:if>
                                       <c:if test="${empty param.txtQuota }">
                                           value="1"
                                       </c:if>
                                       >
                            </div>
                        </div>
                        <c:if test="${not empty error.errorTourId}">
                            <div class="alert alert-danger w-25" role="alert">
                                ${error.errorTourId}
                            </div>
                        </c:if>
                        <div class="input-group mb-3 w-75">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Tour Name:</span>
                            </div>
                            <input type="text" class="form-control" name="txtTourName" value="${param.txtTourName}" >
                        </div>
                        <c:if test="${not empty error.errorTourName}">
                            <div class="alert alert-danger w-75" role="alert">
                                ${error.errorTourName}
                            </div>
                        </c:if>
                        <div class="input-group mb-3 w-75">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Tour Place: </span>
                            </div>
                            <input type="text" class="form-control" name="txtTourPlace" value="${param.txtTourPlace}">
                        </div>
                        <c:if test="${not empty error.errorTourPlace}">
                            <div class="alert alert-danger w-75" role="alert">
                                ${error.errorTourPlace}
                            </div>
                        </c:if>
                        <div class=" row ml-1">
                            <div class="input-group mb-3 w-25">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Date From:</span>
                                </div>
                                <input type="date" min="${requestScope.DATENOW}" class="form-control" name="txtDateFrom" value="${param.txtDateFrom}">
                            </div>
                            <span class="mt-2 ml-5">======>></span>
                            <div class="input-group mb-3 ml-5 w-25">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Date To:</span>
                                </div>
                                <input type="date" min="${requestScope.DATENOW}" class="form-control" name="txtDateTo" value="${param.txtDateTo}">
                            </div>                        
                        </div>
                        <c:if test="${not empty error.errorTourDate}">
                            <div class="alert alert-danger w-75" role="alert">
                                ${error.errorTourDate}
                            </div>
                        </c:if>
                        <div class="input-group mb-3 w-25">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Price Tour:</span>
                            </div>
                            <input type="number" min="10" step="1" max="100000" class="form-control" name="txtPriceTour"                               
                                   <c:if test="${not empty param.txtPriceTour}">
                                       value="${param.txtPriceTour}"
                                   </c:if>
                                   <c:if test="${empty param.txtPriceTour }">
                                       value="10"
                                   </c:if>
                                   >
                            <span class="input-group-text">$</span>
                        </div>
                        <div class="ml-2 col-sm-6">
                            <div id="msg">Select Image :</div>
                            <div  id="image-form">
                                <input type="file" name="txtImagerUrl" class="file" accept="image/*"> 
                                <div class="input-group my-3">
                                    <input type="text" class="form-control" disabled placeholder="Upload Imager" id="file"  value="${param.txtImagerUrl}" >
                                    <div class="input-group-append">
                                        <button type="button" class="browse btn btn-primary">Browse</button>
                                    </div>                                  
                                </div>
                            </div>
                            <c:if test="${not empty error.errorTourImage}">
                                <div class="alert alert-danger" role="alert">
                                    ${error.errorTourImage}
                                </div>
                            </c:if>
                        </div>
                        <div class="ml-2 col-sm-6">
                            <img src="https://placehold.it/320x160" id="preview" class="img-thumbnail">
                        </div>
                        <c:if test="${not empty sessionScope.INPUT_SUCESS}">
                            <div class="alert alert-success" role="alert">
                                ${sessionScope.INPUT_SUCESS}
                            </div>
                        </c:if>
                        <button type="submit" class="btn btn-success mt-3 mb-5 w-25">Input
                            Tour</button>
                    </div>              
                </form>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
                integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
                integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
        <script src="./js/view.js"></script>

    </body>

</html>