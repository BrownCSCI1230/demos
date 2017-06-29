/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class HTree extends JPanel implements HDrawNodeListener, ComponentListener, MouseListener, MouseMotionListener
/*     */ {
/*     */   private float[][] _identity;
/*     */   private HNode _root;
/*     */   private HNode _activeNode;
/*     */   private Image _back;
/*     */   private Image _tempback;
/*     */   private Vector _nodes;
/*     */   private HRobotContainer _robotContainer;
/*     */   private boolean _nodeFound;
/*     */   private boolean _moveNodes;
/*     */   private boolean _needsToBeRedrawn;
/*     */   private Stack _matrices;
/*     */   private HMatrixDisplay _display;
/*     */   private HMatrixStacker _stacker;
/*     */   private boolean _mousePressedRunning;
/*     */   private HToolPalette _palette;
/*     */   private Vector _unlinkedNodes;
/*     */   private HApplet _applet;
/*  38 */   public static final Color BACKGROUND = new Color(205, 190, 180);
/*     */   
/*     */   public HTree()
/*     */   {
/*  42 */     setPreferredSize(getSize());
/*  43 */     this._moveNodes = false;
/*  44 */     this._nodes = new Vector();
/*  45 */     this._unlinkedNodes = new Vector();
/*  46 */     this._identity = HMatrixOps.getIdentity(3);
/*  47 */     this._matrices = new Stack();
/*     */     
/*  49 */     this._mousePressedRunning = false;
/*  50 */     this._needsToBeRedrawn = true;
/*     */     
/*  52 */     addComponentListener(this);
/*  53 */     addMouseListener(this);
/*  54 */     addMouseMotionListener(this);
/*  55 */     setBorder(BorderFactory.createLoweredBevelBorder());
/*  56 */     setBackground(BACKGROUND);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRobotContainer(HRobotContainer paramHRobotContainer)
/*     */   {
/*  63 */     this._robotContainer = paramHRobotContainer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HRobotContainer getRobotContainer()
/*     */   {
/*  70 */     return this._robotContainer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawRobot()
/*     */   {
/*  77 */     this._root.render(this._identity, false, new HPosition(0.0F, 0.0F));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void getMatrix()
/*     */   {
/*  84 */     this._root.getMatrix(this._matrices, false, this._identity, 1, new String(""));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void forceRedrawOnPaint()
/*     */   {
/*  91 */     this._needsToBeRedrawn = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void paintComponent(Graphics paramGraphics)
/*     */   {
/*  98 */     if (this._back == null) {
/*  99 */       this._back = createImage(getSize().width, getSize().height);
/* 100 */       this._tempback = createImage(getSize().width, getSize().height);
/*     */     }
/*     */     
/* 103 */     if (this._back != null) {
/* 104 */       if (this._needsToBeRedrawn) {
/* 105 */         super.paintComponent(this._back.getGraphics());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 112 */         for (int i = 0; i < this._nodes.size(); i++) {
/* 113 */           ((HNode)this._nodes.elementAt(i)).drawThisLine(this._back.getGraphics());
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 120 */         for (int j = 0; j < this._nodes.size(); j++) {
/* 121 */           ((HNode)this._nodes.elementAt(j)).drawThisNode(this._back.getGraphics());
/*     */         }
/* 123 */         this._tempback.getGraphics().drawImage(this._back, 0, 0, null);
/* 124 */         this._needsToBeRedrawn = false;
/*     */       }
/* 126 */       paramGraphics.drawImage(this._back, 0, 0, null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawTree()
/*     */   {
/* 135 */     super.paint(this._back.getGraphics());
/* 136 */     this._root.setSelects(false, false);
/* 137 */     this._root.drawTree();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSelects()
/*     */   {
/* 145 */     this._root.setSelects(false, false);
/* 146 */     for (int i = 0; i < this._unlinkedNodes.size(); i++) {
/* 147 */       ((HNode)this._unlinkedNodes.elementAt(i)).setSelects(false, false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void clearHighlight()
/*     */   {
/* 154 */     if (this._robotContainer != null) {
/* 155 */       Vector localVector = this._robotContainer.getParts();
/* 156 */       for (int i = 0; i < localVector.size(); i++) {
/* 157 */         ((HRobotPart)localVector.elementAt(i)).clearHighlight();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public HRootNode getRoot()
/*     */   {
/* 164 */     return (HRootNode)this._root;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRoot(HNode paramHNode)
/*     */   {
/* 171 */     this._root.addChild(paramHNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawNode(HDrawNodeEvent paramHDrawNodeEvent)
/*     */   {
/* 178 */     paramHDrawNodeEvent.getNode().drawThisNode(this._back.getGraphics());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawLine(HDrawNodeEvent paramHDrawNodeEvent)
/*     */   {
/* 185 */     paramHDrawNodeEvent.getNode().drawThisLine(this._back.getGraphics());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void componentResized(ComponentEvent paramComponentEvent)
/*     */   {
/* 192 */     this._needsToBeRedrawn = true;
/* 193 */     this._back = null;
/*     */     try {
/* 195 */       repaint();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */ 
/*     */   public void addNode(HNode paramHNode)
/*     */   {
/* 203 */     if (!this._nodes.contains(paramHNode)) {
/* 204 */       this._nodes.addElement(paramHNode);
/* 205 */       add(paramHNode);
/* 206 */       if (this._display != null)
/* 207 */         paramHNode.addDrawMatrixListener(this._display);
/* 208 */       if (this._stacker != null) {
/* 209 */         paramHNode.addDrawMatrixListener(this._stacker);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeNode(HNode paramHNode)
/*     */   {
/* 217 */     this._nodes.removeElement(paramHNode);
/* 218 */     if (this._display != null) {
/* 219 */       paramHNode.removeDrawMatrixListener(this._display);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Vector getNodes()
/*     */   {
/* 226 */     return this._nodes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createRoot(HChooser paramHChooser)
/*     */   {
/* 234 */     this._root = new HRootNode(this, paramHChooser);
/* 235 */     this._root.setPosition(150.0F, 50.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMovement(boolean paramBoolean)
/*     */   {
/* 242 */     this._moveNodes = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDisplay(HMatrixDisplay paramHMatrixDisplay)
/*     */   {
/* 249 */     this._display = paramHMatrixDisplay;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HMatrixDisplay getDisplay()
/*     */   {
/* 256 */     return this._display;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStacker(HMatrixStacker paramHMatrixStacker)
/*     */   {
/* 263 */     this._stacker = paramHMatrixStacker;
/*     */   }
/*     */   
/*     */   public HMatrixStacker getStacker() {
/* 267 */     return this._stacker;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPalette(HToolPalette paramHToolPalette, HApplet paramHApplet)
/*     */   {
/* 275 */     this._palette = paramHToolPalette;
/* 276 */     this._applet = paramHApplet;
/*     */   }
/*     */   
/*     */   public HApplet getApplet() {
/* 280 */     return this._applet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 288 */     this._nodeFound = false;
/* 289 */     this._activeNode = null;
/* 290 */     for (int i = 0; (i < this._nodes.size()) && (this._nodeFound == false); i++) {
/* 291 */       HNode localHNode = (HNode)this._nodes.elementAt(i);
/*     */       
/* 293 */       if (HMatrixOps.length(localHNode.getX() - paramMouseEvent.getX(), localHNode.getY() - paramMouseEvent.getY()) <= 15.0F) {
/* 294 */         this._nodeFound = true;
/* 295 */         this._activeNode = localHNode;
/*     */       }
/*     */     }
/*     */     
/* 299 */     if (this._palette != null) {
/* 300 */       this._palette.getSelected().toolPressed(paramMouseEvent, this._activeNode);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void clearMatrices()
/*     */   {
/* 307 */     if (this._display != null) {
/* 308 */       this._display.clearMatrices();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 321 */     Object localObject = null;
/* 322 */     int i = 0;
/*     */     
/* 324 */     this._nodeFound = false;
/* 325 */     int j = 0;
/* 326 */     this._activeNode = null;
/* 327 */     j = 0;
/* 328 */     do { HNode localHNode = (HNode)this._nodes.elementAt(j);
/*     */       
/* 330 */       if (HMatrixOps.length(localHNode.getX() - paramMouseEvent.getX(), localHNode.getY() - paramMouseEvent.getY()) <= 15.0F) {
/* 331 */         localHNode.setMousePressed(true);
/* 332 */         this._nodeFound = true;
/* 333 */         this._activeNode = localHNode;
/*     */       } else {
/* 335 */         localHNode.setMousePressed(false);
/*     */       }
/* 327 */       j++; if (j >= this._nodes.size()) break; } while (this._nodeFound == false);
/* 337 */     for (; 
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 337 */         j < this._nodes.size(); j++) {
/* 338 */       ((HNode)this._nodes.elementAt(j)).setMousePressed(false);
/*     */     }
/*     */     
/* 341 */     if (this._palette != null) {
/* 342 */       this._palette.getSelected().toolReleased(paramMouseEvent, this._activeNode);
/* 343 */       if ((this._activeNode != null) && 
/* 344 */         (!this._nodes.contains(this._activeNode))) {
/* 345 */         this._activeNode = null;
/* 346 */         this._nodeFound = false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 351 */     this._needsToBeRedrawn = true;
/* 352 */     clearHighlight();
/* 353 */     setSelects();
/*     */     
/*     */ 
/* 356 */     if (this._display != null) {
/* 357 */       this._display.clearMatrices();
/* 358 */       clearTransformLabels();
/* 359 */       getMatrix();
/* 360 */       if (this._activeNode != null) {
/* 361 */         this._activeNode.reverseTraverse();
/* 362 */         this._activeNode.drawLabels();
/*     */       }
/*     */       
/* 365 */       this._display.repaint();
/*     */     }
/*     */     
/*     */ 
/* 369 */     repaint();
/* 370 */     this._robotContainer.repaint();
/*     */     
/* 372 */     if (!this._nodeFound) {
/* 373 */       this._root.setChooser();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void startAnimation()
/*     */   {
/* 380 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 381 */       ((HNode)this._nodes.elementAt(i)).setAnimating(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void stopAnimation()
/*     */   {
/* 388 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 389 */       ((HNode)this._nodes.elementAt(i)).setAnimating(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public HNode getActiveNode()
/*     */   {
/* 396 */     return this._activeNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addUnlinkedNode(HNode paramHNode)
/*     */   {
/* 403 */     if (!this._unlinkedNodes.contains(paramHNode)) {
/* 404 */       this._unlinkedNodes.addElement(paramHNode);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeUnlinkedNode(HNode paramHNode)
/*     */   {
/* 411 */     this._unlinkedNodes.removeElement(paramHNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearTransformLabels()
/*     */   {
/* 418 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 419 */       if (((HNode)this._nodes.elementAt(i)).getType() == 2) {
/* 420 */         ((HTransformNode)this._nodes.elementAt(i)).clearLabels();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/* 427 */     if (this._palette != null) {
/* 428 */       this._palette.getSelected().toolDragged(paramMouseEvent);
/*     */       
/*     */ 
/* 431 */       if ((this._palette.getSelected().shouldDrawLine()) && (this._activeNode != null)) {
/* 432 */         Graphics localGraphics = this._back.getGraphics();
/* 433 */         localGraphics.drawImage(this._tempback, 0, 0, null);
/* 434 */         localGraphics.setColor(new Color(100, 100, 100));
/* 435 */         localGraphics.drawLine(this._activeNode.getX(), 
/* 436 */           this._activeNode.getY(), paramMouseEvent.getX(), paramMouseEvent.getY());
/*     */       }
/*     */       
/* 439 */       if (this._palette.getSelected().shouldRepaint()) {
/* 440 */         if (this._palette.getSelected().needsToBeRedrawn())
/* 441 */           this._needsToBeRedrawn = true;
/* 442 */         repaint();
/*     */       }
/*     */     }
/*     */     else {
/* 446 */       System.out.println("palette=null");
/*     */     }
/*     */   }
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*     */   
/*     */   public void mouseMoved(MouseEvent paramMouseEvent) {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HTree.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */