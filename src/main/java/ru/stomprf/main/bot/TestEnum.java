package ru.stomprf.main.bot;

public enum TestEnum {

    JUPITER(3, 15),
    NEPTUN(5, 10);

    public int firstValue;
    private int secondValue;


    TestEnum(int val1, int val2){
        this.firstValue = val1;
        this.secondValue = val2;
    }

    public void returnFirstValue(){
        System.out.println("Object is:" + this.toString() + "\n Value is: " + firstValue );
    }

    @Override
    public String toString() {
        return "TestEnum{" +
                "firstValue=" + firstValue +
                ", secondValue=" + secondValue +
                '}';
    }
}
