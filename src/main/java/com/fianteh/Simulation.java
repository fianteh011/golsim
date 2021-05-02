package com.fianteh;

public class Simulation {

    public static int DEAD = 0;
    public static int ALIVE = 1;

    private int width;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int height;
    int[][] board;


    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            //this would be the border of the board
            StringBuilder line = new StringBuilder("|");
            for (int x = 0; x < width; x++) {
                //output some char to signal, if this cell is dead or alive
                // 0 is dead
                if (this.board[x][y] == DEAD) {
                    //dead
                    line.append(".");
                } else {
                    //alive
                    line.append("*");
                }
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    //two functions to set cellls alive or dead

    public void setAlive(int x, int y) {
        this.setState(x, y, ALIVE);
    }

    public void setDead(int x, int y) {
        this.setState(x, y, DEAD);
    }

    public void setState(int x, int y, int state){
        if (x < 0 || x >= width) {
            return;
        }

        if (y < 0 || y >= height){
            return;
        }

        this.board[x][y] = state;
    }

    //simulation steps
    //count how many neighbours a certain cell has

    public int countAliveNeighbours(int x, int y) {
        int count = 0;

        // ... top
        // .*. middle
        // ...
        count += this.getState(x - 1,y - 1); // top left
        count += this.getState(x,y - 1);
        count += this.getState(x + 1,y - 1);

        count += this.getState(x - 1,y); //not including the middle
        count += this.getState(x + 1,y);

        count += this.getState(x - 1,y + 1);
        count += this.getState(x,y + 1);
        count += this.getState(x + 1,y + 1);

        return count;
    }

    public int getState(int x, int y){
        //check if coordinates are valid
        if (x < 0 || x >= width) {
            return DEAD; // cell is dead
        }

        if (y < 0 || y >= height){
            return DEAD;
        }

        return this.board[x][y];

    }

    //how do we get the simulation from one board to other
    public void step() {
        // to save every changed state, we need to save it in a local variable
        int[][] newBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int aliveNeighbours = countAliveNeighbours(x, y);
                //check CURRENT state of Board
                if (getState(x, y) == ALIVE) {
                    if (aliveNeighbours < 2) {
                        newBoard[x][y] = DEAD;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[x][y] = ALIVE;
                    } else {
                        // 3 or more neighbours
                        newBoard[x][y] = DEAD;
                    }
                } else {
                    if (aliveNeighbours == 3) newBoard[x][y] = ALIVE;
                }

            }
        }

        this.board = newBoard;

    }

}
