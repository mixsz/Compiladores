# Compilador ABCD

Compilador da linguagem ABCD, desenvolvido em Java como projeto de Compiladores. A linguagem ABCD é traduzida para Go, gerando o arquivo `saida.go`.

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Arquitetura](#arquitetura)
- [Requisitos](#requisitos)
- [Como executar](#como-executar)
- [Características da linguagem](#características-da-linguagem)
- [Exemplos de código](#exemplos-de-código)
- [Syntax Highlighting (VS Code)](#syntax-highlighting-vs-code)
- [Estrutura do projeto](#estrutura-do-projeto)

## Sobre o projeto

ABCD é uma linguagem de programação com sintaxe em português. O compilador implementa um pipeline completo de compilação, desde a análise léxica até a geração de código Go executável.

## Arquitetura

O compilador segue um pipeline tradicional de compilação, dividido em quatro módulos principais:

| Etapa | Função |
|---|---|
| **Lexer** | Reconhece os tokens percorrendo o código fonte, valida cada um conforme as expressões regulares definidas em `lexer/expressaoRegular.txt` e os armazena em uma lista |
| **Parser** | Verifica se a sequência de tokens é válida conforme a GLC definida em `parser/gramaticaLivre.txt`, construindo a AST simultaneamente |
| **Semantic** | Valida a AST (escopos, tipos, redeclarações, variáveis não utilizadas) usando uma pilha de escopos (hash) |
| **CodeGen** | Percorre a AST e gera o código Go equivalente (`saida.go`) |

```
main.ABCD → Lexer → Parser → Semantic → CodeGen → saida.go
```

## Requisitos

- Java JDK
- Go

## Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/mixsz/Compilador.git
   cd Compilador
   ```
2. Compile o projeto:
   ```bash
   javac */*.java Main.java
   ```
3. Escreva seu código no arquivo `main.ABCD`.
4. Execute o compilador:
   ```bash
   java -cp . Main
   ```
5. O código Go gerado é salvo em `saida.go` e executado automaticamente.

## Características da linguagem

- Identificadores devem começar com letra minúscula e não podem conter caracteres especiais.
- Palavras reservadas são escritas em maiúsculas e em português (ex: `SE`, `ENQUANTO`, `INTEIRO`).
- Todas as instruções, incluindo comentários, só são válidas após a declaração `INICIE:`.
- É possível declarar e ler uma variável na mesma linha: `INTEIRO id = LEIA(INTEIRO);`.
- Três tipos de variáveis: `INTEIRO` (int), `DECIMAL` (float) e `TEXTO` (string).
- Operadores aritméticos: `+`, `-`, `*`, `/`
- Operadores relacionais: `==`, `!=`, `<`, `>`, `<=`, `>=`
- Operadores lógicos: `E` (and) e `OU` (or)
- Estruturas de controle: `SE`, `SENAOSE`, `SENAO`, `ENQUANTO`, `PARA`
- Suporte a `QUEBRE` e `CONTINUE` dentro de laços
- Saída com `ESCREVA` e entrada com `LEIA`
- Comentários de linha com `COMENTE`

### Regras semânticas

- Variáveis não utilizadas geram erro semântico (igual ao Go).
- Redeclaração de variável gera erro semântico.
- Operadores `-`, `*`, `/` com `TEXTO` geram erro semântico (`+` é permitido como concatenação).
- Variáveis declaradas dentro de um escopo não existem fora dele.

## Exemplos de código

### Soma de 1 até N

```
INICIE:
    INTEIRO soma = 0;
    ESCREVA("Digite o número desejado: ");
    INTEIRO n = LEIA(INTEIRO);
    PARA(INTEIRO i = 1; i <= n; i++){
        soma = soma + i;
    }
    ESCREVA("Soma:" + soma);
```

### Positivo, negativo ou zero

```
INICIE:
    INTEIRO num = LEIA(INTEIRO);
    SE(num < 0){
        ESCREVA("Negativo!");
    }
    SENAOSE(num > 0){
        ESCREVA("Positivo!");
    }
    SENAO{
        ESCREVA("Zero!");
    }
```

### Fatorial

```
INICIE:
    INTEIRO numero = LEIA(INTEIRO);
    INTEIRO valor = 1;
    ENQUANTO(numero > 0){
        valor = valor * numero;
        numero--;
    }
    ESCREVA("Fatorial:" + valor);
    COMENTE "Isso é um comentário";
```

### QUEBRE e CONTINUE

```
INICIE:
    PARA(INTEIRO i = 0; i < 10; i++){
        SE(i == 5){
            CONTINUE;
        }
        SE(i == 8){
            QUEBRE;
        }
        ESCREVA(i);
    }
```

## Syntax Highlighting (VS Code)

Para ter destaque de sintaxe (tokens coloridos) no VS Code:

1. Clique com o botão direito no arquivo `abcd-lang-1.0.0.vsix`.
2. Clique em `Install Extension VSIX`.

> Para editar as cores, modifique `abcd/syntaxes/abcd.tmLanguage.json`, entre na pasta `abcd/` e rode `vsce package --allow-missing-repository` para gerar um novo `.vsix`. É necessário ter o `vsce` instalado: `npm install -g @vscode/vsce`.

## Estrutura do projeto

```
Compilador/
├── abcd/          # Extensão de syntax highlighting para VS Code
├── codegen/        # Geração de código Go a partir da AST
├── lexer/          # Análise léxica (tokenização)
├── parser/          # Análise sintática (construção da AST)
├── semantic/        # Análise semântica (escopos, tipos, validações)
├── Main.java        # Ponto de entrada do compilador
└── main.ABCD         # Arquivo de exemplo/entrada
```
