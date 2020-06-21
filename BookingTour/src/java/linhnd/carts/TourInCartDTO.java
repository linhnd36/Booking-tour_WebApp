/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.carts;

import java.io.Serializable;
import linhnd.dtos.ToursDTO;

/**
 *
 * @author PC
 */
public class TourInCartDTO implements Serializable {

    private ToursDTO TourDto;
    private int Quantity;
    private int RestQuantity;

    public void setRestQuantity(int RestQuantity) {
        this.RestQuantity = RestQuantity;
    }

    public int getRestQuantity() {
        return RestQuantity;
    }

    public TourInCartDTO(ToursDTO TourDto, int Quantity, int RestQuantity) {
        this.TourDto = TourDto;
        this.Quantity = Quantity;
        this.RestQuantity = RestQuantity;
    }

    public ToursDTO getTourDto() {
        return TourDto;
    }

    public void setTourDto(ToursDTO TourDto) {
        this.TourDto = TourDto;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

}
