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
import java.util.Stack;

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

    static int visited[];
    static int max;

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
        
        int maxGlobal = 0;
        boolean isCycle = false;
        visited = new int[n+1];
        
        for(int i=1; i<=n; i++){
            if(isCyclic(i)){
                printWriter.println(cycleDetected); 
                isCycle = true;
                break;
            }
            else
            {
                if(maxGlobal < max+2){
                    maxGlobal = max+2;
                }
            }
        }
        if(!isCycle){
            printWriter.print(maxGlobal);
        }
        printWriter.close();

    }
    private static boolean isCyclic(int v) {
        
        ArrayList<Integer> verticesAdjacentToV = graph.get(v);
        
        for(int i=0; i<verticesAdjacentToV.size(); i++) {
            if(visited[verticesAdjacentToV.get(i)] == 0)
                if(isCyclicComputer(verticesAdjacentToV.get(i), -1))
                    return true;
        }
        return false;
    }
    
    private static boolean isCyclicComputer(int v, int parent){
        Stack<NodeAndParent> stack = new Stack<NodeAndParent>();
        stack.push(new NodeAndParent(v, parent));
        
        while (!stack.empty()) {
            NodeAndParent nodeAndParent = stack.peek();
            int node = nodeAndParent.node;
            stack.pop();
            parent = nodeAndParent.parent;
            
            if(parent == -1)
                visited[node] = 1; 
            else
                visited[node] = visited[parent] + 1;
            if(visited[node] > max){
                max = visited[node];
            }
            
            for(int i=0; i<graph.get(node).size(); i++) {
                if(visited[graph.get(node).get(i)] == 0){
                    int vv = graph.get(node).get(i);
                    NodeAndParent nodeAndParentToStack = new NodeAndParent(vv, node);
                    stack.push(nodeAndParentToStack);
               }
               else if (i != parent) 
                   return true; 
           }   
        }
        return false;
    }
    
    private static class NodeAndParent{
        private int node;
        private int parent;
        public NodeAndParent(int node, int parent){
            this.node = node;
            this.parent = parent;
        }

        public int getNode() {
            return node;
        }

        public void setNode(int node) {
            this.node = node;
        }

        public int getParent() {
            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }
    }
}
