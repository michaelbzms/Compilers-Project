class mytest {
    public static void main(String[] a){
        int[] anInteger;
        boolean aBoolean;
        anInteger[2] = 3;
    }
}


class MySecondTest extends mytest {


    public boolean foo(Car longa){
        return true;
    }

}

class Car {
    int numOfWheels;
}

class Animal {
    int id;

    public int setId(int _id) {
        id = _id;
        return id;
    }

    public int createId(int num1, int num2, int num3){
        int num;
        num = num1 + num2;
        return num + num3;
    }

    public int getId(){
        return id;
    }

}

class Dog extends Animal {

    int numberOfLegs;

    public boolean informLegs(){
        numberOfLegs = 4;
        return true;
    }

    public int geeetAnInt(){
        int[] a;
        a = new int[1];
        a[0] = 2;
        return this.createId(1, a[0], 3);
    }

    public int getNumberOfLegs(){
        return numberOfLegs;
    }

    public int moreId(){
        id = id + 1;
        return id;
    }

    public Animal getAnimalObj(){
        Animal dogInside;
        int id;
        dogInside = new Dog();
        id = dogInside.setId(3);
        return dogInside;
    }

}

