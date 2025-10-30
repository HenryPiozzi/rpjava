package Personagem;

 class Arqueiro extends Personagem  implements Cloneable {

    public Arqueiro(String nome) throws Exception {
        super(nome, (short)20, (short)4, (short)4, (byte)1, new Inventario());
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