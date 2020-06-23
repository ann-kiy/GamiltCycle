package com.company;

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
		Graph g=new Graph(1000);
		g.outInFile("graphData.txt");
		Graph g1=g.getIsomorphicGraph();
		g1.outInFile("graphIsoData.txt");

		System.out.println("Действительно ли граф H изоморфен G?  "+Graph.chekTransposition(g.getGraph(),g1.getGraph(),g1.getReversal()));
        System.out.println("Верен ли гамильтонов цикл для графа H?  "+Graph.checkCycle(g1.getGraph(),g1.getCycle()));


    }
}
