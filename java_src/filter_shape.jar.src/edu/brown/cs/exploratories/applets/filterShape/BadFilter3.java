package edu.brown.cs.exploratories.applets.filterShape;

public class BadFilter3
  implements Filter
{
  public double getWidth()
  {
    return 10.0D;
  }
  
  public double getHeight()
  {
    return 1.0D;
  }
  
  public double getArea()
  {
    return 1.0D;
  }
  
  public double getValue(double paramDouble)
  {
    if ((paramDouble < -5.0D) || (paramDouble > 5.0D)) {
      return 0.0D;
    }
    return 0.1D;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/BadFilter3.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */