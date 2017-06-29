/*     */ import javax.swing.JApplet;
/*     */ 
/*     */ public class ConvolutionSlide extends JApplet implements java.awt.event.ActionListener {
/*     */   public static transient ConvolutionSlide __instance;
/*     */   protected DTFunctionWindow f_;
/*     */   protected DTFunctionWindow g_;
/*     */   protected DTFunctionWindow prod_;
/*     */   protected DTFunctionWindow conv_;
/*     */   protected javax.swing.JSlider slider_;
/*     */   
/*     */   public ConvolutionSlide() {
/*  12 */     __instance = this;
/*     */   }
/*     */   
/*     */   public void init()
/*     */   {
/*  17 */     getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), 1));
/*     */     
/*  19 */     this.f_clr_btn = new javax.swing.JButton("Clear");
/*  20 */     this.f_clr_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  21 */     this.f_clr_btn.addActionListener(this);
/*  22 */     getContentPane().add(this.f_clr_btn);
/*     */     
/*  24 */     this.f_ = new DTFunctionWindow();
/*  25 */     getContentPane().add(this.f_);
/*  26 */     this.fcontroller_ = new EditableDTFunctionController();
/*  27 */     this.f_.setController(this.fcontroller_);
/*     */     
/*  29 */     getContentPane().add(new javax.swing.JSeparator());
/*     */     
/*  31 */     javax.swing.JPanel localJPanel = new javax.swing.JPanel();
/*  32 */     this.norm_btn = new javax.swing.JButton("Normalize");
/*  33 */     this.norm_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  34 */     this.norm_btn.addActionListener(this);
/*  35 */     localJPanel.add(this.norm_btn);
/*  36 */     this.g_clr_btn = new javax.swing.JButton("Clear");
/*  37 */     this.g_clr_btn.setBorder(new javax.swing.border.EtchedBorder());
/*  38 */     this.g_clr_btn.addActionListener(this);
/*  39 */     localJPanel.add(this.g_clr_btn);
/*  40 */     getContentPane().add(localJPanel);
/*     */     
/*  42 */     this.g_ = new DTFunctionWindow();
/*  43 */     getContentPane().add(this.g_);
/*  44 */     this.gcontroller_ = new MovableDTFunctionController();
/*  45 */     this.g_.setController(this.gcontroller_);
/*     */     
/*  47 */     this.slider_ = new javax.swing.JSlider();
/*  48 */     this.g_.getChartWidth();
/*  49 */     this.slider_.setMinimum(-8);
/*  50 */     this.slider_.setMaximum(8);
/*  51 */     this.slider_.setValue(0);
/*  52 */     this.slider_.setPaintTicks(true);
/*  53 */     this.slider_.setMajorTickSpacing(1);
/*  54 */     this.slider_.setSnapToTicks(true);
/*  55 */     getContentPane().add(this.slider_);
/*  56 */     this.gcontroller_.setSlider(this.slider_);
/*     */     
/*  58 */     getContentPane().add(new javax.swing.JSeparator());
/*     */     
/*  60 */     this.prod_ = new DTFunctionWindow();
/*  61 */     this.prod_.setEnabled(false);
/*  62 */     getContentPane().add(this.prod_);
/*  63 */     this.pcontroller_ = new ProductDTFunctionController();
/*  64 */     this.prod_.setController(this.pcontroller_);
/*  65 */     this.pcontroller_.setF(this.fcontroller_);
/*  66 */     this.pcontroller_.setG(this.gcontroller_);
/*     */     
/*  68 */     this.conv_ = new DTFunctionWindow();
/*  69 */     this.conv_.setEnabled(false);
/*  70 */     getContentPane().add(this.conv_);
/*  71 */     this.ccontroller_ = new ConvDTFunctionController();
/*  72 */     this.conv_.setController(this.ccontroller_);
/*  73 */     this.ccontroller_.setF(this.fcontroller_);
/*  74 */     this.ccontroller_.setG(this.gcontroller_);
/*     */     
/*  76 */     getContentPane().add(new javax.swing.JLabel("Daniel L. Gould <dlg@cs.brown.edu>"));
/*     */     
/*  78 */     this.f_.setTitle("f(x) [Function to be Convolved]");
/*     */     
/*  80 */     this.g_.setTitle("g(x) [Convolution Filter]");
/*     */     
/*  82 */     this.slider_.setToolTipText("Slide Filter to Convolve");
/*  83 */     this.prod_.setTitle("f(x)g(x) [Product]");
/*     */     
/*  85 */     this.conv_.setTitle("f(x)*g(x) [Convolution]");
/*     */   }
/*     */   
/*     */ 
/*     */   public void resetPositions()
/*     */   {
/*  91 */     this.gcontroller_.resetPosition();
/*  92 */     this.pcontroller_.resetPosition();
/*  93 */     this.ccontroller_.resetPosition();
/*     */   }
/*     */   
/*     */   public void setSlidable(boolean paramBoolean)
/*     */   {
/*  98 */     this.slider_.setEnabled(paramBoolean);
/*  99 */     if (paramBoolean) {
/* 100 */       this.slider_.setCursor(new java.awt.Cursor(0));
/*     */     }
/*     */     else {
/* 103 */       this.slider_.setCursor(new java.awt.Cursor(3));
/*     */     }
/*     */   }
/*     */   
/*     */   public void fixSlider()
/*     */   {
/* 109 */     int i = this.g_.getChartWidth() / 2 - 1;
/* 110 */     this.slider_.setMinimum(-i);
/* 111 */     this.slider_.setMaximum(i);
/* 112 */     this.slider_.setMajorTickSpacing(i + 1);
/*     */   }
/*     */   
/*     */   protected javax.swing.JButton norm_btn;
/*     */   protected javax.swing.JButton f_clr_btn;
/*     */   protected javax.swing.JButton g_clr_btn;
/*     */   
/*     */   public void fixApplet() {}
/*     */   
/*     */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
/* 122 */     if (paramActionEvent.getSource().equals(this.norm_btn)) {
/* 123 */       this.gcontroller_.normalize();
/* 124 */     } else if (paramActionEvent.getSource().equals(this.f_clr_btn)) {
/* 125 */       this.fcontroller_.clear();
/* 126 */     } else if (paramActionEvent.getSource().equals(this.g_clr_btn)) {
/* 127 */       this.gcontroller_.clear();
/*     */     } else {
/* 129 */       System.out.println("foo");
/*     */     }
/*     */   }
/*     */   
/*     */   protected EditableDTFunctionController fcontroller_;
/*     */   protected MovableDTFunctionController gcontroller_;
/*     */   protected ProductDTFunctionController pcontroller_;
/*     */   protected ConvDTFunctionController ccontroller_;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/discrete_convolution.jar!/ConvolutionSlide.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */