/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HMatrixDisplay
/*     */   extends JScrollPane
/*     */   implements HDrawMatrixListener
/*     */ {
/*     */   private JPanel _display;
/*     */   private Vector _nodesUpdating;
/*     */   private Vector _componentsUpdating;
/*  25 */   public static final Color BACKGROUND = new Color(205, 190, 180);
/*     */   
/*     */   public HMatrixDisplay() {
/*  28 */     super(22, 
/*  29 */       32);
/*  30 */     this._display = new JPanel(false);
/*  31 */     setViewportView(this._display);
/*  32 */     this._display.setBackground(BACKGROUND);
/*  33 */     this._display.setLayout(new BoxLayout(this._display, 1));
/*  34 */     this._nodesUpdating = new Vector();
/*  35 */     this._componentsUpdating = new Vector();
/*     */     
/*  37 */     setBackground(BACKGROUND);
/*  38 */     setBorder(BorderFactory.createLoweredBevelBorder());
/*  39 */     setPreferredSize(getSize());
/*     */     
/*  41 */     adjustScrollBars();
/*     */     
/*  43 */     revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void drawMatrix(HDrawMatrixEvent paramHDrawMatrixEvent)
/*     */   {
/*  52 */     JPanel localJPanel = new JPanel();
/*  53 */     localJPanel.setLayout(new BoxLayout(localJPanel, 0));
/*  54 */     localJPanel.setAlignmentX(0.0F);
/*  55 */     localJPanel.setBackground(BACKGROUND);
/*     */     
/*     */ 
/*  58 */     Vector localVector = paramHDrawMatrixEvent.getMatrices();
/*     */     
/*     */     Color localColor;
/*     */     
/*     */     Object localObject;
/*  63 */     if (localVector.size() > 0)
/*     */     {
/*     */ 
/*     */ 
/*  67 */       localColor = HColorOps.getLighterColor(HShapeNode.BACKGROUND);
/*  68 */       localObject = (HMatrixObject)localVector.elementAt(0);
/*     */       
/*     */ 
/*  71 */       HMatrixComponent localHMatrixComponent = new HMatrixComponent(
/*  72 */         ((HMatrixObject)localObject).getMatrix(), localColor, ((HMatrixObject)localObject).getLabel(), ((HMatrixObject)localObject).getValues());
/*     */       
/*     */ 
/*  75 */       ((HMatrixObject)localObject).getNode().addMatrixChangeListener(localHMatrixComponent);
/*  76 */       this._nodesUpdating.addElement(((HMatrixObject)localObject).getNode());
/*  77 */       this._componentsUpdating.addElement(localHMatrixComponent);
/*     */       
/*     */ 
/*  80 */       localJPanel.add(localHMatrixComponent);
/*  81 */       localJPanel.add(Box.createRigidArea(new Dimension(3, 0)));
/*     */       
/*  83 */       localJPanel.add(HMatrixComponent.getEquals(new Color(200, 200, 100)));
/*     */       
/*  85 */       localJPanel.add(Box.createRigidArea(new Dimension(3, 0)));
/*     */       
/*     */ 
/*  88 */       localVector.removeElementAt(0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  93 */     for (int i = 0; i < localVector.size(); i++) {
/*  94 */       HMatrixObject localHMatrixObject = (HMatrixObject)localVector.elementAt(i);
/*     */       
/*     */ 
/*  97 */       if (localHMatrixObject.getHighlight()) {
/*  98 */         localColor = HColorOps.getLighterColor(HTransformNode.BACKGROUND);
/*     */       } else {
/* 100 */         localColor = BACKGROUND;
/*     */       }
/*     */       
/* 103 */       localObject = new HMatrixComponent(localHMatrixObject.getMatrix(), localColor, 
/* 104 */         localHMatrixObject.getLabel(), 
/* 105 */         localHMatrixObject.getValues());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 110 */       if (localHMatrixObject.getHighlight()) {
/* 111 */         localHMatrixObject.getNode().addMatrixChangeListener((HMatrixChangeListener)localObject);
/* 112 */         this._nodesUpdating.addElement(localHMatrixObject.getNode());
/* 113 */         this._componentsUpdating.addElement(localObject);
/*     */       }
/*     */       
/*     */ 
/* 117 */       localJPanel.add((Component)localObject);
/* 118 */       localJPanel.add(Box.createRigidArea(new Dimension(3, 0)));
/*     */     }
/*     */     
/*     */ 
/* 122 */     this._display.add(localJPanel);
/* 123 */     this._display.add(Box.createRigidArea(new Dimension(0, 5)));
/* 124 */     revalidate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearMatrices()
/*     */   {
/* 133 */     for (int i = 0; i < this._nodesUpdating.size(); i++) {
/* 134 */       HNode localHNode = (HNode)this._nodesUpdating.elementAt(i);
/* 135 */       HMatrixComponent localHMatrixComponent = (HMatrixComponent)this._componentsUpdating.elementAt(i);
/* 136 */       localHNode.removeMatrixChangeListener(localHMatrixComponent);
/*     */     }
/* 138 */     this._display.removeAll();
/* 139 */     this._display.add(Box.createRigidArea(new Dimension(0, 5)));
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
/*     */   protected void adjustScrollBars()
/*     */   {
/* 162 */     JScrollBar localJScrollBar1 = getHorizontalScrollBar();
/* 163 */     JScrollBar localJScrollBar2 = getVerticalScrollBar();
/*     */     
/* 165 */     localJScrollBar1.setUnitIncrement(5);
/* 166 */     localJScrollBar2.setUnitIncrement(10);
/*     */     
/* 168 */     localJScrollBar2.setBlockIncrement(1);
/* 169 */     localJScrollBar1.setBlockIncrement(138);
/*     */   }
/*     */   
/*     */   public void endMatrixDisplay() {}
/*     */   
/*     */   public void popMatrix() {}
/*     */   
/*     */   public void pushMatrix(HMatrixObject paramHMatrixObject) {}
/*     */   
/*     */   public void startMatrixDisplay() {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HMatrixDisplay.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */