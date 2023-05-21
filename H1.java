package cen3024;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class H1 {
    public static void main(String[] args) {
        String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

        try {
            Map<String, Integer> WordFre = getWFreq(url);
            DisplayWordFreq(WordFre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> getWFreq(String url) throws IOException {
        Map<String, Integer> WordFre = new HashMap<>();

        Document doc = Jsoup.parse(new URL(url), 5000);
        Elements paragraphElements = doc.select("p");

        StringBuilder poemBuilder = new StringBuilder();
        for (Element paragraphElement : paragraphElements) {
            String paragraphText = paragraphElement.text();
            poemBuilder.append(paragraphText);
            poemBuilder.append(" ");
        }

        String poemText = poemBuilder.toString();
        String strippedText = RemmoveHtmlT(poemText);

    
        String[] words = strippedText.split("[^a-zA-Z!]+");
        for (String word : words) {         
            if (word.endsWith("!")) {          
                word = word.substring(0, word.length() - 1);
            }
            word = word.toLowerCase();
            if (!word.isEmpty()) {
                WordFre.put(word, WordFre.getOrDefault(word, 0) + 1);
            }
        }
        
        
        return WordFre;
    }

    private static String RemmoveHtmlT(String text) {
       
        return text.replaceAll("\\<.*?\\>", "");
    }

    private static void DisplayWordFreq(Map<String, Integer> WordFre) {
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        pq.addAll(WordFre.entrySet());

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> entry = pq.poll();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
