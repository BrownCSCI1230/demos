/*     */ import java.beans.PropertyChangeEvent;
/*     */ 
/*     */ public class ConvCTFunctionController implements CTFunctionController, Runnable, java.io.Serializable, java.beans.PropertyChangeListener
/*     */ {
/*     */   protected transient int chart_width;
/*     */   protected transient double[] chart_values;
/*     */   protected transient int mask_min;
/*     */   protected transient int mask_max;
/*     */   protected transient Thread worker_thread;
/*     */   protected transient CTFunctionWindow function_window;
/*     */   protected transient EditableCTFunctionController func_f;
/*     */   protected transient MovableScalableCTFunctionController func_g;
/*     */   
/*     */   public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
/*     */   {
/*  16 */     this.function_window = paramCTFunctionWindow;
/*     */   }
/*     */   
/*     */ 
/*     */   public void recompute()
/*     */   {
/*  22 */     this.mask_min = (this.chart_width / 2);
/*  23 */     this.mask_max = (this.chart_width / 2);
/*  24 */     for (int i = 0; i < this.chart_width; i++) {
/*  25 */       this.chart_values[i] = 0.0D;
/*     */     }
/*  27 */     this.function_window.changeValues(0, this.chart_width, this.chart_values);
/*     */     
/*  29 */     ConvolutionSlide.__instance.setSlidable(false);
/*  30 */     this.worker_thread = new Thread(this);
/*  31 */     this.worker_thread.setPriority(4);
/*  32 */     this.worker_thread.start();
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*  37 */     int i = this.chart_width / 2;
/*  38 */     double[] arrayOfDouble1 = this.func_f.getChartValues();
/*  39 */     double[] arrayOfDouble2 = this.func_g.getChartValues();
/*     */     try
/*     */     {
/*  42 */       for (int j = -i; j < i; j++) {
/*  43 */         if (Thread.interrupted()) {
/*  44 */           throw new InterruptedException();
/*     */         }
/*  46 */         for (int k = 0; k < this.chart_width; k++) {
/*  47 */           if ((k - j > 0) && (k - j < this.chart_width)) {
/*  48 */             this.chart_values[(j + i)] += arrayOfDouble1[k] * arrayOfDouble2[(k - j)];
/*     */           }
/*     */         }
/*     */       }
/*  52 */       double d = (this.function_window.getXEnd() - this.function_window.getXStart()) / this.chart_width;
/*  53 */       for (int m = 0; m < this.chart_width; m++) {
/*  54 */         this.chart_values[m] *= d;
/*     */       }
/*  56 */       finishRun();
/*     */     }
/*     */     catch (InterruptedException localInterruptedException)
/*     */     {
/*  60 */       System.out.println("Interrupted exception caught");
/*     */     }
/*     */   }
/*     */   
/*     */   public void reveal(int paramInt)
/*     */   {
/*  66 */     paramInt += this.chart_width / 2;
/*     */     
/*  68 */     if (paramInt < this.mask_min) {
/*  69 */       this.mask_min = paramInt;
/*     */     }
/*  71 */     if (paramInt > this.mask_max) {
/*  72 */       this.mask_max = paramInt;
/*     */     }
/*  74 */     this.function_window.getYEnd();
/*  75 */     this.function_window.getYStart();
/*     */     
/*  77 */     int i = this.mask_max - this.mask_min;
/*  78 */     double[] arrayOfDouble = new double[i];
/*  79 */     for (int j = 0; j < i; j++) {
/*  80 */       arrayOfDouble[j] = this.chart_values[(j + this.mask_min)];
/*     */     }
/*     */     
/*  83 */     this.function_window.changeValues(this.mask_min, i, arrayOfDouble);
/*  84 */     this.function_window.setMarker(paramInt);
/*     */   }
/*     */   
/*     */   public void resetPosition()
/*     */   {
/*  89 */     this.mask_min = (this.chart_width / 2);
/*  90 */     this.mask_max = (this.chart_width / 2);
/*  91 */     this.function_window.setMarker(-1);
/*     */     
/*     */ 
/*  94 */     double[] arrayOfDouble = new double[this.chart_width];
/*  95 */     for (int i = 0; i < this.chart_width; i++) {
/*  96 */       arrayOfDouble[i] = 0.0D;
/*     */     }
/*     */     
/*  99 */     this.function_window.changeValues(0, this.chart_width, arrayOfDouble);
/*     */   }
/*     */   
/*     */   protected void invalidate()
/*     */   {
/* 104 */     if (this.worker_thread != null) {
/* 105 */       this.worker_thread.interrupt();
/*     */     }
/*     */   }
/*     */   
/*     */   private final void finishRun() {
/* 110 */     ConvolutionSlide.__instance.setSlidable(true);
/*     */   }
/*     */   
/*     */   public void resize(int paramInt1, int paramInt2)
/*     */   {
/* 115 */     int i = paramInt1;
/*     */     
/* 117 */     if (this.chart_width != i) {
/* 118 */       this.chart_values = new double[i];
/* 119 */       this.mask_min = (this.chart_width / 2);
/* 120 */       this.mask_max = (this.chart_width / 2);
/*     */     }
/*     */     
/* 123 */     this.chart_width = i;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setF(EditableCTFunctionController paramEditableCTFunctionController)
/*     */   {
/* 129 */     this.func_f = paramEditableCTFunctionController;
/* 130 */     paramEditableCTFunctionController.setConvController(this);
/*     */   }
/*     */   
/*     */   public void setG(MovableScalableCTFunctionController paramMovableScalableCTFunctionController)
/*     */   {
/* 135 */     this.func_g = paramMovableScalableCTFunctionController;
/* 136 */     paramMovableScalableCTFunctionController.setConvController(this);
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */   
/*     */   public void mouseStart(int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void mouseDrag(int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void mouseStop() {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/ConvCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */