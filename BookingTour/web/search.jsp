<%-- 
    Document   : search
    Created on : Jun 14, 2020, 9:53:50 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
    <head>
        <title>Search Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <c:set var="user" value="${sessionScope.USER}" />
                <a class="navbar-brand" href="load-search-page">Booking Tour <c:if test="${user.roleId == 'ADMIN'}">- Admin</c:if></a>
                    <div class="collapse navbar-collapse">                
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item">
                                <a class="nav-link active" href="load-search-page">Search Tour</a>
                            </li>
                        <c:if test="${not empty user && user.roleId == 'ADMIN' }">
                            <li class="nav-item">
                                <a class="nav-link" href="load-insert-page">Insert Tour</a>
                            </li>
                        </c:if>
                        <c:if test="${not empty user}">
                            <li class="nav-item ml-5">
                                <span class="text-danger">Welcome, ${user.name} </span>
                            </li>
                        </c:if>
                    </ul>
                    <div>
                        <c:if test="${ user == null}">
                            <a class="btn btn-outline-success  text-white" href="login-page">
                                Log In
                            </a>
                        </c:if>
                        <c:if test="${not empty user }">
                            <c:if test="${ user.roleId != 'ADMIN'}">
                                <a href="view-cart" class="btn btn-outline-warning  text-white">View Cart</a>
                            </c:if>                    
                            <a class="btn btn-outline-secondary  text-white" href="logout">
                                Log Out
                            </a>
                        </c:if>
                    </div>
                </div>
            </nav>
        </header>
        <div class="row bg-search" >
            <div class="col-6" style="padding-top: 40px; padding-left: 150px">
                <div class="search-form ">
                    <div>
                        <form class="p-3" action="search">
                            <div class="form-group m-3 ">
                                <label class="text-white">Place</label>
                                <input type="text" class="form-control"
                                       placeholder="Enter Location" name="txtPlace" value="${param.txtPlace}">
                            </div>
                            <div class="form-group m-3 row">
                                <div class="col-6">
                                    <label class="text-white">Date From</label>
                                    <input type="date" class="form-control" min="${requestScope.DATE_NOW}" placeholder="Date From" name="txtDateFrom">
                                </div>
                                <div class="col-6">
                                    <label class="text-white">Date To</label>
                                    <input type="date" class="form-control" min="${requestScope.DATE_NOW}" placeholder="Date To" name="txtDateTo">
                                </div>
                            </div>
                            <div class="form-group w-50 ml-5">
                                <label class="text-white">Select Price</label>
                                <select class="form-control" name="txtPrice">
                                    <option value="4" > ALL </option>
                                    <option value="0"> < 100$</option>
                                    <option value="1">100$ - 200$</option>
                                    <option value="2">200$ - 300$</option>
                                    <option value="3"> > 300$ </option>                                 
                                </select>
                            </div>
                            <button type="submit" class="btn btn-success w-75 ml-5">Search</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-6">
                <h1 class="text-white" style="padding-top: 150px;">Travel the World !</h1>
            </div>
        </div>
        <div class="container">

            <div class="row m-4">
                <c:set var="listDiscount" value="${requestScope.LIST_DISCOUNT}"/>
                <c:if test="${not empty listDiscount}">
                    <h1 class=" mt-2">Some discount</h1>
                    <div class="row">
                        <c:forEach var="dto" items="${listDiscount}" >
                            <div class=" col-4 p-3">
                                <div class="card">
                                    <img class="card-img-top discount-img" src="${dto.imagesLink}" alt="Card image cap">
                                    <div class="card-body ">
                                        <h5 class="card-title">${dto.name}</h5>
                                        <p class="card-text"> Expiry Date: ${dto.expiryDate} </p>
                                        <p class="card-text"> Percent Discount: ${dto.percentDis}% </p>
                                        <a class="btn btn-primary  text-white">${dto.discountId}</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <c:set var="listTour" value="${requestScope.SUBLIST_SEARCH}"/>
            <c:if test="${ not empty listTour}">
                <h1 class="text-center mt-2">Search Result</h1>
                <div class="row m-5">
                    <c:forEach var="dtoTour" items="${listTour}">
                        <div class="col-sm-6 mt-2">
                            <div class="card">
                                <img class="card-img-top imageTour"
                                     src="./images/${dtoTour.imageLink}">
                                <div class="card-body">
                                    <h5 class="card-title">${dtoTour.tourName}</h5>
                                    <p class="card-text"><strong>Date:</strong>  ${dtoTour.fromDate}   -->  ${dtoTour.toDate} </p>
                                    <p class="card-text"><strong>Place:</strong>  ${dtoTour.place} -- <span style="color: red">Price: ${dtoTour.price}$</span> </p>   
                                    <p class="card-text"><strong>Quantity:</strong>  ${dtoTour.quota} </p>
                                    <c:url var="url" value="add-tour-to-cart">
                                        <c:param name="txtTourId" value="${dtoTour.tourID}"/>
                                    </c:url>
                                    <c:if test="${empty user || user.roleId == 'USER'}">
                                        <a href="${url}" class="btn btn-primary">Add to Cart</a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${empty listTour && empty listDiscount}">
                <div class="alert alert-danger" role="alert">
                    No Result !
                </div>
            </c:if>

        </div>
        <nav>
            <c:if test="${PAGE_DETAIL > 1 && empty listDiscount}">
                <ul class="pagination pt-2" style="margin-left: 40%;">
                    <c:forEach var="index" begin="1" end="${PAGE_DETAIL}">
                        <form action="paging-page-search">
                            <li class="page-item 
                                <c:if test="${index == CURRENT_PAGE_DETAIL}">
                                    active
                                </c:if>
                                ">
                                <input class="page-link" type="submit" value="${index}" name="pageSearchIndex"/>
                            </li>
                        </form>
                    </c:forEach>
                </ul>
            </c:if>                      
        </nav>
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
