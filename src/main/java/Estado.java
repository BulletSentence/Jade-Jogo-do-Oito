import java.util.ArrayList;
import java.util.Arrays;

public class Estado implements Comparable<Estado>{
    
    private final byte tabela[];
    private final int custo;
    private final int peso;
    
    public Estado(byte b[], int _cost){
        this.tabela = b;
        custo = _cost;
        peso = custo + hurestic();
    }
    
    public byte[] getTabela(){
        return this.tabela;
    }
    public int getCusto(){
        return this.custo;
    }
    
    // Calcula a heuristica
    private int hurestic(){
        int h = 0;
        for(int i = 0; i < tabela.length ; ++i){
            if(tabela[i] == 0) continue;
            int dr = Math.abs(i/3 - (tabela[i]-1)/3);
            int dc = Math.abs(i%3 - (tabela[i]-1)%3);
            h += dr + dc;
        }
        return h;
    }

    public ArrayList<Estado> getNextStates(){
        ArrayList<Estado> estados = new ArrayList<>();
        for(Controle.MOVIMENTOS move : Controle.MOVIMENTOS.values()){
            byte newBoard[] = this.tabela.clone();
            Controle.move(newBoard, move);
            if(!Arrays.equals(this.tabela, newBoard)){
                estados.add(new Estado(newBoard, this.custo + 1));
            }
        }
        return estados;
    }

    @Override
    public int compareTo(Estado o) {
        return this.peso - o.peso;
    }
    
}
