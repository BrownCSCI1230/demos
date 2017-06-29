/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.awt.event.ComponentEvent;
/*    */ import java.awt.event.ComponentListener;
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.event.MouseListener;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HNodePanel
/*    */   extends JPanel
/*    */   implements MouseListener, ComponentListener
/*    */ {
/*    */   private HNode _node;
/*    */   private boolean _selected;
/*    */   private boolean _needsToBeRedrawn;
/*    */   private HNodeTypeChooser _chooser;
/*    */   private Image _back;
/* 26 */   public static final Color BACKGROUND = new Color(205, 190, 180);
/*    */   
/*    */   public HNodePanel(HNode paramHNode, HNodeTypeChooser paramHNodeTypeChooser) {
/* 29 */     this._node = paramHNode;
/* 30 */     this._chooser = paramHNodeTypeChooser;
/* 31 */     this._node.setPosition(16.0F, 16.0F);
/* 32 */     this._selected = false;
/* 33 */     this._needsToBeRedrawn = true;
/*    */     
/* 35 */     setBackground(BACKGROUND);
/* 36 */     addMouseListener(this);
/* 37 */     addComponentListener(this);
/*    */   }
/*    */   
/*    */   public void paintComponent(Graphics paramGraphics) {
/* 41 */     if ((this._back == null) && 
/* 42 */       (getWidth() > 0) && (getHeight() > 0)) {
/* 43 */       this._back = createImage(getWidth(), getHeight());
/* 44 */       this._needsToBeRedrawn = true;
/*    */     }
/*    */     
/* 47 */     if (this._back != null) {
/* 48 */       if (this._needsToBeRedrawn) {
/* 49 */         Graphics localGraphics = this._back.getGraphics();
/* 50 */         super.paintComponent(localGraphics);
/* 51 */         Dimension localDimension = getSize();
/* 52 */         this._node.setSelected(this._selected);
/*    */         
/*    */ 
/* 55 */         this._node.setPosition(localDimension.width / 2, 20.0F);
/* 56 */         this._node.drawThisNode(localGraphics);
/* 57 */         this._needsToBeRedrawn = false;
/*    */       }
/* 59 */       paramGraphics.drawImage(this._back, 0, 0, null);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setSelected(boolean paramBoolean)
/*    */   {
/* 67 */     this._selected = paramBoolean;
/* 68 */     this._needsToBeRedrawn = true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isSelected()
/*    */   {
/* 75 */     return this._selected;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void mousePressed(MouseEvent paramMouseEvent)
/*    */   {
/* 82 */     this._chooser.clearSelected();
/* 83 */     this._selected = true;
/* 84 */     this._chooser.repaint();
/* 85 */     this._needsToBeRedrawn = true;
/*    */   }
/*    */   
/*    */   public void componentResized(ComponentEvent paramComponentEvent) {
/* 89 */     if ((getWidth() > 0) && (getHeight() > 0)) {
/* 90 */       this._back = createImage(getWidth(), getHeight());
/* 91 */       this._needsToBeRedrawn = true;
/*    */     } else {
/* 93 */       this._back = null;
/*    */     }
/* 95 */     try { repaint();
/*    */     }
/*    */     catch (Exception localException) {}
/*    */   }
/*    */   
/*    */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*    */   
/*    */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*    */   
/*    */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*    */   
/*    */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*    */   
/*    */   public void mouseReleased(MouseEvent paramMouseEvent) {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HNodePanel.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */