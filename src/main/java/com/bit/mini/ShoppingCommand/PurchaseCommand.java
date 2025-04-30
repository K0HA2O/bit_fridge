package com.bit.mini.ShoppingCommand;

import com.bit.mini.dao.ShoppingDAO;
import com.bit.mini.dto.ShoppingItemDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PurchaseCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        int listId = Integer.parseInt(request.getParameter("listId"));

        ShoppingDAO dao = new ShoppingDAO();
        ShoppingItemDTO item = dao.getShoppingItem(listId);

        if (item != null) {
            dao.addToUserIngredients(item);
            dao.updateShoppingStatus(listId, "Purchased");
        }
    }
}
