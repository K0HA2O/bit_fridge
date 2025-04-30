package com.bit.mini.Fridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.documentai.v1.Document;
import com.google.cloud.documentai.v1.DocumentProcessorServiceClient;
import com.google.cloud.documentai.v1.ProcessRequest;
import com.google.cloud.documentai.v1.ProcessResponse;
import com.google.cloud.documentai.v1.RawDocument;
import com.google.protobuf.ByteString;

@Service
public class ReceiptCommand {

    // 영수증 인식 처리
    public List<Map<String, String>> detectReceipt(MultipartFile file) throws Exception {
        try (DocumentProcessorServiceClient client = DocumentProcessorServiceClient.create()) {
            ByteString fileBytes = ByteString.copyFrom(file.getBytes());

            // RawDocument 입력 구성
            RawDocument input = RawDocument.newBuilder()
                    .setContent(fileBytes)
                    .setMimeType(file.getContentType()) // MIME 타입 설정
                    .build();

            // 프로세서 경로 설정
            String processorName = "projects/foodimage-447806/locations/us/processors/a46baea99429e453";
            // ProcessRequest 생성
            ProcessRequest request = ProcessRequest.newBuilder()
                    .setName(processorName)
                    .setRawDocument(input)
                    .build();

            // 문서 처리 요청
            ProcessResponse response = client.processDocument(request);
            Document document = response.getDocument();

            // Document AI 결과에서 엔티티 추출
            List<Document.Entity> entities = document.getEntitiesList();

            // 추출한 데이터를 파싱하여 항목명과 수량 분리
            return parseReceiptItems(entities);
        }
    }

    // 데이터 파싱 메서드 (항목명과 수량)
    private List<Map<String, String>> parseReceiptItems(List<Document.Entity> entities) {
        List<Map<String, String>> parsedItems = new ArrayList<>();

        for (Document.Entity entity : entities) {
            String text = entity.getMentionText();
            System.out.println("Raw Text: " + text);

            // 정규표현식으로 이름과 수량 분리
            String pattern = "^(.*?)(\\d+(\\.\\d+)?)(\\s?(개|병|팩|kg|g|L|ml)?)?$"; // 수량과 단위 추출
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(text);

            if (matcher.find()) {
                String name = matcher.group(1).trim(); // 항목명
                String quantity = matcher.group(2).trim(); // 수량
                String unit = matcher.group(5) != null ? matcher.group(5).trim() : "개"; // 단위 (없으면 기본값 "개")

                Map<String, String> item = new HashMap<>();
                item.put("name", name);
                item.put("quantity", quantity);
                item.put("unit", unit);

                parsedItems.add(item);
            } else {
                // 수량이 없는 항목은 이름만 저장
                Map<String, String> item = new HashMap<>();
                item.put("name", text.trim());
                item.put("quantity", "1"); // 기본 수량 1
                item.put("unit", "개"); // 기본 단위 "개"
                parsedItems.add(item);
            }
        }

        return parsedItems;
    }
}
