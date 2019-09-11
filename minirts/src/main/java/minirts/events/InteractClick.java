package minirts.events;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import jm.constants.ProgramChanges;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import minirts.MainWindow;
import minirts.ThreadSound;
import code.music.core.EvolvedNote;
import code.music.enums.Gamme;

public class InteractClick extends MouseAdapter {

    private static final String SOPRANO = "Soprano";

    private MainWindow fenetre;

    private ThreadSound sound;
    private Thread soundTh;

    private Score chor;

    public InteractClick(MainWindow _fenetre) {
        fenetre = _fenetre;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) {
            fenetre.setNewLocation(e.getX(), e.getY());
            if (soundTh != null && soundTh.isAlive()) {
                return;
            }
            Score s_ = getCloneSoldierSound();
            sound = new ThreadSound(s_);
            soundTh = new Thread(sound);
            soundTh.start();
            return;
        }
        if (fenetre.isDragged()) {
            fenetre.setDragged(false);
            return;
        }
        if (fenetre.isAddingSoldier()) {
            fenetre.addNewSoldier(e.getX(), e.getY());
            return;
        }
        fenetre.selectOrDeselect(e.getX(), e.getY());
    }

    private Score getCloneSoldierSound() {
        if (chor != null) {
            return chor;
        }
        chor = new Score();
        Part p_ = new Part(SOPRANO, ProgramChanges.VOICE, 1);
        Phrase phr = new Phrase();
        EvolvedNote e_;
        e_ = new EvolvedNote(Gamme.SOL, 3, 1, 64);
        phr.addNote(e_.getNote());
        p_.addPhrase(phr);
        chor.addPart(p_);
        return chor;
    }

    @Override
    public void mousePressed(MouseEvent _e) {
        fenetre.setFirst(_e.getX(), _e.getY());
    }

//    @Override
//    public void mouseEntered(MouseEvent _e) {
//        MainWindow._currentCursor_ = MainWindow._custCursor_;
//    }
//
//    @Override
//    public void mouseExited(MouseEvent _e) {
//        MainWindow._currentCursor_ = MainWindow.DEFAULT_CURSOR1;
////        if (fenetre.isDragged()) {
////            return;
////        }
////        fenetre.moveCamera(_e.getPoint());
//    }

    @Override
    public void mouseDragged(MouseEvent _e) {
        if (!SwingUtilities.isLeftMouseButton(_e)) {
            return;
        }
        fenetre.setDragged(true);
        fenetre.setLast(_e.getX(), _e.getY());
        fenetre.selectOrDeselectMulti();
    }
}
