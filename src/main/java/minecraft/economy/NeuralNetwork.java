package minecraft.economy;

import java.util.Arrays;
import java.util.Random;

public final class NeuralNetwork {

    private final int[] layers;
    private final double[][][] weights;
    private final double[][] biases;
    private int trained;

    public NeuralNetwork(int seed, int... layers) {
        if (layers.length < 2) throw new IllegalArgumentException("need at least input and output layers");
        this.layers = layers.clone();
        this.weights = new double[layers.length - 1][][];
        this.biases = new double[layers.length - 1][];
        Random rng = new Random(seed);
        for (int l = 0; l < layers.length - 1; l++) {
            int in = layers[l];
            int out = layers[l + 1];
            weights[l] = new double[out][in];
            biases[l] = new double[out];
            double scale = Math.sqrt(2.0 / (in + out));
            for (int j = 0; j < out; j++) {
                biases[l][j] = (rng.nextDouble() - 0.5) * 0.1;
                for (int i = 0; i < in; i++) {
                    weights[l][j][i] = rng.nextGaussian() * scale;
                }
            }
        }
    }

    public int[] layers() {
        return layers.clone();
    }

    public String layerSpec() {
        StringBuilder sb = new StringBuilder();
        for (int layer : layers) {
            if (sb.length() > 0) sb.append(',');
            sb.append(layer);
        }
        return sb.toString();
    }

    public int trained() {
        return trained;
    }

    public void setTrained(int trained) {
        this.trained = Math.max(0, trained);
    }

    public double[] forward(double[] input) {
        if (input.length != layers[0]) {
            throw new IllegalArgumentException("expected " + layers[0] + " inputs, got " + input.length);
        }
        double[] activations = input.clone();
        for (int l = 0; l < weights.length; l++) {
            double[] next = new double[weights[l].length];
            for (int j = 0; j < next.length; j++) {
                double sum = biases[l][j];
                for (int i = 0; i < activations.length; i++) {
                    sum += weights[l][j][i] * activations[i];
                }
                next[j] = l == weights.length - 1 ? sigmoid(sum) : Math.tanh(sum);
            }
            activations = next;
        }
        return activations;
    }

    public double train(double[] input, double[] target, double learningRate) {
        if (target.length != layers[layers.length - 1]) {
            throw new IllegalArgumentException("target size mismatch");
        }
        double[][] activations = new double[layers.length][];
        double[][] zs = new double[layers.length][];
        activations[0] = input.clone();
        for (int l = 0; l < weights.length; l++) {
            zs[l + 1] = new double[weights[l].length];
            activations[l + 1] = new double[weights[l].length];
            for (int j = 0; j < weights[l].length; j++) {
                double sum = biases[l][j];
                for (int i = 0; i < activations[l].length; i++) {
                    sum += weights[l][j][i] * activations[l][i];
                }
                zs[l + 1][j] = sum;
                activations[l + 1][j] = l == weights.length - 1 ? sigmoid(sum) : Math.tanh(sum);
            }
        }

        double[][] deltas = new double[layers.length][];
        int outLayer = layers.length - 1;
        deltas[outLayer] = new double[layers[outLayer]];
        double loss = 0;
        for (int j = 0; j < deltas[outLayer].length; j++) {
            double a = activations[outLayer][j];
            double diff = a - target[j];
            loss += diff * diff;
            deltas[outLayer][j] = diff * a * (1 - a);
        }
        for (int l = outLayer - 1; l >= 1; l--) {
            deltas[l] = new double[layers[l]];
            for (int j = 0; j < deltas[l].length; j++) {
                double sum = 0;
                for (int k = 0; k < deltas[l + 1].length; k++) {
                    sum += weights[l][k][j] * deltas[l + 1][k];
                }
                deltas[l][j] = sum * (1 - activations[l][j] * activations[l][j]);
            }
        }

        for (int l = 0; l < weights.length; l++) {
            for (int j = 0; j < weights[l].length; j++) {
                for (int i = 0; i < weights[l][j].length; i++) {
                    weights[l][j][i] -= learningRate * deltas[l + 1][j] * activations[l][i];
                }
                biases[l][j] -= learningRate * deltas[l + 1][j];
            }
        }
        trained++;
        return loss / deltas[outLayer].length;
    }

    public double[] toFlat() {
        int size = 0;
        for (int l = 0; l < weights.length; l++) {
            size += biases[l].length;
            for (double[] row : weights[l]) size += row.length;
        }
        double[] flat = new double[size];
        int index = 0;
        for (int l = 0; l < weights.length; l++) {
            System.arraycopy(biases[l], 0, flat, index, biases[l].length);
            index += biases[l].length;
            for (double[] row : weights[l]) {
                System.arraycopy(row, 0, flat, index, row.length);
                index += row.length;
            }
        }
        return flat;
    }

    public void fromFlat(double[] flat) {
        int index = 0;
        for (int l = 0; l < weights.length; l++) {
            System.arraycopy(flat, index, biases[l], 0, biases[l].length);
            index += biases[l].length;
            for (double[] row : weights[l]) {
                System.arraycopy(flat, index, row, 0, row.length);
                index += row.length;
            }
        }
        if (index != flat.length) {
            throw new IllegalArgumentException("weight count mismatch: expected " + index + ", got " + flat.length);
        }
    }

    public static double sigmoid(double x) {
        if (x >= 40) return 1;
        if (x <= -40) return 0;
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static double clamp01(double value) {
        return Math.max(0, Math.min(1, value));
    }

    @Override
    public String toString() {
        return "NeuralNetwork" + Arrays.toString(layers) + "{trained=" + trained + "}";
    }
}
