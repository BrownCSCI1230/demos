/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BezierSpline
/*    */   extends Spline
/*    */ {
/*    */   public BezierSpline() {}
/*    */   
/*    */   public BezierSpline(EquationCanvas qX, EquationCanvas qY, BasisFnc bas1, BasisFnc bas2, BasisFnc bas3, BasisFnc bas4)
/*    */   {
/* 16 */     super(bas1, bas2, bas3, bas4);this.m_eqnProc = new BezierEqnProcessor(qX, qY);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void CalcPtForTValue(double t, Point pt, Graphics g)
/*    */   {
/* 28 */     double one_m_t = 1.0D - t;
/* 29 */     double one_m_t_2 = one_m_t * one_m_t;
/* 30 */     double one_m_t_3 = one_m_t_2 * one_m_t;
/* 31 */     double t2 = t * t;
/* 32 */     double t3 = t2 * t;
/*    */     
/* 34 */     double p1 = one_m_t_3;
/* 35 */     double p2 = 3.0D * t * one_m_t_2;
/* 36 */     double p3 = 3.0D * t2 * one_m_t;
/* 37 */     double p4 = t3;
/*    */     
/* 39 */     if ((g != null) && (this.m_showColor)) {
/* 40 */       double red = (p1 * this.m_c1.getRed() + p2 * this.m_c2.getRed() + p3 * this.m_c3.getRed() + p4 * this.m_c4.getRed()) / (p1 + p2 + p3 + p4);
/*    */       
/* 42 */       double green = (p1 * this.m_c1.getGreen() + p2 * this.m_c2.getGreen() + p3 * this.m_c3.getGreen() + p4 * this.m_c4.getGreen()) / (p1 + p2 + p3 + p4);
/*    */       
/* 44 */       double blue = (p1 * this.m_c1.getBlue() + p2 * this.m_c2.getBlue() + p3 * this.m_c3.getBlue() + p4 * this.m_c4.getBlue()) / (p1 + p2 + p3 + p4);
/*    */       
/*    */ 
/* 47 */       g.setColor(new Color((int)red, (int)green, (int)blue));
/*    */     }
/*    */     
/* 50 */     pt.x = ((int)(this.m_p1.x * p1 + p2 * this.m_p2.x + p3 * this.m_p3.x + p4 * this.m_p4.x));
/*    */     
/*    */ 
/*    */ 
/* 54 */     pt.y = ((int)(this.m_p1.y * p1 + p2 * this.m_p2.y + p3 * this.m_p3.y + p4 * this.m_p4.y));
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BezierSpline.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */