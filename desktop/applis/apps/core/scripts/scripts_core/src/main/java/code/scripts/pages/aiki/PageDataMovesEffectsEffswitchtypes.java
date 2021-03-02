package code.scripts.pages.aiki;
import code.sml.*;
import code.util.*;
import code.util.ints.*;
final class PageDataMovesEffectsEffswitchtypes{
private PageDataMovesEffectsEffswitchtypes(){}
static Document build(){
FullDocument doc_ = DocumentBuilder.newXmlDocument(4);
build(doc_);
return doc_;
}
static void build(Document _doc){
Element elt0_=el(_doc,"html");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("xmlns:c","javahtml"));
attrs0_.add(at("c:bean","eff_switchtypes"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"head");
Element elt2_=el(_doc,"link");
CustList<Attr> attrs1_=al(3);
attrs1_.add(at("href","web/css/moves.css"));
attrs1_.add(at("rel","stylesheet"));
attrs1_.add(at("type","text/css"));
at(elt2_,attrs1_);
ad(elt1_,elt2_);
ad(elt0_,elt1_);
Element elt3_=el(_doc,"body");
build0(elt3_,_doc);
ad(elt0_,elt3_);
_doc.appendChild(elt0_);
}
static void build0(Element _body,Document _doc){
Element elt0_=el(_doc,"p");
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("value","msg_effswitchtypes,effect"));
at(elt1_,attrs0_);
ad(elt0_,elt1_);
Element elt2_=el(_doc,"c:import");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("page","{effectBean}"));
at(elt2_,attrs1_);
Element elt3_=el(_doc,"c:package");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("name","aiki.beans.moves.effects"));
at(elt3_,attrs2_);
Element elt4_=el(_doc,"c:class");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("name","EffectBean"));
at(elt4_,attrs3_);
Element elt5_=el(_doc,"c:field");
CustList<Attr> attrs4_=al(1);
attrs4_.add(at("prepare","$intern.index=index"));
at(elt5_,attrs4_);
ad(elt4_,elt5_);
Element elt6_=el(_doc,"c:field");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("prepare","$intern.move=move"));
at(elt6_,attrs5_);
ad(elt4_,elt6_);
ad(elt3_,elt4_);
ad(elt2_,elt3_);
ad(elt0_,elt2_);
Element elt7_=el(_doc,"c:if");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("condition","isResTypes()"));
at(elt7_,attrs6_);
Element elt8_=el(_doc,"c:message");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","msg_effswitchtypes,res_moves"));
at(elt8_,attrs7_);
ad(elt7_,elt8_);
ad(elt0_,elt7_);
Element elt9_=el(_doc,"c:if");
CustList<Attr> attrs8_=al(1);
attrs8_.add(at("condition","isUserTypes()"));
at(elt9_,attrs8_);
Element elt10_=el(_doc,"c:message");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("value","msg_effswitchtypes,user_moves"));
at(elt10_,attrs9_);
ad(elt9_,elt10_);
ad(elt0_,elt9_);
Element elt11_=el(_doc,"c:if");
CustList<Attr> attrs10_=al(1);
attrs10_.add(at("condition","!isConstTypes()"));
at(elt11_,attrs10_);
Element elt12_=el(_doc,"c:if");
CustList<Attr> attrs11_=al(1);
attrs11_.add(at("condition","!chgtTypeByEnv.isEmpty()"));
at(elt12_,attrs11_);
Element elt13_=el(_doc,"c:message");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("value","msg_effswitchtypes,envir"));
at(elt13_,attrs12_);
ad(elt12_,elt13_);
Element elt14_=el(_doc,"table");
Element elt15_=el(_doc,"thead");
Element elt16_=el(_doc,"tr");
Element elt17_=el(_doc,"th");
Element elt18_=el(_doc,"c:message");
CustList<Attr> attrs13_=al(1);
attrs13_.add(at("value","msg_effswitchtypes,envir_env"));
at(elt18_,attrs13_);
ad(elt17_,elt18_);
ad(elt16_,elt17_);
Element elt19_=el(_doc,"th");
Element elt20_=el(_doc,"c:message");
CustList<Attr> attrs14_=al(1);
attrs14_.add(at("value","msg_effswitchtypes,envir_type"));
at(elt20_,attrs14_);
ad(elt19_,elt20_);
ad(elt16_,elt19_);
ad(elt15_,elt16_);
ad(elt14_,elt15_);
Element elt21_=el(_doc,"tbody");
Element elt22_=el(_doc,"c:for");
CustList<Attr> attrs15_=al(5);
attrs15_.add(at("key","e"));
attrs15_.add(at("map","chgtTypeByEnv"));
attrs15_.add(at("value","t"));
attrs15_.add(at("keyClassName","java.lang.Object"));
attrs15_.add(at("varClassName","java.lang.String"));
at(elt22_,attrs15_);
Element elt23_=el(_doc,"tr");
Element elt24_=el(_doc,"td");
Text txt0_=tx(_doc,"{getTrEnv(([e]))}");
ad(elt24_,txt0_);
ad(elt23_,elt24_);
Element elt25_=el(_doc,"td");
Text txt1_=tx(_doc,"{t}");
ad(elt25_,txt1_);
ad(elt23_,elt25_);
ad(elt22_,elt23_);
ad(elt21_,elt22_);
ad(elt14_,elt21_);
ad(elt12_,elt14_);
Element elt26_=el(_doc,"br");
ad(elt12_,elt26_);
Element elt27_=el(_doc,"c:if");
CustList<Attr> attrs16_=al(1);
attrs16_.add(at("condition","!globalMoves.isEmpty()"));
at(elt27_,attrs16_);
Element elt28_=el(_doc,"c:message");
CustList<Attr> attrs17_=al(1);
attrs17_.add(at("value","msg_effswitchtypes,envir_env_exc"));
at(elt28_,attrs17_);
ad(elt27_,elt28_);
Element elt29_=el(_doc,"ul");
Element elt30_=el(_doc,"c:for");
CustList<Attr> attrs18_=al(2);
attrs18_.add(at("list","globalMoves"));
attrs18_.add(at("var","m"));
at(elt30_,attrs18_);
Element elt31_=el(_doc,"li");
Element elt32_=el(_doc,"a");
CustList<Attr> attrs19_=al(2);
attrs19_.add(at("c:command","$clickGlobalMoveFctEnv({([m])})"));
attrs19_.add(at("href",""));
at(elt32_,attrs19_);
Text txt2_=tx(_doc,"{getTrGlobalMoveFctEnv(([m]))}");
ad(elt32_,txt2_);
ad(elt31_,elt32_);
ad(elt30_,elt31_);
ad(elt29_,elt30_);
ad(elt27_,elt29_);
Element elt33_=el(_doc,"br");
ad(elt27_,elt33_);
ad(elt12_,elt27_);
ad(elt11_,elt12_);
Element elt34_=el(_doc,"c:if");
CustList<Attr> attrs20_=al(1);
attrs20_.add(at("condition","!addedTypes.isEmpty()"));
at(elt34_,attrs20_);
Element elt35_=el(_doc,"c:message");
CustList<Attr> attrs21_=al(1);
attrs21_.add(at("value","msg_effswitchtypes,added_types"));
at(elt35_,attrs21_);
ad(elt34_,elt35_);
Element elt36_=el(_doc,"ul");
Element elt37_=el(_doc,"c:for");
CustList<Attr> attrs22_=al(2);
attrs22_.add(at("list","addedTypes"));
attrs22_.add(at("var","t"));
at(elt37_,attrs22_);
Element elt38_=el(_doc,"li");
Text txt3_=tx(_doc,"{getTrAddedType(([t]))}");
ad(elt38_,txt3_);
ad(elt37_,elt38_);
ad(elt36_,elt37_);
ad(elt34_,elt36_);
Element elt39_=el(_doc,"br");
ad(elt34_,elt39_);
ad(elt11_,elt34_);
Element elt40_=el(_doc,"c:if");
CustList<Attr> attrs23_=al(1);
attrs23_.add(at("condition","addedTypes.isEmpty()"));
at(elt40_,attrs23_);
Element elt41_=el(_doc,"c:if");
CustList<Attr> attrs24_=al(1);
attrs24_.add(at("condition","giveToTarget()"));
at(elt41_,attrs24_);
Element elt42_=el(_doc,"c:message");
CustList<Attr> attrs25_=al(1);
attrs25_.add(at("value","msg_effswitchtypes,affect_types_not_const_target"));
at(elt42_,attrs25_);
ad(elt41_,elt42_);
ad(elt40_,elt41_);
Element elt43_=el(_doc,"c:if");
CustList<Attr> attrs26_=al(1);
attrs26_.add(at("condition","giveToUser()"));
at(elt43_,attrs26_);
Element elt44_=el(_doc,"c:message");
CustList<Attr> attrs27_=al(1);
attrs27_.add(at("value","msg_effswitchtypes,affect_types_not_const_user"));
at(elt44_,attrs27_);
ad(elt43_,elt44_);
ad(elt40_,elt43_);
Element elt45_=el(_doc,"c:if");
CustList<Attr> attrs28_=al(1);
attrs28_.add(at("condition","switchTypes()"));
at(elt45_,attrs28_);
Element elt46_=el(_doc,"c:message");
CustList<Attr> attrs29_=al(1);
attrs29_.add(at("value","msg_effswitchtypes,switch_types"));
at(elt46_,attrs29_);
ad(elt45_,elt46_);
ad(elt40_,elt45_);
Element elt47_=el(_doc,"c:if");
CustList<Attr> attrs30_=al(1);
attrs30_.add(at("condition","giveConst()"));
at(elt47_,attrs30_);
Element elt48_=el(_doc,"c:message");
CustList<Attr> attrs31_=al(1);
attrs31_.add(at("value","msg_effswitchtypes,affect_types"));
at(elt48_,attrs31_);
ad(elt47_,elt48_);
Element elt49_=el(_doc,"ul");
Element elt50_=el(_doc,"c:for");
CustList<Attr> attrs32_=al(2);
attrs32_.add(at("list","constTypes"));
attrs32_.add(at("var","t"));
at(elt50_,attrs32_);
Element elt51_=el(_doc,"li");
Text txt4_=tx(_doc,"{getTrConstType(([t]))}");
ad(elt51_,txt4_);
ad(elt50_,elt51_);
ad(elt49_,elt50_);
ad(elt47_,elt49_);
Element elt52_=el(_doc,"br");
ad(elt47_,elt52_);
ad(elt40_,elt47_);
ad(elt11_,elt40_);
ad(elt0_,elt11_);
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
