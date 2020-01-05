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
        
        for(int i=1; i<=n; i++){
            printWriter.println(i + ": ");
            for(int j=0; j<graph.get(i).size(); j++)
                printWriter.println(graph.get(i).get(j));
        }
        
        printWriter.println();
        printWriter.close();
        
        boolean visited[] = new boolean[n+1];
        if(isCyclic(1, visited))
            System.out.print("Cycle detected!"); 
        else
            System.out.print("Compute max length"); 
    }
    
    private static boolean isCyclic(int v, boolean visited[]){
        
        ArrayList<Integer> verticesAdjacentToV = graph.get(v);
        
        for(int i=0; i<verticesAdjacentToV.size(); i++) {
            if(!visited[verticesAdjacentToV.get(i)])
                if(isCyclicUtil(verticesAdjacentToV.get(i), visited, -1))
                    return true;
        }
        return false;
    }
    
    private static boolean isCyclicUtil(int v, boolean visited[], int parent){
        visited[v] = true; 
        
        ArrayList<Integer> verticesAdjacentToV = graph.get(v);
        
        for(int i=0; i<verticesAdjacentToV.size(); i++) {
            if(!visited[verticesAdjacentToV.get(i)]){
                int vv = verticesAdjacentToV.get(i);
                if(isCyclicUtil(vv, visited, v))
                    return true;
            } else if (i != parent) 
                return true; 
        }
        
        return false;
    }
}
