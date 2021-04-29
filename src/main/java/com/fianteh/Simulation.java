package com.fianteh;

public class Simulation {

    private int width;
    private int height;
    private int[][] board;

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
                if (this.board[x][y] == 0) {
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
        this.board[x][y] = 1;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = 0;
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
            return 0; // cell is dead
        }

        if (y < 0 || y >= height){
            return 0;
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
                if (getState(x, y) == 1) {
                    if (aliveNeighbours < 2) {
                        newBoard[x][y] = 0;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[x][y] = 1;
                    } else {
                        // 3 or more neighbours
                        newBoard[x][y] = 0;
                    }
                } else {
                    if (aliveNeighbours == 3) newBoard[x][y] = 1;
                }

            }
        }

        this.board = newBoard;

    }


    public static void main(String[] args) {
        Simulation simulation = new Simulation(8, 5);

        simulation.setAlive(2, 2);
        simulation.setAlive(3, 2);
        simulation.setAlive(4, 2);

        simulation.printBoard();

        simulation.step();

        simulation.printBoard();

        simulation.step();

        simulation.printBoard();
    }

}
