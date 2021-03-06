package aiki.gui.components.labels;
import java.awt.Color;
import java.awt.Dimension;

import aiki.facade.FacadeGame;
import aiki.util.SortingEgg;
import code.gui.images.AbstractImage;
import code.gui.images.AbstractImageFactory;
import code.gui.images.ConverterGraphicBufferedImage;

public class EggLabel extends SelectableLabel {

    private int sideLength;

    private SortingEgg egg;

    private AbstractImage miniImagePk;

    private int xName;

    private int remainSteps;

    private int xSteps;

    private int xRemainSteps;

    public EggLabel(SortingEgg _egg) {
        egg = _egg;
    }

    public void setImagesResults(AbstractImageFactory _fact, FacadeGame _facade) {
        int[][] miniPk_ = _facade.getData().getMiniPk().getVal(egg.getKeyName());
        miniImagePk = ConverterGraphicBufferedImage.decodeToImage(_fact,miniPk_);
        sideLength = _facade.getMap().getSideLength();
        remainSteps = (int) (_facade.getData().getPokemon(egg.getKeyName()).getHatchingSteps().ll() - egg.getSteps());
        xRemainSteps = stringWidth(Long.toString(remainSteps));
    }

    public SortingEgg getEgg() {
        return egg;
    }

    public void setNameCoord(int _xName, int _xSteps, int _height) {
        xName = _xName;
        xSteps = _xSteps;
        setPreferredSize(new Dimension(xName + xSteps + xRemainSteps + sideLength,_height));
    }

    @Override
    public void paintComponent(AbstractImage _g) {
        _g.setColor(Color.WHITE);
        _g.fillRect(0,0,getWidth(),getHeight());
        _g.setColor(Color.BLACK);
        int h_ = getFont().getSize();
        _g.drawImage(miniImagePk, 0, 0);
        _g.drawString(egg.getName(), sideLength, h_);
        _g.drawString(Long.toString(egg.getSteps()), xName + sideLength, h_);
        _g.drawString(Long.toString(remainSteps), xName + xSteps + sideLength, h_);
        super.paintComponent(_g);
    }
}
