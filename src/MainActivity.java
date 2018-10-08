package com.calculator.heroicsandwich.sheep_crush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    BoardView b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.b = new BoardView(this);
        setContentView(this.b);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }
}
