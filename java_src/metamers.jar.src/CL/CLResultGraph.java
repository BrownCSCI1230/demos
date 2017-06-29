package CL;

import GP.GPBoxBoundedShape;
import GP.GPColor;
import GP.GPDrawingArea;
import GP.GPFilledRect;
import GP.GPFramedRect;
import GP.GPPoint;
import GP.GPText;

public class CLResultGraph
  extends CLVisualGraph
{
  int bar_width_;
  
  public CLResultGraph(int paramInt1, int paramInt2, GPPoint paramGPPoint, int paramInt3)
  {
    super(paramInt1, paramInt2, paramGPPoint, "Result", 0);
    this.bar_width_ = paramInt3;
    this.bar_array_ = new GPFilledRect[this.width_];
    this.graph_color_array_ = new GPColor[this.width_];
    this.color_trigger_array_ = new int[this.width_];
    for (int i = 0; i < this.width_; i++)
    {
      this.graph_color_array_[i] = new GPColor(255, 255, 255);
      this.color_trigger_array_[i] = 0;
    }
    GPPoint localGPPoint1 = new GPPoint(paramGPPoint.GetX() - 1, paramGPPoint.GetY() - 1);
    GPPoint localGPPoint2 = new GPPoint(paramGPPoint.GetX() + this.width_ * this.bar_width_, paramGPPoint.GetY() + this.height_ + 1);
    this.frame_ = new GPFramedRect(localGPPoint1, localGPPoint2);
    this.frame_.SetForeground(new GPColor(50, 50, 255));
    this.label_x_.SetString(" ");
    for (int j = 0; j < this.width_; j++)
    {
      int k = this.corner_.GetY() + this.height_;
      int m = this.corner_.GetX() + j * this.bar_width_ + 1;
      int n = m + paramInt3 - 1;
      this.bar_array_[j] = new GPFilledRect(m, k, n, k);
    }
  }
  
  public CLResultGraph(CLResultGraph paramCLResultGraph)
  {
    super(paramCLResultGraph);
    this.bar_width_ = paramCLResultGraph.bar_width_;
    this.bar_array_ = new GPFilledRect[this.width_];
    for (int i = 0; i < this.width_; i++) {
      this.bar_array_[i] = new GPFilledRect(paramCLResultGraph.bar_array_[i].GetTopLeft(), paramCLResultGraph.bar_array_[i].GetBottomRight());
    }
  }
  
  public void SetElement(int paramInt, double paramDouble)
  {
    if (paramDouble > 1.0D) {
      paramDouble = 1.0D;
    } else if (paramDouble < 0.0D) {
      paramDouble = 0.0D;
    }
    int i = this.corner_.GetX() + paramInt * this.bar_width_;
    int j = i + this.bar_width_ - 1;
    int k = this.corner_.GetY();
    int m = k;
    GPPoint localGPPoint;
    if ((paramDouble < this.element_array_[paramInt]) || (paramDouble == 0.0D))
    {
      this.color_trigger_array_[paramInt] = 0;
      localGPPoint = this.bar_array_[paramInt].GetTopLeft();
      k = localGPPoint.GetY();
      m += (int)(this.height_ * (1.0D - paramDouble));
      this.bar_array_[paramInt].SetCoords(i, k, j, m);
    }
    else
    {
      this.color_trigger_array_[paramInt] = 1;
      localGPPoint = this.bar_array_[paramInt].GetBottomRight();
      m = localGPPoint.GetY();
      k += (int)(this.height_ * (1.0D - paramDouble));
      this.bar_array_[paramInt].SetCoords(i, k, j, m);
    }
    LowLevelSetElement(paramInt, paramDouble);
  }
  
  public void SetElement(GPPoint paramGPPoint) {}
  
  public void Undraw(GPDrawingArea paramGPDrawingArea)
  {
    GPPoint localGPPoint1 = this.frame_.GetTopLeft();
    GPPoint localGPPoint2 = this.frame_.GetBottomRight();
    GPFilledRect localGPFilledRect = new GPFilledRect(localGPPoint1.GetX() - 5, localGPPoint1.GetY(), localGPPoint2.GetX() + 1, localGPPoint2.GetY() + 1);
    localGPFilledRect.Erase(paramGPDrawingArea);
    this.label_x_.Draw(paramGPDrawingArea);
    this.label_y_.Draw(paramGPDrawingArea);
    int i = this.corner_.GetY() + this.height_;
    for (int j = 0; j < this.width_; j++)
    {
      this.color_trigger_array_[j] = 1;
      int k = this.corner_.GetX() + j * this.bar_width_;
      int m = k + this.bar_width_ - 1;
      int n = i - (int)(this.height_ * this.element_array_[j]);
      this.bar_array_[j].SetCoords(k, n, m, i);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLResultGraph.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */