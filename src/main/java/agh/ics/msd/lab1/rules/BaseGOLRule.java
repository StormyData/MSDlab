package agh.ics.msd.lab1.rules;

import agh.ics.msd.lab1.Direction;
import agh.ics.msd.lab1.IRule;

import java.awt.*;
import java.util.EnumMap;


public class BaseGOLRule implements IRule {
    private static final Color[] colors = new Color[]{new Color(0.0f, 0.0f, 0.0f, 0.65f), Color.WHITE};
    private final int[] aliveStates = new int[9];
    private final int[] deadStates = new int[9];

    public BaseGOLRule(int[] alive, int[] dead) {
        setRules(alive, dead);
    }

    @Override
    public int getNextState(int current_state, EnumMap<Direction, Integer> neighborStates) {
        int nofAliveNeighbors = getNumOfAliveNeighbors(neighborStates);
        return current_state == 1 ? aliveStates[nofAliveNeighbors] : deadStates[nofAliveNeighbors];
    }

    @Override
    public Color getStateColor(int state) {
        return colors[state];
    }

    public void setRules(int[] alive, int[] dead) {
        for (int i = 0; i < 9; i++) {
            aliveStates[i] = 0;
            deadStates[i] = 0;
        }
        for (int n : alive) {
            if (n < 0 || n > 8)
                throw new IllegalArgumentException("rule has to be between 0 and 8");
            aliveStates[n] = 1;
        }
        for (int n : dead) {
            if (n < 0 || n > 8)
                throw new IllegalArgumentException("rule has to be between 0 and 8");
            deadStates[n] = 1;
        }
    }

    @Override
    public int getNofStates() {
        return 1;
    }

    private int getNumOfAliveNeighbors(EnumMap<Direction, Integer> neighborStates) {
        return (int) neighborStates.values().stream().filter(i -> i > 0).count();
    }
}
