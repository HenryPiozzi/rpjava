public abstract class Personagem {

    protected String nome;
    protected byte pontosVida, ataque, defesa, nivel;
    protected Inventario inventario;

    public Personagem (String nome, byte pontosVida, byte ataque, byte defesa, byte nivel, Inventario inventario) {
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

    public void setPontosVida(byte pontosVida) throws Exception {
        if (pontosVida <= 0)
            throw new Exception("Pontos de vida inválidos");
        this.pontosVida = pontosVida;
    }

    public byte getPontosVida() {
        return this.pontosVida;
    }

    public void setAtaque(byte ataque) throws Exception {
        if (ataque < 0)
            throw new Exception("Ataque inválido");
        this.ataque = ataque;
    }

    public byte getAtaque() {
        return this.ataque;
    }

    public void setDefesa(byte defesa) throws Exception {
        if (defesa < 0)
            throw new Exception("Defesa inválida");
        this.defesa = defesa;
    }

    public byte getDefesa() {
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

    @Override
    public String toString() {
        return this.nome + " (Nível " + this.nivel + ") - Vida: " + this.pontosVida +
                ", Ataque: " + this.ataque + ", Defesa: " + this.defesa +
                ", Inventário: " + this.inventario.toString();
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
        ret = ret * 13 + ((Byte)this.pontosVida).hashCode();
        ret = ret * 13 + ((Byte)this.ataque).hashCode();
        ret = ret * 13 + ((Byte)this.defesa).hashCode();
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
