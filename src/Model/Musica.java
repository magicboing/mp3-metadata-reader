package Model;

import java.io.UnsupportedEncodingException;

public class Musica {
    private String tituloMusica;
    private String artista;
    private String album;
    private String ano;
    private String comentario;
    private int flagTrilha;
    private int numeroFaixa;
    private GeneroMusica Genero;

    public Musica() {
    }

    public Musica(String tituloMusica, String artista, String album, String ano, String comentario, int flagTrilha, int numeroFaixa, GeneroMusica genero) throws UnsupportedEncodingException {
        setTituloMusica(tituloMusica);
        setArtista(artista);
        setAlbum(album);
        setAno(ano);
        setComentario(comentario);
        setFlagTrilha(flagTrilha);
        setNumeroFaixa(numeroFaixa);
        setGenero(genero);
    }

    public String getTituloMusica() {
        return tituloMusica;
    }

    public void setTituloMusica(String tituloMusica) throws UnsupportedEncodingException {
        if(tituloMusica.getBytes("UTF-8").length > 30){
            throw new IllegalArgumentException("Titulo não pode ser vazio.");
        }
        this.tituloMusica = tituloMusica;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) throws UnsupportedEncodingException {
        if(artista.getBytes("UTF-8").length > 30){
            throw new IllegalArgumentException("Artista não pode ser maior que 30 caracteres.");
        }
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) throws UnsupportedEncodingException {
        if(album.getBytes("UTF-8").length > 30){
            throw new IllegalArgumentException("Álbum não pode ser maior que 30 caracteres.");
        }
        this.album = album;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) throws UnsupportedEncodingException {
        if(comentario.getBytes("UTF-8").length > 28){
            throw new IllegalArgumentException("Comentário não pode ser maior que 28 caracteres.");
        }
        this.comentario = comentario;
    }

    public int getFlagTrilha() {
        return flagTrilha;
    }

    public void setFlagTrilha(int flagTrilha) {
        this.flagTrilha = flagTrilha;
    }

    public int getNumeroFaixa() {
        return numeroFaixa;
    }

    public void setNumeroFaixa(int numeroFaixa) {
        this.numeroFaixa = numeroFaixa;
    }

    public GeneroMusica getGenero() {
        return Genero;
    }

    public void setGenero(GeneroMusica genero) {
        Genero = genero;
    }
}