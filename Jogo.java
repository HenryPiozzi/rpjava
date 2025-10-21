public class Jogo{
    private Personagem jogador;
    private Scanner scanner;
    private boolean jogoAtivo;
    private int capituloAtual;
    private int inimigosDerrotados;
    private Random dado;

    public Jogo() {
        this.scanner = new Scanner(System.in);
        this.jogoAtivo = true;
        this.capituloAtual = 1;
        this.inimigosDerrotados = 0;
        this.dado = new Random();
    }

    public void iniciarJogo() {
        System.out.println("Bem-vindo ao RPJAVA!");
        escolherClasse();
        loopPrincipal();
    }

    private void escolherClasse() {
        int opcao = scanner.nextInt()
        while(opcao < 1 || opcao > 3) {
            System.out.println("Escolha sua classe:\n1 - Guerreiro\n2 - Mago\n3 - Arqueiro");
            scanner.nextLine();
            switch (opcao) {
                case 1 -> jogador = new Guerreiro("Jogador");
                case 2 -> jogador = new Mago("Jogador");
                case 3 -> jogador = new Arqueiro("Jogador");
                default -> {
                    System.out.println("Opção inválida, tente novamente!");
                }
            }
        }
    }

    private void loopPrincipal() {
        while (jogoAtivo) {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Explorar");
            System.out.println("2 - Inventário");
            System.out.println("3 - Status");
            System.out.println("4 - Sair");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> explorar();
                case 2 -> jogador.getInventario().listarItens();
                case 3 -> exibirStatus();
                case 4 -> encerrarJogo();
                default -> System.out.println("Escolha inválida!");
            }
        }
    }

    private void explorar() {
        // Exemplo 
        Inimigo inimigo = new Inimigo("Goblin", 30, 5, 2, 1);
        //dar um jeito de gerar um inimigo aleatorio baseado no capituloAtual
        System.out.println("Você encontrou um " + inimigo.getNome() + "!");
        jogador.batalhar(inimigo, dado);
        
        if (jogador.getPontosVida() <= 0) {
            System.out.println("Você foi derrotado...");
            jogoAtivo = false;
        }
        //exemplo de finalização de combate, terminar depois
        if(inimigo.getPontosVida() <= 0){
            inimigosDerrotados++;
            System.out.println("Inimigos derrotados: " + inimigosDerrotados);
            if(inimigosDerrotados % 3 == 0){
                capituloAtual++;
                System.out.println("Parabéns! Você avançou para o capítulo " + capituloAtual);
            }
        }
    }

    private void exibirStatus() {
        System.out.println(jogador);
        System.out.println(this.capituloAtual);
        System.out.println(this.inimigosDerrotados);
    }

    private void encerrarJogo() {
        System.out.println("Saindo do jogo...");
        jogoAtivo = false;
    }
}