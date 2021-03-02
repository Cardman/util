package code.scripts.pages.aiki;
import code.sml.*;
import code.util.*;
import code.util.ints.*;
final class PageDataSimulationEditpokemonmoves{
private PageDataSimulationEditpokemonmoves(){}
static Document build(){
FullDocument doc_ = DocumentBuilder.newXmlDocument(4);
build(doc_);
return doc_;
}
static void build(Document _doc){
Element elt0_=el(_doc,"html");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("xmlns:c","javahtml"));
attrs0_.add(at("c:bean","editpokemonmoves"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"head");
Element elt2_=el(_doc,"title");
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_levelsimu,title_search_moves"));
at(elt3_,attrs1_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
Element elt4_=el(_doc,"link");
CustList<Attr> attrs2_=al(3);
attrs2_.add(at("href","web/css/simulation.css"));
attrs2_.add(at("rel","stylesheet"));
attrs2_.add(at("type","text/css"));
at(elt4_,attrs2_);
ad(elt1_,elt4_);
ad(elt0_,elt1_);
Element elt5_=el(_doc,"body");
build0(elt5_,_doc);
build1(elt5_,_doc);
build2(elt5_,_doc);
ad(elt0_,elt5_);
_doc.appendChild(elt0_);
}
static void build0(Element _body,Document _doc){
Element elt0_=el(_doc,"a");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("c:command","$cancel"));
attrs0_.add(at("href",""));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_levelsimu,cancel"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
ad(_body,elt0_);
}
static void build1(Element _body,Document _doc){
Element elt0_=el(_doc,"form");
CustList<Attr> attrs0_=al(4);
attrs0_.add(at("action",""));
attrs0_.add(at("c:command","$search"));
attrs0_.add(at("method","post"));
attrs0_.add(at("name","searching"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_moves,content_name"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
Element elt2_=el(_doc,"input");
CustList<Attr> attrs2_=al(3);
attrs2_.add(at("c:varValue","typedName"));
attrs2_.add(at("name","typedName"));
attrs2_.add(at("type","text"));
at(elt2_,attrs2_);
ad(elt0_,elt2_);
Element elt3_=el(_doc,"br");
ad(elt0_,elt3_);
Element elt4_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg_moves,cat"));
at(elt4_,attrs3_);
ad(elt0_,elt4_);
Element elt5_=el(_doc,"c:select");
CustList<Attr> attrs4_=al(5);
attrs4_.add(at("default",""));
attrs4_.add(at("map","categories"));
attrs4_.add(at("name","category"));
attrs4_.add(at("update",""));
attrs4_.add(at("varValue","category"));
at(elt5_,attrs4_);
ad(elt0_,elt5_);
Element elt6_=el(_doc,"br");
ad(elt0_,elt6_);
Element elt7_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg_moves,content_type"));
at(elt7_,attrs5_);
ad(elt0_,elt7_);
Element elt8_=el(_doc,"input");
CustList<Attr> attrs6_=al(3);
attrs6_.add(at("c:varValue","typedType"));
attrs6_.add(at("name","typedType"));
attrs6_.add(at("type","text"));
at(elt8_,attrs6_);
ad(elt0_,elt8_);
Element elt9_=el(_doc,"br");
ad(elt0_,elt9_);
Element elt10_=el(_doc,"c:message");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","msg_moves,content_type_whole"));
at(elt10_,attrs7_);
ad(elt0_,elt10_);
Element elt11_=el(_doc,"input");
CustList<Attr> attrs8_=al(3);
attrs8_.add(at("c:varValue","wholeWord"));
attrs8_.add(at("name","wholeWord"));
attrs8_.add(at("type","checkbox"));
at(elt11_,attrs8_);
ad(elt0_,elt11_);
Element elt12_=el(_doc,"br");
ad(elt0_,elt12_);
Element elt13_=el(_doc,"c:if");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("condition","player"));
at(elt13_,attrs9_);
Element elt14_=el(_doc,"c:message");
CustList<Attr> attrs10_=al(1);
attrs10_.add(at("value","msg_simulation,available_moves"));
at(elt14_,attrs10_);
ad(elt13_,elt14_);
Element elt15_=el(_doc,"input");
CustList<Attr> attrs11_=al(3);
attrs11_.add(at("c:varValue","availableMovesOnly"));
attrs11_.add(at("name","availableMovesOnly"));
attrs11_.add(at("type","checkbox"));
at(elt15_,attrs11_);
ad(elt13_,elt15_);
Element elt16_=el(_doc,"br");
ad(elt13_,elt16_);
ad(elt0_,elt13_);
Element elt17_=el(_doc,"c:submit");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("message","msg_simulation,search"));
at(elt17_,attrs12_);
ad(elt0_,elt17_);
ad(_body,elt0_);
}
static void build2(Element _body,Document _doc){
Element elt0_=el(_doc,"form");
CustList<Attr> attrs0_=al(4);
attrs0_.add(at("action",""));
attrs0_.add(at("c:command","$addMoves"));
attrs0_.add(at("method","post"));
attrs0_.add(at("name","add"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"table");
Element elt2_=el(_doc,"caption");
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_moves,moves"));
at(elt3_,attrs1_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
Element elt4_=el(_doc,"thead");
Element elt5_=el(_doc,"tr");
Element elt6_=el(_doc,"th");
Element elt7_=el(_doc,"c:message");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("value","msg_moves,name_h"));
at(elt7_,attrs2_);
ad(elt6_,elt7_);
ad(elt5_,elt6_);
Element elt8_=el(_doc,"th");
Element elt9_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg_moves,pp_h"));
at(elt9_,attrs3_);
ad(elt8_,elt9_);
ad(elt5_,elt8_);
Element elt10_=el(_doc,"th");
Element elt11_=el(_doc,"c:message");
CustList<Attr> attrs4_=al(1);
attrs4_.add(at("value","msg_moves,types_h"));
at(elt11_,attrs4_);
ad(elt10_,elt11_);
ad(elt5_,elt10_);
Element elt12_=el(_doc,"th");
Element elt13_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg_moves,cat_h"));
at(elt13_,attrs5_);
ad(elt12_,elt13_);
ad(elt5_,elt12_);
Element elt14_=el(_doc,"th");
Element elt15_=el(_doc,"c:message");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("value","msg_moves,damag_h"));
at(elt15_,attrs6_);
ad(elt14_,elt15_);
ad(elt5_,elt14_);
Element elt16_=el(_doc,"th");
Element elt17_=el(_doc,"c:message");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","msg_moves,direc_h"));
at(elt17_,attrs7_);
ad(elt16_,elt17_);
ad(elt5_,elt16_);
Element elt18_=el(_doc,"th");
Element elt19_=el(_doc,"c:message");
CustList<Attr> attrs8_=al(1);
attrs8_.add(at("value","msg_moves,prio_h"));
at(elt19_,attrs8_);
ad(elt18_,elt19_);
ad(elt5_,elt18_);
Element elt20_=el(_doc,"th");
Element elt21_=el(_doc,"c:message");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("value","msg_simulation,selected"));
at(elt21_,attrs9_);
ad(elt20_,elt21_);
ad(elt5_,elt20_);
ad(elt4_,elt5_);
ad(elt1_,elt4_);
Element elt22_=el(_doc,"tbody");
Element elt23_=el(_doc,"c:for");
CustList<Attr> attrs10_=al(3);
attrs10_.add(at("className","aiki.beans.facade.simulation.dto.SelectLineMove"));
attrs10_.add(at("list","moves"));
attrs10_.add(at("var","d"));
at(elt23_,attrs10_);
Element elt24_=el(_doc,"tr");
Element elt25_=el(_doc,"td");
Text txt0_=tx(_doc,"{d.displayName}");
ad(elt25_,txt0_);
ad(elt24_,elt25_);
Element elt26_=el(_doc,"td");
Text txt1_=tx(_doc,"{d.pp}");
ad(elt26_,txt1_);
ad(elt24_,elt26_);
Element elt27_=el(_doc,"td");
Element elt28_=el(_doc,"c:for");
CustList<Attr> attrs11_=al(3);
attrs11_.add(at("list","d.getTypes()"));
attrs11_.add(at("var","t"));
attrs11_.add(at("className","java.lang.String"));
at(elt28_,attrs11_);
Text txt2_=tx(_doc,"{t} -");
ad(elt28_,txt2_);
ad(elt27_,elt28_);
ad(elt24_,elt27_);
Element elt29_=el(_doc,"td");
Text txt3_=tx(_doc,"{d.category}");
ad(elt29_,txt3_);
ad(elt24_,elt29_);
Element elt30_=el(_doc,"td");
Element elt31_=el(_doc,"c:if");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("condition","d.isDamageMove()"));
at(elt31_,attrs12_);
Element elt32_=el(_doc,"c:message");
CustList<Attr> attrs13_=al(1);
attrs13_.add(at("value","msg_moves,damaging"));
at(elt32_,attrs13_);
ad(elt31_,elt32_);
ad(elt30_,elt31_);
Element elt33_=el(_doc,"c:if");
CustList<Attr> attrs14_=al(1);
attrs14_.add(at("condition","!d.isDamageMove()"));
at(elt33_,attrs14_);
Element elt34_=el(_doc,"c:message");
CustList<Attr> attrs15_=al(1);
attrs15_.add(at("value","msg_moves,status"));
at(elt34_,attrs15_);
ad(elt33_,elt34_);
ad(elt30_,elt33_);
ad(elt24_,elt30_);
Element elt35_=el(_doc,"td");
Element elt36_=el(_doc,"c:if");
CustList<Attr> attrs16_=al(1);
attrs16_.add(at("condition","!d.isDamageMove()"));
at(elt36_,attrs16_);
Element elt37_=el(_doc,"c:message");
CustList<Attr> attrs17_=al(1);
attrs17_.add(at("value","msg_moves,status_indirect"));
at(elt37_,attrs17_);
ad(elt36_,elt37_);
ad(elt35_,elt36_);
Element elt38_=el(_doc,"c:if");
CustList<Attr> attrs18_=al(1);
attrs18_.add(at("condition","d.isDamageMove()"));
at(elt38_,attrs18_);
Element elt39_=el(_doc,"c:if");
CustList<Attr> attrs19_=al(1);
attrs19_.add(at("condition","d.isDirect()"));
at(elt39_,attrs19_);
Element elt40_=el(_doc,"c:message");
CustList<Attr> attrs20_=al(1);
attrs20_.add(at("value","msg_moves,damaging_direct"));
at(elt40_,attrs20_);
ad(elt39_,elt40_);
ad(elt38_,elt39_);
Element elt41_=el(_doc,"c:if");
CustList<Attr> attrs21_=al(1);
attrs21_.add(at("condition","!d.isDirect()"));
at(elt41_,attrs21_);
Element elt42_=el(_doc,"c:message");
CustList<Attr> attrs22_=al(1);
attrs22_.add(at("value","msg_moves,damaging_indirect"));
at(elt42_,attrs22_);
ad(elt41_,elt42_);
ad(elt38_,elt41_);
ad(elt35_,elt38_);
ad(elt24_,elt35_);
Element elt43_=el(_doc,"td");
Text txt4_=tx(_doc,"{d.priority}");
ad(elt43_,txt4_);
ad(elt24_,elt43_);
Element elt44_=el(_doc,"td");
Element elt45_=el(_doc,"input");
CustList<Attr> attrs23_=al(3);
attrs23_.add(at("c:varValue","d.selected"));
attrs23_.add(at("name","d.selected"));
attrs23_.add(at("type","checkbox"));
at(elt45_,attrs23_);
ad(elt44_,elt45_);
ad(elt24_,elt44_);
ad(elt23_,elt24_);
ad(elt22_,elt23_);
ad(elt1_,elt22_);
ad(elt0_,elt1_);
Element elt46_=el(_doc,"c:submit");
CustList<Attr> attrs24_=al(1);
attrs24_.add(at("message","msg_simulation,add"));
at(elt46_,attrs24_);
ad(elt0_,elt46_);
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
