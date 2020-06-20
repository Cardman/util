package cards.president.beans;

import code.bean.Bean;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.functionid.MethodId;
import code.expressionlanguage.functionid.MethodModifier;
import code.expressionlanguage.stds.ResultErrorStd;
import code.expressionlanguage.stds.StandardClass;
import code.expressionlanguage.stds.StandardConstructor;
import code.expressionlanguage.stds.StandardField;
import code.expressionlanguage.stds.StandardMethod;
import code.expressionlanguage.structs.*;
import code.bean.BeanStruct;
import code.bean.RealInstanceStruct;
import code.bean.nat.BeanNatLgNames;
import code.bean.nat.DefaultInitialization;
import code.util.*;
import code.util.ints.SimpleEntries;
import code.util.ints.SimpleEntry;
import code.util.ints.SimpleList;

public final class PresidentStandards extends BeanNatLgNames {

    private static final String SAME_AMOUNT = "sameAmount";
    private static final String NB_CARDS_PER_PLAYER_MAX = "nbCardsPerPlayerMax";
    private static final String NB_CARDS_PER_PLAYER_MIN = "nbCardsPerPlayerMin";
    private static final String NB_STACKS = "nbStacks";
    private static final String NB_PLAYERS = "nbPlayers";
    private static final String LOOSER_STARTS_FIRST = "looserStartsFirst";
    private static final String SWITCH_CARDS = "switchCards";
    private static final String LOOSING_IF_FINISH_BY_BEST_CARDS = "loosingIfFinishByBestCards";
    private static final String HAS_TO_PLAY = "hasToPlay";
    private static final String POSSIBLE_REVERSING = "possibleReversing";
    private static final String EQUALTY = "equalty";
    private static final String CARTES_BATTUES = "cartesBattues";
    private static final String SCORES = "scores";
    private static final String NUMBER = "number";
    private static final String LINES_DEAL = "linesDeal";
    private static final String NICKNAMES = "nicknames";
    private static final String TYPE_RULES_PRESIDENT = "RulesPresident";
    private static final String TYPE_RESULTS_PRESIDENT = "ResultsPresident";
    private static final String TYPE_RULES_PRESIDENT_BEAN = "cards.president.beans.RulesPresidentBean";
    private static final String TYPE_LINE_DEAL = "cards.president.beans.LineDeal";
    private static final String TYPE_PRESIDENT_BEAN = "cards.president.beans.PresidentBean";
    public PresidentStandards() {
        DefaultInitialization.basicStandards(this);
    }
    @Override
    public void buildOther() {
        buildBeans();
        StringMap<StandardField> fields_;
        StandardClass std_;
        ObjectMap<MethodId, StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        StandardMethod method_;
        StringList params_;
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        fields_ = new StringMap<StandardField>();
        constructors_ = new CustList<StandardConstructor>();
        std_ = new StandardClass(TYPE_PRESIDENT_BEAN, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.put(NICKNAMES, new StandardField(NICKNAMES, TYPE_LIST, false, false, std_));
        fields_.put(LINES_DEAL, new StandardField(LINES_DEAL, TYPE_LIST, false, false, std_));
        getStandards().put(TYPE_PRESIDENT_BEAN, std_);
        fields_ = new StringMap<StandardField>();
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        std_ = new StandardClass(TYPE_LINE_DEAL, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        fields_.put(NUMBER, new StandardField(NUMBER, getAliasPrimInteger(), false, false, std_));
        fields_.put(SCORES, new StandardField(SCORES, TYPE_LIST, false, false, std_));
        getStandards().put(TYPE_LINE_DEAL, std_);
        fields_ = new StringMap<StandardField>();
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        std_ = new StandardClass(TYPE_RULES_PRESIDENT_BEAN, fields_, constructors_, methods_, TYPE_BEAN, MethodModifier.FINAL);
        fields_.put(CARTES_BATTUES, new StandardField(CARTES_BATTUES, getAliasString(), false, false, std_));
        fields_.put(EQUALTY, new StandardField(EQUALTY, getAliasString(), false, false, std_));
        fields_.put(POSSIBLE_REVERSING, new StandardField(POSSIBLE_REVERSING, getAliasPrimBoolean(), false, false, std_));
        fields_.put(HAS_TO_PLAY, new StandardField(HAS_TO_PLAY, getAliasPrimBoolean(), false, false, std_));
        fields_.put(LOOSING_IF_FINISH_BY_BEST_CARDS, new StandardField(LOOSING_IF_FINISH_BY_BEST_CARDS, getAliasPrimBoolean(), false, false, std_));
        fields_.put(SWITCH_CARDS, new StandardField(SWITCH_CARDS, getAliasPrimBoolean(), false, false, std_));
        fields_.put(LOOSER_STARTS_FIRST, new StandardField(LOOSER_STARTS_FIRST, getAliasPrimBoolean(), false, false, std_));
        fields_.put(NB_PLAYERS, new StandardField(NB_PLAYERS, getAliasPrimInteger(), false, false, std_));
        fields_.put(NB_STACKS, new StandardField(NB_STACKS, getAliasPrimInteger(), false, false, std_));
        fields_.put(NB_CARDS_PER_PLAYER_MIN, new StandardField(NB_CARDS_PER_PLAYER_MIN, getAliasPrimByte(), false, false, std_));
        fields_.put(NB_CARDS_PER_PLAYER_MAX, new StandardField(NB_CARDS_PER_PLAYER_MAX, getAliasPrimByte(), false, false, std_));
        params_ = new StringList();
        method_ = new StandardMethod(SAME_AMOUNT, params_, getAliasPrimBoolean(), false, MethodModifier.NORMAL,std_);
        methods_.put(method_.getId(), method_);
        getStandards().put(TYPE_RULES_PRESIDENT_BEAN, std_);
        fields_ = new StringMap<StandardField>();
        constructors_ = new CustList<StandardConstructor>();
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        std_ = new StandardClass(TYPE_RESULTS_PRESIDENT, fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        getStandards().put(TYPE_RESULTS_PRESIDENT, std_);
        fields_ = new StringMap<StandardField>();
        constructors_ = new CustList<StandardConstructor>();
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        std_ = new StandardClass(TYPE_RULES_PRESIDENT, fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        getStandards().put(TYPE_RULES_PRESIDENT, std_);
    }
    @Override
    public ResultErrorStd getOtherResultBean(ContextEl _cont,
                                             ConstructorId _method, Object... _args) {
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringList.quickEq(_method.getName(), TYPE_PRESIDENT_BEAN)) {
            PresidentBean details_ = new PresidentBean();
            details_.setClassName(TYPE_PRESIDENT_BEAN);
            res_.setResult(new BeanStruct(details_));
            return res_;
        }
        if (StringList.quickEq(_method.getName(), TYPE_RULES_PRESIDENT_BEAN)) {
            RulesPresidentBean details_ = new RulesPresidentBean();
            details_.setClassName(TYPE_RULES_PRESIDENT_BEAN);
            res_.setResult(new BeanStruct(details_));
            return res_;
        }
        return res_;
    }
    @Override
    public ResultErrorStd getOtherResult(ContextEl _cont, ClassField _classField, Struct _instance) {
        ResultErrorStd res_ = new ResultErrorStd();
        String fieldName_ = _classField.getFieldName();
        if (((RealInstanceStruct)_instance).getInstance() instanceof PresidentBean) {
            if (StringList.quickEq(fieldName_, NICKNAMES)) {
                res_.setResult(new DefaultStruct(((PresidentBean)((RealInstanceStruct)_instance).getInstance()).getNicknames(), TYPE_LIST));
                return res_;
            }
            if (StringList.quickEq(fieldName_, LINES_DEAL)) {
                res_.setResult(new DefaultStruct(((PresidentBean)((RealInstanceStruct)_instance).getInstance()).getLinesDeal(), TYPE_LIST));
                return res_;
            }
        }
        if (((RealInstanceStruct)_instance).getInstance() instanceof LineDeal) {
            if (StringList.quickEq(fieldName_, SCORES)) {
                res_.setResult(DefaultStruct.newListLong(((LineDeal)((RealInstanceStruct)_instance).getInstance()).getScores(), TYPE_LIST));
                return res_;
            }
            if (StringList.quickEq(fieldName_, NUMBER)) {
                res_.setResult(new IntStruct(((LineDeal)((RealInstanceStruct)_instance).getInstance()).getNumber()));
                return res_;
            }
        }
        if (((RealInstanceStruct)_instance).getInstance() instanceof RulesPresidentBean) {
            RulesPresidentBean rules_ = (RulesPresidentBean) ((RealInstanceStruct)_instance).getInstance();
            if (StringList.quickEq(fieldName_, NB_PLAYERS)) {
                res_.setResult(new IntStruct(rules_.getNbPlayers()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, NB_STACKS)) {
                res_.setResult(new IntStruct(rules_.getNbPlayers()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, NB_CARDS_PER_PLAYER_MIN)) {
                res_.setResult(new ByteStruct(rules_.getNbCardsPerPlayerMin()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, NB_CARDS_PER_PLAYER_MAX)) {
                res_.setResult(new ByteStruct(rules_.getNbCardsPerPlayerMax()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, CARTES_BATTUES)) {
                res_.setResult(new StringStruct(rules_.getCartesBattues()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, EQUALTY)) {
                res_.setResult(new StringStruct(rules_.getEqualty()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, POSSIBLE_REVERSING)) {
                res_.setResult(BooleanStruct.of(rules_.isPossibleReversing()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, HAS_TO_PLAY)) {
                res_.setResult(BooleanStruct.of(rules_.isHasToPlay()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, LOOSING_IF_FINISH_BY_BEST_CARDS)) {
                res_.setResult(BooleanStruct.of(rules_.isLoosingIfFinishByBestCards()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, SWITCH_CARDS)) {
                res_.setResult(BooleanStruct.of(rules_.isSwitchCards()));
                return res_;
            }
            if (StringList.quickEq(fieldName_, LOOSER_STARTS_FIRST)) {
                res_.setResult(BooleanStruct.of(rules_.isLooserStartsFirst()));
                return res_;
            }
        }
        return res_;
    }
    @Override
    public ResultErrorStd getOtherResultBean(ContextEl _cont, Struct _instance,
            ClassMethodId _method, Object... _args) {
        ResultErrorStd res_ = new ResultErrorStd();
        if (((RealInstanceStruct)_instance).getInstance() instanceof RulesPresidentBean) {
            if (StringList.quickEq(_method.getConstraints().getName(), SAME_AMOUNT)) {
                res_.setResult(BooleanStruct.of(((RulesPresidentBean)((RealInstanceStruct)_instance).getInstance()).sameAmount()));
                return res_;
            }
        }
        return res_;
    }

    private String getOtherBeanStructClassName(Object _struct) {
        if (_struct instanceof LineDeal) {
            return TYPE_LINE_DEAL;
        }
        if (_struct instanceof Bean) {
            return ((Bean)_struct).getClassName();
        }
        if (_struct instanceof SimpleList) {
            return TYPE_LIST;
        }
        if (_struct instanceof SimpleEntries) {
            return TYPE_MAP;
        }
        if (_struct instanceof SimpleEntry) {
            return TYPE_ENTRY;
        }
        if (_struct instanceof SimpleItr) {
            return TYPE_ITERATOR;
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
        String className_ = getOtherBeanStructClassName(_element);
        return DefaultStruct.newInstance(_element, className_);
    }

    @Override
    protected Struct newId(Object _obj, String _className) {
        return DefaultStruct.newInstance(_obj, _className);
    }
    public ResultErrorStd getOtherName(ContextEl _cont, Struct _instance) {
        return new ResultErrorStd();
    }
    public ResultErrorStd setOtherResult(ContextEl _cont, ClassField _classField, Struct _instance, Object _value) {
        return new ResultErrorStd();
    }

}
