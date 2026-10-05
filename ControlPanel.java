import java.awt.FlowLayout;
import javax.swing.*;

public class ControlPanel extends JPanel {

    private final JSpinner angleSpin = new JSpinner(new SpinnerNumberModel(45.0, 1.0, 89.0, 0.1));
    private final JSpinner velSpin = new JSpinner(new SpinnerNumberModel(60.0, 10.0, 120.0, 1.0));
    private final JButton fireBtn = new JButton("Fire!");
    private final JButton solutionBtn = new JButton("Show solution");
    private final JButton newTargetBtn = new JButton("New target");

    public ControlPanel() {
        super(new FlowLayout(FlowLayout.LEFT, 10, 8));
        add(new JLabel("Angle (deg):"));
        add(angleSpin);
        add(new JLabel("Velocity (m/s):"));
        add(velSpin);
        add(fireBtn);
        add(solutionBtn);
        add(newTargetBtn);
    }

    public JSpinner getAngleSpin() {
        return angleSpin;
    }

    public JSpinner getVelSpin() {
        return velSpin;
    }

    public JButton getFireBtn() {
        return fireBtn;
    }

    public JButton getSolutionBtn() {
        return solutionBtn;
    }

    public JButton getNewTargetBtn() {
        return newTargetBtn;
    }

    public double getAngleDegrees() {
        return (Double) angleSpin.getValue();
    }

    public double getVelocity() {
        return (Double) velSpin.getValue();
    }
}
