package com.github.sanquira.logic.api;

public interface IDataElement
{   
	public int getX();

	public int getY();
	
	public int getLayer();
	
	public IDataStructure clone();
}
