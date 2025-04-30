package com.bit.mini.MyRecipeCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.UserIngredientDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

@Component("RecipeInfoCommand")
public class RecipeInfoCommand implements Command {
    
    private static final String API_KEY = "b98fdbc0c58743ebad6c2035b9ce612f";
    private static final String BASE_URL = "https://api.spoonacular.com/recipes";

    @Inject
    private UserIngredientDao userIngredientDao;  // 냉장고 재료 DAO 추가

    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");

        // Google Translate API 클라이언트 생성
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // 사용자 ID 가져오기
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return;
        }

        // 레시피 ID 가져오기
        int recipeId = Integer.parseInt(request.getParameter("id"));
        
        try {
            // API에서 레시피 데이터 가져오기
            String recipeDetails = fetchRecipeDetails(recipeId);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> recipeMap = objectMapper.readValue(recipeDetails, Map.class);
            System.out.println("recipeMap : " + recipeMap);
            
            
            // 타이틀 번역
            String originalTitle = (String) recipeMap.get("title");
            String translatedTitle = translateText(translate, originalTitle, "en", "ko");

            // 레시피 재료 가져오기
            List<Map<String, Object>> ingredients = (List<Map<String, Object>>) recipeMap.get("extendedIngredients");

            // 냉장고 재료 가져오기
            List<String> fridgeIngredients = userIngredientDao.getUserFridgeIngredients(userId);

            // ✅ 비교 과정 로그 출력
            System.out.println("🧊 냉장고 재료 목록: " + fridgeIngredients);

            // 부족한 재료 찾기
            List<Map<String, Object>> missingIngredients = new ArrayList<>();

            for (Map<String, Object> ingredient : ingredients) {
                String originalOriginal = (String) ingredient.get("original");

                // ✅ 레시피 재료 한글 번역
                String translatedOriginal = translateText(translate, originalOriginal, "en", "ko");
                ingredient.put("translatedOriginal", translatedOriginal);

                // ✅ 레시피 재료명 가져오기 (소문자 변환 및 공백 제거)
                String ingredientName = translatedOriginal
                        .toLowerCase()
                        .replaceAll("\\s+", "") // 공백 제거
                        .replaceAll("[^가-힣a-zA-Z]", ""); // 특수문자 제거

                System.out.println("🔍 비교할 레시피 재료: " + ingredientName);

                boolean isInFridge = false;

                // ✅ 냉장고 재료 비교
                for (String fridgeIngredient : fridgeIngredients) {
                    String normalizedFridgeIngredient = fridgeIngredient
                            .toLowerCase()
                            .replaceAll("\\s+", "") // 공백 제거
                            .replaceAll("[^가-힣a-zA-Z]", ""); // 특수문자 제거

                    System.out.println("🧊 냉장고 재료 비교: " + normalizedFridgeIngredient);

                    // **부분 문자열 비교 추가** (양방향 포함 여부 체크)
                    if (normalizedFridgeIngredient.contains(ingredientName) || ingredientName.contains(normalizedFridgeIngredient)) {
                        isInFridge = true;
                        System.out.println("✅ 일치하는 재료 발견: " + fridgeIngredient);
                        break;
                    }
                }

                // 부족한 재료 목록에 추가
                if (!isInFridge) {
                    System.out.println("❌ 부족한 재료 추가: " + ingredientName);
                    missingIngredients.add(ingredient);
                }
            }

            // ✅ 최종 부족한 재료 목록 출력
            System.out.println("🚨 최종 부족한 재료 목록: " + missingIngredients);


            

            // 조리법 번역
            String originalInstructions = (String) recipeMap.get("instructions");
            String translatedInstructions = originalInstructions != null 
                ? translateText(translate, originalInstructions, "en", "ko") 
                : "조리법 정보 없음";

            // 데이터 저장
            model.addAttribute("id", recipeId);
            model.addAttribute("title", translatedTitle);
            model.addAttribute("image", recipeMap.get("image"));
            model.addAttribute("servings", recipeMap.get("servings"));
            model.addAttribute("ingredients", ingredients); // 전체 재료
            model.addAttribute("missingIngredients", missingIngredients); // 부족한 재료
            model.addAttribute("instructions", translatedInstructions);
            model.addAttribute("spoonacularSourceUrl", recipeMap.get("spoonacularSourceUrl"));

        } catch (Exception e) {
            System.err.println("Error fetching or processing recipe details: " + e.getMessage());
        }
    }


    public static String fetchRecipeDetails(int recipeId) throws Exception {
        String url = String.format("%s/%d/information?apiKey=%s", BASE_URL, recipeId, API_KEY);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(reader);
                    return jsonNode.toPrettyString();
                } else {
                    throw new RuntimeException("Failed to fetch data: " + statusCode);
                }
            }
        }
    }

    private String translateText(Translate translate, String text, String sourceLang, String targetLang) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        com.google.cloud.translate.Translation translation = translate.translate(
            text,
            Translate.TranslateOption.sourceLanguage(sourceLang),
            Translate.TranslateOption.targetLanguage(targetLang)
        );
        return translation.getTranslatedText();
    }
}
