import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class MapWindow extends JFrame {


    private final JButton[][] buttons = new JButton[TikToe.getSize()][TikToe.getSize()];
    private final String DRAW_X = "DRAW_X";
    private final String DRAW_O = "DRAW_O";
    private final String EMPTY = "EMPTY";
    private int k = 0;


    public MapWindow() {
        setSize(900, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(TikToe.getSize(), TikToe.getSize()));
        for (int i = 0; i < TikToe.getSize(); i++) {
            for (int j = 0; j < TikToe.getSize(); j++) {

                buttons[i][j] = createButton();
                add(buttons[i][j]);
            }
        }
        TikToe.incCountTurns();
        setVisible(true);

    }

    public JButton createButton() {
        return new JButton() {
            {

                ActionListener actionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String action = EMPTY;
                        if (TikToe.getCountTurns() > 0) {
                            if (TikToe.isHumanStep()) {
                                int[] kord = new int[2];
                                action = DRAW_X;
                                kord[1] = getLocation().x / getWidth();
                                kord[0] = getLocation().y / getHeight();
                                TikToe.humanStep(kord);
                                TikToe.incCountTurns();
                                if (TikToe.isDraw() || TikToe.isWin()) {
                                    for (int i = 0; i < TikToe.getSize(); i++) {
                                        for (int j = 0; j < TikToe.getSize(); j++) {
                                            buttons[i][j].setEnabled(false);
                                        }
                                    }
                                    new ResultWindow();
                                }
                                else {
                                    kord = TikToe.robotStep();
                                    buttons[kord[0]][kord[1]].doClick();
                                }

                            } else {
                                action = DRAW_O;
                                TikToe.incCountTurns();
                                if (TikToe.isDraw() || TikToe.isWin()) {
                                    for (int i = 0; i < TikToe.getSize(); i++) {
                                        for (int j = 0; j < TikToe.getSize(); j++) {
                                            buttons[i][j].setEnabled(false);
                                        }
                                    }
                                    new ResultWindow();
                                }
                            }

                            setActionCommand(action);
                        }
                    }
                };
                addActionListener(actionListener);
            }


            @Override
            public void paint(Graphics g) {
                super.paint(g);
                switch (getActionCommand()) {
                    case DRAW_X: {
                        Graphics2D graphics2D = (Graphics2D) g;
                        graphics2D.setStroke(new BasicStroke(10));
                        graphics2D.setColor(Color.cyan);
                        graphics2D.drawLine(0, 0, this.getWidth(), this.getHeight());
                        graphics2D.drawLine(0, this.getHeight(), this.getWidth(), 0);
                        this.setEnabled(false);

                        break;
                    }
                    case DRAW_O: {
                        g.setColor(Color.BLACK);
                        g.drawOval(0, 0, this.getWidth(), this.getHeight());
                        g.setColor(Color.RED);
                        g.fillOval(0, 0, this.getWidth(), this.getHeight());
                        this.setEnabled(false);
                        TikToe.incCountTurns();
                        break;
                    }
                }

            }
        };

    }
}
