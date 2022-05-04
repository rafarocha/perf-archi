package com.perf.archi;

import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    // pares [2,5]=[7,4]..UR + DR
    // pares [0,0]=[8,9]..DR + DR + DR + D
    // pares [9.9]=[1,8]..DL + DL + L
    // pares [0,9]=[9,1]..DL + DL + U

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = 10; // in.nextInt(); // width of the building.
        int H = 10; // in.nextInt(); // height of the building.
        int N = 10; // in.nextInt(); // maximum number of turns before game over.
        int X0 = 0; // in.nextInt();
        int Y0 = 9; // in.nextInt();

        Batman batman = new Batman(X0, Y0);
        Game game = new Game(W, H, N);
        Strategy strategy = new Strategy( game, batman );

        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)

            Direction dir = Direction.valueOf( bombDir );
            String next;

            switch (dir) {
                // basics
                case U: next = strategy.up(); break; 
                case D: next = strategy.down(); break; 
                case R: next = strategy.right(); break; 
                case L: next = strategy.left(); break; 
                // diagonals
                case UR: next = strategy.upRight(); break; 
                case DR: next = strategy.downRight(); break; 
                case DL: next = strategy.downLeft(); break; 
                case UL: next = strategy.upLeft(); break; 
                // throw if anyone
                default : {
                    throw new RuntimeException("invalid command");
                }
            }
            // the location of the next window Batman should jump to.
            System.out.println(next); // "0 0"
            if ( batman.x == 9 && batman.y == 1 ) {
                break;
            }
        }
        System.out.println( "sucess" );
    }

}

enum Direction {
    U, UR, R, DR, D, DL, L, UL;
}

class Limits {
    public int x1, x2;
    public int y1, y2;

    public boolean hasSomeLimit() {
        if ( x1 == 0 && x2 == 0 ) return false;
        if ( y1 == 0 && y2 == 0 ) return false;
        if ( Math.abs(x1 - x2) <= 2 ) return true;
        if ( Math.abs(y1 - y2) <= 2 ) return true;
        return false;
    }
}

class Strategy {
    private Game game;
    private Batman batman;
    private Limits limits;
    private int steps;
    
    public Strategy(Game game, Batman batman) {
        this.game = game; this.batman = batman;
        this.limits = new Limits();
    }

    public String up() {
        limits.x1 = (limits.x1 == 0) ? batman.x : 
            (batman.x > limits.x1) ? batman.x :limits.x1; 
        limits.x2 = (limits.x2 == 0) ? game.W - 1 : 
            (batman.x > limits.x2) ? batman.x :limits.x2; 

        limits.y1 = (limits.y1 == 0) ? 0 : 
            (batman.y > limits.y1) ? batman.y :limits.y1; 
        limits.y2 = (limits.y2 == 0) ? batman.y : 
            (batman.y > limits.y2) ? limits.y2 : batman.y;

        return search( (batman.x), (batman.y - dY()), Direction.DR);
    }
    public String down() {
        limits.x1 = limits.x2 = batman.x;

        limits.y1 = (limits.y1 == 0) ? batman.y : 
            (batman.y > limits.y1) ? batman.y :limits.y1; 
        limits.y2 = (limits.y2 == 0) ? batman.y : 
            (batman.y > limits.y2) ? batman.y :limits.y2; 
            
        return search( (batman.x), (batman.y + dY()), Direction.DR);
    }
    public String right() {
        return search( (batman.x + dX()), (batman.y), Direction.DR);
    }
    public String left() {
        return search( (batman.x - dX()), (batman.y), Direction.DR);
    }
    public String upRight() {
        limits.x1 = (limits.x1 == 0) ? 0 : 
            (batman.x > limits.x1) ? batman.x :limits.x1; 
        limits.x2 = (limits.x2 == 0) ? 0 : 
            (batman.x > limits.x2) ? batman.x :limits.x2; 

        limits.y1 = (limits.y1 == 0) ? 0: 
            (batman.y > limits.y1) ? batman.y :limits.y1; 
        limits.y2 = (limits.y2 == 0) ? game.H -1  : 
            (batman.y > limits.y2) ? limits.y2 : batman.y;

        return search( (batman.x), (batman.y - dY()), Direction.DR);
    }
    public String downRight() {
        limits.x1 = (limits.x1 == 0) ? batman.x : 
            (batman.x > limits.x1) ? batman.x :limits.x1; 
        limits.x2 = (limits.x2 == 0) ? game.W -1 : 
            (batman.x > limits.x2) ? batman.x :limits.x2; 

        limits.y1 = (limits.y1 == 0) ? batman.y : 
            (batman.y > limits.y1) ? batman.y :limits.y1; 
        limits.y2 = (limits.y2 == 0) ? game.H -1 : 
            (batman.y > limits.y2) ? batman.y :limits.y2; 

        return search( (batman.x + dX()), (batman.y + dY()), Direction.DR);
    }
    public String downLeft() {
        limits.x1 = (limits.x1 == 0) ? batman.x : 
            (batman.x > limits.x1) ? batman.x :limits.x1; 
        limits.x2 = (limits.x2 == 0) ? game.W -1 : 
            (batman.x > limits.x2) ? batman.x :limits.x2; 

        limits.y1 = (limits.y1 == 0) ? batman.y : 
            (batman.y > limits.y1) ? batman.y :limits.y1; 
        limits.y2 = (limits.y2 == 0) ? game.H -1 : 
            (batman.y > limits.y2) ? batman.y :limits.y2; 
        return search( (batman.x - dX()), (batman.y + dY()), Direction.DR);
    }
    public String upLeft() {
        return search( (batman.x - dX()), (batman.y - dY()), Direction.DR);
    }

    private String command(Object a, Object b) {
        this.steps++;
        return String.format("%s %s", a, b);
    }

    private String search(int x, int y, Direction dir) {
        batman.x = Math.abs(x);
        batman.y = Math.abs(y);
        return command(batman.x, batman.y);
    }

    private int dX() {
        if ( limits.hasSomeLimit() ) return 1;
        int value = isDiagnoal() ? batman.y : (game.H - batman.y);
        return (int) Math.floor( value / 2 );
    }

    private int dY() {
        if ( limits.hasSomeLimit() ) return 1;
        int value = isDiagnoal() ? batman.x : (game.W - batman.x);
        return (int) Math.floor( value / 2 );
    }

    private boolean isDiagnoal() {
        return (batman.x == 0 && batman.y == game.H - 1);
        // TODO implementar outros
    }

}

class Game {
    public int W;
    public int H;
    public int N;
    public Game(int W, int H, int N) {
        this.W = W; this.H = H; this.N = N;
    }
}

class Batman {
    public Batman(int x, int y) {
        this.x = x; this.y = y;
    }
    public int x;
    public int y;
}