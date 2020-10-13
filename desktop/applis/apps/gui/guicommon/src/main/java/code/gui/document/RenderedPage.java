package code.gui.document;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import code.expressionlanguage.ContextEl;
import code.formathtml.Configuration;
import code.formathtml.Navigation;
import code.formathtml.render.MetaAnchorLabel;
import code.formathtml.render.MetaComponent;
import code.formathtml.render.MetaDocument;
import code.formathtml.util.BeanCustLgNames;
import code.formathtml.util.BeanLgNames;
import code.bean.nat.BeanNatLgNames;
import code.gui.*;
import code.sml.Document;
import code.util.CustList;
import code.util.IdMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.consts.Constants;

public final class RenderedPage implements ProcessingSession {

    private DualPanel page;
    private final ScrollPane scroll;
    private Navigation navigation;
    private IdMap<MetaComponent,DualComponent> refs = new IdMap<MetaComponent,DualComponent>();
    private FindEvent finding;

    private AtomicBoolean processing = new AtomicBoolean();

    private Thread threadAction;

    private CustList<BufferedImage> process = new CustList<BufferedImage>();

    private TextArea area;

    private ProgressingWebDialog dialog;

    private ChangeableTitle frame;

    private ContextEl context;

    private AbstractContextCreator contextCreator;
    private BeanLgNames standards;

    private CustList<DualAnimatedImage> anims = new CustList<DualAnimatedImage>();

    private LabelButton find;
    private TextField field;

    public RenderedPage(ScrollPane _frame) {
        scroll = _frame;
    }

    public void initNav() {
        navigation = new Navigation();
        navigation.setSession(new Configuration());
    }

    public void setFiles(StringMap<String> _web) {
        navigation.setFiles(_web);
    }

    public void setLanguage(String _language) {
        navigation.setLanguage(_language);
        navigation.setLanguages(Constants.getAvailableLanguages());
    }

    public void setLanguage(String _language, StringList _languages) {
        navigation.setLanguage(_language);
        navigation.setLanguages(_languages);
    }

    public void setProcess(CustList<BufferedImage> _process) {
        process = _process;
    }

    /**It is impossible to know by advance if there is an infinite loop in a custom java code =&gt; Give up on tests about dynamic initialize html pages*/
    public void initialize(Navigation _nav,MetaDocument _metaDoc) {
        navigation = _nav;
        CustComponent.invokeLater(new WindowPage(_metaDoc, scroll, this));
    }
    /**It is impossible to know by advance if there is an infinite loop in a custom java code =&gt; Give up on tests about dynamic initialize html pages*/
    public void initialize(Object _db, PreparedAnalyzed _stds) {
        start();
        _stds.getBeanNatLgNames().setDataBase(_db);
        standards = _stds.getBeanNatLgNames();
        navigation = _stds.getNavigation();
        contextCreator = new NativeContextCreator();
        ContextEl ctx_ = _stds.getContext();
        setContext(ctx_);
        navigation.initializeRendSession(ctx_, standards);
        setupText();
    }
    public void initializeOnlyConf(Object _dataBase, PreparedAnalyzed _prepared, String _lg) {
        if (processing.get()) {
            return;
        }
        navigation = _prepared.getNavigation();
        setContext(_prepared.getContext());
        _prepared.getBeanNatLgNames().setDataBase(_dataBase);
        navigation.setLanguage(_lg);
        standards = _prepared.getBeanNatLgNames();
        contextCreator = new NativeContextCreator();
        ThreadDirectActions th_ = new ThreadDirectActions(this);
        threadAction = CustComponent.newThread(th_);
        threadAction.start();
        animateProcess();
    }

    public void initializeOnlyConf(AbstractContextCreator _creator,BeanCustLgNames _stds, Runnable _inst) {
        if (processing.get()) {
            return;
        }
        standards = _stds;
        contextCreator = _creator;
        threadAction = CustComponent.newThread(_inst);
        threadAction.start();
        animateProcess();
    }

    void animateProcess() {
        if (!process.isEmpty()) {
            LoadingWeb load_ = new LoadingWeb(this, process, frame, dialog);
            CustComponent.newThread(load_).start();
        }
    }

    public void refresh() {
        if (processing.get()) {
            return;
        }
        if (!(standards instanceof BeanNatLgNames)) {
            return;
        }
        threadAction = CustComponent.newThread(new ThreadRefresh(this, (BeanNatLgNames) standards));
        threadAction.start();
        animateProcess();
    }

    public void start() {
        processing.set(true);
    }

    void finish() {
        //boolean _stop
        processing.set(false);
    }

    private void setupText() {
        if (!processing.get()) {
            return;
        }
        processing.set(false);
        Document doc_ = navigation.getDocument();
        MetaDocument metadoc_ = MetaDocument.newInstance(doc_,navigation.getSession().getRendKeyWords());
        CustComponent.invokeLater(new WindowPage(metadoc_, scroll, this));
    }
    void directScroll(MetaDocument _meta) {
        if (frame != null) {
            if (!navigation.getTitle().isEmpty()) {
                frame.setTitle(navigation.getTitle());
            }
        }
        String ref_ = navigation.getReferenceScroll();
        if (!ref_.isEmpty()) {
            MetaAnchorLabel lab_ = _meta.getAnchorsRef().getVal(ref_);
            DualComponent c_ = getRefs().getVal(lab_);
            DualComponent r_ = page;
            int x_ = 0;
            int y_ = 0;
            while (c_ != r_) {
                x_ += c_.getGraphic().getXcoords();
                y_ += c_.getGraphic().getYcoords();
                c_ = c_.getContainer();
            }
            scroll.setHorizontalValue(x_);
            scroll.setVerticalValue(y_);
        }
        if (frame != null) {
            frame.pack();
        }
        finish();
    }
    public TextArea getArea() {
        return area;
    }
    @Override
    public boolean isProcessing() {
        return processing.get();
    }
    void setFinding(MetaDocument _document) {
        finding.setFinding(_document);
    }

    void setPage(DualPanel _page) {
        page = _page;
    }
    IdMap<MetaComponent, DualComponent> getRefs() {
        return refs;
    }
    public Navigation getNavigation() {
        return navigation;
    }
    public ScrollPane getScroll() {
        return scroll;
    }
    DualPanel getPage() {
        return page;
    }

    public void setDialog(ProgressingWebDialog _dialog) {
        dialog = _dialog;
    }

    public void setFrame(ChangeableTitle _frame) {
        frame = _frame;
    }

    public void setArea(TextArea _area) {
        area = _area;
    }

    public BeanLgNames getStandards() {
        return standards;
    }

    CustList<DualAnimatedImage> getAnims() {
        return anims;
    }

    public void setSearchText(LabelButton _search) {
        find = _search;
    }

    public void setField(TextField _field) {
        field = _field;
    }

    public void addFinder() {
        if (find.getMouseListeners().length > 0) {
            return;
        }
        finding = new FindEvent(field, this);
        find.addMouseListener(finding);
    }

    public ContextEl getContext() {
        return context;
    }

    public void setContext(ContextEl context) {
        this.context = context;
    }

    public AbstractContextCreator getContextCreator() {
        return contextCreator;
    }
}
