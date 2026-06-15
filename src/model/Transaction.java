package model;

import enums.TransactionStatus;
import enums.TransactionType;

public class Transaction {

    private int transactionId;

    private double amount;

    private TransactionType type;

    private TransactionStatus status;

    private Account fromAccount;

    private Account toAccount;

    public Transaction(
            int transactionId,
            double amount,
            TransactionType type,
            TransactionStatus status,
            Account fromAccount,
            Account toAccount) {

        this.transactionId = transactionId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    @Override
    public String toString() {

        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}