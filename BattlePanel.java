import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class BattlePanel extends JPanel {

    private final List<double[]> trail = new ArrayList<>();
    private Target target;
    private double angleDegrees = 45.0;

    public BattlePanel() {
        setPreferredSize(new Dimension(900, 450));
        setBackground(new Color(225, 238, 250));
    }

    public void setTarget(Target target) {
        this.target = target;
        repaint();
    }

    public void setAngleDegrees(double angleDegrees) {
        this.angleDegrees = angleDegrees;
        repaint();
    }

    public void clearTrail() {
        trail.clear();
    }

    public void addTrailPoint(double x, double y) {
        trail.add(new double[]{x, y});
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int left = 40;
        int bottom = getHeight() - 30;
        double scale = (getWidth() - left - 20) / Constants.WORLD_W;

        drawGrid(g, left, bottom, scale);
        drawGround(g, bottom);
        drawAxisLabels(g, left, bottom, scale);
        drawTarget(g, left, bottom, scale);
        drawCannon(g, left, bottom);
        drawTrail(g, left, bottom, scale);
    }

    private void drawGrid(Graphics2D g, int left, int bottom, double scale) {
        g.setColor(new Color(0, 0, 0, 30));
        for (int m = 0; m <= Constants.WORLD_W; m += 50) {
            int px = left + (int) (m * scale);
            g.drawLine(px, 0, px, bottom);
        }
        for (int m = 50; m * scale < bottom; m += 50) {
            int py = bottom - (int) (m * scale);
            g.drawLine(left, py, getWidth(), py);
        }
    }

    private void drawGround(Graphics2D g, int bottom) {
        g.setColor(new Color(90, 140, 70));
        g.fillRect(0, bottom, getWidth(), getHeight() - bottom);
    }

    private void drawAxisLabels(Graphics2D g, int left, int bottom, double scale) {
        g.setColor(Color.DARK_GRAY);
        for (int m = 0; m <= Constants.WORLD_W; m += 50) {
            g.drawString(m + "m", left + (int) (m * scale) - 10, bottom + 18);
        }
        for (int m = 50; m * scale < bottom; m += 50) {
            g.drawString(m + "m", 4, bottom - (int) (m * scale) + 4);
        }
    }

    private void drawTarget(Graphics2D g, int left, int bottom, double scale) {
        if (target == null) {
            return;
        }
        int tx = left + (int) (target.x * scale);
        int ty = bottom - (int) (target.y * scale);
        int r = (int) (Constants.HIT_RADIUS * scale);
        g.setColor(new Color(130, 100, 70));
        g.fillRect(tx - 10, ty, 20, bottom - ty);
        g.setColor(Color.RED);
        g.fillOval(tx - r, ty - r, 2 * r, 2 * r);
        g.setColor(Color.WHITE);
        g.fillOval(tx - r / 2, ty - r / 2, r, r);
    }

    private void drawCannon(Graphics2D g, int left, int bottom) {
        double a = Math.toRadians(angleDegrees);
        g.setColor(new Color(50, 50, 50));
        g.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(left, bottom, left + (int) (32 * Math.cos(a)), bottom - (int) (32 * Math.sin(a)));
        g.fillOval(left - 12, bottom - 12, 24, 24);
    }

    private void drawTrail(Graphics2D g, int left, int bottom, double scale) {
        g.setColor(new Color(230, 120, 0));
        g.setStroke(new BasicStroke(2f));
        for (int i = 1; i < trail.size(); i++) {
            double[] p0 = trail.get(i - 1);
            double[] p1 = trail.get(i);
            g.drawLine(left + (int) (p0[0] * scale), bottom - (int) (p0[1] * scale),
                       left + (int) (p1[0] * scale), bottom - (int) (p1[1] * scale));
        }
        if (!trail.isEmpty()) {
            double[] last = trail.get(trail.size() - 1);
            g.fillOval(left + (int) (last[0] * scale) - 4, bottom - (int) (last[1] * scale) - 4, 8, 8);
        }
    }
}
