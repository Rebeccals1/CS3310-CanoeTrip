/**************************************************************/
/* Rebecca Smith                                              */
/* Login ID: your_login_here                                  */
/* CS 3310, Fall 2025                                         */
/* Programming Assignment 3                                   */
/* CanoeTrip: loads inputA.txt, inputB.txt, inputC.txt from   */
/* src/main/resources using the classpath, runs the DP canoe  */
/* rental algorithm, prints optimal cost matrix and route.    */
/**************************************************************/

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CanoeTrip {

    private static final int INF = Integer.MAX_VALUE / 4;

    /**************************************************************/
    /* main: runs all three input files from the resources folder */
    /**************************************************************/
    public static void main(String[] args) {

        String[] requiredFiles = {
                "inputA.txt",
                "inputB.txt",
                "inputC.txt"
        };

        System.out.println("=== Running CanoeTrip on inputA.txt, inputB.txt, inputC.txt ===\n");

        for (String fileName : requiredFiles) {
            runForFile(fileName);
            System.out.println("\n----------------------------------------------------\n");
        }

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter another resource file (or type 'none'): ");
        String extra = keyboard.next();

        if (!extra.equalsIgnoreCase("none")) {
            runForFile(extra);
        }

        keyboard.close();
    }

    /**************************************************************/
    /* runForFile: wrapper to load → run DP → print results       */
    /**************************************************************/
    private static void runForFile(String filename) {
        System.out.println("Processing file: " + filename);

        try {
            int[][] directCosts = readCostMatrixFromResources(filename);
            int n = directCosts.length;

            int[][] optimalCosts = new int[n][n];
            int[][] split = new int[n][n];

            computeOptimalCosts(directCosts, optimalCosts, split);

            System.out.println("\nOptimal cost matrix for " + filename + ":");
            printUpperTriangularMatrix(optimalCosts);

            System.out.println("\nOptimal route from 0 to " + (n - 1) + ":");
            List<int[]> route = reconstructRoute(0, n - 1, split);
            printRoute(route, optimalCosts);

        } catch (Exception e) {
            System.out.println("Error: could not open resource file \"" + filename + "\"");
        }
    }

    /**************************************************************/
    /* readCostMatrixFromResources: loads .txt file using class   */
    /* loader. Works with src/main/resources and packaged JARs.   */
    /**************************************************************/
    private static int[][] readCostMatrixFromResources(String filename)
            throws Exception {

        InputStream input = CanoeTrip.class.getResourceAsStream("/" + filename);

        if (input == null) {
            throw new Exception("Resource not found.");
        }

        Scanner fileScanner = new Scanner(input);

        int n = fileScanner.nextInt();
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                cost[i][j] = INF;

        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                cost[i][j] = fileScanner.nextInt();

        fileScanner.close();
        return cost;
    }

    /**************************************************************/
    /* computeOptimalCosts: dynamic programming recurrence logic  */
    /**************************************************************/
    private static void computeOptimalCosts(int[][] directCosts,
                                            int[][] optimalCosts,
                                            int[][] split) {

        int n = directCosts.length;

        for (int i = 0; i < n; i++) {
            optimalCosts[i][i] = 0;
            split[i][i] = -1;
        }

        for (int distance = 1; distance < n; distance++) {
            for (int i = 0; i + distance < n; i++) {
                int j = i + distance;

                int bestCost = directCosts[i][j];
                int bestSplit = -1;

                for (int k = i + 1; k < j; k++) {
                    int candidate = directCosts[i][k] + optimalCosts[k][j];
                    if (candidate < bestCost) {
                        bestCost = candidate;
                        bestSplit = k;
                    }
                }

                optimalCosts[i][j] = bestCost;
                split[i][j] = bestSplit;
            }
        }
    }

    /**************************************************************/
    /* reconstruct optimal route using split[][]                  */
    /**************************************************************/
    private static List<int[]> reconstructRoute(int start, int end, int[][] split) {
        List<int[]> segments = new ArrayList<>();
        reconstructHelper(start, end, split, segments);
        return segments;
    }

    private static void reconstructHelper(int i, int j, int[][] split, List<int[]> segments) {
        int k = split[i][j];
        if (k == -1) {
            segments.add(new int[]{i, j});
        } else {
            reconstructHelper(i, k, split, segments);
            reconstructHelper(k, j, split, segments);
        }
    }

    /**************************************************************/
    /* Helpers                                           */
    /**************************************************************/
    private static void printUpperTriangularMatrix(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                System.out.print(matrix[i][j]);
                if (j < n - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static void printRoute(List<int[]> route, int[][] optimalCosts) {
        for (int[] seg : route) {
            System.out.println("Rent from post " + seg[0] + " to post " + seg[1]);
        }
        int n = optimalCosts.length;
        System.out.println("Total minimal cost = " + optimalCosts[0][n - 1]);
    }
}
