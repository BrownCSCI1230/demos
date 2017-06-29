package edu.brown.cs.exploratories.applets.filterShape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public class FilterSketcher
  extends JComponent
{
  private GraphData graphData = new GraphData();
  private Listener listener = new Listener(null);
  private double domainLow;
  private double domainHigh;
  private double rangeLow;
  private double rangeHigh;
  
  public FilterSketcher()
  {
    addMouseListener(this.listener);
    addMouseMotionListener(this.listener);
    this.domainLow = -1.0D;
    this.domainHigh = 1.0D;
    this.rangeLow = -1.0D;
    this.rangeHigh = 1.0D;
  }
  
  public void setDomainLow(double paramDouble)
  {
    this.domainLow = paramDouble;
    repaint();
  }
  
  public double getDomainLow()
  {
    return this.domainLow;
  }
  
  public void setDomainHigh(double paramDouble)
  {
    this.domainHigh = paramDouble;
    repaint();
  }
  
  public double getDomainHigh()
  {
    return this.domainHigh;
  }
  
  public void setDomain(double paramDouble1, double paramDouble2)
  {
    this.domainLow = paramDouble1;
    this.domainHigh = paramDouble2;
    repaint();
  }
  
  public void setRangeLow(double paramDouble)
  {
    this.rangeLow = paramDouble;
    repaint();
  }
  
  public double getRangeLow()
  {
    return this.rangeLow;
  }
  
  public void setRangeHigh(double paramDouble)
  {
    this.rangeHigh = paramDouble;
    repaint();
  }
  
  public double getRangeHigh()
  {
    return this.rangeHigh;
  }
  
  public void setRange(double paramDouble1, double paramDouble2)
  {
    this.rangeLow = paramDouble1;
    this.rangeHigh = paramDouble2;
    repaint();
  }
  
  public Filter toFilter()
  {
    return new GraphDataFilter(this.graphData);
  }
  
  public void fromFilter(Filter paramFilter)
  {
    this.graphData = new GraphData();
    Insets localInsets = getInsets();
    Point2D.Double localDouble = new Point2D.Double();
    Point2D localPoint2D1 = null;
    Point2D localPoint2D2 = null;
    for (int i = localInsets.left; i < getWidth() - localInsets.left - localInsets.right; i += 2)
    {
      localDouble.setLocation(i, localInsets.top);
      localPoint2D1 = windowToGraph(localDouble);
      double d = paramFilter.getValue(localPoint2D1.getX());
      localPoint2D1.setLocation(localPoint2D1.getX(), d);
      localDouble.setLocation(i + 1, localInsets.top);
      localPoint2D2 = windowToGraph(localDouble);
      d = paramFilter.getValue(localPoint2D2.getX());
      localPoint2D2.setLocation(localPoint2D2.getX(), d);
      this.graphData.addPair(localPoint2D1, localPoint2D2);
    }
    repaint();
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    paintBorder(paramGraphics);
    Insets localInsets = getInsets();
    Iterator localIterator = this.graphData.iterator();
    paramGraphics.setClip(localInsets.top, localInsets.top, getWidth() - localInsets.left - localInsets.right, getHeight() - localInsets.top - localInsets.bottom);
    paramGraphics.setColor(getBackground());
    paramGraphics.fillRect(localInsets.left, localInsets.top, getWidth() - localInsets.left - localInsets.right, getHeight() - localInsets.top - localInsets.bottom);
    paintDataset(paramGraphics);
  }
  
  protected void paintDataset(Graphics paramGraphics)
  {
    paramGraphics.setColor(Color.black);
    Point2D localPoint2D1 = null;
    Object localObject = null;
    Point2D localPoint2D2 = null;
    Point2D localPoint2D3 = null;
    Point2D localPoint2D4 = graphToWindow(new Point2D.Double());
    Polygon localPolygon = new Polygon();
    Iterator localIterator = this.graphData.iterator();
    if (localIterator.hasNext())
    {
      localPoint2D1 = (Point2D)localIterator.next();
      localPoint2D3 = graphToWindow(localPoint2D1);
      localPolygon.addPoint((int)localPoint2D3.getX(), (int)localPoint2D4.getY());
      localPolygon.addPoint((int)localPoint2D3.getX(), (int)localPoint2D3.getY());
      for (localObject = localPoint2D1; localIterator.hasNext(); localObject = localPoint2D2)
      {
        localPoint2D2 = (Point2D)localIterator.next();
        localPoint2D3 = graphToWindow(localPoint2D2);
        localPolygon.addPoint((int)localPoint2D3.getX(), (int)localPoint2D3.getY());
      }
      if (localObject != null)
      {
        localPoint2D3 = graphToWindow(localPoint2D2);
        localPolygon.addPoint((int)localPoint2D3.getX(), (int)localPoint2D4.getY());
      }
    }
    paramGraphics.fillPolygon(localPolygon);
  }
  
  protected Point2D windowToGraph(Point2D paramPoint2D)
  {
    if (paramPoint2D == null) {
      throw new NullPointerException();
    }
    Insets localInsets = getInsets();
    double d1 = 0.0D - this.domainLow;
    double d2 = 0.0D - this.rangeLow;
    double d3 = d1 / (this.domainHigh - this.domainLow);
    double d4 = d2 / (this.rangeHigh - this.rangeLow);
    double d5 = -d3 + (paramPoint2D.getX() - localInsets.left) / (getWidth() - localInsets.left - localInsets.right);
    double d6 = -d4 + (getHeight() - localInsets.top - localInsets.bottom - (paramPoint2D.getY() - localInsets.top)) / (getHeight() - localInsets.top - localInsets.bottom);
    double d7 = d5 * (this.domainHigh - this.domainLow);
    double d8 = d6 * (this.rangeHigh - this.rangeLow);
    return new Point2D.Double(d7, d8);
  }
  
  protected Point2D graphToWindow(Point2D paramPoint2D)
  {
    if (paramPoint2D == null) {
      throw new NullPointerException();
    }
    Insets localInsets = getInsets();
    double d1 = 0.0D - this.domainLow;
    double d2 = 0.0D - this.rangeLow;
    double d3 = d1 / (this.domainHigh - this.domainLow);
    double d4 = d2 / (this.rangeHigh - this.rangeLow);
    double d5 = d3 + paramPoint2D.getX() / (this.domainHigh - this.domainLow);
    double d6 = d4 + paramPoint2D.getY() / (this.rangeHigh - this.rangeLow);
    double d7 = localInsets.left + d5 * (getWidth() - localInsets.left - localInsets.right);
    double d8 = localInsets.top + (getHeight() - localInsets.top - localInsets.bottom) - d6 * (getHeight() - localInsets.top - localInsets.bottom);
    return new Point2D.Double(d7, d8);
  }
  
  public static class GraphDataFilter
    implements Filter
  {
    protected FilterSketcher.GraphData data;
    private double width;
    private double height;
    
    public GraphDataFilter(FilterSketcher.GraphData paramGraphData)
    {
      this.data = new FilterSketcher.GraphData(paramGraphData);
      this.width = calcWidthFromData();
      this.height = calcHeightFromData();
    }
    
    public double getWidth()
    {
      return this.width;
    }
    
    public void setWidth(double paramDouble)
    {
      if (paramDouble < 0.0D) {
        throw new IllegalArgumentException();
      }
      double d = this.width;
      this.width = paramDouble;
      if (this.width < d)
      {
        Iterator localIterator = this.data.iterator();
        Object localObject = null;
        Point2D localPoint2D = null;
        FilterSketcher.GraphData localGraphData = new FilterSketcher.GraphData();
        while (localIterator.hasNext())
        {
          localPoint2D = (Point2D)localIterator.next();
          if (localObject == null)
          {
            if (localPoint2D.getX() >= -(paramDouble / 2.0D)) {
              localObject = localPoint2D;
            }
          }
          else if (localPoint2D.getX() <= paramDouble / 2.0D)
          {
            localGraphData.addPair((Point2D)localObject, localPoint2D);
            localObject = localPoint2D;
          }
        }
        this.data = localGraphData;
      }
    }
    
    public double getHeight()
    {
      return this.height;
    }
    
    public void setHeight(double paramDouble)
    {
      if (paramDouble < 0.0D) {
        throw new IllegalArgumentException();
      }
      double d = this.height;
      this.height = paramDouble;
      if (this.height < d)
      {
        Iterator localIterator = this.data.iterator();
        while (localIterator.hasNext())
        {
          Point2D localPoint2D = (Point2D)localIterator.next();
          if (localPoint2D.getY() > paramDouble) {
            localPoint2D.setLocation(localPoint2D.getX(), paramDouble);
          }
        }
      }
    }
    
    public double getArea()
    {
      Iterator localIterator = this.data.iterator();
      Object localObject = null;
      Point2D localPoint2D1 = null;
      double d1 = 0.0D;
      Point2D localPoint2D2 = null;
      while (localIterator.hasNext())
      {
        localPoint2D2 = (Point2D)localIterator.next();
        if (localPoint2D2.getX() >= -getWidth() / 2.0D) {
          break;
        }
      }
      for (localObject = localPoint2D2; localIterator.hasNext(); localObject = localPoint2D1)
      {
        localPoint2D1 = (Point2D)localIterator.next();
        if (localPoint2D1.getX() > getWidth() / 2.0D) {
          break;
        }
        double d2 = localPoint2D1.getX() - ((Point2D)localObject).getX();
        double d3 = 0.0D;
        double d4 = 0.0D;
        if (((Point2D)localObject).getY() > localPoint2D1.getY())
        {
          d3 = ((Point2D)localObject).getY();
          d4 = localPoint2D1.getY();
        }
        else
        {
          d3 = localPoint2D1.getY();
          d4 = ((Point2D)localObject).getY();
        }
        double d5 = d2 * d4;
        double d6 = 0.5D * d2 * (d3 - d4);
        d1 = d1 + d5 + d6;
      }
      return d1;
    }
    
    public double getValue(double paramDouble)
    {
      if ((paramDouble < -getWidth() / 2.0D) || (paramDouble > getWidth() / 2.0D)) {
        return 0.0D;
      }
      Iterator localIterator = this.data.iterator();
      Object localObject = null;
      Point2D localPoint2D1 = null;
      Point2D localPoint2D2 = null;
      if (!localIterator.hasNext()) {
        return 0.0D;
      }
      localPoint2D2 = (Point2D)localIterator.next();
      if (paramDouble < localPoint2D2.getX()) {
        return 0.0D;
      }
      if (paramDouble == localPoint2D2.getX()) {
        return localPoint2D2.getY();
      }
      localObject = localPoint2D2;
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return 0.0D;
        }
        localPoint2D1 = (Point2D)localIterator.next();
        if (paramDouble < localPoint2D1.getX()) {
          return interpolate((Point2D)localObject, localPoint2D1, paramDouble);
        }
        if (paramDouble == localPoint2D1.getX()) {
          return localPoint2D1.getY();
        }
        localObject = localPoint2D1;
      }
    }
    
    public void normalize()
    {
      double d1 = getArea();
      Iterator localIterator = this.data.iterator();
      while (localIterator.hasNext())
      {
        Point2D localPoint2D = (Point2D)localIterator.next();
        double d2 = localPoint2D.getY();
        localPoint2D.setLocation(localPoint2D.getX(), localPoint2D.getY() / d1);
      }
      this.height = calcHeightFromData();
    }
    
    protected double calcHeightFromData()
    {
      double d = 0.0D;
      Iterator localIterator = this.data.iterator();
      while (localIterator.hasNext())
      {
        Point2D localPoint2D = (Point2D)localIterator.next();
        if (localPoint2D.getY() > d) {
          d = localPoint2D.getY();
        }
      }
      return d;
    }
    
    protected double calcWidthFromData()
    {
      ArrayList localArrayList = this.data.getData();
      double d1 = 0.0D;
      double d2 = 0.0D;
      if (localArrayList.size() >= 1)
      {
        Point2D localPoint2D1 = (Point2D)localArrayList.get(0);
        Point2D localPoint2D2 = (Point2D)localArrayList.get(localArrayList.size() - 1);
        d1 = localPoint2D1.getX();
        d2 = localPoint2D2.getX();
      }
      return 2.0D * Math.max(Math.abs(d1), Math.abs(d2));
    }
    
    protected double interpolate(Point2D paramPoint2D1, Point2D paramPoint2D2, double paramDouble)
    {
      double d1 = (paramDouble - paramPoint2D1.getX()) / (paramPoint2D2.getX() - paramPoint2D1.getX());
      double d2 = paramPoint2D1.getY() + d1 * (paramPoint2D2.getY() - paramPoint2D1.getY());
      return d2;
    }
  }
  
  public static class GraphData
  {
    private ArrayList data = new ArrayList();
    
    public GraphData() {}
    
    public GraphData(GraphData paramGraphData)
    {
      Iterator localIterator = paramGraphData.iterator();
      while (localIterator.hasNext()) {
        this.data.add(localIterator.next());
      }
    }
    
    public void clear()
    {
      this.data.clear();
    }
    
    public Iterator iterator()
    {
      return this.data.iterator();
    }
    
    protected ArrayList getData()
    {
      return this.data;
    }
    
    public void addPair(Point2D paramPoint2D1, Point2D paramPoint2D2)
    {
      if (paramPoint2D1.getX() > paramPoint2D2.getX())
      {
        Point2D localPoint2D1 = paramPoint2D1;
        paramPoint2D1 = paramPoint2D2;
        paramPoint2D2 = localPoint2D1;
      }
      Point2D localPoint2D2 = -1;
      Point2D localPoint2D3 = -1;
      int i = 0;
      int j = 0;
      Point2D localPoint2D5;
      for (Point2D localPoint2D4 = 0; localPoint2D4 < this.data.size(); localPoint2D4++)
      {
        localPoint2D5 = (Point2D)this.data.get(localPoint2D4);
        if ((i == 0) && (localPoint2D5.getX() >= paramPoint2D1.getX()))
        {
          i = 1;
          localPoint2D2 = localPoint2D4;
        }
        if ((i != 0) && (j == 0) && (localPoint2D5.getX() > paramPoint2D2.getX()))
        {
          j = 1;
          localPoint2D3 = localPoint2D4;
          break;
        }
      }
      if (i == 0)
      {
        this.data.add(paramPoint2D1);
        this.data.add(paramPoint2D2);
      }
      else if (j != 0)
      {
        this.data.add(localPoint2D3, paramPoint2D2);
        this.data.add(localPoint2D3, paramPoint2D1);
        if (localPoint2D2 < localPoint2D3) {
          for (localPoint2D5 = localPoint2D2; localPoint2D5 < localPoint2D3; localPoint2D5++) {
            this.data.remove(localPoint2D2);
          }
        }
      }
      else
      {
        int k = this.data.size();
        for (int m = localPoint2D2; m < k; m++) {
          this.data.remove(localPoint2D2);
        }
        this.data.add(paramPoint2D1);
        this.data.add(paramPoint2D2);
      }
    }
    
    public String toString()
    {
      String str = "[";
      for (int i = 0; i < this.data.size(); i++)
      {
        if (i > 0) {
          str = str + ", ";
        }
        str = str + this.data.get(i);
      }
      str = str + "]";
      return str;
    }
  }
  
  private class Listener
    extends MouseInputAdapter
  {
    private Point2D lastPoint;
    private boolean dragging;
    
    private Listener() {}
    
    public void mousePressed(MouseEvent paramMouseEvent)
    {
      if (!this.dragging)
      {
        this.dragging = true;
        this.lastPoint = FilterSketcher.this.windowToGraph(clipToDraggableBounds(paramMouseEvent));
      }
    }
    
    public void mouseDragged(MouseEvent paramMouseEvent)
    {
      if (this.dragging)
      {
        Point2D localPoint2D = FilterSketcher.this.windowToGraph(clipToDraggableBounds(paramMouseEvent));
        FilterSketcher.this.graphData.addPair(this.lastPoint, localPoint2D);
        FilterSketcher.this.repaint();
        this.lastPoint = localPoint2D;
      }
    }
    
    public void mouseReleased(MouseEvent paramMouseEvent)
    {
      if (this.dragging)
      {
        this.lastPoint = null;
        this.dragging = false;
      }
    }
    
    private Point clipToDraggableBounds(MouseEvent paramMouseEvent)
    {
      Insets localInsets = FilterSketcher.this.getInsets();
      int i = paramMouseEvent.getX();
      int j = paramMouseEvent.getY();
      if (i < localInsets.left) {
        i = localInsets.left;
      } else if (i > FilterSketcher.this.getWidth() - localInsets.right) {
        i = FilterSketcher.this.getWidth() - localInsets.right;
      }
      if (j < localInsets.top) {
        j = localInsets.top;
      } else if (j > FilterSketcher.this.getHeight() - localInsets.bottom) {
        j = FilterSketcher.this.getHeight() - localInsets.bottom;
      }
      return new Point(i, j);
    }
    
    Listener(FilterSketcher.1 param1)
    {
      this();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/FilterSketcher.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */