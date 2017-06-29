/*    */ package Bezier;
/*    */ 
/*    */ import corejava.Format;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BezierEqnProcessor
/*    */   extends EqnProcessor
/*    */ {
/*    */   public BezierEqnProcessor(EquationCanvas qX, EquationCanvas qY)
/*    */   {
/* 15 */     super(qX, qY);this.m_qXDefaultStr = "Q(x) = (1-t)^3P1 + 3t(1-t)^2P2 + 3t^2(1-t)P3 + t^3P4";
/* 16 */     this.m_qYDefaultStr = "Q(y) = (1-t)^3P1 + 3t(1-t)^2P2 + 3t^2(1-t)P3 + t^3P4";
/* 17 */     update();
/*    */   }
/*    */   
/*    */ 
/*    */   protected void updateEqnStrings()
/*    */   {
/* 23 */     String tStr = new Format("%4.2f").form(this.m_t);
/* 24 */     String x1Str = new Format("%3i").form(this.m_p1.x);
/* 25 */     String x2Str = new Format("%3i").form(this.m_p2.x);
/* 26 */     String x3Str = new Format("%3i").form(this.m_p3.x);
/* 27 */     String x4Str = new Format("%3i").form(this.m_p4.x);
/* 28 */     String xcStr = new Format("%3i").form(this.m_currPt.x);
/* 29 */     String y1Str = new Format("%3i").form(this.m_p1.y);
/* 30 */     String y2Str = new Format("%3i").form(this.m_p2.y);
/* 31 */     String y3Str = new Format("%3i").form(this.m_p3.y);
/* 32 */     String y4Str = new Format("%3i").form(this.m_p4.y);
/* 33 */     String ycStr = new Format("%3i").form(this.m_currPt.y);
/*    */     
/* 35 */     this.m_qXStr = (xcStr + " = (1-" + tStr + ")^3*" + x1Str + " + 3*" + tStr + "*(1-" + tStr + ")^2*" + x2Str + " + 3*" + tStr + "^2*(1-" + tStr + ")*" + x3Str + " + " + tStr + "^3*" + x4Str);
/*    */     
/* 37 */     this.m_qYStr = (ycStr + " = (1-" + tStr + ")^3*" + y1Str + " + 3*" + tStr + "*(1-" + tStr + ")^2*" + y2Str + " + 3*" + tStr + "^2*(1-" + tStr + ")*" + y3Str + " + " + tStr + "^3*" + y4Str);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BezierEqnProcessor.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */