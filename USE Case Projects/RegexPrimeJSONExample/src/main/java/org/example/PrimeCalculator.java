package org.example;


public class PrimeCalculator implements Runnable{
    int Value;
    public PrimeCalculator(int x){
        this.Value = x;
    }
    char[] Array = new char[500];
    public boolean isPrime(int k){
        if(k == 2){
            return true;
        }
        for(int i = 3;i<=k;i++){
            if(k%i == 0){
                return true;
            }
        }
        return false;
    }

    public void run(){
        boolean flag = isPrime(Value);
    }

}
