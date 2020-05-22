/**
 * REMOVE src package
 */
// Arun Muthu (22704805)
package src;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Class that implements the 4 algorithms outlined in the Project interface
 */
public class MyProject implements Project {

    /**
     * Creates an instance of MyProject that can execute the 4 algorithms
     */
    public MyProject() {}

    /**
     * Uses a depth-first search to perform a floodFillCount
     * @param image The greyscale image as defined above
     * @param row The row index of the pixel to flood-fill from
     * @param col The column index of the pixel to flood-fill from
     * @return The number of pixels that changed colour when performing this operation
     */
    public int floodFillCount(int[][] image, int row, int col) {
        int colour = image[row][col];
        if (colour == 0) return 0; // pixel and contiguous neighbours are already black thus no conversion needed 
        Stack<int[]> stack = new Stack<>();
        int n_rows = image.length;
        int n_cols = image[0].length;

        int count = 0;
        stack.push(new int[] {row, col});
        image[row][col] = 0;

        while (!stack.isEmpty()) {
            int[] pixel = stack.pop();
            int new_row = pixel[0]; int new_col = pixel[1];

            // Add pixel above if possible 
            if (new_row > 0) {
                if (image[new_row - 1][new_col] == colour) 
                {
                    image[new_row - 1][new_col] = 0;
                    stack.push(new int[] {new_row - 1, new_col});
                }
            }

            // Add pixel below if possible 
            if (new_row < n_rows - 1)
            {
                if (image[new_row + 1][new_col] == colour) 
                {
                    image[new_row + 1][new_col] = 0;
                    stack.push(new int[] {new_row + 1, new_col});
                }
            }

            // Add left pixel if possible 
            if (new_col > 0) {
                if (image[new_row][new_col - 1] == colour) 
                {
                    image[new_row][new_col - 1] = 0;
                    stack.push(new int[] {new_row, new_col - 1});
                }
            }

            // Add right pixel if possible 
            if (new_col < n_cols - 1) {
                if (image[new_row][new_col + 1] == colour) 
                {
                    image[new_row][new_col + 1] = 0;
                    stack.push(new int[] {new_row, new_col + 1});
                }
            }

            count++; // pixel has now been processed, hence increase count
        }

        return count;
    }

    /**
     * An implementation inspired by Kadane's algorithm to find the brightest
     * square. 
     * @param image The greyscale image as defined above
     * @param k the dimension of the squares to consider
     * @return The total brightness of the brightest square
     */
    public int brightestSquare(int[][] image, int k) {
        int n_rows = image.length;
        int n_cols = image[0].length;
        int[][] row_sums = new int[n_rows][n_cols - k + 1];
        // row_sums will contain sums of subarrays of length k in each row. There are n_cols - k + 1 possible subarrays of length k in each row 
        // Start with subarray starting at index 0 of the row
        for (int i = 0; i < n_rows; i++) {
            int row_sum = 0;
            for (int j = 0; j < k; j++) {
                row_sum += image[i][j];
            }

            row_sums[i][0] = row_sum;

            // Now caclulate the sum of the other subrows of length k within the same row (progress by 1 index each time)
            // To save time, remove earliest element from row_sum and add latest element 
            for (int j = 1; j < n_cols - k + 1; j++) {
                row_sum += image[i][j + k - 1] - image[i][j-1];
                row_sums[i][j] = row_sum;
            }
            // Move onto next row 
        }

        int max_sum = 0;
        // Calculate sum of every possible k*k square and keep track of maximum sum
        // Start with squares where left-most column is column 0. Start at row 0 and progress by 1 index each time. 
        // Move onto next left-most column once all rows covered for a given column.
        for (int i = 0; i < n_cols - k + 1; i++) {
            int current_sum = 0;
            // Calculate the sum of square where the top-most row is row 0
            for (int j = 0; j < k; j++) {
                current_sum += row_sums[j][i];
            }

            max_sum = Math.max(current_sum, max_sum);
            // Calculate sum of rest of squares with the same left-most column by moving downwards
            // To save time remove top row and add bottom row 
            for (int m = 1; m < n_rows - k + 1; m++) {
                current_sum += row_sums[m + k - 1][i] - row_sums[m - 1][i];
                max_sum = Math.max(current_sum, max_sum);
            }
            // move to next left-most column
        }

        return max_sum;
    }

    /**
     * 
     */
    private class PriorityQueueBlock {
        /**
         * Each sub-array stores the element and its priority 
         */
        private int[][] heap;
        private int end;
        private LinkedList<Integer> list_of_gaps;
        private int[] map;

        public PriorityQueueBlock(int size) {
            heap = new int[size][2];
            end = 0;
            list_of_gaps = new LinkedList<Integer>();
            map = new int[size];
            Arrays.fill(map, -1);
            Arrays.fill(heap, null);
        }

        public boolean contains(int element) {
            return map[element] != -1;
        }

        public int dequeue() {
            int temp = heap[0][0];
            // start from top and call heapify() at each level
            /*int current_node = 0;
            heap[current_node][1] = Integer.MAX_VALUE;
            while (2*current_node + 2 < end) {
                int left_child = 2*current_node + 1;
                int right_child = left_child + 1;
                current_node = (heap[left_child][1] < heap[right_child][1]) ? left_child : right_child; 
                heapifyUp(current_node);
            }
            map[current_node] = -1;*/
            heapifyDown();
            return temp;
        }

        public void enqueue(int element, int priority) {
            int index;
            if (list_of_gaps.isEmpty()) {
                index = end;
                end++;
            }
            else index = list_of_gaps.remove();
        
            heap[index] = new int[] {element, priority};
            map[element] = index;
            heapifyUp(index);
        }

        public void changePriority(int element, int brightness) {
            int index = map[element];
            heap[index][1] = brightness;
            heapifyUp(index);
        }

        public void printArray() {
            for (int[] row: heap) {
                System.out.println(Arrays.toString(row));
            }
        }

        private void heapifyUp(int index) {
            boolean isInPlace = false;
            int child_brightness = heap[index][1];
            while (index != 0 && !isInPlace) {
                int parent = (index - 1)/2;
                int parent_brightness = heap[parent][1];

                // lower brightness has higher priority 
                if (child_brightness < parent_brightness) {
                    int[] temp = heap[parent];
                    heap[parent] = heap[index];
                    int child_element = heap[index][0];
                    int parent_element = temp[0];
                    heap[index] = temp;
                    map[child_element] = parent;
                    map[parent_element] = index;
                    index = parent;
                }
                else isInPlace = true;
            }
        }

        // exclusively called by dequeue()
        private void heapifyDown() {
            int parent_index = 0;
            int child_index = 1;
            while (child_index < heap.length) { // issue 
                int[] left = heap[child_index];
                int[] right;
                if (child_index + 1 >= heap.length) right = null;
                else right = heap[child_index + 1];
                if (left == null) {
                    // heapify property restored
                    if (right == null) break;
                    else {
                        heap[parent_index] = right;
                        map[right[0]] = parent_index;
                        heap[child_index + 1] = null;
                        parent_index = child_index + 1;
                    }
                }
                else {
                    if (right == null) {
                        heap[parent_index] = left;
                        map[left[0]] = parent_index;
                        heap[child_index] = null;
                        parent_index = child_index;
                    }
                    else {
                        int index = (left[1] < right[1]) ? child_index: child_index + 1;
                        heap[parent_index] = heap[index];
                        map[heap[index][0]] = parent_index;
                        heap[index] = null;
                        parent_index = index;
                    }
                }
                child_index = 2*parent_index + 1;
            }
        }
    }

    // don't really need to keep converting between column, row and single number, keep consistent, are two arrays necessary for
    // comparison in while loop? 
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
        int n_pixels = image.length*image[0].length;
        int n_rows = image.length;
        int n_cols = image[0].length;
        int[] pixel_value = new int[n_pixels];
        int[] brightness_key = new int[n_pixels];
        int count = 0;
        for (int i = 0; i < n_rows; i++) {
            for (int j = 0; j < n_cols; j++) {
                pixel_value[count++] = image[i][j];
            }
        }
        
        PriorityQueueBlock pqueue = new PriorityQueueBlock(n_pixels);
        for (int i = 0; i < n_pixels; i++) {
            pqueue.enqueue(i, Integer.MAX_VALUE);
            brightness_key[i] = Integer.MAX_VALUE;
        }
        pqueue.changePriority(ur*n_cols + uc, pixel_value[ur*n_cols + uc]);
        brightness_key[ur*n_cols + uc] = pixel_value[ur*n_cols + uc];
        //brightness_key[vr*n_cols + vc] = pixel_value[vr*n_cols + vc];

        //boolean isFound = false;
        while (true) {
            int element = pqueue.dequeue();
            if (element == vr*n_cols + vc) break;
            // left, right, bottom and top neighbours in that order 
            int[] neighbours = new int[] {element - 1, element + 1, element + n_cols, element - n_cols};

            for (int neighbour: neighbours) {
                if (neighbour >= 0 && neighbour < n_pixels) {
                    if (pqueue.contains(neighbour)) {
                        int max_brightness = Math.max(brightness_key[element], pixel_value[neighbour]);
                        if (max_brightness < brightness_key[neighbour]) {
                            brightness_key[neighbour] =  max_brightness;
                            pqueue.changePriority(neighbour, max_brightness);
                        }
                    }
                }
            }
        }
        return brightness_key[vr*n_cols + vc];
    }

    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
        int[] result = new int[queries.length];
        for (int j = 0; j < queries.length; j++) {
            int max = 0;
            int row = queries[j][0];
            int left = queries[j][1];
            int right = queries[j][2];
            for (int i = left; i < right; i++) {
                if (image[row][i] > max) max = image[row][i];
            }
            result[j] = max;
        }

        return result;
    }
}