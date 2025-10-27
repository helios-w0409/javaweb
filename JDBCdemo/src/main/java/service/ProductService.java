package service;

import mapper.ProductMapper;
import model.Product;
import org.apache.ibatis.session.SqlSession;
import util.MyBatisUtil;

import java.util.List;

public class ProductService {

    /**
     * 分页查询商品（可根据商品名、价格区间、仓库名筛选）
     */
    public List<Product> getProductsByFilter(String name, Double minPrice, Double maxPrice, String warehouseName, int page, int limit) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            int offset = (page - 1) * limit;
            return mapper.selectProductsWithWarehouse(name, minPrice, maxPrice, warehouseName, offset, limit);
        }
    }

    /**
     * 获取筛选后的商品总数（用于分页）
     */
    public int getTotalCountByFilter(String name, Double minPrice, Double maxPrice, String warehouseName) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            return mapper.countProductsWithWarehouse(name, minPrice, maxPrice, warehouseName);
        }
    }

    /**
     * 根据商品ID查询单个商品
     */
    public Product getProductById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            return mapper.selectProductById(id);
        }
    }

    /**
     * 新增商品
     */
    public boolean addProduct(Product product) {
//        System.out.println("准备添加商品");
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            int rows = mapper.insertProduct(product);
            session.commit();
            return rows > 0;
        }
    }

    /**
     * 修改商品信息
     */
    public boolean updateProduct(Product product) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            int rows = mapper.updateProduct(product);
            session.commit();
            return rows > 0;
        }
    }

    /**
     * 删除商品
     */
    public boolean deleteProduct(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            ProductMapper mapper = session.getMapper(ProductMapper.class);
            int rows = mapper.deleteProduct(id);
            session.commit();
            return rows > 0;
        }
    }
}
