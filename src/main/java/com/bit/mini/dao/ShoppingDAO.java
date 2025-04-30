package com.bit.mini.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bit.mini.dto.ShoppingItemDTO;
import com.bit.mini.util.DatabaseUtil;

public class ShoppingDAO {
	
	  public ShoppingItemDTO getShoppingItem(int listId) {
	        ShoppingItemDTO item = null; // 반환할 DTO 객체를 초기화

	        String query = "SELECT * FROM shoppinglist WHERE list_id = ?";

	        try (Connection conn = DatabaseUtil.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {

	            pstmt.setInt(1, listId); // listId 값을 쿼리에 설정
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                // ResultSet에서 데이터 읽기
	                int userId = rs.getInt("user_id");
	                String ingredientName = rs.getString("ingredient_name");
	                float quantity = rs.getFloat("quantity");
	                String unit = rs.getString("unit");
	                String status = rs.getString("status");

	                // ShoppingItemDTO 객체 생성 및 값 설정
	                item = new ShoppingItemDTO(listId, userId, ingredientName, quantity, unit, status);
	            }

	        } catch (Exception e) {
	            e.printStackTrace(); // 예외 발생 시 출력
	        }

	        return item; // DTO 객체 반환 (null일 수도 있음)
	        
	    }

	  public List<ShoppingItemDTO> getShoppingList(int userId) {
		    List<ShoppingItemDTO> shoppingList = new ArrayList<>();
		    String query = "SELECT * FROM shoppinglist WHERE user_id = ?";

		    try (Connection conn = DatabaseUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(query)) {

		        pstmt.setInt(1, userId);
		        ResultSet rs = pstmt.executeQuery();

		        while (rs.next()) {
		            int listId = rs.getInt("list_id");
		            String ingredientName = rs.getString("ingredient_name");
		            float quantity = rs.getFloat("quantity");
		            String unit = rs.getString("unit");
		            String status = rs.getString("status");

		            ShoppingItemDTO item = new ShoppingItemDTO(listId, userId, ingredientName, quantity, unit, status);
		            shoppingList.add(item);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return shoppingList;
		}



	  public void addShoppingItem(ShoppingItemDTO item) {
		    String query = "INSERT INTO shoppinglist (user_id, ingredient_name, quantity, unit, status) VALUES (?, ?, ?, ?, 'Pending')";

		    try (Connection conn = DatabaseUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(query)) {

		        pstmt.setInt(1, item.getUserId());
		        pstmt.setString(2, item.getIngredientName());
		        pstmt.setFloat(3, item.getQuantity());
		        pstmt.setString(4, item.getUnit());

		        int rowsAffected = pstmt.executeUpdate();
		        System.out.println("🛒 [DB] 쇼핑 리스트에 추가된 행 수: " + rowsAffected);

		        if (rowsAffected > 0) {
		            System.out.println("✅ [SUCCESS] 쇼핑 리스트에 재료 추가됨: " + item.getIngredientName());
		        } else {
		            System.out.println("❌ [ERROR] 쇼핑 리스트 추가 실패");
		        }

		    } catch (Exception e) {
		        System.err.println("❌ [ERROR] 쇼핑 리스트 추가 중 오류 발생:");
		        e.printStackTrace();
		    }
		}

	  public void addToUserIngredients(ShoppingItemDTO item) {
		    String query = "INSERT INTO useringredients (user_id, ingredient_name, quantity, unit, expiration_date) " +
		                   "VALUES (?, ?, ?, ?, DATE_ADD(NOW(), INTERVAL 7 DAY))"; // 유효기간 기본값 7일 설정

		    try (Connection conn = DatabaseUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(query)) {

		        pstmt.setInt(1, item.getUserId()); // user_id
		        pstmt.setString(2, item.getIngredientName()); // ingredient_name
		        pstmt.setFloat(3, item.getQuantity()); // quantity
		        pstmt.setString(4, item.getUnit()); // unit

		        pstmt.executeUpdate();

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}


	  public void updateShoppingStatus(int listId, String status) {
		    String query = "UPDATE shoppinglist SET status = ? WHERE list_id = ?";
		    try (Connection conn = DatabaseUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(query)) {

		        pstmt.setString(1, status); // "Purchased"
		        pstmt.setInt(2, listId); // 해당 listId

		        int rowsAffected = pstmt.executeUpdate();
		        System.out.println("Status update rows affected: " + rowsAffected);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

	  public void deleteShoppingItem(int listId) {
		    String query = "DELETE FROM shoppinglist WHERE list_id = ?";
		    try (Connection conn = DatabaseUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(query)) {

		        pstmt.setInt(1, listId);

		        int rowsAffected = pstmt.executeUpdate();
		        System.out.println("Delete rows affected: " + rowsAffected);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	  
	  // ✅ 이미 쇼핑 리스트에 존재하는 재료인지 확인하는 메서드
	    public boolean isIngredientInShoppingList(int userId, String ingredientName) {
	        String query = "SELECT COUNT(*) FROM shoppinglist WHERE user_id = ? AND ingredient_name = ?";

	        try (Connection conn = DatabaseUtil.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {

	            pstmt.setInt(1, userId);
	            pstmt.setString(2, ingredientName);

	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                return true; // 이미 존재
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return false;
	    }

	  
}
