package com.missionaries;

/**
 * Representing a Node's state as snapshot of the wrong bank (Missionaries, cannibals, boat position)
 * Also used for the representation of movements
 */
public class State {

    private int missionaries;
    private int cannibals;
    private int boat;

    public State(int missionaries, int cannibals, int boat) {
        this.missionaries = missionaries;
        this.cannibals = cannibals;
        this.boat = boat;
    }

    public State() {
    }

    /**
     * Copy constructor
     * @param state
     */
    public State(State state) {
        this.missionaries = state.getMissionaries();
        this.cannibals = state.getCannibals();
        this.boat = state.getBoat();
    }

    /**
     * Moves missionaries and cannibals to the goal bank (subtraction from the wrong bank)
     * @param move
     * @return
     */
    public State moveToGoalBank(State move) {
        State newState = new State();
        newState.setMissionaries(this.missionaries - move.getMissionaries());
        newState.setCannibals(this.cannibals - move.getCannibals());
        newState.setBoat(this.boat - move.getBoat());

        return newState;
    }

    /**
     * Moves missionaries and cannibals to the wrong bank (addition to the wrong bank)
     * @param move
     * @return
     */
    public State moveToWrongBank(State move) {
        State newState = new State();
        newState.setMissionaries(this.missionaries + move.getMissionaries());
        newState.setCannibals(this.cannibals + move.getCannibals());
        newState.setBoat(this.boat + move.getBoat());

        return newState;
    }

    public int getMissionaries() {
        return missionaries;
    }

    public void setMissionaries(int missionaries) {
        this.missionaries = missionaries;
    }

    public int getCannibals() {
        return cannibals;
    }

    public void setCannibals(int cannibals) {
        this.cannibals = cannibals;
    }

    public int getBoat() {
        return boat;
    }

    public void setBoat(int boat) {
        this.boat = boat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (boat != state.boat) return false;
        if (cannibals != state.cannibals) return false;
        if (missionaries != state.missionaries) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = missionaries;
        result = 31 * result + cannibals;
        result = 31 * result + boat;
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "missionaries: " + missionaries +
                ", cannibals: " + cannibals +
                ", boat: " + (boat == 1 ? "Wrong bank" : "Right bank") +
                '}';
    }
}
