class ExtendMain{
    public static void main(String[] a){
        System.out.println((new Fac2().Init(123)).ComputeFac(456));
    }
}

class Fac extends ExtendMain{
    int i;
}

class Fac2 extends Fac{
    int j;

    public Fac2 Init(int num){
        i = num;
        return this ;
    }

    public int ComputeFac(int num){
        j = num;
        return i + j ;
    }
}
