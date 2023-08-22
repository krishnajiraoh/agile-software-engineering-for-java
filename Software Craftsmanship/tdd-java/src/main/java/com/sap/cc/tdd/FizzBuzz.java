package com.sap.cc.tdd;

public class FizzBuzz {

    public String print(int i){
        if(i % 3 == 0 && i % 5 == 0){
            return "FizzBuzz";
        }
        if(i % 3 == 0){
            return "Fizz";
        }
        if(i % 5 == 0){
            return "Buzz";
        }
        return Integer.toString(i);

    }
}
