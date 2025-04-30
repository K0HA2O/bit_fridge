package com.bit.mini.MyRecipeCommand;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.MyRecipeDao;
import com.bit.mini.dto.FridgeDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Component("myRecipeCommand")
public class MyRecipeCommand implements Command {

   @Autowired
   private SqlSession sqlSession;

   @Override
   public void execute(Model model) {

      Map<String, Object> map = model.asMap();
      HttpServletRequest request = (HttpServletRequest) map.get("request");
      HttpSession session = request.getSession();
      MyRecipeDao dao = sqlSession.getMapper(MyRecipeDao.class);

      int user_id = (Integer) session.getAttribute("userId");
      float blood_sugar_level = dao.getBloodSugarLevel(user_id);

      // 냉장고 재료 가져오기
      List<FridgeDto> ingredients = dao.getIngredients(user_id);
      StringBuilder ingredientsString = new StringBuilder();

      for (int i = 0; i < ingredients.size(); i++) {
         FridgeDto fridgeDto = ingredients.get(i);
         ingredientsString.append(fridgeDto.getIngredient_name());

         if (i < ingredients.size() - 1) {
            ingredientsString.append(",");
         }
      }
      String ingredientsStr = ingredientsString.toString();

      String tApiKey = "AIzaSyBRix4iLknVID2On0nCVnBqZbCnwh0ZKcg";
      Translate translate = TranslateOptions.newBuilder().setApiKey(tApiKey).build().getService();

      Translation translation = translate.translate(ingredientsStr, Translate.TranslateOption.sourceLanguage("ko"),
            Translate.TranslateOption.targetLanguage("en"));
      
      // 재료 번역 결과
      String translatedIngredients = translation.getTranslatedText();
      String result = translatedIngredients.replaceAll("\\s*,\\s*", ",")
                .replaceAll("\\s+", "");

      // 유저 알레르기 정보 가져오기
      String allergiesJson = dao.getAllergies(user_id); // Json
      String allergiesStr = null;
      
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         String[] allergiesArray = objectMapper.readValue(allergiesJson, String[].class);
         
         allergiesStr = String.join(",", allergiesArray);
      } catch (Exception e) {
         e.getMessage();
      }
      
      System.out.println("includeIngredients : " + result);
      System.out.println("intolerances : " + allergiesStr);
      
      String apiUrl = "";
      String apiKey = "d67472268eda49f4a6234d6845979f3a";
      String baseUrl = "https://api.spoonacular.com/recipes";

      if (blood_sugar_level >= 100) {
         // 고혈당 상태 : 저탄수화물, 저당분 레시피
         System.out.println("고혈당임당");
         apiUrl = baseUrl + "/complexSearch?maxCarbs=20&maxSugar=5&number=8"
               + "&includeIngredients=" + result + "&intolerances=" + allergiesStr + "&sort=random&apiKey=" + apiKey;
      } else if (blood_sugar_level >= 70 && blood_sugar_level < 100) {
         // 정상 상태 : 균형 잡힌 식단 추천
         System.out.println("혈당지수 정상");
         apiUrl = baseUrl + "/complexSearch?minProtein=10&maxFat=20&maxSugar=10&number=8"
               + "&includeIngredients=" + result + "&intolerances=" + allergiesStr + "&sort=random&apiKey=" + apiKey;
      } else {
         // 저혈당 상태 : 고탄수화물 또는 당분 포함
         System.out.println("저혈당임당");
         apiUrl = baseUrl + "/complexSearch?minCarbs=30&minSugar=15&number=8" 
               + "&includeIngredients=" + result + "&intolerances=" + allergiesStr + "&sort=random&apiKey=" + apiKey;
      }

      try {
         HttpClient client = HttpClient.newHttpClient();
         HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl)).GET().build();

         HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
         
//         // Api 응답 출력
//         System.out.println("API Response: " + response.body());
         
         // JSON 응답 데이터 파싱
         ObjectMapper objectMapper = new ObjectMapper();
         Response apiResponse = objectMapper.readValue(response.body(), Response.class);
         
         // 추천 레시피가 없을 경우
          List<Recipe> recommendedRecipes = apiResponse.getResults();
          if (recommendedRecipes == null || recommendedRecipes.isEmpty()) {
              System.out.println("추천 레시피가 없습니다.");
          } else {
              // Step 1: 포함된 재료로 찾은 레시피가 8개 이하인 경우 추가 요청
              if (recommendedRecipes.size() < 8) {
                  // 부족한 개수를 채우기 위한 API 호출
                  String apiUrlWithoutIngredients = baseUrl + "/complexSearch?number=" + (8 - recommendedRecipes.size()) 
                          + "&intolerances=" + allergiesStr + "&sort=random&apiKey=" + apiKey;
                  HttpRequest requestWithoutIngredients = HttpRequest.newBuilder().uri(URI.create(apiUrlWithoutIngredients)).GET().build();
                  HttpResponse<String> responseWithoutIngredients = client.send(requestWithoutIngredients, HttpResponse.BodyHandlers.ofString());

                  Response apiResponseWithoutIngredients = objectMapper.readValue(responseWithoutIngredients.body(), Response.class);
                  recommendedRecipes.addAll(apiResponseWithoutIngredients.getResults());
              }
              
              model.addAttribute("recommendedRecipes", recommendedRecipes);
          }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class Response {
      private List<Recipe> results;

      public List<Recipe> getResults() {
         return results;
      }

      public void setResults(List<Recipe> results) {
         this.results = results;
      }
   }

   // Recipe 클래스
   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class Recipe {
      private int id;
      private String title;
      private String image;

      // Getters and setters for fields
      public int getId() {
    	 System.out.println("레시피 id : " + id);
         return id;
      }

      public void setId(int id) {
         this.id = id;
      }

      public String getTitle() {
         return title;
      }

      public void setTitle(String title) {
         // Google Translate API를 사용해 title을 한글로 번역
           String apiKey = "AIzaSyBRix4iLknVID2On0nCVnBqZbCnwh0ZKcg"; // Google Cloud Translate API 키를 설정
           Translate translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();

           Translation translation = translate.translate(
               title,
               Translate.TranslateOption.sourceLanguage("en"),
               Translate.TranslateOption.targetLanguage("ko")
           );

           // 번역된 제목으로 설정
           this.title = translation.getTranslatedText();
      }

      public String getImage() {
         return image;
      }

      public void setImage(String image) {
         this.image = image;
      }
   }
}
