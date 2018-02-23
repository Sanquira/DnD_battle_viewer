package com.github.sanquira.logic.api;

import java.util.List;
import java.awt.geom.Rectangle2D;

public interface IDataStructure
{
	public void insert(IDataElement element);

	public void remove(IDataElement element);

	public void clear();

	public List<IDataElement> retrieve(Rectangle2D rect, int layer);
}
