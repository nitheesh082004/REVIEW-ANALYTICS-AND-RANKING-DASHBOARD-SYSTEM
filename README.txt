# 🧠 Smart Product Review Platform

A full-stack web application that allows users to browse products, submit reviews, and analyze customer sentiment using Natural Language Processing (NLP). The platform automatically classifies reviews as Positive, Negative, Neutral, Very Positive, or Very Negative and dynamically updates product scores based on user feedback.

---

## 🚀 Features

### 📦 Product Management
- View available products
- Product images and pricing
- Direct Buy Now links
- Dynamic product score tracking

### 🔍 Product Search
- Real-time product search
- Filter products by name
- Fast and responsive UI

### ✍️ Review System
- Submit customer reviews
- View reviews for each product
- Real-time review updates

### 🧠 NLP Sentiment Analysis
- Rule-based sentiment classification
- Detects:
  - Very Positive
  - Positive
  - Neutral
  - Negative
  - Very Negative
- Handles:
  - Negation words (`not good`, `don't like`)
  - Contrast statements (`good but expensive`)

### ⭐ Dynamic Product Ranking
- Positive reviews increase product score
- Negative reviews decrease product score
- Helps identify top-performing products

### 📊 Analytics Dashboard
- Pie chart visualization
- Positive vs Negative vs Neutral review distribution
- Interactive sentiment analysis results

### 📱 Responsive Design
- Compatible with desktop, tablet, and mobile devices

---

## 🛠️ Tech Stack

### Frontend
- React.js
- Axios
- Chart.js
- CSS3

### Backend
- Spring Boot
- Java
- REST APIs

### Database
- MySQL
- JDBC

### NLP
- Rule-Based Sentiment Analysis
- Lexicon-Based Scoring
- HashMap Sentiment Dictionary

---

## 🏗️ System Architecture

```text
React Frontend
       │
       ▼
Axios HTTP Requests
       │
       ▼
Spring Boot REST APIs
       │
       ▼
DAO Layer
       │
       ▼
MySQL Database
       │
       ▼
NLP Sentiment Analyzer
       │
       ▼
Updated Product Scores
```

---

## 📂 Project Modules

### 1. Product Module
- Product listing
- Product details
- Product search

### 2. Review Module
- Add reviews
- Retrieve reviews
- Review history

### 3. NLP Module
- Sentiment extraction
- Score calculation
- Text processing

### 4. Ranking Module
- Dynamic score updates
- Product ranking

### 5. Analytics Module
- Sentiment visualization
- Review statistics

---

## 🧠 Algorithms Used

### Rule-Based Sentiment Analysis
Classifies reviews based on predefined sentiment keywords.

### Lexicon-Based Scoring
Assigns sentiment scores using a dictionary-based approach.

### Negation Handling
Detects phrases such as:
- not good
- don't like
- didn't like

### Contrast Analysis
Handles sentences containing:
- but
- however

Example:

```text
Amazing product but terrible battery
```

The analyzer gives more importance to the second clause.

### Linear Search
Used for product filtering and search functionality.

---

## 📊 Database Tables

### Products

| Column | Type |
|----------|----------|
| id | INT |
| name | VARCHAR |
| description | TEXT |
| price | DOUBLE |
| image_url | VARCHAR |
| buy_link | VARCHAR |
| score | INT |

### Reviews

| Column | Type |
|----------|----------|
| id | INT |
| product_id | INT |
| review_text | TEXT |
| sentiment | VARCHAR |

---

## 🔌 API Endpoints

### Get Products

```http
GET /api/products
```

### Add Review

```http
POST /api/add-review
```

Parameters:

```text
productId
reviewText
```

### Get Reviews

```http
GET /api/reviews?productId=1
```

### Test API

```http
GET /api/test
```

---

## ▶️ How to Run

### Backend

```bash
mvn spring-boot:run
```

Runs on:

```text
http://localhost:8080
```

### Frontend

```bash
npm install
npm run dev
```

Runs on:

```text
http://localhost:5173
```

---

## 🎯 Future Enhancements

- Machine Learning based sentiment analysis
- User authentication and authorization
- Product recommendation engine
- Review moderation
- Admin dashboard
- Advanced analytics
- Cloud deployment

---

## 👨‍💻 Author

**K. Nitheesh**  
B.Tech Computer Science Engineering

---

## 📜 License

This project is developed for educational and academic purposes.
