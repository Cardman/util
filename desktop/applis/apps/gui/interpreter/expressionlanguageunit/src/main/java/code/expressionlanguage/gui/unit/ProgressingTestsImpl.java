package code.expressionlanguage.gui.unit;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.ReportedMessages;
import code.expressionlanguage.options.Options;
import code.expressionlanguage.utilcompo.*;
import code.expressionlanguage.structs.Struct;
import code.gui.Clock;
import code.stream.AbstractFileCoreStream;
import code.stream.StreamBinaryFile;
import code.stream.StreamFolderFile;

public final class ProgressingTestsImpl implements ProgressingTests {
    private TestableFrame mainWindow;
    private ExecutingOptions exec;
    private AbstractFileCoreStream fact;

    public ProgressingTestsImpl(TestableFrame _mainWindow, AbstractFileCoreStream _fact) {
        mainWindow = _mainWindow;
        fact = _fact;
    }

    @Override
    public AbstractInterceptor getInterceptor() {
        return mainWindow.getIntercept();
    }

    @Override
    public ExecutingOptions getExec() {
        return exec;
    }

    @Override
    public void init(ExecutingOptions _exec) {
        exec = _exec;
    }

    @Override
    public void showErrors(ReportedMessages _reportedMessages, Options _opts, ExecutingOptions _exec, FileInfos _infos) {
        String time_ = Clock.getDateTimeText("_", "_", "_");
        MemoryReporter.buildError(_reportedMessages,_exec,_infos,time_);
        AbstractLogger logger_ = _infos.getLogger();
        byte[] bytes_ = _infos.getReporter().exportErrs(_exec, logger_);
        if (bytes_ != null) {
            StreamFolderFile.makeParent(_exec.getOutputFolder()+"/"+_exec.getOutputZip(),fact);
            StreamBinaryFile.writeFile(_exec.getOutputFolder()+"/"+_exec.getOutputZip(),bytes_,mainWindow.getInfos().getTechStreams());
        }
    }

    @Override
    public void showWarnings(RunnableContextEl _ctx, ReportedMessages _reportedMessages, Options _opts, ExecutingOptions _exec, FileInfos _infos) {
        String time_ = Clock.getDateTimeText("_", "_", "_");
        MemoryReporter.buildWarning(_reportedMessages,_exec,_infos,time_);
    }

    @Override
    public void updateInfos(RunnableContextEl _ctx, Struct _infos, Struct _doneTests, Struct _method, Struct _count, LgNamesWithNewAliases _evolved) {
        mainWindow.showProgress(_ctx,_infos,_doneTests,_method,_count, _evolved);
    }

    @Override
    public void finish(RunnableContextEl _ctx, Struct _infos, LgNamesWithNewAliases _evolved) {
        mainWindow.finish(_infos, _evolved);
    }

    @Override
    public void setResults(RunnableContextEl _ctx, Argument _res, LgNamesWithNewAliases _evolved) {
        mainWindow.setResults(_ctx,_res, _evolved);
        ExecutingOptions executingOptions_ = _ctx.getExecutingOptions();
        AbstractLogger logger_ = _evolved.getInfos().getLogger();
        AbstractFileSystem fileSystem_ = _evolved.getInfos().getFileSystem();
        byte[] export_ = _evolved.getInfos().getReporter().export(executingOptions_, fileSystem_, logger_);
        if (export_ == null) {
            return;
        }
        StreamFolderFile.makeParent(executingOptions_.getOutputFolder()+"/"+executingOptions_.getOutputZip(),fact);
        StreamBinaryFile.writeFile(executingOptions_.getOutputFolder()+"/"+executingOptions_.getOutputZip(),export_,mainWindow.getInfos().getTechStreams());
    }
}
