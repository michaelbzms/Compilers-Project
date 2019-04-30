class mytest {
    public static void main(String[] a){
        int anInteger;
        boolean aBoolean;
    }
}


class MySecondTest extends mytest {

    public boolean foo(){
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

