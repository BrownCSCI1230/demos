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
/*    */ public class HUnlinkTool
/*    */   extends HTool
/*    */ {
/*    */   private Color _background;
/*    */   private HTree _tree;
/*    */   private HNode _firstNode;
/*    */   private HRobotContainer _container;
/*    */   
/*    */   public HUnlinkTool(HToolPalette paramHToolPalette, Color paramColor, HTree paramHTree, HRobotContainer paramHRobotContainer)
/*    */   {
/* 27 */     super(paramHToolPalette, paramColor);
/* 28 */     this._background = paramColor;
/* 29 */     this._tree = paramHTree;
/* 30 */     this._container = paramHRobotContainer;
/* 31 */     setBackground(this._background);
/*    */   }
/*    */   
/*    */   public void paint(Graphics paramGraphics) {
/* 35 */     super.paint(paramGraphics);
/*    */     
/* 37 */     int i = getSize().width - 1;
/* 38 */     int j = getSize().height - 1;
/*    */     
/* 40 */     paramGraphics.setColor(Color.black);
/* 41 */     paramGraphics.drawOval(2, 5, 15, 15);
/*    */     
/* 43 */     paramGraphics.drawOval(23, 5, 15, 15);
/*    */     
/* 45 */     paramGraphics.drawArc(10, 5, 15, 15, 90, 180);
/*    */     
/* 47 */     paramGraphics.drawArc(15, 5, 15, 15, 270, 180);
/* 48 */     paramGraphics.setFont(new Font("SansSerif", 1, 10));
/* 49 */     paramGraphics.drawString("Unlink", i / 2 - 18, j / 2 + 15);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void toolPressed(MouseEvent paramMouseEvent, HNode paramHNode)
/*    */   {
/* 56 */     this._firstNode = paramHNode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void toolReleased(MouseEvent paramMouseEvent, HNode paramHNode)
/*    */   {
/* 63 */     if ((paramHNode != null) && (this._firstNode != null)) {
/* 64 */       if (this._firstNode.removeChild(paramHNode)) {
/* 65 */         paramHNode.removeParent(this._firstNode);
/* 66 */         if ((paramHNode.getType() == 3) && (!paramHNode.hasParent())) {
/* 67 */           ((HShapeNode)paramHNode).removeDrawListener(this._container);
/* 68 */           this._container.removePart(((HShapeNode)paramHNode).getPart());
/*    */         }
/* 70 */         this._tree.addUnlinkedNode(paramHNode);
/*    */       }
/* 72 */       else if (paramHNode.removeChild(this._firstNode)) {
/* 73 */         this._firstNode.removeParent(paramHNode);
/* 74 */         if ((this._firstNode.getType() == 3) && (this._firstNode.hasParent())) {
/* 75 */           ((HShapeNode)this._firstNode).removeDrawListener(this._container);
/* 76 */           this._container.removePart(((HShapeNode)this._firstNode).getPart());
/*    */         }
/* 78 */         this._tree.addUnlinkedNode(this._firstNode);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean shouldDrawLine()
/*    */   {
/* 85 */     return true;
/*    */   }
/*    */   
/*    */   public boolean shouldRepaint() {
/* 89 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HUnlinkTool.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */