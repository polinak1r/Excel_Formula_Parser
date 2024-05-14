package org.example.parser;

import java.util.List;

abstract class FunctionExpression extends Expression {
    protected List<Expression> arguments;

    public FunctionExpression(List<Expression> arguments) {
        this.arguments = arguments;
    }
}

class SinExpression extends FunctionExpression {
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

class CosExpression extends FunctionExpression {
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

class PowExpression extends FunctionExpression {
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

class MaxExpression extends FunctionExpression {
    public MaxExpression(List<Expression> arguments) {
        super(arguments);
    }

    @Override
    public double evaluate() {
        if (arguments.size() < 1) {
            throw new IllegalArgumentException("MAX function takes at least one argument");
        }
        return arguments.stream().mapToDouble(Expression::evaluate).max().orElseThrow(IllegalArgumentException::new);
    }
}

class MinExpression extends FunctionExpression {
    public MinExpression(List<Expression> arguments) {
        super(arguments);
    }

    @Override
    public double evaluate() {
        if (arguments.size() < 1) {
            throw new IllegalArgumentException("MIN function takes at least one argument");
        }
        return arguments.stream().mapToDouble(Expression::evaluate).min().orElseThrow(IllegalArgumentException::new);
    }
}
