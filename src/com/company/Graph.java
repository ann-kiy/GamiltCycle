package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class Graph {
    private int size;
    private List<List<Integer>> massEdge;
    private List<Integer> vertex;
    private List<Integer> reversal=null;

    public int getSize() {
        return size;
    }

    public List<Integer> getCycle() {
        return vertex;
    }

    public List<Integer> getReversal() {
        return reversal;
    }
    public List<List<Integer>> getGraph() {
        return massEdge;
    }
    public  Graph(List<List<Integer>> graph, int size,List<Integer> cicle){
        this.massEdge=graph;
        this.vertex=cicle;
        this.size=size;
    }
    public  Graph(List<List<Integer>> graph, int size, List<Integer> cicle, List<Integer> reversal){
        this.massEdge=graph;
        this.vertex=cicle;
        this.reversal=reversal;
        this.size=size;
    }
    public Graph(String nameF) throws IOException {
        List<String> data=Files.readAllLines(Paths.get(nameF));
        massEdge=new ArrayList<>();
        vertex=new ArrayList<>();
      //  System.out.println(chekFile(data)+"???");
        size= Integer.parseInt(data.get(0).split(" ")[0]);
        for (int i=1;i<=Integer.parseInt(data.get(0).split(" ")[1]);i++)
        {
            String s=data.get(i).replace(" ","");
            s=s.substring(1,s.length()-1);
            List<Integer> temp=new ArrayList<>();
            temp.add(Integer.valueOf(s.split(",")[0]));
            temp.add(Integer.valueOf(s.split(",")[1]));
            massEdge.add(temp);
        }
        String[] ver=data.get(massEdge.size()+1).replaceAll(" ","").replaceAll("\\[","").replaceAll("]","").split(",");
        for(int i=0;i<size;i++){
            vertex.add(Integer.parseInt(ver[i]));
        }
    }
    public Graph(int size){
        massEdge=new ArrayList<>();
        this.size=size;
      //  graph= new ArrayList<>(size);
       vertex=new ArrayList<>(size);
       for (int i=0;i<size;i++) {
            vertex.add(i);
       }
        Collections.shuffle(vertex);
        for (int i=1;i<(size);i++)
        {
            List<Integer> temp=new ArrayList<>();
            temp.add(vertex.get(i));
            temp.add(vertex.get(i-1));
            massEdge.add(temp);
        }
        List<Integer> temp=new ArrayList<>();
        temp.add(vertex.get(0));
        temp.add(vertex.get(size-1));
        massEdge.add(temp);
        int k=0,cnt= ThreadLocalRandom.current().nextInt(2*size);
      //  System.out.println(cnt);
        //Добавление дополнительных ребер, помимо гамильтонова цикла
        while(cnt!=k)
        {
            //System.out.println(cnt);
            int rnd1 = ThreadLocalRandom.current().nextInt(size),rnd2 = ThreadLocalRandom.current().nextInt(size);
            if (rnd1==rnd2)
                continue;
            List<Integer> temp1=new ArrayList<>();
            temp1.add(vertex.get(rnd1));
            temp1.add(vertex.get(rnd2));

                massEdge.add(temp1);
               // System.out.println(temp1+"@");
                k++;

        }
    }
    void show(){
        massEdge.forEach(n-> System.out.println(n.toString()));
    }
    int getCountEdgs(){
        return massEdge.size();
    }
    Graph getIsomorphicGraph(){
        List<List<Integer>> temp=new ArrayList<>();
        List<Integer> v=new ArrayList<>(size);
        List<Integer> cicle=new ArrayList<>();
        for (int i=0;i<size;i++) {
            v.add(i);
        }
        Collections.shuffle(v);
        for(List<Integer> list:massEdge){
            List<Integer> t=new ArrayList<>();
            t.add(v.get(list.get(0)));
            t.add(v.get(list.get(1)));
            temp.add(t);
        }
        for (int i:vertex) {
            cicle.add(v.get(i));
        }

    return new Graph(temp,size,cicle, v);
    }
    List<Integer> getGamiltonovСycle(){
        return vertex;
    }
    void outInFile(String nameF) throws IOException {
        String data=this.size+" "+getCountEdgs()+"\n";
        for (List<Integer> l:massEdge)
            data+=l.toString()+"\n";
        data +=vertex.toString();
        Files.write(Paths.get(nameF),data.getBytes() , StandardOpenOption.CREATE);
    }
    static  boolean containsEdge(List<List<Integer>> massEdge, List<Integer> b){
        for (List<Integer> a:massEdge) {
            if (a.size() != 2 || b.size() != 2)
                return false;
            if (b.contains(a.get(0)) && b.contains(a.get(1)))
                return true;
        }
        return false;
    }
    static boolean checkCycle(List<List<Integer>> massEdge,List<Integer> cycle){
        int[] mass = new int[cycle.size()];
        for(int i=0;i<(cycle.size()-2);i++){
            List<Integer> temp=new ArrayList<>();
            temp.add(cycle.get(i));
            temp.add(cycle.get(i+1));
            if(!containsEdge(massEdge,temp))
                return false;
        }
        for(int l:cycle){
            mass[massEdge.get(l).get(0)]++;
            mass[massEdge.get(l).get(1)]++;
        }
        int k=0;
        for(int i:mass){
            //System.out.println("i"+k+++"= "+i);
            if(i!=2)
                return false;
        }
        return true;
    }
    private static boolean chekFile(List<String> data){
//        System.out.println("(\\d)+ (\\d)+\n" +
//                "(\\[(\\d)+, (\\d)+\\]\n)\\{"+data.get(0).split(" ")[1]+"\\}\\[(\\d)+, (\\d)+\\]\n" +
//                "\\[(\\d, )*\\d\\]");
        return Pattern.matches("(\\d)+ (\\d)+\\n" +
                "(\\[(\\d)+, (\\d)+\\]\\n)\\{"+data.get(0).split(" ")[1]+"\\}\\[(\\d)+, (\\d)+\\]\\n" +
                "\\[(\\d, )*\\d\\]",String.valueOf(data));
      //  return Pattern.matches("(\\d)+ (\\d)+\n(\\[(\\d)+, (\\d)+\\])\n{"+data.get(0).split(" ")[1]+"}(\\[(\\d)+ ,(\\d)+\\])\n" +
        //        "\\[(\\d, )*\\d\\]",String.valueOf(data));
        //return Pattern.matches("(\\d)+ (\\d)+",data.get(0));
    }
    static boolean chekTransposition(List<List<Integer>> massEdge,List<List<Integer>> massEdge1,List<Integer> newV){
        List<List<Integer>> temp=new ArrayList<>();
        for(List<Integer> list:massEdge){
            List<Integer> t=new ArrayList<>();
            t.add(newV.get(list.get(0)));
            t.add(newV.get(list.get(1)));
            temp.add(t);
        }
        return temp.equals(massEdge1);
    }
}
