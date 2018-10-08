package com.calculator.heroicsandwich.sheep_crush;
import java.util.Random;

public class Game
{
    private State game_state;
    private byte [][]board; // 7 x 9 board
    private int score;
    private Move c_move;
    private byte [][]delete1; // 7 x 9 board
    private byte [][]delete2;
    private boolean []check;

    public Game()
    {
        Random rand = new Random();
        this.game_state = State.start;
        this.board = new byte[7][];
        this.delete1 = new byte[7][];
        this.delete2 = new byte[7][];
        for(byte n = 0; n < 7; n++)
        {
            this.board[n] = new byte[9];
            this.delete1[n] = new byte[9];
            this.delete2[n] = new byte[9];
            for(byte m = 0; m < 9; m++)
            {
                board[n][m] = (byte)(rand.nextInt(4) + 1);
            }
        }

        this.score = 0;

        check = new boolean[2];
        check[0] = false;
        check[1] = false;
    }

    public void newBoard()
    {
        Random rand = new Random();
        for(byte n = 0; n < 7; n++)
        {
            for(byte m = 0; m < 9; m++)
            {
                board[n][m] = (byte)(rand.nextInt(4) + 1);
            }
        }
    }

    private void clearDelete()
    {
        for(byte n = 0; n < 7; n++)
        {
            for(byte m = 0; m < 9; m++)
            {
                delete1[n][m] = 0;
                delete2[n][m] = 0;
            }
        }
    }

    public void setMove(Move move)
    {
        this.c_move = move;
    }

    public void setMove(int []orig, Direction direction)
    {
        this.c_move.setOrig(orig);
        this.c_move.setDirection(direction);
    }

    public void setMove(int x, int y, Direction direction)
    {
        int []orig = new int[2];
        orig[0] = x;
        orig[1] = y;
        this.setMove(orig, direction);
    }

    private void checkRecursive(int []orig, Direction ignore, byte focus, boolean del)
    {
        int []check = orig.clone();
        if(this.board[orig[0]][orig[1]] == focus)
        {
            (del ? this.delete1 : this.delete2)[orig[0]][orig[1]] = 1;
        }
        else
        {
            (del ? this.delete1 : this.delete2)[orig[0]][orig[1]] = 0;
            return;
        }
        if(orig[1] < 8 && ignore != Direction.down && this.board[orig[0]][orig[1]+1] == focus && (del ? this.delete1 : this.delete2)[orig[0]][orig[1]+1] == 0)
        {
            (del ? this.delete1 : this.delete2)[orig[0]][orig[1]+1] = 1;
            check[1] = orig[1] + 1;
            checkRecursive(check, Direction.up, focus, del);
        }
        if(orig[1] > 0 && ignore != Direction.up && this.board[orig[0]][orig[1]-1] == focus && (del ? this.delete1 : this.delete2)[orig[0]][orig[1]-1] == 0)
        {
            (del ? this.delete1 : this.delete2)[orig[0]][orig[1]-1] = 1;
            check[0] = orig[0];
            check[1] = orig[1] - 1;
            checkRecursive(check, Direction.down, focus, del);
        }
        if(orig[0] > 0 && ignore != Direction.left && this.board[orig[0]-1][orig[1]] == focus && (del ? this.delete1 : this.delete2)[orig[0]-1][orig[1]] == 0)
        {
            (del ? this.delete1 : this.delete2)[orig[0]-1][orig[1]] = 1;
            check[1] = orig[1];
            check[0] = orig[0] - 1;
            checkRecursive(check, Direction.right, focus, del);
        }
        if(orig[0] < 6 && ignore != Direction.right && this.board[orig[0]+1][orig[1]] == focus && (del ? this.delete1 : this.delete2)[orig[0]+1][orig[1]] == 0)
        {
            (del ? this.delete1 : this.delete2)[orig[0]+1][orig[1]] = 1;
            check[1] = orig[1];
            check[0] = orig[0] + 1;
            checkRecursive(check, Direction.left, focus, del);
        }
        return;
    }

    private int numAdjacent(int []orig, byte focus, boolean del)
    {
        checkRecursive(orig, Direction.none, focus, del);
        int count = 0;
        for(short n = 0; n < 9; n++)
        {
            for(short m = 0; m < 7; m++)
            {
                count += ((del ? this.delete1 : this.delete2))[m][n];
            }
        }
        return count;
    }

    public boolean isValidMove()
    {
        int []orig = this.c_move.getOrig();
        int []check = orig.clone();
        byte temp;
        Direction mem = c_move.direction;

        //check for OOB
        switch(this.c_move.direction)
        {
            case up:
            {
                if(orig[1] >= 8)
                {
                    return false;
                }
                temp = board[orig[0]][orig[1]];
                board[orig[0]][orig[1]] = board[orig[0]][orig[1]+1];
                board[orig[0]][orig[1]+1] = temp;
                check[1]++;
                break;
            }
            case down:
            {
                if(orig[1] <= 0)
                {
                    return false;
                }
                temp = board[orig[0]][orig[1]];
                board[orig[0]][orig[1]] = board[orig[0]][orig[1]-1];
                board[orig[0]][orig[1]-1] = temp;
                check[1]--;
                break;
            }
            case left:
            {
                if(orig[0] <= 0)
                {
                    return false;
                }
                temp = board[orig[0]][orig[1]];
                board[orig[0]][orig[1]] = board[orig[0]-1][orig[1]];
                board[orig[0]-1][orig[1]] = temp;
                check[0]--;
                break;
            }
            case right:
            {
                if(orig[0] >= 5)
                {
                    return false;
                }
                temp = board[orig[0]][orig[1]];
                board[orig[0]][orig[1]] = board[orig[0]+1][orig[1]];
                board[orig[0]+1][orig[1]] = temp;
                check[0]++;
                break;
            }
            case none:
            {
                return false;
            }
        }

        this.check[0] = !(numAdjacent(orig, this.board[orig[0]][orig[1]], true) < 3);
        this.check[1] = !(numAdjacent(check, this.board[check[0]][check[1]], false) < 3);

        if(!this.check[0] && !this.check[1])
        {
            switch(mem)
            {
                case up:
                    temp = board[orig[0]][orig[1]];
                    board[orig[0]][orig[1]] = board[orig[0]][orig[1]+1];
                    board[orig[0]][orig[1]+1] = temp;
                    check[1]++;
                    break;
                case down:
                    temp = board[orig[0]][orig[1]];
                    board[orig[0]][orig[1]] = board[orig[0]][orig[1]-1];
                    board[orig[0]][orig[1]-1] = temp;
                    check[1]--;
                    break;
                case left:
                    temp = board[orig[0]][orig[1]];
                    board[orig[0]][orig[1]] = board[orig[0]-1][orig[1]];
                    board[orig[0]-1][orig[1]] = temp;
                    check[0]--;
                    break;
                case right:
                    temp = board[orig[0]][orig[1]];
                    board[orig[0]][orig[1]] = board[orig[0]+1][orig[1]];
                    board[orig[0]+1][orig[1]] = temp;
                    check[0]++;
                    break;
                default:
                    break;
            }
            return false;
        }

        return true;
    }

    public void update()
    {
        Random rand = new Random();
        int []orig = this.c_move.getOrig();
        int []orig2 = orig.clone();
        byte []check_cols = new byte[7];
        int check, mem;
        boolean flag;
        byte g_start = 0;
        byte g_end;
        byte g_total;
        switch(this.c_move.getDirection())
        {
            case up:
                orig2[1]++;
                break;
            case down:
                orig2[1]--;
                break;
            case left:
                orig2[0]--;
                break;
            case right:
                orig2[0]++;
                break;
        }

        if(this.check[0])
        {
            for (byte n = 0; n < 9; n++)
            {
                for (byte m = 0; m < 7; m++)
                {
                    if (delete1[m][n] == 1)
                    {
                        this.board[m][n] = 0;
                        check_cols[m] = 1;
                        this.score++;
                    }
                }
            }
        }

        if(this.check[1])
        {
            for (byte n = 0; n < 9; n++)
            {
                for (byte m = 0; m < 7; m++)
                {
                    if (delete2[m][n] == 1)
                    {
                        this.board[m][n] = 0;
                        check_cols[m] = 1;
                        this.score++;
                    }
                }
            }
            this.clearDelete();
        }

        printBoard();

        for(byte n = 0; n < 7; n++)
        {
            //g_total = 0;
            g_end = 8;
            if(check_cols[n] == 1)
            {
                while(g_end >= 0)
                {
                    for(g_start = 8; g_start >= 0 && this.board[n][g_start] != 0; g_start--);
                    for(g_end = g_start; g_end >= 0 && this.board[n][g_end] == 0; g_end--);
                    for(; g_end >= 0 && this.board[n][g_end] != 0; g_end--)
                    {
                        this.board[n][g_start--] = this.board[n][g_end];
                        this.board[n][g_end] = 0;
                    }
                }
                for(byte m = g_start; m >= 0; m--)
                {
                    this.board[n][m] = (byte)(rand.nextInt(4)+1);
                }
            }
        }
    }

    public void printBoard()
    {
        for(byte n = 8; n >= 0; n--)
        {
            for(byte m = 0; m < 7; m++)
            {
                System.out.printf("%d\t", this.board[m][n]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n\n");
    }

    private void printDelete()
    {
        for(byte n = 0; n < 9; n++)
        {
            for(byte m = 0; m < 7; m++)
            {
                System.out.printf("%d\t", this.delete1[m][n]);
            }
            System.out.printf("\n");
        }
        System.out.printf("Hi mom!");
        System.out.printf("\n\n");
    }
}