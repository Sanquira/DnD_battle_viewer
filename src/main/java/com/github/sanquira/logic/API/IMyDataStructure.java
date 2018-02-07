package com.github.sanquira.logic.API;

import java.util.List;
import java.awt.geom.Rectangle2D;

public interface IMyDataStructure
{
	public void insert(IMyDataElement element);

	public void remove(IMyDataElement element);

	public void clear();

	public List<IMyDataElement> retrieve(Rectangle2D rect, int layer);
	
	//add chunk border retriever because of draw caching??? 
}
