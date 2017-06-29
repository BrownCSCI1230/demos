package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

class CLManagerFive
  extends CLManager
{
  static final int II5_GRAPH_WIDTH = 200;
  static final int II5_GRAPH_HEIGHT = 60;
  static final int II5_RESULT_HEIGHT = 60;
  static final int II5_RESULT_BAR_WIDTH = 30;
  static final int II5_RESULT_PATCH_WIDTH = 55;
  static final int II5_RESULT_PATCH_HEIGHT = 55;
  static final int II5_ACTIVE_LEFT = 0;
  static final int II5_ACTIVE_RIGHT = 1;
  static final int II5_ACTIVE_LIGHT = 2;
  static final int II5_PRODUCT_LEFT = 0;
  static final int II5_PRODUCT_RIGHT = 1;
  static final int II5_RESULT_LEFT = 2;
  static final int II5_RESULT_RIGHT = 3;
  static final int II5_NUM_FILTERS = 3;
  static final int II5_MAX_RESULT_INDEX = 2;
  static final int II5_RESULT_OFFSET = 2;
  static final int Y_1 = 25;
  static final int Y_2 = 135;
  static final int Y_3 = 245;
  static final int Y_4 = 345;
  
  public CLManagerFive(GPDrawingArea paramGPDrawingArea)
  {
    super(paramGPDrawingArea, 200);
    this.active_array_[2] = new CLVisualGraph(200, 60, new GPPoint(175, 25), "Incoming Light", 1);
    this.active_array_[0] = new CLSpectrumGraph(200, 60, new GPPoint(50, 135), "Reflectance", 1);
    this.active_array_[1] = new CLSpectrumGraph(200, 60, new GPPoint(300, 135), "Reflectance", 1);
    this.inactive_array_[0] = new CLSpectrumGraph(200, 60, new GPPoint(50, 245), "Product", 0);
    this.inactive_array_[1] = new CLSpectrumGraph(200, 60, new GPPoint(300, 245), "Product", 0);
    this.inactive_array_[2] = new CLColorPatchResultGraph(3, 60, new GPPoint(85, 345), 30, 55, 55);
    this.inactive_array_[3] = new CLColorPatchResultGraph(3, 60, new GPPoint(335, 345), 30, 55, 55);
    for (int i = 0; i < 3; i++) {
      this.filter_array_[i] = new CLFilter(200, i);
    }
    for (int j = 0; j < 6; j++) {
      this.nonvisual_array_[j] = new CLGraph(200);
    }
  }
  
  public void UpdateRelationships()
  {
    this.active_array_[this.current_active_].SetRange(this.old_index_, this.current_index_, this.old_value_, this.current_value_);
    int i;
    int j;
    if (this.current_active_ == 2)
    {
      i = 0;
      j = 2;
    }
    else
    {
      i = this.current_active_;
      j = i + 1;
    }
    for (int k = i; k < j; k++)
    {
      this.inactive_array_[k].MultiplyRange(this.old_index_, this.current_index_, this.active_array_[k], this.active_array_[2]);
      for (int m = 0; m < 3; m++)
      {
        this.nonvisual_array_[(m + 3 * k)].MultiplyRange(this.old_index_, this.current_index_, this.inactive_array_[k], this.filter_array_[m]);
        this.inactive_array_[(k + 2)].SetElement(m, this.nonvisual_array_[(m + 3 * k)].GetSum() / this.filter_array_[m].GetSum());
      }
      this.inactive_array_[k].DrawBarRange(this.da_, this.old_index_, this.current_index_);
      this.inactive_array_[(k + 2)].DrawBarRange(this.da_, 0, 2);
    }
    this.active_array_[this.current_active_].DrawBarRange(this.da_, this.old_index_, this.current_index_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManagerFive.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */