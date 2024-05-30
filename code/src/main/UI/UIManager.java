package main.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * class handles the UI setup for the puzzle game,
 * including creating buttons with custom appearance and adding event listeners
 */
public class UIManager {
    public JPanel setupUI(ActionListener[] listeners) {
        JPanel jp = new JPanel();
        jp.setLayout(null);
        jp.setBounds(550, 0, 150, 620);
        jp.setBackground(Color.DARK_GRAY);

        int buttonWidth = 130;
        int buttonHeight = 50;
        int buttonGap = 35;
        int yPosition = 50; // initial y position for the first button

        String[] buttonLabels = {
                "Randomize", "Solve puzzle", "Change level",
                "Change difficulty", "Manage images", "Leaderboard"
        };

        // create and add buttons to the panel
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createButton(buttonLabels[i], listeners[i]);
            button.setBounds(0, yPosition, buttonWidth, buttonHeight);
            jp.add(button);
            yPosition += buttonHeight + buttonGap;
        }

        return jp;
    }

    /**
     * creates a customized button with rounded corners and hover effect.
     */
    private JButton createButton(String text, ActionListener listener) {
        final Color defaultColor = new Color(255, 255, 255);
        final Color hoverColor = defaultColor.darker();
        final Color pressedColor = defaultColor.darker().darker();

        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // set button color based on state
                if (getModel().isPressed()) {
                    g2.setColor(pressedColor);
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(defaultColor);
                }

                // draw rounded rectangle
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // draw text
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int stringWidth = fm.stringWidth(getText());
                int stringHeight = fm.getAscent();
                g2.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 3);
                g2.dispose();
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorderPainted(false);
            }
        };

        button.addActionListener(listener);
        button.setBackground(defaultColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Legend Spartan", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultColor);
            }
        });

        return button;
    }
}
