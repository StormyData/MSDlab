package agh.ics.msd.lab1;

import agh.ics.msd.lab1.rules.CitiesGOLRule;
import agh.ics.msd.lab1.rules.ConwaysGOLRule;
import agh.ics.msd.lab1.rules.CoralGOLRule;
import agh.ics.msd.lab1.rules.RainRule;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Class containing GUI: board + buttons
 */
public class GUI extends JPanel implements ActionListener, ChangeListener {
    private static final Map<String, IRule> ruleMap = Map.ofEntries(
            Map.entry("Rain", new RainRule()),
            Map.entry("Conway", new ConwaysGOLRule()),
            Map.entry("Coral", new CoralGOLRule()),
            Map.entry("Cities", new CitiesGOLRule())
    );

    private static final long serialVersionUID = 1L;
    private final int maxDelay = 500;
    private final int initDelay = 100;
    private Timer timer;
    private Board board;
    private JButton start;
    private JButton clear;
    private JSlider pred;
    private JFrame frame;
    private int iterNum = 0;
    private boolean running = false;
    private JList<String> ruleList;

    public GUI(JFrame jf) {
        frame = jf;
        timer = new Timer(initDelay, this);
        timer.stop();
    }

    /**
     * @param container to which GUI and board is added
     */
    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(1024, 768));

        JPanel buttonPanel = new JPanel();

        DefaultListModel<String> ruleModel = new DefaultListModel<>();
        ruleMap.keySet().forEach(ruleModel::addElement);
        ruleList = new JList<>(ruleModel);
        ruleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ruleList.setVisibleRowCount(1);

        JButton ruleChange = new JButton("Set rule");
        ruleChange.setActionCommand("Set rule");
        ruleChange.addActionListener(this);
        start = new JButton("Start");
        start.setActionCommand("Start");
        start.setToolTipText("Starts clock");
        start.addActionListener(this);

        clear = new JButton("Clear");
        clear.setActionCommand("clear");
        clear.setToolTipText("Clears the board");
        clear.addActionListener(this);

        pred = new JSlider();
        pred.setMinimum(0);
        pred.setMaximum(maxDelay);
        pred.setToolTipText("Time speed");
        pred.addChangeListener(this);
        pred.setValue(maxDelay - timer.getDelay());

        buttonPanel.add(ruleList);
        buttonPanel.add(new JScrollPane(ruleChange));
        buttonPanel.add(start);
        buttonPanel.add(clear);
        buttonPanel.add(pred);

        board = new Board(1024, 768 - buttonPanel.getHeight(), new ConwaysGOLRule());
        container.add(board, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * handles clicking on each button
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) {
            iterNum++;
            frame.setTitle("Game of Life (" + Integer.toString(iterNum) + " iteration)");
            board.iteration();
        } else {
            String command = e.getActionCommand();
            switch (command) {
                case "Start":
                    if (!running) {
                        timer.start();
                        start.setText("Pause");
                    } else {
                        timer.stop();
                        start.setText("Start");
                    }
                    running = !running;
                    clear.setEnabled(true);
                    break;
                case "Set rule":
                    board.setRule(ruleMap.get(ruleList.getSelectedValue()));
                    // fallthrough expected
                case "clear":
                    iterNum = 0;
                    timer.stop();
                    start.setEnabled(true);
                    board.clear();
                    frame.setTitle("Cellular Automata Toolbox");
                    break;

            }
        }
    }

    /**
     * slider to control simulation speed
     *
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        timer.setDelay(maxDelay - pred.getValue());
    }
}
