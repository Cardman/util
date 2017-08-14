package code.formathtml;
import java.awt.image.BufferedImage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import code.bean.Bean;
import code.bean.validator.Validator;
import code.bean.validator.ValidatorException;
import code.expressionlanguage.Argument;
import code.expressionlanguage.exceptions.ErrorCausingException;
import code.expressionlanguage.exceptions.InvokeRedinedMethException;
import code.expressionlanguage.opers.util.Struct;
import code.formathtml.exceptions.BadParenthesesException;
import code.formathtml.exceptions.FormNotFoundException;
import code.formathtml.exceptions.InexistingValidatorException;
import code.formathtml.exceptions.NavCaseNotFoundException;
import code.formathtml.images.ConverterBufferedImageIo;
import code.formathtml.util.NodeContainer;
import code.formathtml.util.NodeInformations;
import code.formathtml.util.ValueChangeEvent;
import code.images.ConverterBufferedImage;
import code.resources.ResourceFiles;
import code.serialize.ConverterMethod;
import code.serialize.SerializeXmlObject;
import code.serialize.exceptions.BadAccessException;
import code.serialize.exceptions.InexistingValueForEnum;
import code.serialize.exceptions.NoSuchConverterMethodException;
import code.serialize.exceptions.NoSuchDeclaredMethodException;
import code.serialize.exceptions.RuntimeInstantiationException;
import code.stream.StreamTextFile;
import code.stream.StreamZipFile;
import code.util.CustList;
import code.util.EntryCust;
import code.util.NatTreeMap;
import code.util.NumberMap;
import code.util.Numbers;
import code.util.StringList;
import code.util.StringMap;
import code.util.consts.ConstClasses;
import code.util.exceptions.RuntimeClassNotFoundException;
import code.util.ints.Listable;
import code.util.ints.WithMathFactory;
import code.xml.XmlParser;
import code.xml.exceptions.XmlParseException;

public final class Navigation {

    private static final String NUMBER_ANCHOR = "n-a";

    private static final String NUMBER_INPUT = "n-i";

    private static final String NUMBER_FORM = "n-f";

    private static final String END_TEMP = ">";

    private static final String BEG_TEMP = "<";

//    private static final String UTF_8 = "UTF-8";

//    private static final String EXT = "\\.";
    private static final String EXT = ".";

    private static final String FORMAT_PNG = "png";

    private static final String PROPERTIES = ".properties";

    private static final String CSS = ".css";

    private static final String XML = ".xml";

    private static final String HTML = ".html";

    private static final String TXT = ".txt";

    private static final String JPG = ".jpg";

    private static final String PNG = ".png";

    private static final String END_PATH = ":";

//    private static final String REG_EXP_BEGIN_ARGS = "\\(";
    private static final char BEGIN_ARGS = '(';
    private static final char SEP_ARGS = ',';
    private static final char END_ARGS = ')';

    private static final String ATTRIBUTE_TITLE = "title";

    private static final String ATTRIBUTE_HREF = "href";

    private static final String TAG_A = "a";

    private static final String REF_TAG = "#";

    private static final String TAG_HEAD = "head";

    private static final String TAG_TITLE = "title";

    private static final String PAGE = "page";

    private static final String SESSION = "session";

    private static final String SELECTED = "selected";

    private static final String TAG_OPTION = "option";

    private static final String CHECKED = "checked";

    private static final String ATTRIBUTE_VALUE = "value";

    private static final String ATTRIBUTE_VALUE_MESSAGE = "valueMessage";

    private static final String ATTRIBUTE_FOR = "for";

//    private static final String ATTRIBUTE_MULTIPLE = "multiple";

    private static final String TAG_SPAN = "span";

//    private static final String REG_EXP_POSITIVE_INT = "([0-9]+)";

//    private static final String OFF = "off";

//    private static final String SEP_FIELDS = "&";

//    private static final String SEP_KEY_VALUE = "=";

//    private static final String ENCODE_U_Y_UML = "%u0178";

//    private static final char ENCODE_BEGIN = '%';

//    private static final char NEXT_UNICODE = 'u';

//    private static final char SPACE = ' ';

//    private static final char ENCODE_SPACE = '+';

//    private static final String ENCODE_URL = "%C3%";

//    private static final String ENCODE_PER_CENT = "%25";

    private static final String ON = "on";

    private static final String TAG_SELECT = "select";

    private static final String CHECKBOX = "checkbox";

    private static final String TEXT = "text";

    private static final String RANGE = "range";

    private static final String RADIO = "radio";

    private static final String NUMBER = "number";

    private static final String ATTRIBUTE_TYPE = "type";

//    private static final String ATTRIBUTE_VALUE_CHANGE_EVENT = "valueChangeEvent";

    private static final String ATTRIBUTE_VALIDATOR = "validator";

    private static final String ATTRIBUTE_ID = "id";

    private static final String ATTRIBUTE_GROUP_ID = "groupId";

//    private static final String ALL_TAGS = "*";

    private static final String TAG_INPUT = "input";

    private static final String TEXT_AREA = "textarea";

    private static final String ATTRIBUTE_COMMAND = "command";

    private static final String ATTRIBUTE_ACTION = "action";

//    private static final String ATTRIBUTE_NAME = "name";

//    private static final String TAG_FORM = "form";

    private static final String ATTRIBUTE_ESCAPED_EAMP = "escapedamp";

//    private static final String COMMA = ",";

//    private static final String REG_EXP_ARGS = "(-?[0-9]+(,-?[0-9]+)*)";

    private static final String DOT = ".";

//    private static final String BEGIN_REG_EXP = "^";

//    private static final String REG_EXP_INT = "-?[0-9]+";

    private static final String CALL_METHOD = "$";

    private static final String RETURN_LINE = "\n";

    private static final String EMPTY_STRING = "";

//    private static final char U_Y_UML = 376;
//
//    private static final char MIN_EXT_ASCII = 128;
//    private static final char MAX_EXT_ASCII = 256;
//    private static final int DELTA_EXT_ASCII = 64;
//    private static final char MINUS_CHAR = '-';
    private Configuration session = new Configuration();

    private String currentBeanName;

    private String htmlText;

    private String referenceScroll;

    private String currentUrl;

    private String language;

    private StringMap<String> files = new StringMap<String>();

    private Object dataBase;

    private StringList tooltips = new StringList();

    private String title = EMPTY_STRING;

    private String resourcesFolder = EMPTY_STRING;

    public void setFiles(String _resourcesFile, String _beginPath) {
        StringList list_ = StreamZipFile.getFilesInJar();
        if (list_.isEmpty()) {
            list_ = StreamTextFile.files(_resourcesFile);
        }
        files = new StringMap<String>();
        for (String f: list_) {
            if (!f.startsWith(_beginPath)) {
                continue;
            }
            if (f.endsWith(PNG) || f.endsWith(JPG)) {
                try {
                    files.put(f, toBaseSixtyFour(f));
                } catch (RuntimeException _0) {
                    _0.printStackTrace();
                }
                continue;
            }
            if (f.endsWith(TXT)) {
//                try {
//                    BufferedImage buff_ = ConverterBufferedImageIo.readImage(f);
//                    String txt_ = ConverterBufferedImage.toBaseSixtyFour(buff_, FORMAT_PNG);
//                    files.put(f, txt_);
//                } catch (Exception e_) {
//                    files.put(f, StreamTextFile.ressourceFichier(f));
//                }
                files.put(f, ResourceFiles.ressourceFichier(f));
            }
            if (f.endsWith(HTML) || f.endsWith(XML) || f.endsWith(CSS) || f.endsWith(PROPERTIES)) {
                files.put(f, ResourceFiles.ressourceFichier(f));
            }
        }
    }

    public void setTextFilesWithPrefix(String _beginPath) {
        files = new StringMap<String>();
        for (String f: StreamZipFile.getFilesInJar()) {
            if (!f.startsWith(_beginPath)) {
                continue;
            }
            if (f.endsWith(TXT)) {
                files.put(f, ResourceFiles.ressourceFichier(f));
            }
            if (f.endsWith(HTML) || f.endsWith(XML) || f.endsWith(CSS) || f.endsWith(PROPERTIES)) {
                files.put(f, ResourceFiles.ressourceFichier(f));
            }
        }
    }

    public void setFilesWithPrefix(String _beginPath) {
        files = new StringMap<String>();
        for (String f: StreamZipFile.getFilesInJar()) {
            if (!f.startsWith(_beginPath)) {
                continue;
            }
            if (f.endsWith(PNG) || f.endsWith(JPG)) {
                try {
                    files.put(f, toBaseSixtyFour(f));
                } catch (RuntimeException _0) {
                    _0.printStackTrace();
                }
                continue;
            }
            if (f.endsWith(TXT)) {
                files.put(f, ResourceFiles.ressourceFichier(f));
            }
            if (f.endsWith(HTML) || f.endsWith(XML) || f.endsWith(CSS) || f.endsWith(PROPERTIES)) {
                files.put(f, ResourceFiles.ressourceFichier(f));
            }
        }
    }

    public void setRelativeFiles(String _resourcesFile, String _relativePath) {
        StringList list_ = StreamZipFile.getFilesInJar();
        if (list_.isEmpty()) {
            list_ = StreamTextFile.files(_resourcesFile);
        }
        files = new StringMap<String>();
//        list_ = new StringList(list_).filter(BEGIN_REG_EXP+_resourcesFile+StreamTextFile.SEPARATEUR);
        list_ = new StringList(list_).filterBeginsWith(_resourcesFile+StreamTextFile.SEPARATEUR);
        for (String f: list_) {
//            String f_ = f.replaceAll(BEGIN_REG_EXP+_relativePath+StreamTextFile.SEPARATEUR, EMPTY_STRING);
            String f_ = StringList.replaceBegin(f, _relativePath+StreamTextFile.SEPARATEUR);
            if (f.endsWith(PNG) || f.endsWith(JPG)) {
                try {
                    files.put(f_, toBaseSixtyFour(f));
                } catch (RuntimeException _0) {
                    _0.printStackTrace();
                }
                continue;
            }
            if (f.endsWith(TXT)) {
                try {
                    BufferedImage buff_ = ConverterBufferedImageIo.readImage(f);
                    String txt_ = ConverterBufferedImage.toBaseSixtyFour(buff_, FORMAT_PNG);
                    files.put(f_, txt_);
                } catch (RuntimeException _0) {
                    files.put(f_, ResourceFiles.ressourceFichier(f));
                }
            }
            if (f.endsWith(HTML) || f.endsWith(XML) || f.endsWith(CSS) || f.endsWith(PROPERTIES)) {
                files.put(f_, ResourceFiles.ressourceFichier(f));
            }
        }
    }

    public static String toBaseSixtyFour(String _fileName) {
        BufferedImage bu_ = ResourceFiles.resourceBufferedImage(_fileName);
        StringList ext_ = StringList.splitStrings(_fileName, EXT);
        return ConverterBufferedImage.toBaseSixtyFour(bu_, ext_.get(CustList.SECOND_INDEX));
//        return ConverterBufferedImage.toBaseSixtyFour(bu_, _fileName.split(EXT)[1]);
    }

    public void setFiles(StringMap<String> _web, StringMap<String> _images) {
        files = new StringMap<String>();
        for (String f: _web.getKeys()) {
            files.put(f, _web.getVal(f));
        }
        for (String f: _images.getKeys()) {
            files.put(f, ConverterBufferedImage.toBaseSixtyFour(_images.getVal(f)));
        }
    }

    public void setFiles(StringMap<String> _files, String _beginEncoding) {
        if (_beginEncoding != null) {
            files = new StringMap<String>();
            for (String f: _files.getKeys()) {
                if (!f.startsWith(_beginEncoding)) {
                    files.put(f, _files.getVal(f));
                    continue;
                }
                files.put(f, ConverterBufferedImage.toBaseSixtyFour(_files.getVal(f)));
            }
        }
    }

    public void loadConfiguration(String _conf) {
        boolean found_ = false;
        String fileName_ = EMPTY_STRING;
        for (EntryCust<String, String> e: files.entryList()) {
            if (e.getKey().equalsIgnoreCase(_conf)) {
                fileName_ = e.getKey();
                found_ = true;
                break;
            }
        }
        String content_;
        if (found_) {
            content_ = files.getVal(fileName_);
        } else {
            content_ = ResourceFiles.ressourceFichier(_conf);
        }
//        if (files.contains(_conf)) {
//            content_ = files.getVal(_conf);
//        } else {
//            content_ = StreamTextFile.ressourceFichier(_conf);
//        }
        session = (Configuration) SerializeXmlObject.newObjectFromXmlString(content_);
        session.init();
        if (session.getMathFactory() == null) {
            if (dataBase instanceof WithMathFactory<?>) {
                session.setMathFactory(((WithMathFactory<?>)dataBase).getMathFactory());
            }
        }
//        try {
//            session = (Configuration) SerializeXmlObject.fromXmlStringObject(content_);
//        } catch (ClassCastException e_) {
//            session = (Configuration) SerializeXmlObject.fromXmlStringObject(StreamTextFile.ressourceFichier(_conf));
//        } catch (Exception e_) {
//            e_.printStackTrace();
//            session = (Configuration) SerializeXmlObject.fromXmlStringObject(StreamTextFile.ressourceFichier(_conf));
//        }
    }

    public void setLanguage(String _language) {
        language = _language;
    }

    public void setDataBase(Object _dataBase) {
        dataBase = _dataBase;
    }

    public StringList getTooltips() {
        return tooltips;
    }

    public String getHtmlText() {
        return htmlText;
    }

//    public Document getDocument() {
//        return XmlParser.parseSaxHtml(htmlText);
//    }

    public String getHtmlTextFormatted() {
//        Document doc_ = XmlParser.parseSaxHtml(htmlText);
//        return XmlParser.toFormattedHtml(doc_);
        return XmlParser.toFormattedHtml(session.getDocument());
    }

    public String getReferenceScroll() {
        return referenceScroll;
    }

    public void initializeSession() {
        for (EntryCust<String, Bean> e: session.getBeans().entryList()) {
            Bean bean_ = newBean(e.getValue());
            bean_.setForms(new StringMap<Object>());
            bean_.setDataBase(dataBase);
            bean_.setLanguage(language);
//            bean_.setNavigation(this);
            e.setValue(bean_);
        }
//        for (String b: session.getBeans().getKeys()) {
//            Bean bean_ = newBean(session.getBeans().getVal(b));
//            bean_.setForms(new Map<String, Object>());
//            bean_.setDataBase(dataBase);
//            bean_.setLanguage(language);
//            bean_.setNavigation(this);
//            session.getBeans().put(b, bean_);
//        }
//        for (Bean bean_ : session.getBeans().values()) {
//            //Bean bean_ = session.getBeans().getVal(b);
//            //String scope_ = bean_.getScope();
//            //String className_ = bean_.getClassName();
//            //bean_ = newBean(dataBase, bean_.getClassName(), className_, new Map<String, Object>(), scope_);
//            //bean_ = newBean(bean_);
//            bean_.setForms(new Map<String, Object>());
//            bean_.setDataBase(dataBase);
//            //bean_.setClassName(bean_.getClassName());
//            bean_.setLanguage(language);
//            bean_.setNavigation(this);
//            //session.getBeans().put(b, bean_);
//        }
        String currentUrl_ = session.getFirstUrl();
        String text_ = ExtractFromResources.loadPage(session, files, currentUrl_, resourcesFolder);
        String currentBeanName_;
        try {
            Document doc_ = XmlParser.parseSaxHtml(text_, false, true);
            Element root_ = doc_.getDocumentElement();
            session.setDocument(doc_);
            currentBeanName_ = root_.getAttribute(session.getPrefix()+FormatHtml.BEAN_ATTRIBUTE);
        } catch (RuntimeException _0) {
            throw new XmlParseException(ExtractFromResources.getRealFilePath(currentUrl_), text_);
        }
        htmlText = FormatHtml.processImports(text_, session, language, files, resourcesFolder);
        //For title
        currentBeanName = currentBeanName_;
        currentUrl = currentUrl_;
        setupText(htmlText);
    }

    public void refresh() {
        for (Bean b: session.getBeans().values()) {
            b.setLanguage(language);
        }
        try {
            processAnchorRequest(currentUrl);
        } catch (RuntimeException _0) {
            session.setCurrentUrl(currentUrl);
            String textToBeChanged_ = ExtractFromResources.loadPage(session, files, StringList.getFirstToken(currentUrl,REF_TAG), resourcesFolder);
            session.setDocument(XmlParser.parseSaxHtml(textToBeChanged_, false));
            textToBeChanged_ = FormatHtml.processImports(
                    textToBeChanged_, session, language, files, resourcesFolder);
            setupText(textToBeChanged_);
        } catch (Error _0) {
            session.setCurrentUrl(currentUrl);
            String textToBeChanged_ = ExtractFromResources.loadPage(session, files, StringList.getFirstToken(currentUrl,REF_TAG), resourcesFolder);
            session.setDocument(XmlParser.parseSaxHtml(textToBeChanged_, false));
            textToBeChanged_ = FormatHtml.processImports(
                    textToBeChanged_, session, language, files, resourcesFolder);
            setupText(textToBeChanged_);
        }
    }

    public void processAnchorRequest(String _anchorRef) {
        String textToBeChanged_;
        if (_anchorRef.contains(CALL_METHOD)) {
            session.clearPages();
            HtmlPage htmlPage_ = session.getHtmlPage();
            ImportingPage ip_ = new ImportingPage(true);
            ip_.setPrefix(session.getPrefix());
            ip_.setHtml(htmlText);
            session.addPage(ip_);
            FormatHtml.setEscapedChars(session, session.getDocument(), htmlText);
            Element node_;
            if (htmlPage_.isForm()) {
                Document doc_ = session.getDocument();
                node_ = XmlParser.getFirstElementByAttribute(doc_, NUMBER_FORM, String.valueOf(htmlPage_.getUrl()));
            } else {
                Document doc_ = session.getDocument();
                node_ = XmlParser.getFirstElementByAttribute(doc_, NUMBER_ANCHOR, String.valueOf(htmlPage_.getUrl()));
                if (node_.getAttribute(ATTRIBUTE_HREF).isEmpty()) {
                    htmlPage_.setUsedFieldUrl(ip_.getPrefix()+ATTRIBUTE_COMMAND);
                } else if (node_.getAttribute(ATTRIBUTE_HREF).endsWith(END_PATH)) {
                    htmlPage_.setUsedFieldUrl(ip_.getPrefix()+ATTRIBUTE_COMMAND);
                } else {
                    htmlPage_.setUsedFieldUrl(ATTRIBUTE_HREF);
                }
            }
            ip_.setProcessingNode(node_);
            ip_.setProcessingAttribute(htmlPage_.getUsedFieldUrl());
            ip_.setLookForAttrValue(true);
            ip_.setOffset(0);
//            Pattern pattern_ = Pattern
//                    .compile(REG_EXP_ARGS);
            int indexPoint_ = _anchorRef.indexOf(DOT);
            String action_ = _anchorRef
                    .substring(indexPoint_ + 1);
            String methodName_;
            String suffix_;
            String strArgs_;
            boolean getArg_ = true;
            if (action_.indexOf(BEGIN_ARGS) == CustList.INDEX_NOT_FOUND_ELT) {
                methodName_ = action_;
                suffix_ = EMPTY_STRING;
                strArgs_ = EMPTY_STRING;
                getArg_ = false;
            } else {
                methodName_ = action_.substring(CustList.FIRST_INDEX, action_.indexOf(BEGIN_ARGS));
                suffix_ = action_.substring(action_.indexOf(BEGIN_ARGS));
                strArgs_ = suffix_.substring(CustList.SECOND_INDEX, suffix_.length() - 1);
                StringBuilder str_ = new StringBuilder();
                for (char c: suffix_.toCharArray()) {
                    if (isPartOfArgument(c)) {
                        continue;
                    }
                    str_.append(c);
                }
                suffix_ = str_.toString();
            }
//            String key_ = action_.replaceAll(REG_EXP_INT, EMPTY_STRING);
//            String key_ = removeDigitsMinus(action_);
//            Matcher match_ = pattern_.matcher(action_);
//            String argsString_ = EMPTY_STRING;
//            if (match_.find()) {
//                argsString_ = match_.group(1);
//            }
            CustList<Argument> args_ = new CustList<Argument>();
//            for (String l : StringList.splitStrings(argsString_, COMMA)) {
//                if (l.isEmpty()) {
//                    continue;
//                }
//                args_.add(new Long(l));
//            }
//            for (String l: numbers(action_)) {
//                args_.add(Argument.numberToArgument(l));
//            }
            if (getArg_) {
                ip_.addToOffset(indexPoint_+1+action_.indexOf(BEGIN_ARGS));
                for (String l: StringList.splitChars(strArgs_, SEP_ARGS)) {
                    try {
                        args_.add(Argument.numberToArgument(l));
                    } catch (RuntimeException _0) {
                        throw new BadParenthesesException(session.joinPages());
                    }
                    ip_.addToOffset(l.length()+1);
                }
                ip_.setOffset(0);
            }
//            String command_ = action_;
//            command_ = command_.split(REG_EXP_BEGIN_ARGS)[0];
//            String command_ = StringList.getFirstToken(action_, BEGIN_ARGS);
            String beanName_ = _anchorRef
                    .substring(_anchorRef.indexOf(CALL_METHOD) + 1, indexPoint_);
            ip_.setOffset(_anchorRef.indexOf(CALL_METHOD) + 1);
            Bean bean_ = getNotNullBean(beanName_);
//            Object return_ = FormatHtml.invokeMethodWithNumbers(
//                    session, bean_, command_, args_.toArray(new Argument[0]));
            ip_.setOffset(indexPoint_+1);
            ip_.setGlobalArgumentObj(bean_);
            Object return_ = HtmlRequest.invokeMethodWithNumbers(
                    session, bean_, methodName_, Argument.toArgArray(args_));
            StringMap<Object> forms_ = bean_.getForms();
            String urlDest_ = currentUrl;
            if (return_ != null) {
//                urlDest_ = session.getNavigation()
////                        .getVal(beanName_ + DOT + key_)
//                        .getVal(beanName_ + DOT + methodName_+suffix_)
//                        .getVal(return_.toString());
                ip_.setOffset(_anchorRef.length());
                urlDest_ = getUrlDest(beanName_ + DOT + methodName_+suffix_, return_);
//                        .getVal(beanName_ + DOT + key_)
//                        .getVal(beanName_ + DOT + methodName_+suffix_)
//                        .getVal(return_.toString());
                if (urlDest_ == null) {
                    urlDest_ = currentUrl;
                }
            }
            for (EntryCust<String, Bean> e: session.getBeans().entryList()) {
                if (!reinitBean(urlDest_, beanName_, e.getKey())) {
                    continue;
                }
                bean_ = e.getValue();
                bean_ = newBean(bean_);
                bean_.setForms(forms_);
                e.setValue(bean_);
            }
//            for (String b : session.getBeans().getKeys()) {
//                if (!reinitBean(urlDest_, beanName_, b)) {
//                    continue;
//                }
//                bean_ = session.getBeans().getVal(b);
//                bean_ = newBean(bean_);
//                bean_.setForms(forms_);
//                session.getBeans().put(b, bean_);
//            }
            String currentUrl_ = urlDest_;
            session.setCurrentUrl(currentUrl_);
            String dest_ = StringList.getFirstToken(urlDest_, REF_TAG);
            textToBeChanged_ = ExtractFromResources.loadPage(session, files, dest_, resourcesFolder);
            String currentBeanName_;
            try {
                Document doc_ = XmlParser.parseSaxHtml(textToBeChanged_, false, true);
                Element root_ = doc_.getDocumentElement();
                session.setDocument(doc_);
                currentBeanName_ = root_.getAttribute(session.getPrefix()+FormatHtml.BEAN_ATTRIBUTE);
            } catch (RuntimeException _0) {
                throw new XmlParseException(ExtractFromResources.getRealFilePath(dest_), textToBeChanged_);
            }
            bean_ = getNotNullBean(currentBeanName_);
            bean_.setForms(forms_);
            textToBeChanged_ = FormatHtml.processImports(
                    textToBeChanged_, session, language, files, resourcesFolder);
            currentBeanName = currentBeanName_;
            currentUrl = currentUrl_;
            setupText(textToBeChanged_);
            return;
        }
        if (_anchorRef.isEmpty()) {
            return;
        }
        Bean bean_ = getBean(currentBeanName);
        StringMap<Object> forms_;
        try {
            forms_ = bean_.getForms();
        } catch (Error _0) {
            forms_ = null;
        } catch (RuntimeException _0) {
            forms_ = null;
        }
        for (EntryCust<String, Bean> e: session.getBeans().entryList()) {
            if (!reinitBean(_anchorRef, currentBeanName, e.getKey())) {
                continue;
            }
            bean_ = e.getValue();
            bean_ = newBean(bean_);
            bean_.setForms(forms_);
            e.setValue(bean_);
        }
//        for (String b : session.getBeans().getKeys()) {
//            if (!reinitBean(_anchorRef, currentBeanName, b)) {
//                continue;
//            }
//            bean_ = session.getBeans().getVal(b);
//            bean_ = newBean(bean_);
//            bean_.setForms(forms_);
//            session.getBeans().put(b, bean_);
//        }
        String currentUrl_ = _anchorRef;
        session.setCurrentUrl(currentUrl_);
        String dest_ = StringList.getFirstToken(_anchorRef, REF_TAG);
        textToBeChanged_ = ExtractFromResources.loadPage(session, files, dest_, resourcesFolder);
        String currentBeanName_;
        try {
            Document doc_ = XmlParser.parseSaxHtml(textToBeChanged_, false, true);
            Element root_ = doc_.getDocumentElement();
            session.setDocument(doc_);
            currentBeanName_ = root_.getAttribute(session.getPrefix()+FormatHtml.BEAN_ATTRIBUTE);
        } catch (RuntimeException _0) {
            throw new XmlParseException(ExtractFromResources.getRealFilePath(dest_), textToBeChanged_);
        }
        bean_ = getBean(currentBeanName_);
        try {
            bean_.setForms(forms_);
        } catch (Error _0) {
        } catch (RuntimeException _0) {
        }
        textToBeChanged_ = FormatHtml.processImports(
                textToBeChanged_, session, language, files, resourcesFolder);
        currentBeanName = currentBeanName_;
        currentUrl = currentUrl_;
        setupText(textToBeChanged_);
    }

    private static boolean isPartOfArgument(char _char) {
        if (_char == SEP_ARGS) {
            return false;
        }
        if (_char == BEGIN_ARGS) {
            return false;
        }
        if (_char == END_ARGS) {
            return false;
        }
        return true;
    }

//    private static String removeDigitsMinus(String _string) {
//        StringBuilder str_ = new StringBuilder();
//        for (char c: _string.toCharArray()) {
//            if (c == MINUS_CHAR) {
//                continue;
//            }
//            if (Character.isDigit(c)) {
//                continue;
//            }
//            str_.append(c);
//        }
//        return str_.toString();
//    }

    public void processFormRequest() {
        session.clearPages();
        HtmlPage htmlPage_ = session.getHtmlPage();
        ImportingPage ip_ = new ImportingPage(true);
        ip_.setPrefix(session.getPrefix());
        ip_.setHtml(htmlText);
        session.addPage(ip_);
        NumberMap<Long,NatTreeMap<Long,NodeContainer>> containersMap_;
        containersMap_ = htmlPage_.getContainers();
        long lg_ = htmlPage_.getUrl();
//        Document doc_ = XmlParser.parseSaxHtml(htmlText);
        Document doc_ = session.getDocument();
//        NodeList nodeList_ = doc_.getElementsByTagName(TAG_FORM);
//        int length_ = nodeList_.getLength();
        String actionCommand_ = EMPTY_STRING;
//        boolean found_ = false;
        //retrieving form that is submitted
        Element formElement_ = XmlParser.getFirstElementByAttribute(doc_, NUMBER_FORM, String.valueOf(lg_));
        if (formElement_ == null) {
            htmlPage_.setUsedFieldUrl(EMPTY_STRING);
            throw new FormNotFoundException(EMPTY_STRING);
        }
        htmlPage_.setForm(true);

//        for (int i = CustList.FIRST_INDEX; i < length_; i++) {
//            Element n_ = (Element) nodeList_.item(i);
//            if (!StringList.eq(n_.getAttribute(ATTRIBUTE_NAME),_formName)) {
//                continue;
//            }
//            lg_ = Long.parseLong(n_.getAttribute(NUMBER_FORM));
//            found_ = true;
//            formElement_ = n_;
//            htmlPage_.setUrl(lg_);
//            //As soon as the form is retrieved, then process on it and exit from the loop
//            actionCommand_ = n_.getAttribute(ATTRIBUTE_ACTION);
//            if (actionCommand_.isEmpty()
//                    || actionCommand_.endsWith(END_PATH)) {
//                actionCommand_ = n_.getAttribute(ATTRIBUTE_COMMAND);
//            }
//            break;
//        }
        //As soon as the form is retrieved, then process on it and exit from the loop
        actionCommand_ = formElement_.getAttribute(ATTRIBUTE_ACTION);
        htmlPage_.setUsedFieldUrl(ATTRIBUTE_ACTION);
        if (actionCommand_.isEmpty()
                || actionCommand_.endsWith(END_PATH)) {
            actionCommand_ = formElement_.getAttribute(ip_.getPrefix()+ATTRIBUTE_COMMAND);
            htmlPage_.setUsedFieldUrl(ip_.getPrefix()+ATTRIBUTE_COMMAND);
        }

//        if (!found_) {
//            throw new FormNotFoundException(_formName);
//        }
//        for (String k: _query.getKeys()) {
//            _varMethodsQuery.put(k, EMPTY_STRING);
//        }
//        _varMethodsQuery.putAllMap(_varMethods);
        StringMap<String> errors_;
        errors_ = new StringMap<String>();
        StringMap<Object[]> errorsArgs_;
        errorsArgs_ = new StringMap<Object[]>();
        //TODO converters
        for (EntryCust<Long, NodeContainer> e: containersMap_.getVal(lg_).entryList()) {
            NodeInformations nInfos_ = e.getValue().getNodeInformation();
            String valId_ = nInfos_.getValidator();
            String id_ = nInfos_.getId();
            if (valId_.isEmpty()) {
                continue;
            }
            Element node_ = XmlParser.getElementById(doc_, ATTRIBUTE_ID, ip_.getPrefix()+ATTRIBUTE_GROUP_ID, id_);
            ip_.setProcessingNode(node_);
            ip_.setProcessingAttribute(ip_.getPrefix()+ATTRIBUTE_VALIDATOR);
            ip_.setLookForAttrValue(true);
            ip_.setOffset(0);
            Validator validator_;
            try {
                validator_ = session.getValidators().getVal(valId_);
            } catch (VirtualMachineError _0) {
                throw new ErrorCausingException(valId_+RETURN_LINE+session.joinPages());
            } catch (RuntimeException _0) {
                throw new InvokeRedinedMethException(valId_+RETURN_LINE+session.joinPages());
            }
            if (validator_ == null) {
                throw new InexistingValidatorException(valId_+RETURN_LINE+session.joinPages());
            }
            StringList v_ = nInfos_.getValue();
            String className_ = nInfos_.getInputClass();
            Object obj_ = null;
            int indexTemp_;
            indexTemp_ = className_.indexOf(BEG_TEMP);
            boolean isList_ = false;
            String suffix_ = EMPTY_STRING;
            Class<?> tempClass_ = null;
            if (indexTemp_ != CustList.INDEX_NOT_FOUND_ELT) {
                String prefix_ = EMPTY_STRING;
                prefix_ = className_.substring(CustList.FIRST_INDEX, indexTemp_);
                suffix_ = className_.substring(indexTemp_);
                try {
                    tempClass_ = ConstClasses.classAliasForNameNotInit(prefix_);
                } catch (RuntimeClassNotFoundException _0) {
                    throw new InvokeRedinedMethException(session.joinPages(), new Struct(_0));
                }
                if (Listable.class.isAssignableFrom(tempClass_)) {
                    isList_ = true;
                }
            }
            if (isList_) {
//                CustList<Object> list_ = new CustList<Object>();
                Listable<?> list_ = (Listable<?>) instance(tempClass_);
                String contentClass_ = suffix_;
                contentClass_ = StringList.removeStrings(contentClass_, BEG_TEMP, END_TEMP);
                for (String v:v_) {
                    try {
                        CustList.add(list_, retrieveObjectByClassName(v, contentClass_));
                    } catch (Error _0) {
                    } catch (RuntimeException _0) {
                    }
                }
                obj_ = list_;
            } else {
                try {
                    obj_ = retrieveObjectByClassName(v_.first(), className_);
                } catch (Error _0) {
                } catch (RuntimeException _0) {
                }
            }
            try {
                try {
                    validator_.validate(this, node_, obj_);
                } catch (ValidatorException _0) {
                    errors_.put(id_, _0.format());
                    errorsArgs_.put(id_, _0.getArgs());
                }
            } catch (VirtualMachineError _0) {
                throw new ErrorCausingException(session.joinPages(), new Struct(_0));
            } catch (RuntimeException _0) {
                throw new InvokeRedinedMethException(session.joinPages(), new Struct(_0));
            }
        }
        //begin deleting previous errors
        NodeList spansForm_ = formElement_.getElementsByTagName(TAG_SPAN);
        int lengthSpansForom_ = spansForm_.getLength();
        for (int j = CustList.FIRST_INDEX; j < lengthSpansForom_; j++) {
            Element elt_ = (Element) spansForm_.item(j);
            if (!elt_.hasAttribute(ip_.getPrefix()+ATTRIBUTE_FOR)) {
                continue;
            }
            NodeList children_ = elt_.getChildNodes();
            int ch_ = children_.getLength();
            for (int i = CustList.FIRST_INDEX; i < ch_; i++) {
                elt_.removeChild(children_.item(i));
            }
            Text text_ = doc_.createTextNode(FormatHtml.SPACE);
            elt_.appendChild(text_);
        }
        //end deleting previous errors
        if (!errors_.isEmpty()) {
            processFormErrors(doc_, formElement_, lg_, errors_, errorsArgs_);
            session.clearPages();
            return;
        }
        NatTreeMap<Long, NodeContainer> containers_ = containersMap_.getVal(lg_);
        //Setting values for bean
//        updateBean(_inputClasses, _changing, _query, _varMethodsQuery);
        updateBean(containers_);
        session.clearPages();

        //invoke application
        processAnchorRequest(actionCommand_);
    }

    /**
    @throws RuntimeClassNotFoundException
    @throws InexistingValueForEnum
    @throws NoSuchDeclaredMethodException*/
    private void updateBean(NatTreeMap<Long, NodeContainer> _containers) {
//        if (testing && !applyUpdate) {
//            return;
//        }
        Document doc_ = session.getDocument();
        for (EntryCust<Long, NodeContainer> e: _containers.entryList()) {
//            if (k.isEmpty()) {
//                continue;
//            }
            NodeContainer nCont_ = e.getValue();
            if (!nCont_.isEnabled()) {
                continue;
            }
            Element input_ = XmlParser.getFirstElementByAttribute(doc_, NUMBER_INPUT, String.valueOf(e.getKey()));
            session.getLastPage().setProcessingNode(input_);
            session.getLastPage().setProcessingAttribute(EMPTY_STRING);
//            String simpleKey_ = k;
//            String beanName_ = k.substring(0, k.indexOf(DOT));
            Bean bean_ = getBean(nCont_.getBeanName());
//            String varMethod_ = _varMethods.getVal(k);
            String simpleKey_ = nCont_.getNodeInformation().getName();
            Object obj_ = nCont_.getTypedField();
            Numbers<Long> indexes_ = new Numbers<Long>();
            for (String n: positiveNumbers(simpleKey_)) {
                indexes_.add(Long.parseLong(n));
            }
            String changingValue_ = EMPTY_STRING;
            String ch_ = nCont_.getNodeInformation().getChanging();
            if (!ch_.isEmpty()) {
                String method_ = ch_;
                try {
                    //checking for existence of method_ in the bean class
                    bean_.getClass().getMethod(method_,
                            ValueChangeEvent.class);
                    changingValue_ = method_;
                } catch (Error _0) {
                } catch (RuntimeException _0) {
                } catch (NoSuchMethodException _0) {
                }
            }
            nCont_.getNodeInformation().setChanging(changingValue_);
            Object newObj_;
            StringList v_ = nCont_.getNodeInformation().getValue();
            String className_ = nCont_.getNodeInformation().getInputClass();
            try {
                if (obj_ == null) {
                    newObj_ = retrieveObjectByClassName(v_.first(), className_);
                } else if (obj_ instanceof Listable<?>){
                    Listable<?> list_ = (Listable<?>) instance(obj_.getClass());
                    String contentClass_ = className_.substring(CustList.class.getName().length());
                    contentClass_ = StringList.removeStrings(contentClass_, BEG_TEMP, END_TEMP);
                    for (String v:v_) {
                        try {
                            CustList.add(list_, retrieveObjectByClassName(v, contentClass_));
                        } catch (Error _0) {
                        } catch (RuntimeException _0) {
                        }
                    }
                    newObj_ = list_;
                } else {
                    newObj_ = retrieveObjectByClassName(v_.first(), obj_.getClass().getName());
                }
            } catch (VirtualMachineError _0) {
                throw new ErrorCausingException(session.joinPages(), new Struct(_0));
            } catch (RuntimeException _0) {
                throw new InvokeRedinedMethException(session.joinPages(), new Struct(_0));
            }
//            Object procObj_ = e.getValue().getObject();
//            if (procObj_ != null) {
//                session.getLastPage().setGlobalArgumentObj(procObj_);
//            }
            Struct procObj_ = e.getValue().getStruct();
            if (procObj_ != null) {
                session.getLastPage().setGlobalArgumentStruct(procObj_);
            }
            HtmlRequest.setObject(session, e.getValue(), newObj_, indexes_);
        }
    }

    private Object retrieveObjectByClassName(String _value, String _className) {
        //case where it is better to test on class of the value
        Class<?> class_;
        try {
            class_ = ConstClasses.classAliasForObjectNameNotInit(_className);
        } catch (RuntimeException _0) {
            throw new InvokeRedinedMethException(new Struct(_0));
        } catch (VirtualMachineError _0) {
            throw new ErrorCausingException(session.joinPages(),new Struct(_0));
        } catch (ExceptionInInitializerError _0) {
            throw new ErrorCausingException(session.joinPages(),new Struct(_0));
        }
        if (class_.isEnum()) {
            //Enum
            for (Object o : class_.getEnumConstants()) {
                if (StringList.quickEq(((Enum<?>)o).name(),_value)) {
                    return o;
                }
            }
            throw new InexistingValueForEnum(_value,class_.getName());
        }
//        try {
//            //Enum
//            for (Enum<?> o : class_.asSubclass(Enum.class).getEnumConstants()) {
//                if (StringList.eq(o.name(),_value)) {
//                    return o;
//                }
//            }
//            throw new InexistingValueForEnum(_value,class_.getName());
//        } catch (ClassCastException _0) {
//        }
        //Boolean
        if (class_ == Boolean.class) {
            return StringList.quickEq(_value,ON);
        }
        //Number
        if (Number.class.isAssignableFrom(class_)) {
            return ExtractObject.instanceByString(session, class_,_value);
        }
//        try {
//            class_.asSubclass(Number.class);
//            return ExtractObject.instanceByString(session, class_,_value);
//        } catch (ClassCastException _0) {
//        }
        //Primitivable
//        try {
//            class_.asSubclass(Primitivable.class);
//            return instanceByString(class_,_value);
//        } catch (ClassCastException _0) {
//        }
        try {
            return ConverterMethod.newObject(class_, _value);
        } catch (NoSuchConverterMethodException _0) {
        } catch (VirtualMachineError _0) {
            throw new ErrorCausingException(session.joinPages(),new Struct(_0));
        } catch (ExceptionInInitializerError _0) {
            throw new ErrorCausingException(session.joinPages(),new Struct(_0));
        }
        return _value;
    }

//    private static Object instanceByString(Class<?> _class, String _arg) {
//        try {
//            String name_ = _class.getName();
//            Object value_;
//            if (name_.equalsIgnoreCase(Integer.class.getName())) {
//                value_ = Integer.parseInt(_arg);
//            } else if (name_.equalsIgnoreCase(Long.class.getName())) {
//                value_ = Long.parseLong(_arg);
//            } else if (name_.equalsIgnoreCase(Short.class.getName())) {
//                value_ = Short.parseShort(_arg);
//            } else if (name_.equalsIgnoreCase(Byte.class.getName())) {
//                value_ = Byte.parseByte(_arg);
//            } else if (name_.equalsIgnoreCase(BigInteger.class.getName())) {
//                value_ = new BigInteger(_arg);
//            } else if (name_.equalsIgnoreCase(BigDecimal.class.getName())) {
//                value_ = new BigDecimal(_arg);
//            } else if (name_.equalsIgnoreCase(Double.class.getName())) {
//                value_ = Double.parseDouble(_arg);
//            } else if (name_.equalsIgnoreCase(Float.class.getName())) {
//                value_ = Float.parseFloat(_arg);
//            } else if (name_.equalsIgnoreCase(AtomicInteger.class.getName())) {
//                value_ = new AtomicInteger(Integer.parseInt(_arg));
//            } else if (name_.equalsIgnoreCase(AtomicLong.class.getName())) {
//                value_ = new AtomicLong(Long.parseLong(_arg));
//            } else {
//                Constructor<?> const_ = _class.getConstructor(String.class);
//                return const_.newInstance(_arg);
//            }
//            return value_;
//        } catch (InstantiationException _0) {
//            throw new RuntimeInstantiationException(_0);
//        } catch (NoSuchMethodException _0) {
//            throw new NoSuchDeclaredMethodException(_0);
//        } catch (InvocationTargetException _0) {
//            throw new InvokingException(_0);
//        } catch (IllegalAccessException _0) {
//            throw new BadAccessException(_0);
//        }
//    }

    private static Object instance(Class<?> _class) {
        try {
            return _class.newInstance();
        } catch (InstantiationException _0) {
            throw new RuntimeInstantiationException(_0);
        } catch (IllegalAccessException _0) {
            throw new BadAccessException(_0, _class.getName());
        } catch (VirtualMachineError _0) {
            throw new ErrorCausingException(new Struct(_0));
        } catch (ExceptionInInitializerError _0) {
            throw new ErrorCausingException(new Struct(_0));
        }
    }

    private static StringList positiveNumbers(String _string) {
        StringList tokens_ = StringList.getWordsSeparators(_string);
        StringList newList_ = new StringList();
        for (String t: tokens_) {
            if (StringList.isPositiveNumber(t)) {
                newList_.add(t);
            }
        }
        return newList_;
    }

//    private static StringList numbers(String _string) {
//        StringList tokens_ = StringList.getWordsSeparators(_string);
//        StringList newList_ = new StringList();
//        int i_ = CustList.FIRST_INDEX;
//        for (String t: tokens_) {
//            if (StringList.isPositiveNumber(t)) {
//                String prefix_ = String.valueOf(MINUS_CHAR);
//                if (!tokens_.get(i_ - 1).endsWith(prefix_)) {
//                    prefix_ = EMPTY_STRING;
//                }
//                newList_.add(prefix_+t);
//            }
//            i_ ++;
//        }
//        return newList_;
//    }

    private void processFormErrors(Document _doc, Element _formElement, long _id,
            StringMap<String> _errors, StringMap<Object[]> _errorsArgs) {
        HtmlPage htmlPage_ = session.getHtmlPage();
        NumberMap<Long,NatTreeMap<Long,NodeContainer>> containersMap_;
        containersMap_ = htmlPage_.getContainers();
        NatTreeMap<Long, NodeContainer> containers_ = containersMap_.getVal(_id);
        for (String i : _errors.getKeys()) {
            NodeList spans_ = _formElement.getElementsByTagName(TAG_SPAN);
            int lengthSpans_ = spans_.getLength();
            for (int j = CustList.FIRST_INDEX; j < lengthSpans_; j++) {
                Element elt_ = (Element) spans_.item(j);
                if (!StringList.quickEq(elt_.getAttribute(session.getPrefix()+ATTRIBUTE_FOR),i)) {
                    continue;
                }
                int len_ = elt_.getChildNodes().getLength();
                if (len_ > CustList.ONE_ELEMENT) {
                    continue;
                }
                if (len_ == CustList.ONE_ELEMENT) {
                    elt_.removeChild(elt_.getChildNodes().item(0));
                }
                String error_ = _errors.getVal(i);
                if (!elt_.getAttribute(session.getPrefix()+ATTRIBUTE_VALUE_MESSAGE).isEmpty()) {
                    String valueMessage_ = elt_.getAttribute(session.getPrefix()+ATTRIBUTE_VALUE_MESSAGE);
                    error_ = HtmlRequest.formatErrorMessage(session, valueMessage_, elt_.hasAttribute(session.getPrefix()+ATTRIBUTE_ESCAPED_EAMP), language, files, resourcesFolder, _errorsArgs.getVal(i));
                }
                Text text_ = _doc.createTextNode(error_);
                elt_.appendChild(text_);
            }
        }
        NodeList inputs_ = _doc.getElementsByTagName(TAG_INPUT);
        int lengthInputs_ = inputs_.getLength();
        for (int i = CustList.FIRST_INDEX; i < lengthInputs_; i++) {
            Element elt_ = (Element) inputs_.item(i);
            String idInput_ = elt_.getAttribute(NUMBER_INPUT);
            if (idInput_.isEmpty()) {
                continue;
            }
            NodeContainer nCont_ = containers_.getVal(Long.parseLong(idInput_));
            if (StringList.quickEq(elt_.getAttribute(ATTRIBUTE_TYPE),TEXT)) {
                elt_.setAttribute(ATTRIBUTE_VALUE, nCont_.getNodeInformation().getValue().first());
                continue;
            }
            if (StringList.quickEq(elt_.getAttribute(ATTRIBUTE_TYPE),NUMBER)) {
                elt_.setAttribute(ATTRIBUTE_VALUE, nCont_.getNodeInformation().getValue().first());
                continue;
            }
            if (StringList.quickEq(elt_.getAttribute(ATTRIBUTE_TYPE),RANGE)) {
                elt_.setAttribute(ATTRIBUTE_VALUE, nCont_.getNodeInformation().getValue().first());
                continue;
            }
            if (StringList.quickEq(elt_.getAttribute(ATTRIBUTE_TYPE),CHECKBOX)) {
                if (StringList.quickEq(nCont_.getNodeInformation().getValue().first(),ON)) {
                    elt_.setAttribute(CHECKED, CHECKED);
                } else {
                    elt_.removeAttribute(CHECKED);
                }
                continue;
            }
            if (StringList.quickEq(elt_.getAttribute(ATTRIBUTE_TYPE),RADIO)) {
                String value_ = elt_.getAttribute(ATTRIBUTE_VALUE);
                if (value_ == null) {
                    value_ = EMPTY_STRING;
                }
                if (StringList.quickEq(nCont_.getNodeInformation().getValue().first(), value_)) {
                    elt_.setAttribute(CHECKED, CHECKED);
                } else {
                    elt_.removeAttribute(CHECKED);
                }
            }
        }
        inputs_ = _doc.getElementsByTagName(TAG_SELECT);
        lengthInputs_ = inputs_.getLength();
        for (int i = CustList.FIRST_INDEX; i < lengthInputs_; i++) {
            Element elt_ = (Element) inputs_.item(i);
            String idInput_ = elt_.getAttribute(NUMBER_INPUT);
            if (idInput_.isEmpty()) {
                continue;
            }
            NodeContainer nCont_ = containers_.getVal(Long.parseLong(idInput_));
            NodeList options_ = elt_.getElementsByTagName(TAG_OPTION);
            int optionsLen_ = options_.getLength();
            for (int j = CustList.FIRST_INDEX; j < optionsLen_; j++) {
                Element option_ = (Element) options_.item(j);
                if (nCont_.getNodeInformation().getValue().containsStr(option_.getAttribute(ATTRIBUTE_VALUE))) {
                    option_.setAttribute(SELECTED, SELECTED);
                } else {
                    option_.removeAttribute(SELECTED);
                }
            }
        }
        inputs_ = _doc.getElementsByTagName(TEXT_AREA);
        lengthInputs_ = inputs_.getLength();
        for (int i = CustList.FIRST_INDEX; i < lengthInputs_; i++) {
            Element elt_ = (Element) inputs_.item(i);
            String idInput_ = elt_.getAttribute(NUMBER_INPUT);
            if (idInput_.isEmpty()) {
                continue;
            }
            NodeContainer nCont_ = containers_.getVal(Long.parseLong(idInput_));
            NodeList children_ = elt_.getChildNodes();
            int ch_ = children_.getLength();
            for (int j = CustList.FIRST_INDEX; j < ch_; j++) {
                elt_.removeChild(children_.item(j));
            }
            Text text_ = _doc.createTextNode(nCont_.getNodeInformation().getValue().first());
            elt_.appendChild(text_);
        }
        setupText(XmlParser.toHtml(_doc));
    }

    boolean reinitBean(String _dest, String _beanName, String _currentBean) {
        if (!StringList.quickEq(_currentBean,_beanName)) {
            return false;
        }
        Bean bean_ = getNotNullBean(_currentBean);
        if (StringList.quickEq(bean_.getScope(),SESSION)) {
            return false;
        }
        if (StringList.quickEq(bean_.getScope(),PAGE)) {
            if (StringList.quickEq(currentUrl,StringList.getFirstToken(_dest, REF_TAG))) {
                return false;
            }
        }
        return true;
    }

    Bean newBean(Bean _bean) {
//        Bean bean_ = (Bean) Constants.classForNameCustomFolder(_bean.getClassName()).newInstance();
        try {
            Bean bean_ = (Bean) ConstClasses.classAliasForNameNotInit(_bean.getClassName()).newInstance();
            bean_.setDataBase(_bean.getDataBase());
            bean_.setForms(_bean.getForms());
            bean_.setClassName(ConstClasses.resolve(_bean.getClassName()));
            bean_.setLanguage(language);
//            bean_.setNavigation(this);
            bean_.setScope(_bean.getScope());
            return bean_;
        } catch (IllegalAccessException _0) {
            throw new BadAccessException(_0, _bean.getClassName());
        } catch (InstantiationException _0) {
            throw new RuntimeInstantiationException(_0, _bean.getClassName());
        } catch (RuntimeClassNotFoundException _0) {
            throw new RuntimeClassNotFoundException(_bean.getClassName());
        } catch (VirtualMachineError _0) {
            throw new ErrorCausingException(new Struct(_0));
        } catch (ExceptionInInitializerError _0) {
            throw new ErrorCausingException(new Struct(_0));
        }
    }

    void setupText(String _text) {
        String textToDisplay_ = _text;
        tooltips.clear();
//        Document doc_ = XmlParser.parseSaxHtml(textToDisplay_);
        Document doc_ = session.getDocument();
        NodeList nodes_ = doc_.getElementsByTagName(TAG_A);
        int size_ = nodes_.getLength();
        for (int i = CustList.FIRST_INDEX; i < size_; i++) {
            Element node_ = (Element) nodes_.item(i);
            if (node_.getAttribute(ATTRIBUTE_HREF).isEmpty()) {
                continue;
            }
            String title_ = node_.getAttribute(ATTRIBUTE_TITLE);
            if (title_.isEmpty()) {
                continue;
            }
            tooltips.add(title_);
        }
//        try {
//            Document doc_ = XmlParser.parseSaxHtml(textToDisplay_);
//            NodeList nodes_ = doc_.getElementsByTagName(TAG_A);
//            int size_ = nodes_.getLength();
//            for (int i = CustList.FIRST_INDEX; i < size_; i++) {
//                Node node_ = nodes_.item(i);
//                NamedNodeMap map_ = node_.getAttributes();
//                Node href_ = map_.getNamedItem(ATTRIBUTE_HREF);
//                if (href_ == null) {
//                    continue;
//                }
//                Node title_ = map_.getNamedItem(ATTRIBUTE_TITLE);
//                if (title_ == null) {
//                    continue;
//                }
//                tooltips.add(title_.getNodeValue());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        title = EMPTY_STRING;
//        Document doc_ = XmlParser.parseSaxHtml(textToDisplay_);
//        NodeList nodes_ = doc_.getElementsByTagName(TAG_HEAD);
        nodes_ = doc_.getElementsByTagName(TAG_HEAD);
//        int size_ = nodes_.getLength();
        size_ = nodes_.getLength();
        for (int i = CustList.FIRST_INDEX; i < size_; i++) {
            Element node_ = (Element) nodes_.item(i);
            NodeList subNodes_ = node_.getElementsByTagName(TAG_TITLE);
            int subListSize_ = subNodes_.getLength();
            for (int j = CustList.FIRST_INDEX; j < subListSize_; j++) {
                Element subNode_ = (Element) subNodes_.item(j);
//                title = XmlParser.transformSpecialChars(subNode_.getTextContent().trim());
                title = subNode_.getTextContent().trim();
            }
        }
//        try {
//            Document doc_ = XmlParser.parseSaxHtml(textToDisplay_);
//            NodeList nodes_ = doc_.getElementsByTagName(TAG_HEAD);
//            int size_ = nodes_.getLength();
//            for (int i = CustList.FIRST_INDEX; i < size_; i++) {
//                Element node_ = (Element) nodes_.item(i);
//                NodeList subNodes_ = node_.getElementsByTagName(TAG_TITLE);
//                int subListSize_ = subNodes_.getLength();
//                for (int j = CustList.FIRST_INDEX; j < subListSize_; j++) {
//                    Element subNode_ = (Element) subNodes_.item(j);
//                    title = XmlParser.transformSpecialChars(subNode_.getTextContent().trim());
//                }
//            }
//        } catch (Exception e_) {
//            e_.printStackTrace();
//        }
        htmlText = textToDisplay_;
        StringList tokens_ = StringList.splitStrings(currentUrl, REF_TAG);
        if (tokens_.size() > CustList.ONE_ELEMENT) {
            referenceScroll = tokens_.get(CustList.SECOND_INDEX);
        } else {
            referenceScroll = EMPTY_STRING;
        }
//        if (currentUrl.contains(REF_TAG)) {
//            StringList.splitStrings(currentUrl, REF_TAG).get(1);
//            referenceScroll = currentUrl.split(REF_TAG)[1];
//        } else {
//            referenceScroll = EMPTY_STRING;
//        }
    }

    private String getUrlDest(String _method, Object _return ) {
        StringMap<String> cases_;
        try {
            cases_ = session.getNavigation().getVal(_method);
        } catch (Error _0) {
            throw new NavCaseNotFoundException(_method+RETURN_LINE+session.joinPages(),_0);
        } catch (RuntimeException _0) {
            throw new NavCaseNotFoundException(_method+RETURN_LINE+session.joinPages(),_0);
        }
        if (cases_ == null) {
            throw new NavCaseNotFoundException(_method+RETURN_LINE+session.joinPages());
        }
        try {
            return cases_.getVal(_return.toString());
        } catch (VirtualMachineError _0) {
            throw new ErrorCausingException(session.joinPages(), new Struct(_0));
        } catch (RuntimeException _0) {
            throw new ErrorCausingException(session.joinPages(), new Struct(_0));
        }
    }
    private Bean getBean(String _beanName) {
        try {
            return session.getBeans().getVal(_beanName);
        } catch (VirtualMachineError _0) {
            throw new ErrorCausingException(session.joinPages(), new Struct(_0));
        } catch (RuntimeException _0) {
            throw new InvokeRedinedMethException(session.joinPages(), new Struct(_0));
        }
    }
    private Bean getNotNullBean(String _beanName) {
        try {
            Bean b_ = session.getBeans().getVal(_beanName);
            b_.getClass();
            return b_;
        } catch (VirtualMachineError _0) {
            throw new ErrorCausingException(session.joinPages(), new Struct(_0));
        } catch (RuntimeException _0) {
            throw new InvokeRedinedMethException(session.joinPages(), new Struct(_0));
        }
    }

    public HtmlPage getHtmlPage() {
        return session.getHtmlPage();
    }

    public Configuration getSession() {
        return session;
    }

    public void setSession(Configuration _session) {
        session = _session;
    }

    public String getCurrentBeanName() {
        return currentBeanName;
    }

    public void setCurrentBeanName(String _currentBeanName) {
        currentBeanName = _currentBeanName;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String _currentUrl) {
        currentUrl = _currentUrl;
    }

    public StringMap<String> getFiles() {
        return files;
    }

    public void setFiles(StringMap<String> _files) {
        files = _files;
    }

    public String getLanguage() {
        return language;
    }

    public Object getDataBase() {
        return dataBase;
    }

    public void setHtmlText(String _htmlText) {
        htmlText = _htmlText;
    }

    public void setTooltips(StringList _tooltips) {
        tooltips = _tooltips;
    }

    public String getTitle() {
        return title;
    }

    public String getResourcesFolder() {
        return resourcesFolder;
    }

    public void setResourcesFolder(String _resourcesFolder) {
        resourcesFolder = _resourcesFolder;
    }
}
