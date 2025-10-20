package dao;

import model.Product;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // 分页查询 + 多条件筛选（商品名 + 仓库名 + 价格区间）
    public List<Product> getProductsWithWarehouse(String name, String warehouseName, Double minPrice, Double maxPrice, int page, int limit) {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder(
                    "SELECT p.id, p.name, p.price, p.stock, w.name AS warehouseName, w.location AS warehouseLocation " +
                            "FROM products p LEFT JOIN warehouses w ON p.warehouse_id = w.id WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                sql.append(" AND p.name LIKE ?");
                params.add("%" + name + "%");
            }

            if (warehouseName != null && !warehouseName.isEmpty()) {
                sql.append(" AND w.name LIKE ?");
                params.add("%" + warehouseName + "%");
            }

            if (minPrice != null) {
                sql.append(" AND p.price >= ?");
                params.add(minPrice);
            }

            if (maxPrice != null) {
                sql.append(" AND p.price <= ?");
                params.add(maxPrice);
            }

            sql.append(" LIMIT ?, ?");
            params.add((page - 1) * limit);
            params.add(limit);

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setStock(rs.getInt("stock"));
                p.setWarehouseName(rs.getString("warehouseName"));
                p.setWarehouseLocation(rs.getString("warehouseLocation"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 获取筛选后的总记录数
    public int getTotalCountWithWarehouse(String name, String warehouseName, Double minPrice, Double maxPrice) {
        int count = 0;
        try (Connection conn = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder(
                    "SELECT COUNT(*) FROM products p LEFT JOIN warehouses w ON p.warehouse_id = w.id WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                sql.append(" AND p.name LIKE ?");
                params.add("%" + name + "%");
            }

            if (warehouseName != null && !warehouseName.isEmpty()) {
                sql.append(" AND w.name LIKE ?");
                params.add("%" + warehouseName + "%");
            }

            if (minPrice != null) {
                sql.append(" AND p.price >= ?");
                params.add(minPrice);
            }

            if (maxPrice != null) {
                sql.append(" AND p.price <= ?");
                params.add(maxPrice);
            }

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // 根据 ID 查询单个商品（带仓库信息）
    public Product getProductByIdWithWarehouse(int id) {
        Product p = null;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT p.id, p.name, p.price, p.stock, w.name AS warehouseName, w.location AS warehouseLocation " +
                    "FROM products p LEFT JOIN warehouses w ON p.warehouse_id = w.id WHERE p.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setStock(rs.getInt("stock"));
                p.setWarehouseName(rs.getString("warehouseName"));
                p.setWarehouseLocation(rs.getString("warehouseLocation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
