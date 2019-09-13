package minirts;

import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import code.gui.CustGraphics;
import code.util.EntryCust;
import minirts.events.InteractClick;
import minirts.rts.CustPoint;
import minirts.rts.Facade;
import minirts.rts.Rect;
import minirts.rts.Soldier;
import minirts.rts.SoldierPattern;
import minirts.rts.UnitMapKey;
import code.gui.CustComponent;
import code.gui.Panel;
import code.util.ObjectMap;

import javax.swing.*;

public class PanelBattle {

    private ObjectMap<UnitMapKey,UnitSoldier> soldierLabels = new ObjectMap<UnitMapKey,UnitSoldier>();
    private final Facade facade;

    private boolean paintSelection;

    private Selecting selecting;
    private Panel container = Panel.newAbsolute();
    private Panel content = Panel.newAbsolute();
    public void addMouseMotionListener(MouseMotionListener _l) {
        content.getComponent().addMouseMotionListener(_l);
    }

    public void addMouseWheelListener(MouseWheelListener _l) {
        content.getComponent().addMouseWheelListener(_l);
    }

    public PanelBattle(Facade _facade) {
        facade = _facade;
        content.setOpaque(true);
        content.setBackground(Color.WHITE);
        container.setOpaque(true);
        container.setBackground(Color.WHITE);
        selecting = new Selecting(_facade);
        container.add(selecting);
        container.add(content);
    }

    public void addNewSoldier(int _x, int _y) {
        facade.addNewSoldier(_x, _y);
        if (!facade.isAdded()) {
            return;
        }
        SoldierPattern p_ = facade.getSoldierPattern();
        Soldier s_ = facade.getLastSoldier();
        UnitSoldier soldierLabel_ = new UnitSoldier(s_);
        soldierLabel_.setSize(new Dimension(p_.getWidth(), p_.getHeight()));
        content.add(soldierLabel_);
        soldierLabel_.setLocation(_x, _y);
        soldierLabels.put(facade.getLastSoldierKey(),soldierLabel_);
        CustPoint curTopLeft_ = facade.getTopLeftPoint();
        CustComponent parent_ = container.getParent();
        int w_ = parent_.getWidth();
        int h_ = parent_.getHeight();
        paintSelection = false;
//        repaint(-curTopLeft_.x, -curTopLeft_.y, w_, h_);
        repaint(curTopLeft_.getX(), curTopLeft_.getY(), w_, h_);
    }

    public void repaint(int _x, int _y, int _width, int _height) {
        Rect gl_ = new Rect(_x,_y,_width,_height);
        for (EntryCust<UnitMapKey, UnitSoldier> e: soldierLabels.entryList()) {
            UnitSoldier u_ = e.getValue();
            int w_ = u_.getWidth();
            int h_ = u_.getHeight();
            Soldier soldier_ = u_.getSoldier();
            Rect loc_ = new Rect(soldier_.getX(),soldier_.getY(),w_,h_);
            if (!gl_.intersects(loc_)) {
                continue;
            }
            BufferedImage img_ = new BufferedImage(w_, h_, BufferedImage.TYPE_INT_ARGB);
            Graphics gr_ = img_.getGraphics();
            gr_.setFont(u_.getFont());
            u_.paintComponent(new CustGraphics(gr_));
            u_.setIcon(new ImageIcon(img_));
        }
        paintSelection();
    }

    public void paintSelection() {
        if (paintSelection) {
            int w_ = selecting.getWidth();
            int h_ = selecting.getHeight();
            BufferedImage img_ = new BufferedImage(w_, h_, BufferedImage.TYPE_INT_ARGB);
            Graphics gr_ = img_.getGraphics();
            gr_.setFont(selecting.getFont());
            Rect r_ = facade.getSelection();
            gr_.setColor(Color.BLUE);
            gr_.drawRect(r_.getLeft(), r_.getTop(), r_.getWidth(), r_.getHeight());
            selecting.setIcon(new ImageIcon(img_));
        } else {
            int w_ = selecting.getWidth();
            int h_ = selecting.getHeight();
            BufferedImage img_ = new BufferedImage(w_, h_, BufferedImage.TYPE_INT_ARGB);
            Graphics gr_ = img_.getGraphics();
            gr_.setColor(new Color(255,255,255,0));
            gr_.fillRect(0, 0, w_, h_);
            selecting.setIcon(new ImageIcon(img_));
        }
    }

    public UnitSoldier getSoldierLabel(UnitMapKey _key) {
        return soldierLabels.getVal(_key);
    }

    public static void setSoldierLocation(UnitSoldier _u, int _x, int _y) {
        _u.setLocation(_x, _y);
    }

//    public void selectOrDeselect(Point _first, Point _last) {
//        facade.selectOrDeselect(_first, _last);
//        Point curTopLeft_ = facade.getTopLeftPoint();
//        Component parent_ = getParent();
//        int w_ = parent_.getWidth();
//        int h_ = parent_.getHeight();
//        paintSelection = true;
////        repaint(-curTopLeft_.x, -curTopLeft_.y, w_, h_);
//        repaint(curTopLeft_.x, curTopLeft_.y, w_, h_);
//    }
//
//    public void selectOrDeselect(int _x, int _y) {
//        facade.selectOrDeselect(_x, _y);
//        Point curTopLeft_ = facade.getTopLeftPoint();
//        Component parent_ = getParent();
//        int w_ = parent_.getWidth();
//        int h_ = parent_.getHeight();
//        paintSelection = false;
////        repaint(-curTopLeft_.x, -curTopLeft_.y, w_, h_);
//        repaint(curTopLeft_.x, curTopLeft_.y, w_, h_);
//    }

    public void setRectangle(CustPoint _first, CustPoint _last) {
        facade.setRectangle(_first, _last);
    }

    public void setRectangle(int _x, int _y) {
        facade.setRectangle(_x, _y);
    }

    public void selectOrDeselectMany() {
        facade.selectOrDeselectMany();
        CustPoint curTopLeft_ = facade.getTopLeftPoint();
        CustComponent parent_ = container.getParent();
        int w_ = parent_.getWidth();
        int h_ = parent_.getHeight();
//        paintSelection = false;
//        repaint(-curTopLeft_.x, -curTopLeft_.y, w_, h_);
        repaint(curTopLeft_.getX(), curTopLeft_.getY(), w_, h_);
    }

    public void setNewLocation(int _x, int _y) {
        facade.setNewLocation(_x, _y);
    }

    public void moveCamera(Point _pt) {
        CustComponent parent_ = container.getParent();
        int w_ = parent_.getWidth();
        int h_ = parent_.getHeight();
        facade.moveCamera(_pt.x, _pt.y, w_, h_);
        CustPoint curTopLeft_ = facade.getTopLeftPoint();
        Point pt_ = new Point();
        pt_.x = -curTopLeft_.getX();
        pt_.y = -curTopLeft_.getY();
        container.setLocation(pt_);
//        setLocation(curTopLeft_);
        paintSelection = false;
//        repaint(-curTopLeft_.x, -curTopLeft_.y, w_, h_);
        repaint(curTopLeft_.getX(), curTopLeft_.getY(), w_, h_);
    }
    public void setPaintSelection(boolean _paintSelection) {
        paintSelection = _paintSelection;
    }

    public boolean isPaintSelection() {
        return paintSelection;
    }

    public Facade getFacade() {
        return facade;
    }

    public void addMouseListener(InteractClick _i) {
        content.addMouseListener(_i);
    }

    public Panel getContainer() {
        return container;
    }

    public void setSize(Dimension _dimension) {
        container.setSize(_dimension);
        content.setSize(_dimension);
    }

    public void setLocation(Point _pt) {
        container.setLocation(_pt);
    }
}
