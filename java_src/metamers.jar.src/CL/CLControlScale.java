package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

public class CLControlScale
  extends CLScale
{
  private CLVisualGraph graph = null;
  private CLManagerSeven manager_ = null;
  private int old_value_;
  
  public CLControlScale(GPDrawingArea paramGPDrawingArea, GPPoint paramGPPoint, CLVisualGraph paramCLVisualGraph, CLManagerSeven paramCLManagerSeven)
  {
    super(paramGPDrawingArea, paramGPPoint);
    this.graph = paramCLVisualGraph;
    this.manager_ = paramCLManagerSeven;
    this.old_value_ = 7;
  }
  
  public void Motion()
  {
    this.graph.SetRange(this.old_value_ - 7, this.old_value_ + 7, 0.0D, 0.0D);
    this.graph.SetRange(this.value_ - 7, this.value_ + 7, 1.0D, 1.0D);
    this.old_value_ = this.value_;
  }
  
  public void Release()
  {
    this.graph.SetRange(this.old_value_ - 7, this.old_value_ + 7, 0.0D, 0.0D);
    this.graph.SetRange(this.value_ - 7, this.value_ + 7, 1.0D, 1.0D);
    this.old_value_ = this.value_;
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLControlScale.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */