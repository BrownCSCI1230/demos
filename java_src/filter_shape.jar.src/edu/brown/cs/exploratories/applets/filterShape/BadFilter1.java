package edu.brown.cs.exploratories.applets.filterShape;

public class BadFilter1
  implements Filter
{
  public double getWidth()
  {
    return 2.0D;
  }
  
  public double getHeight()
  {
    return 1.414D;
  }
  
  public double getArea()
  {
    return 1.6D;
  }
  
  public double getValue(double paramDouble)
  {
    if ((paramDouble < -1.0D) || (paramDouble > 1.0D)) {
      return 0.0D;
    }
    return 1.5D * ((1.0D - paramDouble * paramDouble) / Math.sqrt(2.0D));
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/BadFilter1.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */