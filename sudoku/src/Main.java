public class Main {
    public static void main(String[] args) {
        byte sudoku[][] = {
                {0, 0, 0, 5, 0, 0, 0, 0, 0},
                {0, 7, 1, 0, 2, 0, 0, 9, 0},
                {0, 5, 0, 0, 9, 0, 0, 0, 0},

                {8, 0, 0, 3, 6, 0, 0, 0, 1},
                {0, 9, 3, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 5, 0},

                {0, 0, 0, 0, 0, 0, 0, 8, 9},
                {0, 4, 0, 0, 0, 2, 7, 6, 0},
                {2, 0, 0, 6, 0, 0, 5, 0, 0}};

        printSudoku(sudoku);
        if (isValid(sudoku))
            System.out.println("ok!");
        else
            System.out.println("Illegal puzzle!");
    }

    private static void printSudoku(byte[][] sudoku) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (sudoku[i][j] == 0)
                    System.out.print(". ");
                else
                    System.out.print(sudoku[i][j] + " ");

                if (j % 3 == 2)
                    System.out.print("| ");
            }
            System.out.println();
            if (i % 3 == 2)
                System.out.println("-----------------------");
        }
    }

    private static boolean isValid(byte[][] sudoku) {
            /* for(byte[] r :sudoku)
                for(int j = 0; j < r.length; j ++)
                    if (r[j] != 0)
                        for (int jright = j + 1; jright < r.length; jright++)
                            if (r[jright] == r[j])
                                return false;
            return true;
        }*/
        for (byte[] r : sudoku) {
            boolean digitSet[] = new boolean[10];
            for (byte c : r)
                if (c != 0 && digitSet[c])
                    return false;
                else
                    digitSet[c] = true;

        }
        return true;
    }
}
