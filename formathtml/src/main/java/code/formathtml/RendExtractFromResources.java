package code.formathtml;
import code.expressionlanguage.structs.NullStruct;
import code.sml.util.ResourcesMessagesUtil;
import code.util.CustList;
import code.util.EntryCust;
import code.util.StringList;
import code.util.StringMap;

public final class RendExtractFromResources {
    static final String RETURN_LINE = "\n";
    private static final String SEPARATOR_PATH = "/";
    private static final String IMPLICIT_LANGUAGE = "//";
    private static final String LINE_RETURN = "\n";
    private static final String EMPTY_STRING = "";
    private static final String TAB = "\t";
    private static final String EQUALS = "=";
    private static final String BEFORE_LINE_RETURN = "\r\n";
    private RendExtractFromResources() {
    }

    static String getRealFilePath(String _lg, String _link) {
        return StringList.replace(_link, IMPLICIT_LANGUAGE, StringList.concat(SEPARATOR_PATH,_lg,SEPARATOR_PATH));
    }

    static String getFormat(StringMap<String> _messages, String _key, Configuration _conf, String _loc, String _fileName) {
        String fileNamePath_ = ResourcesMessagesUtil.getPropertiesPath(_conf.getMessagesFolder(),_loc,_fileName);
        String value_ = _messages.getVal(_key);
        if (value_ == null) {
            _conf.getContext().setException(NullStruct.NULL_VALUE);
            //check if key_ is in messages_ from fileNamePath_
        } else {
            _conf.setResourceUrl(fileNamePath_);
        }
        return value_;
    }

    static String getQuickFormat(StringMap<String> _messages, String _key) {
        String value_ = _messages.getVal(_key);
        return value_;
    }
    static StringMap<String> getInnerMessagesFromLocaleClass(Configuration _conf, String _loc, String _relative, StringMap<String> _files, String... _resourcesFolder) {
        String content_ = tryGetContent(_conf,_loc,_relative,_files,_resourcesFolder);
        if (_conf.getContext().getException() != null) {
            return new StringMap<String>();
        }
        int index_ = indexCorrectMessages(content_);
        if (index_ >= 0) {
            _conf.getContext().setException(NullStruct.NULL_VALUE);
            return new StringMap<String>();
        }
        return getMessages(content_);
    }
    static String tryGetContent(Configuration _conf, String _loc, String _relative, StringMap<String> _files, String... _resourcesFolder) {
        String folder_ = _conf.getMessagesFolder();
        String fileName_ = ResourcesMessagesUtil.getPropertiesPath(folder_,_loc,_relative);
        return getContentFile(_conf, _files, fileName_, _resourcesFolder);
    }

    static int indexCorrectMessages(String _content) {
        if (_content == null) {
            return 0;
        }
        String lastKey_ = EMPTY_STRING;
        StringMap<String> messages_ = new StringMap<String>();
        int line_ = CustList.FIRST_INDEX;
        for (String l: StringList.splitStrings(_content, BEFORE_LINE_RETURN, LINE_RETURN)) {
            line_++;
            if (l.isEmpty()) {
                continue;
            }
            if (l.startsWith(TAB)) {
                String text_ = messages_.getVal(lastKey_);
                if (text_ != null) {
                    text_ = StringList.concat(text_, l.substring(1));
                    messages_.put(lastKey_, text_);
                }
            } else {
                int indexSep_ = l.indexOf(EQUALS);
                if (indexSep_ < 0) {
                    return line_;
                }
                lastKey_ = l.substring(0,indexSep_);
                messages_.put(lastKey_, l.substring(indexSep_+1));
            }
        }
        return -1;
    }

    static StringMap<String> getMessages(String _content) {
        String lastKey_ = EMPTY_STRING;
        StringMap<String> messages_ = new StringMap<String>();
        for (String l: StringList.splitStrings(_content, BEFORE_LINE_RETURN, LINE_RETURN)) {
            if (l.isEmpty()) {
                continue;
            }
            if (l.startsWith(TAB)) {
                String text_ = messages_.getVal(lastKey_);
                if (text_ != null) {
                    text_ = StringList.concat(text_,l.substring(1));
                    messages_.put(lastKey_, text_);
                }
            } else {
                int indexSep_ = l.indexOf(EQUALS);
                lastKey_ = l.substring(0,indexSep_);
                messages_.put(lastKey_, l.substring(indexSep_+1));
            }
        }
        return messages_;
    }

    static String getContentFile(Configuration _conf, StringMap<String> _files, String _fileName, String... _resourcesFolder) {
        String content_ = null;
        for (EntryCust<String, String> e: _files.entryList()) {
            if (StringList.quickEq(e.getKey(),_fileName)) {
                content_ = e.getValue();
                break;
            }
        }
        return content_;
    }

}
