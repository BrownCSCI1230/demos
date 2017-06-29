package GP;

public abstract class GPBoxBoundedShape
  extends GPShape
{
  protected GPPoint topLeft_;
  protected GPPoint bottomRight_;
  
  public GPBoxBoundedShape()
  {
    this.topLeft_ = new GPPoint();
    this.bottomRight_ = new GPPoint();
  }
  
  public GPBoxBoundedShape(GPPoint paramGPPoint1, GPPoint paramGPPoint2)
  {
    this.topLeft_ = new GPPoint(paramGPPoint1);
    this.bottomRight_ = new GPPoint(paramGPPoint2);
  }
  
  public GPBoxBoundedShape(GPPoint paramGPPoint, int paramInt1, int paramInt2)
  {
    this.topLeft_ = new GPPoint(paramGPPoint);
    this.bottomRight_ = new GPPoint(paramGPPoint.x_ + paramInt1, paramGPPoint.y_ + paramInt2);
  }
  
  public GPBoxBoundedShape(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.topLeft_ = new GPPoint(paramInt1, paramInt2);
    this.bottomRight_ = new GPPoint(paramInt3, paramInt4);
  }
  
  public GPPoint GetTopLeft()
  {
    return new GPPoint(this.topLeft_);
  }
  
  public GPPoint GetBottomRight()
  {
    return new GPPoint(this.bottomRight_);
  }
  
  public void GetPoints(GPPoint paramGPPoint1, GPPoint paramGPPoint2)
  {
    paramGPPoint1.SetPoint(this.topLeft_);
    paramGPPoint2.SetPoint(this.bottomRight_);
  }
  
  public int GetWidth()
  {
    return Math.abs(this.topLeft_.x_ - this.bottomRight_.x_);
  }
  
  public int GetHeight()
  {
    return Math.abs(this.topLeft_.y_ - this.bottomRight_.y_);
  }
  
  public void SetTopLeft(GPPoint paramGPPoint)
  {
    this.topLeft_.x_ = paramGPPoint.x_;
    this.topLeft_.y_ = paramGPPoint.y_;
  }
  
  public void SetBottomRight(GPPoint paramGPPoint)
  {
    this.bottomRight_.x_ = paramGPPoint.x_;
    this.bottomRight_.y_ = paramGPPoint.y_;
  }
  
  public void SetCoords(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.topLeft_.x_ = paramInt1;
    this.topLeft_.y_ = paramInt2;
    this.bottomRight_.x_ = paramInt3;
    this.bottomRight_.y_ = paramInt4;
  }
  
  public void SetPoints(GPPoint paramGPPoint1, GPPoint paramGPPoint2)
  {
    this.topLeft_.x_ = paramGPPoint1.x_;
    this.topLeft_.y_ = paramGPPoint1.y_;
    this.bottomRight_.x_ = paramGPPoint2.x_;
    this.bottomRight_.y_ = paramGPPoint2.y_;
  }
  
  public String toString()
  {
    return this.topLeft_ + " " + this.bottomRight_;
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPBoxBoundedShape.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */