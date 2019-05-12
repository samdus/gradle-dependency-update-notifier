package org.muehlbachler.gradle.plugin.dependencyupdatenotifier;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class BaseClient {
    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client;
    Request.Builder requestBuilder;

    protected BaseClient() {
        client = new OkHttpClient();
        requestBuilder = new Request.Builder()
                .addHeader("Content-Type", "application/json");
    }
}
