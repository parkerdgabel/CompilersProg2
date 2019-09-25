import java.security.Permission;

public class ScannerTest{

  static int numTests = 0;

  class MySecurityManager extends SecurityManager {
    @Override public void checkExit(int status) {
      throw new SecurityException();
    }

    @Override public void checkPermission(Permission perm) {
        // Allow other activities by default
    }
  }

  /* Quick test of token extraction*/
  public static int testTokenExtraction(){
    System.out.println("Total Score: ");
    System.out.println("Automata: ");
    System.out.println("Code: ");
    System.out.println();
    System.out.println("*******************************************");
    System.out.println("Testing Token Extraction");
    Scanner test = new Scanner();
    int points = 0;
    int total = 0;

    String result = test.extractTokens("123");
    String expected = "|NUM: 123|";
    System.out.println("Testing: 123");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Error, Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("+ 3 3");
    expected = "|PLUS: +||NUM: 3||NUM: 3|";
    System.out.println("Testing: + 3 3");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("+ -");
    expected = "|PLUS: +||MINUS: -|";
    System.out.println("Testing: + -");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("* /");
    expected = "|MUL: *||DIV: /|";
    System.out.println("Testing: * /");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("< >");
    expected = "|LT: <||GT: >|";
    System.out.println("Testing: < >");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("<= >=");
    expected = "|LTE: <=||GTE: >=|";
    System.out.println("Testing: <= >=");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("<= 9 9 9     	9");
    expected = "|LTE: <=||NUM: 9||NUM: 9||NUM: 9||NUM: 9|";
    System.out.println("Testing: <= 9 9 9       9");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("	12	2 3 -+ *9  \n <=");
    expected = "|NUM: 12||NUM: 2||NUM: 3||MINUS: -||PLUS: +||MUL: *||NUM: 9||LTE: <=|";
    System.out.println("Testing:        12      2 3 -+ *9  \n <=");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("<=>=>=<\n<=");
    expected = "|LTE: <=||GTE: >=||GTE: >=||LT: <||LTE: <=|";
    System.out.println("Testing: <=>=>=<\n<=");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    result = test.extractTokens("12/\n34<\n><=+-");
    expected = "|NUM: 12||DIV: /||NUM: 34||LT: <||GT: >||LTE: <=||PLUS: +||MINUS: -|";
    System.out.println("Testing: 12/\n34<\n><=+-");
    System.out.println("Expecting: " + expected);
    if(result.equals(expected)){
      System.out.println("Test case passed");
      points++;
    } else {
      System.out.println("Got: " + result);
    }
    System.out.println();
    total++;

    numTests = total;
    System.out.println("Got: " + points + "/" + total + " for non error test cases");
    System.out.println("*******************************************");
    System.out.println();
    return points;
  }

  public ScannerTest(){
    int testsCorrect = testTokenExtraction();
    MySecurityManager secManager = new MySecurityManager();
    System.setSecurityManager(secManager);
    ScannerErrorTest t1 = new ScannerErrorTest();
    try {
      t1.main(new String[]{"1"});
    } catch (SecurityException e){
      System.out.println();
    }
    try {
      t1.main(new String[]{"2"});
    } catch (SecurityException e){
      System.out.println();
    }
    try {
      t1.main(new String[]{"3"});
    } catch (SecurityException e){
      System.out.println();
    }
    try {
      t1.main(new String[]{"4"});
    } catch (SecurityException e){
      System.out.println();
    }
    try {
      t1.main(new String[]{"5"});
    } catch (SecurityException e){
      System.out.println();
    }

  }

  public static void main(String[] args){
    ScannerTest scan = new ScannerTest();
  }

}
