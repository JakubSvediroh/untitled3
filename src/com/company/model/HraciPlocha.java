package com.company.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HraciPlocha {
    private Figurka[] plocha;
    public Figurka[] getPlocha(){
        return plocha;
    }

    private int delkaPlochy;

    private List<Hrac> hraci = new ArrayList<>();
    private int pocetHracu;

    public List<Hrac> getHraci() {
        return hraci;
    }

    private Map<Hrac, StartDomecek> domecky = new HashMap<>();
    private Map<Hrac, CilDomecek> cile = new HashMap<>();

    public Map<Hrac, StartDomecek> getDomecky() {
        return domecky;
    }
    public Map<Hrac, CilDomecek> getCile() {
        return cile;
    }

    private int pocetFigurek;
    private Kostka kostka;
    private boolean hozeno;
    private int naKostce;
    private int znovu;

    public Hrac hrajiciHrac;

    public HraciPlocha(int vel, int pocFig, int pocHracu, Kostka k) {
        delkaPlochy = vel;
        pocetFigurek = pocFig;
        pocetHracu = pocHracu;
        kostka = k;

        plocha = new Figurka[delkaPlochy];

        for(int i = 0; i < pocetHracu; i++) {
            Hrac novyHrac = new Hrac(i, 10 * i, spocitanaCesta(10 * i - 1));

            hraci.add(novyHrac);

            domecky.put(novyHrac, new StartDomecek(novyHrac, pocetFigurek));
            cile.put(novyHrac, new CilDomecek(novyHrac, pocetFigurek));
        }

        hrajiciHrac = hraci.get(0);
        hozeno = false;
        znovu = 0;
    }

    public boolean jeVolno(int kde) {
        return plocha[kde] == null;
    }

    public boolean jeMoje(int kde) {
        if(plocha[kde] == null) {
            return false;
        }
        else {
            return plocha[kde].getKomuPatrim() == hrajiciHrac;
        }
    }

    public void hoditKostkou() {
        if(!hozeno) {
            if(domecky.get(hrajiciHrac).figurkyDom.size() == pocetFigurek) {
                if(znovu < 2) {
                    znovu++;
                    naKostce = kostka.hod();
                    if(naKostce == kostka.getPocetStran()) {
                        hozeno = true;
                    }
                }
                else {
                    konecTahu();
                }
            }
            else {
                hozeno = true;
                naKostce = kostka.hod();
            }
        }

    }

    public int kolikHozeno() {
        return naKostce;
    }

    public void nasaditFigurku() {
        if(naKostce == kostka.getPocetStran()) {
            if(jeVolno(hrajiciHrac.getStartovniPole())) {
                plocha[hrajiciHrac.getStartovniPole()] = domecky.get(hrajiciHrac).nasaditFig();
                hozeno = false;
                znovu = 0;
            }
            else if(plocha[hrajiciHrac.getStartovniPole()].getKomuPatrim() != hrajiciHrac) {
                vyhodit(hrajiciHrac.getStartovniPole());
                plocha[hrajiciHrac.getStartovniPole()] = domecky.get(hrajiciHrac).nasaditFig();
                hozeno = false;
                znovu = 0;
            }
        }
    }

    public void posunFigurky(int kde) {
        if(!jeMoje(kde) || kolikHozeno() == 0) return;

        Figurka fig = plocha[kde];
        int kam = kde + naKostce;

        if(kam > hrajiciHrac.getVstupDoCile() && kde <= hrajiciHrac.getVstupDoCile()) {
            int kamDoCile = kam - hrajiciHrac.getVstupDoCile() - 1;
            if(cile.get(hrajiciHrac).jeVolno(kamDoCile)) {
                cile.get(hrajiciHrac).jitDoCile(fig, kamDoCile);
                plocha[kde] = null;
                konecTahu();
            }
        }
        else if(jeVolno(spocitanaCesta(kam))) {
            plocha[spocitanaCesta(kam)] = fig;
            plocha[kde] = null;
            konecTahu();
        }
        else if(plocha[spocitanaCesta(kam)].getKomuPatrim() != hrajiciHrac) {
            vyhodit(spocitanaCesta(kam));
            plocha[spocitanaCesta(kam)] = fig;
            plocha[kde] = null;
            konecTahu();
        }
    }

    public void posunFigurkyCil(int hrac, int kde) {
        if(hrajiciHrac.getPoradi() == hrac) {
            Figurka fig = plocha[kde];
            int kam = kde + naKostce;

            if(cile.get(hrajiciHrac).jeVolno(kam)) {
                cile.get(hrajiciHrac).posunVCili(fig, kde, kam);
                konecTahu();
            }
        }
    }

    public void vyhodit(int kde) {
        Hrac koho = plocha[kde].getKomuPatrim();
        domecky.get(koho).vratitFig(plocha[kde]);
        plocha[kde] = null;
    }

    public void konecTahu() {
        if(naKostce == kostka.getPocetStran()) {
            hozeno = false;
            znovu = 0;
        }
        else {
            if(hrajiciHrac.getPoradi() + 1 == hraci.size()) {
                hrajiciHrac = hraci.get(0);
            }
            else {
                hrajiciHrac = hraci.get(hrajiciHrac.getPoradi() + 1);
            }
            hozeno = false;
            znovu = 0;
        }

    }

    public void konecHry() {
        // KONEC
    }

    public int spocitanaCesta(int kam) {
        return (kam > delkaPlochy - 1) ? kam - delkaPlochy : kam;
    }
}
