package org.example;

import java.util.ArrayList;

public class ExampleJavaObject {
    ArrayList<Integer>Array;
    public ArrayList<Integer> getArray() {
        return Array;
    }

    public void setArray(ArrayList<Integer> array) {
        Array = array;
    }

    public char[] getCharacterArray() {
        return CharacterArray;
    }

    public void setCharacterArray(char[] characterArray) {
        CharacterArray = characterArray;
    }

    public int getPersist() {
        return Persist;
    }

    public void setPersist(int persist) {
        Persist = persist;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    char [] CharacterArray = new char[5000];
    int Persist;
    int second;
    public ExampleJavaObject(){
        int i = 0;
        this.Array = new ArrayList<Integer>();
        for(int j = 0;j<8000;j++){
            Array.add((Integer) i);
            i++;
        }
        for(int j = 0;j<5000;j++){
            CharacterArray[j] = 'a';
        }
        Persist = 545;
        second = 2;
    }
}
