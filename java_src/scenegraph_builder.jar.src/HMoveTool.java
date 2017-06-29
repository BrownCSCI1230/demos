/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HMoveTool
/*    */   extends HTool
/*    */ {
/*    */   private Color _background;
/*    */   private HNode _activeNode;
/*    */   private boolean _haveNode;
/*    */   private HTree _tree;
/*    */   
/*    */   public HMoveTool(HToolPalette paramHToolPalette, Color paramColor)
/*    */   {
/* 26 */     super(paramHToolPalette, paramColor);
/* 27 */     this._background = paramColor;
/* 28 */     setBackground(this._background);
/* 29 */     this._haveNode = false;
/* 30 */     this._tree = paramHToolPalette.getTree();
/*    */   }
/*    */   
/*    */   public void paint(Graphics paramGraphics) {
/* 34 */     super.paint(paramGraphics);
/*    */     
/* 36 */     int i = getSize().width - 1;
/* 37 */     int j = getSize().height - 1;
/*    */     
/* 39 */     paramGraphics.setColor(Color.black);
/* 40 */     paramGraphics.drawOval(2, 7, 13, 13);
/*    */     
/* 42 */     paramGraphics.drawOval(25, 7, 13, 13);
/*    */     
/* 44 */     paramGraphics.drawLine(17, 14, 23, 14);
/* 45 */     paramGraphics.drawLine(23, 14, 20, 10);
/* 46 */     paramGraphics.drawLine(23, 14, 20, 18);
/*    */     
/* 48 */     paramGraphics.setFont(new Font("SansSerif", 1, 10));
/* 49 */     paramGraphics.drawString("Move", i / 2 - 15, j / 2 + 15);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void toolPressed(MouseEvent paramMouseEvent, HNode paramHNode)
/*    */   {
/* 56 */     if (paramHNode != null) {
/* 57 */       this._activeNode = paramHNode;
/* 58 */       this._haveNode = true;
/*    */     } else {
/* 60 */       this._haveNode = false;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void toolDragged(MouseEvent paramMouseEvent)
/*    */   {
/* 67 */     if (this._haveNode) {
/* 68 */       int i = paramMouseEvent.getX();
/* 69 */       int j = paramMouseEvent.getY();
/* 70 */       int k = this._tree.getSize().width;
/* 71 */       int m = this._tree.getSize().height;
/* 72 */       if (i < 0)
/* 73 */         i = 0;
/* 74 */       if (i > k)
/* 75 */         i = k;
/* 76 */       if (j < 0)
/* 77 */         j = 0;
/* 78 */       if (j > m)
/* 79 */         j = m;
/* 80 */       this._activeNode.setPosition(i, j);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean shouldRepaint() {
/* 85 */     return true;
/*    */   }
/*    */   
/*    */   public boolean needsToBeRedrawn() {
/* 89 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HMoveTool.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */