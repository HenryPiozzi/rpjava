import java.util.Scanner;
import java.util.Random;

public abstract class Personagem implements Cloneable {

    protected String nome;
    protected short pontosVida, pontosVidaMaximo, ataque, defesa, bonusAtaqueTemporario, bonusDefesaTemporario;
    protected boolean efeitoAtivo;
    protected byte nivel;
    protected Inventario inventario;

    protected Scanner scanner = new Scanner(System.in);

    public Personagem(String nome, short pontosVida, short ataque, short defesa, byte nivel, Inventario inventario) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.pontosVidaMaximo = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = new Inventario(inventario);

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

    public short getBonusAtaqueTemporario() {
        return bonusAtaqueTemporario;
    }

    public void setBonusAtaqueTemporario(short bonusAtaqueTemporario) throws Exception {
        if (bonusAtaqueTemporario < 0) {
            throw new Exception("O bônus de ataque temporário não pode ser negativo.");
        }
        this.bonusAtaqueTemporario = bonusAtaqueTemporario;
    }

    public short getBonusDefesaTemporario() {
        return bonusDefesaTemporario;
    }

    public void setBonusDefesaTemporario(short bonusDefesaTemporario) throws Exception {
        if (bonusDefesaTemporario < 0) {
            throw new Exception("O bônus de defesa temporário não pode ser negativo.");
        }
        this.bonusDefesaTemporario = bonusDefesaTemporario;
    }

    public boolean isEfeitoAtivo() {
        return efeitoAtivo;
    }

    public void setEfeitoAtivo(boolean efeitoAtivo) throws Exception {
        if (this.efeitoAtivo && efeitoAtivo) {
            throw new Exception("O efeito já está ativo.");
        }
        this.efeitoAtivo = efeitoAtivo;
    }

    public void batalhar(Inimigo inimigo) throws Exception {
        Random random = new Random();

        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      BATALHA INICIADA!                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println(this.nome + " VS " + inimigo.nome);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();

        while(this.pontosVida > 0 && inimigo.pontosVida > 0) {
            System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
            System.out.println("│ HP Jogador: " + this.pontosVida + "/" + this.pontosVidaMaximo + 
                             " | HP Inimigo: " + inimigo.pontosVida + "       ");
            System.out.println("└─────────────────────────────────────────────────────────────┘");
            System.out.println();
            System.out.println("O que deseja fazer?");
            System.out.println("  1) Atacar");
            System.out.println("  2) Usar Item");
            System.out.println("  3) Fugir");
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
                default -> System.out.println("\nEscolha inválida!");
            }

            if (inimigo.pontosVida <= 0) {
                System.out.println("\n════════════════════════════════════════════════════════");
                System.out.println("           VITÓRIA! Você derrotou " + inimigo.nome);
                System.out.println("════════════════════════════════════════════════════════");
                
                Item itemDropado = Item.itemAleatorio(random.nextInt(4));
                System.out.println("\nItem dropado: " + itemDropado.nome + "!");
                this.inventario.adicionarItem(itemDropado);

                uparNivel();
                break;
            }

            if (this.pontosVida <= 0) {
                System.out.println("\n"+ this.nome + " foi derrotado!");
                break;
            }
        }
    }

    private void atacar(Inimigo inimigo, Random random) throws Exception {
        short jogadorDado = (short) (random.nextInt(6) + 1);
        short inimigoDado = (short) (random.nextInt(6) + 1);

        short ataqueJogador = (short) (this.ataque + this.bonusAtaqueTemporario + jogadorDado);
        short ataqueInimigo = (short) (inimigo.ataque + inimigoDado);

        System.out.println("\n" + this.nome + " rolou " + jogadorDado + " → Ataque total: " + ataqueJogador);
        System.out.println(inimigo.nome + " rolou " + inimigoDado + " → Ataque total: " + ataqueInimigo);
        System.out.println();

        if(ataqueJogador > inimigo.defesa) {
            short danoJogador = (short) (ataqueJogador - inimigo.defesa);
            inimigo.setPontosVida((short) (inimigo.pontosVida - danoJogador));

            System.out.println("Você deu " + danoJogador + " de dano!");
            System.out.println(inimigo.nome + " está com " + inimigo.pontosVida + " HP");
        } else {
            System.out.println(inimigo.nome + " defendeu o ataque!");
        }

        if(ataqueInimigo > this.defesa) {
            short danoInimigo = (short) (ataqueInimigo - (this.defesa + this.bonusDefesaTemporario));
            this.setPontosVida((short) (this.pontosVida - danoInimigo));

            System.out.println("Inimigo deu " + danoInimigo + " de dano!");
            System.out.println("Você está com " + this.pontosVida + " HP");
        } else {
            System.out.println("Você defendeu o ataque!");
        }

        if (efeitoAtivo) {
            bonusAtaqueTemporario = 0;
            bonusDefesaTemporario = 0;
            efeitoAtivo = false;
            System.out.println("\nEfeitos temporários expiraram!");
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

        aplicarEfeitoTemporario(inventario.usarItem(nomeItem));
        return;
    }

    private void aplicarEfeitoTemporario(String[] efeitos) {

        switch (efeitos[0].toLowerCase()) {
            case "cura" -> {
                short cura = (short) (this.pontosVidaMaximo * (Short.parseShort(efeitos[1])/100.0));
                this.pontosVida += cura;
                if (this.pontosVida > this.pontosVidaMaximo)
                    this.pontosVida = this.pontosVidaMaximo;
                System.out.println("Você recuperou " + cura + " de HP!");
            }
            case "dano bônus" -> {
                this.bonusAtaqueTemporario = Short.parseShort(efeitos[1]);
                this.efeitoAtivo = true;
                System.out.println("Seu ataque aumentou temporariamente em " + efeitos[1] + "!");
            }
            case "defesa bônus" -> {
                this.bonusDefesaTemporario = Short.parseShort(efeitos[1]);
                this.efeitoAtivo = true;
                System.out.println("Sua defesa aumentou temporariamente em " + efeitos[1] + "!");
            }
            case "duplo bônus" -> {
                this.bonusAtaqueTemporario = Short.parseShort(efeitos[1]);
                this.bonusDefesaTemporario = Short.parseShort(efeitos[1]);
                this.efeitoAtivo = true;
                System.out.println("MODO BERSERKER ATIVADO! Ataque e Defesa aumentados em " + efeitos[1] + "!");
            }
            default -> System.out.println("O item não causou efeito em batalha.");
        }
    }

    private boolean fugir(Random random) {
        System.out.println("\nTentando fugir...");
        int chance = random.nextInt(100);
        
        if (chance < 50) {
            System.out.println("✓ Você conseguiu fugir!");
            return true;
        } else {
            System.out.println("Você não conseguiu fugir!");
            System.out.println("O inimigo aproveita e ataca!");
            
            try {
                short dano = (short)(this.ataque * 0.3);
                this.setPontosVida((short)(this.pontosVida - dano));
                System.out.println("Você sofreu " + dano + " de dano!");
            } catch (Exception e) {}
            
            return false;
        }
    }

    private void uparNivel() {
        this.nivel++;
        System.out.println("\nLEVEL UP! Você alcançou o nível " + this.nivel + "!");

        System.out.println("\nEscolha um atributo para aumentar:");
        System.out.println("  1) Vida (+5 HP máximo e cura completa)");
        System.out.println("  2) Ataque (+2 de ataque)");
        System.out.println("  3) Defesa (+2 de defesa)");
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
                    System.out.println("\nVida aumentada e restaurada!");
                }
                case 2 -> {
                    this.ataque += 2;
                    System.out.println("\nAtaque aumentado!");
                }
                case 3 -> {
                    this.defesa += 2;
                    System.out.println("\nDefesa aumentada!");
                }
                default -> {
                    this.pontosVidaMaximo += 5;
                    this.pontosVida = this.pontosVidaMaximo;
                    System.out.println("\nVida aumentada (padrão)!");
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
               "  Vida: " + this.pontosVida + "/" + this.pontosVidaMaximo + "\n" +
               "  Ataque: " + this.ataque + "\n" +
               "  Defesa: " + this.defesa + "\n" +
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
        ret = ret * 13 + ((Short)this.pontosVidaMaximo).hashCode();
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