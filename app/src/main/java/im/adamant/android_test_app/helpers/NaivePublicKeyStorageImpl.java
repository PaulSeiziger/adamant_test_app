package im.adamant.android_test_app.helpers;

import java.util.HashMap;

import im.adamant.android_test_app.core.AdamantApiWrapper;
import im.adamant.android_test_app.core.responses.PublicKeyResponse;

public class NaivePublicKeyStorageImpl implements PublicKeyStorage {
    private HashMap<String, String> publicKeys = new HashMap<>();
    private AdamantApiWrapper api;

    public NaivePublicKeyStorageImpl(AdamantApiWrapper api) {
        this.api = api;
    }

    @Override
    public String getPublicKey(String address) {
        if (!publicKeys.containsKey(address)){
            try {
                PublicKeyResponse response = api.getPublicKey(address).blockingFirst();
                if (response.isSuccess()){
                    publicKeys.put(address, response.getPublicKey());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return publicKeys.containsKey(address) ? publicKeys.get(address) : "";
    }
}
