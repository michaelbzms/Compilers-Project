package Util;

public class MyPair<A, B> {
    private final A a;
    private final B b;

    public MyPair(A _a, B _b){
        a = _a;
        b = _b;
    }

    public A getFirst() {
        return a;
    }

    public B getSecond(){
        return b;
    }
}
