package ru;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Gulshat on 08.10.2017.
 */
public class GameWindow extends JFrame {

    // 2. обозначаем размеры
    private static final int WIN_HEIGHT = 550;
    private static final int WIN_WIDTH = 500;
    private static final int WIN_POS_X = 500;
    private static final int WIN_POS_Y = 300;
    public static char map[][];
    public static final char DOT_EMPTY = '*';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static int size;
    public static int dot_to_win;
    JPanel mainPanel;
    JButton[] jbs;

    // 16 создаем константы
    private static StartNewGameWindow startNewGameWindow;
    private static Map field;

    // 3. создаем конструктор
    GameWindow() {
        // 5. добавляем операцию close, при закрытии окна, программа завершается.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 6. задаем размеры
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);

        //12. Добавляем заголовок и запрещаем изменение размера окна
        setTitle("Tic Tac Toe");
        setResizable(false);


        // 9 Создаем панель, (только сначала рассказываем по север юг запад восток),
        // далее рассказываем про new GridLayout(1, 2) и что можно еще что-нибудь добавить
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // 7. создаем кнопку
        JButton btnNewGame = new JButton("Start new game");
        // 7.1 добавляем, потом перепишем
        // add(btnNewGame);
        // 22 обрабатываем стар новая игра
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 19 - 22 делаем наше окно настроек видимое // при крестике окно просто становить не видимым
                startNewGameWindow.setVisible(true);
            }
        });


        // 8. создаем кнопку
        JButton btnExit = new JButton("Exit game");
        // 8.1 добавляем, потом перепишем
        //  add(btnExit);
        // 21 Вешаем слушателя на кнопку
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ноль для остановки программы
                System.exit(0);
            }
        });

        // 10 далее в панель кнопки
        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnExit);

        // 17 добавляем Map (игровое поле)
        field = new Map();
        add(field, BorderLayout.CENTER);

        // 11 добавляем в окно панель и распологаем ее на юге
        add(bottomPanel, BorderLayout.SOUTH);

        // 18 передаем возможность определение координатов следующему окну
        startNewGameWindow = new StartNewGameWindow(this);
        // 19 делаем наше окно настроек видимое
        // startNewGameWindow.setVisible(true);

        // 4. создаем наше первое окно (УРА!)
        setVisible(true);
    }

    // 20 кнопка стартануть будет на окне настроек, этот метод нужно вызывать из 2го окна
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLenght) {
        //field.startNewGame(mode, fieldSizeX, fieldSizeY, winLenght);
        size = fieldSizeX;
        dot_to_win = winLenght;
        mainPanel = new JPanel(new GridLayout(size, size));
        jbs = new JButton[size * size];
        for (int i = 0; i < jbs.length; i++) {
            jbs[i] = new JButton("*");
            mainPanel.add(jbs[i]);
        }
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        initMap();
        for (int i = 0; i < jbs.length; i++) {
            int finalI = i;
            jbs[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jbs[finalI].setText("X");
                    map[(int) ((finalI) / (size))][finalI - size * ((int) (finalI / size))] = DOT_X;
                    if (checkWin(DOT_X) || isMapFull()) System.out.println("Выиграл человек");;
                    aiTurn();
                    if (checkWin(DOT_O) || isMapFull()) System.exit(0);
                }
            });
        }
    }
    public static boolean isMapFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }
    public static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }
    private static int counter(int n, int m, char symb) {
        int count = 0;
        if (n >= 0 && m >= 0 && n < size && m < size) {
            if (map[n][m] == symb) {
                count = 1;
            }
        }
        return count;
    }
    public static boolean checkWin(char symb) {
        int n, m;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                int count = 0;
                for (n = i, m = j; n > i-dot_to_win; n--, m++) {
                    count += counter(n,m,symb);
                    if (count == dot_to_win) {
                        return true;
                    }
                }
                count = 0;
                for (n = i, m = j; m < j+dot_to_win; m++) {
                    count += counter(n,m,symb);
                    if (count == dot_to_win) {
                        return true;
                    }
                }
                count = 0;
                for (n = i, m = j; n < i+dot_to_win; n++, m++) {
                    count += counter(n,m,symb);
                    if (count == dot_to_win) {
                        return true;
                    }
                }
                count = 0;
                for (n = i, m = j; n < i+dot_to_win; n++) {
                    count += counter(n,m,symb);
                    if (count == dot_to_win) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void aiTurn(){
        int x,y;
        Random random = new Random();
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (!isCellValid(x, y));
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if (map[i][j]==DOT_EMPTY) {
                    map[i][j] = DOT_X;
                    if (checkWin(DOT_X)){
                        map[i][j] = DOT_EMPTY;
                        x = j;
                        y = i;
                        break;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if (map[i][j]==DOT_EMPTY) {
                    map[i][j] = DOT_O;
                    if (checkWin(DOT_O)){
                        map[i][j] = DOT_EMPTY;
                        x = j;
                        y = i;
                        break;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }
        map[y][x] = DOT_O;
        jbs[size*y + x].setText("O");
    }
    public boolean isCellValid(int x, int y){
        if (x<0||x>=size||y<0||y>=size){
            return false;
        }
        if (map[y][x] == DOT_EMPTY){
            return true;
        }
        return false;
    }
}
