/**
 * Matheus Boing & Lucas Samuel
 * Disciplina de Programação II
 * Trabalho I
 */
package br.furb.musicDataReader.Views;

import br.furb.musicDataReader.Controllers.MusicController;
import br.furb.musicDataReader.Services.MusicService;
import br.furb.musicDataReader.Models.MusicGenre;
import br.furb.musicDataReader.Models.Music;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;

public class MainForm {
    private JPanel jPanel;
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
    private JButton buttonRemoveInfos;
    private Music music;
    private MusicService musicService;
    private MusicController musicController;

    public MainForm(MusicController musicController) {
        musicService = new MusicService();
        this.musicController = musicController;
        initializeListeners();
        initializeGeneroDropdown();
    }

    /**
     * Exibe o JFrame
     */
    public void show() {
        initializeJFrame();
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    /**
     * Inicializa e exibe o JFrame
     */
    private void initializeJFrame() {
        JFrame frame = new JFrame();
        frame.setContentPane(jPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Leitor de MP3 - Lucas Samuel & Matheus Boing");
        frame.setAutoRequestFocus(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        textFieldFile.setText(System.getProperty("user.dir"));
        setEnabledMusicFieldsState(false);

        frame.setVisible(true);
    }

    /**
     * Inicializa o Dropdown de gêneros incluindo os
     * itens da lista de gêneros
     */
    private void initializeGeneroDropdown() {
        for (MusicGenre genero : musicService.getGenres()) {
            comboBoxGenre.addItem(genero);
        }
    }

    /**
     * Inicializa os ouvintes de eventos
     */
    private void initializeListeners() {
        buttonOpen.addActionListener(e -> buttonOpenOnClickListener());
        buttonSave.addActionListener(e -> buttonSaveOnClickListener());
        buttonRemoveInfos.addActionListener(e -> buttonRemoveInfosOnClickListener());

        textFieldFile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            try {
                textFieldFileOnEnterPressListener();
            } catch (Exception ex) {
                showError("Não foi possível processar o caminho do arquivo");
            }
            }
        });
    }

    /**
     * Abre o arquivo selecionado
     */
    private void buttonOpenOnClickListener() {
        setEnabledMusicFieldsState(false);

        if (textFieldFile.getText().length() > 0) {
            musicController.read(textFieldFile.getText());
        }
    }

    /**
     * Remove os metadados do arquivo
     */
    private void buttonRemoveInfosOnClickListener() {
        setEnabledMusicFieldsState(false);
        int option = JOptionPane.showConfirmDialog(jPanel, "Realmente deseja remover as informações?", "Confirme a remoção",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(option == JOptionPane.OK_OPTION) {
            musicController.delete(textFieldFile.getText());
        }
    }

    /**
     * Salva os metadados do arquivo
     */
    private void buttonSaveOnClickListener() {
        try {
            if (comboBoxGenre.getSelectedIndex() < 0) {
                showError("Selecione um gênero");
                return;
            }

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
            music.setGenre(musicService.getGenres().get(comboBoxGenre.getSelectedIndex() < 0 ? 0 : comboBoxGenre.getSelectedIndex()));
            music.setYear(textFieldYear.getText());
            music.setNote(textFieldNote.getText());

            musicController.save(textFieldFile.getText(), music);
        } catch (UnsupportedEncodingException e) {
            showError("Não foi possível processar o mapa de caracteres");
        } catch (IllegalArgumentException iea) {
            showError(iea.getMessage());
        } catch (Exception ex) {
            showError("Não foi possível processar os dados");
        }
    }

    /**
     * Abre o arquivo ao pressionar ENTER
     */
    private void textFieldFileOnEnterPressListener() {
        buttonOpenOnClickListener();
    }

    /**
     * Carrega os atributos da música no JFrame
     * @param filePath Caminho do arquivo da música
     * @param music Música
     */
    public void loadMusicData(String filePath, Music music) throws UnsupportedEncodingException {
        emptyFields();
        textFieldFile.setText(filePath);
        setEnabledMusicFieldsState(true);

        if (music == null) {
            this.music = new Music();
            return;
        }

        this.music = music;

        if (validateStringData(music.getTitle())) {
            textFieldMusic.setText(music.getTitle());
        }

        if (validateStringData(music.getArtist())) {
            textFieldArtist.setText(music.getArtist());
        }

        if (music.getTrack() > 0) {
            textFieldTrack.setText(String.valueOf(music.getTrack()));
        }

        if (validateStringData(music.getAlbum())) {
            textFieldAlbum.setText(music.getAlbum());
        }

        if (music.getGenre() != null && music.getGenre().getId() >= 0) {
            comboBoxGenre.setSelectedIndex(music.getGenre().getId());
        } else {
            comboBoxGenre.setSelectedIndex(-1);
        }

        if (validateStringData(music.getYear())) {
            textFieldYear.setText(music.getYear());
        }

        if (validateStringData(music.getNote())) {
            textFieldNote.setText(music.getNote());
        }
    }

    /**
     * Valida se a string é válida
     * @param data String a ser validada
     * @return Verdadeiro, se a string é válida
     */
    private boolean validateStringData(String data) {
        return data.length() > 0 && data.getBytes()[0] > 0;
    }

    /**
     * Exibe uma mensagem de erro
     * @param message Mensagem a ser exibida
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(jPanel, message, "Ops!", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Exibe uma mensagem de informação
     * @param message Mensagem a ser exibida
     */
    public void showInfo(String message) {
        JOptionPane.showMessageDialog(jPanel, message, null, JOptionPane.INFORMATION_MESSAGE);
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

        textFieldMusic.setEnabled(state);
        textFieldAlbum.setEnabled(state);
        textFieldYear.setEnabled(state);
        textFieldArtist.setEnabled(state);
        textFieldNote.setEnabled(state);
        textFieldTrack.setEnabled(state);

        comboBoxGenre.setEnabled(state);

        buttonSave.setEnabled(state);
        buttonRemoveInfos.setEnabled(state);
    }

    /**
     * Limpa os campos
     */
    public void emptyFields() {
        textFieldMusic.setText("");
        textFieldArtist.setText("");
        textFieldTrack.setText("");
        textFieldAlbum.setText("");
        comboBoxGenre.setSelectedIndex(-1);
        textFieldYear.setText("");
        textFieldNote.setText("");
    }
}
