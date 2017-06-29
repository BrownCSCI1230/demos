/*    */ import java.awt.Container;
/*    */ 
/*    */ public class Nyquist extends javax.swing.JApplet implements java.awt.event.ActionListener, java.awt.event.ComponentListener { public static transient Nyquist __instance;
/*    */   protected CTFunctionWindow f_;
/*    */   protected CTFunctionWindow recon_;
/*    */   protected DTFunctionWindow s_;
/*    */   protected SinCTFunctionController fcontroller_;
/*    */   protected TRecCTFunctionController rcontroller_;
/*    */   protected SampDTFunctionController scontroller_;
/*    */   protected javax.swing.JSlider slider_;
/*    */   
/* 12 */   public Nyquist() { __instance = this; }
/*    */   
/*    */ 
/*    */   public void init()
/*    */   {
/* 17 */     getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), 1));
/*    */     
/* 19 */     this.f_ = new CTFunctionWindow();
/* 20 */     this.f_.setEnabled(false);
/* 21 */     this.f_.setGraphColor(java.awt.Color.blue);
/* 22 */     this.f_.setMarkers(true);
/* 23 */     getContentPane().add(this.f_);
/* 24 */     this.fcontroller_ = new SinCTFunctionController();
/* 25 */     this.f_.setController(this.fcontroller_);
/*    */     
/* 27 */     javax.swing.JPanel localJPanel1 = new javax.swing.JPanel();
/* 28 */     localJPanel1.add(new javax.swing.JLabel("Frequency:       "));
/* 29 */     javax.swing.JSlider localJSlider1 = new javax.swing.JSlider(0, 4, 128, 32);
/* 30 */     localJSlider1.setPreferredSize(new java.awt.Dimension(460, 30));
/* 31 */     localJSlider1.setPaintTicks(true);
/* 32 */     localJSlider1.setMajorTickSpacing(4);
/* 33 */     localJSlider1.setMinorTickSpacing(1);
/* 34 */     localJPanel1.add(localJSlider1);
/* 35 */     getContentPane().add(localJPanel1);
/* 36 */     this.fcontroller_.setFrequencySlider(localJSlider1);
/*    */     
/* 38 */     javax.swing.JPanel localJPanel2 = new javax.swing.JPanel();
/* 39 */     localJPanel2.add(new javax.swing.JLabel("Sampling Rate:"));
/* 40 */     javax.swing.JSlider localJSlider2 = new javax.swing.JSlider(0, 4, 128, 64);
/* 41 */     localJSlider2.setPreferredSize(new java.awt.Dimension(460, 30));
/* 42 */     localJSlider2.setPaintTicks(true);
/* 43 */     localJSlider2.setMajorTickSpacing(4);
/* 44 */     localJSlider2.setMinorTickSpacing(1);
/* 45 */     localJPanel2.add(localJSlider2);
/* 46 */     getContentPane().add(localJPanel2);
/* 47 */     this.fcontroller_.setSamplingSlider(localJSlider2);
/*    */     
/* 49 */     this.s_ = new DTFunctionWindow();
/* 50 */     getContentPane().add(this.s_);
/* 51 */     this.scontroller_ = new SampDTFunctionController();
/* 52 */     this.s_.setController(this.scontroller_);
/* 53 */     this.fcontroller_.setSampController(this.scontroller_);
/*    */     
/* 55 */     this.recon_ = new CTFunctionWindow();
/* 56 */     this.recon_.setEnabled(false);
/* 57 */     getContentPane().add(this.recon_);
/* 58 */     this.rcontroller_ = new TRecCTFunctionController();
/* 59 */     this.recon_.setController(this.rcontroller_);
/* 60 */     this.fcontroller_.setReconController(this.rcontroller_);
/*    */     
/* 62 */     getContentPane().add(new javax.swing.JLabel("Daniel L. Gould <dlg@cs.brown.edu>"));
/*    */     
/* 64 */     this.f_.setTitle("f(x) [Original function]");
/* 65 */     this.s_.setTitle("f(X) [Sampled f(x)]");
/*    */     
/* 67 */     this.recon_.setTitle("f'(x) [Reconstruction of f(x) with Triangle]");
/* 68 */     this.f_.addComponentListener(this);
/*    */   }
/*    */   
/*    */ 
/*    */   public void resetPositions() {}
/*    */   
/*    */ 
/*    */   public void setSlidable(boolean paramBoolean)
/*    */   {
/* 77 */     this.slider_.setEnabled(paramBoolean);
/* 78 */     if (paramBoolean) {
/* 79 */       this.slider_.setCursor(new java.awt.Cursor(0));
/*    */     }
/*    */     else {
/* 82 */       this.slider_.setCursor(new java.awt.Cursor(3));
/*    */     }
/*    */   }
/*    */   
/*    */   public void fixSlider() {}
/*    */   
/*    */   public void fixApplet() {}
/*    */   
/*    */   public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {}
/*    */   
/*    */   public void componentResized(java.awt.event.ComponentEvent paramComponentEvent) {}
/*    */   
/*    */   public void componentMoved(java.awt.event.ComponentEvent paramComponentEvent) {}
/*    */   
/*    */   public void componentHidden(java.awt.event.ComponentEvent paramComponentEvent) {}
/*    */   
/*    */   public void componentShown(java.awt.event.ComponentEvent paramComponentEvent) {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/nyquist_limit.jar!/Nyquist.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */