package com.bit.mini.dto;

public class ShoppingItemDTO {
    private int listId;
    private int userId;
    private String ingredientName;
    private float quantity;
    private String unit;
    private String status;

    // 기본 생성자
    public ShoppingItemDTO() {
    }

    // 필요한 경우 매개변수가 있는 생성자
    public ShoppingItemDTO(int listId, int userId, String ingredientName, float quantity, String unit, String status) {
        this.listId = listId;
        this.userId = userId;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.status = status;
    }

    // Getter와 Setter
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
