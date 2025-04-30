package com.bit.mini.ShoppingCommand;

import com.bit.mini.dao.ShoppingDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteShoppingItemCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        int listId = Integer.parseInt(request.getParameter("listId"));
        ShoppingDAO dao = new ShoppingDAO();
        dao.deleteShoppingItem(listId);
    }
}
