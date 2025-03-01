package spatialindex.spatialindex;

import java.io.Serializable;



public class Point implements IShape, Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;
	
	public double[] m_pCoords = null;


	public Point(double[] pCoords)
	{
		m_pCoords = new double[pCoords.length];
		System.arraycopy(pCoords, 0, m_pCoords, 0, pCoords.length);
	}

	public Point(final Point pt)
	{
		m_pCoords = new double[pt.m_pCoords.length];
		System.arraycopy(pt.m_pCoords, 0, m_pCoords, 0, pt.m_pCoords.length);
	}

	public boolean equals(Object o)
	{
		if (o instanceof Point)
		{
			Point pt = (Point) o;

			if (pt.m_pCoords.length != m_pCoords.length) return false;

			for (int cIndex = 0; cIndex < m_pCoords.length; cIndex++)
			{
				if (m_pCoords[cIndex] < pt.m_pCoords[cIndex] - Double.MIN_NORMAL ||
						m_pCoords[cIndex] > pt.m_pCoords[cIndex] + Double.MIN_NORMAL) return false;
			}

			return true;
		}

		return false;
	}

	//
	// Cloneable interface
	//

	public Object clone()
	{
		return new Point(m_pCoords);
	}

	//
	// IShape interface
	//

	public boolean intersects(final IShape s)
	{
		if (s instanceof Region) return ((Region) s).contains(this);

		return false;
	}

	public boolean contains(final IShape s)
	{
		return false;
	}

	public boolean touches(final IShape s)
	{
		if (s instanceof Point && this.equals(s)) return true;

		if (s instanceof Region) return ((Region) s).touches(this);

		return false;
	}

	public double[] getCenter()
	{
		double[] pCoords = new double[m_pCoords.length];
		System.arraycopy(m_pCoords, 0, pCoords, 0, m_pCoords.length);
		return pCoords;
	}

	public long getDimension()
	{
		return m_pCoords.length;
	}

	public Region getMBR()
	{
		return new Region(m_pCoords, m_pCoords);
	}

	public double getArea()
	{
		return 0.0f;
	}

	public double getMinimumDistance(final IShape s)
	{
		if (s instanceof Region) return ((Region) s).getMinimumDistance(this);

		if (s instanceof Point) return getMinimumDistance((Point) s);

		throw new IllegalStateException("getMinimumDistance: Not implemented yet!");
	}

	double getMinimumDistance(final Point p)
	{
		if (m_pCoords.length != p.m_pCoords.length) throw new IllegalArgumentException("getMinimumDistance: Shape has the wrong number of dimensions.");

		double ret = 0.0;

		for (int cIndex = 0; cIndex < m_pCoords.length; cIndex++)
		{
			ret += Math.pow(m_pCoords[cIndex] - p.m_pCoords[cIndex], 2.0);
		}

		return Math.sqrt(ret);
	}

	public double getCoord(int index) throws IndexOutOfBoundsException
	{
		if (index >= m_pCoords.length) throw new IndexOutOfBoundsException("" + index);
		return m_pCoords[index];
	}
	
	public boolean isCoLocation(Point pt)
	{
		for (int cIndex = 0; cIndex < m_pCoords.length; cIndex++)
		{
			if ( m_pCoords[cIndex] < ( pt.m_pCoords[cIndex] - Double.MIN_NORMAL ) ||
					m_pCoords[cIndex] > ( pt.m_pCoords[cIndex] + Double.MIN_NORMAL ) ) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		s += "[";
		
		for ( int i = 0; i < m_pCoords.length; i++ )
		{
			s += " " + m_pCoords[i] + ",";
		}
		s = s.substring( 0, s.length() - 1 );
		s += " ]";
		
		return s ;
	}
}
