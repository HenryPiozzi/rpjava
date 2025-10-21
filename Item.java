Item[] itemComum = {
    new Item("Poção de Vida pequena", "Recupera 30% de HP", "Cura", (byte)1),
    new Item("Poção de Ataque", "Causa X de dano a mais no proximo ataque", "Recupera mana", (byte)1),
    new Item("Pedra Pequena", "Uma pedra comum, pode ser jogada em inimigos", "Dano leve", (byte)1)
};

Item[] itemRaro = {
    new Item("Poção Media de Vida", "Recupera 50% de HP", "Cura", (byte)1),
    new Item("Bomba de Fogo", "Causa dano de fogo", "Explosão", (byte)1),
    new Item("Amuleto Raro", "Aumenta 10% da defesa", "Buff de defesa", (byte)1)
};

Item[] itemEpico = {
    new Item("Oleo Flamejante", "Deixa o inimigo pegando fogo, recebendo dano no turno seguinte", "Dano de fogo", (byte)1),
    new Item("Anel da Sabedoria", "Aumenta inteligência em 20", "Buff", (byte)1),
    new Item("Poção Grande de Vida", "Recupera 75% de HP", "Cura", (byte)1)
};

Item[] itemLendario = {
    new Item("Excalibur", "A lendária espada sagrada", "Dano massivo", (byte)1),
    new Item("Elixir dos Deuses", "Recupera todo HP", "Cura total", (byte)1),
    new Item("Capa da Invisibilidade", "Não recebe o proximo ataque", "Invisibilidade", (byte)1)
};

public class Item{
    private String nome;
    private String descricao;
    private String efeito;
    private byte quantidade;

    private static final Item[] ITEM_COMUM = {
        new Item("Poção de Vida pequena", "Recupera 30% de HP", "Cura", (byte) 1),
        new Item("Poção de Ataque", "Causa X de dano a mais no proximo ataque", "Dano bônus", (byte) 1),
        new Item("Pedra Pequena", "Uma pedra comum, pode ser jogada em inimigos", "Dano leve", (byte) 1)
    };

    private static final Item[] ITEM_RARO = {
        new Item("Poção Média de Vida", "Recupera 50% de HP", "Cura", (byte) 1),
        new Item("Bomba de Fogo", "Causa dano de fogo", "Explosão", (byte) 1),
        new Item("Amuleto Raro", "Aumenta 10% da defesa", "Buff de defesa", (byte) 1)
    };

    private static final Item[] ITEM_EPICO = {
        new Item("Óleo Flamejante", "Deixa o inimigo pegando fogo, recebendo dano no turno seguinte", "Dano de fogo", (byte) 1),
        new Item("Anel da Sabedoria", "Aumenta inteligência em 20", "Buff", (byte) 1),
        new Item("Poção Grande de Vida", "Recupera 75% de HP", "Cura", (byte) 1)
    };

    private static final Item[] ITEM_LENDARIO = {
        new Item("Excalibur", "A lendária espada sagrada", "Dano massivo", (byte) 1),
        new Item("Elixir dos Deuses", "Recupera todo HP", "Cura total", (byte) 1),
        new Item("Capa da Invisibilidade", "Não recebe o próximo ataque", "Invisibilidade", (byte) 1)
    };


    public static Item itemAleatorio(int nivelDoItem) {
        switch (nivelDoItem) {
            case 0:
                return ITEM_COMUM[ThreadLocalRandom.current().nextInt(ITEM_COMUM.length)];
            case 1:
                return ITEM_RARO[ThreadLocalRandom.current().nextInt(ITEM_RARO.length)];
            case 2:
                return ITEM_EPICO[ThreadLocalRandom.current().nextInt(ITEM_EPICO.length)];
            case 3:
                return ITEM_LENDARIO[ThreadLocalRandom.current().nextInt(ITEM_LENDARIO.length)];
            default:
                return null;
        }
    }

    public Item(String nome, String descricao, String efeito, byte quantidade){
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEfeito() {
        return this.efeito;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public byte getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(byte quantidade) {
        if (quantidade < 0) {
            this.quantidade = 0;
        } else {
            this.quantidade = quantidade;
        }
    }

    public void adicionarQuantidade(byte valor) {
        if (valor > 0) {
            this.quantidade += valor;
        }
    }

    public boolean usar() {
        if (this.quantidade > 0) {
            this.quantidade--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Item{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", efeito='" + efeito + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Item item = (Item) o;
        if (this.quantidade != item.quantidade) return false;
        if (this.nome != null ? !this.nome.equals(item.nome) : item.nome != null) return false;
        if (this.descricao != null ? !this.descricao.equals(item.descricao) : item.descricao != null) return false;
        return efeito != null ? efeito.equals(item.efeito) : item.efeito == null;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (efeito != null ? efeito.hashCode() : 0);
        result = 31 * result + Byte.hashCode(quantidade);
        return result;
    }

    @Override
    public int compareTo(Item other) {
        if (other == null) return 1;
        int cmp = compareNullable(this.nome, other.nome);
        if (cmp != 0) return cmp;
        cmp = Byte.compare(this.quantidade, other.quantidade);
        if (cmp != 0) return cmp;
        cmp = compareNullable(this.efeito, other.efeito);
        if (cmp != 0) return cmp;
        return compareNullable(this.descricao, other.descricao);
    }

    private static int compareNullable(String a, String b) {
        if (a == b) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }

}