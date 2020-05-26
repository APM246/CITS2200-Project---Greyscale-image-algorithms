/**
 * REMOVE src package
 */
// Arun Muthu (22704805)
package src;

import java.util.ArrayList;
import java.util.Arrays;
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
     * A custom heap implementation of a Priority Queue to be used in darkestPath(), a modified version
     * of Dijkstra's algorithm. 
     */
    private class PriorityQueueHeap {
        /**
         * Each sub-array stores the element and its priority (brightness). The lower the brightness, 
         * the higher the priority. 
         */
        private int[][] heap;
        private int n_elements; // number of elements currently stored in priority queue 
        /**
         * Allows for efficient implementations of contains() and changePriority(). Each index in the array
         * represents the corresponding element (in order of when they were enqueued). heap_position stores
         * each element's position in the heap. A -1 is stored if the element is currently not in the priority queue. 
         */
        private int[] heap_position; 

        /**
         * All elements in map will be initialised to -1 and changed to appropriate value when they are enqueued 
         * or re-enqueued. 
         * @param size the bounded size of the priority queue
         */
        public PriorityQueueHeap(int size) {
            heap = new int[size][2];
            n_elements = 0;
            heap_position = new int[size];
            Arrays.fill(heap_position, -1);
            Arrays.fill(heap, null);
        }

        /**
         * Checks if an element is in the priority queue
         * @param element the element to be checked. 
         * @return true iff the element is currently in the priority queue
         */
        public boolean contains(int element) {
            return heap_position[element] != -1;
        }

        /**
         * Removes the top element from the priority queue 
         * @return the value of the top element 
         */
        public int dequeue() {
            int temp = heap[0][0]; 
            heapifyDown();
            return temp;
        }

        /**
         * Adds an element to the priority queue. Enqueue is only called once to construct
         * the priority queue (when all priorities are set to infinity) and thus heapify() is not needed
         * here. 
         * @param element the element to be added
         * @param priority the priority (brightness) of the element 
         */
        public void enqueue(int element, int priority) {
            int index = n_elements;
            n_elements++;
            heap[index] = new int[] {element, priority};
            heap_position[element] = index;
        }

        /**
         * Changes the priority of an element and re-adjusts its position in the heap if necessary
         * @param element the element to change
         * @param brightness the new priority to be assigned to the element 
         */
        public void changePriority(int element, int brightness) {
            int index = heap_position[element];
            heap[index][1] = brightness;
            heapifyUp(index);
        }

        /**
         * Called exclusively by changePriority(). Starts at the element whose priority has just been 
         * changed and works upwards. At each level, the child is exchanged with the parent if it has a
         * better priority (lower brightness)
         * @param index the index of the element whose priority has been changed 
         */
        private void heapifyUp(int index) {
            boolean isInPlace = false; // set to true once element cannot be placed any higher in the tree 
            int child_brightness = heap[index][1];
            while (index != 0 && !isInPlace) {
                int parent = (index - 1)/2; // move up
                int parent_brightness = heap[parent][1];

                // lower brightness has higher priority 
                if (child_brightness < parent_brightness) {
                    // perform switch 
                    int[] temp = heap[parent];
                    heap[parent] = heap[index];
                    int child_element = heap[index][0];
                    int parent_element = temp[0];
                    heap[index] = temp;
                    heap_position[child_element] = parent;
                    heap_position[parent_element] = index;
                    index = parent;
                }
                else isInPlace = true;
            }
        }

        /**
         * Exclusively called by dequeue(). Start at the top of the priority queue
         * and set the element's priority to infinity. Repeated step: switch the node with
         * either the left or right child depending on priority. Repeat at
         * each level until the top element has reached the bottom. 
         * Note: child_index == left child, child_index + 1 == right child
         */
        private void heapifyDown() {
            int[] head = heap[0]; // dequeued element 
            head[1] = Integer.MAX_VALUE;
            heap_position[head[0]] = -1; // no longer in priority queue
            int parent_index = 0;
            int child_index = 1;
            while (child_index < heap.length) {
                int index;
                if (child_index + 1 >= heap.length) index = child_index; // right node doesn't exist 
                // Compare children, darker pixel will move up whilst other child remains unchanged 
                else index = (heap[child_index][1] < heap[child_index + 1][1]) ? child_index: child_index + 1;
                heap[parent_index] = heap[index];
                heap_position[heap[index][0]] = parent_index;
                heap[index] = head;

                // move down to next level 
                parent_index = index;
                child_index = 2*parent_index + 1;
            }
        }
    }

    /**
     * Implemented through a priority first search which prioritises lower brightness pixels. 
     * @param image The greyscale image as defined above
     * @param ur The row index of the start pixel for the path
     * @param uc The column index of the start pixel for the path
     * @param vr The row index of the end pixel for the path
     * @param vc The column index of the end pixel for the path
     * @return The minimum brightness of any path from (ur, uc) to (vr, vc)
     */
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
        int n_rows = image.length;
        int n_cols = image[0].length;
        int n_pixels = n_rows*n_cols;
        int[] brightness_key = new int[n_pixels]; // keeps track of brightest pixel each pixel has encountered thus far 
        // (in the path starting at (ur, uc) and eventually reaching (vr, vc))
        
        PriorityQueueHeap pqueue = new PriorityQueueHeap(n_pixels);
        for (int i = 0; i < n_pixels; i++) {
            pqueue.enqueue(i, Integer.MAX_VALUE); // elements converted into plain numbers instead of (r,c)
            brightness_key[i] = Integer.MAX_VALUE;
        }
        pqueue.changePriority(ur*n_cols + uc, image[ur][uc]); // starting pixel 
        brightness_key[ur*n_cols + uc] = image[ur][uc];

        while (true) {
            int element = pqueue.dequeue();
            if (element == vr*n_cols + vc) break; // (vr, vc) pixel has been dequeued and contains darkest path

            // adding left, right, top and bottom neighbours in that order (if possible)
            ArrayList<Integer> neighbours = new ArrayList<>();
            if (element % n_cols != 0) neighbours.add(element - 1);
            if (element % n_cols != n_cols - 1) neighbours.add(element + 1);
            if (element / n_cols != n_rows - 1) neighbours.add(element + n_cols);
            if (element / n_cols != 0) neighbours.add(element - n_cols);

            for (int neighbour: neighbours) {
                if (pqueue.contains(neighbour)) {
                    // the brightest pixel encountered thus far by a neighbour is either the brightest pixel 
                    // encountered by the current dequeued element or the brightness of the neighbour itself
                    int max_brightness = Math.max(brightness_key[element], image[neighbour/n_cols][neighbour%n_cols]);
                    // If the brightness is darker, this indicates a better priority. Thus perform relaxation and update pqueue 
                    if (max_brightness < brightness_key[neighbour]) {
                        brightness_key[neighbour] =  max_brightness;
                        pqueue.changePriority(neighbour, max_brightness);
                    }
                }
            }
        }
        return brightness_key[vr*n_cols + vc]; // contains brightness of darkest path encountered by (vr, vc) pixel
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