package com.company.model;

public class Hrac
{
    private int poradi;
    public int getPoradi() {return poradi;}
    private int startovniPole;
    private int vstupDoCile;

    public Hrac(int p, int s, int v) {
        poradi = p;
        startovniPole = s;
        vstupDoCile = v;
    }

    public int getStartovniPole(){return startovniPole;}
    public int getVstupDoCile(){return vstupDoCile;}
}
