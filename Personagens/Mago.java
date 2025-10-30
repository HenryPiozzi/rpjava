public class Mago extends Personagem implements Cloneable {

    public Mago(String nome) throws Exception {
        super(nome, (byte)18, (byte)6, (byte)3, (byte)1, new Inventario());
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