package CL;

import GP.GPColor;
import GP.GPDrawingArea;
import GP.GPFilledRect;
import GP.GPMemImage;
import GP.GPPoint;

class CLManagerSeven
  extends CLManager
{
  static final int II7_GRAPH_WIDTH = 130;
  static final int II7_GRAPH_HEIGHT = 90;
  static final int II7_RESULT_HEIGHT = 90;
  static final int II7_RESULT_BAR_WIDTH = 43;
  static final int II7_INPUT = 0;
  static final int II7_FILTER_GRAPH_OFFSET = 1;
  static final int II7_VISUAL_PRODUCT_OFFSET = 4;
  static final int II7_VISUAL_RESULT_OFFSET = 7;
  static final int II7_NUM_GRAPH_SETS = 3;
  static final int II7_MAX_RESULT_INDEX = 2;
  static final int II7_IMAGE_WIDTH = 200;
  static final int II7_IMAGE_HEIGHT = 200;
  static final int II7_IMAGE_LOC_X = 100;
  static final int II7_IMAGE_LOC_Y = 200;
  static final int II7_IMAGE_CENTER_X = 100;
  static final int II7_IMAGE_CENTER_Y = 100;
  static final int II7_TRIANGLE_SIZE = 90;
  GPMemImage color_image_ = null;
  
  public CLManagerSeven(GPDrawingArea paramGPDrawingArea)
  {
    super(paramGPDrawingArea, 130);
    this.current_active_ = -1;
    this.color_image_ = new GPMemImage(new GPPoint(100, 200), 200, 200, paramGPDrawingArea);
    this.color_image_.Draw(paramGPDrawingArea);
    this.inactive_array_[0] = new CLVisualGraph(130, 90, new GPPoint(115, 25), "Nearly-monospectral input signal", 0);
    this.slider_array_[0] = new CLControlScale(this.da_, new GPPoint(115, 153), this.inactive_array_[0], this);
    this.slider_array_[0].SetMaximum(123);
    this.slider_array_[0].SetMinimum(8);
    this.slider_array_[0].SetValue(80);
    this.slider_array_[0].SetHeight(15);
    this.slider_array_[0].SetWidth(130);
    this.slider_array_[0].HideVal();
    for (int i = 0; i < 3; i++)
    {
      j = 40 + 170 * i;
      this.nonvisual_array_[(i + 1)] = new CLGraph(130);
    }
    for (int j = 4; j < 7; j++)
    {
      k = 40 + 170 * (j - 4);
      this.nonvisual_array_[j] = new CLGraph(130);
    }
    this.inactive_array_[7] = new CLColorResultGraph(3, 90, new GPPoint(300, 25), 43);
    for (int k = 0; k < 3; k++)
    {
      this.filter_array_[k] = new CLFilter(130, k);
      for (int m = 0; m < 130; m++) {
        this.nonvisual_array_[(k + 1)].SetElement(m, this.filter_array_[k].GetElement(m));
      }
    }
  }
  
  public void UpdateRelationships()
  {
    double[] arrayOfDouble = new double[3];
    this.inactive_array_[0].DrawBarRange(this.da_, 0, 129);
    for (int i2 = 0; i2 < 3; i2++)
    {
      this.nonvisual_array_[(i2 + 4)].MultiplyRange(0, 129, this.inactive_array_[0], this.filter_array_[i2]);
      this.inactive_array_[7].SetElement(i2, arrayOfDouble[i2] = this.nonvisual_array_[(i2 + 4)].GetSum() / this.filter_array_[i2].GetSum());
    }
    this.inactive_array_[7].DrawBarRange(this.da_, 0, 2);
    int i = (int)Math.round(100.0D * arrayOfDouble[0]);
    int j = (int)Math.round(100.0D * arrayOfDouble[1]);
    int k = (int)Math.round(100.0D * arrayOfDouble[2]);
    int i1 = i + j + k;
    i = 255 * i / i1;
    j = 255 * j / i1;
    k = 255 * k / i1;
    int m = (int)Math.round(100.0D + 90 * (k - i) * 0.866D / 255.0D);
    int n = (int)Math.round(100.0D + 90.0D * ((i + k) / 2.0D - j) / 255.0D);
    GPFilledRect localGPFilledRect = new GPFilledRect(100 + m - 1, 200 + n - 1, 100 + m + 1, 200 + n + 1);
    localGPFilledRect.SetForeground(new GPColor(i, j, k));
    localGPFilledRect.Draw(this.da_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManagerSeven.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */