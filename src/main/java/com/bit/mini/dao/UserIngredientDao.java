package com.bit.mini.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bit.mini.util.DatabaseUtil;

@Repository
public class UserIngredientDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save ingredients with user_id
    public void save(String name, float quantity, String unit, String expirationDate, int userId) {
        String sql = "INSERT INTO useringredients (ingredient_name, quantity, unit, expiration_date, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, quantity, unit, expirationDate, userId);
    }

    // Delete ingredient by ID
    public void delete(int ingredientId) {
        String sql = "DELETE FROM useringredients WHERE ingredient_id = ?";
        jdbcTemplate.update(sql, ingredientId);
    }

    // Find all ingredients for a specific user
    public List<Map<String, Object>> findByUserId(int userId) {
        String sql = "SELECT * FROM useringredients WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, userId);
    }
    
    public List<String> getUserFridgeIngredients(int userId) {
        List<String> ingredients = new ArrayList<>();
        String query = "SELECT ingredient_name FROM useringredients WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ingredients.add(rs.getString("ingredient_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    public boolean deductIngredient(int userId, String ingredientName, float quantity) {
        String checkQuery = "SELECT quantity FROM useringredients WHERE user_id = ? AND ingredient_name LIKE ?";
        String updateQuery = "UPDATE useringredients SET quantity = quantity - ? WHERE user_id = ? AND ingredient_name LIKE ?";
        String deleteQuery = "DELETE FROM useringredients WHERE quantity <= 0 AND user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            checkStmt.setInt(1, userId);
            checkStmt.setString(2, "%" + ingredientName + "%");
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                float currentQuantity = rs.getFloat("quantity");

                if (currentQuantity < quantity) {
                    return false;
                }

                updateStmt.setFloat(1, quantity);
                updateStmt.setInt(2, userId);
                updateStmt.setString(3, "%" + ingredientName + "%");
                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected > 0) {
                    deleteStmt.setInt(1, userId);
                    deleteStmt.executeUpdate();
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
