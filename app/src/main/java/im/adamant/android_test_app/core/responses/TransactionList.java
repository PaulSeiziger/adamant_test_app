package im.adamant.android_test_app.core.responses;


import java.util.List;

import im.adamant.android_test_app.core.entities.Transaction;
import im.adamant.android_test_app.core.entities.transaction_assets.TransactionAsset;

public class TransactionList<AT extends TransactionAsset> {
    private int nodeTimestamp;
    private boolean success;
    private List<Transaction<AT>> transactions;
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Transaction<AT>> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction<AT>> transactions) {
        this.transactions = transactions;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getNodeTimestamp() {
        return nodeTimestamp;
    }

    public void setNodeTimestamp(int nodeTimestamp) {
        this.nodeTimestamp = nodeTimestamp;
    }
}
