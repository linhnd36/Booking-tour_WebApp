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
public class ErrorInputTour implements Serializable {

    private String ErrorTourId, ErrorTourName, ErrorTourImage, ErrorTourDate, ErrorTourPlace;

    public void setErrorTourPlace(String ErrorTourPlace) {
        this.ErrorTourPlace = ErrorTourPlace;
    }

    public String getErrorTourPlace() {
        return ErrorTourPlace;
    }

    public void setErrorTourDate(String ErrorTourDate) {
        this.ErrorTourDate = ErrorTourDate;
    }

    public String getErrorTourDate() {
        return ErrorTourDate;
    }

    public ErrorInputTour() {
    }

    public String getErrorTourId() {
        return ErrorTourId;
    }

    public String getErrorTourName() {
        return ErrorTourName;
    }

    public String getErrorTourImage() {
        return ErrorTourImage;
    }

    public void setErrorTourId(String ErrorTourId) {
        this.ErrorTourId = ErrorTourId;
    }

    public void setErrorTourName(String ErrorTourName) {
        this.ErrorTourName = ErrorTourName;
    }

    public void setErrorTourImage(String ErrorTourImage) {
        this.ErrorTourImage = ErrorTourImage;
    }

}
