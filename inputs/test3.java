class test2 {
    public static void main(String[] illegal){
        boolean cond;
        int i;
        int max;
        int[] arr;
        ////////////
        cond = true;
        max = 20;
        arr = new int[max];
        i = 0;
        while (i < max) {
            arr[i] = i + 1;
            i = i + 1;
        }
        i = 1;
        //while ((cond && (i < max)) && ((arr[i]) < 14223)){
        while ((i < 2) && (new print().print())){
            if (((i*i) < (max + 1)) && ((max - 1) < (i*i))){
                cond = false;
            } else { }
            i = i + 1;
        }
        //System.out.println(i);
        //System.out.println(max);
    }
}


class print{
    public boolean print(){
        System.out.println(102);
        return true;
    }
}

//class base {
//    int val;
//
//    public int method(){
//        return val;
//    }
//}
//
//class derived1 extends base {
//    int val;
//
//}
//
//class derived2 extends base {
//    int val;
//
//    public int method(){
//        val = 2;
//        return val;
//    }
//}
//
//class derived11 extends derived1 {
//    int val;
//
//    public int method(){
//        val = 11;
//        return val;
//    }
//}
