package im.adamant.android_test_app.core.requests;


import im.adamant.android_test_app.core.entities.Transaction;

public class ProcessTransaction {
    private Transaction<?> transaction;

    public ProcessTransaction(Transaction<?> transaction) {
        this.transaction = transaction;
    }

    public Transaction<?> getTransaction() {
        return transaction;
    }
}
