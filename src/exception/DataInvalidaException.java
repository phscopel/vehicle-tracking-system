package exception;

/**
 * Exceção lançada quando uma data informada viola as regras de negócio,
 * como data de devolução anterior à data de locação, ou a data de locacao menor que a data atual.
 */
public class DataInvalidaException extends Exception {

    /**
     * Cria a exceção.
     *
     * @param message mensagem indicando qual regra de data foi violada
     */
    public DataInvalidaException(String message) {
        super(message);
    }
}
