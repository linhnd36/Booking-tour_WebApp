<%-- 
    Document   : cart
    Created on : Jun 21, 2020, 11:12:14 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Cart User</title>
        <meta charset="UTF-8">   
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="load-search-page">Booking Tour</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="load-search-page">Search Tour</a>
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
            <c:set var="cart" value="${sessionScope.CART}"/>
            <c:if test="${not empty cart}">
                <c:set var="itemTour" value="${cart.cart}"/>
                <c:if test="${not empty itemTour}">
                    <table id="cart" class="table table-hover table-condensed">
                        <thead>
                            <tr>
                                <th style="width: 52%;">Tour</th>
                                <th style="width: 10%;">Price</th>
                                <th style="width: 8%;">Quantity</th>
                                <th style="width: 20%;" class="text-center">Subtotal</th>
                                <th style="width: 10%;">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tourMap" items="${itemTour}" varStatus="counter">
                                <c:set var="dto" value="${tourMap.value.tourDto}"/>
                            <form action="update-cart" >
                                <tr>
                                    <td>
                                        <div class="row">
                                            <div class="col-sm-5 hidden-xs">
                                                <img src="./images/${dto.imageLink}"
                                                     style="width:200px;margin:0; height:150px;" />
                                            </div>
                                            <div class="col-sm-7">
                                                <h4 class="nomargin">${dto.tourName}</h4>
                                                <p>From date: ${dto.fromDate} </p>
                                                <p>To date: ${dto.toDate} </p>
                                                <p>Place: ${dto.place}</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td data-th="Price">${dto.price}$</td>
                                    <td data-th="Quantity">
                                        <input type="number" class="form-control text-center" name="txtQuantityNew" value="${tourMap.value.quantity}" min="1" />
                                        <p class="text-danger">Only ${tourMap.value.restQuantity} tickets left.</p>
                                    </td>
                                    <td data-th="Subtotal" class="text-center">
                                        ${tourMap.value.quantity * dto.price }$
                                        <p class="text-danger">Please update again amount or remove the tour from the cart.</p>
                                    </td>
                                    <td class="actions" data-th="">
                                        <c:url var="deleteUrl" value="delete-cart">
                                            <c:param name="tourId" value="${dto.tourID}"/>
                                        </c:url>
                                        <button type="submit" class="btn btn-info">Update</button>
                                        <a href="${deleteUrl}" class="btn btn-danger">Delete</a>
                                        <input type="hidden" name="tourId" value="${dto.tourID}" />
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                    </c:if>

                    </tbody>
                    <tfoot>
                        <tr>
                            <td>
                                <a href="paging-page-search" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a>
                            </td>
                            <td colspan="2" class="hidden-xs"></td>
                            <td class="hidden-xs text-center">
                                <strong>Total ${sessionScope.TOTAL_BOOKING}$</strong>
                            </td>
                            <td>
                                <c:url var="checkOutUrl" value="check-out">
                                    <c:if test="${ requestScope.DISCOUNT_CODE != 'ERROR'}">
                                        <c:param name="discountCode" value="${requestScope.DISCOUNT_CODE.discountId}"/>
                                    </c:if>
                                </c:url>
                                <a href="${checkOutUrl}"
                                   class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a>
                            </td>
                        </tr>
                    </tfoot>
                </table>
                <c:set var="discount" value="${requestScope.DISCOUNT_CODE}"/>
                <c:if test="${discount != 'ERROR' && not empty discount }">
                    <c:set var="moneyDiscount" value="${(discount.percentDis * sessionScope.TOTAL_BOOKING)/100}"/>
                </c:if>
                <c:if test="${discount == 'ERROR'}">
                    <c:set var="moneyDiscount" value="${(0 * sessionScope.TOTAL_BOOKING)/100}"/>
                </c:if>
                <div class="row">
                    <div class="col-8">
                        <c:set var="listTourNotEnought" value="${requestScope.ERROR_CHECKOUT}"/>
                        <c:if test="${not empty listTourNotEnought}">
                            <c:forEach var="dtoTourNotEnought" items="${listTourNotEnought}" >
                                <div class="alert alert-warning" role="alert">
                                    ${dtoTourNotEnought.tourName} not enought !
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="col-4">
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-muted">Your cart</span>
                        </h4>
                        <ul class="list-group mb-3">
                            <li class="list-group-item d-flex justify-content-between lh-condensed">
                                <div>
                                    <h6 class="my-0">Total Tour:</h6>
                                </div>
                                <span class="text-muted">${sessionScope.TOTAL_BOOKING}$</span>
                            </li>

                            <li class="list-group-item d-flex justify-content-between bg-light">
                                <div class="text-success">
                                    <h6 class="my-0">Promo code</h6>
                                </div>
                                <span class="text-success">- ${moneyDiscount}$</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span>Total (USD)</span>
                                <strong>${sessionScope.TOTAL_BOOKING - moneyDiscount}$</strong>
                            </li>
                        </ul>

                        <form class="card p-2" action="check-discount">
                            <div class="input-group">
                                <input type="text" class="form-control" name="txtDiscountCode" placeholder="Discount code" value="${param.txtDiscountCode}">
                                <div class="input-group-append">
                                    <button type="submit" class="btn btn-secondary">Check Code</button>
                                </div>                            
                            </div>

                            <c:if test="${ discount != 'ERROR' && not empty discount}">
                                <div class="alert alert-success" role="alert">
                                    You can user this code !
                                </div>
                            </c:if>
                            <c:if test="${ discount == 'ERROR'}">
                                <div class="alert alert-danger" role="alert">
                                    Error Code !
                                </div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty cart && empty requestScope.CHECK_OUT_SUCESS }">
                <div class="alert alert-danger" role="alert">
                    No Tour in Cart !
                </div>
            </c:if>
            <c:if test="${empty cart && not empty requestScope.CHECK_OUT_SUCESS }">
                <div class="alert alert-success" role="alert">
                    ${requestScope.CHECK_OUT_SUCESS}
                </div>
                <a href="paging-page-search" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a>
            </c:if>
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

