package edu.brown.cs.exploratories.applets.filterShape;

public class GaussianFilter
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
  
  public double getValue(double paramDouble)
  {
    if ((paramDouble < -10.0D) || (paramDouble > 10.0D)) {
      return 0.0D;
    }
    double d = 0.25D;
    return 1.0D / (2.0D * Math.sqrt(3.141592653589793D * d)) * Math.exp(-(paramDouble * paramDouble / (4.0D * d)));
  }
  
  public double getArea()
  {
    return 1.0D;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/GaussianFilter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */