package com.bit.mini.Fridge;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class VoiceRecognitionCommand {

    private final String clientId = "bnoai1se2x"; // 네이버에서 발급받은 Client ID
    private final String clientSecret = "RYn8ptSOlk4egCpB1mKHOFnuvvUTimC6WrlplCpa"; // 네이버에서 발급받은 Client Secret

    public String recognizeVoice(MultipartFile file) {
        String result = "";
        String language = "Kor"; // 언어 코드 (Kor: 한국어)
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            conn.setRequestMethod("POST");

            // 파일 업로드
            try (OutputStream os = conn.getOutputStream();
                 InputStream fis = file.getInputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            }

            // 응답 처리
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 성공
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    result = jsonResponse.getString("text"); // 인식된 텍스트
                }
            } else { // 실패
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.err.println(line);
                    }
                }
                throw new IOException("Naver API Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error processing the audio file: " + e.getMessage();
        }

        return result;
    }
}