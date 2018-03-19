package code.gui.document;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import code.formathtml.HtmlPage;
import code.formathtml.exceptions.RenderingException;
import code.formathtml.render.MetaDocument;
import code.formathtml.util.BeanLgNames;
import code.sml.Document;
import code.util.StringList;

public final class ThreadActions extends Thread {

    private static final int DELTA = 200;

    private static final String RETURN_LINE = "\n";

    private static final String TAB = "\t";

    private RenderedPage page;

    private String anchor;

    private String fileName;

    private boolean usedFirstUrl;

    private BeanLgNames stds;

    private boolean refresh;

    private boolean form;

    private Timer timer;

    public ThreadActions(RenderedPage _page, BeanLgNames _lgNames, String _anchor, String _fileName, boolean _form, boolean _refresh, boolean _usedFirstUrl) {
        page = _page;
        page.start();
        stds = _lgNames;
        anchor = _anchor;
        form = _form;
        refresh = _refresh;
        fileName = _fileName;
        usedFirstUrl = _usedFirstUrl;
        initTimer();
    }

    private void initTimer() {
        page.getNavigation().getSession().setInterrupt(false);
//        if (session.getLabel() != null) {
//            timer = new Timer(DELTA, new Chronometer(session.getLabel(), session, 0));
//            timer.start();
//        }
    }
    @Override
    public void run() {
        try {
            if (refresh) {
                page.getNavigation().refresh();
                afterAction();
                return;
            }
            if (usedFirstUrl) {
                page.getNavigation().loadConfiguration(fileName, stds);
                HtmlPage htmlPage_ = page.getNavigation().getHtmlPage();
                htmlPage_.setUrl(-1);
                page.getNavigation().initializeSession();
                afterAction();
                return;
            }
            if (form) {
                page.getNavigation().processFormRequest();
                afterAction();
                return;
            }
            page.getNavigation().processAnchorRequest(anchor);
            afterAction();
            return;
        } catch (RenderingException _0) {
            processErrors((Throwable) _0.getCustCause().getInstance());
        } catch (RuntimeException _0) {
            processErrors(_0);
        } catch (Error _0) {
            processErrors(_0);
        }
    }
    private void afterAction() {
        if (page.getNavigation().getSession().isInterrupt()) {
            if (page.isProcessing()) {
                finish();
                return;
            }
        }
        if (!page.isProcessing()) {
            return;
        }
        Document doc_ = page.getNavigation().getDocument();
        MetaDocument metadoc_ = MetaDocument.newInstance(doc_);
        SwingUtilities.invokeLater(new WindowPage(metadoc_, page.getScroll(), page));
    }
    private void processErrors(Throwable _t) {
        if (page.getArea() != null) {
            page.getArea().append(StringList.concat(TAB, _t.getMessage(), RETURN_LINE));
            for (StackTraceElement s : _t.getStackTrace()) {
                page.getArea().append(StringList.concat(
                      TAB, s.getFileName(), TAB, s.getClassName(),
                      TAB, s.getClassName(), TAB, s.getMethodName(), TAB, Long.toString(s.getLineNumber()), RETURN_LINE));
            }
        }
        _t.printStackTrace();
        finish();
    }

    void finish() {
        if (timer != null) {
            timer.stop();
        }
        page.finish(false);
    }
}
