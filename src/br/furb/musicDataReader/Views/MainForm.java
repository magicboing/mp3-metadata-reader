/**
 * Matheus Boing & Lucas Samuel
 * Disciplina de Programação II
 * Trabalho I
 */
package br.furb.musicDataReader.Views;

import br.furb.musicDataReader.Manager;
import br.furb.musicDataReader.Model.MusicGenre;
import br.furb.musicDataReader.Model.Music;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class MainForm {
    private JPanel MainForm;
    private JTextField textFieldFile;
    private JButton buttonOpen;
    private JTextField textFieldMusic;
    private JTextField textFieldArtist;
    private JTextField textFieldTrack;
    private JTextField textFieldAlbum;
    private JTextField textFieldYear;
    private JComboBox comboBoxGenre;
    private JTextField textFieldNote;
    private JButton buttonSave;
    private JLabel labelArquivo;
    private JLabel labelTitle;
    private JLabel labelArtist;
    private JLabel labelTrack;
    private JLabel labelAlbum;
    private JLabel labelGenre;
    private JLabel labelYear;
    private JLabel labelNote;
    private JLabel labelRemoveInfo;
    private Music music;
    private Manager manager;

    public MainForm() {
        initializeListeners();
        initializeGeneroDropdown();
        initializeJFrame();
    }

    /**
     * Inicializa e exibe o JFrame
     */
    private void initializeJFrame() {
        JFrame frame = new JFrame();
        frame.setContentPane(MainForm);
        frame.setSize(800, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Leitor de MP3 - Lucas Samuel & Matheus Boing");
        frame.setAutoRequestFocus(true);
        frame.setResizable(false);

        textFieldFile.setText(System.getProperty("user.dir"));
        frame.setVisible(true);

        setEnabledMusicFieldsState(false);
    }

    /**
     * Inicializa o Dropdown de gêneros incluindo os
     * itens da lista de gêneros
     */
    private void initializeGeneroDropdown() {
        Manager manager = new Manager();

        for (MusicGenre genero : manager.getGenres()) {
            comboBoxGenre.addItem(genero);
        }
    }

    /**
     * Inicializa os ouvintes de eventos
     */
    private void initializeListeners() {
        buttonOpen.addActionListener(e -> {
            try {
                buttonOpenOnClickListener();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MainForm, "Não foi possível abrir o arquivo.", "Ops!", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonSave.addActionListener(e -> buttonSaveOnClickListener());

        labelRemoveInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labelRemoveInfosOnClickListener();
            }
        });

        textFieldFile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textFieldFileOnEnterPressListener();
            }
        });
    }

    /**
     * Abre o arquivo selecionado
     */
    private void buttonOpenOnClickListener() {
        File file = new File("");
        JFileChooser fileChooser = new JFileChooser();

        emptyFields();
        setEnabledMusicFieldsState(false);

        if (textFieldFile.getText().length() > 0) {
            file = new File(textFieldFile.getText());

            if (file.isDirectory()) {
                fileChooser.setCurrentDirectory(new File(textFieldFile.getText()));

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "MP3", "mp3");
                fileChooser.setFileFilter(filter);

                int returnVal = fileChooser.showOpenDialog(MainForm);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    emptyFields();

                    try {
                        file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    } catch (Exception ex) {
                        showError("Não foi possível abrir o arquivo");
                    }
                }
            }
        }

        String fileName = file.getName();

        if(file.isFile()) {
            if (!fileName.substring(fileName.lastIndexOf(".") + 1).equals("mp3")) {
                showError("O arquivo não está no formato MP3");
                return;
            }

            textFieldFile.setText(file.getAbsolutePath());

            manager = new Manager(file);

            try {
                music = manager.readMusic();

                if (music != null) {
                    fillMusicData(music);
                } else {
                    music = new Music();
                }
            } catch (Exception ex) {
                showError("Não foi possível ler o arquivo");
            }

            setEnabledMusicFieldsState(true);
        } else {
            showError("Arquivo não encontrado");
        }
    }

    /**
     * Remove os metadados do arquivo
     */
    private void labelRemoveInfosOnClickListener() {
        if (labelRemoveInfo.isEnabled()) {
            int option = JOptionPane.showConfirmDialog(MainForm, "Realmente deseja remover as informações?", "Confirme a remoção",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(option == JOptionPane.OK_OPTION) {
                try {
                    Music musica = new Music();

                    if (manager.saveMusic(musica)) {
                        emptyFields();
                        JOptionPane.showMessageDialog(MainForm, "Informações removidas com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    showError("Não foi possível remover os dados");
                }
            }
        }
    }

    /**
     * Salva os metadados do arquivo
     */
    private void buttonSaveOnClickListener() {
        try {
            music.setTitle(textFieldMusic.getText());
            music.setArtist(textFieldArtist.getText());
            music.setAlbum(textFieldAlbum.getText());

            int faixa = 0;

            if (textFieldTrack.getText().length() > 0) {
                try {
                    faixa = Integer.parseInt(textFieldTrack.getText());
                } catch (NumberFormatException nfe) {
                    showError("A faixa deve ser um número inteiro");
                    return;
                }
            }

            music.setTrack(faixa);
            music.setGenre(manager.getGenres().get(comboBoxGenre.getSelectedIndex()));
            music.setYear(textFieldYear.getText());
            music.setNote(textFieldNote.getText());

            if (manager.saveMusic(music)) {
                JOptionPane.showMessageDialog(MainForm, "Dados salvos com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (UnsupportedEncodingException e) {
            showError("Não foi possível obter o título da música");
        } catch (IllegalArgumentException iea) {
            showError(iea.getMessage());
        } catch (Exception e) {
            showError("Não foi possível salvar os dados");
        }
    }

    /**
     * Abre o arquivo ao pressionar ENTER
     */
    private void textFieldFileOnEnterPressListener() {
        buttonOpenOnClickListener();
    }

    private void fillMusicData(Music musica) {
        if (musica.getTitle().getBytes()[0] > 0) {
            textFieldMusic.setText(musica.getTitle());
        }

        if (musica.getArtist().getBytes()[0] > 0) {
            textFieldArtist.setText(musica.getArtist());
        }

        if (musica.getTrack() > 0) {
            textFieldTrack.setText(String.valueOf(musica.getTrack()));
        }

        if (musica.getAlbum().getBytes()[0] > 0) {
            textFieldAlbum.setText(musica.getAlbum());
        }

        if (musica.getGenre().getId() >= 0) {
            comboBoxGenre.setSelectedIndex(musica.getGenre().getId());
        }

        if (musica.getYear().getBytes()[0] > 0) {
            textFieldYear.setText(musica.getYear());
        }

        if (musica.getNote().getBytes()[0] > 0) {
            textFieldNote.setText(musica.getNote());
        }
    }

    /**
     * Exibe uma mensagem de erro formatada
     * @param message Mensagem a ser exibida
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(MainForm, message, "Ops!", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Alterna o estado dos campos
     * @param state Estado do campo
     */
    private void setEnabledMusicFieldsState(boolean state) {
        labelAlbum.setEnabled(state);
        labelYear.setEnabled(state);
        labelArtist.setEnabled(state);
        labelNote.setEnabled(state);
        labelTrack.setEnabled(state);
        labelGenre.setEnabled(state);
        labelTitle.setEnabled(state);
        labelRemoveInfo.setEnabled(state);

        textFieldMusic.setEnabled(state);
        textFieldAlbum.setEnabled(state);
        textFieldYear.setEnabled(state);
        textFieldArtist.setEnabled(state);
        textFieldNote.setEnabled(state);
        textFieldTrack.setEnabled(state);

        comboBoxGenre.setEnabled(state);

        buttonSave.setEnabled(state);
    }

    /**
     * Limpa os campos
     */
    private void emptyFields() {
        textFieldMusic.setText("");
        textFieldArtist.setText("");
        textFieldTrack.setText("");
        textFieldAlbum.setText("");
        comboBoxGenre.setSelectedIndex(-1);
        textFieldYear.setText("");
        textFieldNote.setText("");
    }
}
