package logic.data;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.lang.model.element.Element;

import java.util.TreeMap;
import java.util.stream.Collectors;

import logic.API.IMyDataElement;
import logic.API.IMyDataStructure;

public class QuadTreeLayer implements IMyDataStructure
{
	TreeMap<Integer, QuadTree> quadTreeLayer;

	protected int rootWidth, rootHeight;

	public QuadTreeLayer(int rootWidth, int rootHeight)
	{
		this.rootWidth = rootWidth;
		this.rootHeight = rootHeight;
		quadTreeLayer = new TreeMap<>();
	}

	@Override
	public void insert(IMyDataElement element)
	{
		if (!quadTreeLayer.containsKey(element.getLayer()))
			quadTreeLayer.put(element.getLayer(), new QuadTree(rootWidth, rootHeight));

		quadTreeLayer.get(element.getLayer()).insert(element);
	}

	@Override
	public void remove(IMyDataElement element)
	{
		if (quadTreeLayer.containsKey(element.getLayer()))
		{
			quadTreeLayer.get(element.getLayer()).remove(element);
			if (quadTreeLayer.get(element.getLayer()).isEmpty())
			{
				quadTreeLayer.remove(element.getLayer());
			}
		}
	}

	@Override
	public void clear()
	{
		Iterator<Entry<Integer, QuadTree>> it = quadTreeLayer.entrySet().iterator();
		while (it.hasNext())
		{
			Entry<Integer, QuadTree> next = it.next();
			next.getValue().clear();
			it.remove();
		}
	}

	@Override
	public List<IMyDataElement> retrieve(Rectangle2D rect, int layer)
	{
		ArrayList<IMyDataElement> ret = new ArrayList<>();
		if (quadTreeLayer.containsKey(layer))
		{
			quadTreeLayer.get(layer).retrieve(ret, rect);
		}
		return ret;
	}

	private class QuadTree
	{
		private final static int MAX_OBJECTS_IN_NODE = 32 * 32;

		private QuadTree parent;
		private QuadTree[] nodes;
		private int posX, posY, width, height;
		private List<IMyDataElement> objects;

		public QuadTree(int width, int height)
		{
			this(null, 0, 0, width, height);
		}

		protected QuadTree(QuadTree parent, int positionX, int positionY, int width, int height)
		{
			posX = positionX;
			posY = positionY;
			this.width = width;
			this.height = height;
			objects = new ArrayList<>();
			this.parent = parent;
			nodes = new QuadTree[4];
		}

		protected boolean isRoot()
		{
			return parent == null;
		}

		protected boolean isLeaf()
		{
			return nodes[0] == null;
		}

		/**
		 * children indexes: 1|0 --- 2|3
		 */
		protected void split()
		{
			if ((width & 1) == 1 || (height & 1) == 1)
			{
				System.err.println("Warning: Quad split is not integer. Skipping!");
				return;
			}

			int subWidth = width >> 1;
			int subHeight = height >> 1;

			nodes[0] = new QuadTree(this, posX + subWidth, posY, subWidth, subHeight);
			nodes[1] = new QuadTree(this, posX, posY, subWidth, subHeight);
			nodes[2] = new QuadTree(this, posX, posY + subHeight, subWidth, subHeight);
			nodes[3] = new QuadTree(this, posX + subWidth, posY + subHeight, subWidth, subHeight);
		}

		protected int getQuadrantIndex(IMyDataElement element)
		{
			return getQuadrantIndex(element.getX(), element.getY());
		}

		/**
		 * Return index of quadrant in which element fit. If element is outside
		 * of all quadrants returns -1.
		 * 
		 * @param element
		 * @return
		 */
		protected int getQuadrantIndex(int positionX, int positionY)
		{
			float verticalMidPoint = posX + (width / 2f);
			float horizontalMidPoint = posY + (height / 2f);

			boolean topQ = false;
			boolean bottomQ = false;
			if (positionY >= posY && positionY < horizontalMidPoint)
				topQ = true;
			if (positionY >= horizontalMidPoint && positionY < posY + height)
				bottomQ = true;

			// WARNING
			if (topQ && bottomQ)
			{
				System.err.println("Warning: Element belong to both top and bottom quadrants:");
				System.err.println("element: " + positionX + ", " + positionY);
				System.err.println("quad node: " + posX + ", " + posY + ", " + width + ", " + height);
			}

			if (positionX >= posX && positionX < verticalMidPoint)
			{
				if (topQ)
					return 1;
				if (bottomQ)
					return 2;
			}
			if (positionX >= verticalMidPoint && positionX < posX + width)
			{
				if (topQ)
					return 0;
				if (bottomQ)
					return 3;
			}

			return -1;
		}

		protected int getQuadrantFitIndex(Rectangle2D rect)
		{
			int leftTopIndex = getQuadrantIndex((int) rect.getX(), (int) rect.getY());
			int rightBottomIndex = getQuadrantIndex((int) rect.getMaxX(), (int) rect.getMaxY());

			if (leftTopIndex == rightBottomIndex)
				return leftTopIndex;

			return -1;
		}

		protected List<IMyDataElement> getAll(List<IMyDataElement> returnList)
		{
			if (!isLeaf())
			{
				nodes[0].getAll(returnList);
				nodes[1].getAll(returnList);
				nodes[2].getAll(returnList);
				nodes[3].getAll(returnList);
			}
			returnList.addAll(objects);
			return returnList;
		}

		public void insert(IMyDataElement element)
		{
			if (!isLeaf())
			{
				int index = getQuadrantIndex(element);
				if (index != -1)
				{
					nodes[index].insert(element);
					return;
				}
			}

			objects.add(element);

			if (objects.size() > MAX_OBJECTS_IN_NODE)
			{
				if (isLeaf())
				{
					split();
				}
				int i = 0;
				while (i < objects.size())
				{
					int index = getQuadrantIndex(objects.get(i));
					if (index != -1)
						nodes[index].insert(objects.remove(i));
					else
						i++;
				}
			}

			// WARNING
			if (!isLeaf() && objects.size() > 0)
			{
				System.err.println("Warning: No-leaf node contains objects! Objects count: " + objects.size());
			}
		}

		public void remove(IMyDataElement element)
		{
			if (!isLeaf())
			{
				int index = getQuadrantIndex(element);
				if (index != -1)
				{
					nodes[index].remove(element);
				}
			}

			if (objects.contains(element))
			{
				objects.remove(element);
			}

			if (objects.size() == 0 && isLeaf() && !isRoot())
			{
				int cumSum = 0;
				for (int i = 0; i < parent.nodes.length; i++)
				{
					if (!parent.nodes[i].isLeaf())
						cumSum += 1;
					else
						cumSum += parent.nodes[i].objects.size();
				}
				if (cumSum == 0)
				{
					parent.clear();
				}
			}

		}

		public boolean isEmpty()
		{
			return isLeaf() && objects.isEmpty();
		}

		public void clear()
		{
			objects.clear();
			if (!isLeaf())
			{
				for (int i = 0; i < nodes.length; i++)
				{
					nodes[i].clear();
					nodes = null;
				}
			}
		}

		public void retrieve(List<IMyDataElement> returnList, Rectangle2D rect)
		{
			int index = getQuadrantFitIndex(rect);
			if (index != -1 && !isLeaf())
				nodes[index].retrieve(returnList, rect);
			else
			{
				getAll(returnList);
				returnList.removeIf((element) -> !rect.contains(element.getX(), element.getY()));
			}
		}
	}
}
