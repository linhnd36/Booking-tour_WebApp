/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.dtos;

import java.io.Serializable;

/**
 *
 * @author PC
 */
public class ToursDTO implements Serializable {

    private String TourID, TourName, FromDate, ToDate, Price, Place, Quota, ImageLink;

    public ToursDTO() {
    }

    public String getTourID() {
        return TourID;
    }

    public String getTourName() {
        return TourName;
    }

    public String getFromDate() {
        return FromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public String getPrice() {
        return Price;
    }

    public String getPlace() {
        return Place;
    }

    public String getQuota() {
        return Quota;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setTourID(String TourID) {
        this.TourID = TourID;
    }

    public void setTourName(String TourName) {
        this.TourName = TourName;
    }

    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public void setPlace(String Place) {
        this.Place = Place;
    }

    public void setQuota(String Quota) {
        this.Quota = Quota;
    }

    public void setImageLink(String ImageLink) {
        this.ImageLink = ImageLink;
    }

}
