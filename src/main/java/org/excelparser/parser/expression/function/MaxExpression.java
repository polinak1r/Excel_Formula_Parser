package org.excelparser.parser.expression.function;

import org.excelparser.parser.expression.Expression;

import java.util.List;

public class MaxExpression extends FunctionExpression {
    public MaxExpression(List<Expression> arguments) {
        super(arguments);
    }

    @Override
    public double evaluate() {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("MAX function takes at least one argument");
        }
        return arguments.stream().mapToDouble(Expression::evaluate).max().orElseThrow(IllegalArgumentException::new);
    }
}
