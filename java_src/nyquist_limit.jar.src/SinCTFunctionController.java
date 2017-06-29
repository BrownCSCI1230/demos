/*     */ import java.util.EventObject;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ public class SinCTFunctionController
/*     */   implements CTFunctionController, ChangeListener
/*     */ {
/*   9 */   protected double[] sample_values = new double[this.num_samples];
/*     */   
/*     */ 
/*     */   public void calcValues()
/*     */   {
/*  14 */     int i = this.function_window.getChartWidth();
/*  15 */     double d = this.rate_ * 3.141592653589793D / (2.0D * i);
/*     */     
/*  17 */     if (i != this.old_cw) {
/*  18 */       this.values_ = new double[i];
/*  19 */       this.old_cw = i;
/*     */     }
/*     */     
/*  22 */     for (int j = 0; j < i; j++) {
/*  23 */       this.values_[j] = Math.sin(j * d);
/*     */     }
/*     */     
/*  26 */     this.function_window.changeValues(0, i, this.values_);
/*     */   }
/*     */   
/*     */   public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
/*     */   {
/*  31 */     this.function_window = paramCTFunctionWindow;
/*     */   }
/*     */   
/*     */   public void setSampController(SampDTFunctionController paramSampDTFunctionController)
/*     */   {
/*  36 */     this.samp_controller = paramSampDTFunctionController;
/*     */   }
/*     */   
/*     */   public void setReconController(TRecCTFunctionController paramTRecCTFunctionController)
/*     */   {
/*  41 */     this.recon_controller = paramTRecCTFunctionController;
/*     */   }
/*     */   
/*     */   public void setFrequencySlider(JSlider paramJSlider)
/*     */   {
/*  46 */     this.freq_slider = paramJSlider;
/*  47 */     this.freq_slider.addChangeListener(this);
/*     */   }
/*     */   
/*     */   public void setSamplingSlider(JSlider paramJSlider)
/*     */   {
/*  52 */     this.samp_slider = paramJSlider;
/*  53 */     this.samp_slider.addChangeListener(this);
/*     */   }
/*     */   
/*     */   public void stateChanged(ChangeEvent paramChangeEvent)
/*     */   {
/*  58 */     JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
/*     */     
/*  60 */     if (localJSlider == this.freq_slider) {
/*  61 */       this.rate_ = localJSlider.getValue();
/*  62 */       calcValues();
/*     */     }
/*     */     else {
/*  65 */       this.num_samples = localJSlider.getValue();
/*  66 */       this.function_window.setNumSamples(this.num_samples);
/*     */       
/*  68 */       this.sample_values = new double[this.num_samples];
/*     */     }
/*     */     
/*  71 */     int i = this.function_window.getChartWidth();
/*  72 */     double d1 = this.rate_ * 3.141592653589793D / (2.0D * i);
/*     */     
/*  74 */     double d2 = i / (this.num_samples - 1.0D);
/*  75 */     for (int j = 0; j < this.num_samples; j++) {
/*  76 */       this.sample_values[j] = Math.sin(j * d2 * d1);
/*     */     }
/*     */     
/*  79 */     this.samp_controller.updateSamples(this.num_samples, this.sample_values);
/*  80 */     this.recon_controller.updateGraph(this.num_samples, this.sample_values);
/*     */   }
/*     */   
/*     */   public void init()
/*     */   {
/*  85 */     calcValues();
/*  86 */     int i = this.function_window.getChartWidth();
/*  87 */     double d1 = this.rate_ * 3.141592653589793D / (2.0D * i);
/*     */     
/*  89 */     double d2 = i / (this.num_samples - 1.0D);
/*  90 */     for (int j = 0; j < this.num_samples; j++) {
/*  91 */       this.sample_values[j] = Math.sin(j * d2 * d1);
/*     */     }
/*     */     
/*  94 */     this.samp_controller.updateSamples(this.num_samples, this.sample_values);
/*     */   }
/*     */   
/*     */   public void mouseStart(int paramInt1, int paramInt2)
/*     */   {
/*  99 */     calcValues();
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
/*     */ 
/* 121 */   protected int rate_ = 32;
/* 122 */   protected int num_samples = 64;
/*     */   protected CTFunctionWindow function_window;
/*     */   protected SampDTFunctionController samp_controller;
/*     */   protected TRecCTFunctionController recon_controller;
/*     */   protected JSlider freq_slider;
/*     */   protected JSlider samp_slider;
/*     */   protected double[] values_;
/*     */   protected int old_cw;
/*     */   
/*     */   public void mouseDrag(int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void mouseStop() {}
/*     */   
/*     */   public void resize(int paramInt1, int paramInt2) {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/nyquist_limit.jar!/SinCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */