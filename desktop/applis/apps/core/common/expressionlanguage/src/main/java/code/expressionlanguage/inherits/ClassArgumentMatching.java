package code.expressionlanguage.inherits;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.exec.calls.PageEl;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.*;
import code.util.CustList;
import code.util.StringList;

public final class ClassArgumentMatching {

    private static final String ARR_CLASS = "[";

    private final StringList className = new StringList();

    private String unwrapObject = "";

    private boolean checkOnlyNullPe;
    private boolean convertToString;
    private CustList<ClassMethodId> implicits = new CustList<ClassMethodId>();
    private CustList<ClassMethodId> implicitsTest = new CustList<ClassMethodId>();
    private int rootNumber = -1;
    private int memberNumber = -1;
    private int rootNumberTest = -1;
    private int memberNumberTest = -1;

    public ClassArgumentMatching(String _className) {
        className.add(_className);
    }

    public ClassArgumentMatching(StringList _className) {
        className.addAllElts(_className);
    }

    public static ClassArgumentMatching copy(ClassArgumentMatching _copy) {
        return new ClassArgumentMatching(_copy.className);
    }

    public static Struct convert(PageEl _page,ClassArgumentMatching _dest,Struct _arg,
                                 ContextEl _exec) {
        ClassArgumentMatching format_ = _dest.format(_page,_exec);
        if (format_.matchClass(_exec.getStandards().getAliasNumber())) {
            return convertToNumber(_arg);
        }
        if (format_.isBoolType(_exec)) {
            return convertToBoolean(_arg);
        }
        if (toPrimitive(format_,_exec).matchClass(_exec.getStandards().getAliasPrimChar())) {
            return convertToChar(_arg);
        }
        if (isPureNumberClass(format_,_exec)) {
            return PrimitiveTypeUtil.convertToNumber(format_,_arg,_exec.getStandards());
        }
        return _arg;
    }

    public static Struct convertWide(PageEl _page,ClassArgumentMatching _dest,Struct _arg,
                                 ContextEl _exec) {
        ClassArgumentMatching format_ = _dest.format(_page,_exec);
        return convertWide(format_, _arg, _exec);
    }

    public static Struct convertWide(ClassArgumentMatching _dest, Struct _arg, ContextEl _exec) {
        if (isPrimitive(_dest,_exec)) {
            if (_dest.isBoolType(_exec)) {
                return convertToBoolean(_arg);
            }
            if (toPrimitive(_dest,_exec).matchClass(_exec.getStandards().getAliasPrimChar())) {
                return convertToChar(_arg);
            }
            return PrimitiveTypeUtil.convertToNumber(_dest,_arg,_exec.getStandards());
        }
        return _arg;
    }

    public static BooleanStruct convertToBoolean(Struct _arg) {
        if (_arg instanceof BooleanStruct) {
            return (BooleanStruct) _arg;
        }
        return BooleanStruct.of(false);
    }

    public static CharStruct convertToChar(Struct _arg) {
        if (_arg instanceof CharStruct) {
            return (CharStruct) _arg;
        }
        return new CharStruct((char)0);
    }

    public static NumberStruct convertToNumber(Struct _arg) {
        if (_arg instanceof NumberStruct) {
            return (NumberStruct) _arg;
        }
        return new ByteStruct((byte)0);
    }

    public static boolean isPrimitive(ClassArgumentMatching _clMatchLeft,
                                      ContextEl _conf) {
        LgNames stds_ = _conf.getStandards();
        return PrimitiveTypeUtil.isPrimitive(_clMatchLeft, stds_);
    }

    public static boolean isPureNumberClass(ClassArgumentMatching _class, ContextEl _context) {
        return PrimitiveTypeUtil.isPureNumberClass(_class, _context.getStandards());
    }

    public static ClassArgumentMatching toPrimitive(ClassArgumentMatching _class, ContextEl _context) {
        return PrimitiveTypeUtil.toPrimitive(_class, _context.getStandards());
    }

    private ClassArgumentMatching format(PageEl _page, ContextEl _exec) {
        StringList className_ = new StringList();
        for (String s: className) {
            className_.add(_page.formatVarType(s,_exec));
        }
        return new ClassArgumentMatching(className_);
    }

    public boolean isNumericInt(ContextEl _context) {
        AnalyzedPageEl page_ = _context.getAnalyzing();
        LgNames stds_ = page_.getStandards();
        String intPr_ = stds_.getAliasPrimInteger();
        String shortPr_ = stds_.getAliasPrimShort();
        String charPr_ = stds_.getAliasPrimChar();
        String bytePr_ = stds_.getAliasPrimByte();
        ClassArgumentMatching prim_ = AnaTypeUtil.toPrimitive(this, page_);
        if (prim_.matchClass(intPr_)) {
            return true;
        }
        if (prim_.matchClass(shortPr_)) {
            return true;
        }
        if (prim_.matchClass(charPr_)) {
            return true;
        }
        return prim_.matchClass(bytePr_);
    }

    public boolean isArray() {
        for (String b: className) {
            if (b.startsWith(ARR_CLASS)) {
                return true;
            }
        }
        return false;
    }
    public boolean matchVoid(ContextEl _classes) {
        LgNames stds_ = _classes.getAnalyzing().getStandards();
        StringList l_ = new StringList(stds_.getAliasVoid());
        return StringList.equalsSet(className, l_);
    }

    public boolean matchClass(String _class) {
        StringList l_ = new StringList(_class);
        return StringList.equalsSet(className, l_);
    }

    public boolean isVariable() {
        return StringList.contains(className, "");
    }

    public boolean isPrimitive(ContextEl _context) {
        for (String b: className) {
            if (AnaTypeUtil.isPrimitive(b, _context.getAnalyzing())) {
                return true;
            }
        }
        return false;
    }

    public boolean isWrapper(ContextEl _context) {
        for (String b: className) {
            if (AnaTypeUtil.isWrapper(b, _context)) {
                return true;
            }
        }
        return false;
    }
    public boolean isBoolType(ContextEl _context) {
        LgNames lgNames_ = _context.getStandards();
        return isBoolType(lgNames_);
    }
    public boolean isBoolType(AnalyzedPageEl _context) {
        LgNames lgNames_ = _context.getStandards();
        return isBoolType(lgNames_);
    }
    public boolean isBoolType(LgNames _lgNames) {
        String aliasBoolean_ = _lgNames.getAliasBoolean();
        String aliasPrBoolean_ = _lgNames.getAliasPrimBoolean();
        for (String b: className) {
            if (b.isEmpty()) {
                return true;
            }
            if (matchClass(aliasBoolean_)) {
                return true;
            }
            if (matchClass(aliasPrBoolean_)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return className.first();
    }

    public String getSingleNameOrEmpty() {
        if (className.size() != 1) {
            return "";
        }
        return className.first();
    }

    public StringList getNames() {
        return new StringList(className);
    }

    public String getUnwrapObject() {
        return unwrapObject;
    }

    public void setUnwrapObject(String _unwrapObject) {
        unwrapObject = _unwrapObject;
    }

    public void setUnwrapObject(ClassArgumentMatching _other) {
        unwrapObject = _other.getName();
    }

    public boolean isCheckOnlyNullPe() {
        return checkOnlyNullPe;
    }

    public void setCheckOnlyNullPe(boolean _checkOnlyNullPe) {
        checkOnlyNullPe = _checkOnlyNullPe;
    }

    public boolean isConvertToString() {
        return convertToString;
    }

    public void setConvertToString(boolean _convertToString) {
        convertToString = _convertToString;
    }

    public CustList<ClassMethodId> getImplicits() {
        return implicits;
    }

    public CustList<ClassMethodId> getImplicitsTest() {
        return implicitsTest;
    }

    public int getRootNumber() {
        return rootNumber;
    }

    public void setRootNumber(int rootNumber) {
        this.rootNumber = rootNumber;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }

    public int getRootNumberTest() {
        return rootNumberTest;
    }

    public void setRootNumberTest(int rootNumberTest) {
        this.rootNumberTest = rootNumberTest;
    }

    public int getMemberNumberTest() {
        return memberNumberTest;
    }

    public void setMemberNumberTest(int memberNumberTest) {
        this.memberNumberTest = memberNumberTest;
    }
}
