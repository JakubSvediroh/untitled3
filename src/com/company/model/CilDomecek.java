package com.company.model;

public class CilDomecek
{
    private Hrac komuPatrim;

    private Figurka[] cil;
    private int pocetFigurek;

    public CilDomecek(Hrac b, int pocFigurek) {
        komuPatrim = b;
        pocetFigurek = pocFigurek;

        cil = new Figurka[pocFigurek];
    }

    public boolean jeVolno(int kde) {
        return cil[kde] == null;
    }

    public void jitDoCile(Figurka kdo, int kam) {
        cil[kam] = kdo;
    }

    public void posunVCili(Figurka kdo, int odkud, int kam) {
        cil[kam] = kdo;
        cil[odkud] = null;
    }

    public Figurka[] getCil() {
        return cil;
    }

    public boolean mamTuhleFigurku(Figurka f) {
        boolean ano = false;

        for(int i = 0; i < cil.length; i++) {
            if(cil[i] == f) {
                ano = true;
                break;
            }
        }

        return ano;
    }
}
