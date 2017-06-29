package edu.brown.cs.exploratories.applets.filterShape;

import edu.brown.cs.exploratories.components.swing.LabeledNumericSlider;
import edu.brown.cs.exploratories.components.test.TestFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FilterDimensionsDialogPanel
  extends JPanel
{
  private JLabel filterWidthLabel;
  private LabeledNumericSlider filterWidthSlider;
  private JLabel filterHeightLabel;
  private LabeledNumericSlider filterHeightSlider;
  
  public FilterDimensionsDialogPanel()
  {
    this(1.0D, 1.0D);
  }
  
  public FilterDimensionsDialogPanel(double paramDouble1, double paramDouble2)
  {
    initVisualComponents();
    setFilterWidth(paramDouble1);
    setFilterHeight(paramDouble2);
  }
  
  public double getFilterWidth()
  {
    return this.filterWidthSlider.getValue();
  }
  
  public void setFilterWidth(double paramDouble)
  {
    this.filterWidthSlider.setValue(paramDouble);
  }
  
  public double getFilterHeight()
  {
    return this.filterHeightSlider.getValue();
  }
  
  public void setFilterHeight(double paramDouble)
  {
    this.filterHeightSlider.setValue(paramDouble);
  }
  
  private void initVisualComponents()
  {
    this.filterWidthLabel = new JLabel("Width:");
    this.filterWidthSlider = new LabeledNumericSlider();
    this.filterWidthSlider.setMinValue(0.5D);
    this.filterWidthSlider.setMaxValue(10.0D);
    this.filterWidthSlider.setSliderValueToValueRatio(10.0D);
    this.filterHeightLabel = new JLabel("Height:");
    this.filterHeightSlider = new LabeledNumericSlider();
    this.filterHeightSlider.setMinValue(0.5D);
    this.filterHeightSlider.setMaxValue(10.0D);
    setLayout(new GridBagLayout());
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 5, 0, 5);
    add(this.filterWidthLabel, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 5);
    add(this.filterWidthSlider, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 5, 0, 5);
    add(this.filterHeightLabel, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 5);
    add(this.filterHeightSlider, localGridBagConstraints);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    FilterDimensionsDialogPanel localFilterDimensionsDialogPanel = new FilterDimensionsDialogPanel();
    TestFrame localTestFrame = new TestFrame(localFilterDimensionsDialogPanel);
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/FilterDimensionsDialogPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */