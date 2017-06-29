package CL;

public class CLFilter
  extends CLGraph
{
  static final int CL_RED = 0;
  static final int CL_GREEN = 1;
  static final int CL_BLUE = 2;
  protected double area_;
  
  public CLFilter(int paramInt1, int paramInt2)
  {
    super(paramInt1);
    for (int i = 0; i < this.width_; i++) {
      SetElement(i, 0.0D);
    }
    int j = (paramInt2 * 2 + 1) * (int)Math.round(this.width_ / 6.0D);
    int k = this.width_ / -2;
    int m = k * -1;
    if (k + j < 0) {
      k = 0 - j;
    }
    if (m + j > this.width_) {
      m = this.width_ - j;
    }
    for (int n = k; n < m; n++)
    {
      int i1 = n + j;
      SetElement(i1, Math.exp(-1.0D * (n * n) / (this.width_ * 8.5D)));
    }
    this.area_ = (GetSum() / this.width_);
  }
  
  public void FullHeight() {}
  
  public void NoHeight() {}
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLFilter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */