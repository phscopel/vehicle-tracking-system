package repository;

import model.locadora.Locadora;

import java.io.*;


/**
 * Interface que define o contrato de persistência da locadora.
 * <p>
 * Permite trocar a implementação de persistência (serialização, banco de dados,
 * JSON...) sem alterar a lógica de negócio ou a interface de usuário.
 */
public interface LocadoraDAO {

    /**
     * Carrega o estado da locadora a partir do mecanismo de persistência.
     * <p>
     * Se não houver dados persistidos, retorna uma Locadora com coleções vazias.
     *
     * @return instância da Locadora com os dados carregados
     */
    public Locadora carregar();

    /**
     * Persiste o estado atual da locadora.
     *
     * @param sistema instância da Locadora a ser salva
     */
    public void salvar(Locadora sistema);

}