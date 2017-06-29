package edu.brown.cs.exploratories.applets.sampling;

import java.awt.Color;

public class SampDTFunctionController
  implements DTFunctionController
{
  protected Sampling samp_;
  protected DTFunctionWindow function_window;
  protected int num_sample_colors = 1;
  protected Color[] sample_colors = { Color.blue };
  protected int hilight_ = -1;
  
  public SampDTFunctionController(Sampling paramSampling)
  {
    this.samp_ = paramSampling;
  }
  
  public void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow)
  {
    this.function_window = paramDTFunctionWindow;
  }
  
  public void update(int paramInt, double[] paramArrayOfDouble)
  {
    for (int i = 0; i < paramInt; i++)
    {
      DValue localDValue = this.function_window.getDValue(i);
      if (i == this.hilight_) {
        localDValue.setColor(Color.red);
      } else {
        localDValue.setColor(this.sample_colors[(i % this.num_sample_colors)]);
      }
      localDValue.setBoxColor(this.sample_colors[(i % this.num_sample_colors)]);
      localDValue.setValue(paramArrayOfDouble[i] / 255.0D);
    }
    this.function_window.changeValues();
  }
  
  public void resize(int paramInt1, int paramInt2) {}
  
  public void beginUpdate(DValue paramDValue) {}
  
  public void valueUpdate() {}
  
  public void endUpdate() {}
  
  public void setHilight(int paramInt, boolean paramBoolean)
  {
    if (-1 != this.hilight_)
    {
      localDValue = this.function_window.getDValue(this.hilight_);
      localDValue.setColor(this.sample_colors[(this.hilight_ % this.num_sample_colors)]);
      localDValue.setBoxColor(this.sample_colors[(this.hilight_ % this.num_sample_colors)]);
    }
    this.hilight_ = paramInt;
    DValue localDValue = this.function_window.getDValue(paramInt);
    localDValue.setColor(Color.red);
    localDValue.setBoxColor(Color.red);
    if (paramBoolean) {
      this.samp_.getImage().setHilight(paramInt, false);
    }
    this.function_window.changeValues();
  }
  
  public void unsetHilight(boolean paramBoolean)
  {
    if (-1 != this.hilight_)
    {
      DValue localDValue = this.function_window.getDValue(this.hilight_);
      localDValue.setColor(this.sample_colors[(this.hilight_ % this.num_sample_colors)]);
      localDValue.setBoxColor(this.sample_colors[(this.hilight_ % this.num_sample_colors)]);
      this.hilight_ = -1;
      if (paramBoolean) {
        this.samp_.getImage().unsetHilight(false);
      }
      this.function_window.changeValues();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/introduction_to_sampling.jar!/edu/brown/cs/exploratories/applets/sampling/SampDTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */