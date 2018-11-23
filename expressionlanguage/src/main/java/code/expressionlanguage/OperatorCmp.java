package code.expressionlanguage;

import code.expressionlanguage.structs.MethodMetaInfo;
import code.util.CustList;
import code.util.Numbers;
import code.util.StringList;
import code.util.ints.Comparing;

public class OperatorCmp implements Comparing<MethodMetaInfo> {

    @Override
    public int compare(MethodMetaInfo _one, MethodMetaInfo _two) {
        int res_ = _one.getName().compareTo(_two.getName());
        if (res_ != CustList.EQ_CMP) {
            return res_;
        }
        StringList pOne_ = _one.getParameterNames();
        StringList pTwo_ = _two.getParameterNames();
        res_ = Numbers.compare(pOne_.size(), pTwo_.size());
        if (res_ != CustList.EQ_CMP) {
            return res_;
        }
        int s_ = pOne_.size();
        for (int i = CustList.FIRST_INDEX; i < s_; i++) {
            res_ = pOne_.get(i).compareTo(pTwo_.get(i));
            if (res_ != CustList.EQ_CMP) {
                return res_;
            }
        }
        return 0;
    }
}
