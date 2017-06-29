/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.event.MouseListener;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class HTool
/*    */   extends JPanel
/*    */   implements MouseListener
/*    */ {
/*    */   private boolean _selected;
/*    */   private HToolPalette _palette;
/*    */   private Color _background;
/*    */   
/*    */   public HTool(HToolPalette paramHToolPalette, Color paramColor)
/*    */   {
/* 25 */     this._selected = false;
/* 26 */     this._background = paramColor;
/* 27 */     this._palette = paramHToolPalette;
/* 28 */     setSize(new Dimension(40, 40));
/* 29 */     setPreferredSize(getSize());
/* 30 */     setMaximumSize(getSize());
/* 31 */     setMinimumSize(getSize());
/* 32 */     addMouseListener(this);
/* 33 */     setBorder(BorderFactory.createRaisedBevelBorder());
/* 34 */     setBackground(this._background);
/*    */   }
/*    */   
/*    */   public void paint(Graphics paramGraphics)
/*    */   {
/* 39 */     if (this._selected) {
/* 40 */       setBorder(BorderFactory.createLoweredBevelBorder());
/* 41 */       setBackground(HColorOps.getLighterColor(this._background));
/*    */     } else {
/* 43 */       setBorder(BorderFactory.createRaisedBevelBorder());
/* 44 */       setBackground(this._background);
/*    */     }
/* 46 */     super.paint(paramGraphics);
/*    */   }
/*    */   
/*    */   public boolean isSelected() {
/* 50 */     return this._selected;
/*    */   }
/*    */   
/*    */   public boolean shouldDrawLine() {
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public boolean shouldRepaint() {
/* 58 */     return false;
/*    */   }
/*    */   
/*    */   public boolean needsToBeRedrawn() {
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public void setSelected(boolean paramBoolean) {
/* 66 */     this._selected = paramBoolean;
/*    */   }
/*    */   
/*    */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 70 */     this._palette.clearSelected();
/* 71 */     this._selected = true;
/* 72 */     this._palette.repaint();
/*    */   }
/*    */   
/*    */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void mouseReleased(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void toolDragged(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void toolPressed(MouseEvent paramMouseEvent, HNode paramHNode) {}
/*    */   
/*    */   public void toolReleased(MouseEvent paramMouseEvent, HNode paramHNode) {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HTool.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */