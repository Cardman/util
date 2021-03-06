package code.scripts.pages.cards;
import code.sml.*;
import code.util.*;
import code.util.ints.*;
final class HelpCards94{
private HelpCards94(){}
static Document build(){
FullDocument doc_ = DocumentBuilder.newXmlDocument(4);
build(doc_);
return doc_;
}
static void build(Document _doc){
Element elt0_=el(_doc,"html");
Element elt1_=el(_doc,"body");
Text txt0_=tx(_doc,"Le tarot se joue carte par carte.");
ad(elt1_,txt0_);
Element elt2_=el(_doc,"br");
ad(elt1_,elt2_);
Text txt1_=tx(_doc,"On appelle pli, un ensemble de cartes remport&#233; par un joueur ayant jou&#233; la plus forte carte. Le joueur ayant fait un pli b&#233;n&#233;ficie de l''entame suivante.(Exemple: le joueur ayant mis le 21 d''atout, b&#233;n&#233;ficie de l''entame suivante.).");
ad(elt1_,txt1_);
Element elt3_=el(_doc,"br");
ad(elt1_,elt3_);
Element elt4_=el(_doc,"br");
ad(elt1_,elt4_);
Text txt2_=tx(_doc,"Le joueur qui entame est dit entameur.");
ad(elt1_,txt2_);
Element elt5_=el(_doc,"br");
ad(elt1_,elt5_);
Text txt3_=tx(_doc,"Le joueur qui a jou&#233; la plus forte carte d''un pli est dit le ramasseur, il ramasse le pli.");
ad(elt1_,txt3_);
Element elt6_=el(_doc,"br");
ad(elt1_,elt6_);
Element elt7_=el(_doc,"br");
ad(elt1_,elt7_);
Text txt4_=tx(_doc,"On appelle couleur demand&#233;e la couleur de la carte entam&#233;e si celle-ci n''est pas l''Excuse; la couleur de la deuxi&#232;me carte sinon.");
ad(elt1_,txt4_);
Element elt8_=el(_doc,"br");
ad(elt1_,elt8_);
Text txt5_=tx(_doc,"On dit qu''un pli est coup&#233; s''il existe au moins un atout (Excuse exclue).");
ad(elt1_,txt5_);
Element elt9_=el(_doc,"br");
ad(elt1_,elt9_);
Element elt10_=el(_doc,"br");
ad(elt1_,elt10_);
Element elt11_=el(_doc,"ol");
Element elt12_=el(_doc,"li");
Text txt6_=tx(_doc,"L''entame");
ad(elt12_,txt6_);
Element elt13_=el(_doc,"br");
ad(elt12_,elt13_);
Element elt14_=el(_doc,"br");
ad(elt12_,elt14_);
Text txt7_=tx(_doc,"Le joueur &#224; droite du donneur commence &#224; jouer une carte de son choix, une fois que le preneur a fait son chien.");
ad(elt12_,txt7_);
Element elt15_=el(_doc,"br");
ad(elt12_,elt15_);
Text txt8_=tx(_doc,"En cas de grand chelem, c''est le preneur qui entame.");
ad(elt12_,txt8_);
Element elt16_=el(_doc,"br");
ad(elt12_,elt16_);
ad(elt11_,elt12_);
Element elt17_=el(_doc,"li");
Text txt9_=tx(_doc,"Les r&#232;gles");
ad(elt17_,txt9_);
Element elt18_=el(_doc,"br");
ad(elt17_,elt18_);
Element elt19_=el(_doc,"br");
ad(elt17_,elt19_);
Text txt10_=tx(_doc,"L''Excuse peut &#234;tre jou&#233;e &#224; n''importe quel tour.");
ad(elt17_,txt10_);
Element elt20_=el(_doc,"br");
ad(elt17_,elt20_);
Element elt21_=el(_doc,"br");
ad(elt17_,elt21_);
Element elt22_=el(_doc,"ol");
Element elt23_=el(_doc,"li");
Text txt11_=tx(_doc,"Si la carte entam&#233;e est l''Excuse,");
ad(elt23_,txt11_);
Element elt24_=el(_doc,"br");
ad(elt23_,elt24_);
Text txt12_=tx(_doc,"alors le joueur suivant joue la carte de son choix.");
ad(elt23_,txt12_);
Element elt25_=el(_doc,"br");
ad(elt23_,elt25_);
ad(elt22_,elt23_);
Element elt26_=el(_doc,"li");
Text txt13_=tx(_doc,"Si la couleur demand&#233;e est atout(l''Excuse, ici, n''est pas un atout.),");
ad(elt26_,txt13_);
Element elt27_=el(_doc,"br");
ad(elt26_,elt27_);
Element elt28_=el(_doc,"ol");
Element elt29_=el(_doc,"li");
Text txt14_=tx(_doc,"Si le joueur ne poss&#232;de pas d''atout,");
ad(elt29_,txt14_);
Element elt30_=el(_doc,"br");
ad(elt29_,elt30_);
Text txt15_=tx(_doc,"alors il joue la carte de son choix.");
ad(elt29_,txt15_);
Element elt31_=el(_doc,"br");
ad(elt29_,elt31_);
ad(elt28_,elt29_);
Element elt32_=el(_doc,"li");
Text txt16_=tx(_doc,"Sinon si ce dernier peut monter (mettre un atout avec un num&#233;ro sup&#233;rieur),");
ad(elt32_,txt16_);
Element elt33_=el(_doc,"br");
ad(elt32_,elt33_);
Text txt17_=tx(_doc,"alors il doit mettre un atout avec un num&#233;ro sup&#233;rieur; on dit qu''il monte.");
ad(elt32_,txt17_);
Element elt34_=el(_doc,"br");
ad(elt32_,elt34_);
ad(elt28_,elt32_);
Element elt35_=el(_doc,"li");
Text txt18_=tx(_doc,"Sinon, il fournit de l''atout, n''importe lequel,");
ad(elt35_,txt18_);
ad(elt28_,elt35_);
ad(elt26_,elt28_);
Element elt36_=el(_doc,"br");
ad(elt26_,elt36_);
ad(elt22_,elt26_);
Element elt37_=el(_doc,"li");
Text txt19_=tx(_doc,"Si la couleur demand&#233;e n''est pas atout,");
ad(elt37_,txt19_);
Element elt38_=el(_doc,"br");
ad(elt37_,elt38_);
Element elt39_=el(_doc,"ol");
Element elt40_=el(_doc,"li");
Text txt20_=tx(_doc,"Si le joueur poss&#232;de des cartes de m&#234;me couleur que celle de la carte entam&#233;e,");
ad(elt40_,txt20_);
Element elt41_=el(_doc,"br");
ad(elt40_,elt41_);
Text txt21_=tx(_doc,"alors celui-ci doit fournir &#224; la couleur sans &#234;tre oblig&#233; de monter.");
ad(elt40_,txt21_);
Element elt42_=el(_doc,"br");
ad(elt40_,elt42_);
ad(elt39_,elt40_);
Element elt43_=el(_doc,"li");
Text txt22_=tx(_doc,"Sinon, si le joueur ne poss&#232;de pas d''atout");
ad(elt43_,txt22_);
Element elt44_=el(_doc,"br");
ad(elt43_,elt44_);
Text txt23_=tx(_doc,"alors il joue la carte de son choix.");
ad(elt43_,txt23_);
ad(elt39_,elt43_);
Element elt45_=el(_doc,"li");
Text txt24_=tx(_doc,"Sinon, si le pli n''est pas coup&#233;");
ad(elt45_,txt24_);
Element elt46_=el(_doc,"br");
ad(elt45_,elt46_);
Text txt25_=tx(_doc,"alors il doit utiliser un atout de son choix, on dit qu''il coupe.");
ad(elt45_,txt25_);
ad(elt39_,elt45_);
Element elt47_=el(_doc,"li");
Text txt26_=tx(_doc,"Sinon, si le joueur peut monter sur le maximum des atouts jou&#233;s,");
ad(elt47_,txt26_);
Element elt48_=el(_doc,"br");
ad(elt47_,elt48_);
Text txt27_=tx(_doc,"alors il doit utiliser un atout et monter sur les atouts pr&#233;c&#233;dents, on dit qu''il sur-coupe.");
ad(elt47_,txt27_);
Element elt49_=el(_doc,"br");
ad(elt47_,elt49_);
ad(elt39_,elt47_);
Element elt50_=el(_doc,"li");
Text txt28_=tx(_doc,"Sinon il doit utiliser un atout de son choix, on dit qu''il sous-coupe.");
ad(elt50_,txt28_);
Element elt51_=el(_doc,"br");
ad(elt50_,elt51_);
ad(elt39_,elt50_);
ad(elt37_,elt39_);
ad(elt22_,elt37_);
ad(elt17_,elt22_);
ad(elt11_,elt17_);
ad(elt1_,elt11_);
Element elt52_=el(_doc,"br");
ad(elt1_,elt52_);
Element elt53_=el(_doc,"br");
ad(elt1_,elt53_);
Text txt29_=tx(_doc,"Avant le dernier tour, l''Excuse ne permet pas de faire de pli. Elle est imprenable.");
ad(elt1_,txt29_);
Element elt54_=el(_doc,"br");
ad(elt1_,elt54_);
Text txt30_=tx(_doc,"Elle est remplac&#233;e par une carte basse ou un atout num&#233;rot&#233; de 2 &#224; 20.");
ad(elt1_,txt30_);
Element elt55_=el(_doc,"br");
ad(elt1_,elt55_);
Element elt56_=el(_doc,"br");
ad(elt1_,elt56_);
Text txt31_=tx(_doc,"Au dernier tour, si tous les plis pr&#233;c&#233;dents ne sont pas faits par l''&#233;quipe du joueur poss&#233;dant l''Excuse ou par le preneur,");
ad(elt1_,txt31_);
Element elt57_=el(_doc,"br");
ad(elt1_,elt57_);
Text txt32_=tx(_doc,"alors l''Excuse est prenable et appartient au camp du joueur qui fait le dernier pli.");
ad(elt1_,txt32_);
Element elt58_=el(_doc,"br");
ad(elt1_,elt58_);
Element elt59_=el(_doc,"br");
ad(elt1_,elt59_);
Text txt33_=tx(_doc,"Si un joueur avec &#233;ventuellement des partenaires fait ses plis du 1er &#224; l''avant dernier tour et joue l''Excuse au dernier tour,");
ad(elt1_,txt33_);
Element elt60_=el(_doc,"br");
ad(elt1_,elt60_);
Text txt34_=tx(_doc,"alors il r&#233;alise un chelem, m&#234;me s''il ne le demande pas.");
ad(elt1_,txt34_);
Element elt61_=el(_doc,"br");
ad(elt1_,elt61_);
Text txt35_=tx(_doc,"Le(s) partenaire(s) &#233;ventuel(s) d''un joueur, demandant un chelem, ne sont/n''est pas oblig&#233;s de jouer l''Excuse au dernier tour.");
ad(elt1_,txt35_);
Element elt62_=el(_doc,"br");
ad(elt1_,elt62_);
ad(elt0_,elt1_);
_doc.appendChild(elt0_);
}
static Attr at(String _name,String _value){
return CoreDocument.createAttribute(_name,_value);
}
static void at(Element _elt,CustList<Attr> _ls){
_elt.setAttributes(new NamedNodeMap(_ls));
}
static CustList<Attr> al(int _len){
return new CustList<Attr>(new CollCapacity(_len));
}
static Text tx(Document _doc,String _value){
return _doc.createEscapedTextNode(_value);
}
static Element el(Document _doc,String _value){
return _doc.createElement(_value);
}
static void ad(Element _elt,Node _value){
_elt.appendChild(_value);
}
}
