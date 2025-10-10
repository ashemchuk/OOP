package ru.ashemchuk;

import java.util.Scanner;
import ru.ashemchuk.expression.Expression;
import ru.ashemchuk.parser.Parser;
import ru.ashemchuk.parser.Tokenizer;


public class Main {
    public static void main(String[] strings) {
        Scanner console = new Scanner(System.in);
        Tokenizer tokenizer = new Tokenizer(console.nextLine());
        Parser parser = new Parser(tokenizer);
        Expression ex = parser.parseExpression();
        ex.print();
        ex.derivative("x").print();
        ex.simplify().print();
        ex.eval("x=3").print();
    }
}
