import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import javax.swing.Timer;

public class BoardControl{
    
    public static enum MOVES{UP, DOWN, RIGHT, LEFT};
    public static enum SPEED{SLOW, MEDIUM, FAST};
    private int timerSpeed = 500;
    public static final byte[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    private byte[] current = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    private boolean solving = false;
    
    public boolean isSolving(){
        return this.solving;
    }
    
    public byte[] getCurrentBoard(){
        return current.clone();
    }
    
    public void setCurrentBoard(byte[] b){
        this.current = b;
    }
    
    public void setTimerSpeed(SPEED speed){
        switch(speed){
            case SLOW:
                this.timerSpeed = 700;
                break;
            case MEDIUM:
                this.timerSpeed = 300;
                break;
            case FAST:
                this.timerSpeed = 100;
                break;
        }
    }
    
    // Controla as tabelas
    // Verifica a posição do quadrado relativo ao espaço vazio
    public void tilePressed(int btn){
        int blank = getBlankIndex(current);
        if(btn == blank-1){
            move(current, MOVES.LEFT);
        }else if(btn == blank+1){
            move(current, MOVES.RIGHT);
        }else if(btn == blank+3){
            move(current, MOVES.DOWN);
        }else if(btn == blank-3){
            move(current, MOVES.UP);
        }
    }
    
    // Realiza um movimento
    // se o movimento for vazio ele não faz nada
    public static void move(byte[] board, MOVES toMove){
        int blank = getBlankIndex(board);
        if(blank == -1) return;
        switch(toMove){
            case UP:
                if(blank/3 != 0) swap(board, blank, blank-3);
                break;
            case DOWN:
                if(blank/3 != 2) swap(board, blank, blank+3);
                break;
            case RIGHT:
                if(blank%3 != 2) swap(board, blank, blank+1);
                break;
            case LEFT:
                if(blank%3 != 0) swap(board, blank, blank-1);
                break;
        }
    }
    
    public boolean isSolved(){
        return Arrays.equals(this.current, this.GOAL);
    }
    
    //reseta o quadro
    public void resetBoard(){
        for(int i = 0 ; i < current.length-1 ; ++i) current[i] = (byte)(i+1);
        current[current.length - 1] = 0;
    }
    
    // Gera a mesa aleatória mas que seja solucionável
    public void randomizeBoard(){
        byte board[];
        while(!isSolvable(board = getRandomBoard()));
        current = board;
    }
    
    // Gera movimentos aleatórios
    private byte[] getRandomBoard(){
        boolean f[] = new boolean[current.length];
        byte board[] = new byte[current.length];
        Random rand = new Random();

        for(int i = 0 ; i < current.length ; ++i){
            byte t;
            while(f[t = (byte)rand.nextInt(9)]);
            f[t] = true;
            board[i] = t;
        }
        return board;
    }
    
    // Verifica se é solucionável
    private boolean isSolvable(byte board[]){
        int inv = 0;
        for(int i = 0 ; i < board.length ; ++i){
            if(board[i] == 0) continue;
            for(int j = i+1 ; j < board.length ; ++j){
                if(board[j] != 0 && board[i] > board[j]) ++inv;
            }
        }
        
        // Se o numero de inversões for par é solucionavel
        return (inv % 2 == 0);
    }
    
    // Retorna o index do espaço em branco na tabela
    public static int getBlankIndex(byte[] board){
        for(int i = 0 ; i < board.length ; ++i) if(board[i] == 0) return i;
        return -1;
    }
    
    // Troca os elementos de lugar
    public static void swap(byte[] board, int i, int j){
        try{
            byte iv = board[i];
            byte jv = board[j];
            board[i] = jv;
            board[j] = iv;
        }catch(ArrayIndexOutOfBoundsException ex){
        }
    }
    
    // Debug
    public static void print(byte[] b){
        for(int i = 0 ; i < b.length ; ++i)
                System.out.print(b[i] + " ");
            System.out.println("");
    }
    
    public void solve(final GUI gui, Solvers.SOLVE_METHOD method){
        
        Map<String, byte[]> parent = null;
        
        this.solving = true;
        
        long time = System.nanoTime();
        switch(method){
            case A_STAR:
                parent = Solvers.aStar(getCurrentBoard().clone());
                break;
            case DFS:
                parent = Solvers.dfs(getCurrentBoard().clone());
                break;
        }
        
        time = (System.nanoTime() - time) / 1000000;
        
        //Uso Backtracking para prever meu próximo movimento
        Stack<byte[]> nextBoard = new Stack<>();
        nextBoard.add(GOAL.clone());
        while(!Arrays.equals(nextBoard.peek(), this.current))
            nextBoard.add(parent.get(make(nextBoard.peek())));        
        nextBoard.pop();
        
        String status = String.format("<html>%d ms<br/>%d moves<br/>%d expanded nodes</html>", time, nextBoard.size(), Solvers.times);
        gui.setStatus(status);
        
        // Inicia um timer
        new Timer(this.timerSpeed, new ActionListener(){
            private Stack<byte[]> boards;
            public BoardControl bc;
            public ActionListener me(Stack<byte[]> stk, BoardControl _bc){
                this.boards = stk;
                this.bc = _bc;
                return this;
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //se a pilha estiver vazia eu paro o contador
                if(boards.empty() || isSolved()){
                    BoardControl.this.solving = false;
                    ((Timer)e.getSource()).stop();
                    return;
                }

                bc.setCurrentBoard(boards.pop());
                gui.drawBoard();
            }
        }.me(nextBoard, this)).start();
    }

    private String make(byte[] arr){
        String str = "";
        for(int i = 0 ; i < arr.length ; ++i){
            str += String.valueOf(arr[i]);
        }
        return str;
    }
    
}
