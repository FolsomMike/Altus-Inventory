/*******************************************************************************
* Title: JSplitButton.java
* Author: Hunter Schoonover
* Date: 08/14/15
*
* Purpose:
*
* This class is an implementation of a "split" button. The left side acts like
* a normal button, right side has a jPopupMenu attached.
* 
* The source code used was largely based on the Google jSplitButton.
*
*/

//------------------------------------------------------------------------------

package hscomponents.jsplitbutton;

//------------------------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// class JSplitButton
//

public class JSplitButton extends JButton implements MouseMotionListener, 
                                MouseListener, ActionListener, PopupMenuListener
{

    private int separatorSpacing = 4;
    private int splitWidth = 22;
    private int arrowSize = 8;
    private boolean onButton;
    private boolean onSplit;
    private Rectangle splitRectangle;
    private JPopupMenu popupMenu;
    private boolean alwaysDropDown;
    private boolean isShowingPopup;
    private boolean hidePopup;
    private Color arrowColor = Color.BLACK;
    private Color disabledArrowColor = Color.GRAY;
    private Image image;
    protected SplitButtonActionListener splitButtonActionListener = null;
    
    //--------------------------------------------------------------------------
    // JSplitButton::JSplitButton (constructor)
    /**
     * Creates a button with no set text or icon.
     */
    
    public JSplitButton() 
    {
        
        super();
        
    }//end of JSplitButton::JSplitButton (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::JSplitButton (constructor)
    /** 
     * Creates a button with text.
     * 
     * @param pText the text of the button 
     */
    
    public JSplitButton(String pText) 
    {
        
        super(pText);
        
    }//end of JSplitButton::JSplitButton (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::JSplitButton (constructor)
    /** 
     * Creates a button with an icon.
     * 
     * @param pIcon the Icon image to display on the button 
     */
    
    public JSplitButton(Icon pIcon) 
    {
        
        super(pIcon);
        
    }//end of JSplitButton::JSplitButton (constructor)
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::JSplitButton (constructor)
    /**
     * Creates a button with initial text and an icon.
     * 
     * @param pText the text of the button
     * 
     * @param pIcon the Icon image to display on the button
     */
    
    public JSplitButton(String pText, Icon pIcon) 
    {
        
        super(pText, pIcon);
        
    }//end of JSplitButton::JSplitButton (constructor)
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::init
    /**
     * Initializes the object. Must be called immediately after instantiation.
     */

    public void init()
    {

        addMouseMotionListener(this);
        addMouseListener(this);
        addActionListener(this);
        
    }// end of JSplitButton::init
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::actionPerformed
    /**
     * Called when an action is performed.
     * 
     * @param e <code>ActionEvent</code>
     */
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
        if (popupMenu == null) { fireButtonClicked(e); }
        else if (alwaysDropDown) {
            hideOrShowPopup();
            fireButtonClicked(e);
        }
        else if (onSplit) {
            hideOrShowPopup();
            fireSplitbuttonClicked(e);
        }
        else { fireButtonClicked(e); }
        
    }// end of JSplitButton::actionPerformed
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::mouseExited
    /**
     * Called when the mouse exits the button.
     * 
     * @param e <code>MouseEvent</code>
     */

    @Override
    public void mouseExited(MouseEvent e) 
    {
        
        onButton = false;
        onSplit = false;
        repaint(splitRectangle);
        
    }// end of JSplitButton::mouseExited
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::mouseMoved
    /**
     * Called when the mouse moves.
     * 
     * @param e <code>MouseEvent</code>
     */
    
    @Override
    public void mouseMoved(MouseEvent e) 
    {
        
        if (contains(e.getPoint())) { onButton = true; }
        else { onButton = false; }
        
        if (splitRectangle.contains(e.getPoint())) { onSplit = true; } 
        else { onSplit = false; }
        repaint(splitRectangle);
        
    }// end of JSplitButton::mouseMoved
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::paintComponent
    /**
     * Paints the button.
     * 
     * @param g 
     */
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        
        super.paintComponent(g);
        
        Graphics gClone = g.create();
        Color oldColor = gClone.getColor();
        splitRectangle = new Rectangle(getWidth() - splitWidth, 0, splitWidth, 
                                        getHeight());
        gClone.translate(splitRectangle.x, splitRectangle.y);
        int mh = getHeight() / 2;
        int mw = splitWidth / 2;
        gClone.drawImage(getImage(), mw - arrowSize / 2, 
                            mh + 2 - arrowSize / 2, null);
        
        if (onSplit && !alwaysDropDown && popupMenu != null) {
            gClone.setColor(UIManager.getLookAndFeelDefaults()
                                .getColor("Button.background"));
            gClone.drawLine(1, separatorSpacing + 2, 1, 
                                getHeight() - separatorSpacing - 2);
            gClone.setColor(UIManager.getLookAndFeelDefaults()
                                .getColor("Button.shadow"));
            gClone.drawLine(2, separatorSpacing + 2, 2, 
                                getHeight() - separatorSpacing - 2);
        }
        
        gClone.setColor(oldColor);
        
    }// end of JSplitButton::paintComponent
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::popupMenuCanceled
    /**
     * Called when the popup menu was canceled.
     * Sets flags indicating that the popup menu is invisible.
     * 
     * @param pme   <code>PopupMenuEvent</code>
     */
    
    @Override
    public void popupMenuCanceled(PopupMenuEvent pme) {
        
        isShowingPopup = false;
        if(onSplit || (alwaysDropDown&&onButton)) { hidePopup = true; }
        
    }// end of JSplitButton::popupMenuCanceled
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::popupMenuWillBecomeInvisible
    /**
     * Called when the popup menu is about to become invisible.
     * Sets flags indicating that the popup menu is invisible.
     * 
     * @param pme   <code>PopupMenuEvent</code>
     */
    
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
        
        isShowingPopup = false;
        if(onSplit || (alwaysDropDown&&onButton)) { hidePopup = true; }
        
    }// end of JSplitButton::popupMenuWillBecomeInvisible
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::popupMenuWillBecomeVisible
    /**
     * Called when the popup menu is about to become visible.
     * Sets flags indicating that the popup menu is visible.
     * 
     * @param pme   <code>PopupMenuEvent</code>
     */
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
        
        isShowingPopup = true;
        hidePopup = false;
        
    }// end of JSplitButton::popupMenuWillBecomeVisible
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::addSplitButtonActionListener
    /**
     * Adds an <code>SplitButtonActionListener</code> to the button.
     * 
     * 
     * @param pL the <code>ActionListener</code> to be added
     */
    
    public void addSplitButtonActionListener(SplitButtonActionListener pL) 
    {
        
        listenerList.add(SplitButtonActionListener.class, pL);
        
    }// end of JSplitButton::addSplitButtonActionListener
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::fireButtonClicked
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>event</code>
     * parameter.
     *
     * @param e <code>ActionEvent</code>
     * 
     * @see EventListenerList
     */
    
    private void fireButtonClicked(ActionEvent e) 
    {
        
        //Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ActionEvent event = null;
        
        //Process the listeners last to first, notifying
        //those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            
            if (listeners[i] == SplitButtonActionListener.class) {
                //Lazily create the event:
                if (event == null) {
                    String actionCommand = e.getActionCommand();
                    if (actionCommand == null) {
                        actionCommand = getActionCommand();
                    }
                    event = new ActionEvent(JSplitButton.this,
                            ActionEvent.ACTION_PERFORMED,
                            actionCommand,
                            e.getWhen(),
                            e.getModifiers());
                }
                ((SplitButtonActionListener)listeners[i + 1])
                                                        .buttonClicked(event);
            }
            
        }
        
    }// end of JSplitButton::fireButtonClicked
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::fireSplitbuttonClicked
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the <code>event</code>
     * parameter.
     *
     * @param e  <code>ActionEvent</code>
     * 
     * @see EventListenerList
     */
    
    private void fireSplitbuttonClicked(ActionEvent e) 
    {
        
        //Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ActionEvent event = null;
        
        //Process the listeners last to first, notifying
        //those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            
            if (listeners[i] == SplitButtonActionListener.class) {
                //Lazily create the event:
                if (event == null) {
                    String actionCommand = e.getActionCommand();
                    if (actionCommand == null) {
                        actionCommand = getActionCommand();
                    }
                    event = new ActionEvent(JSplitButton.this,
                            ActionEvent.ACTION_PERFORMED,
                            actionCommand,
                            e.getWhen(),
                            e.getModifiers());
                }
                ((SplitButtonActionListener) listeners[i + 1])
                                                    .splitButtonClicked(event);
            }
            
        }
        
    }// end of JSplitButton::fireSplitbuttonClicked
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::getArrowColor
    /**
     * Gets the color of the arrow.
     * 
     * @return arrowColor
     */
    
    public Color getArrowColor() 
    {
        
        return arrowColor;
        
    }// end of JSplitButton::getArrowColor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::getArrowSize
    /**
     * Gets the size of the arrow.
     * 
     * @return size of the arrow
     */
    
    public int getArrowSize() 
    {
        
        return arrowSize;
        
    }// end of JSplitButton::getArrowSize
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::getDisabledArrowColor
    /**
     * Gets the disabled arrow color.
     * 
     * @return disabledArrowColor color of the arrow if no popup attached.
     */
    
    public Color getDisabledArrowColor() 
    {
        
        return disabledArrowColor;
        
    }// end of JSplitButton::getDisabledArrowColor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::getImage
    /**
     * Gets the image to be drawn in the split part. If no is set, a new image 
     * is created with the triangle.
     * 
     * @return the image drawn in the split part
     */
    
    public Image getImage() 
    {
        
        if (image != null) { return image; } 
        else {
            Graphics2D g = null;
            BufferedImage img = new BufferedImage(arrowSize, arrowSize, 
                                                    BufferedImage.TYPE_INT_RGB);
            g = (Graphics2D) img.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, img.getWidth(), img.getHeight());
            g.setColor(popupMenu != null ? arrowColor : disabledArrowColor);
            
            //this creates a triangle facing right >
            g.fillPolygon(new int[]{0, 0, arrowSize / 2}, 
                            new int[]{0, arrowSize, arrowSize / 2}, 3);
            g.dispose();
            
            //rotate it to face downwards
            img = rotate(img, 90);
            
            BufferedImage dimg = new BufferedImage(img.getWidth(), 
                                img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            
            g = (Graphics2D) dimg.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.drawImage(img, null, 0, 0);
            g.dispose();
            
            for (int i = 0; i < dimg.getHeight(); i++) {
                for (int j = 0; j < dimg.getWidth(); j++) {
                    if (dimg.getRGB(j, i) == Color.WHITE.getRGB()) {
                        dimg.setRGB(j, i, 0x8F1C1C);
                    }
                }
            }

            image = Toolkit.getDefaultToolkit().createImage(dimg.getSource());
            return image;
        }
        
    }// end of JSplitButton::getImage
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::getPopupMenu
    /**
     * Returns the JPopupMenu if set, null otherwise.
     * 
     * @return the popup menu displayed when the split part is clicked
     */
    
    public JPopupMenu getPopupMenu() 
    {
        
        return popupMenu;
        
    }// end of JSplitButton::getPopupMenu
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::getSeparatorSpacing
    /** 
     * Returns the separatorSpacing. Separator spacing is the space above and 
     * below the separator (the line drawn when you hover your mouse over the 
     * split part of the button).
     * 
     * @return the space above and below the separator (the
     * line drawn when you hover your mouse over the split part of the button).
     */
    
    public int getSeparatorSpacing() 
    {
        
        return separatorSpacing;
        
    }// end of JSplitButton::getSeparatorSpacing
    //--------------------------------------------------------------------------
    
    
    //--------------------------------------------------------------------------
    // JSplitButton::getSplitWidth
    /**
     * Splitwidth is the  width of the split part of the button.
     * 
     * @return the width of the split part of the button
     */
    
    public int getSplitWidth() 
    {
        
        return splitWidth;
        
    }// end of JSplitButton::getSplitWidth
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::hideOrShowPopup
    /**
     * Either makes the dropdown menu visible or hidden.
     */

    private void hideOrShowPopup() 
    {
        
        if (hidePopup) { popupMenu.setVisible(false); }
        else if (!isShowingPopup) {
            popupMenu.show(this, 
                    getWidth()-(int)popupMenu.getPreferredSize().getWidth(), 
                    getHeight());
        }
        else if (isShowingPopup && (onSplit || (alwaysDropDown&&onButton))) {  
            popupMenu.setVisible(false); 
        }
        
        hidePopup = false;
        
    }// end of JSplitButton::hideOrShowPopup
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::isAlwaysDropDown
    /**
     * If true, then the dropdown menu, if attached, is always shown even if the
     * main button part is clicked.
     * 
     * @return true if alwaysDropdown, false otherwise.
     */
    public boolean isAlwaysDropDown() 
    {
        
        return alwaysDropDown;
        
    }// end of JSplitButton::isAlwaysDropDown
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::removeSplitButtonActionListener
    /**
     * Removes an <code>SplitButtonActionListener</code> from the button.
     * If the listener is the currently set <code>Action</code>
     * for the button, then the <code>Action</code>
     * is set to <code>null</code>.
     *
     * @param pL the listener to be removed
     */
    
    public void removeSplitButtonActionListener(SplitButtonActionListener pL) 
    {
        
        if ((pL != null) && (getAction() == pL)) { setAction(null); } 
        else { listenerList.remove(SplitButtonActionListener.class, pL); }
        
    }// end of JSplitButton::removeSplitButtonActionListener
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::rotate
    /**
     * Rotates the given image with the specified angle.
     * 
     * @param pImg image to rotate
     * 
     * @param pAngle angle of rotation
     * 
     * @return rotated image
     */
    
    private BufferedImage rotate(BufferedImage pImg, int pAngle) 
    {
        
        int w = pImg.getWidth();
        int h = pImg.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, pImg.getType());
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(pAngle), w / 2, h / 2);
        g.drawImage(pImg, null, 0, 0);
        return dimg;
        
    }// end of JSplitButton::rotate
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::setAlwaysDropDown
    /**
     * Sets whether or not to show the dropdown menu when any part of the button
     * is clicked, even the main button.
     * 
     * @param pAlwaysDropDown true to show the attached dropdown even if the 
     * button part is clicked, false otherwise
     */
    
    public void setAlwaysDropDown(boolean pAlwaysDropDown) 
    {
        
        alwaysDropDown = pAlwaysDropDown;
        
    }// end of JSplitButton::setAlwaysDropDown
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::setArrowColor
    /**
     * Set the arrow color.
     * 
     * @param pArrowColor desired arrow color
     */
    
    public void setArrowColor(Color pArrowColor) 
    {
        
        arrowColor = pArrowColor;
        image = null; //to repaint the image with the new color
        
    }// end of JSplitButton::setArrowColor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::setArrowSize
    /**
     * Sets the size of the arrow
     * 
     * @param pArrowSize    the size of the arrow
     */
    
    public void setArrowSize(int pArrowSize) 
    {
        
        arrowSize = pArrowSize;
        image = null; //to repaint the image with the new size
        
    }// end of JSplitButton::setArrowSize
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::setDisabledArrowColor
    /**
     * Sets the disabled arrow color.
     * 
     * @param pDisabledArrowColor color of the arrow if no popup attached.
     */
    
    public void setDisabledArrowColor(Color pDisabledArrowColor) 
    {
        
        disabledArrowColor = pDisabledArrowColor;
        image = null; //to repaint the image with the new color
        
    }// end of JSplitButton::setDisabledArrowColor
    //--------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // JSplitButton::setImage
    /**
     * Sets the image to draw instead of the triangle.
     * 
     * @param pImage image to draw
     */
    
    public void setImage(Image pImage) 
    {
        
        image = pImage;
        
    }// end of JSplitButton::setImage
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::setPopupMenu
    /** 
     * Sets the JPopupMenu to be displayed when the split part of the button is 
     * clicked.
     * 
     * @param pMenu the popup menu to be displayed when the split part is 
     * clicked
    */
    
    public void setPopupMenu(JPopupMenu pMenu) 
    {
        
        popupMenu = pMenu;
        
        popupMenu.addPopupMenuListener(this);
        
        image = null; //to repaint the arrow image
        
    }// end of JSplitButton::setPopupMenu
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::setSeparatorSpacing
    /**
     * Sets the separatorSpacing. Separator spacing is the space above and below
     * the separator( the line drawn when you hover your mouse over the split 
     * part of the button).
     *
     * @param pSeparatorSpacing the separator spacing
     */
    
    public void setSeparatorSpacing(int pSeparatorSpacing) 
    {
        
        separatorSpacing = pSeparatorSpacing;
        
    }// end of JSplitButton::setSeparatorSpacing
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // JSplitButton::setSplitWidth
    /**
     * Splitwidth is the  width of the split part of the button.
     * 
     * @param pSplitWidth    the width of the split part of the button
     */
    
    public void setSplitWidth(int pSplitWidth) 
    {
        
        splitWidth = pSplitWidth;
        
    }// end of JSplitButton::setSplitWidth
    //--------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="Unused Listeners">

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }
// </editor-fold>
    
}//end of class JSplitButton
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------