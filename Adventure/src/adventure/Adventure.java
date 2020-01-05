/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Valentin
 */
public class Adventure {

    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
    private static ArrayList<Pair> pairs = new ArrayList<Pair>();
    private static int visited[];
    private static HashMap<String, Integer> pairsThatDisappear = new HashMap<String, Integer>(); 
    
    public static void main(String[] args) throws IOException {
        Scanner fileIn = new Scanner(new File("adventure.in"));
        int n = fileIn.nextInt();
        int m = fileIn.nextInt();
        int k = fileIn.nextInt();
        
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<Integer>());
        }
        
        for(int i=0; i<m; i++){
            int a = fileIn.nextInt();
            int b = fileIn.nextInt();
            graph.get(a).add(b);
            graph.get(b).add(a);
            pairs.add(new Pair(a, b));
        }
        
        for(int i=0; i<k; i++){
            int t = fileIn.nextInt();
            int v = fileIn.nextInt();
            Pair p = pairs.get(v-1);
            pairsThatDisappear.put(p.a + "," + p.b, t);
        }
        
        bfs(1, n);
        
        FileWriter fileWriter = new FileWriter("adventure.out");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        
        if(visited[n] != 0){
            printWriter.println(visited[n] - 1);
        } else{
            printWriter.println(-1);
        }
        printWriter.close();
    }
    
    private static void bfs(int s, int n) { 
        visited = new int[n+1]; 
  
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
  
        visited[s] = 1; 
        queue.add(s); 
  
        while (queue.size() != 0) 
        { 
            s = queue.poll(); 
            
            ArrayList<Integer> verticesAdjacentToV = graph.get(s);
            for(int i=0; i<verticesAdjacentToV.size(); i++){
                int x = verticesAdjacentToV.get(i); 
                if (visited[x] == 0) 
                { 
                    boolean disappeared = false;
                    boolean contains = false;
                    int t = 0;
                    if(pairsThatDisappear.containsKey(x + "," + s)) {
                        t = pairsThatDisappear.get(x + "," + s);
                        contains = true;
                    } else if(pairsThatDisappear.containsKey(s + "," + x)) {
                        t = pairsThatDisappear.get(s + "," + x);
                        contains = true;
                    }
                    if(contains && t <= visited[s] + 1){
                        disappeared = true;
                    }
                    if(!disappeared){
                        visited[x] = visited[s] + 1; 
                        queue.add(x);
                    }
                } 
            }
        } 
    } 
    
    private static class Pair {
        int a, b;
        
        public Pair(int a, int b){
            this.a = a;
            this.b = b;
        }
    }
}
