import java.util.Scanner;
import java.util.Random;

public abstract class Personagem implements Cloneable {

    protected String nome;
    protected short pontosVida, pontosVidaMaximo, ataque, defesa, bonusAtaqueTemporario, bonusDefesaTemporario;
    protected boolean efeitoAtivo;
    protected byte nivel;
    protected Inventario inventario;

    protected Scanner scanner = new Scanner(System.in);

    public Personagem(String nome, short pontosVida, short ataque, short defesa, byte nivel, Inventario inventario) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new Exception("Nome inválido");
        if (pontosVida < 1) throw new Exception("Pontos de vida inválidos");
        if (ataque < 0) throw new Exception("Ataque inválido");
        if (defesa < 0) throw new Exception("Defesa inválida");
        if (nivel < 1) throw new Exception("Nível inválido");
        
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.pontosVidaMaximo = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = inventario != null ? new Inventario(inventario) : new Inventario();

        this.bonusAtaqueTemporario = 0;
        this.bonusDefesaTemporario = 0;
        this.efeitoAtivo = false;
    }

    public void setNome(String nome) throws Exception {
        if (nome == null)
            throw new Exception("Nome inválido");
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setPontosVida(short pontosVida) throws Exception {
        if (pontosVida < 0)
            throw new Exception("Pontos de vida inválidos");
        this.pontosVida = pontosVida;
    }

    public short getPontosVida() {
        return this.pontosVida;
    }

    public void setPontosVidaMaximo(short pontosVidaMaximo) throws Exception {
        if (pontosVidaMaximo < 1)
            throw new Exception("Pontos de vida máxima inválidos");
        this.pontosVidaMaximo = pontosVidaMaximo;
    }

    public short getPontosVidaMaximo() {
        return this.pontosVidaMaximo;
    }

    public void setAtaque(short ataque) throws Exception {
        if (ataque < 0)
            throw new Exception("Ataque inválido");
        this.ataque = ataque;
    }

    public short getAtaque() {
        return this.ataque;
    }

    public void setDefesa(short defesa) throws Exception {
        if (defesa < 0)
            throw new Exception("Defesa inválida");
        this.defesa = defesa;
    }

    public short getDefesa() {
        return this.defesa;
    }

    public void setNivel(byte nivel) throws Exception {
        if (nivel < 1)
            throw new Exception("Nível inválido");
        this.nivel = nivel;
    }

    public byte getNivel() {
        return this.nivel;
    }

    public void setInventario(Inventario inventario) throws Exception {
        if (inventario == null)
            throw new Exception("Inventário inválido");
        this.inventario = new Inventario(inventario);
    }

    public Inventario getInventario() {
        return this.inventario;
    }

    public void batalhar(Inimigo inimigo) throws Exception {
        Random random = new Random();

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      BATALHA INICIADA!                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("⚔️  " + this.nome + " VS " + inimigo.getNome() + " 💀");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();

        while(this.pontosVida > 0 && inimigo.getPontosVida() > 0) {
            System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
            System.out.println("│ HP Jogador: " + this.pontosVida + "/" + this.pontosVidaMaximo + 
                             " | HP Inimigo: " + inimigo.getPontosVida() + "       ");
            System.out.println("└─────────────────────────────────────────────────────────────┘");
            System.out.println();
            System.out.println("O que deseja fazer?");
            System.out.println("  1) ⚔️  Atacar");
            System.out.println("  2) 🎒 Usar Item");
            System.out.println("  3) 🏃 Fugir");
            System.out.print("\nEscolha: ");

            byte escolha = -1;
            try {
                escolha = scanner.nextByte();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                escolha = -1;
            }

            switch(escolha) {
                case 1 -> {
                    atacar(inimigo, random);
                }
                case 2 -> {
                    usarItem();
                }
                case 3 -> {
                    if (fugir(random)) {
                        return;
                    }
                }
                default -> System.out.println("\n❌ Escolha inválida!");
            }

            if (inimigo.getPontosVida() <= 0) {
                System.out.println("\n✨════════════════════════════════════════════════════════✨");
                System.out.println("   🎉 VITÓRIA! Você derrotou " + inimigo.getNome() + "! 🎉");
                System.out.println("✨════════════════════════════════════════════════════════✨");
                
                Item itemDropado = Item.itemAleatorio(random.nextInt(4));
                System.out.println("\n🎁 Item dropado: " + itemDropado.getNome() + "!");
                this.inventario.adicionarItem(itemDropado);

                uparNivel();
                break;
            }

            if (this.pontosVida <= 0) {
                System.out.println("\n💀 " + this.nome + " foi derrotado!");
                break;
            }
        }
    }

    private void atacar(Inimigo inimigo, Random random) throws Exception {
        short jogadorDado = (short) (random.nextInt(6) + 1);
        short inimigoDado = (short) (random.nextInt(6) + 1);

        short ataqueJogador = (short) (this.ataque + this.bonusAtaqueTemporario + jogadorDado);
        short ataqueInimigo = (short) (inimigo.getAtaque() + inimigoDado);

        System.out.println("\n🎲 " + this.nome + " rolou " + jogadorDado + " → Ataque total: " + ataqueJogador);
        System.out.println("🎲 " + inimigo.getNome() + " rolou " + inimigoDado + " → Ataque total: " + ataqueInimigo);
        System.out.println();

        if(ataqueJogador > inimigo.getDefesa()) {
            short danoJogador = (short) (ataqueJogador - inimigo.getDefesa());
            inimigo.setPontosVida((short) (inimigo.getPontosVida() - danoJogador));

            System.out.println("💥 Você causou " + danoJogador + " de dano!");
            System.out.println("   " + inimigo.getNome() + " está com " + inimigo.getPontosVida() + " HP");
        } else {
            System.out.println("🛡️  " + inimigo.getNome() + " defendeu o ataque!");
        }

        if(ataqueInimigo > this.defesa) {
            short danoInimigo = (short) (ataqueInimigo - (this.defesa + this.bonusDefesaTemporario));
            this.setPontosVida((short) (this.pontosVida - danoInimigo));

            System.out.println("💔 Inimigo causou " + danoInimigo + " de dano!");
            System.out.println("   Você está com " + this.pontosVida + " HP");
        } else {
            System.out.println("🛡️  Você defendeu o ataque!");
        }

        if (efeitoAtivo) {
            bonusAtaqueTemporario = 0;
            bonusDefesaTemporario = 0;
            efeitoAtivo = false;
            System.out.println("\n⏱️  Efeitos temporários expiraram!");
        }
    }

    private void usarItem() {
        System.out.println();
        this.getInventario().listarItens();

        if (this.getInventario().getItens().isEmpty()) {
            return;
        }

        System.out.print("Digite o nome do item (ou 'cancelar'): ");
        String nomeItem = scanner.nextLine().trim();

        if (nomeItem.equalsIgnoreCase("cancelar")) {
            System.out.println("Ação cancelada.");
            return;
        }

        Item itemUsado = null;
        for (Item i : this.getInventario().getItens()) {
            if (i.getNome().equalsIgnoreCase(nomeItem)) {
                itemUsado = new Item(i);
                break;
            }
        }

        boolean usandoItem = this.getInventario().usarItem(nomeItem);

        if (usandoItem && itemUsado != null) {
            System.out.println("\n✓ Você usou " + itemUsado.getNome() + "!");
            aplicarEfeitoTemporario(itemUsado);
        } else {
            System.out.println("\n❌ Item não encontrado ou sem quantidade!");
        }
    }

    private void aplicarEfeitoTemporario(Item item) {
        String nomeItem = item.getNome().toLowerCase();

        try {
            switch (nomeItem) {
                case "poção de vida pequena" -> {
                    short cura = (short) (this.pontosVidaMaximo * 0.3);
                    this.pontosVida += cura;
                    if (this.pontosVida > this.pontosVidaMaximo)
                        this.pontosVida = this.pontosVidaMaximo;
                    System.out.println("💚 Você recuperou " + cura + " de HP!");
                }
                case "poção média de vida" -> {
                    short cura = (short) (this.pontosVidaMaximo * 0.5);
                    this.pontosVida += cura;
                    if (this.pontosVida > this.pontosVidaMaximo)
                        this.pontosVida = this.pontosVidaMaximo;
                    System.out.println("💚 Você recuperou " + cura + " de HP!");
                }
                case "poção grande de vida" -> {
                    short cura = (short) (this.pontosVidaMaximo * 0.75);
                    this.pontosVida += cura;
                    if (this.pontosVida > this.pontosVidaMaximo)
                        this.pontosVida = this.pontosVidaMaximo;
                    System.out.println("💚 Você recuperou " + cura + " de HP!");
                }
                case "elixir dos deuses" -> {
                    this.pontosVida = this.pontosVidaMaximo;
                    System.out.println("✨ Vida totalmente restaurada!");
                }
                case "poção de ataque" -> {
                    this.bonusAtaqueTemporario = 5;
                    this.efeitoAtivo = true;
                    System.out.println("⚔️  Seu ataque aumentou temporariamente!");
                }
                case "amuleto raro" -> {
                    this.bonusDefesaTemporario = 3;
                    this.efeitoAtivo = true;
                    System.out.println("🛡️  Sua defesa aumentou temporariamente!");
                }
                case "elixir da fúria" -> {
                    this.bonusAtaqueTemporario = 8;
                    this.efeitoAtivo = true;
                    System.out.println("😡 Você sente uma fúria incontrolável! Seu ataque explodiu!");
                }
                case "essência do berserker" -> {
                    this.bonusAtaqueTemporario = 6;
                    this.bonusDefesaTemporario = 4;
                    this.efeitoAtivo = true;
                    System.out.println("⚡ MODO BERSERKER ATIVADO! Ataque e Defesa aumentados!");
                }
                case "excalibur" -> {
                    this.bonusAtaqueTemporario = 15;
                    this.efeitoAtivo = true;
                    System.out.println("⚔️  ✨ Poder da Excalibur ativado!");
                }
                case "capa da invisibilidade" -> {
                    this.bonusDefesaTemporario = 9999;
                    this.efeitoAtivo = true;
                    System.out.println("Você está coberto pela capa da invisibilidade! desviará garantido do proximo golpe!");
                }
                default -> System.out.println("🤷 O item não causou efeito em batalha.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao aplicar efeito!");
        }
    }

    private boolean fugir(Random random) {
        System.out.println("\n🏃 Tentando fugir...");
        int chance = random.nextInt(100);
        
        if (chance < 50) {
            System.out.println("✓ Você conseguiu fugir!");
            return true;
        } else {
            System.out.println("❌ Você não conseguiu fugir!");
            System.out.println("O inimigo aproveita e ataca!");
            
            try {
                short dano = (short)(this.ataque * 0.3);
                this.setPontosVida((short)(this.pontosVida - dano));
                System.out.println("💔 Você sofreu " + dano + " de dano!");
            } catch (Exception e) {}
            
            return false;
        }
    }

    private void uparNivel() {
        this.nivel++;
        System.out.println("\n⬆️  LEVEL UP! Você alcançou o nível " + this.nivel + "!");

        System.out.println("\n📈 Escolha um atributo para aumentar:");
        System.out.println("  1) ❤️  Vida (+5 HP máximo e cura completa)");
        System.out.println("  2) ⚔️  Ataque (+2 de ataque)");
        System.out.println("  3) 🛡️  Defesa (+2 de defesa)");
        System.out.print("\nEscolha: ");

        byte escolha = -1;
        try {
            escolha = scanner.nextByte();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            escolha = 1;
        }

        try {
            switch (escolha) {
                case 1 -> {
                    this.pontosVidaMaximo += 5;
                    this.pontosVida = this.pontosVidaMaximo;
                    System.out.println("\n✓ Vida aumentada e restaurada!");
                }
                case 2 -> {
                    this.ataque += 2;
                    System.out.println("\n✓ Ataque aumentado!");
                }
                case 3 -> {
                    this.defesa += 2;
                    System.out.println("\n✓ Defesa aumentada!");
                }
                default -> {
                    this.pontosVidaMaximo += 5;
                    this.pontosVida = this.pontosVidaMaximo;
                    System.out.println("\n✓ Vida aumentada (padrão)!");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao upar atributo!");
        }
    }

    @Override
    public String toString() {
        return "╔═══════════════════════════════════════════════════════════════╗\n" +
               "  " + this.nome + " - Nível " + this.nivel + "\n" +
               "  ❤️  Vida: " + this.pontosVida + "/" + this.pontosVidaMaximo + "\n" +
               "  ⚔️  Ataque: " + this.ataque + "\n" +
               "  🛡️  Defesa: " + this.defesa + "\n" +
               "╚═══════════════════════════════════════════════════════════════╝";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj.getClass() != Personagem.class) return false;

        Personagem p = (Personagem) obj;

        if (!this.nome.equals(p.nome)) return false;
        if (this.pontosVida != p.pontosVida) return false;
        if (this.pontosVidaMaximo != p.pontosVidaMaximo) return false;
        if (this.ataque != p.ataque) return false;
        if (this.defesa != p.defesa) return false;
        if (this.bonusDefesaTemporario != p.bonusDefesaTemporario) return false;
        if (this.bonusAtaqueTemporario != p.bonusAtaqueTemporario) return false;
        if (this.efeitoAtivo != p.efeitoAtivo) return false;
        if (this.nivel != p.nivel) return false;
        if (!this.inventario.equals(p.inventario)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = ret * 13 + this.nome.hashCode();
        ret = ret * 13 + ((Short)this.pontosVida).hashCode();
        ret = ret * 13 + ((Short)this.pontosVidaMaximoo).hashCode();
        ret = ret * 13 + ((Short)this.ataque).hashCode();
        ret = ret * 13 + ((Short)this.defesa).hashCode();
        ret = ret * 13 + ((Short)this.bonusDefesaTemporario).hashCode();
        ret = ret * 13 + ((Short)this.bonusAtaqueTemporario).hashCode();
        ret = ret * 13 + ((Boolean)this.efeitoAtivo).hashCode();
        ret = ret * 13 + ((Byte)this.nivel).hashCode();
        ret = ret * 13 + this.inventario.hashCode();

        if (ret < 0) ret = -ret;

        return ret;
    }

    public Personagem(Personagem modelo) throws Exception {
        if (modelo == null)
            throw new Exception("Modelo de personagem inexistente");

        this.nome = modelo.nome;
        this.pontosVida = modelo.pontosVida;
        this.pontosVidaMaximo = modelo.pontosVidaMaximo;
        this.ataque = modelo.ataque;
        this.defesa = modelo.defesa;
        this.nivel = modelo.nivel;
        this.inventario = new Inventario(modelo.inventario);

        // Cópia dos efeitos
        this.bonusAtaqueTemporario = modelo.bonusAtaqueTemporario;
        this.bonusDefesaTemporario = modelo.bonusDefesaTemporario;
        this.efeitoAtivo = modelo.efeitoAtivo;
    }

    // Cada subclasse implementa seu clone usando o construtor de cópia acima,
    // o super(modelo) para cada clone chama dessa classe.
    @Override
    public abstract Object clone();
}