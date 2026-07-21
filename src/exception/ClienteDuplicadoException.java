package exception;

/**
 * Exceção lançada quando se tenta cadastrar um cliente com um CPF
 * que já existe no sistema.
 */
public class ClienteDuplicadoException extends Exception {

    /**
     * Cria a exceção.
     *
     * @param message mensagem indicando o CPF duplicado
     */
    public ClienteDuplicadoException(String message) {
        super(message);
    }
}

