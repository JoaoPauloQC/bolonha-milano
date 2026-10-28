import java.util.Scanner;

public class Menu{

    public void executar(){
        Scanner sc = new Scanner(System.in);
        boolean emExecucao = true;
        while (emExecucao) {
            
        
            exibirOpcoes();
            int decision = lerOpcao(sc.next());
            if (decision < 0 || decision > 2 ){
                if (decision == 1){
                    iniciarNovoJogo();
                }
                if (decision == 2){
                    exibirSobre();
                }
            }
            else{
                emExecucao = false;
            }
        }
    }

    public static void exibirOpcoes(){
        System.out.println("Bolonha e Milano \n -------------------------- \n1- Novo jogo \n2- Sobre o jogo \n0 - Sair");
    }

    public void iniciarNovoJogo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o nome do jogador: ");
        String nomeJogador = sc.next();
        System.out.println("Insira o nome da cidade: ");
        String nomeCidade = sc.next();

        Jogador jogador = new Jogador(nomeJogador);
        Cidade cidade = new Cidade(nomeCidade, 1400, jogador);
        System.out.println("Novo jogo iniciado");
        cidade.exibirStatus();
    }

    public int lerOpcao(String s){
        try{
            Integer num = Integer.parseInt(s);
            return num;
        }
        catch(Exception e){
            return -1;
        }
    }

    public void exibirSobre(){
        System.out.println("Bolonha e Milano é um projeto para a matéria de Teoria e Desenvolvimento de Sistemas no quarto bimestre.");
    }
    public void abrirMercado(){
        
    }
    public int lerQuantidade(){
        return 0;
    }
    public void comprarGraos(){

    }
    public void venderGraos(){

    }
    public void comprarTerras(){

    }
    public void venderTerras(){

    }

}