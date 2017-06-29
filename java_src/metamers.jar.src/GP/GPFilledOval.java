package GP;

import java.awt.Graphics;

public class GPFilledOval
  extends GPBoxBoundedShape
{
  public GPFilledOval() {}
  
  public GPFilledOval(GPPoint paramGPPoint1, GPPoint paramGPPoint2)
  {
    super(paramGPPoint1, paramGPPoint2);
  }
  
  public GPFilledOval(GPPoint paramGPPoint, int paramInt1, int paramInt2)
  {
    super(paramGPPoint, paramInt1, paramInt2);
  }
  
  public GPFilledOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void DrawPrimitive(Graphics paramGraphics)
  {
    paramGraphics.fillOval(this.topLeft_.x_, this.topLeft_.y_, Math.abs(this.topLeft_.x_ - this.bottomRight_.x_), Math.abs(this.topLeft_.y_ - this.bottomRight_.y_));
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPFilledOval.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */