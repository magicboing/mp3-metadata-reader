/**
 * Matheus Boing & Lucas Samuel
 * Disciplina de Programação II
 * Trabalho I
 */
package br.furb.musicDataReader.Model;

/**
 * Gênero da música
 */
public class MusicGenre {
   private int id;
   private String title;

   public MusicGenre(int id, String title) {
      setId(id);
      setTitle(title);
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      if(id < -1){
         throw new IllegalArgumentException("Código deve ser maior que zero.");
      }
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   /**
    * Retorna o título do gênero
    * @return título do gênero
    */
   public String toString() {
      return this.getTitle();
   }
}
