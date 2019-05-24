class simple {
    public static void main(String[] illegal){
        MyInt a;
        MyInt b;
        int sum;
        System.out.println(true);
        a = new MyInt();
        b = new MyInt();
        System.out.println(true);
        sum = a.setX(1);
        sum = b.setX(3);
        System.out.println(true);
        sum = a.sum(b);
        System.out.println(sum);
    }
}

class MyInt {
    int x;

    public int getX(){
        return x;
    }

    public int setX(int _x){
        x = _x;
        return x;
    }

    public int sum(MyInt another){
        int sum;
        sum = x + (another.getX());
        return sum;
    }
}
