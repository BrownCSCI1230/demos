package edu.brown.cs.exploratories.applets.threeStepScaling;

import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import javax.media.j3d.ImageComponent2D;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class App
  extends JFrame
{
  private static final int IMAGE_INSETS = 60;
  private static final int IMAGE_WIDTH = 200;
  private static final int IMAGE_HEIGHT = 200;
  private static final int FRAME_HEIGHT = 850;
  private static final int FRAME_WIDTH = 1110;
  private static final String IMAGE_FILE_DEFAULT = "glass.jpg";
  private static final int TOP_SCOPE = 32;
  int baseSize = 8;
  Container contentPane;
  JPanel graphicalPane;
  JMenuBar menuBar;
  JMenu menu;
  JMenu submenu;
  JMenuItem menuItem;
  JRadioButtonMenuItem rbMenuItem;
  JCheckBoxMenuItem cbMenuItem;
  InsetImageViewer viewerImage;
  InsetMultipleImagesViewer viewerProcess;
  ImageMouseListener imageMouseListener;
  PixelMouseListener pixelMouseListener;
  Properties imageProperties;
  
  public App()
  {
    super(" <<   I m a g e  S c a l i n g  2 D   >>  -- 3 - S T E P");
    menuInit();
    this.contentPane = getContentPane();
    this.contentPane.setLayout(new BoxLayout(this.contentPane, 1));
    this.graphicalPane = new JPanel();
    this.graphicalPane.setBorder(BorderFactory.createEtchedBorder());
    this.graphicalPane.setPreferredSize(new Dimension(1110, 850));
    this.graphicalPane.setLayout(new BoxLayout(this.graphicalPane, 0));
    this.contentPane.add(this.graphicalPane);
    viewersInit();
    mouseListenersInit();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    App localApp = new App();
    localApp.pack();
    localApp.setVisible(true);
    localApp.setSize(new Dimension(1110, 850));
    localApp.setLocation(new Point(50, 50));
  }
  
  public void setBase(int paramInt)
  {
    this.baseSize = paramInt;
  }
  
  public int getBase()
  {
    return this.baseSize;
  }
  
  public void menuInit()
  {
    this.menuBar = new JMenuBar();
    setJMenuBar(this.menuBar);
    this.menu = new JMenu("F i l e");
    this.menu.setMnemonic(70);
    this.menuBar.add(this.menu);
    this.menuItem = new JMenuItem("Quit", 81);
    this.menuItem.setAccelerator(KeyStroke.getKeyStroke(81, 2));
    ActionListener local1 = new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.exit(0);
      }
    };
    this.menuItem.addActionListener(local1);
    this.menu.add(this.menuItem);
    this.menu = new JMenu("I m a g e");
    this.menu.setMnemonic(73);
    this.menuBar.add(this.menu);
    this.imageProperties = new Properties();
    InputStream localInputStream = getClass().getClassLoader().getResourceAsStream("IMAGES.txt");
    if (localInputStream == null)
    {
      this.imageProperties.put("Glass", "glass.jpg");
      JOptionPane.showMessageDialog(null, "Couldn't find file Images.text ... loading default image.", "Error", 0);
    }
    else
    {
      try
      {
        this.imageProperties.load(localInputStream);
      }
      catch (IOException localIOException) {}
    }
    JMenuItem[] arrayOfJMenuItem = new JMenuItem[this.imageProperties.size()];
    Enumeration localEnumeration = this.imageProperties.propertyNames();
    ActionListener local2 = new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        TextureLoader localTextureLoader = new TextureLoader("glass.jpg", App.this.viewerImage);
        ImageComponent2D localImageComponent2D = localTextureLoader.getImage();
        if (App.this.imageProperties.getProperty(paramAnonymousActionEvent.getActionCommand()).length() != 0)
        {
          localTextureLoader = new TextureLoader(App.this.imageProperties.getProperty(paramAnonymousActionEvent.getActionCommand(), "glass.jpg"), App.this.viewerImage);
          try
          {
            localImageComponent2D = localTextureLoader.getImage();
          }
          catch (NullPointerException localNullPointerException)
          {
            localTextureLoader = new TextureLoader("glass.jpg", App.this.viewerImage);
            JOptionPane.showMessageDialog(null, "Invalid file specified in Images.txt ... loading default image.", "Error", 0);
            localImageComponent2D = localTextureLoader.getImage();
          }
        }
        else
        {
          JOptionPane.showMessageDialog(null, "No file specified for this Item in Images.txt ... loading default image.", "Error", 0);
        }
        App.this.viewerImage.setImage(localImageComponent2D.getImage());
        App.this.viewerProcess.paint(App.this.viewerProcess.getGraphics());
      }
    };
    for (int i = 0; i < this.imageProperties.size(); i++)
    {
      localObject = (String)localEnumeration.nextElement();
      arrayOfJMenuItem[i] = new JMenuItem((String)localObject);
      arrayOfJMenuItem[i].addActionListener(local2);
      this.menu.add(arrayOfJMenuItem[i]);
    }
    this.menu = new JMenu("O p t i o n s");
    this.menu.setMnemonic(79);
    this.submenu = new JMenu("Scope Area");
    this.menuItem = new JMenuItem("8 X 8", 69);
    this.menuItem.setAccelerator(KeyStroke.getKeyStroke(69, 8));
    Object localObject = new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        App.this.setBase(8);
        App.this.viewerProcess.setMouseFlag(false);
        App.this.viewerProcess.setNumRows(8);
        App.this.viewerProcess.setNumColumns(8);
        App.this.viewerProcess.setImage(0, null);
        App.this.viewerProcess.paint(App.this.viewerProcess.getGraphics());
        App.this.viewerProcess.showMarkers(false);
        App.this.viewerImage.setBase(8);
        App.this.viewerImage.getPixelMarker().setBorderVisible(false);
        App.this.viewerImage.setMouseFlag(false);
        App.this.viewerImage.paint(App.this.viewerImage.getGraphics());
      }
    };
    this.menuItem.addActionListener((ActionListener)localObject);
    this.submenu.add(this.menuItem);
    this.menuItem = new JMenuItem("16 X 16", 83);
    this.menuItem.setAccelerator(KeyStroke.getKeyStroke(83, 8));
    ActionListener local4 = new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        App.this.setBase(16);
        App.this.viewerProcess.setMouseFlag(false);
        App.this.viewerProcess.setNumRows(16);
        App.this.viewerProcess.setNumColumns(16);
        App.this.viewerProcess.setImage(0, null);
        App.this.viewerProcess.paint(App.this.viewerProcess.getGraphics());
        App.this.viewerProcess.showMarkers(false);
        App.this.viewerImage.setBase(16);
        App.this.viewerImage.getPixelMarker().setBorderVisible(false);
        App.this.viewerImage.setMouseFlag(false);
        App.this.viewerImage.paint(App.this.viewerImage.getGraphics());
      }
    };
    this.menuItem.addActionListener(local4);
    this.submenu.add(this.menuItem);
    this.menuItem = new JMenuItem("32 X 32", 84);
    this.menuItem.setAccelerator(KeyStroke.getKeyStroke(84, 8));
    ActionListener local5 = new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        App.this.setBase(32);
        App.this.viewerProcess.setMouseFlag(false);
        App.this.viewerProcess.setNumRows(32);
        App.this.viewerProcess.setNumColumns(32);
        App.this.viewerProcess.setImage(0, null);
        App.this.viewerProcess.paint(App.this.viewerProcess.getGraphics());
        App.this.viewerProcess.showMarkers(false);
        App.this.viewerImage.setBase(32);
        App.this.viewerImage.getPixelMarker().setBorderVisible(false);
        App.this.viewerImage.setMouseFlag(false);
        App.this.viewerImage.paint(App.this.viewerImage.getGraphics());
      }
    };
    this.menuItem.addActionListener(local5);
    this.submenu.add(this.menuItem);
    this.menu.add(this.submenu);
    this.menuBar.add(this.menu);
  }
  
  public void viewersInit()
  {
    this.viewerImage = new InsetImageViewer();
    this.viewerImage.setBorder(BorderFactory.createTitledBorder(" <<   O R I G I N A L  I M A G E   >>"));
    Dimension localDimension = new Dimension(260, 260);
    this.viewerImage.setPreferredSize(localDimension);
    this.viewerImage.setMinimumSize(localDimension);
    this.viewerImage.setImageByString("glass.jpg");
    this.viewerImage.setBase(getBase());
    this.viewerImage.setApp(this);
    this.viewerProcess = new InsetMultipleImagesViewer(this.baseSize, this.baseSize);
    this.viewerProcess.setBorder(BorderFactory.createTitledBorder(" <<   T H E  P R O C E S S   >>"));
    this.viewerProcess.setPreferredSize(new Dimension(910, 850));
    this.graphicalPane.add(this.viewerImage);
    this.graphicalPane.add(this.viewerProcess);
  }
  
  public void mouseListenersInit()
  {
    this.imageMouseListener = new ImageMouseListener();
    this.imageMouseListener.setViewer(this.viewerImage);
    this.imageMouseListener.setApp(this);
    this.viewerImage.addMouseListener(this.imageMouseListener);
    this.viewerImage.addMouseMotionListener(this.imageMouseListener);
    this.pixelMouseListener = new PixelMouseListener();
    this.pixelMouseListener.setViewer(this.viewerProcess);
    this.viewerProcess.addMouseListener(this.pixelMouseListener);
    this.viewerProcess.addMouseMotionListener(this.pixelMouseListener);
  }
  
  public InsetMultipleImagesViewer getProcessViewer()
  {
    return this.viewerProcess;
  }
  
  public InsetImageViewer getImageViewer()
  {
    return this.viewerImage;
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/App.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */