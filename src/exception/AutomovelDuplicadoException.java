package exception;

/**
 * Exceção lançada quando se tenta cadastrar um automóvel com uma placa
 * que já existe no sistema.
 */
public class AutomovelDuplicadoException extends Exception {

    /**
     * Cria a exceção.
     *
     * @param message mensagem indicando a placa duplicada
     */
    public AutomovelDuplicadoException(String message) {
        super(message);
    }
}
