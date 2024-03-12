import java.util.*;

public class JogoDaVelha {
    static Scanner sc = new Scanner(System.in);
    static List<Integer> lista = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    static String malha =
            "   |   |   \n" +
                    " 1 | 2 | 3 \n" +
                    "___|___|___\n" +
                    "   |   |   \n" +
                    " 4 | 5 | 6 \n" +
                    "___|___|___\n" +
                    "   |   |   \n" +
                    " 7 | 8 | 9 \n" +
                    "   |   |   ";

    static String[][] matriz = {
            {"1", "2", "3"},
            {"4", "5", "6"},
            {"7", "8", "9"}
    };

    public static final int tamanho_matriz = matriz.length;




    static Pessoa ganhador;

    // Coordenada para indice (tamanho * linha + coluna)
    public static int coordenadaParaIndice(int tamanho_matriz, int linha, int coluna){
        return tamanho_matriz * linha + coluna;
    }

    // Indice para coordenada (Linha: indice // tamanho , Coluna: indice % coluna)
    public static int[] indiceParaCoordenada(int tamanho_matriz, int indice){
        int linha = indice / tamanho_matriz;
        int coluna = indice % tamanho_matriz;
        int[] coordenadas = {linha, coluna};
        return coordenadas;
    }

    public static void print(){
        for(int i = 0; i < tamanho_matriz; i++){
            for(int j = 0; j < tamanho_matriz; j++){

                String indice = String.valueOf(coordenadaParaIndice(tamanho_matriz, i, j) + 1);

                malha = malha.replace(indice, matriz[i][j]);
            }
        }

        System.out.println(malha);
    }

    public static int jogada(Pessoa jogador){
        int posicao;
        while (true){
            System.out.println(jogador.nome + ", digite a posição que você deseja jogar: ");
            posicao = sc.nextInt();

            if(lista.contains(posicao)){
                lista.remove(lista.indexOf(posicao));
                return posicao;

            }else{
                System.out.println("Posição inválida");
            }
        }
    }

    public static void preencherMatriz(Pessoa jogador, Integer posicao){
        int[] coordenada = indiceParaCoordenada(tamanho_matriz, posicao - 1);
        int linha = coordenada[0];
        int coluna = coordenada[1];
        matriz[linha][coluna] = jogador.tag;
    }

    public static void verificarDiagonais(Pessoa jogador){
        List<String> listDiagonal = new ArrayList<>();
        List<String> listDiagonal1 = new ArrayList<>();


        for(int i=0; i< matriz.length; i++){
            listDiagonal.add(matriz[i][i]);
            listDiagonal1.add(matriz[tamanho_matriz - 1 - i][i]);
        }

        if(listDiagonal.stream().allMatch(x -> x.equals(jogador.tag))
                || listDiagonal1.stream().allMatch(x -> x.equals(jogador.tag))){
            lista.clear();
            ganhador = jogador;
        }
    }

    public static void verificarVertical(Pessoa jogador){
        List<String> vertical = new ArrayList<>();

        for(int i=0; i<tamanho_matriz; i++){
            vertical.add(matriz[0][i]);
            vertical.add(matriz[1][i]);
            vertical.add(matriz[2][i]);
            if(vertical.stream().allMatch(x -> x.equals(jogador.tag))){
                lista.clear();
                ganhador = jogador;
            } else{
                vertical.clear();
            }
        }

    }

    public static void verificarHorizontal(Pessoa jogador){
        List<String> vertical = new ArrayList<>();

        for(int i=0; i<tamanho_matriz; i++){
            vertical.add(matriz[i][0]);
            vertical.add(matriz[i][1]);
            vertical.add(matriz[i][2]);
            if(vertical.stream().allMatch(x -> x.equals(jogador.tag))){
                lista.clear();
                ganhador = jogador;
            } else{
                vertical.clear();
            }
        }

    }



    public static void main(String[] args) {

        System.out.print("Digite o nome do Jogador 1: ");
        Pessoa j1 = new Pessoa(sc.next(), "x");

        System.out.print("Digite o nome do Jogador 2: ");
        Pessoa j2 = new Pessoa(sc.next(), "o");

        Pessoa jogador = j1;

        while(!lista.isEmpty()){


            print();
            int posicao = jogada(jogador);
            preencherMatriz(jogador, posicao);
            verificarHorizontal(jogador);
            verificarDiagonais(jogador);
            verificarVertical(jogador);

            if(jogador == j1){
                jogador = j2;
            }else{
                jogador = j1;
            }
        }

        print();

        System.out.println("=======================");
        if(ganhador != null){
            System.out.println("O VENCEDOR É " + ganhador.nome.toUpperCase());
        }else{
            System.out.println("EMPATE!!");
        }

    }
}

class Pessoa{
    String nome;
    String tag;

    Scanner sc = new Scanner(System.in);

    public Pessoa(String nome, String tag){
        this.nome = nome;
        this.tag = tag;
    }
}
