package com.company.model;
import java.util.*;
public class CilDomecek {
private Barva barva;

    private Map <Integer, Figurka> cil =new HashMap<>();
    public Map<Integer,Figurka> getCil(){
        return cil;
    }
    public CilDomecek (Barva barva1,int pocetF){
     barva = barva1;

    }

    public boolean jeFigurka(Figurka f){
        return cil.containsValue(f);
    }

    public boolean mista(int kde) {
        return cil.containsKey(kde);
    }
    public void DoDomecku(Figurka kdo,int kam){
        cil.put(kam, kdo);
        if (cil.size()==4){
        }
    }
    public boolean Volno(int kde) {
        return cil.containsKey(kde);
    }
}
