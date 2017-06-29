package CL;

public class CLGraph
{
  protected int width_;
  protected double sum_;
  protected double[] element_array_;
  
  public CLGraph(int paramInt)
  {
    this.width_ = paramInt;
    this.sum_ = 0.0D;
    this.element_array_ = new double[paramInt];
    for (int i = 0; i < this.width_; i++) {
      this.element_array_[i] = 0.0D;
    }
  }
  
  public CLGraph(CLGraph paramCLGraph)
  {
    this.width_ = paramCLGraph.width_;
    this.sum_ = paramCLGraph.sum_;
    this.element_array_ = new double[this.width_];
    for (int i = 0; i < this.width_; i++) {
      this.element_array_[i] = paramCLGraph.element_array_[i];
    }
  }
  
  public void NoHeight()
  {
    for (int i = 0; i < this.width_; i++) {
      SetElement(i, 0.0D);
    }
  }
  
  public void FullHeight()
  {
    for (int i = 0; i < this.width_; i++) {
      SetElement(i, 1.0D);
    }
  }
  
  public double GetSum()
  {
    return this.sum_;
  }
  
  public double GetArea()
  {
    return this.sum_ / this.width_;
  }
  
  public double GetElement(int paramInt)
  {
    return this.element_array_[paramInt];
  }
  
  public void SetElement(int paramInt, double paramDouble)
  {
    this.sum_ -= this.element_array_[paramInt];
    this.sum_ += paramDouble;
    if (this.sum_ < 0.0D) {
      this.sum_ = 0.0D;
    } else if (this.sum_ > this.width_) {
      this.sum_ = this.width_;
    }
    this.element_array_[paramInt] = paramDouble;
  }
  
  public void LowLevelSetElement(int paramInt, double paramDouble)
  {
    this.sum_ -= this.element_array_[paramInt];
    this.sum_ += paramDouble;
    if (this.sum_ < 0.0D) {
      this.sum_ = 0.0D;
    } else if (this.sum_ > this.width_) {
      this.sum_ = this.width_;
    }
    this.element_array_[paramInt] = paramDouble;
  }
  
  public void SetRange(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2)
  {
    if (paramInt1 == paramInt2)
    {
      SetElement(paramInt2, paramDouble2);
    }
    else
    {
      double d1 = paramDouble1;
      double d2 = (paramDouble2 - paramDouble1) / Math.abs(paramInt2 - paramInt1);
      int i = paramInt1;
      int j = (paramInt2 - paramInt1) / Math.abs(paramInt2 - paramInt1);
      while (i != paramInt2)
      {
        SetElement(i, d1);
        i += j;
        d1 += d2;
      }
    }
  }
  
  public void Multiply(CLGraph paramCLGraph)
  {
    MultiplyRange(0, this.width_ - 1, paramCLGraph);
  }
  
  public void Multiply(CLGraph paramCLGraph1, CLGraph paramCLGraph2)
  {
    MultiplyRange(0, this.width_ - 1, paramCLGraph1, paramCLGraph2);
  }
  
  public void MultiplyRange(int paramInt1, int paramInt2, CLGraph paramCLGraph)
  {
    MultiplyRange(paramInt1, paramInt2, paramCLGraph);
  }
  
  public void MultiplyRange(int paramInt1, int paramInt2, CLGraph paramCLGraph1, CLGraph paramCLGraph2)
  {
    if (paramInt1 == paramInt2)
    {
      SetElement(paramInt2, paramCLGraph1.element_array_[paramInt2] * paramCLGraph2.element_array_[paramInt2]);
    }
    else
    {
      int i = paramInt1;
      int j = (paramInt2 - paramInt1) / Math.abs(paramInt2 - paramInt1);
      paramInt2 += j;
      while (i != paramInt2)
      {
        SetElement(i, paramCLGraph1.element_array_[i] * paramCLGraph2.element_array_[i]);
        i += j;
      }
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLGraph.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */