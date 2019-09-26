import java.util.ArrayList;
import java.util.List;

public class Scanner {

    private char peek;
    private static char[] nonDigitTerminals = { '+', '-', '*', '/', '(', ')' };

    enum TokenType {
        NUM, PLUS, MINUS, MUL, DIV, LPAREN, RPAREN;
    }

    class Token {
        TokenType tokenType;
        String tokenVal;

        public Token(TokenType tokenType, String tokenVal) {
            this.tokenType = tokenType;
            this.tokenVal = tokenVal;
        }

        public String toString() {
            return "|" + this.tokenType + ": " + this.tokenVal + "|";
        }
    }

    private static void printErrorAndExit() {
        System.out.println("TokenError: That Token is wrong");
        System.exit(1);
    }

    /**
     * 
     * Pulls the first char from the stream and sets this.peek to it.**
     * 
     * @param stream
     */

    private void readChar(StringBuilder stream) {
        if (stream.length() != 0) {
            this.peek = stream.charAt(0);
            stream.deleteCharAt(0);
        } else
            this.peek = ' ';
    }

    /**
     * Checks if peek is an operand
     * 
     * @return true if peek is an operand false otherwise
     */
    private boolean peekIsOperand() {
        for (char op : nonDigitTerminals) {
            if (op == this.peek)
                return true;
        }
        return false;
    }

    /**
     * Checks if peek is a whitespace char
     * 
     * @return True if peek is whitespace else false
     */
    private boolean peekIsWhitespace() {
        return (this.peek == ' ') || (this.peek == '\t') || (this.peek == '\n');
    }

    /**
     * Advances stream past all whitespace
     * 
     * @param stream The Token stream
     */
    private void advancePastState0(StringBuilder stream) {
        for (;; readChar(stream)) {
            if (peekIsWhitespace()) {
                continue;
            } else if (stream.length() == 0)
                break;
            else
                break;
        }
    }

    /**
     * Collects all contiguous digits from stream
     * 
     * @param stream The Token Stream
     * @return The digit token
     */
    private Token advancePastState1(StringBuilder stream) {
        StringBuilder v = new StringBuilder(String.valueOf(peek));
        while (true) {
            readChar(stream);
            if (!Character.isDigit(this.peek))
                break;
            v.append(String.valueOf(this.peek));
        }
        return new Token(TokenType.NUM, v.toString());
    }

    /**
     * Collects an operand from the token stream
     * 
     * @param stream The Token stream
     * @return The operand Token
     */
    private Token advancePastState2() {
        switch (this.peek) {
        case '+':
            this.peek = ' ';
            return new Token(TokenType.PLUS, "+");
        case '-':
            this.peek = ' ';
            return new Token(TokenType.MINUS, "-");
        case '*':
            this.peek = ' ';
            return new Token(TokenType.MUL, "*");
        case '/':
            this.peek = ' ';
            return new Token(TokenType.DIV, "/");
        case '(':
            this.peek = ' ';
            return new Token(TokenType.LPAREN, "(");
        case ')':
            this.peek = ' ';
            return new Token(TokenType.RPAREN, ")");
        default:
            printErrorAndExit();
        }
        return null;
    }

    /**
     * Extracts a single token from the input stream
     * 
     * @param stream The Token Stream
     * @return A Token
     */
    public Token extractToken(StringBuilder stream) {
        advancePastState0(stream);
        Token tok = null;
        if (Character.isDigit(this.peek))
            return advancePastState1(stream);
        else if (peekIsOperand())
            return advancePastState2();
        else {
            printErrorAndExit();
        }
        return tok;
    }

    /**
     * Extracts all tokens in a Token stream
     * 
     * @param arg The string to lex
     * @return A string with all Tokens appended
     */
    public List<Token> extractTokens(String arg) {
        if (arg.isEmpty()) {
            printErrorAndExit();
        }
        List<Token> result = new ArrayList<>();
        StringBuilder stream = new StringBuilder(arg);
        readChar(stream);
        while (stream.length() != 0) {
            Token nextToken = extractToken(stream);
            result.add(nextToken);
        }
        if (Character.isDigit(this.peek))
            result.add(new Token(TokenType.NUM, Character.toString(this.peek)));
        else if (peekIsOperand())
            result.add(advancePastState2());
        else if (this.peek != ' ')
            printErrorAndExit();
        return result;
    }

}
