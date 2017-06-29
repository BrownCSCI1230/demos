package edu.brown.cs.exploratories.applets.filterShape;

import edu.brown.cs.exploratories.components.Exploratory;
import edu.brown.cs.exploratories.components.Exploratory.ExploratoryMenuBar;
import edu.brown.cs.exploratories.components.swing.ImageDisplayer;
import edu.brown.cs.exploratories.components.swing.ImageLibrary;
import edu.brown.cs.exploratories.components.swing.ImageSelector;
import edu.brown.cs.exploratories.components.swing.ResizableImageDisplayer;
import edu.brown.cs.exploratories.components.swing.Selection;
import edu.brown.cs.exploratories.components.swing.SelectionChangedEvent;
import edu.brown.cs.exploratories.components.swing.SelectionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FilterShape
  extends Exploratory
{
  private ImageLibrary imageLibrary = new ImageLibrary();
  private Hashtable filterHashtable;
  private Filter triangleFilter = new TriangleFilter();
  private Filter userSpecifiedFilter = new TriangleFilter();
  private ImageSelector sourceSelector;
  private ClippingScaler triangleScaler;
  private ClippingScaler userSpecifiedScaler;
  private JPanel filterSketchPanel;
  private FilterSketcher filterSketcher;
  private JButton filterNormalizeButton;
  private JButton filterClearButton;
  private JButton filterDimensionsButton;
  private JLabel filterWidthLabel;
  private JLabel filterHeightLabel;
  private JLabel filterAreaLabel;
  private ActionListener imageMenuItemActionListener = new ActionListener()
  {
    public void actionPerformed(ActionEvent paramAnonymousActionEvent)
    {
      JMenuItem localJMenuItem = (JMenuItem)paramAnonymousActionEvent.getSource();
      String str = localJMenuItem.getText();
      Image localImage = FilterShape.this.imageLibrary.getImage(str);
      if (localImage != null)
      {
        ImageDisplayer localImageDisplayer = FilterShape.this.sourceSelector.getImageDisplayer();
        localImageDisplayer.setImage(localImage);
        ((ResizableImageClipping)FilterShape.this.userSpecifiedScaler.getSelection()).setClipping(null);
        ((ResizableImageClipping)FilterShape.this.triangleScaler.getSelection()).setClipping(null);
        FilterShape.this.updateClippings();
      }
    }
  };
  private ActionListener filtersMenuItemActionListener = new ActionListener()
  {
    public void actionPerformed(ActionEvent paramAnonymousActionEvent)
    {
      FilterShape.this.userSpecifiedFilter = ((Filter)FilterShape.this.filterHashtable.get(((JMenuItem)paramAnonymousActionEvent.getSource()).getText()));
      ResizableImageClipping localResizableImageClipping = (ResizableImageClipping)FilterShape.this.userSpecifiedScaler.getSelection();
      localResizableImageClipping.setClipping(null);
      FilterShape.this.setFilterParameterControlsEnabled(false);
      FilterShape.this.setFilterWidthLabelValue(FilterShape.this.userSpecifiedFilter.getWidth());
      FilterShape.this.setFilterHeightLabelValue(FilterShape.this.userSpecifiedFilter.getHeight());
      FilterShape.this.setFilterAreaLabelValue(FilterShape.this.userSpecifiedFilter.getArea());
      FilterShape.this.filterSketcher.setDomain(-FilterShape.this.userSpecifiedFilter.getWidth() / 2.0D, FilterShape.this.userSpecifiedFilter.getWidth() / 2.0D);
      FilterShape.this.filterSketcher.setRange(0.0D, FilterShape.this.userSpecifiedFilter.getHeight());
      FilterShape.this.filterSketcher.fromFilter(FilterShape.this.userSpecifiedFilter);
      FilterShape.this.updateClippings();
    }
  };
  private Cursor lastCursor;
  private Cursor lastSourceSelectorCursor;
  private Cursor lastSourceSelectorSelectionCursor;
  private Cursor lastFilterSketcherCursor;
  private Cursor lastFilterSketchPanelCursor;
  private Cursor lastTriangleScalerCursor;
  private Cursor lastTriangleScalerSelectionCursor;
  private Cursor lastUserSpecifiedScalerCursor;
  private Cursor lastUserSpecifiedScalerSelectionCursor;
  
  public FilterShape()
  {
    this.imageLibrary.setLoaderClass(getClass());
    this.filterHashtable = buildFilterHashtable();
    buildVisualComponents();
    Enumeration localEnumeration = this.imageLibrary.getImageNames();
    if (localEnumeration.hasMoreElements())
    {
      Image localImage = this.imageLibrary.getImage((String)localEnumeration.nextElement());
      this.sourceSelector.getImageDisplayer().setImage(localImage);
      updateClippings();
    }
    else
    {
      JOptionPane.showMessageDialog(null, "This Exploratory has not been configured with any images. Please send a bug report to have this problem fixed.");
      System.exit(1);
    }
  }
  
  private void sourceSelectionChanged(SelectionChangedEvent paramSelectionChangedEvent)
  {
    int i = paramSelectionChangedEvent.getID();
    if (i == 3)
    {
      ResizableImageClipping localResizableImageClipping1 = (ResizableImageClipping)this.triangleScaler.getSelection();
      ResizableImageClipping localResizableImageClipping2 = (ResizableImageClipping)this.userSpecifiedScaler.getSelection();
      localResizableImageClipping1.setClipping(null);
      localResizableImageClipping2.setClipping(null);
      updateClippings();
    }
  }
  
  private void triangleClippingSelectionChanged(SelectionChangedEvent paramSelectionChangedEvent)
  {
    int i = paramSelectionChangedEvent.getID();
    ResizableImageClipping localResizableImageClipping1 = (ResizableImageClipping)this.triangleScaler.getSelection();
    ResizableImageClipping localResizableImageClipping2 = (ResizableImageClipping)this.userSpecifiedScaler.getSelection();
    if (i != 0)
    {
      if (i == 1)
      {
        localResizableImageClipping1.setClipping(null);
        localResizableImageClipping2.setClipping(null);
      }
      localResizableImageClipping2.setLocation(paramSelectionChangedEvent.getNewLocation());
      localResizableImageClipping2.setSize(paramSelectionChangedEvent.getNewSize());
      if (i == 3) {
        updateClippings();
      }
    }
  }
  
  private void userSpecifiedClippingSelectionChanged(SelectionChangedEvent paramSelectionChangedEvent)
  {
    int i = paramSelectionChangedEvent.getID();
    ResizableImageClipping localResizableImageClipping1 = (ResizableImageClipping)this.triangleScaler.getSelection();
    ResizableImageClipping localResizableImageClipping2 = (ResizableImageClipping)this.userSpecifiedScaler.getSelection();
    if (i != 0)
    {
      if (i == 1)
      {
        localResizableImageClipping1.setClipping(null);
        localResizableImageClipping2.setClipping(null);
      }
      localResizableImageClipping1.setSize(paramSelectionChangedEvent.getNewSize());
      localResizableImageClipping1.setLocation(paramSelectionChangedEvent.getNewLocation());
      if (i == 3) {
        updateClippings();
      }
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    Exploratory.main(paramArrayOfString, FilterShape.class);
  }
  
  private Hashtable buildFilterHashtable()
  {
    Hashtable localHashtable = new Hashtable();
    localHashtable.put("Triangle Filter", new TriangleFilter());
    localHashtable.put("Quadratic Filter", new QuadraticFilter());
    localHashtable.put("Linear Filter", new LinearFilter());
    localHashtable.put("Gaussian Filter", new GaussianFilter());
    localHashtable.put("Bad Filter 1", new BadFilter1());
    localHashtable.put("Bad Filter 2", new BadFilter2());
    localHashtable.put("Bad Filter 3", new BadFilter3());
    localHashtable.put("Bad Fitler 4", new BadFilter4());
    return localHashtable;
  }
  
  private void updateClippings()
  {
    ResizableImageClipping localResizableImageClipping1 = (ResizableImageClipping)this.triangleScaler.getSelection();
    ResizableImageClipping localResizableImageClipping2 = (ResizableImageClipping)this.userSpecifiedScaler.getSelection();
    if ((localResizableImageClipping1.getClipping() == null) || (localResizableImageClipping2.getClipping() == null))
    {
      setCursors(new Cursor(3));
      BufferedImage localBufferedImage1 = this.sourceSelector.getSelectedSubimage();
      BufferedImage localBufferedImage2;
      if (localResizableImageClipping1.getClipping() == null)
      {
        localBufferedImage2 = ScaleEngine.scale(localBufferedImage1, this.triangleFilter, localResizableImageClipping1.getSize());
        localResizableImageClipping1.setClipping(localBufferedImage2);
      }
      if (localResizableImageClipping2.getClipping() == null)
      {
        localBufferedImage2 = ScaleEngine.scale(localBufferedImage1, this.userSpecifiedFilter, localResizableImageClipping2.getSize());
        localResizableImageClipping2.setClipping(localBufferedImage2);
      }
      revertCursors();
    }
  }
  
  private void setFilterParameterControlsEnabled(boolean paramBoolean)
  {
    this.filterNormalizeButton.setEnabled(paramBoolean);
    this.filterClearButton.setEnabled(paramBoolean);
    this.filterDimensionsButton.setEnabled(paramBoolean);
  }
  
  private void normalizeFilter()
  {
    Filter localFilter = this.filterSketcher.toFilter();
    if ((localFilter instanceof FilterSketcher.GraphDataFilter))
    {
      FilterSketcher.GraphDataFilter localGraphDataFilter = (FilterSketcher.GraphDataFilter)localFilter;
      localGraphDataFilter.normalize();
      this.filterSketcher.fromFilter(localGraphDataFilter);
      this.userSpecifiedFilter = localGraphDataFilter;
      ResizableImageClipping localResizableImageClipping = (ResizableImageClipping)this.userSpecifiedScaler.getSelection();
      localResizableImageClipping.setClipping(null);
      updateClippings();
      setFilterAreaLabelValue(this.userSpecifiedFilter.getArea());
      setFilterWidthLabelValue(this.userSpecifiedFilter.getWidth());
      setFilterHeightLabelValue(this.userSpecifiedScaler.getHeight());
    }
  }
  
  private void clearFilter()
  {
    ZeroFilter localZeroFilter = new ZeroFilter();
    this.filterSketcher.fromFilter(localZeroFilter);
    ResizableImageClipping localResizableImageClipping = (ResizableImageClipping)this.userSpecifiedScaler.getSelection();
    localResizableImageClipping.setClipping(null);
    updateClippings();
    setFilterAreaLabelValue(localZeroFilter.getArea());
  }
  
  private void setFilterAreaLabelValue(double paramDouble)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("#0.0000000");
    this.filterAreaLabel.setText("Area:   " + localDecimalFormat.format(paramDouble));
  }
  
  private void setFilterWidthLabelValue(double paramDouble)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("#0.0");
    this.filterWidthLabel.setText("Width:  " + localDecimalFormat.format(paramDouble));
  }
  
  private void setFilterHeightLabelValue(double paramDouble)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("#0.0");
    this.filterHeightLabel.setText("Height: " + localDecimalFormat.format(paramDouble));
  }
  
  private void adjustFilterDimensions()
  {
    if ((this.userSpecifiedFilter instanceof FilterSketcher.GraphDataFilter))
    {
      double d1 = this.userSpecifiedFilter.getWidth();
      double d2 = this.userSpecifiedFilter.getHeight();
      double d3 = d1;
      double d4 = d2;
      FilterDimensionsDialogPanel localFilterDimensionsDialogPanel = new FilterDimensionsDialogPanel();
      localFilterDimensionsDialogPanel.setFilterWidth(d3);
      localFilterDimensionsDialogPanel.setFilterHeight(d4);
      int i = 1;
      while (i != 0)
      {
        int j = JOptionPane.showConfirmDialog(null, localFilterDimensionsDialogPanel, "Filter Dimensions", 2, -1);
        if (j == 0)
        {
          double d5 = localFilterDimensionsDialogPanel.getFilterWidth();
          double d6 = localFilterDimensionsDialogPanel.getFilterHeight();
          if ((d5 < d3) || (d6 < d4))
          {
            j = JOptionPane.showConfirmDialog(null, "The dimensions you have chosen will cause the contents of the filter to be clipped. Do you want to continue?", "Clip Filter Contents?", 0, 2);
            if (j == 0)
            {
              d3 = d5;
              d4 = d6;
              i = 0;
            }
          }
          else
          {
            d3 = d5;
            d4 = d6;
            i = 0;
          }
        }
        else
        {
          i = 0;
        }
      }
      if ((d3 != d1) || (d4 != d2))
      {
        ((FilterSketcher.GraphDataFilter)this.userSpecifiedFilter).setWidth(d3);
        ((FilterSketcher.GraphDataFilter)this.userSpecifiedFilter).setHeight(d4);
        this.filterSketcher.fromFilter(this.userSpecifiedFilter);
        this.filterSketcher.setDomain(-d3 / 2.0D, d3 / 2.0D);
        this.filterSketcher.setRangeHigh(d4);
        setFilterWidthLabelValue(d3);
        setFilterHeightLabelValue(d4);
        setFilterAreaLabelValue(this.userSpecifiedFilter.getArea());
      }
    }
  }
  
  private void buildVisualComponents()
  {
    buildSourceSelector();
    buildFilterSketchPanel();
    buildTriangleScaler();
    buildUserSpecifiedScaler();
    JPanel localJPanel = new JPanel();
    localJPanel.setLayout(new GridLayout(2, 2));
    localJPanel.add(this.sourceSelector);
    localJPanel.add(this.filterSketchPanel);
    localJPanel.add(this.triangleScaler);
    localJPanel.add(this.userSpecifiedScaler);
    getContentPane().add(localJPanel);
    buildMenuBar();
    setPackingLayout(false);
    setSize(600, 600);
  }
  
  private void buildFilterSketchPanel()
  {
    this.filterSketcher = new FilterSketcher();
    this.filterSketcher.setSize(100, 100);
    this.filterSketcher.setDomain(-1.0D, 1.0D);
    this.filterSketcher.setRange(0.0D, 1.0D);
    this.filterSketcher.fromFilter(this.userSpecifiedFilter);
    this.filterSketcher.setBorder(BorderFactory.createLoweredBevelBorder());
    this.filterSketcher.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
      {
        FilterShape.this.userSpecifiedFilter = FilterShape.this.filterSketcher.toFilter();
        ResizableImageClipping localResizableImageClipping = (ResizableImageClipping)FilterShape.this.userSpecifiedScaler.getSelection();
        localResizableImageClipping.setClipping(null);
        FilterShape.this.updateClippings();
        FilterShape.this.setFilterAreaLabelValue(FilterShape.this.userSpecifiedFilter.getArea());
        FilterShape.this.setFilterParameterControlsEnabled(true);
      }
    });
    this.filterAreaLabel = new JLabel();
    this.filterWidthLabel = new JLabel();
    this.filterHeightLabel = new JLabel();
    this.filterNormalizeButton = new JButton("Normalize");
    this.filterNormalizeButton.setEnabled(false);
    this.filterNormalizeButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        Cursor localCursor = FilterShape.this.filterNormalizeButton.getCursor();
        FilterShape.this.filterNormalizeButton.setCursor(new Cursor(3));
        FilterShape.this.normalizeFilter();
        FilterShape.this.filterNormalizeButton.setCursor(localCursor);
      }
    });
    this.filterClearButton = new JButton("Clear");
    this.filterClearButton.setEnabled(false);
    this.filterClearButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        FilterShape.this.clearFilter();
      }
    });
    this.filterDimensionsButton = new JButton("Dimensions");
    this.filterDimensionsButton.setEnabled(false);
    this.filterDimensionsButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        FilterShape.this.adjustFilterDimensions();
      }
    });
    this.filterSketchPanel = new JPanel();
    this.filterSketchPanel.setBorder(BorderFactory.createTitledBorder("Sketch the scaling filter to be used"));
    this.filterSketchPanel.setLayout(new GridBagLayout());
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridwidth = 2;
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 0);
    this.filterSketchPanel.add(this.filterSketcher, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(5, 3, 5, 10);
    this.filterSketchPanel.add(this.filterNormalizeButton, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 3, 5, 10);
    this.filterSketchPanel.add(this.filterClearButton, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 18;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 3, 3, 10);
    this.filterSketchPanel.add(this.filterDimensionsButton, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 10;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(5, 0, 5, 3);
    this.filterSketchPanel.add(this.filterAreaLabel, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 10;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 0, 5, 3);
    this.filterSketchPanel.add(this.filterWidthLabel, localGridBagConstraints);
    localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.anchor = 10;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.insets = new Insets(0, 0, 3, 3);
    this.filterSketchPanel.add(this.filterHeightLabel, localGridBagConstraints);
  }
  
  private void buildSourceSelector()
  {
    this.sourceSelector = new ImageSelector();
    this.sourceSelector.setImageDisplayer(new ResizableImageDisplayer());
    this.sourceSelector.getImageDisplayer().setImageBackgroundColor(Color.black);
    this.sourceSelector.getSelection().setSelectionHandlesColor(new Color(255, 255, 255, 128));
    this.sourceSelector.getSelection().setSelectionRectangleColor(new Color(255, 255, 255, 192));
    this.sourceSelector.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Select the section of image to be scaled"), BorderFactory.createLoweredBevelBorder()));
    this.sourceSelector.getSelectionListener().addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        FilterShape.this.sourceSelectionChanged((SelectionChangedEvent)paramAnonymousActionEvent);
      }
    });
  }
  
  private void buildTriangleScaler()
  {
    this.triangleScaler = new ClippingScaler();
    this.triangleScaler.getSelection().setSelectionRectangleColor(new Color(255, 255, 255, 128));
    this.triangleScaler.getSelection().setSelectionHandlesColor(new Color(255, 255, 255, 192));
    this.triangleScaler.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Resize using the triangle filter"), BorderFactory.createLoweredBevelBorder()));
    this.triangleScaler.getSelectionListener().addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        FilterShape.this.triangleClippingSelectionChanged((SelectionChangedEvent)paramAnonymousActionEvent);
      }
    });
  }
  
  private void buildUserSpecifiedScaler()
  {
    this.userSpecifiedScaler = new ClippingScaler();
    this.userSpecifiedScaler.getSelection().setSelectionRectangleColor(new Color(255, 255, 255, 128));
    this.userSpecifiedScaler.getSelection().setSelectionHandlesColor(new Color(255, 255, 255, 192));
    this.userSpecifiedScaler.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Resize using the filter you specified"), BorderFactory.createLoweredBevelBorder()));
    this.userSpecifiedScaler.getSelectionListener().addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        FilterShape.this.userSpecifiedClippingSelectionChanged((SelectionChangedEvent)paramAnonymousActionEvent);
      }
    });
  }
  
  private void buildMenuBar()
  {
    Exploratory.ExploratoryMenuBar localExploratoryMenuBar = (Exploratory.ExploratoryMenuBar)getJMenuBar();
    localExploratoryMenuBar.add(buildImageMenu());
    localExploratoryMenuBar.add(buildFiltersMenu());
    setJMenuBar(localExploratoryMenuBar);
  }
  
  private JMenu buildImageMenu()
  {
    JMenu localJMenu = this.imageLibrary.createJMenu();
    localJMenu.setMnemonic(73);
    int i = localJMenu.getItemCount();
    for (int j = 0; j < i; j++)
    {
      JMenuItem localJMenuItem = localJMenu.getItem(j);
      localJMenuItem.addActionListener(this.imageMenuItemActionListener);
    }
    return localJMenu;
  }
  
  private JMenu buildFiltersMenu()
  {
    JMenu localJMenu = new JMenu("Predefined Filters");
    localJMenu.setMnemonic(80);
    Enumeration localEnumeration = this.filterHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      JMenuItem localJMenuItem = new JMenuItem((String)localEnumeration.nextElement());
      localJMenuItem.addActionListener(this.filtersMenuItemActionListener);
      localJMenu.add(localJMenuItem);
    }
    return localJMenu;
  }
  
  private void setCursors(Cursor paramCursor)
  {
    this.lastCursor = getCursor();
    this.lastSourceSelectorCursor = this.sourceSelector.getCursor();
    this.lastSourceSelectorSelectionCursor = this.sourceSelector.getSelection().getCursor();
    this.lastFilterSketcherCursor = this.filterSketcher.getCursor();
    this.lastFilterSketchPanelCursor = this.filterSketchPanel.getCursor();
    this.lastTriangleScalerCursor = this.triangleScaler.getCursor();
    this.lastTriangleScalerSelectionCursor = this.triangleScaler.getSelection().getCursor();
    this.lastUserSpecifiedScalerCursor = this.userSpecifiedScaler.getCursor();
    this.lastUserSpecifiedScalerSelectionCursor = this.userSpecifiedScaler.getSelection().getCursor();
    setCursor(paramCursor);
    this.sourceSelector.setCursor(paramCursor);
    this.sourceSelector.getSelection().setCursor(paramCursor);
    this.filterSketcher.setCursor(paramCursor);
    this.filterSketchPanel.setCursor(paramCursor);
    this.triangleScaler.setCursor(paramCursor);
    this.triangleScaler.getSelection().setCursor(paramCursor);
    this.userSpecifiedScaler.setCursor(paramCursor);
    this.userSpecifiedScaler.getSelection().setCursor(paramCursor);
  }
  
  private void revertCursors()
  {
    setCursor(this.lastCursor);
    this.sourceSelector.setCursor(this.lastSourceSelectorCursor);
    this.sourceSelector.getSelection().setCursor(this.lastSourceSelectorSelectionCursor);
    this.filterSketcher.setCursor(this.lastFilterSketcherCursor);
    this.filterSketchPanel.setCursor(this.lastFilterSketchPanelCursor);
    this.triangleScaler.setCursor(this.lastTriangleScalerCursor);
    this.triangleScaler.getSelection().setCursor(this.lastTriangleScalerSelectionCursor);
    this.userSpecifiedScaler.setCursor(this.lastUserSpecifiedScalerCursor);
    this.userSpecifiedScaler.getSelection().setCursor(this.lastUserSpecifiedScalerSelectionCursor);
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/FilterShape.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */