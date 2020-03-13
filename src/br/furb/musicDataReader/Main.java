/**
 * Matheus Boing & Lucas Samuel
 * Disciplina de Programação II
 * Trabalho I
 */
package br.furb.musicDataReader;

import br.furb.musicDataReader.Controllers.MusicController;
import br.furb.musicDataReader.Views.MainForm;

import javax.swing.*;

/**
 * Classe de inicialização
 */
public class Main {
    public static void main(String[] args) {
        MusicController musicController = new MusicController();

        try {
            musicController.view();
        } catch (IllegalArgumentException iae) {
            musicController.showError(iae.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível iniciar a aplicação", "Ops!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
