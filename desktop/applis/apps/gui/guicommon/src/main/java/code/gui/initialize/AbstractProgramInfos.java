package code.gui.initialize;

import code.expressionlanguage.filenames.AbstractNameValidating;
import code.gui.GroupFrame;
import code.maths.montecarlo.AbstractGenerator;
import code.stream.AbstractFileCoreStream;
import code.stream.ClipStream;
import code.stream.core.TechStreams;
import code.threads.AbstractThreadFactory;
import code.util.CustList;
import code.util.StringMap;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicInteger;

public interface AbstractProgramInfos {
    String getHomePath();
    String getTmpUserFolder();
    CustList<GroupFrame> getFrames();
    StringMap<AtomicInteger> getCounts();
    AbstractGenerator getGenerator();
    AbstractThreadFactory getThreadFactory();
    TechStreams getStreams();
    AbstractFileCoreStream getFileCoreStream();

    AbstractNameValidating getValidator();
    AbstractGraphicStringListGenerator getGeneGraphicList();
    AbstractGraphicComboBoxGenerator getGeneComboBox();
    boolean close(Closeable _cl);
    ClipStream openClip(byte[] _file);
    BufferedImage readImg(String _file);
    boolean writeImg(String _format, String _file, BufferedImage _img);
}
