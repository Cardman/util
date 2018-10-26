package code.expressionlanguage;

import code.util.Numbers;
import code.util.StringList;

public final class ParsedAnnotations {

    private static final char BEGIN_BLOCK = '{';
    private static final char END_BLOCK = '}';
    private static final char BEGIN_CALLING = '(';
    private static final char END_CALLING = ')';
    private static final char DEL_CHAR = '\'';
    private static final char DEL_STRING = '"';
    private static final char ESCAPE = '\\';
    private static final char ANNOT = '@';
    private Numbers<Integer> annotationsIndexes = new Numbers<Integer>();
    private StringList annotations = new StringList();
    private String instruction = "";
    private String after = "";
    private int index;
    private int instructionLocation;
    private int beforeAnnot;
    public ParsedAnnotations(String _instruction, int _instructionLocation) {
        instruction = _instruction;
        instructionLocation = _instructionLocation;
    }
    public void parse() {
        int lenInst_ = instruction.length();
        int j_ = 0;
        int nbPars_ = 0;
        StringBuilder annotation_ = new StringBuilder();
        boolean quoted_ = false;
        boolean quotedChar_ = false;
        while (j_ < lenInst_) {
            char cur_ = instruction.charAt(j_);
            if (quotedChar_) {
                annotation_.append(cur_);
                if (cur_ == ESCAPE) {
                    j_++;
                    annotation_.append(instruction.charAt(j_));
                    j_++;
                    continue;
                }
                if (cur_ == DEL_CHAR) {
                    quotedChar_ = false;
                }
                j_++;
                continue;
            }
            if (quoted_) {
                annotation_.append(cur_);
                if (cur_ == ESCAPE) {
                    j_++;
                    annotation_.append(instruction.charAt(j_));
                    j_++;
                    continue;
                }
                if (cur_ == DEL_STRING) {
                    quoted_ = false;
                }
                j_++;
                continue;
            }
            if (cur_ == DEL_CHAR) {
                annotation_.append(cur_);
                quotedChar_ = true;
                j_++;
                continue;
            }
            if (cur_ == DEL_STRING) {
                annotation_.append(cur_);
                quoted_ = true;
                j_++;
                continue;
            }
            if (cur_ == END_CALLING) {
                nbPars_--;
            }
            if (cur_ == END_BLOCK) {
                nbPars_--;
            }
            if (cur_ == BEGIN_CALLING) {
                nbPars_++;
            }
            if (cur_ == BEGIN_BLOCK) {
                nbPars_++;
            }
            if (StringList.isWordChar(cur_) && nbPars_ == 0) {
                String after_ = instruction.substring(j_+1);
                if (after_.isEmpty() || !isPart(after_.charAt(0))) {
                    String afterTrim_ = after_.trim();
                    if (afterTrim_.isEmpty() || afterTrim_.charAt(0) != '.' && afterTrim_.charAt(0) != ANNOT  && afterTrim_.charAt(0) != BEGIN_CALLING) {
                        annotation_.append(cur_);
                        annotations.add(annotation_.toString());
                        index = j_ + instructionLocation;
                        index++;
                        j_++;
                        while (j_ < lenInst_) {
                            if (!Character.isWhitespace(instruction.charAt(j_))) {
                                break;
                            }
                            index++;
                            j_++;
                        }
                        after = instruction.substring(j_);
                        break;
                    }
                }
            }
            if (cur_ == END_CALLING && nbPars_ == 0) {
                String after_ = instruction.substring(j_+1).trim();
                if (after_.isEmpty() || after_.charAt(0) != ANNOT) {
                    annotation_.append(cur_);
                    annotations.add(annotation_.toString());
                    index = j_ + instructionLocation;
                    index++;
                    j_++;
                    while (j_ < lenInst_) {
                        if (!Character.isWhitespace(instruction.charAt(j_))) {
                            break;
                        }
                        index++;
                        j_++;
                    }
                    after = instruction.substring(j_);
                    break;
                }
            }
            if (nbPars_ == 0 && cur_ == ANNOT) {
                //Add annotation
                if (!annotation_.toString().trim().isEmpty()) {
                    annotations.add(annotation_.toString());
                }
                annotation_.delete(0, annotation_.length());
            }
            if (cur_ == ANNOT) {
                annotationsIndexes.add(beforeAnnot + j_ + instructionLocation);
            }
            annotation_.append(cur_);
            j_++;
        }
    }
    private static boolean isPart(char _char) {
        if (StringList.isDollarWordChar(_char)) {
            return true;
        }
        if (_char == '.') {
            return true;
        }
        if (_char == BEGIN_CALLING) {
            return true;
        }
        return false;
    }
    public Numbers<Integer> getAnnotationsIndexes() {
        return annotationsIndexes;
    }
    public StringList getAnnotations() {
        return annotations;
    }
    public String getAfter() {
        return after;
    }
    public int getIndex() {
        return index;
    }
    
}
