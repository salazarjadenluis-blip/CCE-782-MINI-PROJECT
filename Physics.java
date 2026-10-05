public final class Physics {

    private Physics() {
    }

    public static double xAt(double v, double angleRad, double t) {
        return v * Math.cos(angleRad) * t;
    }

    public static double yAt(double v, double angleRad, double t) {
        return v * Math.sin(angleRad) * t - 0.5 * Constants.G * t * t;
    }

    public static double heightAtDistance(double distance, double angleRad, double v) {
        double cos = Math.cos(angleRad);
        return distance * Math.tan(angleRad)
                - Constants.G * distance * distance / (2 * v * v * cos * cos);
    }

    public static double[] solveAngles(double v, double targetX, double targetY) {
        double g = Constants.G;
        double disc = Math.pow(v, 4) - g * (g * targetX * targetX + 2 * targetY * v * v);
        if (disc < 0) {
            return null;
        }
        double root = Math.sqrt(disc);
        double low = Math.toDegrees(Math.atan((v * v - root) / (g * targetX)));
        double high = Math.toDegrees(Math.atan((v * v + root) / (g * targetX)));
        return new double[]{low, high};
    }
}
