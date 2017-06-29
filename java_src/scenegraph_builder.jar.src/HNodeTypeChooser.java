/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class HNodeTypeChooser extends JPanel implements java.awt.event.ActionListener
/*     */ {
/*     */   private HNode _shape;
/*     */   private HNode _transform;
/*     */   private HNode _group;
/*     */   private JComboBox _shapeType;
/*     */   private JComboBox _transformType;
/*     */   private HTree _tree;
/*     */   private HChooser _chooser;
/*     */   private HNodePanel _shapeNodePanel;
/*     */   private HNodePanel _transformNodePanel;
/*     */   private HNodePanel _groupNodePanel;
/*     */   private JPanel _shapePanel;
/*     */   private JPanel _transformPanel;
/*     */   private JPanel _groupPanel;
/*     */   private String[] _transformTypes;
/*     */   private String[] _shapeTypes;
/*     */   private HRobotContainer _container;
/*     */   private HToolPalette _palette;
/*     */   private JLabel _transformLabel;
/*     */   private JLabel _rotateLabel;
/*     */   private JLabel _groupLabel;
/*     */   
/*     */   public HNodeTypeChooser(HTree paramHTree, HChooser paramHChooser, HRobotContainer paramHRobotContainer)
/*     */   {
/*  34 */     this._tree = paramHTree;
/*  35 */     this._chooser = paramHChooser;
/*  36 */     this._container = paramHRobotContainer;
/*     */     
/*  38 */     GridBagLayout localGridBagLayout = new GridBagLayout();
/*  39 */     java.awt.GridBagConstraints localGridBagConstraints = new java.awt.GridBagConstraints();
/*     */     
/*  41 */     setLayout(localGridBagLayout);
/*  42 */     setSize(new java.awt.Dimension(0, 84));
/*  43 */     setMinimumSize(getSize());
/*  44 */     setPreferredSize(new java.awt.Dimension(0, 84));
/*  45 */     setMaximumSize(new java.awt.Dimension(32767, 84));
/*     */     
/*     */ 
/*  48 */     this._transformTypes = new String[3];
/*  49 */     this._transformTypes[0] = "Translate";
/*  50 */     this._transformTypes[1] = "Rotate";
/*  51 */     this._transformTypes[2] = "Scale";
/*     */     
/*     */ 
/*  54 */     this._shapeTypes = new String[5];
/*  55 */     this._shapeTypes[0] = "Square";
/*  56 */     this._shapeTypes[1] = "Rectangle";
/*  57 */     this._shapeTypes[2] = "Circle";
/*  58 */     this._shapeTypes[3] = "Triangle";
/*  59 */     this._shapeTypes[4] = "House";
/*     */     
/*     */ 
/*     */ 
/*  63 */     JPanel localJPanel1 = new JPanel();
/*  64 */     localJPanel1.setLayout(new javax.swing.BoxLayout(localJPanel1, 1));
/*     */     
/*     */ 
/*  67 */     this._shapePanel = new JPanel();
/*  68 */     this._shapeType = new JComboBox(this._shapeTypes);
/*  69 */     this._shapeType.setSelectedIndex(0);
/*  70 */     this._shapeType.setAlignmentY(0.0F);
/*  71 */     this._shapePanel.setLayout(new javax.swing.BoxLayout(this._shapePanel, 0));
/*  72 */     this._shape = new HShapeNode(null, null, null);
/*  73 */     this._shapeNodePanel = new HNodePanel(this._shape, this);
/*  74 */     this._shapePanel.setBackground(this._shapeNodePanel.getBackground());
/*  75 */     this._shapeType.setEditable(false);
/*  76 */     localJPanel1.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
/*  77 */     this._shapePanel.add(this._shapeNodePanel);
/*  78 */     this._shapePanel.add(this._shapeType);
/*     */     
/*  80 */     localJPanel1.setBackground(this._shapeNodePanel.getBackground());
/*     */     
/*     */ 
/*  83 */     JLabel localJLabel1 = new JLabel("Shape Node");
/*  84 */     localJLabel1.setFont(new java.awt.Font("SansSerif", 1, 16));
/*  85 */     localJLabel1.setForeground(java.awt.Color.black);
/*  86 */     localJLabel1.setAlignmentY(0.5F);
/*  87 */     localJLabel1.setAlignmentX(0.5F);
/*  88 */     localJLabel1.setBackground(this._shapeNodePanel.getBackground());
/*  89 */     localJPanel1.add(localJLabel1);
/*  90 */     localJPanel1.add(this._shapePanel);
/*     */     
/*     */ 
/*  93 */     JPanel localJPanel2 = new JPanel();
/*  94 */     localJPanel2.setLayout(new javax.swing.BoxLayout(localJPanel2, 1));
/*     */     
/*  96 */     this._transformPanel = new JPanel();
/*  97 */     this._transformType = new JComboBox(this._transformTypes);
/*  98 */     this._transformType.setAlignmentY(0.0F);
/*  99 */     this._transformType.setSelectedIndex(0);
/* 100 */     this._transformPanel.setLayout(new javax.swing.BoxLayout(this._transformPanel, 0));
/* 101 */     this._transform = new HTransformNode(null, null);
/* 102 */     this._transformNodePanel = new HNodePanel(this._transform, this);
/* 103 */     this._transformPanel.setBackground(this._transformNodePanel.getBackground());
/* 104 */     localJPanel2.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
/* 105 */     this._transformPanel.add(this._transformNodePanel);
/* 106 */     this._transformPanel.add(this._transformType);
/*     */     
/* 108 */     localJPanel2.setBackground(this._transformNodePanel.getBackground());
/*     */     
/* 110 */     JLabel localJLabel2 = new JLabel("Transform Node");
/* 111 */     localJLabel2.setFont(new java.awt.Font("SansSerif", 1, 16));
/* 112 */     localJLabel2.setForeground(java.awt.Color.black);
/* 113 */     localJLabel2.setAlignmentY(0.5F);
/* 114 */     localJLabel2.setAlignmentX(0.5F);
/* 115 */     localJLabel2.setBackground(this._transformNodePanel.getBackground());
/* 116 */     localJPanel2.add(localJLabel2);
/* 117 */     localJPanel2.add(this._transformPanel);
/*     */     
/* 119 */     JPanel localJPanel3 = new JPanel();
/* 120 */     localJPanel3.setLayout(new javax.swing.BoxLayout(localJPanel3, 1));
/*     */     
/*     */ 
/* 123 */     JLabel localJLabel3 = new JLabel("Group Node");
/* 124 */     localJLabel3.setFont(new java.awt.Font("SansSerif", 1, 16));
/* 125 */     localJLabel3.setForeground(java.awt.Color.black);
/* 126 */     localJLabel3.setAlignmentY(0.5F);
/* 127 */     localJLabel3.setAlignmentX(0.5F);
/*     */     
/* 129 */     this._group = new HGroupNode(null, null);
/* 130 */     this._groupNodePanel = new HNodePanel(this._group, this);
/* 131 */     localJPanel3.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
/* 132 */     localJLabel3.setBackground(this._groupNodePanel.getBackground());
/* 133 */     localJPanel3.setBackground(this._groupNodePanel.getBackground());
/*     */     
/* 135 */     localJPanel3.add(localJLabel3);
/* 136 */     localJPanel3.add(this._groupNodePanel);
/*     */     
/*     */ 
/* 139 */     addItem(localJPanel1, localGridBagLayout, localGridBagConstraints, 0, 0, 1, 1, 1, 0.55D, 0.75D);
/* 140 */     addItem(localJPanel2, localGridBagLayout, localGridBagConstraints, 1, 0, 1, 1, 1, 0.45D, 0.75D);
/* 141 */     addItem(localJPanel3, localGridBagLayout, localGridBagConstraints, 2, 0, 1, 1, 1, 0.0D, 0.75D);
/*     */     
/* 143 */     this._shapeNodePanel.setSelected(true);
/* 144 */     this._shapeType.addActionListener(this);
/* 145 */     this._transformType.addActionListener(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPalette(HToolPalette paramHToolPalette)
/*     */   {
/* 152 */     this._palette = paramHToolPalette;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addItem(Component paramComponent, GridBagLayout paramGridBagLayout, java.awt.GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2)
/*     */   {
/* 161 */     paramGridBagConstraints.gridx = paramInt1;
/* 162 */     paramGridBagConstraints.gridy = paramInt2;
/* 163 */     paramGridBagConstraints.gridwidth = paramInt3;
/* 164 */     paramGridBagConstraints.gridheight = paramInt4;
/* 165 */     paramGridBagConstraints.weightx = paramDouble1;
/* 166 */     paramGridBagConstraints.weighty = paramDouble2;
/* 167 */     paramGridBagConstraints.fill = paramInt5;
/* 168 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/* 169 */     add(paramComponent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearSelected()
/*     */   {
/* 176 */     this._shapeNodePanel.setSelected(false);
/* 177 */     this._transformNodePanel.setSelected(false);
/* 178 */     this._groupNodePanel.setSelected(false);
/* 179 */     if (this._palette != null) {
/* 180 */       this._palette.setActiveTool("Node");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public HNode getNode()
/*     */   {
/* 187 */     Object localObject = null;
/* 188 */     String str; if (this._shapeNodePanel.isSelected()) {
/* 189 */       str = (String)this._shapeType.getSelectedItem();
/*     */       HRobotPart localHRobotPart;
/* 191 */       if (str.equals("Square")) {
/* 192 */         localHRobotPart = new HRobotPart(HMatrixOps.getRect(30.0F, 30.0F), 4);
/*     */       }
/* 194 */       else if (str.equals("Rectangle")) {
/* 195 */         localHRobotPart = new HRobotPart(HMatrixOps.getRect(50.0F, 30.0F), 4);
/*     */       }
/* 197 */       else if (str.equals("Circle")) {
/* 198 */         localHRobotPart = new HRobotPart(HMatrixOps.getCircle(30.0F, 15), 15);
/*     */       }
/* 200 */       else if (str.equals("Triangle")) {
/* 201 */         localHRobotPart = new HRobotPart(HMatrixOps.getTriangle(50.0F, 50.0F), 3);
/*     */       }
/* 203 */       else if (str.equals("House")) {
/* 204 */         localHRobotPart = new HRobotPart(HMatrixOps.getHouse(), 13);
/*     */       } else
/* 206 */         localHRobotPart = new HRobotPart(HMatrixOps.getHouse(), 13);
/* 207 */       localObject = new HShapeNode(localHRobotPart, this._tree, this._chooser);
/* 208 */       ((HNode)localObject).setDescriptor(str);
/*     */ 
/*     */     }
/* 211 */     else if (this._transformNodePanel.isSelected()) {
/* 212 */       str = (String)this._transformType.getSelectedItem();
/* 213 */       if (str.equals("Translate")) {
/* 214 */         localObject = new HTransformTranslateNode(this._tree, 0.0F, 0.0F, this._chooser);
/* 215 */         ((HTransformNode)localObject).setTransform(HMatrixOps.getIdentity(3));
/*     */       }
/* 217 */       else if (str.equals("Rotate")) {
/* 218 */         localObject = new HTransformRotateNode(this._tree, 0.0F, this._chooser);
/* 219 */         ((HTransformNode)localObject).setTransform(HMatrixOps.getIdentity(3));
/*     */       }
/* 221 */       else if (str.equals("Scale")) {
/* 222 */         localObject = new HTransformScaleNode(this._tree, 1.0F, this._chooser);
/* 223 */         ((HTransformNode)localObject).setTransform(HMatrixOps.getIdentity(3));
/*     */       }
/*     */       
/*     */     }
/* 227 */     else if (this._groupNodePanel.isSelected()) {
/* 228 */       localObject = new HGroupNode(this._tree, this._chooser);
/*     */     }
/* 230 */     if (localObject == null) {
/* 231 */       System.out.println("no node found");
/*     */     }
/* 233 */     this._tree.addUnlinkedNode((HNode)localObject);
/*     */     
/* 235 */     return (HNode)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
/*     */   {
/* 242 */     if (paramActionEvent.getSource() == this._shapeType) {
/* 243 */       clearSelected();
/* 244 */       this._shapeNodePanel.setSelected(true);
/* 245 */       repaint();
/*     */     }
/* 247 */     if (paramActionEvent.getSource() == this._transformType) {
/* 248 */       clearSelected();
/* 249 */       this._transformNodePanel.setSelected(true);
/* 250 */       repaint();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HRobotContainer getContainer()
/*     */   {
/* 258 */     return this._container;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HTree getTree()
/*     */   {
/* 265 */     return this._tree;
/*     */   }
/*     */   
/*     */   public void addMouseListener(java.awt.event.MouseListener paramMouseListener) {
/* 269 */     Component[] arrayOfComponent = getComponents();
/*     */     try {
/* 271 */       for (int i = 0;; i++) {
/* 272 */         arrayOfComponent[i].addMouseListener(paramMouseListener);
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 275 */       super.addMouseListener(paramMouseListener);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HNodeTypeChooser.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */