package org.excelparser.parser.expression.function;

import org.excelparser.parser.expression.Expression;

import java.util.List;

public class PowExpression extends FunctionExpression {
    public PowExpression(List<Expression> arguments) {
        super(arguments);
    }

    @Override
    public double evaluate() {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("POW function takes exactly two arguments");
        }
        return Math.pow(arguments.get(0).evaluate(), arguments.get(1).evaluate());
    }
}
