public class Arqueiro extends Personagem  implements Clonobale {

    public Arqueiro(String nome) throws Exception {
        super(nome, (byte)20, (byte)5, (byte)5, (byte)1, new Inventario());
    }

    @Override
    public Object clone ()
    {
        Arqueiro retorno=null;
        try
        {
            retorno = new Arqueiro (this);
        }
        catch (Exception erro)
        {}
        return retorno;
    }

    public Arqueiro(Arqueiro modelo) throws Exception {
        super(modelo);
    }
}