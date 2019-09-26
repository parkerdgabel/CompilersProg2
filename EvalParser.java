import java.util.*;

import java.lang.String;

public class EvalParser {
  Scanner scan;
  private Scanner.Token lookahead;
  private List<Scanner.Token> tokens;
  int tempID = 0;
  String threeAddressResult = "";

  /***************** Three Address Translator ***********************/
  // TODO #2 Continued: Write the functions for E/E', T/T', and F. Return the
  // temporary ID associated with each subexpression and
  // build the threeAddressResult string with your three address translation
  /****************************************/

  private String E3Address() {
    String address = T3Address();
    while (true) {
      if (lookahead.tokenVal.equals("+")) {
        match("+");
        String second = T3Address();
        this.threeAddressResult += op3Address(" + ", address, second);
        address = "temp" + this.tempID;
        this.tempID++;
        continue;
      } else if (lookahead.tokenVal.equals("-")) {
        match("-");
        String second = T3Address();
        this.threeAddressResult += op3Address(" - ", address, second);
        address = "temp" + this.tempID;
        this.tempID++;
        continue;
      }
      break;
    }
    return address;
  }

  private String T3Address() {
    String temporaryID = F3Address();
    while (true) {
      if (lookahead.tokenVal.equals("*")) {
        match("*");
        String second = T3Address();
        String address = op3Address(" * ", temporaryID, second);
        this.threeAddressResult += address;
        this.tempID++;
        continue;
      } else if (lookahead.tokenVal.equals(" / ")) {
        match("/");
        String second = T3Address();
        String address = op3Address(" / ", temporaryID, second);
        this.threeAddressResult += address;
        this.tempID++;
        continue;
      }
      break;
    }
    return temporaryID;
  }

  private String F3Address() {
    String temporaryID = "";
    if (isLookaheadNum()) {
      temporaryID = "temp" + this.tempID;
      String address = num3Address(temporaryID);
      this.threeAddressResult += address;
      this.tempID++;
      match("NUM");
    } else {
      match("(");
      E3Address();
      match(")");
    }
    return temporaryID;
  }

  private String num3Address(String temporaryID) {
    return temporaryID + " = " + this.lookahead.tokenVal + "\n";
  }

  private String op3Address(String op, String firstTemp, String secondTemp) {
    return "temp" + this.tempID + " = " + firstTemp + op + secondTemp + "\n";
  }

  /***************** Simple Expression Evaluator ***********************/
  // TODO #1 Continued: Write the functions for E/E', T/T', and F. Return the
  // expression's value
  /****************************************/

  private int E() {
    int term = T();
    while (true) {
      if (lookahead.tokenVal.equals("+")) {
        match("+");
        term += T();
        continue;
      } else if (lookahead.tokenVal.equals("-")) {
        match("-");
        term -= T();
        continue;
      }
      break;
    }
    return term;
  }

  private int T() {
    int factor = F();
    while (true) {
      if (lookahead.tokenVal.equals("*")) {
        match("*");
        factor *= T();
        continue;
      } else if (lookahead.tokenVal.equals("/")) {
        match("/");
        factor /= T();
        continue;
      }
      break;
    }
    return factor;
  }

  private int F() {
    int factor;
    if (isLookaheadNum()) {
      factor = Integer.parseInt(lookahead.tokenVal);
      match("NUM");
    } else {
      match("(");
      factor = E();
      match(")");
    }
    return factor;
  }

  /* TODO #1: Write a parser that can evaluate expressions */
  public int evaluateExpression(String eval) {
    scan = new Scanner();
    tokens = scan.extractTokens(eval);
    if (tokens.isEmpty())
      parserError();
    lookahead = tokens.remove(0);
    return E();
  }

  /* TODO #2: Now add three address translation to your parser */
  public String getThreeAddr(String eval) {
    this.threeAddressResult = "";
    this.tempID = 0;
    scan = new Scanner();
    tokens = scan.extractTokens(eval);
    if (tokens.isEmpty())
      parserError();
    lookahead = tokens.remove(0);
    E3Address();
    return this.threeAddressResult;
  }

  private void parserError() {
    System.out.println("ParserError: Please Write valid expressions.");
    System.exit(1);
  }

  private boolean isLookaheadNum() {
    try {
      int val = Integer.parseInt(lookahead.tokenVal);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private void match(String t) {
    if (lookahead.tokenVal.equals(t)) {
      if (!tokens.isEmpty())
        lookahead = tokens.remove(0);
    } else if (t.equals("NUM")) {
      if (isLookaheadNum()) {
        if (!tokens.isEmpty())
          lookahead = tokens.remove(0);
      } else {
        parserError();
      }
    } else
      parserError();
  }
}
