package code.stream;
import java.io.*;
import java.nio.charset.Charset;

import code.sml.Document;
import code.sml.DocumentBuilder;
import code.sml.Element;
import code.stream.core.StreamCoreUtil;
import code.util.CustList;
import code.util.StringList;
import code.util.core.DefaultUniformingString;
import code.util.core.IndexConstants;
import code.util.core.StringUtil;
import code.util.ints.UniformingString;

public final class StreamTextFile {

    public static final String SEPARATEUR = "/";
    private static final String UTF_8 = "UTF-8";

    private StreamTextFile() {
    }

    public static StringList allSortedFiles(String _folder,AbstractFileCoreStream _fact) {
        AbstractFile abstractFile_ = _fact.newFile(_folder);
        FileInfo f_ = new FileInfo(abstractFile_,_fact);
        StringList files_ = new StringList();
        for (FileInfo s: getSortedDescNodes(_fact,f_)) {
            files_.add(StringUtil.replaceBackSlash(s.getInfo().getAbsolutePath()));
        }
        return files_;
    }
    private static CustList<FileInfo> getSortedDescNodes(AbstractFileCoreStream _fact,FileInfo _root) {
        CustList<FileInfo> list_ = new CustList<FileInfo>();
        FileInfo c_ = _root;
        while (c_ != null) {
            list_.add(c_);
            c_ = getNext(_fact,c_, _root);
        }
        return list_;
    }

    private static FileInfo getNext(AbstractFileCoreStream _fact,FileInfo _current, FileInfo _root) {
        FileInfo n_ = _current.getFirstChild(_fact);
        if (n_ != null) {
            return n_;
        }
        FileInfo curr_ = _current;
        while (true) {
            FileInfo next_ = curr_.getNextSibling(_fact);
            if (next_ != null) {
                return next_;
            }
            FileInfo par_ = curr_.getParent();
            if (par_ == null || par_ == _root) {
                return null;
            }
            curr_ = par_;
        }
    }
    public static StringList files(String _folder,AbstractFileCoreStream _fact) {
        StringList files_ = new StringList();
        StringList current_ = new StringList(_folder);
        String folder_ = _folder;
        folder_ = _fact.newFile(folder_).getAbsolutePath();
        folder_ = StringUtil.replaceBackSlash(folder_);
        if (folder_.endsWith(StringUtil.concat(SEPARATEUR,_folder))) {
            String suffix_ = StringUtil.concat(SEPARATEUR,_folder);
            folder_ = folder_.substring(IndexConstants.FIRST_INDEX, folder_.length() - suffix_.length());
            folder_ = StringUtil.concat(folder_,SEPARATEUR);
        }
        while (true) {
            StringList new_ = new StringList();
            for (String c : current_) {
                FileListInfo filesFolder_ = _fact.newFile(c).listAbsolute(_fact);
                for (AbstractFile f : filesFolder_.getNames()) {
                    new_.add(f.getAbsolutePath());
                    files_.add(f.getAbsolutePath());
                }
            }
            if (new_.isEmpty()) {
                break;
            }
            current_ = new StringList(new_);
        }
        files_.replaceBackSlashesInStrings();
        StringUtil.removePrefixInStrings(files_,folder_);
        return files_;
    }

    public static String contentsOfFile(String _nomFichier,AbstractFileCoreStream _fact) {
        return contentsOfFile(_nomFichier,new DefaultUniformingString(),_fact);
    }

    public static String contentsOfFile(String _nomFichier, UniformingString _apply,AbstractFileCoreStream _fact) {
        return readFile(_nomFichier,_apply,_fact);
    }

    private static String readFile(String _filePath,UniformingString _apply,AbstractFileCoreStream _fact) {
        AbstractFile file_ = _fact.newFile(_filePath);
        return readingFile(tryCreateBufferedReader(StreamFileCore.tryCreateFileInputStream(_filePath)), file_.length(),_apply);
    }
    private static Reader tryCreateBufferedReader(InputStream _file) {
        if (_file == null) {
            return null;
        }
        return new InputStreamReader(_file, Charset.forName(UTF_8));
    }

    public static Element contenuDocumentXmlExterne(String _nomFichier,AbstractFileCoreStream _fact) {
        Document doc_ = DocumentBuilder.parseSax(contentsOfFile(_nomFichier,_fact));
        if (doc_ == null) {
            return null;
        }
        return doc_.getDocumentElement();
    }

    private static String readingFile(Reader _br, long _capacity,UniformingString _apply) {
        if (_br == null) {
            return null;
        }
        StringBuilder strBuilder_ = new StringBuilder((int) _capacity);
        while (true) {

            int char_ = StreamCoreUtil.read(_br);
            if (char_ == -2) {
                StreamCoreUtil.close(_br);
                return null;
            }
            if (char_ < 0) {
                break;
            }
            strBuilder_.append((char) char_);
        }
        StreamCoreUtil.close(_br);
        return _apply.apply(strBuilder_.toString());
    }

    public static boolean saveTextFile(String _nomFichier, String _text) {
        if (_nomFichier == null) {
            return false;
        }
        return write(_nomFichier, _text, false);
    }
    public static boolean logToFile(String _nomFichier, String _text) {
        if (_nomFichier == null) {
            return false;
        }
        return write(_nomFichier, _text, true);
    }

    private static boolean write(String _nomFichier, String _text, boolean _append) {
        return write(tryCreateWriter(_nomFichier, _append), _text);
    }

    private static boolean write(OutputStream _bw,String _text) {
        if (_bw == null) {
            return false;
        }
        boolean w_ = write(new OutputStreamWriter(_bw,Charset.forName(UTF_8)), _text);
        return w_&&StreamCoreUtil.close(_bw);
    }
    private static boolean write(Writer _bw,String _text) {
        try {
            _bw.write(_text);
            return StreamCoreUtil.close(_bw);
        } catch (IOException e) {
            return false;
        }
    }
    private static OutputStream tryCreateWriter(String _nomFichier, boolean _append) {
        try {
            return new FileOutputStream(_nomFichier,_append);
        } catch (IOException e) {
            return null;
        }
    }

}
