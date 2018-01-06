package ru;

import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
    // 36
    public static final int MODE_H_V_A = 0;
    public static final int MODE_H_V_H = 1;

   // 13. создаем конструктор
    Map() {
        // 13.1 задаем цвет
        setBackground(Color.black);
    }

    // 14. метод начина играть (mode - режим игры (ПК против игрока и игрок против игрока))
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLenght) {
        // заглушка

        System.out.println("mode = " + mode +
                           "fsx " + fieldSizeX +
                           "fsy " + fieldSizeY +
                           "winner= " + winLenght);
    }

}
