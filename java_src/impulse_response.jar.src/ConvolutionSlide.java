/*    */ import javax.swing.JApplet;
/*    */ 
/*    */ public class ConvolutionSlide extends JApplet
/*    */   implements java.awt.event.ActionListener
/*    */ {
/*    */   public static transient ConvolutionSlide __instance;
/*    */   protected IFunctionWindow f_;
/*    */   protected CTFunctionWindow g_;
/*    */   protected CTFunctionWindow conv_;
/*    */   protected javax.swing.JButton g_clr_btn;
/*    */   
/* 12 */   public ConvolutionSlide() { __instance = this; }
/*    */   
/*    */   protected javax.swing.JButton norm_btn;
/*    */   protected javax.swing.JSlider slider_;
/*    */   
/* 17 */   public void init() { getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), 1));
/*    */     
/* 19 */     this.f_ = new IFunctionWindow();
/* 20 */     getContentPane().add(this.f_);
/* 21 */     this.fcontroller_ = new EditableIFunctionController();
/* 22 */     this.f_.setController(this.fcontroller_);
/*    */     
/* 24 */     getContentPane().add(new javax.swing.JSeparator());
/*    */     
/* 26 */     javax.swing.JPanel localJPanel = new javax.swing.JPanel();
/*    */     
/* 28 */     this.g_clr_btn = new javax.swing.JButton("Clear");
/* 29 */     this.g_clr_btn.setBorder(new javax.swing.border.EtchedBorder());
/* 30 */     this.g_clr_btn.addActionListener(this);
/* 31 */     localJPanel.add(this.g_clr_btn);
/* 32 */     this.norm_btn = new javax.swing.JButton("Normalize");
/* 33 */     this.norm_btn.setBorder(new javax.swing.border.EtchedBorder());
/* 34 */     this.norm_btn.addActionListener(this);
/* 35 */     localJPanel.add(this.norm_btn);
/*    */     
/* 37 */     this.g_ = new CTFunctionWindow();
/* 38 */     getContentPane().add(this.g_);
/* 39 */     this.gcontroller_ = new MovableCTFunctionController();
/* 40 */     this.g_.setController(this.gcontroller_);
/*    */     
/* 42 */     this.slider_ = new javax.swing.JSlider();
/* 43 */     this.g_.getChartWidth();
/* 44 */     this.slider_.setMinimum(65273);
/* 45 */     this.slider_.setMaximum(263);
/* 46 */     this.slider_.setValue(0);
/* 47 */     this.slider_.setPaintTicks(true);
/* 48 */     this.slider_.setMajorTickSpacing(263);
/* 49 */     getContentPane().add(this.slider_);
/* 50 */     this.gcontroller_.setSlider(this.slider_);
/*    */     
/* 52 */     getContentPane().add(new javax.swing.JSeparator());
/*    */     
/* 54 */     this.conv_ = new CTFunctionWindow();
/* 55 */     this.conv_.setEnabled(false);
/* 56 */     getContentPane().add(this.conv_);
/* 57 */     this.icontroller_ = new ImpulseCTFunctionController();
/* 58 */     this.conv_.setController(this.icontroller_);
/* 59 */     this.icontroller_.setF(this.fcontroller_);
/* 60 */     this.icontroller_.setG(this.gcontroller_);
/*    */     
/* 62 */     getContentPane().add(new javax.swing.JLabel("Daniel L. Gould <dlg@cs.brown.edu>"));
/*    */     
/* 64 */     this.f_.setTitle("f(x) [Impulse]");
/* 65 */     this.g_.setTitle("g(x) [Convolution Filter]");
/* 66 */     this.conv_.setTitle("f(x)*g(x) [Impulse Response]"); }
/*    */   
/*    */   protected EditableIFunctionController fcontroller_;
/*    */   protected MovableCTFunctionController gcontroller_;
/*    */   protected ImpulseCTFunctionController icontroller_;
/* 71 */   public void resetPositions() { this.slider_.setValue(0); }
/*    */   
/*    */ 
/*    */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent)
/*    */   {
/* 76 */     if (paramActionEvent.getSource().equals(this.norm_btn)) {
/* 77 */       this.gcontroller_.normalize();
/* 78 */     } else if (paramActionEvent.getSource().equals(this.g_clr_btn)) {
/* 79 */       this.gcontroller_.clear();
/*    */     } else {
/* 81 */       System.out.println("foo");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/impulse_response.jar!/ConvolutionSlide.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */