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
import java.util.Scanner;

public class App {

    private static final String POST_URL = "http://localhost:8080/implementation-1.0-SNAPSHOT/rest/ticket";
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {

        String login = null;
        String street = null;
        String parkometer = null;
        String ordinalNumber = null;
        String duration = null;

        System.out.println("Podaj swój login: ");
        while(login == null) login = scanner.nextLine();
        System.out.println("Podaj ulicę (np. Akacjowa,Aptekarska,Bińczycka,Bosaków): ");
        while(street == null) street = scanner.nextLine();
        System.out.println("Podaj numer parkometru na ulicy (np. 1,2): ");
        while(parkometer == null) parkometer = scanner.nextLine();
        System.out.println("Podaj numer miejsca (np. 1,2): ");
        while(ordinalNumber == null) ordinalNumber = scanner.nextLine();
        System.out.println("Na ile sekund chcesz wykupić miejsce?: ");
        while(duration == null) duration = scanner.nextLine();

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
