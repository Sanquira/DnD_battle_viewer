package com.github.sanquira.logic.entities;

import java.awt.Color;

import com.github.sanquira.graphics.api.Graphics;
import com.github.sanquira.graphics.api.IDrawable;
import com.github.sanquira.logic.api.IDataElement;
import com.github.sanquira.logic.api.IDataStructure;

public abstract class AbstractEntity implements IDataElement, IDrawable
{
    protected int dataElementX;
    protected int dataElementY;
    protected int dataElementLayer;

    protected Color backgroundColor;

    public AbstractEntity(int layer, int posX, int posY)
    {
        dataElementLayer = layer;
        dataElementX = posX;
        dataElementY = posY;
        backgroundColor = Color.YELLOW;
    }

    @Override
    public int getX()
    {
        return dataElementX;
    }

    @Override
    public int getY()
    {
        return dataElementY;
    }

    @Override
    public int getLayer()
    {
        return dataElementLayer;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public abstract IDataStructure clone();

    @Override
    public abstract void draw(Graphics g);
}
