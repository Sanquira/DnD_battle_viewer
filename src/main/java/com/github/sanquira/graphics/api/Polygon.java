package com.github.sanquira.graphics.api;

import java.awt.Color;
import java.util.List;

public class Polygon
{
    private List<Vertex2D> vertexList;
    private Color fillColor;
    private Color lineColor;

    public Polygon(List<Vertex2D> vertexList, Color fillColor, Color lineColor)
    {
        this();
        this.vertexList = vertexList;
        this.fillColor = fillColor;
        this.lineColor = lineColor;
    }

    public Polygon(Color fillColor, Color lineColor)
    {
        this();
        this.fillColor = fillColor;
        this.lineColor = lineColor;
    }

    public Polygon(Color lineColor)
    {
        this();
        this.lineColor = lineColor;
    }

    public Polygon()
    {
        this.fillColor = null;
        this.lineColor = Color.BLACK;
    }

    public void addVertex(Vertex2D point)
    {
        vertexList.add(point);
    }

    public void addVertex(int x, int y)
    {
        vertexList.add(new Vertex2D(x, y));
    }

    public List<Vertex2D> getVertexList()
    {
        if (vertexList.size() < 3)
            return null;
        return vertexList;
    }

    public Color getFillColor()
    {
        return fillColor;
    }

    public Color getLineColor()
    {
        return lineColor;
    }

}
