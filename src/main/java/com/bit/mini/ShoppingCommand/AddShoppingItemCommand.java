package com.bit.mini.ShoppingCommand;

import com.bit.mini.dao.ShoppingDAO;
import com.bit.mini.dto.ShoppingItemDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddShoppingItemCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String ingredientName = request.getParameter("ingredientName");
        float quantity = Float.parseFloat(request.getParameter("quantity"));
        String unit = request.getParameter("unit");
        int userId = Integer.parseInt(request.getParameter("userId"));

        ShoppingItemDTO item = new ShoppingItemDTO(0, userId, ingredientName, quantity, unit, "Pending");
        ShoppingDAO dao = new ShoppingDAO();
        dao.addShoppingItem(item);
    }
}
