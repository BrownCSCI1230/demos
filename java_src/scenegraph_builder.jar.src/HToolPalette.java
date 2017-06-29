/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Image;
/*     */ import java.awt.event.MouseListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class HToolPalette
/*     */   extends JPanel
/*     */ {
/*     */   private HTool _node;
/*     */   private HTool _link;
/*     */   private HTool _unlink;
/*     */   private HTool _move;
/*     */   private HTool _trash;
/*     */   private Color _toolColor;
/*     */   private HTree _tree;
/*     */   private Image _back;
/*     */   private boolean _needsToBeRedrawn;
/*  25 */   public static final Color BACKGROUND = new Color(205, 190, 180);
/*     */   
/*     */   public HToolPalette(HNodeTypeChooser paramHNodeTypeChooser) {
/*  28 */     setLayout(new GridLayout(2, 3));
/*     */     
/*  30 */     setBorder(BorderFactory.createLoweredBevelBorder());
/*     */     
/*  32 */     setSize(new Dimension(124, 84));
/*     */     
/*  34 */     this._needsToBeRedrawn = true;
/*     */     
/*     */ 
/*     */ 
/*  38 */     this._toolColor = BACKGROUND;
/*     */     
/*  40 */     setBackground(this._toolColor);
/*  41 */     this._tree = paramHNodeTypeChooser.getTree();
/*  42 */     this._node = new HNodeTool(this, this._toolColor, paramHNodeTypeChooser);
/*  43 */     this._link = new HLinkTool(this, this._toolColor, this._tree, paramHNodeTypeChooser.getContainer());
/*  44 */     this._unlink = new HUnlinkTool(this, this._toolColor, this._tree, paramHNodeTypeChooser.getContainer());
/*  45 */     this._move = new HMoveTool(this, this._toolColor);
/*  46 */     this._trash = new HDeleteTool(this, this._toolColor, this._tree, paramHNodeTypeChooser.getContainer());
/*     */     
/*  48 */     this._node.setSelected(true);
/*     */     
/*     */ 
/*     */ 
/*  52 */     add(this._move);
/*  53 */     add(this._node);
/*  54 */     add(this._trash);
/*  55 */     add(this._link);
/*  56 */     add(this._unlink);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearSelected()
/*     */   {
/*  63 */     this._node.setSelected(false);
/*  64 */     this._link.setSelected(false);
/*  65 */     this._unlink.setSelected(false);
/*  66 */     this._move.setSelected(false);
/*  67 */     this._trash.setSelected(false);
/*  68 */     this._needsToBeRedrawn = true;
/*     */   }
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/*  72 */     if (this._back == null) {
/*  73 */       this._back = createImage(getSize().width, getSize().height);
/*     */     }
/*  75 */     if (this._back != null) {
/*  76 */       if (this._needsToBeRedrawn) {
/*  77 */         super.paintComponent(this._back.getGraphics());
/*  78 */         this._needsToBeRedrawn = false;
/*     */       }
/*  80 */       paramGraphics.drawImage(this._back, 0, 0, null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HTool getSelected()
/*     */   {
/*  88 */     if (this._node.isSelected()) {
/*  89 */       return this._node;
/*     */     }
/*  91 */     if (this._move.isSelected()) {
/*  92 */       return this._move;
/*     */     }
/*  94 */     if (this._link.isSelected()) {
/*  95 */       return this._link;
/*     */     }
/*  97 */     if (this._unlink.isSelected()) {
/*  98 */       return this._unlink;
/*     */     }
/* 100 */     if (this._trash.isSelected())
/* 101 */       return this._trash;
/* 102 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean shouldRepaint()
/*     */   {
/* 109 */     return this._move.isSelected();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setActiveTool(String paramString)
/*     */   {
/* 120 */     clearSelected();
/* 121 */     if (paramString.equals("Node")) {
/* 122 */       this._node.setSelected(true);
/*     */     }
/* 124 */     if (paramString.equals("Move")) {
/* 125 */       this._move.setSelected(true);
/*     */     }
/* 127 */     if (paramString.equals("Link")) {
/* 128 */       this._link.setSelected(true);
/*     */     }
/* 130 */     if (paramString.equals("Unlink")) {
/* 131 */       this._unlink.setSelected(true);
/*     */     }
/* 133 */     if (paramString.equals("Delete"))
/* 134 */       this._trash.setSelected(true);
/* 135 */     this._needsToBeRedrawn = true;
/* 136 */     repaint();
/*     */   }
/*     */   
/*     */   public HTree getTree() {
/* 140 */     return this._tree;
/*     */   }
/*     */   
/*     */   public void addMouseListener(MouseListener paramMouseListener) {
/* 144 */     this._node.addMouseListener(paramMouseListener);
/* 145 */     this._link.addMouseListener(paramMouseListener);
/* 146 */     this._unlink.addMouseListener(paramMouseListener);
/* 147 */     this._move.addMouseListener(paramMouseListener);
/* 148 */     this._trash.addMouseListener(paramMouseListener);
/* 149 */     super.addMouseListener(paramMouseListener);
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HToolPalette.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */