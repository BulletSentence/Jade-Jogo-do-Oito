import java.util.ArrayList;
import java.util.Arrays;

public class State implements Comparable<State>{
    
    private final byte board[];
    private final int cost;
    private final int weight;
    
    public State(byte b[], int _cost){
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

    public ArrayList<State> getNextStates(){
        ArrayList<State> states = new ArrayList<>();
        for(BoardControl.MOVES move : BoardControl.MOVES.values()){
            byte newBoard[] = this.board.clone();
            BoardControl.move(newBoard, move);
            if(!Arrays.equals(this.board, newBoard)){
                states.add(new State(newBoard, this.cost + 1));
            }
        }
        return states;
    }

    @Override
    public int compareTo(State o) {
        return this.weight - o.weight;
    }
    
}
