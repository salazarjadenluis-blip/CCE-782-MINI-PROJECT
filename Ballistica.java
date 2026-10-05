import java.awt.BorderLayout;
import java.util.Random;
import javax.swing.*;

public class Ballistica extends JFrame {

    private final Random rng = new Random();
    private final BattlePanel panel = new BattlePanel();
    private final ControlPanel controls = new ControlPanel();
    private final InfoPanel info = new InfoPanel();

    private int hits = 0;
    private int shots = 0;
    private Target target;

    public Ballistica() {
        super("Ballistica - Artillery Range Trainer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(controls, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(info, BorderLayout.SOUTH);

        controls.getFireBtn().addActionListener(e -> fire());
        controls.getSolutionBtn().addActionListener(e -> showSolution());
        controls.getNewTargetBtn().addActionListener(e -> newTarget());
        controls.getAngleSpin().addChangeListener(e -> panel.setAngleDegrees(controls.getAngleDegrees()));

        panel.setAngleDegrees(controls.getAngleDegrees());
        newTarget();
        pack();
        setLocationRelativeTo(null);
    }

    private void newTarget() {
        target = Target.random(rng);
        info.setTarget(target);
        info.setScore(hits, shots);
        panel.setTarget(target);
        panel.clearTrail();
        panel.repaint();
    }

    private void fire() {
        final double angle = Math.toRadians(controls.getAngleDegrees());
        final double v = controls.getVelocity();

        panel.clearTrail();
        shots++;
        info.setScore(hits, shots);
        controls.getFireBtn().setEnabled(false);
        info.setFeedback("Firing...");

        final double[] t = {0};
        final Timer[] timer = new Timer[1];
        timer[0] = new Timer(16, e -> {
            for (int i = 0; i < 5; i++) {
                t[0] += 0.02;
                double x = Physics.xAt(v, angle, t[0]);
                double y = Physics.yAt(v, angle, t[0]);
                panel.addTrailPoint(x, y);

                if (Math.hypot(x - target.x, y - target.y) <= Constants.HIT_RADIUS) {
                    hits++;
                    endShot(timer[0], "DIRECT HIT! Click \"New target\" for the next one.");
                    return;
                }
                if (y < 0) {
                    endShot(timer[0], missMessage(x, angle, v));
                    return;
                }
            }
            panel.repaint();
        });
        timer[0].start();
    }

    private void endShot(Timer timer, String message) {
        timer.stop();
        info.setFeedback(message);
        info.setScore(hits, shots);
        controls.getFireBtn().setEnabled(true);
        panel.repaint();
    }

    private String missMessage(double landingX, double angle, double v) {
        if (landingX < target.x - Constants.HIT_RADIUS) {
            return String.format("Missed - SHORT. Landed at %.1f m, %.1f m before the target.",
                    landingX, target.x - landingX);
        }
        double diff = Physics.heightAtDistance(target.x, angle, v) - target.y;
        return String.format("Missed - at the target's distance your shell was %.1f m %s it.",
                Math.abs(diff), diff > 0 ? "ABOVE" : "BELOW");
    }

    private void showSolution() {
        double v = controls.getVelocity();
        double[] angles = Physics.solveAngles(v, target.x, target.y);
        if (angles == null) {
            info.setFeedback(String.format("Out of range at %.0f m/s - increase the velocity.", v));
            return;
        }
        info.setFeedback(String.format("At %.0f m/s: low angle = %.1f deg, high angle = %.1f deg",
                v, angles[0], angles[1]));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ballistica().setVisible(true));
    }
}
