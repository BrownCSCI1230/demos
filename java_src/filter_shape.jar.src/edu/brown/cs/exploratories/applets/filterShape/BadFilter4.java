package edu.brown.cs.exploratories.applets.filterShape;

public class BadFilter4
  implements Filter
{
  public double getWidth()
  {
    return 15.0D;
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
    if ((paramDouble < -7.5D) || (paramDouble > 7.5D)) {
      return 0.0D;
    }
    if (paramDouble < -5.0D) {
      return 0.4D * (-paramDouble - 5.0D) / 2.5D;
    }
    if (paramDouble > 5.0D) {
      return 0.4D * (paramDouble - 5.0D) / 2.5D;
    }
    return 0.0D;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/BadFilter4.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */