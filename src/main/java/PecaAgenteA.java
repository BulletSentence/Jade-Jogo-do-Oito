import jade.core.Agent;

public class PecaAgenteA extends Agent {
    public static void move(byte[] tabela, Controle.MOVIMENTOS paraMover){
        int vazio = Controle.getIndexVazio(tabela);
        if(vazio == -1) return;
        switch(paraMover){
            case UP:
                if(vazio/3 != 0) Controle.trocar(tabela, vazio, vazio-3);
                break;
            case DOWN:
                if(vazio/3 != 2) Controle.trocar(tabela, vazio, vazio+3);
                break;
            case RIGHT:
                if(vazio%3 != 2) Controle.trocar(tabela, vazio, vazio+1);
                break;
            case LEFT:
                if(vazio%3 != 0) Controle.trocar(tabela, vazio, vazio-1);
                break;
        }
    }

}
