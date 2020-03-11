/**
 * Matheus Boing & Lucas Samuel
 * Disciplina de Programação II
 * Trabalho I
 */
package br.furb.musicDataReader.Model;

import java.io.UnsupportedEncodingException;

/**
 * Metadados da música
 */
public class Music {
    private String title;
    private String artist;
    private String album;
    private String year;
    private String note;
    private int track;
    private MusicGenre genre;

    public Music() throws UnsupportedEncodingException {
        setTitle("");
        setArtist("");
        setAlbum("");
        setYear("");
        setNote("");
        setTrack(0);
        setGenre(new MusicGenre(0, ""));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws UnsupportedEncodingException {
        byte[] bytes = title.getBytes("UTF-8");
        if(bytes.length > 30){
            throw new IllegalArgumentException("Titulo não pode ser maior que 30 caracteres.");
        }
        this.title = title.trim();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) throws UnsupportedEncodingException {
        if(artist.getBytes("UTF-8").length > 30){
            throw new IllegalArgumentException("Artista não pode ser maior que 30 caracteres.");
        }
        this.artist = artist.trim();
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) throws UnsupportedEncodingException {
        if(album.getBytes("UTF-8").length > 30){
            throw new IllegalArgumentException("Álbum não pode ser maior que 30 caracteres.");
        }
        this.album = album.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) throws UnsupportedEncodingException {
        if(note.getBytes("UTF-8").length > 28){
            throw new IllegalArgumentException("Comentário não pode ser maior que 28 caracteres.");
        }
        this.note = note.trim();
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }
}