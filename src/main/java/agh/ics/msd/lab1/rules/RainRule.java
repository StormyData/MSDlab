package agh.ics.msd.lab1.rules;

import agh.ics.msd.lab1.Direction;
import agh.ics.msd.lab1.IRule;

import java.awt.*;
import java.util.EnumMap;
import java.util.Random;

public class RainRule implements IRule {

    private static final Random random = new Random();
    private static final int nofStates = 6;
    private static final Color[] colors = new Color[nofStates + 1];

    static {
        for (int i = 0; i <= nofStates; i++) {
            colors[i] = new Color(0.0f, 0.0f, 1.0f / nofStates * i, 0.65f);
        }
    }

    @Override
    public int getNextState(int currentState, EnumMap<Direction, Integer> neighborStates) {
        if (!neighborStates.containsKey(Direction.NORTH))
            if (random.nextInt(100) < 5)
                return nofStates;

        int nextState = currentState;
        if (nextState > 0)
            nextState -= 1;
        if (!neighborStates.containsKey(Direction.NORTH))
            return nextState;
        if (currentState == 0 && neighborStates.get(Direction.NORTH) > 0)
            nextState = nofStates;
        return nextState;
    }

    @Override
    public Color getStateColor(int state) {
        return colors[state];
    }

    @Override
    public int getNofStates() {
        return nofStates;
    }
}
