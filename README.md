# Bolonha e Milano

Projeto do 4º bimestre de SPOTDES (IFSP): o jogo **Bolonha e Milano** — estratégia econômica e
política ambientada na Itália medieval — implementado em Java, passo a passo, em 8 etapas incrementais.

De 1 a 4 jogadores governam cada um sua cidade-Estado (Bolonha, Milano, Florença, Veneza),
equilibrando agricultura, impostos, construções e defesa. A cada ano, quem tem prestígio
suficiente sobe um título de nobreza. **Objetivo:** chegar a Rei/Rainha primeiro, ou ter o
título mais alto ao fim de 1410.

## Como compilar e executar

Requisitos: Java 17 ou superior, sem build tool externo. A partir da pasta do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Como rodar os testes

A classe `Testes` compila junto com o jogo (mesmo `javac`) e tem seu próprio `main`:

```bash
java -cp bin bolonhaemilano.Testes
```

Saída esperada no final: `Aprovados: 43 | Reprovados: 0` (37 testes das regras do enunciado + 6 do extra). Se algum teste falhar, aparece
`[FALHA] <descrição>` e o programa termina com código de saída 1.

## Como jogar

### Menu principal

No topo aparece sempre o ano e de quem é a vez. As ações valem para a cidade do jogador da vez.

| Opção | O que faz |
|-------|-----------|
| `1` Novo jogo | Escolhe de 1 a 4 jogadores, seus nomes e cidades (Enter aceita a cidade sugerida) |
| `2` Ver status da cidade | Mostra todos os recursos da cidade da vez |
| `3` Mercado | Comprar/vender grãos (2 florins cada) e terras (1 florim/ha) |
| `4` Agricultura | Colher a safra (1 vez por ano) e distribuir alimentos (20% a 80% dos grãos) |
| `5` Governo | Arrecadar impostos (1 vez por ano), definir taxa (0 a 50%) e nível de justiça |
| `6` Construções | Recrutar soldados (5 florins + 1 servo) e construir (20 florins) |
| `7` Encerrar turno | Passa a vez; o último jogador da rodada fecha o ano |
| `8` Ver classificação | Prestígio de cada cidade e meta do próximo título |
| `9` Sobre o jogo | Resumo das regras |
| `0` Sair | Encerra o programa |

Cada submenu (3 a 6) tem a opção `0` para voltar. Entradas inválidas nunca travam o programa.

### Regras principais

- **Toda cidade começa igual (ano 1400):** 1000 florins, 10000 ha de terras, 500 grãos,
  200 servos, imposto 10%, justiça Moderada, 0 soldados, 0 construções.
- **Colher:** hectares = min(terras, servos × 5); gasta hectares / 2 grãos de semente;
  produz hectares × 2 grãos.
- **Distribuir alimentos:** se o alimento ≥ servos / 5, a população cresce 5%; senão perde o déficit.
- **Impostos:** servos × taxa / 100 × multiplicador da justiça (Justa 0,8 · Moderada 1,0 · Severa 1,3).
- **Prestígio:** construções × 10 + soldados × 2 + servos + terras / 100 (florins não contam).
- **Títulos:** Cavaleiro → Barão (400) → Conde (550) → Marquês (700) → Duque (850) →
  Rei/Rainha (1000). No máximo um título por ano.
- **Fim de jogo:** alguém vira Rei/Rainha ou termina o ano de 1410. Vence o título mais alto;
  empate, maior prestígio.

### Extra: eventos aleatórios

Melhoria opcional (classe `Eventos`): no começo de cada novo ano, cada cidade sofre um evento sorteado:

| Sorteio | Evento | Efeito |
|---------|--------|--------|
| 15% | Ratos | −20% dos grãos |
| 10% | Peste | −10% dos servos |
| 15% | Saqueadores | −25% dos florins, **a menos que** a cidade tenha 1 soldado para cada 10 servos |
| 15% | Boa safra | +20% dos grãos |
| 10% | Imigrantes | +5% dos servos |
| 35% | Ano tranquilo | nada muda |

Com isso os soldados passam a ter uma função defensiva, além do prestígio. Os eventos acontecem
depois da classificação do ano, então o fechamento do ano continua igual ao do enunciado.

### Exemplo (Ana × Beto, ano 1400)

```
1 → 2 jogadores → Ana (Bolonha) e Beto (Milano)
Ana:  4→1 colhe · 4→2→50 distribui · 5→3→3 Severa · 5→1 arrecada 27 · 6→2→10 constrói · 7
Beto: 4→1 colhe · 4→2→50 distribui · 5→2→20 taxa 20% · 5→1 arrecada 42 · 6→1→30 soldados · 7

========= FIM DO ANO 1400 =========
Ana, de Bolonha, agora e Barao!
Bolonha | Ana (Barao) | prestigio 410 (proximo titulo com 550)
Milano | Beto (Cavaleiro) | prestigio 340 (proximo titulo com 400)
```

## Organização das pastas

| Pasta | Conteúdo |
|-------|----------|
| `src/bolonhaemilano/` | Código-fonte Java (pacote `bolonhaemilano`), incluindo `Testes.java` |
| `uml/` | Diagrama de classes e diagrama de sequência do fim do ano (`.puml` + `.png`) |
| `casos-de-uso/` | Diagrama de casos de uso (`.puml` + `.png`) |

### Classes

| Classe | Responsabilidade |
|--------|------------------|
| `Main` | Ponto de entrada: cria o `Menu` e manda executar |
| `Menu` | Laço principal, submenus e leitura do teclado; delega as regras às outras classes |
| `Partida` | Guarda as cidades (1 a 4), turnos, fim do ano, fim de jogo e vencedor |
| `Jogador` | Quem governa: nome e nível do título (`promover()`, `ehMonarca()`) |
| `Cidade` | O que é governado: ano, recursos, configurações de governo e indicadores do ano |
| `Mercado` | Compra e venda de grãos e terras |
| `Agricultura` | Colheita e distribuição de alimentos |
| `Governo` | Impostos, taxa e nível de justiça |
| `Construcoes` | Recrutar soldados e construir |
| `Nobreza` | Cálculo do prestígio e promoção de títulos |
| `Eventos` | **Extra:** eventos aleatórios no começo de cada ano |
| `Testes` | Testes automatizados das regras (preparar → executar → verificar) |

## Histórico das etapas

Cada etapa tem seu(s) commit(s) `etapa N` e a tag `etapa-N`.

1. **Estrutura inicial, Main e menu** — menu em laço com validação de entrada.
2. **Jogador e Cidade** — "Novo jogo" cria os objetos; "Ver status da cidade".
3. **Economia** — florins, terras e grãos; classe `Mercado`.
4. **Agricultura e população** — servos; classe `Agricultura`.
5. **Impostos e governo** — taxa e justiça; classe `Governo`.
6. **Construções e soldados** — classe `Construcoes`, validando dois recursos antes de debitar.
7. **Rodadas, múltiplos jogadores e vitória** — classes `Partida` e `Nobreza`.
8. **Integração, testes e entrega final** — classe `Testes`, refatoração do `Menu`
   (`lerInteiro()` e `exigirJogoEmAndamento()`, sem mudar o comportamento), diagramas finais
   (incluindo o de sequência) e este README. Extra: eventos aleatórios (classe `Eventos`).
