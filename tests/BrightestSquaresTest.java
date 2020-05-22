package tests;
import src.MyProject;
import java.util.Random;
import java.util.Arrays;

public class BrightestSquaresTest {
    public static int testNum = 0;
    public static int numPassed = 0;
    public static MyProject proj = new MyProject();
    
    // To test edge cases, idk
    public static void customTests() {
        System.out.println("\nCUSTOM TESTS");

        int[][] image = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
        };

        testEquality(image, 5, 0);

        // all same except one smaller number, length of square is 6x6
        image = new int[][] {
            {255, 255, 255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255, 255, 255},
            {255, 255, 255, 255, 254, 255, 255},
            {255, 255, 255, 255, 255, 255, 255},
        };

        testEquality(image, 6, 255*35 + 254);

        image = new int[][] {
            {1, 1, 1, 1, 1},
            {1, 1, 2, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
        };

        testEquality(image, 1, 2);

        image = new int[][] {
            {50, 1, 1, 1, 50},
            {1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {0, 0, 198, 0, 0},
            {0, 0, 0, 0, 0},
            {50, 49, 0, 0, 0},
            {50, 50, 1, 1, 50},
        };

        testEquality(image, 2, 199);

        // can't have negative brightness but wup
        image = new int[][] {
            {1, 2, -1, -4, -20},
            {-8, -3, 4, 2, 1},
            {3, 8, 10, 1, 3},
            {-4, -1, 1, 7, -6},
        };

        testEquality(image, 3, 29);
 
        image = new int[][] {
            {100, 89, 1, 0, 85},
            {96, 101, 250, 255, 99},
            {102, 99, 81, 80, 129},
            {107, 110, 121, 111, 130},
        };

        testEquality(image, 2, 250+255+81+80);

    }

    public static void randomTests() {
        System.out.println("\nRANDOM TESTS");
        Random random = new Random();
        int total_tests = 1000;
        int[][] image;

        for (int n = 0; n < total_tests; n++) {
            int n_rows = random.nextInt(10) + 3; // min 3 rows and columns 
            int n_cols = random.nextInt(10) + 3;
            image = new int[n_rows][n_cols];
            int length = Math.max(2, random.nextInt(Math.min(n_rows, n_cols)));
            int expected = 0;
            // where brightest square starts (top left corner)
            int start_row = random.nextInt(n_rows - length);
            int start_col = random.nextInt(n_cols - length);
            
            for (int i = 0; i < n_rows; i++) {
                for (int j = 0; j < n_cols; j++) {
                    if (i >= start_row && i < start_row + length && j >= start_col && j < start_col + length) {
                        int pixel = (int) Math.abs(27.5 * random.nextGaussian()) + 200;
                        image[i][j] = pixel;
                        expected += pixel;
                        
                    }
                    else image[i][j] = random.nextInt(201);
                }
            }

            testEquality(image, length, expected);
        }
    }

    private static void testEquality(int[][] image, int length, int expected) {
        testNum++;
        if (proj.brightestSquare(image, length) == expected) {
            System.out.println("Test " + testNum + " : passed");
            numPassed++;
        }
        else {
            System.out.println("Test " + testNum + " : FAILED");
        }
    }

    public static void printImage(int[][] image) {
        for (int[] row: image) System.out.println(Arrays.toString(row));
    }

    public static void main(String[] args) 
    {
        randomTests();
        customTests();
        System.out.println("\n" + numPassed + "/" + testNum + " tests passed");
    }
}