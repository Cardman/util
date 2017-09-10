package code.formathtml;
import static code.formathtml.EquallableExUtil.assertEq;

import javax.imageio.ImageIO;

import org.junit.BeforeClass;
import org.junit.Test;

import code.formathtml.Configuration;
import code.formathtml.ExtractFromResources;
import code.util.StringMap;
import code.util.consts.Constants;

@SuppressWarnings("static-method")
public class ExtractFromResourcesTest {
    private static final String SEPARATOR_PATH = "/";
    private static final String IMPLICIT_LANGUAGE = SEPARATOR_PATH+SEPARATOR_PATH;

    @BeforeClass
    public static void initialize() {
        ImageIO.setUseCache(false);
        Constants.setLanguage("LOCALE");
    }

    @Test
    public void getInnerMessagesFromLocaleClass1Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "one=Description one\ntwo_lignes=Description\n\t2";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        Configuration conf_ = new Configuration();
        conf_.setMessagesFolder(folder_);
        StringMap<String> messages_ = ExtractFromResources.getInnerMessagesFromLocaleClass(conf_, locale_, relative_, files_);
        assertEq(2, messages_.size());
        assertEq("Description one", messages_.getVal("one"));
        assertEq("Description2", messages_.getVal("two_lignes"));
    }

    @Test
    public void getInnerMessagesFromLocaleClass2Test() {
        String locale_ = "LOCALE";
        String folder_ = "messages";
        String relative_ = "sample/file";
        String content_ = "\tone=Description one\ntwo_lignes=Description 2";
        StringMap<String> files_ = new StringMap<String>();
        files_.put(folder_+"/"+locale_+"/"+relative_+".properties", content_);
        Configuration conf_ = new Configuration();
        conf_.setMessagesFolder(folder_);
        StringMap<String> messages_ = ExtractFromResources.getInnerMessagesFromLocaleClass(conf_, locale_, relative_, files_);
        assertEq(1, messages_.size());
        assertEq("Description 2", messages_.getVal("two_lignes"));
    }

    @Test
    public void loadPage1Test() {
        String html_ = "<html><head><link rel=\"stylesheet\" href=\"css"+IMPLICIT_LANGUAGE+"main.css\"/></head><body><img src=\"imgs"+IMPLICIT_LANGUAGE+"sample.png\"/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put("html/pages/index.html", html_);
        String res_ = ExtractFromResources.loadPage(null, files_, "html/pages/index.html");
//        assertEq("<html><head><link href=\"css/LOCALE/main.css\" rel=\"stylesheet\"/></head><body><img src=\"imgs/LOCALE/sample.png\"/></body></html>",res_);
        assertEq("<html><head><link rel=\"stylesheet\" href=\"css"+IMPLICIT_LANGUAGE+"main.css\"/></head><body><img src=\"imgs"+IMPLICIT_LANGUAGE+"sample.png\"/></body></html>",res_);
    }

    @Test
    public void loadPage2Test() {
        String html_ = "<html><head><link rel=\"stylesheet\"/></head><body><img/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put("html/pages/index.html", html_);
        String res_ = ExtractFromResources.loadPage(null, files_, "html/pages/index.html");
        assertEq("<html><head><link rel=\"stylesheet\"/></head><body><img/></body></html>",res_);
    }

    @Test
    public void loadPage3Test() {
        String html_ = "<html><head><link href=\"css/LOCALE/main.css\"/><link/><link rel=\"other\"/><link rel=\"stylesheet\" href=\"file:main.css\"/></head><body><img src=\"file:img.png\"/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put("html/pages/index.html", html_);
        String res_ = ExtractFromResources.loadPage(null, files_, "html/pages/index.html");
//        assertEq("<html><head><link href=\"css/LOCALE/main.css\"/><link/><link rel=\"other\"/><link href=\"file:main.css\" rel=\"stylesheet\"/></head><body><img src=\"file:img.png\"/></body></html>",res_);
        assertEq("<html><head><link href=\"css/LOCALE/main.css\"/><link/><link rel=\"other\"/><link rel=\"stylesheet\" href=\"file:main.css\"/></head><body><img src=\"file:img.png\"/></body></html>",res_);
    }

    @Test
    public void loadPage4Test() {
        String html_ = "<html><body><a href=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/><a href=\"\" command=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/><form action=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/><form action=\"\" command=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put("html/pages/index.html", html_);
        String res_ = ExtractFromResources.loadPage(null, files_, "html/pages/index.html");
//        assertEq("<html><body><a href=\"html/pages/LOCALE/index.html\"/><a command=\"html/pages/LOCALE/index.html\" href=\"\"/><form action=\"html/pages/LOCALE/index.html\"/><form action=\"\" command=\"html/pages/LOCALE/index.html\"/></body></html>",res_);
        assertEq("<html><body><a href=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/><a href=\"\" command=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/><form action=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/><form action=\"\" command=\"html/pages"+IMPLICIT_LANGUAGE+"index.html\"/></body></html>",res_);
    }

    @Test
    public void loadPage5Test() {
        String html_ = "<html><body><a href=\"\"/><form action=\"\"/><a href=\"file:html/pages/LOCALE/index.html\"/><a command=\"html/pages/LOCALE/index.html\"/><form action=\"file:html/pages/LOCALE/index.html\"/><form command=\"html/pages/LOCALE/index.html\"/></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put("html/pages/index.html", html_);
        String res_ = ExtractFromResources.loadPage(null, files_, "html/pages/index.html");
        assertEq("<html><body><a href=\"\"/><form action=\"\"/><a href=\"file:html/pages/LOCALE/index.html\"/><a command=\"html/pages/LOCALE/index.html\"/><form action=\"file:html/pages/LOCALE/index.html\"/><form command=\"html/pages/LOCALE/index.html\"/></body></html>",res_);
    }

    @Test
    public void loadPage6Test() {
        String html_ = "<html><body><a href=\"\"/><form action=\"\"/><a href=\"file:html/pages/LOCALE/index.html\"/><a command=\"html/pages/LOCALE/index.html\"/><form action=\"file:html/pages/LOCALE/index.html\"/><form command=\"html/pages/LOCALE/index.html\"/><br></body></html>";
        StringMap<String> files_ = new StringMap<String>();
        files_.put("html/pages/index.html", html_);
        String res_ = ExtractFromResources.loadPage(null, files_, "html/pages/index.html");
        assertEq("<html><body><a href=\"\"/><form action=\"\"/><a href=\"file:html/pages/LOCALE/index.html\"/><a command=\"html/pages/LOCALE/index.html\"/><form action=\"file:html/pages/LOCALE/index.html\"/><form command=\"html/pages/LOCALE/index.html\"/><br></body></html>",res_);
    }

}
