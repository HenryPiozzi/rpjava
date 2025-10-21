public class Guerreiro extends Personagem  implements  Clonobale {

    public Guerreiro(String nome) throws Exception {
        super(nome, (byte)20, (byte)5, (byte)5, (byte)1, new Inventario());
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