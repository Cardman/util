package code.formathtml;

import static code.formathtml.EquallableExUtil.assertEq;

import javax.imageio.ImageIO;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import code.bean.Bean;
import code.bean.translator.Translator;
import code.expressionlanguage.exceptions.BadExpressionLanguageException;
import code.expressionlanguage.exceptions.InvokeRedinedMethException;
import code.formathtml.classes.BeanOne;
import code.formathtml.classes.MyStrangeTranslator;
import code.formathtml.classes.MyTranslator;
import code.formathtml.classes.SimpleMathFactory;
import code.formathtml.exceptions.InexistingTranslatorException;
import code.util.StringList;
import code.util.StringMap;
import code.util.consts.Constants;
import code.xml.XmlParser;

@SuppressWarnings("static-method")
public class ExtractObjectTest {

    @BeforeClass
    public static void initialize() {
        ImageIO.setUseCache(false);
        Constants.setLanguage("LOCALE");
    }

    @Test
    public void formatMessage1Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"$composite.integer\"/></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("5", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage2Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"$message\"><param value=\"One\" quoted=\"quoted\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("Test One", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage3Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"$message\"><!-- Comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("Test 5", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage4Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc {0}";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"msg_example,one\"/></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("Description one", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage5Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc {0}";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"msg_example,three\"><param value=\"elt\" quoted=\"quoted\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc elt", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage6Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc {0}";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"msg_example,three\"><!-- comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc 5", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage7Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\"><!-- comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc &lt;5&gt;", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage8Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc \"{0}\"";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\"><!-- comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc \"5\"", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage9Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\"><!-- comment --><param value=\"&quot;\" quoted=\"quoted\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc &lt;\"&gt;", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }


    @Test
    public void formatMessage10Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"$composite.integer\" escapedamp='true'/></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("5", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage11Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"$message\" escapedamp='true'><param value=\"One\" quoted=\"quoted\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("Test One", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage12Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"$message\" escapedamp='true'><!-- Comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("Test 5", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage13Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc {0}";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"msg_example,one\" escapedamp='true'/></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("Description one", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage14Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc {0}";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"msg_example,three\" escapedamp='true'><param value=\"elt\" quoted=\"quoted\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc elt", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage15Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc {0}";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"msg_example,three\" escapedamp='true'><!-- comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc 5", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage16Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\" escapedamp='true'><!-- comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc &lt;5&gt;", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage17Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc \"{0}\"";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\" escapedamp='true'><!-- comment --><param value=\"$composite.integer\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc \"5\"", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage18Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\" escapedamp='true'><!-- comment --><param value=\"&quot;\" quoted=\"quoted\"/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc &lt;\"&gt;", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }


    @Test
    public void formatMessage19Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc \"{0}\"";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\" escapedamp='true'><!-- comment --><param value=\"$composite.string\" escaped='escaped'/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setString("{escaped}");
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc \"'{'escaped'}'\"", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void formatMessage20Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "<html xmlns:c='javahtml'><body><c:message value=\"sample/file,three\" escapedamp='true'><!-- comment --><param value=\"{escaped}\" quoted=\"quoted\" escaped=''/></c:message></body></html>";
        Document doc_ = XmlParser.parseSaxHtml(html_);
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        Element elt_ = (Element) doc_.getElementsByTagName("c:message").item(0);
//        Element elt_ = (Element) doc_.getElementsByTagName("c_message").item(0);
//        Map<String,LoopVariable> vars_ = new Map<String,LoopVariable>();
//        LoopVariable global_ = new LoopVariable();
//        global_.setElement(bean_);
//        vars_.put("", global_);
//        Map<String,LocalVariable> loc_ = new Map<String,LocalVariable>();
        assertEq("desc &lt;'{'escaped'}'&gt;", ExtractObject.formatMessage(conf_, locale_, ip_, elt_, files_));
    }

    @Test
    public void evaluateMathExpression1Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body><c:set var=\"n\" mathexpr=\"1+1\" isbool=\"false\"/>{n;.}</body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().getStrings().add("FIRST");
        bean_.getComposite().getStrings().add("SECOND");
        bean_.getComposite().setInteger(5);
        bean_.getComposites().get(0).setStrings(new StringList("F"));
        bean_.getComposites().get(1).setStrings(new StringList("S"));
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        conf_.setMathFactory(new SimpleMathFactory());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        assertEq(2L,((Long)ExtractObject.evaluateMathExpression(ip_, conf_, false, "1+1")).longValue());
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setDocument(doc_);
//        conf_.setHtml(html_);
//        String render_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        assertXMLEqualRuntime("<html xmlns:c='javahtml' xmlns='javahtml'><body>2</body></html>", render_);
    }

    @Test
    public void evaluateMathExpression2Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body><c:set var=\"n\" mathexpr=\"false|true\" isbool=\"true\"/>{n;.}<c:set var=\"n\" mathexpr=\"false\" isbool=\"true\"/>{n;.}</body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().getStrings().add("FIRST");
        bean_.getComposite().getStrings().add("SECOND");
        bean_.getComposite().setInteger(5);
        bean_.getComposites().get(0).setStrings(new StringList("F"));
        bean_.getComposites().get(1).setStrings(new StringList("S"));
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        conf_.setMathFactory(new SimpleMathFactory());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        assertEq(true,(Boolean)ExtractObject.evaluateMathExpression(ip_, conf_, true, "false|true"));
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setDocument(doc_);
//        conf_.setHtml(html_);
//        String render_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        assertXMLEqualRuntime("<html xmlns:c='javahtml' xmlns='javahtml'><body>truefalse</body></html>", render_);
    }

    @Test
    public void evaluateMathExpression3Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body><c:set var=\"n\" mathexpr=\"false|true\" isbool=\"true\"/>{n;.}<c:set var=\"n\" mathexpr=\"false\" isbool=\"true\"/>{n;.}</body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().getStrings().add("FIRST");
        bean_.getComposite().getStrings().add("SECOND");
        bean_.getComposite().setInteger(5);
        bean_.getComposites().get(0).setStrings(new StringList("F"));
        bean_.getComposites().get(1).setStrings(new StringList("S"));
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        conf_.setMathFactory(new SimpleMathFactory());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        assertEq(false,(Boolean)ExtractObject.evaluateMathExpression(ip_, conf_, true, "false"));
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setDocument(doc_);
//        conf_.setHtml(html_);
//        String render_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        assertXMLEqualRuntime("<html xmlns:c='javahtml' xmlns='javahtml'><body>truefalse</body></html>", render_);
    }

    @Test
    public void evaluateMathExpression4Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body><c:set var=\"n\" mathexpr=\"`composite.integer`+1\" isbool=\"false\"/>{n;.}</body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().getStrings().add("FIRST");
        bean_.getComposite().getStrings().add("SECOND");
        bean_.getComposite().setInteger(5);
        bean_.getComposites().get(0).setStrings(new StringList("F"));
        bean_.getComposites().get(1).setStrings(new StringList("S"));
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        conf_.setMathFactory(new SimpleMathFactory());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        assertEq(6L,((Long)ExtractObject.evaluateMathExpression(ip_, conf_, false, "`composite.integer`+1")).longValue());
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setDocument(doc_);
//        conf_.setHtml(html_);
//        String render_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        assertXMLEqualRuntime("<html xmlns:c='javahtml' xmlns='javahtml'><body>6</body></html>", render_);
    }
    @Test
    public void evaluateMathExpression5Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body><c:while mathexpr=\"`composite.strings.isEmpty()`\">EMPTY</c:while></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().getStrings().add("FIRST");
        bean_.getComposite().getStrings().add("SECOND");
        bean_.getComposite().setInteger(5);
        bean_.getComposites().get(0).setStrings(new StringList("F"));
        bean_.getComposites().get(1).setStrings(new StringList("S"));
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        conf_.setMathFactory(new SimpleMathFactory());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        assertEq(false,(Boolean)ExtractObject.evaluateMathExpression(ip_, conf_, true, "`composite.strings.isEmpty()`"));
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        conf_.setDocument(doc_);
//        String render_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        assertXMLEqualRuntime("<html xmlns:c='javahtml' xmlns='javahtml'><body/></html>", render_);
    }

    @Test
    public void evaluateMathExpression6Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        bean_.getComposites().get(0).setStrings(new StringList("F"));
        bean_.getComposites().get(1).setStrings(new StringList("S"));
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        conf_.setMathFactory(new SimpleMathFactory());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        assertEq(true,(Boolean)ExtractObject.evaluateMathExpression(ip_, conf_, true, "`composite.strings.isEmpty()`"));
    }

    @Test
    public void formatNamedVariables1Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "{composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("5", formated_);
    }

    @Test
    public void formatNamedVariables2Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>{composite.integer|trans}</body></html>";
        String html_ = "{trans,composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
        //String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, bean_);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>5 &amp;lt;= 5</body></html>", formated_);
        assertEq("5 &lt;= 5", formated_);
    }

    @Test
    public void formatNamedVariables3Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>'{composite.integerToo}'</body></html>";
        String html_ = "'{composite.integerToo}'";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, bean_);
//        assertEq("<html xmlns:c=\"javahtm:l\" xmlns=\"javahtml\"><body>{composite.integerToo}</body></html>", formated_);
        assertEq("{composite.integerToo}", formated_);
    }

    @Test
    public void formatNamedVariables4Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>'{composite.integerToo|trans}'</body></html>";
        String html_ = "'{composite.integerToo|trans}'";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, bean_);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>{composite.integerToo|trans}</body></html>", formated_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("{composite.integerToo|trans}", formated_);
    }

    @Test
    public void formatNamedVariables5Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''composite.integer";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, bean_);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'composite.integer", formated_);
    }

    @Test
    public void formatNamedVariables6Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''{composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, null);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'5", formated_);
    }

    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables7Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''{composite.integer";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, null);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables8Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, null);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables9Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''{composite.integer{}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, null);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test
    public void formatNamedVariables10Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''{composite.integer} {composite.integer+composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, null);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'5 10", formated_);
    }

    @Test
    public void formatNamedVariables11Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''{'c'} {composite.integer+composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, null);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'c 10", formated_);
    }

    @Test
    public void formatNamedVariables12Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>''composite.integer</body></html>";
        String html_ = "''{'c'}{composite.integer+composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
//        Document doc_ = XmlParser.parseSaxHtml(html_, false, true);
//        conf_.setHtml(html_);
//        String formated_ = FormatHtml.processHtml(doc_, "bean_one", conf_, locale_, files_);
//        String formated_ = FormatHtml.formatNamedVariables(html_, conf_, files_, null);
//        assertEq("<html xmlns:c=\"javahtml\" xmlns=\"javahtml\"><body>'composite.integer</body></html>", formated_);
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'c10", formated_);
    }

    @Test
    public void formatNamedVariables13Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "''{'{'} {composite.integer+composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'{ 10", formated_);
    }


    @Test
    public void formatNamedVariables14Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "''{'}'} {composite.integer+composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'} 10", formated_);
    }

    @Test
    public void formatNamedVariables15Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "''{\"{}\"} {composite.integer+composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        String formated_ = ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
        assertEq("'{} 10", formated_);
    }
    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables1FailTest() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "{";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables2FailTest() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "{}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables3FailTest() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>{composite.integer|trans}</body></html>";
        String html_ = "{[trans}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables4FailTest() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>{composite.integer|trans}</body></html>";
        String html_ = "{[trans";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }
    
    @Test(expected=InexistingTranslatorException.class)
    public void formatNamedVariables5FailTest() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>{composite.integer|trans}</body></html>";
        String html_ = "{trans2,composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test(expected=InvokeRedinedMethException.class)
    public void formatNamedVariables6FailTest() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
//        String html_ = "<html xmlns:c='javahtml'><body>{composite.integer|trans}</body></html>";
        String html_ = "{trans,composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyStrangeTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    @Test(expected=BadExpressionLanguageException.class)
    public void formatNamedVariables7FailTest() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description two\nthree=desc &lt;{0}&gt;";
        String html_ = "{,composite.integer}";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        BeanOne bean_ = new BeanOne();
        bean_.getComposite().setInteger(5);
        Configuration conf_ = new Configuration();
        conf_.setBeans(new StringMap<Bean>());
        conf_.getBeans().put("bean_one", bean_);
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setTranslators(new StringMap<Translator>());
        conf_.getTranslators().put("trans", new MyTranslator());
        addImportingPage(conf_, false);
        addBean(conf_, bean_);
        ImportingPage ip_ = conf_.getLastPage();
        ExtractObject.formatNumVariables(html_, conf_, ip_, files_);
    }

    private static void addImportingPage(Configuration _conf, boolean _rendering) {
        _conf.setupValiatorsTranslators("LOCALE");
        _conf.addPage(new ImportingPage(_rendering));
    }

    private static void addBean(Configuration _conf, Bean _bean) {
//        LoopVariable lv_ = new LoopVariable();
//        lv_.setElement(_bean);
//        lv_.setExtendedExpression("");
//        _conf.getLastPage().getVars().put("", lv_);
        _conf.getLastPage().setGlobalArgumentObj(_bean);
    }

//    @Test
//    public void getFields1Test() {
//        String html_ = "<html><body>composite.integer</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(0, fields_.size());
//    }
//
//    @Test
//    public void getFields2Test() {
//        String html_ = "<html><body>{composite.integer}</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(1, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//    }
//
//    @Test
//    public void getFields3Test() {
//        String html_ = "<html><body>'quoted'</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(0, fields_.size());
//    }
//
//    @Test
//    public void getFields4Test() {
//        String html_ = "<html><body>{composite.integer} '{mycommented}'</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(1, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//    }
//
//    @Test
//    public void getFields5Test() {
//        String html_ = "<html><body>'{mycommented}'</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(0, fields_.size());
//    }
//
//    @Test
//    public void getFields6Test() {
//        String html_ = "<html><body>'{mycommented}' {composite.integer}</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(1, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//    }
//
//    @Test
//    public void getFields7Test() {
//        String html_ = "<html><body>'{mycommented} {composite.integer}</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(0, fields_.size());
//    }
//
//    @Test
//    public void getFields8Test() {
//        String html_ = "<html><body>{composite.integer} '{mycommented}</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(1, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//    }
//
//    @Test
//    public void getFields9Test() {
//        String html_ = "<html><body>{composite.integer} {mycommented}</body></html>'";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(2, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//        assertEq("mycommented",fields_.get(1));
//    }
//
//    @Test
//    public void getFields10Test() {
//        String html_ = "<html><body>{composite.integer} {mycommented}</body></html>''";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(2, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//        assertEq("mycommented",fields_.get(1));
//    }
//
//    @Test
//    public void getFields11Test() {
//        String html_ = "<html><body>{composite.integer} '{mycommented}' {compositeTwo.integer}</body></html>";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(2, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//        assertEq("compositeTwo.integer",fields_.get(1));
//    }
//
//    @Test
//    public void getFields12Test() {
//        String html_ = "<html><body>{composite.integer} {mycommented}</body></html>'quoted'";
//        StringList fields_ = ExtractObject.getFields(html_);
//        assertEq(2, fields_.size());
//        assertEq("composite.integer",fields_.get(0));
//        assertEq("mycommented",fields_.get(1));
//    }


}
