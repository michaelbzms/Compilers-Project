class args {
    public static void main(String [] s){
        args v;
        v = new args();
        System.out.println(new Callable().call3(10, new int[10], new Callable()));
    }
}


class Callable {

    public int call3(int num, int[] arr, Callable ref){
        arr[7] = 7;
        System.out.println(arr[7]);
        return 0;
    }

}