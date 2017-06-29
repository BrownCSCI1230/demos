/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.util.Vector;
/*    */ import javax.swing.JComponent;
/*    */ 
/*    */ 
/*    */ public class HTraversalThread
/*    */   extends Thread
/*    */ {
/*    */   private HTree _tree;
/*    */   private Vector _nodes;
/*    */   private Vector _selects;
/*    */   private boolean _moreNodes;
/*    */   private HNode _currentNode;
/*    */   private HNode _nextNode;
/*    */   private HNode _previousNode;
/*    */   private HNode _activeNode;
/*    */   private int _currentIndex;
/*    */   
/*    */   public HTraversalThread(HTree paramHTree)
/*    */   {
/* 22 */     this._tree = paramHTree;
/* 23 */     this._nodes = this._tree.getNodes();
/* 24 */     this._moreNodes = true;
/* 25 */     this._selects = new Vector();
/*    */     
/*    */ 
/*    */ 
/* 29 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 30 */       HNode localHNode = (HNode)this._nodes.elementAt(i);
/* 31 */       this._selects.addElement(new Boolean(localHNode.isSelected()));
/* 32 */       localHNode.setSelected(false);
/*    */     }
/* 34 */     this._activeNode = this._tree.getActiveNode();
/*    */   }
/*    */   
/*    */   public void run()
/*    */   {
/* 39 */     if (this._tree.getDisplay() != null)
/* 40 */       this._tree.getStacker().startMatrixDisplay();
/* 41 */     if (this._tree.getRobotContainer() != null)
/* 42 */       this._tree.getRobotContainer().clearShape();
/* 43 */     this._currentNode = this._tree.getRoot();
/* 44 */     if (this._tree.getDisplay() != null)
/* 45 */       this._currentNode.drawLabels();
/* 46 */     this._tree.startAnimation();
/* 47 */     this._tree.forceRedrawOnPaint();
/* 48 */     this._tree.repaint();
/* 49 */     this._currentNode.animate(this, null, HMatrixOps.getIdentity(3));
/* 50 */     cleanUpTree();
/*    */   }
/*    */   
/*    */   protected void cleanUpTree()
/*    */   {
/* 55 */     for (int i = 0; i < this._nodes.size(); i++) {
/* 56 */       HNode localHNode = (HNode)this._nodes.elementAt(i);
/* 57 */       if (i < this._selects.size()) {
/* 58 */         localHNode.setSelected(((Boolean)this._selects.elementAt(i)).booleanValue());
/*    */       } else
/* 60 */         localHNode.setSelected(false);
/* 61 */       localHNode.clearLineHighlight();
/*    */     }
/* 63 */     this._tree.stopAnimation();
/* 64 */     this._tree.forceRedrawOnPaint();
/* 65 */     this._tree.repaint();
/* 66 */     this._tree.getApplet().disablePause();
/* 67 */     if (this._tree.getStacker() != null) {
/* 68 */       this._tree.getStacker().endMatrixDisplay();
/* 69 */       this._tree.getStacker().clearMatrices();
/* 70 */       if (this._tree.getDisplay() != null) {
/* 71 */         this._tree.clearTransformLabels();
/* 72 */         this._tree.getMatrix();
/* 73 */         if (this._activeNode != null) {
/* 74 */           this._activeNode.reverseTraverse();
/* 75 */           this._activeNode.drawLabels();
/*    */         }
/* 77 */         this._tree.getDisplay().paintComponents(this._tree.getDisplay().getGraphics());
/*    */       }
/* 79 */       this._tree.getStacker().paintComponents(this._tree.getStacker().getGraphics());
/*    */     }
/* 81 */     if (this._tree.getRobotContainer() != null)
/* 82 */       this._tree.getRobotContainer().repaint();
/*    */   }
/*    */   
/*    */   public void interrupt() {
/* 86 */     cleanUpTree();
/* 87 */     super.interrupt();
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HTraversalThread.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */