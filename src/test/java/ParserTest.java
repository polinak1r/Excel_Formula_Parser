import org.excelparser.parser.expression.Expression;
import org.excelparser.parser.formula.FormulaLexer;
import org.excelparser.parser.formula.FormulaParser;
import org.excelparser.parser.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


class ParserTest {

    private JTable table;

    @BeforeEach
    void setUp() {
        table = new JTable(100, 27);
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 3; j++) {
                table.setValueAt("2", i, j);
            }
        }
    }

    @Test
    void testBasicArithmetic() {
        assertFormulaEquals("-2", -2);
        assertFormulaEquals("1 + 1", 2);
        assertFormulaEquals("5 - 1", 4);
        assertFormulaEquals("2 * 3", 6);
        assertFormulaEquals("6 / 2", 3);
    }

    @Test
    void testComplexExpressionsWithParentheses() {
        assertFormulaEquals("-(-2)", 2);
        assertFormulaEquals("(1 + 2) * 3", 9);
        assertFormulaEquals("2 * (3 + 4)", 14);
        assertFormulaEquals("2 * (2 + 2) / 2", 4);
        assertFormulaEquals("(2 + 3) * (4 - 2)", 10);
    }

    @Test
    void testFunctionCalls() {
        assertFormulaEquals("sin(0)", 0);
        assertFormulaEquals("cos(0)", 1);
        assertFormulaEquals("pow(2, 5)", 32);
        assertFormulaEquals("max(1, 2, 3)", 3);
        assertFormulaEquals("min(1, 2, 3)", 1);
    }

    @Test
    void testCellReferences() {
        assertFormulaEquals("A1", 2);
        assertFormulaEquals("A1 + A2", 4);
        assertFormulaEquals("B1 * C1 * B3 * A2", 16);
        assertFormulaEquals("A1 + B2 * C3", 6);
    }

    void testMixedCases() {
        assertFormulaEquals("pow(-2, A1 - 3) * (42 + B2)", -22);
        assertFormulaEquals("-2 + max(-2, A1 * 3, pow(A1, A2))  *  (42 + B2)", 262);
    }


    private void assertFormulaEquals(String formula, double expected) {
        try {
            FormulaLexer lexer = new FormulaLexer(formula);
            List<Token> tokens = lexer.tokenize();
            FormulaParser parser = new FormulaParser(tokens, table);
            Expression result = parser.parse();
            assertEquals(expected, result.evaluate());
        } catch (Exception e) {
            fail("Exception during formula parsing or evaluation: " + e.getMessage());
        }
    }
}
