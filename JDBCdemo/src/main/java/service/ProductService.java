package service;

import dao.ProductDAO;
import model.Product;
import java.util.List;

public class ProductService {
    private ProductDAO dao = new ProductDAO();

    // ✅ 分页查询（默认查询全部，不筛选）
    public List<Product> getProductsByPage(int page, int limit) {
        return dao.getProductsWithWarehouse(null, null, null, null, page, limit);
    }

    // ✅ 带筛选条件的分页查询（支持商品名 + 仓库名 + 价格区间）
    public List<Product> getProductsByFilter(String name, String warehouseName, Double minPrice, Double maxPrice, int page, int limit) {
        return dao.getProductsWithWarehouse(name, warehouseName, minPrice, maxPrice, page, limit);
    }

    // ✅ 获取筛选后的总记录数（支持仓库名）
    public int getTotalCountByFilter(String name, String warehouseName, Double minPrice, Double maxPrice) {
        return dao.getTotalCountWithWarehouse(name, warehouseName, minPrice, maxPrice);
    }

    // ✅ 根据商品id查询单个商品（带仓库信息）
    public Product getProductById(int id) {
        return dao.getProductByIdWithWarehouse(id);
    }
}
