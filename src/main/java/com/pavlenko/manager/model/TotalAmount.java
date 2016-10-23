package com.pavlenko.manager.model;

/**
 * Total amount of all child transactions plain java object
 *
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
public class TotalAmount {

    private double sum;

    public TotalAmount() {

    }

    public TotalAmount(double initialSum) {
        sum = initialSum;
    }


    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
