package agh.ics.msd.lab1;

import java.util.EnumMap;

public class Point {

    private IRule rule;
    private EnumMap<Direction, Point> neighbors = new EnumMap<>(Direction.class);
    private EnumMap<Direction, Integer> neighborState = new EnumMap<>(Direction.class);
    private int currentState = 0;
    private int nextState = 0;

    public Point(IRule rule) {
        this.rule = rule;
    }

    public void clicked() {
        currentState = (++currentState) % rule.getNofStates();
    }

    public int getState() {
        return currentState;
    }

    public void setState(int s) {
        currentState = s;
    }

    public void calculateNewState() {
        neighborState.replaceAll((Direction dir, Integer old) -> neighbors.get(dir).currentState);
        nextState = rule.getNextState(currentState, neighborState);
    }

    public void changeState() {
        currentState = nextState;
    }

    public void setNeighbor(Direction dir, Point nei) {
        neighbors.put(dir, nei);
        neighborState.put(dir, nei.currentState);
    }

    public void setRule(IRule rule) {
        this.rule = rule;
        currentState = 0;
        nextState = 0;
    }
}
