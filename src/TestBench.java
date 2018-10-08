package com.calculator.heroicsandwich.sheep_crush;

import java.util.Scanner;

public class TestBench
{
    public static void main(String args[])
    {
        Game g = new Game();
        int []p = new int[2];
        Move m;
        String c = new String();
        Direction d;
        Scanner in = new Scanner(System.in);

        for(;;)
        {
            g.printBoard();
            System.out.printf("Enter starting position x:\t");
            p[0] = in.nextInt();
            System.out.printf("Enter starting position y:\t");
            p[1] = in.nextInt();
            System.out.printf("Enter (u)p (d)own (l)eft (r)ight:\t");
            c = in.nextLine();
            c = in.nextLine();

            switch(c.charAt(0))
            {
                case 'u':
                    d = Direction.up;
                    break;
                case 'd':
                    d = Direction.down;
                    break;
                case 'l':
                    d = Direction.left;
                    break;
                case 'r':
                    d = Direction.right;
                    break;
                default:
                    d = Direction.none;
                    break;
            }
            m = new Move(p, d);
            g.setMove(m);

            if(g.isValidMove())
            {
                g.update();
            }

            else
            {
                System.out.printf("D:\n\n");
            }
        }
    }
}
