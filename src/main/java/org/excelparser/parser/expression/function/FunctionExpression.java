package org.excelparser.parser.expression.function;

import org.excelparser.parser.expression.Expression;

import java.util.List;

abstract class FunctionExpression extends Expression {
    protected List<Expression> arguments;

    public FunctionExpression(List<Expression> arguments) {
        this.arguments = arguments;
    }
}

