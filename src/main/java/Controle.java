import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import javax.swing.Timer;

public class Controle {
    
    public static enum MOVIMENTOS {UP, DOWN, RIGHT, LEFT};
    public static enum VELOCIDADE {SLOW, MEDIUM, FAST};
    private int timerSpeed = 500;
    public static final byte[] OBJETIVO = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    private byte[] atualmente = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    private boolean resolvendo = false;

    public boolean isResolvendo(){
        return this.resolvendo;
    }
    
    public byte[] getTabelaAtual(){
        return atualmente.clone();
    }
    
    public void setTabelaAtual(byte[] b){
        this.atualmente = b;
    }
    
    public void setTimerSpeed(VELOCIDADE velocidade){
        switch(velocidade){
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
        int vazio = getIndexVazio(atualmente);
        if(btn == vazio-1){
            move(atualmente, MOVIMENTOS.LEFT);
        }else if(btn == vazio+1){
            move(atualmente, MOVIMENTOS.RIGHT);
        }else if(btn == vazio+3){
            move(atualmente, MOVIMENTOS.DOWN);
        }else if(btn == vazio-3){
            move(atualmente, MOVIMENTOS.UP);
        }
    }
    
    // Realiza um movimento
    // se o movimento for vazio ele não faz nada
    public static void move(byte[] tabela, MOVIMENTOS paraMover){
        int vazio = getIndexVazio(tabela);
        if(vazio == -1) return;
        switch(paraMover){
            case UP:
                if(vazio/3 != 0) trocar(tabela, vazio, vazio-3);
                break;
            case DOWN:
                if(vazio/3 != 2) trocar(tabela, vazio, vazio+3);
                break;
            case RIGHT:
                if(vazio%3 != 2) trocar(tabela, vazio, vazio+1);
                break;
            case LEFT:
                if(vazio%3 != 0) trocar(tabela, vazio, vazio-1);
                break;
        }
    }
    
    public boolean isSolved(){
        return Arrays.equals(this.atualmente, this.OBJETIVO);
    }
    
    //reseta o quadro
    public void resetTabela(){
        for(int i = 0; i < atualmente.length-1 ; ++i) atualmente[i] = (byte)(i+1);
        atualmente[atualmente.length - 1] = 0;
    }
    
    // Gera a mesa aleatória mas que seja solucionável
    public void randomizeBoard(){
        byte board[];
        while(!isResolvivel(board = getTabelaRandomica()));
        atualmente = board;
    }
    
    // Gera movimentos aleatórios
    private byte[] getTabelaRandomica(){
        boolean f[] = new boolean[atualmente.length];
        byte tabela[] = new byte[atualmente.length];
        Random rand = new Random();

        for(int i = 0; i < atualmente.length ; ++i){
            byte t;
            while(f[t = (byte)rand.nextInt(9)]);
            f[t] = true;
            tabela[i] = t;
        }
        return tabela;
    }
    
    // Verifica se é solucionável
    private boolean isResolvivel(byte tabela[]){
        int inv = 0;
        for(int i = 0 ; i < tabela.length ; ++i){
            if(tabela[i] == 0) continue;
            for(int j = i+1 ; j < tabela.length ; ++j){
                if(tabela[j] != 0 && tabela[i] > tabela[j]) ++inv;
            }
        }
        
        // Se o numero de inversões for par é solucionavel
        return (inv % 2 == 0);
    }
    
    // Retorna o index do espaço em branco na tabela
    public static int getIndexVazio(byte[] board){
        for(int i = 0 ; i < board.length ; ++i) if(board[i] == 0) return i;
        return -1;
    }
    
    // Troca os elementos de lugar
    public static void trocar(byte[] tabela, int i, int j){
        try{
            byte iv = tabela[i];
            byte jv = tabela[j];
            tabela[i] = jv;
            tabela[j] = iv;
        }catch(ArrayIndexOutOfBoundsException ex){
        }
    }
    
    public void solve(final GUI gui, Resolver.SOLVE_METHOD method){
        
        Map<String, byte[]> parent = null;
        this.resolvendo = true;
        long time = System.nanoTime();
        switch(method){
            case A_STAR:
                parent = Resolver.aStar(getTabelaAtual().clone());
                break;
        }
        
        time = (System.nanoTime() - time) / 1000000;
        
        //Uso Backtracking para prever meu próximo movimento
        Stack<byte[]> proxTabela = new Stack<>();
        proxTabela.add(OBJETIVO.clone());
        while(!Arrays.equals(proxTabela.peek(), this.atualmente))
            proxTabela.add(parent.get(make(proxTabela.peek())));
        proxTabela.pop();

        String status = String.format("Quantidade de Movimentos: ", Resolver.times);
        gui.setStatus(status);
        
        // Inicia um timer
        new Timer(this.timerSpeed, new ActionListener(){
            private Stack<byte[]> tabela;
            public Controle bc;
            public ActionListener me(Stack<byte[]> stk, Controle _bc){
                this.tabela = stk;
                this.bc = _bc;
                return this;
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {

                //se a pilha estiver vazia eu paro o contador
                if(tabela.empty() || isSolved()){
                    Controle.this.resolvendo = false;
                    ((Timer)e.getSource()).stop();
                    return;
                }

                bc.setTabelaAtual(tabela.pop());
                gui.drawBoard();
            }
        }.me(proxTabela, this)).start();
    }

    private String make(byte[] arr){
        String str = "";
        for(int i = 0 ; i < arr.length ; ++i){
            str += String.valueOf(arr[i]);
        }
        return str;
    }
    
}
