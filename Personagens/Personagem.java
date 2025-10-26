import java.util.Scanner;
import java.util.Random;

public abstract class Personagem implements  Cloneable {

    protected String nome;
    protected short pontosVida, ataque, defesa;
    protected short bonusAtaqueTemporario = 0;
    protected short bonusDefesaTemporario = 0;
    protected boolean efeitoAtivo = false;
    protected byte nivel;
    protected Inventario inventario;

    public Personagem (String nome, short pontosVida, short ataque, short defesa, byte nivel, Inventario inventario) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.inventario = new Inventario(inventario);
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

    Scanner scanner = new Scanner(System.in);

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
                // Colocar aqui o item aleatório
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
            System.out.println(inimigo.getNome() + "defendeu seu ataque! Ataque menor que a defesa.");
        }

        // Ataque do inimigo (Defesa do jogador)
        if(ataqueInimigo > this.defesa) {
            byte danoInimigo = (short) (ataqueInimigo - (this.defesa + this.bonusDefesaTemporario));
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
        boolean usandoItem = this.getInventario().usarItem(nomeItem);

        if (usandoItem) {
            System.out.println(" Você usou " + nomeItem + "!");
        } else {
            System.out.println("Item não encontrado ou sem quantidade!");
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
                this.pontosVida += 5;
                System.out.println("Vida upada!");

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
        return this.nome + " (Nível " + this.nivel + ") - Vida: " + this.pontosVida +
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
        if (this.ataque != p.ataque) return false;
        if (this.defesa != p.defesa) return false;
        if (this.nivel != p.nivel) return false;
        if (!this.inventario.equals(p.inventario)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = ret * 13 + this.nome.hashCode();
        ret = ret * 13 + ((Short)this.pontosVida).hashCode();
        ret = ret * 13 + ((Short)this.ataque).hashCode();
        ret = ret * 13 + ((Short)this.defesa).hashCode();
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
        this.ataque = modelo.ataque;
        this.defesa = modelo.defesa;
        this.nivel = modelo.nivel;
        this.inventario = new Inventario(modelo.inventario);
    }

    // Cada subclasse implementa seu clone usando o construtor de cópia acima,
    // o super(modelo) para cada clone chama dessa classe.
    @Override
    public abstract Object clone();
}
