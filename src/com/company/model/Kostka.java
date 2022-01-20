package com.company.model;

import java.util.Random;

public class Kostka
{
    private Random random = new Random();

    private int pocetStran;

    public int getPocetStran()
    {
        return pocetStran;
    }

    public Kostka(int strany){
        pocetStran = strany;
    }

    public int hod() {
        return random.nextInt(pocetStran)+1;
    }
}
