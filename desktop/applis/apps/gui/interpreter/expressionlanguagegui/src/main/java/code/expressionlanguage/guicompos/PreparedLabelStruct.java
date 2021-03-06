package code.expressionlanguage.guicompos;

import code.expressionlanguage.structs.Struct;
import code.gui.CustComponent;
import code.gui.PreparedLabel;
import code.gui.images.AbstractImage;
import code.gui.images.AbstractImageFactory;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public final class PreparedLabelStruct extends CustComponentStruct {
    private final PreparedLabel textLabel;

    protected PreparedLabelStruct(AbstractImageFactory _fact, String _className) {
        super(_className);
        textLabel = PreparedLabel.prep(_fact);
    }
    protected PreparedLabelStruct(AbstractImageFactory _fact, Struct _img,String _className) {
        super(_className);
        textLabel = new PreparedLabel(_fact,builImage(_img));
    }
    public void setImage(AbstractImageFactory _fact, Struct _text) {
        textLabel.setIcon(_fact,builImage(_text));
    }

    public static AbstractImage builImage(Struct _text) {
        AbstractImage img_ = null;
        if (_text instanceof ImageStruct) {
            img_ = ((ImageStruct) _text).getImage();
        }
        return img_;
    }

    public void addMouseListener(MouseListener _mouseListener) {
        if (_mouseListener != null) {
            textLabel.addMouseListener(_mouseListener);
        }
    }

    public PreparedLabel getTextLabel() {
        return textLabel;
    }

    @Override
    protected CustComponent getComponent() {
        return textLabel;
    }

    @Override
    public int getWidth() {
        return textLabel.getWidth();
    }

    @Override
    public int getHeight() {
        return textLabel.getHeight();
    }

    public void addKeyListener(KeyListener _i) {
        textLabel.addKeyListener(_i);

    }
}
