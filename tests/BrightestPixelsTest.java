package tests;
import src.*;
import java.util.Arrays;

public class BrightestPixelsTest {

    public static int numTests = 0;
    public static int numPassed = 0;

    public static void testArray(String name, int[] value, int[] expected) {
        numTests++;
        String pref = "";
        boolean same = Arrays.equals(value, expected);
        if (same) {
            pref = "Pass:\t";
            numPassed++;
        } else {
            pref = "Fail:\t";
        }
        System.out.println(pref + name + ((same) ? "" : " got " + Arrays.toString(value)));
    }

    public static void main(String[] args) {
        Project proj = new MyProject();

        int[][] image = new int[][] {
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 1, 0, 1, 0 },
            { 255, 255, 255, 255, 255, 255, 255 },
            { 1, 2, 3, 4, 5, 6, 7 },
            { 7, 6, 5, 4, 3, 2, 1 },
        };

        String pref = "BrightestPixels ";

        testArray(pref + "example 1", proj.brightestPixelsInRowSegments(image, new int[][] {
            { 0, 0, 1 },
            { 0, 0, 7 },
            { 0, 3, 6 },
            { 0, 1, 3 },
            { 1, 0, 1 },
            { 1, 1, 2 },
            { 1, 0, 7 },
            { 1, 4, 7 },
            { 2, 0, 1 },
            { 2, 3, 6 },
            { 3, 0, 1 },
            { 3, 0, 2 },
            { 3, 0, 3 },
            { 3, 0, 4 },
            { 3, 0, 5 },
            { 3, 0, 6 },
            { 3, 0, 7 },
            { 3, 1, 2 },
            { 3, 2, 3 },
            { 3, 3, 4 },
            { 3, 5, 7 },
            { 4, 0, 1 },
            { 4, 0, 2 },
            { 4, 0, 3 },
            { 4, 0, 4 },
            { 4, 0, 5 },
            { 4, 0, 6 },
            { 4, 0, 7 },
            { 4, 1, 2 },
            { 4, 2, 3 },
            { 4, 3, 4 },
            { 4, 5, 7 }
        }), new int[] { 0, 0, 0, 0, 0, 1, 1, 1, 255, 255, 1, 2, 3, 4, 5, 6, 7, 2, 3, 4, 7, 7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 2 });

        image = new int[][] {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 1, 0, 1, 0, 1 },
            { 255, 255, 255, 255, 255, 255, 255, 255 },
            { 1, 2, 3, 4, 5, 6, 7, 8 },
            { 7, 6, 5, 4, 3, 2, 1, 0 },
        };

        testArray(pref + "example 2", proj.brightestPixelsInRowSegments(image, new int[][] {
            { 0, 0, 1 },
            { 0, 0, 8 },
            { 0, 3, 6 },
            { 0, 1, 3 },
            { 1, 0, 1 },
            { 1, 1, 2 },
            { 1, 0, 8 },
            { 1, 4, 8 },
            { 2, 0, 1 },
            { 2, 3, 6 },
            { 3, 0, 1 },
            { 3, 0, 2 },
            { 3, 0, 3 },
            { 3, 0, 4 },
            { 3, 0, 5 },
            { 3, 0, 6 },
            { 3, 0, 8 },
            { 3, 1, 2 },
            { 3, 2, 3 },
            { 3, 3, 4 },
            { 3, 5, 7 },
            { 4, 0, 1 },
            { 4, 0, 2 },
            { 4, 0, 3 },
            { 4, 0, 4 },
            { 4, 0, 5 },
            { 4, 0, 6 },
            { 4, 0, 7 },
            { 4, 1, 2 },
            { 4, 2, 3 },
            { 4, 3, 4 },
            { 4, 5, 7 }
        }), new int[] { 0, 0, 0, 0, 0, 1, 1, 1, 255, 255, 1, 2, 3, 4, 5, 6, 8, 2, 3, 4, 7, 7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 2 });

        System.out.println("Passed " + numPassed + "/" + numTests);
    }

}