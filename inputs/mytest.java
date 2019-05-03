class mytest {
    public static void main(String[] illegal){
        int[] anInteger;
        boolean aBoolean;
        anInteger[2] = 2;
    }
}


class A {
    int i;
    boolean flag;
    int j;
    public int foo() {
        return 4;
    }
    public boolean fa() {
        return true;
    }
}

class B extends A {
    A type;
    int k;
    int j;
    public int foo() {
        return 4;
    }
    public boolean bla() {
        return true;
    }
}

class C extends B {
    B type;

    public int foo() {
        return 4;
    }
    public boolean blimblom(int num) {
        return true;
    }
}


class MySecondTest extends mytest {


    public boolean foo(Car longa){
        return true;
    }

}

class Car {
    int numOfWheels;

    public int moreId(int amount){
        int id;
        id = id + amount;
        return id;
    }

}

class Animal extends Car {
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
        numberOfLegs = this.moreId(id);
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

    public Animal getAnimalObj(){
        Animal dogInside;
        int id;
        dogInside = new Dog();
        id = dogInside.setId(3);
        return dogInside;
    }

}

