package edu.brown.cs.exploratories.components.swing;

import edu.brown.cs.exploratories.components.awt.event.ActionListenerSupport;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class SelectionListener
{
  private Selection selection;
  private Rectangle dragBounds;
  private MouseInputListener listener = new Listener(null);
  private boolean autoHideSelectionHandles;
  private ActionListenerSupport actionListenerSupport = new ActionListenerSupport(this);
  
  public Selection getSelection()
  {
    return this.selection;
  }
  
  public void setSelection(Selection paramSelection)
  {
    if (this.selection != null)
    {
      this.selection.removeMouseListener(this.listener);
      this.selection.removeMouseMotionListener(this.listener);
    }
    this.selection = paramSelection;
    if (this.selection != null)
    {
      this.selection.addMouseListener(this.listener);
      this.selection.addMouseMotionListener(this.listener);
      if (getAutoHideSelectionHandles()) {
        this.selection.setHandlesVisible(false);
      }
    }
  }
  
  public boolean getAutoHideSelectionHandles()
  {
    return this.autoHideSelectionHandles;
  }
  
  public void setAutoHideSelectionHandles(boolean paramBoolean)
  {
    this.autoHideSelectionHandles = paramBoolean;
    if (this.selection != null) {
      this.selection.setHandlesVisible(false);
    }
  }
  
  public Rectangle getDragBounds()
  {
    return this.dragBounds;
  }
  
  public void setDragBounds(Rectangle paramRectangle)
  {
    this.dragBounds = paramRectangle;
  }
  
  public void addActionListener(ActionListener paramActionListener)
  {
    this.actionListenerSupport.addActionListener(paramActionListener);
  }
  
  public void removeActionListener(ActionListener paramActionListener)
  {
    this.actionListenerSupport.removeActionListener(paramActionListener);
  }
  
  protected void fireActionEvent(ActionEvent paramActionEvent)
  {
    this.actionListenerSupport.fireActionEvent(paramActionEvent);
  }
  
  private static class HandleEnum
  {
    public static HandleEnum NORTH = new HandleEnum("north");
    public static HandleEnum NORTH_WEST = new HandleEnum("northWest");
    public static HandleEnum WEST = new HandleEnum("west");
    public static HandleEnum SOUTH_WEST = new HandleEnum("southWest");
    public static HandleEnum SOUTH = new HandleEnum("south");
    public static HandleEnum SOUTH_EAST = new HandleEnum("southEast");
    public static HandleEnum EAST = new HandleEnum("east");
    public static HandleEnum NORTH_EAST = new HandleEnum("northEast");
    public static HandleEnum CENTER = new HandleEnum("center");
    private String name;
    
    private HandleEnum(String paramString)
    {
      this.name = paramString;
    }
    
    public String toString()
    {
      return this.name;
    }
  }
  
  private class Listener
    extends MouseInputAdapter
  {
    private transient int xOffset;
    private transient int yOffset;
    private transient int startWidth;
    private transient int startHeight;
    private transient SelectionListener.HandleEnum handlePressed = null;
    private transient Insets insets;
    private transient boolean dragging;
    
    private Listener() {}
    
    public void mouseEntered(MouseEvent paramMouseEvent)
    {
      if (!this.dragging) {
        SelectionListener.this.selection.setHandlesVisible(true);
      }
    }
    
    public void mouseExited(MouseEvent paramMouseEvent)
    {
      if (!this.dragging) {
        SelectionListener.this.selection.setHandlesVisible(false);
      }
    }
    
    public void mouseMoved(MouseEvent paramMouseEvent)
    {
      if (!this.dragging)
      {
        if (!SelectionListener.this.selection.getHandlesVisible()) {
          SelectionListener.this.selection.setHandlesVisible(true);
        }
        SelectionListener.HandleEnum localHandleEnum = identifyHandlePressed(paramMouseEvent);
        Cursor localCursor;
        if (localHandleEnum == SelectionListener.HandleEnum.NORTH) {
          localCursor = new Cursor(8);
        } else if (localHandleEnum == SelectionListener.HandleEnum.NORTH_WEST) {
          localCursor = new Cursor(6);
        } else if (localHandleEnum == SelectionListener.HandleEnum.WEST) {
          localCursor = new Cursor(10);
        } else if (localHandleEnum == SelectionListener.HandleEnum.SOUTH_WEST) {
          localCursor = new Cursor(4);
        } else if (localHandleEnum == SelectionListener.HandleEnum.SOUTH) {
          localCursor = new Cursor(9);
        } else if (localHandleEnum == SelectionListener.HandleEnum.SOUTH_EAST) {
          localCursor = new Cursor(5);
        } else if (localHandleEnum == SelectionListener.HandleEnum.EAST) {
          localCursor = new Cursor(11);
        } else if (localHandleEnum == SelectionListener.HandleEnum.NORTH_EAST) {
          localCursor = new Cursor(7);
        } else if (localHandleEnum == SelectionListener.HandleEnum.CENTER) {
          localCursor = new Cursor(13);
        } else {
          localCursor = null;
        }
        SelectionListener.this.selection.setCursor(localCursor);
      }
    }
    
    public void mousePressed(MouseEvent paramMouseEvent)
    {
      this.xOffset = paramMouseEvent.getX();
      this.yOffset = paramMouseEvent.getY();
      this.startWidth = SelectionListener.this.selection.getWidth();
      this.startHeight = SelectionListener.this.selection.getHeight();
      this.handlePressed = identifyHandlePressed(paramMouseEvent);
      this.dragging = true;
      SelectionListener.this.fireActionEvent(new SelectionChangedEvent(SelectionListener.this, SelectionListener.this.selection.getLocation(), SelectionListener.this.selection.getSize(), 0));
    }
    
    public void mouseDragged(MouseEvent paramMouseEvent)
    {
      if (this.handlePressed != null)
      {
        int i = paramMouseEvent.getX() - this.xOffset;
        int j = paramMouseEvent.getY() - this.yOffset;
        int k = SelectionListener.this.selection.getX();
        int m = SelectionListener.this.selection.getY();
        int n = SelectionListener.this.selection.getWidth();
        int i1 = SelectionListener.this.selection.getHeight();
        Rectangle localRectangle = SelectionListener.this.getDragBounds();
        Container localContainer = SelectionListener.this.selection.getParent();
        Insets localInsets = localContainer.getInsets();
        int i2;
        int i3;
        int i4;
        int i5;
        if ((localRectangle != null) && (localRectangle.width > 0) && (localRectangle.height > 0))
        {
          i2 = localInsets.top + localRectangle.y;
          i3 = localInsets.left + localRectangle.x;
          i4 = localInsets.top + localRectangle.y + localRectangle.height;
          i5 = localInsets.left + localRectangle.x + localRectangle.width;
          if (i2 < localInsets.top) {
            i2 = localInsets.top;
          }
          if (i3 < localInsets.left) {
            i3 = localInsets.left;
          }
          if ((i4 < 0) || (i4 > localContainer.getHeight() - localInsets.bottom)) {
            i4 = localContainer.getHeight() - localInsets.bottom;
          }
          if ((i5 < 0) || (i5 > localContainer.getWidth() - localInsets.right)) {
            i5 = localContainer.getWidth() - localInsets.right;
          }
        }
        else
        {
          i2 = localInsets.top;
          i3 = localInsets.left;
          i4 = localContainer.getHeight() - localInsets.bottom;
          i5 = localContainer.getWidth() - localInsets.right;
        }
        int i6;
        if (this.handlePressed == SelectionListener.HandleEnum.NORTH)
        {
          m += j;
          i1 -= j;
          if (i1 - SelectionListener.this.selection.getHandleSize() < 0)
          {
            i6 = SelectionListener.this.selection.getHandleSize() - i1;
            m -= i6;
            i1 = SelectionListener.this.selection.getHandleSize() + i6;
            this.startHeight = (i1 - i6);
            this.handlePressed = SelectionListener.HandleEnum.SOUTH;
            SelectionListener.this.selection.setCursor(new Cursor(9));
          }
          else if (m < i2)
          {
            i6 = i2 - m;
            m = i2;
            i1 -= i6;
          }
        }
        else
        {
          int i7;
          if (this.handlePressed == SelectionListener.HandleEnum.NORTH_WEST)
          {
            k += i;
            m += j;
            n -= i;
            i1 -= j;
            if ((i1 - SelectionListener.this.selection.getHandleSize() < 0) && (n - SelectionListener.this.selection.getHandleSize() < 0))
            {
              i6 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i6;
              i1 = SelectionListener.this.selection.getHandleSize() + i6;
              this.startHeight = (i1 - i6);
              i7 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i7;
              n = SelectionListener.this.selection.getHandleSize() + i7;
              this.startWidth = (n - i7);
              this.handlePressed = SelectionListener.HandleEnum.SOUTH_EAST;
              SelectionListener.this.selection.setCursor(new Cursor(5));
            }
            else if (i1 - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i6;
              i1 = SelectionListener.this.selection.getHandleSize() + i6;
              this.startHeight = (i1 - i6);
              if (k < i3)
              {
                i7 = i3 - k;
                k = i3;
                n -= i7;
              }
              this.handlePressed = SelectionListener.HandleEnum.SOUTH_WEST;
              SelectionListener.this.selection.setCursor(new Cursor(4));
            }
            else if (n - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.startWidth = (n - i6);
              if (m < i2)
              {
                i7 = i2 - m;
                m = i2;
                i1 -= i7;
              }
              this.handlePressed = SelectionListener.HandleEnum.NORTH_EAST;
              SelectionListener.this.selection.setCursor(new Cursor(7));
            }
            else
            {
              if (k < i3)
              {
                i6 = i3 - k;
                k = i3;
                n -= i6;
              }
              if (m < i2)
              {
                i6 = i2 - m;
                m = i2;
                i1 -= i6;
              }
            }
          }
          else if (this.handlePressed == SelectionListener.HandleEnum.WEST)
          {
            k += i;
            n -= i;
            if (n - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.startWidth = (n - i6);
              this.handlePressed = SelectionListener.HandleEnum.EAST;
              SelectionListener.this.selection.setCursor(new Cursor(11));
            }
            else if (k < i3)
            {
              i6 = i3 - k;
              k = i3;
              n -= i6;
            }
          }
          else if (this.handlePressed == SelectionListener.HandleEnum.SOUTH_WEST)
          {
            k += i;
            n -= i;
            i1 = this.startHeight + j;
            if ((n - SelectionListener.this.selection.getHandleSize() < 0) && (i1 - SelectionListener.this.selection.getHandleSize() < 0))
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.startWidth = (n - i6);
              i7 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i7;
              i1 = SelectionListener.this.selection.getHandleSize() + i7;
              this.yOffset = (paramMouseEvent.getY() + i7);
              this.handlePressed = SelectionListener.HandleEnum.NORTH_EAST;
              SelectionListener.this.selection.setCursor(new Cursor(7));
            }
            else if (n - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.startWidth = (n - i6);
              if (m + i1 > i4) {
                i1 = i4 - m;
              }
              this.handlePressed = SelectionListener.HandleEnum.SOUTH_EAST;
              SelectionListener.this.selection.setCursor(new Cursor(5));
            }
            else if (i1 - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i6;
              i1 = SelectionListener.this.selection.getHandleSize() + i6;
              this.yOffset = (paramMouseEvent.getY() + i6);
              if (k < i3)
              {
                i7 = i3 - k;
                k = i3;
                n -= i7;
              }
              this.handlePressed = SelectionListener.HandleEnum.NORTH_WEST;
              SelectionListener.this.selection.setCursor(new Cursor(6));
            }
            else
            {
              if (k < i3)
              {
                i6 = i3 - k;
                k = i3;
                n -= i6;
              }
              if (m + i1 > i4) {
                i1 = i4 - m;
              }
            }
          }
          else if (this.handlePressed == SelectionListener.HandleEnum.SOUTH)
          {
            i1 = this.startHeight + j;
            if (i1 - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i6;
              i1 = SelectionListener.this.selection.getHandleSize() + i6;
              this.yOffset = (paramMouseEvent.getY() + i6);
              this.handlePressed = SelectionListener.HandleEnum.NORTH;
              SelectionListener.this.selection.setCursor(new Cursor(8));
            }
            else if (m + i1 > i4)
            {
              i1 = i4 - m;
            }
          }
          else if (this.handlePressed == SelectionListener.HandleEnum.SOUTH_EAST)
          {
            n = this.startWidth + i;
            i1 = this.startHeight + j;
            if ((n - SelectionListener.this.selection.getHandleSize() < 0) && (i1 - SelectionListener.this.selection.getHandleSize() < 0))
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.xOffset = (paramMouseEvent.getX() + i6);
              i7 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i7;
              i1 = SelectionListener.this.selection.getHandleSize() + i7;
              this.yOffset = (paramMouseEvent.getY() + i7);
              this.handlePressed = SelectionListener.HandleEnum.NORTH_WEST;
              SelectionListener.this.selection.setCursor(new Cursor(6));
            }
            else if (n - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.xOffset = (paramMouseEvent.getX() + i6);
              if (m + i1 > i4) {
                i1 = i4 - m;
              }
              this.handlePressed = SelectionListener.HandleEnum.SOUTH_WEST;
              SelectionListener.this.selection.setCursor(new Cursor(4));
            }
            else if (i1 - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i6;
              i1 = SelectionListener.this.selection.getHandleSize() + i6;
              this.yOffset = (paramMouseEvent.getY() + i6);
              if (k + n > i5) {
                n = i5 - k;
              }
              this.handlePressed = SelectionListener.HandleEnum.NORTH_EAST;
              SelectionListener.this.selection.setCursor(new Cursor(7));
            }
            else
            {
              if (k + n > i5) {
                n = i5 - k;
              }
              if (m + i1 > i4) {
                i1 = i4 - m;
              }
            }
          }
          else if (this.handlePressed == SelectionListener.HandleEnum.EAST)
          {
            n = this.startWidth + i;
            i1 = this.startHeight;
            if (n - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.xOffset = (paramMouseEvent.getX() + i6);
              this.handlePressed = SelectionListener.HandleEnum.WEST;
              SelectionListener.this.selection.setCursor(new Cursor(10));
            }
            else if (k + n > i5)
            {
              n = i5 - k;
            }
          }
          else if (this.handlePressed == SelectionListener.HandleEnum.NORTH_EAST)
          {
            m += j;
            n = this.startWidth + i;
            i1 -= j;
            if ((n - SelectionListener.this.selection.getHandleSize() < 0) && (i1 - SelectionListener.this.selection.getHandleSize() < 0))
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.xOffset = (paramMouseEvent.getX() + i6);
              i7 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i7;
              i1 = SelectionListener.this.selection.getHandleSize() + i7;
              this.startHeight = (i1 - i7);
              this.handlePressed = SelectionListener.HandleEnum.SOUTH_WEST;
              SelectionListener.this.selection.setCursor(new Cursor(4));
            }
            else if (n - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - n;
              k -= i6;
              n = SelectionListener.this.selection.getHandleSize() + i6;
              this.xOffset = (paramMouseEvent.getX() + i6);
              if (m < i2)
              {
                i7 = i2 - m;
                m = i2;
                i1 -= i7;
              }
              this.handlePressed = SelectionListener.HandleEnum.NORTH_WEST;
              SelectionListener.this.selection.setCursor(new Cursor(6));
            }
            else if (i1 - SelectionListener.this.selection.getHandleSize() < 0)
            {
              i6 = SelectionListener.this.selection.getHandleSize() - i1;
              m -= i6;
              i1 = SelectionListener.this.selection.getHandleSize() + i6;
              this.startHeight = (i1 - i6);
              if (k + n > i5) {
                n = i5 - k;
              }
              this.handlePressed = SelectionListener.HandleEnum.SOUTH_EAST;
              SelectionListener.this.selection.setCursor(new Cursor(5));
            }
            else
            {
              if (k + n > i5) {
                n = i5 - k;
              }
              if (m < i2)
              {
                i6 = i2 - m;
                m = i2;
                i1 -= i6;
              }
            }
          }
          else if (this.handlePressed == SelectionListener.HandleEnum.CENTER)
          {
            k += i;
            m += j;
            if (k < i3) {
              k = i3;
            }
            if (m < i2) {
              m = i2;
            }
            if (k + n > i5) {
              k = i5 - n;
            }
            if (m + i1 > i4) {
              m = i4 - i1;
            }
          }
        }
        SelectionListener.this.selection.setLocation(k, m);
        SelectionListener.this.selection.setSize(n, i1);
        if (this.handlePressed == SelectionListener.HandleEnum.CENTER) {
          SelectionListener.this.fireActionEvent(new SelectionChangedEvent(SelectionListener.this, SelectionListener.this.selection.getLocation(), SelectionListener.this.selection.getSize(), 2));
        } else {
          SelectionListener.this.fireActionEvent(new SelectionChangedEvent(SelectionListener.this, SelectionListener.this.selection.getLocation(), SelectionListener.this.selection.getSize(), 1));
        }
      }
    }
    
    public void mouseReleased(MouseEvent paramMouseEvent)
    {
      this.dragging = false;
      SelectionListener.this.fireActionEvent(new SelectionChangedEvent(SelectionListener.this, SelectionListener.this.selection.getLocation(), SelectionListener.this.selection.getSize(), 3));
    }
    
    private SelectionListener.HandleEnum identifyHandlePressed(MouseEvent paramMouseEvent)
    {
      SelectionListener.HandleEnum localHandleEnum = null;
      Selection localSelection = SelectionListener.this.getSelection();
      if (localSelection.getHandlesVisible())
      {
        double d1 = localSelection.getHandleSize();
        double d2 = d1 / 2.0D;
        double d3 = paramMouseEvent.getX();
        double d4 = paramMouseEvent.getY();
        double d5 = localSelection.getWidth();
        double d6 = localSelection.getHeight();
        Insets localInsets = localSelection.getInsets();
        double d7 = localInsets.left;
        double d8 = localInsets.top;
        double d9 = localInsets.bottom;
        double d10 = localInsets.right;
        if ((d3 > d7) && (d3 < d7 + d1) && (d4 > d8) && (d4 < d8 + d1)) {
          localHandleEnum = SelectionListener.HandleEnum.NORTH_WEST;
        } else if ((d3 > d7) && (d3 < d7 + d1) && (d4 > d6 / 2.0D - d2) && (d4 < d6 / 2.0D + d2)) {
          localHandleEnum = SelectionListener.HandleEnum.WEST;
        } else if ((d3 > d7) && (d3 < d7 + d1) && (d4 > d6 - d8 - d1) && (d4 < d6 - d8)) {
          localHandleEnum = SelectionListener.HandleEnum.SOUTH_WEST;
        } else if ((d3 > d5 / 2.0D - d2) && (d3 < d5 / 2.0D + d2) && (d4 > d8) && (d4 < d8 + d1)) {
          localHandleEnum = SelectionListener.HandleEnum.NORTH;
        } else if ((d3 > d5 / 2.0D - d2) && (d3 < d5 / 2.0D + d2) && (d4 > d6 - d8 - d1) && (d4 < d6 - d8)) {
          localHandleEnum = SelectionListener.HandleEnum.SOUTH;
        } else if ((d3 > d5 - d10 - d1) && (d3 < d5 - d10) && (d4 > d8) && (d4 < d8 + d1)) {
          localHandleEnum = SelectionListener.HandleEnum.NORTH_EAST;
        } else if ((d3 > d5 - d10 - d1) && (d3 < d5 - d10) && (d4 > d6 / 2.0D - d2) && (d4 < d6 / 2.0D + d2)) {
          localHandleEnum = SelectionListener.HandleEnum.EAST;
        } else if ((d3 > d5 - d10 - d1) && (d3 < d5 - d10) && (d4 > d6 - d8 - d1) && (d4 < d6 - d8)) {
          localHandleEnum = SelectionListener.HandleEnum.SOUTH_EAST;
        } else if ((d3 > d7 + d2) && (d3 < d5 - d10 - d2) && (d4 > d8 + d2) && (d4 < d6 - d9 - d2)) {
          localHandleEnum = SelectionListener.HandleEnum.CENTER;
        }
      }
      return localHandleEnum;
    }
    
    Listener(SelectionListener.1 param1)
    {
      this();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/SelectionListener.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */