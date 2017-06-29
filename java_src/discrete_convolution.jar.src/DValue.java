/*     */ import java.awt.event.MouseEvent;
/*     */ 
/*     */ public class DValue extends java.awt.event.MouseMotionAdapter
/*     */ {
/*     */   protected transient DTFunctionWindow function_window;
/*     */   
/*     */   public DValue(DTFunctionWindow paramDTFunctionWindow, int paramInt, double paramDouble)
/*     */   {
/*   9 */     this.x_ = paramInt;
/*  10 */     this.value_ = paramDouble;
/*  11 */     this.function_window = paramDTFunctionWindow;
/*     */   }
/*     */   
/*     */   public void draw(java.awt.Graphics paramGraphics)
/*     */   {
/*  16 */     if (this.is_visible) {
/*  17 */       int i = this.function_window.XChtToPix(this.x_);
/*  18 */       int j = this.function_window.YChtToPix(this.value_);
/*     */       
/*  20 */       paramGraphics.setColor(this.bar_color);
/*  21 */       paramGraphics.drawLine(i, this.function_window.YChtToPix(0.0D), 
/*  22 */         i, j);
/*     */       
/*  24 */       if ((this.mouse_over) && (this.is_active)) {
/*  25 */         paramGraphics.setColor(this.active_box_color);
/*     */       }
/*     */       else {
/*  28 */         paramGraphics.setColor(this.box_color);
/*     */       }
/*     */       
/*  31 */       paramGraphics.fillRect(i - 5, j - 5, 
/*  32 */         10, 10);
/*     */     }
/*     */   }
/*     */   
/*     */   public void beginDrag(MouseEvent paramMouseEvent)
/*     */   {
/*  38 */     if (this.is_active) {
/*  39 */       int i = this.function_window.getChartWidth();
/*  40 */       this.function_window.getChartHeight();
/*     */       
/*  42 */       this.last_x = this.function_window.XPixToCht(paramMouseEvent.getX());
/*  43 */       this.last_y = this.function_window.YPixToCht(paramMouseEvent.getY());
/*     */       
/*  45 */       this.h_min = 0;
/*  46 */       this.h_max = i;
/*  47 */       this.v_min = this.function_window.getYStart();
/*  48 */       this.v_max = this.function_window.getYEnd();
/*     */       
/*  50 */       this.function_window.addMouseMotionListener(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void endDrag()
/*     */   {
/*  56 */     this.function_window.removeMouseMotionListener(this);
/*     */   }
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/*  61 */     if (this.horiz_enable) {
/*  62 */       int i = this.function_window.XPixToCht(paramMouseEvent.getX());
/*  63 */       int j = i - this.last_x;
/*  64 */       if ((i + j > this.h_min) && 
/*  65 */         (i + j < this.h_max)) {
/*  66 */         this.x_ += j;
/*  67 */         this.last_x = i;
/*     */       }
/*  69 */       else if (i + j <= this.h_min) {
/*  70 */         this.x_ = this.h_min;
/*  71 */         this.last_x = 0;
/*     */       }
/*     */       else {
/*  74 */         this.x_ = this.h_max;
/*  75 */         this.last_x = this.function_window.getChartWidth();
/*     */       }
/*     */     }
/*     */     
/*  79 */     if (this.vert_enable) {
/*  80 */       double d1 = this.function_window.YPixToCht(paramMouseEvent.getY());
/*  81 */       double d2 = d1 - this.last_y;
/*  82 */       if ((d1 + d2 > this.v_min) && 
/*  83 */         (d1 + d2 < this.v_max)) {
/*  84 */         this.value_ += d2;
/*  85 */         this.last_y = this.function_window.YPixToCht(paramMouseEvent.getY());
/*     */       }
/*  87 */       else if (d1 + d2 <= this.v_min) {
/*  88 */         this.value_ = this.v_min;
/*  89 */         this.last_y = this.function_window.getYStart();
/*     */       }
/*     */       else {
/*  92 */         this.value_ = this.v_max;
/*  93 */         this.last_y = this.function_window.getYEnd();
/*     */       }
/*     */     }
/*     */     
/*  97 */     this.function_window.changeValues();
/*  98 */     this.function_window.valueUpdate();
/*     */   }
/*     */   
/*     */   public boolean intersects(MouseEvent paramMouseEvent)
/*     */   {
/* 103 */     int i = this.function_window.XChtToPix(this.x_);
/* 104 */     int j = this.function_window.YChtToPix(this.value_);
/*     */     
/* 106 */     this.mouse_over = ((Math.abs(paramMouseEvent.getX() - i) <= 5) && 
/* 107 */       (Math.abs(paramMouseEvent.getY() - j) <= 5));
/*     */     
/* 109 */     return this.mouse_over;
/*     */   }
/*     */   
/* 112 */   public int getX() { return this.x_; }
/* 113 */   public void setX(int paramInt) { this.x_ = paramInt; }
/*     */   
/* 115 */   public double getValue() { return this.value_; }
/* 116 */   public void setValue(double paramDouble) { this.value_ = paramDouble; }
/*     */   
/* 118 */   public void setVisible(boolean paramBoolean) { this.is_visible = paramBoolean; }
/* 119 */   public void setActive(boolean paramBoolean) { this.is_active = paramBoolean; }
/* 120 */   public void setHorizontalEnable(boolean paramBoolean) { this.horiz_enable = paramBoolean; }
/* 121 */   public void setVerticalEnable(boolean paramBoolean) { this.vert_enable = paramBoolean; }
/* 122 */   public void setColor(java.awt.Color paramColor) { this.bar_color = paramColor; }
/* 123 */   public void setBoxColor(java.awt.Color paramColor) { this.box_color = paramColor; }
/*     */   
/*     */ 
/*     */ 
/* 127 */   protected transient boolean mouse_over = false;
/*     */   
/*     */   protected transient int last_x;
/*     */   protected transient double last_y;
/*     */   protected int x_;
/*     */   protected double value_;
/* 133 */   protected boolean is_visible = true;
/* 134 */   protected boolean is_active = true;
/* 135 */   protected boolean horiz_enable = false;
/* 136 */   protected boolean vert_enable = true;
/*     */   protected int h_min;
/*     */   protected int h_max;
/* 139 */   protected double v_min; protected double v_max; protected java.awt.Color bar_color = java.awt.Color.black;
/* 140 */   protected java.awt.Color box_color = java.awt.Color.darkGray;
/* 141 */   protected java.awt.Color active_box_color = java.awt.Color.blue;
/*     */   public static final int BOX_WIDTH = 5;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/discrete_convolution.jar!/DValue.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */