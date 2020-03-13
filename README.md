# Leitor de metadados de MP3

Projeto de uma aplicação desktop capaz de ler e gravar metadados MP3, no formato ID3v1.1.

Esse projeto foi construído para atender ao **Trabalho I** da disciplina de **Programação II**, ministrada pelo professor Gilvan Justino na 3ª fase do curso de Sistemas de Informação, da Fundação Universidade Regional de Blumenau.

### Alunos responsáveis pelo projeto:

Lucas Samuel Kluser\
lkluser@furb.br

Matheus Leopoldo dos Santos Boing\
mlsboing@furb.br

---
## Sobre o projeto
O projeto foi construído utilizando a arquitetura MVC (Model - View - Controller). Entretanto, para não aumentar a complexidade ou tempo despendido na execução do projeto, não foram empregados todos (ou foram empregados parcialmente) os conceitos dessa arquitetura.

## Diagrama de classes
![Diagrama de classes](https://github.com/magicboing/trabalho1/blob/master/diagrama.png?raw=true)

## Como executar localmente

1. Clone o repositório em sua máquina local\
   `git clone https://github.com/magicboing/trabalho1`
2. Importe o projeto em seu IDE
3. Execute o projeto através do método **main()**, da classe **Main**

### Dicas de uso

1. Ao pressionar `Enter` no campo **Arquivo**, o leitor tentará abrir o arquivo no caminho especificado no campo **Arquivo**.
2. Ao pressionar `Enter` no campo **Arquivo**, a janela de seleção de arquivo será aberta se:
   1. O caminho para o arquivo no campo **Arquivo** for referente ao arquivo já aberto no leitor;
   2. O caminho no campo **Arquivo** for um diretório.
