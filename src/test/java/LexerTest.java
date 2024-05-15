import org.excelparser.parser.formula.FormulaLexer;
import org.excelparser.parser.token.Token;
import org.excelparser.parser.token.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LexerTest {

    @Test
    public void testNumber() {
        FormulaLexer lexer = new FormulaLexer("1");
        var list = lexer.tokenize();
        var expected = List.of(new Token(TokenType.NUMBER, "1"));
        Assertions.assertEquals(list, expected);
    }
}