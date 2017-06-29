/*     */ import java.beans.PropertyChangeEvent;
/*     */ 
/*     */ public class ImpulseCTFunctionController implements CTFunctionController, java.io.Serializable, java.beans.PropertyChangeListener
/*     */ {
/*     */   protected transient int chart_width;
/*     */   protected transient double[] chart_values;
/*     */   protected transient double[] temp_values;
/*     */   protected transient int mask_min;
/*     */   protected transient int mask_max;
/*     */   protected transient CTFunctionWindow function_window;
/*     */   protected transient EditableIFunctionController func_f;
/*     */   protected transient EditableCTFunctionController func_g;
/*     */   
/*     */   public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
/*     */   {
/*  16 */     this.function_window = paramCTFunctionWindow;
/*     */     
/*  18 */     this.chart_width = this.function_window.getChartWidth();
/*  19 */     this.chart_values = new double[this.chart_width];
/*  20 */     this.temp_values = new double[this.chart_width];
/*     */   }
/*     */   
/*     */   public void resize(int paramInt1, int paramInt2)
/*     */   {
/*  25 */     int i = paramInt1;
/*     */     
/*  27 */     if (this.chart_width != i) {
/*  28 */       this.chart_values = new double[i];
/*  29 */       this.temp_values = new double[this.chart_width];
/*     */     }
/*     */     
/*  32 */     this.chart_width = i;
/*     */   }
/*     */   
/*     */ 
/*     */   public void recompute()
/*     */   {
/*  38 */     int i = this.chart_width / 2;
/*  39 */     if ((this.mask_min != i) || 
/*  40 */       (this.mask_max != i)) {
/*  41 */       this.mask_min = (this.chart_width / 2);
/*  42 */       this.mask_max = (this.chart_width / 2);
/*  43 */       resetPosition();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  48 */     double[] arrayOfDouble = this.func_g.getChartValues();
/*     */     
/*  50 */     int j = this.func_f.getDValue().getX() - this.chart_width / 2;
/*  51 */     double d = this.func_f.getDValue().getValue();
/*     */     
/*  53 */     if (d != 0.0D) { int k;
/*  54 */       if (j < 0) {
/*  55 */         for (k = 0; k < this.chart_width + j; k++) {
/*  56 */           this.chart_values[k] = (d * arrayOfDouble[(this.chart_width - (k - j))]);
/*     */         }
/*     */         
/*  59 */       } else if (j > 0.0D) {
/*  60 */         for (k = j + 1; k < this.chart_width; k++) {
/*  61 */           this.chart_values[k] = (d * arrayOfDouble[(this.chart_width - (k - j))]);
/*     */         }
/*     */         
/*     */       } else {
/*  65 */         for (k = 1; k < this.chart_width; k++) {
/*  66 */           this.chart_values[k] = (d * arrayOfDouble[(this.chart_width - k)]);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void reveal(int paramInt)
/*     */   {
/*  74 */     paramInt += this.chart_width / 2;
/*     */     
/*  76 */     if (paramInt < this.mask_min) {
/*  77 */       this.mask_min = paramInt;
/*     */     }
/*  79 */     if (paramInt > this.mask_max) {
/*  80 */       this.mask_max = paramInt;
/*     */     }
/*  82 */     double d1 = this.function_window.getYEnd();
/*  83 */     double d2 = this.function_window.getYStart();
/*     */     
/*  85 */     int i = this.mask_max - this.mask_min;
/*  86 */     double[] arrayOfDouble = new double[i];
/*  87 */     for (int j = 0; j < i; j++) {
/*  88 */       arrayOfDouble[j] = this.chart_values[(j + this.mask_min)];
/*  89 */       if (arrayOfDouble[j] > d1)
/*  90 */         arrayOfDouble[j] = d1;
/*  91 */       if (arrayOfDouble[j] < d2) {
/*  92 */         arrayOfDouble[j] = d2;
/*     */       }
/*     */     }
/*  95 */     this.function_window.changeValues(this.mask_min, i, arrayOfDouble);
/*  96 */     this.function_window.setMarker(paramInt);
/*     */   }
/*     */   
/*     */   public void resetPosition()
/*     */   {
/* 101 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/*     */ 
/* 104 */     double[] arrayOfDouble = new double[this.chart_width];
/* 105 */     for (int i = 0; i < this.chart_width; i++) {
/* 106 */       arrayOfDouble[i] = 0.0D;
/*     */     }
/*     */     
/* 109 */     this.function_window.changeValues(0, this.chart_width, arrayOfDouble);
/*     */   }
/*     */   
/*     */   public void setF(EditableIFunctionController paramEditableIFunctionController)
/*     */   {
/* 114 */     this.func_f = paramEditableIFunctionController;
/* 115 */     paramEditableIFunctionController.setImpulseController(this);
/*     */   }
/*     */   
/*     */   public void setG(EditableCTFunctionController paramEditableCTFunctionController)
/*     */   {
/* 120 */     this.func_g = paramEditableCTFunctionController;
/* 121 */     paramEditableCTFunctionController.setImpulseController(this);
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


/* Location:              /Users/masonbartle/Downloads/impulse_response.jar!/ImpulseCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */