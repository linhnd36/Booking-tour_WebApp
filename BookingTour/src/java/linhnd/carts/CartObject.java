/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.carts;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import linhnd.daos.ToursDAO;
import linhnd.dtos.ToursDTO;

/**
 *
 * @author PC
 */
public class CartObject implements Serializable {

    private Map<String, TourInCartDTO> cart;

    public Map<String, TourInCartDTO> getCart() {
        return cart;
    }

    public void addTourToCart(String idTour, int restQuantity) throws SQLException, NamingException {
        if (idTour == null) {
            return;
        }
        if (idTour.isEmpty()) {
            return;
        }
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }
        ToursDAO dao = new ToursDAO();
        ToursDTO dtoTour = dao.getTour(idTour);
        int quatity = 1;
        if (this.cart.containsKey(idTour)) {
            quatity = this.cart.get(idTour).getQuantity() + 1;
        }
        TourInCartDTO dto = new TourInCartDTO(dtoTour, quatity, restQuantity);
        this.cart.put(idTour, dto);

    }

    public void removeTourFromCart(String idTour) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(idTour)) {
            this.cart.remove(idTour);
            if (this.cart.isEmpty()) {
                this.cart = null;
            }
        }
    }

    public void updateQuantity(String idTour, int quantity) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(idTour)) {
            this.cart.get(idTour).setQuantity(quantity);
        }
    }

}
