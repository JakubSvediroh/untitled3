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

    private List<Barva> hraci = new ArrayList<>();
    private int pocetHracu;

    public List<Barva> getHraci() {
        return hraci;
    }

    private Map<Barva, Startdomecek> domecky = new HashMap<>();
    private Map<Barva, CilDomecek> cile = new HashMap<>();

    public Map<Barva, CilDomecek> getCile() {
        return cile;
    }

    private int pocetFigurek;
    private Kostka kostka;
    private boolean byloHozeno;
    private int hod;

    public Barva praveHraje;

    public HraciPlocha(int vel, int pocFig, int pocHracu, Kostka k) {
        delkaPlochy = vel;
        pocetFigurek = pocFig;
        pocetHracu = pocHracu;
        kostka = k;

        plocha = new Figurka[delkaPlochy];

        for(int i = 0; i < pocetHracu; i++) {
            Barva novyHrac = new Barva(10 * i, spocitanaCesta(10 * i - 1), i);

            hraci.add(novyHrac);

            domecky.put(novyHrac, new Startdomecek(novyHrac, pocetFigurek));
            cile.put(novyHrac, new CilDomecek(novyHrac, pocetFigurek));
        }

        praveHraje = hraci.get(2);

        Figurka figs = new Figurka(hraci.get(2));
        plocha[20] = figs;
    }

    public boolean jeVolno(int kde) {
        return plocha[kde] == null;
    }

    public boolean jeMoje(int kde) {
        if(plocha[kde] == null) {
            return false;
        }
        else {
            return plocha[kde].getBarva() == praveHraje;
        }
    }

    public void hoditKostkou() {
        if(!byloHozeno) {
            byloHozeno = true;
            hod = kostka.hod();
        }
    }

    public int kolikHozeno() {
        return (byloHozeno) ? hod : 0;
    }

    public void nasaditFigurku() {
        if(jeVolno(praveHraje.getStartpole())) {
            plocha[praveHraje.getStartpole()] = domecky.get(praveHraje).nasaditFigurku();
        }
        else if(plocha[praveHraje.getStartpole()].getBarva() != praveHraje) {
            vyhodit(praveHraje.getStartpole());
            plocha[praveHraje.getStartpole()] = domecky.get(praveHraje).nasaditFigurku();
        }
    }

    public void posunFigurky(int kde) {
        Figurka jaka = plocha[kde];
        int kam = kde + hod;


        if(!cile.get(praveHraje).jeFigurka(jaka)) {
            if(kam > praveHraje.getCilpole() && kde < praveHraje.getCilpole()) {
                int kamDoCile = kam - praveHraje.getCilpole() - 1;
                if(cile.get(praveHraje).Volno(kamDoCile)) {
                    cile.get(praveHraje).DoDomecku(jaka, kamDoCile);
                    plocha[kde] = null;
                }
            }
            else if(jeVolno(spocitanaCesta(kam))) {
                plocha[spocitanaCesta(kam)] = jaka;
                plocha[kde] = null;
            }
            else if(plocha[spocitanaCesta(kam)].getBarva() != praveHraje) {
                vyhodit(spocitanaCesta(kam));
                plocha[spocitanaCesta(kam)] = jaka;
            }
        }
        else {
            if(cile.get(praveHraje).Volno(kam)) {
                // posunout v cili
            }
        }
        byloHozeno = false;
    }

    public void vyhodit(int kde) {
        Barva koho = plocha[kde].getBarva();
        domecky.get(koho).vratitFigurku(plocha[kde]);
        plocha[kde] = null;
    }

    public void konecTahu() {
        byloHozeno = false;
        praveHraje = hraci.get(praveHraje.getPoradi() + 1);
    }

    public void konecHry() {
        // KONEC
    }

    public int spocitanaCesta(int kam) {
        return (kam > delkaPlochy - 1) ? kam - delkaPlochy : kam;

    }
}
