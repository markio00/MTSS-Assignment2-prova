////////////////////////////////////////////////////////////////////
// Marco Marchiante 1222397
// Michele Bettin 1216735
////////////////////////////////////////////////////////////////////


package it.unipd.mtss;

public class Calculator {
    public int evaluate(String expression) {
        int sum = 0;
        for(String summand : expression.split("\\+")) {
            sum += Integer.valueOf(summand);
        }
        return sum;
    }

}
