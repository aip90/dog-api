package com.example.dogapi.singleton;

public class Singleton {

    private static Singleton instance = null;

    public String s;

    private Singleton()
    {
        s = "Hello this is Singleton class";
    }

    public static Singleton getInstance()
    {
        if (instance == null)
            instance = new Singleton();

        return instance;
    }
}
