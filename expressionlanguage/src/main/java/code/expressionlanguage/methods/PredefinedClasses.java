package code.expressionlanguage.methods;

import code.expressionlanguage.PrimitiveTypeUtil;


public final class PredefinedClasses {

    public static final String ITERABLE = "$iterable";
    public static final String ITERATOR = "$iterator";
    private static final String PUBLIC_ACCESS = "PUBLIC";

    private PredefinedClasses() {
    }

    public static String getIterableType() {
        String iterable_ = "<interface access='"+PUBLIC_ACCESS+"' name='"+ITERABLE+"' template='&lt;#T&gt;'>";
        iterable_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='iterator' class='"+ITERATOR+"&lt;#T&gt;'/>\n";
        iterable_ += "</interface>";
        return iterable_;
    }

    public static String getIteratorType() {
        String iterable_ = "<interface access='"+PUBLIC_ACCESS+"' name='"+ITERATOR+"' template='&lt;#T&gt;'>";
        iterable_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='next' class='#T'/>\n";
        iterable_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='hasNext' class='"+PrimitiveTypeUtil.PRIM_BOOLEAN+"'/>\n";
        iterable_ += "</interface>";
        return iterable_;
    }
}
