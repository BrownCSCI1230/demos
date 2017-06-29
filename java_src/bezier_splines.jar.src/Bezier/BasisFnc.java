/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Point;
/*    */ 
/*    */ public abstract class BasisFnc extends ParametricLine
/*    */ {
/*    */   String m_eqnText;
/*    */   Color m_color;
/*    */   Component m_cont;
/*    */   
/*    */   public BasisFnc(String eqnText, Color col)
/*    */   {
/* 17 */     this.m_eqnText = eqnText;
/* 18 */     this.m_color = col;
/* 19 */     this.PT_RADIUS = 2;
/* 20 */     this.PT_DIAMETER = (this.PT_RADIUS * 2);
/*    */   }
/*    */   
/*    */   public void setContainer(Component cont)
/*    */   {
/* 25 */     this.m_cont = cont;
/*    */   }
/*    */   
/*    */   public String getEqnText()
/*    */   {
/* 30 */     return this.m_eqnText;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void ptFromTVal(double t, Point currPt)
/*    */   {
/* 37 */     if (this.m_cont != null) {
/* 38 */       Dimension d = this.m_cont.size();
/*    */       
/* 40 */       double tVal = tVal(t);
/* 41 */       currPt.y = (d.height - 15 - new Double(tVal(t) * (d.height - 15 - 15)).intValue());
/*    */       
/*    */ 
/*    */ 
/* 45 */       currPt.x = (new Double(t * (d.width - 15 - 15)).intValue() + 15);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void Draw(Graphics g)
/*    */   {
/* 55 */     g.setColor(this.m_color);
/*    */     
/* 57 */     Point prevPt = new Point(0, 0);Point currPt = new Point(0, 0);
/*    */     
/* 59 */     for (double t = 0.0D; t < 1.01D; t += this.m_tValue) {
/* 60 */       ptFromTVal(t, currPt);
/*    */       
/* 62 */       if (t == 0.0D) {
/* 63 */         prevPt.x = currPt.x;
/* 64 */         prevPt.y = currPt.y;
/*    */       }
/*    */       
/* 67 */       g.drawLine(prevPt.x, prevPt.y, currPt.x, currPt.y);
/*    */       
/* 69 */       if (this.m_showTPoints) {
/* 70 */         g.fillOval(currPt.x - this.PT_RADIUS, currPt.y - this.PT_RADIUS, this.PT_DIAMETER, this.PT_DIAMETER);
/*    */       }
/*    */       
/* 73 */       prevPt.x = currPt.x;
/* 74 */       prevPt.y = currPt.y;
/*    */     }
/*    */   }
/*    */   
/*    */   public void highlightPoint(double t)
/*    */   {
/* 80 */     Graphics g = this.m_cont.getGraphics();
/* 81 */     if (g != null) {
/* 82 */       g.setColor(this.m_color);
/* 83 */       Point currPt = new Point(0, 0);
/* 84 */       ptFromTVal(t, currPt);
/* 85 */       g.fillOval(currPt.x - this.PT_RADIUS * 2, currPt.y - this.PT_RADIUS * 2, this.PT_DIAMETER * 2, this.PT_DIAMETER * 2);
/*    */     }
/*    */   }
/*    */   
/*    */   public abstract double tVal(double paramDouble);
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisFnc.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */