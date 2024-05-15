package org.excelparser.parser.expression.function;

import org.excelparser.parser.expression.Expression;

import java.util.List;

public class MinExpression extends FunctionExpression {
    public MinExpression(List<Expression> arguments) {
        super(arguments);
    }

    @Override
    public double evaluate() {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("MIN function takes at least one argument");
        }
        return arguments.stream().mapToDouble(Expression::evaluate).min().orElseThrow(IllegalArgumentException::new);
    }
}
