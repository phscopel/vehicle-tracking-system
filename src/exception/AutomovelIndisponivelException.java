package exception;

/**
 * Exceção lançada quando se tenta registrar uma locação para um automóvel
 * que já está alugado ou não existe no sistema.
 */
public class AutomovelIndisponivelException extends Exception {

    /**
     * Cria a exceção.
     *
     * @param message mensagem indicando o motivo da indisponibilidade
     */
    public AutomovelIndisponivelException(String message) {
        super(message);
    }
}
