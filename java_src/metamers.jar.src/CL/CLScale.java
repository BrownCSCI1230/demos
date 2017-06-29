package CL;

import GP.GPColor;
import GP.GPDrawingArea;
import GP.GPFilledRect;
import GP.GPFont;
import GP.GPPoint;
import GP.GPText;

public class CLScale
{
  private static final int THUMB_SIZE = 4;
  private static final int BUFFER = 2;
  private static final int TEXT_HEIGHT = 18;
  private static final int TEXT_BUFFER = 3;
  private static final int DEFAULT_WIDTH = 100;
  private static final int DEFAULT_HEIGHT = 15;
  private static final int MIN_WIDTH = 14;
  private static final int MIN_HEIGHT = 6;
  private int height_;
  private int width_;
  private int left_;
  private int bottom_;
  boolean showValue_;
  int min_;
  int max_;
  int value_;
  int pixel_;
  int pixelMin_;
  int pixelMax_;
  GPFont font_;
  GPDrawingArea da_;
  GPPoint location_;
  
  public CLScale(GPDrawingArea paramGPDrawingArea, GPPoint paramGPPoint)
  {
    this.da_ = paramGPDrawingArea;
    this.location_ = paramGPPoint;
    this.left_ = paramGPPoint.GetX();
    this.bottom_ = paramGPPoint.GetY();
    this.min_ = 1;
    this.max_ = 100;
    this.showValue_ = false;
    this.width_ = 100;
    this.height_ = 33;
    this.pixelMin_ = (this.left_ + 4 + 2);
    this.pixelMax_ = (this.left_ + this.width_ - 4 - 2 - 1);
    this.font_ = new GPFont();
    SetValue(1);
  }
  
  public void Motion() {}
  
  public void Release() {}
  
  public void SetMaximum(int paramInt)
  {
    this.max_ = paramInt;
    if (this.max_ < this.min_)
    {
      int i = this.min_;
      this.min_ = this.max_;
      this.max_ = i;
    }
    SetValue(this.value_);
  }
  
  public void SetMinimum(int paramInt)
  {
    this.min_ = paramInt;
    if (this.max_ < this.min_)
    {
      int i = this.min_;
      this.min_ = this.max_;
      this.max_ = i;
    }
    SetValue(this.value_);
  }
  
  public void SetValue(int paramInt)
  {
    this.value_ = paramInt;
    if (this.value_ < this.min_) {
      this.value_ = this.min_;
    } else if (this.value_ > this.max_) {
      this.value_ = this.max_;
    }
    if (this.value_ != this.min_) {
      this.pixel_ = (Math.round(Math.abs((this.value_ - this.min_) / (this.max_ - this.min_)) * (this.pixelMax_ - this.pixelMin_)) + this.pixelMin_);
    } else {
      this.pixel_ = this.pixelMin_;
    }
    Redraw();
  }
  
  public void SetHeight(int paramInt)
  {
    if (paramInt < 6) {
      paramInt = 6;
    }
    this.height_ = (paramInt + 18);
    Redraw();
  }
  
  public void SetWidth(int paramInt)
  {
    if (paramInt < 14) {
      paramInt = 14;
    }
    this.width_ = paramInt;
    this.pixelMin_ = (this.left_ + 4 + 2);
    this.pixelMax_ = (this.left_ + this.width_ - 4 - 2 - 1);
    Redraw();
  }
  
  public void ShowVal()
  {
    this.showValue_ = true;
    Redraw();
  }
  
  public void HideVal()
  {
    this.showValue_ = false;
    Redraw();
  }
  
  public int GetValue()
  {
    return this.value_;
  }
  
  public void Redraw()
  {
    int i = this.location_.GetX();
    int j = this.location_.GetY() - this.height_;
    GPPoint localGPPoint1 = new GPPoint(i + this.width_, j + this.height_ - 18);
    GPPoint localGPPoint2 = new GPPoint(i, j + this.height_ - 18);
    GPPoint localGPPoint3 = new GPPoint(i + this.width_, j + this.height_);
    GPPoint localGPPoint4 = new GPPoint(this.pixel_ - 4, j);
    GPPoint localGPPoint5 = new GPPoint(this.pixel_ + 4, j + this.height_ - 18);
    GPFilledRect localGPFilledRect1 = new GPFilledRect(new GPPoint(i, j), localGPPoint1);
    localGPFilledRect1.SetForeground(new GPColor("Dark Gray"));
    GPFilledRect localGPFilledRect2 = new GPFilledRect(localGPPoint2, localGPPoint3);
    localGPFilledRect2.SetForeground(new GPColor("Light Gray"));
    GPFilledRect localGPFilledRect3 = new GPFilledRect(localGPPoint4, localGPPoint5);
    localGPFilledRect3.SetForeground(new GPColor("Orange"));
    localGPFilledRect1.Draw(this.da_);
    localGPFilledRect3.Draw(this.da_);
    if (this.showValue_)
    {
      String str = String.valueOf(this.value_);
      GPText localGPText = new GPText(str, this.pixel_ - this.font_.GetStringWidth(this.da_, str) / 2, localGPPoint2.GetY() + 18 - 3);
      localGPFilledRect2.Draw(this.da_);
      localGPText.Draw(this.da_);
    }
  }
  
  public boolean mouseStuff(GPPoint paramGPPoint, int paramInt)
  {
    int i = paramGPPoint.GetX();
    if (i < this.pixelMin_) {
      return false;
    }
    if (i > this.pixelMax_) {
      return false;
    }
    int j = paramGPPoint.GetY();
    if (j > this.bottom_) {
      return false;
    }
    if (j < this.bottom_ - this.height_) {
      return false;
    }
    if (paramInt == 0) {
      mouseDown(paramGPPoint);
    }
    if (paramInt == 1) {
      mouseDrag(paramGPPoint);
    }
    if (paramInt == 2) {
      mouseUp(paramGPPoint);
    }
    return true;
  }
  
  void HandleMouse(int paramInt)
  {
    this.pixel_ = Math.max(paramInt, this.pixelMin_);
    this.pixel_ = Math.min(this.pixel_, this.pixelMax_);
    double d;
    if (this.pixel_ != this.pixelMin_) {
      d = (this.pixel_ - this.pixelMin_) / (this.pixelMax_ - this.pixelMin_);
    } else {
      d = 0.0D;
    }
    this.value_ = ((int)Math.round(d * (this.max_ - this.min_)) + this.min_);
    Redraw();
  }
  
  public boolean mouseDown(GPPoint paramGPPoint)
  {
    HandleMouse(paramGPPoint.GetX());
    Motion();
    return true;
  }
  
  public boolean mouseDrag(GPPoint paramGPPoint)
  {
    HandleMouse(paramGPPoint.GetX());
    Motion();
    return true;
  }
  
  public boolean mouseUp(GPPoint paramGPPoint)
  {
    HandleMouse(paramGPPoint.GetX());
    Release();
    return true;
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLScale.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */