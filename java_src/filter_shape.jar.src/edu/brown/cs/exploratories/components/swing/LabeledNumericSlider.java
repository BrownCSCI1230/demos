package edu.brown.cs.exploratories.components.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LabeledNumericSlider
  extends JPanel
{
  public static final String DEFAULT_DECIMAL_FORMAT_STRING = "#0.0";
  public static final double DEFAULT_MIN_VALUE = 0.0D;
  public static final double DEFAULT_MAX_VALUE = 10.0D;
  public static final double DEFAULT_SLIDER_VALUE_TO_VALUE_RATIO = 10.0D;
  public static final double DEFAULT_VALUE = 0.0D;
  private JLabel label;
  private JSlider slider;
  private DecimalFormat decimalFormat = new DecimalFormat("#0.0");
  private double value = 0.0D;
  private double minValue = 0.0D;
  private double maxValue = 10.0D;
  private double sliderValueToValueRatio = 10.0D;
  
  public LabeledNumericSlider()
  {
    initVisualComponents();
    setValue(0.0D);
    setMinValue(0.0D);
    setMaxValue(10.0D);
    setSliderValueToValueRatio(10.0D);
  }
  
  public JLabel getLabel()
  {
    return this.label;
  }
  
  public JSlider getSlider()
  {
    return this.slider;
  }
  
  public DecimalFormat getDecimalFormat()
  {
    return this.decimalFormat;
  }
  
  public double getValue()
  {
    return this.value;
  }
  
  public void setValue(double paramDouble)
  {
    setValueInternal(paramDouble);
    this.slider.setValue(valueToSliderValue(getValue()));
  }
  
  public double getMinValue()
  {
    return this.minValue;
  }
  
  public void setMinValue(double paramDouble)
  {
    this.minValue = paramDouble;
    if (this.minValue > getValue()) {
      setValue(this.minValue);
    }
    this.slider.setMinimum(valueToSliderValue(this.minValue));
  }
  
  public double getMaxValue()
  {
    return this.maxValue;
  }
  
  public void setMaxValue(double paramDouble)
  {
    this.maxValue = paramDouble;
    if (this.maxValue < getValue()) {
      setValue(this.maxValue);
    }
    this.slider.setMaximum(valueToSliderValue(this.maxValue));
  }
  
  public double getSliderValueToValueRatio()
  {
    return this.sliderValueToValueRatio;
  }
  
  public void setSliderValueToValueRatio(double paramDouble)
  {
    this.sliderValueToValueRatio = paramDouble;
    this.slider.setValue(valueToSliderValue(getValue()));
  }
  
  private int valueToSliderValue(double paramDouble)
  {
    return (int)(paramDouble * getSliderValueToValueRatio());
  }
  
  private double sliderValueToValue(int paramInt)
  {
    return paramInt / getSliderValueToValueRatio();
  }
  
  private void setValueInternal(double paramDouble)
  {
    if (paramDouble > getMaxValue()) {
      paramDouble = getMaxValue();
    }
    if (paramDouble < getMinValue()) {
      paramDouble = getMinValue();
    }
    this.value = paramDouble;
    this.label.setText(this.decimalFormat.format(paramDouble));
  }
  
  private void initVisualComponents()
  {
    this.label = new JLabel("#0.0");
    this.slider = new JSlider(0);
    this.slider.setValue(valueToSliderValue(0.0D));
    this.slider.setMinimum(valueToSliderValue(0.0D));
    this.slider.setMaximum(valueToSliderValue(10.0D));
    this.slider.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent paramAnonymousChangeEvent)
      {
        LabeledNumericSlider.this.setValueInternal(LabeledNumericSlider.access$100(LabeledNumericSlider.this, LabeledNumericSlider.this.slider.getValue()));
      }
    });
    setLayout(new GridBagLayout());
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 5);
    add(this.label, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 0);
    add(this.slider, localGridBagConstraints);
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/LabeledNumericSlider.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */