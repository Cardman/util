package aiki.gui.components;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import code.gui.AutoCompleteDocument;
import code.gui.LabelButton;
import code.gui.NumComboBox;
import code.util.CustList;
import code.util.EnumList;
import code.util.EqList;
import code.util.StringList;
import code.util.pagination.SearchingMode;
import aiki.facade.FacadeGame;
import aiki.gui.components.labels.EggLabel;
import aiki.gui.components.listeners.ChangedDeltaPageEvent;
import aiki.gui.components.listeners.ChangedModeEvent;
import aiki.gui.components.listeners.ChangedNbResultsEvent;
import aiki.gui.components.listeners.ChangedPageEvent;
import aiki.gui.components.listeners.NewSearchEvent;
import aiki.gui.components.listeners.SearchEvent;
import aiki.gui.listeners.PaginatorEvent;
import aiki.util.SortingEgg;

public final class PaginatorEgg extends Paginator {

    private static final String NAME = "name";

    private static final String STEPS = "steps";

    private static final String REMAIN_STEPS = "remainSteps";

    //private static final String EGG = "egg";

    private JTextField name;

    private EnumList<SearchingMode> order = new EnumList<SearchingMode>();

    //private JComboBoxSearchingMode modeFirstName = new JComboBoxSearchingMode();
    private ComboBoxSearchingMode modeName;

    private JTextField minSteps = new JTextField(16);

    private JTextField maxSteps = new JTextField(16);

    private JPanel results = new JPanel();

    private ComboBoxSelectedBool cmpNameSorting;

    private NumComboBox cmpNamePrio = new NumComboBox();

    private ComboBoxSelectedBool cmpStepsSorting;

    private NumComboBox cmpStepsPrio = new NumComboBox();

    public PaginatorEgg(Window _w, FacadeGame _d) {
        super(ACCESS_EGG);
        setWindow(_w);
        setFacade(_d);
        order.add(SearchingMode.WHOLE_STRING);
        order.add(SearchingMode.SUBSTRING);
        order.add(SearchingMode.META_CHARACTER);
        order.add(SearchingMode.REG_EXP);
        modeName = new ComboBoxSearchingMode();
        modeName.setWithDefaultValue(false);
        modeName.refresh(order, getMessagesSearchMode());
        cmpNameSorting = new ComboBoxSelectedBool();
        cmpNameSorting.setWithDefaultValue(false);
        cmpNameSorting.refresh(getFacade().getTranslatedBooleansCurLanguage());
        cmpStepsSorting = new ComboBoxSelectedBool();
        cmpStepsSorting.setWithDefaultValue(false);
        cmpStepsSorting.refresh(getFacade().getTranslatedBooleansCurLanguage());
        int nbCmp_ = getFacade().getNbComparatorsEgg();
        for (int i = CustList.FIRST_INDEX; i <= nbCmp_; i++) {
            cmpNamePrio.addItem(i);
            cmpStepsPrio.addItem(i);
        }
        getFacade().setSearchModeNameEgg(SearchingMode.WHOLE_STRING);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        StringList pk_ = new StringList();
        for (String p: getFacade().getData().getPokedex().getKeys()) {
            String pkTr_ = getFacade().translatePokemon(p);
            pk_.add(pkTr_);
        }
        name = AutoCompleteDocument.createAutoCompleteTextField(pk_, 16);
//        name.getDocument().addDocumentListener(new DocumentAdaptater() {
//
//            public void updateText() {
//                getFacade().setContentOfNameEgg(convertStringField(name.getText()));
//            }
//        });
        modeName.addActionListener(new ChangedModeEvent(modeName, name));
//        modeName.addItemListener(new ItemListener(){
//            public void itemStateChanged(ItemEvent _e) {
//                SearchingMode s_ = modeName.getCurrent();
//                getFacade().setSearchModeNameEgg(s_);
//                AutoCompleteDocument.setMode(name, s_);
//            }
//        });
//        minSteps.getDocument().addDocumentListener(new DocumentAdaptater() {
//
//            public void updateText() {
//                getFacade().setMinStepsEgg(convertNumberField(minSteps.getText()));
//            }
//        });
//        maxSteps.getDocument().addDocumentListener(new DocumentAdaptater() {
//
//            public void updateText() {
//                getFacade().setMaxStepsEgg(convertNumberField(maxSteps.getText()));
//            }
//        });
//        cmpNameSorting.addItemListener(new ItemListener(){
//            public void itemStateChanged(ItemEvent _e) {
//                getFacade().setCmpNameIncreasingEgg(cmpNameSorting.getCurrent());
//            }
//        });
//        cmpStepsSorting.addItemListener(new ItemListener(){
//            public void itemStateChanged(ItemEvent _e) {
//                getFacade().setCmpStepsIncreasingEgg(cmpStepsSorting.getCurrent());
//            }
//        });
//        cmpNamePrio.addItemListener(new ItemListener(){
//            public void itemStateChanged(ItemEvent _e) {
//                getFacade().setCmpNamePriorityEgg((Integer)cmpNamePrio.getSelectedItem());
//            }
//        });
//        cmpStepsPrio.addItemListener(new ItemListener(){
//            public void itemStateChanged(ItemEvent _e) {
//                getFacade().setCmpStepsPriorityEgg((Integer)cmpStepsPrio.getSelectedItem());
//            }
//        });
        JPanel search_;
        search_ = new JPanel(new GridLayout(0,3));
        search_.add(new JLabel(getMessages().getVal(NAME)));
        search_.add(name);
        search_.add(modeName);
        search_.add(new JLabel(getMessages().getVal(REMAIN_STEPS)));
        search_.add(minSteps);
        search_.add(maxSteps);
        add(search_);
        JPanel sorting_;
        sorting_ = new JPanel(new GridLayout(0,3));
        sorting_.add(new JLabel(getMessages().getVal(NAME)));
        sorting_.add(cmpNameSorting);
        sorting_.add(cmpNamePrio);
        sorting_.add(new JLabel(getMessages().getVal(REMAIN_STEPS)));
        sorting_.add(cmpStepsSorting);
        sorting_.add(cmpStepsPrio);
        add(sorting_);
        JPanel top_;
        top_ = new JPanel();
        LabelButton button_;
        button_ = new LabelButton(getMessages().getVal(SEARCH));
        button_.addMouseListener(new SearchEvent(this));
        top_.add(button_);
        button_ = new LabelButton(getMessages().getVal(NEW_SEARCH));
        button_.addMouseListener(new NewSearchEvent(this));
        top_.add(button_);
        add(top_);
        //        results.setLayout(new BoxLayout(results, BoxLayout.PAGE_AXIS));
        results.setLayout(new GridLayout(0, 1));
        //map.getSideLength()
        //miniImagePk egg.getName() steps remainSteps
        //getHeader().setText(getMessages().getVal(EGG));
        String h_ = getMessages().getVal(NAME)+SPACES;
        h_ += getMessages().getVal(STEPS)+SPACES;
        h_ += getMessages().getVal(REMAIN_STEPS);
        getHeader().addString(h_, FIRST_PIXEL);
        getHeader().setPreferredSize(new Dimension(getHeader().width(h_), HEIGTH_CHARS));
        results.add(getHeader());
        add(new JScrollPane(results));
        JPanel bottom_ = new JPanel();
        getNbResults().setValue(getFacade().getNbResultsPerPageEgg());
        getNbResults().addChangeListener(new ChangedNbResultsEvent(this));
        bottom_.add(getNbResults());
        getPages().addActionListener(new ChangedPageEvent(this));
        getDelta().getDocument().addDocumentListener(new ChangedDeltaPageEvent(this));
        bottom_.add(getBegin());
        bottom_.add(getPreviousDelta());
        bottom_.add(getPrevious());
        bottom_.add(getPages());
        bottom_.add(getNext());
        bottom_.add(getNextDelta());
        bottom_.add(getEnd());
        bottom_.add(getDelta());
        add(bottom_);
        changeNav();
    }

    @Override
    public void changeNbResults() {
        int int_ = (Integer)getNbResults().getValue();
        if (int_ <= 0) {
            return;
        }
        getFacade().setNbResultsPerPageEgg(int_);
        refreshResults();
        changePages();
        changeNav();
        getWindow().pack();
    }

    @Override
    public void changePageNumber() {
        if (isAdding()) {
            return;
        }
        getFacade().changePageEgg(getPages().getSelectedIndex());
        refreshResults();
        getWindow().pack();
    }

    @Override
    public void changeDeltaPage() {
        try {
            int nb_ = Integer.parseInt(getDelta().getText());
            if (nb_ <= 0) {
                return;
            }
            getFacade().setDeltaEgg(nb_);
//        }catch(Exception _e){
        }catch(NumberFormatException _0){
            getFacade().setDeltaEgg(1);
        }
    }

    public void refreshLang() {
        initMessages(ACCESS_EGG);
        modeName.refresh(order, getMessagesSearchMode());
        cmpNameSorting.refresh(getFacade().getTranslatedBooleansCurLanguage());
        cmpStepsSorting.refresh(getFacade().getTranslatedBooleansCurLanguage());
    }

    public void clearResults() {
        getFacade().clearFoundResultsStoragePokemon();
        refreshResults();
        changePages();
        changeNav();
        getWindow().pack();
    }

    @Override
    public void search() {
        setInfos();
        getFacade().searchEgg();
        refreshResults();
        changePages();
        changeNav();
        getWindow().pack();
    }

    public void appendResults() {
        //data.getPagination().appendResults(data.getPeople());
        refreshResults();
        changePages();
        changeNav();
        getWindow().pack();
    }

    @Override
    public void newSearch() {
        setInfos();
        getFacade().newSearchEgg();
        refreshResults();
        changePages();
        changeNav();
        getWindow().pack();
    }

    private void setInfos() {
        getFacade().setContentOfNameEgg(convertStringField(name.getText()));
        SearchingMode s_ = modeName.getCurrent();
        getFacade().setSearchModeNameEgg(s_);
        getFacade().setMinStepsEgg(convertNumberField(minSteps.getText()));
        getFacade().setMaxStepsEgg(convertNumberField(maxSteps.getText()));
        getFacade().setCmpNameIncreasingEgg(cmpNameSorting.getCurrent());
        getFacade().setCmpStepsIncreasingEgg(cmpStepsSorting.getCurrent());
        getFacade().setCmpNamePriorityEgg((Integer)cmpNamePrio.getSelectedItem());
        getFacade().setCmpStepsPriorityEgg((Integer)cmpStepsPrio.getSelectedItem());
    }

    @Override
    public void check(int _line) {
        getFacade().checkLineEggs(_line);
        check(_line, getFacade().getLineEgg());
//        int count_ = results.getComponentCount();
//        for (SelectableLabel s: getResultsLabels()) {
//            s.setSelected(false);
//        }
////        for (int i = CustList.SECOND_INDEX; i < count_; i++) {
////            SelectableLabel l_ = (SelectableLabel) results.getComponent(i);
////            l_.setSelected(false);
////        }
//        if (getFacade().getLineEgg() != CustList.INDEX_NOT_FOUND_ELT) {
//            SelectableLabel l_ = getResultsLabels().get(_line);
//            l_.setSelected(true);
//        }
//        for (SelectableLabel s: getResultsLabels()) {
//            s.repaint();
//        }
//        for (int i = CustList.SECOND_INDEX; i < count_; i++) {
//            SelectableLabel l_ = (SelectableLabel) results.getComponent(i);
//            l_.repaint();
//        }
    }
    private void refreshResults() {
        getHeader().clearStrings();
        getResultsLabels().clear();
        results.removeAll();
        EqList<SortingEgg> rendered_ = getFacade().getRenderedEgg();
        CustList<EggLabel> list_ = new CustList<EggLabel>();
        int h_ = Math.max(getFacade().getMap().getSideLength(), HEIGTH_CHARS);
        int nb_ = rendered_.size();
        for (int i = CustList.FIRST_INDEX; i < nb_; i++) {
            EggLabel l_ = new EggLabel(rendered_.get(i));
            l_.setImagesResults(getFacade());
            l_.addMouseListener(new PaginatorEvent(this,i));
            list_.add(l_);
        }
        getHeader().addString(getMessages().getVal(NAME)+SPACES, FIRST_PIXEL);
        int maxPixName_ = getHeader().width(getMessages().getVal(NAME)+SPACES);
        for (EggLabel l: list_) {
            int value_ = l.getFontMetrics(l.getFont()).stringWidth(l.getEgg().getName()+SPACES);
            if (value_ > maxPixName_) {
                maxPixName_ = value_;
            }
        }
        int side_ = getFacade().getMap().getSideLength();
        getHeader().addString(getMessages().getVal(STEPS)+SPACES, side_+maxPixName_);
        int maxPixSteps_ = getHeader().width(getMessages().getVal(STEPS)+SPACES);
        for (EggLabel l: list_) {
            int value_ = l.getFontMetrics(l.getFont()).stringWidth(Integer.toString(l.getEgg().getSteps())+SPACES);
            if (value_ > maxPixSteps_) {
                maxPixSteps_ = value_;
            }
        }
        getHeader().addString(getMessages().getVal(REMAIN_STEPS), side_+maxPixSteps_+maxPixName_);
        for (EggLabel l: list_) {
            l.setNameCoord(maxPixName_, maxPixSteps_, h_);
        }
        results.add(getHeader());
        for (EggLabel l: list_) {
            l.repaint();
            results.add(l);
            getResultsLabels().add(l);
        }
    }
    private void changePages() {
        setAdding(true);
        getPages().removeAllItems();
        int nbPages_ = getFacade().pagesEgg();
        for (int i = CustList.FIRST_INDEX; i < nbPages_; i++) {
            getPages().addItem(i);
        }
        getEnd().setTextAndSize(Integer.toString(nbPages_ - 1));
        setAdding(false);
    }
    private void changeNav() {
        changeNav(getFacade().enabledPreviousEgg(), getFacade().enabledNextEgg(), getFacade().pagesEgg(), getFacade().getNumberPageEgg());
//        previous.setEnabled(getFacade().enabledPreviousEgg());
//        next.setEnabled(getFacade().enabledNextEgg());
//        previousDelta.setEnabled(getFacade().pagesPk() > CustList.FIRST_INDEX);
//        nextDelta.setEnabled(getFacade().pagesPk() > CustList.FIRST_INDEX);
//        begin.setEnabled(getFacade().pagesPk() > CustList.FIRST_INDEX);
//        end.setEnabled(getFacade().pagesPk() > CustList.FIRST_INDEX);
//        adding = true;
//        try {
//            pages.setSelectedIndex(getFacade().getNumberPageEgg());
//        } catch (Exception e_) {
//            pages.setSelectedIndex(CustList.INDEX_NOT_FOUND_ELT);
//        }
//        adding = false;
    }

    @Override
    public void begin() {
        getFacade().beginEgg();
        changeNav();
        refreshResults();
        getWindow().pack();
    }

    @Override
    public void previousDelta() {
        getFacade().previousDeltaEgg();
        changeNav();
        refreshResults();
        getWindow().pack();
    }

    @Override
    public void previous() {
        getFacade().previousEgg();
        changeNav();
        refreshResults();
        getWindow().pack();
    }

    @Override
    public void next() {
        getFacade().nextEgg();
        changeNav();
        refreshResults();
        getWindow().pack();
    }

    @Override
    public void nextDelta() {
        getFacade().nextDeltaEgg();
        changeNav();
        refreshResults();
        getWindow().pack();
    }

    @Override
    public void end() {
        getFacade().endEgg();
        changeNav();
        refreshResults();
        getWindow().pack();
    }
}
