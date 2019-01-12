package code.expressionlanguage.errors.custom;

import code.expressionlanguage.inherits.Mapping;
import code.expressionlanguage.methods.Classes;
import code.util.EntryCust;
import code.util.StringList;

public final class BadImplicitCast extends FoundErrorInterpret {

    private Mapping mapping;

    @Override
    public String display(Classes _classes) {
        StringList tabs_ = new StringList();
        for (EntryCust<String, StringList> e: mapping.getMapping().entryList()) {
            tabs_.add(e.getKey());
            tabs_.add("{");
            tabs_.add(e.getValue().join(";"));
            tabs_.add("}");
        }
        return StringList.concat(super.display(_classes),SEP_INFO,mapping.getArg().getNames().join(""),SEP_INFO,mapping.getParam().getNames().join(""),SEP_INFO,tabs_.join(","),SEP_INFO);
    }

    public void setMapping(Mapping _mapping) {
        mapping = _mapping;
    }
}
