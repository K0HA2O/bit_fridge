package com.bit.mini.Controller;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpoonacularAPIExample {
    private static final String API_KEY = "162ca19b79d04f76bc40ccb824ccad0e";
    private static final String BASE_URL = "https://api.spoonacular.com";

    public static void main(String[] args) {

        try {
            String query = "pasta";
            String ingredients = "tomato,cheese,basil";
            String excludeIngredients = "eggs";
            String encodedExcludeIngredients = URLEncoder.encode(excludeIngredients, StandardCharsets.UTF_8);
            String url = BASE_URL + "/recipes/complexSearch?includeIngredients=" + ingredients + "&excludeIngredients="
            		+ excludeIngredients + "&apiKey=" + API_KEY;
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // JSON 응답 데이터 파싱 (Response 클래스 사용)
            ObjectMapper objectMapper = new ObjectMapper();
            Response apiResponse = objectMapper.readValue(response.body(), Response.class);

            // 결과 출력
            System.out.println("Recipes:");
            for (Recipe recipe : apiResponse.getResults()) {
                System.out.println("- " + recipe.getTitle());
                System.out.println("-- " + recipe.getMissedIngredients());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Response 클래스: "results" 필드가 Recipe[] 배열로 포함됨
class Response {
    private List<Recipe> results;

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}

// Recipe 클래스
class Recipe {
    private int id;
    private String title;
    private String image;
    private String imageType;
    private int usedIngredientCount;  // 추가된 필드
    private int missedIngredientCount; // 추가된 필드
    private List<Ingredient> usedIngredients;  // usedIngredients 필드 추가
    private List<Ingredient> missedIngredients;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
    
    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public void setUsedIngredientCount(int usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public void setMissedIngredientCount(int missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }

	public List<Ingredient> getUsedIngredients() {
		return usedIngredients;
	}

	public void setUsedIngredients(List<Ingredient> usedIngredients) {
		this.usedIngredients = usedIngredients;
	}

	public List<Ingredient> getMissedIngredients() {
		return missedIngredients;
	}

	public void setMissedIngredients(List<Ingredient> missedIngredients) {
		this.missedIngredients = missedIngredients;
	}

    
}

// Ingredient 클래스
class Ingredient {
    private int id;
    private double amount;
    private String unit;
    private String aisle;
    private String name;
    private String original;
    private String originalName;
    private String image;
    private String unitLong;
    private String unitShort;
    private List<String> meta;
    
    public List<String> getMeta() {
		return meta;
	}

	public void setMeta(List<String> meta) {
		this.meta = meta;
	}

	public String getUnitShort() {
		return unitShort;
	}

	public void setUnitShort(String unitShort) {
		this.unitShort = unitShort;
	}

	// Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

	public String getUnitLong() {
		return unitLong;
	}

	public void setUnitLong(String unitLong) {
		this.unitLong = unitLong;
	}
    
}
