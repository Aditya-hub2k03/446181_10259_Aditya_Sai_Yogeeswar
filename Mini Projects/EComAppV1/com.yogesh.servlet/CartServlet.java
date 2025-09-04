package com.yogesh.servlet;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<String> cart = (List<String>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        String itemName = req.getParameter("name");
        if (itemName != null) {
            cart.add(itemName);
        }

        session.setAttribute("cart", cart);
        resp.sendRedirect("cart.jsp");
    }
}
