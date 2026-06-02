import { useEffect, useState } from "react";
import axios from "axios";
import { Pie } from "react-chartjs-2";
import "chart.js/auto";
import "./App.css";

function App() {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [reviewText, setReviewText] = useState("");
  const [search, setSearch] = useState("");

  // ✅ LOAD PRODUCTS
  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = () => {
    axios.get("http://localhost:8080/api/products")
      .then(res => {
        setProducts(res.data);
        setFilteredProducts(res.data);
      })
      .catch(err => console.log(err));
  };

  // ✅ SEARCH
  useEffect(() => {
    const filtered = products.filter(p =>
      p.name.toLowerCase().includes(search.toLowerCase())
    );
    setFilteredProducts(filtered);
  }, [search, products]);

  // ✅ SELECT PRODUCT
  const selectProduct = (product) => {
    setSelectedProduct(product);

    axios.get(`http://localhost:8080/api/reviews?productId=${product.id}`)
      .then(res => setReviews(res.data))
      .catch(err => console.log(err));
  };

  // 🔥 ADD REVIEW (FULL FIX)
  const addReview = () => {
    if (!reviewText || !selectedProduct) return;

    axios.post("http://localhost:8080/api/add-review", null, {
      params: {
        productId: selectedProduct.id,
        reviewText: reviewText
      }
    }).then(() => {

      setReviewText("");

      // 🔥 RELOAD PRODUCTS (FIX SCORE ISSUE)
      axios.get("http://localhost:8080/api/products")
        .then(res => {
          setProducts(res.data);
          setFilteredProducts(res.data);

          // 🔥 UPDATE SELECTED PRODUCT WITH NEW SCORE
          const updated = res.data.find(p => p.id === selectedProduct.id);
          setSelectedProduct(updated);
        });

      // 🔥 RELOAD REVIEWS
      axios.get(`http://localhost:8080/api/reviews?productId=${selectedProduct.id}`)
        .then(res => setReviews(res.data));
    });
  };

  // ✅ CHART DATA
  const chartData = {
    labels: ["Positive", "Negative", "Neutral"],
    datasets: [
      {
        data: [
          reviews.filter(r => r.sentiment === "Positive").length,
          reviews.filter(r => r.sentiment === "Negative").length,
          reviews.filter(r => r.sentiment === "Neutral").length,
        ],
        backgroundColor: ["#36A2EB", "#FF6384", "#FFCE56"]
      }
    ]
  };

  return (
    <div className="container">

      <h1>🧠 Smart Product Review Platform</h1>

      {/* 🔍 SEARCH */}
      <input
        type="text"
        placeholder="Search product..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="search"
      />

      <div className="layout">

        {/* LEFT */}
        <div className="left">
          <h3>Available Products</h3>

          {filteredProducts.map(p => (
            <div
              key={p.id}
              className="product-card"
              onClick={() => selectProduct(p)}
            >
              <img src={p.imageUrl} alt="" />
              <div>
                <h4>{p.name}</h4>
                <p>₹{p.price}</p>
              </div>
            </div>
          ))}
        </div>

        {/* RIGHT */}
        <div className="right">

          {selectedProduct && (
            <>
              <div className="selected-product">
                <img src={selectedProduct.imageUrl} alt="" />
                <h2>{selectedProduct.name}</h2>
                <p>₹{selectedProduct.price}</p>
                <p>⭐ Score: {selectedProduct.score}</p>

                <a href={selectedProduct.buyLink} target="_blank" rel="noreferrer">
                  <button className="buy">Buy Now</button>
                </a>
              </div>

              {/* 🔥 REVIEW BOX FIXED */}
              <div className="review-box">
                <textarea
                  placeholder="Write your review..."
                  value={reviewText}
                  onChange={(e) => setReviewText(e.target.value)}
                />

                <button className="submit-btn" onClick={addReview}>
                  Submit Review
                </button>
              </div>

              {/* REVIEWS */}
              <div className="reviews">
                <h3>Customer Reviews</h3>
                {reviews.map((r, i) => (
                  <p key={i}>
                    {r.reviewText} → <b>{r.sentiment}</b>
                  </p>
                ))}
              </div>

              {/* CHART */}
              <div className="chart">
                <h3>Sentiment Analysis</h3>
                <Pie data={chartData} />
              </div>
            </>
          )}

        </div>
      </div>
    </div>
  );
}

export default App;