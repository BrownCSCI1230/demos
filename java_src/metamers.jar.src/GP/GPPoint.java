package GP;

import java.awt.Graphics;

public class GPPoint
  extends GPShape
{
  int x_;
  int y_;
  
  public GPPoint()
  {
    this(0, 0);
  }
  
  public GPPoint(int paramInt1, int paramInt2)
  {
    this.x_ = paramInt1;
    this.y_ = paramInt2;
  }
  
  public GPPoint(GPPoint paramGPPoint)
  {
    this(paramGPPoint.x_, paramGPPoint.y_);
  }
  
  public int GetX()
  {
    return this.x_;
  }
  
  public int GetY()
  {
    return this.y_;
  }
  
  public void SetX(int paramInt)
  {
    this.x_ = paramInt;
  }
  
  public void SetY(int paramInt)
  {
    this.y_ = paramInt;
  }
  
  public void SetCoords(int paramInt1, int paramInt2)
  {
    this.x_ = paramInt1;
    this.y_ = paramInt2;
  }
  
  public void SetPoint(GPPoint paramGPPoint)
  {
    this.x_ = paramGPPoint.x_;
    this.y_ = paramGPPoint.y_;
  }
  
  public String toString()
  {
    return this.x_ + "," + this.y_;
  }
  
  public void Randomize(GPDrawingArea paramGPDrawingArea)
  {
    this.x_ = ((int)Math.round(Math.random() * paramGPDrawingArea.GetWidth()));
    this.y_ = ((int)Math.round(Math.random() * paramGPDrawingArea.GetHeight()));
  }
  
  public void Randomize(GPBoxBoundedShape paramGPBoxBoundedShape)
  {
    this.x_ = ((int)Math.round(Math.random() * paramGPBoxBoundedShape.GetWidth()));
    this.y_ = ((int)Math.round(Math.random() * paramGPBoxBoundedShape.GetHeight()));
    this.x_ += Math.min(paramGPBoxBoundedShape.GetTopLeft().GetX(), paramGPBoxBoundedShape.GetBottomRight().GetX());
    this.y_ += Math.min(paramGPBoxBoundedShape.GetTopLeft().GetY(), paramGPBoxBoundedShape.GetBottomRight().GetY());
  }
  
  public void Randomize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.x_ = ((int)Math.round(Math.random() * Math.abs(paramInt3 - paramInt1)));
    this.y_ = ((int)Math.round(Math.random() * Math.abs(paramInt4 - paramInt2)));
    this.x_ += Math.min(paramInt1, paramInt3);
    this.y_ += Math.min(paramInt2, paramInt4);
  }
  
  public void DrawPrimitive(Graphics paramGraphics)
  {
    paramGraphics.drawLine(this.x_, this.y_, this.x_, this.y_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPPoint.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */