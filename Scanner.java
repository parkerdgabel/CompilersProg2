public class Scanner {

    private char peek;
    private static char[] operands = { '+', '-', '*', '/', '<', '>' };

    enum TokenType {
        NUM, PLUS, MINUS, MUL, DIV, LT, LTE, GT, GTE;
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
        System.err.println("TokenError: Last Token was incorrect.");
        System.out.println("TokenError: Last Token was incorrect.");
        System.exit(1);
    }

    /** Pulls the first char from the stream and sets this.peek to it.
     * @param stream
     */
    private void readChar(StringBuilder stream) {
        if (stream.length() != 0) {
            this.peek = stream.charAt(0);
            stream.deleteCharAt(0);
        }
    }

    /** Checks if peek is an operand
     * @return true if peek is an operand false otherwise
     */
    private boolean peekIsOperand() {
        for (char op: operands) {
            if (op == this.peek)
                return true;
        }
        return false;
    }

    /** Checks if peek is a whitespace char
     * @return True if peek is whitespace else false
     */
    private boolean peekIsWhitespace() {
        return (this.peek == ' ') || (this.peek == '\t') || (this.peek == '\n');
    }

    /** Advances stream past all whitespace
     * @param stream The Token stream
     */
    private void advancePastState0(StringBuilder stream) {
        for (;; readChar(stream)) {
            if (peekIsWhitespace())
                continue;
            else if (stream.length() == 0)
                break;
            else
                break;
        }
    }

    /** Collects all contiguous digits from stream
     * @param stream The Token Stream
     * @return The digit token
     */
    private Token advancePastState1(StringBuilder stream) {
        StringBuilder v = new StringBuilder(String.valueOf(peek));
        while (stream.length() != 0) {
            readChar(stream);
            if (!Character.isDigit(this.peek))
                break;
            v.append(String.valueOf(this.peek));
        }
        return new Token(TokenType.NUM, v.toString());
    }

    /** Collects an operand from the token stream
     * @param stream The Token stream
     * @return The operand Token
     */
    private Token advancePastState2(StringBuilder stream) {
        switch (this.peek) {
            case '+':
                readChar(stream);
                return new Token(TokenType.PLUS, "+");
            case '-':
                readChar(stream);
                return new Token(TokenType.MINUS, "-");
            case '*':
                readChar(stream);
                return new Token(TokenType.MUL, "*");
            case '/':
                readChar(stream);
                return new Token(TokenType.DIV, "/");
            case '<':
                readChar(stream);
                if (peek == '=')
                    return new Token(TokenType.LTE, "<=");
                else
                    return new Token(TokenType.LT, "<");
            case '>':
                readChar(stream);
                if (peek == '=')
                    return new Token(TokenType.GTE, ">=");
                else
                    return new Token(TokenType.GT, ">");
            default:
                printErrorAndExit();
        }
        return null;
    }

    /** Extracts a single token from the input stream
     * @param stream The Token Stream
     * @return A Token
     */
    public Token extractToken(StringBuilder stream) {
        this.peek = ' ';
        advancePastState0(stream);
        Token tok = null;
        if(Character.isDigit(this.peek))
            return advancePastState1(stream);
        else if(peekIsOperand())
            return advancePastState2(stream);
        else {
            printErrorAndExit();
        }
        return tok;
    }

    /** Extracts all tokens in a Token stream
     * @param arg The string to lex
     * @return A string with all Tokens appended
     */
    public String extractTokens(String arg) {
        String result = "";
        StringBuilder stream = new StringBuilder(arg);
        while (stream.length() != 0) {
            Token nextToken = extractToken(stream);
            result += nextToken.toString();
        }
        return result;
    }

}

