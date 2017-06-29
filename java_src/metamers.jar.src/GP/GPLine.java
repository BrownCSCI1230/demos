package GP;

import java.awt.Graphics;

public class GPLine
  extends GPBoxBoundedShape
{
  public GPLine() {}
  
  public GPLine(GPPoint paramGPPoint1, GPPoint paramGPPoint2)
  {
    super(paramGPPoint1, paramGPPoint2);
  }
  
  public GPLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public GPPoint GetStart()
  {
    return new GPPoint(this.topLeft_);
  }
  
  public GPPoint GetEnd()
  {
    return new GPPoint(this.bottomRight_);
  }
  
  public void SetStart(GPPoint paramGPPoint)
  {
    this.topLeft_.x_ = paramGPPoint.x_;
    this.topLeft_.y_ = paramGPPoint.y_;
  }
  
  public void SetEnd(GPPoint paramGPPoint)
  {
    this.bottomRight_.x_ = paramGPPoint.x_;
    this.bottomRight_.y_ = paramGPPoint.y_;
  }
  
  public void DrawPrimitive(Graphics paramGraphics)
  {
    paramGraphics.drawLine(this.topLeft_.x_, this.topLeft_.y_, this.bottomRight_.x_, this.bottomRight_.y_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPLine.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */