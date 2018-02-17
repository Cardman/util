package code.util.opers;
import static code.util.EquallableExUtil.assertEq;

import org.junit.Test;

public final class BaseSixtyFourUtilTest {

    @Test
    public void parseFourChars1Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAAA");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(0, bytes_[2]);
    }

    @Test
    public void parseFourChars2Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAAB");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(1, bytes_[2]);
    }

    @Test
    public void parseFourChars3Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAA/");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(63, bytes_[2]);
    }

    @Test
    public void parseFourChars4Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AABA");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(64, bytes_[2]);
    }

    @Test
    public void parseFourChars5Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAB/");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(127, bytes_[2]);
    }

    @Test
    public void parseFourChars6Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AACA");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(-128, bytes_[2]);
    }

    @Test
    public void parseFourChars7Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAAZ");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(25, bytes_[2]);
    }

    @Test
    public void parseFourChars8Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAAa");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(26, bytes_[2]);
    }

    @Test
    public void parseFourChars9Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAAz");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(51, bytes_[2]);
    }

    @Test
    public void parseFourChars10Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAA0");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(52, bytes_[2]);
    }

    @Test
    public void parseFourChars11Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAA9");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(61, bytes_[2]);
    }

    @Test
    public void parseFourChars12Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("AAA+");
        assertEq(3, bytes_.length);
        assertEq(0, bytes_[0]);
        assertEq(0, bytes_[1]);
        assertEq(62, bytes_[2]);
    }

    @Test
    public void parseFourChars13Test() {
        byte[] bytes_ = BaseSixtyFourUtil.parseFourChars("/1z+");
        assertEq(3, bytes_.length);
        assertEq(-1, bytes_[0]);
        assertEq(92, bytes_[1]);
        assertEq(-2, bytes_[2]);
    }
    @Test
    public void getImageByString1Test() {
        int[][] img_ = BaseSixtyFourUtil.getImageByString("AAABAAAA");
        assertEq(1, img_.length);
        assertEq(1, img_[0].length);
        assertEq(0, img_[0][0]);
    }
    @Test
    public void getImageByString2Test() {
        int[][] img_ = BaseSixtyFourUtil.getImageByString("AAABABAB");
        assertEq(1, img_.length);
        assertEq(1, img_[0].length);
        assertEq(4097, img_[0][0]);
    }
    @Test
    public void getImageByString3Test() {
        int[][] img_ = BaseSixtyFourUtil.getImageByString("AAACABABbaba");
        assertEq(1, img_.length);
        assertEq(2, img_[0].length);
        assertEq(4097, img_[0][0]);
        assertEq(7186138, img_[0][1]);
    }
    @Test
    public void getImageByString4Test() {
        int[][] img_ = BaseSixtyFourUtil.getImageByString("AAABABABbaba");
        assertEq(2, img_.length);
        assertEq(1, img_[0].length);
        assertEq(4097, img_[0][0]);
        assertEq(1, img_[1].length);
        assertEq(7186138, img_[1][0]);
    }
    @Test
    public void getImageByString5Test() {
        int[][] img_ = BaseSixtyFourUtil.getImageByString("AAAB////");
        assertEq(1, img_.length);
        assertEq(1, img_[0].length);
        assertEq(16777215, img_[0][0]);
    }
    @Test
    public void getImageByString6Test() {
        int[][] img_ = BaseSixtyFourUtil.getImageByString("AAACbabaABAB");
        assertEq(1, img_.length);
        assertEq(2, img_[0].length);
        assertEq(7186138, img_[0][0]);
        assertEq(4097, img_[0][1]);
    }
    @Test
    public void getImageByString7Test() {
        int[][] img_ = BaseSixtyFourUtil.getImageByString("AAABbabaABAB");
        assertEq(2, img_.length);
        assertEq(1, img_[0].length);
        assertEq(7186138, img_[0][0]);
        assertEq(1, img_[1].length);
        assertEq(4097, img_[1][0]);
    }
    @Test
    public void getImageByString8Test() {
        StringBuilder imgStr_ = new StringBuilder(4+4*128);
        imgStr_.append("AACA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        imgStr_.append("AAAA");
        int[][] img_ = BaseSixtyFourUtil.getImageByString(imgStr_.toString());
        assertEq(1, img_.length);
        assertEq(128, img_[0].length);
        checkZero(img_[0]);
    }

    private static void checkZero(int[] _array) {
        for (int i: _array) {
            assertEq(0, i);
        }
    }
}
