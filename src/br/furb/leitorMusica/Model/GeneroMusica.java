   package br.furb.leitorMusica.Model;

public class GeneroMusica {
   private int codigo;
   private String descricao;

   public GeneroMusica(int codigo, String descricao) {
      setCodigo(codigo);
      setDescricao(descricao);
   }

   public int getCodigo() {
      return codigo;
   }

   public void setCodigo(int codigo) {
      if(codigo < -1){
         throw new IllegalArgumentException("Código deve ser maior que zero.");
      }
      this.codigo = codigo;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public String toString() {
      return this.getDescricao();
   }
}
