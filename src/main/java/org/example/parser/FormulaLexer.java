package org.example.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaLexer {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+\\.\\d*|\\.\\d+|\\d+");
    private static final Pattern OPERATOR_PATTERN = Pattern.compile("[-+*/]");
    private static final Pattern PARENTHESIS_PATTERN = Pattern.compile("[()]");
    private static final Pattern FUNCTION_PATTERN = Pattern.compile("[a-zA-Z]{2,}");
    private static final Pattern CELL_REF_PATTERN = Pattern.compile("[A-Za-z]\\d+");
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    private final String input;
    private final ArrayList<Token> tokens = new ArrayList<>();
    private int currentIndex = 0;

    public FormulaLexer(String input) {
        this.input = input;
    }

    public ArrayList<Token> tokenize() {
        while (currentIndex < input.length()) {
            char currentChar = input.charAt(currentIndex);

            if (Character.isWhitespace(currentChar)) {
                currentIndex++;
                continue;
            }

            Token token = null;

            token = matchPattern(NUMBER_PATTERN, TokenType.NUMBER);
            if (token == null) token = matchPattern(OPERATOR_PATTERN, TokenType.OPERATOR);
            if (token == null) token = matchPattern(PARENTHESIS_PATTERN, TokenType.PARENTHESIS);
            if (token == null) token = matchPattern(FUNCTION_PATTERN, TokenType.FUNCTION);
            if (token == null) token = matchPattern(CELL_REF_PATTERN, TokenType.CELL_REF);
            if (token == null) token = matchPattern(COMMA_PATTERN, TokenType.COMMA);

            if (token != null) {
                tokens.add(token);
            } else {
                throw new IllegalArgumentException("Unexpected character: " + currentChar);
            }
        }
        return tokens;
    }

    private Token matchPattern(Pattern pattern, TokenType type) {
        Matcher matcher = pattern.matcher(input.substring(currentIndex));
        if (matcher.lookingAt()) {
            String value = matcher.group();
            currentIndex += value.length();
            return new Token(type, value);
        }
        return null;
    }
}
