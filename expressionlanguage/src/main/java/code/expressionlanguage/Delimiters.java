package code.expressionlanguage;
import code.util.CustList;
import code.util.Numbers;
import code.util.StringList;

public final class Delimiters {

    private int badOffset=-1;
    private int firstPrintableChar = CustList.INDEX_NOT_FOUND_ELT;
    private int absoluteOffset;
    private int childOffest;
    private int indexBegin;
    private int indexEnd;
    private char begin;
    private char end;
    private boolean partOfString;
    private Numbers<Integer> allowedOperatorsIndexes = new Numbers<Integer>();
    private Numbers<Integer> delStringsChars = new Numbers<Integer>();
    private Numbers<Integer> delNumbers = new Numbers<Integer>();
    private Numbers<Integer> delKeyWordSuper = new Numbers<Integer>();
    private Numbers<Integer> delKeyWordClassChoice = new Numbers<Integer>();
    private Numbers<Integer> delKeyWordStatic = new Numbers<Integer>();
    private StringList delKeyWordStaticExtract = new StringList();
    private Numbers<Integer> delCast = new Numbers<Integer>();
    private Numbers<Integer> delInstanceof = new Numbers<Integer>();
    private Numbers<Integer> delLambda = new Numbers<Integer>();
    private Numbers<Integer> delVararg = new Numbers<Integer>();
    private Numbers<Integer> delClass = new Numbers<Integer>();
    private CustList<NumberInfos> nbInfos = new CustList<NumberInfos>();
    private CustList<VariableInfo> variables = new CustList<VariableInfo>();

    public int getBadOffset() {
        return badOffset;
    }

    public void setBadOffset(int _badOffset) {
        badOffset = Math.max(_badOffset,0);
    }

    public Numbers<Integer> getAllowedOperatorsIndexes() {
        return allowedOperatorsIndexes;
    }

    public Numbers<Integer> getDelStringsChars() {
        return delStringsChars;
    }

    public Numbers<Integer> getDelNumbers() {
        return delNumbers;
    }

    public Numbers<Integer> getDelKeyWordSuper() {
        return delKeyWordSuper;
    }

    public void setDelKeyWordSuper(Numbers<Integer> _delKeyWordSuper) {
        delKeyWordSuper = _delKeyWordSuper;
    }

    public Numbers<Integer> getDelKeyWordClassChoice() {
        return delKeyWordClassChoice;
    }

    public void setDelKeyWordClassChoice(Numbers<Integer> _delKeyWordClassChoice) {
        delKeyWordClassChoice = _delKeyWordClassChoice;
    }

    public Numbers<Integer> getDelKeyWordStatic() {
        return delKeyWordStatic;
    }

    public void setDelKeyWordStatic(Numbers<Integer> _delKeyWordStatic) {
        delKeyWordStatic = _delKeyWordStatic;
    }

    public StringList getDelKeyWordStaticExtract() {
        return delKeyWordStaticExtract;
    }

    public void setDelKeyWordStaticExtract(StringList _delKeyWordStaticExtract) {
        delKeyWordStaticExtract = _delKeyWordStaticExtract;
    }

    public Numbers<Integer> getDelCast() {
        return delCast;
    }

    public void setDelCast(Numbers<Integer> _delCast) {
        delCast = _delCast;
    }
    public Numbers<Integer> getDelInstanceof() {
        return delInstanceof;
    }

    public void setDelInstanceof(Numbers<Integer> _delInstanceof) {
        delInstanceof = _delInstanceof;
    }

    public Numbers<Integer> getDelLambda() {
        return delLambda;
    }

    public void setDelLambda(Numbers<Integer> _delLambda) {
        delLambda = _delLambda;
    }

    public Numbers<Integer> getDelVararg() {
        return delVararg;
    }

    public void setDelVararg(Numbers<Integer> _delVararg) {
        delVararg = _delVararg;
    }

    public Numbers<Integer> getDelClass() {
        return delClass;
    }

    public void setDelClass(Numbers<Integer> _delClass) {
        delClass = _delClass;
    }

    public int getFirstPrintableChar() {
        return firstPrintableChar;
    }
    public void setFirstPrintableChar(int _firstPrintableChar) {
        firstPrintableChar = _firstPrintableChar;
    }

    public int getAbsoluteOffset() {
        return absoluteOffset;
    }
    public void setAbsoluteOffset(int _absoluteOffset) {
        absoluteOffset = _absoluteOffset;
    }
    public int getChildOffest() {
        return childOffest;
    }
    public void setChildOffest(int _childOffest) {
        childOffest = _childOffest;
    }
    public int getIndexBegin() {
        return indexBegin;
    }
    public void setIndexBegin(int _indexBegin) {
        indexBegin = _indexBegin;
    }
    public int getIndexEnd() {
        return indexEnd;
    }
    public void setIndexEnd(int _indexEnd) {
        indexEnd = _indexEnd;
    }
    public char getBegin() {
        return begin;
    }
    public void setBegin(char _begin) {
        begin = _begin;
    }
    public char getEnd() {
        return end;
    }
    public void setEnd(char _end) {
        end = _end;
    }
    public boolean isPartOfString() {
        return partOfString;
    }
    public void setPartOfString(boolean _partOfString) {
        partOfString = _partOfString;
    }

    public CustList<NumberInfos> getNbInfos() {
        return nbInfos;
    }
    public CustList<VariableInfo> getVariables() {
        return variables;
    }
}
