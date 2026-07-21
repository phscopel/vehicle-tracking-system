package exception;

/**
 * Exceção lançada quando um CPF informado não corresponde a nenhum
 * cliente cadastrado no sistema.
 */
public class ClienteNaoEncontradoException extends Exception {

    /**
     * Cria a exceção.
     *
     * @param message mensagem indicando que o cliente não foi encontrado
     */
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}
