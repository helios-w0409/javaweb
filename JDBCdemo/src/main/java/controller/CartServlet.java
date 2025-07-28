package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;
import service.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORSHeaders(req, resp);

        resp.setContentType("application/json;charset=UTF-8");
        HttpSession session = req.getSession(true);
        System.out.println("Session ID (POST): " + session.getId());

        Map<Integer, Integer> cart = getOrCreateCart(session);
        String action = req.getParameter("action");

        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            if ("delete".equals(action)) {
                cart.remove(productId);
            } else if ("update".equals(action)) {
                if (quantity <= 0) {
                    cart.remove(productId);
                } else {
                    cart.put(productId, quantity);
                }
            } else { // 默认添加
                cart.merge(productId, quantity, Integer::sum);
            }

            session.setAttribute("cart", cart); // 保存购物车
            sendCartResponse(resp, cart);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"success\":false,\"message\":\"参数或服务器错误\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORSHeaders(req, resp);

        resp.setContentType("application/json;charset=UTF-8");
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.getWriter().write("{\"data\":[],\"totalQuantity\":0,\"totalAmount\":0}");
            return;
        }

        Map<Integer, Integer> cart = getOrCreateCart(session);
        System.out.println("Session ID (GET): " + session.getId() + "  cart: " + cart);
        sendCartResponse(resp, cart);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORSHeaders(req, resp);

        resp.setContentType("application/json;charset=UTF-8");
        HttpSession session = req.getSession(true);
        session.setAttribute("cart", new HashMap<>()); // 清空购物车
        resp.getWriter().write("{\"success\":true,\"message\":\"购物车已清空\"}");
    }

    // 设置跨域头，允许携带 cookie
    private void setCORSHeaders(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }

    // 获取或新建购物车
    private Map<Integer, Integer> getOrCreateCart(HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
            System.out.println("新建购物车，sessionId=" + session.getId());
        }
        return cart;
    }

    // 返回购物车 JSON
    private void sendCartResponse(HttpServletResponse resp, Map<Integer, Integer> cart) throws IOException {
        int totalQuantity = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;

        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"success\":true,")
                .append("\"code\":0,")
                .append("\"msg\":\"\",")
                .append("\"count\":").append(cart.size()).append(",")
                .append("\"data\":[");

        boolean first = true;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product p = productService.getProductById(productId);

            if (p != null) {
                if (!first) sb.append(",");
                BigDecimal subtotal = p.getPrice().multiply(BigDecimal.valueOf(quantity));
                totalQuantity += quantity;
                totalAmount = totalAmount.add(subtotal);

                sb.append("{")
                        .append("\"id\":").append(p.getId()).append(",")
                        .append("\"name\":\"").append(escapeJson(p.getName())).append("\",")
                        .append("\"price\":").append(p.getPrice()).append(",")
                        .append("\"quantity\":").append(quantity).append(",")
                        .append("\"subtotal\":").append(subtotal)
                        .append("}");

                first = false;
            }
        }

        sb.append("],")
                .append("\"totalQuantity\":").append(totalQuantity).append(",")
                .append("\"totalAmount\":").append(totalAmount)
                .append("}");

        resp.getWriter().write(sb.toString());
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
