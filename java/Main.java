package com.missionaries;

public class Main {

    public static void main(String[] args) {
        Node node = new Node(true, new State(3, 3, 1), null);
        Node goal = new Node(node.findGoal());
        for (Node pathNode : goal.getPathToRoot()) {
            System.out.println(pathNode.getCurrentState().toString());
        }
    }
}
