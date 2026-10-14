# Bolonha e Milano

Projeto do 4º bimestre de SPOTDES (IFSP): o jogo **Bolonha e Milano** — estratégia econômica medieval —
implementado em Java, passo a passo, em 8 etapas incrementais.

## Como compilar e executar

Requisitos: Java 17 ou superior (sem build tool externo). A partir da pasta do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Estado atual (Etapa 1)

Menu principal em laço com 3 opções (Novo jogo, Sobre o jogo, Sair). Entradas inválidas
(texto ou número fora da lista) são tratadas sem travar o programa.

## Organização das pastas

| Pasta | Conteúdo |
|-------|----------|
| `src/bolonhaemilano/` | Código-fonte Java (pacote `bolonhaemilano`) |
| `uml/` | Diagrama de classes (`.puml` + imagem `.png`) |
| `casos-de-uso/` | Diagrama de casos de uso (a partir da Etapa 2) |
