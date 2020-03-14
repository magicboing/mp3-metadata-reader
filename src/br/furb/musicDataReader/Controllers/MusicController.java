package br.furb.musicDataReader.Controllers;

import br.furb.musicDataReader.Models.Music;
import br.furb.musicDataReader.Services.MusicService;
import br.furb.musicDataReader.Views.MainForm;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class MusicController {
    private MainForm mainForm;
    private MusicService musicService;
    private String openedFile;

    public MusicController() {
        mainForm = new MainForm(this);
        musicService = new MusicService();
    }

    public void view() {
        mainForm.show();
    }

    public void read(String filePath) {
        File file = new File(filePath);
        JFileChooser fileChooser = new JFileChooser();
        Music music;

        try {
            if (file.isDirectory() || openedFile.equals(filePath)) {
                fileChooser.setCurrentDirectory(new File(filePath));

                FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3", "mp3");
                fileChooser.setFileFilter(filter);

                int returnVal = fileChooser.showOpenDialog(mainForm.getJPanel());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }

            String fileName = file.getName();

            if (file.isFile()) {
                if (!fileName.substring(fileName.lastIndexOf(".") + 1).equals("mp3")) {
                    showError("O arquivo não contém a extensão .mp3");
                    return;
                }

                try {
                    musicService.setFile(file);
                    music = musicService.readMusic();
                    mainForm.loadMusicData(file.getAbsolutePath(), music);
                    openedFile = file.getAbsolutePath();
                } catch (Exception ex) {
                    showError("Não foi possível ler o arquivo");
                }
            }
        } catch (Exception ex) {
            showError("Não foi possível abrir o arquivo");
        }
    }

    public void save(String filePath, Music music) {
        try {
            musicService.setFile(new File(filePath));
            musicService.saveMusic(music);
            showInfo("Informações salvas com sucesso");
        } catch (Exception ex) {
            showError("Não foi possível salvar as informações");
        }
    }

    public void delete(String filePath) {
        musicService.setFile(new File(filePath));

        try {
            if (musicService.deleteMusic()) {
                mainForm.loadMusicData(filePath, null);
                showInfo("Informações removidas com sucesso");
            } else {
                showError("Não existem informações para remover");
            }
        } catch (Exception ex) {
            showError("Não foi possível remover as informações");
        }
    }

    public void showInfo(String message) {
        mainForm.showInfo(message);
    }

    public void showError(String message) {
        mainForm.showError(message);
    }
}
