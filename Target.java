import java.util.Random;

public class Target {

    public final double x;
    public final double y;

    public Target(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Target random(Random rng) {
        return new Target(100 + rng.nextInt(351), rng.nextInt(61));
    }
}
