package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.PrintStream;
import java.net.URL;
import java.util.Vector;
import javax.swing.JApplet;

public class ObjectSpace
  extends Component
{
  Image[] images_ = new Image['Ѐ'];
  String[] names_ = new String['Ѐ'];
  protected Image offscreen_;
  protected Image background;
  private Image backgroundImage;
  protected Graphics graphics;
  protected Graphics backgroundGraphics;
  protected int width;
  protected int height;
  protected int BGWidth;
  protected int BGHeight;
  protected int numImages;
  protected int curImage;
  private Vector clickables;
  private Vector manipulables;
  private Vector resizeables;
  private Vector drawables;
  private Vector draggables;
  private Vector mouseTrackers;
  private Vector highlightables;
  private Vector activeObjects;
  private Vector activeDraggableObjects;
  private Vector masterList;
  private Vector foregroundObjects;
  private Vector clickableNonDrawables;
  private JApplet applet;
  private ImageLoader imageLoader;
  private MediaTracker tracker;
  private boolean backgroundImageIsSet;
  private boolean oneDragAtATime;
  private boolean oneClickAtATime;
  private boolean animOn;
  private Color backgroundColor;
  private double xScaleFacter;
  private double yScaleFacter;
  private RedrawThread redrawManager;
  
  public ObjectSpace(JApplet paramJApplet, int paramInt1, int paramInt2)
  {
    this.applet = paramJApplet;
    this.width = paramInt1;
    this.height = paramInt2;
    this.backgroundImageIsSet = false;
    this.backgroundColor = Color.black;
    this.oneDragAtATime = true;
    this.oneClickAtATime = true;
    this.animOn = true;
    new MBCanvasMouseListener();
    new MBCanvasMouseMotionListener();
    new JKresizeListener();
    setLocation(0, 0);
    setBackground(Color.black);
    this.curImage = 0;
    this.tracker = new MediaTracker(this);
    this.numImages = 0;
    this.clickables = new Vector(10, 5);
    this.manipulables = new Vector(10, 5);
    this.resizeables = new Vector(10, 5);
    this.drawables = new Vector(10, 5);
    this.draggables = new Vector(10, 5);
    this.highlightables = new Vector(10, 5);
    this.mouseTrackers = new Vector(10, 5);
    this.activeObjects = new Vector(10, 5);
    this.activeDraggableObjects = new Vector(10, 5);
    this.masterList = new Vector(10, 5);
    this.foregroundObjects = new Vector(10, 5);
    this.clickableNonDrawables = new Vector(10, 5);
    this.redrawManager = new RedrawThread(this, 15);
    this.applet.getContentPane().add(this);
    initialize();
  }
  
  public void initialize()
  {
    setToSize(this.width, this.height);
    setVisible(true);
    restoreBackground();
    this.graphics.drawImage(this.background, 0, 0, this);
  }
  
  public void mainloop()
  {
    finishLoading();
    this.redrawManager.start();
  }
  
  public void setImageIndex(String paramString1, String paramString2)
  {
    this.imageLoader = new ImageLoader(paramString1, paramString2);
  }
  
  private void setToSize(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    setSize(paramInt1, paramInt2);
    this.offscreen_ = new BufferedImage(paramInt1, paramInt2, 1);
    this.background = new BufferedImage(paramInt1, paramInt2, 1);
    if ((this.offscreen_ != null) && (this.background != null))
    {
      this.graphics = this.offscreen_.getGraphics();
      this.backgroundGraphics = this.background.getGraphics();
    }
    repaint();
  }
  
  public void addObject(VisualObject paramVisualObject)
  {
    if (!this.clickables.contains(paramVisualObject)) {
      this.clickables.addElement(paramVisualObject);
    }
    if (!this.resizeables.contains(paramVisualObject)) {
      this.resizeables.addElement(paramVisualObject);
    }
    if (!this.drawables.contains(paramVisualObject)) {
      this.drawables.insertElementAt(paramVisualObject, this.drawables.size() - this.foregroundObjects.size());
    }
    if (!this.masterList.contains(paramVisualObject)) {
      this.masterList.addElement(paramVisualObject);
    }
    paramVisualObject.setObjectSpace(this, this.graphics);
  }
  
  public void removeObject(VisualObject paramVisualObject)
  {
    this.clickables.removeElement(paramVisualObject);
    this.manipulables.removeElement(paramVisualObject);
    this.resizeables.removeElement(paramVisualObject);
    if (this.foregroundObjects.contains(paramVisualObject)) {
      this.foregroundObjects.removeElement(paramVisualObject);
    }
    this.drawables.removeElement(paramVisualObject);
    this.highlightables.removeElement(paramVisualObject);
    this.masterList.removeElement(paramVisualObject);
    this.mouseTrackers.removeElement(paramVisualObject);
    this.draggables.removeElement(paramVisualObject);
    paramVisualObject.setObjectSpace(this, this.graphics);
  }
  
  public void declareForegroundObject(Drawable paramDrawable)
  {
    this.foregroundObjects.removeElement(paramDrawable);
    this.foregroundObjects.addElement(paramDrawable);
    if (!this.drawables.contains(paramDrawable)) {
      this.drawables.addElement(paramDrawable);
    }
  }
  
  public void undeclareForegroundObject(Drawable paramDrawable)
  {
    if (this.foregroundObjects.removeElement(paramDrawable))
    {
      this.drawables.removeElement(paramDrawable);
      this.drawables.insertElementAt(paramDrawable, this.drawables.size() - this.foregroundObjects.size());
    }
  }
  
  public void enableClickable(Clickable paramClickable)
  {
    if (!this.clickables.contains(paramClickable)) {
      this.clickables.addElement(paramClickable);
    }
    if ((!this.drawables.contains(paramClickable)) && (!this.clickableNonDrawables.contains(paramClickable))) {
      this.clickableNonDrawables.addElement(paramClickable);
    }
  }
  
  public void enableManipulable(Manipulable paramManipulable)
  {
    if (!this.manipulables.contains(paramManipulable)) {
      this.manipulables.addElement(paramManipulable);
    }
  }
  
  public void enableResizeable(Resizeable paramResizeable)
  {
    if (!this.resizeables.contains(paramResizeable)) {
      this.resizeables.addElement(paramResizeable);
    }
  }
  
  public void enableHighlightable(Highlightable paramHighlightable)
  {
    if (!this.highlightables.contains(paramHighlightable)) {
      this.highlightables.addElement(paramHighlightable);
    }
  }
  
  public void enableMouseTracker(MouseTracker paramMouseTracker)
  {
    if (!this.mouseTrackers.contains(paramMouseTracker)) {
      this.mouseTrackers.addElement(paramMouseTracker);
    }
  }
  
  public void enableDrawable(Drawable paramDrawable)
  {
    if (!this.drawables.contains(paramDrawable))
    {
      this.drawables.insertElementAt(paramDrawable, this.drawables.size() - this.foregroundObjects.size());
      paramDrawable.setObjectSpace(this, this.graphics);
    }
    System.out.println("removing from clickableNonDrawables");
    this.clickableNonDrawables.removeElement(paramDrawable);
  }
  
  public void enableDraggable(Draggable paramDraggable)
  {
    if (!this.draggables.contains(paramDraggable)) {
      this.draggables.addElement(paramDraggable);
    }
  }
  
  public boolean disableClickable(Clickable paramClickable)
  {
    this.clickableNonDrawables.removeElement(paramClickable);
    return this.clickables.removeElement(paramClickable);
  }
  
  public boolean disableManipulable(Manipulable paramManipulable)
  {
    return this.manipulables.removeElement(paramManipulable);
  }
  
  public boolean disableResizeable(Resizeable paramResizeable)
  {
    return this.resizeables.removeElement(paramResizeable);
  }
  
  public boolean disableHighlightable(Highlightable paramHighlightable)
  {
    return this.highlightables.removeElement(paramHighlightable);
  }
  
  public boolean disableMouseTracker(MouseTracker paramMouseTracker)
  {
    return this.mouseTrackers.removeElement(paramMouseTracker);
  }
  
  public boolean disableDrawable(Drawable paramDrawable)
  {
    if ((this.clickables.contains(paramDrawable)) && (!this.clickableNonDrawables.contains(paramDrawable))) {
      this.clickableNonDrawables.addElement(paramDrawable);
    }
    this.foregroundObjects.removeElement(paramDrawable);
    return this.drawables.removeElement(paramDrawable);
  }
  
  public boolean disableDraggable(Draggable paramDraggable)
  {
    return this.draggables.removeElement(paramDraggable);
  }
  
  private void clicked(int paramInt1, int paramInt2)
  {
    int i;
    if (this.oneClickAtATime)
    {
      i = 0;
      for (int j = this.drawables.size() - 1; j > -1; j--)
      {
        Drawable localDrawable = (Drawable)this.drawables.elementAt(j);
        if ((this.clickables.contains(localDrawable)) && (localDrawable.contains(paramInt1 - localDrawable.getX(), paramInt2 - localDrawable.getY())))
        {
          ((Clickable)localDrawable).mouseClicked(paramInt1 - localDrawable.getX(), paramInt2 - localDrawable.getY());
          i = 1;
          j = -1;
        }
      }
      if (i == 0) {
        for (int k = this.clickableNonDrawables.size() - 1; k > -1; k--)
        {
          Clickable localClickable2 = (Clickable)this.clickableNonDrawables.elementAt(k);
          if (localClickable2.contains(paramInt1 - localClickable2.getX(), paramInt2 - localClickable2.getY()))
          {
            localClickable2.mouseClicked(paramInt1 - localClickable2.getX(), paramInt2 - localClickable2.getY());
            i = 1;
          }
        }
      }
    }
    else
    {
      for (i = 0; i < this.clickables.size(); i++)
      {
        Clickable localClickable1 = (Clickable)this.clickables.elementAt(i);
        if (localClickable1.contains(paramInt1 - localClickable1.getX(), paramInt2 - localClickable1.getY())) {
          localClickable1.mouseClicked(paramInt1 - localClickable1.getX(), paramInt2 - localClickable1.getY());
        }
      }
    }
    redraw();
  }
  
  private void pressed(int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < this.manipulables.size(); i++)
    {
      Manipulable localManipulable = (Manipulable)this.manipulables.elementAt(i);
      if (localManipulable.contains(paramInt1 - localManipulable.getX(), paramInt2 - localManipulable.getY()))
      {
        localManipulable.mousePressed(paramInt1, paramInt2);
        this.activeObjects.addElement(localManipulable);
      }
    }
    int j;
    Drawable localDrawable;
    if (this.oneDragAtATime)
    {
      for (j = this.drawables.size() - 1; j > -1; j--)
      {
        localDrawable = (Drawable)this.drawables.elementAt(j);
        if ((this.draggables.contains(localDrawable)) && (localDrawable.contains(paramInt1 - localDrawable.getX(), paramInt2 - localDrawable.getY())))
        {
          this.activeDraggableObjects.addElement(localDrawable);
          bringToFront(localDrawable);
          j = -1;
        }
      }
    }
    else
    {
      for (j = 0; j < this.drawables.size(); j++)
      {
        localDrawable = (Drawable)this.drawables.elementAt(j);
        if ((this.draggables.contains(localDrawable)) && (localDrawable.contains(paramInt1 - localDrawable.getX(), paramInt2 - localDrawable.getY()))) {
          this.activeDraggableObjects.addElement(localDrawable);
        }
      }
      bringToFront(this.activeDraggableObjects);
    }
    redraw();
  }
  
  private void released(int paramInt1, int paramInt2)
  {
    for (int i = 0; i < this.activeObjects.size(); i++)
    {
      Manipulable localManipulable = (Manipulable)this.activeObjects.elementAt(i);
      localManipulable.mouseReleased(paramInt1 - localManipulable.getX(), paramInt2 - localManipulable.getY());
    }
    this.activeObjects.setSize(0);
    this.activeDraggableObjects.setSize(0);
    redraw();
  }
  
  private void dragged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = 0; i < this.mouseTrackers.size(); i++)
    {
      MouseTracker localMouseTracker = (MouseTracker)this.mouseTrackers.elementAt(i);
      localMouseTracker.mouseMoved(paramInt1, paramInt2);
    }
    int j = paramInt1 - paramInt3;
    int k = paramInt2 - paramInt4;
    for (int m = 0; m < this.activeObjects.size(); m++)
    {
      Manipulable localManipulable = (Manipulable)this.activeObjects.elementAt(m);
      localManipulable.mouseDragged(j, k);
    }
    for (int n = 0; n < this.activeDraggableObjects.size(); n++)
    {
      Draggable localDraggable = (Draggable)this.activeDraggableObjects.elementAt(n);
      localDraggable.move(j, k);
    }
    redraw();
  }
  
  private void moved(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = 0; i < this.highlightables.size(); i++)
    {
      Highlightable localHighlightable = (Highlightable)this.highlightables.elementAt(i);
      if (localHighlightable.contains(paramInt1 - localHighlightable.getX(), paramInt2 - localHighlightable.getY())) {
        localHighlightable.highlight(paramInt1 - localHighlightable.getX(), paramInt2 - localHighlightable.getY());
      } else {
        localHighlightable.highlightOff();
      }
    }
    for (int j = 0; j < this.mouseTrackers.size(); j++)
    {
      MouseTracker localMouseTracker = (MouseTracker)this.mouseTrackers.elementAt(j);
      localMouseTracker.mouseMoved(paramInt1, paramInt2);
    }
    redraw();
  }
  
  public void resizeAll(int paramInt1, int paramInt2)
  {
    this.xScaleFacter = (paramInt1 / this.width);
    this.yScaleFacter = (paramInt2 / this.height);
    setToSize(paramInt1, paramInt2);
    restoreBackground();
    this.graphics.drawImage(this.background, 0, 0, this);
    for (int i = 0; i < this.resizeables.size(); i++)
    {
      Resizeable localResizeable = (Resizeable)this.resizeables.elementAt(i);
      localResizeable.resize(this.xScaleFacter, this.yScaleFacter, this.width, this.height);
    }
    for (int j = 0; j < this.masterList.size(); j++) {
      ((VisualObject)this.masterList.elementAt(j)).setObjectSpace(this, this.graphics);
    }
  }
  
  public void bringToFront(Drawable paramDrawable)
  {
    if (!this.foregroundObjects.contains(paramDrawable))
    {
      this.drawables.removeElement(paramDrawable);
      this.drawables.insertElementAt(paramDrawable, this.drawables.size() - this.foregroundObjects.size());
    }
  }
  
  public void bringForwardButPreserveTopObject(Drawable paramDrawable, int paramInt)
  {
    if (!this.foregroundObjects.contains(paramDrawable))
    {
      this.drawables.removeElement(paramDrawable);
      this.drawables.insertElementAt(paramDrawable, this.drawables.size() - (paramInt + this.foregroundObjects.size()));
    }
  }
  
  public void bringToFront(Vector paramVector)
  {
    for (int i = paramVector.size(); i < 0; i--) {
      if (!this.foregroundObjects.contains(paramVector.elementAt(i))) {
        this.drawables.removeElement(paramVector.elementAt(i));
      }
    }
    for (int j = paramVector.size(); j < 0; j--) {
      if (!this.foregroundObjects.contains(paramVector.elementAt(j))) {
        this.drawables.insertElementAt(paramVector.elementAt(j), this.drawables.size() - this.foregroundObjects.size());
      }
    }
  }
  
  public void sendToBack(Drawable paramDrawable)
  {
    if (!this.foregroundObjects.contains(paramDrawable))
    {
      this.drawables.removeElement(paramDrawable);
      this.drawables.insertElementAt(paramDrawable, 0);
    }
  }
  
  public void sendToBack(Vector paramVector)
  {
    for (int i = 0; i < paramVector.size(); i++) {
      if (!this.foregroundObjects.contains(paramVector.elementAt(i))) {
        this.drawables.removeElement(paramVector.elementAt(i));
      }
    }
    for (int j = 0; j < paramVector.size(); j++) {
      if (!this.foregroundObjects.contains(paramVector.elementAt(j))) {
        this.drawables.insertElementAt(paramVector.elementAt(j), 0);
      }
    }
  }
  
  public Drawable getDrawableObjectAt(int paramInt1, int paramInt2)
  {
    Drawable localDrawable = null;
    for (int i = this.drawables.size() - 1; i > -1; i--)
    {
      localDrawable = (Drawable)this.drawables.elementAt(i);
      if (localDrawable.contains(paramInt1 - localDrawable.getX(), paramInt2 - localDrawable.getY())) {
        i = -1;
      } else {
        localDrawable = null;
      }
    }
    return localDrawable;
  }
  
  public Vector getDrawableObjectsAt(int paramInt1, int paramInt2)
  {
    Vector localVector = new Vector(10, 5);
    for (int i = 0; i < this.drawables.size(); i++)
    {
      Drawable localDrawable = (Drawable)this.drawables.elementAt(i);
      if (localDrawable.contains(paramInt1 - localDrawable.getX(), paramInt2 - localDrawable.getY())) {
        localVector.addElement(localDrawable);
      }
    }
    return localVector;
  }
  
  public Image loadImage(String paramString)
  {
    String str = this.imageLoader.getImage(paramString);
    URL localURL = getClass().getResource(str);
    if (localURL == null) {
      throw new Error();
    }
    Image localImage = Toolkit.getDefaultToolkit().getImage(localURL);
    this.names_[this.curImage] = paramString;
    this.images_[this.curImage] = localImage;
    this.tracker.addImage(this.images_[this.curImage], this.curImage);
    this.curImage += 1;
    return localImage;
  }
  
  public void finishLoading()
  {
    for (int i = 0; i < this.curImage; i++) {
      try
      {
        this.tracker.waitForID(i, 600L);
        if (!this.tracker.checkID(i)) {
          System.out.println("Load failed on " + i);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        System.out.println("Problem image; i = " + i);
        if (this.tracker.isErrorID(i)) {
          return;
        }
      }
    }
  }
  
  public MediaTracker getTracker()
  {
    return this.tracker;
  }
  
  public ImageObserver getImageObserver()
  {
    return this;
  }
  
  public void turnAnimationOn()
  {
    this.animOn = true;
  }
  
  public void turnAnimationOff()
  {
    this.animOn = false;
  }
  
  public boolean getAnimStatus()
  {
    return this.animOn;
  }
  
  public void printObjectStatus(VisualObject paramVisualObject)
  {
    System.out.println("Object status report for " + paramVisualObject);
    if (this.clickables.contains(paramVisualObject)) {
      System.out.println("is clickable");
    }
    if (this.drawables.contains(paramVisualObject)) {
      System.out.println("is drawable");
    }
    if (this.manipulables.contains(paramVisualObject)) {
      System.out.println("is manipulable");
    }
    if (this.resizeables.contains(paramVisualObject)) {
      System.out.println("is resizeable");
    }
    if (this.highlightables.contains(paramVisualObject)) {
      System.out.println("is highlightable");
    }
    if (this.mouseTrackers.contains(paramVisualObject)) {
      System.out.println("is mouseTracker");
    }
    if (this.draggables.contains(paramVisualObject)) {
      System.out.println("is draggable");
    }
    if (this.foregroundObjects.contains(paramVisualObject)) {
      System.out.println("is foreground object");
    }
    if (this.masterList.contains(paramVisualObject)) {
      System.out.println("is acknowledged by the masterList");
    }
    if (this.clickableNonDrawables.contains(paramVisualObject)) {
      System.out.println("is a clickableNonDrawable");
    }
  }
  
  public void setColor(Color paramColor)
  {
    this.graphics.setColor(paramColor);
    this.backgroundGraphics.setColor(paramColor);
  }
  
  public void drawImage(int paramInt1, int paramInt2, Image paramImage)
  {
    this.graphics.drawImage(paramImage, paramInt1, paramInt2, this);
  }
  
  public void fillShape(Polygon paramPolygon)
  {
    this.graphics.fillPolygon(paramPolygon);
  }
  
  public void drawShape(Polygon paramPolygon)
  {
    this.graphics.drawPolygon(paramPolygon);
  }
  
  public void fillShape(Rectangle paramRectangle)
  {
    Point localPoint = paramRectangle.getLocation();
    Dimension localDimension = paramRectangle.getSize();
    this.graphics.fillRect(localPoint.x, localPoint.y, localDimension.width, localDimension.height);
  }
  
  public void drawShape(Rectangle paramRectangle)
  {
    Point localPoint = paramRectangle.getLocation();
    Dimension localDimension = paramRectangle.getSize();
    this.graphics.drawRect(localPoint.x, localPoint.y, localDimension.width, localDimension.height);
  }
  
  public Graphics getGraphics()
  {
    return this.graphics;
  }
  
  public void stopDrawing()
  {
    this.redrawManager.stopDrawing();
  }
  
  public void drawImageToBackground(int paramInt1, int paramInt2, Image paramImage)
  {
    this.backgroundGraphics.drawImage(paramImage, paramInt1, paramInt2, this);
  }
  
  public void fillShapeToBackground(Polygon paramPolygon)
  {
    this.backgroundGraphics.fillPolygon(paramPolygon);
  }
  
  public void drawShapeToBackground(Polygon paramPolygon)
  {
    this.backgroundGraphics.drawPolygon(paramPolygon);
  }
  
  public void fillShapeToBackground(Rectangle paramRectangle)
  {
    Point localPoint = paramRectangle.getLocation();
    Dimension localDimension = paramRectangle.getSize();
    this.backgroundGraphics.fillRect(localPoint.x, localPoint.y, localDimension.width, localDimension.height);
  }
  
  public void drawShapeToBackground(Rectangle paramRectangle)
  {
    Point localPoint = paramRectangle.getLocation();
    Dimension localDimension = paramRectangle.getSize();
    this.backgroundGraphics.drawRect(localPoint.x, localPoint.y, localDimension.width, localDimension.height);
  }
  
  public Graphics backgroundGraphics()
  {
    return this.backgroundGraphics;
  }
  
  public void redraw()
  {
    this.redrawManager.requestRedraw();
  }
  
  public void executeRedraw()
  {
    repaint();
  }
  
  public void restoreBackground()
  {
    if (this.backgroundImageIsSet) {
      this.backgroundGraphics.drawImage(this.backgroundImage, 0, 0, this.width, this.height, 0, 0, this.BGWidth, this.BGHeight, this);
    } else {
      fillCanvas(this.backgroundColor);
    }
  }
  
  public void setBackgroundImage(String paramString)
  {
    this.backgroundImage = loadImage(paramString);
    finishLoading();
    this.BGWidth = this.backgroundImage.getWidth(this);
    this.BGHeight = this.backgroundImage.getHeight(this);
    Graphics localGraphics = this.background.getGraphics();
    localGraphics.drawImage(this.backgroundImage, 0, 0, this.width, this.height, 0, 0, this.BGWidth, this.BGHeight, this);
    this.backgroundImageIsSet = true;
    executeRedraw();
  }
  
  public void setBackgroundColor(Color paramColor)
  {
    this.backgroundColor = paramColor;
    restoreBackground();
  }
  
  public void paint(Graphics paramGraphics)
  {
    this.graphics.drawImage(this.background, 0, 0, this);
    drawAll();
    paramGraphics.drawImage(this.offscreen_, 0, 0, this);
  }
  
  public void refresh()
  {
    repaint(0L);
  }
  
  public Image getImage()
  {
    return this.offscreen_;
  }
  
  public void fillCanvas(Color paramColor)
  {
    if (this.backgroundGraphics != null)
    {
      this.backgroundGraphics.setColor(paramColor);
      this.backgroundGraphics.fillRect(0, 0, this.width, this.height);
    }
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  private void drawAll()
  {
    for (int i = 0; i < this.drawables.size(); i++)
    {
      Drawable localDrawable = (Drawable)this.drawables.elementAt(i);
      localDrawable.draw();
    }
  }
  
  public class JKresizeListener
    extends ComponentAdapter
  {
    private ObjectSpace toResize = ObjectSpace.this;
    
    public JKresizeListener()
    {
      this.toResize.addComponentListener(this);
    }
    
    public void componentResized(ComponentEvent paramComponentEvent)
    {
      this.toResize.resizeAll(this.toResize.getSize().width, this.toResize.getSize().height);
    }
  }
  
  public class MBCanvasMouseMotionListener
    extends MouseMotionAdapter
  {
    private ObjectSpace canvas_ = ObjectSpace.this;
    int prevx;
    int prevy;
    
    public MBCanvasMouseMotionListener()
    {
      this.canvas_.addMouseMotionListener(this);
    }
    
    public void mouseDragged(MouseEvent paramMouseEvent)
    {
      ObjectSpace.this.dragged(paramMouseEvent.getX(), paramMouseEvent.getY(), this.prevx, this.prevy);
      this.prevx = paramMouseEvent.getX();
      this.prevy = paramMouseEvent.getY();
    }
    
    public void mouseMoved(MouseEvent paramMouseEvent)
    {
      ObjectSpace.this.moved(paramMouseEvent.getX(), paramMouseEvent.getY(), this.prevx, this.prevy);
      this.prevx = paramMouseEvent.getX();
      this.prevy = paramMouseEvent.getY();
    }
  }
  
  public class MBCanvasMouseListener
    extends MouseAdapter
  {
    private boolean mousePressed_;
    private ObjectSpace canvas_ = ObjectSpace.this;
    
    public MBCanvasMouseListener()
    {
      this.canvas_.addMouseListener(this);
    }
    
    public void mousePressed(MouseEvent paramMouseEvent)
    {
      if (!this.mousePressed_) {
        ObjectSpace.this.pressed(paramMouseEvent.getX(), paramMouseEvent.getY(), paramMouseEvent.getModifiers());
      }
      this.mousePressed_ = true;
    }
    
    public void mouseReleased(MouseEvent paramMouseEvent)
    {
      if (this.mousePressed_) {
        ObjectSpace.this.released(paramMouseEvent.getX(), paramMouseEvent.getY());
      }
      this.mousePressed_ = false;
    }
    
    public void mouseClicked(MouseEvent paramMouseEvent)
    {
      ObjectSpace.this.clicked(paramMouseEvent.getX(), paramMouseEvent.getY());
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/ObjectSpace.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */