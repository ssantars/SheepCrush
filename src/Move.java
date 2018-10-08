package com.calculator.heroicsandwich.sheep_crush;

public class Move
{
    private int []orig;
    protected Direction direction;

    private Move()
    {
        this.orig = new int[2];
        this.direction = Direction.none;
    }

    public Move(int []orig, Direction direction)
    {
        this.orig = orig;
        this.direction = direction;
    }

    public Move(int x, int y, Direction direction)
    {
        this.orig = new int[2];
        this.orig[0] = x;
        this.orig[1] = y;
        this.direction = direction;
    }

    public void setOrig(int []orig)
    {
        this.orig = orig;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public int[] getOrig()
    {
        return this.orig;
    }

    public Direction getDirection()
    {
        return this.direction;
    }
}
