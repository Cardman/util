package code.formathtml;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.files.CommentDelimiters;
import code.expressionlanguage.fwd.Forwards;
import code.formathtml.structs.BeanInfo;
import code.expressionlanguage.analyze.errors.AnalysisMessages;
import code.expressionlanguage.options.ContextFactory;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.options.Options;
import code.formathtml.errors.RendKeyWords;
import code.formathtml.util.BeanCustLgNames;
import code.formathtml.util.DualConfigurationContext;
import code.sml.Element;
import code.sml.ElementList;
import code.util.*;
import code.util.core.NumberUtil;
import code.util.core.StringUtil;

public final class ReadConfiguration {

    private ReadConfiguration(){
    }

    public static boolean loadContext(Element _elt, String _lg, BeanCustLgNames _stds, Configuration _conf, AnalyzedPageEl _page, Forwards _forwards, DualConfigurationContext _context) {
        KeyWords kw_ = new KeyWords();
        AnalysisMessages a_ = new AnalysisMessages();
        RendKeyWords rkw_ = _conf.getRendKeyWords();
        _stds.buildAliases(_elt,_lg, rkw_,kw_, _context.getAnalysisMessages(),a_);
        ContextFactory.validateStds(_forwards, a_, kw_, new CustList<CommentDelimiters>(), _context.getOptions(), _stds.getContent(), _page);
        AnalysisMessages.validateMessageContents(_context.getAnalysisMessages().allMessages(), _page);
        if (!_page.isEmptyMessageError()) {
            return false;
        }
        StringMap<String> allTags_ = rkw_.allTags();
        rkw_.validateTagContents(allTags_, _page);
        rkw_.validateDuplicates(allTags_, _page);
        StringMap<String> allAttrs_ = rkw_.allAttrs();
        rkw_.validateAttrContents(allAttrs_, _page);
        rkw_.validateDuplicates(allAttrs_, _page);
        StringMap<String> allValues_ = rkw_.allValues();
        rkw_.validateValueContents(allValues_, _page);
        rkw_.validateDuplicates(allValues_, _page);
        StringMap<String> allStyleAttrs_ = rkw_.allStyleAttrs();
        rkw_.validateAttrContents(allStyleAttrs_, _page);
        rkw_.validateDuplicates(allStyleAttrs_, _page);
        StringMap<String> allSyleValues_ = rkw_.allStyleValues();
        rkw_.validateStyleValueContents(allSyleValues_, _page);
        rkw_.validateDuplicates(allSyleValues_, _page);
        StringMap<String> allStyleUnits_ = rkw_.allStyleUnits();
        rkw_.validateStyleUnitContents(allStyleUnits_, _page);
        rkw_.validateDuplicates(allStyleUnits_, _page);
        if (!_page.isEmptyStdError()) {
            return false;
        }
        return true;
    }

    public static Options getOptions(Element _elt, DualConfigurationContext _context) {
        Options opt_ = _context.getOptions();
        opt_.setReadOnly(true);
        for (Element c: _elt.getChildElements()) {
            String fieldName_ = c.getAttribute("field");
            if (StringUtil.quickEq(fieldName_, "options")) {
                loadOptions(c, opt_);
            }
        }
        int stack_ = -1;
        int tab_ = 4;
        for (Element c: _elt.getChildElements()) {
            String fieldName_ = c.getAttribute("field");
            if (StringUtil.quickEq(fieldName_, "stackOverFlow")) {
                stack_=(NumberUtil.parseInt(c.getAttribute("value")));
                continue;
            }
            if (StringUtil.quickEq(fieldName_, "tabWidth")) {
                tab_=(NumberUtil.parseInt(c.getAttribute("value")));
            }
        }
        opt_.setStack(stack_);
        opt_.setTabWidth(tab_);
        return opt_;
    }

    private static void loadOptions(Element _elt, Options _opt) {
        _opt.setReadOnly(true);
        for (Element c: _elt.getChildElements()) {
            String fieldName_ = c.getAttribute("field");
            if (StringUtil.quickEq(fieldName_, "classes")) {
                _opt.getTypesInit().add(c.getAttribute("value"));
            }
        }
    }
    public static StringMap<BeanInfo> loadBeans(Element _elt) {
        StringMap<BeanInfo> beans_ = new StringMap<BeanInfo>();
        StringList keys_ = new StringList();
        CustList<BeanInfo> values_ = new CustList<BeanInfo>();
        for (Element c: _elt.getChildElements()) {
            if (c.hasAttribute("key")) {
                keys_.add(c.getAttribute("value"));
            } else {
                values_.add(loadBean(c));
            }
        }
        int len_ = keys_.size();
        for (int i = 0; i < len_; i++) {
            beans_.put(keys_.get(i), values_.get(i));
        }
        return beans_;
    }
    private static BeanInfo loadBean(Element _elt) {
        BeanInfo bean_ = new BeanInfo();
        for (Element c: _elt.getChildElements()) {
            String fieldName_ = c.getAttribute("field");
            if (StringUtil.quickEq(fieldName_, "scope")) {
                bean_.setScope(c.getAttribute("value"));
                continue;
            }
            if (StringUtil.quickEq(fieldName_, "className")) {
                bean_.setClassName(c.getAttribute("value"));
            }
        }
        return bean_;
    }

    public static StringMap<StringMap<String>> loadStringMapStrings(Element _elt) {
        StringMap<StringMap<String>> navigation_;
        navigation_ = new StringMap<StringMap<String>>();
        StringList keys_ = new StringList();
        CustList<StringMap<String>> values_ = new CustList<StringMap<String>>();
        for (Element c: _elt.getChildElements()) {
            if (c.hasAttribute("key")) {
                keys_.add(c.getAttribute("value"));
            } else {
                values_.add(loadStringMapString(c));
            }
        }
        int len_ = keys_.size();
        for (int i = 0; i < len_; i++) {
            navigation_.put(keys_.get(i), values_.get(i));
        }
        return navigation_;
    }
    public static StringMap<String> loadStringMapString(Element _elt) {
        StringMap<String> map_ = new StringMap<String>();
        StringList keys_ = new StringList();
        StringList values_ = new StringList();
        for (Element c: _elt.getChildElements()) {
            if (c.hasAttribute("key")) {
                keys_.add(c.getAttribute("value"));
            } else {
                values_.add(c.getAttribute("value"));
            }
        }
        int len_ = keys_.size();
        for (int i = 0; i < len_; i++) {
            map_.put(keys_.get(i), values_.get(i));
        }
        return map_;
    }

    public static StringList getStringList(Element _elt) {
        ElementList childElements_ = _elt.getChildElements();
        int len_ = childElements_.getLength();
        StringList list_ = new StringList(new CollCapacity(len_));
        for (Element c: childElements_) {
            list_.add(getString(c));
        }
        return list_;
    }
    public static String getString(Element _elt) {
        return _elt.getAttribute("value");
    }
}
