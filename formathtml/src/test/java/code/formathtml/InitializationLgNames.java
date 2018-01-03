package code.formathtml;

import code.expressionlanguage.stds.LgNames;
import code.formathtml.util.BeanLgNames;

public final class InitializationLgNames {

    private InitializationLgNames(){
    }
    public static BeanLgNames initStandards() {
        BeanLgNames lgNames_ = new BeanLgNames();
        lgNames_.setAliasRate("java.lang.Long");
        basicStandards(lgNames_);
        lgNames_.build();
        lgNames_.setupOverrides();
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
//        _lgNames.setAliasCast("$badCast");
        _lgNames.setAliasCast("code.expressionlanguage.exceptions.DynamicCastClassException");
        _lgNames.setAliasDivisionZero("code.expressionlanguage.exceptions.DivideZeroException");
        //_lgNames.setAliasSof("$sofe");
        //_lgNames.setAliasMath("$math");
        _lgNames.setAliasMath("java.lang.Math");
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
        _lgNames.setAliasForDigit("isForDigit");
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
        _lgNames.setAliasDeleteCharAt("charAt");
        _lgNames.setAliasEnsureCapacity("ensureCapacity");
        _lgNames.setAliasInsert("insert");
        _lgNames.setAliasReverse("reverse");
        _lgNames.setAliasSetCharAt("setCharAt");
        _lgNames.setAliasSetLength("setLength");
        _lgNames.setAliasTrimToSize("trimToSize");
        _lgNames.setAliasCountable("code.util.ints.Countable");
        _lgNames.setAliasGet("get");
        _lgNames.setAliasSize("size");
        _lgNames.setAliasSimpleIterator("simpleIterator");
        _lgNames.setAliasSimpleIteratorType("code.util.SimpleItr");
        _lgNames.setAliasSimpleIterableType("code.util.ints.SimpleIterable");
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
