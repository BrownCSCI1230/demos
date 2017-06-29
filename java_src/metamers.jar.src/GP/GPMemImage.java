package GP;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class GPMemImage
  extends GPShape
{
  private static final int SLEEP = 300;
  private static final int TIMEOUT = 30000;
  Image image_;
  GPPoint topLeft_;
  GPDrawingArea da_;
  boolean erasing_;
  int width_;
  int height_;
  int[] pixels_ = null;
  
  private GPMemImage() {}
  
  public GPMemImage(GPPoint paramGPPoint, int paramInt1, int paramInt2, GPDrawingArea paramGPDrawingArea)
  {
    this.da_ = paramGPDrawingArea;
    this.erasing_ = false;
    this.topLeft_ = paramGPPoint;
    this.width_ = paramInt1;
    this.height_ = paramInt2;
    this.pixels_ = new int[this.width_ * this.height_];
    int i = 0;
    for (int n = 0; n < this.height_; n++) {
      for (int i1 = 0; i1 < this.width_; i1++)
      {
        int m;
        int k;
        int j = k = m = 'ª';
        this.pixels_[(i++)] = (0xFF000000 | j << 16 | k << 8 | m);
      }
    }
    rebuildFromMemory();
    Draw(this.da_);
  }
  
  public void SetColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.pixels_[(paramInt5 * this.width_ + paramInt4)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[(paramInt5 * this.width_ + paramInt4 + 1)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[(paramInt5 * this.width_ + paramInt4 - 1)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[((paramInt5 + 1) * this.width_ + paramInt4)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[((paramInt5 + 1) * this.width_ + paramInt4 + 1)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[((paramInt5 + 1) * this.width_ + paramInt4 - 1)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[((paramInt5 - 1) * this.width_ + paramInt4)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[((paramInt5 - 1) * this.width_ + paramInt4 + 1)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    this.pixels_[((paramInt5 - 1) * this.width_ + paramInt4 - 1)] = (0xFF000000 | paramInt1 << 16 | paramInt2 << 8 | paramInt3);
    rebuildFromMemory();
  }
  
  public GPPoint GetTopLeft()
  {
    return new GPPoint(this.topLeft_);
  }
  
  public void SetTopLeft(GPPoint paramGPPoint)
  {
    this.topLeft_ = paramGPPoint;
  }
  
  public int GetWidth()
  {
    return this.image_.getWidth(null);
  }
  
  public int GetHeight()
  {
    return this.image_.getHeight(null);
  }
  
  public Image GetImage()
  {
    return this.image_;
  }
  
  public void Erase(GPDrawingArea paramGPDrawingArea)
  {
    this.erasing_ = true;
    super.Erase(paramGPDrawingArea);
    this.erasing_ = false;
  }
  
  public void DrawPrimitive(Graphics paramGraphics)
  {
    if (!this.erasing_) {
      paramGraphics.drawImage(this.image_, this.topLeft_.x_, this.topLeft_.y_, this.da_);
    } else {
      paramGraphics.fillRect(this.topLeft_.x_, this.topLeft_.y_, this.image_.getWidth(null), this.image_.getHeight(null));
    }
  }
  
  public void rebuildFromMemory()
  {
    this.image_ = this.da_.createImage(new MemoryImageSource(this.width_, this.height_, this.pixels_, 0, this.width_));
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPMemImage.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */