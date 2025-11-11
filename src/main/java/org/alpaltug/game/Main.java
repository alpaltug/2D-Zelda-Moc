package org.alpaltug.game;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Zelda Mock");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // pack the JPanel so that we can see it

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
