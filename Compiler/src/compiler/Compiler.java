/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Valentin
 */
public class Compiler {
    private static String defaultClass = "Object";
    private static String cycleDetected = "Cycle detected!";
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
    private static HashMap<String, Integer> classNames = new HashMap<String, Integer>();
    private static Integer n;

    static int visited[] ;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner fileIn = new Scanner(new File("compiler.in"));
        n = fileIn.nextInt();
        classNames.put(defaultClass, 0);
        graph.add(new ArrayList<Integer>());
        
        for(int i=1; i<=n; i++){
            graph.add(new ArrayList<Integer>());
            String className = fileIn.next();
            classNames.put(className, i);
        }
        
        for(int i=1; i<=n; i++) {
            String className = fileIn.next();
            graph.get(classNames.get(className)).add(i);
        }
        
        FileWriter fileWriter = new FileWriter("compiler.out");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        
        visited = new int[n+1];
        if(isCyclic(1))
            printWriter.println(cycleDetected); 
        else
        {
            int max = 0;
            for(int i=1; i<n+1; i++){
                if(max<visited[i])
                    max = visited[i];
            }
            printWriter.println(max+2);
        }
        printWriter.close();
    }
    private static boolean isCyclic(int v) {
        
        ArrayList<Integer> verticesAdjacentToV = graph.get(v);
        
        for(int i=0; i<verticesAdjacentToV.size(); i++) {
            if(visited[verticesAdjacentToV.get(i)] == 0)
                if(isCyclicUtil(verticesAdjacentToV.get(i), -1))
                    return true;
        }
        return false;
    }
    
    private static boolean isCyclicUtil(int v, int parent){
        if(parent == -1)
            visited[v] = 1; 
        else
            visited[v] = visited[parent] + 1;
        
        for(int i=0; i<graph.get(v).size(); i++) {
            if(visited[graph.get(v).get(i)] == 0){
                int vv = graph.get(v).get(i);
                if(isCyclicUtil(vv, v))
                    return true;
            } else if (i != parent) 
                return true; 
        }
        
        return false;
    }
}
