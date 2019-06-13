package cards.president;
import org.junit.Assert;

import cards.president.enumerations.CardPresident;
import cards.president.enumerations.Playing;
import code.util.Numbers;
import code.util.StringList;

public final class EquallablePresidentUtil {

    private static final String DIFF = " != ";

    private EquallablePresidentUtil() {
    }
    public static void assertEq(long _expected, long _result) {
        Assert.assertTrue(StringList.concat(Long.toString(_expected),DIFF,Long.toString(_result)), sameValue(_expected, _result));
    }
    public static void assertEq(long _expected, int _result) {
        Assert.assertTrue(StringList.concat(Long.toString(_expected),DIFF,Long.toString(_result)), sameValue(_expected, _result));
    }
    private static boolean sameValue(long _expected, long _result) {
        return _expected == _result;
    }
    public static void assertEq(HandPresident _expected, HandPresident _result) {
        Assert.assertNotNull(_result);
        Assert.assertTrue(_expected.eq(_result));
    }

    public static void assertEq(Playing _expected, Playing _result) {
        Assert.assertSame(_expected, _result);
    }

    public static void assertEq(CardPresident _expected, CardPresident _result) {
        Assert.assertSame(_expected, _result);
    }
}
