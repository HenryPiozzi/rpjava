public class Mago extends Personagem implements Cloneable {

    public Mago(String nome) throws Exception {
        super(nome, (short)18, (short)6, (short)3, (byte)1, new Inventario());
    }
    // Método clone para a classe Mago
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
    // Chama o construtor da classe personagem.
    public Mago(Mago modelo) throws Exception {
        super(modelo);
    }
}