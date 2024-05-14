package org.example.parser;

public abstract class Expression {
    public abstract double evaluate();
}

class NumberExpression extends Expression {
    private double value;

    public NumberExpression(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}

class BinaryExpression extends Expression {
    private Expression left;
    private Expression right;
    private char operator;

    public BinaryExpression(Expression left, Expression right, char operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public double evaluate() {
        switch (operator) {
            case '+': return (left.evaluate() + right.evaluate());
            case '-': return left.evaluate() - right.evaluate();
            case '*': return left.evaluate() * right.evaluate();
            case '/':
                double rightEval = right.evaluate();
                if (rightEval == 0) throw new ArithmeticException("Division by zero");
                return left.evaluate() / rightEval;
            default: throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
