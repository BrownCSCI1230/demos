/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ParametricLine
/*    */ {
/*    */   protected int PT_RADIUS;
/*    */   protected int PT_DIAMETER;
/*    */   protected double m_tValue;
/*    */   protected boolean m_showTPoints;
/*    */   Point m_origPt;
/*    */   
/*    */   public ParametricLine(double t)
/*    */   {
/* 20 */     this.PT_RADIUS = 3;this.PT_DIAMETER = (this.PT_RADIUS * 2);this.m_tValue = 0.05D;this.m_showTPoints = true;this.m_origPt = new Point(0, 0);this.m_tValue = t;
/*    */   }
/*    */   
/*    */   public ParametricLine()
/*    */   {
/* 25 */     this.PT_RADIUS = 3;this.PT_DIAMETER = (this.PT_RADIUS * 2);this.m_tValue = 0.05D;this.m_showTPoints = true;this.m_origPt = new Point(0, 0);
/*    */   }
/*    */   
/* 28 */   public void flipShowTPoints() { this.m_showTPoints = (!this.m_showTPoints); }
/*    */   
/* 30 */   public void setTValue(double t) { this.m_tValue = t; }
/*    */   
/*    */   protected boolean MouseOnPt(int x, int y, Point pt)
/*    */   {
/* 34 */     if ((x >= pt.x - this.PT_DIAMETER) && (x <= pt.x + this.PT_DIAMETER) && (y >= pt.y - this.PT_DIAMETER) && (y <= pt.y + this.PT_DIAMETER))
/*    */     {
/* 36 */       return true;
/*    */     }
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/ParametricLine.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */