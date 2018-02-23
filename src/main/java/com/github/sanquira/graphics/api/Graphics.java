package com.github.sanquira.graphics.api;

import java.util.List;

public class Graphics
{
    private List<Text> textList;
    private List<Line> lineList;
    private List<Polygon> polygonList;
    
    public void addPolygon(Polygon polygon)
    {
        polygonList.add(polygon);
    }

    public void addLine(Line line)
    {
        lineList.add(line);
    }

    public void addText(Text text)
    {
        textList.add(text);
    }

    public List<Text> getTextList()
    {
        return textList;
    }

    public List<Line> getLineList()
    {
        return lineList;
    }

    public List<Polygon> getPolygonList()
    {
        return polygonList;
    }

}
