package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class InsetMultipleImagesViewer
  extends JPanel
{
  protected static final int INSET_DEFAULT = 30;
  protected static final int FIRST_MARKER_SIZE = 1;
  protected static final int SECOND_MARKER_SIZE = 3;
  protected static final int OFFSET = 5;
  protected static final Color TEXT_LABEL_COLOR = Color.black;
  protected static final int TOP_SCOPE = 32;
  protected Color imageInsetsColor;
  protected Insets imageInsets;
  protected Rectangle rectBound;
  protected Rectangle filterRect;
  protected int size;
  protected ImageRectangle[] imageRect;
  protected BufferedImage[] image;
  protected int pixelSize = 1;
  protected int numColumns;
  protected int numRows;
  protected RectangleSamplePoint primaryMarker;
  protected RectangleSamplePoint secondaryMarker;
  protected Color transparent;
  protected Point point;
  protected String orient;
  protected ImageRectangle currRect;
  protected int currRow;
  protected int currColumn;
  protected boolean mouseFlag = false;
  protected OvalSamplePoint[] sample;
  protected String orientation;
  
  public InsetMultipleImagesViewer(int paramInt1, int paramInt2)
  {
    super(new BorderLayout());
    this.numColumns = paramInt1;
    this.numRows = paramInt2;
    this.imageInsets = new Insets(30, 30, 30, 30);
    this.imageInsetsColor = Color.white;
    setBackground(this.imageInsetsColor);
    setDoubleBuffered(true);
    this.imageRect = new ImageRectangle[3];
    for (int i = 0; i < 3; i++) {
      this.imageRect[i] = new ImageRectangle();
    }
    this.filterRect = new Rectangle();
    this.image = new BufferedImage[3];
    this.transparent = new Color(255, 255, 255, 0);
    this.primaryMarker = new RectangleSamplePoint(0, 0, this.transparent);
    this.secondaryMarker = new RectangleSamplePoint(0, 0, this.transparent);
    this.primaryMarker.setBorderVisible(false);
    this.secondaryMarker.setBorderVisible(false);
    this.sample = new OvalSamplePoint[5];
    for (int j = 0; j < 5; j++) {
      this.sample[j] = new OvalSamplePoint(0, 0, this.transparent);
    }
  }
  
  public void setNumRows(int paramInt)
  {
    this.numRows = paramInt;
  }
  
  public int getNumRows()
  {
    return this.numRows;
  }
  
  public void setNumColumns(int paramInt)
  {
    this.numColumns = paramInt;
  }
  
  public int getNumColumns()
  {
    return this.numColumns;
  }
  
  public void setInsetColor(Color paramColor)
  {
    this.imageInsetsColor = paramColor;
    if (getGraphics() != null) {
      paintComponent(getGraphics());
    }
  }
  
  public Color getInsetColor()
  {
    return this.imageInsetsColor;
  }
  
  public void setInsets(Insets paramInsets)
  {
    this.imageInsets = paramInsets;
    if (getGraphics() != null) {
      paintComponent(getGraphics());
    }
  }
  
  public Insets getInsets()
  {
    return this.imageInsets;
  }
  
  public RectangleSamplePoint getPixelMarker()
  {
    return this.primaryMarker;
  }
  
  public ImageRectangle getImageRect(int paramInt)
  {
    return this.imageRect[paramInt];
  }
  
  public ImageRectangle getCurrentRect()
  {
    return this.currRect;
  }
  
  public void setCurrentRect(ImageRectangle paramImageRectangle)
  {
    this.currRect = paramImageRectangle;
  }
  
  public void setImage(int paramInt, BufferedImage paramBufferedImage)
  {
    this.image[paramInt] = paramBufferedImage;
  }
  
  public int getCurrentRow()
  {
    return this.currRow;
  }
  
  public void setCurrentRow(int paramInt)
  {
    ImageRectangle localImageRectangle = getCurrentRect();
    this.currRow = ((paramInt - localImageRectangle.y) / getPixelSize());
  }
  
  public int getCurrentColumn()
  {
    return this.currColumn;
  }
  
  public void setCurrentColumn(int paramInt)
  {
    ImageRectangle localImageRectangle = getCurrentRect();
    this.currColumn = ((paramInt - localImageRectangle.x) / getPixelSize());
  }
  
  public void setMouseFlag(boolean paramBoolean)
  {
    this.mouseFlag = paramBoolean;
  }
  
  public void setPixelSize(int paramInt)
  {
    this.pixelSize = paramInt;
    this.primaryMarker.setWidth(this.pixelSize);
    this.primaryMarker.setHeight(this.pixelSize);
  }
  
  public int getPixelSize()
  {
    return this.pixelSize;
  }
  
  public void setOrientation(String paramString)
  {
    this.orientation = paramString;
  }
  
  public String getOrientation()
  {
    return this.orientation;
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    localGraphics2D.setPaint(this.imageInsetsColor);
    localGraphics2D.fill(new Rectangle(0, 0, getWidth(), getHeight()));
    this.rectBound = calcRectBound();
    int i = this.rectBound.width / 13;
    int j = (this.rectBound.width - i) / 3;
    j /= 32;
    j *= 32;
    setPixelSize(j / getNumColumns());
    this.filterRect.setBounds(this.rectBound.x + j + i, this.rectBound.y, j * 2, j);
    this.size = (this.filterRect.height / 8);
    this.imageRect[0].setBounds(this.rectBound.x, this.rectBound.y, j, j);
    this.imageRect[1].setBounds(this.rectBound.x, this.rectBound.y + j + i, j, j * 2);
    this.imageRect[2].setBounds(this.rectBound.x + j + i, this.rectBound.y + j + i, j * 2, j * 2);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawRect(this.rectBound.x - 20, this.rectBound.y - 15, this.imageRect[0].width + this.imageRect[2].width + i + 40, this.imageRect[0].height + this.imageRect[1].height + i + 40);
    paramGraphics.setColor(Color.gray);
    paramGraphics.drawRect(this.filterRect.x - 5, this.filterRect.y - 5, this.filterRect.width + 10, this.filterRect.height + 10);
    for (int k = 0; k < 3; k++) {
      paramGraphics.drawRect(this.imageRect[k].x, this.imageRect[k].y, this.imageRect[k].width, this.imageRect[k].height);
    }
    if (this.image[0] != null)
    {
      for (int m = 0; m < 3; m++)
      {
        localGraphics2D.drawImage(this.image[m], this.imageRect[m].x, this.imageRect[m].y, this.imageRect[m].width, this.imageRect[m].height, this);
        this.imageRect[m].setImage(this.image[m]);
        paramGraphics.drawRect(this.imageRect[m].x - 5, this.imageRect[m].y - 5, this.imageRect[m].width + 10, this.imageRect[m].height + 10);
      }
      paramGraphics.setColor(Color.gray);
      Rectangle localRectangle1 = new Rectangle(this.imageRect[0].x + this.imageRect[0].width / 2 - this.size / 2, this.imageRect[0].y + this.imageRect[0].height + this.size / 4, this.size, this.size);
      paramGraphics.fillRect(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height);
      Point localPoint1 = new Point(localRectangle1.x - this.size, localRectangle1.y + this.size);
      Point localPoint2 = new Point(localRectangle1.x + this.size / 2, localRectangle1.y + 2 * this.size);
      Point localPoint3 = new Point(localRectangle1.x + localRectangle1.width + this.size, localRectangle1.y + this.size);
      int[] arrayOfInt1 = new int[3];
      arrayOfInt1[0] = localPoint1.x;
      arrayOfInt1[1] = localPoint2.x;
      arrayOfInt1[2] = localPoint3.x;
      int[] arrayOfInt2 = new int[3];
      arrayOfInt2[0] = localPoint1.y;
      arrayOfInt2[1] = localPoint2.y;
      arrayOfInt2[2] = localPoint3.y;
      paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
      Rectangle localRectangle2 = new Rectangle(this.imageRect[1].x + this.imageRect[1].width + this.size / 4, this.imageRect[1].y + this.imageRect[1].height / 2 - this.size / 2, this.size, this.size);
      paramGraphics.fillRect(localRectangle2.x, localRectangle2.y, localRectangle2.width, localRectangle2.height);
      Point localPoint4 = new Point(localRectangle2.x + localRectangle2.width, localRectangle2.y - this.size);
      Point localPoint5 = new Point(localRectangle2.x + localRectangle2.width, localRectangle2.y + 2 * this.size);
      Point localPoint6 = new Point(localRectangle2.x + localRectangle2.width + this.size, localRectangle2.y + this.size / 2);
      int[] arrayOfInt3 = new int[3];
      arrayOfInt3[0] = localPoint4.x;
      arrayOfInt3[1] = localPoint5.x;
      arrayOfInt3[2] = localPoint6.x;
      int[] arrayOfInt4 = new int[3];
      arrayOfInt4[0] = localPoint4.y;
      arrayOfInt4[1] = localPoint5.y;
      arrayOfInt4[2] = localPoint6.y;
      paramGraphics.fillPolygon(arrayOfInt3, arrayOfInt4, 3);
      if (this.mouseFlag == true)
      {
        ImageRectangle localImageRectangle = getCurrentRect();
        this.primaryMarker.setBorderVisible(false);
        this.secondaryMarker.setBorderVisible(false);
        if ((localImageRectangle != null) && (getPixelSize() != 0))
        {
          int n = localImageRectangle.x + getCurrentColumn() * getPixelSize();
          int i1 = localImageRectangle.y + getCurrentRow() * getPixelSize();
          showMarker(n, i1, localImageRectangle);
          drawKernel(paramGraphics, this.filterRect);
          paramGraphics.setColor(Color.black);
          paramGraphics.drawLine(this.filterRect.x + 6 * this.filterRect.width / 11, this.filterRect.y + 3, this.filterRect.x + 6 * this.filterRect.width / 11, this.filterRect.y + this.filterRect.height - 3);
          paramGraphics.drawLine(this.filterRect.x + 6 * this.filterRect.width / 11, this.filterRect.y + 13 * this.filterRect.height / 18, this.filterRect.x + this.filterRect.width - 3, this.filterRect.y + 13 * this.filterRect.height / 18);
          paramGraphics.setColor(TEXT_LABEL_COLOR);
          paramGraphics.setFont(new Font("Helvetica", 0, this.filterRect.height / 18));
          paramGraphics.drawString("Colors involved: ", this.filterRect.x + 3 * this.filterRect.width / 5, this.filterRect.y + 2 * this.filterRect.height / 18);
          paramGraphics.drawString("Resulting Color Mix: ", this.filterRect.x + 3 * this.filterRect.width / 5, this.filterRect.y + 14 * this.filterRect.height / 18);
          Color localColor = new Color(localImageRectangle.getImage().getRGB((this.primaryMarker.getX() - localImageRectangle.x) / getPixelSize(), (this.primaryMarker.getY() - localImageRectangle.y) / getPixelSize()));
          OvalSamplePoint localOvalSamplePoint = new OvalSamplePoint(this.filterRect.x + 7 * this.filterRect.width / 8 + this.size / 2, this.filterRect.y + 5 * this.filterRect.height / 6 + this.size / 2, localColor);
          localOvalSamplePoint.setWidth(this.size);
          localOvalSamplePoint.setHeight(this.size);
          localOvalSamplePoint.setBorderVisible(true);
          localOvalSamplePoint.paint(paramGraphics);
          paramGraphics.setColor(Color.black);
          paramGraphics.setFont(new Font("Helvetica", 0, this.filterRect.height / 18));
          paramGraphics.drawString("R: " + localColor.getRed(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + 15 * this.filterRect.height / 18);
          paramGraphics.drawString("G: " + localColor.getGreen(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + 16 * this.filterRect.height / 18);
          paramGraphics.drawString("B: " + localColor.getBlue(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + 17 * this.filterRect.height / 18);
        }
      }
    }
    this.primaryMarker.paint(localGraphics2D);
    this.secondaryMarker.paint(localGraphics2D);
  }
  
  protected Rectangle calcRectBound()
  {
    int j;
    int k;
    int i;
    if ((getWidth() > 0) && (getHeight() > 0))
    {
      if (getWidth() > getHeight())
      {
        j = this.imageInsets.top;
        k = getHeight() - 2 * j;
        i = (getWidth() - k) / 2;
      }
      else
      {
        i = this.imageInsets.left;
        k = getWidth() - 2 * i;
        j = (getHeight() - k) / 2;
      }
    }
    else
    {
      i = 0;
      j = 0;
      k = 0;
    }
    return new Rectangle(i, j, k, k);
  }
  
  public void showMarker(int paramInt1, int paramInt2, ImageRectangle paramImageRectangle)
  {
    this.primaryMarker.setX(paramInt1);
    this.primaryMarker.setY(paramInt2);
    this.primaryMarker.setBorderVisible(true);
    ImageRectangle localImageRectangle = getSecondaryRect(paramImageRectangle);
    if (localImageRectangle != null)
    {
      double d1 = localImageRectangle.height / paramImageRectangle.height;
      double d2 = localImageRectangle.width / paramImageRectangle.width;
      this.secondaryMarker.setHeight(getPixelSize());
      this.secondaryMarker.setWidth(getPixelSize());
      if (d1 != 1.0D)
      {
        paramInt2 -= paramImageRectangle.y;
        paramInt2 -= getPixelSize() / 2;
        paramInt2 = (int)(paramInt2 * d1);
        paramInt2 += localImageRectangle.y;
        paramInt2 -= 1 * getPixelSize();
        this.secondaryMarker.setHeight(3 * getPixelSize());
        paramInt1 = paramInt1 - paramImageRectangle.x + localImageRectangle.x;
        setOrientation("vertical");
      }
      else if (d2 != 1.0D)
      {
        paramInt1 -= paramImageRectangle.x;
        paramInt1 -= getPixelSize() / 2;
        paramInt1 = (int)(paramInt1 * d2);
        paramInt1 += localImageRectangle.x;
        paramInt1 -= 1 * getPixelSize();
        this.secondaryMarker.setWidth(3 * getPixelSize());
        paramInt2 = paramInt2 - paramImageRectangle.y + localImageRectangle.y;
        setOrientation("horizontal");
      }
      this.secondaryMarker.setX(paramInt1);
      this.secondaryMarker.setY(paramInt2);
      this.secondaryMarker.setBorderVisible(true);
    }
    else
    {
      setOrientation("not");
    }
  }
  
  public void drawKernel(Graphics paramGraphics, Rectangle paramRectangle)
  {
    Point localPoint1 = null;
    Point localPoint2 = null;
    Point localPoint3 = null;
    Point[] arrayOfPoint = new Point[5];
    if (getOrientation() == "not")
    {
      paramGraphics.setColor(Color.white);
      paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
    }
    else
    {
      this.currRect = getCurrentRect();
      ImageRectangle localImageRectangle = getSecondaryRect(this.currRect);
      double d1;
      double d2;
      int i;
      int j;
      int k;
      OvalSamplePoint localOvalSamplePoint1;
      OvalSamplePoint localOvalSamplePoint2;
      OvalSamplePoint localOvalSamplePoint3;
      OvalSamplePoint localOvalSamplePoint4;
      Color localColor;
      double d3;
      double d4;
      if (getOrientation() == "vertical")
      {
        localPoint1 = new Point(paramRectangle.x + paramRectangle.width / 4, (int)(paramRectangle.y + paramRectangle.height / 5));
        localPoint2 = new Point(paramRectangle.x + paramRectangle.width / 4, (int)(paramRectangle.y + paramRectangle.height / 5 * 4));
        localPoint3 = new Point((int)(paramRectangle.x + paramRectangle.width / 4 + 2.0D * (paramRectangle.height / 5.0D) / 3.0D), paramRectangle.y + paramRectangle.height / 2);
        drawTriangle(paramGraphics, localPoint1, localPoint2, localPoint3);
        paramGraphics.setColor(Color.red);
        paramGraphics.drawLine(paramRectangle.x + paramRectangle.width / 8, paramRectangle.y, paramRectangle.x + paramRectangle.width / 8, paramRectangle.y + paramRectangle.height);
        paramGraphics.drawLine(localPoint1.x - 5, localPoint1.y, localPoint1.x - 3 * paramRectangle.width / 16, localPoint1.y);
        paramGraphics.drawLine(localPoint2.x - 5, localPoint2.y, localPoint2.x - 3 * paramRectangle.width / 16, localPoint2.y);
        d1 = this.secondaryMarker.getY() + this.secondaryMarker.getHeight() / 2;
        d1 -= localImageRectangle.y;
        d1 -= d1 % getPixelSize();
        d1 = d1 + localImageRectangle.y - getPixelSize() / 2;
        d2 = d1;
        for (i = 0; (d1 > localImageRectangle.y) && (d1 > this.secondaryMarker.getY() - getPixelSize()); i++)
        {
          arrayOfPoint[i] = new Point(this.secondaryMarker.getX() + getPixelSize() / 2, (int)d1);
          d1 -= getPixelSize();
        }
        d2 += getPixelSize();
        d1 = d2;
        while ((d1 < localImageRectangle.y + localImageRectangle.height) && (d1 < this.secondaryMarker.getY() + this.secondaryMarker.getHeight() + getPixelSize()))
        {
          arrayOfPoint[i] = new Point(this.secondaryMarker.getX() + getPixelSize() / 2, (int)d1);
          d1 += getPixelSize();
          i++;
        }
        d1 = this.secondaryMarker.getY() + this.secondaryMarker.getHeight() / 2;
        j = 1;
        for (k = 0; k < 5; k++)
        {
          localOvalSamplePoint1 = null;
          localOvalSamplePoint2 = null;
          localOvalSamplePoint3 = null;
          localOvalSamplePoint4 = null;
          if (arrayOfPoint[k] != null)
          {
            localColor = new Color(localImageRectangle.getImage().getRGB((arrayOfPoint[k].x - localImageRectangle.x) / getPixelSize(), (arrayOfPoint[k].y - localImageRectangle.y) / getPixelSize()));
            d3 = arrayOfPoint[k].y - d1;
            d3 /= getPixelSize();
            d3 = d3 * paramRectangle.height / 5.0D;
            localOvalSamplePoint1 = new OvalSamplePoint(paramRectangle.x + paramRectangle.width / 8, (int)(paramRectangle.y + paramRectangle.height / 2 + d3), localColor);
            localOvalSamplePoint1.setWidth(this.size);
            localOvalSamplePoint1.setHeight(this.size);
            localOvalSamplePoint1.setBorderVisible(true);
            if ((localOvalSamplePoint1.getY() > localPoint1.y) && (localOvalSamplePoint1.getY() < localPoint2.y))
            {
              if (localOvalSamplePoint1.getY() - localPoint1.y < (localPoint2.y - localPoint1.y) / 2) {
                d4 = (localOvalSamplePoint1.getY() - localPoint1.y) * Math.tan(Math.atan(0.4182243D));
              } else {
                d4 = (localPoint2.y - localOvalSamplePoint1.getY()) * Math.tan(Math.atan(0.4182243D));
              }
              paramGraphics.setColor(localColor);
              paramGraphics.fillRect(localOvalSamplePoint1.getX(), localOvalSamplePoint1.getY() - this.size / 4, (int)(paramRectangle.width / 8 + d4), this.size / 2);
              paramGraphics.setColor(Color.black);
              paramGraphics.drawRect(localOvalSamplePoint1.getX(), localOvalSamplePoint1.getY() - this.size / 4, (int)(paramRectangle.width / 8 + d4), this.size / 2);
              localOvalSamplePoint2 = new OvalSamplePoint(paramRectangle.x + 7 * paramRectangle.width / 8 + this.size / 2, paramRectangle.y + j * paramRectangle.height / 6 + this.size / 2, localColor);
              localOvalSamplePoint2.setWidth(this.size);
              localOvalSamplePoint2.setHeight(this.size);
              localOvalSamplePoint2.setBorderVisible(true);
              localOvalSamplePoint2.paint(paramGraphics);
              paramGraphics.setColor(TEXT_LABEL_COLOR);
              paramGraphics.setFont(new Font("Helvetica", 0, this.filterRect.height / 18));
              paramGraphics.drawString("R: " + localColor.getRed(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              paramGraphics.drawString("G: " + localColor.getGreen(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + 2 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              paramGraphics.drawString("B: " + localColor.getGreen(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + 3 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              if (j != 3)
              {
                paramGraphics.setColor(Color.gray);
                paramGraphics.drawLine(this.filterRect.x + 6 * this.filterRect.width / 10, this.filterRect.y + 3 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18, this.filterRect.x + 17 * this.filterRect.width / 20, this.filterRect.y + 3 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              }
              j++;
            }
            localOvalSamplePoint1.paint(paramGraphics);
            localOvalSamplePoint3 = new OvalSamplePoint(arrayOfPoint[k].x, arrayOfPoint[k].y, Color.red);
            localOvalSamplePoint3.setWidth(getPixelSize() / 5);
            localOvalSamplePoint3.setHeight(getPixelSize() / 5);
            localOvalSamplePoint3.setOutsideMarkerColor(this.transparent);
            localOvalSamplePoint3.setInsideMarkerColor(Color.black);
            localOvalSamplePoint3.paint(paramGraphics);
            localOvalSamplePoint4 = new OvalSamplePoint(localOvalSamplePoint1.getX(), localOvalSamplePoint1.getY(), Color.red);
            localOvalSamplePoint4.setWidth(this.size / 5);
            localOvalSamplePoint4.setHeight(this.size / 5);
            localOvalSamplePoint4.setOutsideMarkerColor(this.transparent);
            localOvalSamplePoint4.setInsideMarkerColor(Color.black);
            localOvalSamplePoint4.paint(paramGraphics);
          }
        }
      }
      else if (getOrientation() == "horizontal")
      {
        localPoint1 = new Point(paramRectangle.x + paramRectangle.width / 4, (int)(paramRectangle.y + paramRectangle.height / 2 - 2.0D * (paramRectangle.width / 10.0D) / 3.0D));
        localPoint2 = new Point((int)(paramRectangle.x + paramRectangle.width / 10.0D), paramRectangle.y + paramRectangle.height / 2);
        localPoint3 = new Point((int)(paramRectangle.x + paramRectangle.width / 5 * 2.0D), paramRectangle.y + paramRectangle.height / 2);
        drawTriangle(paramGraphics, localPoint1, localPoint2, localPoint3);
        paramGraphics.setColor(Color.red);
        paramGraphics.drawLine(paramRectangle.x, paramRectangle.y + paramRectangle.height / 4 * 3, paramRectangle.x + paramRectangle.width / 2, paramRectangle.y + paramRectangle.height / 4 * 3);
        paramGraphics.drawLine(localPoint2.x, localPoint2.y + 5, localPoint2.x, localPoint2.y + 3 * paramRectangle.height / 8);
        paramGraphics.drawLine(localPoint3.x, localPoint3.y + 5, localPoint3.x, localPoint3.y + 3 * paramRectangle.height / 8);
        d1 = this.secondaryMarker.getX() + this.secondaryMarker.getWidth() / 2;
        d1 -= localImageRectangle.x;
        d1 -= d1 % getPixelSize();
        d1 = d1 + localImageRectangle.x - getPixelSize() / 2;
        d2 = d1;
        for (i = 0; (d1 > localImageRectangle.x) && (d1 > this.secondaryMarker.getX() - getPixelSize()); i++)
        {
          arrayOfPoint[i] = new Point((int)d1, this.secondaryMarker.getY() + getPixelSize() / 2);
          d1 -= getPixelSize();
        }
        d2 += getPixelSize();
        d1 = d2;
        while ((d1 < localImageRectangle.x + localImageRectangle.width) && (d1 < this.secondaryMarker.getX() + this.secondaryMarker.getWidth() + getPixelSize()))
        {
          arrayOfPoint[i] = new Point((int)d1, this.secondaryMarker.getY() + getPixelSize() / 2);
          d1 += getPixelSize();
          i++;
        }
        d1 = this.secondaryMarker.getX() + this.secondaryMarker.getWidth() / 2;
        j = 1;
        for (k = 0; k < 5; k++)
        {
          localOvalSamplePoint1 = null;
          localOvalSamplePoint2 = null;
          localOvalSamplePoint3 = null;
          localOvalSamplePoint4 = null;
          if (arrayOfPoint[k] != null)
          {
            localColor = new Color(localImageRectangle.getImage().getRGB((arrayOfPoint[k].x - localImageRectangle.x) / getPixelSize(), (arrayOfPoint[k].y - localImageRectangle.y) / getPixelSize()));
            d3 = arrayOfPoint[k].x - d1;
            d3 /= getPixelSize();
            d3 = d3 * paramRectangle.width / 10.0D;
            localOvalSamplePoint1 = new OvalSamplePoint((int)(paramRectangle.x + paramRectangle.width / 4 + d3), paramRectangle.y + paramRectangle.height / 4 * 3, localColor);
            localOvalSamplePoint1.setWidth(this.size);
            localOvalSamplePoint1.setHeight(this.size);
            localOvalSamplePoint1.setBorderVisible(true);
            if ((localOvalSamplePoint1.getX() > localPoint2.x) && (localOvalSamplePoint1.getX() < localPoint3.x))
            {
              if (localOvalSamplePoint1.getX() - localPoint2.x < (localPoint3.x - localPoint2.x) / 2) {
                d4 = (localOvalSamplePoint1.getX() - localPoint2.x) * Math.tan(Math.atan(0.4182243D));
              } else {
                d4 = (localPoint3.x - localOvalSamplePoint1.getX()) * Math.tan(Math.atan(0.4182243D));
              }
              paramGraphics.setColor(localColor);
              paramGraphics.fillRect(localOvalSamplePoint1.getX() - this.size / 4, (int)(localOvalSamplePoint1.getY() - paramRectangle.height / 4 - d4), this.size / 2, (int)(paramRectangle.height / 4 + d4));
              paramGraphics.setColor(Color.black);
              paramGraphics.drawRect(localOvalSamplePoint1.getX() - this.size / 4, (int)(localOvalSamplePoint1.getY() - paramRectangle.height / 4 - d4), this.size / 2, (int)(paramRectangle.height / 4 + d4));
              localOvalSamplePoint2 = new OvalSamplePoint(paramRectangle.x + 7 * paramRectangle.width / 8 + this.size / 2, paramRectangle.y + j * paramRectangle.height / 6 + this.size / 2, localColor);
              localOvalSamplePoint2.setWidth(this.size);
              localOvalSamplePoint2.setHeight(this.size);
              localOvalSamplePoint2.setBorderVisible(true);
              localOvalSamplePoint2.paint(paramGraphics);
              paramGraphics.setColor(TEXT_LABEL_COLOR);
              paramGraphics.setFont(new Font("Helvetica", 0, this.filterRect.height / 18));
              paramGraphics.drawString("R: " + localColor.getRed(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              paramGraphics.drawString("G: " + localColor.getGreen(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + 2 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              paramGraphics.drawString("B: " + localColor.getGreen(), this.filterRect.x + 7 * this.filterRect.width / 10, this.filterRect.y + 3 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              if (j != 3)
              {
                paramGraphics.setColor(Color.gray);
                paramGraphics.drawLine(this.filterRect.x + 6 * this.filterRect.width / 10, this.filterRect.y + 3 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18, this.filterRect.x + 17 * this.filterRect.width / 20, this.filterRect.y + 3 * this.filterRect.height / 18 + 3 * j * this.filterRect.height / 18);
              }
              j++;
            }
            localOvalSamplePoint1.paint(paramGraphics);
            localOvalSamplePoint3 = new OvalSamplePoint(arrayOfPoint[k].x, arrayOfPoint[k].y, Color.red);
            localOvalSamplePoint3.setWidth(getPixelSize() / 5);
            localOvalSamplePoint3.setHeight(getPixelSize() / 5);
            localOvalSamplePoint3.setOutsideMarkerColor(this.transparent);
            localOvalSamplePoint3.setInsideMarkerColor(Color.black);
            localOvalSamplePoint3.paint(paramGraphics);
            localOvalSamplePoint4 = new OvalSamplePoint(localOvalSamplePoint1.getX(), localOvalSamplePoint1.getY(), Color.red);
            localOvalSamplePoint4.setWidth(this.size / 5);
            localOvalSamplePoint4.setHeight(this.size / 5);
            localOvalSamplePoint4.setOutsideMarkerColor(this.transparent);
            localOvalSamplePoint4.setInsideMarkerColor(Color.black);
            localOvalSamplePoint4.paint(paramGraphics);
          }
        }
      }
    }
  }
  
  public ImageRectangle getSecondaryRect(Rectangle paramRectangle)
  {
    ImageRectangle localImageRectangle = null;
    if (paramRectangle == getImageRect(0))
    {
      localImageRectangle = null;
      this.secondaryMarker.setBorderVisible(false);
    }
    else if (paramRectangle == getImageRect(1))
    {
      localImageRectangle = getImageRect(0);
    }
    else if (paramRectangle == getImageRect(2))
    {
      localImageRectangle = getImageRect(1);
    }
    return localImageRectangle;
  }
  
  public void drawTriangle(Graphics paramGraphics, Point paramPoint1, Point paramPoint2, Point paramPoint3)
  {
    paramGraphics.setColor(Color.black);
    paramGraphics.drawLine(paramPoint1.x, paramPoint1.y, paramPoint2.x, paramPoint2.y);
    paramGraphics.drawLine(paramPoint2.x, paramPoint2.y, paramPoint3.x, paramPoint3.y);
    paramGraphics.drawLine(paramPoint3.x, paramPoint3.y, paramPoint1.x, paramPoint1.y);
  }
  
  public void showMarkers(boolean paramBoolean)
  {
    this.primaryMarker.setBorderVisible(paramBoolean);
    this.secondaryMarker.setBorderVisible(paramBoolean);
    paint(getGraphics());
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/InsetMultipleImagesViewer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */