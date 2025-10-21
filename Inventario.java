import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
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

    public boolean usarItem(String nome) {
        for (int idx = 0; idx < itens.size(); idx++) {
            Item i = itens.get(idx);
            if (i.getNome().equalsIgnoreCase(nome.trim())) {
                if (i.getQuantidade() > 0) {
                    i.setQuantidade((byte) (i.getQuantidade() - 1));
                    System.out.println("Você usou: " + i.getNome() + " | Restante: " + i.getQuantidade());
                    if (i.getQuantidade() <= 0) {
                        itens.remove(idx);
                    }
                    return true;
                } else {
                    System.out.println("Você não tem mais " + i.getNome());
                    return false;
                }
            }
        }
        System.out.println("Item não encontrado no inventário.");
        return false;
    }

    public void listarItens() {
        if (itens.isEmpty()) {
            System.out.println("Seu inventário está vazio.");
            return;
        }
        System.out.println("Itens no inventário:");
        for (Item i : itens) {
            System.out.println("- " + i.getNome() + " (x" + i.getQuantidade() + ") — " + i.getDescricao());
        }
    }

    @Override
    public Inventario clone() {
        Inventario clone = new Inventario();
        for (Item i : this.itens) {
            Item novoItem = new Item(i.getNome(), i.getDescricao(), i.getEfeito(), i.getQuantidade());
            clone.itens.add(novoItem);
        }
        return clone;
    }
}
