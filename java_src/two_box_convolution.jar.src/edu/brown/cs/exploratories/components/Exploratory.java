package edu.brown.cs.exploratories.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Exploratory
  extends JApplet
{
  public static final int DEFAULT_WIDTH = 200;
  public static final int DEFAULT_HEIGHT = 200;
  private int topBorder = 2;
  private int leftBorder = 2;
  private int bottomBorder = 2;
  private int rightBorder = 2;
  private ExploratoryMenuBar menuBar;
  private JPanel contentPanel;
  private static List argumentsCache;
  private static Map parametersCache;
  private static Object settingUp = new Object();
  private List arguments;
  private Map parameters;
  private boolean packingLayout;
  
  public Exploratory()
  {
    super.setSize(200, 200);
    if (argumentsCache == null) {
      argumentsCache = new Vector();
    }
    if (parametersCache == null) {
      parametersCache = new HashMap();
    }
    this.arguments = new Vector();
    this.arguments.addAll(argumentsCache);
    this.parameters = new HashMap();
    this.parameters.putAll(parametersCache);
    this.menuBar = new ExploratoryMenuBar();
    setJMenuBar(this.menuBar);
    this.contentPanel = new JPanel();
    this.contentPanel.setLayout(new BorderLayout());
    this.contentPanel.setBorder(BorderFactory.createEmptyBorder(this.topBorder, this.leftBorder, this.bottomBorder, this.rightBorder));
    setContentPane(this.contentPanel);
    this.packingLayout = false;
  }
  
  public int getNumArguments()
  {
    return this.arguments.size();
  }
  
  public String getArgument(int paramInt)
  {
    String str = null;
    try
    {
      str = (String)this.arguments.get(paramInt);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      str = null;
    }
    return str;
  }
  
  public Iterator getArguments()
  {
    return this.arguments.iterator();
  }
  
  public String getParameter(String paramString)
  {
    String str = null;
    if (paramString == null) {
      throw new NullPointerException();
    }
    if (isActive())
    {
      str = super.getParameter(paramString);
      if (str == null) {
        str = super.getParameter(paramString.toUpperCase());
      }
      if (str == null) {
        str = super.getParameter(paramString.toLowerCase());
      }
    }
    if (str == null) {
      str = (String)this.parameters.get(paramString);
    }
    if (str == null) {
      str = (String)this.parameters.get(paramString.toUpperCase());
    }
    if (str == null) {
      str = (String)this.parameters.get(paramString.toLowerCase());
    }
    return str;
  }
  
  private static void setArguments(String[] paramArrayOfString)
  {
    argumentsCache = new Vector();
    parametersCache = new HashMap();
    if (paramArrayOfString != null) {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        String str1 = paramArrayOfString[i];
        int j = str1.indexOf('=');
        if (j == -1)
        {
          argumentsCache.add(str1);
        }
        else
        {
          String str2 = str1.substring(0, j);
          String str3 = str1.substring(j + 1);
          parametersCache.put(str2, str3);
        }
      }
    }
  }
  
  public boolean isPackingLayout()
  {
    return this.packingLayout;
  }
  
  public void setPackingLayout(boolean paramBoolean)
  {
    this.packingLayout = paramBoolean;
  }
  
  public static void main(String[] paramArrayOfString, Class paramClass)
  {
    try
    {
      Exploratory localExploratory;
      synchronized (settingUp)
      {
        setArguments(paramArrayOfString);
        localExploratory = (Exploratory)paramClass.newInstance();
      }
      JFrame localJFrame = new JFrame();
      localJFrame.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent paramAnonymousWindowEvent)
        {
          System.exit(1);
        }
      });
      ExploratoryMenuBar localExploratoryMenuBar = (ExploratoryMenuBar)localExploratory.getJMenuBar();
      localExploratoryMenuBar.setFileMenuVisible(true);
      localJFrame.setJMenuBar(localExploratoryMenuBar);
      Container localContainer = localExploratory.getContentPane();
      localJFrame.setContentPane(localContainer);
      if (localExploratory.isPackingLayout()) {
        localJFrame.pack();
      } else {
        localJFrame.setSize(localExploratory.getWidth(), localExploratory.getHeight());
      }
      localJFrame.setVisible(true);
    }
    catch (Exception localException)
    {
      System.out.println(localException);
      localException.printStackTrace();
    }
  }
  
  protected static class ExploratoryMenuBar
    extends JMenuBar
  {
    private static final String FILE_MENU_STRING = "File";
    private static final int FILE_MENU_MNEMONIC = 70;
    private static final String CLOSE_ITEM_STRING = "Close";
    private static final int CLOSE_ITEM_MNEMONIC = 67;
    private static final String EXIT_ITEM_STRING = "Exit";
    private static final int EXIT_ITEM_MNEMONIC = 88;
    private static final String HELP_MENU_STRING = "Help";
    private static final int HELP_MENU_MNEMONIC = 72;
    private JMenu fileMenu;
    private boolean fileMenuVisible;
    private JMenu helpMenu;
    
    public ExploratoryMenuBar()
    {
      this(false);
    }
    
    public ExploratoryMenuBar(boolean paramBoolean)
    {
      JPopupMenu.setDefaultLightWeightPopupEnabled(false);
      this.fileMenu = new JMenu("File");
      this.fileMenu.setMnemonic(70);
      JMenuItem localJMenuItem = new JMenuItem("Exit", 88);
      localJMenuItem.addActionListener(new Exploratory.3(this));
      this.fileMenu.add(localJMenuItem);
      setFileMenuVisible(paramBoolean);
      this.helpMenu = new Exploratory.ExploratoryHelpMenu("Help");
      this.helpMenu.setMnemonic(72);
      super.add(this.helpMenu);
    }
    
    public void setFileMenuVisible(boolean paramBoolean)
    {
      this.fileMenuVisible = paramBoolean;
      if (paramBoolean) {
        add(this.fileMenu, 0);
      } else {
        remove(this.fileMenu);
      }
    }
    
    public boolean isFileMenuVisible()
    {
      return this.fileMenuVisible;
    }
    
    protected void addImpl(Component paramComponent, Object paramObject, int paramInt)
    {
      if ((isFileMenuVisible()) && (paramComponent != this.fileMenu) && (paramInt == 0)) {
        paramInt++;
      } else if (((paramInt == getComponentCount()) || (paramInt == -1)) && (paramComponent != this.helpMenu) && (paramComponent != this.fileMenu)) {
        paramInt = getComponentCount() - 1;
      }
      super.addImpl(paramComponent, paramObject, paramInt);
    }
    
    public void remove(int paramInt)
    {
      Component localComponent = getComponent(paramInt);
      if (((isFileMenuVisible()) && (localComponent == this.fileMenu)) || (localComponent == this.helpMenu)) {
        throw new UnsupportedOperationException();
      }
      super.remove(paramInt);
    }
    
    public void remove(Component paramComponent)
    {
      if (((isFileMenuVisible()) && (paramComponent == this.fileMenu)) || (paramComponent == this.helpMenu)) {
        throw new UnsupportedOperationException();
      }
      super.remove(paramComponent);
    }
    
    public void removeAll()
    {
      throw new UnsupportedOperationException();
    }
  }
  
  protected static class ExploratoryHelpMenu
    extends JMenu
  {
    private static final String ABOUT_ITEM_STRING = "About";
    private static final int ABOUT_MENU_MNEMONIC = 65;
    private JMenuItem aboutMenuItem = new JMenuItem("About", 65);
    private Object about;
    
    ExploratoryHelpMenu(String paramString)
    {
      super();
      this.aboutMenuItem.addActionListener(new Exploratory.2(this));
      add(this.aboutMenuItem);
      this.about = "Please visit http://www.cs.brown.edu/exploratories for more information about this and other exploratories applets.";
    }
    
    public Object getAbout()
    {
      return this.about;
    }
    
    public void setAbout(Object paramObject)
    {
      if (paramObject == null) {
        throw new NullPointerException();
      }
      this.about = paramObject;
    }
    
    public void remove(Component paramComponent)
    {
      if (paramComponent == this.aboutMenuItem) {
        throw new UnsupportedOperationException();
      }
      super.remove(paramComponent);
    }
    
    public void remove(int paramInt)
    {
      JMenuItem localJMenuItem = getItem(paramInt);
      if (localJMenuItem == this.aboutMenuItem) {
        throw new UnsupportedOperationException();
      }
      super.remove(paramInt);
    }
    
    public void remove(JMenuItem paramJMenuItem)
    {
      if (paramJMenuItem == this.aboutMenuItem) {
        throw new UnsupportedOperationException();
      }
      super.remove(paramJMenuItem);
    }
    
    public void removeAll()
    {
      throw new UnsupportedOperationException();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/two_box_convolution.jar!/edu/brown/cs/exploratories/components/Exploratory.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */