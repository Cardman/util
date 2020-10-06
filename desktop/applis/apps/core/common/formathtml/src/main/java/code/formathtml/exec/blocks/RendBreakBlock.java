package code.formathtml.exec.blocks;

import code.expressionlanguage.ContextEl;
import code.formathtml.Configuration;
import code.formathtml.ImportingPage;
import code.formathtml.stacks.*;
import code.formathtml.util.BeanLgNames;

public final class RendBreakBlock extends RendLeaf implements RendWithEl,RendMethodCallingFinally {

    private String label;
    public RendBreakBlock(int _offsetTrim, String _info) {
        super(_offsetTrim);
        label = _info;
    }


    @Override
    public void processEl(Configuration _cont, BeanLgNames _stds, ContextEl _ctx) {
        removeBlockFinally(_cont, _stds, _ctx);
    }

    @Override
    public void removeBlockFinally(Configuration _conf, BeanLgNames _stds, ContextEl _ctx) {
        ImportingPage ip_ = _conf.getLastPage();
        while (hasBlockBreak(ip_,label)) {
            RendRemovableVars bl_ = ip_.getRendLastStack();
            if (ImportingPage.setRemovedCallingFinallyToProcess(ip_,bl_,this,null)) {
                break;
            }
        }
    }

}
