package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class EmployeeAbbreviationGenerator {
    public static String generateAbbreviation(String fullName) {
        String[] names = fullName.split(" ");
        StringBuilder abbreviation = new StringBuilder();

        if (names.length >= 2) {
            abbreviation.append(names[0].substring(0, 1).toLowerCase()); // First letter of the first name
            abbreviation.append(names[1].substring(0, Math.min(2, names[1].length())).toLowerCase()); // First two letters of the last name
        } else if (names.length == 1) {
            abbreviation.append(names[0].substring(0, Math.min(3, names[0].length())).toLowerCase()); // Use the entire name if only one name is provided
        }

        return abbreviation.toString();
    }
}
