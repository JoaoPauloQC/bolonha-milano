package bolonhaemilano;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controla o laco principal do jogo: exibe as opcoes, le e valida a escolha
 * do jogador e delega o trabalho para as outras classes. Guarda a Partida e
 * a cidade de quem esta jogando (a cidade da vez).
 */
public class Menu {

    private static final String[] CIDADES_SUGERIDAS = {"Bolonha", "Milano", "Florenca", "Veneza"};

    private Scanner scanner;
    private boolean emExecucao;
    private Partida partida;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;
    private Construcoes construcoes;
    private Nobreza nobreza;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        mercado = new Mercado();
        agricultura = new Agricultura();
        governo = new Governo();
        construcoes = new Construcoes();
        nobreza = new Nobreza();
    }

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("             BOLONHA E MILANO");
        System.out.println("  Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        if (partida != null) {
            System.out.println("Ano " + partida.getAnoAtual() + " | Vez de " + cidade.getDono().getNome()
                    + " (" + cidade.getDono().getTitulo() + "), de " + cidade.getNome());
            System.out.println("------------------------------------------");
        }
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificacao");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return 0; // fim da entrada: encerra o jogo / volta
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirStatus();
                break;
            case 3:
                abrirMercado();
                break;
            case 4:
                abrirAgricultura();
                break;
            case 5:
                abrirGoverno();
                break;
            case 6:
                abrirConstrucoes();
                break;
            case 7:
                encerrarTurno();
                break;
            case 8:
                exibirClassificacao();
                break;
            case 9:
                exibirSobre();
                break;
            case 0:
                System.out.println("Ate a proxima!");
                emExecucao = false;
                break;
            default:
                System.out.println("Opcao invalida. Tente novamente.");
                break;
        }
    }

    private void iniciarNovoJogo() {
        System.out.print("Quantos jogadores (1 a " + Partida.MAXIMO_JOGADORES + ")? ");
        int quantidade = lerOpcao();
        if (quantidade < 1 || quantidade > Partida.MAXIMO_JOGADORES) {
            quantidade = 2;
            System.out.println("Quantidade invalida: a partida tera 2 jogadores.");
        }

        partida = new Partida();
        for (int i = 0; i < quantidade; i++) {
            System.out.print("Nome do jogador " + (i + 1) + ": ");
            String nomeJogador = scanner.nextLine().trim();
            if (nomeJogador.isEmpty()) {
                nomeJogador = "Jogador " + (i + 1);
            }
            String sugerida = CIDADES_SUGERIDAS[i];
            System.out.print("Nome da cidade de " + nomeJogador + " (Enter = " + sugerida + "): ");
            String nomeCidade = scanner.nextLine().trim();
            if (nomeCidade.isEmpty()) {
                nomeCidade = sugerida;
            }
            partida.adicionarCidade(new Cidade(nomeCidade, new Jogador(nomeJogador)));
        }

        cidade = partida.getCidadeAtual();
        System.out.println();
        System.out.println("Novo jogo iniciado! Ano " + partida.getAnoAtual() + ". A partida vai ate");
        System.out.println("o fim de " + partida.getAnoFinal() + " ou ate alguem virar Rei/Rainha.");
        System.out.println("Comeca: " + cidade.getDono().getNome() + " (" + cidade.getDono().getTitulo()
                + "), de " + cidade.getNome() + ".");
    }

    private void exibirStatus() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        cidade.exibirStatus();
    }

    // ===================== MERCADO (Etapa 3) =====================

    private void abrirMercado() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        boolean noMercado = true;
        while (noMercado) {
            System.out.println();
            System.out.println("--------------- MERCADO ---------------");
            System.out.println("Preco do grao: " + mercado.getPrecoGrao() + " florins | Preco da terra: "
                    + mercado.getPrecoTerra() + " florim/hectare");
            System.out.println("Florins: " + cidade.getFlorins() + " | Graos: " + cidade.getGraos()
                    + " | Terras: " + cidade.getTerras() + " ha");
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    comprarGraos();
                    break;
                case 2:
                    venderGraos();
                    break;
                case 3:
                    comprarTerras();
                    break;
                case 4:
                    venderTerras();
                    break;
                case 0:
                    noMercado = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }

    private int lerQuantidade() {
        System.out.print("Quantidade: ");
        if (!scanner.hasNextLine()) {
            return -1;
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void comprarGraos() {
        int quantidade = lerQuantidade();
        if (mercado.comprarGraos(cidade, quantidade)) {
            System.out.println("Compra realizada: +" + quantidade + " graos.");
        } else {
            System.out.println("Compra recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    private void venderGraos() {
        int quantidade = lerQuantidade();
        if (mercado.venderGraos(cidade, quantidade)) {
            System.out.println("Venda realizada: -" + quantidade + " graos.");
        } else {
            System.out.println("Venda recusada: quantidade invalida ou graos insuficientes.");
        }
    }

    private void comprarTerras() {
        int quantidade = lerQuantidade();
        if (mercado.comprarTerras(cidade, quantidade)) {
            System.out.println("Compra realizada: +" + quantidade + " hectares.");
        } else {
            System.out.println("Compra recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    private void venderTerras() {
        int quantidade = lerQuantidade();
        if (mercado.venderTerras(cidade, quantidade)) {
            System.out.println("Venda realizada: -" + quantidade + " hectares.");
        } else {
            System.out.println("Venda recusada: quantidade invalida ou terras insuficientes.");
        }
    }

    // ===================== AGRICULTURA (Etapa 4) =====================

    private void abrirAgricultura() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        boolean naAgricultura = true;
        while (naAgricultura) {
            System.out.println();
            System.out.println("------------- AGRICULTURA -------------");
            System.out.println("Terras: " + cidade.getTerras() + " ha | Servos: " + cidade.getServos()
                    + " | Graos: " + cidade.getGraos());
            System.out.println("1 - Colher (processar safra)");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    naAgricultura = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }

    private void colher() {
        int hectares = agricultura.processarColheita(cidade);
        if (hectares == -1) {
            System.out.println("A colheita deste ano ja foi feita. Aguarde o proximo ano.");
            return;
        }
        System.out.println("Colheita feita: " + hectares + " hectares cultivados. Graos em estoque: "
                + cidade.getGraos());
    }

    private void distribuirAlimentos() {
        System.out.print("Percentual dos graos a distribuir (20 a 80): ");
        int percentual = lerOpcao();
        int servosAntes = cidade.getServos();
        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Alimentos distribuidos. Servos: " + servosAntes + " -> " + cidade.getServos()
                    + " | Graos restantes: " + cidade.getGraos());
        } else {
            System.out.println("Distribuicao recusada: o percentual deve ficar entre 20 e 80.");
        }
    }

    // ===================== GOVERNO (Etapa 5) =====================

    private void abrirGoverno() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        boolean noGoverno = true;
        while (noGoverno) {
            System.out.println();
            System.out.println("--------------- GOVERNO ---------------");
            System.out.println("Florins: " + cidade.getFlorins() + " | Taxa de imposto: " + cidade.getTaxaImposto()
                    + "% | Justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);
        if (arrecadado == -1) {
            System.out.println("Os impostos deste ano ja foram arrecadados. Aguarde o proximo ano.");
            return;
        }
        System.out.println("Impostos arrecadados: " + arrecadado + " florins. Florins: " + cidade.getFlorins());
    }

    private void definirTaxaImposto() {
        System.out.print("Nova taxa de imposto (0 a 50): ");
        int taxa = lerOpcao();
        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("Taxa de imposto definida em " + taxa + "%.");
        } else {
            System.out.println("Taxa recusada: deve ficar entre 0 e 50.");
        }
    }

    private void definirJustica() {
        System.out.println("1 - Justa (arrecadacao x0,8)");
        System.out.println("2 - Moderada (arrecadacao x1,0)");
        System.out.println("3 - Severa (arrecadacao x1,3)");
        System.out.print("Escolha o nivel de justica: ");
        int opcao = lerOpcao();
        String nivel;
        switch (opcao) {
            case 1:
                nivel = "Justa";
                break;
            case 2:
                nivel = "Moderada";
                break;
            case 3:
                nivel = "Severa";
                break;
            default:
                nivel = "";
                break;
        }
        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("Nivel de justica definido: " + nivel + ".");
        } else {
            System.out.println("Opcao invalida: o nivel de justica nao foi alterado.");
        }
    }

    // ===================== CONSTRUCOES (Etapa 6) =====================

    private void abrirConstrucoes() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        boolean naConstrucoes = true;
        while (naConstrucoes) {
            System.out.println();
            System.out.println("------------- CONSTRUCOES -------------");
            System.out.println("Florins: " + cidade.getFlorins() + " | Servos: " + cidade.getServos()
                    + " | Soldados: " + cidade.getSoldados() + " | Construcoes: " + cidade.getConstrucoes());
            System.out.println("1 - Recrutar soldados (" + construcoes.getPrecoSoldado() + " florins + 1 servo cada)");
            System.out.println("2 - Construir (" + construcoes.getPrecoConstrucao() + " florins cada)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    recrutarSoldados();
                    break;
                case 2:
                    construir();
                    break;
                case 0:
                    naConstrucoes = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }

    private void recrutarSoldados() {
        int quantidade = lerQuantidade();
        if (construcoes.recrutarSoldados(cidade, quantidade)) {
            System.out.println("Recrutamento feito: +" + quantidade + " soldados.");
        } else {
            System.out.println("Recrutamento recusado: quantidade invalida, florins ou servos insuficientes.");
        }
    }

    private void construir() {
        int quantidade = lerQuantidade();
        if (construcoes.construir(cidade, quantidade)) {
            System.out.println("Obra concluida: +" + quantidade + " construcoes.");
        } else {
            System.out.println("Construcao recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    // ===================== RODADAS E VITORIA (Etapa 7) =====================

    private void encerrarTurno() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        System.out.println("Turno de " + cidade.getDono().getNome() + " encerrado.");
        int anoQueTerminou = partida.getAnoAtual();
        boolean fimDoAno = partida.passarTurno();

        if (fimDoAno) {
            List<Cidade> promovidas = partida.encerrarAno(nobreza);
            System.out.println();
            System.out.println("========= FIM DO ANO " + anoQueTerminou + " =========");
            for (Cidade promovida : promovidas) {
                System.out.println(promovida.getDono().getNome() + ", de " + promovida.getNome()
                        + ", agora e " + promovida.getDono().getTitulo() + "!");
            }
            exibirClassificacao();
            if (partida.terminou()) {
                encerrarPartida();
                return;
            }
            System.out.println("Comeca o ano " + partida.getAnoAtual() + ".");
        }

        cidade = partida.getCidadeAtual();
        System.out.println("Agora e a vez de " + cidade.getDono().getNome() + " (" + cidade.getDono().getTitulo()
                + "), de " + cidade.getNome() + ".");
    }

    private void exibirClassificacao() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        // ordena uma copia da lista: titulo mais alto primeiro; empate, maior prestigio
        List<Cidade> ordem = new ArrayList<>(partida.getCidades());
        ordem.sort((a, b) -> {
            int nivel = b.getDono().getNivelTitulo() - a.getDono().getNivelTitulo();
            if (nivel != 0) {
                return nivel;
            }
            return nobreza.calcularPrestigio(b) - nobreza.calcularPrestigio(a);
        });

        System.out.println("----------- CLASSIFICACAO -----------");
        for (Cidade c : ordem) {
            int necessario = nobreza.prestigioParaProximoTitulo(c.getDono());
            String meta = necessario == -1 ? "titulo maximo" : "proximo titulo com " + necessario;
            System.out.println(c.getNome() + " | " + c.getDono().getNome() + " (" + c.getDono().getTitulo()
                    + ") | prestigio " + nobreza.calcularPrestigio(c) + " (" + meta + ")");
        }
    }

    private void encerrarPartida() {
        Cidade vencedora = partida.determinarVencedor(nobreza);
        System.out.println();
        System.out.println("============ FIM DE JOGO ============");
        System.out.println("Vencedor: " + vencedora.getDono().getNome() + " (" + vencedora.getDono().getTitulo()
                + "), de " + vencedora.getNome() + ", com prestigio " + nobreza.calcularPrestigio(vencedora) + "!");
        System.out.println("Escolha 'Novo jogo' para jogar de novo.");
        partida = null;
        cidade = null;
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e politica");
        System.out.println("ambientado na Italia medieval. De 1 a 4 jogadores governam suas");
        System.out.println("cidades-Estado, um turno por vez; quando o ultimo encerra o turno,");
        System.out.println("o ano fecha e quem tem prestigio suficiente sobe um titulo.");
        System.out.println();
        System.out.println("Titulos: Cavaleiro -> Barao (400) -> Conde (550) -> Marques (700)");
        System.out.println("         -> Duque (850) -> Rei/Rainha (1000)");
        System.out.println("Prestigio = construcoes x 10 + soldados x 2 + servos + terras / 100");
        System.out.println("Colher e arrecadar impostos: uma vez por ano por cidade.");
        System.out.println("Fim: quando alguem vira Rei/Rainha ou ao fim de 1410. Vence o titulo");
        System.out.println("mais alto (empate: maior prestigio).");
    }
}
