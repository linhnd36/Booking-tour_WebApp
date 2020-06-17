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
public class DiscountCodeDTO implements Serializable {

    private String DiscountId, Name, ImagesLink, ExpiryDate;
    private int PercentDis;

    public DiscountCodeDTO() {
    }

    public String getDiscountId() {
        return DiscountId;
    }

    public String getName() {
        return Name;
    }

    public String getImagesLink() {
        return ImagesLink;
    }

    public void setImagesLink(String ImagesLink) {
        this.ImagesLink = ImagesLink;
    }



    public String getExpiryDate() {
        return ExpiryDate;
    }

    public int getPercentDis() {
        return PercentDis;
    }

    public void setDiscountId(String DiscountId) {
        this.DiscountId = DiscountId;
    }

    public void setName(String Name) {
        this.Name = Name;
    }


    public void setExpiryDate(String ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

    public void setPercentDis(int PercentDis) {
        this.PercentDis = PercentDis;
    }

}
