package br.com.tavernadovale.tavernadovale.exceptions;

public class TipoInvalidoException extends RuntimeException {

    // Construtor padrão com mensagem fixa
    public TipoInvalidoException() {
        super("O tipo do produto precisa ser: 'Destilada', 'Fermentada', 'Refrigerante', 'Comidas'");
    }

    // Construtor com mensagem personalizada
    public TipoInvalidoException(String message) {
        super(message);
    }
}