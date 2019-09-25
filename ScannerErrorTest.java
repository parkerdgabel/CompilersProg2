public class ScannerErrorTest{

  public static void testErrorOne(){
    System.out.println("*******************************************");
    System.out.println("Testing Errors");
    System.out.println("Testing: @");
    Scanner test = new Scanner();
    String result = test.extractTokens("@");
    System.out.println("Error Test Case (should report an error): " + result);
    System.out.println();
  }

  public static void testErrorTwo(){
    System.out.println("*******************************************");
    System.out.println("Testing Errors");
    System.out.println("Testing: 1.2");
    Scanner test = new Scanner();
    String result = test.extractTokens("1.2");
    System.out.println("Error Test Case (should report an error): " + result);
    System.out.println();
  }

  public static void testErrorThree(){
    System.out.println("*******************************************");
    System.out.println("Testing Errors");
    System.out.println("Testing: <-=");
    Scanner test = new Scanner();
    String result = test.extractTokens("<-=");
    System.out.println("Error Test Case (should report an error): " + result);
    System.out.println();
  }

  public static void testErrorFour(){
    System.out.println("*******************************************");
    System.out.println("Testing Errors");
    System.out.println("Testing: \"This string isn't allowed!\"");
    Scanner test = new Scanner();
    String result = test.extractTokens("\"This string isn't allowed!\"");
    System.out.println("Error Test Case (should report an error): " + result);
    System.out.println();
  }

  public static void testErrorFive(){
    System.out.println("*******************************************");
    System.out.println("Testing Errors");
    System.out.println("Testing: &&");
    Scanner test = new Scanner();
    String result = test.extractTokens("&&");
    System.out.println("Error Test Case (should report an error): " + result);
    System.out.println();
  }

  public static void main(String[] args){
    if(args[0].equals("1")){
      testErrorOne();
    } else if(args[0].equals("2")){
      testErrorTwo();
    } else if(args[0].equals("3")){
      testErrorThree();
    } else if(args[0].equals("4")){
      testErrorFour();
    } else {
      testErrorFive();
    }
  }

}
