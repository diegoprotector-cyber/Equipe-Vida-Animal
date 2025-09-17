import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;

// ==================== MODEL (Camada de Dados) ====================

class Pessoa {
    private String nome;
    private String cpf;
    private String email;

    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Nome: " + nome + " | CPF: " + cpf + " | Email: " + email;
    }
}

class Usuario extends Pessoa {
    private String cargo;
    private String login;
    private String senha;
    private String perfil;

    public Usuario(String nome, String cpf, String email, String cargo, String login, String senha, String perfil) {
        super(nome, cpf, email);
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    @Override
    public String toString() {
        return super.toString() + " | Cargo: " + cargo + " | Perfil: " + perfil;
    }
}

class Projeto {
    private String nomeProjeto;
    private String descricao;
    private String status;
    private Usuario gerente;
    private Equipe equipe;

    public Projeto(String nomeProjeto, String descricao, String status, Usuario gerente) {
        this.nomeProjeto = nomeProjeto;
        this.descricao = descricao;
        this.status = status;
        this.gerente = gerente;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public String getNomeProjeto() { return nomeProjeto; }
    public String getDescricao() { return descricao; }
    public String getStatus() { return status; }
    public Usuario getGerente() { return gerente; }

    @Override
    public String toString() {
        String info = "Projeto: " + nomeProjeto + " | Status: " + status + " | Gerente: " + gerente.getNome();
        if (equipe != null) {
            info += " | Equipe: " + equipe.getNomeEquipe();
        }
        return info;
    }
}

class Equipe {
    private String nomeEquipe;
    private String descricao;
    private ArrayList<Usuario> membros;

    public Equipe(String nomeEquipe, String descricao) {
        this.nomeEquipe = nomeEquipe;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public String getDescricao() {
        return descricao;
    }

    public ArrayList<Usuario> getMembros() {
        return membros;
    }

    public void adicionarMembro(Usuario usuario) {
        if (!membros.contains(usuario)) {
            membros.add(usuario);
            System.out.println("Membro " + usuario.getNome() + " adicionado à equipe.");
        } else {
            System.out.println("Usuário já é membro desta equipe.");
        }
    }

    public void removerMembro(Usuario usuario) {
        if (membros.contains(usuario)) {
            membros.remove(usuario);
            System.out.println("Membro " + usuario.getNome() + " removido da equipe.");
        } else {
            System.out.println("Usuário não é membro desta equipe.");
        }
    }

    public void listarMembros() {
        System.out.println("Membros da equipe " + nomeEquipe + ":");
        for (int i = 0; i < membros.size(); i++) {
            System.out.println(i + " - " + membros.get(i).getNome() + " (" + membros.get(i).getCargo() + ")");
        }
    }

    @Override
    public String toString() {
        return "Equipe: " + nomeEquipe + " | Descrição: " + descricao + " | Total Membros: " + membros.size();
    }
}

// ==================== VIEW (Camada de Apresentação) ====================

class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirErro(String erro) {
        System.err.println("Erro: " + erro);
    }

    public int exibirMenuPrincipal() {
        System.out.println("\n===== ONG Equipe Vida Animal =====");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("2 - Listar Usuários");
        System.out.println("3 - Cadastrar Projeto");
        System.out.println("4 - Listar Projetos");
        System.out.println("5 - Cadastrar Equipe");
        System.out.println("6 - Listar Equipes");
        System.out.println("7 - Gerenciar Equipes");
        System.out.println("8 - Associar Equipe a Projeto");
        System.out.println("9 - Sair");
        System.out.print("Escolha: ");

        return lerInteiro();
    }

    public int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public String[] lerDadosUsuario() {
        String[] dados = new String[7];
        dados[0] = lerString("Nome: ");
        dados[1] = lerString("CPF: ");
        dados[2] = lerString("Email: ");
        dados[3] = lerString("Cargo: ");
        dados[4] = lerString("Login: ");
        dados[5] = lerString("Senha: ");
        dados[6] = lerString("Perfil (admin/gerente/colaborador): ");
        return dados;
    }

    public String[] lerDadosProjeto() {
        String[] dados = new String[3];
        dados[0] = lerString("Nome do projeto: ");
        dados[1] = lerString("Descrição: ");
        dados[2] = lerString("Status (planejado/em andamento/concluído/cancelado): ");
        return dados;
    }

    public String[] lerDadosEquipe() {
        String[] dados = new String[2];
        dados[0] = lerString("Nome da equipe: ");
        dados[1] = lerString("Descrição: ");
        return dados;
    }

    public void exibirUsuarios(List<Usuario> usuarios) {
        System.out.println("=== Usuários Cadastrados ===");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (int i = 0; i < usuarios.size(); i++) {
                System.out.println(i + " - " + usuarios.get(i));
            }
        }
    }

    public void exibirProjetos(List<Projeto> projetos) {
        System.out.println("=== Projetos ===");
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto cadastrado.");
        } else {
            for (int i = 0; i < projetos.size(); i++) {
                System.out.println(i + " - " + projetos.get(i));
            }
        }
    }

    public void exibirEquipes(List<Equipe> equipes) {
        System.out.println("=== Equipes ===");
        if (equipes.isEmpty()) {
            System.out.println("Nenhuma equipe cadastrada.");
        } else {
            for (int i = 0; i < equipes.size(); i++) {
                System.out.println(i + " - " + equipes.get(i));
            }
        }
    }

    public int selecionarIndice(String mensagem, int tamanhoMaximo) {
        System.out.println(mensagem);
        int indice = lerInteiro();
        if (indice < 0 || indice >= tamanhoMaximo) {
            return -1;
        }
        return indice;
    }

    public String[] lerIndicesMembros() {
        System.out.println("Adicione membros (digite índices separados por vírgula, ou -1 para pular):");
        return scanner.nextLine().split(",");
    }

    public void fecharScanner() {
        scanner.close();
    }
}

// ==================== CONTROLLER (Camada de Lógica) ====================

class UsuarioController {
    private ArrayList<Usuario> usuarios;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
    }

    public boolean cadastrarUsuario(String nome, String cpf, String email,
                                    String cargo, String login, String senha, String perfil) {
        if (cpfExists(cpf)) {
            return false;
        }

        if (emailExists(email)) {
            return false;
        }

        usuarios.add(new Usuario(nome, cpf, email, cargo, login, senha, perfil));
        return true;
    }

    public ArrayList<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioPorIndice(int indice) {
        if (indice >= 0 && indice < usuarios.size()) {
            return usuarios.get(indice);
        }
        return null;
    }

    private boolean cpfExists(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    private boolean emailExists(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeUsuarios() {
        return !usuarios.isEmpty();
    }
}

class ProjetoController {
    private ArrayList<Projeto> projetos;

    public ProjetoController() {
        this.projetos = new ArrayList<>();
    }

    public boolean cadastrarProjeto(String nomeProjeto, String descricao,
                                    String status, Usuario gerente) {
        projetos.add(new Projeto(nomeProjeto, descricao, status, gerente));
        return true;
    }

    public ArrayList<Projeto> listarProjetos() {
        return projetos;
    }

    public boolean associarEquipeAProjeto(int indiceProjeto, Equipe equipe) {
        if (indiceProjeto >= 0 && indiceProjeto < projetos.size()) {
            projetos.get(indiceProjeto).setEquipe(equipe);
            return true;
        }
        return false;
    }

    public Projeto getProjetoPorIndice(int indice) {
        if (indice >= 0 && indice < projetos.size()) {
            return projetos.get(indice);
        }
        return null;
    }

    public boolean existeProjetos() {
        return !projetos.isEmpty();
    }
}

class EquipeController {
    private ArrayList<Equipe> equipes;

    public EquipeController() {
        this.equipes = new ArrayList<>();
    }

    public boolean cadastrarEquipe(String nomeEquipe, String descricao) {
        equipes.add(new Equipe(nomeEquipe, descricao));
        return true;
    }

    public ArrayList<Equipe> listarEquipes() {
        return equipes;
    }

    public boolean adicionarMembro(int indiceEquipe, Usuario usuario) {
        if (indiceEquipe >= 0 && indiceEquipe < equipes.size()) {
            equipes.get(indiceEquipe).adicionarMembro(usuario);
            return true;
        }
        return false;
    }

    public boolean removerMembro(int indiceEquipe, int indiceMembro) {
        if (indiceEquipe >= 0 && indiceEquipe < equipes.size()) {
            Equipe equipe = equipes.get(indiceEquipe);
            if (indiceMembro >= 0 && indiceMembro < equipe.getMembros().size()) {
                Usuario membro = equipe.getMembros().get(indiceMembro);
                equipe.removerMembro(membro);
                return true;
            }
        }
        return false;
    }

    public void listarMembrosEquipe(int indiceEquipe) {
        if (indiceEquipe >= 0 && indiceEquipe < equipes.size()) {
            equipes.get(indiceEquipe).listarMembros();
        }
    }

    public Equipe getEquipePorIndice(int indice) {
        if (indice >= 0 && indice < equipes.size()) {
            return equipes.get(indice);
        }
        return null;
    }

    public boolean existeEquipes() {
        return !equipes.isEmpty();
    }
}

// ==================== MAIN (Aplicação) ====================

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        UsuarioController usuarioController = new UsuarioController();
        ProjetoController projetoController = new ProjetoController();
        EquipeController equipeController = new EquipeController();

        int opcao;
        do {
            opcao = view.exibirMenuPrincipal();

            switch (opcao) {
                case 1 -> cadastrarUsuario(view, usuarioController);
                case 2 -> view.exibirUsuarios(usuarioController.listarUsuarios());
                case 3 -> cadastrarProjeto(view, usuarioController, projetoController);
                case 4 -> view.exibirProjetos(projetoController.listarProjetos());
                case 5 -> cadastrarEquipe(view, equipeController, usuarioController);
                case 6 -> view.exibirEquipes(equipeController.listarEquipes());
                case 7 -> gerenciarEquipes(view, equipeController, usuarioController);
                case 8 -> associarEquipeProjeto(view, projetoController, equipeController);
                case 9 -> view.exibirMensagem("Encerrando...");
                default -> view.exibirErro("Opção inválida.");
            }

        } while (opcao != 9);

        view.fecharScanner();
    }

    private static void cadastrarUsuario(ConsoleView view, UsuarioController controller) {
        String[] dados = view.lerDadosUsuario();
        boolean sucesso = controller.cadastrarUsuario(dados[0], dados[1], dados[2],
                dados[3], dados[4], dados[5], dados[6]);
        if (sucesso) {
            view.exibirMensagem("Usuário cadastrado com sucesso!");
        } else {
            view.exibirErro("CPF ou email já cadastrado.");
        }
    }

    private static void cadastrarProjeto(ConsoleView view, UsuarioController usuarioController,
                                         ProjetoController projetoController) {
        if (!usuarioController.existeUsuarios()) {
            view.exibirErro("Cadastre pelo menos um usuário antes de criar projetos!");
            return;
        }

        String[] dados = view.lerDadosProjeto();
        view.exibirUsuarios(usuarioController.listarUsuarios());

        int idx = view.selecionarIndice("Escolha o gerente pelo índice:",
                usuarioController.listarUsuarios().size());
        if (idx == -1) {
            view.exibirErro("Índice inválido.");
            return;
        }

        Usuario gerente = usuarioController.getUsuarioPorIndice(idx);
        projetoController.cadastrarProjeto(dados[0], dados[1], dados[2], gerente);
        view.exibirMensagem("Projeto cadastrado!");
    }

    private static void cadastrarEquipe(ConsoleView view, EquipeController equipeController,
                                        UsuarioController usuarioController) {
        String[] dados = view.lerDadosEquipe();
        equipeController.cadastrarEquipe(dados[0], dados[1]);

        if (usuarioController.existeUsuarios()) {
            view.exibirUsuarios(usuarioController.listarUsuarios());
            String[] indices = view.lerIndicesMembros();

            for (String idxStr : indices) {
                try {
                    int idx = Integer.parseInt(idxStr.trim());
                    if (idx == -1) break;
                    Usuario usuario = usuarioController.getUsuarioPorIndice(idx);
                    if (usuario != null) {
                        equipeController.adicionarMembro(equipeController.listarEquipes().size() - 1, usuario);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        view.exibirMensagem("Equipe cadastrada!");
    }

    private static void gerenciarEquipes(ConsoleView view, EquipeController equipeController,
                                         UsuarioController usuarioController) {
        if (!equipeController.existeEquipes()) {
            view.exibirErro("Nenhuma equipe cadastrada.");
            return;
        }

        view.exibirEquipes(equipeController.listarEquipes());
        int idxEquipe = view.selecionarIndice("Escolha a equipe pelo índice:",
                equipeController.listarEquipes().size());
        if (idxEquipe == -1) {
            view.exibirErro("Índice inválido.");
            return;
        }

        view.exibirMensagem("1 - Adicionar membro");
        view.exibirMensagem("2 - Remover membro");
        view.exibirMensagem("3 - Listar membros");
        int op = view.lerInteiro();

        switch (op) {
            case 1 -> {
                view.exibirUsuarios(usuarioController.listarUsuarios());
                int idxUsuario = view.selecionarIndice("Escolha o usuário pelo índice:",
                        usuarioController.listarUsuarios().size());
                if (idxUsuario != -1) {
                    equipeController.adicionarMembro(idxEquipe, usuarioController.getUsuarioPorIndice(idxUsuario));
                } else {
                    view.exibirErro("Índice inválido.");
                }
            }
            case 2 -> {
                equipeController.listarMembrosEquipe(idxEquipe);
                int idxMembro = view.selecionarIndice("Escolha o membro pelo índice para remover:",
                        equipeController.getEquipePorIndice(idxEquipe).getMembros().size());
                if (idxMembro != -1) {
                    equipeController.removerMembro(idxEquipe, idxMembro);
                } else {
                    view.exibirErro("Índice inválido.");
                }
            }
            case 3 -> equipeController.listarMembrosEquipe(idxEquipe);
            default -> view.exibirErro("Opção inválida.");
        }
    }

    private static void associarEquipeProjeto(ConsoleView view, ProjetoController projetoController,
                                              EquipeController equipeController) {
        if (!projetoController.existeProjetos() || !equipeController.existeEquipes()) {
            view.exibirErro("É necessário ter pelo menos um projeto e uma equipe cadastrados.");
            return;
        }

        view.exibirProjetos(projetoController.listarProjetos());
        int idxProjeto = view.selecionarIndice("Escolha o projeto pelo índice:",
                projetoController.listarProjetos().size());
        if (idxProjeto == -1) {
            view.exibirErro("Índice inválido.");
            return;
        }

        view.exibirEquipes(equipeController.listarEquipes());
        int idxEquipe = view.selecionarIndice("Escolha a equipe pelo índice:",
                equipeController.listarEquipes().size());
        if (idxEquipe == -1) {
            view.exibirErro("Índice inválido.");
            return;
        }

        Equipe equipe = equipeController.getEquipePorIndice(idxEquipe);
        if (projetoController.associarEquipeAProjeto(idxProjeto, equipe)) {
            view.exibirMensagem("Equipe associada ao projeto com sucesso!");
        } else {
            view.exibirErro("Falha ao associar equipe ao projeto.");
        }
    }
}