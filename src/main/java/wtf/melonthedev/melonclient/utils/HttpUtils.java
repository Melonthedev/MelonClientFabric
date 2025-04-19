package wtf.melonthedev.melonclient.utils;

import wtf.melonthedev.melonclient.settings.ClientSettings;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpUtils {

    public static void executePost(String targetURL) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetURL))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void executePostAsync(String targetURL) {
        if (ClientSettings.enableDeveloperMode)
            System.out.println("POST ASYNC (" + targetURL + ")");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetURL))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static String executeGet(String targetURL) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetURL))
                    .GET()
                    .build();
            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            return response.get().body();
        } catch (InterruptedException | ExecutionException ignored) {}
        return null;
    }

    public static void executeGetAsync(String targetURL, ResponseCallback callback) {
        if (ClientSettings.enableDeveloperMode)
            System.out.println("GET ASYNC (" + targetURL + ")");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetURL))
                .GET()
                .build();
        CompletableFuture.runAsync(() -> {
            try {
                CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                callback.onResponse(response.get().body());
            } catch (InterruptedException | ExecutionException ignored) {}
        });
    }

    public interface ResponseCallback {
        void onResponse(String response);
    }
}
