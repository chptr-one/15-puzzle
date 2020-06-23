package puzzle.gui;

import puzzle.Game;

import javax.swing.*;

public class ControlPanel extends JPanel {
    private Game game;

    private JButton shuffleJButton;
    private JButton solveJButton;
    private JRadioButton dim3x3;
    private JRadioButton dim4x4;
    private JComboBox<String> algList;

    public ControlPanel(Game game) {
        this.game = game;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        dim3x3 = new JRadioButton("3 x 3");
        dim3x3.addActionListener(e -> game.setDimension(3));
        dim4x4 = new JRadioButton("4 x 4", true);
        dim4x4.addActionListener(e -> game.setDimension(4));
        ButtonGroup dimButtonsGroup = new ButtonGroup();
        dimButtonsGroup.add(dim3x3);
        dimButtonsGroup.add(dim4x4);
        JPanel dimPanel = new JPanel();
        dimPanel.add(new JLabel("Board size :"));
        dimPanel.add(dim3x3);
        dimPanel.add(dim4x4);
        add(dimPanel);

        JComboBox<String> algList = new JComboBox<>();
        algList.addItem("A* search");
        algList.addItem("IDA* (Iterative deepening A*)");
        algList.setSelectedIndex(1);
        add(algList);

        shuffleJButton = new JButton("Shuffle");
        shuffleJButton.addActionListener(e -> game.shuffle());
        solveJButton = new JButton("Solve");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(shuffleJButton);
        buttonPanel.add(solveJButton);
        add(buttonPanel);
    }
}
