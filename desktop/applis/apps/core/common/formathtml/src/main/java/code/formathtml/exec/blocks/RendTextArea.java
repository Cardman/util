package code.formathtml.exec.blocks;

import code.expressionlanguage.ContextEl;
import code.formathtml.Configuration;
import code.formathtml.exec.RendStackCall;
import code.formathtml.exec.opers.RendDynOperationNode;
import code.formathtml.stacks.RendReadWrite;
import code.formathtml.util.BeanLgNames;
import code.formathtml.util.FieldUpdates;
import code.formathtml.util.InputInfo;
import code.sml.Document;
import code.sml.Element;
import code.util.CustList;
import code.util.EntryCust;
import code.util.StringMap;
import code.util.core.StringUtil;

public final class RendTextArea extends RendParentBlock implements RendWithEl {
    private CustList<RendDynOperationNode> opsRead = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsValue = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsWrite = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsConverter = new CustList<RendDynOperationNode>();
    private CustList<RendDynOperationNode> opsConverterField = new CustList<RendDynOperationNode>();
    private StringMap<ExecTextPart> execAttributesText = new StringMap<ExecTextPart>();
    private StringMap<ExecTextPart> execAttributes = new StringMap<ExecTextPart>();

    private String varNameConverter = EMPTY_STRING;
    private String varNameConverterField = EMPTY_STRING;
    private String varName = EMPTY_STRING;
    private InputInfo varNames = new InputInfo();
    private String id = EMPTY_STRING;
    private String idClass = EMPTY_STRING;
    private String idName = EMPTY_STRING;
    private String className = EMPTY_STRING;
    private final Element elt;

    public RendTextArea(CustList<RendDynOperationNode> _opsRead, CustList<RendDynOperationNode> _opsValue, CustList<RendDynOperationNode> _opsWrite,
                        CustList<RendDynOperationNode> _opsConverter, CustList<RendDynOperationNode> _opsConverterField,
                        StringMap<ExecTextPart> _execAttributesText, StringMap<ExecTextPart> _execAttributes,
                        String _varNameConverter, String _varNameConverterField,
                        String _varName, String _id, String _idClass, String _idName, String _className, Element _elt, InputInfo _list) {
        this.opsRead = _opsRead;
        this.opsValue = _opsValue;
        this.opsWrite = _opsWrite;
        this.opsConverter = _opsConverter;
        this.opsConverterField = _opsConverterField;
        this.execAttributesText = _execAttributesText;
        this.execAttributes = _execAttributes;
        this.varNameConverter = _varNameConverter;
        this.varNameConverterField = _varNameConverterField;
        this.varName = _varName;
        this.id = _id;
        this.idClass = _idClass;
        this.idName = _idName;
        this.className = _className;
        this.elt = _elt;
        varNames = _list;
    }

    @Override
    public void processEl(Configuration _cont, BeanLgNames _stds, ContextEl _ctx, RendStackCall _rendStack) {
        RendReadWrite rw_ = _rendStack.getLastPage().getRendReadWrite();
        Document doc_ = rw_.getDocument();
        Element docElementSelect_ = appendChild(doc_,rw_,_cont.getRendKeyWords().getKeyWordTextarea());
        FieldUpdates f_ = new FieldUpdates();
        f_.setId(id);
        f_.setIdClass(idClass);
        f_.setIdName(idName);
        f_.setOpsRead(opsRead);
        f_.setOpsWrite(opsWrite);
        f_.setVarName(varName);
        f_.setVarNames(varNames);
        f_.setClassName(className);
        f_.setVarNameConverter(varNameConverter);
        f_.setOpsConverter(opsConverter);
        for (EntryCust<String, ExecTextPart> e: execAttributesText.entryList()) {
            ExecTextPart res_ = e.getValue();
            String txt_ = RenderingText.render(res_, _stds, _ctx, _rendStack);
            if (_ctx.callsOrException(_rendStack.getStackCall())) {
                return;
            }
            docElementSelect_.setAttribute(e.getKey(),txt_);
        }
        if (elt.hasAttribute(StringUtil.concat(_cont.getPrefix(),_cont.getRendKeyWords().getAttrValidator()))) {
            docElementSelect_.setAttribute(StringUtil.concat(_cont.getPrefix(),_cont.getRendKeyWords().getAttrValidator()),
                    elt.getAttribute(StringUtil.concat(_cont.getPrefix(),_cont.getRendKeyWords().getAttrValidator())));
        }
        fetchName(_cont, elt, docElementSelect_, f_, _stds, _ctx, _rendStack);
        fetchValue(_cont,elt,docElementSelect_,opsValue,varNameConverterField,opsConverterField, _stds, _ctx, _rendStack);
        if (_ctx.callsOrException(_rendStack.getStackCall())) {
            return;
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
}
