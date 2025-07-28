package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Product;
import service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private final ProductService service = new ProductService(); // 创建 ProductService 实例

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取分页参数
        int page = 1;
        int limit = 10;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception ignored) {}
        try {
            limit = Integer.parseInt(req.getParameter("limit"));
        } catch (Exception ignored) {}

        // 2. 获取筛选参数
        String name = req.getParameter("name");
        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");

        Double minPrice = null;
        Double maxPrice = null;
        try {
            if (minPriceStr != null && !minPriceStr.isEmpty()) {
                minPrice = Double.parseDouble(minPriceStr);
            }
            if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceStr);
            }
        } catch (NumberFormatException e) {
            // 如果价格格式不对，忽略价格筛选
        }

        // 3. 调用服务方法获取分页数据和总记录数
        List<Product> products = service.getProductsByFilter(name, minPrice, maxPrice, page, limit);
        int totalCount = service.getTotalCountByFilter(name, minPrice, maxPrice);

        // 4. 构造 JSON 数据格式
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"totalCount\":").append(totalCount).append(",");
        json.append("\"data\":[");

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            json.append("{")
                    .append("\"id\":").append(p.getId()).append(",")
                    .append("\"name\":\"").append(p.getName()).append("\",")
                    .append("\"price\":").append(p.getPrice()).append(",")
                    .append("\"stock\":").append(p.getStock())
                    .append("}");
            if (i < products.size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        // 5. 设置响应格式为 JSON 并输出数据
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(json.toString());
    }
}
