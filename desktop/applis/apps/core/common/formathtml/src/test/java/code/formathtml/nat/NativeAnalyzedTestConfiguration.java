package code.formathtml.nat;

import code.bean.nat.BeanNatLgNames;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.fwd.Forwards;
import code.formathtml.Configuration;
import code.formathtml.analyze.AnalyzingDoc;
import code.formathtml.analyze.blocks.AnaRendDocumentBlock;
import code.formathtml.util.DualConfigurationContext;
import code.util.StringMap;

class NativeAnalyzedTestConfiguration {
    private final Configuration configuration;
    private final AnalyzedPageEl analyzing;
    private final Forwards forwards;
    private final BeanNatLgNames adv;
    private final AnalyzingDoc analyzingDoc = new AnalyzingDoc();
    private final ContextEl context;
    private DualConfigurationContext dual;
    private StringMap<AnaRendDocumentBlock> analyzed = new StringMap<AnaRendDocumentBlock>();

    NativeAnalyzedTestConfiguration(Configuration configuration, NativeAnalyzedTestContext analyzing, Forwards _forwards, BeanNatLgNames _standards) {
        this.configuration = configuration;
        forwards = _forwards;
        adv= _standards;
        dual = analyzing.getDual();
        analyzingDoc.setContent(adv);
        this.analyzing = analyzing.getAnalyzing();
        context = analyzing.getContext();
    }

    Configuration getConfiguration() {
        return configuration;
    }

    AnalyzedPageEl getAnalyzing() {
        return analyzing;
    }

    Forwards getForwards() {
        return forwards;
    }

    AnalyzingDoc getAnalyzingDoc() {
        return analyzingDoc;
    }

    ContextEl getContext() {
        return context;
    }

    boolean isEmptyErrors() {
        return analyzing.isEmptyErrors();
    }

    void setNavigation(StringMap<StringMap<String>> stringMapStringMap) {
        configuration.setNavigation(stringMapStringMap);
    }

    StringMap<StringMap<String>> getNavigation() {
        return configuration.getNavigation();
    }

    BeanNatLgNames getAdv() {
        return adv;
    }

    StringMap<AnaRendDocumentBlock> getAnalyzed() {
        return analyzed;
    }

    void setAnalyzed(StringMap<AnaRendDocumentBlock> analyzed) {
        this.analyzed = analyzed;
    }

    DualConfigurationContext getDual() {
        return dual;
    }
}
