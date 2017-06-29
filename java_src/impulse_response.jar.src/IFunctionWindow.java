/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ public class IFunctionWindow extends javax.swing.JLayeredPane implements java.io.Serializable, java.awt.event.ComponentListener, java.awt.event.MouseListener, java.awt.event.MouseMotionListener
/*     */ {
/*     */   public IFunctionWindow()
/*     */   {
/*  16 */     setOpaque(true);
/*     */     
/*     */ 
/*     */ 
/*  20 */     setDoubleBuffered(true);
/*     */     
/*  22 */     this.border_ = javax.swing.BorderFactory.createTitledBorder(
/*  23 */       javax.swing.BorderFactory.createRaisedBevelBorder(), 
/*  24 */       "Function", 
/*  25 */       0, 
/*  26 */       3);
/*  27 */     setBorder(this.border_);
/*     */     
/*  29 */     addMouseListener(this);
/*  30 */     addMouseMotionListener(this);
/*  31 */     addComponentListener(this);
/*     */     
/*  33 */     this.prev_size = getSize();
/*     */     
/*  35 */     this.value_ = new DValue(this, 263, 1.0D);
/*  36 */     this.value_.setHorizontalEnable(true);
/*  37 */     this.value_.setVerticalEnable(false);
/*     */   }
/*     */   
/*     */   public void setController(IFunctionController paramIFunctionController)
/*     */   {
/*  42 */     this.function_controller = paramIFunctionController;
/*  43 */     this.function_controller.setFunctionWindow(this);
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
/*  56 */     createBacking(paramGraphics);
/*  57 */     createGraph(paramGraphics);
/*     */     
/*  59 */     Rectangle localRectangle = paramGraphics.getClipBounds();
/*  60 */     paramGraphics.drawImage(this.chart_img, 
/*  61 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  62 */       localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, 
/*  63 */       null);
/*     */     
/*  65 */     this.prev_size = getSize();
/*     */     
/*  67 */     this.redraw_backing = false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void createGraph(Graphics paramGraphics)
/*     */   {
/*  73 */     if ((this.chart_img == null) || 
/*  74 */       (!this.prev_size.equals(getSize())) || 
/*  75 */       (this.redraw_backing))
/*     */     {
/*  77 */       if (this.chart_img == null) {
/*  78 */         realloc();
/*     */       }
/*  80 */       this.chart_img = createImage(getSize().width, getSize().height);
/*  81 */       this.chart_gfx = this.chart_img.getGraphics();
/*  82 */       this.chart_gfx.setFont(getFont());
/*     */       
/*  84 */       this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.white, null);
/*     */       
/*  86 */       Insets localInsets = getInsets();
/*     */       int i;
/*     */       int j;
/*  89 */       int k; int m; int n; int i1; if (this.show_numbers) {
/*  90 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/*  91 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/*  92 */         k = localInsets.left + i + 4;
/*  93 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/*  94 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/*  95 */         n = m + 4;
/*  96 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/*  99 */         i = 1;
/* 100 */         j = 0;
/* 101 */         k = 6;
/* 102 */         m = 1;
/* 103 */         n = 6;
/* 104 */         i1 = 0;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 109 */       int i2 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 110 */       int i3 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 112 */       if ((i2 != this.chart_width) || (i3 != this.chart_height)) {
/* 113 */         realloc();
/*     */       }
/* 115 */       this.value_.draw(this.chart_gfx);
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
/*     */   public void createBacking(Graphics paramGraphics)
/*     */   {
/* 132 */     if ((this.graphpaper_img == null) || 
/* 133 */       (!this.prev_size.equals(getSize())) || 
/* 134 */       (this.redraw_backing))
/*     */     {
/*     */ 
/*     */ 
/* 138 */       this.graphpaper_img = createImage(getSize().width, getSize().height);
/* 139 */       this.graphpaper_gfx = this.graphpaper_img.getGraphics();
/* 140 */       this.graphpaper_gfx.setFont(getFont());
/*     */       
/*     */ 
/* 143 */       Insets localInsets = getInsets();
/*     */       int i;
/* 145 */       int j; int k; int m; int n; int i1; if (this.show_numbers) {
/* 146 */         i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 147 */         j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
/* 148 */         k = localInsets.left + i + 4;
/* 149 */         m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 150 */           this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 151 */         n = m + 4;
/* 152 */         i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
/*     */       }
/*     */       else {
/* 155 */         i = 1;
/* 156 */         j = 0;
/* 157 */         k = 6;
/* 158 */         m = 1;
/* 159 */         n = 6;
/* 160 */         i1 = 0;
/*     */       }
/*     */       
/* 163 */       int i2 = k + 1;
/* 164 */       int i3 = localInsets.top + 1 + m;
/* 165 */       int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
/* 166 */       int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
/*     */       
/* 168 */       this.graphpaper_gfx.setColor(Color.lightGray);
/* 169 */       this.graphpaper_gfx.fillRect(0, 0, getSize().width, getSize().height);
/*     */       
/* 171 */       this.graphpaper_gfx.setColor(getForeground());
/* 172 */       this.graphpaper_gfx.drawRect(i2 - 1, i3 - 1, i4 + 2, i5 + 2);
/*     */       
/* 174 */       this.graphpaper_gfx.setColor(this.bg_color);
/* 175 */       this.graphpaper_gfx.fillRect(i2, i3, i4, i5);
/*     */       
/*     */ 
/* 178 */       double d1 = this.x_start;
/*     */       String str;
/*     */       int i6;
/* 181 */       for (int i7 = 0; i7 < this.x_divisions; i7++) {
/* 182 */         int i8 = (int)(i2 + i4 * i7 / (this.x_divisions - 1.0D));
/*     */         
/* 184 */         if (this.show_numbers) {
/* 185 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 186 */           str = new Double(d1).toString();
/* 187 */           if (d1 < 0.0D) {
/* 188 */             i6 = Math.min(5, str.length());
/*     */           } else
/* 190 */             i6 = Math.min(4, str.length());
/* 191 */           str = str.substring(0, i6);
/*     */           
/*     */ 
/* 194 */           this.graphpaper_gfx.drawString(str, i8 - j * str.length() / 2, i3 + i5 + i1 + 4);
/* 195 */           d1 += (this.x_end - this.x_start) / (this.x_divisions - 1.0D);
/*     */         }
/*     */         
/* 198 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 199 */         this.graphpaper_gfx.drawLine(i8, i3, i8, i3 + i5);
/*     */       }
/*     */       
/*     */ 
/* 203 */       double d2 = this.y_end;
/* 204 */       for (int i9 = 0; i9 < this.y_divisions; i9++) {
/* 205 */         i10 = (int)(i3 + i5 * i9 / (this.y_divisions - 1.0D));
/*     */         
/* 207 */         if (this.show_numbers) {
/* 208 */           this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 209 */           str = new Double(d2).toString();
/* 210 */           i6 = Math.min(4, str.length());
/* 211 */           str = str.substring(0, i6);
/*     */           
/* 213 */           this.graphpaper_gfx.drawString(str, localInsets.left + 2, i10 + 2);
/* 214 */           d2 -= (this.y_end - this.y_start) / (this.y_divisions - 1.0D);
/*     */         }
/*     */         
/* 217 */         this.graphpaper_gfx.setColor(this.secondary_cht_color);
/* 218 */         this.graphpaper_gfx.drawLine(i2, i10, i2 + i4, i10);
/*     */       }
/*     */       
/*     */ 
/* 222 */       this.graphpaper_gfx.setColor(this.primary_cht_color);
/* 223 */       int i10 = (int)(-this.x_start / (this.x_end - this.x_start) * i4) + i2;
/* 224 */       if ((i10 >= i2) && (i10 <= i2 + i4)) {
/* 225 */         this.graphpaper_gfx.drawLine(i10, i3, i10, i3 + i5);
/*     */       }
/* 227 */       int i11 = (int)(i3 + i5 - -this.y_start / (this.y_end - this.y_start) * i5);
/* 228 */       if ((i11 >= i3) && (i11 <= i3 + i5)) {
/* 229 */         this.graphpaper_gfx.drawLine(i2, i11, i2 + i4, i11);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void realloc()
/*     */   {
/* 236 */     Insets localInsets = getInsets();
/*     */     int i;
/* 238 */     int j; int k; int m; if ((this.graphpaper_gfx != null) && (this.show_numbers)) {
/* 239 */       i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
/* 240 */       j = localInsets.left + i + 4;
/*     */       
/* 242 */       k = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + 
/* 243 */         this.graphpaper_gfx.getFontMetrics().getMaxDescent();
/* 244 */       m = k + 4;
/*     */     }
/*     */     else {
/* 247 */       i = 1;
/* 248 */       j = 6;
/*     */       
/* 250 */       k = 1;
/* 251 */       m = 6;
/*     */     }
/*     */     
/* 254 */     this.chart_x = (j + 1);
/* 255 */     this.chart_y = (localInsets.top + 1 + k);
/* 256 */     this.chart_width = (getSize().width - localInsets.right - j - 2 - i / 2);
/* 257 */     this.chart_height = (getSize().height - (localInsets.top + localInsets.bottom + m + 2) - k);
/*     */   }
/*     */   
/*     */   public void changeValues()
/*     */   {
/* 262 */     if ((this.graphpaper_gfx == null) || (this.chart_gfx == null)) {
/* 263 */       System.err.println("Internal error: Graphics Contexts not initialized in CTFunctionWindow.changeValues()");
/* 264 */       return;
/*     */     }
/*     */     
/*     */ 
/* 268 */     this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.white, null);
/*     */     
/* 270 */     this.value_.draw(this.chart_gfx);
/*     */     
/* 272 */     repaint();
/*     */   }
/*     */   
/*     */   public DValue getDValue()
/*     */   {
/* 277 */     return this.value_;
/*     */   }
/*     */   
/*     */ 
/*     */   public int XPixToCht(int paramInt)
/*     */   {
/* 283 */     return paramInt - this.chart_x;
/*     */   }
/*     */   
/*     */   public int XChtToPix(int paramInt)
/*     */   {
/* 288 */     return paramInt + this.chart_x;
/*     */   }
/*     */   
/*     */   public double YPixToCht(int paramInt)
/*     */   {
/* 293 */     return (this.chart_y + this.chart_height - paramInt) * (this.y_end - this.y_start) / this.chart_height + this.y_start;
/*     */   }
/*     */   
/*     */   public int YChtToPix(double paramDouble)
/*     */   {
/* 298 */     return (int)(this.chart_y + this.chart_height - (paramDouble - this.y_start) * (this.chart_height / (this.y_end - this.y_start)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 317 */     if (this.value_.intersects(paramMouseEvent)) {
/* 318 */       this.function_controller.beginUpdate(this.value_);
/* 319 */       this.value_.beginDrag(paramMouseEvent);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */   public void mouseMoved(MouseEvent paramMouseEvent)
/*     */   {
/* 329 */     boolean bool = false;
/* 330 */     if (this.value_.intersects(paramMouseEvent)) {
/* 331 */       bool = true;
/*     */     }
/*     */     
/* 334 */     if ((bool) || (this.was_foo)) {
/* 335 */       changeValues();
/*     */     }
/* 337 */     this.was_foo = bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {}
/*     */   
/*     */ 
/*     */   public void valueUpdate()
/*     */   {
/* 347 */     this.function_controller.valueUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */   public void componentResized(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */ 
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {}
/*     */   
/* 360 */   public Dimension getMinimumSize() { return new Dimension(150, 150); }
/* 361 */   public Dimension getPreferredSize() { return new Dimension(600, 300); }
/*     */   
/* 363 */   public int getChartHeight() { return this.chart_height; }
/* 364 */   public int getChartWidth() { return this.chart_width; }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean)
/*     */   {
/* 368 */     super.setEnabled(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTitle(String paramString)
/*     */   {
/* 376 */     this.border_.setTitle(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 382 */     return this.border_.getTitle();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setForeground(Color paramColor)
/*     */   {
/* 392 */     this.redraw_backing = true;
/* 393 */     super.setForeground(paramColor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFont(java.awt.Font paramFont)
/*     */   {
/* 404 */     this.redraw_backing = true;
/* 405 */     super.setFont(paramFont);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphBackgroundColor()
/*     */   {
/* 414 */     return this.bg_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphBackgroundColor(Color paramColor)
/*     */   {
/* 423 */     this.redraw_backing = true;
/* 424 */     this.bg_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getGraphColor()
/*     */   {
/* 433 */     return this.graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGraphColor(Color paramColor)
/*     */   {
/* 442 */     this.redraw_backing = true;
/* 443 */     this.graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getActiveGraphColor()
/*     */   {
/* 452 */     return this.active_graph_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setActiveGraphColor(Color paramColor)
/*     */   {
/* 461 */     this.redraw_backing = true;
/* 462 */     this.active_graph_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getPrimaryChartColor()
/*     */   {
/* 470 */     return this.primary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPrimaryChartColor(Color paramColor)
/*     */   {
/* 478 */     this.redraw_backing = true;
/* 479 */     this.primary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getSecondaryChartColor()
/*     */   {
/* 487 */     return this.secondary_cht_color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSecondaryChartColor(Color paramColor)
/*     */   {
/* 495 */     this.redraw_backing = true;
/* 496 */     this.secondary_cht_color = paramColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getXDivisions()
/*     */   {
/* 504 */     return this.x_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXDivisions(int paramInt)
/*     */   {
/* 512 */     this.redraw_backing = true;
/* 513 */     this.x_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getYDivisions()
/*     */   {
/* 521 */     return this.y_divisions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYDivisions(int paramInt)
/*     */   {
/* 529 */     this.redraw_backing = true;
/* 530 */     this.y_divisions = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXStart()
/*     */   {
/* 538 */     return this.x_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXStart(double paramDouble)
/*     */   {
/* 546 */     this.redraw_backing = true;
/* 547 */     this.x_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getXEnd()
/*     */   {
/* 555 */     return this.x_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXEnd(double paramDouble)
/*     */   {
/* 563 */     this.redraw_backing = true;
/* 564 */     this.x_end = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYStart()
/*     */   {
/* 572 */     return this.y_start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYStart(double paramDouble)
/*     */   {
/* 580 */     this.redraw_backing = true;
/* 581 */     this.y_start = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getYEnd()
/*     */   {
/* 589 */     return this.y_end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYEnd(double paramDouble)
/*     */   {
/* 597 */     this.redraw_backing = true;
/* 598 */     this.y_end = paramDouble;
/*     */   }
/*     */   
/* 601 */   protected boolean show_numbers = true;
/* 602 */   protected Color bg_color = Color.white;
/* 603 */   protected Color graph_color = Color.darkGray;
/* 604 */   protected Color active_graph_color = Color.blue;
/* 605 */   protected Color primary_cht_color = Color.black;
/* 606 */   protected Color secondary_cht_color = Color.lightGray;
/* 607 */   protected int x_divisions = 17;
/* 608 */   protected int y_divisions = 5;
/* 609 */   protected double x_start = -8.0D;
/* 610 */   protected double x_end = 8.0D;
/*     */   protected double y_start;
/* 612 */   protected double y_end = 1.0D;
/*     */   
/*     */   protected transient DValue value_;
/*     */   
/*     */   protected transient javax.swing.border.TitledBorder border_;
/*     */   protected transient Dimension prev_size;
/*     */   protected transient java.awt.Image graphpaper_img;
/*     */   protected transient Graphics graphpaper_gfx;
/*     */   protected transient java.awt.Image chart_img;
/*     */   protected transient Graphics chart_gfx;
/* 622 */   protected transient boolean redraw_backing = true;
/*     */   protected transient int chart_x;
/* 624 */   protected transient int chart_y; protected transient int chart_width; protected transient int chart_height; protected transient boolean was_foo = false;
/*     */   protected transient IFunctionController function_controller;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/impulse_response.jar!/IFunctionWindow.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */