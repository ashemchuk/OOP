package ru.ashemchuk.expression;

public abstract class Expression {
    public abstract Expression derivative(String var);
    public abstract Expression eval(String signification);
    public abstract Expression simplify();

    @Override
    public abstract String toString();

    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    public void print (){
        System.out.println(this);
    }
}
