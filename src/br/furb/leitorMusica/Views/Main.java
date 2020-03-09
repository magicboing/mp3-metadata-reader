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

    private void buttonAbrirOnClickListener() {
        File file = new File("");
        JFileChooser fileChooser = new JFileChooser();

        if (textFieldArquivo.getText().length() > 0) {
            fileChooser.setCurrentDirectory(new File(textFieldArquivo.getText()));
        }

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

        String fileName = file.getName();

        if (!fileName.substring(fileName.lastIndexOf(".") + 1).equals("mp3")) {
            showError("O arquivo não está no formato MP3");
            return;
        }

        textFieldArquivo.setText(file.getAbsolutePath());

        manipuladorMusica = new ManipuladorMusica(file);

        try {
            musica = manipuladorMusica.lerMusica();
        } catch (Exception ex) {
            showError("Não foi possível ler o arquivo");
        }

        if (musica != null) {
            fillMusicData(musica);
        } else {
            musica = new Musica();
        }

        setEnabledMusicFieldsState(true);
    }

    private void labelRemoverInformacoesOnClickListener() {
        try {
            Musica musica = new Musica("", "", "", "", "", 0, 0, manipuladorMusica.getGeneros().get(0));

            if (manipuladorMusica.salvar(musica)) {
                emptyFields();
                JOptionPane.showMessageDialog(MainForm, "Dados salvos com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            showError("Não foi possível remover os dados");
        }
    }

    private void buttonSalvarOnClickListener() {
        try {
            musica.setTituloMusica(textFieldMusica.getText());
            musica.setArtista(textFieldArtista.getText());
            musica.setAlbum(textFieldAlbum.getText());
            int faixa = textFieldFaixa.getText().length() > 0 ? Integer.parseInt(textFieldFaixa.getText()) : 0;
            musica.setNumeroFaixa(faixa);
            musica.setGenero(manipuladorMusica.getGeneros().get(comboBoxGenero.getSelectedIndex()));
            musica.setAno(textFieldAno.getText());
            musica.setComentario(textFieldComentario.getText());

            if (manipuladorMusica.salvar(musica)) {
                JOptionPane.showMessageDialog(MainForm, "Dados salvos com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (UnsupportedEncodingException e) {
            showError("Não foi possível obter o título da música");
        } catch (IllegalArgumentException iea) {
            showError(iea.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillMusicData(Musica musica) {
        if (musica.getTituloMusica().getBytes()[0] > 0) {
            textFieldMusica.setText(musica.getTituloMusica());
        }

        if (musica.getArtista().getBytes()[0] > 0) {
            textFieldArtista.setText(musica.getArtista());
        }

        if (musica.getNumeroFaixa() > 0) {
            textFieldFaixa.setText(String.valueOf(musica.getNumeroFaixa()));
        }

        if (musica.getAlbum().getBytes().length > 0) {
            textFieldAlbum.setText(musica.getAlbum());
        }

        if (musica.getGenero().getCodigo() >= 0) {
            comboBoxGenero.setSelectedIndex(musica.getGenero().getCodigo());
        }

        if (musica.getAno().getBytes().length > 0) {
            textFieldAno.setText(musica.getAno());
        }

        if (musica.getComentario().getBytes().length > 0) {
            textFieldComentario.setText(musica.getComentario());
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

    private void emptyFields() {
        textFieldMusica.setText("");
        textFieldArtista.setText("");
        textFieldFaixa.setText("");
        textFieldAlbum.setText("");
        comboBoxGenero.setSelectedIndex(-1);
        textFieldAno.setText("");
        textFieldComentario.setText("");
    }
}
