import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class InfoPanel extends JPanel {

    private final JLabel targetLabel = new JLabel();
    private final JLabel feedback = new JLabel("Set your angle and velocity, then fire!");
    private final JLabel scoreLabel = new JLabel();

    public InfoPanel() {
        super(new GridLayout(3, 1));
        setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        targetLabel.setFont(targetLabel.getFont().deriveFont(Font.BOLD, 15f));
        add(targetLabel);
        add(feedback);
        add(scoreLabel);
    }

    public void setTarget(Target target) {
        targetLabel.setText(String.format("Target: X = %.0f m,  Y = %.0f m", target.x, target.y));
    }

    public void setFeedback(String message) {
        feedback.setText(message);
    }

    public void setScore(int hits, int shots) {
        scoreLabel.setText("Hits: " + hits + "   Shots: " + shots);
    }
}
