/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EditableCTFunctionController
/*     */   implements CTFunctionController, Serializable, PropertyChangeListener
/*     */ {
/*     */   protected transient double scale_factor;
/*     */   protected transient int chart_width;
/*     */   
/*     */   public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
/*     */   {
/*  16 */     this.function_window = paramCTFunctionWindow;
/*     */     
/*  18 */     this.chart_width = this.function_window.getChartWidth();
/*  19 */     this.chart_values = new double[this.chart_width];
/*     */   }
/*     */   
/*     */   public void mouseStart(int paramInt1, int paramInt2)
/*     */   {
/*  24 */     this.last_x = paramInt1;
/*  25 */     this.last_y = paramInt2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  31 */     mouseDrag(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void mouseDrag(int paramInt1, int paramInt2)
/*     */   {
/*  36 */     double d1 = this.function_window.getYStart();
/*  37 */     double d2 = this.function_window.getYEnd();
/*  38 */     double d3 = (d2 - d1) / this.function_window.getChartHeight();
/*     */     
/*     */ 
/*  41 */     int i = Math.abs(paramInt1 - this.last_x);
/*     */     int j;
/*     */     double[] arrayOfDouble;
/*  44 */     if (i == 0) {
/*  45 */       i = 1;
/*  46 */       j = paramInt1;
/*  47 */       arrayOfDouble = new double[1];
/*  48 */       arrayOfDouble[0] = (paramInt2 * d3 + d1);
/*  49 */       this.chart_values[j] = arrayOfDouble[0];
/*     */     }
/*     */     else {
/*  52 */       if (paramInt1 < this.last_x) {
/*  53 */         j = paramInt1;
/*     */       } else {
/*  55 */         j = paramInt1 - i + 1;
/*     */       }
/*  57 */       arrayOfDouble = new double[i];
/*     */       double d4;
/*  59 */       double d5; if (paramInt1 > this.last_x) {
/*  60 */         d4 = this.last_y;
/*  61 */         d5 = paramInt2;
/*     */       }
/*     */       else {
/*  64 */         d5 = this.last_y;
/*  65 */         d4 = paramInt2;
/*     */       }
/*     */       
/*  68 */       for (int k = 0; k < i; k++) {
/*  69 */         double d6 = (k + 1) / i;
/*  70 */         arrayOfDouble[k] = (d3 * ((1.0D - d6) * d4 + d6 * d5) + d1);
/*  71 */         this.chart_values[(j + k)] = arrayOfDouble[k];
/*     */       }
/*     */     }
/*     */     
/*  75 */     this.function_window.changeValues(j, i, arrayOfDouble);
/*  76 */     if (this.imp_ctrl != null) {
/*  77 */       this.imp_ctrl.recompute();
/*     */     }
/*  79 */     this.last_x = paramInt1;
/*  80 */     this.last_y = paramInt2;
/*     */   }
/*     */   
/*     */   public void mouseStop()
/*     */   {
/*  85 */     if (this.imp_ctrl != null) {
/*  86 */       this.imp_ctrl.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resize(int paramInt1, int paramInt2) {
/*  91 */     int i = paramInt1;
/*     */     
/*  93 */     if (this.chart_width != i) {
/*  94 */       this.chart_values = new double[i];
/*     */     }
/*  96 */     this.chart_width = i;
/*     */   }
/*     */   
/*     */   public void normalize()
/*     */   {
/* 101 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/* 103 */     double d1 = 0.0D;
/* 104 */     for (int i = 0; i < this.chart_width; i++) {
/* 105 */       d1 += Math.abs(this.chart_values[i]);
/*     */     }
/* 107 */     double d2 = this.function_window.getXStart();
/* 108 */     double d3 = this.function_window.getXEnd();
/* 109 */     d1 *= (d3 - d2) / this.chart_width;
/*     */     
/* 111 */     if (d1 < 1.0E-5D) {
/* 112 */       return;
/*     */     }
/* 114 */     for (int j = 0; j < this.chart_width; j++) {
/* 115 */       this.chart_values[j] /= d1;
/*     */     }
/* 117 */     this.function_window.changeValues(0, this.chart_width, this.chart_values);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 122 */     if (this.imp_ctrl != null) {
/* 123 */       this.imp_ctrl.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 128 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/* 130 */     for (int i = 0; i < this.chart_width; i++) {
/* 131 */       this.chart_values[i] = 0.0D;
/*     */     }
/* 133 */     this.function_window.changeValues(0, this.chart_width, this.chart_values);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 138 */     if (this.imp_ctrl != null) {
/* 139 */       this.imp_ctrl.recompute();
/*     */     }
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
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
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
/*     */   public void setImpulseController(ImpulseCTFunctionController paramImpulseCTFunctionController)
/*     */   {
/* 170 */     this.imp_ctrl = paramImpulseCTFunctionController;
/*     */   }
/*     */   
/* 173 */   public int getChartWidth() { return this.chart_width; }
/* 174 */   public double[] getChartValues() { return this.chart_values; }
/*     */   
/*     */ 
/*     */ 
/* 178 */   protected transient int last_x = -1;
/* 179 */   protected transient int last_y = -1;
/*     */   protected transient double[] chart_values;
/*     */   protected transient CTFunctionWindow function_window;
/*     */   protected transient ImpulseCTFunctionController imp_ctrl;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/impulse_response.jar!/EditableCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */