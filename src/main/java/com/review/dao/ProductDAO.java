package com.review.dao;

import com.review.db.DBConnection;
import com.shoppingcartnlp.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository; // 🔥 ADD THIS

@Repository // 🔥 ADD THIS
public class ProductDAO {

    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Product p = new Product();

                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                p.setBuyLink(rs.getString("buy_link"));
                p.setScore(rs.getInt("score"));

                products.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public void updateProductScore(int productId, int score) {

        String sql = "UPDATE products SET score = score + ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, score);
            stmt.setInt(2, productId);

            int rows = stmt.executeUpdate();
            System.out.println("Rows updated: " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}