package com.mymap.trafficpredict.model;

import java.util.ArrayList;

public class Path {
    ArrayList<Node> nodes = new ArrayList<>();

    @Override
    public String toString() {
        String s = "";
        for(Node n : nodes) {
            s += n + ", ";
        }
        return s;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }
}
