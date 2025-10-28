import java.util.Scanner;
import java.util.Random;

public abstract class Personagem implements  Cloneable {

    protected String nome;
    protected short pontosVida, pontosVidaMaximo, ataque, defesa, bonusAtaqueTemporario, bonusDefesaTemporario;
    protected boolean efeitoAtivo;
    protected byte nivel;
    protected Inventario inventario;

    protected Scanner scanner = new Scanner(System.in);

    public Personagem (String nome, short pontosVida, short ataque, short defesa, byte nivel, Inventario inventario) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.pontosVidaMaximo = pontosVida; // define o limite inicial
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = new Inventario(inventario);

        // Efeito das poções
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

    public void batalhar(Inimigo inimigo) {
        Random random = new Random();

        System.out.println("\nBatalha iniciada!");
        System.out.println(this.nome + " VS " + inimigo.getNome());
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        while(this.pontosVida > 0 && inimigo.getPontosVida() > 0) {
            System.out.println("\nO que deseja fazer?");
            System.out.println("1) Atacar");
            System.out.println("2) Usar Item");
            System.out.println("3) Fugir");
            System.out.print("Escolha: ");

            byte escolha = scanner.nextByte();
            scanner.nextLine();

            switch(escolha) {
                case 1 -> {
                    atacar(inimigo, random);
                }
                case 2 -> {
                    usarItem();
                }
                case 3 -> {
                    fugir();
                    return;
                }
                default -> System.out.println("\nEscolha uma dessas opções!");
            }
            // Inimigo é derrotado
            if (inimigo.getPontosVida() <= 0) {
                System.out.println("\n" + inimigo.getNome() + " foi derrotado!");
                System.out.println("Você venceu a batalha!");

                uparNivel(nivel);

                Random sorteio = new Random();
                int chance = sorteio.nextInt(100); // de 0 a 99
                Item itemDropado;

                if (chance < 50) { // 50% de chance de comum
                    itemDropado = Item.ITEM_COMUM[sorteio.nextInt(Item.ITEM_COMUM.length)];
                } else if (chance < 80) { // 30% de chance de raro
                    itemDropado = Item.ITEM_RARO[sorteio.nextInt(Item.ITEM_RARO.length)];
                } else if (chance < 95) { // 15% de chance de épico
                    itemDropado = Item.ITEM_EPICO[sorteio.nextInt(Item.ITEM_EPICO.length)];
                } else { // 5% de chance de lendário
                    itemDropado = Item.ITEM_LENDARIO[sorteio.nextInt(Item.ITEM_LENDARIO.length)];
                }

                System.out.println("Você encontrou um item: " + itemDropado.getNome() + "!");
                this.inventario.adicionarItem(itemDropado);

                break;
            }
            // Jogador é derrotado
            if (this.pontosVida <= 0) {
                System.out.println("\n" + this.nome + " foi derrotado!");
                break;
            }
        }
    }

    private void atacar(Inimigo inimigo, Random random) {
        //Joga o dado
        short jogadorDado = (short) (random.nextInt(6) + 1);
        short inimigoDado = (short) (random.nextInt(6) + 1);

        //Soma o dado com o ataque
        short ataqueJogador = (short) (this.ataque + this.bonusAtaqueTemporario + jogadorDado);
        short ataqueInimigo = (short) (inimigo.getAtaque() + inimigoDado);

        System.out.println("\n" + this.nome + " rolou " + jogadorDado + " e ficou com " + ataqueJogador + " de ataque!");
        System.out.println(inimigo.getNome + " rolou " + inimigoDado + " e ficou com " + ataqueInimigo + " de ataque!");

        //Ataque do jogador (Defesa do Inimigo)
        if(ataqueJogador > inimigo.getDefesa()) {
            short danoJogador = (short) (ataqueJogador - inimigo.getDefesa());
            inimigo.setPontosVida((short) (inimigo.getPontosVida() - danoJogador));

            System.out.println("Você causou" + danoJogador + " de dano!");
            System.out.println(inimigo.getNome() " está agora com " + inimigo.getPontosVida() + " de vida!");
        } else {
            System.out.println(inimigo.getNome() + "defendeu o ataque! Ataque menor que a defesa.");
        }

        // Ataque do inimigo (Defesa do jogador)
        if(ataqueInimigo > this.defesa) {
            short danoInimigo = (short) (ataqueInimigo - (this.defesa + this.bonusDefesaTemporario));
            this.setPontosVida((short) (this.pontosVida - danoInimigo));

            System.out.println("Inimigo causou " + danoInimigo + " de dano!");
            System.out.println(this.nome + " está agora com " + this.pontosVida " de vida!");
        } else {
            System.out.println(this.nome + "defendeu o ataque! Ataque menor que a defesa.");
        }

        // Remove os buffs temporários após o turno.
        if (efeitoAtivo) {
            bonusAtaqueTemporario = 0;
            bonusDefesaTemporario = 0;
            efeitoAtivo = false;
        }
    }

    private void usarItem() {
        System.out.print("\nInventário: ");
        this.getInventario().listarItens();

        System.out.print("Digite o nome do item: ");
        String nomeItem = scanner.nextLine();

        Item itemUsado = null;
        for (Item i : this.getInventario().clone().itens) {
            if (i.getNome().equalsIgnoreCase(nomeItem)) {
                itemUsado = i;
                break;
            }
        }

        boolean usandoItem = this.getInventario().usarItem(nomeItem);

        if (usandoItem) {
            System.out.println(" Você usou " + nomeItem + "!");

            if (itemUsado != null) {
               aplicarEfeitoTemporario(itemUsado);
               System.out.println("Efeito aplicado: " + itemUsado.getEfeito());
            }
        } else {
            System.out.println("Item não encontrado ou sem quantidade!");
        }
    }

    private void aplicarEfeitoTemporario(Item item) {
        String nomeItem = item.getNome().toLowerCase();

        switch (nomeItem) {
            case "poção de vida pequena" -> {
                short cura = (short) (this.pontosVidaMaximo * 0.3);
                this.pontosVida += cura;
                if (this.pontosVida > this.pontosVidaMaximo)
                    this.pontosVida = this.pontosVidaMaximo;
                System.out.println("Você recuperou " + cura + " de HP!");
            }
            case "poção media de vida" -> {
                short cura = (short) (this.pontosVidaMaximo * 0.5);
                this.pontosVida += cura;
                if (this.pontosVida > this.pontosVidaMaximo)
                    this.pontosVida = this.pontosVidaMaximo;
                System.out.println("Você recuperou " + cura + " de HP!");
            }
            case "poção grande de vida" -> {
                short cura = (short) (this.pontosVidaMaximo * 0.75);
                this.pontosVida += cura;
                if (this.pontosVida > this.pontosVidaMaximo)
                    this.pontosVida = this.pontosVidaMaximo;
                System.out.println("Você recuperou " + cura + " de HP!");
            }
            case "elixir dos deuses" -> {
                this.pontosVida = this.pontosVidaMaximo;
                System.out.println("Vida totalmente restaurada!");
            }
            case "poção de ataque" -> {
                this.bonusAtaqueTemporario = 3;
                this.efeitoAtivo = true;
                System.out.println("Seu ataque aumentou temporariamente!");
            }
            case "amuleto raro" -> {
                this.bonusDefesaTemporario = 3;
                this.efeitoAtivo = true;
                System.out.println("Sua defesa aumentou temporariamente!");
            }
            case "excalibur" -> {
                this.bonusAtaqueTemporario = 10;
                this.efeitoAtivo = true;
                System.out.println("Você usou a Excalibur! Irá causar um dano altíssimo!");
            }
            default -> System.out.println("O item não fez efeito.");
        }
    }

    private void fugir() {
        System.out.println("\nVocê fugiu da batalha!");
    }

    public void uparNivel(byte nivel) {

        this.nivel++;
        System.out.println("\nVocê subiu de nível! Agora você está no nível " + this.nivel);

        System.out.println("\nVocê ganhou um ponto de atríbuto.");
        System.out.println("O que deseja upar?");
        System.out.println("1) Vida");
        System.out.println("2) Ataque");
        System.out.println("3) Defesa");

        System.out.print("Escolha: ");
        byte escolha = scanner.nextByte();

        switch (escolha) {
            case 1 -> {
                this.pontosVidaMaximo += 3; // aumenta o limite máximo
                this.pontosVida = this.pontosVidaMaximo; // recupera toda a vida ao upar
                System.out.println("Vida upada e restaurada!");
            }
            case 2 -> {
                this.ataque += 1;
                System.out.println("Ataque upado!");
            }
            case 3 -> {
                this.defesa += 1;
                System.out.println("Defesa upada!");
            }
            default -> System.out.println("\nEscolha uma dessas opções!");
        }
    }

    @Override
    public String toString() {
        return this.nome +
                " (Nível " + this.nivel + ") " +
                "- Vida: " + this.pontosVida + "/" + this.pontosVidaMaximo +
                ", Ataque: " + this.ataque + ", Defesa: " + this.defesa +
                ", Inventário: " + this.inventario.listarItens();
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
