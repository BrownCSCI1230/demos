/*     */ import java.awt.Container;
/*     */ 
/*     */ public class ConvolutionSlide extends javax.swing.JApplet implements java.awt.event.ActionListener { public static transient ConvolutionSlide __instance;
/*     */   protected CTFunctionWindow f_;
/*     */   protected CTFunctionWindow g_;
/*     */   protected CTFunctionWindow prod_;
/*     */   protected CTFunctionWindow conv_;
/*     */   protected javax.swing.JSlider slider_;
/*     */   protected javax.swing.JButton clr_btn;
/*     */   protected javax.swing.JButton norm_btn;
/*     */   
/*  12 */   public ConvolutionSlide() { __instance = this; }
/*     */   
/*     */   protected javax.swing.JButton box_btn;
/*     */   protected javax.swing.JButton tri_btn;
/*     */   
/*  17 */   public void init() { getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), 1));
/*     */     
/*  19 */     this.clr_btn = new javax.swing.JButton("Clear");
/*  20 */     this.clr_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  21 */     this.clr_btn.addActionListener(this);
/*  22 */     getContentPane().add(this.clr_btn);
/*     */     
/*  24 */     this.f_ = new CTFunctionWindow();
/*  25 */     getContentPane().add(this.f_);
/*  26 */     this.fcontroller_ = new EditableCTFunctionController();
/*  27 */     this.f_.setController(this.fcontroller_);
/*     */     
/*  29 */     getContentPane().add(new javax.swing.JSeparator());
/*     */     
/*  31 */     javax.swing.JPanel localJPanel = new javax.swing.JPanel();
/*     */     
/*  33 */     this.norm_btn = new javax.swing.JButton("Normalize");
/*  34 */     this.norm_btn.setBorder(new javax.swing.border.EtchedBorder(java.awt.Color.yellow, java.awt.Color.darkGray));
/*  35 */     this.norm_btn.addActionListener(this);
/*  36 */     localJPanel.add(this.norm_btn);
/*  37 */     this.box_btn = new javax.swing.JButton("Box Filter");
/*  38 */     this.box_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  39 */     this.box_btn.addActionListener(this);
/*  40 */     localJPanel.add(this.box_btn);
/*  41 */     this.tri_btn = new javax.swing.JButton("Triangle");
/*  42 */     this.tri_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  43 */     localJPanel.add(this.tri_btn);
/*  44 */     this.tri_btn.addActionListener(this);
/*  45 */     this.gaussian_btn = new javax.swing.JButton("Gaussian");
/*  46 */     this.gaussian_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  47 */     localJPanel.add(this.gaussian_btn);
/*  48 */     this.gaussian_btn.addActionListener(this);
/*  49 */     this.sinc_btn = new javax.swing.JButton("Sinc");
/*  50 */     this.sinc_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  51 */     localJPanel.add(this.sinc_btn);
/*  52 */     this.sinc_btn.addActionListener(this);
/*  53 */     getContentPane().add(localJPanel);
/*     */     
/*  55 */     this.g_ = new CTFunctionWindow();
/*  56 */     getContentPane().add(this.g_);
/*  57 */     this.gcontroller_ = new MovableScalableCTFunctionController(new BoxFunction());
/*  58 */     this.g_.setController(this.gcontroller_);
/*     */     
/*  60 */     this.slider_ = new javax.swing.JSlider();
/*  61 */     this.g_.getChartWidth();
/*  62 */     this.slider_.setMinimum(65273);
/*  63 */     this.slider_.setMaximum(263);
/*  64 */     this.slider_.setValue(0);
/*  65 */     this.slider_.setPaintTicks(true);
/*  66 */     this.slider_.setMajorTickSpacing(263);
/*  67 */     getContentPane().add(this.slider_);
/*  68 */     this.gcontroller_.setSlider(this.slider_);
/*     */     
/*  70 */     getContentPane().add(new javax.swing.JSeparator());
/*     */     
/*  72 */     this.prod_ = new CTFunctionWindow();
/*  73 */     this.prod_.setEnabled(false);
/*  74 */     getContentPane().add(this.prod_);
/*  75 */     this.pcontroller_ = new ProductCTFunctionController();
/*  76 */     this.prod_.setController(this.pcontroller_);
/*  77 */     this.pcontroller_.setF(this.fcontroller_);
/*  78 */     this.pcontroller_.setG(this.gcontroller_);
/*     */     
/*  80 */     this.conv_ = new CTFunctionWindow();
/*  81 */     this.conv_.setEnabled(false);
/*  82 */     getContentPane().add(this.conv_);
/*  83 */     this.ccontroller_ = new ConvCTFunctionController();
/*  84 */     this.conv_.setController(this.ccontroller_);
/*  85 */     this.ccontroller_.setF(this.fcontroller_);
/*  86 */     this.ccontroller_.setG(this.gcontroller_);
/*     */     
/*  88 */     getContentPane().add(new javax.swing.JLabel("Daniel L. Gould <dlg@cs.brown.edu>"));
/*     */     
/*  90 */     this.f_.setTitle("f(x) [Function to be Convolved]");
/*     */     
/*  92 */     this.g_.setTitle("g(x) [Convolution filter]");
/*     */     
/*  94 */     this.slider_.setToolTipText("Slide Filter to Convolve");
/*  95 */     this.prod_.setTitle("f(x)g(x) [Product]");
/*     */     
/*  97 */     this.conv_.setTitle("f(x)*g(x) [Convolution]"); }
/*     */   
/*     */   protected javax.swing.JButton gaussian_btn;
/*     */   protected javax.swing.JButton sinc_btn;
/*     */   protected EditableCTFunctionController fcontroller_;
/*     */   
/* 103 */   public void resetPositions() { this.gcontroller_.resetPosition();
/* 104 */     this.pcontroller_.resetPosition();
/* 105 */     this.ccontroller_.resetPosition(); }
/*     */   
/*     */   protected MovableScalableCTFunctionController gcontroller_;
/*     */   protected ProductCTFunctionController pcontroller_;
/*     */   
/* 110 */   public void setSlidable(boolean paramBoolean) { this.slider_.setEnabled(paramBoolean);
/* 111 */     if (paramBoolean) {
/* 112 */       this.slider_.setCursor(new java.awt.Cursor(0));
/*     */     }
/*     */     else
/* 115 */       this.slider_.setCursor(new java.awt.Cursor(3));
/*     */   }
/*     */   
/*     */   protected ConvCTFunctionController ccontroller_;
/*     */   protected int component_hack;
/*     */   public void fixSlider() {
/* 121 */     if (this.component_hack > 7) {
/* 122 */       int i = this.g_.getChartWidth() / 2 - 1;
/* 123 */       this.slider_.setMinimum(-i);
/* 124 */       this.slider_.setMaximum(i);
/* 125 */       this.slider_.setMajorTickSpacing(i + 1);
/* 126 */       resetPositions();
/*     */     }
/*     */   }
/*     */   
/*     */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
/*     */   {
/* 132 */     if (paramActionEvent.getSource().equals(this.norm_btn)) {
/* 133 */       this.gcontroller_.normalize();
/* 134 */     } else if (paramActionEvent.getSource().equals(this.box_btn)) {
/* 135 */       this.gcontroller_.setFunction(new BoxFunction());
/* 136 */     } else if (paramActionEvent.getSource().equals(this.tri_btn)) {
/* 137 */       this.gcontroller_.setFunction(new TriangleFunction());
/* 138 */     } else if (paramActionEvent.getSource().equals(this.gaussian_btn)) {
/* 139 */       this.gcontroller_.setFunction(new GaussianFunction());
/* 140 */     } else if (paramActionEvent.getSource().equals(this.sinc_btn)) {
/* 141 */       this.gcontroller_.setFunction(new SincFunction());
/* 142 */     } else if (paramActionEvent.getSource().equals(this.clr_btn)) {
/* 143 */       this.fcontroller_.clear();
/*     */     } else {
/* 145 */       System.out.println("foo");
/*     */     }
/*     */   }
/*     */   
/*     */   public void fixApplet() {
/* 150 */     this.component_hack += 1;
/* 151 */     if (this.component_hack == 8) {
/* 152 */       fixSlider();
/* 153 */       this.gcontroller_.normalize();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/ConvolutionSlide.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */