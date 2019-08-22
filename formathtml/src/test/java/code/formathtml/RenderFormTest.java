package code.formathtml;

import code.bean.Bean;

import code.expressionlanguage.AnalyzedPageEl;
import code.expressionlanguage.structs.Struct;

import code.sml.Document;
import code.sml.DocumentBuilder;
import code.util.StringMap;
import org.junit.Test;

import static code.formathtml.EquallableExUtil.assertEq;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public final class RenderFormTest extends CommonRender {
    @Test
    public void process1Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description \nthree=desc &lt;{0}&gt;<a c:command=\"$click\">two</a>After\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"$click\" name=\"myform\"><input type=\"radio\" name=\"first.value\" c:varValue=\"2\"/><input type=\"radio\" name=\"first.value\" c:varValue=\"4\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public Dto first:");
        file_.append(" $public $void beforeDisplaying(){");
        file_.append("  first=$new Dto(4):");
        file_.append(" }");
        file_.append(" $public $void click(){");
        file_.append(" }");
        file_.append("}");
        file_.append("$public $class pkg.Dto{");
        file_.append(" $public $int value:");
        file_.append(" $public Dto($int p){");
        file_.append("  value = p;.;:");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElThird(filesSec_);
        conf_.setBeans(new StringMap<Bean>());
        addImportingPage(conf_);
        Struct bean_ = RenderExpUtil.processEl("$new pkg.BeanOne()", 0, conf_).getStruct();
        addBeanInfo(conf_,"bean_one",bean_);
        conf_.clearPages();
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);


        Document doc_ = DocumentBuilder.parseSax(html_);
        conf_.getAnalyzingDoc().setFiles(files_);
        setLocale(locale_, conf_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        conf_.getContext().setAnalyzing(new AnalyzedPageEl());
        conf_.getAnalyzing().setEnabledInternVars(false);
        rendDocumentBlock_.buildFctInstructions(conf_);
        conf_.setDocument(doc_);
        assertTrue(conf_.getClasses().isEmptyErrors());
        String res_ = RendBlock.getRes(rendDocumentBlock_, conf_);
        assertEq("<html><body><form action=\"\" c:command=\"$bean_one.click\" name=\"myform\" n-f=\"0\"><input type=\"radio\" name=\"bean_one.first.value\" n-i=\"0\" value=\"2\"/><input type=\"radio\" name=\"bean_one.first.value\" n-i=\"0\" value=\"4\" checked=\"checked\"/></form></body></html>",res_);
        assertNull(conf_.getException());
    }
    @Test
    public void process2Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description \nthree=desc &lt;{0}&gt;<a c:command=\"$click\">two</a>After\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"$click({0})\" name=\"myform\"><input type=\"radio\" name=\"first.value\" c:varValue=\"2\"/><input type=\"radio\" name=\"first.value\" c:varValue=\"4\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public Dto first:");
        file_.append(" $public $void beforeDisplaying(){");
        file_.append("  first=$new Dto(4):");
        file_.append(" }");
        file_.append(" $public $void click($int i){");
        file_.append(" }");
        file_.append("}");
        file_.append("$public $class pkg.Dto{");
        file_.append(" $public $int value:");
        file_.append(" $public Dto($int p){");
        file_.append("  value = p;.;:");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElThird(filesSec_);
        conf_.setBeans(new StringMap<Bean>());
        addImportingPage(conf_);
        Struct bean_ = RenderExpUtil.processEl("$new pkg.BeanOne()", 0, conf_).getStruct();
        addBeanInfo(conf_,"bean_one",bean_);
        conf_.clearPages();
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);


        Document doc_ = DocumentBuilder.parseSax(html_);
        conf_.getAnalyzingDoc().setFiles(files_);
        setLocale(locale_, conf_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        conf_.getContext().setAnalyzing(new AnalyzedPageEl());
        conf_.getAnalyzing().setEnabledInternVars(false);
        rendDocumentBlock_.buildFctInstructions(conf_);
        
        conf_.setDocument(doc_);
        assertTrue(conf_.getClasses().isEmptyErrors());
        String res_ = RendBlock.getRes(rendDocumentBlock_, conf_);
        assertEq("<html><body><form action=\"\" c:command=\"$bean_one.click(0)\" name=\"myform\" n-f=\"0\"><input type=\"radio\" name=\"bean_one.first.value\" n-i=\"0\" value=\"2\"/><input type=\"radio\" name=\"bean_one.first.value\" n-i=\"0\" value=\"4\" checked=\"checked\"/></form></body></html>",res_);
        assertNull(conf_.getException());
    }
    @Test
    public void process3Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description \nthree=desc &lt;{0}&gt;<a c:command=\"$click\">two</a>After\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form name=\"myform\"/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public Dto first:");
        file_.append(" $public $void beforeDisplaying(){");
        file_.append("  first=$new Dto(4):");
        file_.append(" }");
        file_.append(" $public $void click(){");
        file_.append(" }");
        file_.append("}");
        file_.append("$public $class pkg.Dto{");
        file_.append(" $public $int value:");
        file_.append(" $public Dto($int p){");
        file_.append("  value = p;.;:");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElThird(filesSec_);
        conf_.setBeans(new StringMap<Bean>());
        addImportingPage(conf_);
        Struct bean_ = RenderExpUtil.processEl("$new pkg.BeanOne()", 0, conf_).getStruct();
        addBeanInfo(conf_,"bean_one",bean_);
        conf_.clearPages();
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);


        Document doc_ = DocumentBuilder.parseSax(html_);
        conf_.getAnalyzingDoc().setFiles(files_);
        setLocale(locale_, conf_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        conf_.getContext().setAnalyzing(new AnalyzedPageEl());
        conf_.getAnalyzing().setEnabledInternVars(false);
        rendDocumentBlock_.buildFctInstructions(conf_);
        
        conf_.setDocument(doc_);
        assertTrue(conf_.getClasses().isEmptyErrors());
        String res_ = RendBlock.getRes(rendDocumentBlock_, conf_);
        assertEq("<html><body><form name=\"myform\" n-f=\"0\"/></body></html>",res_);
        assertNull(conf_.getException());
    }
    @Test
    public void process4Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description \nthree=desc &lt;{0}&gt;<a c:command=\"$click\">two</a>After\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"$click({1/0})\" name=\"myform\"><input type=\"radio\" name=\"first.value\" c:varValue=\"2\"/><input type=\"radio\" name=\"first.value\" c:varValue=\"4\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public Dto first:");
        file_.append(" $public $void beforeDisplaying(){");
        file_.append("  first=$new Dto(4):");
        file_.append(" }");
        file_.append(" $public $void click($int i){");
        file_.append(" }");
        file_.append("}");
        file_.append("$public $class pkg.Dto{");
        file_.append(" $public $int value:");
        file_.append(" $public Dto($int p){");
        file_.append("  value = p;.;:");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElThird(filesSec_);
        conf_.setBeans(new StringMap<Bean>());
        addImportingPage(conf_);
        Struct bean_ = RenderExpUtil.processEl("$new pkg.BeanOne()", 0, conf_).getStruct();
        addBeanInfo(conf_,"bean_one",bean_);
        conf_.clearPages();
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);


        Document doc_ = DocumentBuilder.parseSax(html_);
        conf_.getAnalyzingDoc().setFiles(files_);
        setLocale(locale_, conf_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        conf_.getContext().setAnalyzing(new AnalyzedPageEl());
        conf_.getAnalyzing().setEnabledInternVars(false);
        rendDocumentBlock_.buildFctInstructions(conf_);
        
        conf_.setDocument(doc_);
        assertTrue(conf_.getClasses().isEmptyErrors());
        RendBlock.getRes(rendDocumentBlock_, conf_);
        assertNotNull(conf_.getException());
    }
    @Test
    public void process5Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description \nthree=desc &lt;{0}&gt;<a c:command=\"$click\">two</a>After\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"$click\"/><form action=\"\" c:command=\"$click2\"/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public Dto first:");
        file_.append(" $public $void beforeDisplaying(){");
        file_.append("  first=$new Dto(4):");
        file_.append(" }");
        file_.append(" $public $void click(){");
        file_.append(" }");
        file_.append(" $public $void click2(){");
        file_.append(" }");
        file_.append("}");
        file_.append("$public $class pkg.Dto{");
        file_.append(" $public $int value:");
        file_.append(" $public Dto($int p){");
        file_.append("  value = p;.;:");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElThird(filesSec_);
        conf_.setBeans(new StringMap<Bean>());
        addImportingPage(conf_);
        Struct bean_ = RenderExpUtil.processEl("$new pkg.BeanOne()", 0, conf_).getStruct();
        addBeanInfo(conf_,"bean_one",bean_);
        conf_.clearPages();
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);


        Document doc_ = DocumentBuilder.parseSax(html_);
        conf_.getAnalyzingDoc().setFiles(files_);
        setLocale(locale_, conf_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        conf_.getContext().setAnalyzing(new AnalyzedPageEl());
        conf_.getAnalyzing().setEnabledInternVars(false);
        rendDocumentBlock_.buildFctInstructions(conf_);
        
        conf_.setDocument(doc_);
        assertTrue(conf_.getClasses().isEmptyErrors());
        String res_ = RendBlock.getRes(rendDocumentBlock_, conf_);
        assertEq("<html><body><form action=\"\" c:command=\"$bean_one.click\" n-f=\"0\"/><form action=\"\" c:command=\"$bean_one.click2\" n-f=\"1\"/></body></html>",res_);
        assertNull(conf_.getException());
    }
    @Test
    public void process1FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description \nthree=desc &lt;{0}&gt;<a c:command=\"$click\">two</a>After\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"$click({&quot;&quot;},{$null})\" name=\"myform\"><input type=\"radio\" name=\"first.value\" c:varValue=\"2\"/><input type=\"radio\" name=\"first.value\" c:varValue=\"4\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public Dto first:");
        file_.append(" $public $void beforeDisplaying(){");
        file_.append("  first=$new Dto(4):");
        file_.append(" }");
        file_.append(" $public $void click($int i){");
        file_.append(" }");
        file_.append("}");
        file_.append("$public $class pkg.Dto{");
        file_.append(" $public $int value:");
        file_.append(" $public Dto($int p){");
        file_.append("  value = p;.;:");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElThird(filesSec_);
        conf_.setBeans(new StringMap<Bean>());
        addImportingPage(conf_);
        Struct bean_ = RenderExpUtil.processEl("$new pkg.BeanOne()", 0, conf_).getStruct();
        addBeanInfo(conf_,"bean_one",bean_);
        conf_.clearPages();
        conf_.setMessagesFolder(folder_);
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);


        Document doc_ = DocumentBuilder.parseSax(html_);
        conf_.getAnalyzingDoc().setFiles(files_);
        setLocale(locale_, conf_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        conf_.getContext().setAnalyzing(new AnalyzedPageEl());
        conf_.getAnalyzing().setEnabledInternVars(false);
        rendDocumentBlock_.buildFctInstructions(conf_);
        conf_.setDocument(doc_);
        assertTrue(!conf_.getClasses().isEmptyErrors());
    }
}
