class mytest {
    public static void main(String[] a){

    }
}

class Car {
    int numOfWheels;
}

class Animal {
    int id;

    public boolean setId(int _id) {
        id = _id;
        return true;
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
        Dog dog;
        dog = new Car();
        return dog;
    }

}

