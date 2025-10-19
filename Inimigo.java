public class Inimigo extends Personagem {

    public Inimigo(String nome, byte pontosVida, byte ataque, byte defesa, byte nivel, Inventario inventario) throws Exception {
        super(nome, pontosVida, ataque, defesa, nivel, inventario);
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