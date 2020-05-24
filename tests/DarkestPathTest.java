package tests;
import src.*;
import java.util.Random;
import java.util.Arrays;
import java.lang.System;

public class DarkestPathTest {

    public static int numAutomatedTests = 6000;
    public static int numTests = 0;
    public static int numPassed = 0;

    public static void testInts(String name, int value, int expected) {
        numTests++;
        String pref = "";
        if (value == expected) {
            pref = "Pass:\t";
            numPassed++;
        } else {
            pref = "Fail:\t";
        }
        System.out.println(pref + name + ((value == expected) ? "" : " got " + value));
    }

    public static void runSimpleTests(Project proj, int count) {
        Random rand = new Random();
        int maxSize = 512;
        for (int i = 0; i < count; i++) {
            int rows = rand.nextInt(maxSize - 1) + 2;
            int cols = rand.nextInt(maxSize - 1) + 2;
            int[][] image = new int[rows][cols];
            for (int r = 0; r < rows; r++) {
                Arrays.fill(image[r], 255);
            }
            int startR = rand.nextInt(rows / 2);
            int startC = rand.nextInt(cols / 2);
            int endR = rand.nextInt(rows / 2) + rows / 2;
            int endC = rand.nextInt(cols / 2) + cols / 2;

            int max = rand.nextInt(255);
            image[startR][startC] = max;
            int currentRow = startR;
            int currentCol = startC;
            while (currentRow != endR || currentCol != endC) {
                if (currentRow < endR && currentCol < endC) {
                    int direction = rand.nextInt(2);
                    if (direction == 0) {
                        currentRow += 1;
                    } else {
                        currentCol += 1;
                    }
                } else if (currentRow < endR) {
                    currentRow += 1;
                } else {
                    currentCol += 1;
                }
                int value = rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(32);
                if (value > max) {
                    max = value;
                }
                image[currentRow][currentCol] = value;
            }

            if (startR > 1 && startC > 1 && endR < rows - 2 && endC < cols - 2) {
                // Create alternate path over the top of the image
                int maxRightPath = Math.max(image[startR][startC], image[endR][endC]);
                for (int j = startC - 2; j <= endC + 2; j++) {
                    int value = rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(32);
                    image[0][j] = value;
                    if (value > maxRightPath) {
                        maxRightPath = value;
                    }
                }
                for (int j = 1; j <= startR; j++) {
                    int value = rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(32);
                    image[j][startC - 2] = value;
                    if (value > maxRightPath) {
                        maxRightPath = value;
                    }
                }
                for (int j = 1; j <= endR; j++) {
                    int value = rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(32);
                    image[j][endC + 2] = value;
                    if (value > maxRightPath) {
                        maxRightPath = value;
                    }
                }
                int value = rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(32);
                image[startR][startC - 1] = value;
                if (value > maxRightPath) {
                    maxRightPath = value;
                }
                value = rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(33) + rand.nextInt(32);
                image[endR][endC + 1] = value;
                if (value > maxRightPath) {
                    maxRightPath = value;
                }
                if (maxRightPath < max) {
                    max = maxRightPath;
                }
            }

            testInts("DarkestPath " + max, proj.darkestPath(image, startR, startC, endR, endC), max);
        }
    }

    public static void runDefaultTests(Project proj) {
        int[][] image = new int[][] {
            { 0,   10,  0,   10,  0,   100, 20 },
            { 5,   0,   1,   5,   200, 1,   5 },
            { 0,   0,   0,   0,   0,   0,   0 },
            { 0,   0,   100, 100, 200, 50,  0 },
            { 5,   5,   3,   4,   3,   2,   1 },
            { 255, 255, 255, 255, 255, 255, 255 },
        };

        String pref = "DarkestPath ";

        testInts(pref + "example 1",
            proj.darkestPath(image, 0, 0, 5, 6), 255);

        testInts(pref + "example 2",
            proj.darkestPath(image, 1, 1, 2, 6), 0);

        testInts(pref + "example 3",
            proj.darkestPath(image, 2, 6, 0, 2), 1);

        testInts(pref + "example 4",
            proj.darkestPath(image, 0, 6, 0, 3), 20);

        image = new int[][] {
            { 0, 5, 5, 5, 5 },
            { 5, 0, 5, 5, 5 },
            { 5, 5, 0, 5, 5 },
            { 5, 5, 5, 0, 5 },
            { 5, 5, 5, 5, 0 },
        };

        testInts(pref + "example 5",
            proj.darkestPath(image, 0, 0, 4, 4), 5);

        image[1][0] = 0;
        testInts(pref + "example 6",
            proj.darkestPath(image, 0, 0, 4, 4), 5);

        image[2][1] = 0;
        testInts(pref + "example 7",
            proj.darkestPath(image, 0, 0, 4, 4), 5);

        image[3][2] = 0;
        testInts(pref + "example 8",
            proj.darkestPath(image, 0, 0, 4, 4), 5);

        image[4][3] = 0;
        testInts(pref + "example 9",
            proj.darkestPath(image, 0, 0, 4, 4), 0);

        image[4][3] = 1;
        testInts(pref + "example 10",
            proj.darkestPath(image, 0, 0, 4, 4), 1);

        image[3][4] = 0;
        testInts(pref + "example 11",
            proj.darkestPath(image, 0, 0, 4, 4), 0);

        testInts(pref + "to itself 1",
            proj.darkestPath(image, 0, 0, 0, 0), 0);

        testInts(pref + "to itself 2",
            proj.darkestPath(image, 0, 4, 0, 4), 5);
    }

    public static void main(String[] args) {
        Project project = new MyProject();
        long start = System.currentTimeMillis();
        //runDefaultTests(project);
        runSimpleTests(project, numAutomatedTests);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("Passed " + numPassed + "/" + numTests + " tests");
    }
}