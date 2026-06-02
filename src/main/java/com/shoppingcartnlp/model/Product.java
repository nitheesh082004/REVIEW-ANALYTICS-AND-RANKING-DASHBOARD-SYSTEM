package com.shoppingcartnlp.model;

public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String buyLink;
    private String imageUrl;
    private int score; // 🔥 IMPORTANT

    // ✅ Default constructor
    public Product() {}

    // ✅ Full constructor (optional)
    public Product(int id, String name, String description, double price,
                   String category, String buyLink, String imageUrl, int score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.buyLink = buyLink;
        this.imageUrl = imageUrl;
        this.score = score;
    }

    // 🔹 GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // 🔥 SCORE (CRITICAL)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}