package br.furb.leitorMusica.Views;

import br.furb.leitorMusica.ManipuladorMusica;
import br.furb.leitorMusica.Model.GeneroMusica;
import br.furb.leitorMusica.Model.Musica;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
    private JPanel MainForm;
    private JTextField textFieldArquivo;
    private JButton buttonAbrir;
    private JTextField textFieldMusica;
    private JTextField textFieldArtista;
    private JTextField textFieldFaixa;
    private JTextField textFieldAlbum;
    private JTextField textFieldAno;
    private JComboBox comboBoxGenero;
    private JTextField textFieldComentario;
    private JButton buttonSalvar;
    private JButton buttonCancelar;
    private JLabel labelArquivo;
    private JLabel labelTituloMusica;
    private JLabel labelArtista;
    private JLabel labelFaixa;
    private JLabel labelAlbum;
    private JLabel labelGenero;
    private JLabel labelAno;
    private JLabel labelComentario;
    private JLabel labelRemoverInformacoes;
    private Musica musica;
    private ManipuladorMusica manipuladorMusica;

    public Main() {
        initializeListeners();
        initializeGeneroDropdown();
        initializeJFrame();
    }

    private void initializeJFrame() {
        JFrame frame = new JFrame();
        frame.setContentPane(MainForm);
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Leitor de MP3 - Lucas Samuel & Matheus Boing");
        frame.setAutoRequestFocus(true);
        frame.setResizable(false);

        textFieldArquivo.setText("D:\\Users\\lucas\\Documents\\OneDrive - FURB\\Documentos\\Sistemas de Informação\\SIS03\\trabalho1\\src\\Warriors.mp3");
        frame.setVisible(true);

        setEnabledMusicFieldsState(false);
    }

    private void initializeGeneroDropdown() {
        ManipuladorMusica manipuladorMusica = new ManipuladorMusica();

        for (GeneroMusica genero : manipuladorMusica.getGeneros()) {
            comboBoxGenero.addItem(genero);
        }
    }

    private void initializeListeners() {
        buttonAbrir.addActionListener(e -> {
            try {
                buttonAbrirOnClickListener();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MainForm, "Não foi possível abrir o arquivo.", "Ops!", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonSalvar.addActionListener(e -> buttonSalvarOnClickListener());

        labelRemoverInformacoes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labelRemoverInformacoesOnClickListener();
            }
        });
    }

    private void buttonAbrirOnClickListener() throws Exception {
        File file = new File("");

        if (textFieldArquivo.getText().length() == 0) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "MP3", "mp3");
            fileChooser.setFileFilter(filter);

            int returnVal = fileChooser.showOpenDialog(MainForm);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else {
            file = new File(textFieldArquivo.getText());
        }

        if (!file.isFile()) {
            showError("Não foi possível abrir o arquivo.");
            return;
        }

        /*String fileName = file.getName();
        String[] fileNameSplit = fileName.split(".");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        if (!extension.equals("mp3")) {
            showError("O arquivo não está no formato MP3");
            return;
        }*/

        textFieldArquivo.setEnabled(false);
        textFieldArquivo.setText(file.getAbsolutePath());

        manipuladorMusica = new ManipuladorMusica(file);
        musica = manipuladorMusica.lerMusica();

        if (musica != null) {
            fillMusicData(musica);
        }

        setEnabledMusicFieldsState(true);
    }

    private void labelRemoverInformacoesOnClickListener() {
        JOptionPane.showMessageDialog(MainForm, "Remover informações");
    }

    private void buttonSalvarOnClickListener() {
        try {
            String nomeMusica = textFieldMusica.getText();
            musica.setTituloMusica(textFieldMusica.getText());
            musica.setArtista(textFieldArtista.getText());
            musica.setAlbum(textFieldAlbum.getText());
            musica.setNumeroFaixa(Integer.parseInt(textFieldFaixa.getText()));
            musica.setGenero((GeneroMusica)comboBoxGenero.getSelectedItem());
        } catch (UnsupportedEncodingException e) {
            showError("Não foi possível obter o título da música");
        } catch (IllegalArgumentException iea) {
            showError(iea.getMessage());
        } catch (Exception e) {
            showError("Não foi possível salvar a música");
        }
    }

    private void fillMusicData(Musica musica) {
        if (musica.getTituloMusica().getBytes()[0] > 0) {
            textFieldMusica.setText(musica.getTituloMusica());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(MainForm, message, "Ops!", JOptionPane.ERROR_MESSAGE);
    }

    private void setEnabledMusicFieldsState(boolean state) {
        labelAlbum.setEnabled(state);
        labelAno.setEnabled(state);
        labelArtista.setEnabled(state);
        labelComentario.setEnabled(state);
        labelFaixa.setEnabled(state);
        labelGenero.setEnabled(state);
        labelTituloMusica.setEnabled(state);
        labelRemoverInformacoes.setEnabled(state);

        textFieldMusica.setEnabled(state);
        textFieldAlbum.setEnabled(state);
        textFieldAno.setEnabled(state);
        textFieldArtista.setEnabled(state);
        textFieldComentario.setEnabled(state);
        textFieldFaixa.setEnabled(state);

        comboBoxGenero.setEnabled(state);

        buttonSalvar.setEnabled(state);
        buttonCancelar.setEnabled(state);
    }
}
