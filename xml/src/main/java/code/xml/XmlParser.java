package code.xml;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import code.util.CustList;
import code.util.EntryCust;
import code.util.NatTreeMap;
import code.util.Numbers;
import code.util.StringList;
import code.util.StringMap;
import code.xml.exceptions.BadNumberedCharacterException;
import code.xml.exceptions.XmlParseException;

public final class XmlParser {

    private static final char BEGIN_COMMENT = '!';
    private static final String END_COMMENT = "-->";
    private static final String NEXT_COMMENT = "--";

    private static boolean _indentXmlWhileWriting_;

    private static boolean _omitDeclaration_;

    private static final String HTML = "html";

    //    private static final String UNPREFIXED_END = "</c_";
    //
    //    private static final String UNPREFIXED_BEGIN = "<c_";
    //
    //    private static final String PREFIXED_END = "</c:";
    //
    //    private static final String PREFIXED_BEGIN = "<c:";

    private static final char ASCII_32 = 32;
    //    private static final char ASCII_37 = 37;
    private static final char ASCII_38 = 38;
    private static final char ASCII_128 = 128;
    //    private static final char ASCII_256 = 256;
    //    private static final char U_Y_UML_159 = 159;
    private static final char IEXCL = 161;
    private static final char CENT = 162;
    private static final char POUND = 163;
    private static final char CURREN = 164;
    private static final char YEN = 165;
    private static final char BRVBAR = 166;
    private static final char SECT = 167;
    private static final char UML = 168;
    private static final char COPY = 169;
    private static final char ORDF = 170;
    private static final char LAQUO = 171;
    private static final char NOT = 172;
    private static final char SHY = 173;
    private static final char REG = 174;
    private static final char MACR = 175;
    private static final char DEG = 176;
    private static final char PLUSMN = 177;
    private static final char SUP2 = 178;
    private static final char SUP3 = 179;
    private static final char ACUTE = 180;
    private static final char MICRO = 181;
    private static final char PARA = 182;
    private static final char MIDDOT = 183;
    private static final char CEDIL = 184;
    private static final char SUP1 = 185;
    private static final char ORDM = 186;
    private static final char RAQUO = 187;
    private static final char FRAC14 = 188;
    private static final char FRAC12 = 189;
    private static final char FRAC34 = 190;
    private static final char IQUEST = 191;
    private static final char U_A_GRAVE = 192;
    private static final char U_A_ACUTE = 193;
    private static final char U_A_CIRC = 194;
    private static final char U_A_TILDE = 195;
    private static final char U_A_UML = 196;
    private static final char U_A_RING = 197;
    private static final char U_AE_LIG = 198;
    private static final char U_C_CEDIL = 199;
    private static final char U_E_GRAVE = 200;
    private static final char U_E_ACUTE = 201;
    private static final char U_E_CIRC = 202;
    private static final char U_E_UML = 203;
    private static final char U_I_GRAVE = 204;
    private static final char U_I_ACUTE = 205;
    private static final char U_I_CIRC = 206;
    private static final char U_I_UML = 207;
    private static final char U_ETH = 208;
    private static final char U_N_TILDE = 209;
    private static final char U_O_GRAVE = 210;
    private static final char U_O_ACUTE = 211;
    private static final char U_O_CIRC = 212;
    private static final char U_O_TILDE = 213;
    private static final char U_O_UML = 214;
    private static final char TIMES = 215;
    private static final char U_O_SLASH = 216;
    private static final char U_U_GRAVE = 217;
    private static final char U_U_ACUTE = 218;
    private static final char U_U_CIRC = 219;
    private static final char U_U_UML = 220;
    private static final char U_Y_ACUTE = 221;
    private static final char U_THORN = 222;
    private static final char SZLIG = 223;
    private static final char AGRAVE = 224;
    private static final char AACUTE = 225;
    private static final char ACIRC = 226;
    private static final char ATILDE = 227;
    private static final char AUML = 228;
    private static final char ARING = 229;
    private static final char AELIG = 230;
    private static final char CCEDIL = 231;
    private static final char EGRAVE = 232;
    private static final char EACUTE = 233;
    private static final char ECIRC = 234;
    private static final char EUML = 235;
    private static final char IGRAVE = 236;
    private static final char IACUTE = 237;
    private static final char ICIRC = 238;
    private static final char IUML = 239;
    private static final char ETH = 240;
    private static final char NTILDE = 241;
    private static final char OGRAVE = 242;
    private static final char OACUTE = 243;
    private static final char OCIRC = 244;
    private static final char OTILDE = 245;
    private static final char OUML = 246;
    private static final char DIVIDE = 247;
    private static final char OSLASH = 248;
    private static final char UGRAVE = 249;
    private static final char UACUTE = 250;
    private static final char UCIRC = 251;
    private static final char UUML = 252;
    private static final char YACUTE = 253;
    private static final char THORN = 254;
    private static final char YUML = 255;
    private static final char U_OE_LIG = 338;
    private static final char OELIG = 339;
    private static final char U_SCARON = 352;
    private static final char SCARON = 353;
    private static final char U_Y_UML = 376;
    private static final char CIRC = 710;
    private static final char TILDE = 732;
    private static final char ENSP = 8194;
    private static final char EMSP = 8195;
    private static final char THINSP = 8201;
    private static final char ZWNJ = 8204;
    private static final char ZWJ = 8205;
    private static final char LRM = 8206;
    private static final char RLM = 8207;
    private static final char NDASH = 8211;
    private static final char MDASH = 8212;
    private static final char LSQUO = 8216;
    private static final char RSQUO = 8217;
    private static final char SBQUO = 8218;
    private static final char LDQUO = 8220;
    private static final char RDQUO = 8221;
    private static final char BDQUO = 8222;
    private static final char D_AGGER = 8224;
    private static final char DAGGER = 8225;
    private static final char PERMIL = 8240;
    private static final char LSAQUO = 8249;
    private static final char RSAQUO = 8250;
    private static final char EURO = 8364;
    private static final char FNOF = 402;
    private static final char U_A_LPHA = 913;
    private static final char U_B_ETA = 914;
    private static final char U_G_AMMA = 915;
    private static final char U_D_ELTA = 916;
    private static final char U_E_PSILON = 917;
    private static final char U_Z_ETA = 918;
    private static final char U_E_TA = 919;
    private static final char U_T_HETA = 920;
    private static final char U_I_OTA = 921;
    private static final char U_K_APPA = 922;
    private static final char U_L_AMBDA = 923;
    private static final char U_M_U = 924;
    private static final char U_N_U = 925;
    private static final char U_X_I = 926;
    private static final char U_O_MICRON = 927;
    private static final char U_P_I = 928;
    private static final char U_R_HO = 929;
    private static final char U_S_IGMA = 931;
    private static final char U_T_AU = 932;
    private static final char U_U_PSILON = 933;
    private static final char U_P_HI = 934;
    private static final char U_C_HI = 935;
    private static final char U_P_SI = 936;
    private static final char U_O_MEGA = 937;
    private static final char ALPHA = 945;
    private static final char BETA = 946;
    private static final char GAMMA = 947;
    private static final char DELTA = 948;
    private static final char EPSILON = 949;
    private static final char ZETA = 950;
    private static final char ETA = 951;
    private static final char THETA = 952;
    private static final char IOTA = 953;
    private static final char KAPPA = 954;
    private static final char LAMBDA = 955;
    private static final char MU = 956;
    private static final char NU = 957;
    private static final char XI = 958;
    private static final char OMICRON = 959;
    private static final char PI = 960;
    private static final char RHO = 961;
    private static final char SIGMAF = 962;
    private static final char SIGMA = 963;
    private static final char TAU = 964;
    private static final char UPSILON = 965;
    private static final char PHI = 966;
    private static final char CHI = 967;
    private static final char PSI = 968;
    private static final char OMEGA = 969;
    private static final char THETASYM = 977;
    private static final char UPSIH = 978;
    private static final char PIV = 982;
    private static final char BULL = 8226;
    private static final char HELLIP = 8230;
    private static final char PRIME = 8242;
    private static final char U_PRIME = 8243;
    private static final char OLINE = 8254;
    private static final char FRASL = 8260;
    private static final char WEIERP = 8472;
    private static final char IMAGE = 8465;
    private static final char REAL = 8476;
    private static final char TRADE = 8482;
    private static final char ALEFSYM = 8501;
    private static final char LARR = 8592;
    private static final char UARR = 8593;
    private static final char RARR = 8594;
    private static final char DARR = 8595;
    private static final char HARR = 8596;
    private static final char CRARR = 8629;
    private static final char U_LARR = 8656;
    private static final char U_UARR = 8657;
    private static final char U_RARR = 8658;
    private static final char U_DARR = 8659;
    private static final char U_HARR = 8660;
    private static final char FORALL = 8704;
    private static final char PART = 8706;
    private static final char EXIST = 8707;
    private static final char EMPTY = 8709;
    private static final char NABLA = 8711;
    private static final char ISIN = 8712;
    private static final char NOTIN = 8713;
    private static final char NI = 8715;
    private static final char PROD = 8719;
    private static final char SUM = 8721;
    private static final char MINUS = 8722;
    private static final char LOWAST = 8727;
    private static final char RADIC = 8730;
    private static final char PROP = 8733;
    private static final char INFIN = 8734;
    private static final char ANG = 8736;
    private static final char AND = 8743;
    private static final char OR = 8744;
    private static final char CAP = 8745;
    private static final char CUP = 8746;
    private static final char INT = 8747;
    private static final char THERE4 = 8756;
    private static final char SIM = 8764;
    private static final char CONG = 8773;
    private static final char ASYMP = 8776;
    private static final char NE = 8800;
    private static final char EQUIV = 8801;
    private static final char LE = 8804;
    private static final char GE = 8805;
    private static final char SUB = 8834;
    private static final char SUP = 8835;
    private static final char NSUB = 8836;
    private static final char SUBE = 8838;
    private static final char SUPE = 8839;
    private static final char OPLUS = 8853;
    private static final char OTIMES = 8855;
    private static final char PERP = 8869;
    private static final char SDOT = 8901;
    private static final char LCEIL = 8968;
    private static final char RCEIL = 8969;
    private static final char LFLOOR = 8970;
    private static final char RFLOOR = 8971;
    private static final char LANG = 9001;
    private static final char RANG = 9002;
    private static final char LOZ = 9674;
    private static final char SPADES = 9824;
    private static final char CLUBS = 9827;
    private static final char HEARTS = 9829;
    private static final char DIAMS = 9830;
    private static final char NBSP = 160;
    private static final char QUOT = 34;
    private static final char APOS = 39;
    private static final char LT = 60;
    private static final char GT = 62;
    private static final String E_U_AELIG = "&AElig;";
    //    private static final String E_U_AGGER = "&AGGER;";
    private static final String E_U_AACUTE = "&Aacute;";
    private static final String E_U_ACIRC = "&Acirc;";
    private static final String E_U_AGRAVE = "&Agrave;";
    private static final String E_U_ALPHA = "&Alpha;";
    private static final String E_U_ARING = "&Aring;";
    private static final String E_U_ATILDE = "&Atilde;";
    private static final String E_U_AUML = "&Auml;";
    private static final String E_U_BETA = "&Beta;";
    private static final String E_U_CCEDIL = "&Ccedil;";
    private static final String E_U_CHI = "&Chi;";
    //    private static final String E_U_DARR = "&DARR;";
    private static final String E_U_DAGGER = "&Dagger;";
    private static final String E_U_DELTA = "&Delta;";
    private static final String E_U_ETH = "&ETH;";
    private static final String E_U_EACUTE = "&Eacute;";
    private static final String E_U_ECIRC = "&Ecirc;";
    private static final String E_U_EGRAVE = "&Egrave;";
    private static final String E_U_EPSILON = "&Epsilon;";
    private static final String E_U_ETA = "&Eta;";
    private static final String E_U_EUML = "&Euml;";
    private static final String E_U_GAMMA = "&Gamma;";
    //    private static final String E_U_HARR = "&HARR;";
    private static final String E_U_IACUTE = "&Iacute;";
    private static final String E_U_ICIRC = "&Icirc;";
    private static final String E_U_IGRAVE = "&Igrave;";
    private static final String E_U_IOTA = "&Iota;";
    private static final String E_U_IUML = "&Iuml;";
    private static final String E_U_KAPPA = "&Kappa;";
    //    private static final String E_U_LARR = "&LARR;";
    private static final String E_U_LAMBDA = "&Lambda;";
    private static final String E_U_MU = "&Mu;";
    private static final String E_U_NTILDE = "&Ntilde;";
    private static final String E_U_NU = "&Nu;";
    private static final String E_U_OELIG = "&OElig;";
    private static final String E_U_OACUTE = "&Oacute;";
    private static final String E_U_OCIRC = "&Ocirc;";
    private static final String E_U_OGRAVE = "&Ograve;";
    private static final String E_U_OMEGA = "&Omega;";
    private static final String E_U_OMICRON = "&Omicron;";
    private static final String E_U_OSLASH = "&Oslash;";
    private static final String E_U_OTILDE = "&Otilde;";
    private static final String E_U_OUML = "&Ouml;";
    //    private static final String E_U_PRIME = "&PRIME;";
    private static final String E_U_PHI = "&Phi;";
    private static final String E_U_PI = "&Pi;";
    private static final String E_P_RIME = "&Prime;";
    private static final String E_U_PSI = "&Psi;";
    //    private static final String E_U_RARR = "&RARR;";
    private static final String E_U_RHO = "&Rho;";
    //    private static final String E_U_SCARON = "&SCARON;";
    private static final String E_S_CARON = "&Scaron;";
    private static final String E_U_SIGMA = "&Sigma;";
    private static final String E_U_THORN = "&THORN;";
    private static final String E_U_TAU = "&Tau;";
    private static final String E_U_THETA = "&Theta;";
    //    private static final String E_U_UARR = "&UARR;";
    private static final String E_U_UACUTE = "&Uacute;";
    private static final String E_U_UCIRC = "&Ucirc;";
    private static final String E_U_UGRAVE = "&Ugrave;";
    private static final String E_U_UPSILON = "&Upsilon;";
    private static final String E_U_UUML = "&Uuml;";
    private static final String E_U_XI = "&Xi;";
    private static final String E_U_YACUTE = "&Yacute;";
    private static final String E_U_YUML = "&Yuml;";
    private static final String E_U_ZETA = "&Zeta;";
    private static final String E_AACUTE = "&aacute;";
    private static final String E_ACIRC = "&acirc;";
    private static final String E_ACUTE = "&acute;";
    private static final String E_AELIG = "&aelig;";
    private static final String E_AGRAVE = "&agrave;";
    private static final String E_ALEFSYM = "&alefsym;";
    private static final String E_ALPHA = "&alpha;";
    private static final String E_AMP = "&amp;";
    private static final String E_AND = "&and;";
    private static final String E_ANG = "&ang;";
    private static final String E_APOS = "&apos;";
    private static final String E_ARING = "&aring;";
    private static final String E_ASYMP = "&asymp;";
    private static final String E_ATILDE = "&atilde;";
    private static final String E_AUML = "&auml;";
    private static final String E_BDQUO = "&bdquo;";
    private static final String E_BETA = "&beta;";
    private static final String E_BRVBAR = "&brvbar;";
    private static final String E_BULL = "&bull;";
    private static final String E_CAP = "&cap;";
    private static final String E_CCEDIL = "&ccedil;";
    private static final String E_CEDIL = "&cedil;";
    private static final String E_CENT = "&cent;";
    private static final String E_CHI = "&chi;";
    private static final String E_CIRC = "&circ;";
    private static final String E_CLUBS = "&clubs;";
    private static final String E_CONG = "&cong;";
    private static final String E_COPY = "&copy;";
    private static final String E_CRARR = "&crarr;";
    private static final String E_CUP = "&cup;";
    private static final String E_CURREN = "&curren;";
    private static final String E_D_ARR = "&dArr;";
    private static final String E_DAGGER = "&dagger;";
    private static final String E_DARR = "&darr;";
    private static final String E_DEG = "&deg;";
    private static final String E_DELTA = "&delta;";
    private static final String E_DIAMS = "&diams;";
    private static final String E_DIVIDE = "&divide;";
    private static final String E_EACUTE = "&eacute;";
    private static final String E_ECIRC = "&ecirc;";
    private static final String E_EGRAVE = "&egrave;";
    private static final String E_EMPTY = "&empty;";
    private static final String E_EMSP = "&emsp;";
    private static final String E_ENSP = "&ensp;";
    private static final String E_EPSILON = "&epsilon;";
    private static final String E_EQUIV = "&equiv;";
    private static final String E_ETA = "&eta;";
    private static final String E_ETH = "&eth;";
    private static final String E_EUML = "&euml;";
    private static final String E_EURO = "&euro;";
    private static final String E_EXIST = "&exist;";
    private static final String E_FNOF = "&fnof;";
    private static final String E_FORALL = "&forall;";
    private static final String E_FRAC12 = "&frac12;";
    private static final String E_FRAC14 = "&frac14;";
    private static final String E_FRAC34 = "&frac34;";
    private static final String E_FRASL = "&frasl;";
    private static final String E_GAMMA = "&gamma;";
    private static final String E_GE = "&ge;";
    private static final String E_GT = "&gt;";
    private static final String E_H_ARR = "&hArr;";
    private static final String E_HARR = "&harr;";
    private static final String E_HEARTS = "&hearts;";
    private static final String E_HELLIP = "&hellip;";
    private static final String E_IACUTE = "&iacute;";
    private static final String E_ICIRC = "&icirc;";
    private static final String E_IEXCL = "&iexcl;";
    private static final String E_IGRAVE = "&igrave;";
    private static final String E_IMAGE = "&image;";
    private static final String E_INFIN = "&infin;";
    private static final String E_INT = "&int;";
    private static final String E_IOTA = "&iota;";
    private static final String E_IQUEST = "&iquest;";
    private static final String E_ISIN = "&isin;";
    private static final String E_IUML = "&iuml;";
    private static final String E_KAPPA = "&kappa;";
    private static final String E_L_ARR = "&lArr;";
    private static final String E_LAMBDA = "&lambda;";
    private static final String E_LANG = "&lang;";
    private static final String E_LAQUO = "&laquo;";
    private static final String E_LARR = "&larr;";
    private static final String E_LCEIL = "&lceil;";
    private static final String E_LDQUO = "&ldquo;";
    private static final String E_LE = "&le;";
    private static final String E_LFLOOR = "&lfloor;";
    private static final String E_LOWAST = "&lowast;";
    private static final String E_LOZ = "&loz;";
    private static final String E_LRM = "&lrm;";
    private static final String E_LSAQUO = "&lsaquo;";
    private static final String E_LSQUO = "&lsquo;";
    private static final String E_LT = "&lt;";
    private static final String E_MACR = "&macr;";
    private static final String E_MDASH = "&mdash;";
    private static final String E_MICRO = "&micro;";
    private static final String E_MIDDOT = "&middot;";
    private static final String E_MINUS = "&minus;";
    private static final String E_MU = "&mu;";
    private static final String E_NABLA = "&nabla;";
    private static final String E_NBSP = "&nbsp;";
    private static final String E_NDASH = "&ndash;";
    private static final String E_NE = "&ne;";
    private static final String E_NI = "&ni;";
    private static final String E_NOT = "&not;";
    private static final String E_NOTIN = "&notin;";
    private static final String E_NSUB = "&nsub;";
    private static final String E_NTILDE = "&ntilde;";
    private static final String E_NU = "&nu;";
    private static final String E_OACUTE = "&oacute;";
    private static final String E_OCIRC = "&ocirc;";
    private static final String E_OELIG = "&oelig;";
    private static final String E_OGRAVE = "&ograve;";
    private static final String E_OLINE = "&oline;";
    private static final String E_OMEGA = "&omega;";
    private static final String E_OMICRON = "&omicron;";
    private static final String E_OPLUS = "&oplus;";
    private static final String E_OR = "&or;";
    private static final String E_ORDF = "&ordf;";
    private static final String E_ORDM = "&ordm;";
    private static final String E_OSLASH = "&oslash;";
    private static final String E_OTILDE = "&otilde;";
    private static final String E_OTIMES = "&otimes;";
    private static final String E_OUML = "&ouml;";
    private static final String E_PARA = "&para;";
    private static final String E_PART = "&part;";
    private static final String E_PERMIL = "&permil;";
    private static final String E_PERP = "&perp;";
    private static final String E_PHI = "&phi;";
    private static final String E_PI = "&pi;";
    private static final String E_PIV = "&piv;";
    private static final String E_PLUSMN = "&plusmn;";
    private static final String E_POUND = "&pound;";
    private static final String E_PRIME = "&prime;";
    private static final String E_PROD = "&prod;";
    private static final String E_PROP = "&prop;";
    private static final String E_PSI = "&psi;";
    private static final String E_QUOT = "&quot;";
    private static final String E_R_ARR = "&rArr;";
    private static final String E_RADIC = "&radic;";
    private static final String E_RANG = "&rang;";
    private static final String E_RAQUO = "&raquo;";
    private static final String E_RARR = "&rarr;";
    private static final String E_RCEIL = "&rceil;";
    private static final String E_RDQUO = "&rdquo;";
    private static final String E_REAL = "&real;";
    private static final String E_REG = "&reg;";
    private static final String E_RFLOOR = "&rfloor;";
    private static final String E_RHO = "&rho;";
    private static final String E_RLM = "&rlm;";
    private static final String E_RSAQUO = "&rsaquo;";
    private static final String E_RSQUO = "&rsquo;";
    private static final String E_SBQUO = "&sbquo;";
    private static final String E_SCARON = "&scaron;";
    private static final String E_SDOT = "&sdot;";
    private static final String E_SECT = "&sect;";
    private static final String E_SHY = "&shy;";
    private static final String E_SIGMA = "&sigma;";
    private static final String E_SIGMAF = "&sigmaf;";
    private static final String E_SIM = "&sim;";
    private static final String E_SPADES = "&spades;";
    private static final String E_SUB = "&sub;";
    private static final String E_SUBE = "&sube;";
    private static final String E_SUM = "&sum;";
    private static final String E_SUP1 = "&sup1;";
    private static final String E_SUP2 = "&sup2;";
    private static final String E_SUP3 = "&sup3;";
    private static final String E_SUP = "&sup;";
    private static final String E_SUPE = "&supe;";
    private static final String E_SZLIG = "&szlig;";
    private static final String E_TAU = "&tau;";
    private static final String E_THERE4 = "&there4;";
    private static final String E_THETA = "&theta;";
    private static final String E_THETASYM = "&thetasym;";
    private static final String E_THINSP = "&thinsp;";
    private static final String E_THORN = "&thorn;";
    private static final String E_TILDE = "&tilde;";
    private static final String E_TIMES = "&times;";
    private static final String E_TRADE = "&trade;";
    private static final String E_U_ARR = "&uArr;";
    private static final String E_UACUTE = "&uacute;";
    private static final String E_UARR = "&uarr;";
    private static final String E_UCIRC = "&ucirc;";
    private static final String E_UGRAVE = "&ugrave;";
    private static final String E_UML = "&uml;";
    private static final String E_UPSIH = "&upsih;";
    private static final String E_UPSILON = "&upsilon;";
    private static final String E_UUML = "&uuml;";
    private static final String E_WEIERP = "&weierp;";
    private static final String E_XI = "&xi;";
    private static final String E_YACUTE = "&yacute;";
    private static final String E_YEN = "&yen;";
    private static final String E_YUML = "&yuml;";
    private static final String E_ZETA = "&zeta;";
    private static final String E_ZWJ = "&zwj;";
    private static final String E_ZWNJ = "&zwnj;";

    private static final String ENCODE = "&#{0};";

//        private static final String WEB_SEPARATOR = "/";
//        private static final String SEPARATOR_HTTP_WEB_PAGE = WEB_SEPARATOR+WEB_SEPARATOR;
//        private static final String INDENT_KEY = "{http:"+SEPARATOR_HTTP_WEB_PAGE+"xml.apache.org/xslt}indent-amount";

    private static final String ALL = "*";

    private static final String EMPTY_STRING = "";

    private static final char ENCODED = '&';

    private static final char NUMBERED_CHAR = '#';

    private static final char SLASH = '/';

    private static final char TAB = '\t';

    private static final char LINE_RETURN = '\n';

    //    private static final String PER_CENT = "%";
    //    private static final String ENCODE_PER_CENT = "%25";
    //    private static final String SPACE = " ";
    //    private static final String ENCODE_SPACE = "+";

    private static final String YES = "yes";
    private static final String XML = "xml";

    //    private static final String NO_DIGITS = "[^0-9]+";

//        private static final int NB_INDENT = 4;

    private static final char END_ESCAPED = ';';

    private static final char EQUALS = '=';

    private XmlParser() {
    }

    public static Element getFirstElementByAttribute(Document _doc, String _attr, String _value) {
        NodeList all_ = _doc.getElementsByTagName(ALL);
        int lengthAll_ = all_.getLength();
        for (int i = CustList.FIRST_INDEX; i < lengthAll_; i++) {
            Node n_ = all_.item(i);
            if (StringList.quickEq(((Element) n_).getAttribute(_attr),_value)) {
                return (Element) n_;
            }
        }
        return null;
    }

    public static Element getElementById(Document _doc, String _attr, String _secAttr, String _id) {
        Element element_ = null;
        NodeList all_ = _doc.getElementsByTagName(ALL);
        int lengthAll_ = all_.getLength();
        for (int i = CustList.FIRST_INDEX; i < lengthAll_; i++) {
            Node n_ = all_.item(i);
            //            if (!(n_ instanceof Element)) {
                //                continue;
                //            }
            if (_id == null) {
                continue;
            }
            if (StringList.quickEq(((Element) n_).getAttribute(_attr),_id)) {
                element_ = (Element) n_;
                break;
            }
            if (StringList.quickEq(((Element) n_).getAttribute(_secAttr),_id)) {
                element_ = (Element) n_;
                break;
            }
        }
        return element_;
    }

    public static RowCol getRowColOfNodeOrAttribute(String _xml, Node _node, int _offest, String _attribute, int _tabWidth) {
        return getRowColOfNodeOrAttribute(_xml, _node, _offest, _attribute, _tabWidth, false);
    }

    public static RowCol getRowColOfNodeOrAttribute(String _xml, Node _node, int _offest,
            String _attribute, int _tabWidth, boolean _attrValue) {
        if (_node == null) {
            return new RowCol();
        }
        int index_ = getIndexOfNodeOrAttribute(_xml, _node, _attribute, _attrValue);
        return getRowColOfString(_xml, index_ + _offest, _tabWidth);
    }

    public static RowCol getRowColOfString(String _string, int _index, int _tabWidth) {
        //        String sub_ = _string.substring(CustList.FIRST_INDEX, _index + 1);
        int lastIndex_ = _string.lastIndexOf(LINE_RETURN, _index);
        int nbChars_ = 0;
        int nbLine_ = CustList.ONE_ELEMENT;
        for (int i = lastIndex_; i >= CustList.FIRST_INDEX; i--) {
            if (_string.charAt(i) == LINE_RETURN) {
                nbLine_++;
            }
        }
        for (int i = _index; i > lastIndex_; i--) {
            if (_string.charAt(i) == TAB) {
                nbChars_ += _tabWidth;
            } else {
                nbChars_++;
            }
        }
        RowCol rc_ = new RowCol();
        rc_.setRow(nbLine_);
        rc_.setCol(nbChars_);
        return rc_;
    }

    public static int getIndexOfNodeOrAttribute(String _xml, Node _node, String _attribute) {
        return getIndexOfNodeOrAttribute(_xml, _node, _attribute, false);
    }

    public static
            ElementOffsetsNext getIndexesOfElementOrAttribute(
                    String _xml, ElementOffsetsNext _previous,
                    Element _node, int _tabWidth) {
        int next_ = CustList.INDEX_NOT_FOUND_ELT;
//        int nextLineReturn_ = CustList.FIRST_INDEX;
        StringMap<RowCol> m_ = new StringMap<RowCol>();
        StringMap<Numbers<Integer>> offsetsMap_;
        offsetsMap_ = new StringMap<Numbers<Integer>>();
        Numbers<Integer> offsets_ = new Numbers<Integer>();
        StringMap<Numbers<Integer>> tabsMap_;
        tabsMap_ = new StringMap<Numbers<Integer>>();
        Numbers<Integer> tabs_ = new Numbers<Integer>();
        int index_ = _previous.getNextElt();
        String nodeName_ = _node.getNodeName();
        int found_ = _xml.indexOf(LT+nodeName_, index_);
        int nbLineReturns_ = 0;
        int minLine_ = _previous.getNextCol().getRow();
        int indexLoc_ = _previous.getNextCol().getCol();
//        int rem_ = 0;
//        int lret_ = _previous.getNextEltLineReturn();
        for (int i = index_; i <= found_; i++) {
            if (_xml.charAt(i) == LINE_RETURN) {
                nbLineReturns_++;
//                indexLoc_ = 1;
                indexLoc_ = 0;
//                rem_ = -1;
            } else {
                if (_xml.charAt(i) == TAB) {
                    indexLoc_+=_tabWidth-1;
                }
                indexLoc_++;
            }
//            indexLoc_++;
        }
        RowCol rc_ = new RowCol();
        rc_.setRow(nbLineReturns_+minLine_+1);
//        rc_.setCol(indexLoc_+1+rem_);
        rc_.setCol(indexLoc_+1);
//        rc_.setCol(indexLoc_);
        m_.put(EMPTY_STRING, rc_);
        int len_ = _xml.length();
//        int i_ = found_+1;
        int i_ = found_+1;
        int nodeLen_ = nodeName_.length();
        int k_ = 0;
        boolean addAttribute_ = true;
        Character delimiter_ = null;
        StringBuilder str_ = new StringBuilder();
        int j_ = indexLoc_;
        int offset_ = 0;
        RowCol endHeader_ = new RowCol();
        RowCol nextCol_ = new RowCol();
//        boolean slash_ = false;
        while (i_ < len_) {
            char ch_ = _xml.charAt(i_);
            if (ch_ == GT) {
                if (addAttribute_) {
                    endHeader_.setRow(nbLineReturns_+minLine_+1);
//                rc_.setCol(j_+1+rem_);
                    endHeader_.setCol(j_+2);
                }
                addAttribute_ = false;
//                if (slash_) {
//                    break;
//                }
            }
            if (addAttribute_ && k_ > nodeLen_) {
                if (delimiter_ == null) {
                    if (ch_ == APOS) {
                        delimiter_ = ch_;
                    } else if (ch_ == QUOT) {
                        delimiter_ = ch_;
                    }
                    if (delimiter_ == null) {
                        if (!Character.isWhitespace(ch_) && ch_ != EQUALS) {
                            str_.append(ch_);
                        }
//                        if (Character.isWhitespace(ch_) || ch_ == EQUALS) {
//                            rc_ = new RowCol();
//                            rc_.setRow(nbLineReturns_+minLine_+1);
//                            rc_.setCol(j_+1);
//                            m_.put(str_.toString(), rc_);
//                            if (ch_ == EQUALS) {
//                                str_ = new StringBuilder();
//                            }
//                        } else {
//                            str_.append(ch_);
//                        }
                    } else {
                        rc_ = new RowCol();
                        rc_.setRow(nbLineReturns_+minLine_+1);
//                        rc_.setCol(j_+1+rem_);
                        rc_.setCol(j_+2);
                        offset_ = 0;
//                        rc_.setCol(j_+1);
                    }
                } else {
                    if (ch_ == delimiter_) {
                        delimiter_ = null;
                        m_.put(str_.toString(), rc_);
                        offsetsMap_.put(str_.toString(), offsets_);
                        offsets_ = new Numbers<Integer>();
                        tabsMap_.put(str_.toString(), tabs_);
                        tabs_ = new Numbers<Integer>();
                        str_ = new StringBuilder();
                    } else if (ch_ == LINE_RETURN) {
                        offsets_.add(offset_);
                    } else if (ch_ == TAB) {
                        tabs_.add(offset_);
                    }
                    offset_++;
//                    if (delimiter_ == null) {
//                        if (Character.isWhitespace(ch_) || ch_ == EQUALS) {
//                            rc_ = new RowCol();
//                            rc_.setRow(nbLineReturns_+minLine_+1);
//                            rc_.setCol(j_+1);
//                            m_.put(str_.toString(), rc_);
//                            if (ch_ == EQUALS) {
//                                str_ = new StringBuilder();
//                            }
//                        } else {
//                            str_.append(ch_);
//                        }
//                    }
                }
            } else if (ch_ == LT && k_ >= nodeLen_ - 1) {
                if (i_ + 1 < len_) {
                    if (_xml.charAt(i_ + 1) != SLASH) {
                        if (_xml.charAt(i_ + 1) != BEGIN_COMMENT) {
                            next_ = i_;
                            nextCol_.setRow(nbLineReturns_+minLine_);
                            nextCol_.setCol(j_);
                            break;
                        }
                        i_ += NEXT_COMMENT.length();
                        j_ += NEXT_COMMENT.length();
                        while (true) {
                            char locCh_ = _xml.charAt(i_);
                            if (locCh_ == LINE_RETURN) {
                                nbLineReturns_++;
//                                rem_ = -1;
                                j_ = 0;
                            }
                            if (_xml.substring(i_).startsWith(END_COMMENT)) {
                                break;
                            }
                            i_++;
                            if (locCh_ != LINE_RETURN) {
                                j_++;
                                if (locCh_ == TAB) {
                                    j_+=_tabWidth-1;
                                }
                            }
//                            j_++;
                        }
//                        i_ = _xml.indexOf(END_COMMENT, i_) + END_COMMENT.length();
                        i_ += END_COMMENT.length();
                        j_ += END_COMMENT.length();
                    }
                }
            }
            k_++;
            if (ch_ == LINE_RETURN) {
                nbLineReturns_++;
//                rem_ = -1;
//                nextLineReturn_ = i_;
                j_ = 0;
            } else {
                j_++;
                if (ch_ == TAB) {
                    j_+=_tabWidth-1;
                }
            }
//            if (ch_ == SLASH) {
//                slash_ = true;
//            }
            i_++;
//            j_++;
        }
//        return new ElementOffsetsNext(m_, nextCol_, next_, nextLineReturn_);
        return new ElementOffsetsNext(tabsMap_, offsetsMap_, m_, nextCol_, endHeader_, next_, found_ + 1);
    }
    public static RowCol getOffset(
            String _attribute,
            StringMap<RowCol> _attributes,
            StringMap<NatTreeMap<Integer,Integer>> _specialChars,
            int _offset,
            StringMap<Numbers<Integer>> _offsets,
            StringMap<Numbers<Integer>> _tabs,
            RowCol _endHeader,
            int _tabWidth) {
        if (!_attributes.contains(_attribute)) {
            return _endHeader;
        }
        RowCol offset_ = _attributes.getVal(_attribute);
        if (_attribute.isEmpty()) {
            return offset_;
        }
        int delta_ = 0;
        if (_specialChars != null) {
            NatTreeMap<Integer, Integer> esc_ = _specialChars.getVal(_attribute);
            if (esc_ != null) {
                int nbIndexes_ = getIndexesCount(_offset, esc_);
//                NodeAttribute na_ = new NodeAttribute();
//                na_.setNode(processingNode);
//                na_.setAttribue(processingAttribute);
                for (int i = 0; i < nbIndexes_; i++) {
                    delta_ += esc_.getValue(i);
                }
            }
        }
        delta_ += _offset;
        Numbers<Integer> offsets_ = _offsets.getVal(_attribute);
        Numbers<Integer> tabs_ = _tabs.getVal(_attribute);
        RowCol ret_ = new RowCol();
        boolean exist_ = false;
        int index_ = CustList.FIRST_INDEX;
        if (!offsets_.isEmpty()) {
            int i_ = CustList.FIRST_INDEX;
            while (i_ < offsets_.size()) {
                if (offsets_.get(i_) > delta_) {
                    break;
                }
                i_++;
            }
//            int toCurrent_ = 0;
            if (i_ > CustList.FIRST_INDEX) {
                exist_ = true;
                index_ = offsets_.get(i_ - 1);
//                toCurrent_ = delta_ - index_;
//            } else {
//                toCurrent_ = delta_;
            }
            ret_.setRow(offset_.getRow()+i_);
        } else {
            ret_.setRow(offset_.getRow());
        }
        int nb_ = 0;
        for (int i = index_; i < delta_; i++) {
            if (tabs_.containsObj(i)) {
                nb_ += _tabWidth;
            } else {
                nb_++;
            }
        }
        if (exist_) {
            ret_.setCol(nb_);
        } else {
            ret_.setCol(nb_+offset_.getCol());
        }
        return ret_;
    }
    private static int getIndexesCount(int _offset, NatTreeMap<Integer, Integer> _t) {
        int delta_ = 0;
        int count_ = 0;
        for (EntryCust<Integer, Integer> e: _t.entryList()) {
            if (e.getKey() - delta_ >= _offset) {
                return count_;
            }
            delta_ += e.getValue();
            count_++;
        }
        return count_;
    }
    public static StringMap<NatTreeMap<Integer,Integer>> getSpecialChars(String _html, Element _element) {
        StringMap<NatTreeMap<Integer,Integer>> encoded_;
        encoded_ = new StringMap<NatTreeMap<Integer,Integer>>();
        String html_ = _html;
        int index_ = getIndexOfNodeOrAttribute(html_, _element, EMPTY_STRING);
        int endHeader_ = html_.indexOf(GT, index_);
        int beginHeader_ = index_ + _element.getNodeName().length();
        StringMap<AttributePart> attr_;
        attr_ = getAttributes(html_, beginHeader_, endHeader_);
        for (EntryCust<String, AttributePart> e: attr_.entryList()) {
//            NodeAttribute nodeAttr_ = new NodeAttribute();
//            nodeAttr_.setNode(associateElement);
//            nodeAttr_.setAttribue(e.getKey());
            encoded_.put(e.getKey(), getIndexesSpecChars(html_, true, e.getValue(), index_));
        }
        return encoded_;
    }
    public static NatTreeMap<Integer, Integer> getIndexesSpecChars(
            String _html, boolean _realAttr, AttributePart _att, int _beginNode) {
        int begin_ = _att.getBegin();
        int end_ = _att.getEnd();
        int i_ = begin_;
        int delta_ = 0;
        if (_realAttr) {
            delta_ = begin_ - _beginNode;
        }
        int beginEscaped_ = i_;
        NatTreeMap<Integer, Integer> indexes_;
        indexes_ = new NatTreeMap<Integer, Integer>();
        while (i_ < end_) {
            if (_html.charAt(i_) == ENCODED) {
                beginEscaped_ = i_;
                i_++;
                while (_html.charAt(i_) != END_ESCAPED) {
                    i_++;
                }
                indexes_.put(beginEscaped_ - _beginNode - delta_, i_ - beginEscaped_);
            }
            i_++;
        }
        return indexes_;
    }
    public static StringMap<AttributePart> getAttributes(String _html, int _from, int _to) {
        StringMap<AttributePart> attributes_;
        attributes_ = new StringMap<AttributePart>();
        StringBuilder str_ = new StringBuilder();
        int beginToken_ = _from;
        Character delimiter_ = null;
        for (int i = _from; i < _to; i++) {
            char ch_ = _html.charAt(i);
            if (delimiter_ == null) {
                if (ch_ == APOS) {
                    delimiter_ = ch_;
                    beginToken_ = i + 1;
                } else if (ch_ == QUOT) {
                    delimiter_ = ch_;
                    beginToken_ = i + 1;
                }
            } else {
                if (ch_ == delimiter_) {
                    AttributePart attrPart_ = new AttributePart();
                    attrPart_.setBegin(beginToken_);
                    attrPart_.setEnd(i);
                    attributes_.put(str_.toString(), attrPart_);
                    str_ = new StringBuilder();
                    delimiter_ = null;
                    continue;
                }
            }
            if (delimiter_ == null) {
                if (Character.isWhitespace(ch_) || ch_ == EQUALS) {
                    continue;
                }
                str_.append(ch_);
            }
        }
        return attributes_;
    }
    public static int indexOfBeginNode(Node _node, String _html, int _from) {
        if (_node instanceof Element) {
            return _html.indexOf(LT+_node.getNodeName(), _from) + 1;
        }
        if (_node instanceof Text) {
            int indexText_ = _html.indexOf(GT, _from);
            while (true) {
                if (_html.charAt(indexText_ + 1) != LT) {
                    break;
                }
                indexText_ = _html.indexOf(GT, indexText_ + 1);
            }
            return indexText_ + 1;
        }
        return _html.indexOf(EMPTY_STRING+LT+BEGIN_COMMENT, _from);
    }

    public static int getIndexOfNodeOrAttribute(String _xml, Node _node, String _attribute, boolean _attrValue) {
        //        Numbers<Integer> indexes_ = getIndexes(_node);
        //        if (indexes_.isEmpty()) {
            //            return CustList.INDEX_NOT_FOUND_ELT;
            //        }
        Document doc_ = _node.getOwnerDocument();
        Element root_ = doc_.getDocumentElement();
        CustList<Node> nodesBefore_ = getDeepChildNodesDocOrder(root_, _node);
        int nbSameNamedNodes_ = CustList.SIZE_EMPTY;
        //        CustList<Node> sameNamedNodes_ = new CustList<Node>();
        if (_node instanceof Element) {
            String nodeName_ = _node.getNodeName();
            for (Node n: nodesBefore_) {
                if (StringList.quickEq(n.getNodeName(), nodeName_) && n instanceof Element) {
                    nbSameNamedNodes_++;
                }
            }
            int index_ = 0;
            int count_ = 0;
            int nb_ = nbSameNamedNodes_ + 1;
            int found_ = CustList.INDEX_NOT_FOUND_ELT;
            while (count_ < nb_) {
                found_ = _xml.indexOf(LT+nodeName_, index_) + 1;
                //                int j_ = found_ - 1;
                //                boolean isTag_ = _xml.charAt(j_) == LT;
                boolean isTag_ = true;
                //                while (true) {
                //                    if (Character.isWhitespace(_xml.charAt(j_))) {
                //                        j_--;
                //                        continue;
                //                    }
                //                    isTag_ = _xml.charAt(j_) == LT;
                //                    break;
                //                }
                //                if (isTag_) {
                //                    j_ = found_ + nodeName_.length();
                //                    if (!Character.isWhitespace(_xml.charAt(j_))) {
                //                        if (_xml.charAt(j_) != GT) {
                //                            if (_xml.charAt(j_) != SLASH) {
                //                                isTag_ = false;
                //                            }
                //                        }
                //                    }
                //                }
                int j_ = found_ + nodeName_.length();
                if (!Character.isWhitespace(_xml.charAt(j_))) {
                    if (_xml.charAt(j_) != GT) {
                        if (_xml.charAt(j_) != SLASH) {
                            isTag_ = false;
                        }
                    }
                }
                if (isTag_) {
                    count_++;
                }
                //                index_ = found_ + nodeName_.length();
                index_ = _xml.indexOf(LT, found_+ nodeName_.length());
                while (_xml.charAt(index_+1) == BEGIN_COMMENT) {
                    index_ = _xml.indexOf(END_COMMENT, index_+NEXT_COMMENT.length()) + END_COMMENT.length();
                    index_ = _xml.indexOf(LT, index_);
                }
            }
            if (_attribute.isEmpty()) {
                return found_;
            }
            int firstIndex_ = found_ + nodeName_.length();
            while (Character.isWhitespace(_xml.charAt(firstIndex_))) {
                firstIndex_++;
            }
            int lastIndex_ = _xml.indexOf(GT, firstIndex_);
            int beginToken_ = firstIndex_;
            //            TreeMap<Integer, String> attributes_;
            //            attributes_ = new TreeMap<Integer, String>(new);
            StringBuilder str_ = new StringBuilder();
            //            int nbQuotes_ = 0;
            //            boolean change_ = false;
            Character delimiter_ = null;
            int foundAttr_ = CustList.INDEX_NOT_FOUND_ELT;
            for (int i = firstIndex_; i < lastIndex_; i++) {
                char ch_ = _xml.charAt(i);
                if (delimiter_ == null) {
                    if (ch_ == APOS) {
                        //                        nbQuotes_++;
                        delimiter_ = ch_;
                    } else if (ch_ == QUOT) {
                        //                        nbQuotes_++;
                        delimiter_ = ch_;
                    }
                } else {
                    if (ch_ == delimiter_) {
                        delimiter_ = null;
                        //                        change_ = true;
                        beginToken_ = i + 1;
                        while (Character.isWhitespace(_xml.charAt(beginToken_))) {
                            beginToken_++;
                        }
                        continue;
                        //                        nbQuotes_++;
                        //                        if (nbQuotes_ % 2 == 0) {
                        //                            delimiter_ = null;
                        //                        }
                    }
                }
                if (delimiter_ == null) {
                    //                    if (!Character.isWhitespace(ch_) && change_) {
                    //                        beginToken_ = i;
                    //                    }
                    if (Character.isWhitespace(ch_) || ch_ == EQUALS) {
                        if (StringList.quickEq(str_.toString(), _attribute)) {
                            foundAttr_ = beginToken_;
                            break;
                        }
                        if (ch_ == EQUALS) {
                            str_ = new StringBuilder();
                        }
                        continue;
                    }
                    str_.append(ch_);
                    //                    if (ch_ == EQUALS) {
                    //                        attributes_.put(beginToken_, str_.toString());
                    //                        str_ = new StringBuilder();
                    //                    } else {
                    //                        str_.append(ch_);
                    //                    }
                }
            }
            if (foundAttr_ == CustList.INDEX_NOT_FOUND_ELT) {
                return lastIndex_;
            }
            //            if (attributes_.getKeys(_attribute).isEmpty()) {
            //                return lastIndex_;
            //            }
            //            foundAttr_ = attributes_.getKeys(_attribute).first();
            if (_attrValue) {
                foundAttr_ += _attribute.length();
                while (true) {
                    if (_xml.charAt(foundAttr_) == QUOT) {
                        break;
                    }
                    if (_xml.charAt(foundAttr_) == APOS) {
                        break;
                    }
                    foundAttr_++;
                }
                foundAttr_++;
            }
            return foundAttr_;
        }
        String searchedText_ = _node.getTextContent();
        for (Node n: nodesBefore_) {
            if (!(n instanceof Text)) {
                continue;
            }
            if (StringList.quickEq(n.getTextContent(), searchedText_)) {
                nbSameNamedNodes_++;
            }
        }
        StringBuilder arg_ = new StringBuilder();
        int i_ = _xml.indexOf(LT) + 1;
        int found_ = CustList.INDEX_NOT_FOUND_ELT;
        int count_ = 0;
        int nb_ = nbSameNamedNodes_ + 1;
        boolean inside_ = false;
        while (true) {
            if (count_ >= nb_) {
                break;
            }
            char cur_ = _xml.charAt(i_);
            if (cur_ == GT) {
                inside_ = true;
                i_++;
                found_ = i_;
            } else if (cur_ == LT) {
                inside_ = false;
                StringBuilder formatted_ = new StringBuilder();
                int j_ = 0;
                int lenArg_ = arg_.length();
                while (j_ < lenArg_) {
                    char curCharArg_ = arg_.charAt(j_);
                    if (curCharArg_ != ENCODED) {
                        formatted_.append(curCharArg_);
                    } else {
                        if (arg_.charAt(j_+1) == NUMBERED_CHAR) {
                            j_++;
                            j_++;
                            StringBuilder nbArg_ = new StringBuilder();
                            char charArg_ = arg_.charAt(j_);
                            while (charArg_ != END_ESCAPED) {
                                j_++;
                                nbArg_.append(charArg_);
                                charArg_ = arg_.charAt(j_);
                            }
                            int intArg_ = Integer.parseInt(nbArg_.toString());
                            formatted_.append((char)intArg_);
                            j_++;
                            continue;
                        }
                        StringBuilder strArg_ = new StringBuilder();
                        char charArg_ = arg_.charAt(j_);
                        while (charArg_ != END_ESCAPED) {
                            j_++;
                            strArg_.append(charArg_);
                            charArg_ = arg_.charAt(j_);
                        }
                        String convered_ = encodeHtml(strArg_.toString()+END_ESCAPED);
                        convered_ = convered_.substring(CustList.SECOND_INDEX + 1, convered_.length() - 1);
                        int intArg_ = Integer.parseInt(convered_);
                        formatted_.append((char)intArg_);
                        j_++;
                        continue;
                    }
                    j_++;
                }
                if (StringList.quickEq(formatted_.toString(), searchedText_)) {
                    count_++;
                }
                arg_ = new StringBuilder();
                i_++;
            } else if (inside_) {
                arg_.append(cur_);
                i_++;
            } else {
                i_++;
            }
        }
        return found_;
        //        int index_ = 0;
        //        String surroundText_ = GT+searchedText_+LT;
        //        while (count_ < nb_) {
            //            found_ = _xml.indexOf(surroundText_, index_);
            //            count_ ++;
            //            index_ = found_ + surroundText_.length();
            //        }
        //        return found_ + 1;
    }

    public static CustList<Node> getDeepChildNodesDocOrder(Node _root, Node _until) {
        CustList<Node> nodes_ = new CustList<Node>();
        if (_root == null) {
            return nodes_;
        }
        Node en_ = _root;
        while (true) {
            if (en_ == _until) {
                break;
            }
            nodes_.add(en_);
            Node n_ = en_.getFirstChild();
            if (n_ != null) {
                en_ = n_;
                continue;
            }
            if (en_ == _root) {
                break;
            }
            n_ = en_.getNextSibling();
            if (n_ != null) {
                en_ = n_;
                continue;
            }
            n_ = en_.getParentNode();
            if (n_ == _root) {
                break;
            }
            Node next_ = n_.getNextSibling();
            while (next_ == null) {
                Node par_ = n_.getParentNode();
                if (par_ == _root) {
                    break;
                }
                next_ = par_.getNextSibling();
                n_ = par_;
            }
            if (next_ == null) {
                break;
            }
            en_ = next_;
        }
        return nodes_;
    }

    public static Numbers<Integer> getIndexes(Node _node) {
        Node par_ = _node.getParentNode();
        Numbers<Integer> indexes_ = new Numbers<Integer>();
        if (par_ == null) {
            return indexes_;
        }
        Document doc_ = _node.getOwnerDocument();
        Element root_ = doc_.getDocumentElement();
        Node currentParent_ = par_;
        Node current_ = _node;
        while (true) {
            if (current_ == root_) {
                break;
            }
            int index_ = CustList.FIRST_INDEX;
            for (Node c: childrenNodes(currentParent_)) {
                if (c == current_) {
                    indexes_.add(CustList.FIRST_INDEX, index_);
                    break;
                }
                index_++;
            }
            current_ = currentParent_;
            currentParent_ = currentParent_.getParentNode();
        }
        return indexes_;
    }

    public static CustList<Node> childrenNodes(Node _noeud) {
        CustList<Node> noeuds_ = new CustList<Node>();
        NodeList noeudsListe_ = _noeud.getChildNodes();
        int nbNoeuds_ = noeudsListe_.getLength();
        for (int i = CustList.FIRST_INDEX; i < nbNoeuds_; i++) {
            noeuds_.add(noeudsListe_.item(i));
        }
        return noeuds_;
    }

    public static CustList<Element> childrenElements(Node _noeud) {
        CustList<Element> noeuds_ = new CustList<Element>();
        NodeList noeudsListe_ = _noeud.getChildNodes();
        int nbNoeuds_ = noeudsListe_.getLength();
        for (int i = CustList.FIRST_INDEX; i < nbNoeuds_; i++) {
            Node n_ = noeudsListe_.item(i);
            if (n_ instanceof Element) {
                noeuds_.add((Element)n_);
            }
        }
        return noeuds_;
    }

    public static Document parseSaxHtml(String _xml) {
        return parseSaxHtml(_xml, false);
    }

    public static Document parseSaxHtmlRowCol(String _xml) {
        String enc_ = encodeHtml(_xml);
        return parseSaxNotNullRowCol(enc_);
    }

    public static Document parseSaxHtml(String _xml, boolean _acceptNull) {
        String enc_ = encodeHtml(_xml);
        //replace map of strings
        //        Map<String,String> map_ = new Map<String,String>();
        //        map_.put(PREFIXED_BEGIN, UNPREFIXED_BEGIN);
        //        map_.put(PREFIXED_END, UNPREFIXED_END);
        //        String res_ = StringList.replace(enc_, map_);
        //        StringBuilder escapedXml_ = new StringBuilder();
        //        for (char c: res_.toCharArray()) {
            //            if (c >= ASCII_128) {
        //                escapedXml_.append(ENCODED+NUMBERED_CHAR);
        //                escapedXml_.append((int)c);
        //                escapedXml_.append(END_ESCAPED);
        //            } else {
        //                escapedXml_.append(c);
        //            }
        //        }
        //        String xmlString_ = escapedXml_.toString();
        return parseSaxNotNull(enc_, _acceptNull);
        //        enc_ = StringList.replace(enc_, PREFIXED_BEGIN, UNPREFIXED_BEGIN);
        //        enc_ = StringList.replace(enc_, PREFIXED_END, UNPREFIXED_END);
        //        return parseSax(enc_);
    }
    public static Document parseSaxHtmlRowCol(String _xml, boolean _acceptNull, boolean _prefix) {
        String enc_ = encodeHtml(_xml);
        if (_acceptNull) {
            return parseSax(enc_, _prefix);
        }
        return parseSaxNotNullRowCol(enc_, _prefix);
    }

    public static Document parseSaxHtml(String _xml, boolean _acceptNull, boolean _prefix) {
        String enc_ = encodeHtml(_xml);
        if (_acceptNull) {
            return parseSax(enc_, _prefix);
        }
        return parseSaxNotNull(enc_, false, _prefix);
    }
    //    public static String transformSpecialChars(String _htmlText) {
    //        return transformSpecialChars(_htmlText, true);
    //    }

    public static String transformSpecialChars(String _htmlText) {
        return transformSpecialChars(_htmlText, true);
    }

    public static String transformSpecialChars(String _htmlText, boolean _affectEamp) {
        StringMap<String> map_ = new StringMap<String>();
        map_.put(E_NBSP, StringList.simpleFormat(ENCODE, (int)NBSP));
        map_.put(E_IEXCL, StringList.simpleFormat(ENCODE, (int)IEXCL));
        map_.put(E_CENT, StringList.simpleFormat(ENCODE, (int)CENT));
        map_.put(E_POUND, StringList.simpleFormat(ENCODE, (int)POUND));
        map_.put(E_CURREN, StringList.simpleFormat(ENCODE, (int)CURREN));
        map_.put(E_YEN, StringList.simpleFormat(ENCODE, (int)YEN));
        map_.put(E_BRVBAR, StringList.simpleFormat(ENCODE, (int)BRVBAR));
        map_.put(E_SECT, StringList.simpleFormat(ENCODE, (int)SECT));
        map_.put(E_UML, StringList.simpleFormat(ENCODE, (int)UML));
        map_.put(E_COPY, StringList.simpleFormat(ENCODE, (int)COPY));
        map_.put(E_ORDF, StringList.simpleFormat(ENCODE, (int)ORDF));
        map_.put(E_LAQUO, StringList.simpleFormat(ENCODE, (int)LAQUO));
        map_.put(E_NOT, StringList.simpleFormat(ENCODE, (int)NOT));
        map_.put(E_SHY, StringList.simpleFormat(ENCODE, (int)SHY));
        map_.put(E_REG, StringList.simpleFormat(ENCODE, (int)REG));
        map_.put(E_MACR, StringList.simpleFormat(ENCODE, (int)MACR));
        map_.put(E_DEG, StringList.simpleFormat(ENCODE, (int)DEG));
        map_.put(E_PLUSMN, StringList.simpleFormat(ENCODE, (int)PLUSMN));
        map_.put(E_SUP2, StringList.simpleFormat(ENCODE, (int)SUP2));
        map_.put(E_SUP3, StringList.simpleFormat(ENCODE, (int)SUP3));
        map_.put(E_ACUTE, StringList.simpleFormat(ENCODE, (int)ACUTE));
        map_.put(E_MICRO, StringList.simpleFormat(ENCODE, (int)MICRO));
        map_.put(E_PARA, StringList.simpleFormat(ENCODE, (int)PARA));
        map_.put(E_MIDDOT, StringList.simpleFormat(ENCODE, (int)MIDDOT));
        map_.put(E_CEDIL, StringList.simpleFormat(ENCODE, (int)CEDIL));
        map_.put(E_SUP1, StringList.simpleFormat(ENCODE, (int)SUP1));
        map_.put(E_ORDM, StringList.simpleFormat(ENCODE, (int)ORDM));
        map_.put(E_RAQUO, StringList.simpleFormat(ENCODE, (int)RAQUO));
        map_.put(E_FRAC14, StringList.simpleFormat(ENCODE, (int)FRAC14));
        map_.put(E_FRAC12, StringList.simpleFormat(ENCODE, (int)FRAC12));
        map_.put(E_FRAC34, StringList.simpleFormat(ENCODE, (int)FRAC34));
        map_.put(E_IQUEST, StringList.simpleFormat(ENCODE, (int)IQUEST));
        map_.put(E_U_AGRAVE, StringList.simpleFormat(ENCODE, (int)U_A_GRAVE));
        map_.put(E_U_AACUTE, StringList.simpleFormat(ENCODE, (int)U_A_ACUTE));
        map_.put(E_U_ACIRC, StringList.simpleFormat(ENCODE, (int)U_A_CIRC));
        map_.put(E_U_ATILDE, StringList.simpleFormat(ENCODE, (int)U_A_TILDE));
        map_.put(E_U_AUML, StringList.simpleFormat(ENCODE, (int)U_A_UML));
        map_.put(E_U_ARING, StringList.simpleFormat(ENCODE, (int)U_A_RING));
        map_.put(E_U_AELIG, StringList.simpleFormat(ENCODE, (int)U_AE_LIG));
        map_.put(E_U_CCEDIL, StringList.simpleFormat(ENCODE, (int)U_C_CEDIL));
        map_.put(E_U_EGRAVE, StringList.simpleFormat(ENCODE, (int)U_E_GRAVE));
        map_.put(E_U_EACUTE, StringList.simpleFormat(ENCODE, (int)U_E_ACUTE));
        map_.put(E_U_ECIRC, StringList.simpleFormat(ENCODE, (int)U_E_CIRC));
        map_.put(E_U_EUML, StringList.simpleFormat(ENCODE, (int)U_E_UML));
        map_.put(E_U_IGRAVE, StringList.simpleFormat(ENCODE, (int)U_I_GRAVE));
        map_.put(E_U_IACUTE, StringList.simpleFormat(ENCODE, (int)U_I_ACUTE));
        map_.put(E_U_ICIRC, StringList.simpleFormat(ENCODE, (int)U_I_CIRC));
        map_.put(E_U_IUML, StringList.simpleFormat(ENCODE, (int)U_I_UML));
        map_.put(E_U_ETH, StringList.simpleFormat(ENCODE, (int)U_ETH));
        map_.put(E_U_NTILDE, StringList.simpleFormat(ENCODE, (int)U_N_TILDE));
        map_.put(E_U_OGRAVE, StringList.simpleFormat(ENCODE, (int)U_O_GRAVE));
        map_.put(E_U_OACUTE, StringList.simpleFormat(ENCODE, (int)U_O_ACUTE));
        map_.put(E_U_OCIRC, StringList.simpleFormat(ENCODE, (int)U_O_CIRC));
        map_.put(E_U_OTILDE, StringList.simpleFormat(ENCODE, (int)U_O_TILDE));
        map_.put(E_U_OUML, StringList.simpleFormat(ENCODE, (int)U_O_UML));
        map_.put(E_TIMES, StringList.simpleFormat(ENCODE, (int)TIMES));
        map_.put(E_U_OSLASH, StringList.simpleFormat(ENCODE, (int)U_O_SLASH));
        map_.put(E_U_UGRAVE, StringList.simpleFormat(ENCODE, (int)U_U_GRAVE));
        map_.put(E_U_UACUTE, StringList.simpleFormat(ENCODE, (int)U_U_ACUTE));
        map_.put(E_U_UCIRC, StringList.simpleFormat(ENCODE, (int)U_U_CIRC));
        map_.put(E_U_UUML, StringList.simpleFormat(ENCODE, (int)U_U_UML));
        map_.put(E_U_YACUTE, StringList.simpleFormat(ENCODE, (int)U_Y_ACUTE));
        map_.put(E_U_THORN, StringList.simpleFormat(ENCODE, (int)U_THORN));
        map_.put(E_SZLIG, StringList.simpleFormat(ENCODE, (int)SZLIG));
        map_.put(E_AGRAVE, StringList.simpleFormat(ENCODE, (int)AGRAVE));
        map_.put(E_AACUTE, StringList.simpleFormat(ENCODE, (int)AACUTE));
        map_.put(E_ACIRC, StringList.simpleFormat(ENCODE, (int)ACIRC));
        map_.put(E_ATILDE, StringList.simpleFormat(ENCODE, (int)ATILDE));
        map_.put(E_AUML, StringList.simpleFormat(ENCODE, (int)AUML));
        map_.put(E_ARING, StringList.simpleFormat(ENCODE, (int)ARING));
        map_.put(E_AELIG, StringList.simpleFormat(ENCODE, (int)AELIG));
        map_.put(E_CCEDIL, StringList.simpleFormat(ENCODE, (int)CCEDIL));
        map_.put(E_EGRAVE, StringList.simpleFormat(ENCODE, (int)EGRAVE));
        map_.put(E_EACUTE, StringList.simpleFormat(ENCODE, (int)EACUTE));
        map_.put(E_ECIRC, StringList.simpleFormat(ENCODE, (int)ECIRC));
        map_.put(E_EUML, StringList.simpleFormat(ENCODE, (int)EUML));
        map_.put(E_IGRAVE, StringList.simpleFormat(ENCODE, (int)IGRAVE));
        map_.put(E_IACUTE, StringList.simpleFormat(ENCODE, (int)IACUTE));
        map_.put(E_ICIRC, StringList.simpleFormat(ENCODE, (int)ICIRC));
        map_.put(E_IUML, StringList.simpleFormat(ENCODE, (int)IUML));
        map_.put(E_ETH, StringList.simpleFormat(ENCODE, (int)ETH));
        map_.put(E_NTILDE, StringList.simpleFormat(ENCODE, (int)NTILDE));
        map_.put(E_OGRAVE, StringList.simpleFormat(ENCODE, (int)OGRAVE));
        map_.put(E_OACUTE, StringList.simpleFormat(ENCODE, (int)OACUTE));
        map_.put(E_OCIRC, StringList.simpleFormat(ENCODE, (int)OCIRC));
        map_.put(E_OTILDE, StringList.simpleFormat(ENCODE, (int)OTILDE));
        map_.put(E_OUML, StringList.simpleFormat(ENCODE, (int)OUML));
        map_.put(E_DIVIDE, StringList.simpleFormat(ENCODE, (int)DIVIDE));
        map_.put(E_OSLASH, StringList.simpleFormat(ENCODE, (int)OSLASH));
        map_.put(E_UGRAVE, StringList.simpleFormat(ENCODE, (int)UGRAVE));
        map_.put(E_UACUTE, StringList.simpleFormat(ENCODE, (int)UACUTE));
        map_.put(E_UCIRC, StringList.simpleFormat(ENCODE, (int)UCIRC));
        map_.put(E_UUML, StringList.simpleFormat(ENCODE, (int)UUML));
        map_.put(E_YACUTE, StringList.simpleFormat(ENCODE, (int)YACUTE));
        map_.put(E_THORN, StringList.simpleFormat(ENCODE, (int)THORN));
        map_.put(E_YUML, StringList.simpleFormat(ENCODE, (int)YUML));
        map_.put(E_QUOT, StringList.simpleFormat(ENCODE, (int)QUOT));
        //        map_.put(E_LT, StringList.simpleFormat(ENCODE, (int)LT));
        //        map_.put(E_GT, StringList.simpleFormat(ENCODE, (int)GT));
        map_.put(E_APOS, StringList.simpleFormat(ENCODE, (int)APOS));
        map_.put(E_U_OELIG, StringList.simpleFormat(ENCODE, (int)U_OE_LIG));
        map_.put(E_OELIG, StringList.simpleFormat(ENCODE, (int)OELIG));
        map_.put(E_S_CARON, StringList.simpleFormat(ENCODE, (int)U_SCARON));
        map_.put(E_SCARON, StringList.simpleFormat(ENCODE, (int)SCARON));
        map_.put(E_U_YUML, StringList.simpleFormat(ENCODE, (int)U_Y_UML));
        map_.put(E_CIRC, StringList.simpleFormat(ENCODE, (int)CIRC));
        map_.put(E_TILDE, StringList.simpleFormat(ENCODE, (int)TILDE));
        map_.put(E_ENSP, StringList.simpleFormat(ENCODE, (int)ENSP));
        map_.put(E_EMSP, StringList.simpleFormat(ENCODE, (int)EMSP));
        map_.put(E_THINSP, StringList.simpleFormat(ENCODE, (int)THINSP));
        map_.put(E_ZWNJ, StringList.simpleFormat(ENCODE, (int)ZWNJ));
        map_.put(E_ZWJ, StringList.simpleFormat(ENCODE, (int)ZWJ));
        map_.put(E_LRM, StringList.simpleFormat(ENCODE, (int)LRM));
        map_.put(E_RLM, StringList.simpleFormat(ENCODE, (int)RLM));
        map_.put(E_NDASH, StringList.simpleFormat(ENCODE, (int)NDASH));
        map_.put(E_MDASH, StringList.simpleFormat(ENCODE, (int)MDASH));
        map_.put(E_LSQUO, StringList.simpleFormat(ENCODE, (int)LSQUO));
        map_.put(E_RSQUO, StringList.simpleFormat(ENCODE, (int)RSQUO));
        map_.put(E_SBQUO, StringList.simpleFormat(ENCODE, (int)SBQUO));
        map_.put(E_LDQUO, StringList.simpleFormat(ENCODE, (int)LDQUO));
        map_.put(E_RDQUO, StringList.simpleFormat(ENCODE, (int)RDQUO));
        map_.put(E_BDQUO, StringList.simpleFormat(ENCODE, (int)BDQUO));
        map_.put(E_DAGGER, StringList.simpleFormat(ENCODE, (int)D_AGGER));
        map_.put(E_U_DAGGER, StringList.simpleFormat(ENCODE, (int)DAGGER));
        map_.put(E_PERMIL, StringList.simpleFormat(ENCODE, (int)PERMIL));
        map_.put(E_LSAQUO, StringList.simpleFormat(ENCODE, (int)LSAQUO));
        map_.put(E_RSAQUO, StringList.simpleFormat(ENCODE, (int)RSAQUO));
        map_.put(E_EURO, StringList.simpleFormat(ENCODE, (int)EURO));
        map_.put(E_FNOF, StringList.simpleFormat(ENCODE, (int)FNOF));
        map_.put(E_U_ALPHA, StringList.simpleFormat(ENCODE, (int)U_A_LPHA));
        map_.put(E_U_BETA, StringList.simpleFormat(ENCODE, (int)U_B_ETA));
        map_.put(E_U_GAMMA, StringList.simpleFormat(ENCODE, (int)U_G_AMMA));
        map_.put(E_U_DELTA, StringList.simpleFormat(ENCODE, (int)U_D_ELTA));
        map_.put(E_U_EPSILON, StringList.simpleFormat(ENCODE, (int)U_E_PSILON));
        map_.put(E_U_ZETA, StringList.simpleFormat(ENCODE, (int)U_Z_ETA));
        map_.put(E_U_ETA, StringList.simpleFormat(ENCODE, (int)U_E_TA));
        map_.put(E_U_THETA, StringList.simpleFormat(ENCODE, (int)U_T_HETA));
        map_.put(E_U_IOTA, StringList.simpleFormat(ENCODE, (int)U_I_OTA));
        map_.put(E_U_KAPPA, StringList.simpleFormat(ENCODE, (int)U_K_APPA));
        map_.put(E_U_LAMBDA, StringList.simpleFormat(ENCODE, (int)U_L_AMBDA));
        map_.put(E_U_MU, StringList.simpleFormat(ENCODE, (int)U_M_U));
        map_.put(E_U_NU, StringList.simpleFormat(ENCODE, (int)U_N_U));
        map_.put(E_U_XI, StringList.simpleFormat(ENCODE, (int)U_X_I));
        map_.put(E_U_OMICRON, StringList.simpleFormat(ENCODE, (int)U_O_MICRON));
        map_.put(E_U_PI, StringList.simpleFormat(ENCODE, (int)U_P_I));
        map_.put(E_U_RHO, StringList.simpleFormat(ENCODE, (int)U_R_HO));
        map_.put(E_U_SIGMA, StringList.simpleFormat(ENCODE, (int)U_S_IGMA));
        map_.put(E_U_TAU, StringList.simpleFormat(ENCODE, (int)U_T_AU));
        map_.put(E_U_UPSILON, StringList.simpleFormat(ENCODE, (int)U_U_PSILON));
        map_.put(E_U_PHI, StringList.simpleFormat(ENCODE, (int)U_P_HI));
        map_.put(E_U_CHI, StringList.simpleFormat(ENCODE, (int)U_C_HI));
        map_.put(E_U_PSI, StringList.simpleFormat(ENCODE, (int)U_P_SI));
        map_.put(E_U_OMEGA, StringList.simpleFormat(ENCODE, (int)U_O_MEGA));
        map_.put(E_ALPHA, StringList.simpleFormat(ENCODE, (int)ALPHA));
        map_.put(E_BETA, StringList.simpleFormat(ENCODE, (int)BETA));
        map_.put(E_GAMMA, StringList.simpleFormat(ENCODE, (int)GAMMA));
        map_.put(E_DELTA, StringList.simpleFormat(ENCODE, (int)DELTA));
        map_.put(E_EPSILON, StringList.simpleFormat(ENCODE, (int)EPSILON));
        map_.put(E_ZETA, StringList.simpleFormat(ENCODE, (int)ZETA));
        map_.put(E_ETA, StringList.simpleFormat(ENCODE, (int)ETA));
        map_.put(E_THETA, StringList.simpleFormat(ENCODE, (int)THETA));
        map_.put(E_IOTA, StringList.simpleFormat(ENCODE, (int)IOTA));
        map_.put(E_KAPPA, StringList.simpleFormat(ENCODE, (int)KAPPA));
        map_.put(E_LAMBDA, StringList.simpleFormat(ENCODE, (int)LAMBDA));
        map_.put(E_MU, StringList.simpleFormat(ENCODE, (int)MU));
        map_.put(E_NU, StringList.simpleFormat(ENCODE, (int)NU));
        map_.put(E_XI, StringList.simpleFormat(ENCODE, (int)XI));
        map_.put(E_OMICRON, StringList.simpleFormat(ENCODE, (int)OMICRON));
        map_.put(E_PI, StringList.simpleFormat(ENCODE, (int)PI));
        map_.put(E_RHO, StringList.simpleFormat(ENCODE, (int)RHO));
        map_.put(E_SIGMAF, StringList.simpleFormat(ENCODE, (int)SIGMAF));
        map_.put(E_SIGMA, StringList.simpleFormat(ENCODE, (int)SIGMA));
        map_.put(E_TAU, StringList.simpleFormat(ENCODE, (int)TAU));
        map_.put(E_UPSILON, StringList.simpleFormat(ENCODE, (int)UPSILON));
        map_.put(E_PHI, StringList.simpleFormat(ENCODE, (int)PHI));
        map_.put(E_CHI, StringList.simpleFormat(ENCODE, (int)CHI));
        map_.put(E_PSI, StringList.simpleFormat(ENCODE, (int)PSI));
        map_.put(E_OMEGA, StringList.simpleFormat(ENCODE, (int)OMEGA));
        map_.put(E_THETASYM, StringList.simpleFormat(ENCODE, (int)THETASYM));
        map_.put(E_UPSIH, StringList.simpleFormat(ENCODE, (int)UPSIH));
        map_.put(E_PIV, StringList.simpleFormat(ENCODE, (int)PIV));
        map_.put(E_BULL, StringList.simpleFormat(ENCODE, (int)BULL));
        map_.put(E_HELLIP, StringList.simpleFormat(ENCODE, (int)HELLIP));
        map_.put(E_PRIME, StringList.simpleFormat(ENCODE, (int)PRIME));
        map_.put(E_P_RIME, StringList.simpleFormat(ENCODE, (int)U_PRIME));
        map_.put(E_OLINE, StringList.simpleFormat(ENCODE, (int)OLINE));
        map_.put(E_FRASL, StringList.simpleFormat(ENCODE, (int)FRASL));
        map_.put(E_WEIERP, StringList.simpleFormat(ENCODE, (int)WEIERP));
        map_.put(E_IMAGE, StringList.simpleFormat(ENCODE, (int)IMAGE));
        map_.put(E_REAL, StringList.simpleFormat(ENCODE, (int)REAL));
        map_.put(E_TRADE, StringList.simpleFormat(ENCODE, (int)TRADE));
        map_.put(E_ALEFSYM, StringList.simpleFormat(ENCODE, (int)ALEFSYM));
        map_.put(E_LARR, StringList.simpleFormat(ENCODE, (int)LARR));
        map_.put(E_UARR, StringList.simpleFormat(ENCODE, (int)UARR));
        map_.put(E_RARR, StringList.simpleFormat(ENCODE, (int)RARR));
        map_.put(E_DARR, StringList.simpleFormat(ENCODE, (int)DARR));
        map_.put(E_HARR, StringList.simpleFormat(ENCODE, (int)HARR));
        map_.put(E_CRARR, StringList.simpleFormat(ENCODE, (int)CRARR));
        map_.put(E_L_ARR, StringList.simpleFormat(ENCODE, (int)U_LARR));
        map_.put(E_U_ARR, StringList.simpleFormat(ENCODE, (int)U_UARR));
        map_.put(E_R_ARR, StringList.simpleFormat(ENCODE, (int)U_RARR));
        map_.put(E_D_ARR, StringList.simpleFormat(ENCODE, (int)U_DARR));
        map_.put(E_H_ARR, StringList.simpleFormat(ENCODE, (int)U_HARR));
        map_.put(E_FORALL, StringList.simpleFormat(ENCODE, (int)FORALL));
        map_.put(E_PART, StringList.simpleFormat(ENCODE, (int)PART));
        map_.put(E_EXIST, StringList.simpleFormat(ENCODE, (int)EXIST));
        map_.put(E_EMPTY, StringList.simpleFormat(ENCODE, (int)EMPTY));
        map_.put(E_NABLA, StringList.simpleFormat(ENCODE, (int)NABLA));
        map_.put(E_ISIN, StringList.simpleFormat(ENCODE, (int)ISIN));
        map_.put(E_NOTIN, StringList.simpleFormat(ENCODE, (int)NOTIN));
        map_.put(E_NI, StringList.simpleFormat(ENCODE, (int)NI));
        map_.put(E_PROD, StringList.simpleFormat(ENCODE, (int)PROD));
        map_.put(E_SUM, StringList.simpleFormat(ENCODE, (int)SUM));
        map_.put(E_MINUS, StringList.simpleFormat(ENCODE, (int)MINUS));
        map_.put(E_LOWAST, StringList.simpleFormat(ENCODE, (int)LOWAST));
        map_.put(E_RADIC, StringList.simpleFormat(ENCODE, (int)RADIC));
        map_.put(E_PROP, StringList.simpleFormat(ENCODE, (int)PROP));
        map_.put(E_INFIN, StringList.simpleFormat(ENCODE, (int)INFIN));
        map_.put(E_ANG, StringList.simpleFormat(ENCODE, (int)ANG));
        map_.put(E_AND, StringList.simpleFormat(ENCODE, (int)AND));
        map_.put(E_OR, StringList.simpleFormat(ENCODE, (int)OR));
        map_.put(E_CAP, StringList.simpleFormat(ENCODE, (int)CAP));
        map_.put(E_CUP, StringList.simpleFormat(ENCODE, (int)CUP));
        map_.put(E_INT, StringList.simpleFormat(ENCODE, (int)INT));
        map_.put(E_THERE4, StringList.simpleFormat(ENCODE, (int)THERE4));
        map_.put(E_SIM, StringList.simpleFormat(ENCODE, (int)SIM));
        map_.put(E_CONG, StringList.simpleFormat(ENCODE, (int)CONG));
        map_.put(E_ASYMP, StringList.simpleFormat(ENCODE, (int)ASYMP));
        map_.put(E_NE, StringList.simpleFormat(ENCODE, (int)NE));
        map_.put(E_EQUIV, StringList.simpleFormat(ENCODE, (int)EQUIV));
        map_.put(E_LE, StringList.simpleFormat(ENCODE, (int)LE));
        map_.put(E_GE, StringList.simpleFormat(ENCODE, (int)GE));
        map_.put(E_SUB, StringList.simpleFormat(ENCODE, (int)SUB));
        map_.put(E_SUP, StringList.simpleFormat(ENCODE, (int)SUP));
        map_.put(E_NSUB, StringList.simpleFormat(ENCODE, (int)NSUB));
        map_.put(E_SUBE, StringList.simpleFormat(ENCODE, (int)SUBE));
        map_.put(E_SUPE, StringList.simpleFormat(ENCODE, (int)SUPE));
        map_.put(E_OPLUS, StringList.simpleFormat(ENCODE, (int)OPLUS));
        map_.put(E_OTIMES, StringList.simpleFormat(ENCODE, (int)OTIMES));
        map_.put(E_PERP, StringList.simpleFormat(ENCODE, (int)PERP));
        map_.put(E_SDOT, StringList.simpleFormat(ENCODE, (int)SDOT));
        map_.put(E_LCEIL, StringList.simpleFormat(ENCODE, (int)LCEIL));
        map_.put(E_RCEIL, StringList.simpleFormat(ENCODE, (int)RCEIL));
        map_.put(E_LFLOOR, StringList.simpleFormat(ENCODE, (int)LFLOOR));
        map_.put(E_RFLOOR, StringList.simpleFormat(ENCODE, (int)RFLOOR));
        map_.put(E_LANG, StringList.simpleFormat(ENCODE, (int)LANG));
        map_.put(E_RANG, StringList.simpleFormat(ENCODE, (int)RANG));
        map_.put(E_LOZ, StringList.simpleFormat(ENCODE, (int)LOZ));
        map_.put(E_SPADES, StringList.simpleFormat(ENCODE, (int)SPADES));
        map_.put(E_CLUBS, StringList.simpleFormat(ENCODE, (int)CLUBS));
        map_.put(E_HEARTS, StringList.simpleFormat(ENCODE, (int)HEARTS));
        map_.put(E_DIAMS, StringList.simpleFormat(ENCODE, (int)DIAMS));
        if (_affectEamp) {
            map_.put(E_AMP, StringList.simpleFormat(ENCODE, (int)ASCII_38));
        }
        int length_ = _htmlText.length();
        StringBuilder str_ = new StringBuilder();
        int i_ = 0;
        int iBegin_ = 0;
        while (i_ < length_) {
            char ch_ = _htmlText.charAt(i_);
            if (ch_ != ENCODED) {
                str_.append(ch_);
                i_++;
                continue;
            }
            iBegin_ = i_;
            while(true) {
                i_++;
                if (i_ >= length_) {
                    break;
                }
                ch_ = _htmlText.charAt(i_);
                //                if (!Character.isLetterOrDigit(ch_)) {
                    //                    break;
                    //                }
                if (ch_ == END_ESCAPED) {
                    break;
                }
            }
            if (i_ >= length_) {
                str_.append(_htmlText.substring(iBegin_));
                break;
            }
            boolean add_ = false;
            for (EntryCust<String,String> k: map_.entryList()) {
                boolean equals_ = true;
                int j_ = 0;
                String key_ = k.getKey();
                for (int i = iBegin_; i <= i_; i++) {
                    if (_htmlText.charAt(i) != key_.charAt(j_)) {
                        equals_ = false;
                        break;
                    }
                    j_++;
                }
                if (equals_) {
                    String strValue_ = k.getValue();
                    strValue_ = strValue_.substring(2, strValue_.length() - 1);
                    //                    Integer ascii_ = new Integer(k.getValue().replaceAll(NO_DIGITS, EMPTY_STRING));
                    int ascii_ = Integer.parseInt(strValue_);
                    char char_ = (char) ascii_;
                    str_.append(char_);
                    i_++;
                    add_ = true;
                    break;
                }
            }
            if (!add_) {
                if (_htmlText.charAt(iBegin_ + 1) == NUMBERED_CHAR) {
                    String strValue_ = _htmlText.substring(iBegin_ + 2, i_);
                    int ascii_;
                    try {
                        ascii_ = Integer.parseInt(strValue_);
                    } catch (NumberFormatException _0) {
                        throw new BadNumberedCharacterException(_htmlText, iBegin_ + 2, strValue_);
                    }
                    char char_ = (char) ascii_;
                    str_.append(char_);
                    i_++;
                    continue;
                }
                str_.append(_htmlText.substring(iBegin_, i_ + 1));
                i_++;
            }
        }
        return str_.toString();
    }

    public static String encodeHtml(String _htmlText) {
        StringMap<String> map_ = new StringMap<String>();
        map_.put(E_NBSP, StringList.simpleFormat(ENCODE, (int)NBSP));
        map_.put(E_IEXCL, StringList.simpleFormat(ENCODE, (int)IEXCL));
        map_.put(E_CENT, StringList.simpleFormat(ENCODE, (int)CENT));
        map_.put(E_POUND, StringList.simpleFormat(ENCODE, (int)POUND));
        map_.put(E_CURREN, StringList.simpleFormat(ENCODE, (int)CURREN));
        map_.put(E_YEN, StringList.simpleFormat(ENCODE, (int)YEN));
        map_.put(E_BRVBAR, StringList.simpleFormat(ENCODE, (int)BRVBAR));
        map_.put(E_SECT, StringList.simpleFormat(ENCODE, (int)SECT));
        map_.put(E_UML, StringList.simpleFormat(ENCODE, (int)UML));
        map_.put(E_COPY, StringList.simpleFormat(ENCODE, (int)COPY));
        map_.put(E_ORDF, StringList.simpleFormat(ENCODE, (int)ORDF));
        map_.put(E_LAQUO, StringList.simpleFormat(ENCODE, (int)LAQUO));
        map_.put(E_NOT, StringList.simpleFormat(ENCODE, (int)NOT));
        map_.put(E_SHY, StringList.simpleFormat(ENCODE, (int)SHY));
        map_.put(E_REG, StringList.simpleFormat(ENCODE, (int)REG));
        map_.put(E_MACR, StringList.simpleFormat(ENCODE, (int)MACR));
        map_.put(E_DEG, StringList.simpleFormat(ENCODE, (int)DEG));
        map_.put(E_PLUSMN, StringList.simpleFormat(ENCODE, (int)PLUSMN));
        map_.put(E_SUP2, StringList.simpleFormat(ENCODE, (int)SUP2));
        map_.put(E_SUP3, StringList.simpleFormat(ENCODE, (int)SUP3));
        map_.put(E_ACUTE, StringList.simpleFormat(ENCODE, (int)ACUTE));
        map_.put(E_MICRO, StringList.simpleFormat(ENCODE, (int)MICRO));
        map_.put(E_PARA, StringList.simpleFormat(ENCODE, (int)PARA));
        map_.put(E_MIDDOT, StringList.simpleFormat(ENCODE, (int)MIDDOT));
        map_.put(E_CEDIL, StringList.simpleFormat(ENCODE, (int)CEDIL));
        map_.put(E_SUP1, StringList.simpleFormat(ENCODE, (int)SUP1));
        map_.put(E_ORDM, StringList.simpleFormat(ENCODE, (int)ORDM));
        map_.put(E_RAQUO, StringList.simpleFormat(ENCODE, (int)RAQUO));
        map_.put(E_FRAC14, StringList.simpleFormat(ENCODE, (int)FRAC14));
        map_.put(E_FRAC12, StringList.simpleFormat(ENCODE, (int)FRAC12));
        map_.put(E_FRAC34, StringList.simpleFormat(ENCODE, (int)FRAC34));
        map_.put(E_IQUEST, StringList.simpleFormat(ENCODE, (int)IQUEST));
        map_.put(E_U_AGRAVE, StringList.simpleFormat(ENCODE, (int)U_A_GRAVE));
        map_.put(E_U_AACUTE, StringList.simpleFormat(ENCODE, (int)U_A_ACUTE));
        map_.put(E_U_ACIRC, StringList.simpleFormat(ENCODE, (int)U_A_CIRC));
        map_.put(E_U_ATILDE, StringList.simpleFormat(ENCODE, (int)U_A_TILDE));
        map_.put(E_U_AUML, StringList.simpleFormat(ENCODE, (int)U_A_UML));
        map_.put(E_U_ARING, StringList.simpleFormat(ENCODE, (int)U_A_RING));
        map_.put(E_U_AELIG, StringList.simpleFormat(ENCODE, (int)U_AE_LIG));
        map_.put(E_U_CCEDIL, StringList.simpleFormat(ENCODE, (int)U_C_CEDIL));
        map_.put(E_U_EGRAVE, StringList.simpleFormat(ENCODE, (int)U_E_GRAVE));
        map_.put(E_U_EACUTE, StringList.simpleFormat(ENCODE, (int)U_E_ACUTE));
        map_.put(E_U_ECIRC, StringList.simpleFormat(ENCODE, (int)U_E_CIRC));
        map_.put(E_U_EUML, StringList.simpleFormat(ENCODE, (int)U_E_UML));
        map_.put(E_U_IGRAVE, StringList.simpleFormat(ENCODE, (int)U_I_GRAVE));
        map_.put(E_U_IACUTE, StringList.simpleFormat(ENCODE, (int)U_I_ACUTE));
        map_.put(E_U_ICIRC, StringList.simpleFormat(ENCODE, (int)U_I_CIRC));
        map_.put(E_U_IUML, StringList.simpleFormat(ENCODE, (int)U_I_UML));
        map_.put(E_U_ETH, StringList.simpleFormat(ENCODE, (int)U_ETH));
        map_.put(E_U_NTILDE, StringList.simpleFormat(ENCODE, (int)U_N_TILDE));
        map_.put(E_U_OGRAVE, StringList.simpleFormat(ENCODE, (int)U_O_GRAVE));
        map_.put(E_U_OACUTE, StringList.simpleFormat(ENCODE, (int)U_O_ACUTE));
        map_.put(E_U_OCIRC, StringList.simpleFormat(ENCODE, (int)U_O_CIRC));
        map_.put(E_U_OTILDE, StringList.simpleFormat(ENCODE, (int)U_O_TILDE));
        map_.put(E_U_OUML, StringList.simpleFormat(ENCODE, (int)U_O_UML));
        map_.put(E_TIMES, StringList.simpleFormat(ENCODE, (int)TIMES));
        map_.put(E_U_OSLASH, StringList.simpleFormat(ENCODE, (int)U_O_SLASH));
        map_.put(E_U_UGRAVE, StringList.simpleFormat(ENCODE, (int)U_U_GRAVE));
        map_.put(E_U_UACUTE, StringList.simpleFormat(ENCODE, (int)U_U_ACUTE));
        map_.put(E_U_UCIRC, StringList.simpleFormat(ENCODE, (int)U_U_CIRC));
        map_.put(E_U_UUML, StringList.simpleFormat(ENCODE, (int)U_U_UML));
        map_.put(E_U_YACUTE, StringList.simpleFormat(ENCODE, (int)U_Y_ACUTE));
        map_.put(E_U_THORN, StringList.simpleFormat(ENCODE, (int)U_THORN));
        map_.put(E_SZLIG, StringList.simpleFormat(ENCODE, (int)SZLIG));
        map_.put(E_AGRAVE, StringList.simpleFormat(ENCODE, (int)AGRAVE));
        map_.put(E_AACUTE, StringList.simpleFormat(ENCODE, (int)AACUTE));
        map_.put(E_ACIRC, StringList.simpleFormat(ENCODE, (int)ACIRC));
        map_.put(E_ATILDE, StringList.simpleFormat(ENCODE, (int)ATILDE));
        map_.put(E_AUML, StringList.simpleFormat(ENCODE, (int)AUML));
        map_.put(E_ARING, StringList.simpleFormat(ENCODE, (int)ARING));
        map_.put(E_AELIG, StringList.simpleFormat(ENCODE, (int)AELIG));
        map_.put(E_CCEDIL, StringList.simpleFormat(ENCODE, (int)CCEDIL));
        map_.put(E_EGRAVE, StringList.simpleFormat(ENCODE, (int)EGRAVE));
        map_.put(E_EACUTE, StringList.simpleFormat(ENCODE, (int)EACUTE));
        map_.put(E_ECIRC, StringList.simpleFormat(ENCODE, (int)ECIRC));
        map_.put(E_EUML, StringList.simpleFormat(ENCODE, (int)EUML));
        map_.put(E_IGRAVE, StringList.simpleFormat(ENCODE, (int)IGRAVE));
        map_.put(E_IACUTE, StringList.simpleFormat(ENCODE, (int)IACUTE));
        map_.put(E_ICIRC, StringList.simpleFormat(ENCODE, (int)ICIRC));
        map_.put(E_IUML, StringList.simpleFormat(ENCODE, (int)IUML));
        map_.put(E_ETH, StringList.simpleFormat(ENCODE, (int)ETH));
        map_.put(E_NTILDE, StringList.simpleFormat(ENCODE, (int)NTILDE));
        map_.put(E_OGRAVE, StringList.simpleFormat(ENCODE, (int)OGRAVE));
        map_.put(E_OACUTE, StringList.simpleFormat(ENCODE, (int)OACUTE));
        map_.put(E_OCIRC, StringList.simpleFormat(ENCODE, (int)OCIRC));
        map_.put(E_OTILDE, StringList.simpleFormat(ENCODE, (int)OTILDE));
        map_.put(E_OUML, StringList.simpleFormat(ENCODE, (int)OUML));
        map_.put(E_DIVIDE, StringList.simpleFormat(ENCODE, (int)DIVIDE));
        map_.put(E_OSLASH, StringList.simpleFormat(ENCODE, (int)OSLASH));
        map_.put(E_UGRAVE, StringList.simpleFormat(ENCODE, (int)UGRAVE));
        map_.put(E_UACUTE, StringList.simpleFormat(ENCODE, (int)UACUTE));
        map_.put(E_UCIRC, StringList.simpleFormat(ENCODE, (int)UCIRC));
        map_.put(E_UUML, StringList.simpleFormat(ENCODE, (int)UUML));
        map_.put(E_YACUTE, StringList.simpleFormat(ENCODE, (int)YACUTE));
        map_.put(E_THORN, StringList.simpleFormat(ENCODE, (int)THORN));
        map_.put(E_YUML, StringList.simpleFormat(ENCODE, (int)YUML));
        map_.put(E_QUOT, StringList.simpleFormat(ENCODE, (int)QUOT));
        map_.put(E_LT, StringList.simpleFormat(ENCODE, (int)LT));
        map_.put(E_GT, StringList.simpleFormat(ENCODE, (int)GT));
        map_.put(E_APOS, StringList.simpleFormat(ENCODE, (int)APOS));
        map_.put(E_U_OELIG, StringList.simpleFormat(ENCODE, (int)U_OE_LIG));
        map_.put(E_OELIG, StringList.simpleFormat(ENCODE, (int)OELIG));
        map_.put(E_S_CARON, StringList.simpleFormat(ENCODE, (int)U_SCARON));
        map_.put(E_SCARON, StringList.simpleFormat(ENCODE, (int)SCARON));
        map_.put(E_U_YUML, StringList.simpleFormat(ENCODE, (int)U_Y_UML));
        map_.put(E_CIRC, StringList.simpleFormat(ENCODE, (int)CIRC));
        map_.put(E_TILDE, StringList.simpleFormat(ENCODE, (int)TILDE));
        map_.put(E_ENSP, StringList.simpleFormat(ENCODE, (int)ENSP));
        map_.put(E_EMSP, StringList.simpleFormat(ENCODE, (int)EMSP));
        map_.put(E_THINSP, StringList.simpleFormat(ENCODE, (int)THINSP));
        map_.put(E_ZWNJ, StringList.simpleFormat(ENCODE, (int)ZWNJ));
        map_.put(E_ZWJ, StringList.simpleFormat(ENCODE, (int)ZWJ));
        map_.put(E_LRM, StringList.simpleFormat(ENCODE, (int)LRM));
        map_.put(E_RLM, StringList.simpleFormat(ENCODE, (int)RLM));
        map_.put(E_NDASH, StringList.simpleFormat(ENCODE, (int)NDASH));
        map_.put(E_MDASH, StringList.simpleFormat(ENCODE, (int)MDASH));
        map_.put(E_LSQUO, StringList.simpleFormat(ENCODE, (int)LSQUO));
        map_.put(E_RSQUO, StringList.simpleFormat(ENCODE, (int)RSQUO));
        map_.put(E_SBQUO, StringList.simpleFormat(ENCODE, (int)SBQUO));
        map_.put(E_LDQUO, StringList.simpleFormat(ENCODE, (int)LDQUO));
        map_.put(E_RDQUO, StringList.simpleFormat(ENCODE, (int)RDQUO));
        map_.put(E_BDQUO, StringList.simpleFormat(ENCODE, (int)BDQUO));
        map_.put(E_DAGGER, StringList.simpleFormat(ENCODE, (int)D_AGGER));
        map_.put(E_U_DAGGER, StringList.simpleFormat(ENCODE, (int)DAGGER));
        map_.put(E_PERMIL, StringList.simpleFormat(ENCODE, (int)PERMIL));
        map_.put(E_LSAQUO, StringList.simpleFormat(ENCODE, (int)LSAQUO));
        map_.put(E_RSAQUO, StringList.simpleFormat(ENCODE, (int)RSAQUO));
        map_.put(E_EURO, StringList.simpleFormat(ENCODE, (int)EURO));
        map_.put(E_FNOF, StringList.simpleFormat(ENCODE, (int)FNOF));
        map_.put(E_U_ALPHA, StringList.simpleFormat(ENCODE, (int)U_A_LPHA));
        map_.put(E_U_BETA, StringList.simpleFormat(ENCODE, (int)U_B_ETA));
        map_.put(E_U_GAMMA, StringList.simpleFormat(ENCODE, (int)U_G_AMMA));
        map_.put(E_U_DELTA, StringList.simpleFormat(ENCODE, (int)U_D_ELTA));
        map_.put(E_U_EPSILON, StringList.simpleFormat(ENCODE, (int)U_E_PSILON));
        map_.put(E_U_ZETA, StringList.simpleFormat(ENCODE, (int)U_Z_ETA));
        map_.put(E_U_ETA, StringList.simpleFormat(ENCODE, (int)U_E_TA));
        map_.put(E_U_THETA, StringList.simpleFormat(ENCODE, (int)U_T_HETA));
        map_.put(E_U_IOTA, StringList.simpleFormat(ENCODE, (int)U_I_OTA));
        map_.put(E_U_KAPPA, StringList.simpleFormat(ENCODE, (int)U_K_APPA));
        map_.put(E_U_LAMBDA, StringList.simpleFormat(ENCODE, (int)U_L_AMBDA));
        map_.put(E_U_MU, StringList.simpleFormat(ENCODE, (int)U_M_U));
        map_.put(E_U_NU, StringList.simpleFormat(ENCODE, (int)U_N_U));
        map_.put(E_U_XI, StringList.simpleFormat(ENCODE, (int)U_X_I));
        map_.put(E_U_OMICRON, StringList.simpleFormat(ENCODE, (int)U_O_MICRON));
        map_.put(E_U_PI, StringList.simpleFormat(ENCODE, (int)U_P_I));
        map_.put(E_U_RHO, StringList.simpleFormat(ENCODE, (int)U_R_HO));
        map_.put(E_U_SIGMA, StringList.simpleFormat(ENCODE, (int)U_S_IGMA));
        map_.put(E_U_TAU, StringList.simpleFormat(ENCODE, (int)U_T_AU));
        map_.put(E_U_UPSILON, StringList.simpleFormat(ENCODE, (int)U_U_PSILON));
        map_.put(E_U_PHI, StringList.simpleFormat(ENCODE, (int)U_P_HI));
        map_.put(E_U_CHI, StringList.simpleFormat(ENCODE, (int)U_C_HI));
        map_.put(E_U_PSI, StringList.simpleFormat(ENCODE, (int)U_P_SI));
        map_.put(E_U_OMEGA, StringList.simpleFormat(ENCODE, (int)U_O_MEGA));
        map_.put(E_ALPHA, StringList.simpleFormat(ENCODE, (int)ALPHA));
        map_.put(E_BETA, StringList.simpleFormat(ENCODE, (int)BETA));
        map_.put(E_GAMMA, StringList.simpleFormat(ENCODE, (int)GAMMA));
        map_.put(E_DELTA, StringList.simpleFormat(ENCODE, (int)DELTA));
        map_.put(E_EPSILON, StringList.simpleFormat(ENCODE, (int)EPSILON));
        map_.put(E_ZETA, StringList.simpleFormat(ENCODE, (int)ZETA));
        map_.put(E_ETA, StringList.simpleFormat(ENCODE, (int)ETA));
        map_.put(E_THETA, StringList.simpleFormat(ENCODE, (int)THETA));
        map_.put(E_IOTA, StringList.simpleFormat(ENCODE, (int)IOTA));
        map_.put(E_KAPPA, StringList.simpleFormat(ENCODE, (int)KAPPA));
        map_.put(E_LAMBDA, StringList.simpleFormat(ENCODE, (int)LAMBDA));
        map_.put(E_MU, StringList.simpleFormat(ENCODE, (int)MU));
        map_.put(E_NU, StringList.simpleFormat(ENCODE, (int)NU));
        map_.put(E_XI, StringList.simpleFormat(ENCODE, (int)XI));
        map_.put(E_OMICRON, StringList.simpleFormat(ENCODE, (int)OMICRON));
        map_.put(E_PI, StringList.simpleFormat(ENCODE, (int)PI));
        map_.put(E_RHO, StringList.simpleFormat(ENCODE, (int)RHO));
        map_.put(E_SIGMAF, StringList.simpleFormat(ENCODE, (int)SIGMAF));
        map_.put(E_SIGMA, StringList.simpleFormat(ENCODE, (int)SIGMA));
        map_.put(E_TAU, StringList.simpleFormat(ENCODE, (int)TAU));
        map_.put(E_UPSILON, StringList.simpleFormat(ENCODE, (int)UPSILON));
        map_.put(E_PHI, StringList.simpleFormat(ENCODE, (int)PHI));
        map_.put(E_CHI, StringList.simpleFormat(ENCODE, (int)CHI));
        map_.put(E_PSI, StringList.simpleFormat(ENCODE, (int)PSI));
        map_.put(E_OMEGA, StringList.simpleFormat(ENCODE, (int)OMEGA));
        map_.put(E_THETASYM, StringList.simpleFormat(ENCODE, (int)THETASYM));
        map_.put(E_UPSIH, StringList.simpleFormat(ENCODE, (int)UPSIH));
        map_.put(E_PIV, StringList.simpleFormat(ENCODE, (int)PIV));
        map_.put(E_BULL, StringList.simpleFormat(ENCODE, (int)BULL));
        map_.put(E_HELLIP, StringList.simpleFormat(ENCODE, (int)HELLIP));
        map_.put(E_PRIME, StringList.simpleFormat(ENCODE, (int)PRIME));
        map_.put(E_P_RIME, StringList.simpleFormat(ENCODE, (int)U_PRIME));
        map_.put(E_OLINE, StringList.simpleFormat(ENCODE, (int)OLINE));
        map_.put(E_FRASL, StringList.simpleFormat(ENCODE, (int)FRASL));
        map_.put(E_WEIERP, StringList.simpleFormat(ENCODE, (int)WEIERP));
        map_.put(E_IMAGE, StringList.simpleFormat(ENCODE, (int)IMAGE));
        map_.put(E_REAL, StringList.simpleFormat(ENCODE, (int)REAL));
        map_.put(E_TRADE, StringList.simpleFormat(ENCODE, (int)TRADE));
        map_.put(E_ALEFSYM, StringList.simpleFormat(ENCODE, (int)ALEFSYM));
        map_.put(E_LARR, StringList.simpleFormat(ENCODE, (int)LARR));
        map_.put(E_UARR, StringList.simpleFormat(ENCODE, (int)UARR));
        map_.put(E_RARR, StringList.simpleFormat(ENCODE, (int)RARR));
        map_.put(E_DARR, StringList.simpleFormat(ENCODE, (int)DARR));
        map_.put(E_HARR, StringList.simpleFormat(ENCODE, (int)HARR));
        map_.put(E_CRARR, StringList.simpleFormat(ENCODE, (int)CRARR));
        map_.put(E_L_ARR, StringList.simpleFormat(ENCODE, (int)U_LARR));
        map_.put(E_U_ARR, StringList.simpleFormat(ENCODE, (int)U_UARR));
        map_.put(E_R_ARR, StringList.simpleFormat(ENCODE, (int)U_RARR));
        map_.put(E_D_ARR, StringList.simpleFormat(ENCODE, (int)U_DARR));
        map_.put(E_H_ARR, StringList.simpleFormat(ENCODE, (int)U_HARR));
        map_.put(E_FORALL, StringList.simpleFormat(ENCODE, (int)FORALL));
        map_.put(E_PART, StringList.simpleFormat(ENCODE, (int)PART));
        map_.put(E_EXIST, StringList.simpleFormat(ENCODE, (int)EXIST));
        map_.put(E_EMPTY, StringList.simpleFormat(ENCODE, (int)EMPTY));
        map_.put(E_NABLA, StringList.simpleFormat(ENCODE, (int)NABLA));
        map_.put(E_ISIN, StringList.simpleFormat(ENCODE, (int)ISIN));
        map_.put(E_NOTIN, StringList.simpleFormat(ENCODE, (int)NOTIN));
        map_.put(E_NI, StringList.simpleFormat(ENCODE, (int)NI));
        map_.put(E_PROD, StringList.simpleFormat(ENCODE, (int)PROD));
        map_.put(E_SUM, StringList.simpleFormat(ENCODE, (int)SUM));
        map_.put(E_MINUS, StringList.simpleFormat(ENCODE, (int)MINUS));
        map_.put(E_LOWAST, StringList.simpleFormat(ENCODE, (int)LOWAST));
        map_.put(E_RADIC, StringList.simpleFormat(ENCODE, (int)RADIC));
        map_.put(E_PROP, StringList.simpleFormat(ENCODE, (int)PROP));
        map_.put(E_INFIN, StringList.simpleFormat(ENCODE, (int)INFIN));
        map_.put(E_ANG, StringList.simpleFormat(ENCODE, (int)ANG));
        map_.put(E_AND, StringList.simpleFormat(ENCODE, (int)AND));
        map_.put(E_OR, StringList.simpleFormat(ENCODE, (int)OR));
        map_.put(E_CAP, StringList.simpleFormat(ENCODE, (int)CAP));
        map_.put(E_CUP, StringList.simpleFormat(ENCODE, (int)CUP));
        map_.put(E_INT, StringList.simpleFormat(ENCODE, (int)INT));
        map_.put(E_THERE4, StringList.simpleFormat(ENCODE, (int)THERE4));
        map_.put(E_SIM, StringList.simpleFormat(ENCODE, (int)SIM));
        map_.put(E_CONG, StringList.simpleFormat(ENCODE, (int)CONG));
        map_.put(E_ASYMP, StringList.simpleFormat(ENCODE, (int)ASYMP));
        map_.put(E_NE, StringList.simpleFormat(ENCODE, (int)NE));
        map_.put(E_EQUIV, StringList.simpleFormat(ENCODE, (int)EQUIV));
        map_.put(E_LE, StringList.simpleFormat(ENCODE, (int)LE));
        map_.put(E_GE, StringList.simpleFormat(ENCODE, (int)GE));
        map_.put(E_SUB, StringList.simpleFormat(ENCODE, (int)SUB));
        map_.put(E_SUP, StringList.simpleFormat(ENCODE, (int)SUP));
        map_.put(E_NSUB, StringList.simpleFormat(ENCODE, (int)NSUB));
        map_.put(E_SUBE, StringList.simpleFormat(ENCODE, (int)SUBE));
        map_.put(E_SUPE, StringList.simpleFormat(ENCODE, (int)SUPE));
        map_.put(E_OPLUS, StringList.simpleFormat(ENCODE, (int)OPLUS));
        map_.put(E_OTIMES, StringList.simpleFormat(ENCODE, (int)OTIMES));
        map_.put(E_PERP, StringList.simpleFormat(ENCODE, (int)PERP));
        map_.put(E_SDOT, StringList.simpleFormat(ENCODE, (int)SDOT));
        map_.put(E_LCEIL, StringList.simpleFormat(ENCODE, (int)LCEIL));
        map_.put(E_RCEIL, StringList.simpleFormat(ENCODE, (int)RCEIL));
        map_.put(E_LFLOOR, StringList.simpleFormat(ENCODE, (int)LFLOOR));
        map_.put(E_RFLOOR, StringList.simpleFormat(ENCODE, (int)RFLOOR));
        map_.put(E_LANG, StringList.simpleFormat(ENCODE, (int)LANG));
        map_.put(E_RANG, StringList.simpleFormat(ENCODE, (int)RANG));
        map_.put(E_LOZ, StringList.simpleFormat(ENCODE, (int)LOZ));
        map_.put(E_SPADES, StringList.simpleFormat(ENCODE, (int)SPADES));
        map_.put(E_CLUBS, StringList.simpleFormat(ENCODE, (int)CLUBS));
        map_.put(E_HEARTS, StringList.simpleFormat(ENCODE, (int)HEARTS));
        map_.put(E_DIAMS, StringList.simpleFormat(ENCODE, (int)DIAMS));
        map_.put(E_AMP, StringList.simpleFormat(ENCODE, (int)ASCII_38));
        int length_ = _htmlText.length();
        StringBuilder str_ = new StringBuilder();
        int i_ = 0;
        int iBegin_ = 0;
        while (i_ < length_) {
            char ch_ = _htmlText.charAt(i_);
            if (ch_ != ENCODED) {
                str_.append(ch_);
                i_++;
                continue;
            }
            iBegin_ = i_;
            while(true) {
                i_++;
                if (i_ >= length_) {
                    break;
                }
                ch_ = _htmlText.charAt(i_);
                //                if (!Character.isLetterOrDigit(ch_)) {
                    //                    break;
                    //                }
                if (ch_ == END_ESCAPED) {
                    break;
                }
            }
            if (i_ >= length_) {
                str_.append(_htmlText.substring(iBegin_));
                break;
            }
            boolean add_ = false;
            for (EntryCust<String,String> k: map_.entryList()) {
                boolean equals_ = true;
                int j_ = 0;
                String key_ = k.getKey();
                for (int i = iBegin_; i <= i_; i++) {
                    //j_ is max as i_-iBegin_+1
                    if (_htmlText.charAt(i) != key_.charAt(j_)) {
                        equals_ = false;
                        break;
                    }
                    j_++;
                }
                if (equals_) {
                    str_.append(k.getValue());
                    i_++;
                    add_ = true;
                    break;
                }
            }
            if (!add_) {
                str_.append(_htmlText.substring(iBegin_, i_ + 1));
                i_++;
            }
        }
        return str_.toString();
    }

    //    public static String encodeUrlString(String _urlString, boolean _encodeAccent) {
    //        Map<String, String> replacedStrings_ = new Map<>();
    //        replacedStrings_.put(ENCODE_SPACE, SPACE);
    //        for (short c = ASCII_32; c < ASCII_37; c++) {
    //            replacedStrings_.put(PER_CENT+Long.toHexString(c).toUpperCase(), Character.toString((char) c));
    //        }
    //        if (_encodeAccent) {
    //            for (short c = ASCII_38; c < ASCII_256; c++) {
    //                replacedStrings_.put(PER_CENT+Long.toHexString(c).toUpperCase(), Character.toString((char) c));
    //            }
    //            replacedStrings_.put(ENCODE_PER_CENT, PER_CENT);
    //            return StringList.formatBasic(_urlString, replacedStrings_, false);
    //        }
    //        for (short c = ASCII_38; c < ASCII_128; c++) {
    //            replacedStrings_.put(PER_CENT+Long.toHexString(c).toUpperCase(), Character.toString((char) c));
    //        }
    //        replacedStrings_.put(ENCODE_PER_CENT, PER_CENT);
    //        return StringList.formatBasic(_urlString, replacedStrings_, true);
    //    }

    public static String encodeToHtml(String _text) {
        StringBuilder escapedXml_ = new StringBuilder();
        for (char c: _text.toCharArray()) {
            if (c >= ASCII_128 || c < ASCII_32) {
                escapedXml_.append(ENCODED);
                escapedXml_.append(NUMBERED_CHAR);
                escapedXml_.append((int)c);
                escapedXml_.append(END_ESCAPED);
            } else {
                escapedXml_.append(c);
            }
        }
        return escapedXml_.toString();
        //        Map<Character, String> map_;
        //        map_ = new Map<>();
        //        map_.put(U_Y_UML_159, E_U_YUML);
        //        map_.put(IEXCL, E_IEXCL);
        //        map_.put(CENT, E_CENT);
        //        map_.put(POUND, E_POUND);
        //        map_.put(CURREN, E_CURREN);
        //        map_.put(YEN, E_YEN);
        //        map_.put(BRVBAR, E_BRVBAR);
        //        map_.put(SECT, E_SECT);
        //        map_.put(UML, E_UML);
        //        map_.put(COPY, E_COPY);
        //        map_.put(ORDF, E_ORDF);
        //        map_.put(LAQUO, E_LAQUO);
        //        map_.put(NOT, E_NOT);
        //        map_.put(SHY, E_SHY);
        //        map_.put(REG, E_REG);
        //        map_.put(MACR, E_MACR);
        //        map_.put(DEG, E_DEG);
        //        map_.put(PLUSMN, E_PLUSMN);
        //        map_.put(SUP2, E_SUP2);
        //        map_.put(SUP3, E_SUP3);
        //        map_.put(ACUTE, E_ACUTE);
        //        map_.put(MICRO, E_MICRO);
        //        map_.put(PARA, E_PARA);
        //        map_.put(MIDDOT, E_MIDDOT);
        //        map_.put(CEDIL, E_CEDIL);
        //        map_.put(SUP1, E_SUP1);
        //        map_.put(ORDM, E_ORDM);
        //        map_.put(RAQUO, E_RAQUO);
        //        map_.put(FRAC14, E_FRAC14);
        //        map_.put(FRAC12, E_FRAC12);
        //        map_.put(FRAC34, E_FRAC34);
        //        map_.put(IQUEST, E_IQUEST);
        //        map_.put(U_A_GRAVE, E_U_AGRAVE);
        //        map_.put(U_A_ACUTE, E_U_AACUTE);
        //        map_.put(U_A_CIRC, E_U_ACIRC);
        //        map_.put(U_A_TILDE, E_U_ATILDE);
        //        map_.put(U_A_UML, E_U_AUML);
        //        map_.put(U_A_RING, E_U_ARING);
        //        map_.put(U_AE_LIG, E_U_AELIG);
        //        map_.put(U_C_CEDIL, E_U_CCEDIL);
        //        map_.put(U_E_GRAVE, E_U_EGRAVE);
        //        map_.put(U_E_ACUTE, E_U_EACUTE);
        //        map_.put(U_E_CIRC, E_U_ECIRC);
        //        map_.put(U_E_UML, E_U_EUML);
        //        map_.put(U_I_GRAVE, E_U_IGRAVE);
        //        map_.put(U_I_ACUTE, E_U_IACUTE);
        //        map_.put(U_I_CIRC, E_U_ICIRC);
        //        map_.put(U_I_UML, E_U_IUML);
        //        map_.put(U_ETH, E_U_ETH);
        //        map_.put(U_N_TILDE, E_U_NTILDE);
        //        map_.put(U_O_GRAVE, E_U_OGRAVE);
        //        map_.put(U_O_ACUTE, E_U_OACUTE);
        //        map_.put(U_O_CIRC, E_U_OCIRC);
        //        map_.put(U_O_TILDE, E_U_OTILDE);
        //        map_.put(U_O_UML, E_U_OUML);
        //        map_.put(TIMES, E_TIMES);
        //        map_.put(U_O_SLASH, E_U_OSLASH);
        //        map_.put(U_U_GRAVE, E_U_UGRAVE);
        //        map_.put(U_U_ACUTE, E_U_UACUTE);
        //        map_.put(U_U_CIRC, E_U_UCIRC);
        //        map_.put(U_U_UML, E_U_UUML);
        //        map_.put(U_Y_ACUTE, E_U_YACUTE);
        //        map_.put(U_THORN, E_U_THORN);
        //        map_.put(SZLIG, E_SZLIG);
        //        map_.put(AGRAVE, E_AGRAVE);
        //        map_.put(AACUTE, E_AACUTE);
        //        map_.put(ACIRC, E_ACIRC);
        //        map_.put(ATILDE, E_ATILDE);
        //        map_.put(AUML, E_AUML);
        //        map_.put(ARING, E_ARING);
        //        map_.put(AELIG, E_AELIG);
        //        map_.put(CCEDIL, E_CCEDIL);
        //        map_.put(EGRAVE, E_EGRAVE);
        //        map_.put(EACUTE, E_EACUTE);
        //        map_.put(ECIRC, E_ECIRC);
        //        map_.put(EUML, E_EUML);
        //        map_.put(IGRAVE, E_IGRAVE);
        //        map_.put(IACUTE, E_IACUTE);
        //        map_.put(ICIRC, E_ICIRC);
        //        map_.put(IUML, E_IUML);
        //        map_.put(ETH, E_ETH);
        //        map_.put(NTILDE, E_NTILDE);
        //        map_.put(OGRAVE, E_OGRAVE);
        //        map_.put(OACUTE, E_OACUTE);
        //        map_.put(OCIRC, E_OCIRC);
        //        map_.put(OTILDE, E_OTILDE);
        //        map_.put(OUML, E_OUML);
        //        map_.put(DIVIDE, E_DIVIDE);
        //        map_.put(OSLASH, E_OSLASH);
        //        map_.put(UGRAVE, E_UGRAVE);
        //        map_.put(UACUTE, E_UACUTE);
        //        map_.put(UCIRC, E_UCIRC);
        //        map_.put(UUML, E_UUML);
        //        map_.put(YACUTE, E_YACUTE);
        //        map_.put(THORN, E_THORN);
        //        map_.put(YUML, E_YUML);
        //        map_.put(U_OE_LIG, E_U_OELIG);
        //        map_.put(OELIG, E_OELIG);
        //        map_.put(U_SCARON, E_U_SCARON);
        //        map_.put(SCARON, E_SCARON);
        //        map_.put(U_Y_UML, E_U_YUML);
        //        map_.put(CIRC, E_CIRC);
        //        map_.put(TILDE, E_TILDE);
        //        map_.put(ENSP, E_ENSP);
        //        map_.put(EMSP, E_EMSP);
        //        map_.put(THINSP, E_THINSP);
        //        map_.put(ZWNJ, E_ZWNJ);
        //        map_.put(ZWJ, E_ZWJ);
        //        map_.put(LRM, E_LRM);
        //        map_.put(RLM, E_RLM);
        //        map_.put(NDASH, E_NDASH);
        //        map_.put(MDASH, E_MDASH);
        //        map_.put(LSQUO, E_LSQUO);
        //        map_.put(RSQUO, E_RSQUO);
        //        map_.put(SBQUO, E_SBQUO);
        //        map_.put(LDQUO, E_LDQUO);
        //        map_.put(RDQUO, E_RDQUO);
        //        map_.put(BDQUO, E_BDQUO);
        //        map_.put(D_AGGER, E_U_AGGER);
        //        map_.put(DAGGER, E_DAGGER);
        //        map_.put(PERMIL, E_PERMIL);
        //        map_.put(LSAQUO, E_LSAQUO);
        //        map_.put(RSAQUO, E_RSAQUO);
        //        map_.put(EURO, E_EURO);
        //        map_.put(FNOF, E_FNOF);
        //        map_.put(U_A_LPHA, E_U_ALPHA);
        //        map_.put(U_B_ETA, E_U_BETA);
        //        map_.put(U_G_AMMA, E_U_GAMMA);
        //        map_.put(U_D_ELTA, E_U_DELTA);
        //        map_.put(U_E_PSILON, E_U_EPSILON);
        //        map_.put(U_Z_ETA, E_U_ZETA);
        //        map_.put(U_E_TA, E_U_ETA);
        //        map_.put(U_T_HETA, E_U_THETA);
        //        map_.put(U_I_OTA, E_U_IOTA);
        //        map_.put(U_K_APPA, E_U_KAPPA);
        //        map_.put(U_L_AMBDA, E_U_LAMBDA);
        //        map_.put(U_M_U, E_U_MU);
        //        map_.put(U_N_U, E_U_NU);
        //        map_.put(U_X_I, E_U_XI);
        //        map_.put(U_O_MICRON, E_U_OMICRON);
        //        map_.put(U_P_I, E_U_PI);
        //        map_.put(U_R_HO, E_U_RHO);
        //        map_.put(U_S_IGMA, E_U_SIGMA);
        //        map_.put(U_T_AU, E_U_TAU);
        //        map_.put(U_U_PSILON, E_U_UPSILON);
        //        map_.put(U_P_HI, E_U_PHI);
        //        map_.put(U_C_HI, E_U_CHI);
        //        map_.put(U_P_SI, E_U_PSI);
        //        map_.put(U_O_MEGA, E_U_OMEGA);
        //        map_.put(ALPHA, E_ALPHA);
        //        map_.put(BETA, E_BETA);
        //        map_.put(GAMMA, E_GAMMA);
        //        map_.put(DELTA, E_DELTA);
        //        map_.put(EPSILON, E_EPSILON);
        //        map_.put(ZETA, E_ZETA);
        //        map_.put(ETA, E_ETA);
        //        map_.put(THETA, E_THETA);
        //        map_.put(IOTA, E_IOTA);
        //        map_.put(KAPPA, E_KAPPA);
        //        map_.put(LAMBDA, E_LAMBDA);
        //        map_.put(MU, E_MU);
        //        map_.put(NU, E_NU);
        //        map_.put(XI, E_XI);
        //        map_.put(OMICRON, E_OMICRON);
        //        map_.put(PI, E_PI);
        //        map_.put(RHO, E_RHO);
        //        map_.put(SIGMAF, E_SIGMAF);
        //        map_.put(SIGMA, E_SIGMA);
        //        map_.put(TAU, E_TAU);
        //        map_.put(UPSILON, E_UPSILON);
        //        map_.put(PHI, E_PHI);
        //        map_.put(CHI, E_CHI);
        //        map_.put(PSI, E_PSI);
        //        map_.put(OMEGA, E_OMEGA);
        //        map_.put(THETASYM, E_THETASYM);
        //        map_.put(UPSIH, E_UPSIH);
        //        map_.put(PIV, E_PIV);
        //        map_.put(BULL, E_BULL);
        //        map_.put(HELLIP, E_HELLIP);
        //        map_.put(PRIME, E_PRIME);
        //        map_.put(U_PRIME, E_U_PRIME);
        //        map_.put(OLINE, E_OLINE);
        //        map_.put(FRASL, E_FRASL);
        //        map_.put(WEIERP, E_WEIERP);
        //        map_.put(IMAGE, E_IMAGE);
        //        map_.put(REAL, E_REAL);
        //        map_.put(TRADE, E_TRADE);
        //        map_.put(ALEFSYM, E_ALEFSYM);
        //        map_.put(LARR, E_LARR);
        //        map_.put(UARR, E_UARR);
        //        map_.put(RARR, E_RARR);
        //        map_.put(DARR, E_DARR);
        //        map_.put(HARR, E_HARR);
        //        map_.put(CRARR, E_CRARR);
        //        map_.put(U_LARR, E_U_LARR);
        //        map_.put(U_UARR, E_U_UARR);
        //        map_.put(U_RARR, E_U_RARR);
        //        map_.put(U_DARR, E_U_DARR);
        //        map_.put(U_HARR, E_U_HARR);
        //        map_.put(FORALL, E_FORALL);
        //        map_.put(PART, E_PART);
        //        map_.put(EXIST, E_EXIST);
        //        map_.put(EMPTY, E_EMPTY);
        //        map_.put(NABLA, E_NABLA);
        //        map_.put(ISIN, E_ISIN);
        //        map_.put(NOTIN, E_NOTIN);
        //        map_.put(NI, E_NI);
        //        map_.put(PROD, E_PROD);
        //        map_.put(SUM, E_SUM);
        //        map_.put(MINUS, E_MINUS);
        //        map_.put(LOWAST, E_LOWAST);
        //        map_.put(RADIC, E_RADIC);
        //        map_.put(PROP, E_PROP);
        //        map_.put(INFIN, E_INFIN);
        //        map_.put(ANG, E_ANG);
        //        map_.put(AND, E_AND);
        //        map_.put(OR, E_OR);
        //        map_.put(CAP, E_CAP);
        //        map_.put(CUP, E_CUP);
        //        map_.put(INT, E_INT);
        //        map_.put(THERE4, E_THERE4);
        //        map_.put(SIM, E_SIM);
        //        map_.put(CONG, E_CONG);
        //        map_.put(ASYMP, E_ASYMP);
        //        map_.put(NE, E_NE);
        //        map_.put(EQUIV, E_EQUIV);
        //        map_.put(LE, E_LE);
        //        map_.put(GE, E_GE);
        //        map_.put(SUB, E_SUB);
        //        map_.put(SUP, E_SUP);
        //        map_.put(NSUB, E_NSUB);
        //        map_.put(SUBE, E_SUBE);
        //        map_.put(SUPE, E_SUPE);
        //        map_.put(OPLUS, E_OPLUS);
        //        map_.put(OTIMES, E_OTIMES);
        //        map_.put(PERP, E_PERP);
        //        map_.put(SDOT, E_SDOT);
        //        map_.put(LCEIL, E_LCEIL);
        //        map_.put(RCEIL, E_RCEIL);
        //        map_.put(LFLOOR, E_LFLOOR);
        //        map_.put(RFLOOR, E_RFLOOR);
        //        map_.put(LANG, E_LANG);
        //        map_.put(RANG, E_RANG);
        //        map_.put(LOZ, E_LOZ);
        //        map_.put(SPADES, E_SPADES);
        //        map_.put(CLUBS, E_CLUBS);
        //        map_.put(HEARTS, E_HEARTS);
        //        map_.put(DIAMS, E_DIAMS);
        //        StringBuilder str_ = new StringBuilder();
        //        for (char c: _text.toCharArray()) {
        //            if (map_.contains(c)) {
        //                str_.append(map_.getVal(c));
        //            } else {
        //                str_.append(c);
        //            }
        //        }
        //        return str_.toString();
    }

    public static Element documentElement(Document _doc) {
        Element element_ = _doc.getDocumentElement();
        element_.normalize();
        return element_;
    }

    public static Document parseSaxNotNull(String _xml, boolean _acceptNull) {
        if (_acceptNull) {
            return parseSax(_xml);
        }
        //        DocumentBuilderFactory factory_ = DocumentBuilderFactory.newInstance();
        //factory_.setNamespaceAware(true);
        DocumentBuilder builder_ = newXmlDocumentBuilder();
        ByteArrayInputStream inputSteam_ = null;
        try {
            inputSteam_ = new ByteArrayInputStream(_xml.getBytes());
            //            builder_ = factory_.newDocumentBuilder();
            return builder_.parse(inputSteam_);
        } catch (RuntimeException _0) {
            throw new XmlParseException(_xml);
            //        } catch (ParserConfigurationException _0) {
            //            return null;
        } catch (SAXException _0) {
            throw new XmlParseException(_xml);
        } catch (IOException _0) {
            throw new XmlParseException(_xml);
        } finally {
            try {
                if (inputSteam_ != null) {
                    inputSteam_.close();
                }
            } catch (IOException _0) {
                _0.printStackTrace();
            }
        }
        //        DocumentBuilderFactory dbFactory_ = DocumentBuilderFactory.newInstance();
        //        DocumentBuilder dBuilder_ = dbFactory_.newDocumentBuilder();
        //        InputSource is_ = new InputSource();
        ////        is_.setEncoding(StandardCharsets.UTF_8.name());
        //        is_.setEncoding(StandardCharsets.ISO_8859_1.name());
        //        is_.setCharacterStream(new StringReader(_xml));
        //        return dBuilder_.parse(is_);
    }

    public static Document parseSaxNotNullRowCol(String _xml) {
        DocumentBuilder builder_ = newXmlDocumentBuilder();
        ByteArrayInputStream inputSteam_ = null;
        try {
            inputSteam_ = new ByteArrayInputStream(_xml.getBytes());
            return builder_.parse(inputSteam_);
        } catch (RuntimeException _0) {
            throw new XmlParseException(_xml);
        } catch (SAXParseException _0) {
            RowCol rc_ = new RowCol();
            rc_.setCol(_0.getColumnNumber());
            rc_.setRow(_0.getLineNumber());
            throw new XmlParseException(rc_, _xml);
        } catch (SAXException _0) {
            throw new XmlParseException(_xml);
        } catch (IOException _0) {
            throw new XmlParseException(_xml);
        } finally {
            try {
                if (inputSteam_ != null) {
                    inputSteam_.close();
                }
            } catch (IOException _0) {
                _0.printStackTrace();
            }
        }
    }

    public static Document parseSaxNotNullRowCol(String _xml, boolean _namespace) {
        DocumentBuilder builder_ = newXmlDocumentBuilder(_namespace);
        ByteArrayInputStream inputSteam_ = null;
        try {
            inputSteam_ = new ByteArrayInputStream(_xml.getBytes());
            return builder_.parse(inputSteam_);
        } catch (RuntimeException _0) {
            throw new XmlParseException(_xml);
        } catch (SAXParseException _0) {
            RowCol rc_ = new RowCol();
            rc_.setCol(_0.getColumnNumber());
            rc_.setRow(_0.getLineNumber());
            throw new XmlParseException(rc_, _xml);
        } catch (SAXException _0) {
            throw new XmlParseException(_xml);
        } catch (IOException _0) {
            throw new XmlParseException(_xml);
        } finally {
            try {
                if (inputSteam_ != null) {
                    inputSteam_.close();
                }
            } catch (IOException _0) {
                _0.printStackTrace();
            }
        }
    }


    public static Document parseSaxNotNull(String _xml, boolean _acceptNull, boolean _namespace) {
        if (_acceptNull) {
            return parseSax(_xml, _namespace);
        }
        //        DocumentBuilderFactory factory_ = DocumentBuilderFactory.newInstance();
        //factory_.setNamespaceAware(true);
        DocumentBuilder builder_ = newXmlDocumentBuilder(_namespace);
        ByteArrayInputStream inputSteam_ = null;
        try {
            inputSteam_ = new ByteArrayInputStream(_xml.getBytes());
            //            builder_ = factory_.newDocumentBuilder();
            return builder_.parse(inputSteam_);
        } catch (RuntimeException _0) {
            throw new XmlParseException(_xml);
            //        } catch (ParserConfigurationException _0) {
            //            return null;
        } catch (SAXException _0) {
            throw new XmlParseException(_xml);
        } catch (IOException _0) {
            throw new XmlParseException(_xml);
        } finally {
            try {
                if (inputSteam_ != null) {
                    inputSteam_.close();
                }
            } catch (IOException _0) {
                _0.printStackTrace();
            }
        }
        //        DocumentBuilderFactory dbFactory_ = DocumentBuilderFactory.newInstance();
        //        DocumentBuilder dBuilder_ = dbFactory_.newDocumentBuilder();
        //        InputSource is_ = new InputSource();
        ////        is_.setEncoding(StandardCharsets.UTF_8.name());
        //        is_.setEncoding(StandardCharsets.ISO_8859_1.name());
        //        is_.setCharacterStream(new StringReader(_xml));
        //        return dBuilder_.parse(is_);
    }

    public static Document parseSax(String _xml) {
        if (_xml == null) {
            return null;
        }
        //        DocumentBuilderFactory factory_ = DocumentBuilderFactory.newInstance();
        //factory_.setNamespaceAware(true);
        DocumentBuilder builder_ = newXmlDocumentBuilder();
        ByteArrayInputStream inputSteam_ = new ByteArrayInputStream(_xml.getBytes());
        try {
            //            builder_ = factory_.newDocumentBuilder();
            return builder_.parse(inputSteam_);
        } catch (SAXException _0) {
            _0.printStackTrace();
            return null;
        } catch (IOException _0) {
            return null;
            //        } catch (ParserConfigurationException _0) {
            //            return null;
        } finally {
            try {
                inputSteam_.close();
            } catch (IOException _0) {
                _0.printStackTrace();
            }
        }
        //        DocumentBuilderFactory dbFactory_ = DocumentBuilderFactory.newInstance();
        //        DocumentBuilder dBuilder_ = dbFactory_.newDocumentBuilder();
        //        InputSource is_ = new InputSource();
        ////        is_.setEncoding(StandardCharsets.UTF_8.name());
        //        is_.setEncoding(StandardCharsets.ISO_8859_1.name());
        //        is_.setCharacterStream(new StringReader(_xml));
        //        return dBuilder_.parse(is_);
    }

    public static Document parseSax(String _xml, boolean _namespaceAware) {
        if (_xml == null) {
            return null;
        }
        //        DocumentBuilderFactory factory_ = DocumentBuilderFactory.newInstance();
        //factory_.setNamespaceAware(true);
        DocumentBuilder builder_ = newXmlDocumentBuilder(_namespaceAware);
        ByteArrayInputStream inputSteam_ = new ByteArrayInputStream(_xml.getBytes());
        try {
            //            builder_ = factory_.newDocumentBuilder();
            return builder_.parse(inputSteam_);
        } catch (SAXException _0) {
            _0.printStackTrace();
            return null;
        } catch (IOException _0) {
            return null;
            //        } catch (ParserConfigurationException _0) {
            //            return null;
        } finally {
            try {
                inputSteam_.close();
            } catch (IOException _0) {
                _0.printStackTrace();
            }
        }
        //        DocumentBuilderFactory dbFactory_ = DocumentBuilderFactory.newInstance();
        //        DocumentBuilder dBuilder_ = dbFactory_.newDocumentBuilder();
        //        InputSource is_ = new InputSource();
        ////        is_.setEncoding(StandardCharsets.UTF_8.name());
        //        is_.setEncoding(StandardCharsets.ISO_8859_1.name());
        //        is_.setCharacterStream(new StringReader(_xml));
        //        return dBuilder_.parse(is_);
    }

    public static Document newXmlDocument() {
        try {
            DocumentBuilder builder_;
            builder_ = newXmlDocumentBuilder();
            return builder_.newDocument();
        } catch (RuntimeException _0) {
            return null;
        }
    }

    public static Document newXmlDocument(boolean _namespaceAware) {
        try {
            DocumentBuilder builder_;
            builder_ = newXmlDocumentBuilder(_namespaceAware);
            return builder_.newDocument();
        } catch (RuntimeException _0) {
            return null;
        }
    }

    public static DocumentBuilder newXmlDocumentBuilder(boolean _namespaceAware) {
        try {
            DocumentBuilderFactory factory_ = DocumentBuilderFactory.newInstance();
            factory_.setNamespaceAware(_namespaceAware);
            return factory_.newDocumentBuilder();
        } catch (RuntimeException _0) {
            return null;
        } catch (ParserConfigurationException _0) {
            return null;
        }
    }

    public static DocumentBuilder newXmlDocumentBuilder() {
        try {
            DocumentBuilderFactory factory_ = DocumentBuilderFactory.newInstance();
            return factory_.newDocumentBuilder();
        } catch (RuntimeException _0) {
            return null;
        } catch (ParserConfigurationException _0) {
            return null;
        }
    }

    public static String toFormattedHtml(Document _doc) {
        Element root_ = _doc.getDocumentElement();
        try {
            DOMSource source_ = new DOMSource(root_);
            StringWriter writer_ = new StringWriter();
            //_indentXmlWhileWriting_
            getHtmlTransformer().transform(source_, new StreamResult(writer_));
            return writer_.getBuffer().toString();
        } catch (RuntimeException _0) {
            return EMPTY_STRING;
        } catch (TransformerException _0) {
            return EMPTY_STRING;
        }
    }

    public static String toHtml(Document _doc) {
        Element root_ = _doc.getDocumentElement();
        return toXmlWithoutHeader(root_);
    }

    public static String toXml(Node _doc) {
        try {
            DOMSource source_ = new DOMSource(_doc);
            StringWriter writer_ = new StringWriter();
            //_indentXmlWhileWriting_
            getTransformer().transform(source_, new StreamResult(writer_));
            return writer_.getBuffer().toString();
        } catch (RuntimeException _0) {
            return EMPTY_STRING;
        } catch (TransformerException _0) {
            return EMPTY_STRING;
        }
    }

    public static String toXmlWithoutHeader(Node _element) {
        try {
            DOMSource source_ = new DOMSource(_element);
            StringWriter writer_ = new StringWriter();
            //_indentXmlWhileWriting_
            getTransformerWithoutHeader().transform(source_, new StreamResult(writer_));
            return writer_.getBuffer().toString();
        } catch (RuntimeException _0) {
            return EMPTY_STRING;
        } catch (TransformerException _0) {
            return EMPTY_STRING;
        }
    }

    public static String indent(String _xml) {
        int index_ = CustList.FIRST_INDEX;
        int indentation_ = CustList.SIZE_EMPTY;
        StringBuilder indented_ = new StringBuilder();
        while (true) {
            if (index_ >= _xml.length()) {
                break;
            }
            int i_ = index_;
            boolean change_ = true;
            if (_xml.charAt(i_) != LT) {
                change_ = false;
                while (_xml.charAt(i_) != LT) {
                    i_++;
                }
                i_--;
            } else {
                while (_xml.charAt(i_) != GT) {
                    i_++;
                }
            }
            boolean begin_ = false;
            boolean end_ = false;
            if (change_) {
                if (_xml.charAt(index_ + 1) == SLASH) {
                    end_ = true;
                } else if (_xml.charAt(i_ - 1) != SLASH) {
                    begin_ = true;
                }
            }
            if (end_) {
                indentation_--;
            }
            for (int i = CustList.FIRST_INDEX; i < indentation_; i++) {
                indented_.append(TAB);
            }
            indented_.append(_xml.substring(index_, i_ + 1));
            indented_.append(LINE_RETURN);
            if (begin_) {
                indentation_++;
            }
            index_ = i_ + 1;
        }
        indented_.deleteCharAt(indented_.length() - 1);
        return indented_.toString();
    }

    public static String indentWithoutTextNode(String _xml) {
        int index_ = CustList.FIRST_INDEX;
        int indentation_ = CustList.SIZE_EMPTY;
        StringBuilder indented_ = new StringBuilder();
        while (true) {
            if (index_ >= _xml.length()) {
                break;
            }
            int i_ = index_;
            while (_xml.charAt(i_) != GT) {
                i_++;
            }
            boolean begin_ = false;
            boolean end_ = false;
            if (_xml.charAt(index_ + 1) == SLASH) {
                end_ = true;
            } else if (_xml.charAt(i_ - 1) != SLASH) {
                begin_ = true;
            }
            if (end_) {
                indentation_--;
            }
            for (int i = CustList.FIRST_INDEX; i < indentation_; i++) {
                indented_.append(TAB);
            }
            indented_.append(_xml.substring(index_, i_ + 1));
            indented_.append(LINE_RETURN);
            if (begin_) {
                indentation_++;
            }
            index_ = i_ + 1;
        }
        indented_.deleteCharAt(indented_.length() - 1);
        return indented_.toString();
    }

    private static Transformer getTransformerWithoutHeader() {
        //boolean _indent
        Transformer xmlTransformer_ = getTransformer();
        xmlTransformer_.setOutputProperty(OutputKeys.METHOD, XML);
        xmlTransformer_.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
        return xmlTransformer_;
    }

    public static Transformer getTransformer() {
        //boolean _indent
        try {
            Transformer xmlTransformer_ = TransformerFactory.newInstance().newTransformer();
//            xmlTransformer_.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
            xmlTransformer_.setOutputProperty(OutputKeys.METHOD, XML);
            xmlTransformer_.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.getName());
            //            if (_indent) {
//                            xmlTransformer_.setOutputProperty(OutputKeys.INDENT, YES);
//                            xmlTransformer_.setOutputProperty(INDENT_KEY, Integer.toString(NB_INDENT));
            //            }
            if (_omitDeclaration_) {
                xmlTransformer_.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
            }
            return xmlTransformer_;
        } catch (RuntimeException _0) {
            _0.printStackTrace();
            return null;
        } catch (TransformerConfigurationException _0) {
            _0.printStackTrace();
            return null;
        } catch (TransformerFactoryConfigurationError _0) {
            _0.printStackTrace();
            return null;
        }
    }

    public static Transformer getHtmlTransformer() {
        //boolean _indent
        try {
            Transformer xmlTransformer_ = TransformerFactory.newInstance().newTransformer();
            xmlTransformer_.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.getName());
            //            if (_indent) {
            //                xmlTransformer_.setOutputProperty(OutputKeys.INDENT, YES);
            //                xmlTransformer_.setOutputProperty(INDENT_KEY, Integer.toString(NB_INDENT));
            //            }
            xmlTransformer_.setOutputProperty(OutputKeys.METHOD, HTML);
            xmlTransformer_.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
            return xmlTransformer_;
        } catch (RuntimeException _0) {
            _0.printStackTrace();
            return null;
        } catch (TransformerConfigurationException _0) {
            _0.printStackTrace();
            return null;
        } catch (TransformerFactoryConfigurationError _0) {
            _0.printStackTrace();
            return null;
        }
    }

    public static boolean isIndentXmlWhileWriting() {
        return _indentXmlWhileWriting_;
    }

    public static void setIndentXmlWhileWriting(boolean _indentXmlWhileWriting) {
        _indentXmlWhileWriting_ = _indentXmlWhileWriting;
    }

    public static boolean isOmitDeclaration() {
        return _omitDeclaration_;
    }

    public static void setOmitDeclaration(boolean _omitDeclaration) {
        _omitDeclaration_ = _omitDeclaration;
    }
}


