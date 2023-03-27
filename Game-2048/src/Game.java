import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {
    private int score = 0;
    public int maximum = 0;
    private int counter = 0;
    private static int[][] area;
    private int goal;
    private static final List<Integer> POSSIBLE_GOALS = Arrays.asList(8, 16, 32, 64, 128, 256, 512, 1024, 2048);

    public Game(){
        area = new int[4][4];
        goal = 16;
    }

    public void initGame2048(){
        for (int i = 0; i < 4; i++) { // i will be row
            for (int j = 0; j < 4; j++) { // j will be column
                area[i][j] = 0;
            }
        }
        int firstElement = (int) (0 + Math.random() * 4);
        int secondElement = (int) (0 + Math.random() * 4);
        area[firstElement][secondElement] = 2;

        for (int  i = 0; i < 4; i++) {
            boolean added = true;
            for (int j = 0; j < 4; j++) {
                if (area[i][j] == 0) {
                    firstElement = (int) (0 + Math.random() * 4);
                    secondElement = (int) (0 + Math.random() * 4);
                    added = false;
                    break;
                }
            }
            if (!added) {
                break;
            }
        }
        area[firstElement][secondElement] = 2;
        counter++;
        score = 0;
        maximum = 0;
    }

    private boolean isEmpty() { //check for numbers
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (area[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveLeft(){
        for (int i = 0; i < 4; i++) { //move without multiplication
            for (int j = 0; j < 4; j++) {
                for (int k = j; k > 0 ; k--) {
                    if(area[i][k - 1] == 0){
                        area[i][k - 1] = area[i][k];
                        area[i][k] = 0;
                    }
                }
            }

            for (int j = 0; j < 3; j++) {//move with multiplication
                if (area[i][j] != 0 && area[i][j] == area[i][j + 1]) {//checking if numbers can be multiplied
                    area[i][j] *= 2;
                    score += area[i][j];
                    for (int k = j + 1; k < 3; k++) {
                        area[i][k] = area[i][k + 1];
                    }
                    area[i][3] = 0;
                }
            }
        }
    }

    public void moveRight(){
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                for (int k = j; k < 3; k++) {
                    if(area[i][k + 1] == 0){
                        area[i][k + 1] = area[i][k];
                        area[i][k] = 0;
                    }
                }
            }
            for (int j = 3; j > 0; j--) {
                if (area[i][j] != 0 && area[i][j] == area[i][j - 1]) {
                    area[i][j] *= 2;
                    score += area[i][j];
                    for (int k = j - 1; k > 0; k--) {
                        area[i][k] = area[i][k - 1];
                    }
                    area[i][0] = 0;
                }
            }
        }
    }

    public void moveDown(){
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                for (int k = j; k < 3; k++) {
                    if (area[k + 1][i] == 0) {
                        area[k + 1][i] = area[k][i];
                        area[k][i] = 0;
                    }
                }
            }
            for (int j = 3; j > 0; j--) {
                if (area[j][i] != 0 && area[j][i] == area[j - 1][i]) {
                    area[j][i] *= 2;
                    score += area[j][i];
                    for (int k = j - 1; k > 0; k--) {
                        area[k][i] = area[k - 1][i];
                    }
                    area[0][i] = 0;
                }
            }
        }
    }
    public void moveUp(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = j; k > 0; k--) {
                    if (area[k - 1][i] == 0) {
                        area[k - 1][i] = area[k][i];
                        area[k][i] = 0;
                    }
                }
            }
            for (int j = 0; j < 3; j++) {
                if (area[j][i] != 0 && area[j][i] == area[j + 1][i]) {
                    area[j][i] *= 2;
                    score += area[j][i];
                    for (int k = j + 1; k < 3; k++) {
                        area[k][i] = area[k + 1][i];
                    }
                    area[3][i] = 0;
                }
            }
        }
    }
    public boolean goalCheck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (area[i][j] > maximum) {
                    maximum = area[i][j];
                }
            }
        }
        return maximum == goal;
    }
    public void random() {
        boolean added = true;
        if (isEmpty()) {
            while (added) {
                int firstElement = (int) (0 + Math.random() * 4);
                int secondElement = (int) (0 + Math.random() * 4);
                if (area[firstElement][secondElement] == 0) {
                    if (counter % 5 == 0) {
                        area[firstElement][secondElement] = 4;
                    } else {
                        area[firstElement][secondElement] = 2;
                    }
                    added = false;
                }
            }
        }
        counter++;
    }
    public void changeGoalMinus() {
        int index = POSSIBLE_GOALS.indexOf(goal);
        if (goal > 8) {
            index--;
            goal = POSSIBLE_GOALS.get(index);
            initGame2048();
        }
    }

    public void changeGoalPlus() {
        int index = POSSIBLE_GOALS.indexOf(goal);
        if (goal < 2048) {
            index++;
            goal = POSSIBLE_GOALS.get(index);
            initGame2048();
        }
    }
    public boolean isLose() {
        boolean up = false;
        boolean right = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j > 0; j--) {
                if (area[i][j] == area[i][j -1]) {
                    right = true;
                    break;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j > 0; j--) {
                if (area[j - 1][i] == area[j][i]) {
                    up = true;
                    break;
                }
            }
        }
        return !right && !up && !isEmpty();
    }

    public int getValue(int i, int j) {
        return area[i][j];
    }

    public int getScore() {
        return score;
    }

    public int getGoal() {
        return goal;
    }
}
