package code.scripts.pages.aiki;
import code.sml.*;
import code.util.*;
import code.util.ints.*;
final class PageDataIndex{
private PageDataIndex(){}
static Document build(){
FullDocument doc_ = DocumentBuilder.newXmlDocument(4);
build(doc_);
return doc_;
}
static void build(Document _doc){
Element elt0_=el(_doc,"html");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("xmlns:c","javahtml"));
attrs0_.add(at("c:bean","welcome"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"head");
Element elt2_=el(_doc,"title");
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_index,title"));
at(elt3_,attrs1_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
ad(elt0_,elt1_);
Element elt4_=el(_doc,"body");
build0(elt4_,_doc);
ad(elt0_,elt4_);
_doc.appendChild(elt0_);
}
static void build0(Element _body,Document _doc){
Element elt0_=el(_doc,"ul");
Element elt1_=el(_doc,"li");
Element elt2_=el(_doc,"a");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("c:command","web/html/general/general.html"));
attrs0_.add(at("href",""));
at(elt2_,attrs0_);
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_index,general"));
at(elt3_,attrs1_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
ad(elt0_,elt1_);
Element elt4_=el(_doc,"li");
Element elt5_=el(_doc,"a");
CustList<Attr> attrs2_=al(2);
attrs2_.add(at("c:command","web/html/round/helpround.html"));
attrs2_.add(at("href",""));
at(elt5_,attrs2_);
Element elt6_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg_index,round"));
at(elt6_,attrs3_);
ad(elt5_,elt6_);
ad(elt4_,elt5_);
ad(elt0_,elt4_);
Element elt7_=el(_doc,"li");
Element elt8_=el(_doc,"a");
CustList<Attr> attrs4_=al(2);
attrs4_.add(at("c:command","$clickPokedex"));
attrs4_.add(at("href",""));
at(elt8_,attrs4_);
Element elt9_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg_index,pokedex"));
at(elt9_,attrs5_);
ad(elt8_,elt9_);
ad(elt7_,elt8_);
ad(elt0_,elt7_);
Element elt10_=el(_doc,"li");
Element elt11_=el(_doc,"a");
CustList<Attr> attrs6_=al(2);
attrs6_.add(at("c:command","$clickItems"));
attrs6_.add(at("href",""));
at(elt11_,attrs6_);
Element elt12_=el(_doc,"c:message");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","msg_index,items"));
at(elt12_,attrs7_);
ad(elt11_,elt12_);
ad(elt10_,elt11_);
ad(elt0_,elt10_);
Element elt13_=el(_doc,"li");
Element elt14_=el(_doc,"a");
CustList<Attr> attrs8_=al(2);
attrs8_.add(at("c:command","$seeAllMoves"));
attrs8_.add(at("href",""));
at(elt14_,attrs8_);
Element elt15_=el(_doc,"c:message");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("value","msg_index,moves"));
at(elt15_,attrs9_);
ad(elt14_,elt15_);
ad(elt13_,elt14_);
ad(elt0_,elt13_);
Element elt16_=el(_doc,"li");
Element elt17_=el(_doc,"a");
CustList<Attr> attrs10_=al(2);
attrs10_.add(at("c:command","$seeLearntMoves"));
attrs10_.add(at("href",""));
at(elt17_,attrs10_);
Element elt18_=el(_doc,"c:message");
CustList<Attr> attrs11_=al(1);
attrs11_.add(at("value","msg_index,learntMoves"));
at(elt18_,attrs11_);
ad(elt17_,elt18_);
ad(elt16_,elt17_);
ad(elt0_,elt16_);
Element elt19_=el(_doc,"li");
Element elt20_=el(_doc,"a");
CustList<Attr> attrs12_=al(2);
attrs12_.add(at("c:command","$seeNotLearntMoves"));
attrs12_.add(at("href",""));
at(elt20_,attrs12_);
Element elt21_=el(_doc,"c:message");
CustList<Attr> attrs13_=al(1);
attrs13_.add(at("value","msg_index,notLearntMoves"));
at(elt21_,attrs13_);
ad(elt20_,elt21_);
ad(elt19_,elt20_);
ad(elt0_,elt19_);
Element elt22_=el(_doc,"li");
Element elt23_=el(_doc,"a");
CustList<Attr> attrs14_=al(2);
attrs14_.add(at("c:command","$clickAbilities"));
attrs14_.add(at("href",""));
at(elt23_,attrs14_);
Element elt24_=el(_doc,"c:message");
CustList<Attr> attrs15_=al(1);
attrs15_.add(at("value","msg_index,abilities"));
at(elt24_,attrs15_);
ad(elt23_,elt24_);
ad(elt22_,elt23_);
ad(elt0_,elt22_);
Element elt25_=el(_doc,"li");
Element elt26_=el(_doc,"a");
CustList<Attr> attrs16_=al(2);
attrs16_.add(at("c:command","$clickStatus"));
attrs16_.add(at("href",""));
at(elt26_,attrs16_);
Element elt27_=el(_doc,"c:message");
CustList<Attr> attrs17_=al(1);
attrs17_.add(at("value","msg_index,status"));
at(elt27_,attrs17_);
ad(elt26_,elt27_);
ad(elt25_,elt26_);
ad(elt0_,elt25_);
Element elt28_=el(_doc,"li");
Element elt29_=el(_doc,"a");
CustList<Attr> attrs18_=al(2);
attrs18_.add(at("c:command","web/html/combo/combos.html"));
attrs18_.add(at("href",""));
at(elt29_,attrs18_);
Element elt30_=el(_doc,"c:message");
CustList<Attr> attrs19_=al(1);
attrs19_.add(at("value","msg_index,combos"));
at(elt30_,attrs19_);
ad(elt29_,elt30_);
ad(elt28_,elt29_);
ad(elt0_,elt28_);
Element elt31_=el(_doc,"li");
Element elt32_=el(_doc,"a");
CustList<Attr> attrs20_=al(2);
attrs20_.add(at("c:command","web/html/endround/endround.html"));
attrs20_.add(at("href",""));
at(elt32_,attrs20_);
Element elt33_=el(_doc,"c:message");
CustList<Attr> attrs21_=al(1);
attrs21_.add(at("value","msg_index,endRound"));
at(elt33_,attrs21_);
ad(elt32_,elt33_);
ad(elt31_,elt32_);
ad(elt0_,elt31_);
Element elt34_=el(_doc,"li");
Element elt35_=el(_doc,"a");
CustList<Attr> attrs22_=al(2);
attrs22_.add(at("c:command","web/html/map/map.html"));
attrs22_.add(at("href",""));
at(elt35_,attrs22_);
Element elt36_=el(_doc,"c:message");
CustList<Attr> attrs23_=al(1);
attrs23_.add(at("value","msg_index,map"));
at(elt36_,attrs23_);
ad(elt35_,elt36_);
ad(elt34_,elt35_);
ad(elt0_,elt34_);
Element elt37_=el(_doc,"li");
Element elt38_=el(_doc,"a");
CustList<Attr> attrs24_=al(2);
attrs24_.add(at("c:command","web/html/solution/solution.html"));
attrs24_.add(at("href",""));
at(elt38_,attrs24_);
Element elt39_=el(_doc,"c:message");
CustList<Attr> attrs25_=al(1);
attrs25_.add(at("value","msg_index,solution"));
at(elt39_,attrs25_);
ad(elt38_,elt39_);
ad(elt37_,elt38_);
ad(elt0_,elt37_);
Element elt40_=el(_doc,"li");
Element elt41_=el(_doc,"a");
CustList<Attr> attrs26_=al(2);
attrs26_.add(at("c:command","$clickSimulation"));
attrs26_.add(at("href",""));
at(elt41_,attrs26_);
Element elt42_=el(_doc,"c:message");
CustList<Attr> attrs27_=al(1);
attrs27_.add(at("value","msg_index,simulation"));
at(elt42_,attrs27_);
ad(elt41_,elt42_);
ad(elt40_,elt41_);
ad(elt0_,elt40_);
Element elt43_=el(_doc,"li");
Element elt44_=el(_doc,"a");
CustList<Attr> attrs28_=al(2);
attrs28_.add(at("c:command","web/html/langs/langs.html"));
attrs28_.add(at("href",""));
at(elt44_,attrs28_);
Element elt45_=el(_doc,"c:message");
CustList<Attr> attrs29_=al(1);
attrs29_.add(at("value","msg_index,langs"));
at(elt45_,attrs29_);
ad(elt44_,elt45_);
ad(elt43_,elt44_);
ad(elt0_,elt43_);
ad(_body,elt0_);
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
