import org.excelparser.parser.expression.Expression;
import org.excelparser.parser.formula.FormulaLexer;
import org.excelparser.parser.formula.FormulaParser;
import org.excelparser.parser.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


public class ParserExceptionTest {
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
    void testEmptyExpression() {
        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("");
        });
    }

    @Test
    void testMismatchedParentheses() {
        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("(1 + 2");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("1 + 2)");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula(")1 + 2(");
        });
    }

    @Test
    void testUnexpectedToken() {
        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("1 + * 2");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("2 ** 2");
        });
    }

    @Test
    void testUnknownFunction() {
        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("unknownFunc(1, 2)");
        });
    }

    @Test
    void testInvalidCellReference() {
        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("A101");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parseFormula("A0");
        });
    }


    private void parseFormula(String formula) {
        parseFormula(formula, -1, -1);
    }

    private void parseFormula(String formula, int row, int column) {
        try {
            FormulaLexer lexer = new FormulaLexer(formula);
            List<Token> tokens = lexer.tokenize();
            FormulaParser parser = new FormulaParser(tokens, table);
            if (row >= 0 && column >= 0) {
                table.setValueAt(formula, row, column);
            }
            Expression result = parser.parse();
            result.evaluate();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
