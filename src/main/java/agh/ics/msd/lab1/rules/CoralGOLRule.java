package agh.ics.msd.lab1.rules;

public class CoralGOLRule extends BaseGOLRule {
    private static final int[] ALIVE = new int[]{4, 5, 6, 7, 8};
    private static final int[] DEAD = new int[]{3};

    public CoralGOLRule() {
        super(ALIVE, DEAD);
    }
}
