/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Point;
/*    */ 
/*    */ public abstract class EqnProcessor {
/*    */   protected double m_t;
/*    */   protected Point m_p1;
/*    */   protected Point m_p2;
/*    */   protected Point m_p3;
/*    */   protected Point m_p4;
/*    */   protected Point m_currPt;
/*    */   protected String m_qXStr;
/*    */   protected String m_qYStr;
/*    */   protected String m_qXDefaultStr;
/*    */   protected String m_qYDefaultStr;
/*    */   protected EquationCanvas m_qX;
/*    */   protected EquationCanvas m_qY;
/*    */   
/* 19 */   public EqnProcessor(EquationCanvas qX, EquationCanvas qY) { this.m_qX = qX;
/* 20 */     this.m_qY = qY;
/*    */   }
/*    */   
/*    */   protected abstract void updateEqnStrings();
/*    */   
/*    */   public void update(Point p1, Point p2, Point p3, Point p4, Point currPt, double t) {
/* 26 */     this.m_p1 = p1;
/* 27 */     this.m_p2 = p2;
/* 28 */     this.m_p3 = p3;
/* 29 */     this.m_p4 = p4;
/* 30 */     this.m_t = t;
/* 31 */     this.m_currPt = currPt;
/*    */     
/* 33 */     updateEqnStrings();
/*    */     
/* 35 */     this.m_qX.setText(this.m_qXStr);
/* 36 */     this.m_qY.setText(this.m_qYStr);
/*    */   }
/*    */   
/*    */   public void update()
/*    */   {
/* 41 */     this.m_qX.setText(this.m_qXDefaultStr);
/* 42 */     this.m_qY.setText(this.m_qYDefaultStr);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/EqnProcessor.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */