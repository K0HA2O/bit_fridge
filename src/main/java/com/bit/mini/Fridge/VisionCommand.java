package com.bit.mini.Fridge;

import com.bit.mini.dao.UserIngredientDao;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("VisionCommand")
public class VisionCommand {

    @Autowired
    private UserIngredientDao repository;

    @Autowired
    private TranslationCommand translationCommand;

    // Google Vision API로 객체 감지
    public List<String> detectObjects(MultipartFile file) throws Exception {
        ByteString imgBytes = ByteString.copyFrom(file.getBytes());
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feature)
                .setImage(img)
                .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            System.out.println("Starting Vision API call...");
            AnnotateImageResponse response = client.batchAnnotateImages(Collections.singletonList(request))
                                                    .getResponsesList().get(0);

            if (response.hasError()) {
                throw new Exception("Vision API Error: " + response.getError().getMessage());
            }

            List<String> detectedObjects = new ArrayList<>();
            for (EntityAnnotation annotation : response.getLabelAnnotationsList()) {
                detectedObjects.add(annotation.getDescription()); // 모든 결과를 추가
                System.out.println("Detected object: " + annotation.getDescription() + ", Confidence: " + annotation.getScore());
            }

            if (detectedObjects.isEmpty()) {
                detectedObjects.add("No objects detected");
            }

            // 번역 처리
            List<String> translatedObjects = translationCommand.translateToKorean(detectedObjects);
            return translatedObjects;
        }
        
        
    }
    public void saveIngredients(List<String> ingredientNames, List<Float> quantities, List<String> units, List<String> expirationDates, int userId) {
        for (int i = 0; i < ingredientNames.size(); i++) {
            try {
                System.out.println("Saving ingredient: " + ingredientNames.get(i));
                // userId를 추가하여 저장
                repository.save(ingredientNames.get(i), quantities.get(i), units.get(i), expirationDates.get(i), userId);
                System.out.println("Saved successfully: " + ingredientNames.get(i));
            } catch (Exception e) {
                System.out.println("Error saving ingredient: " + ingredientNames.get(i) + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
