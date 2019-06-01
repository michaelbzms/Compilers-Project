class mytest2 {
    public static void main(String[] illegal){
        int[] anInteger;
        boolean aBoolean;
        Car acar;
        Animal a;
        a = new Dog().getAnimalObj();
        System.out.println(a.getId());
        System.out.println(a.geeetAnInt());
        System.out.println(new MySecondTest().foo(new Car()));
    }
}


class A {
    int i;
    boolean flag;
    int j;
    public int foo() {
        mytest2 a;
        a = new mytest2();
        return 4;
    }
    public int fa() {
        return 2;
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
        int num;
        num = 1;
        num = (new C().blimblom(num)).fa();
        return num;
    }

    public int fa(){
        return 1;
    }

    public B blimblom(int num) {
        return new B();
    }
}


class MySecondTest extends mytest2 {


    public int foo(Car longa){
        int newid;
        newid = longa.moreId(13);
        return newid;
    }

}

class Car {
    int numOfWheels;

    public int moreId(int amount){
        int id;
        int[] arr;
        boolean a;
        a = true;
        id = 10;
        if (! a) {
            arr = new int[id];
            id = arr.length;
        } else
            id = id + amount;
        return id;
    }

}

class truck extends Car {

}

class Animal  {
    int id;

    MySecondTest f;


    public int setId(int _id) {
        id = _id;
        return id;
    }

    public int createId(int num1, int num2, int num3){
        int num;
        num = num1 + num2;
        return num + num3;
    }

    public int geeetAnInt(){
        return 0;
    }

    public int getId(){
        return id;
    }

}

class Dog extends Animal {

    int numberOfLegs;

    public int getId(){
        System.out.println(102);
        return id;
    }


    public int moreId(int amount){
        int id;
        int[] arr;
        int a;
        a = f.foo(new truck());
        id = 10;
        if (false) {
            arr = new int[id];
            id = arr.length;
        } else
            id = id + amount;
        return id;
    }

    public boolean informLegs(){
        Animal animal;
        numberOfLegs = this.moreId(id);
        animal = this.giveAnimalgetDog(new Dog());
        return true;
    }

    public Animal giveAnimalgetDog(Animal animal){
        return new Dog();
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

