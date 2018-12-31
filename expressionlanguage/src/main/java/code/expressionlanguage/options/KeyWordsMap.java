package code.expressionlanguage.options;

import code.expressionlanguage.stds.LgNames;
import code.util.StringMap;

public final class KeyWordsMap {

    private StringMap<KeyWords> keyWords = new StringMap<KeyWords>();
    private StringMap<LgNames> stds = new StringMap<LgNames>();
    public KeyWordsMap() {
        initKeyWords();
    }
    public void initKeyWords() {
        KeyWords en_ = new KeyWords();
        KeyWords fr_ = new KeyWords();
        en_.setKeyWordAbstract("abstract");
        fr_.setKeyWordAbstract("abstrait");
        en_.setKeyWordAnnotation("annotation");
        fr_.setKeyWordAnnotation("annotation");
        en_.setKeyWordBool("bool");
        fr_.setKeyWordBool("bool");
        en_.setKeyWordBreak("break");
        fr_.setKeyWordBreak("sortir");
        en_.setKeyWordCase("case");
        fr_.setKeyWordCase("cas");
        en_.setKeyWordCast("$");
        fr_.setKeyWordCast("$");
        en_.setKeyWordCatch("catch");
        fr_.setKeyWordCatch("capture");
        en_.setKeyWordClass("class");
        fr_.setKeyWordClass("classe");
        en_.setKeyWordClasschoice("classchoice");
        fr_.setKeyWordClasschoice("choixclasse");
        en_.setKeyWordContinue("continue");
        fr_.setKeyWordContinue("iterer");
        en_.setKeyWordDefault("default");
        fr_.setKeyWordDefault("autrement");
        en_.setKeyWordDo("do");
        fr_.setKeyWordDo("faire");
        en_.setKeyWordElse("else");
        fr_.setKeyWordElse("sinon");
        en_.setKeyWordElseif("elseif");
        fr_.setKeyWordElseif("sinonsi");
        en_.setKeyWordEnum("enum");
        fr_.setKeyWordEnum("enum");
        en_.setKeyWordEscBound("b");
        fr_.setKeyWordEscBound("b");
        en_.setKeyWordEscFeed("r");
        fr_.setKeyWordEscFeed("r");
        en_.setKeyWordEscForm("f");
        fr_.setKeyWordEscForm("f");
        en_.setKeyWordEscLine("n");
        fr_.setKeyWordEscLine("n");
        en_.setKeyWordEscTab("t");
        fr_.setKeyWordEscTab("t");
        en_.setKeyWordEscUnicode("u");
        fr_.setKeyWordEscUnicode("u");
        en_.setKeyWordFalse("false");
        fr_.setKeyWordFalse("faux");
        en_.setKeyWordFinal("final");
        fr_.setKeyWordFinal("final");
        en_.setKeyWordFinally("finally");
        fr_.setKeyWordFinally("finallement");
        en_.setKeyWordFirstopt("$firstopt");
        fr_.setKeyWordFirstopt("$premieropt");
        en_.setKeyWordFor("for");
        fr_.setKeyWordFor("pour");
        en_.setKeyWordForeach("foreach");
        fr_.setKeyWordForeach("pourchaque");
        en_.setKeyWordId("$id");
        fr_.setKeyWordId("$id");
        en_.setKeyWordIf("if");
        fr_.setKeyWordIf("si");
        en_.setKeyWordInstanceof("instanceof");
        fr_.setKeyWordInstanceof("instancede");
        en_.setKeyWordInterface("interface");
        fr_.setKeyWordInterface("interface");
        en_.setKeyWordInterfaces("interfaces");
        fr_.setKeyWordInterfaces("interfaces");
        en_.setKeyWordIntern("$intern");
        fr_.setKeyWordIntern("$interne");
        en_.setKeyWordIter("iter");
        fr_.setKeyWordIter("iter");
        en_.setKeyWordLambda("$lambda");
        fr_.setKeyWordLambda("$lambda");
        en_.setKeyWordLang("$lang");
        fr_.setKeyWordLang("$langue");
        en_.setKeyWordNbBin("b");
        fr_.setKeyWordNbBin("b");
        en_.setKeyWordNbExpBin("p");
        fr_.setKeyWordNbExpBin("p");
        en_.setKeyWordNbExpDec("e");
        fr_.setKeyWordNbExpDec("e");
        en_.setKeyWordNbHex("x");
        fr_.setKeyWordNbHex("x");
        en_.setKeyWordNbSufByte("B");
        fr_.setKeyWordNbSufByte("O");
        en_.setKeyWordNbSufBytePrim("b");
        fr_.setKeyWordNbSufBytePrim("o");
        en_.setKeyWordNbSufShort("S");
        fr_.setKeyWordNbSufShort("O2");
        en_.setKeyWordNbSufShortPrim("s");
        fr_.setKeyWordNbSufShortPrim("o2");
        en_.setKeyWordNbSufCharacter("C");
        fr_.setKeyWordNbSufCharacter("C");
        en_.setKeyWordNbSufCharacterPrim("c");
        fr_.setKeyWordNbSufCharacterPrim("c");
        en_.setKeyWordNbSufInteger("I");
        fr_.setKeyWordNbSufInteger("O4");
        en_.setKeyWordNbSufIntegerPrim("i");
        fr_.setKeyWordNbSufIntegerPrim("o4");
        en_.setKeyWordNbSufLong("L");
        fr_.setKeyWordNbSufLong("O8");
        en_.setKeyWordNbSufLongPrim("l");
        fr_.setKeyWordNbSufLongPrim("o8");
        en_.setKeyWordNbSufFloat("F");
        fr_.setKeyWordNbSufFloat("F");
        en_.setKeyWordNbSufFloatPrim("f");
        fr_.setKeyWordNbSufFloatPrim("f");
        en_.setKeyWordNbSufDouble("D");
        fr_.setKeyWordNbSufDouble("D");
        en_.setKeyWordNbSufDoublePrim("d");
        fr_.setKeyWordNbSufDoublePrim("d");
        en_.setKeyWordNew("new");
        fr_.setKeyWordNew("nouveau");
        en_.setKeyWordNormal("normal");
        fr_.setKeyWordNormal("normal");
        en_.setKeyWordNull("null");
        fr_.setKeyWordNull("nul");
        en_.setKeyWordOperator("operator");
        fr_.setKeyWordOperator("operateur");
        en_.setKeyWordPackage("package");
        fr_.setKeyWordPackage("paquetage");
        en_.setKeyWordPrivate("private");
        fr_.setKeyWordPrivate("prive");
        en_.setKeyWordProtected("protected");
        fr_.setKeyWordProtected("protege");
        en_.setKeyWordPublic("public");
        fr_.setKeyWordPublic("public");
        en_.setKeyWordReturn("return");
        fr_.setKeyWordReturn("retour");
        en_.setKeyWordStatic("static");
        fr_.setKeyWordStatic("static");
        en_.setKeyWordSuper("super");
        fr_.setKeyWordSuper("super");
        en_.setKeyWordSuperaccess("superaccess");
        fr_.setKeyWordSuperaccess("superacces");
        en_.setKeyWordSwitch("switch");
        fr_.setKeyWordSwitch("selon");
        en_.setKeyWordThat("that");
        fr_.setKeyWordThat("cela");
        en_.setKeyWordThis("this");
        fr_.setKeyWordThis("ceci");
        en_.setKeyWordThisaccess("thisaccess");
        fr_.setKeyWordThisaccess("cetacces");
        en_.setKeyWordThrow("throw");
        fr_.setKeyWordThrow("lancer");
        en_.setKeyWordTrue("true");
        fr_.setKeyWordTrue("vrai");
        en_.setKeyWordTry("try");
        fr_.setKeyWordTry("essai");
        en_.setKeyWordValueOf("$valueOf");
        fr_.setKeyWordValueOf("$valeurDe");
        en_.setKeyWordValues("$values");
        fr_.setKeyWordValues("$valeurs");
        en_.setKeyWordVar("var");
        fr_.setKeyWordVar("var");
        en_.setKeyWordVararg("$vararg");
        fr_.setKeyWordVararg("$vararg");
        en_.setKeyWordWhile("while");
        fr_.setKeyWordWhile("tantque");
        keyWords.put("en", en_);
        keyWords.put("fr", fr_);
    }
    public void initEnStds(LgNames _lgNames) {
        _lgNames.setDefaultPkg("$core");
        _lgNames.setAliasObject("$core.Object");
        _lgNames.setAliasVoid("void");
        _lgNames.setAliasCharSequence("$core.CharSequence");
        _lgNames.setAliasCompareTo("compareTo");
        _lgNames.setAliasCompare("compare");
        _lgNames.setAliasEquals("equals");
        _lgNames.setAliasToString("toString");
        _lgNames.setAliasValueOf("valueOf");
        _lgNames.setAliasMaxValueField("MAX_VALUE");
        _lgNames.setAliasMinValueField("MIN_VALUE");
        _lgNames.setAliasIteratorType("$core.Iterator");
        _lgNames.setAliasIterator("iterator");
        _lgNames.setAliasIterable("$core.Iterable");
        _lgNames.setAliasEnumParam("$core.Enum");
        _lgNames.setAliasEnum("$core.$en");
        _lgNames.setAliasEnums("$core.Enums");
        _lgNames.setAliasReplacement("$core.Replacement");
        _lgNames.setAliasStore("$core.BadStore");
        _lgNames.setAliasNullPe("$core.NullObject");
        _lgNames.setAliasBadIndex("$core.BadIndexException");
        _lgNames.setAliasBadSize("$core.NegativeSize");
        _lgNames.setAliasError("$core.Error");
        _lgNames.setAliasGetMessage("getMessage");
        _lgNames.setAliasCustomError("$core.CustomError");
        _lgNames.setAliasCast("$core.BadCast");
        _lgNames.setAliasDivisionZero("$core.DivideZero");
        _lgNames.setAliasMath("$core.Math");
        _lgNames.setAliasAbs("abs");
        _lgNames.setAliasMod("mod");
        _lgNames.setAliasQuot("quot");
        _lgNames.setAliasSof("$core.StackOverFlow");
        _lgNames.setAliasNbFormat("badFormat");
        _lgNames.setAliasBadEncode("$core.badEncode");
        _lgNames.setAliasPrimBoolean("boolean");
        _lgNames.setAliasPrimByte("byte");
        _lgNames.setAliasPrimShort("short");
        _lgNames.setAliasPrimChar("char");
        _lgNames.setAliasPrimInteger("int");
        _lgNames.setAliasPrimLong("long");
        _lgNames.setAliasPrimFloat("float");
        _lgNames.setAliasPrimDouble("double");
        _lgNames.setAliasBoolean("$core.Boolean");
        _lgNames.setAliasByte("$core.Byte");
        _lgNames.setAliasShort("$core.Short");
        _lgNames.setAliasCharacter("$core.Character");
        _lgNames.setAliasInteger("$core.Integer");
        _lgNames.setAliasLong("$core.Long");
        _lgNames.setAliasFloat("$core.Float");
        _lgNames.setAliasDouble("$core.Double");
        _lgNames.setAliasNumber("$core.Number");
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
        _lgNames.setAliasGetDirectionality("getDirectionality");
        _lgNames.setAliasGetType("getType");
        _lgNames.setAliasString("$core.String");
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
        _lgNames.setAliasIsEmpty("isEmpty");
        _lgNames.setAliasLastIndexOf("lastIndexOf");
        _lgNames.setAliasRegionMatches("regionMatches");
        _lgNames.setAliasSubstring("substring");
        _lgNames.setAliasSubSequence("subSequence");
        _lgNames.setAliasToLowerCase("toLowerCase");
        _lgNames.setAliasToUpperCase("toUpperCase");
        _lgNames.setAliasTrim("trim");
        _lgNames.setAliasStringBuilder("$core.StringBuilder");
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
        _lgNames.setAliasErrorInitClass("$core.DefErrorClass");
        _lgNames.setAliasClone("clone");
        _lgNames.setAliasReadResources("readContent");
        _lgNames.setAliasReadResourcesNames("readNames");
        _lgNames.setAliasResources("$core.Resources");
        _lgNames.setAliasEnumValues("values");
        _lgNames.setAliasInvokeTarget("$core.InvokeTarget");
        _lgNames.setAliasClassNotFoundError("$core.ClassNotFound");
        _lgNames.setAliasGetVariableOwner("getVariableOwner");
        _lgNames.setAliasGetGenericVariableOwner("getGenericVariableOwner");
        _lgNames.setAliasGetString("getString");
        _lgNames.setAliasClass("$core.Class");
        _lgNames.setAliasStackTraceElement("$core.stack");
        _lgNames.setAliasCurrentStack("current");
        _lgNames.setAliasFct("$core.Fct");
        _lgNames.setAliasCall("call");
        _lgNames.setAliasAnnotation("$core.Annotation");
        _lgNames.setAliasAnnotated("$core.Annotated");
        _lgNames.setAliasGetDefaultValue("getDefaultValue");
        _lgNames.setAliasGetAnnotations("getAnnotations");
        _lgNames.setAliasGetAnnotationsParameters("getAnnotationsParameters");
        _lgNames.setAliasGetDeclaredConstructors("getDeclaredConstructors");
        _lgNames.setAliasGetDeclaredFields("getDeclaredFields");
        _lgNames.setAliasGetDeclaredMethods("getDeclaredMethods");
        _lgNames.setAliasMakeGeneric("makeGeneric");
        _lgNames.setAliasGetAllClasses("getAllClasses");
        _lgNames.setAliasGetOperators("getOperators");
        _lgNames.setAliasConstructor("$core.Constructor");
        _lgNames.setAliasField("$core.Field");
        _lgNames.setAliasMethod("$core.Method");
        _lgNames.setAliasInvoke("invoke");
        _lgNames.setAliasNewInstance("newInstance");
        _lgNames.setAliasIsPolymorph("isPolymorph");
        _lgNames.setAliasSetPolymorph("setPolymorph");
        _lgNames.setAliasIsAbstract("isAbstract");
        _lgNames.setAliasGetName("getName");
        _lgNames.setAliasGetPrettyName("getPrettyName");
        _lgNames.setAliasGetField("get");
        _lgNames.setAliasSetField("set");
        _lgNames.setAliasGetClass("getClass");
        _lgNames.setAliasGetEnclosingType("getEnclosingType");
        _lgNames.setAliasGetDeclaredClasses("getDeclaredClasses");
        _lgNames.setAliasForName("forName");
        _lgNames.setAliasObjectsUtil("$core.ObjectsUtil");
        _lgNames.setAliasSameRef("eq");
        _lgNames.setAliasGetParent("getParent");
        _lgNames.setAliasNext("next");
        _lgNames.setAliasHasNext("hasNext");
        _lgNames.setAliasIterableTable("$core.IterableTable");
        _lgNames.setAliasIteratorTable("iteratorTable");
        _lgNames.setAliasIteratorTableType("$core.IteratorTable");
        _lgNames.setAliasHasNextPair("hasNextPair");
        _lgNames.setAliasNextPair("nextPair");
        _lgNames.setAliasPairType("$core.Pair");
        _lgNames.setAliasGetFirst("getFirst");
        _lgNames.setAliasGetSecond("getSecond");
        _lgNames.setAliasName("name");
        _lgNames.setAliasOrdinal("ordinal");
        _lgNames.setAliasEnumName("name");
        _lgNames.setAliasEnumOrdinal("ordinal");
        _lgNames.setAliasEnumPredValueOf("valueOf");
        _lgNames.setAliasGetOldString("getOldString");
        _lgNames.setAliasGetNewString("getNewString");
        _lgNames.setAliasSetOldString("setOldString");
        _lgNames.setAliasSetNewString("setNewString");
        _lgNames.setAliasGetSuperClass("getSuperClass");
        _lgNames.setAliasGetGenericSuperClass("getGenericSuperClass");
        _lgNames.setAliasGetInterfaces("getInterfaces");
        _lgNames.setAliasGetGenericInterfaces("getGenericInterfaces");
        _lgNames.setAliasGetLowerBounds("getLowerBounds");
        _lgNames.setAliasGetUpperBounds("getUpperBounds");
        _lgNames.setAliasGetComponentType("getComponentType");
        _lgNames.setAliasMakeArray("makeArray");
        _lgNames.setAliasGetParameterTypes("getParameterTypes");
        _lgNames.setAliasGetTypeParameters("getTypeParameters");
        _lgNames.setAliasGetParameterNames("getParameterNames");
        _lgNames.setAliasGetGenericReturnType("getGenericReturnType");
        _lgNames.setAliasGetReturnType("getReturnType");
        _lgNames.setAliasGetActualTypeArguments("getActualTypeArguments");
        _lgNames.setAliasGetGenericTypeArguments("getGenericTypeArguments");
        _lgNames.setAliasGetFieldType("getType");
        _lgNames.setAliasGetGenericType("getGenericType");
        _lgNames.setAliasIsFinal("isFinal");
        _lgNames.setAliasIsStatic("isStatic");
        _lgNames.setAliasIsVarargs("isVarargs");
        _lgNames.setAliasIsNormal("isNormal");
        _lgNames.setAliasIsPublic("isPublic");
        _lgNames.setAliasIsProtected("isProtected");
        _lgNames.setAliasIsPackage("isPackage");
        _lgNames.setAliasIsPrivate("isPrivate");
        _lgNames.setAliasIsClass("isClass");
        _lgNames.setAliasIsWildCard("isWildCard");
        _lgNames.setAliasIsInterface("isInterface");
        _lgNames.setAliasIsEnum("isEnum");
        _lgNames.setAliasIsPrimitive("isPrimitive");
        _lgNames.setAliasIsArray("isArray");
        _lgNames.setAliasIsAnnotation("isAnnotation");
        _lgNames.setAliasMakeWildCard("makeWildCard");
        _lgNames.setAliasIsInstance("isInstance");
        _lgNames.setAliasIsAssignableFrom("isAssignableFrom");
        _lgNames.setAliasInit("init");
        _lgNames.setAliasDefaultInstance("defaultInstance");
        _lgNames.setAliasEnumValueOf("enumValueOf");
        _lgNames.setAliasGetEnumConstants("getEnumConstants");
        _lgNames.setAliasGetGenericBounds("getGenericBounds");
        _lgNames.setAliasGetBounds("getBounds");
        _lgNames.setAliasArrayNewInstance("newArrayInstance");
        _lgNames.setAliasArrayGet("get");
        _lgNames.setAliasArraySet("set");
        _lgNames.setAliasArrayGetLength("getLength");
        _lgNames.setAliasGetDeclaringClass("getDeclaringClass");
        _lgNames.setAliasBinQuot("binQuot");
        _lgNames.setAliasBinMod("binMod");
        _lgNames.setAliasPlus("plus");
        _lgNames.setAliasMinus("minus");
        _lgNames.setAliasMult("mult");
        _lgNames.setAliasAnd("and");
        _lgNames.setAliasOr("or");
        _lgNames.setAliasXor("xor");
        _lgNames.setAliasNegBin("negBin");
        _lgNames.setAliasNeg("neg");
        _lgNames.setAliasLt("lt");
        _lgNames.setAliasGt("gt");
        _lgNames.setAliasLe("le");
        _lgNames.setAliasGe("ge");
        _lgNames.setAliasShiftLeft("shiftLeft");
        _lgNames.setAliasShiftRight("shiftRight");
        _lgNames.setAliasBitShiftLeft("bitShiftLeft");
        _lgNames.setAliasBitShiftRight("bitShiftRight");
        _lgNames.setAliasRotateLeft("rotateLeft");
        _lgNames.setAliasRotateRight("rotateRight");
        _lgNames.setAliasRandom("random");
        _lgNames.setFalseString("false");
        _lgNames.setTrueString("true");
        _lgNames.setNullString("");
        stds.put("en", _lgNames);
    }
    public void initFrStds(LgNames _lgNames) {
        _lgNames.setDefaultPkg("$coeur");
        _lgNames.setAliasObject("$coeur.Objet");
        _lgNames.setAliasVoid("vide");
        _lgNames.setAliasCharSequence("$coeur.SequenceCaractere");
        _lgNames.setAliasCompareTo("comparer");
        _lgNames.setAliasCompare("compare");
        _lgNames.setAliasEquals("egal");
        _lgNames.setAliasToString("chaine");
        _lgNames.setAliasValueOf("valeurDe");
        _lgNames.setAliasMaxValueField("MAX_VALUE");
        _lgNames.setAliasMinValueField("MIN_VALUE");
        _lgNames.setAliasIteratorType("$coeur.Iterateur");
        _lgNames.setAliasIterator("iterateur");
        _lgNames.setAliasIterable("$coeur.Iterable");
        _lgNames.setAliasEnumParam("$coeur.Enum");
        _lgNames.setAliasEnum("$coeur.$en");
        _lgNames.setAliasEnums("$coeur.Enums");
        _lgNames.setAliasReplacement("$coeur.Remplacement");
        _lgNames.setAliasStore("$coeur.MauvaisStockage");
        _lgNames.setAliasNullPe("$coeur.ObjectNul");
        _lgNames.setAliasBadEncode("$coeur.MauvaisEncodage");
        _lgNames.setAliasBadIndex("$coeur.MauvaisIndice");
        _lgNames.setAliasBadSize("$coeur.TailleNegative");
        _lgNames.setAliasError("$coeur.Erreur");
        _lgNames.setAliasGetMessage("valMessage");
        _lgNames.setAliasCustomError("$coeur.ErreurPerso");
        _lgNames.setAliasCast("$coeur.MauvaisTranstype");
        _lgNames.setAliasDivisionZero("$coeur.DivisionZero");
        _lgNames.setAliasMath("$coeur.Math");
        _lgNames.setAliasAbs("abs");
        _lgNames.setAliasMod("mod");
        _lgNames.setAliasQuot("quot");
        _lgNames.setAliasSof("$coeur.PileTropGrande");
        _lgNames.setAliasNbFormat("$coeur.MauvaisFormat");
        _lgNames.setAliasPrimBoolean("booleen");
        _lgNames.setAliasPrimByte("entier1");
        _lgNames.setAliasPrimShort("entier2");
        _lgNames.setAliasPrimChar("caractere");
        _lgNames.setAliasPrimInteger("entier4");
        _lgNames.setAliasPrimLong("entier8");
        _lgNames.setAliasPrimFloat("flottant");
        _lgNames.setAliasPrimDouble("double");
        _lgNames.setAliasBoolean("$coeur.Booleen");
        _lgNames.setAliasByte("$coeur.Entier1");
        _lgNames.setAliasShort("$coeur.Entier2");
        _lgNames.setAliasCharacter("$coeur.Caractere");
        _lgNames.setAliasInteger("$coeur.Entier4");
        _lgNames.setAliasLong("$coeur.Entier8");
        _lgNames.setAliasFloat("$coeur.Floattant");
        _lgNames.setAliasDouble("$coeur.Double");
        _lgNames.setAliasNumber("$coeur.Nombre");
        _lgNames.setAliasParseBoolean("parseBooleen");
        _lgNames.setAliasParseByte("parseEntier1");
        _lgNames.setAliasParseShort("parseEntier2");
        _lgNames.setAliasParseInt("parseEntier4");
        _lgNames.setAliasParseLong("parseEntier8");
        _lgNames.setAliasParseFloat("parseFlottant");
        _lgNames.setAliasParseDouble("parseDouble");
        _lgNames.setAliasBooleanValue("booleenValue");
        _lgNames.setAliasByteValue("valEntier1");
        _lgNames.setAliasShortValue("valEntier2");
        _lgNames.setAliasCharValue("valCaractere");
        _lgNames.setAliasIntValue("valEntier4");
        _lgNames.setAliasLongValue("valEntier8");
        _lgNames.setAliasFloatValue("valFlottant");
        _lgNames.setAliasDoubleValue("valDouble");
        _lgNames.setAliasDigit("chiffre");
        _lgNames.setAliasIsDigit("estChiffre");
        _lgNames.setAliasIsLetter("estLettre");
        _lgNames.setAliasIsLetterOrDigit("estLettreOuChiffre");
        _lgNames.setAliasIsWordChar("estCaractereMot");
        _lgNames.setAliasIsLowerCase("estMinuscule");
        _lgNames.setAliasIsUpperCase("estMajuscule");
        _lgNames.setAliasIsWhitespace("estEspaceBlanc");
        _lgNames.setAliasIsSpace("estEspace");
        _lgNames.setAliasIsInfinite("estInfini");
        _lgNames.setAliasIsNan("estNbIndefini");
        _lgNames.setAliasForDigit("convertir");
        _lgNames.setAliasGetDirectionality("valDirection");
        _lgNames.setAliasGetType("valType");
        _lgNames.setAliasString("$coeur.Chaine");
        _lgNames.setAliasLength("longueur");
        _lgNames.setAliasCharAt("car");
        _lgNames.setAliasToCharArray("versTableauCaracter");
        _lgNames.setAliasSplit("separer");
        _lgNames.setAliasSplitChars("separerCaracteres");
        _lgNames.setAliasSplitStrings("separerChaines");
        _lgNames.setAliasReplace("remplacer");
        _lgNames.setAliasReplaceMultiple("remplacerMultiple");
        _lgNames.setAliasEqualsIgnoreCase("egalIgnorantCasse");
        _lgNames.setAliasCompareToIgnoreCase("compareIgnorantCasse");
        _lgNames.setAliasContains("contient");
        _lgNames.setAliasEndsWith("terminePar");
        _lgNames.setAliasStartsWith("commencePar");
        _lgNames.setAliasIndexOf("indexDe");
        _lgNames.setAliasFormat("formatter");
        _lgNames.setAliasGetBytes("valOctets");
        _lgNames.setAliasIsEmpty("estVide");
        _lgNames.setAliasLastIndexOf("dernierIndiceDe");
        _lgNames.setAliasRegionMatches("correspondRegions");
        _lgNames.setAliasSubstring("sousChaine");
        _lgNames.setAliasSubSequence("sousSequence");
        _lgNames.setAliasToLowerCase("versMinuscule");
        _lgNames.setAliasToUpperCase("versMajuscule");
        _lgNames.setAliasTrim("trimmer");
        _lgNames.setAliasStringBuilder("$coeur.ConstructeurChaine");
        _lgNames.setAliasAppend("ajouter");
        _lgNames.setAliasCapacity("capacite");
        _lgNames.setAliasClear("vider");
        _lgNames.setAliasDelete("supprimer");
        _lgNames.setAliasDeleteCharAt("supprimerCaractere");
        _lgNames.setAliasEnsureCapacity("assurerCapacite");
        _lgNames.setAliasInsert("inserer");
        _lgNames.setAliasReverse("inverser");
        _lgNames.setAliasSetCharAt("majCaractere");
        _lgNames.setAliasSetLength("majLongueur");
        _lgNames.setAliasTrimToSize("trimmerTaille");
        _lgNames.setAliasErrorInitClass("$coeur.ErrorDefClasse");
        _lgNames.setAliasClone("clone");
        _lgNames.setAliasReadResources("lireContenu");
        _lgNames.setAliasReadResourcesNames("lireNoms");
        _lgNames.setAliasResources("$coeur.Ressources");
        _lgNames.setAliasEnumValues("valeurs");
        _lgNames.setAliasInvokeTarget("$coeur.InvoqueCible");
        _lgNames.setAliasClassNotFoundError("$coeur.ClasseNonTrouve");
        _lgNames.setAliasGetVariableOwner("valVariablePoss");
        _lgNames.setAliasGetGenericVariableOwner("valGeneVariablePoss");
        _lgNames.setAliasGetString("valChaine");
        _lgNames.setAliasClass("$coeur.Classe");
        _lgNames.setAliasStackTraceElement("$coeur.pile");
        _lgNames.setAliasCurrentStack("courante");
        _lgNames.setAliasFct("$coeur.Fct");
        _lgNames.setAliasCall("appeler");
        _lgNames.setAliasAnnotation("$coeur.Annotation");
        _lgNames.setAliasAnnotated("$coeur.Annote");
        _lgNames.setAliasGetDefaultValue("valDefValeur");
        _lgNames.setAliasGetAnnotations("valAnnotations");
        _lgNames.setAliasGetAnnotationsParameters("valAnnotationsParametrees");
        _lgNames.setAliasGetDeclaredConstructors("valConstructeursDeclares");
        _lgNames.setAliasGetDeclaredFields("valChampsDeclares");
        _lgNames.setAliasGetDeclaredMethods("valMethodsDeclares");
        _lgNames.setAliasMakeGeneric("rendreGeneric");
        _lgNames.setAliasGetAllClasses("valClasses");
        _lgNames.setAliasGetOperators("valOperateurs");
        _lgNames.setAliasConstructor("$coeur.Constructeur");
        _lgNames.setAliasField("$coeur.Champ");
        _lgNames.setAliasMethod("$coeur.Methode");
        _lgNames.setAliasInvoke("invoque");
        _lgNames.setAliasNewInstance("nouvelleInstance");
        _lgNames.setAliasIsPolymorph("estPolymorphe");
        _lgNames.setAliasSetPolymorph("majPolymorphe");
        _lgNames.setAliasIsAbstract("estAbstrait");
        _lgNames.setAliasGetName("valNom");
        _lgNames.setAliasGetPrettyName("valJoliNom");
        _lgNames.setAliasGetField("obtenir");
        _lgNames.setAliasSetField("maj");
        _lgNames.setAliasGetClass("valClasse");
        _lgNames.setAliasGetEnclosingType("valTypeContenant");
        _lgNames.setAliasGetDeclaredClasses("valClassesDeclarees");
        _lgNames.setAliasForName("parNom");
        _lgNames.setAliasObjectsUtil("$coeur.ObjetsUtil");
        _lgNames.setAliasSameRef("eq");
        _lgNames.setAliasGetParent("valParent");
        _lgNames.setAliasNext("suivant");
        _lgNames.setAliasHasNext("aSuivant");
        _lgNames.setAliasIterableTable("$coeur.IterableTable");
        _lgNames.setAliasIteratorTable("iterateurTable");
        _lgNames.setAliasIteratorTableType("$coeur.IterateurTable");
        _lgNames.setAliasHasNextPair("aSuivantPair");
        _lgNames.setAliasNextPair("suivantPair");
        _lgNames.setAliasPairType("$coeur.Pair");
        _lgNames.setAliasGetFirst("valPremier");
        _lgNames.setAliasGetSecond("valDeuxieme");
        _lgNames.setAliasName("nom");
        _lgNames.setAliasOrdinal("ordinal");
        _lgNames.setAliasEnumName("nom");
        _lgNames.setAliasEnumOrdinal("ordinal");
        _lgNames.setAliasEnumPredValueOf("valeurDe");
        _lgNames.setAliasGetOldString("valChaineAvant");
        _lgNames.setAliasGetNewString("valChaineApres");
        _lgNames.setAliasSetOldString("majChaineAvant");
        _lgNames.setAliasSetNewString("majChaineApres");
        _lgNames.setAliasGetSuperClass("valSuperClasse");
        _lgNames.setAliasGetGenericSuperClass("valGeneSuperClasse");
        _lgNames.setAliasGetInterfaces("valInterfaces");
        _lgNames.setAliasGetGenericInterfaces("valGeneInterfaces");
        _lgNames.setAliasGetLowerBounds("valSousTypesContraintes");
        _lgNames.setAliasGetUpperBounds("valSuperTypesContraintes");
        _lgNames.setAliasGetComponentType("valTypeComposent");
        _lgNames.setAliasMakeArray("rendreTableau");
        _lgNames.setAliasGetParameterTypes("valTypesDeParametres");
        _lgNames.setAliasGetTypeParameters("valTypesParametres");
        _lgNames.setAliasGetParameterNames("valNomsParameters");
        _lgNames.setAliasGetGenericReturnType("valGeneTypeRetour");
        _lgNames.setAliasGetReturnType("valTypeRetour");
        _lgNames.setAliasGetActualTypeArguments("valCourantArgTypes");
        _lgNames.setAliasGetGenericTypeArguments("valGeneArgTypes");
        _lgNames.setAliasGetFieldType("valType");
        _lgNames.setAliasGetGenericType("valGeneType");
        _lgNames.setAliasIsFinal("estFinal");
        _lgNames.setAliasIsStatic("estStatic");
        _lgNames.setAliasIsVarargs("estVarargs");
        _lgNames.setAliasIsNormal("estNormal");
        _lgNames.setAliasIsPublic("estPublic");
        _lgNames.setAliasIsProtected("estProtege");
        _lgNames.setAliasIsPackage("estPaquetage");
        _lgNames.setAliasIsPrivate("estPrive");
        _lgNames.setAliasIsClass("estClasse");
        _lgNames.setAliasIsWildCard("estSynthetique");
        _lgNames.setAliasIsInterface("estInterface");
        _lgNames.setAliasIsEnum("estEnum");
        _lgNames.setAliasIsPrimitive("estPrimitif");
        _lgNames.setAliasIsArray("estTableau");
        _lgNames.setAliasIsAnnotation("estAnnotation");
        _lgNames.setAliasMakeWildCard("rendreSynthetique");
        _lgNames.setAliasIsInstance("estInstance");
        _lgNames.setAliasIsAssignableFrom("estAssignableDe");
        _lgNames.setAliasInit("init");
        _lgNames.setAliasDefaultInstance("instanceParDefaut");
        _lgNames.setAliasEnumValueOf("enumValeurDe");
        _lgNames.setAliasGetEnumConstants("valEnumConst");
        _lgNames.setAliasGetGenericBounds("valGeneContraintes");
        _lgNames.setAliasGetBounds("valContraintes");
        _lgNames.setAliasArrayNewInstance("nouvelleInstanceTableau");
        _lgNames.setAliasArrayGet("obtenir");
        _lgNames.setAliasArraySet("maj");
        _lgNames.setAliasArrayGetLength("valLongeur");
        _lgNames.setAliasGetDeclaringClass("valClasseDeclarante");
        _lgNames.setAliasBinQuot("binQuot");
        _lgNames.setAliasBinMod("binMod");
        _lgNames.setAliasPlus("plus");
        _lgNames.setAliasMinus("moins");
        _lgNames.setAliasMult("mult");
        _lgNames.setAliasAnd("et");
        _lgNames.setAliasOr("ou");
        _lgNames.setAliasXor("ouExc");
        _lgNames.setAliasNegBin("negBin");
        _lgNames.setAliasNeg("neg");
        _lgNames.setAliasLt("pq");
        _lgNames.setAliasGt("gq");
        _lgNames.setAliasLe("pqe");
        _lgNames.setAliasGe("gqe");
        _lgNames.setAliasShiftLeft("glisserGauche");
        _lgNames.setAliasShiftRight("glisserDroite");
        _lgNames.setAliasBitShiftLeft("binGlisserGauche");
        _lgNames.setAliasBitShiftRight("binGlisserDroite");
        _lgNames.setAliasRotateLeft("rotGauche");
        _lgNames.setAliasRotateRight("rotDroite");
        _lgNames.setAliasRandom("alea");
        _lgNames.setFalseString("faux");
        _lgNames.setTrueString("vrai");
        _lgNames.setNullString("");
        stds.put("fr", _lgNames);
    }
    public KeyWords getKeyWords(String _lg) {
        return keyWords.getVal(_lg);
    }
    public StringMap<KeyWords> getKeyWords() {
        return keyWords;
    }
}
