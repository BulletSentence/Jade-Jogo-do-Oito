import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Resolver {
    
    public static enum RESOLVER_POR {ESTRELA};

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
            if(Arrays.equals(crnt.getTabela(), Controle.OBJETIVO)) break;
            for(Estado child : crnt.getNextStates()){
                if(dist.getOrDefault(stringify(child.getTabela()), Integer.MAX_VALUE) > child.getCusto()){
                    parent.put(stringify(child.getTabela()), crnt.getTabela());
                    dist.put(stringify(child.getTabela()), child.getCusto());
                    q.add(child);
                }
            }
        }
        
        return parent;
    }

    public static String stringify(byte[] arr){
        String str = "";
        for(int i = 0 ; i < arr.length ; ++i){
            str += String.valueOf(arr[i]);
        }
        return str;
    }
    
}
