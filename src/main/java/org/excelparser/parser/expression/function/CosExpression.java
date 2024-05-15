package org.excelparser.parser.expression.function;

import org.excelparser.parser.expression.Expression;

import java.util.List;


public class CosExpression extends FunctionExpression {
    public CosExpression(List<Expression> arguments) {
        super(arguments);
    }

    @Override
    public double evaluate() {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("COS function takes exactly one argument");
        }
        return Math.cos(arguments.get(0).evaluate());
    }
}
