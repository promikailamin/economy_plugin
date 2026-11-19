package minecraft.economy;

public final class NeuralNetworkHarness {

    private NeuralNetworkHarness() {
    }

    public static void main(String[] args) {
        deterministicSeed();
        learnOr();
        roundTrip();
        System.out.println("NeuralNetworkHarness: PASS");
    }

    private static void deterministicSeed() {
        NeuralNetwork a = new NeuralNetwork(42, 3, 5, 2);
        NeuralNetwork b = new NeuralNetwork(42, 3, 5, 2);
        double[] input = {0.2, 0.5, 0.9};
        double[] outA = a.forward(input);
        double[] outB = b.forward(input);
        for (int i = 0; i < outA.length; i++) {
            check(outA[i] >= 0 && outA[i] <= 1, "output must be in [0,1]");
            check(Math.abs(outA[i] - outB[i]) < 1e-12, "same seed must be deterministic");
        }
    }

    private static void learnOr() {
        NeuralNetwork net = new NeuralNetwork(7, 2, 6, 1);
        double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[] targets = {0, 1, 1, 1};
        for (int epoch = 0; epoch < 4000; epoch++) {
            for (int i = 0; i < inputs.length; i++) {
                net.train(inputs[i], new double[]{targets[i]}, 0.35);
            }
        }
        double low = net.forward(new double[]{0, 0})[0];
        double high1 = net.forward(new double[]{0, 1})[0];
        double high2 = net.forward(new double[]{1, 0})[0];
        double high3 = net.forward(new double[]{1, 1})[0];
        check(low < 0.20, "0 OR 0 should be low, got " + low);
        check(high1 > 0.80, "0 OR 1 should be high, got " + high1);
        check(high2 > 0.80, "1 OR 0 should be high, got " + high2);
        check(high3 > 0.80, "1 OR 1 should be high, got " + high3);
        check(net.trained() == 16000, "training counter");
    }

    private static void roundTrip() {
        NeuralNetwork source = new NeuralNetwork(99, 4, 7, 3);
        double[] flat = source.toFlat();
        NeuralNetwork copy = new NeuralNetwork(1, 4, 7, 3);
        copy.fromFlat(flat);
        double[] input = {0.1, 0.4, 0.7, 0.9};
        double[] a = source.forward(input);
        double[] b = copy.forward(input);
        for (int i = 0; i < a.length; i++) {
            check(Math.abs(a[i] - b[i]) < 1e-12, "flat round trip must preserve output");
        }
        copy.setTrained(12);
        check(copy.trained() == 12, "trained counter restore");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
