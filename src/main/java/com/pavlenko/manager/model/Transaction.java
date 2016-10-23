package com.pavlenko.manager.model;

/**
 * Transaction plain java object
 *
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
public class Transaction {
    private double amount;
    private String type;
    private long parentId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

}
