import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    // Construtor de cópia
    public Inventario(Inventario modelo) {
        this.itens = new ArrayList<>();
        for (Item i : modelo.itens) {
            Item novoItem = new Item(i.getNome(), i.getDescricao(), i.getEfeito(), i.getPotencia(), i.getQuantidade());
            this.itens.add(novoItem);
        }
    }

    public void adicionarItem(Item item) {
        for (Item i : itens) {
            if (i.getNome().equals(item.getNome())) {
                i.setQuantidade((byte) (i.getQuantidade() + item.getQuantidade()));
                return;
            }
        }
        itens.add(item);
    }

    public String[] usarItem(String nome) {
        for (int idx = 0; idx < itens.size(); idx++) {
            Item i = itens.get(idx);
            if (i.getNome().equalsIgnoreCase(nome.trim())) {
                if (i.getQuantidade() > 0) {
                    return i.usar();
                } else {
                    System.out.println("Você não tem mais " + i.getNome());
                    return new String[]{"", "0"};
                }
            }
        }
        System.out.println("Item não encontrado no inventário.");
        return new String[]{"", "0"};
    }

    public void listarItens() {
        if (itens.isEmpty()) {
            System.out.println("Seu inventário está vazio.");
            return;
        }
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        INVENTÁRIO                             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        for (Item i : itens) {
            System.out.println(i.getNome() + " (x" + i.getQuantidade() + ")");
            System.out.println("     " + i.getDescricao());
            System.out.println();
        }
    }

    public List<Item> getItens() {
        return this.itens;
    }

    @Override
    public Inventario clone() {
        Inventario clone = new Inventario();
        for (Item i : this.itens) {
            Item novoItem = new Item(i.getNome(), i.getDescricao(), i.getEfeito(), i.getPotencia(), i.getQuantidade());
            clone.itens.add(novoItem);
        }
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        Inventario outro = (Inventario) obj;
        return this.itens.equals(outro.itens);
    }

    @Override
    public int hashCode() {
        return itens != null ? itens.hashCode() : 0;
    }
}