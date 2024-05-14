import org.example.parser.*;
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