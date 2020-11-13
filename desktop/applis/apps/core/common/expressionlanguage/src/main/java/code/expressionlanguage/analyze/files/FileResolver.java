package code.expressionlanguage.analyze.files;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.InterfacesPart;
import code.expressionlanguage.analyze.blocks.*;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.errors.custom.GraphicErrorInterpret;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.options.KeyWords;
import code.util.*;
import code.util.core.IndexConstants;
import code.util.core.StringUtil;

public final class FileResolver {

    private static final char FOR_BLOCKS = ':';
    private static final char END_LINE = ';';
    private static final char END_IMPORTS = END_LINE;
    private static final char PKG = '.';
    private static final String EMPTY_STRING = "";
    private static final char SEP_ENUM_CONST = ',';
    private static final char BEGIN_TEMPLATE = '<';
    private static final char BEGIN_BLOCK = '{';
    private static final char END_BLOCK = '}';
    private static final char BEGIN_ARRAY = '[';
    private static final char END_ARRAY = ']';
    private static final char BEGIN_CALLING = '(';
    private static final char END_CALLING = ')';
    private static final char PART_SEPARATOR = '=';
    private static final char DEL_CHAR = '\'';
    private static final char DEL_STRING = '"';
    private static final char DEL_TEXT = '`';
    private static final char ESCAPE = '\\';
    private static final char ANNOT = '@';

    private FileResolver(){
    }

    public static void parseFile(FileBlock _block, String _fileName, String _file, AnalyzedPageEl _page) {
        StringList importedTypes_ = new StringList();
        StringBuilder str_ = new StringBuilder();
        KeyWords keyWords_ = _page.getKeyWords();
        String keyWordOperator_ = keyWords_.getKeyWordOperator();
        String keyWordPackage_ = keyWords_.getKeyWordPackage();
        String keyWordPrivate_ = keyWords_.getKeyWordPrivate();
        String keyWordProtected_ = keyWords_.getKeyWordProtected();
        String keyWordPublic_ = keyWords_.getKeyWordPublic();
        String keyWordAbstract_ = keyWords_.getKeyWordAbstract();
        String keyWordAnnotation_ = keyWords_.getKeyWordAnnotation();
        String keyWordClass_ = keyWords_.getKeyWordClass();
        String keyWordEnum_ = keyWords_.getKeyWordEnum();
        String keyWordFinal_ = keyWords_.getKeyWordFinal();
        String keyWordInterface_ = keyWords_.getKeyWordInterface();
        int i_ = IndexConstants.FIRST_INDEX;
        int len_ = _file.length();
        CommentDelimiters current_ = null;
        int indexImport_ = 0;
        Ints badIndexes_ = new Ints();
        Ints offsetsImports_ = new Ints();
        Ints beginComments_ = _block.getBeginComments();
        Ints endComments_ = _block.getEndComments();
        CustList<CommentDelimiters> comments_ = _page.getComments();
        while (i_ < len_) {
            char currentChar_ = _file.charAt(i_);
            if (current_ != null) {
                String endCom_ = getEndCom(_file, i_, current_);
                int length_ = endCom_.length();
                if (length_ > 0) {
                    i_ += length_;
                    appendEnd(i_, endCom_, endComments_);
                    appendEndComment(str_, endCom_);
                    current_ = null;
                    continue;
                }
                i_++;
                continue;
            }
            if (str_.toString().trim().isEmpty()) {
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordPublic_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordOperator_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordProtected_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordPackage_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordPrivate_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordAbstract_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordAnnotation_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordClass_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordEnum_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordFinal_)) {
                    break;
                }
                if (StringExpUtil.startsWithKeyWord(_file,i_,keyWordInterface_)) {
                    break;
                }
                if (currentChar_ == ANNOT) {
                    break;
                }
            }
            boolean skip_= false;
            for (CommentDelimiters c: comments_) {
                if (_file.startsWith(c.getBegin(),i_)) {
                    current_ = c;
                    beginComments_.add(i_);
                    int beginLen_ = c.getBegin().length();
                    for (int e = 0; e < beginLen_; e++) {
                        str_.append(' ');
                    }
                    i_ += beginLen_;
                    skip_ = true;
                    break;
                }
            }
            if (skip_) {
                continue;
            }
            if (currentChar_ == END_IMPORTS) {
                importedTypes_.add(str_.toString());
                offsetsImports_.add(indexImport_);
                str_.delete(0, str_.length());
            } else {
                if (!StringUtil.isWhitespace(currentChar_)) {
                    indexImport_ = setInstLocation(str_, indexImport_, i_);
                    str_.append(currentChar_);
                } else {
                    str_.append(currentChar_);
                }
            }
            i_ = i_ + 1;
        }
        if (i_ >= len_) {
            badIndexes_.add(i_);
        }
        InputTypeCreation input_ = new InputTypeCreation();
        input_.setNextIndex(i_);
        input_.setType(OuterBlockEnum.OUTER_TYPE);
        _block.getImports().addAllElts(importedTypes_);
        _block.getImportsOffset().addAllElts(offsetsImports_);
        input_.setFile(_block);
        if (!badIndexes_.isEmpty()) {
            for (int i: badIndexes_) {
                FoundErrorInterpret b_ = new FoundErrorInterpret();
                b_.setFileName(_fileName);
                b_.setIndexFile(Math.max(0,Math.min(_block.getLength()-1,i)));
                //if empty file => add underlined space
                //else underline last char
                b_.buildError(_page.getAnalysisMessages().getBadIndexInParser());
                _page.addLocError(b_);
                GraphicErrorInterpret g_ = new GraphicErrorInterpret(b_);
                g_.setLength(1);
                _block.getErrorsFiles().getLi().add(g_);
            }
            return;
        }
        //the file is not trimmed empty
        while (true) {
            ResultCreation res_ = createType(_file, input_, _page);
            badIndexes_ = input_.getBadIndexes();
            for (int i: badIndexes_) {
                FoundErrorInterpret b_ = new FoundErrorInterpret();
                b_.setFileName(_fileName);
                b_.setIndexFile(Math.max(0,Math.min(_block.getLength()-1,i)));
                //underline index char
                b_.buildError(_page.getAnalysisMessages().getBadIndexInParser());
                _page.addLocError(b_);
                GraphicErrorInterpret g_ = new GraphicErrorInterpret(b_);
                g_.setLength(1);
                _block.getErrorsFiles().getLi().add(g_);
            }
            Block block_ = res_.getBlock();
            if (block_ != null) {
                _block.appendChild(block_);
            }
            i_ = res_.getNextIndex();
            boolean hasNext_ = false;
            boolean ended_ = true;
            current_ = null;
            while (i_ < len_) {
                char currentChar_ = _file.charAt(i_);
                if (current_ != null) {
                    String endCom_ = getEndCom(_file, i_, current_);
                    int length_ = endCom_.length();
                    if (length_ > 0) {
                        i_ += length_;
                        appendEnd(i_, endCom_, endComments_);
                        current_ = null;
                        continue;
                    }
                    i_++;
                    continue;
                }
                if (StringExpUtil.isTypeLeafChar(currentChar_)) {
                    hasNext_ = true;
                    ended_ = false;
                    break;
                }
                if (currentChar_ == ANNOT) {
                    hasNext_ = true;
                    ended_ = false;
                    break;
                }
                boolean skip_= false;
                for (CommentDelimiters c: comments_) {
                    if (_file.startsWith(c.getBegin(),i_)) {
                        current_ = c;
                        beginComments_.add(i_);
                        i_ += c.getBegin().length();
                        skip_ = true;
                        break;
                    }
                }
                if (skip_) {
                    continue;
                }

                i_ = i_ + 1;
            }
            if (ended_ && current_ != null) {
                endComments_.add(len_ - 1);
            }
            if (!hasNext_) {
                return;
            }
            input_.setNextIndex(i_);
        }
    }

    static void appendEndComment(StringBuilder _str, String _endCom) {
        for (char c: _endCom.toCharArray()) {
            if (c < ' ') {
                _str.append(c);
            } else {
                _str.append(' ');
            }
        }
    }

    private static ResultCreation createType(String _file, InputTypeCreation _input, AnalyzedPageEl _page) {
        return processOuterTypeBody(_input, EMPTY_STRING,0, _file, _page);
    }
    public static ResultCreation processOuterTypeBody(InputTypeCreation _input, String _pkgName, int _offset,
                                                      String _file, AnalyzedPageEl _page) {
        ResultCreation out_ = new ResultCreation();
        int len_ = _file.length();
        KeyWords keyWords_ = _page.getKeyWords();
        String packageName_ = _pkgName;
        String keyWordCase_ = keyWords_.getKeyWordCase();
        String keyWordDefault_ = keyWords_.getKeyWordDefault();
        StringBuilder instruction_ = new StringBuilder();
        int instructionLocation_ = _input.getNextIndex();
        FileBlock fileBlock_ = _input.getFile();
        Ints braces_ = new Ints();
        Ints parentheses_ = new Ints();
        boolean constChar_ = false;
        boolean constString_ = false;
        boolean constText_ = false;
        boolean declType_ = false;
        BracedBlock currentParent_ = null;

        int i_ = _input.getNextIndex();
        boolean okType_ = false;
        boolean enableByEndLine_ = false;
        AfterBuiltInstruction after_ = new AfterBuiltInstruction();
        after_.setIndex(i_);
        after_.setParent(null);
        CommentDelimiters current_ = null;
        CustList<CommentDelimiters> comments_ = _page.getComments();
        Ints beginComments_ = fileBlock_.getBeginComments();
        Ints endComments_ = fileBlock_.getEndComments();
        while (i_ < len_) {
            char currentChar_ = _file.charAt(i_);
            if (current_ != null) {
                String endCom_ = getEndCom(_file, i_, current_);
                int length_ = endCom_.length();
                if (length_ > 0) {
                    i_ += length_;
                    appendEnd(i_, endCom_, endComments_);
                    appendEndComment(instruction_, endCom_);
                    current_ = null;
                    continue;
                }
                instruction_.append(' ');
                i_++;
                continue;
            }
            if (constChar_) {
                instruction_.append(currentChar_);
                if (currentChar_ == ESCAPE) {
                    if (i_ + 1 >= len_) {
                        //ERROR
                        i_++;
                        continue;
                    }
                    instruction_.append(_file.charAt(i_+1));

                    i_ = i_ + 1;

                    i_ = i_ + 1;
                    continue;
                }
                if (currentChar_ == DEL_CHAR) {

                    i_ = i_ + 1;
                    constChar_ = false;
                    continue;
                }

                i_ = i_ + 1;
                continue;
            }
            if (constString_) {
                instruction_.append(currentChar_);
                if (currentChar_ == ESCAPE) {
                    if (i_ + 1 >= len_) {
                        //ERROR
                        i_++;
                        continue;
                    }
                    instruction_.append(_file.charAt(i_+1));

                    i_ = i_ + 1;

                    i_ = i_ + 1;
                    continue;
                }
                if (currentChar_ == DEL_STRING) {

                    i_ = i_ + 1;
                    constString_ = false;
                    continue;
                }

                i_ = i_ + 1;
                continue;
            }
            if (constText_) {
                instruction_.append(currentChar_);
                if (i_ + 1 >= len_) {
                    //ERROR
                    i_++;
                    continue;
                }
                if(currentChar_ == DEL_TEXT) {
                    if (_file.charAt(i_ + 1) != DEL_TEXT) {

                        i_ = i_ + 1;
                        constText_ = false;
                        continue;
                    }
                    instruction_.append(_file.charAt(i_+1));

                    i_ = i_ + 1;

                    i_ = i_ + 1;
                    continue;
                }

                i_ = i_ + 1;
                continue;
            }
            boolean skip_= false;
            for (CommentDelimiters c: comments_) {
                if (_file.startsWith(c.getBegin(),i_)) {
                    current_ = c;
                    beginComments_.add(i_);
                    int beginLen_ = c.getBegin().length();
                    i_ += beginLen_;
                    for (int e = 0; e < beginLen_; e++) {
                        instruction_.append(' ');
                    }
                    skip_ = true;
                    break;
                }
            }
            if (skip_) {
                continue;
            }
            if (currentChar_ == DEL_CHAR) {
                instructionLocation_ = setInstLocation(instruction_, instructionLocation_, i_);
                instruction_.append(currentChar_);
                constChar_ = true;

                i_ = i_ + 1;
                continue;
            }
            if (currentChar_ == DEL_STRING) {
                instructionLocation_ = setInstLocation(instruction_, instructionLocation_, i_);
                instruction_.append(currentChar_);
                constString_ = true;

                i_ = i_ + 1;
                continue;
            }
            if (currentChar_ == DEL_TEXT) {
                instructionLocation_ = setInstLocation(instruction_, instructionLocation_, i_);
                instruction_.append(currentChar_);
                constText_ = true;

                i_ = i_ + 1;
                continue;
            }
            boolean endInstruction_ = false;
            if (parentheses_.isEmpty()) {
                if (currentChar_ == END_LINE) {
                    endInstruction_ = true;
                }
                if (currentChar_ == ':') {
                    String str_ = instruction_.toString().trim();
                    if (StringExpUtil.startsWithKeyWord(str_, keyWordCase_)
                            || StringUtil.quickEq(str_, keyWordDefault_)
                            || startsWithDefVar(str_,keyWordDefault_)) {
                        endInstruction_ = true;
                    }
                    if (endInstruction_ && currentParent_ instanceof SwitchPartBlock) {
                        currentParent_ = currentParent_.getParent();
                    }
                }
                if (currentChar_ == SEP_ENUM_CONST && enableByEndLine_) {
                    endInstruction_ = true;
                }
                if (currentChar_ == END_BLOCK) {
                    endInstruction_ = true;
                }
                if (currentChar_ == BEGIN_BLOCK) {
                    EndInstruction end_ = endInstruction(currentParent_, instruction_, enableByEndLine_, _page);
                    if (end_ != EndInstruction.NONE) {
                        endInstruction_ = true;
                        if (end_ == EndInstruction.DECLARE_TYPE) {
                            declType_ = true;
                        }
                    }
                }
                if (enableByEndLine_) {
                    if (currentChar_ == BEGIN_TEMPLATE) {
                        //increment to last greater
                        instructionLocation_ = setInstLocation(instruction_, instructionLocation_, i_);
                        ParsedTemplatedType par_ = new ParsedTemplatedType(instruction_,i_);
                        par_.parse(_file,comments_,beginComments_,endComments_);
                        i_ = par_.getCurrent();
                        continue;
                    }
                }
                //End line
            }
            if (!endInstruction_) {
                instructionLocation_ = setInstLocation(instruction_, instructionLocation_, i_);
                instruction_.append(currentChar_);
            }
            if (currentChar_ == BEGIN_CALLING) {
                parentheses_.add(i_);
            }
            if (currentChar_ == BEGIN_ARRAY) {
                parentheses_.add(i_);
            }
            if (currentChar_ == END_CALLING) {
                if (parentheses_.isEmpty()) {
                    addBadIndex(_input, currentParent_, i_);
                    break;
                }
                parentheses_.removeQuicklyLast();
            }
            if (currentChar_ == END_ARRAY) {
                if (parentheses_.isEmpty()) {
                    addBadIndex(_input, currentParent_, i_);
                    break;
                }
                parentheses_.removeQuicklyLast();
            }
            if (currentChar_ == BEGIN_BLOCK) {
                if (endInstruction_) {
                    braces_.add(i_);
                } else {
                    parentheses_.add(i_);
                }
            }
            if (currentChar_ == END_BLOCK) {
                if (endInstruction_) {
                    braces_.removeLast();
                } else {
                    parentheses_.removeLast();
                }
            }
            if (endInstruction_) {
                after_ = processInstruction(out_,_input, packageName_, currentChar_, currentParent_,
                        instructionLocation_,
                        instruction_, declType_, i_,_offset, enableByEndLine_, _page);
                enableByEndLine_ = after_.isEnabledEnumHeader();
                currentParent_ = after_.getParent();
                i_ = after_.getIndex();
                packageName_ = after_.getPackageName();
                instructionLocation_ = i_;
                declType_ = false;
                if (braces_.isEmpty()) {
                    okType_ = true;
                    break;
                }
            }

            i_ = i_ + 1;
        }
        if (okType_) {

            i_ = i_ + 1;
        } else {
            addBadIndex(_input, currentParent_, len_);
        }
        out_.setNextIndex(i_);
        return out_;
    }

    private static int setInstLocation(StringBuilder _instruction, int _instructionLocation, int _i) {
        if (_instruction.length() == 0) {
            return _i;
        }
        return _instructionLocation;
    }

    private static void addBadIndex(InputTypeCreation _input, BracedBlock _currentParent, int _index) {
        if (_currentParent != null) {
            _currentParent.getBadIndexes().add(_index);
        } else {
            _input.getBadIndexes().add(_index);
        }
    }
    static void appendEnd(int _i, String _e, Ints _endComments) {
        if (_e.trim().isEmpty()) {
            _endComments.add(_i -2);
        } else {
            _endComments.add(_i -1);
        }
    }

    private static EndInstruction endInstruction(BracedBlock _parent, StringBuilder _instruction,
                                                 boolean _enableByEndLine, AnalyzedPageEl _page) {
        String tr_ = _instruction.toString().trim();
        KeyWords keyWords_ = _page.getKeyWords();
        if (_parent == null) {
            if (tr_.isEmpty()) {
                return EndInstruction.DECLARE_TYPE;
            }
            if (tr_.charAt(0) == ANNOT) {
                ParsedAnnotations par_ = new ParsedAnnotations(tr_, 0);
                par_.parse();
                tr_ = par_.getAfter();
            }
            String word_ = EMPTY_STRING;
            if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPrivate())) {
                word_ = keyWords_.getKeyWordPrivate();
            } else if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPackage())) {
                word_ = keyWords_.getKeyWordPackage();
            } else if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordProtected())) {
                word_ = keyWords_.getKeyWordProtected();
            } else {
                if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPublic())) {
                    word_ = keyWords_.getKeyWordPublic();
                }
            }
            String afterAccess_ = tr_.substring(word_.length()).trim();
            String keyWordAbstract_ = keyWords_.getKeyWordAbstract();
            String keyWordAnnotation_ = keyWords_.getKeyWordAnnotation();
            String keyWordClass_ = keyWords_.getKeyWordClass();
            String keyWordEnum_ = keyWords_.getKeyWordEnum();
            String keyWordFinal_ = keyWords_.getKeyWordFinal();
            String keyWordInterface_ = keyWords_.getKeyWordInterface();
            String beforeQu_ = afterAccess_;
            if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordAbstract_)) {
                beforeQu_ = beforeQu_.substring(keyWordAbstract_.length()).trim();
            }
            if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordFinal_)) {
                beforeQu_ = beforeQu_.substring(keyWordFinal_.length()).trim();
            }
            boolean dType_ = false;
            if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordClass_)) {
                dType_ = true;
            } else if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordEnum_)) {
                dType_ = true;
            } else if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordInterface_)) {
                dType_ = true;
            } else {
                if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordAnnotation_)) {
                    dType_ = true;
                }
            }
            if (dType_) {
                return EndInstruction.DECLARE_TYPE;
            }
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (tr_.isEmpty()) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordIntern())) {
            return EndInstruction.NONE;
        }
        if (_enableByEndLine) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        String trTmp_ = tr_;
        if (tr_.charAt(0) == ANNOT) {
            ParsedAnnotations par_ = new ParsedAnnotations(tr_, 0);
            par_.parse();
            tr_ = par_.getAfter();
        }
        String word_ = EMPTY_STRING;
        if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPrivate())) {
            word_ = keyWords_.getKeyWordPrivate();
        } else if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPackage())) {
            word_ = keyWords_.getKeyWordPackage();
        } else if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordProtected())) {
            word_ = keyWords_.getKeyWordProtected();
        } else {
            if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPublic())) {
                word_ = keyWords_.getKeyWordPublic();
            }
        }
        String afterAccess_ = tr_.substring(word_.length()).trim();
        String keyWordAbstract_ = keyWords_.getKeyWordAbstract();
        String keyWordAnnotation_ = keyWords_.getKeyWordAnnotation();
        String keyWordClass_ = keyWords_.getKeyWordClass();
        String keyWordEnum_ = keyWords_.getKeyWordEnum();
        String keyWordFinal_ = keyWords_.getKeyWordFinal();
        String keyWordInterface_ = keyWords_.getKeyWordInterface();
        String keyWordStatic_ = keyWords_.getKeyWordStatic();
        String beforeQu_ = afterAccess_;
        if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordAbstract_)) {
            beforeQu_ = beforeQu_.substring(keyWordAbstract_.length()).trim();
        }
        if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordStatic_)) {
            beforeQu_ = beforeQu_.substring(keyWordStatic_.length()).trim();
        }
        if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordFinal_)) {
            beforeQu_ = beforeQu_.substring(keyWordFinal_.length()).trim();
        }
        boolean dType_ = false;
        if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordClass_)) {
            beforeQu_ = beforeQu_.substring(keyWordClass_.length()).trim();
            dType_ = true;
        } else if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordEnum_)) {
            beforeQu_ = beforeQu_.substring(keyWordEnum_.length()).trim();
            dType_ = true;
        } else if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordInterface_)) {
            beforeQu_ = beforeQu_.substring(keyWordInterface_.length()).trim();
            dType_ = true;
        } else {
            if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordAnnotation_)) {
                beforeQu_ = beforeQu_.substring(keyWordAnnotation_.length()).trim();
                dType_ = true;
            }
        }
        if (dType_) {
            if (!StringExpUtil.nextCharIs(beforeQu_,0,beforeQu_.length(),'(')) {
                return EndInstruction.DECLARE_TYPE;
            }
            return EndInstruction.NONE;
        }
        if (_parent instanceof AnnotationBlock){
            return EndInstruction.NONE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordIf())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordElse())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordElseif())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordSwitch())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordTry())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordCatch())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordFinally())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordFor())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordForeach())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordIter())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordDo())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordWhile())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringUtil.quickEq(trTmp_, keyWordStatic_)) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordReturn())) {
            return EndInstruction.NONE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordThrow())) {
            return EndInstruction.NONE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordBreak())) {
            return EndInstruction.NONE;
        }
        if (StringExpUtil.startsWithKeyWord(trTmp_,keyWords_.getKeyWordContinue())) {
            return EndInstruction.NONE;
        }
        char lastCh_ = trTmp_.charAt(trTmp_.length() - 1);
        if (lastCh_ =='=') {
            return EndInstruction.NONE;
        }
        if (lastCh_ ==']') {
            return EndInstruction.NONE;
        }
        if (lastCh_ ==':') {
            return EndInstruction.NONE;
        }
        if (lastCh_ =='?') {
            return EndInstruction.NONE;
        }
        if (!(_parent instanceof RootBlock)) {
            return EndInstruction.NONE;
        }
        if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordOperator())) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        word_ = EMPTY_STRING;
        if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPrivate())) {
            word_ = keyWords_.getKeyWordPrivate();
        } else {
            if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPackage())) {
                word_ = keyWords_.getKeyWordPackage();
            } else {
                if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordProtected())) {
                    word_ = keyWords_.getKeyWordProtected();
                } else {
                    if (StringExpUtil.startsWithKeyWord(tr_,keyWords_.getKeyWordPublic())) {
                        word_ = keyWords_.getKeyWordPublic();
                    }
                }
            }
        }
        afterAccess_ = tr_.substring(word_.length());
        String trimmedAfterAccess_ = afterAccess_.trim();
        boolean ctor_ = false;
        if (trimmedAfterAccess_.startsWith("(")) {
            ctor_ = true;
        } else {
            int leftPar_ = trimmedAfterAccess_.indexOf('(');
            if (leftPar_ > -1&&StringExpUtil.isTypeLeafPart(trimmedAfterAccess_.substring(0,leftPar_).trim())){
                ctor_ = true;
            }
        }
        if (ctor_) {
            return EndInstruction.NO_DECLARE_TYPE;
        }
        String keyWordNormal_ = keyWords_.getKeyWordNormal();
        String keyWordStaticCall_ = keyWords_.getKeyWordStaticCall();
        StringList wordsSep_ = StringExpUtil.getDollarWordSeparators(trimmedAfterAccess_);
        int i_ = 0;
        int len_ = wordsSep_.size();
        while (i_ < len_) {
            String ws_ = wordsSep_.get(i_);
            if (!StringExpUtil.isDollarWord(ws_)) {
                i_++;
                continue;
            }
            if (StringUtil.quickEq(ws_,keyWordNormal_)) {
                i_++;
                continue;
            }
            if (StringUtil.quickEq(ws_,keyWordAbstract_)) {
                i_++;
                continue;
            }
            if (StringUtil.quickEq(ws_,keyWordStatic_)) {
                i_++;
                continue;
            }
            if (StringUtil.quickEq(ws_,keyWordStaticCall_)) {
                i_++;
                continue;
            }
            if (StringUtil.quickEq(ws_,keyWordFinal_)) {
                i_++;
                continue;
            }
            break;
        }
        if (i_ >= len_) {
            return EndInstruction.NONE;
        }
        String trAfterType_ = afterDeclaringType(wordsSep_, i_);
        int lenTrAfterType_ = trAfterType_.length();
        int indexTrAfterType_ = 0;
        while (indexTrAfterType_ < lenTrAfterType_) {
            char cur_ = trAfterType_.charAt(indexTrAfterType_);
            if (!StringExpUtil.isTypeLeafChar(cur_)) {
                break;
            }
            indexTrAfterType_++;
        }
        while (indexTrAfterType_ < lenTrAfterType_) {
            char cur_ = trAfterType_.charAt(indexTrAfterType_);
            if (!StringUtil.isWhitespace(cur_)) {
                break;
            }
            indexTrAfterType_++;
        }
        if (!StringExpUtil.nextCharIs(trAfterType_, indexTrAfterType_, lenTrAfterType_, BEGIN_CALLING)) {
            return EndInstruction.NONE;
        }
        return EndInstruction.NO_DECLARE_TYPE;
    }

    private static String afterDeclaringType(StringList _wordsSep, int _index) {
        String join_ = StringUtil.join(_wordsSep.mid(_index), "");
        String typeStr_ = getFoundType(join_);
        return join_.substring(typeStr_.length()).trim();
    }

    private static AfterBuiltInstruction processInstruction(ResultCreation _out, InputTypeCreation _input, String _pkgName, char _currentChar,
                                                            BracedBlock _currentParent,
                                                            int _instructionLocation, StringBuilder _instruction, boolean _declType, int _i, int _offset, boolean _enabledEnum, AnalyzedPageEl _page) {
        AfterBuiltInstruction after_ = new AfterBuiltInstruction();
        BracedBlock currentParent_ = _currentParent;
        FileBlock file_ = _input.getFile();
        int instructionLocation_ = _instructionLocation;
        Block br_ = null;
        String found_ = _instruction.toString();
        String trimmedInstruction_ = found_.trim();
        int instructionRealLocation_ = instructionLocation_;
        KeyWords keyWords_ = _page.getKeyWords();
        String keyWordFinal_ = keyWords_.getKeyWordFinal();
        if (!trimmedInstruction_.isEmpty()) {
            instructionLocation_ += StringUtil.getFirstPrintableCharIndex(found_);
        }
        boolean enableByEndLine_ = _enabledEnum;
        String packageName_ = _pkgName;
        if (currentParent_ == null) {
            if (_out.getBlock() != null) {
                _out.getBlock().getBadIndexes().add(_i);
                _instruction.delete(0, _instruction.length());
                after_.setIndex(_i);
                return after_;
            }
            int nextIndex_ = instructionRealLocation_;
            int beginType_ = nextIndex_;
            int accessOffsetType_ = beginType_;
            String afterAccessType_ = found_;
            Ints annotationsIndexesTypes_ = new Ints();
            StringList annotationsTypes_ = new StringList();
            Ints badIndexes_ = _input.getBadIndexes();
            int deltaType_ = 0;
            String trimType_ = afterAccessType_.trim();
            if (trimType_.isEmpty()) {
                if (_input.getType() == OuterBlockEnum.ANON_FCT) {
                    AnonymousFunctionBlock typeBlock_;
                    typeBlock_ = new AnonymousFunctionBlock(_input.getNextIndexBef()+_offset,
                            new OffsetsBlock(beginType_+_offset,beginType_+_offset), _page);
                    typeBlock_.setBegin(beginType_+_offset);
                    typeBlock_.setLengthHeader(1);
                    typeBlock_.getAnnotations().addAllElts(annotationsTypes_);
                    typeBlock_.getAnnotationsIndexes().addAllElts(annotationsIndexesTypes_);
                    typeBlock_.setFile(file_);
                    _out.setBlock(typeBlock_);
                    currentParent_ = typeBlock_;
                } else {
                    RootBlock typeBlock_;
                    typeBlock_ = new AnonymousTypeBlock(beginType_+_offset, packageName_,
                            new OffsetAccessInfo(beginType_+_offset, AccessEnum.PUBLIC), "", new IntMap<String>(),
                            new OffsetsBlock(beginType_+_offset,beginType_+_offset));
                    typeBlock_.setBegin(beginType_+_offset);
                    typeBlock_.setNameLength(1);
                    typeBlock_.setLengthHeader(1);
                    typeBlock_.getAnnotations().addAllElts(annotationsTypes_);
                    typeBlock_.getAnnotationsIndexes().addAllElts(annotationsIndexesTypes_);
                    typeBlock_.setFile(file_);
                    _out.setBlock(typeBlock_);
                    currentParent_ = typeBlock_;
                }
            } else {
                if (trimType_.charAt(0) == ANNOT) {
                    // accessOffesType_ == nextIndex_ == i_ + 1;
                    ParsedAnnotations par_ = new ParsedAnnotations(afterAccessType_, instructionRealLocation_ + _offset);
                    par_.parse();
                    annotationsIndexesTypes_ = par_.getAnnotationsIndexes();
                    annotationsTypes_ = par_.getAnnotations();
                    afterAccessType_ = par_.getAfter();
                    accessOffsetType_ = par_.getIndex() - _offset;
                    deltaType_ = accessOffsetType_ - instructionRealLocation_;
                }
                nextIndex_ += deltaType_;
                String keyWordOperator_ = keyWords_.getKeyWordOperator();
                enableByEndLine_ = false;
                if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordOperator_)) {
                    nextIndex_ += keyWordOperator_.length();
                    String afterOper_ = afterAccessType_.substring(keyWordOperator_.length());
                    int offAfterOper_ = StringUtil.getFirstPrintableCharIndex(afterOper_);
                    nextIndex_ += offAfterOper_;
                    StringBuilder symbol_;
                    int symbolIndex_ = nextIndex_;
                    String trAfterOper_ = afterOper_.trim();
                    symbol_ = fetchSymbol(trAfterOper_);
                    nextIndex_ += symbol_.length();
                    String afterSymbol_ = trAfterOper_.substring(symbol_.length());
                    int offAfterSymbol_ = StringUtil.getFirstPrintableCharIndex(afterSymbol_);
                    nextIndex_ += offAfterSymbol_;
                    String trAfterSymbol_ = afterSymbol_.trim();
                    ParsedImportedTypes p_ = new ParsedImportedTypes(nextIndex_,_offset, trAfterSymbol_);
                    StringList importedTypes_ = p_.getImportedTypes();
                    Ints offsetsImports_ = p_.getOffsetsImports();
                    String afterImports_;
                    int locIndex_ = p_.getNextIndex();
                    if (!p_.isSkip()) {
                        nextIndex_ = p_.getOffset();
                        afterImports_ = trAfterSymbol_.substring(locIndex_);
                    } else {
                        afterImports_ = afterSymbol_;
                    }
                    String info_ = afterImports_;
                    int typeOffset_ = nextIndex_;
                    int paramOffest_;
                    String declaringType_;
                    String afterModifier_ = info_;
                    info_ = afterModifier_.trim();
                    declaringType_ = getFoundType(info_);
                    int declTypeLen_ = declaringType_.length();
                    String afterType_ = info_.substring(declTypeLen_);
                    int afterTypeOff_ = StringUtil.getFirstPrintableCharIndex(afterType_);
                    info_ = afterType_.trim();
                    int leftParIndex_ = info_.indexOf(BEGIN_CALLING);
                    if (leftParIndex_ < 0) {
                        badIndexes_.add(nextIndex_);
                    }
                    String afterMethodName_ = info_.substring(leftParIndex_ + 1);
                    paramOffest_ = afterTypeOff_ + typeOffset_ + declTypeLen_ + 1;
                    paramOffest_ += StringUtil.getFirstPrintableCharIndex(afterMethodName_);
                    info_ = afterMethodName_.trim();
                    ParsedFctHeader parseHeader_ = new ParsedFctHeader();
                    parseHeader_.parse(paramOffest_,info_,_offset, _page);
                    Ints offestsTypes_ = parseHeader_.getOffestsTypes();
                    Ints offestsParams_ = parseHeader_.getOffestsParams();
                    StringList parametersType_ = parseHeader_.getParametersType();
                    StringList parametersName_ = parseHeader_.getParametersName();
                    CustList<Ints> annotationsIndexesParams_ = parseHeader_.getAnnotationsIndexesParams();
                    CustList<StringList> annotationsParams_ = parseHeader_.getAnnotationsParams();
                    boolean ok_ = parseHeader_.isOk();
                    currentParent_ = new OperatorBlock(new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()),
                            new OffsetStringInfo(symbolIndex_ + _offset, symbol_.toString().trim()), parametersType_,
                            offestsTypes_, parametersName_, offestsParams_, new OffsetsBlock(nextIndex_+_offset, nextIndex_+_offset));
                    ((NamedFunctionBlock)currentParent_).getAnnotationsParams().addAllElts(annotationsParams_);
                    ((NamedFunctionBlock)currentParent_).getAnnotationsIndexesParams().addAllElts(annotationsIndexesParams_);
                    ((OperatorBlock)currentParent_).getImports().addAllElts(importedTypes_);
                    ((OperatorBlock)currentParent_).getImportsOffset().addAllElts(offsetsImports_);
                    ((OperatorBlock)currentParent_).getAnnotations().addAllElts(annotationsTypes_);
                    ((OperatorBlock)currentParent_).getAnnotationsIndexes().addAllElts(annotationsIndexesTypes_);
                    currentParent_.setFile(file_);
                    _out.setBlock(currentParent_);
                    if (!ok_) {
                        currentParent_.getBadIndexes().add(_i+_offset);
                    }
                } else {
                    AccessEnum access_ = AccessEnum.PUBLIC;
                    String keyWordAbstract_ = keyWords_.getKeyWordAbstract();
                    String keyWordAnnotation_ = keyWords_.getKeyWordAnnotation();
                    String keyWordClass_ = keyWords_.getKeyWordClass();
                    String keyWordEnum_ = keyWords_.getKeyWordEnum();
                    String keyWordInterface_ = keyWords_.getKeyWordInterface();
                    String keyWordPackage_ = keyWords_.getKeyWordPackage();
                    String keyWordPrivate_ = keyWords_.getKeyWordPrivate();
                    String keyWordProtected_ = keyWords_.getKeyWordProtected();
                    String keyWordPublic_ = keyWords_.getKeyWordPublic();
                    if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordPublic_)) {
                        access_ = AccessEnum.PUBLIC;
                        nextIndex_ += keyWordPublic_.length();
                        String afterAccess_ = afterAccessType_.substring(keyWordPublic_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        afterAccessType_ = afterAccess_.trim();
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordProtected_)) {
                        access_ = AccessEnum.PROTECTED;
                        nextIndex_ += keyWordProtected_.length();
                        String afterAccess_ = afterAccessType_.substring(keyWordProtected_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        afterAccessType_ = afterAccess_.trim();
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordPackage_)) {
                        access_ = AccessEnum.PACKAGE;
                        nextIndex_ += keyWordPackage_.length();
                        String afterAccess_ = afterAccessType_.substring(keyWordPackage_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        afterAccessType_ = afterAccess_.trim();
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordPrivate_)) {
                        access_ = AccessEnum.PRIVATE;
                        nextIndex_ += keyWordPrivate_.length();
                        String afterAccess_ = afterAccessType_.substring(keyWordPrivate_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        afterAccessType_ = afterAccess_.trim();
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordAbstract_)) {
                        access_ = AccessEnum.PACKAGE;
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordFinal_)) {
                        access_ = AccessEnum.PACKAGE;
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordClass_)) {
                        access_ = AccessEnum.PACKAGE;
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordInterface_)) {
                        access_ = AccessEnum.PACKAGE;
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordEnum_)) {
                        access_ = AccessEnum.PACKAGE;
                    } else if (StringExpUtil.startsWithKeyWord(afterAccessType_, keyWordAnnotation_)) {
                        access_ = AccessEnum.PACKAGE;
                    } else {
                        //ERROR
                        badIndexes_.add(nextIndex_);
                    }
                    boolean abstractType_ = false;
                    boolean finalType_ = false;
                    String beforeQu_ = afterAccessType_;
                    if (StringExpUtil.startsWithKeyWord(beforeQu_, keyWordAbstract_)) {
                        abstractType_ = true;
                        nextIndex_ = nextIndex_ + keyWordAbstract_.length();
                        String afterAccess_ = beforeQu_.substring(keyWordAbstract_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        beforeQu_ = afterAccess_.trim();
                    }
                    if (StringExpUtil.startsWithKeyWord(beforeQu_, keyWordFinal_)) {
                        finalType_ = true;
                        nextIndex_ = nextIndex_ + keyWordFinal_.length();
                        String afterAccess_ = beforeQu_.substring(keyWordFinal_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        beforeQu_ = afterAccess_.trim();
                    }
                    int categoryOffset_ = nextIndex_;
                    String type_ = keyWordClass_;
                    if (StringExpUtil.startsWithKeyWord(beforeQu_, keyWordClass_)) {
                        type_ = keyWordClass_;
                        nextIndex_ = nextIndex_ + keyWordClass_.length();
                        String afterAccess_ = beforeQu_.substring(keyWordClass_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        beforeQu_ = afterAccess_.trim();
                    } else if (StringExpUtil.startsWithKeyWord(beforeQu_, keyWordEnum_)) {
                        type_ = keyWordEnum_;
                        nextIndex_ = nextIndex_ + keyWordEnum_.length();
                        String afterAccess_ = beforeQu_.substring(keyWordEnum_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        beforeQu_ = afterAccess_.trim();
                    } else if (StringExpUtil.startsWithKeyWord(beforeQu_, keyWordInterface_)) {
                        type_ = keyWordInterface_;
                        nextIndex_ = nextIndex_ + keyWordInterface_.length();
                        String afterAccess_ = beforeQu_.substring(keyWordInterface_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        beforeQu_ = afterAccess_.trim();
                    } else if (StringExpUtil.startsWithKeyWord(beforeQu_, keyWordAnnotation_)) {
                        type_ = keyWordAnnotation_;
                        nextIndex_ = nextIndex_ + keyWordAnnotation_.length();
                        String afterAccess_ = beforeQu_.substring(keyWordAnnotation_.length());
                        nextIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
                        beforeQu_ = afterAccess_.trim();
                    } else {
                        //ERROR
                        badIndexes_.add(nextIndex_);
                    }
                    ParsedImportedTypes p_ = new ParsedImportedTypes(nextIndex_,_offset, beforeQu_);
                    StringList importedTypes_ = p_.getImportedTypes();
                    Ints offsetsImports_ = p_.getOffsetsImports();
                    String afterImports_;
                    int locIndex_ = p_.getNextIndex();
                    if (!p_.isSkip()) {
                        nextIndex_ = p_.getOffset();
                        afterImports_ = beforeQu_.substring(locIndex_);
                    } else {
                        afterImports_ = beforeQu_;
                    }
                    //insert interfaces static initialization for class and enums
                    String substring_ = afterImports_;
                    InterfacesPart interfacesPart_ = new InterfacesPart(substring_,nextIndex_);
                    interfacesPart_.parse(_page.getKeyWords(),nextIndex_,_offset);
                    StringList staticInitInterfaces_ = interfacesPart_.getStaticInitInterfaces();
                    Ints staticInitInterfacesOffset_ = interfacesPart_.getStaticInitInterfacesOffset();
                    boolean okType_ = interfacesPart_.isOk();
                    int afterInterfaces_ = interfacesPart_.getLocIndex();
                    int delta_ = afterInterfaces_ - nextIndex_;
                    nextIndex_ = afterInterfaces_;
                    String part_;
                    int bad_ = -1;
                    part_ =  substring_.substring(delta_);
                    InheritingPart inh_ = new InheritingPart(nextIndex_,part_);
                    inh_.parse(nextIndex_,_offset);
                    IntMap<String> superTypes_ = inh_.getSuperTypes();
                    String tempDef_ = inh_.getTempDef();
                    String typeName_ = inh_.getTypeName();
                    int beginDefinition_ = inh_.getBeginDefinition();
                    String baseName_;
                    int lastDot_ = typeName_.lastIndexOf(PKG);
                    if (lastDot_ >= 0) {
                        packageName_ = typeName_.substring(0, lastDot_);
                        baseName_ = typeName_.substring(lastDot_ + 1);
                    } else {
                        baseName_ = typeName_;
                    }
                    if (lastDot_ >= 0&&packageName_.isEmpty()) {
                        baseName_ = typeName_.substring(lastDot_);
                    }
                    RootBlock typeBlock_;
                    if (StringUtil.quickEq(type_, keyWordEnum_)) {
                        enableByEndLine_ = true;
                        typeBlock_ = new EnumBlock(beginDefinition_+_offset, baseName_, packageName_,
                                new OffsetAccessInfo(accessOffsetType_+_offset, access_) , tempDef_, superTypes_, new OffsetsBlock(beginType_+_offset,beginType_+_offset));
                    } else if (StringUtil.quickEq(type_, keyWordClass_)) {
                        typeBlock_ = new ClassBlock(beginDefinition_+_offset, baseName_, packageName_,
                                new OffsetAccessInfo(accessOffsetType_+_offset, access_), tempDef_, superTypes_, finalType_, abstractType_, true,
                                new OffsetsBlock(beginType_+_offset,beginType_+_offset));
                    } else if (StringUtil.quickEq(type_, keyWordInterface_)) {
                        typeBlock_ = new InterfaceBlock(beginDefinition_+_offset, baseName_, packageName_,
                                new OffsetAccessInfo(accessOffsetType_+_offset, access_) , tempDef_, superTypes_, true, new OffsetsBlock(beginType_+_offset,beginType_+_offset));
                    } else {
                        typeBlock_ = new AnnotationBlock(beginDefinition_+_offset, baseName_, packageName_,
                                new OffsetAccessInfo(accessOffsetType_+_offset, access_) , tempDef_, superTypes_, new OffsetsBlock(beginType_+_offset,beginType_+_offset));
                    }
                    typeBlock_.setupOffsets(baseName_,packageName_);
                    typeBlock_.setBegin(categoryOffset_+_offset);
                    typeBlock_.setLengthHeader(type_.length());
                    if (!okType_) {
                        typeBlock_.getBadIndexes().add(bad_);
                    }
                    typeBlock_.getImports().addAllElts(importedTypes_);
                    typeBlock_.getImportsOffset().addAllElts(offsetsImports_);
                    typeBlock_.getStaticInitInterfaces().addAllElts(staticInitInterfaces_);
                    typeBlock_.getStaticInitInterfacesOffset().addAllElts(staticInitInterfacesOffset_);
                    typeBlock_.getAnnotations().addAllElts(annotationsTypes_);
                    typeBlock_.getAnnotationsIndexes().addAllElts(annotationsIndexesTypes_);
                    typeBlock_.setFile(file_);
                    _out.setBlock(typeBlock_);
                    currentParent_ = typeBlock_;
                    file_.getPackages().add(StringExpUtil.removeDottedSpaces(packageName_));
                    int indexDotPkg_ = Math.max(0,packageName_.indexOf('.'));
                    file_.getBasePackages().add(StringExpUtil.removeDottedSpaces(packageName_.substring(0,indexDotPkg_)));
                }
            }
        } else if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWords_.getKeyWordIntern())) {
            String exp_ = trimmedInstruction_.substring(keyWords_.getKeyWordIntern().length());
            int internOffest_ = instructionLocation_ + keyWords_.getKeyWordIntern().length();
            int lastPar_ = exp_.lastIndexOf('}');
            int beg_ = exp_.indexOf('{');
            boolean ok_ = false;
            if (beg_ >= 0 && lastPar_ >= beg_ + 1) {
                internOffest_ += beg_ +1;
                exp_ = exp_.substring(beg_ +1,lastPar_);
                internOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
                ok_ = true;
            }
            InternOverrideBlock int_ = new InternOverrideBlock(new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset),exp_, internOffest_+_offset);
            if (!ok_) {
                int_.getBadIndexes().add(_instructionLocation + 1+_offset);
            }
            int_.setBegin(instructionLocation_+_offset);
            int_.setLengthHeader(keyWords_.getKeyWordIntern().length());
            currentParent_.appendChild(int_);
        } else if (currentParent_ instanceof AnnotationBlock) {
            if (!trimmedInstruction_.isEmpty()) {
                if (_declType) {
                    RootBlock built_ = processTypeHeader(_offset, _pkgName,true,
                            instructionLocation_, instructionRealLocation_,
                            found_,
                            trimmedInstruction_,
                            AccessEnum.PUBLIC, _page);
                    currentParent_.appendChild(built_);
                    built_.setParentType((AnnotationBlock)currentParent_);
                    if (built_ instanceof EnumBlock) {
                        enableByEndLine_ = true;
                    }
                    br_ = built_;
                } else {
                    String fieldName_;
                    int expressionOffest_;
                    String expression_;
                    Ints annotationsIndexes_ = new Ints();
                    StringList annotations_ = new StringList();
                    int typeOffset_ = instructionLocation_;
                    if (trimmedInstruction_.charAt(0) == ANNOT) {
                        ParsedAnnotations par_ = new ParsedAnnotations(found_, instructionRealLocation_+_offset);
                        par_.parse();
                        annotationsIndexes_ = par_.getAnnotationsIndexes();
                        annotations_ = par_.getAnnotations();
                        found_ = par_.getAfter();
                        typeOffset_ = par_.getIndex()-_offset;
                    }
                    String otherModifier_;
                    String infoModifiers_ = found_.trim();
                    int finalOff_ = 0;
                    boolean final_ = false;
                    boolean meth_ = true;
                    int deltaFinal_;
                    if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordFinal_)) {
                        otherModifier_ = keyWordFinal_;
                        int lenLoc_ = otherModifier_.length();
                        deltaFinal_ = lenLoc_;
                        String sub_ = infoModifiers_.substring(lenLoc_);
                        int deltaSec_ = StringUtil.getFirstPrintableCharIndex(sub_);
                        deltaFinal_ += deltaSec_;
                        finalOff_ = typeOffset_;
                        found_ = sub_.substring(deltaSec_);
                        final_ = true;
                        meth_ = false;
                        typeOffset_ += deltaFinal_;
                    }
                    String declaringType_ = getFoundType(found_);
                    found_ = found_.substring(declaringType_.length());
                    String realFound_ = found_;
                    found_ = found_.trim();
                    int lenAfterModifiers_ = found_.length();
                    int indexMod_ = 0;
                    while (indexMod_ < lenAfterModifiers_) {
                        char cur_ = found_.charAt(indexMod_);
                        if (!StringExpUtil.isTypeLeafChar(cur_)) {
                            break;
                        }
                        indexMod_++;
                    }
                    while (indexMod_ < lenAfterModifiers_) {
                        char cur_ = found_.charAt(indexMod_);
                        if (!StringUtil.isWhitespace(cur_)) {
                            break;
                        }
                        indexMod_++;
                    }
                    if (found_.indexOf(BEGIN_CALLING, indexMod_) != indexMod_) {
                        meth_ = false;
                    }
                    if (!meth_) {
                        int fieldNameOffest_ = StringUtil.getFirstPrintableCharIndex(realFound_) +declaringType_.trim().length() + typeOffset_;
                        FieldBlock field_ = new FieldBlock(
                                new OffsetAccessInfo(-1, AccessEnum.PUBLIC),
                                new OffsetBooleanInfo(-1, true),
                                new OffsetBooleanInfo(finalOff_+_offset, final_),
                                new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()),
                                new OffsetStringInfo(fieldNameOffest_+_offset, realFound_.trim()),
                                new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                        field_.getAnnotations().addAllElts(annotations_);
                        field_.getAnnotationsIndexes().addAllElts(annotationsIndexes_);
                        int fieldNb_ = ((RootBlock)currentParent_).getCountField();
                        field_.setFieldNumber(fieldNb_);
                        ((RootBlock)currentParent_).setCountField(fieldNb_+1);
                        br_ = field_;
                    } else {
                        found_ = realFound_;
                        int fieldOffest_ = typeOffset_;
                        fieldOffest_ += declaringType_.trim().length();
                        int offFound_ = StringUtil.getFirstPrintableCharIndex(found_);
                        fieldOffest_ += offFound_;
                        int indexBeginCalling_ = found_.indexOf(BEGIN_CALLING);
                        AnnotationMethodBlock annMeth_;
                        int rightPar_;
                        fieldName_ = found_.substring(0, indexBeginCalling_);
                        rightPar_ = found_.indexOf(END_CALLING,indexBeginCalling_);
                        if (rightPar_ > -1) {
                            expression_ = found_.substring(rightPar_ + 1);
                            expressionOffest_ = fieldOffest_ - offFound_ + rightPar_ + 1;
                        } else {
                            expression_ = found_.substring(fieldName_.length());
                            expressionOffest_ = fieldOffest_ + fieldName_.trim().length();
                        }
                        if (!expression_.trim().isEmpty()) {
                            expressionOffest_ += StringUtil.getFirstPrintableCharIndex(expression_);
                        }
                        annMeth_ = new AnnotationMethodBlock(
                                new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()),
                                new OffsetStringInfo(fieldOffest_+_offset, fieldName_.trim()),
                                new OffsetStringInfo(expressionOffest_+_offset, expression_.trim()),
                                new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset),rightPar_);
                        ((RootBlock)currentParent_).getAnnotationsMethodsBlocks().add(annMeth_);
                        if (rightPar_ < indexBeginCalling_) {
                            annMeth_.setKo();
                        } else if (!found_.substring(indexBeginCalling_+1,rightPar_).trim().isEmpty()){
                            annMeth_.setKo();
                        }
                        annMeth_.getAnnotations().addAllElts(annotations_);
                        annMeth_.getAnnotationsIndexes().addAllElts(annotationsIndexes_);
                        int fctNb_ = ((RootBlock)currentParent_).getCountFct();
                        annMeth_.setNumberFct(fctNb_);
                        ((RootBlock)currentParent_).setCountFct(fctNb_+1);
                        int nameNb_ = ((RootBlock)currentParent_).getCountName();
                        annMeth_.setNameNumber(nameNb_);
                        ((RootBlock)currentParent_).setCountName(nameNb_+1);
                        br_ = annMeth_;
                    }
                    currentParent_.appendChild(br_);
                }
            } else {
                //implicit static block
                if (_currentChar != END_BLOCK) {
                    br_ = new StaticBlock(new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                    int initNb_ = ((RootBlock)currentParent_).getCountInit();
                    ((InitBlock) br_).setNumber(initNb_);
                    ((RootBlock)currentParent_).setCountInit(initNb_+1);
                    int fctNb_ = ((RootBlock)currentParent_).getCountFct();
                    ((InitBlock) br_).setNumberFct(fctNb_);
                    ((RootBlock)currentParent_).setCountFct(fctNb_+1);
                    int bodyFctNb_ = ((RootBlock)currentParent_).getCountBodyFct();
                    ((InitBlock) br_).setNumberBodyFct(bodyFctNb_);
                    ((RootBlock)currentParent_).setCountBodyFct(bodyFctNb_+1);
                    currentParent_.appendChild(br_);
                }
            }
            if (_currentChar == END_BLOCK) {
                if (!trimmedInstruction_.isEmpty()) {
                    int affectOffset_;
                    int afterDeclareOffset_;
                    affectOffset_ = instructionRealLocation_;
                    affectOffset_ += StringUtil.getFirstPrintableCharIndex(found_);
                    afterDeclareOffset_ = affectOffset_;
                    br_ = new Line(new OffsetStringInfo(afterDeclareOffset_+_offset, trimmedInstruction_), new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                    br_.getBadIndexes().add(_i);
                    br_.setBegin(_i+_offset);
                    br_.setLengthHeader(1);
                    currentParent_.appendChild(br_);
                }
                currentParent_ = currentParent_.getParent();
            } else {
                if (br_ instanceof BracedBlock && _currentChar != END_LINE) {
                    currentParent_ = (BracedBlock) br_;
                }
            }
        } else if (currentParent_ instanceof EnumBlock && enableByEndLine_) {
            if (!trimmedInstruction_.isEmpty()) {
                String fieldName_;
                int fieldOffest_ = instructionLocation_;
                int expressionOffest_;
                String expression_ = EMPTY_STRING;
                int delta_ = 0;
                Ints annotationsIndexes_ = new Ints();
                StringList annotations_ = new StringList();
                if (trimmedInstruction_.charAt(0) == ANNOT) {
                    ParsedAnnotations par_ = new ParsedAnnotations(found_, instructionRealLocation_+_offset);
                    par_.parse();
                    annotationsIndexes_ = par_.getAnnotationsIndexes();
                    annotations_ = par_.getAnnotations();
                    found_ = par_.getAfter();
                    fieldOffest_ = par_.getIndex()-_offset;
                    delta_ = fieldOffest_ - instructionRealLocation_;
                }
                boolean ok_ = true;
                int indexBeginCalling_ = found_.indexOf(BEGIN_CALLING);
                if (indexBeginCalling_ >= 0) {
                    fieldName_ = found_.substring(0, indexBeginCalling_);
                    int endIndex_ = found_.lastIndexOf(END_CALLING);
                    expressionOffest_ = instructionRealLocation_ + indexBeginCalling_ + 1 + delta_;
                    if (endIndex_ < indexBeginCalling_ + 1) {
                        ok_ = false;
                    } else {
                        expression_ = found_.substring(indexBeginCalling_ + 1, endIndex_);
                        if (!expression_.isEmpty()) {
                            expressionOffest_ += StringUtil.getFirstPrintableCharIndex(expression_);
                        }
                    }
                } else {
                    fieldName_ = found_;
                    expressionOffest_ = fieldOffest_;
                    expressionOffest_ += fieldName_.trim().length();
                    expressionOffest_ += fieldName_.length() - StringUtil.getLastPrintableCharIndex(fieldName_) - 1;
                }
                int indexTmp_ = fieldName_.indexOf(Templates.TEMPLATE_BEGIN);
                String tmpPart_ = EMPTY_STRING;
                int templateOffset_ = 0;
                if (indexTmp_ > -1) {
                    templateOffset_ = fieldOffest_;
                    tmpPart_ = fieldName_.substring(indexTmp_);
                    fieldName_ = fieldName_.substring(0, indexTmp_);
                    templateOffset_ += fieldName_.trim().length();
                    templateOffset_ += fieldName_.length() - StringUtil.getLastPrintableCharIndex(fieldName_) - 1;
                }
                if (_currentChar == BEGIN_BLOCK) {
                    enableByEndLine_ = false;
                    InnerElementBlock elt_ = new InnerElementBlock((EnumBlock) currentParent_, _pkgName, new OffsetStringInfo(fieldOffest_+_offset, fieldName_.trim()),
                            new OffsetStringInfo(templateOffset_+_offset, tmpPart_.trim()),
                            new OffsetStringInfo(expressionOffest_+_offset, expression_.trim()), new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                    elt_.setupOffsets(fieldName_.trim(),_pkgName);
                    elt_.setParentType((RootBlock) currentParent_);
                    elt_.getAnnotations().addAllElts(annotations_);
                    elt_.getAnnotationsIndexes().addAllElts(annotationsIndexes_);
                    br_ = elt_;
                } else {
                    ElementBlock elt_ = new ElementBlock((EnumBlock) currentParent_, new OffsetStringInfo(fieldOffest_+_offset, fieldName_.trim()),
                            new OffsetStringInfo(templateOffset_+_offset, tmpPart_.trim()),
                            new OffsetStringInfo(expressionOffest_+_offset, expression_.trim()), new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                    elt_.getAnnotations().addAllElts(annotations_);
                    elt_.getAnnotationsIndexes().addAllElts(annotationsIndexes_);
                    br_ = elt_;
                }
                int fieldNb_ = ((RootBlock)currentParent_).getCountField();
                ((InfoBlock)br_).setFieldNumber(fieldNb_);
                ((RootBlock)currentParent_).setCountField(fieldNb_+1);
                if (!ok_) {
                    br_.getBadIndexes().add(indexBeginCalling_ + 1+_offset);
                }
                currentParent_.appendChild(br_);
                if (_currentChar == BEGIN_BLOCK) {
                    currentParent_ = (BracedBlock) br_;
                }
            }
            if (_currentChar == END_BLOCK) {
                currentParent_ = currentParent_.getParent();
            }
            if (_currentChar == END_LINE || _currentChar == END_BLOCK) {
                enableByEndLine_ = false;
            }
        } else if (_currentChar != END_BLOCK) {
            Block bl_ = processInstructionBlock(_offset, instructionLocation_, instructionRealLocation_, _i, currentParent_, trimmedInstruction_, _page);
            if (bl_ == null) {
                if (_declType) {
                    //Inner types
                    boolean defStatic_;
                    MemberCallingsBlock outerFuntion_ = Block.getOuterFuntionInType(currentParent_);
                    if (outerFuntion_ != null) {
                        defStatic_ = outerFuntion_.getStaticContext() != MethodAccessKind.INSTANCE;
                    } else {
                        defStatic_ = false;
                    }
                    RootBlock built_ = processTypeHeader(_offset, _pkgName,defStatic_,
                            instructionLocation_, instructionRealLocation_,
                            found_,
                            trimmedInstruction_,
                            AccessEnum.PACKAGE, _page);
                    built_.setParentType(currentParent_.retrieveParentType());
                    currentParent_.appendChild(built_);
                    if (built_ instanceof EnumBlock) {
                        enableByEndLine_ = true;
                    }
                    br_ = built_;
                } else if (currentParent_ instanceof RootBlock) {
                    //fields, constructors or methods
                    br_ = processTypeMember(_currentChar, _instruction, instructionLocation_, _i, _offset, (RootBlock)currentParent_, _page);
                } else {
                    int affectOffset_;
                    int afterDeclareOffset_;
                    boolean finalLocalVar_ = StringExpUtil.startsWithKeyWord(trimmedInstruction_, keyWordFinal_);
                    int delta_ = 0;
                    int deltaAfter_ = 0;
                    if (finalLocalVar_) {
                        deltaAfter_ = keyWordFinal_.length();
                        delta_ = StringUtil.getFirstPrintableCharIndex(found_) + deltaAfter_;
                        deltaAfter_ = delta_;
                        deltaAfter_ += StringUtil.getFirstPrintableCharIndex(found_.substring(delta_));
                    }
                    found_ = found_.substring(delta_);
                    String declaringType_ = getDeclaringTypeInstr(found_,keyWords_);
                    boolean typeDeclaring_ = !declaringType_.trim().isEmpty();
                    String info_;
                    int realTypeOffset_;
                    if (finalLocalVar_) {
                        realTypeOffset_ = instructionRealLocation_ + deltaAfter_;
                    } else {
                        realTypeOffset_ = instructionLocation_;
                    }
                    if (typeDeclaring_) {
                        int varNameOffset_ = instructionRealLocation_ + delta_;
                        varNameOffset_ += declaringType_.length();
                        info_ = found_.substring(declaringType_.length());
                        varNameOffset_ += StringUtil.getFirstPrintableCharIndex(info_);
                        afterDeclareOffset_ = varNameOffset_;
                    } else {
                        affectOffset_ = instructionRealLocation_;
                        affectOffset_ += StringUtil.getFirstPrintableCharIndex(found_);
                        afterDeclareOffset_ = affectOffset_;
                        info_ = found_;
                    }
                    String inst_ = info_;
                    if (typeDeclaring_) {
                        br_ = new DeclareVariable(new OffsetBooleanInfo(instructionLocation_+_offset, finalLocalVar_),
                                new OffsetStringInfo(realTypeOffset_+_offset, declaringType_.trim()),
                                new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                        currentParent_.appendChild(br_);
                    }
                    br_ = new Line(new OffsetStringInfo(afterDeclareOffset_+_offset, inst_.trim()), new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                    br_.setBegin(_i+_offset);
                    br_.setLengthHeader(1);
                    currentParent_.appendChild(br_);
                }
            } else {
                br_ = bl_;
            }
            if (br_ instanceof BracedBlock && _currentChar != END_LINE) {
                currentParent_ = (BracedBlock) br_;
            }
        } else {
            //currentChar_ == END_BLOCK
            if (!trimmedInstruction_.isEmpty()) {
                int affectOffset_;
                int afterDeclareOffset_;
                affectOffset_ = instructionRealLocation_;
                affectOffset_ += StringUtil.getFirstPrintableCharIndex(found_);
                afterDeclareOffset_ = affectOffset_;
                br_ = new Line(new OffsetStringInfo(afterDeclareOffset_+_offset, trimmedInstruction_), new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                br_.getBadIndexes().add(_i+_offset);
                br_.setBegin(_i+_offset);
                br_.setLengthHeader(1);
                currentParent_.appendChild(br_);
            }
            if (currentParent_ instanceof SwitchPartBlock) {
                currentParent_ = currentParent_.getParent();
            }
            if (currentParent_ instanceof InnerTypeOrElement) {
                enableByEndLine_ = true;
            }
            currentParent_ = currentParent_.getParent();
        }
        _instruction.delete(0, _instruction.length());
        after_.setIndex(_i);
        after_.setParent(currentParent_);
        after_.setEnabledEnumHeader(enableByEndLine_);
        after_.setPackageName(packageName_);
        return after_;
    }
    private static RootBlock processTypeHeader(int _offset, String _pkgName,
                                               boolean _defStatic,
                                               int _instructionLocation, int _instructionRealLocation, String _found,
                                               String _trimmedInstruction,
                                               AccessEnum _defAccess, AnalyzedPageEl _page) {
        //Inner types
        KeyWords keyWords_ = _page.getKeyWords();
        AccessEnum accessFct_ = _defAccess;
        String word_ = EMPTY_STRING;
        int trFound_ = StringUtil.getFirstPrintableCharIndex(_found);
        String keyWordPackage_ = keyWords_.getKeyWordPackage();
        String keyWordPrivate_ = keyWords_.getKeyWordPrivate();
        String keyWordProtected_ = keyWords_.getKeyWordProtected();
        String keyWordPublic_ = keyWords_.getKeyWordPublic();
        String trimmedInstruction_ = _trimmedInstruction;
        Ints annotationsIndexes_ = new Ints();
        StringList annotations_ = new StringList();
        int typeOffset_ = _instructionLocation;
        if (trimmedInstruction_.charAt(0) == ANNOT) {
            ParsedAnnotations par_ = new ParsedAnnotations(trimmedInstruction_, _instructionLocation+_offset);
            par_.parse();
            annotationsIndexes_ = par_.getAnnotationsIndexes();
            annotations_ = par_.getAnnotations();
            trimmedInstruction_ = par_.getAfter();
            typeOffset_ = par_.getIndex()-_offset;
        }
        if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordPrivate_)) {
            accessFct_ = AccessEnum.PRIVATE;
            word_ = keyWordPrivate_;
        } else {
            if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordPackage_)) {
                accessFct_ = AccessEnum.PACKAGE;
                word_ = keyWordPackage_;
            } else {
                if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordProtected_)) {
                    accessFct_ = AccessEnum.PROTECTED;
                    word_ = keyWordProtected_;
                } else {
                    if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordPublic_)) {
                        accessFct_ = AccessEnum.PUBLIC;
                        word_ = keyWordPublic_;
                    }
                }
            }
        }
        String afterAccess_ = trimmedInstruction_.substring(word_.length());
        int locIndex_ = typeOffset_ + word_.length();
        locIndex_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
        boolean staticType_ = _defStatic;
        boolean abstractType_ = false;
        boolean finalType_ = false;
        String keyWordAbstract_ = keyWords_.getKeyWordAbstract();
        String keyWordAnnotation_ = keyWords_.getKeyWordAnnotation();
        String keyWordClass_ = keyWords_.getKeyWordClass();
        String keyWordEnum_ = keyWords_.getKeyWordEnum();
        String keyWordFinal_ = keyWords_.getKeyWordFinal();
        String keyWordInterface_ = keyWords_.getKeyWordInterface();
        String keyWordStatic_ = keyWords_.getKeyWordStatic();
        String beforeQu_ = afterAccess_.trim();
        if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordAbstract_)) {
            abstractType_ = true;
            String sub_ = beforeQu_.substring(keyWordAbstract_.length());
            beforeQu_ = sub_.trim();
            locIndex_ += keyWordAbstract_.length();
            locIndex_ += StringUtil.getFirstPrintableCharIndex(sub_);
        }
        if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordStatic_)) {
            staticType_ = true;
            String sub_ = beforeQu_.substring(keyWordStatic_.length());
            beforeQu_ = sub_.trim();
            locIndex_ += keyWordStatic_.length();
            locIndex_ += StringUtil.getFirstPrintableCharIndex(sub_);
        }
        if (StringExpUtil.startsWithKeyWord(beforeQu_,keyWordFinal_)) {
            finalType_ = true;
            String sub_ = beforeQu_.substring(keyWordFinal_.length());
            beforeQu_ = sub_.trim();
            locIndex_ += keyWordFinal_.length();
            locIndex_ += StringUtil.getFirstPrintableCharIndex(sub_);
        }
        String type_;
        int categoryOffset_ = locIndex_;
        String infoModifiers_ = beforeQu_;
        if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordClass_)) {
            type_ = keyWordClass_;
            locIndex_ += keyWordClass_.length();
        } else if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordEnum_)) {
            type_ = keyWordEnum_;
            locIndex_ += keyWordEnum_.length();
        } else if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordInterface_)) {
            type_ = keyWordInterface_;
            locIndex_ += keyWordInterface_.length();
        } else {
            type_ = keyWordAnnotation_;
            locIndex_ += keyWordAnnotation_.length();
        }
        String sub_ = beforeQu_.substring(type_.length());
        beforeQu_ = sub_.trim();
        locIndex_ += StringUtil.getFirstPrintableCharIndex(sub_);

        return tryBuiltTypeWithInfos(keyWords_,_offset,beforeQu_, _instructionLocation, _instructionRealLocation,_pkgName,
                accessFct_, trFound_, annotationsIndexes_, annotations_, typeOffset_,
                locIndex_, staticType_, abstractType_, finalType_,
                type_, categoryOffset_);
    }

    private static RootBlock tryBuiltTypeWithInfos(KeyWords _keyWords, int _offset, String _infoPart, int _instructionLocation, int _instructionRealLocation, String _pkgName, AccessEnum _accessFct, int _trFound,
                                                   Ints _annotationsIndexes, StringList _annotations, int _typeOffset, int _locIndex,
                                                   boolean _staticType, boolean _abstractType, boolean _finalType,
                                                   String _type,
                                                   int _categoryOffset) {
        ParsedImportedTypes p_ = new ParsedImportedTypes(_locIndex,_offset, _infoPart);
        StringList importedTypes_ = p_.getImportedTypes();
        Ints offsetsImports_ = p_.getOffsetsImports();
        int locIndex_ = p_.getNextIndex();
        //insert interfaces static initialization for class and enums
        String infoPart_ = _infoPart;
        if (!p_.isSkip()) {
            infoPart_ = infoPart_.substring(locIndex_);
            locIndex_ = p_.getOffset();
        } else {
            locIndex_ = _locIndex;
        }
        InterfacesPart interfacesPart_ = new InterfacesPart(infoPart_,locIndex_);
        interfacesPart_.parse(_keyWords,locIndex_,_offset);
        locIndex_ = interfacesPart_.getLocIndex();
        infoPart_ = interfacesPart_.getPart();
        StringList staticInitInterfaces_ = interfacesPart_.getStaticInitInterfaces();
        Ints staticInitInterfacesOffset_ = interfacesPart_.getStaticInitInterfacesOffset();
        boolean ok_ = interfacesPart_.isOk();
        RootBlock r_ = tryBuildType(_keyWords,_offset,infoPart_, _instructionLocation, _instructionRealLocation, _pkgName,
                _accessFct, _trFound, _annotationsIndexes,
                _annotations, _typeOffset, locIndex_, _staticType, _abstractType, _finalType,
                _type, _categoryOffset, importedTypes_,
                offsetsImports_, staticInitInterfaces_, staticInitInterfacesOffset_);
        if (!ok_) {
            r_.getBadIndexes().add(locIndex_);
        }
        return r_;
    }

    private static RootBlock tryBuildType(KeyWords _keyWords, int _offset, String _infoPart, int _instructionLocation, int _instructionRealLocation, String _pkgName,
                                          AccessEnum _accessFct, int _trFound, Ints _annotationsIndexes, StringList _annotations, int _typeOffset, int _locIndex,
                                          boolean _staticType, boolean _abstractType, boolean _finalType,
                                          String _type, int _categoryOffset, StringList _importedTypes,
                                          Ints _offsetsImports, StringList _staticInitInterfaces, Ints _staticInitInterfacesOffset) {
        InheritingPart inh_ = new InheritingPart(_locIndex,_infoPart);
        inh_.parse(_locIndex,_offset);
        IntMap<String> superTypes_ = inh_.getSuperTypes();
        String tempDef_ = inh_.getTempDef();
        String typeName_ = inh_.getTypeName();
        int beginDefinition_ = inh_.getBeginDefinition();

        String keyWordClass_ = _keyWords.getKeyWordClass();
        String keyWordEnum_ = _keyWords.getKeyWordEnum();
        String keyWordInterface_ = _keyWords.getKeyWordInterface();
        RootBlock typeBlock_;
        if (StringUtil.quickEq(_type, keyWordEnum_)) {
            typeBlock_ = new EnumBlock(beginDefinition_, typeName_, _pkgName,
                    new OffsetAccessInfo(_typeOffset - 1+_offset, _accessFct) , tempDef_, superTypes_,
                    new OffsetsBlock(_instructionRealLocation + _trFound+_offset, _instructionLocation + _trFound+_offset));
        } else if (StringUtil.quickEq(_type, keyWordClass_)) {
            typeBlock_ = new ClassBlock(beginDefinition_, typeName_, _pkgName,
                    new OffsetAccessInfo(_typeOffset - 1+_offset, _accessFct), tempDef_, superTypes_, _finalType, _abstractType, _staticType,
                    new OffsetsBlock(_instructionRealLocation + _trFound+_offset, _instructionLocation + _trFound+_offset));
        } else if (StringUtil.quickEq(_type, keyWordInterface_)) {
            typeBlock_ = new InterfaceBlock(beginDefinition_, typeName_, _pkgName,
                    new OffsetAccessInfo(_typeOffset - 1+_offset, _accessFct) , tempDef_, superTypes_, _staticType,
                    new OffsetsBlock(_instructionRealLocation + _trFound+_offset, _instructionLocation + _trFound+_offset));
        } else {
            typeBlock_ = new AnnotationBlock(beginDefinition_, typeName_, _pkgName,
                    new OffsetAccessInfo(_typeOffset - 1+_offset, _accessFct) , tempDef_, superTypes_,
                    new OffsetsBlock(_instructionRealLocation + _trFound+_offset, _instructionLocation + _trFound+_offset));
        }
        typeBlock_.setBegin(_categoryOffset+_offset);
        typeBlock_.setLengthHeader(_type.length());
        typeBlock_.setupOffsets(typeName_,"");
        typeBlock_.getImports().addAllElts(_importedTypes);
        typeBlock_.getImportsOffset().addAllElts(_offsetsImports);
        typeBlock_.getStaticInitInterfaces().addAllElts(_staticInitInterfaces);
        typeBlock_.getStaticInitInterfacesOffset().addAllElts(_staticInitInterfacesOffset);
        typeBlock_.getAnnotations().addAllElts(_annotations);
        typeBlock_.getAnnotationsIndexes().addAllElts(_annotationsIndexes);
        return typeBlock_;
    }

    private static Block processTypeMember(char _currentChar,
                                           StringBuilder _instruction, int _instructionLocation, int _i, int _offset, RootBlock _currentParent, AnalyzedPageEl _page) {
        int instructionLocation_ = _instructionLocation;
        String found_ = _instruction.toString();
        String trimmedInstruction_ = found_.trim();
        int instructionRealLocation_ = instructionLocation_;
        instructionLocation_ += StringUtil.getFirstPrintableCharIndex(found_);
        Block br_;
        AccessEnum accessFct_ = AccessEnum.PACKAGE;
        String word_ = EMPTY_STRING;
        int trFound_ = StringUtil.getFirstPrintableCharIndex(found_);
        int accessOffest_ = trFound_ + _i - found_.length();
        Ints annotationsIndexes_ = new Ints();
        StringList annotations_ = new StringList();
        int deltaAccess_ = 0;
        KeyWords keyWords_ = _page.getKeyWords();
        String keyWordAbstract_ = keyWords_.getKeyWordAbstract();
        String keyWordFinal_ = keyWords_.getKeyWordFinal();
        String keyWordNormal_ = keyWords_.getKeyWordNormal();
        String keyWordPackage_ = keyWords_.getKeyWordPackage();
        String keyWordPrivate_ = keyWords_.getKeyWordPrivate();
        String keyWordProtected_ = keyWords_.getKeyWordProtected();
        String keyWordPublic_ = keyWords_.getKeyWordPublic();
        String keyWordStatic_ = keyWords_.getKeyWordStatic();
        String keyWordStaticCall_ = keyWords_.getKeyWordStaticCall();
        if (trimmedInstruction_.charAt(0) == ANNOT) {
            ParsedAnnotations par_ = new ParsedAnnotations(trimmedInstruction_, accessOffest_+_offset);
            par_.parse();
            annotationsIndexes_ = par_.getAnnotationsIndexes();
            annotations_ = par_.getAnnotations();
            trimmedInstruction_ = par_.getAfter();
            accessOffest_ = par_.getIndex()-_offset;
            deltaAccess_ = accessOffest_ - (trFound_ + _i - found_.length());
        }
        if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordPrivate_)) {
            accessFct_ = AccessEnum.PRIVATE;
            word_ = keyWordPrivate_;
        } else {
            if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordPackage_)) {
                word_ = keyWordPackage_;
            } else {
                if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordProtected_)) {
                    accessFct_ = AccessEnum.PROTECTED;
                    word_ = keyWordProtected_;
                } else {
                    if (StringExpUtil.startsWithKeyWord(trimmedInstruction_,keyWordPublic_)) {
                        accessFct_ = AccessEnum.PUBLIC;
                        word_ = keyWordPublic_;
                    }
                }
            }
        }
        String afterAccess_ = trimmedInstruction_.substring(word_.length());
        String trimmedAfterAccess_ = afterAccess_.trim();
        String infoModifiers_ = trimmedAfterAccess_;
        boolean field_ = false;
        if (_currentChar == ';' && StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordStatic_)) {
            String otherModifier_;
            otherModifier_ = keyWordStatic_;
            int lenLoc_ = otherModifier_.length();
            String sub_ = infoModifiers_.substring(lenLoc_);
            int delta_ = StringUtil.getFirstPrintableCharIndex(sub_);
            infoModifiers_ = sub_.substring(delta_);
            if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordFinal_)) {
                field_ = true;
            }
        }
        boolean ctor_ = false;
        String ctorName_ = "";
        if (!field_) {
            if (trimmedAfterAccess_.startsWith("(")) {
                ctor_ = true;
            } else {
                int indexPar_ = Math.max(0, trimmedAfterAccess_.indexOf('('));
                String firstPart_ = trimmedAfterAccess_.substring(0, indexPar_).trim();
                if (StringExpUtil.isTypeLeafPart(firstPart_)) {
                    ctorName_ = firstPart_;
                    ctor_ = true;
                }
            }
        }
        boolean meth_ = false;
        boolean oper_ = false;
        if (StringExpUtil.startsWithKeyWord(trimmedAfterAccess_,keyWords_.getKeyWordOperator())) {
            oper_ = true;
        } else if (!field_ && !ctor_) {
            infoModifiers_ = trimmedAfterAccess_;
            String otherModifier_;
            if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordNormal_)) {
                otherModifier_ = keyWordNormal_;
                int lenLoc_ = otherModifier_.length();
                String sub_ = infoModifiers_.substring(lenLoc_);
                int delta_ = StringUtil.getFirstPrintableCharIndex(sub_);
                infoModifiers_ = sub_.substring(delta_);
            } else {
                if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordAbstract_)) {
                    otherModifier_ = keyWordAbstract_;
                    int lenLoc_ = otherModifier_.length();
                    String sub_ = infoModifiers_.substring(lenLoc_);
                    int delta_ = StringUtil.getFirstPrintableCharIndex(sub_);
                    infoModifiers_ = sub_.substring(delta_);
                } else {
                    if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordStatic_)) {
                        otherModifier_ = keyWordStatic_;
                        int lenLoc_ = otherModifier_.length();
                        String sub_ = infoModifiers_.substring(lenLoc_);
                        int delta_ = StringUtil.getFirstPrintableCharIndex(sub_);
                        infoModifiers_ = sub_.substring(delta_);
                    } else {
                        if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordStaticCall_)) {
                            otherModifier_ = keyWordStaticCall_;
                            int lenLoc_ = otherModifier_.length();
                            String sub_ = infoModifiers_.substring(lenLoc_);
                            int delta_ = StringUtil.getFirstPrintableCharIndex(sub_);
                            infoModifiers_ = sub_.substring(delta_);
                        } else {
                            if (StringExpUtil.startsWithKeyWord(infoModifiers_,keyWordFinal_)) {
                                otherModifier_ = keyWordFinal_;
                                int lenLoc_ = otherModifier_.length();
                                String sub_ = infoModifiers_.substring(lenLoc_);
                                int delta_ = StringUtil.getFirstPrintableCharIndex(sub_);
                                infoModifiers_ = sub_.substring(delta_);
                            }
                        }
                    }
                }
            }
            String typeStr_ = getFoundType(infoModifiers_);
            infoModifiers_ = infoModifiers_.substring(typeStr_.length()).trim();
            int lenAfterModifiers_ = infoModifiers_.length();
            int indexMod_ = 0;
            while (indexMod_ < lenAfterModifiers_) {
                char cur_ = infoModifiers_.charAt(indexMod_);
                if (!StringExpUtil.isTypeLeafChar(cur_)) {
                    break;
                }
                indexMod_++;
            }
            while (indexMod_ < lenAfterModifiers_) {
                char cur_ = infoModifiers_.charAt(indexMod_);
                if (!StringUtil.isWhitespace(cur_)) {
                    break;
                }
                indexMod_++;
            }
            if (StringExpUtil.nextCharIs(infoModifiers_,indexMod_,lenAfterModifiers_,BEGIN_CALLING)) {
                meth_ = true;
            }
        }
        if (meth_|| oper_||ctor_||_currentChar != ';') {
            if (_currentParent instanceof InterfaceBlock && word_.isEmpty()) {
                accessFct_ = AccessEnum.PUBLIC;
            }

            //constructors or methods or types
            int modifierOffest_ = accessOffest_ + word_.length();
            modifierOffest_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
            String info_ = afterAccess_.trim();
            int methodNameOffest_ = -1;
            int typeOffset_ = -1;
            int paramOffest_;
            String methodName_ = EMPTY_STRING;
            String declaringType_ = EMPTY_STRING;
            String modifier_ = EMPTY_STRING;
            String prefModifier_ = EMPTY_STRING;
            int leftPar_=0;
            if (meth_) {
                if (StringExpUtil.startsWithKeyWord(info_,keyWordNormal_)) {
                    modifier_ = keyWordNormal_;
                    prefModifier_ = modifier_;
                } else {
                    if (StringExpUtil.startsWithKeyWord(info_,keyWordAbstract_)) {
                        modifier_ = keyWordAbstract_;
                        prefModifier_ = modifier_;
                    } else {
                        if (StringExpUtil.startsWithKeyWord(info_,keyWordStatic_)) {
                            modifier_ = keyWordStatic_;
                            prefModifier_ = modifier_;
                        } else {
                            if (StringExpUtil.startsWithKeyWord(info_,keyWordStaticCall_)) {
                                modifier_ = keyWordStaticCall_;
                                prefModifier_ = modifier_;
                            } else {
                                if (StringExpUtil.startsWithKeyWord(info_,keyWordFinal_)) {
                                    modifier_ = keyWordFinal_;
                                    prefModifier_ = modifier_;
                                }
                            }
                        }
                    }
                }
                String afterModifier_ = info_.substring(prefModifier_.length());
                typeOffset_ = modifierOffest_ + prefModifier_.length();
                if (modifier_.isEmpty()) {
                    if (_currentParent instanceof InterfaceBlock) {
                        modifier_ = keyWordAbstract_;
                    } else {
                        modifier_ = keyWordNormal_;
                    }
                }
                typeOffset_ += StringUtil.getFirstPrintableCharIndex(afterModifier_);
                info_ = afterModifier_.trim();
                declaringType_ = getFoundType(info_);
                String afterType_ = info_.substring(declaringType_.length());
                methodNameOffest_ = typeOffset_ + declaringType_.length();
                methodNameOffest_ += StringUtil.getFirstPrintableCharIndex(afterType_);
                info_ = afterType_.trim();
                int leftParIndex_ = info_.indexOf(BEGIN_CALLING);
                methodName_ = info_.substring(0, leftParIndex_);
                String afterMethodName_ = info_.substring(leftParIndex_ + 1);
                paramOffest_ = methodNameOffest_ + leftParIndex_ + 1;
                paramOffest_ += StringUtil.getFirstPrintableCharIndex(afterMethodName_);
                info_ = afterMethodName_.trim();
            } else if (oper_){
                accessFct_ = AccessEnum.PUBLIC;
                modifier_ = keyWordStatic_;
                prefModifier_ = keyWords_.getKeyWordOperator();
                String afterModifier_ = info_.substring(prefModifier_.length());
                methodNameOffest_ = modifierOffest_ + prefModifier_.length();
                methodNameOffest_ += StringUtil.getFirstPrintableCharIndex(afterModifier_);
                afterModifier_ = afterModifier_.substring(StringUtil.getFirstPrintableCharIndex(afterModifier_));
                methodName_ = fetchSymbol(afterModifier_).toString();
                afterModifier_ = afterModifier_.substring(methodName_.length());
                typeOffset_ = methodNameOffest_ + methodName_.length();
                typeOffset_ += StringUtil.getFirstPrintableCharIndex(afterModifier_);
                info_ = afterModifier_.trim();
                if (StringExpUtil.startsWithKeyWord(info_,keyWordStaticCall_)) {
                    modifier_ = keyWordStaticCall_;
                    typeOffset_ += keyWordStaticCall_.length();
                    String after_ = info_.substring(keyWordStaticCall_.length());
                    typeOffset_ += StringUtil.getFirstPrintableCharIndex(after_);
                    info_ = after_.trim();
                }
                declaringType_ = getFoundType(info_);
                String afterType_ = info_.substring(declaringType_.length());
                int leftParIndex_ = afterType_.indexOf('(');
                paramOffest_ = typeOffset_+declaringType_.length() + leftParIndex_ + 1;
                String afterMethodName_ = afterType_.substring(leftParIndex_ + 1);
                paramOffest_ += StringUtil.getFirstPrintableCharIndex(afterMethodName_);
                info_ = afterMethodName_.trim();
            } else {
                paramOffest_ = modifierOffest_;
                int indexLeftPar_ = info_.indexOf(BEGIN_CALLING);
                paramOffest_ += indexLeftPar_ + 1;
                leftPar_ = paramOffest_;
                String after_ = info_.substring(indexLeftPar_ + 1);
                paramOffest_ += StringUtil.getFirstPrintableCharIndex(after_);
                info_ = after_.trim();
            }
            ParsedFctHeader parseHeader_ = new ParsedFctHeader();
            parseHeader_.parse(paramOffest_,info_,_offset, _page);
            info_ = parseHeader_.getInfo();
            Ints offestsTypes_ = parseHeader_.getOffestsTypes();
            Ints offestsParams_ = parseHeader_.getOffestsParams();
            StringList parametersType_ = parseHeader_.getParametersType();
            StringList parametersName_ = parseHeader_.getParametersName();
            CustList<Ints> annotationsIndexesParams_ = parseHeader_.getAnnotationsIndexesParams();
            CustList<StringList> annotationsParams_ = parseHeader_.getAnnotationsParams();
            boolean ok_ = parseHeader_.isOk();
            int offsetLast_ = parseHeader_.getOffsetLast();
            if (oper_) {
                String retType_ = declaringType_.trim();
                String trimMeth_ = methodName_.trim();
                MethodKind kind_;
                OverridableBlock ov_;
                kind_ = MethodKind.OPERATOR;
                ov_ = new OverridableBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                        new OffsetStringInfo(typeOffset_+_offset, retType_),
                        new OffsetStringInfo(methodNameOffest_+_offset, trimMeth_), parametersType_, offestsTypes_,
                        parametersName_, offestsParams_, new OffsetStringInfo(modifierOffest_+_offset, modifier_),
                        new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset), _page);
                ov_.setKind(kind_);
                _currentParent.getOverridableBlocks().add(ov_);
                int countFct_ = _currentParent.getCountFct();
                ov_.setNumberFct(countFct_);
                _currentParent.setCountFct(countFct_+1);
                int bodyFctNb_ = _currentParent.getCountBodyFct();
                ov_.setNumberBodyFct(bodyFctNb_);
                _currentParent.setCountBodyFct(bodyFctNb_+1);
                int countName_ = _currentParent.getCountName();
                ov_.setNameNumber(countName_);
                _currentParent.setCountName(countName_+1);
                int countOv_ = _currentParent.getCountOv();
                ov_.setNameOverrideNumber(countOv_);
                _currentParent.setCountOv(countOv_+1);
                br_ = ov_;
            } else if (meth_) {
                String retType_ = declaringType_.trim();
                String trimMeth_ = methodName_.trim();
                MethodKind kind_;
                OverridableBlock ov_;
                if (StringUtil.quickEq(trimMeth_, _page.getKeyWords().getKeyWordFalse())) {
                    kind_ = MethodKind.FALSE_OPERATOR;
                    ov_ = new OverridableBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                            new OffsetStringInfo(typeOffset_+_offset, retType_),
                            new OffsetStringInfo(methodNameOffest_+_offset, trimMeth_), parametersType_, offestsTypes_,
                            parametersName_, offestsParams_, new OffsetStringInfo(modifierOffest_+_offset, modifier_),
                            new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset), _page);
                } else if (StringUtil.quickEq(trimMeth_, _page.getKeyWords().getKeyWordTrue())) {
                    kind_ = MethodKind.TRUE_OPERATOR;
                    ov_ = new OverridableBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                            new OffsetStringInfo(typeOffset_+_offset, retType_),
                            new OffsetStringInfo(methodNameOffest_+_offset, trimMeth_), parametersType_, offestsTypes_,
                            parametersName_, offestsParams_, new OffsetStringInfo(modifierOffest_+_offset, modifier_),
                            new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset), _page);
                } else if (StringUtil.quickEq(trimMeth_, _page.getKeyWords().getKeyWordExplicit())) {
                    kind_ = MethodKind.EXPLICIT_CAST;
                    ov_ = new OverridableBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                            new OffsetStringInfo(typeOffset_+_offset, retType_),
                            new OffsetStringInfo(methodNameOffest_+_offset, trimMeth_), parametersType_, offestsTypes_,
                            parametersName_, offestsParams_, new OffsetStringInfo(modifierOffest_+_offset, modifier_),
                            new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset), _page);
                } else if (StringUtil.quickEq(trimMeth_, _page.getKeyWords().getKeyWordCast())) {
                    kind_ = MethodKind.IMPLICIT_CAST;
                    ov_ = new OverridableBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                            new OffsetStringInfo(typeOffset_+_offset, retType_),
                            new OffsetStringInfo(methodNameOffest_+_offset, trimMeth_), parametersType_, offestsTypes_,
                            parametersName_, offestsParams_, new OffsetStringInfo(modifierOffest_+_offset, modifier_),
                            new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset), _page);
                } else if (StringUtil.quickEq(trimMeth_, _page.getKeyWords().getKeyWordThis())) {
                    boolean get_ = !StringUtil.quickEq(retType_, _page.getAliasVoid());
                    if (!get_) {
                        kind_ = MethodKind.SET_INDEX;
                        trimMeth_ = "[]=";
                    } else {
                        kind_ = MethodKind.GET_INDEX;
                        trimMeth_ = "[]";
                    }
                    ov_ = new OverridableBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                            new OffsetStringInfo(typeOffset_+_offset, retType_),
                            new OffsetStringInfo(methodNameOffest_+_offset, trimMeth_), parametersType_, offestsTypes_,
                            parametersName_, offestsParams_, new OffsetStringInfo(modifierOffest_+_offset, modifier_),
                            new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset), _page);
                    ov_.setMatchParamNames(false);
                } else {
                    if (StringUtil.quickEq(trimMeth_, _page.getKeyWords().getKeyWordToString())
                            &&!StringUtil.quickEq(modifier_,keyWordStatic_)
                            &&!StringUtil.quickEq(modifier_,keyWordStaticCall_)
                            &&parametersType_.isEmpty()) {
                        kind_ = MethodKind.TO_STRING;
                    } else {
                        kind_ = MethodKind.STD_METHOD;
                    }
                    ov_ = new OverridableBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                            new OffsetStringInfo(typeOffset_+_offset, retType_),
                            new OffsetStringInfo(methodNameOffest_+_offset, trimMeth_), parametersType_, offestsTypes_,
                            parametersName_, offestsParams_, new OffsetStringInfo(modifierOffest_+_offset, modifier_),
                            new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset), _page);
                    ov_.setDefinition(info_);
                    ov_.setDefinitionOffset(offsetLast_);
                }
                ov_.setKind(kind_);
                _currentParent.getOverridableBlocks().add(ov_);
                int countFct_ = _currentParent.getCountFct();
                ov_.setNumberFct(countFct_);
                _currentParent.setCountFct(countFct_+1);
                int bodyFctNb_ = _currentParent.getCountBodyFct();
                ov_.setNumberBodyFct(bodyFctNb_);
                _currentParent.setCountBodyFct(bodyFctNb_+1);
                int countName_ = _currentParent.getCountName();
                ov_.setNameNumber(countName_);
                _currentParent.setCountName(countName_+1);
                int countOv_ = _currentParent.getCountOv();
                ov_.setNameOverrideNumber(countOv_);
                _currentParent.setCountOv(countOv_+1);
                br_ = ov_;
            } else {
                br_ = new ConstructorBlock(new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                        new OffsetStringInfo(accessOffest_+_offset, EMPTY_STRING),
                        new OffsetStringInfo(accessOffest_+_offset, EMPTY_STRING), parametersType_, offestsTypes_,
                        parametersName_, offestsParams_,leftPar_+_offset, new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
                ((ConstructorBlock)br_).setCtorName(ctorName_);
                int countFct_ = _currentParent.getCountFct();
                ((ConstructorBlock)br_).setNumberFct(countFct_);
                _currentParent.setCountFct(countFct_+1);
                int bodyFctNb_ = _currentParent.getCountBodyFct();
                ((ConstructorBlock)br_).setNumberBodyFct(bodyFctNb_);
                _currentParent.setCountBodyFct(bodyFctNb_+1);
                int countName_ = _currentParent.getCountName();
                ((ConstructorBlock)br_).setNameNumber(countName_);
                _currentParent.setCountName(countName_+1);
                if (parametersType_.isEmpty()) {
                    _currentParent.setEmptyCtor((ConstructorBlock) br_);
                }
            }
            if (!ok_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            ((NamedFunctionBlock)br_).getAnnotationsParams().addAllElts(annotationsParams_);
            ((NamedFunctionBlock)br_).getAnnotationsIndexesParams().addAllElts(annotationsIndexesParams_);
            ((NamedFunctionBlock)br_).getAnnotations().addAllElts(annotations_);
            ((NamedFunctionBlock)br_).getAnnotationsIndexes().addAllElts(annotationsIndexes_);
            _currentParent.appendChild(br_);
        } else {

            //fields
            int delta_ = StringUtil.getFirstPrintableCharIndex(found_) + word_.length();
            delta_ += deltaAccess_;
            delta_ += StringUtil.getFirstPrintableCharIndex(afterAccess_);
            String info_ = afterAccess_.trim();
            int staticOffest_ = -1;
            int finalOffest_ = -1;
            boolean static_ = false;
            boolean final_ = false;
            if (StringExpUtil.startsWithKeyWord(info_, keyWordStatic_)) {
                staticOffest_ = _i - found_.length() + delta_;
                static_ = true;
                String afterStatic_ = info_.substring(keyWordStatic_.length());
                delta_ += keyWordStatic_.length();
                delta_ += StringUtil.getFirstPrintableCharIndex(afterStatic_);
                info_ = afterStatic_.trim();
            }
            if (StringExpUtil.startsWithKeyWord(info_,keyWordFinal_)) {
                finalOffest_ = _i - found_.length() + delta_;
                final_ = true;
                String afterFinal_ = info_.substring(keyWordFinal_.length());
                delta_ += keyWordFinal_.length();
                delta_ += StringUtil.getFirstPrintableCharIndex(afterFinal_);
                info_ = afterFinal_.trim();
            }
            int typeOffest_ = _i - found_.length() + delta_;
            String declaringType_ = getFoundType(info_);
            String afterType_ = info_.substring(declaringType_.length());
            int fieldNameOffest_ = StringUtil.getFirstPrintableCharIndex(afterType_) +declaringType_.length() + typeOffest_;
            br_ = new FieldBlock(
                    new OffsetAccessInfo(accessOffest_+_offset, accessFct_),
                    new OffsetBooleanInfo(staticOffest_+_offset, static_), new OffsetBooleanInfo(finalOffest_+_offset, final_),
                    new OffsetStringInfo(typeOffest_+_offset,declaringType_.trim()),
                    new OffsetStringInfo(fieldNameOffest_+_offset, afterType_.trim()),
                    new OffsetsBlock(instructionRealLocation_+_offset, instructionLocation_+_offset));
            ((FieldBlock)br_).getAnnotations().addAllElts(annotations_);
            ((FieldBlock)br_).getAnnotationsIndexes().addAllElts(annotationsIndexes_);
            int fieldNb_ = _currentParent.getCountField();
            ((FieldBlock)br_).setFieldNumber(fieldNb_);
            _currentParent.setCountField(fieldNb_+1);
            _currentParent.appendChild(br_);
        }
        return br_;
    }

    private static StringBuilder fetchSymbol(String _afterModifier) {
        int len_ = _afterModifier.length();
        int j_ = 0;
        StringBuilder symbol_ = new StringBuilder();
        while (j_ < len_) {
            char currentChar_ = _afterModifier.charAt(j_);
            if (!isOperatorCharacter(currentChar_)) {
                //found space or import or return type
                break;
            }
            symbol_.append(currentChar_);
            j_++;
        }
        return symbol_;
    }

    private static Block processInstructionBlock(int _offset,
                                                 int _instructionLocation,
                                                 int _instructionRealLocation, int _i, BracedBlock _currentParent, String _trimmedInstruction, AnalyzedPageEl _page) {
        KeyWords keyWords_ = _page.getKeyWords();
        String keyWordBreak_ = keyWords_.getKeyWordBreak();
        String keyWordCase_ = keyWords_.getKeyWordCase();
        String keyWordCatch_ = keyWords_.getKeyWordCatch();
        String keyWordContinue_ = keyWords_.getKeyWordContinue();
        String keyWordDefault_ = keyWords_.getKeyWordDefault();
        String keyWordDo_ = keyWords_.getKeyWordDo();
        String keyWordElse_ = keyWords_.getKeyWordElse();
        String keyWordElseif_ = keyWords_.getKeyWordElseif();
        String keyWordFinal_ = keyWords_.getKeyWordFinal();
        String keyWordFinally_ = keyWords_.getKeyWordFinally();
        String keyWordFor_ = keyWords_.getKeyWordFor();
        String keyWordForeach_ = keyWords_.getKeyWordForeach();
        String keyWordIf_ = keyWords_.getKeyWordIf();
        String keyWordIter_ = keyWords_.getKeyWordIter();
        String keyWordReturn_ = keyWords_.getKeyWordReturn();
        String keyWordStatic_ = keyWords_.getKeyWordStatic();
        String keyWordSwitch_ = keyWords_.getKeyWordSwitch();
        String keyWordThrow_ = keyWords_.getKeyWordThrow();
        String keyWordTry_ = keyWords_.getKeyWordTry();
        String keyWordWhile_ = keyWords_.getKeyWordWhile();
        Block br_ = null;
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordBreak_)) {
            String exp_ = _trimmedInstruction.substring(keyWordBreak_.length());
            String label_ = exp_.trim();
            int conditionOffest_ = _instructionLocation + keyWordBreak_.length();
            int lastPar_ = StringUtil.getFirstPrintableCharIndex(exp_);
            if (!exp_.isEmpty()) {
                lastPar_--;
            }
            int labelOff_ = conditionOffest_ + lastPar_+ 1;
            br_ = new BreakBlock(new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordBreak_.length());
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordContinue_)) {
            String exp_ = _trimmedInstruction.substring(keyWordContinue_.length());
            String label_ = exp_.trim();
            int conditionOffest_ = _instructionLocation + keyWordContinue_.length();
            int lastPar_ = StringUtil.getFirstPrintableCharIndex(exp_);
            if (!exp_.isEmpty()) {
                lastPar_--;
            }
            int labelOff_ = conditionOffest_ + lastPar_+ 1;
            br_ = new ContinueBlock(new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordContinue_.length());
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordReturn_)) {
            String exp_ = _trimmedInstruction.substring(keyWordReturn_.length());
            int expressionOffest_ = _instructionLocation + keyWordReturn_.length();
            if (!exp_.trim().isEmpty()) {
                expressionOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
            }
            br_ = new ReturnMethod(new OffsetStringInfo(expressionOffest_+_offset,exp_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordReturn_.length());
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordThrow_)) {
            String exp_ = _trimmedInstruction.substring(keyWordThrow_.length());
            int expressionOffest_ = _instructionLocation + keyWordThrow_.length();
            if (!exp_.trim().isEmpty()) {
                expressionOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
            }
            br_ = new Throwing(new OffsetStringInfo(expressionOffest_+_offset,exp_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordThrow_.length());
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordCase_)) {
            String exp_ = _trimmedInstruction.substring(keyWordCase_.length());
            int valueOffest_ = _instructionLocation + keyWordCase_.length();
            if (!exp_.trim().isEmpty()) {
                valueOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
            }
            br_ = new CaseCondition(
                    new OffsetStringInfo(valueOffest_+_offset, exp_.trim()),
                    new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            //if next after i starts with brace or not
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordCase_.length());
            return br_;
        }
        if (StringUtil.quickEq(_trimmedInstruction,keyWordDefault_)) {
            br_ = new DefaultCondition(
                    new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordDefault_.length());
            return br_;
        }
        if (startsWithDefVar(_trimmedInstruction, keyWordDefault_)) {
            String exp_ = _trimmedInstruction.substring(keyWordDefault_.length());
            int valueOffest_ = _instructionLocation + keyWordDefault_.length();
            valueOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
            br_ = new DefaultCondition(
                    new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset),exp_.trim(),valueOffest_+_offset);
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordDefault_.length());
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordWhile_)) {
            Block child_ = _currentParent.getFirstChild();
            if (child_ != null) {
                while (child_.getNextSibling() != null) {
                    child_ = child_.getNextSibling();
                }
            }
            String exp_ = _trimmedInstruction.substring(keyWordWhile_.length());
            int conditionOffest_ = _instructionLocation + keyWordWhile_.length();
            int lastPar_ = exp_.lastIndexOf(END_CALLING);
            int labelOff_ = conditionOffest_ + lastPar_+ 1;
            int beg_ = exp_.indexOf(BEGIN_CALLING);
            String label_ = exp_;
            boolean ok_ = false;
            if (beg_ >= 0 && lastPar_ >= beg_ + 1) {
                conditionOffest_ += beg_ +1;
                exp_ = exp_.substring(beg_ +1, lastPar_);
                ok_ = true;
            }
            conditionOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
            if (child_ instanceof DoBlock) {
                br_ = new DoWhileCondition(new OffsetStringInfo(conditionOffest_+_offset, exp_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            } else {
                label_ = label_.substring(lastPar_ + 1);
                if (!label_.isEmpty()) {
                    labelOff_ += StringUtil.getFirstPrintableCharIndex(label_);
                }
                br_ = new WhileCondition(new OffsetStringInfo(conditionOffest_+_offset, exp_.trim()),
                        new OffsetStringInfo(labelOff_+_offset, label_.trim()),
                        new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            }
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordWhile_.length());
            if (!ok_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            ((Condition)br_).setTestOffset(_i+_offset);
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordCatch_)) {
            String info_ = _trimmedInstruction.substring(keyWordCatch_.length());
            int leftPar_ = info_.indexOf(BEGIN_CALLING);
            if (leftPar_ > -1) {
                int typeOffset_ = keyWordCatch_.length() + _instructionLocation + leftPar_+1;
                info_ = info_.substring(leftPar_+1);
                String declaringType_ = getFoundType(info_);
                typeOffset_ += StringUtil.getFirstPrintableCharIndex(declaringType_);
                int variableOffset_ = typeOffset_ + declaringType_.length();
                info_ = info_.substring(declaringType_.length());
                variableOffset_ += StringUtil.getFirstPrintableCharIndex(info_);
                int endIndex_ = info_.indexOf(END_CALLING);
                String variable_ = "";
                boolean ok_ = false;
                if (endIndex_ >= 0) {
                    variable_ = info_.substring(0, endIndex_);
                    ok_ = true;
                }
                br_ = new CatchEval(new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()),
                        new OffsetStringInfo(variableOffset_+_offset,variable_.trim()),
                        new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
                if (!ok_) {
                    br_.getBadIndexes().add(_i);
                }
            } else {
                br_ = new NullCatchEval(new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            }
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordCatch_.length());
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordIf_)) {
            String exp_ = _trimmedInstruction.substring(keyWordIf_.length());
            int conditionOffest_ = _instructionLocation + keyWordIf_.length();
            int lastPar_ = exp_.lastIndexOf(END_CALLING);
            int labelOff_ = conditionOffest_ + lastPar_+ 1;
            int beg_ = exp_.indexOf(BEGIN_CALLING);
            boolean ok_ = false;
            String label_ = exp_;
            if (beg_ >= 0 && lastPar_ >= beg_ + 1) {
                conditionOffest_ += beg_ +1;
                exp_ = exp_.substring(beg_ +1,lastPar_);
                conditionOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
                ok_ = true;
            }
            label_ = label_.substring(lastPar_ + 1);
            if (!label_.isEmpty()) {
                labelOff_ += StringUtil.getFirstPrintableCharIndex(label_);
            }
            br_ = new IfCondition(new OffsetStringInfo(conditionOffest_+_offset, exp_.trim()),
                    new OffsetStringInfo(labelOff_+_offset, label_.trim()),
                    new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            if (!ok_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            ((Condition)br_).setTestOffset(_i+_offset);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordIf_.length());
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordElseif_)) {
            String exp_ = _trimmedInstruction.substring(keyWordElseif_.length());
            int conditionOffest_ = _instructionLocation + keyWordElseif_.length();
            int beg_ = exp_.indexOf(BEGIN_CALLING);
            conditionOffest_ += beg_ +1;
            int lastPar_ = exp_.lastIndexOf(END_CALLING);
            boolean ok_ = false;
            if (beg_ >= 0 && lastPar_ >= beg_ + 1) {
                exp_ = exp_.substring(beg_ +1, lastPar_);
                conditionOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
                ok_ = true;
            }
            br_ = new ElseIfCondition(new OffsetStringInfo(conditionOffest_+_offset, exp_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset),keyWordElseif_.length());
            if (!ok_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            ((Condition)br_).setTestOffset(_i+_offset);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordElseif_.length());
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordElse_)) {
            String afterElse_ = _trimmedInstruction.substring(keyWordElse_.length());
            String exp_ = afterElse_.trim();
            if (StringExpUtil.startsWithKeyWord(exp_,keyWordIf_)) {
                int deltaFirst_ = keyWordElse_.length();
                int firstPr_ = StringUtil.getFirstPrintableCharIndex(afterElse_);
                deltaFirst_ += firstPr_;
                deltaFirst_ += keyWordIf_.length();
                exp_ = exp_.substring(keyWordIf_.length());
                int conditionOffest_ = _instructionLocation;
                conditionOffest_ += keyWordElse_.length();
                conditionOffest_ += firstPr_;
                conditionOffest_ += keyWordIf_.length();
                int beg_ = exp_.indexOf(BEGIN_CALLING);
                conditionOffest_ += beg_ +1;
                int lastPar_ = exp_.lastIndexOf(END_CALLING);
                boolean ok_ = false;
                if (beg_ >= 0 && lastPar_ >= beg_ + 1) {
                    exp_ = exp_.substring(beg_ +1, lastPar_);
                    conditionOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
                    ok_ = true;
                }
                br_ = new ElseIfCondition(new OffsetStringInfo(conditionOffest_+_offset, exp_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset),deltaFirst_);
                if (!ok_) {
                    br_.getBadIndexes().add(_i+_offset);
                }
                ((Condition)br_).setTestOffset(_i+_offset);
                br_.setBegin(_instructionLocation+deltaFirst_-keyWordIf_.length()+_offset);
                br_.setLengthHeader(keyWordIf_.length());
                _currentParent.appendChild(br_);
            } else {
                br_ = new ElseCondition(new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
                br_.setBegin(_instructionLocation+_offset);
                br_.setLengthHeader(keyWordElse_.length());
                _currentParent.appendChild(br_);
            }
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordDo_)) {
            String exp_ = _trimmedInstruction.substring(keyWordDo_.length());
            String label_ = exp_.trim();
            int conditionOffest_ = _instructionLocation + keyWordDo_.length();
            int lastPar_ = StringUtil.getFirstPrintableCharIndex(exp_);
            if (!exp_.isEmpty()) {
                lastPar_--;
            }
            int labelOff_ = conditionOffest_ + lastPar_+ 1;
            br_ = new DoBlock(new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordDo_.length());
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordFinally_)) {
            br_ = new FinallyEval(new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordFinally_.length());
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordTry_)) {
            String exp_ = _trimmedInstruction.substring(keyWordTry_.length());
            String label_ = exp_.trim();
            int conditionOffest_ = _instructionLocation + keyWordTry_.length();
            int lastPar_ = StringUtil.getFirstPrintableCharIndex(exp_);
            if (!exp_.isEmpty()) {
                lastPar_--;
            }
            int labelOff_ = conditionOffest_ + lastPar_+ 1;
            br_ = new TryEval(new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordTry_.length());
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordForeach_)) {
            String exp_ = _trimmedInstruction.substring(keyWordForeach_.length());
            int indexClassOffest_ = _instructionLocation + keyWordForeach_.length();
            int lastPar_ = exp_.lastIndexOf(END_CALLING);
            int labelOff_ = indexClassOffest_ + lastPar_+ 1;
            String label_ = exp_;
            label_ = label_.substring(lastPar_ + 1);
            if (!label_.isEmpty()) {
                labelOff_ += StringUtil.getFirstPrintableCharIndex(label_);
            }
            int typeOffset_ = _instructionLocation + _trimmedInstruction.indexOf(BEGIN_CALLING) + 1;
            if (!exp_.trim().isEmpty()) {
                indexClassOffest_ += StringUtil.getFirstPrintableCharIndex(exp_) + 1;
            }
            boolean ok_ = true;
            String indexClassName_ = EMPTY_STRING;
            if (exp_.trim().indexOf(BEGIN_ARRAY) == 0) {
                int endArr_ = exp_.indexOf(END_ARRAY);
                if (endArr_ < 0) {
                    ok_ = false;
                } else {
                    indexClassName_ = exp_.substring(0, endArr_);
                    indexClassOffest_ += StringUtil.getFirstPrintableCharIndex(indexClassName_);
                    exp_ = exp_.substring(endArr_ + 1);
                }
            }
            String afterIndex_ = exp_.substring(exp_.indexOf(BEGIN_CALLING) + 1);
            typeOffset_ += StringUtil.getFirstPrintableCharIndex(afterIndex_);
            exp_ = afterIndex_;
            String declaringType_ = getFoundType(exp_);
            int varOffset_ = typeOffset_ + declaringType_.length();
            exp_ = exp_.substring(declaringType_.length());
            int forBlocks_ = exp_.indexOf(FOR_BLOCKS);
            int endIndex_ = exp_.lastIndexOf(END_CALLING);
            String variable_ = "";
            int expOffset_ = varOffset_;
            int setOff_ = expOffset_-1;
            if (forBlocks_ < 0 || endIndex_ < forBlocks_ + 1) {
                ok_ = false;
            } else {
                variable_ = exp_.substring(0, forBlocks_);
                varOffset_ += StringUtil.getFirstPrintableCharIndex(variable_);
                expOffset_ = varOffset_;
                expOffset_ += forBlocks_;
                setOff_ = expOffset_-1;
                exp_ = exp_.substring(forBlocks_ + 1, endIndex_);
                expOffset_ += StringUtil.getFirstPrintableCharIndex(exp_);
            }
            String variableName_ = variable_.trim();
            if (StringExpUtil.isTypeLeafPart(variableName_)) {
                br_ = new ForEachLoop(new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()),
                        new OffsetStringInfo(varOffset_+_offset, variableName_),
                        new OffsetStringInfo(expOffset_+_offset, exp_.trim()), new OffsetStringInfo(indexClassOffest_+_offset, indexClassName_.trim()),
                        new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset),setOff_+_offset, _page);
            } else {
                int nextIndexVar_ = variableName_.indexOf(',');
                String firstVar_ = "";
                String afterFirst_ = "";
                if (nextIndexVar_ < 0) {
                    ok_ = false;
                } else {
                    firstVar_ = variableName_.substring(0, nextIndexVar_);
                    afterFirst_ = variableName_.substring(nextIndexVar_+1);
                }
                String declaringTypeSec_ = getFoundType(afterFirst_);
                int secType_ = varOffset_;
                secType_ += nextIndexVar_+1;
                int secVarOff_ = secType_;
                secType_ += StringUtil.getFirstPrintableCharIndex(declaringTypeSec_);
                secVarOff_ += declaringTypeSec_.length();
                String padSecVar_= afterFirst_.substring(declaringTypeSec_.length());
                secVarOff_ += StringUtil.getFirstPrintableCharIndex(padSecVar_);
                String secVar_ = padSecVar_.trim();
                br_ = new ForEachTable(
                        new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()), new OffsetStringInfo(varOffset_+_offset, firstVar_),
                        new OffsetStringInfo(secType_+_offset, declaringTypeSec_.trim()), new OffsetStringInfo(secVarOff_+_offset, secVar_),
                        new OffsetStringInfo(expOffset_+_offset, exp_.trim()), new OffsetStringInfo(indexClassOffest_+_offset, indexClassName_.trim()),
                        new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset),setOff_+_offset, _page);
            }
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordForeach_.length());
            if (!ok_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordIter_)) {
            String exp_ = _trimmedInstruction.substring(keyWordIter_.length());
            int indexClassOffest_ = _instructionLocation + keyWordIter_.length();
            int lastPar_ = exp_.lastIndexOf(END_CALLING);
            int labelOff_ = indexClassOffest_ + lastPar_+ 1;
            String label_ = exp_;
            int typeOffset_ = _instructionLocation + _trimmedInstruction.indexOf(BEGIN_CALLING) + 1;
            if (!exp_.trim().isEmpty()) {
                indexClassOffest_ += StringUtil.getFirstPrintableCharIndex(exp_) + 1;
            }
            String indexClassName_ = EMPTY_STRING;
            boolean ok_ = true;
            if (exp_.trim().indexOf(BEGIN_ARRAY) == 0) {
                int endArr_ = exp_.indexOf(END_ARRAY);
                if (endArr_ < 0) {
                    ok_ = false;
                } else {
                    indexClassName_ = exp_.substring(0, endArr_);
                    exp_ = exp_.substring(endArr_ + 1);
                }
            }
            int begCall_ = exp_.indexOf(BEGIN_CALLING);
            int endIndex_ = exp_.lastIndexOf(END_CALLING);
            if (begCall_ < 0 || endIndex_ < begCall_ + 1) {
                ok_ = false;
            } else {
                exp_ = exp_.substring(begCall_ + 1, endIndex_);
            }
            String declaringType_ = getFoundType(exp_);
            int varOffset_ = typeOffset_ + declaringType_.length();
            typeOffset_ += StringExpUtil.getOffset(exp_);
            exp_ = exp_.substring(declaringType_.length());
            int eqIndex_ = exp_.indexOf(PART_SEPARATOR);
            String variable_ = "";
            int firstOff_ = 0;
            if (eqIndex_ < 0) {
                ok_ = false;
            } else {
                variable_ = exp_.substring(0, eqIndex_);
                firstOff_ = StringExpUtil.getOffset(variable_);
                varOffset_ += firstOff_;
                exp_ = exp_.substring(eqIndex_ + 1);
            }
            int nextElt_ = getIndex(exp_);
            int initOff_ = varOffset_ + variable_.length()-firstOff_+1;
            String init_ = "";
            int secondOff_ = 0;
            if (nextElt_ < 0) {
                ok_ = false;
            } else {
                init_ = exp_.substring(0, nextElt_);
                secondOff_ = StringExpUtil.getOffset(init_);
                initOff_ += secondOff_;
                exp_ = exp_.substring(nextElt_+1);
            }
            nextElt_ = getIndex(exp_);
            int toOff_ = initOff_ + init_.length()-secondOff_+1;
            String to_ = "";
            int thirdOff_ = 0;
            if (nextElt_ < 0) {
                ok_ = false;
            } else {
                to_ = exp_.substring(0, nextElt_);
                thirdOff_ = StringExpUtil.getOffset(to_);
                toOff_ += thirdOff_;
            }
            boolean eq_ = false;
            int expOff_ = toOff_ + to_.length()-thirdOff_;
            int stepOff_ = expOff_ + 1;
            if (nextElt_ + 1 >= exp_.length()) {
                ok_ = false;
                stepOff_--;
            } else {
                if (exp_.charAt(nextElt_ + 1) == END_LINE) {
                    eq_ = true;
                    nextElt_++;
                    stepOff_++;
                }
                exp_ = exp_.substring(nextElt_ + 1);
            }
            String step_ = exp_;
            stepOff_ += StringExpUtil.getOffset(step_);
            label_ = label_.substring(lastPar_ + 1);
            if (!label_.isEmpty()) {
                labelOff_ += StringUtil.getFirstPrintableCharIndex(label_);
            }
            br_ = new ForIterativeLoop(new OffsetStringInfo(typeOffset_+_offset,declaringType_.trim()), new OffsetStringInfo(varOffset_+_offset,variable_.trim()),
                    new OffsetStringInfo(initOff_+_offset,init_.trim()), new OffsetStringInfo(toOff_+_offset,to_.trim()),
                    new OffsetBooleanInfo(expOff_+_offset, eq_) , new OffsetStringInfo(stepOff_+_offset,step_.trim()), new OffsetStringInfo(indexClassOffest_+_offset,indexClassName_.trim()),
                    new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset), _page);
            if (!ok_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordIter_.length());
            _currentParent.appendChild(br_);
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordFor_)) {
            String exp_ = _trimmedInstruction.substring(keyWordFor_.length());
            int indexClassOffest_ = _instructionLocation + keyWordFor_.length();
            int lastPar_ = exp_.lastIndexOf(END_CALLING);
            int labelOff_ = indexClassOffest_ + lastPar_+ 1;
            String label_ = exp_;
            int typeOffset_ = _instructionLocation + _trimmedInstruction.indexOf(BEGIN_CALLING) + 1;
            if (!exp_.trim().isEmpty()) {
                indexClassOffest_ += StringUtil.getFirstPrintableCharIndex(exp_) + 1;
            }
            String indexClassName_ = EMPTY_STRING;
            boolean okIndex_ = true;
            if (exp_.trim().indexOf(BEGIN_ARRAY) == 0) {
                int endArr_ = exp_.indexOf(END_ARRAY);
                if (endArr_ < 0) {
                    okIndex_ = false;
                } else {
                    indexClassName_ = exp_.substring(0, endArr_);
                    exp_ = exp_.substring(endArr_ + 1);
                }
            }
            int begCall_ = exp_.indexOf(BEGIN_CALLING);
            int endCall_ = exp_.lastIndexOf(END_CALLING);
            if (begCall_ < 0 || endCall_ < begCall_ + 1) {
                okIndex_ = false;
            } else {
                exp_ = exp_.substring(begCall_ + 1, endCall_);
            }
            boolean finalLocalVar_ = StringExpUtil.startsWithKeyWord(exp_.trim(), keyWordFinal_);
            int finalOffset_ = typeOffset_;
            int deltaAfter_;
            if (finalLocalVar_) {
                int delta_ = StringUtil.getFirstPrintableCharIndex(exp_) + keyWordFinal_.length();
                deltaAfter_ = delta_;
                String afterDelta_ = exp_.substring(delta_);
                deltaAfter_ += StringExpUtil.getOffset(afterDelta_);
            } else {
                deltaAfter_ = StringExpUtil.getOffset(exp_);
            }
            typeOffset_ += deltaAfter_;
            exp_ = exp_.substring(deltaAfter_);
            String declaringType_ = getDeclaringTypeInstr(exp_,keyWords_);
            exp_ = exp_.substring(declaringType_.length());
            boolean ok_ = false;
            int nextEltMut_ = getIndex(exp_);
            String expAfterType_ = exp_;
            if (nextEltMut_ > -1) {
                int initOff_ = typeOffset_ + declaringType_.length();
                String init_ = exp_.substring(0, nextEltMut_);
                int off_ = StringExpUtil.getOffset(init_);
                int toOff_ = initOff_ + nextEltMut_+1;
                initOff_ += off_;
                exp_ = exp_.substring(nextEltMut_+1);
                int nextElt_ = getIndex(exp_);
                if (nextElt_ > -1) {
                    String to_ = exp_.substring(0, nextElt_);
                    int offTwo_ = StringExpUtil.getOffset(to_);
                    int stepOff_ = toOff_ + nextElt_+1;
                    toOff_ += offTwo_;
                    exp_ = exp_.substring(nextElt_ + 1);
                    String step_ = exp_;
                    stepOff_ += StringExpUtil.getOffset(step_);
                    label_ = label_.substring(lastPar_ + 1);
                    labelOff_ += getLabelOffset(label_);
                    br_ = new ForMutableIterativeLoop(
                            new OffsetBooleanInfo(finalOffset_+_offset, finalLocalVar_),
                            new OffsetStringInfo(typeOffset_+_offset,declaringType_.trim()),
                            new OffsetStringInfo(initOff_+_offset,init_.trim()), new OffsetStringInfo(toOff_+_offset,to_.trim()),
                            new OffsetStringInfo(stepOff_+_offset,step_.trim()), new OffsetStringInfo(indexClassOffest_+_offset,indexClassName_.trim()),
                            new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset), _page);
                    _currentParent.appendChild(br_);
                    ((ForMutableIterativeLoop) br_).setTestOffset(_i+_offset);
                    ok_ = true;
                }
            }
            if (!ok_) {
                int nextElt_ = expAfterType_.indexOf(FOR_BLOCKS);
                if (nextElt_ > -1) {
                    int expOffset_ = typeOffset_ + declaringType_.length();
                    expOffset_ += nextElt_+1;
                    int setOff_ = expOffset_-1;
                    String init_ = expAfterType_.substring(0, nextElt_);
                    exp_ = expAfterType_.substring(nextElt_+1);
                    expOffset_ += StringExpUtil.getOffset(exp_);
                    String variableName_ = init_.trim();
                    label_ = label_.substring(lastPar_ + 1);
                    labelOff_ += getLabelOffset(label_);
                    if (StringExpUtil.isTypeLeafPart(variableName_)) {
                        int varOffset_ = typeOffset_ + declaringType_.length();
                        varOffset_ += StringUtil.getFirstPrintableCharIndex(init_);
                        br_ = new ForEachLoop(new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()),
                                new OffsetStringInfo(varOffset_+_offset, variableName_), new OffsetStringInfo(expOffset_+_offset, exp_.trim()),
                                new OffsetStringInfo(indexClassOffest_+_offset, indexClassName_.trim()),
                                new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset),setOff_+_offset, _page);
                        _currentParent.appendChild(br_);
                    } else {
                        int nextIndexVar_ = variableName_.indexOf(',');
                        if (nextIndexVar_ >= 0) {
                            String firstVar_ = variableName_.substring(0, nextIndexVar_);
                            String afterFirst_ = variableName_.substring(nextIndexVar_+1);
                            String declaringTypeSec_ = getFoundType(afterFirst_);
                            String padSecVar_= afterFirst_.substring(declaringTypeSec_.length());
                            String secVar_ = padSecVar_.trim();
                            if (StringExpUtil.isTypeLeafPart(secVar_)) {
                                int varOffset_ = typeOffset_ + declaringType_.length();
                                varOffset_ += StringUtil.getFirstPrintableCharIndex(init_);
                                int secType_ = varOffset_;
                                secType_ += nextIndexVar_+1;
                                int secVarOff_ = secType_;
                                secType_ += StringExpUtil.getOffset(declaringTypeSec_);
                                secVarOff_ += declaringTypeSec_.length();
                                secVarOff_ += StringUtil.getFirstPrintableCharIndex(padSecVar_);
                                br_ = new ForEachTable(
                                        new OffsetStringInfo(typeOffset_+_offset, declaringType_.trim()), new OffsetStringInfo(varOffset_+_offset, firstVar_.trim()),
                                        new OffsetStringInfo(secType_+_offset, declaringTypeSec_.trim()), new OffsetStringInfo(secVarOff_+_offset, secVar_),
                                        new OffsetStringInfo(expOffset_+_offset, exp_.trim()), new OffsetStringInfo(indexClassOffest_+_offset, indexClassName_.trim()),
                                        new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset),setOff_+_offset, _page);
                                _currentParent.appendChild(br_);
                            }
                        }
                    }
                }
            }
            if (br_ == null) {
                br_ = new UnclassedBracedBlock(new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
                _currentParent.appendChild(br_);
                br_.getBadIndexes().add(_i+_offset);
            }
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordFor_.length());
            if (!okIndex_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            return br_;
        }
        if (StringExpUtil.startsWithKeyWord(_trimmedInstruction,keyWordSwitch_)) {
            String exp_ = _trimmedInstruction.substring(keyWordSwitch_.length());
            int valueOffest_ = _instructionLocation + keyWordSwitch_.length();
            int lastPar_ = exp_.lastIndexOf(END_CALLING);
            int labelOff_ = valueOffest_ + lastPar_+ 1;
            int afterLeftPar_ = exp_.indexOf(BEGIN_CALLING) + 1;
            valueOffest_ += afterLeftPar_;
            String label_ = exp_;
            boolean ok_ = false;
            if (afterLeftPar_ <= lastPar_) {
                exp_ = exp_.substring(afterLeftPar_, lastPar_);
                valueOffest_ += StringUtil.getFirstPrintableCharIndex(exp_);
                ok_ = true;
            }
            label_ = label_.substring(lastPar_ + 1);
            if (!label_.isEmpty()) {
                labelOff_ += StringUtil.getFirstPrintableCharIndex(label_);
            }
            br_ = new SwitchBlock(new OffsetStringInfo(valueOffest_+_offset, exp_.trim()), new OffsetStringInfo(labelOff_+_offset, label_.trim()), new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            if (!ok_) {
                br_.getBadIndexes().add(_i+_offset);
            }
            _currentParent.appendChild(br_);
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordSwitch_.length());
            return br_;
        }
        if (StringUtil.quickEq(_trimmedInstruction, keyWordStatic_)) {
            br_ = new StaticBlock(new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            _currentParent.appendChild(br_);
            if (_currentParent instanceof RootBlock) {
                int initNb_ = ((RootBlock)_currentParent).getCountInit();
                ((InitBlock) br_).setNumber(initNb_);
                ((RootBlock)_currentParent).setCountInit(initNb_+1);
                int fctNb_ = ((RootBlock)_currentParent).getCountFct();
                ((InitBlock) br_).setNumberFct(fctNb_);
                ((RootBlock)_currentParent).setCountFct(fctNb_+1);
                int bodyFctNb_ = ((RootBlock)_currentParent).getCountBodyFct();
                ((InitBlock) br_).setNumberBodyFct(bodyFctNb_);
                ((RootBlock)_currentParent).setCountBodyFct(bodyFctNb_+1);
            }
            br_.setBegin(_instructionLocation+_offset);
            br_.setLengthHeader(keyWordStatic_.length());
            return br_;
        }
        if (_trimmedInstruction.isEmpty()) {
            if (_currentParent instanceof RootBlock) {
                br_ = new InstanceBlock(new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
                int initNb_ = ((RootBlock)_currentParent).getCountInit();
                ((InitBlock) br_).setNumber(initNb_);
                ((RootBlock)_currentParent).setCountInit(initNb_+1);
                int fctNb_ = ((RootBlock)_currentParent).getCountFct();
                ((InitBlock) br_).setNumberFct(fctNb_);
                ((RootBlock)_currentParent).setCountFct(fctNb_+1);
                int bodyFctNb_ = ((RootBlock)_currentParent).getCountBodyFct();
                ((InitBlock) br_).setNumberBodyFct(bodyFctNb_);
                ((RootBlock)_currentParent).setCountBodyFct(bodyFctNb_+1);
            } else {
                br_ = new UnclassedBracedBlock(new OffsetsBlock(_instructionRealLocation+_offset, _instructionLocation+_offset));
            }
            br_.setBegin(_i+_offset);
            br_.setLengthHeader(1);
            _currentParent.appendChild(br_);
        }
        //Not an error
        return br_;
    }

    private static boolean startsWithDefVar(String _trimmedInstruction, String _keyWordDefault) {
        return StringExpUtil.startsWithKeyWord(_trimmedInstruction, _keyWordDefault)&&StringExpUtil.isTypeLeafPart(_trimmedInstruction.substring(_keyWordDefault.length()).trim());
    }

    private static int getLabelOffset(String _label) {
        if (_label.isEmpty()) {
            return 0;
        }
        return StringUtil.getFirstPrintableCharIndex(_label);
    }

    static String getFoundType(String _found) {
        ParsedType p_ = new ParsedType();
        p_.parse(_found);
        return p_.getInstruction().toString();
    }

    private static boolean isOperatorCharacter(char _char) {
        if (_char == '+') {
            return true;
        }
        if (_char == '-') {
            return true;
        }
        if (_char == '*') {
            return true;
        }
        if (_char == '%') {
            return true;
        }
        if (_char == '/') {
            return true;
        }
        if (_char == '!') {
            return true;
        }
        if (_char == '=') {
            return true;
        }
        if (_char == '<') {
            return true;
        }
        if (_char == '>') {
            return true;
        }
        if (_char == '&') {
            return true;
        }
        if (_char == '|') {
            return true;
        }
        if (_char == '^') {
            return true;
        }
        return _char == '~';
    }

    private static String getDeclaringTypeInstr(String _found, KeyWords _options) {
        String keyWordNew_ = _options.getKeyWordNew();
        ParsedType p_ = new ParsedType();
        p_.parse(_found);
        if (p_.isOk(new CustList<String>(keyWordNew_))) {
            return p_.getInstruction().toString();
        }
        return "";
    }
    private static int getIndex(String _info) {
        int indexInstr_ = 0;
        int instrLen_ = _info.length();
        int localCallings_ = 0;
        boolean localConstChar_ = false;
        boolean localConstString_ = false;
        boolean localConstText_ = false;
        while (indexInstr_ < instrLen_) {
            char locChar_ = _info.charAt(indexInstr_);
            if (localConstChar_) {
                if (locChar_ == ESCAPE) {
                    indexInstr_++;
                    indexInstr_++;
                    continue;
                }
                if (locChar_ == DEL_CHAR) {
                    indexInstr_++;
                    localConstChar_ = false;
                    continue;
                }
                indexInstr_++;
                continue;
            }
            if (localConstString_) {
                if (locChar_ == ESCAPE) {
                    indexInstr_++;
                    indexInstr_++;
                    continue;
                }
                if (locChar_ == DEL_STRING) {
                    indexInstr_++;
                    localConstString_ = false;
                    continue;
                }
                indexInstr_++;
                continue;
            }
            if (localConstText_) {
                if (locChar_ == DEL_TEXT) {
                    if (indexInstr_ + 1 >= instrLen_ ||_info.charAt(indexInstr_ + 1) != DEL_TEXT) {
                        indexInstr_++;
                        localConstText_ = false;
                        continue;
                    }
                    indexInstr_++;
                }
                indexInstr_++;
                continue;
            }
            if (localCallings_ == 0 && locChar_ == END_LINE) {
                return indexInstr_;
            }
            if (locChar_ == DEL_CHAR) {
                localConstChar_ = true;
            }
            if (locChar_ == DEL_STRING) {
                localConstString_ = true;
            }
            if (locChar_ == DEL_TEXT) {
                localConstText_ = true;
            }
            if (locChar_ == BEGIN_CALLING) {
                localCallings_++;
            }
            if (locChar_ == END_CALLING) {
                localCallings_--;
            }
            if (locChar_ == BEGIN_ARRAY) {
                localCallings_++;
            }
            if (locChar_ == END_ARRAY) {
                localCallings_--;
            }
            if (locChar_ == BEGIN_BLOCK) {
                localCallings_++;
            }
            if (locChar_ == END_BLOCK) {
                localCallings_--;
            }
            indexInstr_++;
        }
        return -1;
    }

    static String getEndCom(String _file, int _i, CommentDelimiters _current) {
        String endCom_ = "";
        for (String e: _current.getEnd()) {
            if (_file.startsWith(e, _i)) {
                endCom_ = e;
                break;
            }
        }
        return endCom_;
    }
}
