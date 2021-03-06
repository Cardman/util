package code.scripts.pages.aiki;
import code.sml.*;
import code.util.*;
import code.util.ints.*;
final class PageDataEndroundIndividual{
private PageDataEndroundIndividual(){}
static Document build(){
FullDocument doc_ = DocumentBuilder.newXmlDocument(4);
build(doc_);
return doc_;
}
static void build(Document _doc){
Element elt0_=el(_doc,"html");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("xmlns:c","javahtml"));
attrs0_.add(at("c:bean","end_individual"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"head");
Element elt2_=el(_doc,"link");
CustList<Attr> attrs1_=al(3);
attrs1_.add(at("href","web/css/abilities.css"));
attrs1_.add(at("rel","stylesheet"));
attrs1_.add(at("type","text/css"));
at(elt2_,attrs1_);
ad(elt1_,elt2_);
ad(elt0_,elt1_);
Element elt3_=el(_doc,"body");
build0(elt3_,_doc);
build1(elt3_,_doc);
ad(elt0_,elt3_);
_doc.appendChild(elt0_);
}
static void build0(Element _body,Document _doc){
Element elt0_=el(_doc,"c:import");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("page","{endRoundHtml}"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:package");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("name","aiki.beans.endround"));
at(elt1_,attrs1_);
Element elt2_=el(_doc,"c:class");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("name","EffectEndRoundBean"));
at(elt2_,attrs2_);
Element elt3_=el(_doc,"c:field");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("prepare","$intern.index=index"));
at(elt3_,attrs3_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
ad(elt0_,elt1_);
ad(_body,elt0_);
}
static void build1(Element _body,Document _doc){
Element elt0_=el(_doc,"p");
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("value","msg_individual,effect"));
at(elt1_,attrs0_);
ad(elt0_,elt1_);
Element elt2_=el(_doc,"c:if");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("condition","!deleteAllStatus.isZero()"));
at(elt2_,attrs1_);
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("value","msg_individual,delete_all_status"));
at(elt3_,attrs2_);
Element elt4_=el(_doc,"param");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","deleteAllStatus"));
at(elt4_,attrs3_);
ad(elt3_,elt4_);
ad(elt2_,elt3_);
ad(elt0_,elt2_);
Element elt5_=el(_doc,"c:if");
CustList<Attr> attrs4_=al(1);
attrs4_.add(at("condition","!recoilDamage.isZero()"));
at(elt5_,attrs4_);
Element elt6_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg_individual,recoil_damage"));
at(elt6_,attrs5_);
Element elt7_=el(_doc,"param");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("value","recoilDamage"));
at(elt7_,attrs6_);
ad(elt6_,elt7_);
ad(elt5_,elt6_);
ad(elt0_,elt5_);
Element elt8_=el(_doc,"c:if");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("condition","!healHp.isZero()"));
at(elt8_,attrs7_);
Element elt9_=el(_doc,"c:message");
CustList<Attr> attrs8_=al(1);
attrs8_.add(at("value","msg_individual,heal_hp"));
at(elt9_,attrs8_);
Element elt10_=el(_doc,"param");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("value","healHp"));
at(elt10_,attrs9_);
ad(elt9_,elt10_);
ad(elt8_,elt9_);
ad(elt0_,elt8_);
Element elt11_=el(_doc,"c:if");
CustList<Attr> attrs10_=al(1);
attrs10_.add(at("condition","!userStatusEndRound.isEmpty()"));
at(elt11_,attrs10_);
Element elt12_=el(_doc,"c:message");
CustList<Attr> attrs11_=al(1);
attrs11_.add(at("value","msg_individual,user_status"));
at(elt12_,attrs11_);
Element elt13_=el(_doc,"param");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("value","getTrUserStatus()"));
at(elt13_,attrs12_);
ad(elt12_,elt13_);
Element elt14_=el(_doc,"param");
CustList<Attr> attrs13_=al(1);
attrs13_.add(at("value","index"));
at(elt14_,attrs13_);
ad(elt12_,elt14_);
ad(elt11_,elt12_);
ad(elt0_,elt11_);
Element elt15_=el(_doc,"c:if");
CustList<Attr> attrs14_=al(1);
attrs14_.add(at("condition","!multDamageStatus.isEmpty()"));
at(elt15_,attrs14_);
Element elt16_=el(_doc,"c:message");
CustList<Attr> attrs15_=al(1);
attrs15_.add(at("value","msg_individual,mult_damage_status"));
at(elt16_,attrs15_);
ad(elt15_,elt16_);
Element elt17_=el(_doc,"table");
Element elt18_=el(_doc,"thead");
Element elt19_=el(_doc,"tr");
Element elt20_=el(_doc,"th");
Element elt21_=el(_doc,"c:message");
CustList<Attr> attrs16_=al(1);
attrs16_.add(at("value","msg_individual,mult_damage_status_key"));
at(elt21_,attrs16_);
ad(elt20_,elt21_);
ad(elt19_,elt20_);
Element elt22_=el(_doc,"th");
Element elt23_=el(_doc,"c:message");
CustList<Attr> attrs17_=al(1);
attrs17_.add(at("value","msg_individual,mult_damage_status_value"));
at(elt23_,attrs17_);
ad(elt22_,elt23_);
ad(elt19_,elt22_);
ad(elt18_,elt19_);
ad(elt17_,elt18_);
Element elt24_=el(_doc,"tbody");
Element elt25_=el(_doc,"c:for");
CustList<Attr> attrs18_=al(5);
attrs18_.add(at("key","s"));
attrs18_.add(at("map","multDamageStatus"));
attrs18_.add(at("value","r"));
attrs18_.add(at("keyClassName","java.lang.Object"));
attrs18_.add(at("varClassName","r"));
at(elt25_,attrs18_);
Element elt26_=el(_doc,"tr");
Element elt27_=el(_doc,"td");
Element elt28_=el(_doc,"a");
CustList<Attr> attrs19_=al(2);
attrs19_.add(at("c:command","$clickDamageStatus({index},{([s])})"));
attrs19_.add(at("href",""));
at(elt28_,attrs19_);
Text txt0_=tx(_doc,"{getTrDamageStatus(([s]))}");
ad(elt28_,txt0_);
ad(elt27_,elt28_);
ad(elt26_,elt27_);
Element elt29_=el(_doc,"td");
Text txt1_=tx(_doc,"{r}");
ad(elt29_,txt1_);
ad(elt26_,elt29_);
ad(elt25_,elt26_);
ad(elt24_,elt25_);
ad(elt17_,elt24_);
ad(elt15_,elt17_);
Element elt30_=el(_doc,"br");
ad(elt15_,elt30_);
ad(elt0_,elt15_);
Element elt31_=el(_doc,"c:if");
CustList<Attr> attrs20_=al(1);
attrs20_.add(at("condition","!healHpByOwnerTypes.isEmpty()"));
at(elt31_,attrs20_);
Element elt32_=el(_doc,"c:message");
CustList<Attr> attrs21_=al(1);
attrs21_.add(at("value","msg_individual,heal_hp_by_owner_types"));
at(elt32_,attrs21_);
ad(elt31_,elt32_);
Element elt33_=el(_doc,"table");
Element elt34_=el(_doc,"thead");
Element elt35_=el(_doc,"tr");
Element elt36_=el(_doc,"th");
Element elt37_=el(_doc,"c:message");
CustList<Attr> attrs22_=al(1);
attrs22_.add(at("value","msg_individual,heal_hp_by_owner_types_key"));
at(elt37_,attrs22_);
ad(elt36_,elt37_);
ad(elt35_,elt36_);
Element elt38_=el(_doc,"th");
Element elt39_=el(_doc,"c:message");
CustList<Attr> attrs23_=al(1);
attrs23_.add(at("value","msg_individual,heal_hp_by_owner_types_value"));
at(elt39_,attrs23_);
ad(elt38_,elt39_);
ad(elt35_,elt38_);
ad(elt34_,elt35_);
ad(elt33_,elt34_);
Element elt40_=el(_doc,"tbody");
Element elt41_=el(_doc,"c:for");
CustList<Attr> attrs24_=al(5);
attrs24_.add(at("key","s"));
attrs24_.add(at("map","healHpByOwnerTypes"));
attrs24_.add(at("value","r"));
attrs24_.add(at("keyClassName","java.lang.Object"));
attrs24_.add(at("varClassName","r"));
at(elt41_,attrs24_);
Element elt42_=el(_doc,"tr");
Element elt43_=el(_doc,"c:if");
CustList<Attr> attrs25_=al(1);
attrs25_.add(at("condition","isType(([s]))"));
at(elt43_,attrs25_);
Element elt44_=el(_doc,"td");
Text txt2_=tx(_doc,"{getTrType(([s]))}");
ad(elt44_,txt2_);
ad(elt43_,elt44_);
ad(elt42_,elt43_);
Element elt45_=el(_doc,"c:if");
CustList<Attr> attrs26_=al(1);
attrs26_.add(at("condition","!isType(([s]))"));
at(elt45_,attrs26_);
Element elt46_=el(_doc,"td");
Element elt47_=el(_doc,"c:message");
CustList<Attr> attrs27_=al(1);
attrs27_.add(at("value","msg_individual,heal_hp_by_owner_types_other"));
at(elt47_,attrs27_);
ad(elt46_,elt47_);
ad(elt45_,elt46_);
ad(elt42_,elt45_);
Element elt48_=el(_doc,"c:if");
CustList<Attr> attrs28_=al(1);
attrs28_.add(at("condition","r.isZeroOrGt()"));
at(elt48_,attrs28_);
Element elt49_=el(_doc,"td");
Element elt50_=el(_doc,"c:message");
CustList<Attr> attrs29_=al(1);
attrs29_.add(at("value","msg_individual,heal_hp_by_owner_types_value_w"));
at(elt50_,attrs29_);
Element elt51_=el(_doc,"param");
CustList<Attr> attrs30_=al(1);
attrs30_.add(at("value","r.absNb()"));
at(elt51_,attrs30_);
ad(elt50_,elt51_);
ad(elt49_,elt50_);
ad(elt48_,elt49_);
ad(elt42_,elt48_);
Element elt52_=el(_doc,"c:if");
CustList<Attr> attrs31_=al(1);
attrs31_.add(at("condition","!r.isZeroOrGt()"));
at(elt52_,attrs31_);
Element elt53_=el(_doc,"td");
Element elt54_=el(_doc,"c:message");
CustList<Attr> attrs32_=al(1);
attrs32_.add(at("value","msg_individual,heal_hp_by_owner_types_value_l"));
at(elt54_,attrs32_);
Element elt55_=el(_doc,"param");
CustList<Attr> attrs33_=al(1);
attrs33_.add(at("value","r.absNb()"));
at(elt55_,attrs33_);
ad(elt54_,elt55_);
ad(elt53_,elt54_);
ad(elt52_,elt53_);
ad(elt42_,elt52_);
ad(elt41_,elt42_);
ad(elt40_,elt41_);
ad(elt33_,elt40_);
ad(elt31_,elt33_);
Element elt56_=el(_doc,"br");
ad(elt31_,elt56_);
ad(elt0_,elt31_);
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
