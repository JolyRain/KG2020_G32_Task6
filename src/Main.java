/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import app.App;
import app.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setSize(1500, 1000);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }
}
