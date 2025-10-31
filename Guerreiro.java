public class Guerreiro extends Personagem  implements  Cloneable {

    public Guerreiro(String nome) throws Exception {
        super(nome, (short)23, (short)2, (short)5, (byte)1, new Inventario());
    }
    // Método clone para a classe Guerreiro
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
    // Chama o construtor da classe personagem.
    public Guerreiro(Guerreiro modelo) throws Exception {
        super(modelo);
    }
}