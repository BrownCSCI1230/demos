package edu.brown.cs.exploratories.components.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class TestFrame
  extends JFrame
{
  public static final int DEFAULT_WIDTH = 400;
  public static final int DEFAULT_HEIGHT = 400;
  private static final String FILE_MENU_STRING = "File";
  private static final String CLOSE_ITEM_STRING = "Close";
  private static final String EXIT_ITEM_STRING = "Exit";
  protected static int numTestFramesOpen = 0;
  private int topBorder = 2;
  private int leftBorder = 2;
  private int bottomBorder = 2;
  private int rightBorder = 2;
  private Component content = null;
  private JPanel contentPanel = null;
  
  public TestFrame()
  {
    this(null);
  }
  
  public TestFrame(Component paramComponent)
  {
    getContentPane().setLayout(new BorderLayout());
    numTestFramesOpen += 1;
    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    JMenuBar localJMenuBar = new JMenuBar();
    JMenu localJMenu = new JMenu("File");
    localJMenu.setMnemonic(70);
    JMenuItem localJMenuItem1 = new JMenuItem("Close", 67);
    localJMenuItem1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        TestFrame.this.dispose();
        TestFrame.windowClosed();
      }
    });
    localJMenu.add(localJMenuItem1);
    JMenuItem localJMenuItem2 = new JMenuItem("Exit", 88);
    localJMenuItem2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.exit(1);
      }
    });
    localJMenu.add(localJMenuItem2);
    localJMenuBar.add(localJMenu);
    setJMenuBar(localJMenuBar);
    this.contentPanel = new JPanel();
    this.contentPanel.setLayout(new BorderLayout());
    this.contentPanel.setBorder(BorderFactory.createEmptyBorder(this.topBorder, this.leftBorder, this.bottomBorder, this.rightBorder));
    getContentPane().add(this.contentPanel);
    if (paramComponent != null) {
      setContent(paramComponent);
    }
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent paramAnonymousWindowEvent) {}
    });
    setSize(400, 400);
    setVisible(true);
  }
  
  public Component getContent()
  {
    return this.content;
  }
  
  public void setContent(Component paramComponent)
    throws IllegalArgumentException
  {
    if (paramComponent == null) {
      throw new IllegalArgumentException("Null reference passed to param val in TestFrame.setContent()");
    }
    Component localComponent = getContent();
    if (localComponent != null) {
      this.contentPanel.remove(localComponent);
    }
    this.contentPanel.add(paramComponent, "Center");
    this.content = paramComponent;
  }
  
  protected static synchronized void windowClosed()
  {
    numTestFramesOpen -= 1;
    if (numTestFramesOpen == 0) {
      System.exit(0);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/additive_color_mixing.jar!/edu/brown/cs/exploratories/components/test/TestFrame.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */