<%-- 
    Document   : login
    Created on : Jun 14, 2020, 3:05:35 PM
    Author     : PC
--%>
<%@page import="linhnd.utils.APIWrapper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body class="bgLogin">
        <div class="loginclass">
            <form action="login" method="POST" class="p-4" style="background-color: white; margin-top: 50px; border-radius: 2%;">
                <h1 class="text-center">Booking Tour</h1>
                <h4 class="text-center">Login</h4>
                <div class="form-group">
                    <label >Username :</label>
                    <input type="text" class="form-control" placeholder="Enter username" name="txtUsername">
                </div>
                <div class="form-group">
                    <label for="pwd">Password :</label>
                    <input type="password" class="form-control" placeholder="Enter password" name="txtPassword">
                </div>
                <c:set var="error" value="${requestScope.ERROR}"/>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
                <button type="submit" class="btn btn-success w-100">Login</button>
                <a href="${ APIWrapper.getDiaLogLink() }" class="btn btn-outline-primary w-100 mt-3">Login with Facebook</a>
            </form>
        </div>
    </body>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
    crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
    crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
    crossorigin="anonymous"></script>
</html>