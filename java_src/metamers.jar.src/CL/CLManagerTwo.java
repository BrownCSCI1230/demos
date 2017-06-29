package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

class CLManagerTwo
  extends CLManager
{
  static final int II2_GRAPH_WIDTH = 130;
  static final int II2_GRAPH_HEIGHT = 90;
  static final int II2_RESULT_HEIGHT = 90;
  static final int II2_RESULT_BAR_WIDTH = 43;
  static final int II2_ONLY_ACTIVE = 0;
  static final int II2_VISUAL_PRODUCT_OFFSET = 3;
  static final int II2_VISUAL_RESULT_OFFSET = 6;
  static final int II2_NUM_GRAPH_SETS = 3;
  static final int II2_MAX_RESULT_INDEX = 2;
  
  public CLManagerTwo(GPDrawingArea paramGPDrawingArea)
  {
    super(paramGPDrawingArea, 130);
    this.active_array_[0] = new CLVisualGraph(130, 90, new GPPoint(115, 25), "Input", 1);
    for (int i = 0; i < 3; i++)
    {
      j = 40 + 170 * i;
      this.inactive_array_[i] = new CLVisualGraph(130, 90, new GPPoint(j, 165), "Filter", 0);
    }
    for (int j = 3; j < 6; j++)
    {
      k = 40 + 170 * (j - 3);
      this.inactive_array_[j] = new CLVisualGraph(130, 90, new GPPoint(k, 305), "Product", 0);
    }
    this.inactive_array_[6] = new CLResultGraph(3, 90, new GPPoint(300, 25), 43);
    for (int k = 0; k < 3; k++)
    {
      this.filter_array_[k] = new CLFilter(130, k);
      for (int m = 0; m < 130; m++) {
        this.inactive_array_[k].SetElement(m, this.filter_array_[k].GetElement(m));
      }
    }
  }
  
  public void UpdateRelationships()
  {
    this.active_array_[this.current_active_].SetRange(this.old_index_, this.current_index_, this.old_value_, this.current_value_);
    for (int i = 0; i < 3; i++)
    {
      this.inactive_array_[(i + 3)].MultiplyRange(this.old_index_, this.current_index_, this.active_array_[0], this.filter_array_[i]);
      this.inactive_array_[6].SetElement(i, this.inactive_array_[(i + 3)].GetSum() / this.filter_array_[i].GetSum());
      this.inactive_array_[(i + 3)].DrawBarRange(this.da_, this.old_index_, this.current_index_);
    }
    this.inactive_array_[6].DrawBarRange(this.da_, 0, 2);
    this.active_array_[this.current_active_].DrawBarRange(this.da_, this.old_index_, this.current_index_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManagerTwo.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */