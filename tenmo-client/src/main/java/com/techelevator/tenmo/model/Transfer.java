package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transfer {

    private Long transfer_id;
    private Long account_from;
    private Long account_to;
    private double amount;
    @JsonProperty("transfer_status_id")
    private Long transferStatus;
    @JsonProperty("transfer_type_id")
    private Long transferType;
    private String senderUsername;
    private String receiverUsername;

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public Long getTransfer_id() {
        return transfer_id;
    }

    public Long getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Long transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Long getTransferType() {
        return transferType;
    }

    public void setTransferType(Long transferType) {
        this.transferType = transferType;
    }

    public void setTransfer_id(Long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public Long getAccount_from() {
        return account_from;
    }

    public void setAccount_from(Long account_from) {
        this.account_from = account_from;
    }

    public Long getAccount_to() {
        return account_to;
    }

    public void setAccount_to(Long account_to) {
        this.account_to = account_to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
