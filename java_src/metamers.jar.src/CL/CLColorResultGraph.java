package CL;

import GP.GPColor;
import GP.GPPoint;

public class CLColorResultGraph
  extends CLResultGraph
{
  CLColorResultGraph(int paramInt1, int paramInt2, GPPoint paramGPPoint, int paramInt3)
  {
    super(paramInt1, paramInt2, paramGPPoint, paramInt3);
    for (int i = 0; i < this.width_; i++)
    {
      int j = i % 3;
      if (j == 0) {
        this.graph_color_array_[i] = new GPColor(255, 0, 0);
      } else if (j == 1) {
        this.graph_color_array_[i] = new GPColor(0, 255, 0);
      } else if (j == 2) {
        this.graph_color_array_[i] = new GPColor(0, 0, 255);
      }
    }
  }
  
  CLColorResultGraph(CLColorResultGraph paramCLColorResultGraph)
  {
    super(paramCLColorResultGraph);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLColorResultGraph.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */