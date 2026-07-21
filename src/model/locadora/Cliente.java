package model.locadora;

import java.io.Serial;
import java.io.Serializable;

/**
 * Representa um cliente cadastrado no sistema de locadora.
 */
public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;

    /**
     * Cria um cliente com nome e CPF.
     *
     * @param nome nome do cliente
     * @param cpf  CPF do cliente.
     */
    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s | CPF: %s", nome, cpf);
    }
}