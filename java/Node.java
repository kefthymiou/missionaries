package com.missionaries;

import java.util.*;

/**
 * Representing a node of the possible states tree.
 */
public class Node {

    private State currentState = new State(3, 3, 1);
    private Node parent;
    private boolean toGoalBank = true;
    private State goal = new State(0, 0, 0);
    private List<State> possibleMoves = Arrays.asList(new State(1, 0, 1), new State(2, 0, 1), new State(0, 1, 1),
            new State(0, 2, 1), new State(1, 1, 1));

    public Node() {
    }

    public Node(boolean toGoalBank, State currentState, Node parent) {
        this.toGoalBank = toGoalBank;
        this.currentState = currentState;
        this.parent = parent;
    }

    /**
     * Copy constructor
     * @param node
     */
    public Node(Node node) {
        this.currentState = new State(node.getCurrentState());
        if (node.getParent() != null) {
            this.parent = new Node(node.getParent());
        }
        this.toGoalBank = node.isToGoalBank();
    }

    /**
     * Transition to the next node
     * @param move
     * @return
     */
    public Node transition(State move) {
        if (this.toGoalBank) {
            return new Node(!this.toGoalBank, currentState.moveToGoalBank(move) , this);
        } else {
            return new Node(!this.toGoalBank, currentState.moveToWrongBank(move) , this);
        }
    }

    public boolean isGoal() {
        return this.currentState.equals(goal);
    }

    /**
     * A Node is valid only when there are no more cannibals than missionaries on any side (unless there are no missionaries at all)
     * @return
     */
    public boolean isValid() {
        boolean valid = true;
        int missionaries = this.getCurrentState().getMissionaries();
        int cannibals = this.getCurrentState().getCannibals();

        //Negative values check
        if (!(missionaries >= 0 && cannibals >= 0 && (3 - missionaries >= 0) && (3 - cannibals >= 0))) {
            valid = false;
        }

        //Wrong side
        if (!(missionaries == 0 || missionaries >= cannibals)) {
            valid = false;
        }

        //Right side
        if (!((3 - missionaries == 0) || (3 - missionaries >= 3 - cannibals))) {
            valid = false;
        }

        return valid;
    }

    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();
        for (State move : this.possibleMoves) {
            Node possibleChild = this.transition(move);
            if (possibleChild.isValid()) {
                children.add(possibleChild);
            }
        }

        return children;
    }

    public Node findGoal() {
        Node goal = new Node();
        boolean goalFound = false;
        List<Node> checkedNodes = new ArrayList<>();
        checkedNodes.add(this);
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.addAll(this.getChildren());
        while(!nodeQueue.isEmpty() && !goalFound) {
            Node nodeToCheck = nodeQueue.remove();
            if (!checkedNodes.contains(nodeToCheck)) {
                checkedNodes.add(nodeToCheck);
                if (nodeToCheck.isGoal()) {
                    goal = new Node(nodeToCheck);
                    goalFound = true;
                } else {
                    for (Node childNode : nodeToCheck.getChildren()) {
                        nodeQueue.add(childNode);
                    }
                }
            } else {
                nodeQueue.remove(nodeToCheck);
            }
        }

        return goal;
    }

    public List<Node> getPathToRoot() {
        Node node = this;
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.getParent();
        }
        Collections.reverse(path);

        return path;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isToGoalBank() {
        return toGoalBank;
    }

    public void setToGoalBank(boolean toGoalBank) {
        this.toGoalBank = toGoalBank;
    }

    public State getGoal() {
        return goal;
    }

    public void setGoal(State goal) {
        this.goal = goal;
    }

    public List<State> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<State> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (!currentState.equals(node.currentState)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return currentState.hashCode();
    }
}
