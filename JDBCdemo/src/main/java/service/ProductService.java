package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductService {
    private ProductDAO dao = new ProductDAO();

    // 以前的分页方法可以保留
    public List<Product> getProductsByPage(int page, int limit) {
        return dao.getProductsByPage(page, limit);
    }

    // ✅ 删除 brand 参数
    public List<Product> getProductsByFilter(String name, Double minPrice, Double maxPrice, int page, int limit) {
        return dao.getProductsByFilter(name, minPrice, maxPrice, page, limit);
    }

    // ✅ 删除 brand 参数
    public int getTotalCountByFilter(String name, Double minPrice, Double maxPrice) {
        return dao.getTotalCountByFilter(name, minPrice, maxPrice);
    }

    // 根据商品id查询单个商品
    public Product getProductById(int id) {
        return dao.getProductById(id);
    }
}
