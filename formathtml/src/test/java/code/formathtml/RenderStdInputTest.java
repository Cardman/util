package code.formathtml;

import code.util.StringMap;
import org.junit.Test;

import static code.formathtml.EquallableExUtil.assertEq;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public final class RenderStdInputTest extends CommonRender {
    @Test
    public void process1Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"text\" name=\"bean_one.textField\" n-i=\"0\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }

    @Test
    public void process2Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField;");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"text\" name=\"bean_one.textField\" n-i=\"0\" value=\"\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process3Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"checkbox\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public $boolean textField=$true;");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"checkbox\" name=\"bean_one.textField\" n-i=\"0\" checked=\"checked\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process4Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"checkbox\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public $boolean textField;");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"checkbox\" name=\"bean_one.textField\" n-i=\"0\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process5Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input id='myId{0}' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input id=\"myId0\" type=\"text\" name=\"bean_one.textField\" n-i=\"0\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process6Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input c:groupId='myId\\{0\\}\\\\' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input c:groupId=\"myId{0}\\\" type=\"text\" name=\"bean_one.textField\" n-i=\"0\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process7Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input c:groupId='myId{1/0}' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        RendBlock.getRes(rendDocumentBlock_,conf_);
        assertNotNull(getException(conf_));
    }
    @Test
    public void process8Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\" c:convertValue='conv'/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append(" $public String conv(String p){");
        file_.append("  $return p;");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"text\" name=\"bean_one.textField\" n-i=\"0\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process9Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\" c:convertField='convertField'/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append(" $public String convertField(Object p){");
        file_.append("  $return (String)p;");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"text\" name=\"bean_one.textField\" n-i=\"0\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process10Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\" c:convertField='convertField'/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append(" $public String convertField(Object p){");
        file_.append("  $return \"\"+($int)p;");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        RendBlock.getRes(rendDocumentBlock_,conf_);
        assertNotNull(getException(conf_));
    }
    @Test
    public void process11Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"number\" name=\"numberField\" c:varValue=\"numberField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public $int numberField=10;");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"number\" name=\"bean_one.numberField\" n-i=\"0\" value=\"10\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process12Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"range\" name=\"numberField\" c:varValue=\"numberField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public $int numberField=10;");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"range\" name=\"bean_one.numberField\" n-i=\"0\" value=\"10\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process13Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><input type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><input type=\"text\" name=\"textField\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process14Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><c:set className='$var' value='a=$new $int[]{1}'/><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"number\" name=\"a[0]\" c:varValue=\"a[0]\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"number\" name=\"bean_one.a[0]\" n-i=\"0\" value=\"1\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process15Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><c:set className='$var' value='a=$new $int[]{1}'/><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"number\" name=\"a[0]\" c:varValue=\"a[0]\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"text\" name=\"bean_one.textField\" n-i=\"0\" value=\"txt\"/><input type=\"number\" name=\"bean_one.a[0]\" n-i=\"1\" value=\"1\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process16Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><c:set className='$var' value='a=$new $int[]{1}'/><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"number\" name=\"a[0]\" c:varValue=\"a[0]\"/><input type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"number\" name=\"bean_one.a[0]\" n-i=\"0\" value=\"1\"/><input type=\"text\" name=\"bean_one.textField\" n-i=\"1\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process17Test() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField[0]\" c:varValue=\"textField[0]\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String[] textField={\"txt\"};");
        file_.append(" $public String $this($int i){");
        file_.append("  $return textField[i];");
        file_.append(" }");
        file_.append(" $public $void $this($int i){");
        file_.append("  textField[i]=$value;");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(conf_.isEmptyErrors());
        assertEq("<html><body><form action=\"\" c:command=\"page1.html\" name=\"myform\" n-f=\"0\"><input type=\"text\" name=\"bean_one.textField[0]\" n-i=\"0\" value=\"txt\"/><input type=\"submit\" value=\"OK\"/></form></body></html>", RendBlock.getRes(rendDocumentBlock_,conf_));
        assertNull(getException(conf_));
    }
    @Test
    public void process1FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input c:groupId='myId{}' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }
    @Test
    public void process2FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input c:groupId='myId}' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }
    @Test
    public void process3FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input c:groupId='myId\\e' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }
    @Test
    public void process4FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input c:groupId='myId{' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }
    @Test
    public void process5FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\" c:convertField='convertField'/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append(" $public Object convertField(Object p){");
        file_.append("  $return p;");
        file_.append(" }");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);
        
        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");
        
        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }

    @Test
    public void process6FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input c:className='Object' type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);

        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");

        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }

    @Test
    public void process7FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" name=\"textField\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public Object textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);

        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");

        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }
    @Test
    public void process8FailTest() {
        String locale_ = "en";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo=Description <a href=\"\">two</a>\nthree=desc &lt;{0}&gt;\nfour=''asp''";
        String html_ = "<html c:bean=\"bean_one\"><body><form action=\"\" c:command=\"page1.html\" name=\"myform\"><input type=\"text\" c:varValue=\"textField\"/><input type=\"submit\" value=\"OK\"/></form></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(EquallableExUtil.formatFile(folder_,locale_,relative_), content_);
        files_.put("page1.html", html_);
        StringMap<String> filesSec_ = new StringMap<String>();
        StringBuilder file_ = new StringBuilder();
        file_.append("$public $class pkg.BeanOne:code.bean.Bean{");
        file_.append(" $public String textField=\"txt\";");
        file_.append("}");
        filesSec_.put("my_file",file_.toString());
        Configuration conf_ = contextElFive(filesSec_);

        conf_.setMessagesFolder(folder_);
        conf_.setFirstUrl("page1.html");

        conf_.setProperties(new StringMap<String>());
        conf_.getProperties().put("msg_example", relative_);
        conf_.setNavigation(new StringMap<StringMap<String>>());
        conf_.getAnalyzingDoc().setFiles(files_);
        RendDocumentBlock rendDocumentBlock_ = buildRendWithOneBean(html_, conf_);
        assertTrue(!conf_.isEmptyErrors());
    }

}
