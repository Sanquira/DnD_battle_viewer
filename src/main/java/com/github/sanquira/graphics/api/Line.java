package com.github.sanquira.graphics.api;

import java.awt.Color;

public class Line
{
    private Vertex2D lineStart, lineEnd;
    private Color lineColor;

    public Line(Vertex2D lineStart, Vertex2D lineEnd, Color lineColor)
    {
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.lineColor = lineColor;
    }

    public Line(Vertex2D lineStart, Vertex2D lineEnd)
    {
        this.lineColor = Color.BLACK;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
    }

    public Color getLineColor()
    {
        return lineColor;
    }

    public Vertex2D getLineStart()
    {
        return lineStart;
    }

    public Vertex2D getLineEnd()
    {
        return lineEnd;
    }
}
