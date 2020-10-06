import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Resolver {
    
    public static enum SOLVE_METHOD{A_STAR, DFS};
    private static final Map<String, byte[]> dfs_parent = new HashMap<>();
    private static final Set<String> dfs_vis = new HashSet<>();
    
    // Conta o numero de nós
    public static long times;
    
    // Resolve usando o A ESTRELA
    public static Map<String, byte[]> aStar(byte[] current){
        PriorityQueue<Estado> q = new PriorityQueue<>();
        Map<String, Integer> dist = new HashMap<>();
        Map<String, byte[]> parent = new HashMap<>();
        
        times = 0;

        dist.put(stringify(current), 0);
        q.add(new Estado(current, 0));

        while(!q.isEmpty()){
            Estado crnt = q.poll();
            times++;
            if(Arrays.equals(crnt.getBoard(), Controle.GOAL)) break;
            for(Estado child : crnt.getNextStates()){
                if(dist.getOrDefault(stringify(child.getBoard()), Integer.MAX_VALUE) > child.getCost()){                    
                    parent.put(stringify(child.getBoard()), crnt.getBoard());
                    dist.put(stringify(child.getBoard()), child.getCost());
                    q.add(child);
                }
            }
        }
        
        return parent;
    }
    
    //takes a byte array and returns it as a string for the map to hash
    //never hash arrays in java, they almost always return different hash values
    public static String stringify(byte[] arr){
        String str = "";
        for(int i = 0 ; i < arr.length ; ++i){
            str += String.valueOf(arr[i]);
        }
        return str;
    }
    
}
