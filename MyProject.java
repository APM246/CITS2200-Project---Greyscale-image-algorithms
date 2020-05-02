import java.util.Stack;

public class MyProject implements Project {

    public MyProject() {}

    // could avoid use of isVisited by changing value in image array
    public int floodFillCount(int[][] image, int row, int col) {
        int[][] image_copy = image.clone();
        int colour = image_copy[row][col];
        if (colour == 0) return 0;
        Stack<int[]> stack = new Stack<>();
        int n_rows = image_copy.length;
        int n_cols = image_copy[0].length;

        int count = 0;
        stack.push(new int[] {row, col});
        image_copy[row][col] = 0;

        while (!stack.isEmpty()) {
            int[] pixel = stack.pop();
            int new_row = pixel[0]; int new_col = pixel[1];
            count++;

            if (new_row > 0) {
                if (image_copy[new_row - 1][new_col] == colour) 
                {
                    image_copy[new_row - 1][new_col] = 0;
                    stack.push(new int[] {new_row - 1, new_col});
                }
            }

            if (new_row < n_rows - 1)
            {
                if (image_copy[new_row + 1][new_col] == colour) 
                {
                    image_copy[new_row + 1][new_col] = 0;
                    stack.push(new int[] {new_row + 1, new_col});
                }
            }

            if (new_col > 0) {
                if (image_copy[new_row][new_col - 1] == colour) 
                {
                    image_copy[new_row][new_col - 1] = 0;
                    stack.push(new int[] {new_row, new_col - 1});
                }
            }

            if (new_col < n_cols - 1) {
                if (image_copy[new_row][new_col + 1] == colour) 
                {
                    image_copy[new_row][new_col + 1] = 0;
                    stack.push(new int[] {new_row, new_col + 1});
                }
            }
        }

        return count;
    }

    public int brightestSquare(int[][] image, int k) {
        int max_sum = 0;
        int rows = image.length;
        int cols = image[0].length;
        for (int i = 0; i < rows - k + 1; i++) {
            for (int j = 0; j < cols - k + 1; j++) {
                int sum = 0;
                for (int m = 0; m < k; m++) {
                    for (int n = 0; n < k; n++) {
                        sum += image[i+m][j+n];
                    }
                }
                if (sum > max_sum) max_sum = sum;
            }
        }

        return max_sum;
    }

    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
        return -1;
    }

    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
        return null;
    }

    public static void main(String[] args) 
    {
        
    }
}