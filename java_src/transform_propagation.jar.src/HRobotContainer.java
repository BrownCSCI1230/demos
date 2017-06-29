/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HRobotContainer
/*     */   extends JPanel
/*     */   implements ComponentListener, HDrawListener
/*     */ {
/*     */   private Image _back;
/*     */   private Image _background;
/*     */   private HTree _tree;
/*     */   private Vector _parts;
/*  25 */   public static final Color BACKGROUND = new Color(205, 190, 180);
/*     */   
/*     */   private float _xSpacing;
/*     */   
/*     */   private float _ySpacing;
/*     */   
/*     */ 
/*     */   public HRobotContainer(HTree paramHTree)
/*     */   {
/*  34 */     this._tree = paramHTree;
/*  35 */     this._parts = new Vector();
/*  36 */     setBorder(BorderFactory.createLoweredBevelBorder());
/*  37 */     setBackground(BACKGROUND);
/*  38 */     addComponentListener(this);
/*  39 */     this._ySpacing = 20.0F;
/*  40 */     this._xSpacing = 20.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void paintComponent(Graphics paramGraphics)
/*     */   {
/*  47 */     if (this._back == null) {
/*  48 */       this._back = createImage(getSize().width, getSize().height);
/*  49 */       this._background = drawBackground();
/*     */     }
/*     */     
/*  52 */     if (this._back != null) {
/*  53 */       Graphics localGraphics = this._back.getGraphics();
/*  54 */       super.paintComponent(localGraphics);
/*  55 */       localGraphics.drawImage(this._background, 2, 2, this);
/*     */       
/*  57 */       this._tree.setSelects();
/*  58 */       for (int i = 0; i < this._parts.size(); i++)
/*  59 */         ((HRobotPart)this._parts.elementAt(i)).resetIndex();
/*  60 */       this._tree.drawRobot();
/*  61 */       paramGraphics.drawImage(this._back, 0, 0, this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearShape()
/*     */   {
/*  70 */     Graphics localGraphics = this._back.getGraphics();
/*  71 */     super.paint(localGraphics);
/*  72 */     localGraphics.drawImage(this._background, 2, 2, this);
/*  73 */     this._tree.setSelects();
/*  74 */     for (int i = 0; i < this._parts.size(); i++)
/*  75 */       ((HRobotPart)this._parts.elementAt(i)).resetIndex();
/*  76 */     getGraphics().drawImage(this._back, 0, 0, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Image drawBackground()
/*     */   {
/*  83 */     int i = getSize().width - 4;
/*  84 */     int j = getSize().height - 4;
/*  85 */     Image localImage = createImage(i, j);
/*  86 */     Graphics localGraphics = localImage.getGraphics();
/*  87 */     localGraphics.setColor(BACKGROUND);
/*  88 */     localGraphics.fillRect(0, 0, i, j);
/*     */     
/*  90 */     localGraphics.setColor(HColorOps.getDarkerColor(BACKGROUND));
/*  91 */     for (float f1 = i / 2; f1 > 0.0F; f1 -= this._xSpacing) {
/*  92 */       localGraphics.drawLine((int)f1, 0, (int)f1, j);
/*     */     }
/*     */     
/*  95 */     for (float f2 = i / 2; f2 < i; f2 += this._xSpacing) {
/*  96 */       localGraphics.drawLine((int)f2, 0, (int)f2, j);
/*     */     }
/*     */     
/*  99 */     for (float f3 = j / 2; f3 > 0.0F; f3 -= this._ySpacing) {
/* 100 */       localGraphics.drawLine(0, (int)f3, i, (int)f3);
/*     */     }
/*     */     
/* 103 */     for (float f4 = j / 2; f4 < j; f4 += this._ySpacing) {
/* 104 */       localGraphics.drawLine(0, (int)f4, i, (int)f4);
/*     */     }
/*     */     
/* 107 */     localGraphics.setColor(HColorOps.getLighterColor(BACKGROUND));
/* 108 */     localGraphics.drawLine(i / 2, 0, i / 2, j);
/* 109 */     localGraphics.drawLine(0, j / 2, i, j / 2);
/*     */     
/* 111 */     return localImage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void draw(HDrawEvent paramHDrawEvent)
/*     */   {
/* 118 */     Graphics localGraphics = this._back.getGraphics();
/* 119 */     HRobotPart localHRobotPart = paramHDrawEvent.getPart();
/* 120 */     localGraphics.setColor(HColorOps.getDarkerColor(HShapeNode.BACKGROUND));
/* 121 */     Polygon localPolygon = localHRobotPart.getShape();
/* 122 */     localPolygon.translate(getSize().width / 2, getSize().height / 2);
/* 123 */     localGraphics.drawPolygon(localPolygon);
/* 124 */     if (localHRobotPart.isSelected()) {
/* 125 */       localGraphics.fillPolygon(localPolygon);
/*     */     }
/*     */     
/* 128 */     if (paramHDrawEvent.drawCenter()) {
/* 129 */       HPosition localHPosition = paramHDrawEvent.getCenter();
/* 130 */       localGraphics.fillRect((int)(localHPosition.x - 3.0F) + getSize().width / 2, 
/* 131 */         (int)(localHPosition.y - 3.0F) + getSize().height / 2, 6, 6);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawCenter(HDrawEvent paramHDrawEvent)
/*     */   {
/* 139 */     Graphics localGraphics = this._back.getGraphics();
/* 140 */     if (paramHDrawEvent.drawCenter()) {
/* 141 */       localGraphics.setColor(HColorOps.getDarkerColor(HShapeNode.BACKGROUND));
/* 142 */       HPosition localHPosition = paramHDrawEvent.getCenter();
/* 143 */       localGraphics.fillRect((int)(localHPosition.x - 3.0F) + getSize().width / 2, 
/* 144 */         (int)(localHPosition.y - 3.0F) + getSize().height / 2, 6, 6);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawImmediately(HDrawEvent paramHDrawEvent)
/*     */   {
/* 152 */     Graphics localGraphics = this._back.getGraphics();
/* 153 */     HRobotPart localHRobotPart = paramHDrawEvent.getPart();
/* 154 */     localGraphics.setColor(HColorOps.getDarkerColor(HShapeNode.BACKGROUND));
/* 155 */     Polygon localPolygon = localHRobotPart.getShape();
/* 156 */     localPolygon.translate(getSize().width / 2, getSize().height / 2);
/* 157 */     localGraphics.drawPolygon(localPolygon);
/* 158 */     localGraphics.fillPolygon(localPolygon);
/* 159 */     getGraphics().drawImage(this._back, 0, 0, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addPart(HRobotPart paramHRobotPart)
/*     */   {
/* 166 */     if (!this._parts.contains(paramHRobotPart)) {
/* 167 */       this._parts.addElement(paramHRobotPart);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void removePart(HRobotPart paramHRobotPart)
/*     */   {
/* 174 */     this._parts.removeElement(paramHRobotPart);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean containsPart(HRobotPart paramHRobotPart)
/*     */   {
/* 181 */     return this._parts.contains(paramHRobotPart);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector getParts()
/*     */   {
/* 188 */     return this._parts;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void componentResized(ComponentEvent paramComponentEvent)
/*     */   {
/* 195 */     this._back = null;
/* 196 */     this._background = null;
/*     */     
/*     */     try
/*     */     {
/* 200 */       repaint();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HRobotContainer.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */