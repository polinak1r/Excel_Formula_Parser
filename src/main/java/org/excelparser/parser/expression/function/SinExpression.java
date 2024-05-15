package org.excelparser.parser.expression.function;

import org.excelparser.parser.expression.Expression;

import java.util.List;

public class SinExpression extends FunctionExpression {
    public SinExpression(List<Expression> arguments) {
        super(arguments);
    }

    @Override
    public double evaluate() {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("SIN function takes exactly one argument");
        }
        return Math.sin(arguments.get(0).evaluate());
    }
}