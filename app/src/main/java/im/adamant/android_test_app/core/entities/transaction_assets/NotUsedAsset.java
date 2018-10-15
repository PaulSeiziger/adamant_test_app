package im.adamant.android_test_app.core.entities.transaction_assets;

public class NotUsedAsset implements TransactionAsset {
    @Override
    public byte[] getBytesDigest() {
        return new byte[0];
    }
}
