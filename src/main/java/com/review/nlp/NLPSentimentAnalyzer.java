package com.review.nlp;

import java.util.*;

public class NLPSentimentAnalyzer {

    static Map<String, Integer> sentimentMap = new HashMap<>();

    static {
    	 // 🔥 VERY POSITIVE (3)
        String[] veryPositive = {
            "amazing","excellent","perfect","fantastic","awesome","outstanding",
            "brilliant","superb","incredible","best","wonderful","fabulous",
            "phenomenal","spectacular","marvelous","exceptional","magnificent",
            "terrific","legendary","flawless","stunning","remarkable",
            "topnotch","firstclass","unbelievable","mindblowing","astonishing",
            "extraordinary","impeccable","divine","glorious","splendid",
            "sensational","dazzling","pristine","elite","premiumquality",
            "highend","worldclass","unmatched","unparalleled","unbeatable",
            "breathtaking","fivestar","perfectly","ideal","ultimate",
            "luxurious","delightful","charming","exquisite","impressive"
        };

        // ✅ POSITIVE (2)
        String[] positive = {
            "good","nice","great","liked","like","love","satisfied","happy",
            "worth","reliable","fast","smooth","cool","valuable",
            "quality","premium","easy","comfortable","durable","efficient",
            "solid","stable","useful","handy","convenient","responsive","clean",
            "wellmade","affordable","reasonable","trustworthy","dependable",
            "flexible","versatile","compact","lightweight","practical",
            "functional","userfriendly","intuitive","sleek","stylish","modern",
            "neat","organized","helpful","productive","secure","safe",
            "consistent","balanced","pleasant","enjoyable","satisfying"
        };

        // 🙂 LIGHT POSITIVE (1)
        String[] lightPositive = {
            "decent","fine","ok","okay","acceptable","fair","alright",
            "manageable","usable","passable","sufficient","adequate",
            "tolerable","simple","basic","moderate","serviceable"
        };

        // ⚠️ NEUTRAL (0)
        String[] neutral = {
            "average","normal","medium","typical","standard","regular",
            "ordinary","common","expected","neutral","balanced",
            "soso","plain","routine","default","usual"
        };

        // ❌ NEGATIVE (-2)
        String[] negative = {
            "bad","poor","slow","lag","issue","issues","problem","problems",
            "cheap","weak","disappointed","boring","difficult","annoying",
            "faulty","waste","inefficient","unstable","buggy","glitchy",
            "inconsistent","unresponsive","fragile","overpriced",
            "underwhelming","mediocre","inferior","lowquality","confusing",
            "messy","clunky","noisy","heavy","bulky","limited","unreliable",
            "uncomfortable","complicated","delayed","rough","notgood",
        };

        // 🔥 VERY NEGATIVE (-3)
        String[] veryNegative = {
            "worst","terrible","awful","useless","hate","horrible",
            "pathetic","disaster","broken","damage","crash","garbage",
            "junk","trash","worthless","unusable","failed","failure",
            "defective","ruined","destroyed","frustrating","unacceptable",
            "atrocious","appalling","dreadful","nightmare","hopeless",
            "abysmal","lousy","ridiculous","unbearable"
        };

        for (String w : veryPositive) sentimentMap.put(w, 3);
        for (String w : positive) sentimentMap.put(w, 2);
        for (String w : lightPositive) sentimentMap.put(w, 1);
        for (String w : neutral) sentimentMap.put(w, 0);
        for (String w : negative) sentimentMap.put(w, -2);
        for (String w : veryNegative) sentimentMap.put(w, -3);
    }

    public static String analyzeSentiment(String text) {

        // 🔥 LOWERCASE
        text = text.toLowerCase();

        // 🔥 FIX CONTRACTIONS
        text = text.replace("don't", "dont");
        text = text.replace("didn't", "didnt");
        text = text.replace("do not", "dont");

        // 🔥 REMOVE SYMBOLS (IMPORTANT FIX)
        text = text.replaceAll("[^a-zA-Z\\s]", "");

        // 🔥 HANDLE "BUT" CORRECTLY
        if (text.contains("but")) {
            String[] parts = text.split("but");
            if (parts.length > 1) {
                text = parts[1].trim(); // focus on second part
            }
        }
        if (text.contains("nothing impressive") || text.contains("nothing special"))
            return "Neutral";

        if (text.contains("just okay") || text.contains("just average"))
            return "Neutral";

        if (text.contains("nothing extraordinary") ||
        	    text.contains("nothing special") ||
        	    text.contains("nothing impressive")) {
        	    return "Neutral";
        	}
        // 🔥 STRONG PHRASES
        if (text.contains("dont like") || text.contains("didnt like") || text.contains("not like"))
            return "Negative";

        if (text.contains("not good") || text.contains("not worth"))
            return "Negative";

        if (text.contains("not bad"))
            return "Positive";

        String[] words = text.split("\\s+");

        int score = 0;

        for (int i = 0; i < words.length; i++) {

            // 🔥 CLEAN EACH WORD (CRITICAL FIX)
            String word = words[i].replaceAll("[^a-z]", "");
            if (word.equals("nothing") || word.equals("just") || word.equals("only")) {
                score -= 1; // reduce positivity
            }
            if (word.equals("nothing") && i + 1 < words.length) {
                String next = words[i + 1];

                if (sentimentMap.containsKey(next)) {
                    score -= sentimentMap.get(next); // reverse meaning
                }
                continue;
            }
            // 🔥 NEGATION HANDLING
            if ((word.equals("not") || word.equals("dont") || word.equals("didnt"))
                    && i + 1 < words.length) {

                String next = words[i + 1].replaceAll("[^a-z]", "");

                if (sentimentMap.containsKey(next)) {
                    score -= sentimentMap.get(next);
                }
                continue;
            }

            if (sentimentMap.containsKey(word)) {
                score += sentimentMap.get(word);
            }
        }

        // 🎯 FINAL DECISION
        if (score >= 4) return "Very Positive";
        if (score >= 2) return "Positive";
        if (score <= -4) return "Very Negative";
        if (score <= -1) return "Negative";

        return "Neutral";
    }

    // 🔥 SCORE FOR DATABASE
    public static int getScore(String sentiment) {

        switch (sentiment) {
            case "Very Positive": return 3;
            case "Positive": return 2;
            case "Neutral": return 0;
            case "Negative": return -2;
            case "Very Negative": return -3;
            default: return 0;
        }
    }
}