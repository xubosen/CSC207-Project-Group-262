import java.io.*;
import java.net.*;

public class CalendarificAPIAccessExample {
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception
    {
        // Code copied off stack overflow. Put the url you put on hoppscotch in the string below
        // Don't leave your api token on the string like I did!
        String urlOnHoppScotch = "https://calendarific.com/api/v2/holidays?api_key=kCEKqxdNSAyLKA1TmlqoEevXsAo0s6TS&country=ca&year=2023";
        System.out.println(getHTML(urlOnHoppScotch));
    }
}