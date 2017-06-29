/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Stack;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ public abstract class HNode
/*     */   extends JComponent
/*     */   implements HControllable
/*     */ {
/*     */   private HPosition _pos;
/*     */   private Vector _nodeListeners;
/*     */   private Vector _parents;
/*     */   private int _diameter;
/*     */   private int _descriptorOffset;
/*     */   private boolean _isDrawn;
/*     */   private HTree _tree;
/*     */   private boolean _selected;
/*     */   private boolean _mousePressed;
/*     */   private String _descriptor;
/*     */   private HChooser _chooser;
/*     */   private Vector _matrixListeners;
/*     */   private HNode _lineHighlight;
/*     */   private Font _smallFont;
/*     */   private Font _largeFont;
/*     */   private Color _darkerColor;
/*     */   private Color _lighterColor;
/*     */   private Color _currentColor;
/*     */   
/*     */   public HNode(HTree paramHTree, HChooser paramHChooser)
/*     */   {
/*  37 */     this._selected = false;
/*  38 */     this._mousePressed = false;
/*  39 */     this._chooser = paramHChooser;
/*  40 */     this._tree = paramHTree;
/*  41 */     this._pos = new HPosition();
/*  42 */     this._nodeListeners = new Vector();
/*  43 */     this._parents = new Vector();
/*  44 */     this._matrixListeners = new Vector();
/*  45 */     this._diameter = 30;
/*  46 */     this._isDrawn = false;
/*  47 */     this._descriptor = new String(" ");
/*  48 */     this._descriptorOffset = 0;
/*     */     
/*  50 */     setSize(new Dimension(30, 30));
/*     */     
/*  52 */     this._currentColor = getColor();
/*  53 */     this._lighterColor = HColorOps.getLighterColor(this._currentColor);
/*  54 */     this._darkerColor = HColorOps.getDarkerColor(this._currentColor);
/*     */     
/*  56 */     this._largeFont = new Font("SansSerif", 0, 24);
/*  57 */     this._smallFont = new Font("SansSerif", 1, 16);
/*     */     
/*  59 */     if (this._tree != null) {
/*  60 */       addDrawNodeListener(this._tree);
/*  61 */       this._tree.addNode(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void fireDrawNodeEvent()
/*     */   {
/*  71 */     for (int i = 0; i < this._nodeListeners.size(); i++)
/*     */     {
/*  73 */       ((HDrawNodeListener)this._nodeListeners.elementAt(i)).drawNode(new HDrawNodeEvent(this)); }
/*     */   }
/*     */   
/*     */   protected void fireDrawMatrixEvent(HDrawMatrixEvent paramHDrawMatrixEvent) {
/*  77 */     for (int i = 0; i < this._matrixListeners.size(); i++)
/*  78 */       ((HDrawMatrixListener)this._matrixListeners.elementAt(i)).drawMatrix(paramHDrawMatrixEvent);
/*     */   }
/*     */   
/*     */   protected void fireDrawLineEvent() {
/*  82 */     for (int i = 0; i < this._nodeListeners.size(); i++)
/*     */     {
/*  84 */       ((HDrawNodeListener)this._nodeListeners.elementAt(i)).drawLine(new HDrawNodeEvent(this)); }
/*     */   }
/*     */   
/*     */   protected void firePushMatrixEvent(HMatrixObject paramHMatrixObject) {
/*  88 */     for (int i = 0; i < this._matrixListeners.size(); i++)
/*  89 */       ((HDrawMatrixListener)this._matrixListeners.elementAt(i)).pushMatrix(paramHMatrixObject);
/*     */   }
/*     */   
/*     */   protected void firePopMatrixEvent() {
/*  93 */     for (int i = 0; i < this._matrixListeners.size(); i++) {
/*  94 */       ((HDrawMatrixListener)this._matrixListeners.elementAt(i)).popMatrix();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector getParents()
/*     */   {
/* 188 */     Vector localVector = new Vector();
/* 189 */     for (int i = 0; i < this._parents.size(); i++) {
/* 190 */       localVector.addElement(this._parents.elementAt(i));
/*     */     }
/* 192 */     return localVector;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void animateAction(Thread paramThread, HNode paramHNode)
/*     */   {
/* 199 */     setSelected(true);
/* 200 */     setLineHighlight(paramHNode);
/* 201 */     this._tree.forceRedrawOnPaint();
/* 202 */     this._tree.repaint();
/* 203 */     this._tree.setSelects();
/*     */     try {
/* 205 */       Thread.yield();
/* 206 */       Thread.sleep(500L);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 209 */       paramThread.stop();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void finishAnimateAction(Thread paramThread)
/*     */   {
/*     */     try
/*     */     {
/* 222 */       Thread.yield();
/* 223 */       Thread.sleep(500L);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 226 */       paramThread.stop();
/*     */     }
/* 228 */     setSelected(false);
/* 229 */     clearLineHighlight();
/* 230 */     this._tree.forceRedrawOnPaint();
/* 231 */     this._tree.repaint();
/* 232 */     this._tree.setSelects();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBeDeleted()
/*     */   {
/* 245 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDescriptor(String paramString)
/*     */   {
/* 252 */     this._descriptor = paramString;
/* 253 */     Graphics localGraphics = getGraphics();
/* 254 */     localGraphics.setFont(this._smallFont);
/* 255 */     this._descriptorOffset = (localGraphics.getFontMetrics().stringWidth(this._descriptor) / 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean checkForParent(HNode paramHNode)
/*     */   {
/* 262 */     if ((this._parents.contains(paramHNode)) || (this == paramHNode)) {
/* 263 */       return true;
/*     */     }
/*     */     
/* 266 */     for (int i = 0; i < this._parents.size(); i++) {
/* 267 */       boolean bool = ((HNode)this._parents.elementAt(i)).checkForParent(paramHNode);
/* 268 */       if (bool)
/* 269 */         return true;
/*     */     }
/* 271 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDescriptor()
/*     */   {
/* 278 */     return this._descriptor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HNode getParent(int paramInt)
/*     */   {
/* 285 */     return (HNode)this._parents.elementAt(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getNumParents()
/*     */   {
/* 292 */     return this._parents.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeParent(HNode paramHNode)
/*     */   {
/* 299 */     this._parents.removeElement(paramHNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addParent(HNode paramHNode)
/*     */   {
/* 306 */     this._parents.addElement(paramHNode);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasParent()
/*     */   {
/* 313 */     if (this._parents.size() > 0)
/* 314 */       return true;
/* 315 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addDrawNodeListener(HDrawNodeListener paramHDrawNodeListener)
/*     */   {
/* 322 */     this._nodeListeners.addElement(paramHDrawNodeListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeDrawNodeListener(HDrawNodeListener paramHDrawNodeListener)
/*     */   {
/* 329 */     this._nodeListeners.removeElement(paramHDrawNodeListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addDrawMatrixListener(HDrawMatrixListener paramHDrawMatrixListener)
/*     */   {
/* 336 */     this._matrixListeners.addElement(paramHDrawMatrixListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeDrawMatrixListener(HDrawMatrixListener paramHDrawMatrixListener)
/*     */   {
/* 343 */     this._matrixListeners.removeElement(paramHDrawMatrixListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPosition(float paramFloat1, float paramFloat2)
/*     */   {
/* 350 */     this._pos.x = paramFloat1;
/* 351 */     this._pos.y = paramFloat2;
/* 352 */     setLocation((int)paramFloat1 - 15, (int)paramFloat2 - 15);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPosition(HPosition paramHPosition)
/*     */   {
/* 359 */     setPosition(paramHPosition.x, paramHPosition.y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HPosition getPosition()
/*     */   {
/* 366 */     return this._pos;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/* 373 */     return (int)this._pos.x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getY()
/*     */   {
/* 380 */     return (int)this._pos.y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLineHighlight(HNode paramHNode)
/*     */   {
/* 387 */     this._lineHighlight = paramHNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearLineHighlight()
/*     */   {
/* 394 */     this._lineHighlight = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawThisLine(Graphics paramGraphics)
/*     */   {
/* 401 */     Color localColor = HColorOps.getDarkerColor(getColor());
/*     */     
/*     */ 
/*     */ 
/* 405 */     for (int i = 0; i < this._parents.size(); i++) {
/* 406 */       paramGraphics.setColor(localColor);
/* 407 */       HNode localHNode = (HNode)this._parents.elementAt(i);
/*     */       
/* 409 */       paramGraphics.drawLine((int)this._pos.x, (int)this._pos.y, 
/* 410 */         localHNode.getX(), localHNode.getY());
/* 411 */       if (localHNode == this._lineHighlight) {
/* 412 */         paramGraphics.setColor(new Color(255, 255, 255));
/* 413 */         paramGraphics.drawLine((int)this._pos.x - 1, (int)this._pos.y - 1, 
/* 414 */           localHNode.getX() - 1, localHNode.getY() - 1);
/* 415 */         paramGraphics.drawLine((int)this._pos.x + 1, (int)this._pos.y + 1, 
/* 416 */           localHNode.getX() + 1, localHNode.getY() + 1);
/* 417 */         paramGraphics.drawLine((int)this._pos.x, (int)this._pos.y, 
/* 418 */           localHNode.getX(), localHNode.getY());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawThisNode(Graphics paramGraphics)
/*     */   {
/* 430 */     int i = 0;
/* 431 */     if (this._selected) {
/* 432 */       i = 45;
/*     */     } else {
/* 434 */       i = 225;
/*     */     }
/*     */     
/* 437 */     int j = (int)(this._pos.x - this._diameter / 2);
/* 438 */     int k = (int)(this._pos.y - this._diameter / 2);
/*     */     
/* 440 */     paramGraphics.setColor(this._currentColor);
/* 441 */     paramGraphics.fillOval(j, k, 
/* 442 */       this._diameter, this._diameter);
/*     */     
/*     */ 
/* 445 */     paramGraphics.setColor(this._lighterColor);
/* 446 */     paramGraphics.drawOval(j, k, 
/* 447 */       this._diameter, this._diameter);
/* 448 */     paramGraphics.drawOval(j + 1, k + 1, 
/* 449 */       this._diameter - 2, this._diameter - 2);
/*     */     
/*     */ 
/* 452 */     paramGraphics.setColor(this._darkerColor);
/* 453 */     paramGraphics.drawArc(j, k, 
/* 454 */       this._diameter, this._diameter, i, 180);
/* 455 */     paramGraphics.drawArc(j + 1, k + 1, 
/* 456 */       this._diameter - 2, this._diameter - 2, i, 180);
/*     */     
/*     */ 
/* 459 */     paramGraphics.setColor(Color.darkGray);
/* 460 */     paramGraphics.setFont(this._largeFont);
/* 461 */     paramGraphics.drawString(getString(), (int)(this._pos.x - 8.0F), (int)(this._pos.y + 8.0F));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 466 */     paramGraphics.setColor(this._darkerColor);
/* 467 */     paramGraphics.setFont(this._smallFont);
/* 468 */     paramGraphics.drawString(this._descriptor, (int)this._pos.x - this._descriptorOffset, (int)this._pos.y + 29);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDrawn()
/*     */   {
/* 476 */     return this._isDrawn;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDrawn(boolean paramBoolean)
/*     */   {
/* 483 */     this._isDrawn = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSelected(boolean paramBoolean)
/*     */   {
/* 490 */     this._selected = paramBoolean;
/* 491 */     this._currentColor = getColor();
/* 492 */     this._lighterColor = HColorOps.getLighterColor(this._currentColor);
/* 493 */     this._darkerColor = HColorOps.getDarkerColor(this._currentColor);
/*     */     
/*     */ 
/*     */ 
/* 497 */     if (this._selected) {
/* 498 */       this._currentColor = this._lighterColor;
/* 499 */       this._lighterColor = HColorOps.getLighterColor(this._currentColor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isSelected()
/*     */   {
/* 507 */     return this._selected;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMousePressed(boolean paramBoolean)
/*     */   {
/* 514 */     setSelected(paramBoolean);
/* 515 */     this._mousePressed = paramBoolean;
/* 516 */     if (paramBoolean) {
/* 517 */       this._chooser.setActive(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setChooser()
/*     */   {
/* 525 */     this._chooser.setActive(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean mousePressed()
/*     */   {
/* 532 */     return this._mousePressed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 538 */   public boolean translateActive() { return false; }
/* 539 */   public boolean rotateActive() { return false; }
/* 540 */   public boolean scaleActive() { return false; }
/*     */   
/* 542 */   public HPosition getTransform() { return new HPosition(0.0F, 0.0F); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reverseTraverse()
/*     */   {
/* 549 */     for (int i = 0; i < this._parents.size(); i++) {
/* 550 */       ((HNode)this._parents.elementAt(i)).reverseTraverse();
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract void addChild(HNode paramHNode);
/*     */   
/*     */   public void addMatrixChangeListener(HMatrixChangeListener paramHMatrixChangeListener) {}
/*     */   
/*     */   public abstract void animate(Thread paramThread, HNode paramHNode, float[][] paramArrayOfFloat);
/*     */   
/*     */   public abstract boolean canAddChild(HNode paramHNode);
/*     */   
/*     */   public abstract void drawLabels();
/*     */   
/*     */   public abstract void drawLines();
/*     */   
/*     */   public abstract void drawTree();
/*     */   
/*     */   public abstract HNode getChild(int paramInt);
/*     */   
/*     */   public abstract Vector getChildren();
/*     */   
/*     */   public abstract Color getColor();
/*     */   
/*     */   public abstract void getMatrix(Stack paramStack, boolean paramBoolean, float[][] paramArrayOfFloat, int paramInt, String paramString);
/*     */   
/*     */   public abstract String getString();
/*     */   
/*     */   public abstract int getType();
/*     */   
/*     */   public abstract boolean hasChild();
/*     */   
/*     */   public abstract boolean hasChild(int paramInt);
/*     */   
/*     */   public abstract boolean removeChild(HNode paramHNode);
/*     */   
/*     */   public void removeMatrixChangeListener(HMatrixChangeListener paramHMatrixChangeListener) {}
/*     */   
/*     */   public abstract void render(float[][] paramArrayOfFloat, boolean paramBoolean, HPosition paramHPosition);
/*     */   
/*     */   public void setAnimating(boolean paramBoolean) {}
/*     */   
/*     */   public abstract void setSelects(boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public void updateTransform(HPosition paramHPosition) {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HNode.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */