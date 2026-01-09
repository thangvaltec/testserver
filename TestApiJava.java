import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

/**
 * Simple Java API Test - Based on test.py
 * Single test case with policeId = "null"
 * 
 * Compile: javac -encoding UTF-8 TestApiJava.java
 * Run: java TestApiJava
 */
public class TestApiJava {

    // 1. Server URL settings
    private static final String API_URL = "https://mot-recog.facet-cloud.com/recv";
    private static final String DEVICE_ID = "2222222";
    private static final String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

    public static void main(String[] args) {
        // Set console to UTF-8 for proper Japanese character display
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // If UTF-8 not supported, continue with default
        }

        // 2. Image file path
        String imagePath = "tan.jpg";

        // Check if file exists
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.out.println("Error: '" + imagePath + "' not found.");
            return;
        }

        // 3. Get timestamp (Unix milliseconds)
        String timestamp = String.valueOf(System.currentTimeMillis());

        // 4. Prepare request data
        String policeId = "null";

        try {
            System.out.println("Request");
            System.out.println(
                    "Sending data: timestamp=" + timestamp + ", deviceId=" + DEVICE_ID + ", policeId=" + policeId);

            // Send request
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            try (OutputStream outputStream = connection.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true)) {

                // Add timestamp field
                writer.append("--" + BOUNDARY + "\r\n");
                writer.append("Content-Disposition: form-data; name=\"timestamp\"\r\n\r\n");
                writer.append(timestamp + "\r\n");
                writer.flush();

                // Add deviceId field
                writer.append("--" + BOUNDARY + "\r\n");
                writer.append("Content-Disposition: form-data; name=\"deviceId\"\r\n\r\n");
                writer.append(DEVICE_ID + "\r\n");
                writer.flush();

                // Add policeId field
                writer.append("--" + BOUNDARY + "\r\n");
                writer.append("Content-Disposition: form-data; name=\"policeId\"\r\n\r\n");
                writer.append(policeId + "\r\n");
                writer.flush();

                // Add image file
                writer.append("--" + BOUNDARY + "\r\n");
                writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"image.jpg\"\r\n");
                writer.append("Content-Type: image/jpeg\r\n\r\n");
                writer.flush();

                // Write image bytes
                Files.copy(imageFile.toPath(), outputStream);
                outputStream.flush();

                writer.append("\r\n");
                writer.flush();

                // End of multipart
                writer.append("--" + BOUNDARY + "--\r\n");
                writer.flush();
            }

            // Get response
            int responseCode = connection.getResponseCode();
            String responseBody;

            if (responseCode >= 200 && responseCode < 300) {
                responseBody = readStream(connection.getInputStream());
            } else {
                InputStream errorStream = connection.getErrorStream();
                responseBody = errorStream != null ? readStream(errorStream) : "No response body";
            }

            // Display result
            System.out.println("Response");
            System.out.println("status_code: " + responseCode);
            System.out.println("response result: " + responseBody);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
