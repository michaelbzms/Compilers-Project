class test4 {
    public static void main(String[] illegal){
        System.out.println(new Obj().callStuff());
    }
}


class Methods {
    public boolean bool(){
        return true;
    }

    public int integer(){
        return 102;
    }

    public int[] intarray(){
        int i;
        int[] p;
        p = new int[100];
        i = 0;
        while (i < 100){
            p[i] = i + 1;
            i = i + 1;
        }
        return p;
    }

    public Methods getThis(){
        return this;
    }

    public Obj getObject(){
        Obj o;
        o = new Obj();
        return o;
    }

}


class Obj {
    int x;
    int y;
    boolean b;
    int[] arr;
    Obj next;
    Methods m;

    public boolean callStuff(){
        boolean b;
        Methods methods;
        m = new Methods();
        b = m.bool();
        System.out.println(b);
        x = m.integer();
        System.out.println(x);
        y = x + (m.integer());
        System.out.println(y);
        arr = m.intarray();
        next = m.getObject();
        methods = m.getThis();
        return (!(b) && (next.callStuff()));
    }
}
