import java.util.Scanner;
import java.util.Random;

public class Jogo {
    private Personagem jogador;
    private Scanner scanner;
    private boolean jogoAtivo;
    private byte capituloAtual;
    private byte inimigosDerrotados;
    private Random dado;
    private boolean[] cristaisPurificados;
    private boolean introducaoMostrada;
    private Personagem savePoint;

    // Construtor
    public Jogo() {
        this.scanner = new Scanner(System.in);
        this.jogoAtivo = true;
        this.capituloAtual = 1;
        this.inimigosDerrotados = 0;
        this.dado = new Random();
        this.cristaisPurificados = new boolean[3];
        this.introducaoMostrada = false;
    }

    // Método para iniciar o jogo, chamando os outros necessarios
    public void iniciarJogo() {
        mostrarIntroducao();
        escolherClasse();
        darItensIniciais();
        loopPrincipal();
    }

    //metodo para iniciar a historia do jogo
    private void mostrarIntroducao() {
        limparTela();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          A LENDA DOS CRISTAIS ANCESTRAIS                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("O reino de Cristalios sempre viveu em paz, protegido por três");
        System.out.println("cristais ancestrais que mantinham o equilíbrio do mundo.");
        System.out.println();
        System.out.println("Mas tudo mudou quando o Lorde das Sombras despertou...");
        System.out.println();
        System.out.println("Ele corrompeu os cristais, espalhando escuridão por toda terra.");
        System.out.println("Monstros surgiram, aldeias foram devastadas, e o caos reina.");
        System.out.println();
        System.out.println("Você foi escolhido(a) pelo Conselho de Sábios para uma missão:");
        System.out.println("Recuperar e purificar os três cristais antes que seja tarde demais!");
        System.out.println();
        System.out.println("Os cristais estão escondidos em:");
        System.out.println("  Floresta Sombria - Cristal da Natureza");
        System.out.println("  Cavernas de Gelo - Cristal da Água");
        System.out.println("  Torre Carmesim - Cristal do Fogo");
        System.out.println();
        System.out.print("Pressione ENTER para começar sua jornada...");
        scanner.nextLine();
    }

    // Método para escolher a classe do personagem e inserir o nome
    private void escolherClasse() {
        limparTela();
        
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   BEM-VINDO, AVENTUREIRO!                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.print("Digite o nome do seu personagem: ");
        String nomePersonagem = scanner.nextLine().trim();
        
        while (nomePersonagem.isEmpty()) {
            System.out.print("Nome inválido! Digite um nome válido: ");
            nomePersonagem = scanner.nextLine().trim();
        }
        
        limparTela();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ESCOLHA SUA CLASSE                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Olá, " + nomePersonagem + "! Escolha sua classe:");
        System.out.println();
        System.out.println("1 - GUERREIRO");
        System.out.println("    Forte e resistente, especialista em combate corpo a corpo.");
        System.out.println("    + Alta defesa e vida (25 HP, 3 ATK, 5 DEF)");
        System.out.println();
        System.out.println("2 - MAGO");
        System.out.println("    Mestre das artes arcanas, causa grande dano mágico.");
        System.out.println("    + Alto ataque mágico (18 HP, 7 ATK, 3 DEF)");
        System.out.println();
        System.out.println("3 - ARQUEIRO");
        System.out.println("    Ágil e preciso, ataca à distância com maestria.");
        System.out.println("    + Balanceado e versátil (20 HP, 5 ATK, 4 DEF)");
        System.out.println();
        System.out.print("Escolha sua classe (1-3): ");
        
        int opcao = -1;
        while (opcao < 1 || opcao > 3) {
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 1 -> {
                        jogador = new Guerreiro(nomePersonagem);
                        System.out.println("\n" + nomePersonagem + ", você escolheu o caminho do Guerreiro!");
                    }
                    case 2 -> {
                        jogador = new Mago(nomePersonagem); 
                        System.out.println("\n" + nomePersonagem + ", você escolheu o caminho do Mago!");
                    }
                    case 3 -> {
                        jogador = new Arqueiro(nomePersonagem); 
                        System.out.println("\n" + nomePersonagem + ", você escolheu o caminho do Arqueiro!");
                    }
                    default -> {
                        System.out.print("Opção inválida! Escolha 1, 2 ou 3: ");
                        opcao = -1;
                    }
                }
            } catch (Exception e) {
                System.out.print("Entrada inválida! Digite um número (1-3): ");
                scanner.nextLine();
                opcao = -1;
            }
        }
        pausar();
    }

    //metodo para dar poções ao jogador no inicio do jogo
    private void darItensIniciais() {
        Item pocaoVida = new Item("Pocao de Vida pequena", "Recupera 30% de HP", "Cura", "30", (byte)3);
        jogador.getInventario().adicionarItem(pocaoVida);
        System.out.println("\nVocê recebeu 3x Poções de Vida pequena!");
        pausar();
    }

    //loop principal do jogo, onde o jogador escolhe 
    //explorar, podendo encontrar um combate ou armadilha
    //ver o inventario, onde pode saber os itens que possui
    //ver o status completo do personagem e hisotira
    //criar um save point que pode ser retornado futuramente ao escolher carregar save point
    private void loopPrincipal() {
        while (jogoAtivo) {
            limparTela();
            mostrarStatusRapido();
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                         MENU PRINCIPAL                        ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("1 - Explorar");
            System.out.println("2 - Inventário");
            System.out.println("3 - Status Completo");
            System.out.println("4 - Criar Save Point");
            System.out.println("5 - Carregar Save Point");
            System.out.println("6 - Sair do Jogo");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int escolha = -1;
            try {
                escolha = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                escolha = -1;
            }

            switch (escolha) {
                case 1 -> explorar();
                case 2 -> {
                    limparTela();
                    jogador.getInventario().listarItens();
                    pausar();
                }
                case 3 -> {
                    limparTela();
                    exibirStatus();
                    pausar();
                }
                case 4 -> criarSavePoint();
                case 5 -> carregarSavePoint();
                case 6 -> encerrarJogo();
                default -> {
                    System.out.println("\nEscolha inválida!");
                    pausar();
                }
            }
        }
    }

    //metodo que direciona o jogador à area que ele escolher
    private void explorar() {
        limparTela();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        EXPLORAÇÃO                             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Você está na encruzilhada do destino...");
        System.out.println();
        System.out.println("Para onde deseja ir?");
        System.out.println();
        
        String statusFloresta = cristaisPurificados[0] ? "PURIFICADO" : "CORROMPIDO";
        String statusCavernas = cristaisPurificados[1] ? "PURIFICADO" : "CORROMPIDO";
        String statusTorre = cristaisPurificados[2] ? "PURIFICADO" : "CORROMPIDO";
        
        System.out.println("1 - Floresta Sombria [" + statusFloresta + "]");
        System.out.println("2 - Cavernas de Gelo [" + statusCavernas + "]");
        System.out.println("3 - Torre Carmesim [" + statusTorre + "]");
        System.out.println("4 - Voltar à Vila (descansar)");
        System.out.println();
        System.out.print("Escolha: ");

        int escolha = -1;
        try {
            escolha = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
        }

        switch (escolha) {
            case 1 -> explorarFlorestaSombria();
            case 2 -> explorarCavernasDeGelo();
            case 3 -> explorarTorreCarmesim();
            case 4 -> descansarNaVila();
            default -> System.out.println("\nOpção inválida!");
        }
    }

    //todas os metodos de explorar criam um inimigo aleatorio e chamam a função de batalhar do personagem
    //verificando se houve uma vitoria ou derrota ao termino dela
    //e as funções que verificam o progresso de purificação dos cristais
    //concedem bonus ao jogador dependendo da dificuldade do local

    //metodo para explorar a floresta sombria, 
    // tendo 70% de chance de encontrar um inimigo e 30% de encontrar um tesouro aleatorio
    private void explorarFlorestaSombria() {
        limparTela();
        System.out.println("FLORESTA SOMBRIA");
        System.out.println("═══════════════════════════════════════");
        System.out.println();
        
        if (cristaisPurificados[0]) {
            System.out.println("A floresta está pacífica agora, com o cristal purificado.");
            System.out.println("Você pode ouvir os pássaros cantando novamente...");
            pausar();
            return;
        }
        
        System.out.println("Você adentra a floresta densa e escura.");
        System.out.println("Galhos secos estalam sob seus pés...");
        System.out.println("De repente, você ouve um rosnado!");
        System.out.println();
        pausar();

        int evento = dado.nextInt(100);
        
        if (evento < 70) { // 70% chance de combate
            Inimigo inimigo = Inimigo.gerarInimigo(inimigosDerrotados);
            System.out.println("Um " + inimigo.getNome() + " aparece do nada!");
            pausar();
            try {
                jogador.batalhar(inimigo);
                
                if (jogador.getPontosVida() > 0 && inimigo.getPontosVida() <= 0) {
                    inimigosDerrotados++;
                    verificarProgressoFloresta();
                } else if (jogador.getPontosVida() <= 0) {
                    gameOver();
                }
            } catch (Exception e) {
                System.out.println("Erro durante a batalha!");
            }
        } else { // 30% chance de encontrar tesouro
            System.out.println("Você encontra um baú escondido entre as raízes!");
            Item tesouro = Item.itemAleatorio(dado.nextInt(3));
            jogador.getInventario().adicionarItem(tesouro);
            System.out.println("Você ganhou: " + tesouro.getNome());
        }
        pausar();
    }

    //metodo que verifica se a floresta já foi purificada
    private void verificarProgressoFloresta() {
        if (inimigosDerrotados >= 3 && !cristaisPurificados[0]) {
            System.out.println("\nVOCÊ ENCONTROU O CRISTAL DA NATUREZA!");
            System.out.println();
            System.out.println("O cristal pulsa com energia corrompida...");
            System.out.println("Você sente a escuridão tentando te consumir!");
            System.out.println();
            System.out.print("Pressione ENTER para purificar o cristal...");
            scanner.nextLine();
            
            System.out.println("\nVocê concentra sua energia...");
            System.out.println("Uma luz brilhante envolve o cristal!");
            System.out.println("A escuridão é expelida!");
            System.out.println();
            System.out.println("CRISTAL DA NATUREZA PURIFICADO!");
            
            cristaisPurificados[0] = true;
            capituloAtual++;
            
            // Recompensa
            try {
                jogador.setPontosVidaMaximo((short)(jogador.getPontosVidaMaximo() + 10));
                jogador.setPontosVida(jogador.getPontosVidaMaximo());
                System.out.println("\nSua vida máxima aumentou em 10!");
                System.out.println("Sua vida foi restaurada!");
            } catch (Exception e) {}
            
            verificarProgressoGeral();
            pausar();
        }
    }

    //metodo para explorar as cavernas de gelo,
    // tendo 65% de chance de encontrar um inimigo, 20% de encontrar um tesouro aleatorio e 15% de cair em uma armadilha
    private void explorarCavernasDeGelo() {
        limparTela();
        System.out.println("CAVERNAS DE GELO");
        System.out.println("═══════════════════════════════════════");
        System.out.println();
        
        if (cristaisPurificados[1]) {
            System.out.println("As cavernas estão calmas, com o gelo brilhando pacificamente.");
            pausar();
            return;
        }
        
        System.out.println("Você entra nas cavernas congelantes...");
        System.out.println("O frio penetra até os ossos.");
        System.out.println("Você ouve ecos distantes...");
        System.out.println();
        pausar();

        int evento = dado.nextInt(100);
        
        if (evento < 65) { // 65% chance de combate
            Inimigo inimigo = Inimigo.gerarInimigo(inimigosDerrotados);
            System.out.println("Um " + inimigo.getNome() + " congelado ataca!");
            pausar();
            
            try {
                jogador.batalhar(inimigo);
                
                if (jogador.getPontosVida() > 0 && inimigo.getPontosVida() <= 0) {
                    inimigosDerrotados++;
                    verificarProgressoCavernas();
                } else if (jogador.getPontosVida() <= 0) {
                    gameOver();
                }
            } catch (Exception e) {
                System.out.println("Erro durante a batalha!");
            }
        } else if (evento < 85) { // 20% chance de tesouro
            System.out.println("Você encontra um cristal de gelo valioso!");
            Item tesouro = Item.itemAleatorio(dado.nextInt(4));
            jogador.getInventario().adicionarItem(tesouro);
            System.out.println("Você ganhou: " + tesouro.getNome());
        } else { // 15% chance de armadilha
            System.out.println("ARMADILHA! O chão desmorona!");
            try {
                short dano = (short)(jogador.getPontosVidaMaximo() * 0.15);
                jogador.setPontosVida((short)(jogador.getPontosVida() - dano));
                System.out.println("Você sofreu " + dano + " de dano!");
                
                if (jogador.getPontosVida() <= 0) {
                    gameOver();
                }
            } catch (Exception e) {}
        }
        pausar();
    }

    //metodo que verifica se as cavernas já foram purificadas
    private void verificarProgressoCavernas() {
        if (inimigosDerrotados >= 6 && !cristaisPurificados[1]) {
            System.out.println("\nVOCÊ ENCONTROU O CRISTAL DA ÁGUA!");
            System.out.println();
            System.out.println("O cristal está envolto em gelo negro...");
            System.out.print("Pressione ENTER para purificar o cristal...");
            scanner.nextLine();
            
            System.out.println("\nVocê derrete o gelo com sua determinação!");
            System.out.println("O cristal brilha em azul puro!");
            System.out.println();
            System.out.println("CRISTAL DA ÁGUA PURIFICADO!");
            
            cristaisPurificados[1] = true;
            capituloAtual++;
            
            // Recompensa
            try {
                jogador.setAtaque((short)(jogador.getAtaque() + 3));
                System.out.println("\nSeu ataque aumentou em 3!");
            } catch (Exception e) {}
            
            verificarProgressoGeral();
            pausar();
        }
    }

    //metodo para explorar a torre carmesim
    //tendo 75% de chance de encontrar um inimigo mais forte e 
    //25% de chance de achar um tesouro epico ou lendario
    private void explorarTorreCarmesim() {
        limparTela();
        System.out.println("TORRE CARMESIM");
        System.out.println("═══════════════════════════════════════");
        System.out.println();
        
        if (cristaisPurificados[2]) {
            System.out.println("A torre está em paz, as chamas purificadas.");
            pausar();
            return;
        }
        
        System.out.println("Você sobe os degraus da torre em chamas...");
        System.out.println("O calor é intenso, quase insuportável.");
        System.out.println("Você sente uma presença maligna...");
        System.out.println();
        pausar();

        int evento = dado.nextInt(100);
        
        if (evento < 75) { // 75% chance de combate difícil
            Inimigo inimigo = Inimigo.gerarInimigo(inimigosDerrotados + 1); // Inimigos mais fortes
            System.out.println("Um " + inimigo.getNome() + " flamejante surge!");
            pausar();
            
            try {
                jogador.batalhar(inimigo);
                
                if (jogador.getPontosVida() > 0 && inimigo.getPontosVida() <= 0) {
                    inimigosDerrotados++;
                    verificarProgressoTorre();
                } else if (jogador.getPontosVida() <= 0) {
                    gameOver();
                }
            } catch (Exception e) {
                System.out.println("Erro durante a batalha!");
            }
        } else { // 25% chance de item raro
            System.out.println("Você encontra uma relíquia antiga!");
            Item tesouro = Item.itemAleatorio(2 + dado.nextInt(2)); // Épico ou Lendário
            jogador.getInventario().adicionarItem(tesouro);
            System.out.println("Você ganhou: " + tesouro.getNome());
        }
        pausar();
    }

    private void verificarProgressoTorre() {
        if (inimigosDerrotados >= 10 && !cristaisPurificados[2]) {
            System.out.println("\nVOCÊ ENCONTROU O CRISTAL DO FOGO!");
            System.out.println();
            System.out.println("O cristal arde em chamas negras...");
            System.out.println("Esta é a fonte do poder do Lorde das Sombras!");
            System.out.print("Pressione ENTER para purificar o cristal...");
            scanner.nextLine();
            
            System.out.println("\nVocê canaliza toda sua força!");
            System.out.println("As chamas se transformam em luz dourada!");
            System.out.println("O poder das sombras é banido!");
            System.out.println();
            System.out.println("CRISTAL DO FOGO PURIFICADO!");
            
            cristaisPurificados[2] = true;
            capituloAtual++;
            
            // Recompensa
            try {
                jogador.setDefesa((short)(jogador.getDefesa() + 3));
                System.out.println("\nSua defesa aumentou em 3!");
            } catch (Exception e) {}
            
            verificarProgressoGeral();
            pausar();
        }
    }

    private void verificarProgressoGeral() {
        if(cristaisPurificados[0] && cristaisPurificados[1] && cristaisPurificados[2]) {
            lutaFinal();
        }
    }

    private void lutaFinal() {
        limparTela();
        jogador.setPontosVida(jogador.getPontosVidaMaximo());
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    CONFRONTO FINAL                            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Com os três cristais purificados, o poder das sombras enfraquece...");
        System.out.println();
        System.out.println("De repente, o céu escurece e uma voz ecoa:");
        System.out.println();
        System.out.println("\"Vocês ousam desafiar meu poder? PATÉTICOS!\"");
        System.out.println();
        System.out.println("O LORDE DAS SOMBRAS se materializa diante de você!");
        System.out.println();
        System.out.print("Pressione ENTER para enfrentar o confronto final...");
        scanner.nextLine();
        
        // Salvar automaticamente antes da luta final
        try {
            savePoint = (Personagem) jogador.clone();
            System.out.println("\n[Sistema] Save automático realizado antes da batalha final!");
            pausar();
        } catch (Exception e) {}
        
        limparTela();
        
        // Criar o Lorde das Sombras - boss final
        try {
            Inimigo lordeDasSombras = new Inimigo(
                "Lorde das Sombras", 
                (short)(jogador.getPontosVidaMaximo() * 2), // Vida = 2x a do jogador
                (short)(jogador.getAtaque() + 5),            // Ataque maior
                (short)(jogador.getDefesa() + 3),            // Defesa maior
                (byte)10,                                     // Nível 10
                new Inventario()
            );
            
            System.out.println("╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                        BATALHA FINAL                          ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("" + jogador.getNome() + " VS LORDE DAS SOMBRAS");
            System.out.println();
            System.out.println("\"Vocês são corajosos, mas a escuridão sempre prevalece!\"");
            System.out.println();
            pausar();
            
            // Fases da batalha
            jogador.batalhar(lordeDasSombras);
            
            // Verificar resultado
            if (jogador.getPontosVida() < 0) {
                // Derrota
                limparTela();
                System.out.println("═══════════════════════════════════════════════════════════════");
                System.out.println("\"PATÉTICO! A ESCURIDÃO SEMPRE VENCE!\"");
                System.out.println("═══════════════════════════════════════════════════════════════");
                gameOver();
            } else {
                // VITÓRIA!
                vitoriaFinalBoss();
            }
        }catch (Exception e) {
            System.out.println("Erro durante a batalha final: " + e.getMessage());
        }
    }

    private void vitoriaFinalBoss() {
        limparTela();
        System.out.println("O Lorde das Sombras cai de joelhos...");
        System.out.println();
        System.out.println("\"Não... IMPOSSÍVEL! EU SOU... ETERNO...\"");
        System.out.println();
        System.out.println("Com um último grito, ele se dissolve em fumaça negra.");
        System.out.println();
        pausar();
        verificarVitoria(); // Mostra a tela final de vitória
    }

    //verifica se o jogador purificou os tres cristais e exibe a mensagem de vitoria
    private void verificarVitoria() {
        if (cristaisPurificados[0] && cristaisPurificados[1] && cristaisPurificados[2]) {
            limparTela();
            System.out.println("╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                          VITÓRIA!                             ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("PARABÉNS!");
            System.out.println();
            System.out.println("Você purificou os três Cristais Ancestrais!");
            System.out.println();
            System.out.println("Com o poder dos cristais restaurado, a escuridão se dissipa.");
            System.out.println("O Lorde das Sombras foi banido para sempre!");
            System.out.println();
            System.out.println("O reino de Cristalios está salvo graças à sua coragem!");
            System.out.println("Seu nome será lembrado para sempre como o herói que");
            System.out.println("restaurou a paz e a luz ao mundo!");
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.println("ESTATÍSTICAS FINAIS:");
            System.out.println("  • Inimigos derrotados: " + inimigosDerrotados);
            System.out.println("  • Nível alcançado: " + jogador.getNivel());
            System.out.println("  • Capítulos completados: " + capituloAtual);
            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.println();
            System.out.println("Obrigado por jogar A LENDA DOS CRISTAIS ANCESTRAIS!");
            System.out.println();
            jogoAtivo = false;
            pausar();
        }
    }

    private void descansarNaVila() {
        limparTela();
        System.out.println("VILA DE CRISTALIOS");
        System.out.println("═══════════════════════════════════════");
        System.out.println();
        System.out.println("Você retorna à vila segura...");
        System.out.println("Os aldeões te recebem com gratidão.");
        System.out.println();
        System.out.println("Você descansa na taverna local.");
        System.out.println();
        
        try {
            jogador.setPontosVida(jogador.getPontosVidaMaximo());
            System.out.println("Sua vida foi totalmente restaurada!");
        } catch (Exception e) {}
        
        pausar();
    }

    private void criarSavePoint() {
        try {
            savePoint = (Personagem) jogador.clone();
            limparTela();
            System.out.println("SAVE POINT CRIADO COM SUCESSO!");
            System.out.println("\nSeu progresso foi salvo.");
        } catch (Exception e) {
            System.out.println("Erro ao criar save point!");
        }
        pausar();
    }

    private void carregarSavePoint() {
        if (savePoint == null) {
            limparTela();
            System.out.println("Nenhum save point encontrado!");
        } else {
            try {
                jogador = (Personagem) savePoint.clone();
                limparTela();
                System.out.println("SAVE POINT CARREGADO!");
                System.out.println("\nProgresso restaurado.");
            } catch (Exception e) {
                System.out.println("Erro ao carregar save point!");
            }
        }
        pausar();
    }

    private void gameOver() {
        limparTela();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                         GAME OVER                             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Você foi derrotado...");
        System.out.println();
        System.out.println("A escuridão prevalece sobre Cristalios.");
        System.out.println("Mas sua jornada não termina aqui...");
        System.out.println();
        
        if (savePoint != null) {
            System.out.println("Deseja carregar seu último save point? (S/N)");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (resposta.equals("S")) {
                carregarSavePoint();
                return;
            }
        }
        
        System.out.println("Obrigado por jogar!");
        jogoAtivo = false;
        pausar();
    }

    private void exibirStatus() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      STATUS DO JOGADOR                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println(jogador);
        System.out.println();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("PROGRESSO DA MISSÃO:");
        System.out.println("  Cristal da Natureza: " + (cristaisPurificados[0] ? " PURIFICADO" : " CORROMPIDO"));
        System.out.println("  Cristal da Água: " + (cristaisPurificados[1] ? " PURIFICADO" : " CORROMPIDO"));
        System.out.println("  Cristal do Fogo: " + (cristaisPurificados[2] ? " PURIFICADO" : " CORROMPIDO"));
        System.out.println("----------------------------------------------------------------------");
        System.out.println("  • Inimigos derrotados: " + inimigosDerrotados);
        System.out.println("  • Capítulo atual: " + capituloAtual);
    }

    private void mostrarStatusRapido() {
        String classe = jogador.getClass().getSimpleName();
        System.out.println("----------------------------------------------------------------------");
        System.out.println(classe + " | Nível: " + jogador.getNivel() + 
                         " | HP: " + jogador.getPontosVida() + "/" + jogador.getPontosVidaMaximo() +
                         " | Capítulo: " + capituloAtual);
        
        int cristaisPurif = (cristaisPurificados[0] ? 1 : 0) + 
                           (cristaisPurificados[1] ? 1 : 0) + 
                           (cristaisPurificados[2] ? 1 : 0);
        System.out.println("Cristais Purificados: " + cristaisPurif + "/3");
        System.out.println("----------------------------------------------------------------------");
    }

    private void encerrarJogo() {
        limparTela();
        System.out.println("Obrigado por jogar A LENDA DOS CRISTAIS ANCESTRAIS!");
        System.out.println();
        System.out.println("Até a próxima aventura!");
        jogoAtivo = false;
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pausar() {
        System.out.println();
        System.out.print("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciarJogo();
    }
}