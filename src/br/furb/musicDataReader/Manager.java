/**
 * Matheus Boing & Lucas Samuel
 * Disciplina de Programação II
 * Trabalho I
 */
package br.furb.musicDataReader;

import br.furb.musicDataReader.Model.MusicGenre;
import br.furb.musicDataReader.Model.Music;

import java.io.*;
import java.util.ArrayList;

/**
 * Manipulador de metadados da música
 */
public class Manager {
    private File file;
    private ArrayList<MusicGenre> genres;

    public Manager() {
        genres = new ArrayList<>();
        genres.add(new MusicGenre(0, "Blues"));
        genres.add(new MusicGenre(1, "Classic Rock"));
        genres.add(new MusicGenre(2, "Country"));
        genres.add(new MusicGenre(3, "Dance"));
        genres.add(new MusicGenre(4, "Disco"));
        genres.add(new MusicGenre(5, "Funk"));
        genres.add(new MusicGenre(6, "Grunge"));
        genres.add(new MusicGenre(7, "Hip-Hop"));
        genres.add(new MusicGenre(8, "Jazz"));
        genres.add(new MusicGenre(9, "Metal"));
        genres.add(new MusicGenre(10, "New GeneroMusica Age"));
        genres.add(new MusicGenre(11, "Oldies"));
        genres.add(new MusicGenre(12, "Other"));
        genres.add(new MusicGenre(13, "Pop"));
        genres.add(new MusicGenre(14, "R&B"));
        genres.add(new MusicGenre(15, "Rap"));
        genres.add(new MusicGenre(16, "Reggae"));
        genres.add(new MusicGenre(17, "Rock"));
        genres.add(new MusicGenre(18, "Techno"));
        genres.add(new MusicGenre(19, "Industrial"));
        genres.add(new MusicGenre(20, "Alternative"));
        genres.add(new MusicGenre(21, "Ska"));
        genres.add(new MusicGenre(22, "Death Metal"));
        genres.add(new MusicGenre(23, "Pranks"));
        genres.add(new MusicGenre(24, "Soundtrack"));
        genres.add(new MusicGenre(25, "Euro-Techno"));
        genres.add(new MusicGenre(26, "Ambient"));
        genres.add(new MusicGenre(27, "Trip-Hop"));
        genres.add(new MusicGenre(28, "Vocal"));
        genres.add(new MusicGenre(29, "Jazz+Funk"));
        genres.add(new MusicGenre(30, "Fusion"));
        genres.add(new MusicGenre(31, "Trance"));
        genres.add(new MusicGenre(32, "Classical"));
        genres.add(new MusicGenre(33, "Instrumental"));
        genres.add(new MusicGenre(34, "Acid"));
        genres.add(new MusicGenre(35, "House"));
        genres.add(new MusicGenre(36, "Game"));
        genres.add(new MusicGenre(37, "Sound Clip"));
        genres.add(new MusicGenre(38, "Gospel"));
        genres.add(new MusicGenre(39, "Noise"));
        genres.add(new MusicGenre(40, "AlternRock"));
        genres.add(new MusicGenre(41, "Bass"));
        genres.add(new MusicGenre(42, "Soul"));
        genres.add(new MusicGenre(43, "Punk"));
        genres.add(new MusicGenre(44, "Space"));
        genres.add(new MusicGenre(45, "Meditative"));
        genres.add(new MusicGenre(46, "Instrumental"));
        genres.add(new MusicGenre(47, "Instrumental Rock"));
        genres.add(new MusicGenre(48, "Ethnic"));
        genres.add(new MusicGenre(49, "Gothic"));
        genres.add(new MusicGenre(50, "Darkwave"));
        genres.add(new MusicGenre(51, "Techno-Industrial"));
        genres.add(new MusicGenre(52, "Electronic"));
        genres.add(new MusicGenre(53, "Pop-Folk"));
        genres.add(new MusicGenre(54, "Eurodance"));
        genres.add(new MusicGenre(55, "Dream"));
        genres.add(new MusicGenre(56, "Southern Rock"));
        genres.add(new MusicGenre(57, "Comedy"));
        genres.add(new MusicGenre(58, "Cult"));
        genres.add(new MusicGenre(59, "Gangsta"));
        genres.add(new MusicGenre(60, "Top 40"));
        genres.add(new MusicGenre(61, "Christian Rap"));
        genres.add(new MusicGenre(62, "Pop/Funk"));
        genres.add(new MusicGenre(63, "Jungle"));
        genres.add(new MusicGenre(64, "Native American"));
        genres.add(new MusicGenre(65, "Cabaret"));
        genres.add(new MusicGenre(66, "New GeneroMusica Wave"));
        genres.add(new MusicGenre(67, "Psychadelic"));
        genres.add(new MusicGenre(68, "Rave"));
        genres.add(new MusicGenre(69, "Showtunes"));
        genres.add(new MusicGenre(70, "Trailer"));
        genres.add(new MusicGenre(71, "Lo-Fi"));
        genres.add(new MusicGenre(72, "Tribal"));
        genres.add(new MusicGenre(73, "Acid Punk"));
        genres.add(new MusicGenre(74, "Acid Jazz"));
        genres.add(new MusicGenre(75, "Polka"));
        genres.add(new MusicGenre(76, "Retro"));
        genres.add(new MusicGenre(77, "Musical"));
        genres.add(new MusicGenre(78, "Rock & Roll"));
        genres.add(new MusicGenre(79, "Hard Rock"));
    }

    public Manager(File file) {
        this();
        setFile(file);
    }

    public ArrayList<MusicGenre> getGenres() {
        return this.genres;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Desserializa o objeto música
     * @return Música com o estado recuperado do arquivo
     * @throws Exception
     */
    public Music readMusic() throws Exception {
        Music music = new Music();

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")){
            long length = raf.length();
            raf.seek(length - 128);

            byte[] byteArray = new byte[3];
            raf.read(byteArray);

            if(!new String(byteArray).equals("TAG")){
                return null;
            }

            byteArray = new byte[30];
            raf.read(byteArray);
            music.setTitle(new String(byteArray));

            raf.read(byteArray);
            music.setArtist(new String(byteArray));

            raf.read(byteArray);
            music.setAlbum(new String(byteArray));

            byteArray = new byte[4];
            raf.read(byteArray);
            music.setYear(new String(byteArray));

            byteArray = new byte[28];
            raf.read(byteArray);
            music.setNote(new String(byteArray));

            byteArray = new byte[1];
            raf.read(byteArray);

            raf.read(byteArray);
            music.setTrack(Byte.valueOf(byteArray[0]).intValue());

            raf.read(byteArray);
            music.setGenre(genres.get(Byte.valueOf(byteArray[0]).intValue()));
        }

        return music;
    }

    /**
     * Serializa a música no formato ID3v1.1
     * @param music Música a ser serializada
     * @return Verdadeiro, se possível salvar
     * @throws Exception
     */
    public boolean saveMusic(Music music) throws Exception {
        boolean result;

        try(RandomAccessFile raf = new RandomAccessFile(file, "rw")){
            raf.seek(raf.length() - 128);

            byte[] byteArray = new byte[3];
            raf.read(byteArray);
            String tag = new String(byteArray);

            if(!(tag.equals("TAG"))){
                raf.seek(raf.length());
                raf.write("TAG".getBytes());
            }

            raf.write(padBytes(30, music.getTitle()));
            raf.write(padBytes(30, music.getArtist()));
            raf.write(padBytes(30, music.getAlbum()));
            raf.write(padBytes(4, music.getYear()));
            raf.write(padBytes(28, music.getNote()));
            raf.write(0);
            raf.write(music.getTrack());
            raf.write(music.getGenre().getId());

            result = true;
        }

        return result;
    }

    public boolean deleteMusic() throws IOException {
        try(RandomAccessFile raf = new RandomAccessFile(file, "rw")){
            raf.seek(raf.length() - 128);

            byte[] byteArray = new byte[3];
            raf.read(byteArray);
            String tag = new String(byteArray);

            if(!(tag.equals("TAG"))){
                return false;
            }

            raf.setLength(raf.length() - 128);

            return true;
        }
    }

    /**
     * Retorna um vetor de bytes do conteúdo com tamanho fixo
     * @param length Tamanho do vetor
     * @param content Conteúdo a ser incluido no vetor
     * @return Vetor de bytes
     */
    private byte[] padBytes(int length, String content){
        byte[] bytes = new byte[length];

        for (int i = 0; i < bytes.length; i++){
            if(i < content.getBytes().length){
                bytes[i] = content.getBytes()[i];
            }
        }

        return bytes;
    }
}
