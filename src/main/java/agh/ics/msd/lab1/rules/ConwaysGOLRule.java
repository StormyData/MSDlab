package agh.ics.msd.lab1.rules;

public class ConwaysGOLRule extends BaseGOLRule {
    private static final int[] ALIVE = new int[]{2, 3};
    private static final int[] DEAD = new int[]{3};

    public ConwaysGOLRule() {
        super(ALIVE, DEAD);
    }
}
