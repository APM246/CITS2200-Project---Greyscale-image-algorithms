package tests;
import java.util.Arrays;
import src.MyProject;

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

    public static void main(String[] args) 
    {
        testPriorityQueue();

    }
}