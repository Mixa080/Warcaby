package warcaby.gui;

import javax.swing.*;
import java.awt.*;

public class TimerPanel extends JPanel {
    private JLabel timeLabel;
    private int secondsPassed;
    private Timer timer;
    private String playerName;

    public TimerPanel(String playerName) {
        this.playerName = playerName;
        this.secondsPassed = 0;
        
        setLayout(new BorderLayout());
        timeLabel = new JLabel(playerName + ": 00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(timeLabel, BorderLayout.CENTER);

        timer = new Timer(1000, e -> {
            secondsPassed++;
            updateLabel();
        });
        
        setBackground(Color.LIGHT_GRAY);
    }

    public void startTimer() {
        timer.start();
        setBackground(Color.GREEN);
    }

    public void stopTimer() {
        timer.stop();
        setBackground(Color.LIGHT_GRAY);
    }

    public void reset() {
        secondsPassed = 0;
        updateLabel();
    }

    private void updateLabel() {
        int minutes = secondsPassed / 60;
        int seconds = secondsPassed % 60;
        timeLabel.setText(String.format("%s: %02d:%02d", playerName, minutes, seconds));
    }
}
