package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Product;
import service.ProductService;

import java.io.IOException;
import java.util.List;

import java.math.BigDecimal;


@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private final ProductService service = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 分页参数
        int page = 1;
        int limit = 10;
        try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
        try { limit = Integer.parseInt(req.getParameter("limit")); } catch (Exception ignored) {}

        // 2. 筛选参数
        String name = req.getParameter("name");
        String warehouseName = req.getParameter("warehouseName");
        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");

        Double minPrice = null, maxPrice = null;
        try {
            if (minPriceStr != null && !minPriceStr.isEmpty()) minPrice = Double.parseDouble(minPriceStr);
            if (maxPriceStr != null && !maxPriceStr.isEmpty()) maxPrice = Double.parseDouble(maxPriceStr);
        } catch (NumberFormatException ignored) {}

        // 3. 查询数据
        List<Product> products = service.getProductsByFilter(name, minPrice, maxPrice, warehouseName, page, limit);
        int totalCount = service.getTotalCountByFilter(name, minPrice, maxPrice, warehouseName);

        // 4. 构造 JSON
        StringBuilder json = new StringBuilder();
        json.append("{\"totalCount\":").append(totalCount).append(",\"data\":[");

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            json.append("{")
                    .append("\"id\":").append(p.getId()).append(",")
                    .append("\"name\":\"").append(p.getName()).append("\",")
                    .append("\"price\":").append(p.getPrice()).append(",")
                    .append("\"stock\":").append(p.getStock()).append(",")
                    .append("\"warehouseName\":\"").append(p.getWarehouseName() != null ? p.getWarehouseName() : "").append("\",")
                    .append("\"warehouseLocation\":\"").append(p.getWarehouseLocation() != null ? p.getWarehouseLocation() : "").append("\"")
                    .append("}");
            if (i < products.size() - 1) json.append(",");
        }
        json.append("]}");

        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                Product p = new Product();
                p.setName(req.getParameter("name"));
                //p.setPrice(Double.parseDouble(req.getParameter("price")));
                p.setPrice(new BigDecimal(req.getParameter("price")));

                p.setStock(Integer.parseInt(req.getParameter("stock")));
                p.setWarehouseName(req.getParameter("warehouseName"));
                p.setWarehouseLocation(req.getParameter("warehouseLocation"));

                service.addProduct(p);
                resp.getWriter().write("{\"status\":\"success\"}");

            } else if ("update".equals(action)) {
                Product p = new Product();
                p.setId(Integer.parseInt(req.getParameter("id")));
                p.setName(req.getParameter("name"));
               // p.setPrice(Double.parseDouble(req.getParameter("price")));
                p.setPrice(new BigDecimal(req.getParameter("price")));

                p.setStock(Integer.parseInt(req.getParameter("stock")));
                p.setWarehouseName(req.getParameter("warehouseName"));
                p.setWarehouseLocation(req.getParameter("warehouseLocation"));

                service.updateProduct(p);
                resp.getWriter().write("{\"status\":\"success\"}");

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                service.deleteProduct(id);
                resp.getWriter().write("{\"status\":\"success\"}");

            } else {
                resp.getWriter().write("{\"status\":\"error\",\"message\":\"未知操作\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
