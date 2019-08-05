package code.formathtml.util;

import code.formathtml.RendBlock;
import code.util.EntryCust;
import code.util.IntTreeMap;
import code.util.StringList;
import code.util.StringMap;

public final class AnalyzingDoc {
    private StringMap<String> files = new StringMap<String>();
    private StringList languages = new StringList();

    private RendBlock currentBlock;
    private String internGlobalClass="";
    private String attribute="";
    private String fileName="";

    public static int getSum(int _offset, int _glOffset, RendBlock _currentBlock, String _attribute) {
        int delta_ = getDelta(_offset, _currentBlock, _attribute);
        return _glOffset+_offset+delta_;
    }

    public int getSum(int _offset) {
        if (currentBlock == null) {
            return 0;
        }
        int delta_ = getDelta(_offset, currentBlock, attribute);
        return _offset+delta_;
    }

    private static int getDelta(int _offset, RendBlock _currentBlock, String _attribute) {
        int delta_ = 0;
        IntTreeMap< Integer> esc_ = getEscapedChars(_currentBlock, _attribute);
        if (esc_ != null) {
            int nbIndexes_ = getIndexesCount(esc_, _offset);
            for (int i = 0; i < nbIndexes_; i++) {
                delta_ += esc_.getValue(i);
            }
        }
        return delta_;
    }

    private static int getIndexesCount(IntTreeMap< Integer> _t, int _offset) {
        int delta_ = 0;
        int count_ = 0;
        for (EntryCust<Integer, Integer> e: _t.entryList()) {
            if (e.getKey() - delta_ >= _offset) {
                return count_;
            }
            delta_ += e.getValue();
            count_++;
        }
        return count_;
    }
    private static IntTreeMap<Integer> getEscapedChars(RendBlock _currentBlock, String _attribute) {
        StringMap<IntTreeMap<Integer>> escapedChars_ = _currentBlock.getEscapedChars();
        for (EntryCust<String, IntTreeMap< Integer>> t: escapedChars_.entryList()) {
            String c_ = t.getKey();
            if (!StringList.quickEq(c_, _attribute)) {
                continue;
            }
            return t.getValue();
        }
        return null;
    }

    public void setAttribute(String _attribute) {
        attribute = _attribute;
    }

    public void setFiles(StringMap<String> _files) {
        files = _files;
    }

    public StringMap<String> getFiles() {
        return files;
    }

    public StringList getLanguages() {
        return languages;
    }

    public void setLanguages(StringList _languages) {
        languages = _languages;
    }

    public RendBlock getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(RendBlock _currentBlock) {
        currentBlock = _currentBlock;
    }

    public String getInternGlobalClass() {
        return internGlobalClass;
    }

    public void setInternGlobalClass(String _internGlobalClass) {
        internGlobalClass = _internGlobalClass;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String _fileName) {
        fileName = _fileName;
    }
}
