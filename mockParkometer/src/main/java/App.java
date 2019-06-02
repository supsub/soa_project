import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final String POST_URL = "http://localhost:8080/implementation-1.0-SNAPSHOT/rest/ticket";
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {


        System.out.println("Podaj swój login: ");
        String login = scanner.nextLine();
        System.out.println("Podaj ulicę (np. Akacjowa,Aptekarska,Bińczycka,Bosaków): ");
        String street = scanner.nextLine();
        System.out.println("Podaj numer parkometru (np. 1,2): ");
        String parkometer = scanner.nextLine();
        System.out.println("Podaj numer miejsca (np. 1,2): ");
        String ordinalNumber = scanner.nextLine();
        System.out.println("Na ile minut chcesz wykupić miejsce?: ");
        String duration = scanner.nextLine();

        JSONObject json = new JSONObject();
        json.put("street", street);
        json.put("parkometerOrd", parkometer);
        json.put("ordinalNumber", ordinalNumber);
        json.put("owner", login);
        json.put("duration", duration);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(POST_URL);
            StringEntity params = new StringEntity(json.toString(),"UTF-8");
            request.addHeader("content-type", "application/json;charset=utf-8");
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            InputStream content = response.getEntity().getContent();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(content));
                String line = null;

                while ((line = br.readLine()) != null) {
                    if (line.equalsIgnoreCase("quit")) {
                        break;
                    }
                    System.out.println(line);
                }
            } catch (IOException ioe) {
                System.out.println("Exception while reading input " + ioe);
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException ioe) {
                    System.out.println("Error while closing stream: " + ioe);
                }
            }
// handle response here...
        } catch (Exception ex) {
            // handle exception here
        } finally {
            httpClient.close();
        }
    }


}
