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
/*    */ public class HLinkTool
/*    */   extends HTool
/*    */ {
/*    */   private Color _background;
/*    */   private HNode _firstNode;
/*    */   private HTree _tree;
/*    */   private HRobotContainer _container;
/*    */   
/*    */   public HLinkTool(HToolPalette paramHToolPalette, Color paramColor, HTree paramHTree, HRobotContainer paramHRobotContainer)
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
/* 41 */     paramGraphics.drawOval(2, 3, 18, 18);
/*    */     
/* 43 */     paramGraphics.drawOval(11, 3, 18, 18);
/*    */     
/* 45 */     paramGraphics.drawOval(20, 3, 18, 18);
/* 46 */     paramGraphics.setFont(new Font("SansSerif", 1, 10));
/* 47 */     paramGraphics.drawString("Link", i / 2 - 11, j / 2 + 15);
/*    */   }
/*    */   
/*    */   public void toolPressed(MouseEvent paramMouseEvent, HNode paramHNode) {
/* 51 */     this._firstNode = paramHNode;
/*    */   }
/*    */   
/*    */   public void toolReleased(MouseEvent paramMouseEvent, HNode paramHNode) {
/* 55 */     if ((this._firstNode != null) && (paramHNode != null))
/*    */     {
/* 57 */       if ((this._firstNode.canAddChild(paramHNode)) && (!this._firstNode.checkForParent(paramHNode))) {
/* 58 */         this._firstNode.addChild(paramHNode);
/* 59 */         paramHNode.addParent(this._firstNode);
/* 60 */         this._tree.removeUnlinkedNode(paramHNode);
/* 61 */         if ((paramHNode.getType() == 3) && 
/* 62 */           (!this._container.containsPart(((HShapeNode)paramHNode).getPart()))) {
/* 63 */           this._container.addPart(((HShapeNode)paramHNode).getPart());
/* 64 */           ((HShapeNode)paramHNode).addDrawListener(this._container);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean shouldDrawLine() {
/* 71 */     return true;
/*    */   }
/*    */   
/*    */   public boolean shouldRepaint() {
/* 75 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HLinkTool.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */