package code.formathtml.exec.blocks;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.exec.variables.LocalVariable;
import code.formathtml.Configuration;
import code.formathtml.exec.RendStackCall;
import code.formathtml.exec.RenderExpUtil;
import code.formathtml.exec.opers.RendDynOperationNode;
import code.formathtml.stacks.RendReadWrite;
import code.formathtml.util.*;
import code.sml.*;
import code.util.*;
import code.util.core.StringUtil;

public final class RendSelect extends RendParentBlock implements RendWithEl {
    private CustList<RendDynOperationNode> opsRead = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsValue = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsWrite = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsMap = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsDefault = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsConverter = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsConverterField = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsConverterFieldValue = new CustList<RendDynOperationNode>();
    private StringMap<ExecTextPart> execAttributesText = new StringMap<ExecTextPart>();
    private StringMap<ExecTextPart> execAttributes = new StringMap<ExecTextPart>();
    private String varName = EMPTY_STRING;
    private InputInfo varNames = new InputInfo();
    private String id = EMPTY_STRING;
    private String idClass = EMPTY_STRING;
    private String idName = EMPTY_STRING;
    private final Element elt;
    private final boolean multiple;
    private String varNameConverter = EMPTY_STRING;
    private String varNameConverterField = EMPTY_STRING;
    private String varNameConverterFieldValue = EMPTY_STRING;
    private String className = EMPTY_STRING;
    private final boolean arrayConverter;

    public RendSelect(CustList<RendDynOperationNode> _opsRead, CustList<RendDynOperationNode> _opsValue, CustList<RendDynOperationNode> _opsWrite,
                      CustList<RendDynOperationNode> _opsMap, CustList<RendDynOperationNode> _opsDefault, CustList<RendDynOperationNode> _opsConverter,
                      CustList<RendDynOperationNode> _opsConverterField, CustList<RendDynOperationNode> _opsConverterFieldValue,
                      StringMap<ExecTextPart> _execAttributesText, StringMap<ExecTextPart> _execAttributes,
                      String _varName, String _id, String _idClass, String _idName, Element _elt, boolean _multiple,
                      String _varNameConverter, String _varNameConverterField, String _varNameConverterFieldValue,
                      String _className, boolean _arrayConverter, InputInfo _list) {
        this.opsRead = _opsRead;
        this.opsValue = _opsValue;
        this.opsWrite = _opsWrite;
        this.opsMap = _opsMap;
        this.opsDefault = _opsDefault;
        this.opsConverter = _opsConverter;
        this.opsConverterField = _opsConverterField;
        this.opsConverterFieldValue = _opsConverterFieldValue;
        this.execAttributesText = _execAttributesText;
        this.execAttributes = _execAttributes;
        this.varName = _varName;
        this.id = _id;
        this.idClass = _idClass;
        this.idName = _idName;
        this.elt = _elt;
        this.multiple = _multiple;
        this.varNameConverter = _varNameConverter;
        this.varNameConverterField = _varNameConverterField;
        this.varNameConverterFieldValue = _varNameConverterFieldValue;
        this.className = _className;
        this.arrayConverter = _arrayConverter;
        varNames = _list;
    }

    @Override
    public void processEl(Configuration _cont, BeanLgNames _stds, ContextEl _ctx, RendStackCall _rendStack) {
        Argument value_ = RenderExpUtil.calculateReuse(opsValue, _stds, _ctx, _rendStack);
        if (_ctx.callsOrException(_rendStack.getStackCall())) {
            return;
        }
        Argument map_ = RenderExpUtil.calculateReuse(opsMap, _stds, _ctx, _rendStack);
        if (_ctx.callsOrException(_rendStack.getStackCall())) {
            return;
        }
        RendReadWrite rw_ = _rendStack.getLastPage().getRendReadWrite();
        Document doc_ = rw_.getDocument();
        Element docElementSelect_ = appendChild(doc_,rw_,_cont.getRendKeyWords().getKeyWordSelect());
        if (multiple) {
            docElementSelect_.setAttribute(_cont.getRendKeyWords().getAttrMultiple(), _cont.getRendKeyWords().getAttrMultiple());
        }
        String name_ = elt.getAttribute(_cont.getRendKeyWords().getAttrName());
        String default_ = elt.getAttribute(_cont.getRendKeyWords().getAttrDefault());
        if (default_.isEmpty()) {
            processOptionsMapEnumName(_cont, map_.getStruct(),
                    doc_, docElementSelect_,
                    value_.getStruct(), _stds, _ctx, _rendStack);
        } else {
            processOptionsMapEnum(_cont, map_.getStruct(),
                    doc_, docElementSelect_, _stds, _ctx, _rendStack);
        }
        boolean id_ = false;
        for (EntryCust<String, ExecTextPart> e: execAttributesText.entryList()) {
            ExecTextPart res_ = e.getValue();
            String txt_ = RenderingText.render(res_, _stds, _ctx, _rendStack);
            if (_ctx.callsOrException(_rendStack.getStackCall())) {
                return;
            }
            id_ = true;
            docElementSelect_.setAttribute(e.getKey(),txt_);
        }
        if (id_ && !elt.getAttribute(_cont.getRendKeyWords().getAttrValidator()).trim().isEmpty()) {
            docElementSelect_.setAttribute(StringUtil.concat(_cont.getPrefix(),_cont.getRendKeyWords().getAttrValidator()),
                    elt.getAttribute(_cont.getRendKeyWords().getAttrValidator()));
        }
        docElementSelect_.setAttribute(_cont.getRendKeyWords().getAttrName(), name_);
        processIndexes(_cont,elt,docElementSelect_, _stds, _ctx, _rendStack);
        if (_ctx.callsOrException(_rendStack.getStackCall())) {
            return;
        }
        Longs stack_ = _rendStack.getFormParts().getFormsNb();
        if (!stack_.isEmpty()) {
            FormInputCoords inputs_ = new FormInputCoords();
            inputs_.setForm(stack_.last());
            inputs_.setInput(_rendStack.getFormParts().getIndexes().getNb());
            StringList allOptions_ = new StringList();
            ElementList elts_ = docElementSelect_.getElementsByTagName(_cont.getRendKeyWords().getKeyWordOption());
            int nbElts_ = elts_.getLength();
            for (int i = 0; i < nbElts_; i++) {
                Element opt_ = elts_.item(i);
                allOptions_.add(opt_.getAttribute(_cont.getRendKeyWords().getAttrValue()));
            }
            _rendStack.getHtmlPage().getSelects().put(inputs_, allOptions_);
        }
        for (EntryCust<String, ExecTextPart> e: execAttributes.entryList()) {
            ExecTextPart res_ = e.getValue();
            String txt_ = RenderingText.render(res_, _stds, _ctx, _rendStack);
            if (_ctx.callsOrException(_rendStack.getStackCall())) {
                return;
            }
            docElementSelect_.setAttribute(e.getKey(),txt_);
        }
        processBlock(_cont, _stds, _ctx, _rendStack);
    }

    private void processOptionsMapEnum(Configuration _conf, Struct _extractedMap,
                                       Document _docSelect, Element _docElementSelect, BeanLgNames _advStandards, ContextEl _ctx, RendStackCall _rendStackCall) {
        Argument argDef_ = RenderExpUtil.calculateReuse(opsDefault, _advStandards, _ctx, _rendStackCall);
        if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
            return;
        }
        processOptionsMapEnumName(_conf,_extractedMap,_docSelect,_docElementSelect,argDef_.getStruct(), _advStandards, _ctx, _rendStackCall);
    }

    private void processOptionsMapEnumName(Configuration _conf, Struct _extractedMap,
                                           Document _docSelect, Element _docElementSelect, Struct _returnedVarValue, BeanLgNames _advStandards, ContextEl _ctx, RendStackCall _rendStackCall) {
        CustList<Struct> obj_ = values(_conf,_returnedVarValue, _advStandards, _ctx, _rendStackCall);
        if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
            return;
        }
        Argument arg_ = iteratorMultTable(_extractedMap,_conf, _advStandards, _ctx, _rendStackCall);
        if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
            return;
        }
        Struct l_;
        l_ = arg_.getStruct();
        processOptions(_conf, _docSelect, _docElementSelect, obj_, l_, _advStandards, _ctx, _rendStackCall);
    }

    private void processOptions(Configuration _conf, Document _docSelect, Element _docElementSelect, CustList<Struct> _obj, Struct _l, BeanLgNames _advStandards, ContextEl _ctx, RendStackCall _rendStackCall) {
        while (true) {
            Argument hasNext_ = hasNextPair(_l, _conf, _advStandards, _ctx, _rendStackCall);
            if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                return;
            }
            if (BooleanStruct.isFalse(hasNext_.getStruct())) {
                break;
            }
            Argument nextPair_ = nextPair(_l, _conf, _advStandards, _ctx, _rendStackCall);
            if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                return;
            }
            Struct entry_ = nextPair_.getStruct();
            Argument first_ = first(entry_, _conf, _advStandards, _ctx, _rendStackCall);
            if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                return;
            }
            Struct o_ = first_.getStruct();
            if (o_ == NullStruct.NULL_VALUE) {
                continue;
            }
            Element option_ = _docSelect.createElement(_conf.getRendKeyWords().getKeyWordOption());
            String value_ = processOptionValue(o_, _advStandards, _ctx, _rendStackCall);
            if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                return;
            }
            option_.setAttribute(_conf.getRendKeyWords().getAttrValue(),value_);
            for (Struct n: _obj) {
                if (n.sameReference(o_)) {
                    option_.setAttribute(_conf.getRendKeyWords().getAttrSelected(), _conf.getRendKeyWords().getAttrSelected());
                    break;
                }
            }
            Argument second_ = second(entry_, _conf, _advStandards, _ctx, _rendStackCall);
            if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                return;
            }
            String txt_ = processOptionText(second_, _advStandards, _ctx, _rendStackCall);
            if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                return;
            }
            option_.appendChild(_docSelect.createTextNode(txt_));
            _docElementSelect.appendChild(option_);
        }
    }
    private String processOptionValue(Struct _arg, BeanLgNames _advStandards, ContextEl _ctx, RendStackCall _rendStackCall) {
        if (opsConverterField.isEmpty()) {
            return getStringKey(_arg, _advStandards, _ctx, _rendStackCall);
        }
        LocalVariable locVar_ = LocalVariable.newLocalVariable(_arg, _ctx.getStandards().getContent().getCoreNames().getAliasObject());
        _rendStackCall.getLastPage().putValueVar(varNameConverterField, locVar_);
        Argument arg_ = RenderExpUtil.calculateReuse(opsConverterField, _advStandards, _ctx, _rendStackCall);
        _rendStackCall.getLastPage().removeRefVar(varNameConverterField);
        if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
            return EMPTY_STRING;
        }
        return _advStandards.processString(arg_, _ctx, _rendStackCall);
    }
    private String processOptionText(Argument _arg, BeanLgNames _advStandards, ContextEl _ctx, RendStackCall _rendStackCall) {
        if (opsConverterFieldValue.isEmpty()) {
            return _advStandards.processString(_arg, _ctx, _rendStackCall);
        }
        LocalVariable locVar_ = LocalVariable.newLocalVariable(_arg.getStruct(), _ctx.getStandards().getContent().getCoreNames().getAliasObject());
        _rendStackCall.getLastPage().putValueVar(varNameConverterFieldValue, locVar_);
        Argument arg_ = RenderExpUtil.calculateReuse(opsConverterFieldValue, _advStandards, _ctx, _rendStackCall);
        _rendStackCall.getLastPage().removeRefVar(varNameConverterFieldValue);
        if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
            return EMPTY_STRING;
        }
        return _advStandards.processString(arg_, _ctx, _rendStackCall);
    }

    private CustList<Struct> values(Configuration _conf, Struct _returnedVarValue, BeanLgNames _stds, ContextEl _ctx, RendStackCall _rendStackCall) {
        IdList<Struct> obj_ = new IdList<Struct>();
        if (multiple) {
            Argument arg_ = iterator(_returnedVarValue,_conf, _stds, _ctx, _rendStackCall);
            if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                return obj_;
            }
            Struct it_ = arg_.getStruct();
            while (true) {
                Argument hasNext_ = hasNext(it_, _conf, _stds, _ctx, _rendStackCall);
                if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                    return obj_;
                }
                if (BooleanStruct.isFalse(hasNext_.getStruct())) {
                    break;
                }
                Argument next_ = next(it_, _conf, _stds, _ctx, _rendStackCall);
                if (_ctx.callsOrException(_rendStackCall.getStackCall())) {
                    return obj_;
                }
                obj_.add(next_.getStruct());
            }
        } else {
            obj_.add(_returnedVarValue);
        }
        return obj_;
    }
    private void processIndexes(Configuration _cont, Element _read, Element _write, BeanLgNames _advStandards, ContextEl _ctx, RendStackCall _rendStackCall) {
        FieldUpdates f_ = new FieldUpdates();
        f_.setId(id);
        f_.setIdClass(idClass);
        f_.setIdName(idName);
        f_.setOpsRead(opsRead);
        f_.setOpsWrite(opsWrite);
        f_.setVarName(varName);
        f_.setVarNames(varNames);
        f_.setVarNameConverter(varNameConverter);
        f_.setOpsConverter(opsConverter);
        f_.setArrayConverter(arrayConverter);
        f_.setClassName(className);
        fetchName(_cont, _read, _write, f_, _advStandards, _ctx, _rendStackCall);
        fetchValue(_cont,_read,_write,opsValue,varNameConverterField,opsConverterField, _advStandards, _ctx, _rendStackCall);
    }
}
