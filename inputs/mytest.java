class mytest {
    public static void main(String[] illegal){
        int i;
        boolean b;
        int[] arr;
        System.out.println(i);
        System.out.println(i < (i+1));
    }
}

class A extends mytest {
    int[] Afield;

    public int[] afunct(int[] a1, int[] a2){
        a2[0] = a1[1];
        a1 = a2;
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
        int[] arr;
        arr[arr[1]] = (this.afunct(arr, arr))[2];
        return true;
    }

}


