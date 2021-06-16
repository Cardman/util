package code.threads;

public final class LockFactory {
    private LockFactory(){
    }
    public static AbstractLock newLock(AbstractAtomicBoolean _val) {
        return new CustomLock(_val);
    }
}
