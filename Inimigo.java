import java.util.concurrent.ThreadLocalRandom;

public class Inimigo extends Personagem implements Cloneable {

    public Inimigo(String nome, short pontosVida, short ataque, short defesa, byte nivel, Inventario inventario) throws Exception {
        super(nome, pontosVida, ataque, defesa, nivel, inventario);
    }

    // Método estático para gerar um inimigo aleatório baseado no capítulo atual
    public static Inimigo gerarInimigo(int capituloAtual, int inimigosDerrotados) {
        String[] nomesBase = {"Goblin", "Orc", "Esqueleto", "Lobo", "Bandido", "Mago Negro", "Troll", "Zumbi"};

        String nome = nomesBase[ThreadLocalRandom.current().nextInt(nomesBase.length)];

        int vidaBase = 10;
        int ataqueBase = 3;
        int defesaBase = 2;

        int pontosVida = vidaBase + (capituloAtual * ThreadLocalRandom.current().nextInt(5, 15));
        int ataque = ataqueBase + (capituloAtual * ThreadLocalRandom.current().nextInt(1, 4));
        int defesa = defesaBase + (capituloAtual * ThreadLocalRandom.current().nextInt(1, 3));
        byte nivel = (byte)inimigosDerrotados;

        try {
            return new Inimigo(nome, (short)pontosVida, (short)ataque, (short)defesa, nivel, new Inventario());
        } catch (Exception e) {
            System.out.println("Erro ao gerar inimigo: " + e.getMessage());
            try {
                return new Inimigo("Goblin", (short)15, (short)5, (short)2, (byte)1, new Inventario());
            } catch (Exception e2) {
                return null;
            }
        }
    }

    @Override
    public Object clone() {
        Inimigo retorno = null;
        try {
            retorno = new Inimigo(this);
        } catch (Exception erro) {
            System.out.println("Erro ao clonar inimigo: " + erro.getMessage());
        }
        return retorno;
    }

    public Inimigo(Inimigo modelo) throws Exception {
        super(modelo);
    }
}