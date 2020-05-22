package tests;
import src.*;
import java.util.Random;
import java.util.ArrayList;

public class FloodCountTest
{
    public static class coord
    {
        public int r;
        public int c;
    
        public coord(int r, int c) 
        {
            this.r = r;
            this.c = c;
        }
    
        public boolean equals(Object other)
        {
            if (other == null || !(other instanceof coord)) {
                return false;
            }
        
            coord coord = (coord) other;
            return this.r == coord.r && this.c == coord.c;
        }
    }

    public static void Print(int[][] image)
    {
        for (int r = 0; r < image.length; r++)
        {
            for (int c = 0; c < image[0].length; c++)
            {
                System.out.format("%d ", image[r][c]);
            }
            System.out.println();
        }
    }

    public static boolean InBounds(int width, int height, int r, int c)
    {
        return r >= 0 && c >= 0 && r < height && c < width;
    }

    public static int NumNeighbours(int[][] image, int r, int c, int shade)
    {
        int[][] offsets = {
            { 1,  0 },
            { 0,  1 },
            {-1,  0 },
            { 0, -1 },
        };

        int count = 0;
        for (int i = 0; i < 4; i++)
        {
            int roff = r + offsets[i][0];
            int coff = c + offsets[i][1];
            if (InBounds(image[0].length, image.length, roff, coff) && image[roff][coff] == shade)
            {
                count++;
            }
        }
        return count;
    }

    public static boolean IsInteresting(int[][] image, int r, int c, int shade)
    {
        return NumNeighbours(image, r, c, shade) == 1;
    }

    public static boolean IsValid(int[][] image, coord loc)
    {
        int[][] offsets = {
            { 1,  0 },
            { 0,  1 },
            {-1,  0 },
            { 0, -1 },
        };
        int shade = image[loc.r][loc.c];
        for (int i = 0; i < 4; i++)
        {
            int roff = loc.r + offsets[i][0];
            int coff = loc.c + offsets[i][1];
            if (InBounds(image[0].length, image.length, roff, coff) && IsInteresting(image, roff, coff, shade))
                return true;
        }
        return false;
    }

    public static int[][] GenerateTestCase(int r, int c, int sr, int sc, int count, int shade)
    {
        int[][] image = new int[r][c];
        assert(count <= r * c);

        Random rand = new Random();
        for (int x = 0; x < r; x++)
        {
            for (int y = 0; y < c; y++)
            {
                do
                {
                    image[x][y] = rand.nextInt(5);
                }
                while (image[x][y] == shade);
            }
        }
        
        int i = 1;
        ArrayList<coord> list = new ArrayList<coord>();
        list.add(new coord(sr, sc));
        image[sr][sc] = shade;

        int[][] offsets = {
            { 1,  0 },
            { 0,  1 },
            {-1,  0 },
            { 0, -1 },
        };
        int iterations = 0;
        while (i < count)
        {
            iterations++;
            coord loc = list.get(rand.nextInt(list.size()));
            if (!IsValid(image, loc))
            {
                list.remove(loc);
                continue;
            }

            int dir = rand.nextInt(4);
            
            int row = loc.r + offsets[dir][0];
            int col = loc.c + offsets[dir][1];
            coord newLoc = new coord(row, col);
            
            if (!InBounds(image[0].length, image.length, row, col) || list.contains(newLoc) || 
                !IsInteresting(image, row, col, shade) || image[row][col] == shade)
            {
                continue;
            }

            i++;
            image[row][col] = shade;
            list.add(newLoc);
        }
        
        // Print(image);
        // System.out.println("#Iterations = " + iterations);
        return image;
    }

    public static int passed = 0;
    public static int num_tests = 1000;

    public static void main(String[] args)
    {
        Random rand = new Random();
        MyProject proj = new MyProject();
        for (int i = 0; i < num_tests; i++)
        {
            int width = 50 + rand.nextInt(50);
            int height = 50 + rand.nextInt(50);
            int ans = 1 + rand.nextInt(width * height / 2);
            int[][] image = GenerateTestCase(height, width, height / 2, width / 2, ans, 1);
            if (proj.floodFillCount(image, height / 2, width / 2) == ans)
                passed++;
        }

        System.out.println("Passed " + passed + "/" + num_tests + " tests");
    }
}