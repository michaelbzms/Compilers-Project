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
}