package code.formathtml.sample;

import code.bean.Bean;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.common.NumParsers;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.functionid.MethodModifier;
import code.expressionlanguage.stds.*;
import code.expressionlanguage.structs.*;
import code.formathtml.Configuration;
import code.bean.nat.BeanNatLgNames;
import code.bean.nat.DefaultInitialization;
import code.bean.BeanStruct;
import code.bean.RealInstanceStruct;
import code.formathtml.structs.BeanInfo;
import code.util.*;
import code.util.core.StringUtil;

public final class CustBeanLgNames extends BeanNatLgNames {

    private static final String TYPE_INTS = "code.formathtml.classes.Ints";
    private static final String TYPE_RATE = "code.formathtml.classes.Rate";
    private static final String GET_VALUE = "getValue";
    private static final String EQ = "eq";
    private static final String REMOVE_AND_EXIST_AFTER = "removeAndExistAfter";
    private static final String CLEAR = "clear";
    private static final String LAST = "last";
    private static final String ADD = "add";
    private static final String SUM = "sum";
    private static final String NB_BEANS = "NB_BEANS";
    private static final String UPDATE_VALUE = "updateValue";
    private static final String SET_STRING = "setString";
    private static final String GET_STRING_ELT = "getStringElt";
    private static final String SUMMUM = "summum";
    private static final String GET_STRING = "getString";
    private static final String PRIVATE_METHOD = "privateMethod";
    private static final String INTERN_METHOD = "internMethod";
    private static final String STRINGS = "strings";
    private static final String STRING = "string";
    private static final String MY_CHAR = "myChar";
    private static final String INTEGER = "integer";
    private static final String DISPLAYED = "displayed";
    private static final String SET_TYPED_TEXT = "setTypedText";
    private static final String SET_RADIO_LONG = "setRadioLong";
    private static final String SET_COMBO_NUMBERS = "setComboNumbers";
    private static final String SET_COMBO_NUMBER_TWO = "setComboNumberTwo";
    private static final String SET_COMBO_NUMBER = "setComboNumber";
    private static final String SET_CHECK_BOX = "setCheckBox";
    private static final String SETUP = "setup";
    private static final String GET_RADIO_LONG = "getRadioLong";
    private static final String GET_TYPED_TEXT = "getTypedText";
    private static final String GET_TYPED_STRING = "getTypedString";
    private static final String GET_COMBOBOX_MAP = "getComboboxMap";
    private static final String GET_COMBOBOX = "getCombobox";
    private static final String GET_COMBO_NUMBERS = "getComboNumbers";
    private static final String GET_COMBO_NUMBER_TWO = "getComboNumberTwo";
    private static final String GET_COMBO_NUMBER = "getComboNumber";
    private static final String IS_CHECK_BOX = "isCheckBox";
    private static final String GET_DATA_BASE = "getDataBase";
    private static final String VALIDATE_STRINGS_SAVE = "validateStringsSave";
    private static final String VALIDATE_MAP = "validateMap";
    private static final String VALIDATE_INTS_SAVE = "validateIntsSave";
    private static final String GO_TWO_ARGS = "goTwoArgs";
    private static final String GET_STRINGS = "getStrings";
    private static final String GET_TREE = "getTree";
    private static final String ARRAY_INT = "arrayInt";
    private static final String MY_ENUM_TWO = "myEnumTwo";
    private static final String MY_ENUM_THREE = "myEnumThree";
    private static final String MY_ENUM_ONE = "myEnumOne";
    private static final String GET_DEFAULT_CHOICES = "getDefaultChoices";
    private static final String SELECTED_STRINGS = "selectedStrings";
    private static final String SETTER = "setter";
    private static final String SET_INVISIBLE_INT_FIELD = "setInvisibleIntField";
    private static final String SET_INVISIBLE_FIELD = "setInvisibleField";
    private static final String NUMBERS_TWO = "numbersTwo";
    private static final String INDEX_TWO = "indexTwo";
    private static final String INDEX = "index";
    private static final String VALIDATE = "validate";
    private static final String SET_TYPED_STRING = "setTypedString";
    private static final String SET_TYPED_INT = "setTypedInt";
    private static final String GO_TEXT_AREA = "goTextArea";
    private static final String GO = "go";
    private static final String GET_CHOSEN_NUMBERS = "getChosenNumbers";
    private static final String TYPED_STRING = "typedString";
    private static final String TYPED_INT = "typedInt";
    private static final String TYPED_SHORT = "typedShort";
    private static final String RATE = "rate";
    private static final String NULLABLE_INT = "nullableInt";
    private static final String NULLABLE_CHECKBOX = "nullableCheckbox";
    private static final String FIELD = "field";
    private static final String CHOOSE = "choose";
    private static final String CHECKED = "checked";
    private static final String VALIDATE_STRINGS = "validateStrings";
    private static final String GET_TRANS = "getTrans";
    private static final String GET_STANDARD = "getStandard";
    private static final String GET_SPAN_CLASSES = "getSpanClasses";
    private static final String GET_SPAN_CLASS = "getSpanClass";
    private static final String GET_COMPOSITES = "getComposites";
    private static final String GET_DEFAULT_CHOICE = "getDefaultChoice";
    private static final String GET_COMPOSITE = "getComposite";
    private static final String HAS_MORE_THAN_ONE = "hasMoreThanOne";
    private static final String INVOKE_METHOD = "invokeMethod";
    private static final String GO_TO_PAGE = "goToPage";
    private static final String GO_TO_NULL_PAGE = "goToNullPage";
    private static final String GET_DOUBLE = "getDouble";
    private static final String GET_LIST = "getList";
    private static final String TREE = "tree";
    private static final String TRANSLATIONS = "translations";
    private static final String SELECTED_STRING = "selectedString";
    private static final String NUMBERS = "numbers";
    private static final String MESSAGE = "message";
    private static final String MAP = "map";
    private static final String COMPOSITE = "composite";
    private static final String COMMON_CLASS = "commonClass";
    private static final String COMBOBOX = "combobox";
    private static final String CHOSEN_NUMBERS = "chosenNumbers";
    private static final String CHOSEN_NUMBERS_NULL = "chosenNumbersNull";
    private static final String CHOSEN_NUMBER = "chosenNumber";
    private static final String GET = "get";
    private static final String GET_REVERSE = "getReverse";
    private static final String TYPE_SIMPLE_DATA_BASE = "code.formathtml.classes.SimpleDataBase";
    private static final String TYPE_NAT_TREE_MAP_STRING_INTEGER = "code.formathtml.classes.NatTreeMapStringInteger";
    private static final String TYPE_RATE_EQ = "code.formathtml.classes.RateEq";
    private static final String TYPE_ENCAPS_FIELDS = "code.formathtml.classes.EncapsFields";
    private static final String TYPE_PICKABLE_LIST = "code.formathtml.classes.PickableList";
    private static final String TYPE_GENE_OBJECTS = "code.formathtml.classes.GeneObjects";
    private static final String TYPE_GENE_OBJS = "code.formathtml.classes.GeneObjs";
    private static final String TYPE_ENUM_NUMBERS = "code.formathtml.classes.EnumNumbers";
    private static final String TYPE_ENUM_NUMBER = "code.formathtml.classes.EnumNumber";
    private static final String TYPE_BEAN_UTIL = "code.formathtml.classes.BeanUtil";
    private static final String TYPE_COMPOSITE = "code.formathtml.classes.Composite";
    private static final String TYPE_BEAN_EIGHT = "code.formathtml.classes.BeanEight";
    private static final String TYPE_BEAN_SEVEN = "code.formathtml.classes.BeanSeven";
    private static final String TYPE_BEAN_SIX = "code.formathtml.classes.BeanSix";
    private static final String TYPE_BEAN_FIVE = "code.formathtml.classes.BeanFive";
    private static final String TYPE_BEAN_FOUR = "code.formathtml.classes.BeanFour";
    private static final String TYPE_BEAN_THREE = "code.formathtml.classes.BeanThree";
    private static final String TYPE_BEAN_TWO = "code.formathtml.classes.BeanTwo";
    private static final String TYPE_BEAN_ONE = "code.formathtml.classes.BeanOne";
    private static final String TYPE_UNSELECTED_RADIO = "code.formathtml.classes.UnselectedRadio";
    private static final String TYPE_MY_VALIDATOR_ENUMS = "code.formathtml.classes.MyValidatorEnums";
    private static final String TYPE_MY_VALIDATOR_ENUM = "code.formathtml.classes.MyValidatorEnum";
    private static final String TYPE_MY_VALIDATOR = "code.formathtml.classes.MyValidator";
    private static final String TYPE_STRING_LIST = "code.util.StringList";
    private static final String ALIAS_LS = "ls";
    private static final String ALIAS_LSE = "lse";
    private Composite dataBase;
    public CustBeanLgNames() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_INTS, fields_, constructors_, methods_, TYPE_LIST, MethodModifier.FINAL);
        cl_.getDirectInterfaces().add(TYPE_COUNTABLE);
        getStandards().addEntry(TYPE_INTS, cl_);
        getIterables().put(TYPE_INTS, getContent().getNbAlias().getAliasInteger());
        DefaultInitialization.basicStandards(this);
    }
    @Override
    public void buildOther() {
        buildBeans();
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_STRING_LIST, fields_, constructors_, methods_, TYPE_LIST, MethodModifier.FINAL);
        cl_.getDirectInterfaces().add(TYPE_COUNTABLE);
        cl_.getDirectInterfaces().add(TYPE_DISPLAYABLE);
        params_ = new StringList();
        method_ = new StandardMethod(GET_REVERSE,params_,TYPE_STRING_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(GET,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_STRING_LIST, cl_);
        getIterables().put(TYPE_STRING_LIST, getAliasString());
        buildBeanOne();
        buildBeanTwo();
        buildBeanThree();
        buildBeanFour();
        buildBeanFive();
        buildBeanSix();
        buildBeanSeven();
        buildBeanEight();
        buildComposite();
        buildBeanUtil();
        buildEnumNumber();
        buildEnumNumbers();
        buildEncapsFields();
        buildGeneObjs();
        buildGeneObjects();
        buildPickableList();
        buildPickableList();
        buildRate();
        buildRateEq();
        buildSimpleDataBase();
        buildNatTreeMapStringInteger();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        cl_ = new StandardClass(TYPE_MY_VALIDATOR, fields_, constructors_, methods_, TYPE_VALIDATOR, MethodModifier.FINAL);
        getStandards().addEntry(TYPE_MY_VALIDATOR, cl_);
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        cl_ = new StandardClass(TYPE_MY_VALIDATOR_ENUM, fields_, constructors_, methods_, TYPE_VALIDATOR, MethodModifier.FINAL);
        getStandards().addEntry(TYPE_MY_VALIDATOR_ENUM, cl_);
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        cl_ = new StandardClass(TYPE_MY_VALIDATOR_ENUMS, fields_, constructors_, methods_, TYPE_VALIDATOR, MethodModifier.FINAL);
        getStandards().addEntry(TYPE_MY_VALIDATOR_ENUMS, cl_);
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        cl_ = new StandardClass(TYPE_UNSELECTED_RADIO, fields_, constructors_, methods_, TYPE_VALIDATOR, MethodModifier.FINAL);
        getStandards().addEntry(TYPE_UNSELECTED_RADIO, cl_);
    }
    private void buildBeanOne() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_ONE, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.add(new StandardField(CHOSEN_NUMBER, getAliasString(),false,false,cl_));
        fields_.add(new StandardField(CHOSEN_NUMBERS,TYPE_ENUM_NUMBERS,false,false,cl_));
        fields_.add(new StandardField(COMBOBOX,TYPE_ENUM_NUMBERS,false,false,cl_));
        fields_.add(new StandardField(COMMON_CLASS, getAliasString(),false,false,cl_));
        fields_.add(new StandardField(COMPOSITE,TYPE_COMPOSITE,false,false,cl_));
        fields_.add(new StandardField(MAP, TYPE_MAP,false,false,cl_));
        fields_.add(new StandardField(MESSAGE, getAliasString(),false,false,cl_));
        fields_.add(new StandardField(NUMBERS, TYPE_MAP,false,false,cl_));
        fields_.add(new StandardField(SELECTED_STRING, getAliasString(),false,false,cl_));
        fields_.add(new StandardField(TRANSLATIONS, TYPE_MAP,false,false,cl_));
        fields_.add(new StandardField(TREE, TYPE_MAP,false,false,cl_));
        fields_.add(new StandardField(STRINGS,TYPE_STRING_LIST,false,false,cl_));
        params_ = new StringList(getContent().getNbAlias().getAliasInteger());
        method_ = new StandardMethod(GET_LIST,params_, TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getNbAlias().getAliasLong());
        method_ = new StandardMethod(GET_DOUBLE,params_, getContent().getPrimTypes().getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GO_TO_NULL_PAGE,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GO_TO_PAGE,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getNbAlias().getAliasLong());
        method_ = new StandardMethod(GO_TO_PAGE,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(INVOKE_METHOD,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(HAS_MORE_THAN_ONE,params_, getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMPOSITE,params_,TYPE_COMPOSITE, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_DEFAULT_CHOICE,params_,TYPE_ENUM_NUMBER, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMPOSITES,params_, TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimLong());
        method_ = new StandardMethod(GET_SPAN_CLASS,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimLong(), getContent().getPrimTypes().getAliasPrimLong(), getContent().getPrimTypes().getAliasPrimLong());
        method_ = new StandardMethod(GET_SPAN_CLASSES,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(GET_STANDARD,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getNbAlias().getAliasLong());
        method_ = new StandardMethod(GET_TRANS,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_STRINGS,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_BEAN_ONE, cl_);
    }
    private void buildBeanTwo() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_TWO, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.add(new StandardField(CHECKED, getAliasPrimBoolean(),false,false,cl_));
        fields_.add(new StandardField(CHOOSE, getAliasString(),false,false,cl_));
        fields_.add(new StandardField(CHOSEN_NUMBER,TYPE_ENUM_NUMBER,false,false,cl_));
        fields_.add(new StandardField(FIELD, getAliasString(),false,false,cl_));
        fields_.add(new StandardField(NULLABLE_CHECKBOX, getContent().getNbAlias().getAliasBoolean(),false,false,cl_));
        fields_.add(new StandardField(NULLABLE_INT, getContent().getNbAlias().getAliasLong(),false,false,cl_));
        fields_.add(new StandardField(RATE,TYPE_RATE,false,false,cl_));
        fields_.add(new StandardField(TYPED_INT, getContent().getPrimTypes().getAliasPrimInteger(),false,false,cl_));
        fields_.add(new StandardField(TYPED_SHORT, getContent().getPrimTypes().getAliasPrimShort(),false,false,cl_));
        fields_.add(new StandardField(TYPED_STRING, getAliasString(),false,false,cl_));
        params_ = new StringList();
        method_ = new StandardMethod(GET_CHOSEN_NUMBERS,params_, TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GO,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getNbAlias().getAliasLong());
        method_ = new StandardMethod(GO,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GO_TEXT_AREA,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(SET_TYPED_INT,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(SET_TYPED_STRING,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_BEAN_TWO, cl_);
    }
    private void buildBeanThree() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_THREE, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.add(new StandardField(INDEX, getContent().getPrimTypes().getAliasPrimInteger(),false,false,cl_));
        fields_.add(new StandardField(INDEX_TWO, getContent().getPrimTypes().getAliasPrimInteger(),false,false,cl_));
        fields_.add(new StandardField(NUMBERS, TYPE_LIST,false,false,cl_));
        fields_.add(new StandardField(NUMBERS_TWO, TYPE_LIST,false,false,cl_));
        getStandards().addEntry(TYPE_BEAN_THREE, cl_);
    }
    private void buildBeanFour() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_FOUR, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        params_ = new StringList(getContent().getNbAlias().getAliasInteger());
        method_ = new StandardMethod(SET_INVISIBLE_FIELD,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(SET_INVISIBLE_INT_FIELD,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(SETTER,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_BEAN_FOUR, cl_);
    }
    private void buildBeanFive() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_FIVE, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.add(new StandardField(CHOSEN_NUMBERS,TYPE_ENUM_NUMBERS,false,false,cl_));
        fields_.add(new StandardField(CHOSEN_NUMBERS_NULL, TYPE_LIST,false,false,cl_));
        fields_.add(new StandardField(COMBOBOX,TYPE_ENUM_NUMBERS,false,false,cl_));
        fields_.add(new StandardField(SELECTED_STRINGS,TYPE_STRING_LIST,false,false,cl_));
        fields_.add(new StandardField(TRANSLATIONS, TYPE_MAP,false,false,cl_));
        fields_.add(new StandardField(TREE, TYPE_MAP,false,false,cl_));
        params_ = new StringList();
        method_ = new StandardMethod(GO,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_DEFAULT_CHOICES,params_, TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_BEAN_FIVE, cl_);
    }
    private void buildBeanSix() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_SIX, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.add(new StandardField(MY_ENUM_ONE,TYPE_ENUM_NUMBER,false,false,cl_));
        fields_.add(new StandardField(MY_ENUM_THREE,TYPE_ENUM_NUMBER,false,false,cl_));
        fields_.add(new StandardField(MY_ENUM_TWO,TYPE_ENUM_NUMBER,false,false,cl_));
        getStandards().addEntry(TYPE_BEAN_SIX, cl_);
    }
    private void buildBeanSeven() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_SEVEN, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.add(new StandardField(ARRAY_INT,TYPE_INTS,false,true,cl_));
        fields_.add(new StandardField(COMPOSITE,TYPE_COMPOSITE,false,false,cl_));
        params_ = new StringList();
        method_ = new StandardMethod(GET_TREE,params_,TYPE_NAT_TREE_MAP_STRING_INTEGER, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_STRINGS,params_,TYPE_STRING_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getNbAlias().getAliasDouble());
        method_ = new StandardMethod(GET_DOUBLE,params_, getContent().getPrimTypes().getAliasPrimDouble(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger(), getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(GO_TWO_ARGS,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_INTS_SAVE,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_MAP,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_STRINGS,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_STRINGS_SAVE,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_BEAN_SEVEN, cl_);
    }
    private void buildBeanEight() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_EIGHT, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        params_ = new StringList();
        method_ = new StandardMethod(GET_DATA_BASE,params_,TYPE_SIMPLE_DATA_BASE, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_CHECK_BOX,params_, getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBO_NUMBER,params_,TYPE_ENUM_NUMBER, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBO_NUMBER_TWO,params_,TYPE_ENUM_NUMBER, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBO_NUMBERS,params_,TYPE_ENUM_NUMBERS, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBOBOX,params_, TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMPOSITES,params_, TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBOBOX_MAP,params_, TYPE_MAP, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TYPED_STRING,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TYPED_TEXT,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_RADIO_LONG,params_, getContent().getPrimTypes().getAliasPrimLong(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SETUP,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasPrimBoolean());
        method_ = new StandardMethod(SET_CHECK_BOX,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(TYPE_ENUM_NUMBER);
        method_ = new StandardMethod(SET_COMBO_NUMBER,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(TYPE_ENUM_NUMBER);
        method_ = new StandardMethod(SET_COMBO_NUMBER_TWO,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(TYPE_ENUM_NUMBERS);
        method_ = new StandardMethod(SET_COMBO_NUMBERS,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimLong());
        method_ = new StandardMethod(SET_RADIO_LONG,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(SET_TYPED_STRING,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(SET_TYPED_TEXT,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_BEAN_EIGHT, cl_);
    }
    private void buildComposite() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardConstructor ctor_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_COMPOSITE, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        fields_.add(new StandardField(DISPLAYED, getAliasPrimBoolean(),false,false,cl_));
        fields_.add(new StandardField(INTEGER, getContent().getPrimTypes().getAliasPrimInteger(),false,false,cl_));
        fields_.add(new StandardField(MAP, TYPE_MAP,false,false,cl_));
        fields_.add(new StandardField(MY_CHAR, getContent().getPrimTypes().getAliasPrimChar(),false,false,cl_));
        fields_.add(new StandardField(STRING, getAliasString(),false,false,cl_));
        fields_.add(new StandardField(STRINGS,TYPE_STRING_LIST,false,false,cl_));
        params_ = new StringList();
        method_ = new StandardMethod(INTERN_METHOD,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(PRIVATE_METHOD,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_STRING,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_STRINGS,params_,TYPE_STRING_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(SUMMUM,params_, getContent().getPrimTypes().getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(GET_STRING_ELT,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(SET_STRING,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList("code.formathtml.util.ValueChangeEvent");
        method_ = new StandardMethod(UPDATE_VALUE,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        params_ = new StringList();
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        cl_.getDirectInterfaces().add(TYPE_DISPLAYABLE);
        getStandards().addEntry(TYPE_COMPOSITE, cl_);
    }
    private void buildBeanUtil() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_BEAN_UTIL, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        fields_.add(new StandardField(NB_BEANS, getAliasPrimBoolean(),true,true,cl_));
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger(), getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(SUM,params_, getContent().getPrimTypes().getAliasPrimInteger(), false, MethodModifier.STATIC);
        methods_.add( method_);
        getStandards().addEntry(TYPE_BEAN_UTIL, cl_);
    }
    private void buildEnumNumber() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_ENUM_NUMBER, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        cl_.getDirectInterfaces().add(TYPE_DISPLAYABLE);
        getStandards().addEntry(TYPE_ENUM_NUMBER, cl_);
    }
    private void buildEnumNumbers() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_ENUM_NUMBERS, fields_, constructors_, methods_, TYPE_LIST, MethodModifier.FINAL);
        cl_.getDirectInterfaces().add(TYPE_DISPLAYABLE);
        getStandards().addEntry(TYPE_ENUM_NUMBERS, cl_);
        getIterables().put(TYPE_ENUM_NUMBERS,TYPE_ENUM_NUMBER);
    }
    private void buildGeneObjs() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardConstructor ctor_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_GENE_OBJS, fields_, constructors_, methods_, TYPE_LIST, MethodModifier.FINAL);
        params_ = new StringList(getAliasObject());
        method_ = new StandardMethod(ADD,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(GET,params_, getAliasObject(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        params_ = new StringList(TYPE_LIST);
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        params_ = new StringList(getAliasObject());
        ctor_ = new StandardConstructor(params_,true);
        constructors_.add(ctor_);
        getStandards().addEntry(TYPE_GENE_OBJS, cl_);
    }
    private void buildGeneObjects() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardConstructor ctor_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_GENE_OBJECTS, fields_, constructors_, methods_, TYPE_LIST, MethodModifier.FINAL);
        params_ = new StringList(getAliasObject());
        method_ = new StandardMethod(ADD,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(LAST,params_, getAliasObject(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CLEAR,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        getStandards().addEntry(TYPE_GENE_OBJECTS, cl_);
    }
    private void buildPickableList() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardConstructor ctor_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_PICKABLE_LIST, fields_, constructors_, methods_, TYPE_LIST, MethodModifier.FINAL);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimInteger());
        method_ = new StandardMethod(REMOVE_AND_EXIST_AFTER,params_, getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_LIST,params_,TYPE_GENE_OBJECTS, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        getStandards().addEntry(TYPE_PICKABLE_LIST, cl_);
    }
    private void buildEncapsFields() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_ENCAPS_FIELDS, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        params_ = new StringList();
        method_ = new StandardMethod(IS_CHECK_BOX,params_, getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBO_NUMBER,params_,TYPE_ENUM_NUMBER, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBO_NUMBER_TWO,params_,TYPE_ENUM_NUMBER, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBO_NUMBERS,params_,TYPE_ENUM_NUMBERS, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBOBOX,params_, TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_COMBOBOX_MAP,params_, TYPE_MAP, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TYPED_STRING,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TYPED_TEXT,params_, getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_RADIO_LONG,params_, getContent().getPrimTypes().getAliasPrimLong(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasPrimBoolean());
        method_ = new StandardMethod(SET_CHECK_BOX,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(TYPE_ENUM_NUMBER);
        method_ = new StandardMethod(SET_COMBO_NUMBER,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(TYPE_ENUM_NUMBER);
        method_ = new StandardMethod(SET_COMBO_NUMBER_TWO,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(TYPE_ENUM_NUMBERS);
        method_ = new StandardMethod(SET_COMBO_NUMBERS,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getContent().getPrimTypes().getAliasPrimLong());
        method_ = new StandardMethod(SET_RADIO_LONG,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(SET_TYPED_STRING,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        method_ = new StandardMethod(SET_TYPED_TEXT,params_, getContent().getCoreNames().getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_ENCAPS_FIELDS, cl_);
    }
    private void buildRate() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_RATE, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        cl_.getDirectInterfaces().add(TYPE_DISPLAYABLE);
        getStandards().addEntry(TYPE_RATE, cl_);
    }
    private void buildRateEq() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardConstructor ctor_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_RATE_EQ, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        params_ = new StringList(TYPE_RATE_EQ);
        method_ = new StandardMethod(EQ,params_, getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(getAliasString());
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        params_ = new StringList(TYPE_RATE_EQ);
        ctor_ = new StandardConstructor(params_,false);
        constructors_.add(ctor_);
        cl_.getDirectInterfaces().add(TYPE_DISPLAYABLE);
        getStandards().addEntry(TYPE_RATE_EQ, cl_);
    }
    private void buildNatTreeMapStringInteger() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_NAT_TREE_MAP_STRING_INTEGER, fields_, constructors_, methods_, TYPE_MAP, MethodModifier.FINAL);
        getStandards().addEntry(TYPE_NAT_TREE_MAP_STRING_INTEGER, cl_);
    }
    private void buildSimpleDataBase() {
        CustList<StandardField> fields_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        StandardMethod method_;
        StringList params_;
        StandardClass cl_;
        cl_ = new StandardClass(TYPE_SIMPLE_DATA_BASE, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        params_ = new StringList();
        method_ = new StandardMethod(GET_VALUE,params_, getContent().getPrimTypes().getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        getStandards().addEntry(TYPE_SIMPLE_DATA_BASE, cl_);
    }
    @Override
    public ResultErrorStd getOtherResult(ContextEl _cont, ClassField _classField, Struct _instance) {
        ResultErrorStd res_ = new ResultErrorStd();
        Object instance_ = null;
        if (_instance != NullStruct.NULL_VALUE) {
            instance_ = ((RealInstanceStruct)_instance).getInstance();
        }
        String className_ = _classField.getClassName();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(className_,TYPE_BEAN_ONE)) {
            BeanOne i_ = (BeanOne)instance_;
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBER)) {
                if (i_.getChosenNumber() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(StdStruct.newInstance(i_.getChosenNumber(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBERS)) {
                res_.setResult(new StdStruct(i_.getChosenNumbers(),TYPE_ENUM_NUMBERS));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMBOBOX)) {
                res_.setResult(new StdStruct(i_.getCombobox(),TYPE_ENUM_NUMBERS));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMMON_CLASS)) {
                res_.setResult(new StringStruct(i_.getCommonClass()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMPOSITE)) {
                res_.setResult(new StdStruct(i_.getComposite(),TYPE_COMPOSITE));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MAP)) {
                res_.setResult(new StdStruct(i_.getMap(),ALIAS_LSE));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MESSAGE)) {
                res_.setResult(new StringStruct(i_.getMessage()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,NUMBERS)) {
                res_.setResult(new StdStruct(i_.getNumbers(),ALIAS_LSE));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,SELECTED_STRING)) {
                if (i_.getSelectedString() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StringStruct(i_.getSelectedString()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TRANSLATIONS)) {
                res_.setResult(new StdStruct(i_.getTranslations(),ALIAS_LSE));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TREE)) {
                if (i_.getTree() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StdStruct(i_.getTree(),ALIAS_LSE));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_TWO)) {
            BeanTwo i_ = (BeanTwo)instance_;
            if (StringUtil.quickEq(fieldName_,CHECKED)) {
                res_.setResult(BooleanStruct.of(i_.isChecked()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOOSE)) {
                if (i_.getChoose() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StringStruct(i_.getChoose()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBER)) {
                if (i_.getChosenNumber() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(StdStruct.newInstance(i_.getChosenNumber(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,FIELD)) {
                if (i_.getField() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StringStruct(i_.getField()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,NULLABLE_CHECKBOX)) {
                if (i_.getNullableCheckbox() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                } else {
                    res_.setResult(BooleanStruct.of(i_.getNullableCheckbox()));
                }
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,NULLABLE_INT)) {
                if (i_.getNullableInt() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                } else {
                    res_.setResult(new LongStruct(i_.getNullableInt()));
                }
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,RATE)) {
                if (i_.getRate() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StdStruct(i_.getRate(),TYPE_RATE));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TYPED_INT)) {
                res_.setResult(new IntStruct(i_.getTypedInt()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TYPED_SHORT)) {
                res_.setResult(new ShortStruct(i_.getTypedShort()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TYPED_STRING)) {
                if (i_.getTypedString() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StringStruct(i_.getTypedString()));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_THREE)) {
            BeanThree i_ = (BeanThree)instance_;
            if (StringUtil.quickEq(fieldName_,INDEX)) {
                res_.setResult(new IntStruct(i_.getIndex()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,INDEX_TWO)) {
                res_.setResult(new IntStruct(i_.getIndexTwo()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,NUMBERS)) {
                res_.setResult(StdStruct.newListInt(i_.getNumbers(),ALIAS_LS));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,NUMBERS_TWO)) {
                res_.setResult(StdStruct.newListInt(i_.getNumbersTwo(),ALIAS_LS));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_FIVE)) {
            BeanFive i_ = (BeanFive)instance_;
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBERS)) {
                EnumNumbers nbs_ = i_.getChosenNumbers();
                if (nbs_ == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StdStruct(nbs_,TYPE_ENUM_NUMBERS));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBERS_NULL)) {
                StringList nbs_ = i_.getChosenNumbersNull();
                if (nbs_ == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StdStruct(nbs_,TYPE_STRING_LIST));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMBOBOX)) {
                EnumNumbers nbs_ = i_.getCombobox();
                if (nbs_ == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StdStruct(nbs_,TYPE_ENUM_NUMBERS));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,SELECTED_STRINGS)) {
                res_.setResult(new StdStruct(i_.getSelectedStrings(),TYPE_STRING_LIST));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TRANSLATIONS)) {
                res_.setResult(new StdStruct(i_.getTranslations(),ALIAS_LSE));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TREE)) {
                res_.setResult(new StdStruct(i_.getTree(),ALIAS_LSE));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_SIX)) {
            BeanSix i_ = (BeanSix)instance_;
            if (StringUtil.quickEq(fieldName_,MY_ENUM_ONE)) {
                if (i_.getMyEnumOne() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(StdStruct.newInstance(i_.getMyEnumOne(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MY_ENUM_THREE)) {
                if (i_.getMyEnumThree() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(StdStruct.newInstance(i_.getMyEnumThree(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MY_ENUM_TWO)) {
                if (i_.getMyEnumTwo() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(StdStruct.newInstance(i_.getMyEnumTwo(),TYPE_ENUM_NUMBER));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_SEVEN)) {
            BeanSeven i_ = (BeanSeven)instance_;
            if (StringUtil.quickEq(fieldName_,ARRAY_INT)) {
                Ints in_ = i_.getArrayInt();
                res_.setResult(StdStruct.newInstance(in_,TYPE_INTS));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMPOSITE)) {
                res_.setResult(new StdStruct(i_.getComposite(),TYPE_COMPOSITE));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_COMPOSITE)) {
            Composite i_ = (Composite)instance_;
            if (StringUtil.quickEq(fieldName_,DISPLAYED)) {
                res_.setResult(BooleanStruct.of(i_.isDisplayed()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,INTEGER)) {
                res_.setResult(new IntStruct(i_.getInteger()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MAP)) {
                res_.setResult(new StdStruct(i_.getMap(),ALIAS_LSE));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MY_CHAR)) {
                res_.setResult(new CharStruct(i_.getMyChar()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,STRING)) {
                if (i_.getString() == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StringStruct(i_.getString()));
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,STRINGS)) {
                res_.setResult(new StdStruct(i_.getStrings(),TYPE_STRING_LIST));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_UTIL)) {
            if (StringUtil.quickEq(fieldName_,NB_BEANS)) {
                res_.setResult(new IntStruct(BeanUtil.NB_BEANS));
                return res_;
            }
        }
        return res_;
    }

    @Override
    public ResultErrorStd setOtherResult(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        Object instance_ =  ((RealInstanceStruct)_instance).getInstance();
        String className_ = _classField.getClassName();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(className_,TYPE_BEAN_ONE)) {
            BeanOne i_ = (BeanOne)instance_;
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBER)) {
                i_.setChosenNumber((String)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBERS)) {
                i_.setChosenNumbers((EnumNumbers)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMBOBOX)) {
                i_.setCombobox((EnumNumbers)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMMON_CLASS)) {
                i_.setCommonClass((String)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMPOSITE)) {
                i_.setComposite((Composite) _val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MESSAGE)) {
                i_.setMessage((String)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,SELECTED_STRING)) {
                i_.setSelectedString((String)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_TWO)) {
            BeanTwo i_ = (BeanTwo)instance_;
            if (StringUtil.quickEq(fieldName_,CHECKED)) {
                i_.setChecked(BooleanStruct.isTrue(_val));
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOOSE)) {
                i_.setChoose((String)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBER)) {
                i_.setChosenNumber((EnumNumber)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,FIELD)) {
                i_.setField((String)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,NULLABLE_CHECKBOX)) {
                i_.setNullableCheckbox((Boolean)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,NULLABLE_INT)) {
                if (_val== NullStruct.NULL_VALUE) {
                    i_.setNullableInt(null);
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                i_.setNullableInt(NumParsers.convertToNumber(_val).longStruct());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,RATE)) {
                i_.setRate((Rate)((RealInstanceStruct)_val).getInstance());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TYPED_INT)) {
                i_.setTypedInt((Integer)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TYPED_SHORT)) {
                i_.setTypedShort(NumParsers.convertToNumber(_val).shortStruct());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,TYPED_STRING)) {
                i_.setTypedString(NumParsers.getString(_val).getInstance());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_THREE)) {
            BeanThree i_ = (BeanThree)instance_;
            if (StringUtil.quickEq(fieldName_,INDEX)) {
                i_.setIndex((Integer)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,INDEX_TWO)) {
                i_.setIndexTwo((Integer)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_FIVE)) {
            BeanFive i_ = (BeanFive)instance_;
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBERS)) {
                i_.setChosenNumbers((EnumNumbers)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,CHOSEN_NUMBERS_NULL)) {
                i_.setChosenNumbersNull((StringList)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,COMBOBOX)) {
                i_.setCombobox((EnumNumbers)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,SELECTED_STRINGS)) {
                i_.setSelectedStrings((StringList)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_SIX)) {
            BeanSix i_ = (BeanSix)instance_;
            if (StringUtil.quickEq(fieldName_,MY_ENUM_ONE)) {
                i_.setMyEnumOne((EnumNumber)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MY_ENUM_THREE)) {
                i_.setMyEnumThree((EnumNumber)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MY_ENUM_TWO)) {
                i_.setMyEnumTwo((EnumNumber)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_SEVEN)) {
            BeanSeven i_ = (BeanSeven)instance_;
            if (StringUtil.quickEq(fieldName_,COMPOSITE)) {
                i_.setComposite((Composite) _val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_COMPOSITE)) {
            Composite i_ = (Composite)instance_;
            if (StringUtil.quickEq(fieldName_,DISPLAYED)) {
                i_.setDisplayed((Boolean)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,INTEGER)) {
                i_.setInteger((Integer)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,MY_CHAR)) {
                i_.setMyChar((Character)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,STRING)) {
                i_.setString((String)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(fieldName_,STRINGS)) {
                i_.setStrings((StringList)(Object)_val);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        return res_;
    }

    @Override
    public ResultErrorStd getOtherResultBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        ResultErrorStd res_ = new ResultErrorStd();

        Object instance_ = null;
        if (!_method.getConstraints().isStaticMethod()) {
            instance_ = ((RealInstanceStruct)_instance).getInstance();
        }
        String className_ = _method.getClassName();
        String methodName_ = _method.getConstraints().getName();
        if (StringUtil.quickEq(className_,TYPE_STRING_LIST)) {
            StringList i_ = (StringList)instance_;
            if (StringUtil.quickEq(methodName_,GET_REVERSE)) {
                res_.setResult(new StdStruct(i_.getReverse(),TYPE_STRING_LIST));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET)) {
                res_.setResult(new StringStruct(i_.get(NumParsers.convertToNumber(_args[0]).intStruct())));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_ONE)) {
            BeanOne i_ = (BeanOne)instance_;
            if (StringUtil.quickEq(methodName_,GO_TO_PAGE)) {
                if (_method.getConstraints().getParametersTypes().size() == 0) {
                    res_.setResult(new StringStruct(i_.goToPage()));
                    return res_;
                }
                if (_method.getConstraints().getParametersTypes().size() == 1) {
                    res_.setResult(new StringStruct(i_.goToPage(NumParsers.convertToNumber(_args[0]).longStruct())));
                    return res_;
                }
            }
            if (StringUtil.quickEq(methodName_,GET_LIST)) {
                res_.setResult(new IntStruct(i_.getList(NumParsers.convertToNumber(_args[0]).intStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_DOUBLE)) {
                res_.setResult(new IntStruct(i_.getDouble(NumParsers.convertToNumber(_args[0]).longStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GO_TO_NULL_PAGE)) {
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,INVOKE_METHOD)) {
                if (_args[0] == null) {
                    return res_;
                }
                if (i_.getComposite() == null) {
                    return res_;
                }
                if (i_.getComposite().getStrings() == null) {
                    return res_;
                }
                res_.setResult(new StringStruct(i_.invokeMethod(NumParsers.convertToNumber(_args[0]).intStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,HAS_MORE_THAN_ONE)) {
                res_.setResult(BooleanStruct.of(i_.hasMoreThanOne()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMPOSITE)) {
                res_.setResult(new StdStruct(i_.getComposite(),TYPE_COMPOSITE));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_DEFAULT_CHOICE)) {
                res_.setResult(StdStruct.newInstance(i_.getDefaultChoice(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMPOSITES)) {
                res_.setResult(new StdStruct(i_.getComposites(),ALIAS_LS));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_SPAN_CLASS)) {
                res_.setResult(new StringStruct(i_.getSpanClass(NumParsers.convertToNumber(_args[0]).longStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_SPAN_CLASSES)) {
                res_.setResult(new StringStruct(i_.getSpanClasses(NumParsers.convertToNumber(_args[0]).longStruct(),NumParsers.convertToNumber(_args[1]).longStruct(),NumParsers.convertToNumber(_args[2]).longStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_STANDARD)) {
                String str_ = i_.getStandard((String)(Object)_args[0]);
                if (str_ == null) {
                    res_.setResult(NullStruct.NULL_VALUE);
                    return res_;
                }
                res_.setResult(new StringStruct(str_));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_TRANS)) {
                res_.setResult(new StringStruct(i_.getTrans(NumParsers.convertToNumber(_args[0]).longStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,VALIDATE_STRINGS)) {
                i_.validateStrings();
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_TWO)) {
            BeanTwo i_ = (BeanTwo)instance_;
            if (StringUtil.quickEq(methodName_,GO)) {
                if (_method.getConstraints().getParametersTypes().size() == 0) {
                    res_.setResult(new StringStruct(i_.go()));
                    return res_;
                }
                if (_method.getConstraints().getParametersTypes().size() == 1) {
                    res_.setResult(new StringStruct(i_.go(NumParsers.convertToNumber(_args[0]).longStruct())));
                    return res_;
                }
            }
            if (StringUtil.quickEq(methodName_,GET_CHOSEN_NUMBERS)) {
                res_.setResult(new StdStruct(i_.getChosenNumbers(),ALIAS_LS));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GO_TEXT_AREA)) {
                res_.setResult(new StringStruct(i_.goTextArea()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_TYPED_INT)) {
                i_.setTypedInt(NumParsers.convertToNumber(_args[0]).intStruct());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_TYPED_STRING)) {
                i_.setTypedString((String)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,VALIDATE)) {
                i_.validate();
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_FOUR)) {
            BeanFour i_ = (BeanFour)instance_;
            if (StringUtil.quickEq(methodName_,SET_INVISIBLE_FIELD)) {
                i_.setInvisibleField(NumParsers.convertToNumber(_args[0]).intStruct());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_INVISIBLE_INT_FIELD)) {
                i_.setInvisibleIntField(NumParsers.convertToNumber(_args[0]).intStruct());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SETTER)) {
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_FIVE)) {
            BeanFive i_ = (BeanFive)instance_;
            if (StringUtil.quickEq(methodName_,GO)) {
                res_.setResult(new StringStruct(i_.go()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_DEFAULT_CHOICES)) {
                res_.setResult(new StdStruct(i_.getDefaultChoices(),ALIAS_LS));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_SEVEN)) {
            BeanSeven i_ = (BeanSeven)instance_;
            if (StringUtil.quickEq(methodName_,GET_TREE)) {
                res_.setResult(new StdStruct(i_.getTree(),TYPE_NAT_TREE_MAP_STRING_INTEGER));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_STRINGS)) {
                res_.setResult(new StdStruct(i_.getStrings(),TYPE_STRING_LIST));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_DOUBLE)) {
                res_.setResult(new DoubleStruct(i_.getDouble((Double)(Object)_args[0])));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GO_TWO_ARGS)) {
                res_.setResult(new StringStruct(i_.goTwoArgs(NumParsers.convertToNumber(_args[0]).intStruct(),NumParsers.convertToNumber(_args[1]).intStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,VALIDATE_INTS_SAVE)) {
                i_.validateIntsSave();
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,VALIDATE_MAP)) {
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,VALIDATE_STRINGS)) {
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,VALIDATE_STRINGS_SAVE)) {
                i_.validateStringsSave();
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_EIGHT)) {
            BeanEight i_ = (BeanEight)instance_;
            if (StringUtil.quickEq(methodName_,GET_DATA_BASE)) {
                res_.setResult(StdStruct.newInstance(i_.getDataBase(),TYPE_SIMPLE_DATA_BASE));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,IS_CHECK_BOX)) {
                res_.setResult(BooleanStruct.of(i_.isCheckBox()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBO_NUMBER)) {
                res_.setResult(StdStruct.newInstance(i_.getComboNumber(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBO_NUMBER_TWO)) {
                res_.setResult(StdStruct.newInstance(i_.getComboNumberTwo(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBO_NUMBERS)) {
                res_.setResult(new StdStruct(i_.getComboNumbers(),TYPE_ENUM_NUMBERS));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBOBOX)) {
                res_.setResult(new StdStruct(i_.getCombobox(),ALIAS_LS));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMPOSITES)) {
                res_.setResult(new StdStruct(i_.getComposites(),ALIAS_LS));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBOBOX_MAP)) {
                res_.setResult(new StdStruct(i_.getComboboxMap(),ALIAS_LSE));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_TYPED_STRING)) {
                res_.setResult(new StringStruct(i_.getTypedString()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_TYPED_TEXT)) {
                res_.setResult(new StringStruct(i_.getTypedText()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_RADIO_LONG)) {
                res_.setResult(new LongStruct(i_.getRadioLong()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SETUP)) {
                res_.setResult(new StringStruct(i_.setup()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_CHECK_BOX)) {
                i_.setCheckBox((Boolean)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_COMBO_NUMBER)) {
                i_.setComboNumber((EnumNumber)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_COMBO_NUMBER_TWO)) {
                i_.setComboNumberTwo((EnumNumber)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_COMBO_NUMBERS)) {
                i_.setComboNumbers((EnumNumbers)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_RADIO_LONG)) {
                i_.setRadioLong(NumParsers.convertToNumber(_args[0]).intStruct());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_TYPED_STRING)) {
                i_.setTypedString((String)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_TYPED_TEXT)) {
                i_.setTypedText((String)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_COMPOSITE)) {
            Composite i_ = (Composite)instance_;
            if (StringUtil.quickEq(methodName_,INTERN_METHOD)) {
                res_.setResult(new StringStruct(i_.internMethod()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,PRIVATE_METHOD)) {
                res_.setResult(new StringStruct(i_.privateMethod()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_STRING)) {
                res_.setResult(new StringStruct(i_.getString()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_STRINGS)) {
                res_.setResult(new StdStruct(i_.getStrings(),TYPE_STRING_LIST));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SUMMUM)) {
                res_.setResult(new IntStruct(i_.summum(NumParsers.convertToNumber(_args[0]).intStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_STRING_ELT)) {
                res_.setResult(new StringStruct(i_.getStringElt(NumParsers.convertToNumber(_args[0]).intStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_STRING)) {
                i_.setString((String)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_UTIL)) {
            if (StringUtil.quickEq(methodName_,SUM)) {
                res_.setResult(new IntStruct(BeanUtil.sum(NumParsers.convertToNumber(_args[0]).intStruct(),NumParsers.convertToNumber(_args[1]).intStruct())));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_ENCAPS_FIELDS)) {
            EncapsFields i_ = (EncapsFields)instance_;
            if (StringUtil.quickEq(methodName_,IS_CHECK_BOX)) {
                res_.setResult(BooleanStruct.of(i_.isCheckBox()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBO_NUMBER)) {
                res_.setResult(StdStruct.newInstance(i_.getComboNumber(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBO_NUMBER_TWO)) {
                res_.setResult(StdStruct.newInstance(i_.getComboNumberTwo(),TYPE_ENUM_NUMBER));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBO_NUMBERS)) {
                res_.setResult(new StdStruct(i_.getComboNumbers(),TYPE_ENUM_NUMBERS));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBOBOX)) {
                res_.setResult(new StdStruct(i_.getCombobox(),ALIAS_LS));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_COMBOBOX_MAP)) {
                res_.setResult(new StdStruct(i_.getComboboxMap(),ALIAS_LSE));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_TYPED_STRING)) {
                res_.setResult(new StringStruct(i_.getTypedString()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_TYPED_TEXT)) {
                res_.setResult(new StringStruct(i_.getTypedText()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_RADIO_LONG)) {
                res_.setResult(new LongStruct(i_.getRadioLong()));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_CHECK_BOX)) {
                i_.setCheckBox((Boolean)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_COMBO_NUMBER)) {
                i_.setComboNumber((EnumNumber)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_COMBO_NUMBER_TWO)) {
                i_.setComboNumberTwo((EnumNumber)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_COMBO_NUMBERS)) {
                i_.setComboNumbers((EnumNumbers)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_RADIO_LONG)) {
                i_.setRadioLong(NumParsers.convertToNumber(_args[0]).intStruct());
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_TYPED_STRING)) {
                i_.setTypedString((String)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,SET_TYPED_TEXT)) {
                i_.setTypedText((String)(Object)_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_GENE_OBJS)) {
            GeneObjs i_ = (GeneObjs)instance_;
            if (StringUtil.quickEq(methodName_,ADD)) {
                i_.add(_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_GENE_OBJECTS)) {
            GeneObjects i_ = (GeneObjects)instance_;
            if (StringUtil.quickEq(methodName_,ADD)) {
                i_.add(_args[0]);
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
            if (StringUtil.quickEq(methodName_,CLEAR)) {
                i_.clear();
                res_.setResult(NullStruct.NULL_VALUE);
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_PICKABLE_LIST)) {
            PickableList i_ = (PickableList)instance_;
            if (StringUtil.quickEq(methodName_,REMOVE_AND_EXIST_AFTER)) {
                res_.setResult(BooleanStruct.of(i_.removeAndExistAfter(NumParsers.convertToNumber(_args[0]).intStruct())));
                return res_;
            }
            if (StringUtil.quickEq(methodName_,GET_LIST)) {
                res_.setResult(new StdStruct(i_.getList(),TYPE_GENE_OBJECTS));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_RATE_EQ)) {
            RateEq i_ = (RateEq)instance_;
            if (StringUtil.quickEq(methodName_,EQ)) {
                res_.setResult(BooleanStruct.of(i_.eq((RateEq)(Object) _args[0])));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_SIMPLE_DATA_BASE)) {
            SimpleDataBase i_ = (SimpleDataBase)instance_;
            if (StringUtil.quickEq(methodName_,GET_VALUE)) {
                res_.setResult(new IntStruct(i_.getValue()));
                return res_;
            }
        }
        return res_;
    }
    @Override
    public ResultErrorStd getOtherResultBean(ContextEl _cont, ConstructorId _method, Struct... _args) {
        ResultErrorStd res_ = new ResultErrorStd();
        String className_ = _method.getName();
        if (StringUtil.quickEq(className_,TYPE_BEAN_ONE)) {
            res_.setResult(new BeanStruct(new BeanOne()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_FIVE)) {
            res_.setResult(new BeanStruct(new BeanFive()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_TWO)) {
            res_.setResult(new BeanStruct(new BeanTwo()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_COMPOSITE)) {
            if (_method.getParametersTypes().isEmpty()) {
                res_.setResult(new StdStruct(new Composite(),TYPE_COMPOSITE));
                return res_;
            }
            if (_method.getParametersTypes().size() == 1) {
                res_.setResult(new StdStruct(new Composite((String)(Object)_args[0]),TYPE_COMPOSITE));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_RATE_EQ)) {
            if (StringUtil.quickEq(_method.getParametersTypes().first(), getAliasString())) {
                if (!RateEq.matchesRate((String)(Object)_args[0])) {
                    return res_;
                }
                res_.setResult(new StdStruct(new RateEq((String)(Object)_args[0]),TYPE_RATE_EQ));
                return res_;
            }
            if (StringUtil.quickEq(_method.getParametersTypes().first(), TYPE_RATE_EQ)) {
                res_.setResult(new StdStruct(new RateEq((RateEq)(Object)_args[0]),TYPE_RATE_EQ));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_GENE_OBJS)) {
            if (_method.getParametersTypes().isEmpty()) {
                res_.setResult(new StdStruct(new GeneObjs(),TYPE_GENE_OBJS));
                return res_;
            }
            if (StringUtil.quickEq(_method.getParametersTypes().first(), getAliasObject())) {
                if (_args[0] instanceof Object) {
                    res_.setResult(new StdStruct(new GeneObjs((Object[])(Object)_args[0]),TYPE_GENE_OBJS));
                    return res_;
                }
                res_.setResult(new StdStruct(new GeneObjs(_args[0]),TYPE_GENE_OBJS));
                return res_;
            }
        }
        if (StringUtil.quickEq(className_,TYPE_STRING_LIST)) {
            res_.setResult(new StdStruct(new StringList(),TYPE_STRING_LIST));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_ONE)) {
            res_.setResult(new BeanStruct(new BeanOne()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_TWO)) {
            res_.setResult(new BeanStruct(new BeanTwo()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_THREE)) {
            res_.setResult(new BeanStruct(new BeanThree()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_FOUR)) {
            res_.setResult(new BeanStruct(new BeanFour()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_FIVE)) {
            res_.setResult(new BeanStruct(new BeanFive()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_SIX)) {
            res_.setResult(new BeanStruct(new BeanSix()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_SEVEN)) {
            res_.setResult(new BeanStruct(new BeanSeven()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_BEAN_EIGHT)) {
            res_.setResult(new BeanStruct(new BeanEight()));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_ENUM_NUMBERS)) {
            res_.setResult(new StdStruct(new EnumNumbers(),TYPE_ENUM_NUMBERS));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_ENCAPS_FIELDS)) {
            res_.setResult(StdStruct.newInstance(new EncapsFields(),TYPE_ENCAPS_FIELDS));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_GENE_OBJECTS)) {
            res_.setResult(new StdStruct(new GeneObjects(),TYPE_GENE_OBJECTS));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_PICKABLE_LIST)) {
            res_.setResult(StdStruct.newInstance(new PickableList(),TYPE_PICKABLE_LIST));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_SIMPLE_DATA_BASE)) {
            res_.setResult(StdStruct.newInstance(new SimpleDataBase(),TYPE_SIMPLE_DATA_BASE));
            return res_;
        }
        if (StringUtil.quickEq(className_,TYPE_NAT_TREE_MAP_STRING_INTEGER)) {
            res_.setResult(new StdStruct(new NatTreeMapStringInteger(),TYPE_NAT_TREE_MAP_STRING_INTEGER));
            return res_;
        }
        return res_;
    }

    private String getOtherBeanStructClassName(Object _struct) {
        if (_struct instanceof int[]) {
            return StringExpUtil.getPrettyArrayType(getContent().getPrimTypes().getAliasPrimInteger());
        }
        if (_struct instanceof Integer[]) {
            return StringExpUtil.getPrettyArrayType(getContent().getNbAlias().getAliasInteger());
        }
        if (_struct instanceof int[][]) {
            return StringExpUtil.getPrettyArrayType(getContent().getPrimTypes().getAliasPrimInteger(),2);
        }
        if (_struct instanceof Integer[][]) {
            return StringExpUtil.getPrettyArrayType(getContent().getNbAlias().getAliasInteger(),2);
        }
        if (_struct instanceof Object[][]) {
            return StringExpUtil.getPrettyArrayType(getAliasObject(), 2);
        }
        if (_struct instanceof String[]) {
            return StringExpUtil.getPrettyArrayType(getAliasString());
        }
        if (_struct instanceof Object[]) {
            return StringExpUtil.getPrettyArrayType(getAliasObject());
        }
        if (_struct instanceof EnumNumber) {
            return TYPE_ENUM_NUMBER;
        }
        if (_struct instanceof EnumNumbers) {
            return TYPE_ENUM_NUMBERS;
        }
        if (_struct instanceof Composite) {
            return TYPE_COMPOSITE;
        }
        if (_struct instanceof EncapsFields) {
            return TYPE_ENCAPS_FIELDS;
        }
        if (_struct instanceof BeanOne) {
            return TYPE_BEAN_ONE;
        }
        if (_struct instanceof BeanTwo) {
            return TYPE_BEAN_TWO;
        }
        if (_struct instanceof BeanThree) {
            return TYPE_BEAN_THREE;
        }
        if (_struct instanceof BeanFour) {
            return TYPE_BEAN_FOUR;
        }
        if (_struct instanceof BeanFive) {
            return TYPE_BEAN_FIVE;
        }
        if (_struct instanceof BeanSix) {
            return TYPE_BEAN_SIX;
        }
        if (_struct instanceof BeanSeven) {
            return TYPE_BEAN_SEVEN;
        }
        if (_struct instanceof BeanEight) {
            return TYPE_BEAN_EIGHT;
        }
        if (_struct instanceof Rate) {
            return TYPE_RATE;
        }
        if (_struct instanceof RateEq) {
            return TYPE_RATE_EQ;
        }
        if (_struct instanceof StringList) {
            return TYPE_STRING_LIST;
        }
        if (_struct instanceof Ints) {
            return TYPE_INTS;
        }
        if (_struct instanceof GeneObjects) {
            return TYPE_GENE_OBJECTS;
        }
        if (_struct instanceof GeneObjs) {
            return TYPE_GENE_OBJS;
        }
        if (_struct instanceof PickableList) {
            return TYPE_PICKABLE_LIST;
        }
        if (_struct instanceof SimpleDataBase) {
            return TYPE_SIMPLE_DATA_BASE;
        }
        return getAliasObject();
    }

    @Override
    public Struct wrapStd(Object _element) {
        if (_element == null) {
            return NullStruct.NULL_VALUE;
        }
        if (_element instanceof Byte) {
            return new ByteStruct((Byte) _element);
        }
        if (_element instanceof Short) {
            return new ShortStruct((Short) _element);
        }
        if (_element instanceof Character) {
            return new CharStruct((Character) _element);
        }
        if (_element instanceof Integer) {
            return new IntStruct((Integer) _element);
        }
        if (_element instanceof Long) {
            return new LongStruct((Long) _element);
        }
        if (_element instanceof Float) {
            return new FloatStruct((Float) _element);
        }
        if (_element instanceof Double) {
            return new DoubleStruct((Double) _element);
        }
        if (_element instanceof Boolean) {
            return BooleanStruct.of((Boolean) _element);
        }
        if (_element instanceof String) {
            return new StringStruct((String) _element);
        }
        if (_element instanceof StringBuilder) {
            return new StringBuilderStruct((StringBuilder) _element);
        }
        String aliasObject_ = getAliasObject();
        String className_ = getOtherBeanStructClassName(_element);
        if (StringUtil.quickEq(className_, getAliasObject())) {
            return StdStruct.newInstance(_element, aliasObject_);
        }
        return StdStruct.newInstance(_element, className_);
    }

    @Override
    protected Struct newId(Object _obj, String _className) {
        return StdStruct.newInstance(_obj, _className);
    }
    @Override
    public ResultErrorStd getStructToBeValidated(StringList _values,
                                                 String _className, Configuration _context, ContextEl _ctx, StackCall _stack) {
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(_className, TYPE_RATE)) {
            if (!Rate.matchesRate(_values.first())) {
                return res_;
            }
            res_.setResult(new StdStruct(new Rate(_values.first()), _className));
            return res_;
        }
        if (StringUtil.quickEq(_className, TYPE_ENUM_NUMBER)) {
            EnumNumber en_ = EnumNumber.getByName(_values.first());
            if (en_ != null) {
                res_.setResult(StdStruct.newInstance(en_, _className));
            }
            return res_;
        }
        if (StringUtil.quickEq(_className, TYPE_ENUM_NUMBERS)) {
            EnumNumbers list_ = new EnumNumbers();
            for (String s: _values) {
                list_.add(EnumNumber.getByName(s));
            }
            res_.setResult(new StdStruct(list_, _className));
            return res_;
        }
        return super.getStructToBeValidated(_values,_className,_context, _ctx, _stack);
    }
    @Override
    public ResultErrorStd getOtherName(ContextEl _cont, Struct _instance) {
        ResultErrorStd res_ = new ResultErrorStd();
        if (!(_instance instanceof RealInstanceStruct)) {
            Struct arg_ = Argument.getNull(_instance);
            res_.setResult(arg_);
            return res_;
        }
        if (((RealInstanceStruct)_instance).getInstance() instanceof EnumNumber) {
            res_.setResult(new StringStruct(((EnumNumber)((RealInstanceStruct)_instance).getInstance()).name()));
            return res_;
        }
        return res_;
    }
    protected Struct newSimpleBean(String _language, BeanInfo _bean, ContextEl _ctx, StackCall _stackCall) {
        ConstructorId id_ = new ConstructorId(_bean.getResolvedClassName(), new StringList(), false);
        ResultErrorStd res_ = ApplyCoreMethodUtil.newInstance(_ctx, id_, _stackCall, Argument.toArgArray(new CustList<Argument>()));
        Struct strBean_ = res_.getResult();
        BeanStruct str_ = (BeanStruct) strBean_;
        Bean bean_ = str_.getBean();
        bean_.setDataBase(dataBase);
        bean_.setForms(new StringMapObject());
        bean_.setLanguage(_language);
        bean_.setScope(_bean.getScope());
        return strBean_;
    }
    public void setDataBase(Composite _c) {
        dataBase = _c;
    }
}
