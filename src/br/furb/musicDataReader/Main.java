/**
 * Matheus Boing & Lucas Samuel
 * Disciplina de Programação II
 * Trabalho I
 */
package br.furb.musicDataReader;

import br.furb.musicDataReader.Views.MainForm;

import javax.swing.*;

/**
 * Classe de inicialização
 */
public class Main {
    public static void main(String[] args) {
            try {
                MainForm mainForm = new MainForm();
            } catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(null, iae.getMessage(), "Ops!", JOptionPane.ERROR_MESSAGE);
            }
    }
}
