package CL;

import GP.GPColor;
import GP.GPDrawingArea;
import GP.GPLine;
import GP.GPPoint;

public class CLSpectrumGraph
  extends CLVisualGraph
{
  public CLSpectrumGraph(int paramInt1, int paramInt2, GPPoint paramGPPoint, String paramString, int paramInt3)
  {
    super(paramInt1, paramInt2, paramGPPoint, paramString, paramInt3);
    int i = this.width_ / 6;
    int j = this.width_ - 6 * i;
    double d2 = 1.0D;
    double d1 = 0.0D;
    for (int k = 0; k < i; k++)
    {
      d3 = k / i;
      this.graph_color_array_[k] = ConvertToRGB(d1, d2, d3);
    }
    d2 = 1.0D;
    double d3 = 1.0D;
    for (k = 0; k < 4 * i; k++)
    {
      d1 = (int)(240.0D * (k / (4 * i)));
      this.graph_color_array_[(k + i)] = ConvertToRGB(d1, d2, d3);
    }
    d2 = 1.0D;
    d1 = 240.0D;
    for (k = 0; k < i + j; k++)
    {
      d3 = 1.0D - k / (i + j);
      this.graph_color_array_[(k + 5 * i)] = ConvertToRGB(d1, d2, d3);
    }
  }
  
  public CLSpectrumGraph(CLSpectrumGraph paramCLSpectrumGraph)
  {
    super(paramCLSpectrumGraph);
  }
  
  public void DrawSurroundings(GPDrawingArea paramGPDrawingArea)
  {
    int i = this.corner_.GetX();
    int j = this.corner_.GetY();
    for (int k = 0; k < this.width_; k++)
    {
      GPLine localGPLine = new GPLine(i + k, j - 5, i + k, j - 7);
      localGPLine.SetForeground(this.graph_color_array_[k]);
      localGPLine.Draw(paramGPDrawingArea);
    }
    super.DrawSurroundings(paramGPDrawingArea);
  }
  
  public void Undraw(GPDrawingArea paramGPDrawingArea)
  {
    int i = this.corner_.GetX();
    int j = this.corner_.GetY();
    for (int k = 0; k < this.width_; k++)
    {
      GPLine localGPLine = new GPLine(i + k, j - 5, i + k, j - 7);
      localGPLine.Erase(paramGPDrawingArea);
    }
    super.Undraw(paramGPDrawingArea);
  }
  
  GPColor ConvertToRGB(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d8;
    double d7;
    double d6 = d7 = d8 = 0.0D;
    if (paramDouble2 == 0.0D)
    {
      if (paramDouble1 == 0.0D) {
        d6 = d7 = d8 = paramDouble3;
      } else {
        d6 = d7 = d8 = 0.0D;
      }
    }
    else
    {
      if (paramDouble1 >= 360.0D) {
        paramDouble1 -= 360.0D;
      }
      paramDouble1 /= 60.0D;
      double d2 = Math.floor(paramDouble1);
      double d1 = paramDouble1 - d2;
      double d3 = paramDouble3 * (1.0D - paramDouble2);
      double d4 = paramDouble3 * (1.0D - paramDouble2 * d1);
      double d5 = paramDouble3 * (1.0D - paramDouble2 * (1.0D - d1));
      if (d2 == 0.0D)
      {
        d6 = paramDouble3;
        d7 = d5;
        d8 = d3;
      }
      else if (d2 == 1.0D)
      {
        d6 = d4;
        d7 = paramDouble3;
        d8 = d3;
      }
      else if (d2 == 2.0D)
      {
        d6 = d3;
        d7 = paramDouble3;
        d8 = d5;
      }
      else if (d2 == 3.0D)
      {
        d6 = d3;
        d7 = d4;
        d8 = paramDouble3;
      }
      else if (d2 == 4.0D)
      {
        d6 = d5;
        d7 = d3;
        d8 = paramDouble3;
      }
      else if (d2 == 5.0D)
      {
        d6 = paramDouble3;
        d7 = d3;
        d8 = d4;
      }
    }
    return new GPColor((int)(d6 * 255.0D), (int)(d7 * 255.0D), (int)(d8 * 255.0D));
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLSpectrumGraph.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */