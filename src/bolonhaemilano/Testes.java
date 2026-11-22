package bolonhaemilano;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Testes automatizados das regras do jogo, sem JUnit: cada teste segue o
 * padrao preparar -> executar -> verificar. Nenhuma classe do jogo conhece
 * esta classe.
 *
 * Como rodar: java -cp bin bolonhaemilano.Testes
 */
public class Testes {

    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) {
        System.out.println("===== TESTES BOLONHA E MILANO =====");
        testarCidade();
        testarMercado();
        testarAgricultura();
        testarGoverno();
        testarConstrucoes();
        testarJogadorENobreza();
        testarPartida();
        testarEventos();
        testarHistorico();
        testarPersistencia();

        System.out.println();
        System.out.println("Aprovados: " + aprovados + " | Reprovados: " + reprovados);
        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK] " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    private static void testarCidade() {
        System.out.println();
        System.out.println("-- Cidade --");
        Cidade cidade = novaCidade();
        verificar("cidade nasce com 1000 florins", cidade.getFlorins() == 1000);
        verificar("cidade nasce no ano 1400", cidade.getAnoAtual() == 1400);

        boolean removeu = cidade.removerFlorins(1500);
        verificar("removerFlorins acima do saldo devolve false", !removeu);
        verificar("... e nao altera o saldo", cidade.getFlorins() == 1000);

        cidade.marcarColheitaFeita();
        cidade.avancarAno();
        verificar("avancarAno soma 1 ao ano", cidade.getAnoAtual() == 1401);
        verificar("avancarAno libera a colheita de novo", !cidade.isColheitaFeita());
    }

    private static void testarMercado() {
        System.out.println();
        System.out.println("-- Mercado --");
        Mercado mercado = new Mercado();

        Cidade cidade = novaCidade();
        boolean comprou = mercado.comprarGraos(cidade, 100);
        verificar("comprar 100 graos da certo", comprou);
        verificar("... e custa 200 florins", cidade.getFlorins() == 800);
        verificar("... e soma 100 graos", cidade.getGraos() == 600);

        Cidade outra = novaCidade();
        verificar("comprar quantidade negativa e recusado", !mercado.comprarGraos(outra, -10));
        verificar("comprar sem florins suficientes e recusado", !mercado.comprarGraos(outra, 600));
    }

    private static void testarAgricultura() {
        System.out.println();
        System.out.println("-- Agricultura --");
        Agricultura agricultura = new Agricultura();

        Cidade cidade = novaCidade();
        int hectares = agricultura.processarColheita(cidade);
        verificar("primeira colheita do ano cultiva 1000 hectares", hectares == 1000);
        verificar("... e deixa 2000 graos (500 - 500 sementes + 2000)", cidade.getGraos() == 2000);
        verificar("segunda colheita no mesmo ano devolve -1", agricultura.processarColheita(cidade) == -1);

        Cidade outra = novaCidade();
        verificar("distribuir 10% e recusado (minimo 20%)", !agricultura.distribuirAlimentos(outra, 10));
    }

    private static void testarGoverno() {
        System.out.println();
        System.out.println("-- Governo --");
        Governo governo = new Governo();

        Cidade cidade = novaCidade();
        verificar("taxa de 60% e recusada (maximo 50%)", !governo.definirTaxaImposto(cidade, 60));
        verificar("justica \"Branda\" e recusada", !governo.definirJustica(cidade, "Branda"));

        governo.definirJustica(cidade, "Severa");
        int arrecadado = governo.arrecadarImpostos(cidade);
        verificar("200 servos, 10%, Severa: arrecada 26 florins", arrecadado == 26);
        verificar("segunda arrecadacao no mesmo ano devolve -1", governo.arrecadarImpostos(cidade) == -1);
    }

    private static void testarConstrucoes() {
        System.out.println();
        System.out.println("-- Construcoes --");
        Construcoes construcoes = new Construcoes();

        // preparar: florins bastam (500 de 1000), servos nao (so 50)
        Cidade cidade = novaCidade();
        cidade.removerServos(150);
        // executar
        boolean recrutou = construcoes.recrutarSoldados(cidade, 100);
        // verificar
        verificar("recrutar 100 soldados com so 50 servos e recusado", !recrutou);
        verificar("... e NAO debita florins pela metade", cidade.getFlorins() == 1000);

        Cidade outra = novaCidade();
        verificar("construir 10 da certo", construcoes.construir(outra, 10));
        verificar("... e custa 200 florins", outra.getFlorins() == 800);
    }

    private static void testarJogadorENobreza() {
        System.out.println();
        System.out.println("-- Jogador e Nobreza --");
        Nobreza nobreza = new Nobreza();

        Cidade cidade = novaCidade();
        verificar("jogador comeca como Cavaleiro", cidade.getDono().getTitulo().equals("Cavaleiro"));
        verificar("prestigio inicial e 300 (200 servos + 10000/100 terras)", nobreza.calcularPrestigio(cidade) == 300);
        verificar("com prestigio 300 nao sobe de titulo", !nobreza.verificarPromocao(cidade));

        // 50 construcoes: prestigio 800, daria para Marques, mas sobe so um titulo
        cidade.adicionarConstrucoes(50);
        boolean subiu = nobreza.verificarPromocao(cidade);
        verificar("com 50 construcoes sobe para Barao", subiu && cidade.getDono().getTitulo().equals("Barao"));
        verificar("sobe no maximo um titulo por chamada", cidade.getDono().getNivelTitulo() == 1);

        Jogador rei = new Jogador("Monarca");
        while (rei.promover()) {
            // promove ate o ultimo titulo
        }
        verificar("promover para alem de Rei/Rainha e recusado",
                rei.ehMonarca() && !rei.promover() && rei.getTitulo().equals("Rei/Rainha"));
    }

    private static void testarPartida() {
        System.out.println();
        System.out.println("-- Partida --");
        Nobreza nobreza = new Nobreza();
        Partida partida = new Partida();
        Cidade bolonha = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade milano = new Cidade("Milano", new Jogador("Beto"));
        partida.adicionarCidade(bolonha);
        partida.adicionarCidade(milano);

        verificar("primeiro turno e de Bolonha", partida.getCidadeAtual() == bolonha);
        verificar("passar o turno de Bolonha NAO fecha o ano", !partida.passarTurno());
        verificar("agora e a vez de Milano", partida.getCidadeAtual() == milano);
        verificar("passar o turno de Milano fecha o ano", partida.passarTurno());

        milano.adicionarConstrucoes(20); // prestigio 500: Milano vira Barao
        List<Cidade> promovidas = partida.encerrarAno(nobreza);
        verificar("encerrarAno avanca todas as cidades",
                bolonha.getAnoAtual() == 1401 && milano.getAnoAtual() == 1401 && promovidas.size() == 1);
        verificar("partida ainda nao terminou em 1401", !partida.terminou());
        verificar("vencedor parcial e Milano (titulo mais alto)", partida.determinarVencedor(nobreza) == milano);

        partida.adicionarCidade(new Cidade("Florenca", new Jogador("Caio")));
        partida.adicionarCidade(new Cidade("Veneza", new Jogador("Dora")));
        boolean aceitouQuinta = partida.adicionarCidade(new Cidade("Roma", new Jogador("Eva")));
        verificar("quinta cidade e recusada (maximo 4)", !aceitouQuinta);
    }

    // EXTRA: eventos aleatorios. O numero do sorteio e escolhido pelo teste.
    private static void testarEventos() {
        System.out.println();
        System.out.println("-- Eventos (extra) --");
        Eventos eventos = new Eventos(new Random(42));

        Cidade cidade = novaCidade();
        eventos.aplicarEvento(cidade, 0);
        verificar("ratos levam 20% dos graos (500 -> 400)", cidade.getGraos() == 400);

        Cidade doente = novaCidade();
        eventos.aplicarEvento(doente, 20);
        verificar("peste mata 10% dos servos (200 -> 180)", doente.getServos() == 180);

        Cidade semDefesa = novaCidade();
        eventos.aplicarEvento(semDefesa, 30);
        verificar("saqueadores levam 25% dos florins de cidade sem soldados", semDefesa.getFlorins() == 750);

        Cidade defendida = novaCidade();
        defendida.adicionarSoldados(20); // 20 soldados para 200 servos
        eventos.aplicarEvento(defendida, 30);
        verificar("com 1 soldado para cada 10 servos os saqueadores sao expulsos", defendida.getFlorins() == 1000);

        Cidade tranquila = novaCidade();
        eventos.aplicarEvento(tranquila, 99);
        verificar("ano tranquilo nao altera nada",
                tranquila.getGraos() == 500 && tranquila.getServos() == 200 && tranquila.getFlorins() == 1000);

        Cidade sorteada = novaCidade();
        String descricao = eventos.sortearEvento(sorteada);
        verificar("sortearEvento sempre devolve uma descricao", descricao != null && !descricao.isEmpty());
    }

    // EXTRA: historico anual
    private static void testarHistorico() {
        System.out.println();
        System.out.println("-- Historico (extra) --");
        Nobreza nobreza = new Nobreza();
        Partida partida = new Partida();
        Cidade bolonha = novaCidade();
        partida.adicionarCidade(bolonha);

        verificar("cidade nova nao tem historico", bolonha.getHistorico().isEmpty());

        bolonha.adicionarConstrucoes(10); // prestigio 400: vira Barao ao fechar 1400
        partida.passarTurno();
        partida.encerrarAno(nobreza);
        RegistroAnual registro = bolonha.getHistorico().get(0);
        verificar("encerrarAno registra o ano que fechou (1400)",
                bolonha.getHistorico().size() == 1 && registro.getAno() == 1400);
        verificar("... com o prestigio e o titulo ja promovido",
                registro.getPrestigio() == 400 && registro.getTitulo().equals("Barao"));
    }

    // EXTRA: salvar e carregar a partida
    private static void testarPersistencia() {
        System.out.println();
        System.out.println("-- Persistencia (extra) --");
        Persistencia persistencia = new Persistencia();
        Nobreza nobreza = new Nobreza();

        // preparar: partida com 2 cidades, um ano fechado, vez de Milano
        Partida partida = new Partida();
        Cidade bolonha = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade milano = new Cidade("Milano", new Jogador("Beto"));
        partida.adicionarCidade(bolonha);
        partida.adicionarCidade(milano);
        bolonha.adicionarConstrucoes(10);
        partida.passarTurno();
        partida.passarTurno();
        partida.encerrarAno(nobreza);
        new Governo().definirJustica(milano, "Severa");
        new Agricultura().processarColheita(milano);
        partida.passarTurno();

        File arquivo;
        try {
            arquivo = File.createTempFile("bolonha-teste", ".txt");
            arquivo.deleteOnExit();
        } catch (IOException e) {
            verificar("criar arquivo temporario para o teste", false);
            return;
        }

        // executar
        boolean salvou = persistencia.salvar(partida, arquivo.getPath());
        Partida carregada = persistencia.carregar(arquivo.getPath());

        // verificar
        verificar("salvar a partida da certo", salvou);
        verificar("carregar devolve uma partida com as 2 cidades",
                carregada != null && carregada.getCidades().size() == 2);
        if (carregada == null) {
            return;
        }
        Cidade b = carregada.getCidades().get(0);
        Cidade m = carregada.getCidades().get(1);
        verificar("... mantem ano, titulo e recursos",
                b.getAnoAtual() == 1401 && b.getDono().getTitulo().equals("Barao")
                        && b.getConstrucoes() == 10 && b.getFlorins() == bolonha.getFlorins());
        verificar("... mantem justica e colheita feita no ano",
                m.getNivelJustica().equals("Severa") && m.isColheitaFeita() && m.getGraos() == milano.getGraos());
        verificar("... mantem de quem e a vez (Milano)", carregada.getCidadeAtual().getNome().equals("Milano"));
        verificar("... mantem o historico", b.getHistorico().size() == 1 && b.getHistorico().get(0).getAno() == 1400);
        verificar("carregar arquivo inexistente devolve null",
                persistencia.carregar(arquivo.getPath() + "-nao-existe") == null);
    }
}
