package exception;

/**
 * Exceção lançada quando se tenta registrar a devolução de um automóvel
 * que não possui locação aberta no sistema.
 */
public class LocacaoNaoEncontradaException extends Exception {

    /**
     * Cria a exceção.
     *
     * @param message mensagem indicando a placa sem locação aberta
     */
    public LocacaoNaoEncontradaException(String message) {
        super(message);
    }
}
