package agh.ics.msd.lab1.rules;

public class CitiesGOLRule extends BaseGOLRule {
    private static final int[] ALIVE = new int[]{2, 3, 4, 5};
    private static final int[] DEAD = new int[]{4, 5, 6, 7, 8};

    public CitiesGOLRule() {
        super(ALIVE, DEAD);
    }
}
