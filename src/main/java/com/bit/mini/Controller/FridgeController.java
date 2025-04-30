package com.bit.mini.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bit.mini.Fridge.ReceiptCommand;
import com.bit.mini.Fridge.VisionCommand;
import com.bit.mini.Fridge.VoiceRecognitionCommand;
import com.bit.mini.dao.UserIngredientDao;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/fridge")
public class FridgeController {

	private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/search/image";
	private static final String REST_API_KEY = "f2409f10d2f3676fab3e7122f1faa2d8";

	@Autowired
	private VisionCommand visionCommand;

	@Autowired
	private ReceiptCommand receiptCommand;

	@Autowired
	private VoiceRecognitionCommand voiceRecognitionCommand;

	@Autowired
	private UserIngredientDao userIngredientDao;

	// 업로드 페이지
	@RequestMapping(value = "/addition", method = RequestMethod.GET)
	public String showUploadForm() {
		return "Fridge/addition"; // /WEB-INF/views/Fridge/addition.jsp
	}
	
    @RequestMapping(value = "/loading", method = RequestMethod.GET)
    public String showLoadingPage() {
        return "Fridge/loading"; // 📌 views/Fridge/loading.jsp 로 연결
    }
    
	@RequestMapping(value = "/fridge", method = RequestMethod.GET)
	public String showFridgeMainPage(HttpSession session, Model model) {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				model.addAttribute("error", "로그인이 필요합니다.");
				return "redirect:/home";
			}

			// 사용자 ID에 따른 재료 목록 가져오기
			List<Map<String, Object>> ingredients = userIngredientDao.findByUserId(userId);
			LocalDate today = LocalDate.now();

			for (Map<String, Object> ingredient : ingredients) {
				// 유효기간 문자열로 변환
				Object expirationDateObj = ingredient.get("expiration_date");
				if (expirationDateObj instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) expirationDateObj;
					ingredient.put("expiration_date", sqlDate.toString()); // 문자열로 변환
				}

				// 남은 날짜 계산
				if (ingredient.get("expiration_date") != null) {
					LocalDate expirationDate = LocalDate.parse(ingredient.get("expiration_date").toString());
					long daysLeft = ChronoUnit.DAYS.between(today, expirationDate);
					ingredient.put("daysLeft", daysLeft);
				} else {
					ingredient.put("daysLeft", Long.MAX_VALUE); // 유효기간 없음
				}
				// 이미지 URL 가져오기
				String ingredientName = (String) ingredient.get("ingredient_name");
				String imageUrl = fetchImageFromKakao(ingredientName);
				ingredient.put("image_url", imageUrl); // 이미지 URL 추가
			}

			model.addAttribute("ingredients", ingredients);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "재료를 불러오는 데 문제가 발생했습니다: " + e.getMessage());
		}
		return "Fridge/fridge";
	}

	private String fetchImageFromKakao(String query) {
		RestTemplate restTemplate = new RestTemplate();

		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + REST_API_KEY);

		// 요청 파라미터 설정
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// 요청 실행
		String url = KAKAO_API_URL + "?query=" + query + "&size=1";

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		// JSON 파싱
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> responseBody = mapper.readValue(response.getBody(), Map.class);
			List<Map<String, Object>> documents = (List<Map<String, Object>>) responseBody.get("documents");
			if (documents != null && !documents.isEmpty()) {
				return (String) documents.get(0).get("thumbnail_url");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 파일 업로드 및 처리
	@RequestMapping(value = "/addition", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type,
			RedirectAttributes redirectAttributes) {
		try {
			switch (type.toLowerCase()) {
			case "object":
				return handleObjectUpload(file, redirectAttributes);
			case "receipt":
				return handleReceiptUpload(file, redirectAttributes);
			case "voice":
				return handleVoiceUpload(file, redirectAttributes);
			default:
				redirectAttributes.addFlashAttribute("error", "잘못된 유형이 지정되었습니다.");
				return "redirect:/fridge/addition";
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "파일 처리 중 오류가 발생했습니다: " + e.getMessage());
			return "redirect:/fridge/addition";
		}
	}

	private String handleObjectUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		List<String> detectedObjects = visionCommand.detectObjects(file);
		redirectAttributes.addFlashAttribute("detectedObjects", detectedObjects);
		return "redirect:/fridge/inputForm";
	}

	private String handleReceiptUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		List<Map<String, String>> receiptItems = receiptCommand.detectReceipt(file);
		redirectAttributes.addFlashAttribute("receiptItems", receiptItems);
		return "redirect:/fridge/inputForm";
	}

	private String handleVoiceUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		String transcript = voiceRecognitionCommand.recognizeVoice(file);

		List<Map<String, String>> parsedIngredients = parseVoiceTranscript(transcript);
		redirectAttributes.addFlashAttribute("parsedIngredients", parsedIngredients);

		return "redirect:/fridge/inputForm";
	}

	private List<Map<String, String>> parseVoiceTranscript(String transcript) {
		List<Map<String, String>> ingredients = new ArrayList<>();
		String[] items = transcript.split(",");
		for (String item : items) {
			Map<String, String> ingredient = new HashMap<>();
			String[] parts = item.trim().split(" ");
			if (parts.length >= 2) {
				ingredient.put("name", parts[0]);
				String quantityUnit = parts[1];
				String quantity = quantityUnit.replaceAll("[^0-9.]", "");
				String unit = quantityUnit.replaceAll("[0-9.]", "");
				ingredient.put("quantity", quantity.isEmpty() ? "1" : quantity);
				ingredient.put("unit", unit.isEmpty() ? "개" : unit);
			} else {
				ingredient.put("name", item.trim());
				ingredient.put("quantity", "1");
				ingredient.put("unit", "개");
			}
			ingredients.add(ingredient);
		}
		return ingredients;
	}
    @RequestMapping(value = "/updateIngredients", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateIngredients(@RequestBody String jsonString, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Integer userId = (Integer) session.getAttribute("userId");

        // ✅ 세션 확인
        if (userId == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        // ✅ JSON 수동 파싱 (스프링 3.0에서는 ObjectMapper 사용)
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestData;

        try {
            requestData = objectMapper.readValue(jsonString, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "JSON 파싱 오류 발생");
            return response;
        }

        List<Map<String, Object>> ingredients = (List<Map<String, Object>>) requestData.get("ingredients");

        // ✅ 냉장고 재료 가져오기
        List<String> fridgeIngredients = userIngredientDao.getUserFridgeIngredients(userId);

        boolean hasDeducted = false;

        for (Map<String, Object> ingredient : ingredients) {
            String name = (String) ingredient.get("name");
            float quantity = ((Number) ingredient.get("quantity")).floatValue();

            // ✅ 냉장고에 해당 재료가 있는지 확인
            for (String fridgeIngredient : fridgeIngredients) {
                if (fridgeIngredient.contains(name) || name.contains(fridgeIngredient)) {
                    // ✅ 재료 차감
                    boolean success = userIngredientDao.deductIngredient(userId, fridgeIngredient, quantity);

                    if (success) {
                        hasDeducted = true;
                    }
                    break;
                }
            }
        }

        if (!hasDeducted) {
            response.put("success", false);
            response.put("message", "냉장고에 차감할 수 있는 재료가 없습니다.");
            return response;
        }

        response.put("success", true);
        response.put("message", "재료가 성공적으로 차감되었습니다.");
        return response;
    }

	// 🚀 응답을 간편하게 생성하는 유틸리티 메서드 추가
	private Map<String, Object> createResponse(boolean success, String message) {
	    Map<String, Object> response = new HashMap<>();
	    response.put("success", success);
	    response.put("message", message);
	    return response;
	}

	@RequestMapping(value = "/inputForm", method = RequestMethod.GET)
	public String showInputForm(Model model) {
	    if (model.containsAttribute("detectedObjects")) {
	        return "Fridge/objectResult"; // 상품 이미지 결과
	    } else if (model.containsAttribute("receiptItems")) {
	        return "Fridge/receiptResult"; // 영수증 결과
	    } else if (model.containsAttribute("parsedIngredients")) {
	        return "Fridge/voiceInputResult"; // 음성 입력 결과
	    } else {
	        model.addAttribute("error", "적절한 데이터를 찾을 수 없습니다.");
	        return "redirect:/fridge/addition";
	    }
	}


	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteIngredient(@RequestParam("ingredient_id") int ingredientId,
			RedirectAttributes redirectAttributes) {
		try {
			// 데이터베이스에서 삭제
			userIngredientDao.delete(ingredientId);
			redirectAttributes.addFlashAttribute("success", "재료가 성공적으로 삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "재료 삭제 중 오류가 발생했습니다: " + e.getMessage());
		}
		return "redirect:/fridge/fridge"; // 냉장고 메인 페이지로 리다이렉트
	}

	// 사물 저장
	@RequestMapping(value = "/objectResult", method = RequestMethod.POST)
	public String saveDetectedObjects(@RequestParam("selectedIndices") List<Integer> selectedIndices,
			@RequestParam Map<String, String> allParams, HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			// 세션에서 userId 가져오기
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
				return "redirect:/home"; // 로그인 페이지로 리다이렉트
			}

			for (Integer index : selectedIndices) {
				String name = allParams.get("name_" + index);
				if (name == null || name.trim().isEmpty()) {
					throw new IllegalArgumentException("상품 이름이 비어 있습니다.");
				}
				float quantity = Float.parseFloat(allParams.getOrDefault("quantity_" + index, "1.0"));
				String unit = allParams.getOrDefault("unit_" + index, "개");
				String expirationDate = allParams.getOrDefault("expirationDate_" + index, "9999-12-31");

				// userId 포함하여 저장
				userIngredientDao.save(name, quantity, unit, expirationDate, userId);
			}
			redirectAttributes.addFlashAttribute("success", "상품 데이터를 성공적으로 저장했습니다.");
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
		}
		return "redirect:/fridge/addition";
	}

	// 영수증 저장
	@RequestMapping(value = "/saveReceipt", method = RequestMethod.POST)
	public String saveReceiptData(@RequestParam("selectedIndices") List<Integer> selectedIndices,
			@RequestParam Map<String, String> allParams, HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			// 세션에서 userId 가져오기
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
				return "redirect:/home"; // 로그인 페이지로 리다이렉트
			}

			for (Integer index : selectedIndices) {
				String name = allParams.get("name_" + index);
				if (name == null || name.trim().isEmpty()) {
					throw new IllegalArgumentException("상품 이름이 비어 있습니다.");
				}
				float quantity = Float.parseFloat(allParams.getOrDefault("quantity_" + index, "1.0"));
				String unit = allParams.getOrDefault("unit_" + index, "개");
				String expirationDate = allParams.getOrDefault("expirationDate_" + index, "9999-12-31");

				// userId 포함하여 저장
				userIngredientDao.save(name, quantity, unit, expirationDate, userId);
			}
			redirectAttributes.addFlashAttribute("success", "영수증 데이터를 성공적으로 저장했습니다.");
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
		}
		return "redirect:/fridge/addition";
	}

	   // 📌 음성 녹음 후 업로드 처리
	@RequestMapping(value = "/uploadVoice", method = RequestMethod.POST)
	public String handleVoiceUpload(@RequestParam("voiceData") String base64Audio, RedirectAttributes redirectAttributes) {
	    try {
	        byte[] audioBytes = Base64.getDecoder().decode(base64Audio.split(",")[1]);
	        Path tempFile = Files.createTempFile("voice_", ".wav");
	        Files.write(tempFile, audioBytes, StandardOpenOption.CREATE);

	        File audioFile = tempFile.toFile();
	        MultipartFile multipartFile = new MockMultipartFile(audioFile.getName(), new FileInputStream(audioFile));

	        // 음성 인식 실행
	        String transcript = voiceRecognitionCommand.recognizeVoice(multipartFile);

	        // 음성 인식 결과를 리스트로 변환
	        List<Map<String, String>> parsedIngredients = parseVoiceTranscript(transcript);

	        // 결과 데이터 저장 후 결과 화면으로 이동
	        redirectAttributes.addFlashAttribute("parsedIngredients", parsedIngredients);
	        redirectAttributes.addFlashAttribute("recognizedText", transcript);

	        return "redirect:/fridge/inputForm"; // 결과 페이지로 이동

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "음성 처리 중 오류 발생: " + e.getMessage());
	        return "redirect:/fridge/addition";
	    }
	}

	// 음성 저장
	@RequestMapping(value = "/saveVoiceResult", method = RequestMethod.POST)
	public String saveVoiceResult(@RequestParam Map<String, String> allParams, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {
			// 세션에서 userId 가져오기
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
				return "redirect:/home"; // 로그인 페이지로 리다이렉트
			}

			for (String key : allParams.keySet()) {
				if (key.startsWith("name_")) {
					String index = key.substring(5); // 'name_' 이후 숫자 추출
					String name = allParams.get("name_" + index);
					float quantity = Float.parseFloat(allParams.getOrDefault("quantity_" + index, "1.0"));
					String unit = allParams.getOrDefault("unit_" + index, "개");
					String expirationDate = allParams.getOrDefault("expirationDate_" + index, "9999-12-31");

					// userId 포함하여 저장
					userIngredientDao.save(name, quantity, unit, expirationDate, userId);
				}
			}
			redirectAttributes.addFlashAttribute("success", "음성 인식 데이터를 성공적으로 저장했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
		}
		return "redirect:/fridge/addition";
	}
}