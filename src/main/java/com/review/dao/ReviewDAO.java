package com.review.dao;

import com.review.db.DBConnection;
import com.shoppingcartnlp.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository; // 🔥 IMPORTANT

@Repository // 🔥 REQUIRED
public class ReviewDAO {

    public void addReview(Review review) {

        String sql = "INSERT INTO reviews (product_id, review_text, sentiment) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getProductId());
            stmt.setString(2, review.getReviewText());
            stmt.setString(3, review.getSentiment());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviewsByProduct(int productId) {

        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT * FROM reviews WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("product_id"),
                        rs.getString("review_text"),
                        rs.getString("sentiment")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }
}