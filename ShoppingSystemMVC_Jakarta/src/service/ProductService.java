package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;
public class ProductService {
    private ProductDAO dao = new ProductDAO();
    public List<Product> getProductsByPage(int page, int limit) {
        return dao.getProductsByPage(page, limit);
    }
}
