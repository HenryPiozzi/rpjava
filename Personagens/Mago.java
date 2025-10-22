public class Mago extends Personagem implements Cloneable {

    public Mago(String nome) throws Exception {
        super(nome, (byte)20, (byte)5, (byte)5, (byte)1, new Inventario());
    }

    @Override
    public Object clone ()
    {
        Mago retorno=null;
        try
        {
            retorno = new Mago (this);
        }
        catch (Exception erro)
        {}
        return retorno;
    }

    public Mago(Mago modelo) throws Exception {
        super(modelo);
    }
}