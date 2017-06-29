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
/*  27 */     if (this.conv_ctrl != null) {
/*  28 */       this.conv_ctrl.invalidate();
/*     */     }
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
/*  76 */     if (this.prod_ctrl != null) {
/*  77 */       this.prod_ctrl.recompute(j, i);
/*     */     }
/*  79 */     this.last_x = paramInt1;
/*  80 */     this.last_y = paramInt2;
/*     */   }
/*     */   
/*     */   public void mouseStop()
/*     */   {
/*  85 */     if (this.conv_ctrl != null) {
/*  86 */       this.conv_ctrl.recompute();
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
/* 119 */     if (this.prod_ctrl != null) {
/* 120 */       this.prod_ctrl.recompute(0, this.chart_width);
/*     */     }
/* 122 */     if (this.conv_ctrl != null) {
/* 123 */       this.conv_ctrl.recompute();
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
/* 135 */     if (this.prod_ctrl != null) {
/* 136 */       this.prod_ctrl.recompute(0, this.chart_width);
/*     */     }
/* 138 */     if (this.conv_ctrl != null) {
/* 139 */       this.conv_ctrl.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */   public void fromFunction(Function paramFunction) {
/* 144 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/* 146 */     double d1 = this.function_window.getXStart();
/* 147 */     double d2 = this.function_window.getXEnd();
/*     */     
/* 149 */     for (int i = 0; i < this.chart_width; i++) {
/* 150 */       this.chart_values[i] = paramFunction.value((d2 - d1) / this.chart_width * i + d1);
/*     */     }
/* 152 */     this.function_window.changeValues(0, this.chart_width, this.chart_values);
/*     */     
/* 154 */     if (this.prod_ctrl != null) {
/* 155 */       this.prod_ctrl.recompute(0, this.chart_width);
/*     */     }
/* 157 */     if (this.conv_ctrl != null) {
/* 158 */       this.conv_ctrl.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */   
/*     */ 
/*     */   public void setProductController(ProductCTFunctionController paramProductCTFunctionController)
/*     */   {
/* 168 */     this.prod_ctrl = paramProductCTFunctionController;
/*     */   }
/*     */   
/*     */   public void setConvController(ConvCTFunctionController paramConvCTFunctionController)
/*     */   {
/* 173 */     this.conv_ctrl = paramConvCTFunctionController;
/*     */   }
/*     */   
/* 176 */   public int getChartWidth() { return this.chart_width; }
/* 177 */   public double[] getChartValues() { return this.chart_values; }
/*     */   
/*     */ 
/*     */ 
/* 181 */   protected transient int last_x = -1;
/* 182 */   protected transient int last_y = -1;
/*     */   protected transient double[] chart_values;
/*     */   protected transient CTFunctionWindow function_window;
/*     */   protected transient ProductCTFunctionController prod_ctrl;
/*     */   protected transient ConvCTFunctionController conv_ctrl;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/EditableCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */