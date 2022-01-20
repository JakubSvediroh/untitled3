package com.company.model;

import java.util.*;

public class StartDomecek
{
    private Hrac komuPatrim;

    public List<Figurka> figurkyDom = new ArrayList<>();

    public StartDomecek(Hrac hrac, int PocetFig) {
        komuPatrim = hrac;
        for (int i = 0; i < PocetFig; i++) {
            figurkyDom.add(new Figurka(komuPatrim));
        }
    }
    public Figurka nasaditFig () {
        Figurka nasazovanaFig = figurkyDom.get(figurkyDom.size() - 1);
        figurkyDom.remove(figurkyDom.size() - 1);
        return nasazovanaFig;
    }

    public void vratitFig(Figurka vracenaFig) {
        figurkyDom.add(vracenaFig);
    }
}

