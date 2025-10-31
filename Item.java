import java.util.concurrent.ThreadLocalRandom;

public class Item implements Comparable<Item> {
    private String nome;
    private String descricao;
    private String efeito;
    private String potencia;
    private byte quantidade;

    //criação de variaveis estáticas para a geração de itens aleatorios no jogo
    private static final Item[] ITEM_COMUM = {
        new Item("Pocao de Vida pequena", "Recupera 30% de HP", "Cura", "30", (byte) 1),
        new Item("Pocao de Ataque", "Causa X de dano a mais no proximo ataque", "Dano bônus", "5", (byte) 1),
    };

    private static final Item[] ITEM_RARO = {
        new Item("Pocao Media de Vida", "Recupera 50% de HP", "Cura", "50", (byte) 1),
        new Item("Amuleto Raro", "Aumenta 10% da defesa", "Defesa bônus", "3", (byte) 1),
        new Item("Elixir da Furia", "Aumenta drasticamente o ataque no próximo turno", "Dano bônus", "8", (byte) 1)
    };

    private static final Item[] ITEM_EPICO = {
        new Item("Essencia do Berserker", "Aumenta ataque e defesa simultaneamente por 2 turnos", "Duplo bônus", "5",(byte) 1),
        new Item("Pocao Grande de Vida", "Recupera 75% de HP", "Cura", "75", (byte) 1)
    };

    private static final Item[] ITEM_LENDARIO = {
        new Item("Excalibur", "A lendária espada sagrada", "Dano bônus", "15", (byte) 1),
        new Item("Elixir dos Deuses", "Recupera todo HP", "Cura", "100", (byte) 1),
        new Item("Capa da Invisibilidade", "Não recebe o próximo ataque", "Defesa bônus", "999", (byte) 1)
    };

    // Método estático para gerar um item aleatório com base no nível fornecido
    public static Item itemAleatorio(int nivelDoItem) {
        switch (nivelDoItem) {
            case 0:
                return new Item(ITEM_COMUM[ThreadLocalRandom.current().nextInt(ITEM_COMUM.length)]);
            case 1:
                return new Item(ITEM_RARO[ThreadLocalRandom.current().nextInt(ITEM_RARO.length)]);
            case 2:
                return new Item(ITEM_EPICO[ThreadLocalRandom.current().nextInt(ITEM_EPICO.length)]);
            case 3:
                return new Item(ITEM_LENDARIO[ThreadLocalRandom.current().nextInt(ITEM_LENDARIO.length)]);
            default:
                return new Item(ITEM_COMUM[0]);
        }
    }

    // Construtor padrão
    public Item(String nome, String descricao, String efeito, String potencia, byte quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.potencia = potencia;
        this.quantidade = quantidade;
    }

    // Construtor de cópia
    public Item(Item modelo) {
        this.nome = modelo.nome;
        this.descricao = modelo.descricao;
        this.efeito = modelo.efeito;
        this.potencia = modelo.potencia;
        this.quantidade = modelo.quantidade;
    }

    // Getters e Setters
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

    public String getPotencia() {
        return this.potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
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

    // Método para adicionar quantidade ao item
    public void adicionarQuantidade(byte valor) {
        if (valor > 0) {
            this.quantidade += valor;
        }
    }

    // Método para usar o item, retornando o efeito e a potência do mesmo e decrementando a quantidade se a quantidade for maior que zero 
    public String[] usar() {
        if (this.quantidade > 0) {
            this.quantidade--;
            return new String[]{this.efeito, this.potencia};
        }
        return new String[]{"", "0"};
    }

    //toString, equals, hashCode e compareTo
    @Override
    public String toString() {
        return "Item{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", efeito='" + efeito + '\'' +
                ", potencia='" + potencia + '\'' +
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
        if (this.potencia != null ? !this.potencia.equals(item.potencia) : item.potencia != null) return false;
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
        result = 31 * result + (potencia != null ? potencia.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Item other) {
        if (other == null) return 1;
        int cmp = compareNullable(this.nome, other.nome);
        if (cmp != 0) return cmp;
        cmp = Byte.compare(this.quantidade, other.quantidade);
        if (cmp != 0) return cmp;
        cmp = compareNullable(this.potencia, other.potencia);
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