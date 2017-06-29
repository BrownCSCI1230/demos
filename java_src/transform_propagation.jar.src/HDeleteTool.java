/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HDeleteTool
/*     */   extends HTool
/*     */ {
/*     */   private Color _background;
/*     */   private HTree _tree;
/*     */   private HRobotContainer _container;
/*     */   
/*     */   public HDeleteTool(HToolPalette paramHToolPalette, Color paramColor, HTree paramHTree, HRobotContainer paramHRobotContainer)
/*     */   {
/*  26 */     super(paramHToolPalette, paramColor);
/*  27 */     this._background = paramColor;
/*  28 */     this._tree = paramHTree;
/*  29 */     this._container = paramHRobotContainer;
/*  30 */     setBackground(this._background);
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*  34 */     super.paint(paramGraphics);
/*     */     
/*  36 */     int i = getSize().width - 1;
/*  37 */     int j = getSize().height - 1;
/*     */     
/*  39 */     int k = 0;
/*  40 */     int m = -3;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  45 */     paramGraphics.setColor(Color.lightGray);
/*  46 */     paramGraphics.fillOval(k + 10, m + 25, 20, 5);
/*     */     
/*  48 */     paramGraphics.setColor(Color.black);
/*  49 */     paramGraphics.drawOval(k + 10, m + 25, 20, 5);
/*     */     
/*     */ 
/*  52 */     paramGraphics.setColor(Color.lightGray);
/*  53 */     paramGraphics.fillRect(k + 10, m + 15, 20, 12);
/*     */     
/*  55 */     paramGraphics.setColor(Color.black);
/*  56 */     paramGraphics.drawLine(k + 10, m + 15, k + 10, m + 27);
/*  57 */     paramGraphics.drawLine(k + 30, m + 15, k + 30, m + 27);
/*     */     
/*     */ 
/*  60 */     paramGraphics.setColor(Color.lightGray);
/*  61 */     paramGraphics.fillOval(k + 8, m + 10, 24, 5);
/*  62 */     paramGraphics.fillRect(k + 8, m + 9, 24, 3);
/*  63 */     paramGraphics.fillOval(k + 8, m + 6, 24, 5);
/*     */     
/*     */ 
/*  66 */     paramGraphics.setColor(Color.black);
/*  67 */     paramGraphics.drawArc(k + 8, m + 10, 24, 5, 180, 180);
/*  68 */     paramGraphics.drawLine(k + 8, m + 9, k + 8, m + 12);
/*  69 */     paramGraphics.drawLine(k + 32, m + 9, k + 32, m + 12);
/*  70 */     paramGraphics.drawOval(k + 8, m + 6, 24, 5);
/*     */     
/*     */ 
/*  73 */     paramGraphics.drawLine(k + 14, m + 17, k + 14, m + 27);
/*  74 */     paramGraphics.drawLine(k + 20, m + 17, k + 20, m + 27);
/*  75 */     paramGraphics.drawLine(k + 26, m + 17, k + 26, m + 27);
/*     */     
/*  77 */     paramGraphics.setColor(Color.black);
/*  78 */     paramGraphics.setFont(new Font("SansSerif", 1, 10));
/*  79 */     paramGraphics.drawString("Trash", i / 2 - 15, j - 2);
/*     */   }
/*     */   
/*     */   public void toolReleased(MouseEvent paramMouseEvent, HNode paramHNode) {
/*  83 */     if (paramHNode != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  89 */       if (paramHNode.canBeDeleted()) {
/*  90 */         this._tree.removeNode(paramHNode);
/*  91 */         System.out.println("deleting node");
/*  92 */         this._tree.removeUnlinkedNode(paramHNode);
/*     */         
/*  94 */         Vector localVector1 = paramHNode.getParents();
/*     */         HNode localHNode;
/*  96 */         for (int i = 0; i < localVector1.size(); i++) {
/*  97 */           localHNode = (HNode)localVector1.elementAt(i);
/*  98 */           localHNode.removeChild(paramHNode);
/*  99 */           paramHNode.removeParent(localHNode);
/*     */         }
/*     */         
/* 102 */         Vector localVector2 = paramHNode.getChildren();
/* 103 */         for (int j = 0; j < localVector2.size(); j++) {
/* 104 */           localHNode = (HNode)localVector2.elementAt(j);
/* 105 */           localHNode.removeParent(paramHNode);
/* 106 */           paramHNode.removeChild(localHNode);
/*     */         }
/* 108 */         this._tree.forceRedrawOnPaint();
/* 109 */         this._tree.repaint();
/*     */         
/*     */ 
/* 112 */         if (paramHNode.getType() == 3) {
/* 113 */           this._container.removePart(((HShapeNode)paramHNode).getPart());
/* 114 */           ((HShapeNode)paramHNode).removeDrawListener(this._container);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HDeleteTool.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */