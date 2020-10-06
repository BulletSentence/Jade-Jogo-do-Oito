import java.util.ArrayList;
import java.util.Arrays;

public class Estado implements Comparable<Estado>{
    
    private final byte board[];
    private final int cost;
    private final int weight;
    
    public Estado(byte b[], int _cost){
        this.board = b;
        cost = _cost;
        weight = cost + hurestic();
    }
    
    public byte[] getBoard(){
        return this.board;
    }
    public int getCost(){
        return this.cost;
    }
    
    // Calcula a heuristica
    private int hurestic(){
        int h = 0;
        for(int i = 0 ; i < board.length ; ++i){
            if(board[i] == 0) continue;
            int dr = Math.abs(i/3 - (board[i]-1)/3);
            int dc = Math.abs(i%3 - (board[i]-1)%3);
            h += dr + dc;
        }
        return h;
    }

    public ArrayList<Estado> getNextStates(){
        ArrayList<Estado> estados = new ArrayList<>();
        for(Controle.MOVIMENTOS move : Controle.MOVIMENTOS.values()){
            byte newBoard[] = this.board.clone();
            Controle.move(newBoard, move);
            if(!Arrays.equals(this.board, newBoard)){
                estados.add(new Estado(newBoard, this.cost + 1));
            }
        }
        return estados;
    }

    @Override
    public int compareTo(Estado o) {
        return this.weight - o.weight;
    }
    
}
