class mytest {
    public static void main(String[] illegal){
        boolean bull;
        B b;
        D d;
        int[] arr;
        arr = new int[102];
        b = new C();
        d = new D();
        arr = b.afunct(arr, arr);  // should print 102 cuz virtual
        arr = d.afunct(arr, arr);  // should print 42 cuz inhertitance
        b = new B();
        arr = b.afunct(arr, arr);  // should print 42
        bull = d.checkInheritanceAndArgs();
    }
}

class A extends mytest {
    int[] Afield;

    public int[] afunct(int[] a1, int[] a2){
        a2[0] = a1[1];
        a1 = a2;
        System.out.println(42);
        return new int[2];
    }

}

class B extends A {
    int[] Bfield;

    public int bfunct(int[] aarr, int i){
        return aarr[(i + 1)];
    }

}

class C extends B {
    int[] Cfield;

    public int[] afunct(int[] c1, int[] c2){
        int[] res;
        mytest a;
        System.out.println(102);
        a = new mytest();
        if ((c1[0]) < (c2[1])){
            res = c1;
        } else {
            res = c2;
        }
        return res;
    }

}

class D extends B {
    int[] Dfield;

    public boolean checkInheritanceAndArgs(){
        int[] array;
        int i;
        i = 1;
        array[0] = 4;
        System.out.println(array[0]);
        System.out.println(array[1]);
        System.out.println(array.length);
        //array[array[1]] = (this.afunct(array, array))[1];
        return true;
    }

}


