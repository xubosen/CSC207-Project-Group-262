package apiDocuments;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EmailVerification {

    private static final String API_KEY = "d9db538de48c4a14bf95c6cfa7a45824"; // Always keep such information private
    private static final String EMAIL = "yoohamj@gmail.com";
    private static final String BASE_URL = "https://emailvalidation.abstractapi.com/v1/";

    public static void main(String[] args) {
        try {
            String apiUrl = BASE_URL + "?api_key=" + API_KEY + "&email=" + EMAIL;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method and properties
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response
                System.out.println(response.toString());
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
