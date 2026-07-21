package repository;

import model.locadora.Locadora;

import java.io.*;

/**
 * Implementação de LocadoraDAO que persiste os dados via serialização Java.
 * <p>
 * Serializa o objeto Locadora inteiro em um único arquivo dados.dat,
 * garantindo que,  ou todos os dados são salvos juntos, ou nenhum é.
 * Utiliza try-with-resources para garantir o fechamento correto dos fluxos de I/O.
 */
public class LocadoraSerializableDAO implements LocadoraDAO {

    private static final String ARQUIVO = "dados.dat";

    /**
     * Carrega a locadora a partir do arquivo dados.dat.
     * <p>
     * Se o arquivo não existir ou ocorrer erro de leitura, retorna uma
     * Locadora com coleções vazias.
     *
     * @return instância de Locadora com os dados carregados
     */
    @Override
    public Locadora carregar() {

        File file = new File(ARQUIVO);

        if (!file.exists()) {
            return new Locadora();
        }

        try (ObjectInputStream entrada =
                     new ObjectInputStream(new FileInputStream(file))) {

            return (Locadora) entrada.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
            return new Locadora();
        }
    }

    /**
     * Salva a locadora no arquivo dados.dat.
     * <p>
     * Em caso de erro de escrita, exibe a mensagem no console de erro
     * sem interromper a execução.
     *
     * @param sistema instância de Locadora a ser salva
     */
    @Override
    public void salvar(Locadora sistema) {

        try (ObjectOutputStream saida =
                     new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {

            saida.writeObject(sistema);

        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}