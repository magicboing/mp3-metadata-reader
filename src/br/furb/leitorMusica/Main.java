package br.furb.leitorMusica;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
            try {
                br.furb.leitorMusica.Views.Main main = new br.furb.leitorMusica.Views.Main();
            } catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(null, iae.getMessage(), "Ops!", JOptionPane.ERROR_MESSAGE);
            }
    }
}
