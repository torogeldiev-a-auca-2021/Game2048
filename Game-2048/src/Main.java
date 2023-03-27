import processing.core.*;

// if application does not work , try to recompile it
public class Main extends PApplet {
    public static float x;
    public static float y;
    public static float elementSize;
    Game game;

    public void settings() {
        fullScreen();
    }

    public void setup() {
        x = width / 2f - 300;
        y = height / 2f - 300;
        elementSize = 150;

        noStroke();
        game = new Game();
        game.initGame2048();
    }

    public void draw() {
        background(0,0,0);
        noStroke();
        drawBoard();
    }

    private void drawBoard() {
        drawText();
        drawScore();
        drawBestScore();
        setGoal();
        currentGoal();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                getColor(i, j);
                drawElement(i, j);
                fill(255, 50, 255);
                textSize(100);
                if (game.getValue(i, j) != 0) {
                    drawNumber(i,j);
                }
            }
        }
        drawBorder();
        if (game.goalCheck()) {
            drawWinTable();
        }
        if (game.isLose()) {
            drawLoseTable();
        }
    }

    private void drawBorder() {
        strokeWeight(4);
        stroke(0);
        noFill();
        rect(0, 0, width, height);
    }

    private void getColor(int i, int j) {
        switch (game.getValue(i,j)) {
            case 2:
                fill(238, 228, 218);
            case 4:
                fill(238, 228, 218);
            case 8:
                fill(242, 177, 121);
            case 16:
                fill(245, 149, 99);
            case 32:
                fill(246, 124, 95);
            case 64:
                fill(246, 95, 59);
            case 128:
                fill(237, 207, 114);
            case 256:
                fill(237, 204, 97);
            case 512:
                fill(237, 200, 80);
            case 1024:
                fill(237, 197, 63);
            case 2048:
                fill(237, 194, 46);
            default:
                fill(205, 193, 180);
        }
    }
    private void drawElement(int i, int j) {
        stroke(150);
        strokeWeight(8);
        square(x + elementSize * j, y + i * elementSize, elementSize);
    }

    private void drawNumber(int i, int j){
        switch (game.getValue(i,j)){
            case 2:
            case 4:
            case 8:
                fill(242, 177, 121);
                text(game.getValue(i,j), x + j * elementSize + elementSize / 2 - 25, y + i * elementSize + elementSize / 2 + 25);
                break;
            case 16:
            case 32:
            case 64:
                fill(game.getValue(i,j));
                text(game.getValue(i,j), x + j * elementSize + elementSize / 2 - 50, y + i * elementSize + elementSize / 2 + 25);
                break;
            case 128:
            case 256:
            case 512:
                fill(game.getValue(i,j));
                textSize(80);
                text(game.getValue(i,j), x + j * elementSize + elementSize / 2 - 60, y + i * elementSize + elementSize / 2 + 25);
                break;
            default:
                fill(game.getValue(i,j));
                textSize(60);
                text(game.getValue(i,j), x + j * elementSize + elementSize / 2 - 60, y + i * elementSize + elementSize / 2 + 20);
        }
    }
    public void drawText() {
        fill(255, 255, 0);
        textSize(100);
        strokeWeight(3);
        text("Game2048", x + 50, 90);
    }
    public void keyPressed() {
        switch (keyCode) {
            case UP:
                game.moveUp();
                game.random();
                break;
            case DOWN:
                game.moveDown();
                game.random();
                break;
            case RIGHT:
                game.moveRight();
                game.random();
                break;
            case LEFT:
                game.moveLeft();
                game.random();
                break;
        }
        if (key == '+') {
            game.changeGoalPlus();
        }
        if (key == '-') {
            game.changeGoalMinus();
        }
    }
    private void setGoal() {
        fill(255, 255, 0);
        textSize(50);
        strokeWeight(2);
        text("Change Goal: +,-", width / 2.5f, height - 50);
    }
    private void drawScore() {
        fill(255, 255, 0);
        textSize(80);
        strokeWeight(3);
        text("Score: " + game.getScore(), x + 650, (height / 2f) );
    }
    private void drawBestScore() {
        fill(255, 255, 0);
        textSize(60);
        strokeWeight(2);
        text("Best: " + game.getScore(), x + 650, (height / 2f) + 100);
    }
    private void drawWinTable() {
        if (key != ENTER) {
            pushMatrix();
            translate(x, y + 200);
            noStroke();
            fill(20, 20, 150);
            rect(0, 0, 600, 200);
            fill(255, 255, 0);
            textSize(50);
            text("You Reached The Goal: ", 25, 50);
            text("Press enter to continue", 25, 150);
            popMatrix();
        } else {
            game.maximum = 10000;
        }
    }
    private void currentGoal(){
        fill(255, 255, 0);
        textSize(80);
        strokeWeight(2);
        text("Goal:", width / 11f, (height / 2f));
        fill(255, 255, 255);
        text(game.getGoal(), width / 5f, height / 2f);
    }

    private void drawLoseTable() {
        pushMatrix();
        translate(x, y + 200);
        noStroke();
        fill(20, 20, 150);
        rect(0, 0, 600, 200);
        fill(255, 255, 0);
        textSize(50);
        text("You Lost. No moves left: ", 25, 50);
        text("Press enter to restart", 25, 150);
        if (key == ENTER) {
            game.initGame2048();
        }
        popMatrix();
    }
    public static void main(String[] args) {
        PApplet.main("Main");
    }

}