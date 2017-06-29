package edu.brown.cs.exploratories.applets.filterShape;

public class TriangleFilter
  implements Filter
{
  public double getWidth()
  {
    return 2.0D;
  }
  
  public double getHeight()
  {
    return 1.0D;
  }
  
  public double getValue(double paramDouble)
  {
    if ((paramDouble < -1.0D) || (paramDouble > 1.0D)) {
      return 0.0D;
    }
    if (paramDouble <= 0.0D) {
      return 1.0D + paramDouble;
    }
    return 1.0D - paramDouble;
  }
  
  public double getArea()
  {
    return 1.0D;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/TriangleFilter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */