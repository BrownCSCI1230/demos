/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ public class CTFunctionWindow extends JLayeredPane implements java.io.Serializable, java.awt.event.ComponentListener, java.awt.event.MouseListener, java.awt.event.MouseMotionListener
/*     */ {
/*     */   public CTFunctionWindow()
/*     */   {
/*  24 */     setOpaque(true);
/*     */     
/*     */ 
/*     */ 
/*  28 */     setDoubleBuffered(true);
/*     */     
/*  30 */     this.border_ = BorderFactory.createTitledBorder(
/*  31 */       BorderFactory.createRaisedBevelBorder(), 
/*  32 */       "Function", 
/*  33 */       0, 
/*  34 */       3);
/*  35 */     setBorder(this.border_);
/*     */     
/*  37 */     addMouseListener(this);
/*  38 */     addComponentListener(this);
/*     */     
/*  40 */     this.prev_size = getSize();
/*     */   }
/*     */   
/*     */   public void setController(CTFunctionController paramCTFunctionController)
/*     */   {
/*  45 */     this.function_controller = paramCTFunctionController;
/*  46 */     this.function_controller.setFunctionWindow(this);
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
/*     */   public void paintComponent(Graphics paramGraphics)
/*     */   {
/*  59 */     createBacking(paramGraphics);
/*  60 */     createGraph(paramGraphics);
/*     */     
/*  62 */     Rectangle localRectangle = paramGraphics.getClipBounds();
/*  63 */     paramGraphics.drawImage(this.chart_img, 
/*  64 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  65 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  66 */       null);
/*     */     
/*  68 */     this.prev_size = getSize();
/*     */     
/*  70 */     if (this.marker_bar > -1) {
/*  71 */       paramGraphics.setColor(Color.red);
/*  72 */       double d = this.chart_height / (this.y_end - this.y_start);
/*  73 */       int i = (int)(-this.y_start * d);
/*  74 */       int j = (int)((this.graph_values[this.marker_bar] - this.y_start) * d);
/*  75 */       paramGraphics.drawLine(this.marker_bar + this.chart_x, this.chart_y + this.chart_height - i, 
/*  76 */         this.marker_bar + this.chart_x, this.chart_y + this.chart_height - j);
/*     */     }
/*     */     
/*  79 */     this.redraw_backing = false;
/*  80 */     this.active_changed = false;
/*     */   }
/*     */   
/*     */   public void createGraph(Graphics paramGraphics)
/*     */   {
/*  85 */     if ((this.chart_img == null) || 
/*  86 */       (!this.prev_size.equals(getSize())) || 
/*  87 */       (this.redraw_backing) || (this.active_changed))
/*     */     {
/*  89 */       if (this.chart_img == null) {
/*  90 */         realloc();
/*     */       }
/*  92 */       this.chart_img = createImage(getSize().width, getSize().height);
/*  93 */       this.chart_gfx = this.chart_img.getGraphics();
/*  94 */       this.chart_gfx.setFont(getFont());
/*     */       
/*  96 */       this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.white, null);
/*     */       
/*  98 */       Insets localInsets = getInsets();
/*     */       int i;
/*     */       int j;
/* 101 */       int k; int m; int n; int i1; if (this.show_numbers) {
/* 102 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 103 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/* 104 */         k = localInsets.left + i + 4;
/* 105 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 106 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 107 */         n = m + 4;
/* 108 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/* 111 */         i = 1;
/* 112 */         j = 0;
/* 113 */         k = 6;
/* 114 */         m = 1;
/* 115 */         n = 6;
/* 116 */         i1 = 0;
/*     */       }
/*     */       
/* 119 */       int i2 = k + 1;
/* 120 */       int i3 = localInsets.top + 1 + m;
/* 121 */       int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 122 */       int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 124 */       if ((i4 != this.chart_width) || (i5 != this.chart_height)) {
/* 125 */         realloc();
/*     */       }
/* 127 */       if (this.is_active) {
/* 128 */         this.chart_gfx.setColor(this.active_graph_color);
/*     */       } else {
/* 130 */         this.chart_gfx.setColor(this.graph_color);
/*     */       }
/*     */       
/* 133 */       double d = i5 / (this.y_end - this.y_start);
/*     */       int i8;
/*     */       int i6;
/* 136 */       if (this.graph_filled) {
/* 137 */         i8 = (int)(-this.y_start * d);
/* 138 */         if (i8 < 0)
/* 139 */           i8 = 0;
/* 140 */         if (i8 > i5) {
/* 141 */           i8 = i5;
/*     */         }
/* 143 */         for (int i9 = 0; i9 < i4; i9++) {
/* 144 */           i6 = (int)((this.graph_values[i9] - this.y_start) * d);
/* 145 */           this.chart_gfx.drawLine(i9 + i2, i3 + i5 - i8, i9 + i2, i3 + i5 - i6);
/*     */         }
/*     */       }
/*     */       else {
/* 149 */         int i7 = (int)((this.graph_values[0] - this.y_start) * d);
/* 150 */         for (i8 = 0; i8 < i4 - 1; i8++) {
/* 151 */           i6 = i7;
/* 152 */           i7 = (int)((this.graph_values[(i8 + 1)] - this.y_start) * d);
/* 153 */           this.chart_gfx.drawLine(i8 + i2, i3 + i5 - i6, i8 + i2 + 1, i3 + i5 - i7);
/*     */         }
/*     */       }
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
/*     */   public void createBacking(Graphics paramGraphics)
/*     */   {
/* 171 */     if ((this.graphpaper_img == null) || 
/* 172 */       (!this.prev_size.equals(getSize())) || 
/* 173 */       (this.redraw_backing))
/*     */     {
/*     */ 
/*     */ 
/* 177 */       this.graphpaper_img = createImage(getSize().width, getSize().height);
/* 178 */       this.graphpaper_gfx = this.graphpaper_img.getGraphics();
/* 179 */       this.graphpaper_gfx.setFont(getFont());
/*     */       
/*     */ 
/* 182 */       Insets localInsets = getInsets();
/*     */       int i;
/* 184 */       int j; int k; int m; int n; int i1; if (this.show_numbers) {
/* 185 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 186 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/* 187 */         k = localInsets.left + i + 4;
/* 188 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 189 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 190 */         n = m + 4;
/* 191 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/* 194 */         i = 1;
/* 195 */         j = 0;
/* 196 */         k = 6;
/* 197 */         m = 1;
/* 198 */         n = 6;
/* 199 */         i1 = 0;
/*     */       }
/*     */       
/* 202 */       int i2 = k + 1;
/* 203 */       int i3 = localInsets.top + 1 + m;
/* 204 */       int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 205 */       int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 207 */       this.graphpaper_gfx.setColor(Color.lightGray);
/* 208 */       this.graphpaper_gfx.fillRect(0, 0, getSize().width, getSize().height);
/*     */       
/* 210 */       this.graphpaper_gfx.setColor(getForeground());
/* 211 */       this.graphpaper_gfx.drawRect(i2 - 1, i3 - 1, i4 + 2, i5 + 2);
/*     */       
/* 213 */       this.graphpaper_gfx.setColor(this.bg_color);
/* 214 */       this.graphpaper_gfx.fillRect(i2, i3, i4, i5);
/*     */       
/*     */ 
/* 217 */       double d1 = this.x_start;
/*     */       String str;
/*     */       int i6;
/* 220 */       for (int i7 = 0; i7 < this.x_divisions; i7++) {
/* 221 */         int i8 = (int)(i2 + i4 * i7 / (this.x_divisions - 1.0D));
/*     */         
/* 223 */         if (this.show_numbers) {
/* 224 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 225 */           str = new Double(d1).toString();
/* 226 */           if (d1 < 0.0D) {
/* 227 */             i6 = Math.min(5, str.length());
/*     */           } else
/* 229 */             i6 = Math.min(4, str.length());
/* 230 */           str = str.substring(0, i6);
/*     */           
/*     */ 
/* 233 */           this.graphpaper_gfx.drawString(str, i8 - j * str.length() / 2, i3 + i5 + i1 + 2);
/* 234 */           d1 += (this.x_end - this.x_start) / (this.x_divisions - 1.0D);
/*     */         }
/*     */         
/* 237 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 238 */         this.graphpaper_gfx.drawLine(i8, i3, i8, i3 + i5);
/*     */       }
/*     */       
/*     */ 
/* 242 */       double d2 = this.y_end;
/* 243 */       for (int i9 = 0; i9 < this.y_divisions; i9++) {
/* 244 */         i10 = (int)(i3 + i5 * i9 / (this.y_divisions - 1.0D));
/*     */         
/* 246 */         if (this.show_numbers) {
/* 247 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 248 */           str = new Double(d2).toString();
/* 249 */           i6 = Math.min(4, str.length());
/* 250 */           str = str.substring(0, i6);
/*     */           
/* 252 */           this.graphpaper_gfx.drawString(str, localInsets.left + 2, i10 + 2);
/* 253 */           d2 -= (this.y_end - this.y_start) / (this.y_divisions - 1.0D);
/*     */         }
/*     */         
/* 256 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 257 */         this.graphpaper_gfx.drawLine(i2, i10, i2 + i4, i10);
/*     */       }
/*     */       
/*     */ 
/* 261 */       this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 262 */       int i10 = (int)(-this.x_start / (this.x_end - this.x_start) * i4) + i2;
/* 263 */       if ((i10 >= i2) && (i10 <= i2 + i4)) {
/* 264 */         this.graphpaper_gfx.drawLine(i10, i3, i10, i3 + i5);
/*     */       }
/* 266 */       int i11 = (int)(i3 + i5 - -this.y_start / (this.y_end - this.y_start) * i5);
/* 267 */       if ((i11 >= i3) && (i11 <= i3 + i5)) {
/* 268 */         this.graphpaper_gfx.drawLine(i2, i11, i2 + i4, i11);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void changeValues(int paramInt1, int paramInt2, double[] paramArrayOfDouble) {
/* 274 */     if ((this.graphpaper_gfx == null) || (this.chart_gfx == null)) {
/* 275 */       System.err.println("Internal error: Graphics Contexts not initialized in CTFunctionWindow.changeValues()");
/* 276 */       return;
/*     */     }
/*     */     
/*     */ 
/* 280 */     int i = paramInt1;
/* 281 */     if (i < 0) {
/* 282 */       i = 0;
/*     */     }
/* 284 */     int j = paramInt1 + paramInt2;
/* 285 */     if (j > this.chart_width) {
/* 286 */       j = this.chart_width;
/*     */     }
/* 288 */     this.chart_gfx.drawImage(this.graphpaper_img, 
/* 289 */       i + this.chart_x, this.chart_y, j + this.chart_x, this.chart_y + this.chart_height, 
/* 290 */       i + this.chart_x, this.chart_y, j + this.chart_x, this.chart_y + this.chart_height, 
/* 291 */       null);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */     if (this.is_active) {
/* 299 */       this.chart_gfx.setColor(this.active_graph_color);
/*     */     } else {
/* 301 */       this.chart_gfx.setColor(this.graph_color);
/*     */     }
/*     */     
/* 304 */     double d = this.chart_height / (this.y_end - this.y_start);
/*     */     try { int n;
/*     */       int i2;
/*     */       int i1;
/* 308 */       int k; if (this.graph_filled) {
/* 309 */         n = (int)(-this.y_start * d);
/* 310 */         if (n < 0)
/* 311 */           n = 0;
/* 312 */         if (n > this.chart_height) {
/* 313 */           n = this.chart_height;
/*     */         }
/*     */         
/* 316 */         for (i2 = 0; i2 < paramInt2; i2++) {
/* 317 */           i1 = i2 + paramInt1;
/* 318 */           this.graph_values[i1] = paramArrayOfDouble[i2];
/* 319 */           if (this.graph_values[i1] > this.y_end)
/* 320 */             this.graph_values[i1] = this.y_end;
/* 321 */           if (this.graph_values[i1] < this.y_start)
/* 322 */             this.graph_values[i1] = this.y_start;
/* 323 */           k = (int)((this.graph_values[i1] - this.y_start) * d);
/*     */           
/* 325 */           this.chart_gfx.drawLine(i2 + paramInt1 + this.chart_x, this.chart_y + this.chart_height - n, 
/* 326 */             i2 + paramInt1 + this.chart_x, this.chart_y + this.chart_height - k);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 331 */         for (i1 = 0; i1 < paramInt2; i1++) {
/* 332 */           n = i1 + paramInt1;
/* 333 */           this.graph_values[n] = paramArrayOfDouble[i1];
/* 334 */           if (this.graph_values[n] > this.y_end)
/* 335 */             this.graph_values[n] = this.y_end;
/* 336 */           if (this.graph_values[n] < this.y_start) {
/* 337 */             this.graph_values[n] = this.y_start;
/*     */           }
/*     */         }
/* 340 */         i2 = paramInt1 + paramInt2 - 1;
/* 341 */         int m = (int)((this.graph_values[paramInt1] - this.y_start) * d);
/* 342 */         if (paramInt1 == i2) {
/* 343 */           this.chart_gfx.drawLine(paramInt1 + this.chart_x, this.chart_y + this.chart_height - m, 
/* 344 */             paramInt1 + this.chart_x, this.chart_y + this.chart_height - m);
/*     */         }
/*     */         else {
/* 347 */           for (int i3 = paramInt1; i3 < i2; i3++) {
/* 348 */             k = m;
/* 349 */             m = (int)((this.graph_values[(i3 + 1)] - this.y_start) * d);
/* 350 */             this.chart_gfx.drawLine(i3 + this.chart_x, this.chart_y + this.chart_height - k, 
/* 351 */               i3 + this.chart_x + 1, this.chart_y + this.chart_height - m);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 357 */       System.err.println("Internal error: Function Change Event arry out of bounds.");
/*     */     }
/*     */     
/* 360 */     repaint();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void realloc()
/*     */   {
/* 370 */     Insets localInsets = getInsets();
/*     */     int i;
/* 372 */     int j; int k; int m; if ((this.graphpaper_gfx != null) && (this.show_numbers)) {
/* 373 */       i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 374 */       j = localInsets.left + i + 4;
/*     */       
/* 376 */       k = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 377 */         this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 378 */       m = k + 4;
/*     */     }
/*     */     else {
/* 381 */       i = 1;
/* 382 */       j = 6;
/*     */       
/* 384 */       k = 1;
/* 385 */       m = 6;
/*     */     }
/*     */     
/* 388 */     this.chart_x = (j + 1);
/* 389 */     this.chart_y = (localInsets.top + 1 + k);
/* 390 */     this.chart_width = (getSize().width - localInsets.right - j - 2 - i / 2);
/* 391 */     this.chart_height = (getSize().height - (localInsets.top + localInsets.bottom + m + 2) - k);
/*     */     
/*     */ 
/* 394 */     this.graph_values = new double[this.chart_width];
/*     */     
/* 396 */     this.function_controller.resize(this.chart_width, this.chart_height);
/* 397 */     ConvolutionSlide.__instance.fixApplet();
/*     */   }
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent)
/*     */   {
/* 402 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/* 404 */     int i = paramMouseEvent.getX() - this.chart_x;
/* 405 */     if ((i < 0) || (i >= this.chart_width)) {
/* 406 */       return;
/*     */     }
/* 408 */     int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
/* 409 */     if (j < 0)
/* 410 */       j = 0;
/* 411 */     if (j > this.chart_height) {
/* 412 */       j = this.chart_height;
/*     */     }
/* 414 */     this.function_controller.mouseStart(i, j);
/* 415 */     this.function_controller.mouseStop();
/*     */   }
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent)
/*     */   {
/* 420 */     this.is_active = true;
/* 421 */     this.active_changed = true;
/* 422 */     repaint();
/*     */   }
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent)
/*     */   {
/* 427 */     this.is_active = false;
/* 428 */     this.active_changed = true;
/* 429 */     repaint();
/*     */   }
/*     */   
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 434 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/* 436 */     addMouseMotionListener(this);
/*     */     
/* 438 */     int i = paramMouseEvent.getX() - this.chart_x;
/* 439 */     if ((i < 0) || (i >= this.chart_width)) {
/* 440 */       return;
/*     */     }
/* 442 */     int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
/* 443 */     if (j < 0)
/* 444 */       j = 0;
/* 445 */     if (j > this.chart_height) {
/* 446 */       j = this.chart_height;
/*     */     }
/* 448 */     this.function_controller.mouseStart(i, j);
/*     */   }
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 453 */     this.function_controller.mouseStop();
/*     */     
/* 455 */     removeMouseMotionListener(this);
/*     */     
/* 457 */     if (!this.graph_filled)
/* 458 */       this.active_changed = true;
/* 459 */     repaint();
/*     */   }
/*     */   
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/* 464 */     int i = paramMouseEvent.getX() - this.chart_x;
/* 465 */     if (i < 0) {
/* 466 */       i = 0;
/* 467 */     } else if (i >= this.chart_width - 1) {
/* 468 */       i = this.chart_width - 2;
/*     */     }
/* 470 */     int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
/* 471 */     if (j < 0)
/* 472 */       j = 0;
/* 473 */     if (j > this.chart_height) {
/* 474 */       j = this.chart_height;
/*     */     }
/* 476 */     this.function_controller.mouseDrag(i, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseMoved(MouseEvent paramMouseEvent)
/*     */   {
/* 483 */     mouseDragged(paramMouseEvent);
/*     */   }
/*     */   
/*     */   public void componentResized(ComponentEvent paramComponentEvent)
/*     */   {
/* 488 */     realloc();
/* 489 */     this.function_controller.resize(this.chart_width, this.chart_height);
/* 490 */     ConvolutionSlide.__instance.fixSlider();
/*     */   }
/*     */   
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/* 499 */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) { this.change_support.addPropertyChangeListener(paramPropertyChangeListener); }
/*     */   
/*     */ 
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 504 */     this.change_support.removePropertyChangeListener(paramPropertyChangeListener);
/*     */   }
/*     */   
/* 507 */   public Dimension getMinimumSize() { return new Dimension(150, 150); }
/* 508 */   public Dimension getPreferredSize() { return new Dimension(600, 300); }
/*     */   
/* 510 */   public int getChartHeight() { return this.chart_height; }
/* 511 */   public int getChartWidth() { return this.chart_width; }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean)
/*     */   {
/* 515 */     super.setEnabled(paramBoolean);
/*     */     
/* 517 */     if (paramBoolean == false) {
/* 518 */       removeMouseListener(this);
/* 519 */       this.is_active = false;
/* 520 */       this.active_changed = true;
/* 521 */       repaint();
/*     */     }
/*     */     else {
/* 524 */       addMouseListener(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTitle(String paramString)
/*     */   {
/* 533 */     this.border_.setTitle(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 539 */     return this.border_.getTitle();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getGraphFilled()
/*     */   {
/* 546 */     return this.graph_filled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphFilled(boolean paramBoolean)
/*     */   {
/* 554 */     this.redraw_backing = true;
/* 555 */     this.graph_filled = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setForeground(Color paramColor)
/*     */   {
/* 566 */     this.redraw_backing = true;
/* 567 */     super.setForeground(paramColor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFont(Font paramFont)
/*     */   {
/* 578 */     this.redraw_backing = true;
/* 579 */     super.setFont(paramFont);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphBackgroundColor()
/*     */   {
/* 588 */     return this.bg_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphBackgroundColor(Color paramColor)
/*     */   {
/* 597 */     this.redraw_backing = true;
/* 598 */     this.bg_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphColor()
/*     */   {
/* 607 */     return this.graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphColor(Color paramColor)
/*     */   {
/* 616 */     this.redraw_backing = true;
/* 617 */     this.graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getActiveGraphColor()
/*     */   {
/* 626 */     return this.active_graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setActiveGraphColor(Color paramColor)
/*     */   {
/* 635 */     this.redraw_backing = true;
/* 636 */     this.active_graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getPrimaryChartColor()
/*     */   {
/* 644 */     return this.primary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPrimaryChartColor(Color paramColor)
/*     */   {
/* 652 */     this.redraw_backing = true;
/* 653 */     this.primary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getSecondaryChartColor()
/*     */   {
/* 661 */     return this.secondary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSecondaryChartColor(Color paramColor)
/*     */   {
/* 669 */     this.redraw_backing = true;
/* 670 */     this.secondary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getXDivisions()
/*     */   {
/* 678 */     return this.x_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXDivisions(int paramInt)
/*     */   {
/* 686 */     this.redraw_backing = true;
/* 687 */     this.x_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getYDivisions()
/*     */   {
/* 695 */     return this.y_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYDivisions(int paramInt)
/*     */   {
/* 703 */     this.redraw_backing = true;
/* 704 */     this.y_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXStart()
/*     */   {
/* 712 */     return this.x_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXStart(double paramDouble)
/*     */   {
/* 720 */     this.redraw_backing = true;
/* 721 */     this.x_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXEnd()
/*     */   {
/* 729 */     return this.x_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXEnd(double paramDouble)
/*     */   {
/* 737 */     this.redraw_backing = true;
/* 738 */     this.x_end = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYStart()
/*     */   {
/* 746 */     return this.y_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYStart(double paramDouble)
/*     */   {
/* 754 */     this.redraw_backing = true;
/* 755 */     this.y_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYEnd()
/*     */   {
/* 763 */     return this.y_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYEnd(double paramDouble)
/*     */   {
/* 771 */     this.redraw_backing = true;
/* 772 */     this.y_end = paramDouble;
/*     */   }
/*     */   
/* 775 */   public void setMarker(int paramInt) { this.marker_bar = paramInt; }
/*     */   
/* 777 */   protected boolean graph_filled = true;
/* 778 */   protected boolean show_numbers = true;
/* 779 */   protected Color bg_color = Color.white;
/* 780 */   protected Color graph_color = Color.darkGray;
/* 781 */   protected Color active_graph_color = Color.blue;
/* 782 */   protected Color primary_cht_color = Color.black;
/* 783 */   protected Color secondary_cht_color = Color.lightGray;
/* 784 */   protected int x_divisions = 9;
/* 785 */   protected int y_divisions = 5;
/* 786 */   protected double x_start = -4.0D;
/* 787 */   protected double x_end = 4.0D;
/* 788 */   protected double y_start = -1.0D;
/* 789 */   protected double y_end = 1.0D;
/* 790 */   protected int marker_bar = -1;
/*     */   
/*     */   protected transient double[] graph_values;
/*     */   protected transient TitledBorder border_;
/*     */   protected transient Dimension prev_size;
/*     */   protected transient Image graphpaper_img;
/*     */   protected transient Graphics graphpaper_gfx;
/*     */   protected transient Image chart_img;
/*     */   protected transient Graphics chart_gfx;
/* 799 */   protected transient boolean redraw_backing = true;
/*     */   protected transient boolean is_active;
/*     */   protected transient boolean active_changed;
/*     */   protected transient int chart_x;
/* 803 */   protected transient int chart_y; protected transient int chart_width; protected transient int chart_height; protected transient PropertyChangeSupport change_support = new PropertyChangeSupport(this);
/*     */   protected transient CTFunctionController function_controller;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/CTFunctionWindow.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */