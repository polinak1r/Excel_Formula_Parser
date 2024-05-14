package org.example.parser;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FormulaParser {
    private List<Token> tokens;

    private JTable table;

    public FormulaParser(List<Token> tokens, JTable table) {
        this.tokens = tokens;
        this.table = table;
    }

    public List<List<Token>> split(List<Token> tokensToSplit, char[] separators) {
        List<List<Token>> expressions = new ArrayList<>();
        List<Token> currentExpressionTokens = new ArrayList<>();
        int openCount = 0;
        int closeCount = 0;

        for (Token currentToken : tokensToSplit) {
            if (isOperator(currentToken.getValue(), '(')) {
                openCount++;
            } else if (isOperator(currentToken.getValue(), ')')) {
                closeCount++;
            }

            boolean isSeparator = false;
            for (char sep : separators) {
                if (isOperator(currentToken.getValue(), sep) && openCount == closeCount) {
                    expressions.add(new ArrayList<>(currentExpressionTokens));
                    currentExpressionTokens.clear();
                    isSeparator = true;
                    break;
                }
            }

            if (!isSeparator) {
                currentExpressionTokens.add(currentToken);
            }
        }

        if (!currentExpressionTokens.isEmpty()) {
            expressions.add(new ArrayList<>(currentExpressionTokens));
        }

        return expressions;
    }

    public Expression parse() {
        Expression result = parseExpression(tokens);
        return result;
    }

    public Expression parseExpression(List<Token> tokensToParse) {
        char[] separators = {'+', '-'};
        List<List<Token>> splitTokens = split(tokensToParse, separators);

        List<Expression> terms = new ArrayList<>();
        for (List<Token> tokenList : splitTokens) {
            terms.add(parseTerm(tokenList));
        }

        Expression result = terms.get(0);
        int tokenIndex = 0;
        for (int i = 1; i < terms.size(); i++) {
            tokenIndex += splitTokens.get(i - 1).size();
            Token operatorToken = tokensToParse.get(tokenIndex);
            char operator = operatorToken.getValue().charAt(0);
            tokenIndex++;

            result = new BinaryExpression(result, terms.get(i), operator);
        }

        return result;
    }


    public Expression parseTerm(List<Token> tokensToParse) {
        char[] separators = {'*', '/'};
        List<List<Token>> splitTokens = split(tokensToParse, separators);

        List<Expression> factors = new ArrayList<>();
        for (List<Token> tokenList : splitTokens) {
            factors.add(parseFactor(tokenList));
        }

        Expression result = factors.get(0);
        int tokenIndex = 0;
        for (int i = 1; i < factors.size(); i++) {
            tokenIndex += splitTokens.get(i - 1).size();
            Token operatorToken = tokensToParse.get(tokenIndex);
            char operator = operatorToken.getValue().charAt(0);
            tokenIndex++;

            result = new BinaryExpression(result, factors.get(i), operator);
        }

        return result;
    }


    private Expression parseFactor(List<Token> tokensToParse) {
        if (tokensToParse.size() == 1) {
            Token token = tokensToParse.get(0);
            switch (token.getType()) {
                case NUMBER:
                    return new NumberExpression(Double.parseDouble(token.getValue()));
                case CELL_REF:
                    return parseCellReference(token.getValue());
                default:
                    throw new IllegalArgumentException("Unexpected token: " + token.getValue());
            }
        } else if (tokensToParse.get(0).getType() == TokenType.PARENTHESIS && tokensToParse.get(0).getValue().equals("(") &&
                tokensToParse.get(tokensToParse.size() - 1).getType() == TokenType.PARENTHESIS && tokensToParse.get(tokensToParse.size() - 1).getValue().equals(")")) {
            return parseExpression(tokensToParse.subList(1, tokensToParse.size() - 1));
        } else if (tokensToParse.get(0).getType() == TokenType.FUNCTION) {
            return parseFunction(tokensToParse);
        } else {
            throw new IllegalArgumentException("Unexpected token sequence: " + tokensToParse);
        }
    }

    private Expression parseFunction(List<Token> tokensToParse) {
        Token functionToken = tokensToParse.get(0);
        String functionName = functionToken.getValue();
        List<Expression> arguments = new ArrayList<>();

        int openParenthesisIndex = tokensToParse.indexOf(new Token(TokenType.PARENTHESIS, "("));
        int closeParenthesisIndex = tokensToParse.lastIndexOf(new Token(TokenType.PARENTHESIS, ")"));
        if (openParenthesisIndex == -1 || closeParenthesisIndex == -1) {
            throw new IllegalArgumentException("Mismatched parentheses in function call: " + functionName);
        }

        List<Token> argumentsTokens = tokensToParse.subList(openParenthesisIndex + 1, closeParenthesisIndex);
        List<List<Token>> splitArgumentsTokens = split(argumentsTokens, new char[]{','});
        for (List<Token> argumentTokens : splitArgumentsTokens) {
            arguments.add(parseExpression(argumentTokens));
        }

        switch (functionName.toLowerCase()) {
            case "sin":
                return new SinExpression(arguments);
            case "cos":
                return new CosExpression(arguments);
            case "pow":
                return new PowExpression(arguments);
            case "max":
                return new MaxExpression(arguments);
            case "min":
                return new MinExpression(arguments);
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }


    private Expression parseCellReference(String cellRef) {
        int column = cellRef.charAt(0) - 'A' + 1;
        int row = Integer.parseInt(cellRef.substring(1)) - 1;
        Object cellValue = table.getValueAt(row, column);
        if (table.getSelectedColumn() == column && table.getSelectedRow() == row) {
            throw new IllegalArgumentException("Cell can't reference to itself");
        }
        try {
            return new NumberExpression(Double.parseDouble(cellValue.toString()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cell reference does not contain a number");
        }
    }

    private boolean isOperator(String value, char operator) {
        return value.length() == 1 && value.charAt(0) == operator;
    }
}