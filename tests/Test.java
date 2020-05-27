package tests;
import java.util.Arrays;
import src.MyProject;
import java.util.Random;

public class Test {

    private void printArray(int[][] arr) {
        for (int[] row: arr) {
            System.out.println(Arrays.toString(row));
        }
    }

    public void brightestSquareTest() {
        int[][] image = new int[][] {
            { 0, 0, 1, 1, 1 },
            { 0, 1, 1, 2, 2 },
            { 2, 3, 3, 0, 2 },
            { 2, 2, 2, 2, 2 }
        };

        MyProject project = new MyProject();
        System.out.println(project.brightestSquare(image, 3));
    }

    public static void testPriorityQueue() {
        int[][] image = new int[][] {
            { 0, 0, 1, 1, 1 },
            { 0, 1, 1, 2, 2 },
            { 2, 3, 3, 0, 2 },
            { 2, 2, 2, 2, 2 }
        };
        MyProject project = new MyProject();
        project.darkestPath(image, 0, 0, 2, 3);
    }

    public static int[] yikes(int[][] image, int[][] queries) {
        long start = System.currentTimeMillis();
        int[] result = new int[queries.length];
        for (int j = 0; j < queries.length; j++) {
            int max = 0;
            int row = queries[j][0];
            int left = queries[j][1];
            int right = queries[j][2];
            // finds maximum element in row segment 
            for (int i = left; i < right; i++) {
                if (image[row][i] > max) max = image[row][i];
            }
            result[j] = max;
            result[j] = max; // maximum element appended to result array
        }
        System.out.println((System.currentTimeMillis() - start)/1000 + " seconds for QC");
        return result;
    }

    public static void main(String[] args) 
    {
        //testPriorityQueue();
        // for (int[] wow: bintree.getHeap()) System.out.println(Arrays.toString(wow));
        Random random = new Random();
        int num_elements = 200;
        int num_rows = 200;
        int num_queries = 50000000;
        int[][] image = new int[num_rows][num_elements];
        int[][] queries = new int[num_queries][3];
        for (int j = 0; j < num_rows; j++) {
            for (int i = 0; i < num_elements; i++) image[j][i] = random.nextInt(2560); 
        }

        for (int i = 0; i < num_queries; i++) {
            int num1 = 0;
            int num2 = -1;
            while (num1 > num2) {
                num1 = random.nextInt(num_elements);
                num2 = random.nextInt(num_elements);
            }
            queries[i] = new int[] {random.nextInt(num_rows), num1, num2};
        }

        long start = System.currentTimeMillis();
        int answer1 = new MyProject().brightestPixelsInRowSegments(image, new int[][] {{0,4534,image[0].length}})[0];
        System.out.println((System.currentTimeMillis() - start)/1000 + " seconds");
        int answer2 = yikes(image, new int[][] {{0,4534,image[0].length}})[0];
        System.out.println("Answer is " + answer1 + ", " + answer2);
    }
}