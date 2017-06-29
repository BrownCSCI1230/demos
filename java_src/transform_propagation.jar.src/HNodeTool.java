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
/*    */ 
/*    */ public class HNodeTool
/*    */   extends HTool
/*    */ {
/*    */   private Color _background;
/*    */   private HNodeTypeChooser _chooser;
/*    */   
/*    */   public HNodeTool(HToolPalette paramHToolPalette, Color paramColor, HNodeTypeChooser paramHNodeTypeChooser)
/*    */   {
/* 25 */     super(paramHToolPalette, paramColor);
/* 26 */     this._background = paramColor;
/* 27 */     this._chooser = paramHNodeTypeChooser;
/* 28 */     setBackground(this._background);
/*    */   }
/*    */   
/*    */   public void paint(Graphics paramGraphics) {
/* 32 */     super.paint(paramGraphics);
/*    */     
/* 34 */     int i = getSize().width - 1;
/* 35 */     int j = getSize().height - 1;
/*    */     
/* 37 */     paramGraphics.setColor(Color.black);
/* 38 */     paramGraphics.drawOval(2, 2, i - 4, 
/* 39 */       i - 4);
/* 40 */     paramGraphics.drawOval(3, 3, i - 6, 
/* 41 */       i - 6);
/* 42 */     paramGraphics.setFont(new Font("SansSerif", 1, 10));
/* 43 */     paramGraphics.drawString("Node", i / 2 - 13, j / 2 + 5);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void toolPressed(MouseEvent paramMouseEvent, HNode paramHNode)
/*    */   {
/* 50 */     HNode localHNode = null;
/* 51 */     if (paramHNode == null) {
/* 52 */       localHNode = this._chooser.getNode();
/* 53 */       localHNode.setPosition(paramMouseEvent.getX(), paramMouseEvent.getY());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HNodeTool.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */