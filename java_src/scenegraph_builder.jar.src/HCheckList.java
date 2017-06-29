/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Font;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HCheckList
/*    */   extends JPanel
/*    */ {
/* 20 */   private static final Color BACKGROUND = new Color(205, 190, 180);
/*    */   private String[] _listItems;
/*    */   private JLabel _goals;
/*    */   
/*    */   public HCheckList() {
/* 25 */     setBackground(BACKGROUND);
/* 26 */     setBorder(BorderFactory.createLoweredBevelBorder());
/* 27 */     setLayout(new BoxLayout(this, 1));
/*    */     
/* 29 */     this._goals = new JLabel("Goals");
/* 30 */     this._goals.setBackground(BACKGROUND);
/* 31 */     this._goals.setFont(new Font("SansSerif", 1, 16));
/*    */     
/* 33 */     add(this._goals);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void changeList(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 42 */     removeAll();
/* 43 */     add(this._goals);
/* 44 */     for (int i = 0; i < paramInt; i++) {
/* 45 */       JCheckBox localJCheckBox = new JCheckBox(paramArrayOfString[i]);
/* 46 */       add(localJCheckBox);
/* 47 */       localJCheckBox.setBackground(BACKGROUND);
/*    */     }
/* 49 */     revalidate();
/* 50 */     repaint();
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HCheckList.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */