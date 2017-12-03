package code.xml.components;

public abstract class CharacterData extends ChangeableChild {
    protected CharacterData(Document _ownerDocument) {
        super(_ownerDocument);
    }
    public abstract void appendData(String _arg);
    public abstract void deleteData(int _offset, int _count);
    public abstract String getData();
    public abstract int getLength();
    public abstract void insertData(int offset, String arg);
    public abstract void replaceData(int offset, int count, String arg);
    public abstract void setData(String data);
    public abstract String substringData(int offset, int count);
}
