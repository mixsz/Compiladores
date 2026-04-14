package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String fonte;
    private int pos;
    private char charAtual;

    public Lexer(String fonte){
        this.fonte = fonte;
        this.pos = 0;
        this.charAtual = fonte.isEmpty() ? '\0' : fonte.charAt(0);
    }

    private void avanca(){
        pos++;
        if(pos < fonte.length())
            charAtual = fonte.charAt(pos);
        else
            charAtual = '\0';
    }

    private void ignoraEspacos(){
        while(charAtual == ' ' || charAtual == '\t' || charAtual == '\n' || charAtual == '\r')
            avanca();
    }

    private Token reservadaOuId(){
        String resultado = "";
        while(Character.isLetterOrDigit(charAtual)) {
            resultado += charAtual;
            avanca();
        }
        // palavras reservadas
        if(resultado.equals("INTEIRO"))  return new Token("tipoInt", resultado);
        if(resultado.equals("DECIMAL"))  return new Token("tipoFloat", resultado);
        if(resultado.equals("TEXTO"))    return new Token("tipoString", resultado);
        if(resultado.equals("SE"))       return new Token("se", resultado);
        if(resultado.equals("SENAO"))    return new Token("senao", resultado);
        if(resultado.equals("SENAOSE"))  return new Token("senaose", resultado);
        if(resultado.equals("E"))        return new Token("e", resultado);
        if(resultado.equals("OU"))       return new Token("ou", resultado);
        if(resultado.equals("ENQUANTO")) return new Token("enquanto", resultado);
        if(resultado.equals("QUEBRE"))   return new Token("quebre", resultado);
        if(resultado.equals("CONTINUE")) return new Token("continue", resultado);
        if(resultado.equals("PARA"))     return new Token("para", resultado);
        if(resultado.equals("LEIA"))     return new Token("leia", resultado);
        if(resultado.equals("ESCREVA"))  return new Token("escreva", resultado);
        if(resultado.equals("INICIE"))   return new Token("inicie", resultado);
        if(resultado.equals("COMENTE"))  return new Token("comente", resultado);
        // identificador: começa com minúscula

        // se o token comecar com numero ou caracter especial ou letra maiuscula (se nao for as reservadas): incorreto!
        if (!(resultado.charAt(0) >= 'a' && resultado.charAt(0) <= 'z')) {
            throw new RuntimeException("ERRO LÉXICO: Caracter inicial inválido --> " + resultado);
        }
            return new Token("id", resultado);
    }

    private Token numero(){
        String resultado = "";

        // inteiro
        while(Character.isDigit(charAtual)){
            resultado += charAtual;
            avanca();
        }

        // parte decimal
        if(charAtual == '.'){
            resultado += charAtual;
            avanca();
            
            if(!Character.isDigit(charAtual)){ 
                throw new RuntimeException("ERRO LÉXICO: Caracter após o ponto inválido --> " + resultado); // 55. n pode
            }

            while(Character.isDigit(charAtual)){
                resultado += charAtual;
                avanca();
            }
        }

        if(Character.isLetter(charAtual)){
            throw new RuntimeException("ERRO LÉXICO: Número seguido de letra inválida --> " + resultado + charAtual); // 2424.52a
        }

        if(resultado.contains(".")){
            return new Token("decimal", resultado);
        }
        return new Token("inteiro", resultado);
    }

}
