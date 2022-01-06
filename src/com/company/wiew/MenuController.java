package com.company.wiew;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import com.company.model.*;

public class MenuController {
    HraciPlocha hraciplocha;

    int[][] mapa = new int[][] {
            {0 , 0 , 0 , 0 , 9 , 10, 11, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 8 , 0 , 12, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 7 , 0 , 13, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 6 , 0 , 14, 0 , 0 , 0 , 0 },
            {1 , 2 , 3 , 4 , 5 , 0 , 15, 16, 17, 18, 19},
            {40, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 20},
            {39, 38, 37, 36, 35, 0 , 25, 24, 23, 22, 21},
            {0 , 0 , 0 , 0 , 34, 0 , 26, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 33, 0 , 27, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 32, 0 , 28, 0 , 0 , 0 , 0 },
            {0 , 0 , 0 , 0 , 31, 30, 29, 0 , 0 , 0 , 0 },
    };

    int[][][] domecky = new int[][][] {
            {{0, 0}, {1, 0}, {1, 1}, {0, 1}},
            {{9, 0}, {10, 0}, {10, 1}, {9, 1}},
            {{9, 9}, {10, 9}, {10, 10}, {9, 10}},
            {{0, 9}, {1, 9}, {1, 10}, {0, 10}}
    };

    int[][][] cile = new int[][][] {
            {{1, 5}, {2, 5}, {3, 5}, {4, 5}},
            {{5, 1}, {5, 2}, {5, 3}, {5, 4}},
            {{9, 5}, {8, 5}, {7, 5}, {6, 5}},
            {{5, 9}, {5, 8}, {5, 7}, {5, 6}}
    };

    private String[] barvy = new String[] {
            "f00",
            "00f",
            "0f0",
            "ff0",
    };

    @FXML
    public FlowPane flowPane;

    @FXML
    public GridPane gridPane;

    @FXML
    public Button[] tlacitka;

    @FXML
    public Button[][] tlacitkaVCili;

    @FXML
    public void novaplocha(){

        hraciplocha = new HraciPlocha(40, 4, 4, new Kostka(6));

        vytvoritCestu();
        vytvoritCile();
        vytvoritDomy();
        vytvoritKostku();

        aktualizacePlochy();
    }

    private void vytvoritCestu() {
        tlacitka = new Button[40];

        for (int a = 0; a < 11; a++){
            for(int b = 0; b < 11; b++){
                if(mapa[b][a] > 0){
                    tlacitka[mapa[b][a] - 1] = new Button("");
                    tlacitka[mapa[b][a] - 1].setPrefSize(gridPane.getWidth() / 11, gridPane.getHeight() / 11);
                    tlacitka[mapa[b][a] - 1].setFocusTraversable(false);

                    int c = a;
                    int d = b;

                    tlacitka[mapa[b][a] - 1].setOnAction(e -> klikPole(mapa[d][c] - 1));

                    gridPane.add(tlacitka[mapa[b][a] - 1], a, b);
                }
            }
        }
    }

    private void vytvoritCile() {
        tlacitkaVCili = new Button[4][4];

        for(int a = 0; a < 4; a++) {
            for(int b = 0; b < 4; b++) {
                tlacitkaVCili[a][b] = new Button("");
                tlacitkaVCili[a][b].setPrefSize(gridPane.getWidth() / 11, gridPane.getHeight() / 11);
                tlacitkaVCili[a][b].setFocusTraversable(false);
                tlacitkaVCili[a][b].setDefaultButton(true);

                gridPane.add(tlacitkaVCili[a][b], cile[a][b][0], cile[a][b][1]);
            }
        }
    }

    private void vytvoritDomy() {
        for(int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                Button dom = new Button("");
                dom.setPrefSize(gridPane.getWidth() / 11, gridPane.getHeight() / 11);
                dom.setFocusTraversable(false);
                dom.setStyle("-fx-border-color: #" + barvy[a] + ";" +
                        "-fx-border-width: 2" + ";" +
                        "-fx-border-insets: 0" + ";");

                gridPane.add(dom, domecky[a][b][0], domecky[a][b][1]);
            }
        }
    }

    private void vytvoritKostku() {
        Button kostka = new Button("0");
        kostka.setPrefSize(gridPane.getWidth() / 11, gridPane.getHeight() / 11);
        kostka.setFocusTraversable(false);
        kostka.setOnAction(actionEvent ->
        {
            hraciplocha.hoditKostkou();
            kostka.setText(String.valueOf(hraciplocha.kolikHozeno()));
        });

        gridPane.add(kostka, 5, 5);
    }

    public void klikPole(int pole){
        if(!hraciplocha.jeMoje(pole) || hraciplocha.kolikHozeno() == 0) return;

        hraciplocha.posunFigurky(pole);

        aktualizacePlochy();
    }

    public void aktualizacePlochy() {

        // Cesta
        for(int a = 0; a < 40; a++) {
            tlacitka[a].setStyle(
                    (hraciplocha.getPlocha()[a] == null)
                            ? ""
                            : "-fx-background-color: #" + barvy[hraciplocha.getPlocha()[a].getBarva().getPoradi()]
            );
        }

        // Cile
        for(int b = 0; b < 4; b++) {
            for(int c = 0; c < tlacitkaVCili[b].length; c++) {
                tlacitkaVCili[b][c].setStyle(
                        (hraciplocha.getCile().get(hraciplocha.getHraci().get(b)).getCil().get(c) == null)
                                ? ""
                                : "-fx-background-color: #" + barvy[hraciplocha.getCile().get(hraciplocha.getHraci().get(b)).getCil().get(c).getBarva().getPoradi()]
                );
            }
        }
    }
}
