package code.expressionlanguage.utilcompo;

import code.expressionlanguage.filenames.AbstractNameValidating;
import code.maths.montecarlo.AbstractGenerator;
import code.util.core.DefaultUniformingString;

public final class FileInfos {
    private final AbstractResourcesReader reader;
    private final AbstractLogger logger;
    private final AbstractFileSystem fileSystem;
    private final AbstractReporter reporter;
    private final AbstractGenerator generator;

    public FileInfos(AbstractResourcesReader _reader, AbstractLogger _logger, AbstractFileSystem _fileSystem, AbstractReporter _reporter, AbstractGenerator _generator) {
        this.reader = _reader;
        this.logger = _logger;
        this.fileSystem = _fileSystem;
        this.reporter = _reporter;
        this.generator = _generator;
    }

    public static FileInfos buildMemoryFromFile(AbstractResourcesReader _reader, AbstractGenerator _generator, byte[] _conf, byte[] _src, byte[] _files, AbstractNameValidating _nameValidating,AbstractIssuer _issuer) {
        DefaultUniformingString uniformingString_ = new DefaultUniformingString();
        return new FileInfos(_reader,new MemoryLogger(_nameValidating,_issuer),new MemoryFileSystem(uniformingString_,_nameValidating),new MemoryReporter(_conf, _src, _files, _nameValidating, uniformingString_),_generator);
    }
    public AbstractResourcesReader getReader() {
        return reader;
    }

    public AbstractLogger getLogger() {
        return logger;
    }

    public AbstractFileSystem getFileSystem() {
        return fileSystem;
    }

    public AbstractReporter getReporter() {
        return reporter;
    }

    public AbstractGenerator getGenerator() {
        return generator;
    }
}
