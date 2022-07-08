import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.Random;

public class NeuralNetwork{
  Random rn = new Random(100);
  // ArrayList<double[]> hiddenLayers = new ArrayList<double[]>();

  int NUMBER_OF_INPUTS = 19; // 18 + 1 (bias)
  int NUMBER_OF_OUTPUTS = 9;
  int HIDDEN_ACTIVATIONS_1 = 15;
  int HIDDEN_ACTIVATIONS_2 = 12;
  double[] inputLayer = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  // double[] inputLayer = new double[NUMBER_OF_INPUTS];
  double[] hiddenLayer1 = new double[HIDDEN_ACTIVATIONS_1];
  double[] hiddenLayer2 = new double[HIDDEN_ACTIVATIONS_2];
  double[] outputLayer = new double[NUMBER_OF_OUTPUTS];
  double[][] weights1;
  double[][] weights2;
  double[][] weights3;

  NeuralNetwork(){
    this.weights1 = generateWeights(NUMBER_OF_INPUTS, HIDDEN_ACTIVATIONS_1);
    this.weights2 = generateWeights(HIDDEN_ACTIVATIONS_1, HIDDEN_ACTIVATIONS_2);
    this.weights3 = generateWeights(HIDDEN_ACTIVATIONS_2, NUMBER_OF_OUTPUTS);
  }

  private double[][] generateWeights(int m, int n){
    double minN = -1;
    double maxN = 1;
    double[][] weights = new double[m][n];

    for (int i = 0; i < m; i++){
      for (int j = 0; j < n; j++){
        weights[i][j] = minN + rn.nextDouble() * (maxN - minN);
      }
    }
    return weights;
  }

  private void RELU(double[] arr){
    for (int i = 0; i < arr.length; i++){
      arr[i] = Math.max(0, arr[i]);
    }
  }

  private void softMax(double[] arr){
    double total = 0;
    for (int i = 0; i < arr.length; i++){
      total += Math.exp(arr[i]);
    }
    System.out.println("----------");
    System.out.println(total);
    System.out.println("-----------------");
    total = Arrays.stream(arr).map(Math::exp).sum();
    System.out.println(total);

  }

  private void feedForward(){
    Arrays.fill(hiddenLayer1, 0);
    Arrays.fill(hiddenLayer2, 0);
    Arrays.fill(outputLayer, 0);

    System.out.println(Arrays.toString(inputLayer));
    System.out.println("-----------------------------------");


    for (int i = 0; i < this.weights1.length; i++){
      System.out.println(Arrays.toString(this.weights1[i]));
    }
    System.out.println("-----------------------------------");
    System.out.println(this.weights1.length);
    System.out.println(this.weights1[0].length);

    for (int i = 0; i < hiddenLayer1.length; i++){
      for (int j = 0; j < this.weights1.length; j++){
        hiddenLayer1[i] += inputLayer[j] * this.weights1[j][i];
      }
    }
    RELU(hiddenLayer1);

    for (int i = 0; i < hiddenLayer2.length; i++){
      for (int j = 0; j < this.weights2.length; j++){
        hiddenLayer2[i] += hiddenLayer1[j] * this.weights2[j][i];
      }
    }
    RELU(hiddenLayer2);

    for (int i = 0; i < outputLayer.length; i++){
      for (int j = 0; j < this.weights3.length; j++){
        outputLayer[i] += hiddenLayer2[j] * this.weights3[j][i];
      }
    }

    for (int i = 0; i < hiddenLayer1.length; i++){
      System.out.println(hiddenLayer1[i]);
    }
    System.out.println("---------------------------");

    for (int i = 0; i < hiddenLayer2.length; i++){
      System.out.println(hiddenLayer2[i]);
    }
    System.out.println("---------------------------");

    for (int i = 0; i < outputLayer.length; i++){
      System.out.println(outputLayer[i]);
    }
    softMax(outputLayer);
  }

  public static void main(String[] args) {
    NeuralNetwork main = new NeuralNetwork();
    main.feedForward();
  }
}
