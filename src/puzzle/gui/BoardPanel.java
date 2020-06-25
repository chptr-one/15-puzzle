package puzzle.gui;

import puzzle.common.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BoardPanel extends JPanel {
    static private final int TILE_SIZE = 80;
    static private final int PADDING = 2;
    private Board board;

    BoardPanel(Board board) {
        this.board = board;
        int dimension = (TILE_SIZE + PADDING / 2) * board.getDimension() - PADDING / 2;
        setPreferredSize(new Dimension(dimension, dimension));
        setBackground(Color.LIGHT_GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                makeMove(e.getX(), e.getY());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        Font font = g.getFont().deriveFont(Font.BOLD, 20f);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                if (board.getTiles()[board.toIndex(row, col)] != 0) {
                    drawBlock(g, metrics, row, col, board.getTiles()[board.toIndex(row, col)]);
                }
            }
        }
    }

    private void drawBlock(Graphics g, FontMetrics metrics, int row, int col, int value) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(col * TILE_SIZE + PADDING, row * TILE_SIZE + PADDING,
                TILE_SIZE - PADDING, TILE_SIZE - PADDING,
                10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(col * TILE_SIZE + PADDING, row * TILE_SIZE + PADDING,
                TILE_SIZE - PADDING, TILE_SIZE - PADDING,
                10, 10);
        String s = value == 0 ? "" : String.valueOf(value);
        int x = col * TILE_SIZE + PADDING / 2 + (TILE_SIZE - metrics.stringWidth(s)) / 2;
        int y = row * TILE_SIZE + PADDING / 2 + ((TILE_SIZE - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(s, x, y);
    }

    public void setBoard(Board board) {
        int dimension = (TILE_SIZE + PADDING / 2) * board.getDimension() - PADDING / 2;
        setPreferredSize(new Dimension(dimension, dimension));
        updateBoard(board);
    }

    public void updateBoard(Board board) {
        this.board = board;
        repaint();
    }

    public void makeMove(int x, int y) {
        int row = (y - PADDING / 2) / TILE_SIZE;
        int col = (x - PADDING / 2) / TILE_SIZE;
        updateBoard(board.moveTile(row, col));
    }
}