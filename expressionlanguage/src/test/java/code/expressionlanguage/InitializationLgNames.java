package code.expressionlanguage;

import code.expressionlanguage.classes.CustLgNames;
import code.expressionlanguage.stds.LgNames;

public final class InitializationLgNames {

    public static LgNames initAdvStandards(ContextEl _context) {
        LgNames lgNames_ = new CustLgNames();
        lgNames_.setContext(_context);
        basicStandards(lgNames_);
        lgNames_.build();
        _context.setStandards(lgNames_);
        lgNames_.setupOverrides(_context);
        return lgNames_;
    }
    private static void basicStandards(LgNames _lgNames) {
        _lgNames.setAliasObject("java.lang.Object");
        _lgNames.setAliasVoid("$void");
        _lgNames.setAliasCharSequence("java.lang.CharSequence");
        _lgNames.setAliasDisplayable("code.util.ints.Displayable");
        _lgNames.setAliasDisplay("display");
        _lgNames.setAliasCompareTo("compareTo");
        _lgNames.setAliasCompare("compare");
        _lgNames.setAliasEquals("equals");
        _lgNames.setAliasToString("toString");
        _lgNames.setAliasValueOf("valueOf");
        _lgNames.setAliasMaxValueField("MAX_VALUE");
        _lgNames.setAliasMinValueField("MIN_VALUE");
        _lgNames.setAliasIteratorType("$iterator");
        _lgNames.setAliasIterator("iterator");
        _lgNames.setAliasIterable("$iterable");
        _lgNames.setAliasEnumParam("$Enum");
        _lgNames.setAliasEnum("$enum");
        _lgNames.setAliasEnums("$enums");
        _lgNames.setAliasReplacement("code.util.Replacement");
//        _lgNames.setAliasStore("$badStore");
        _lgNames.setAliasStore("code.expressionlanguage.exceptions.DynamicArrayStoreException");
//        _lgNames.setAliasNullPe("$npe");
        _lgNames.setAliasNullPe("code.util.exceptions.NullObjectException");
        _lgNames.setAliasBadEncode("$enc");
//        _lgNames.setAliasBadIndex("$badIndex");
        _lgNames.setAliasBadIndex("code.expressionlanguage.exceptions.BadIndexException");
//        _lgNames.setAliasBadSize("$badSize");
        _lgNames.setAliasBadSize("code.expressionlanguage.exceptions.NegativeSizeException");
//        _lgNames.setAliasError("$error");
        _lgNames.setAliasError("java.lang.Exception");
        _lgNames.setAliasCustomError("$customError");
//        _lgNames.setAliasCast("$badCast");
        _lgNames.setAliasCast("code.expressionlanguage.exceptions.DynamicCastClassException");
//        _lgNames.setAliasDivisionZero("$divZero");
        _lgNames.setAliasDivisionZero("code.expressionlanguage.exceptions.DivideZeroException");
        //_lgNames.setAliasSof("$sofe");
        _lgNames.setAliasMath("$math");
        _lgNames.setAliasAbs("abs");
        _lgNames.setAliasMod("mod");
        _lgNames.setAliasQuot("quot");
        _lgNames.setAliasSof("code.expressionlanguage.exceptions.StackOverFlow");
        _lgNames.setAliasNbFormat("badFormat");
        _lgNames.setAliasBadEncode("badEncode");
        _lgNames.setAliasPrimBoolean("$boolean");
        _lgNames.setAliasPrimByte("$byte");
        _lgNames.setAliasPrimShort("$short");
        _lgNames.setAliasPrimChar("$char");
        _lgNames.setAliasPrimInteger("$int");
        _lgNames.setAliasPrimLong("$long");
        _lgNames.setAliasPrimFloat("$float");
        _lgNames.setAliasPrimDouble("$double");
        _lgNames.setAliasBoolean("java.lang.Boolean");
        _lgNames.setAliasByte("java.lang.Byte");
        _lgNames.setAliasShort("java.lang.Short");
        _lgNames.setAliasCharacter("java.lang.Character");
        _lgNames.setAliasInteger("java.lang.Integer");
        _lgNames.setAliasLong("java.lang.Long");
        _lgNames.setAliasFloat("java.lang.Float");
        _lgNames.setAliasDouble("java.lang.Double");
        _lgNames.setAliasNumber("java.lang.Number");
        _lgNames.setAliasParseBoolean("parseBoolean");
        _lgNames.setAliasParseByte("parseByte");
        _lgNames.setAliasParseShort("parseShort");
        _lgNames.setAliasParseInt("parseInt");
        _lgNames.setAliasParseLong("parseLong");
        _lgNames.setAliasParseFloat("parseFloat");
        _lgNames.setAliasParseDouble("parseDouble");
        _lgNames.setAliasBooleanValue("booleanValue");
        _lgNames.setAliasByteValue("byteValue");
        _lgNames.setAliasShortValue("shortValue");
        _lgNames.setAliasCharValue("charValue");
        _lgNames.setAliasIntValue("intValue");
        _lgNames.setAliasLongValue("longValue");
        _lgNames.setAliasFloatValue("floatValue");
        _lgNames.setAliasDoubleValue("doubleValue");
        _lgNames.setAliasDigit("digit");
        _lgNames.setAliasIsDigit("isDigit");
        _lgNames.setAliasIsLetter("isLetter");
        _lgNames.setAliasIsLetterOrDigit("isLetterOrDigit");
        _lgNames.setAliasIsWordChar("isWordChar");
        _lgNames.setAliasIsLowerCase("isLowerCase");
        _lgNames.setAliasIsUpperCase("isUpperCase");
        _lgNames.setAliasIsWhitespace("isWhitespace");
        _lgNames.setAliasIsSpace("isSpace");
        _lgNames.setAliasIsInfinite("isInfinite");
        _lgNames.setAliasIsNan("isNan");
        _lgNames.setAliasForDigit("forDigit");
        _lgNames.setAliasGetDirectionality("isGetDirectionality");
        _lgNames.setAliasGetType("getType");
        _lgNames.setAliasString("java.lang.String");
        _lgNames.setAliasLength("length");
        _lgNames.setAliasCharAt("charAt");
        _lgNames.setAliasToCharArray("toCharArray");
        _lgNames.setAliasSplit("split");
        _lgNames.setAliasSplitChars("splitChars");
        _lgNames.setAliasSplitStrings("splitStrings");
        _lgNames.setAliasReplace("replace");
        _lgNames.setAliasReplaceMultiple("replaceMultiple");
        _lgNames.setAliasEqualsIgnoreCase("equalsIgnoreCase");
        _lgNames.setAliasCompareToIgnoreCase("compareToIgnoreCase");
        _lgNames.setAliasContains("contains");
        _lgNames.setAliasEndsWith("endsWith");
        _lgNames.setAliasStartsWith("startsWith");
        _lgNames.setAliasIndexOf("indexOf");
        _lgNames.setAliasFormat("format");
        _lgNames.setAliasGetBytes("getBytes");
        _lgNames.setAliasIntern("intern");
        _lgNames.setAliasIsEmpty("isEmpty");
        _lgNames.setAliasLastIndexOf("lastIndexOf");
        _lgNames.setAliasRegionMatches("regionMatches");
        _lgNames.setAliasSubstring("substring");
        _lgNames.setAliasSubSequence("subSequence");
        _lgNames.setAliasToLowerCase("toLowerCase");
        _lgNames.setAliasToUpperCase("toUpperCase");
        _lgNames.setAliasTrim("trim");
        _lgNames.setAliasStringBuilder("java.lang.StringBuilder");
        _lgNames.setAliasAppend("append");
        _lgNames.setAliasCapacity("capacity");
        _lgNames.setAliasClear("clear");
        _lgNames.setAliasDelete("delete");
        _lgNames.setAliasDeleteCharAt("deleteCharAt");
        _lgNames.setAliasEnsureCapacity("ensureCapacity");
        _lgNames.setAliasInsert("insert");
        _lgNames.setAliasReverse("reverse");
        _lgNames.setAliasSetCharAt("setCharAt");
        _lgNames.setAliasSetLength("setLength");
        _lgNames.setAliasTrimToSize("trimToSize");
        _lgNames.setAliasCountable("code.util.ints.Countable");
        _lgNames.setAliasGet("get");
        _lgNames.setAliasSize("size");
//        _lgNames.setAliasSimpleIterator("simpleIterator");
        _lgNames.setAliasSimpleIterator("iterator");
        _lgNames.setAliasSimpleIterableType("code.util.ints.SimpleIterable");
        _lgNames.setAliasSimpleIteratorType("code.util.SimpleItr");
        _lgNames.setAliasErrorInitClass("$defErrorClass");
        _lgNames.setAliasInvokeTarget("$invokeTaget");
        _lgNames.setAliasClassNotFoundError("$classNotFound");
        _lgNames.setAliasClass("$Class");
        _lgNames.setAliasGetDeclaredConstructors("getDeclaredConstructors");
        _lgNames.setAliasGetDeclaredFields("getDeclaredFields");
        _lgNames.setAliasGetDeclaredMethods("getDeclaredMethods");
        _lgNames.setAliasMakeGeneric("makeGeneric");
        _lgNames.setAliasGetAllClasses("getAllClasses");
        _lgNames.setAliasConstructor("$Constructor");
        _lgNames.setAliasField("$Field");
        _lgNames.setAliasMethod("$Method");
        _lgNames.setAliasInvoke("invoke");
        _lgNames.setAliasNewInstance("newInstance");
        _lgNames.setAliasIsPolymorph("isPolymorph");
        _lgNames.setAliasSetPolymorph("setPolymorph");
        _lgNames.setAliasIsAbstract("isAbstract");
        _lgNames.setAliasGetName("getName");
        _lgNames.setAliasGetField("get");
        _lgNames.setAliasSetField("set");
        _lgNames.setAliasGetClass("getClass");
        _lgNames.setAliasForName("forName");
        _lgNames.setAliasObjectsUtil("$ObjectsUtil");
        _lgNames.setAliasSameRef("eq");
        _lgNames.setAliasNext("next");
        _lgNames.setAliasHasNext("hasNext");
        _lgNames.setAliasName("name");
        _lgNames.setAliasOrdinal("ordinal");
        _lgNames.setAliasGetOldString("getOldString");
        _lgNames.setAliasGetNewString("getNewString");
        _lgNames.setAliasSetOldString("setOldString");
        _lgNames.setAliasSetNewString("setNewString");
    }
}
