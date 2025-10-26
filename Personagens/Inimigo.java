public class Inimigo extends Personagem implements  Cloneable {

    public Inimigo(String nome, short pontosVida, short ataque, short defesa, byte nivel, Inventario inventario) throws Exception {
        super(nome, pontosVida, ataque, defesa, nivel, inventario);
    }

    
    public static Inimigo gerarInimigo(int capituloAtual) {
        String[] nomesBase = {"Goblin", "Orc", "Esqueleto", "Lobo", "Bandido", "Mago Negro"};

        String nome = nomesBase[ThreadLocalRandom.current().nextInt(nomesBase.length)];

        int vidaBase = 5;
        int ataqueBase = 2;
        int defesaBase = 1;

        int pontosVida = vidaBase + (capituloAtual * ThreadLocalRandom.current().nextInt(10, 20));
        int ataque = ataqueBase + (capituloAtual * ThreadLocalRandom.current().nextInt(2, 5));
        int defesa = defesaBase + (capituloAtual * ThreadLocalRandom.current().nextInt(1, 3));
        int nivel = capituloAtual;

        return new Inimigo(nome, pontosVida, ataque, defesa, nivel);
    }

    @Override
    public Object clone ()
    {
        Inimigo retorno=null;
        try
        {
            retorno = new Inimigo (this);
        }
        catch (Exception erro)
        {}
        return retorno;
    }

    public Inimigo(Inimigo modelo) throws Exception {
        super(modelo);
    }
}