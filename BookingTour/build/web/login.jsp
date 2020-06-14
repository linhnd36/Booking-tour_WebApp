<%-- 
    Document   : login
    Created on : Jun 14, 2020, 3:05:35 PM
    Author     : PC
--%>

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
            <form action="" method="POST" class="p-4" style="background-color: white; margin-top: 50px; border-radius: 2%;">
                <h1 class="text-center">Booking Tour</h1>
                <h4 class="text-center">Login</h4>
                <div class="form-group">
                    <label for="email">Username :</label>
                    <input type="email" class="form-control" placeholder="Enter email" name="txtUsername">
                </div>
                <div class="form-group">
                    <label for="pwd">Password :</label>
                    <input type="password" class="form-control" placeholder="Enter password" name="txtPassword">
                </div>
                <button type="submit" class="btn btn-success w-100">Login</button>
                <button type="submit" class="btn btn-outline-primary w-100 mt-3">Login with Facebook</button>
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