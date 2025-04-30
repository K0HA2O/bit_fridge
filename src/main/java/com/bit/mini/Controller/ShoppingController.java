package com.bit.mini.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.mini.dao.ShoppingDAO;
import com.bit.mini.dto.ShoppingItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/shopping")
public class ShoppingController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ShoppingDAO shoppingDAO = new ShoppingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login"); // 로그인 페이지로 리디렉션
            return;
        }

        List<ShoppingItemDTO> shoppingList = shoppingDAO.getShoppingList(userId);
        request.setAttribute("shoppingList", shoppingList);
        request.getRequestDispatcher("/WEB-INF/views/Shopping/shoppingList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Action received: " + action);

        if ("add".equals(action)) {
            handleAddShoppingItem(request, response);
        } else if ("purchase".equals(action)) {
            handlePurchase(request, response);
        } else if ("delete".equals(action)) {
            handleDelete(request, response);
        } else if ("addMissingIngredients".equals(action)) { // 🛒 부족한 재료 추가 기능
            handleAddMissingIngredients(request, response);
        } else {
            System.err.println("Unknown action: " + action);
            response.sendRedirect("shopping");
        }
    }

    // 🛒 쇼핑 리스트에 새로운 재료 추가
    private void handleAddShoppingItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ingredientName = request.getParameter("ingredientName");
        String quantityStr = request.getParameter("quantity");
        String unit = request.getParameter("unit");

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            float quantity = Float.parseFloat(quantityStr);
            ShoppingItemDTO item = new ShoppingItemDTO(0, userId, ingredientName, quantity, unit, "Pending");
            shoppingDAO.addShoppingItem(item);
            System.out.println("Item added successfully");

        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format: " + quantityStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("shopping");
    }

    // 🛍️ 장바구니에서 재료 구매 처리
    private void handlePurchase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int listId = Integer.parseInt(request.getParameter("listId"));

        ShoppingItemDTO item = shoppingDAO.getShoppingItem(listId);
        if (item != null) {
            shoppingDAO.addToUserIngredients(item);
            shoppingDAO.updateShoppingStatus(listId, "Purchased");
        }

        response.sendRedirect("shopping");
    }

    // 🗑️ 장바구니에서 재료 삭제 처리
    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int listId = Integer.parseInt(request.getParameter("listId"));
        shoppingDAO.deleteShoppingItem(listId);
        response.sendRedirect("shopping");
    }

 // 🛒 부족한 재료를 쇼핑 리스트에 추가하는 기능 (DB 반영 문제 해결)
    private void handleAddMissingIngredients(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            System.out.println("❌ [ERROR] 로그인되지 않은 사용자");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"success\":false, \"message\":\"로그인이 필요합니다.\"}");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestData;

        try {
            requestData = objectMapper.readValue(request.getReader(), Map.class);
        } catch (Exception e) {
            System.out.println("❌ [ERROR] JSON 파싱 오류 발생");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"success\":false, \"message\":\"JSON 파싱 오류 발생\"}");
            return;
        }

        List<Map<String, Object>> missingIngredients = (List<Map<String, Object>>) requestData.get("ingredients");

        System.out.println("🔍 [LOG] 받은 부족한 재료 목록: " + missingIngredients);

        if (missingIngredients == null || missingIngredients.isEmpty()) {
            System.out.println("❌ [ERROR] 부족한 재료가 없습니다.");
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"success\":false, \"message\":\"부족한 재료가 없습니다.\"}");
            return;
        }

        ShoppingDAO shoppingDAO = new ShoppingDAO();

        for (Map<String, Object> ingredient : missingIngredients) {
            String name = (String) ingredient.get("name");
            float quantity = ((Number) ingredient.get("quantity")).floatValue();
            String unit = (String) ingredient.get("unit");

            ShoppingItemDTO item = new ShoppingItemDTO(0, userId, name, quantity, unit, "Pending");

            System.out.println("🛒 [LOG] 쇼핑 리스트 추가 시도: " + name + " - " + quantity + " " + unit);
            shoppingDAO.addShoppingItem(item);
        }

        System.out.println("✅ [SUCCESS] 부족한 재료가 쇼핑 리스트에 추가됨");
        
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"success\":true, \"message\":\"부족한 재료가 쇼핑 리스트에 추가되었습니다.\"}");
    }
}