class mytest {
    public static void main(String[] illegal){
        Point s;
        Point n;
        int[] arr;
        int i;
        int sum;
        s = new Point();
        n = s.getNew(1, 2);
        sum = n.sum();
        System.out.println(sum);
        ////////////////////////
        arr = new int[sum];
        i = 0;
        while (i < (arr.length)){
            arr[i] = i + 1;
            System.out.println(arr[i]);
            i = i + 1;
        }
        ////////////////////////
        if (!!!false){
            System.out.println(true);
        } else {
            System.out.println(false);
        }
        ////////////////////////
        i = s.lotsOfArgsTest(10,20,30,40,50);
    }
}

class Point{
    int x;
    int y;

    public int sum(){
        int res;
        res = x + y;
        return res;
    }

    public int setX(int _x){
        x = _x;
        return x;
    }

    public int setY(int _y){
        y = _y;
        return y;
    }

    public Point getNew(int i, int j){
        int pff;
        Point newpoint;
        newpoint = new Point();
        pff = newpoint.setX(i);
        pff = newpoint.setY(j);
        return newpoint;
    }


    public int lotsOfArgsTest(int one, int two, int three, int four, int five){
        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
        System.out.println(four);
        System.out.println(five);
        return 0;
    }
}