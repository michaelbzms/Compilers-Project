class test1 {
    public static void main(String[] illegal){
        base ptr;
        derived1 ptr1;
        ptr = new base();
        System.out.println(ptr.method());
        ptr = new derived1();
        System.out.println(ptr.method());
        ptr = new derived2();
        System.out.println(ptr.method());
        ptr = new derived11();
        System.out.println(ptr.method());
    }
}


class base {
    int val;

    public int method(){
        return val;
    }
}

class derived1 extends base {
    int val;

//    public int method(){
//        val = 1;
//        return val;
//    }
}

class derived2 extends base {
    int val;

    public int method(){
        val = 2;
        return val;
    }
}

class derived11 extends derived1 {
    int val;

    public int method(){
        val = 11;
        return val;
    }
}
