package dao;

import model.Product;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // 原分页查询
    public List<Product> getProductsByPage(int page, int limit) {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM products LIMIT ?, ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, (page - 1) * limit);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 根据筛选条件分页查询
    public List<Product> getProductsByFilter(String name, Double minPrice, Double maxPrice, int page, int limit) {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                sql.append(" AND name LIKE ?");
                params.add("%" + name + "%");
            }
            if (minPrice != null) {
                sql.append(" AND price >= ?");
                params.add(minPrice);
            }
            if (maxPrice != null) {
                sql.append(" AND price <= ?");
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
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 根据筛选条件查询总记录数
    public int getTotalCountByFilter(String name, Double minPrice, Double maxPrice) {
        int count = 0;
        try (Connection conn = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM products WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                sql.append(" AND name LIKE ?");
                params.add("%" + name + "%");
            }
            if (minPrice != null) {
                sql.append(" AND price >= ?");
                params.add(minPrice);
            }
            if (maxPrice != null) {
                sql.append(" AND price <= ?");
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

    // 根据 ID 查询单个商品
    public Product getProductById(int id) {
        Product p = null;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM products WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
