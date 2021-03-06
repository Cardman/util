package code.expressionlanguage.stds;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.CstFieldInfo;
import code.expressionlanguage.common.NumParsers;
import code.expressionlanguage.exec.*;
import code.expressionlanguage.exec.blocks.ExecOverridableBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.calls.util.CustomFoundExc;
import code.expressionlanguage.exec.calls.util.CustomFoundMethod;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.inherits.ExecInherits;
import code.expressionlanguage.exec.inherits.Parameters;
import code.expressionlanguage.exec.opers.ExecInvokingOperation;
import code.expressionlanguage.exec.util.ArgumentListCall;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.exec.util.ExecOverrideInfo;
import code.expressionlanguage.functionid.StdClassModifier;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.common.ClassArgumentMatching;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.MethodModifier;
import code.expressionlanguage.structs.*;
import code.maths.litteraladv.MaParser;
import code.maths.montecarlo.AbstractGenerator;
import code.maths.montecarlo.MonteCarloUtil;
import code.util.*;
import code.util.core.NumberUtil;
import code.util.core.StringUtil;

public final class AliasMathType {
    private String aliasAbs;
    private String aliasMax;
    private String aliasMin;
    private String aliasQuot;
    private String aliasMod;
    private String aliasMath;
    private String aliasBinQuot;
    private String aliasBinMod;
    private String aliasPlus;
    private String aliasMinus;
    private String aliasMult;
    private String aliasAnd;
    private String aliasOr;
    private String aliasXor;
    private String aliasNeg;
    private String aliasNegBin;
    private String aliasLt;
    private String aliasGt;
    private String aliasLe;
    private String aliasGe;
    private String aliasShiftLeft;
    private String aliasShiftRight;
    private String aliasBitShiftLeft;
    private String aliasBitShiftRight;
    private String aliasRotateLeft;
    private String aliasRotateRight;
    private String aliasRandom;
    private String aliasNativeRandom;
    private String aliasEval;
    private String aliasSeed;
    private String aliasSeedSpecGenerator;
    private String aliasSeedSpecDoubleGenerator;
    private final AliasParamMath params = new AliasParamMath();
    public void build(LgNames _stds) {
        CustList<CstFieldInfo> fields_;
        StringList params_;
        StandardMethod method_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<CstFieldInfo>();
        String aliasObject_ = _stds.getContent().getCoreNames().getAliasObject();
        String aliasPrimInteger_ = _stds.getContent().getPrimTypes().getAliasPrimInteger();
        String aliasPrimLong_ = _stds.getContent().getPrimTypes().getAliasPrimLong();
        String aliasPrimFloat_ = _stds.getContent().getPrimTypes().getAliasPrimFloat();
        String aliasPrimDouble_ = _stds.getContent().getPrimTypes().getAliasPrimDouble();
        String aliasPrimBoolean_ = _stds.getContent().getPrimTypes().getAliasPrimBoolean();
        StandardClass std_ = new StandardClass(aliasMath, fields_, constructors_, methods_, aliasObject_, StdClassModifier.HYPER_ABSTRACT);
        params_ = new StringList(aliasPrimInteger_);
        method_ = new StandardMethod(aliasAbs, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Abs0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_);
        method_ = new StandardMethod(aliasAbs, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Abs0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_);
        method_ = new StandardMethod(aliasAbs, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Abs0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_);
        method_ = new StandardMethod(aliasAbs, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3Abs0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasMax, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Max0(),params.getAliasMath0Max1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasMax, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Max0(),params.getAliasMath1Max1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_,aliasPrimFloat_);
        method_ = new StandardMethod(aliasMax, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Max0(),params.getAliasMath2Max1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasMax, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3Max0(),params.getAliasMath3Max1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasMin, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Min0(),params.getAliasMath0Min1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasMin, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Min0(),params.getAliasMath1Min1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_,aliasPrimFloat_);
        method_ = new StandardMethod(aliasMin, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Min0(),params.getAliasMath2Min1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasMin, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3Min0(),params.getAliasMath3Min1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasQuot, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Quot0(),params.getAliasMath0Quot1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasQuot, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Quot0(),params.getAliasMath1Quot1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasMod, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Mod0(),params.getAliasMath0Mod1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasMod, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Mod0(),params.getAliasMath1Mod1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Plus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Plus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Plus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3Plus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Minus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Minus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Minus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3Minus0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimBoolean_);
        method_ = new StandardMethod(aliasNeg, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Neg0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_);
        method_ = new StandardMethod(aliasNegBin, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0NegBin0()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_);
        method_ = new StandardMethod(aliasNegBin, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1NegBin0()));
        methods_.add( method_);
        _stds.getStandards().addEntry(aliasMath, std_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath4Plus0(),params.getAliasMath4Plus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath5Plus0(),params.getAliasMath5Plus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_,aliasPrimFloat_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath6Plus0(),params.getAliasMath6Plus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasPlus, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath7Plus0(),params.getAliasMath7Plus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath4Minus0(),params.getAliasMath4Minus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath5Minus0(),params.getAliasMath5Minus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_,aliasPrimFloat_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath6Minus0(),params.getAliasMath6Minus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasMinus, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath7Minus0(),params.getAliasMath7Minus1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasMult, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Mult0(),params.getAliasMath0Mult1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasMult, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Mult0(),params.getAliasMath1Mult1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_,aliasPrimFloat_);
        method_ = new StandardMethod(aliasMult, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Mult0(),params.getAliasMath2Mult1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasMult, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3Mult0(),params.getAliasMath3Mult1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasBinQuot, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0BinQuot0(),params.getAliasMath0BinQuot1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasBinQuot, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1BinQuot0(),params.getAliasMath1BinQuot1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_,aliasPrimFloat_);
        method_ = new StandardMethod(aliasBinQuot, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2BinQuot0(),params.getAliasMath2BinQuot1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasBinQuot, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3BinQuot0(),params.getAliasMath3BinQuot1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasBinMod, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0BinMod0(),params.getAliasMath0BinMod1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasBinMod, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1BinMod0(),params.getAliasMath1BinMod1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimFloat_,aliasPrimFloat_);
        method_ = new StandardMethod(aliasBinMod, params_, aliasPrimFloat_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2BinMod0(),params.getAliasMath2BinMod1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasBinMod, params_, aliasPrimDouble_, false, MethodModifier.STATIC,new StringList(params.getAliasMath3BinMod0(),params.getAliasMath3BinMod1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasAnd, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0And0(),params.getAliasMath0And1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasAnd, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1And0(),params.getAliasMath1And1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimBoolean_,aliasPrimBoolean_);
        method_ = new StandardMethod(aliasAnd, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2And0(),params.getAliasMath2And1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasOr, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Or0(),params.getAliasMath0Or1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasOr, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Or0(),params.getAliasMath1Or1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimBoolean_,aliasPrimBoolean_);
        method_ = new StandardMethod(aliasOr, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Or0(),params.getAliasMath2Or1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasXor, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Xor0(),params.getAliasMath0Xor1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasXor, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Xor0(),params.getAliasMath1Xor1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimBoolean_,aliasPrimBoolean_);
        method_ = new StandardMethod(aliasXor, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath2Xor0(),params.getAliasMath2Xor1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasShiftLeft, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0ShiftLeft0(),params.getAliasMath0ShiftLeft1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasShiftLeft, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1ShiftLeft0(),params.getAliasMath1ShiftLeft1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasShiftRight, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0ShiftRight0(),params.getAliasMath0ShiftRight1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasShiftRight, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1ShiftRight0(),params.getAliasMath1ShiftRight1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasBitShiftLeft, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0BitShiftLeft0(),params.getAliasMath0BitShiftLeft1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasBitShiftLeft, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1BitShiftLeft0(),params.getAliasMath1BitShiftLeft1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasBitShiftRight, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0BitShiftRight0(),params.getAliasMath0BitShiftRight1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasBitShiftRight, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1BitShiftRight0(),params.getAliasMath1BitShiftRight1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasRotateLeft, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0RotateLeft0(),params.getAliasMath0RotateLeft1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasRotateLeft, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1RotateLeft0(),params.getAliasMath1RotateLeft1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimInteger_,aliasPrimInteger_);
        method_ = new StandardMethod(aliasRotateRight, params_, aliasPrimInteger_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0RotateRight0(),params.getAliasMath0RotateRight1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasRotateRight, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1RotateRight0(),params.getAliasMath1RotateRight1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasLe, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Le0(),params.getAliasMath0Le1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasGe, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Ge0(),params.getAliasMath0Ge1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasLt, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Lt0(),params.getAliasMath0Lt1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_,aliasPrimLong_);
        method_ = new StandardMethod(aliasGt, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Gt0(),params.getAliasMath0Gt1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasLe, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Le0(),params.getAliasMath1Le1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasGe, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Ge0(),params.getAliasMath1Ge1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasLt, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Lt0(),params.getAliasMath1Lt1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimDouble_,aliasPrimDouble_);
        method_ = new StandardMethod(aliasGt, params_, aliasPrimBoolean_, false, MethodModifier.STATIC,new StringList(params.getAliasMath1Gt0(),params.getAliasMath1Gt1()));
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_);
        method_ = new StandardMethod(aliasRandom, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0Random0()));
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(aliasRandom, params_, aliasPrimDouble_, false, MethodModifier.STATIC);
        methods_.add( method_);
        params_ = new StringList(aliasPrimLong_);
        method_ = new StandardMethod(aliasNativeRandom, params_, aliasPrimLong_, false, MethodModifier.STATIC,new StringList(params.getAliasMath0NativeRandom0()));
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(aliasNativeRandom, params_, aliasPrimDouble_, false, MethodModifier.STATIC);
        methods_.add( method_);
        params_ = new StringList(aliasObject_);
        method_ = new StandardMethod(aliasSeed, params_, _stds.getContent().getCoreNames().getAliasVoid(), false, MethodModifier.STATIC,new StringList(params.getAliasMath0Seed0()));
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(aliasSeed, params_, aliasObject_, false, MethodModifier.STATIC);
        methods_.add( method_);
        params_ = new StringList(_stds.getContent().getPredefTypes().getAliasSeedGenerator());
        method_ = new StandardMethod(aliasSeedSpecGenerator, params_, _stds.getContent().getCoreNames().getAliasVoid(), false, MethodModifier.STATIC,new StringList(params.getAliasMath0SeedSpecGenerator0()));
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(aliasSeedSpecGenerator, params_, _stds.getContent().getPredefTypes().getAliasSeedGenerator(), false, MethodModifier.STATIC);
        methods_.add( method_);
        params_ = new StringList(_stds.getContent().getPredefTypes().getAliasSeedDoubleGenerator());
        method_ = new StandardMethod(aliasSeedSpecDoubleGenerator, params_, _stds.getContent().getCoreNames().getAliasVoid(), false, MethodModifier.STATIC,new StringList(params.getAliasMath0SeedSpecDoubleGenerator0()));
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(aliasSeedSpecDoubleGenerator, params_, _stds.getContent().getPredefTypes().getAliasSeedDoubleGenerator(), false, MethodModifier.STATIC);
        methods_.add( method_);
        params_ = new StringList(_stds.getContent().getCharSeq().getAliasString(),_stds.getContent().getCharSeq().getAliasReplacement());
        method_ = new StandardMethod(aliasEval, params_, _stds.getContent().getCharSeq().getAliasString(), true, MethodModifier.STATIC);
        methods_.add( method_);
    }
    public static ResultErrorStd invokeStdMethod(ContextEl _cont, ClassMethodId _method, StackCall _stackCall, Argument... _args) {
        ResultErrorStd result_ = new ResultErrorStd();
        Struct[] args_ = ExecHelper.getObjects(_args);
        String name_ = _method.getConstraints().getName();
//        StringList paramList_ = _method.getConstraints().getParametersTypes();
        LgNames lgNames_ = _cont.getStandards();
        AliasMathType am_ = lgNames_.getMathRef();
        String aliasPrimLong_ = lgNames_.getContent().getPrimTypes().getAliasPrimLong();
        String aliasPrimFloat_ = lgNames_.getContent().getPrimTypes().getAliasPrimFloat();
        String aliasPrimDouble_ = lgNames_.getContent().getPrimTypes().getAliasPrimDouble();
        if (StringUtil.quickEq(name_, lgNames_.getContent().getMathRef().getAliasAbs())) {
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimDouble_)) {
                result_.setResult(new DoubleStruct(Math.abs(NumParsers.convertToNumber(args_[0]).doubleStruct())));
                return result_;
            }
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimFloat_)) {
                result_.setResult(new FloatStruct(Math.abs(NumParsers.convertToNumber(args_[0]).floatStruct())));
                return result_;
            }
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimLong_)) {
                result_.setResult(new LongStruct(Math.abs(NumParsers.convertToNumber(args_[0]).longStruct())));
                return result_;
            }
            result_.setResult(new IntStruct(Math.abs(NumParsers.convertToNumber(args_[0]).intStruct())));
            return result_;
        }
        if (StringUtil.quickEq(name_, lgNames_.getContent().getMathRef().getAliasMax())) {
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimDouble_)) {
                result_.setResult(new DoubleStruct(Math.max(NumParsers.convertToNumber(args_[0]).doubleStruct(),NumParsers.convertToNumber(args_[1]).doubleStruct())));
                return result_;
            }
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimFloat_)) {
                result_.setResult(new FloatStruct(Math.max(NumParsers.convertToNumber(args_[0]).floatStruct(),NumParsers.convertToNumber(args_[1]).floatStruct())));
                return result_;
            }
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimLong_)) {
                result_.setResult(new LongStruct(Math.max(NumParsers.convertToNumber(args_[0]).longStruct(),NumParsers.convertToNumber(args_[1]).longStruct())));
                return result_;
            }
            result_.setResult(new IntStruct(Math.max(NumParsers.convertToNumber(args_[0]).intStruct(),NumParsers.convertToNumber(args_[1]).intStruct())));
            return result_;
        }
        if (StringUtil.quickEq(name_, lgNames_.getContent().getMathRef().getAliasMin())) {
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimDouble_)) {
                result_.setResult(new DoubleStruct(Math.min(NumParsers.convertToNumber(args_[0]).doubleStruct(),NumParsers.convertToNumber(args_[1]).doubleStruct())));
                return result_;
            }
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimFloat_)) {
                result_.setResult(new FloatStruct(Math.min(NumParsers.convertToNumber(args_[0]).floatStruct(),NumParsers.convertToNumber(args_[1]).floatStruct())));
                return result_;
            }
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimLong_)) {
                result_.setResult(new LongStruct(Math.min(NumParsers.convertToNumber(args_[0]).longStruct(),NumParsers.convertToNumber(args_[1]).longStruct())));
                return result_;
            }
            result_.setResult(new IntStruct(Math.min(NumParsers.convertToNumber(args_[0]).intStruct(),NumParsers.convertToNumber(args_[1]).intStruct())));
            return result_;
        }
        if (StringUtil.quickEq(name_, lgNames_.getContent().getMathRef().getAliasMod())) {
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimLong_)) {
                long num_ = NumParsers.convertToNumber(args_[0]).longStruct();
                long den_ = NumParsers.convertToNumber(args_[1]).longStruct();
                if (den_ == 0) {
                    _stackCall.setCallingState(new CustomFoundExc(getDivideZero(_cont, _stackCall)));
                    return result_;
                }
                result_.setResult(new LongStruct(NumberUtil.mod(num_, den_)));
                return result_;
            }
            int num_ = NumParsers.convertToNumber(args_[0]).intStruct();
            int den_ = NumParsers.convertToNumber(args_[1]).intStruct();
            if (den_ == 0) {
                _stackCall.setCallingState(new CustomFoundExc(getDivideZero(_cont, _stackCall)));
                return result_;
            }
            result_.setResult(new IntStruct(NumberUtil.mod(num_, den_)));
            return result_;
        }
        if (StringUtil.quickEq(name_, lgNames_.getContent().getMathRef().getAliasQuot())) {
            if (StringUtil.quickEq(_method.getConstraints().getParametersType(0), aliasPrimLong_)) {
                long num_ = NumParsers.convertToNumber(args_[0]).longStruct();
                long den_ = NumParsers.convertToNumber(args_[1]).longStruct();
                if (den_ == 0) {
                    _stackCall.setCallingState(new CustomFoundExc(getDivideZero(_cont, _stackCall)));
                    return result_;
                }
                result_.setResult(new LongStruct(NumberUtil.quot(num_, den_)));
                return result_;
            }
            int num_ = NumParsers.convertToNumber(args_[0]).intStruct();
            int den_ = NumParsers.convertToNumber(args_[1]).intStruct();
            if (den_ == 0) {
                _stackCall.setCallingState(new CustomFoundExc(getDivideZero(_cont, _stackCall)));
                return result_;
            }
            result_.setResult(new IntStruct(NumberUtil.quot(num_, den_)));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasPlus)) {
            if (_method.getConstraints().getParametersTypesLength() == 1) {
                result_.setResult(args_[0]);
                return result_;
            }
            result_.setResult(NumParsers.calculateSum(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]),
                    ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasMinus)) {
            if (_method.getConstraints().getParametersTypesLength() != 1) {
                result_.setResult(NumParsers.calculateDiff(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]),
                        ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
                return result_;
            }
            NumberStruct b_ = NumParsers.convertToNumber(_args[0].getStruct());
            byte cast_ = ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes());
            result_.setResult(NumParsers.opposite(b_,cast_));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasMult)) {
            result_.setResult(NumParsers.calculateMult(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]),
                    ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasBinMod)) {
            Struct arg_ = NumParsers.calculateMod(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]),
                    ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes()));
            if (arg_ == NullStruct.NULL_VALUE) {
                _stackCall.setCallingState(new CustomFoundExc(getDivideZero(_cont, _stackCall)));
                return result_;
            }
            result_.setResult(arg_);
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasBinQuot)) {
            Struct arg_ = NumParsers.calculateDiv(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]),
                    ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes()));
            if (arg_ == NullStruct.NULL_VALUE) {
                _stackCall.setCallingState(new CustomFoundExc(getDivideZero(_cont, _stackCall)));
                return result_;
            }
            result_.setResult(arg_);
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasNegBin)) {
            byte cast_ = ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes());
            result_.setResult(NumParsers.negBinNumber(NumParsers.convertToNumber(_args[0].getStruct()),cast_));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasNeg)) {
            result_.setResult(NumParsers.convertToBoolean(_args[0].getStruct()).neg());
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasAnd)) {
            result_.setResult(NumParsers.calculateAnd(args_[0], args_[1], ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasOr)) {
            result_.setResult(NumParsers.calculateOr(args_[0], args_[1], ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasXor)) {
            result_.setResult(NumParsers.calculateXor(args_[0], args_[1], ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasLt)) {
            result_.setResult(NumParsers.quickCalculateLowerNb(args_[0], args_[1]));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasGt)) {
            result_.setResult(NumParsers.quickCalculateGreaterNb(args_[0], args_[1]));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasLe)) {
            if (NumParsers.sameValue(args_[0],args_[1])) {
                result_.setResult(BooleanStruct.of(true));
                return result_;
            }
            result_.setResult(NumParsers.quickCalculateLowerNb(args_[0], args_[1]));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasGe)) {
            if (NumParsers.sameValue(args_[0],args_[1])) {
                result_.setResult(BooleanStruct.of(true));
                return result_;
            }
            result_.setResult(NumParsers.quickCalculateGreaterNb(args_[0], args_[1]));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasShiftLeft)) {
            result_.setResult(NumParsers.calculateShiftLeft(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]), ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasShiftRight)) {
            result_.setResult(NumParsers.calculateShiftRight(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]), ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasBitShiftLeft)) {
            result_.setResult(NumParsers.calculateBitShiftLeft(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]), ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasBitShiftRight)) {
            result_.setResult(NumParsers.calculateBitShiftRight(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]), ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasRotateLeft)) {
            result_.setResult(NumParsers.calculateRotateLeft(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]), ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (StringUtil.quickEq(name_, am_.aliasRotateRight)) {
            result_.setResult(NumParsers.calculateRotateRight(NumParsers.convertToNumber(args_[0]), NumParsers.convertToNumber(args_[1]), ClassArgumentMatching.getPrimitiveCast(_method.getConstraints().getParametersType(0), lgNames_.getPrimTypes())));
            return result_;
        }
        if (_stackCall.getInitializingTypeInfos().isInitEnums()) {
            _stackCall.getInitializingTypeInfos().failInitEnums();
            return result_;
        }
        if (StringUtil.quickEq(_method.getConstraints().getName(), lgNames_.getContent().getMathRef().getAliasSeed())) {
            if (_method.getConstraints().getParametersTypesLength() == 0) {
                Struct seed_ = _stackCall.getSeed();
                result_.setResult(seed_);
                return result_;
            }
            _stackCall.setSeed(_args[0].getStruct());
            result_.setResult(NullStruct.NULL_VALUE);
            return result_;
        }
        if (StringUtil.quickEq(_method.getConstraints().getName(), lgNames_.getContent().getMathRef().getAliasSeedSpecGenerator())) {
            if (_method.getConstraints().getParametersTypesLength() == 0) {
                Struct seed_ = _stackCall.getSeedSpecGenerator();
                result_.setResult(seed_);
                return result_;
            }
            _stackCall.setSeedSpecGenerator(_args[0].getStruct());
            result_.setResult(NullStruct.NULL_VALUE);
            return result_;
        }
        if (StringUtil.quickEq(_method.getConstraints().getName(), lgNames_.getContent().getMathRef().getAliasSeedSpecDoubleGenerator())) {
            if (_method.getConstraints().getParametersTypesLength() == 0) {
                Struct seed_ = _stackCall.getSeedSpecDoubleGenerator();
                result_.setResult(seed_);
                return result_;
            }
            _stackCall.setSeedSpecDoubleGenerator(_args[0].getStruct());
            result_.setResult(NullStruct.NULL_VALUE);
            return result_;
        }
        if (StringUtil.quickEq(_method.getConstraints().getName(), lgNames_.getContent().getMathRef().getAliasEval())) {
            eval(args_[0],args_[1],result_,_cont);
            return result_;
        }
        if (StringUtil.quickEq(_method.getConstraints().getName(), lgNames_.getContent().getMathRef().getAliasNativeRandom())) {
            AbstractGenerator generator_ = lgNames_.getGenerator();
            if (_method.getConstraints().getParametersTypesLength() == 0) {
                result_.setResult(new DoubleStruct(generator_.pick()));
                return result_;
            }
            result_.setResult(new LongStruct(MonteCarloUtil.randomLong(NumParsers.convertToNumber(args_[0]).longStruct(),generator_)));
            return result_;
        }
        if (_method.getConstraints().getParametersTypesLength() == 0) {
            return random(_cont, result_, _stackCall);
        }
        return randomParam(_cont, result_, args_, _stackCall);
    }

    private static ErrorStruct getDivideZero(ContextEl _cont, StackCall _stackCall) {
        return new ErrorStruct(_cont, _cont.getStandards().getContent().getCoreNames().getAliasDivisionZero(), _stackCall);
    }

    private static void eval(Struct _st, Struct _seps, ResultErrorStd _res, ContextEl _context) {
        LgNames lgNames_ = _context.getStandards();
        AbstractGenerator generator_ = lgNames_.getGenerator();
        String val_ = NumParsers.getStringValue(_st);
        CustList<Replacement> repls_ = NumParsers.getReplValue(_seps);
        _res.setResult(new StringStruct(MaParser.processEl(generator_,val_,repls_)));
    }
    private static ResultErrorStd random(ContextEl _cont, ResultErrorStd _result, StackCall _stackCall) {
        LgNames lgNames_ = _cont.getStandards();
        Struct seedSpec_ = _stackCall.getSeedSpecDoubleGenerator();
        Argument argSeedSpec_ = new Argument(seedSpec_);
        ExecTypeFunction p_ = new ExecTypeFunction((ExecRootBlock)null,null);
        CustList<Argument> argsToPass_ = new CustList<Argument>();
        ExecFormattedRootBlock cl_ = ExecFormattedRootBlock.defValue();
        if (matchNotNull(_cont, seedSpec_, lgNames_.getContent().getPredefTypes().getAliasSeedDoubleGenerator())) {
            String argClassName_ = seedSpec_.getClassName(_cont);
            Classes classes_ = _cont.getClasses();
            ExecOverrideInfo polymorphMeth_ = ExecInvokingOperation.polymorph(_cont, seedSpec_,classes_.getSeedDoubleGeneratorPair());
            p_ = polymorphMeth_.getPair();
            cl_ = ExecFormattedRootBlock.getFullObject(argClassName_, polymorphMeth_.getClassName(), _cont);
        }
        if (p_.getFct() instanceof ExecOverridableBlock) {
            ExecOverridableBlock meth_ = (ExecOverridableBlock)p_.getFct();
            if (seedSpec_ instanceof AbstractFunctionalInstance && ((AbstractFunctionalInstance)seedSpec_).getNamed() == meth_) {
                Argument fct_ = new Argument(((AbstractFunctionalInstance)seedSpec_).getFunctional());
                _result.setResult(ExecInvokingOperation.prepareCallDynReflect(fct_,argsToPass_,_cont, _stackCall).getStruct());
                return _result;
            }
            _stackCall.setCallingState(new CustomFoundMethod(argSeedSpec_,cl_, p_, new Parameters()));
            return _result;
        }
        Struct seed_ = _stackCall.getSeed();
        Argument argSeed_ = new Argument(seed_);
        if (matchNotNull(_cont, seed_, lgNames_.getContent().getPredefTypes().getAliasSeedDoubleGenerator())) {
            String argClassName_ = seed_.getClassName(_cont);
            Classes classes_ = _cont.getClasses();
            ExecOverrideInfo polymorphMeth_ = ExecInvokingOperation.polymorph(_cont, seed_,classes_.getSeedDoubleGeneratorPair());
            p_ = polymorphMeth_.getPair();
            cl_ = ExecFormattedRootBlock.getFullObject(argClassName_, polymorphMeth_.getClassName(), _cont);
        }
        if (p_.getFct() instanceof ExecOverridableBlock) {
            ExecOverridableBlock meth_ = (ExecOverridableBlock)p_.getFct();
            if (seed_ instanceof AbstractFunctionalInstance && ((AbstractFunctionalInstance)seed_).getNamed() == meth_) {
                Argument fct_ = new Argument(((AbstractFunctionalInstance)seed_).getFunctional());
                _result.setResult(ExecInvokingOperation.prepareCallDynReflect(fct_,argsToPass_,_cont, _stackCall).getStruct());
                return _result;
            }
            _stackCall.setCallingState(new CustomFoundMethod(argSeed_,cl_, p_, new Parameters()));
            return _result;
        }
        AbstractGenerator generator_ = lgNames_.getGenerator();
        _result.setResult(new DoubleStruct(generator_.pick()));
        return _result;
    }

    private static ResultErrorStd randomParam(ContextEl _cont, ResultErrorStd _result, Struct[] _args, StackCall _stackCall) {
        LgNames lgNames_ = _cont.getStandards();
        Struct seedSpec_ = _stackCall.getSeedSpecGenerator();
        Argument argSeedSpec_ = new Argument(seedSpec_);
        ExecTypeFunction p_ = new ExecTypeFunction((ExecRootBlock)null,null);
        CustList<Argument> argsToPass_ = new CustList<Argument>();
        ExecFormattedRootBlock cl_ = new ExecFormattedRootBlock((ExecRootBlock) null,"");
        if (matchNotNull(_cont, seedSpec_, lgNames_.getContent().getPredefTypes().getAliasSeedGenerator())) {
            String argClassName_ = seedSpec_.getClassName(_cont);
            Classes classes_ = _cont.getClasses();
            ExecOverrideInfo polymorphMeth_ = ExecInvokingOperation.polymorph(_cont, seedSpec_, classes_.getSeedGeneratorPair());
            p_ = polymorphMeth_.getPair();
            cl_ = ExecFormattedRootBlock.getFullObject(argClassName_, polymorphMeth_.getClassName(), _cont);
            argsToPass_.add(new Argument(_args[0]));
        }
        if (p_.getFct() instanceof ExecOverridableBlock) {
            ExecOverridableBlock meth_ = (ExecOverridableBlock)p_.getFct();
            if (seedSpec_ instanceof AbstractFunctionalInstance && ((AbstractFunctionalInstance)seedSpec_).getNamed() == meth_) {
                Argument fct_ = new Argument(((AbstractFunctionalInstance)seedSpec_).getFunctional());
                _result.setResult(ExecInvokingOperation.prepareCallDynReflect(fct_,argsToPass_,_cont, _stackCall).getStruct());
                return _result;
            }
            ArgumentListCall argList_ = new ArgumentListCall(argsToPass_);
            ExecTemplates.wrapAndCall(p_, cl_,argSeedSpec_, _cont, _stackCall, argList_);
            return _result;
        }
        argsToPass_.clear();
        Struct seed_ = _stackCall.getSeed();
        Argument argSeed_ = new Argument(seed_);
        if (matchNotNull(_cont, seed_, lgNames_.getContent().getPredefTypes().getAliasSeedGenerator())) {
            String argClassName_ = seed_.getClassName(_cont);
            Classes classes_ = _cont.getClasses();
            ExecOverrideInfo polymorphMeth_ = ExecInvokingOperation.polymorph(_cont, seed_, classes_.getSeedGeneratorPair());
            p_ = polymorphMeth_.getPair();
            cl_ = ExecFormattedRootBlock.getFullObject(argClassName_, polymorphMeth_.getClassName(), _cont);
            argsToPass_.add(new Argument(_args[0]));
        }
        if (p_.getFct() instanceof ExecOverridableBlock) {
            ExecOverridableBlock meth_ = (ExecOverridableBlock)p_.getFct();
            if (seed_ instanceof AbstractFunctionalInstance && ((AbstractFunctionalInstance)seed_).getNamed() == meth_) {
                Argument fct_ = new Argument(((AbstractFunctionalInstance)seed_).getFunctional());
                _result.setResult(ExecInvokingOperation.prepareCallDynReflect(fct_,argsToPass_,_cont, _stackCall).getStruct());
                return _result;
            }
            ArgumentListCall argList_ = new ArgumentListCall(argsToPass_);
            ExecTemplates.wrapAndCall(p_, cl_,argSeed_, _cont, _stackCall, argList_);
            return _result;
        }
        AbstractGenerator generator_ = lgNames_.getGenerator();
        _result.setResult(new LongStruct(MonteCarloUtil.randomLong(NumParsers.convertToNumber(_args[0]).longStruct(),generator_)));
        return _result;
    }

    private static boolean matchNotNull(ContextEl _cont, Struct _seed, String _type) {
        return _seed != NullStruct.NULL_VALUE
                && ExecInherits.safeObject(_type, Argument.getNull(_seed).getClassName(_cont), _cont) == ErrorType.NOTHING;
    }

    public String getAliasAbs() {
        return aliasAbs;
    }
    public void setAliasAbs(String _aliasAbs) {
        aliasAbs = _aliasAbs;
    }

    public String getAliasMax() {
        return aliasMax;
    }

    public void setAliasMax(String _aliasMax) {
        this.aliasMax = _aliasMax;
    }

    public String getAliasMin() {
        return aliasMin;
    }

    public void setAliasMin(String _aliasMin) {
        this.aliasMin = _aliasMin;
    }

    public String getAliasQuot() {
        return aliasQuot;
    }
    public void setAliasQuot(String _aliasQuot) {
        aliasQuot = _aliasQuot;
    }
    public String getAliasMod() {
        return aliasMod;
    }
    public void setAliasMod(String _aliasMod) {
        aliasMod = _aliasMod;
    }
    public String getAliasMath() {
        return aliasMath;
    }
    public void setAliasMath(String _aliasMath) {
        aliasMath = _aliasMath;
    }
    public String getAliasBinQuot() {
        return aliasBinQuot;
    }
    public void setAliasBinQuot(String _aliasBinQuot) {
        aliasBinQuot = _aliasBinQuot;
    }
    public String getAliasBinMod() {
        return aliasBinMod;
    }
    public void setAliasBinMod(String _aliasBinMod) {
        aliasBinMod = _aliasBinMod;
    }
    public String getAliasPlus() {
        return aliasPlus;
    }
    public void setAliasPlus(String _aliasPlus) {
        aliasPlus = _aliasPlus;
    }
    public String getAliasMinus() {
        return aliasMinus;
    }
    public void setAliasMinus(String _aliasMinus) {
        aliasMinus = _aliasMinus;
    }
    public String getAliasMult() {
        return aliasMult;
    }
    public void setAliasMult(String _aliasMult) {
        aliasMult = _aliasMult;
    }
    public String getAliasAnd() {
        return aliasAnd;
    }
    public void setAliasAnd(String _aliasAnd) {
        aliasAnd = _aliasAnd;
    }
    public String getAliasOr() {
        return aliasOr;
    }
    public void setAliasOr(String _aliasOr) {
        aliasOr = _aliasOr;
    }
    public String getAliasXor() {
        return aliasXor;
    }
    public void setAliasXor(String _aliasXor) {
        aliasXor = _aliasXor;
    }
    public String getAliasNegBin() {
        return aliasNegBin;
    }
    public void setAliasNegBin(String _aliasNegBin) {
        aliasNegBin = _aliasNegBin;
    }
    public String getAliasNeg() {
        return aliasNeg;
    }
    public void setAliasNeg(String _aliasNeg) {
        aliasNeg = _aliasNeg;
    }
    public String getAliasLt() {
        return aliasLt;
    }
    public void setAliasLt(String _aliasLt) {
        aliasLt = _aliasLt;
    }
    public String getAliasGt() {
        return aliasGt;
    }
    public void setAliasGt(String _aliasGt) {
        aliasGt = _aliasGt;
    }
    public String getAliasLe() {
        return aliasLe;
    }
    public void setAliasLe(String _aliasLe) {
        aliasLe = _aliasLe;
    }
    public String getAliasGe() {
        return aliasGe;
    }
    public void setAliasGe(String _aliasGe) {
        aliasGe = _aliasGe;
    }
    public String getAliasShiftLeft() {
        return aliasShiftLeft;
    }
    public void setAliasShiftLeft(String _aliasShiftLeft) {
        aliasShiftLeft = _aliasShiftLeft;
    }
    public String getAliasShiftRight() {
        return aliasShiftRight;
    }
    public void setAliasShiftRight(String _aliasShiftRight) {
        aliasShiftRight = _aliasShiftRight;
    }
    public String getAliasBitShiftLeft() {
        return aliasBitShiftLeft;
    }
    public void setAliasBitShiftLeft(String _aliasBitShiftLeft) {
        aliasBitShiftLeft = _aliasBitShiftLeft;
    }
    public String getAliasBitShiftRight() {
        return aliasBitShiftRight;
    }
    public void setAliasBitShiftRight(String _aliasBitShiftRight) {
        aliasBitShiftRight = _aliasBitShiftRight;
    }
    public String getAliasRotateLeft() {
        return aliasRotateLeft;
    }
    public void setAliasRotateLeft(String _aliasRotateLeft) {
        aliasRotateLeft = _aliasRotateLeft;
    }
    public String getAliasRotateRight() {
        return aliasRotateRight;
    }
    public void setAliasRotateRight(String _aliasRotateRight) {
        aliasRotateRight = _aliasRotateRight;
    }

    public String getAliasRandom() {
        return aliasRandom;
    }

    public void setAliasRandom(String _aliasRandom) {
        aliasRandom = _aliasRandom;
    }

    public String getAliasNativeRandom() {
        return aliasNativeRandom;
    }

    public void setAliasNativeRandom(String _aliasNativeRandom) {
        this.aliasNativeRandom = _aliasNativeRandom;
    }

    public String getAliasEval() {
        return aliasEval;
    }

    public void setAliasEval(String _aliasEval) {
        this.aliasEval = _aliasEval;
    }

    public String getAliasSeed() {
        return aliasSeed;
    }

    public void setAliasSeed(String _aliasSeed) {
        this.aliasSeed = _aliasSeed;
    }

    public String getAliasSeedSpecGenerator() {
        return aliasSeedSpecGenerator;
    }

    public void setAliasSeedSpecGenerator(String _aliasSeedSpecGenerator) {
        this.aliasSeedSpecGenerator = _aliasSeedSpecGenerator;
    }

    public String getAliasSeedSpecDoubleGenerator() {
        return aliasSeedSpecDoubleGenerator;
    }

    public void setAliasSeedSpecDoubleGenerator(String _aliasSeedSpecDoubleGenerator) {
        this.aliasSeedSpecDoubleGenerator = _aliasSeedSpecDoubleGenerator;
    }

    public AliasParamMath getParams() {
        return params;
    }
}
