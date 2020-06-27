package puzzle.gui;

import puzzle.Game;
import puzzle.search.SearchAlgorithm;

import javax.swing.*;
import java.util.Map;

class ControlPanel extends JPanel {
    ControlPanel(Game game) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JRadioButton dim3x3 = new JRadioButton("3 x 3");
        dim3x3.addActionListener(e -> game.setDimension(3));
        JRadioButton dim4x4 = new JRadioButton("4 x 4", true);
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
        for (Map.Entry<String, SearchAlgorithm> entry : Game.ALGORITHMS) {
            algList.addItem(entry.getKey());
        }
        algList.setSelectedIndex(game.getSolverId());
        //noinspection rawtypes
        algList.addActionListener(e -> game.setSolverId(((JComboBox) e.getSource()).getSelectedIndex()));
        add(algList);

        JButton shuffleJButton = new JButton("Shuffle");
        shuffleJButton.addActionListener(e -> game.shuffle());
        JButton resolveJButton = new JButton("Resolve");
        resolveJButton.addActionListener(e -> game.resolve());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(shuffleJButton);
        buttonPanel.add(resolveJButton);
        add(buttonPanel);
    }
}
