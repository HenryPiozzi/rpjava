public class Guerreiro extends Personagem  implements  Cloneable {

    public Guerreiro(String nome) throws Exception {
        super(nome, (short)20, (short)5, (short)5, (byte)1, new Inventario());
    }

    @Override
    public Object clone ()
    {
        Guerreiro retorno=null;
        try
        {
            retorno = new Guerreiro (this);
        }
        catch (Exception erro)
        {}
        return retorno;
    }

    public Guerreiro(Guerreiro modelo) throws Exception {
        super(modelo);
    }
}