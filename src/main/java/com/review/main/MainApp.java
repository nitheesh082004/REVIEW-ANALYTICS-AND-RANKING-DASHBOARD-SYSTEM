package com.review.main;

import com.review.dao.ProductDAO;
import com.review.dao.ReviewDAO;
import com.review.nlp.NLPSentimentAnalyzer;
import com.shoppingcartnlp.model.Product;
import com.shoppingcartnlp.model.Review;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ProductDAO productDAO = new ProductDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        // 📦 Show products
        List<Product> products = productDAO.getAllProducts();

        System.out.println("\n=== Available Products ===");
        for (Product p : products) {
            System.out.println(p.getId() + " - " + p.getName());
        }

        // 🎯 Select product
        System.out.print("\nEnter product ID: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        // ✍️ Enter review
        System.out.print("Enter your review: ");
        String reviewText = scanner.nextLine();

        // 🧠 NLP
        String sentiment = NLPSentimentAnalyzer.analyzeSentiment(reviewText);

        // 💾 Save review
        Review review = new Review(productId, reviewText, sentiment);
        reviewDAO.addReview(review);

        System.out.println("\n✅ Sentiment: " + sentiment);

        scanner.close();
    }
}