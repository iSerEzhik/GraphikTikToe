import java.util.Scanner;

public class TikToe {
    private static final int size = 3;
    private static final char empty = '*';
    private static final char circle = 'O';
    private static final char cross = 'X';
    private static final char[][] map = new char[size][size];
    private static Scanner scan = new Scanner(System.in);
    private static int countTurns=0;
    private static char winner = empty;

    public void newMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = empty;
            }
        }
    }

    public static boolean isDraw() {
        boolean draw = false;
        for (char[] lineC : map) {
            for (char c : lineC
            ) {
                if (c != empty) {
                    draw = true;
                } else return false;
            }
        }
        return draw;
    }

    public static boolean ifEmpty(int x, int y) {
        return map[x][y] == empty;
    }

    public static boolean checkWin(char[][] map) {
        int k = 1;
        boolean winMainD = false;
        boolean winExtraD = false;
        boolean winHor = false;
        boolean winVer = false;
        int column, line;
        for (column = 0; column < size; column++) {
            if (k == 3) break;
            for (line = 1; line < size; line++) {
                if (map[line][column] == map[line-1][column] && map[line][column] != empty) {
                    winner = map[line][column];
                    winVer = true;
                    k++;
                } else {
                    winner = empty;
                    k = 1;
                    winVer = false;
                    break;
                }
            }
        }
        if (winVer) return winVer;
        for (line = 0; line < size; line++) {
            if (k == 3) break;
            for (column = 1; column < size; column++) {
                if (map[line][column] == map[line][column - 1] && map[line][column] != empty) {
                    k++;
                    winner = map[line][column];
                    winHor = true;
                } else {
                    winner = empty;
                    winHor = false;
                    k = 1;
                    break;
                }
            }
        }
        if (winHor) return winHor;
        for (column = 1; column < size; column++) {
            if (map[column][column] == map[column - 1][column - 1] && map[column][column] != empty) {
                winner = map[column][column];
                winMainD = true;

            } else {
                winner = empty;
                winMainD = false;
                break;
            }
        }
        if (winMainD) return winMainD;
        for (column = size - 1; column > 0; column--) {
            if (map[size - column - 1][column] == map[size - column][column - 1] && map[size - column - 1][column] != empty) {
                winner = map[size - column - 1][column];
                winExtraD = true;
            } else {
                winner = empty;
                return false;
            }
        }
        return winExtraD;
    }

    public static int[] findCross() {
        int[] kor = new int[2];
        int i = 0, j = 0;
        while (map[i][j] != cross) {
            j++;
            if (j == size) {
                i++;
                j = 0;
            }
        }
        kor[0] = i;
        kor[1] = j;
        return kor;
    }
    public static boolean isWin(){
        return checkWin(map);
    }
    public static boolean ifNextStepWin(int[] kor) {
        if (kor[0] != -1) return true;
        else return false;
    }

    public static int[] nextStepWin(char who) {
        int[] kor = new int[2];
        boolean t = false;
        char[][] copyMap = new char[size][size];
        int i = 0, j = 0;
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                copyMap[i][j] = map[i][j];
            }
        }
        int k = 1;
        for (i = 0; i < size; i++) {
            if (t == true) break;
            for (j = 0; j < size; j++) {
                if (copyMap[i][j] == empty)
                    copyMap[i][j] = who;

                else continue;
                if (checkWin(copyMap)) {
                    t = true;
                    break;
                } else copyMap[i][j] = empty;

            }

        }
        if (t == true) {
            kor[0] = --i;
            kor[1] = j;
        } else {
            kor[0] = -1;
            kor[1] = -1;
        }
        return kor;

    }


    public static int[] robotStep() {
        int[] kor;
        int i, j;
        if (ifNextStepWin(nextStepWin(circle))) {
            kor = nextStepWin(circle);
            map[kor[0]][kor[1]] = circle;
            return kor;
        } else if (ifNextStepWin(nextStepWin(cross))) {
            kor = nextStepWin(cross);
            map[kor[0]][kor[1]] = circle;
            return kor;
        } else {
            kor = findCross();
            i = kor[0];
            j = kor[1];
            int k = 1;
            while (k < size) {
                if (i - k > 0 && j - k > 0 && map[i - k][j - k] == empty) {
                    i = i - k;
                    j = j - k;
                    break;
                } else if (i + k < size && j - k > 0 && map[i + k][j - k] == empty) {
                    i = i + k;
                    j = j - k;
                    break;
                } else if (i - k > 0 && j + k < size && map[i - k][j + k] == empty) {
                    i = i - k;
                    j = j + k;
                    break;
                } else if (i + k < size && j + k < size && map[i + k][j + k] == empty) {
                    i = i + k;
                    j = j + k;
                    break;
                } else if (i + k < size && map[i + k][j] == empty) {
                    i = i + k;
                    break;
                } else if (i - k > 0 && map[i - k][j] == empty) {
                    i = i - k;
                    break;
                } else if (j + k < size && map[i][j + k] == empty) {
                    j += k;
                    break;
                } else if (j - k > 0 && map[i][j - k] == empty) {
                    j = j - k;
                    break;
                } else k++;
            }
            if (ifEmpty(i, j)) {
                map[i][j] = circle;
                kor[0] = i;
                kor[1] = j;
                return kor;
            } else {
                kor[0] = -1;
                kor[1] = -1;
                return kor;
            }
        }
    }

    public static boolean humanStep(int[] kor) {
        if (ifEmpty(kor[0], kor[1]))
            map[kor[0]][kor[1]] = cross;
        return ifEmpty(kor[0], kor[1]);
    }
    public static int getSize(){
        return size;
    }
    public static void printMap(){
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.println();
            for (int j = 0; j < size; j++)
            {

                System.out.print(map[i][j]+" ");
            }

        }
    }
    public static void incCountTurns(){
        countTurns++;
    }


    public static boolean isHumanStep(){
        return countTurns%2==1;
    }

    public static int getCountTurns() {
        return countTurns;
    }

    public static char getWinner() {
        return winner;
    }
}