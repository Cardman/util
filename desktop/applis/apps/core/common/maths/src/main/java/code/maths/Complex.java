package code.maths;
import code.util.StringList;
import code.util.core.StringUtil;
import code.util.ints.Displayable;


public final class Complex implements Displayable {

    private static final String SEPARATOR = ":";

    private final Rate real;

    private final Rate imag;

    public Complex() {
        this(Rate.zero());
    }
    public Complex(Rate _real) {
        this(_real, Rate.zero());
    }
    public Complex(Rate _real, Rate _imag) {
        real = _real;
        imag = _imag;
    }
    public Complex(Complex _c) {
        real = new Rate(_c.real);
        imag = new Rate(_c.imag);
    }

    
    public static Complex newFract(String _arg) {
        StringList args_ = StringUtil.splitStrings(_arg, SEPARATOR);
        return new Complex(new Rate(args_.first()), new Rate(args_.last()));
    }

    public Complex conjug() {
        return new Complex(real, imag.opposNb());
    }
    public Rate squareMod() {
        return Rate.plus(Rate.multiply(real, real), Rate.multiply(imag, imag));
    }
    public boolean isZero() {
        return real.isZero() && imag.isZero();
    }
    public Complex power(LgInt _power) {
        Complex out_ = new Complex(Rate.one());
        LgInt ind_ = LgInt.zero();
        while (LgInt.strLower(ind_,_power)) {
            out_ = out_.multiply(this);
            ind_.increment();
        }
        return out_;
    }
    public Complex inv() {
        Rate mod_= squareMod();
        return new Complex(Rate.divide(real,mod_),Rate.divide(imag,mod_).opposNb());
    }
    public Complex divide(Complex _o) {
        return multiply(_o.inv());
    }
    public Complex multiply(Complex _o) {
        Rate r_ = Rate.minus(Rate.multiply(real, _o.real), Rate.multiply(imag, _o.imag));
        Rate i_  = Rate.plus(Rate.multiply(real, _o.imag), Rate.multiply(imag, _o.real));
        return new Complex(r_, i_);
    }
    public Complex minus(Complex _complex) {
        return add(_complex.opposite());
    }
    public Complex opposite() {
        return new Complex(real.opposNb(), imag.opposNb());
    }
    public Complex add(Complex _complex) {
        return new Complex(Rate.plus(real, _complex.real), Rate.plus(imag, _complex.imag));
    }
    public Rate getReal() {
        return real;
    }

    public Rate getImag() {
        return imag;
    }
    public static boolean eq(Complex _tx1,Complex _tx2) {
        return _tx1.eq(_tx2);
    }

    public boolean eq(Complex _o) {
        if (!Rate.eq(_o.real, real)) {
            return false;
        }
        return Rate.eq(_o.imag, imag);
    }
    
    @Override
    public String display() {
        StringBuilder str_ = new StringBuilder(real.toNumberString());
        str_.append(SEPARATOR);
        str_.append(imag.toNumberString());
        return str_.toString();
    }
}
