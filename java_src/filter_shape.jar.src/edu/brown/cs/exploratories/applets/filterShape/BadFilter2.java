package edu.brown.cs.exploratories.applets.filterShape;

public class BadFilter2
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
  
  public double getArea()
  {
    return 0.629D;
  }
  
  public double getValue(double paramDouble)
  {
    if ((paramDouble < -1.0D) || (paramDouble > 1.0D)) {
      return 0.0D;
    }
    return (1.0D - paramDouble * paramDouble) / Math.sqrt(2.0D) / 1.5D;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/BadFilter2.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */