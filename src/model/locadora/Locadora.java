package model.locadora;

import exception.*;
import model.automovel.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe central do sistema, responsável por toda a lógica de negócio da locadora.
 * <p>
 * Mantém as coleções de clientes, automóveis, locações abertas e histórico,
 * e expõe operações para cadastro, consulta e registro de locações e devoluções.
 * É serializada como um único objeto para garantir consistência na persistência.
 */
public class Locadora implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private Map<String, Cliente> clientes;
    private Map<String, Automovel> automoveis;
    private Map<String, Locacao> locacoesAbertas;
    private List<Locacao> historicoLocacoes;

    /**
     * Cria uma locadora com todas as coleções vazias.
     */
    public Locadora() {
        this.clientes = new HashMap<>();
        this.automoveis = new HashMap<>();
        this.locacoesAbertas = new HashMap<>();
        this.historicoLocacoes = new ArrayList<>();
    }

    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public Map<String, Automovel> getAutomoveis() {
        return automoveis;
    }

    /**
     * Cadastra um novo cliente no sistema.
     *
     * @param nome nome completo do cliente
     * @param cpf  CPF do cliente, usado como identificador único
     * @throws ClienteDuplicadoException se já existir um cliente com o mesmo CPF
     */
    public void cadastrarCliente(String nome, String cpf) throws ClienteDuplicadoException {

        if(clientes.containsKey(cpf)){
            throw new ClienteDuplicadoException("Ja existe um cliente cadastrado com esse CPF" + cpf);
        }

        Cliente cliente = new Cliente(nome, cpf);
        clientes.put(cpf, cliente);
    }

    /**
     * Cadastra um novo automóvel no sistema de acordo com a categoria informada.
     *
     * @param categoria       categoria do veículo (POPULAR, MEDIO ou GRANDE)
     * @param placa           placa de identificação do veículo
     * @param anoModelo       ano de fabricação do modelo
     * @param valorBase       valor base da diária antes de descontos
     * @throws AutomovelDuplicadoException se já existir um automóvel com a mesma placa
     */
    public void cadastrarAutomovel(Categoria categoria, String placa,  int anoModelo, double valorBase) throws AutomovelDuplicadoException {

        if (automoveis.containsKey(placa)) {
            throw new AutomovelDuplicadoException("Ja existe um automovel cadastrado com a placa " + placa);
        }

        Automovel automovel;
        switch (categoria) {
            case POPULAR:
                automovel = new AutomovelPopular(placa, anoModelo, valorBase);
                break;
            case MEDIO:
                automovel = new AutomovelMedio(placa, anoModelo, valorBase);
                break;
            case GRANDE:
                automovel = new AutomovelGrande(placa, anoModelo, valorBase);
                break;
            default:
                automovel = null;
        }

        automoveis.put(placa, automovel);
    }

    /**
     * Registra uma nova locação para o cliente e automóvel informados.
     * <p>
     * O automóvel é marcado como indisponível ao criar a locação.
     *
     * @param cpf          CPF do cliente que está realizando a locação
     * @param placa        placa do automóvel a ser locado
     * @param dataLocacao  data de início da locação
     * @param dataPrevista data prevista para a devolução
     * @throws ClienteNaoEncontradoException  se o CPF não corresponder a nenhum cliente cadastrado
     * @throws AutomovelIndisponivelException se a placa não existir ou o automóvel já estiver locado
     * @throws DataInvalidaException          se a data prevista não for posterior à data de locação
     */
    public void registrarLocacao(String cpf, String placa, LocalDate dataLocacao, LocalDate dataPrevista) throws AutomovelIndisponivelException, ClienteNaoEncontradoException, DataInvalidaException {
        Cliente cliente = this.clientes.get(cpf);
        Automovel auto = this.automoveis.get(placa);

        if (cliente == null){
            throw new ClienteNaoEncontradoException("Cliente nao encontrado");
        }

        if(auto == null || !auto.isDisponivel()){
            throw new AutomovelIndisponivelException("O automovel selecionado não esta disponivel para locacao.");
        }

        if(dataLocacao.isBefore(LocalDate.now())) {
            throw new DataInvalidaException("A data de locacao não pode ser anterior à data de hoje.");
        }

        if (!dataPrevista.isAfter(dataLocacao)) {
            throw new DataInvalidaException("A data prevista de devolução não pode ser anterior à data de locação.");
        }

        Locacao locacao = new Locacao(cliente, auto, dataLocacao, dataPrevista);

        locacoesAbertas.put(placa, locacao);
    }

    /**
     * Registra a devolução de um automóvel, calcula os valores devidos e
     * move a locação para o histórico.
     * <p>
     * O automóvel é marcado como disponível ao concluir a devolução.
     *
     * @param placa    placa do automóvel sendo devolvido
     * @param dataReal data em que o automóvel foi efetivamente devolvido
     * @return a locação encerrada, com todos os dados para exibição do resumo
     * @throws LocacaoNaoEncontradaException se não houver locação aberta para a placa informada
     * @throws DataInvalidaException         se a data real de devolução não for posterior à data de locação
     */
    public Locacao registrarDevolucao(String placa, LocalDate dataReal) throws LocacaoNaoEncontradaException, DataInvalidaException {
        Locacao locacao = this.locacoesAbertas.get(placa);

        if(locacao == null){
            throw new LocacaoNaoEncontradaException("Locacao nao encontrada para a placa " + placa);
        }

        if(!dataReal.isAfter(locacao.getDataLocacao())){
            throw new DataInvalidaException("A data de devolução não pode ser anterior à data de locação.");
        }

        locacao.registrarDevolucao(dataReal);

        locacoesAbertas.remove(placa);
        historicoLocacoes.add(locacao);

        return locacao;
    }

    /**
     * Retorna a lista de automóveis disponíveis para locação.
     *
     * @return lista de automóveis com disponivel = true
     */
    public List<Automovel> listarAutomoveisDisponiveis() {
        List<Automovel> disponiveis = new ArrayList<>();

        for (Automovel automovel : automoveis.values()) {
            if (automovel.isDisponivel()) {
                disponiveis.add(automovel);
            }
        }

        return disponiveis;
    }
}