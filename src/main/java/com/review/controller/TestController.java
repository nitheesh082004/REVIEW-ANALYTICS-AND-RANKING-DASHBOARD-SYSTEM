package com.review.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.review.dao.ProductDAO;
import com.review.dao.ReviewDAO;
import com.review.nlp.NLPSentimentAnalyzer;
import com.shoppingcartnlp.model.Product;
import com.shoppingcartnlp.model.Review;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ReviewDAO reviewDAO;

    // ✅ TEST
    @GetMapping("/test")
    public String test() {
        return "Backend working!";
    }

    // ✅ GET PRODUCTS
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productDAO.getAllProducts();
    }

    // 🔥 ADD REVIEW + UPDATE SCORE
    @PostMapping("/add-review")
    public String addReview(@RequestParam int productId,
                            @RequestParam String reviewText) {

        // 🔥 NLP
        String sentiment = NLPSentimentAnalyzer.analyzeSentiment(reviewText);
        int score = NLPSentimentAnalyzer.getScore(sentiment);

        // 💾 Save review
        Review review = new Review(productId, reviewText, sentiment);
        reviewDAO.addReview(review);

        // 🔥 Update product score
        productDAO.updateProductScore(productId, score);

        return "Review added with sentiment: " + sentiment;
    }

    // ✅ GET REVIEWS
    @GetMapping("/reviews")
    public List<Review> getReviews(@RequestParam int productId) {
        return reviewDAO.getReviewsByProduct(productId);
    }

    // 🔥 CONSISTENT SCORE API
    @GetMapping("/score")
    public int getScore(@RequestParam int productId) {

        List<Review> reviews = reviewDAO.getReviewsByProduct(productId);

        int total = 0;

        for (Review r : reviews) {
            total += NLPSentimentAnalyzer.getScore(r.getSentiment());
        }

        return total;
    }
}