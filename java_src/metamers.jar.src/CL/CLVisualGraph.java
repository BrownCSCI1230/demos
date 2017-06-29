package CL;

import GP.GPBoxBoundedShape;
import GP.GPColor;
import GP.GPDrawingArea;
import GP.GPFilledRect;
import GP.GPFramedRect;
import GP.GPLine;
import GP.GPPoint;
import GP.GPText;

public class CLVisualGraph
  extends CLGraph
{
  static final int CL_ACTIVE = 1;
  static final int CL_INACTIVE = 0;
  static final int CL_TICK_LENGTH = 3;
  protected GPBoxBoundedShape[] bar_array_;
  protected GPColor[] graph_color_array_;
  protected int[] color_trigger_array_;
  protected GPFramedRect frame_;
  protected GPPoint corner_;
  protected int height_;
  protected int status_;
  protected GPText label_x_;
  protected GPText label_y_;
  protected GPColor black_color_;
  
  public CLVisualGraph(int paramInt1, int paramInt2, GPPoint paramGPPoint, String paramString, int paramInt3)
  {
    super(paramInt1);
    this.height_ = paramInt2;
    this.corner_ = paramGPPoint;
    this.status_ = paramInt3;
    this.label_x_ = new GPText("Frequency", 0, 0);
    this.label_y_ = new GPText(paramString, 0, 0);
    this.label_x_.SetForeground(new GPColor("white"));
    this.label_y_.SetForeground(new GPColor("white"));
    this.black_color_ = new GPColor(0, 0, 0);
    this.graph_color_array_ = new GPColor[this.width_];
    this.color_trigger_array_ = new int[this.width_];
    for (int i = 0; i < this.width_; i++)
    {
      this.graph_color_array_[i] = new GPColor(255, 255, 255);
      this.color_trigger_array_[i] = 0;
    }
    GPPoint localGPPoint1 = new GPPoint(paramGPPoint.GetX() - 1, paramGPPoint.GetY() - 1);
    GPPoint localGPPoint2 = new GPPoint(paramGPPoint.GetX() + this.width_, paramGPPoint.GetY() + this.height_ + 1);
    this.frame_ = new GPFramedRect(localGPPoint1, localGPPoint2);
    if (this.status_ == 1) {
      this.frame_.SetForeground(new GPColor(255, 50, 50));
    } else {
      this.frame_.SetForeground(new GPColor(50, 50, 255));
    }
    GPPoint localGPPoint3 = this.frame_.GetTopLeft();
    GPPoint localGPPoint4 = this.frame_.GetBottomRight();
    int j = paramString.length();
    this.label_x_.SetCoords(localGPPoint4.GetX() - 55, localGPPoint4.GetY() + 15);
    this.label_y_.SetCoords(localGPPoint3.GetX() - 3 * j, localGPPoint3.GetY() - 6);
    this.bar_array_ = new GPLine[this.width_];
    for (int k = 0; k < this.width_; k++)
    {
      int m = this.corner_.GetY() + this.height_;
      int n = this.corner_.GetX();
      GPPoint localGPPoint5 = new GPPoint(n + k, m);
      this.bar_array_[k] = new GPLine(localGPPoint5, localGPPoint5);
    }
  }
  
  public CLVisualGraph(CLVisualGraph paramCLVisualGraph)
  {
    super(paramCLVisualGraph);
    this.height_ = paramCLVisualGraph.height_;
    this.corner_ = paramCLVisualGraph.corner_;
    this.status_ = paramCLVisualGraph.status_;
    this.label_x_ = paramCLVisualGraph.label_x_;
    this.label_y_ = paramCLVisualGraph.label_y_;
    this.black_color_ = new GPColor(0, 0, 0);
    this.bar_array_ = new GPLine[this.width_];
    for (int i = 0; i < this.width_; i++) {
      this.bar_array_[i] = new GPLine();
    }
    this.graph_color_array_ = new GPColor[this.width_];
    this.color_trigger_array_ = new int[this.width_];
    for (int j = 0; j < this.width_; j++)
    {
      GPPoint localGPPoint1 = paramCLVisualGraph.bar_array_[j].GetTopLeft();
      GPPoint localGPPoint2 = paramCLVisualGraph.bar_array_[j].GetBottomRight();
      this.bar_array_[j] = new GPLine(localGPPoint1, localGPPoint2);
      this.graph_color_array_[j] = paramCLVisualGraph.graph_color_array_[j];
      this.color_trigger_array_[j] = paramCLVisualGraph.color_trigger_array_[j];
    }
  }
  
  public void SetElement(int paramInt, double paramDouble)
  {
    int i = this.corner_.GetX() + paramInt;
    int j = this.corner_.GetY();
    int k = j;
    GPPoint localGPPoint;
    if ((paramDouble < this.element_array_[paramInt]) || (paramDouble == 0.0D))
    {
      this.color_trigger_array_[paramInt] = 0;
      localGPPoint = this.bar_array_[paramInt].GetTopLeft();
      j = localGPPoint.GetY();
      k += (int)(this.height_ * (1.0D - paramDouble));
      this.bar_array_[paramInt].SetCoords(i, j, i, k);
    }
    else
    {
      this.color_trigger_array_[paramInt] = 1;
      localGPPoint = this.bar_array_[paramInt].GetBottomRight();
      k = localGPPoint.GetY();
      j += (int)(this.height_ * (1.0D - paramDouble));
      this.bar_array_[paramInt].SetCoords(i, j, i, k);
    }
    super.SetElement(paramInt, paramDouble);
  }
  
  public void SetElement(GPPoint paramGPPoint)
  {
    int i = ConvertPointToIndex(paramGPPoint);
    double d = ConvertPointToValue(paramGPPoint);
    SetElement(i, d);
  }
  
  public void SetRange(GPPoint paramGPPoint1, GPPoint paramGPPoint2)
  {
    int i = ConvertPointToIndex(paramGPPoint1);
    int j = ConvertPointToIndex(paramGPPoint2);
    double d1 = ConvertPointToValue(paramGPPoint1);
    double d2 = ConvertPointToValue(paramGPPoint2);
    SetRange(i, j, d1, d2);
  }
  
  public void Draw(GPDrawingArea paramGPDrawingArea)
  {
    DrawSurroundings(paramGPDrawingArea);
    for (int i = 0; i < this.width_; i++)
    {
      if (this.color_trigger_array_[i] != 0) {
        this.bar_array_[i].SetForeground(this.graph_color_array_[i]);
      } else {
        this.bar_array_[i].SetForeground(this.black_color_);
      }
      this.bar_array_[i].Draw(paramGPDrawingArea);
    }
  }
  
  public void DrawSurroundings(GPDrawingArea paramGPDrawingArea)
  {
    this.frame_.Draw(paramGPDrawingArea);
    GPColor localGPColor;
    if (this.status_ == 1) {
      localGPColor = new GPColor(255, 50, 50);
    } else {
      localGPColor = new GPColor(50, 50, 255);
    }
    int i = this.corner_.GetX() - 1;
    for (int j = this.corner_.GetY() + this.height_ + 1; j > this.corner_.GetY(); j -= 10)
    {
      GPLine localGPLine = new GPLine(i, j, i - 3, j);
      localGPLine.SetForeground(localGPColor);
      localGPLine.Draw(paramGPDrawingArea);
    }
    this.label_x_.Draw(paramGPDrawingArea);
    this.label_y_.Draw(paramGPDrawingArea);
  }
  
  public void Undraw(GPDrawingArea paramGPDrawingArea)
  {
    GPPoint localGPPoint1 = this.frame_.GetTopLeft();
    GPPoint localGPPoint2 = this.frame_.GetBottomRight();
    GPFilledRect localGPFilledRect = new GPFilledRect(localGPPoint1.GetX() - 5, localGPPoint1.GetY(), localGPPoint2.GetX() + 1, localGPPoint2.GetY() + 1);
    localGPFilledRect.Erase(paramGPDrawingArea);
    this.label_x_.Draw(paramGPDrawingArea);
    this.label_y_.Draw(paramGPDrawingArea);
    int i = this.corner_.GetX();
    int j = this.corner_.GetY() + this.height_;
    for (int k = 0; k < this.width_; k++)
    {
      this.color_trigger_array_[k] = 1;
      int m = j - (int)(this.height_ * this.element_array_[k]);
      this.bar_array_[k].SetCoords(i + k, m, i + k, j);
    }
  }
  
  public void DrawBarRange(GPDrawingArea paramGPDrawingArea, int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2)
    {
      if (this.color_trigger_array_[paramInt2] != 0) {
        this.bar_array_[paramInt2].SetForeground(this.graph_color_array_[paramInt2]);
      } else {
        this.bar_array_[paramInt2].SetForeground(this.black_color_);
      }
      this.bar_array_[paramInt2].Draw(paramGPDrawingArea);
    }
    else
    {
      int i = paramInt1;
      int j = (paramInt2 - paramInt1) / Math.abs(paramInt2 - paramInt1);
      paramInt2 += j;
      while (i != paramInt2)
      {
        if (this.color_trigger_array_[i] != 0) {
          this.bar_array_[i].SetForeground(this.graph_color_array_[i]);
        } else {
          this.bar_array_[i].SetForeground(this.black_color_);
        }
        this.bar_array_[i].Draw(paramGPDrawingArea);
        i += j;
      }
    }
  }
  
  public boolean PointInGraph(GPPoint paramGPPoint)
  {
    int i = 0;
    int j = paramGPPoint.GetX();
    int k = paramGPPoint.GetY();
    GPPoint localGPPoint1 = this.frame_.GetTopLeft();
    GPPoint localGPPoint2 = this.frame_.GetBottomRight();
    if ((j > localGPPoint1.GetX()) && (j < localGPPoint2.GetX()) && (k > localGPPoint1.GetY()) && (k < localGPPoint2.GetY())) {
      i = 1;
    }
    return (i == 1) && (this.status_ != 0);
  }
  
  public int ConvertPointToIndex(GPPoint paramGPPoint)
  {
    int i = paramGPPoint.GetX();
    int j = paramGPPoint.GetY();
    int k = this.corner_.GetX();
    int m = this.corner_.GetY();
    int n = i - k;
    if (n < 0) {
      n = 0;
    } else if (n >= this.width_) {
      n = this.width_ - 1;
    }
    return n;
  }
  
  public double ConvertPointToValue(GPPoint paramGPPoint)
  {
    int i = paramGPPoint.GetX();
    int j = paramGPPoint.GetY();
    int k = this.corner_.GetX();
    int m = this.corner_.GetY();
    double d = (m + this.height_ - j) / this.height_;
    if (d < 0.0D) {
      d = 0.0D;
    } else if (d > 1.0D) {
      d = 1.0D;
    }
    return d;
  }
  
  public int GetStatus()
  {
    return this.status_;
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLVisualGraph.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */