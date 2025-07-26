package com.ca.election.notification.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTemplate {
    public static void main(String[] args) {
        String template = """
                Hello <input text="vijay" />, your order <input text="order-001" /> is confirmed.
                """;

        // Regex to match text="..."
        Pattern pattern = Pattern.compile("text=\"(.*?)\"");
        Matcher matcher = pattern.matcher(template);

        List<String> keys = new ArrayList<>();

        while (matcher.find()) {
            String key = matcher.group(1); // Extract key inside ${...}
            keys.add(key);
        }

        System.out.println("Extracted Keys: " + keys);
    }
}
