package com.github.sanquira.graphics.api;

import java.awt.Color;

public class Text
{
    Vertex2D textPosition;
    String text;
    Color textColor;
    public Text(Vertex2D textPosition, String text, Color textColor)
    {
        this.textPosition = textPosition;
        this.text = text;
        this.textColor = textColor;
    }
    public Text(Vertex2D textPosition, String text)
    {
        this.textPosition = textPosition;
        this.text = text;
    }
    public Vertex2D getTextPosition()
    {
        return textPosition;
    }
    public String getText()
    {
        return text;
    }
    public Color getTextColor()
    {
        return textColor;
    }
    
    
}
