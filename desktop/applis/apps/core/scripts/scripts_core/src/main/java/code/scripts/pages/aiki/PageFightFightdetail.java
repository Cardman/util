package code.scripts.pages.aiki;
import code.sml.*;
import code.util.*;
import code.util.ints.*;
final class PageFightFightdetail{
private PageFightFightdetail(){}
static Document build(){
FullDocument doc_ = DocumentBuilder.newXmlDocument(4);
build(doc_);
return doc_;
}
static void build(Document _doc){
Element elt0_=el(_doc,"html");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("xmlns:c","javahtml"));
attrs0_.add(at("c:bean","fight_detail"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"head");
Element elt2_=el(_doc,"title");
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_fight,title_detail_fight"));
at(elt3_,attrs1_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
Element elt4_=el(_doc,"link");
CustList<Attr> attrs2_=al(3);
attrs2_.add(at("href","web_fight/css/fight.css"));
attrs2_.add(at("rel","stylesheet"));
attrs2_.add(at("type","text/css"));
at(elt4_,attrs2_);
ad(elt1_,elt4_);
ad(elt0_,elt1_);
Element elt5_=el(_doc,"body");
build0(elt5_,_doc);
build1(elt5_,_doc);
build2(elt5_,_doc);
build3(elt5_,_doc);
build4(elt5_,_doc);
build5(elt5_,_doc);
build6(elt5_,_doc);
build7(elt5_,_doc);
ad(elt0_,elt5_);
_doc.appendChild(elt0_);
}
static void build0(Element _body,Document _doc){
Element elt0_=el(_doc,"a");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("c:command","web_fight/html/fight.html"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_team,fight"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
ad(_body,elt0_);
Element elt2_=el(_doc,"br");
ad(_body,elt2_);
}
static void build1(Element _body,Document _doc){
Element elt0_=el(_doc,"a");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("c:command","web_fight/html/fightdetail.html"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_fight,refresh"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
ad(_body,elt0_);
Element elt2_=el(_doc,"br");
ad(_body,elt2_);
}
static void build2(Element _body,Document _doc){
Element elt0_=el(_doc,"c:if");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("condition","!sortedFighters.isEmpty()"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_fight,sorted_fighters_fct_choices"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
Element elt2_=el(_doc,"ul");
Element elt3_=el(_doc,"c:for");
CustList<Attr> attrs2_=al(2);
attrs2_.add(at("list","sortedFighters"));
attrs2_.add(at("var","f"));
at(elt3_,attrs2_);
Element elt4_=el(_doc,"li");
Text txt0_=tx(_doc,"{getFighter(([f]))}");
ad(elt4_,txt0_);
ad(elt3_,elt4_);
ad(elt2_,elt3_);
ad(elt0_,elt2_);
ad(_body,elt0_);
}
static void build3(Element _body,Document _doc){
Element elt0_=el(_doc,"c:if");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("condition","!sortedFightersWildFight.isEmpty()"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_fight,sorted_fighters_fct_choices_wild"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
Element elt2_=el(_doc,"c:for");
CustList<Attr> attrs2_=al(5);
attrs2_.add(at("key","m"));
attrs2_.add(at("map","sortedFightersWildFight"));
attrs2_.add(at("value","f"));
attrs2_.add(at("keyClassName","java.lang.String"));
attrs2_.add(at("varClassName","ls"));
at(elt2_,attrs2_);
Text txt0_=tx(_doc,"{m}");
ad(elt2_,txt0_);
Element elt3_=el(_doc,"br");
ad(elt2_,elt3_);
Element elt4_=el(_doc,"ul");
Element elt5_=el(_doc,"c:for");
CustList<Attr> attrs3_=al(2);
attrs3_.add(at("list","f"));
attrs3_.add(at("var","p"));
at(elt5_,attrs3_);
Element elt6_=el(_doc,"li");
Text txt1_=tx(_doc,"{getFighterWildFight(([m]),([p]))}");
ad(elt6_,txt1_);
ad(elt5_,elt6_);
ad(elt4_,elt5_);
ad(elt2_,elt4_);
ad(elt0_,elt2_);
ad(_body,elt0_);
}
static void build4(Element _body,Document _doc){
Element elt0_=el(_doc,"c:message");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("value","msg_fight,damage_fct_choices"));
at(elt0_,attrs0_);
ad(_body,elt0_);
}
static void build5(Element _body,Document _doc){
Element elt0_=el(_doc,"table");
Element elt1_=el(_doc,"thead");
Element elt2_=el(_doc,"tr");
Element elt3_=el(_doc,"th");
Element elt4_=el(_doc,"c:message");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("value","msg_fight,damage_fct_choices_key_one"));
at(elt4_,attrs0_);
ad(elt3_,elt4_);
ad(elt2_,elt3_);
Element elt5_=el(_doc,"th");
Element elt6_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_fight,damage_fct_choices_key_two"));
at(elt6_,attrs1_);
ad(elt5_,elt6_);
ad(elt2_,elt5_);
Element elt7_=el(_doc,"th");
Element elt8_=el(_doc,"c:message");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("value","msg_fight,damage_fct_choices_key_three"));
at(elt8_,attrs2_);
ad(elt7_,elt8_);
ad(elt2_,elt7_);
Element elt9_=el(_doc,"th");
Element elt10_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg_fight,damage_fct_choices_damage"));
at(elt10_,attrs3_);
ad(elt9_,elt10_);
ad(elt2_,elt9_);
ad(elt1_,elt2_);
ad(elt0_,elt1_);
Element elt11_=el(_doc,"tbody");
Element elt12_=el(_doc,"c:for");
CustList<Attr> attrs4_=al(3);
attrs4_.add(at("className","aiki.beans.facade.fight.KeyHypothesis"));
attrs4_.add(at("list","damage"));
attrs4_.add(at("var","d"));
at(elt12_,attrs4_);
Element elt13_=el(_doc,"tr");
Element elt14_=el(_doc,"td");
Text txt0_=tx(_doc,"{d.getPlayerPokemon()}");
ad(elt14_,txt0_);
ad(elt13_,elt14_);
Element elt15_=el(_doc,"td");
Text txt1_=tx(_doc,"{d.getMove()}");
ad(elt15_,txt1_);
ad(elt13_,elt15_);
Element elt16_=el(_doc,"td");
Text txt2_=tx(_doc,"{d.getTargetPokemon()}");
ad(elt16_,txt2_);
Element elt17_=el(_doc,"c:if");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("condition","d.isBelongsToUser()"));
at(elt17_,attrs5_);
Element elt18_=el(_doc,"c:message");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("value","msg_fight,damage_fct_choices_player"));
at(elt18_,attrs6_);
ad(elt17_,elt18_);
ad(elt16_,elt17_);
Element elt19_=el(_doc,"c:if");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("condition","!d.isBelongsToUser()"));
at(elt19_,attrs7_);
Element elt20_=el(_doc,"c:message");
CustList<Attr> attrs8_=al(1);
attrs8_.add(at("value","msg_fight,damage_fct_choices_foe"));
at(elt20_,attrs8_);
ad(elt19_,elt20_);
ad(elt16_,elt19_);
Text txt3_=tx(_doc,"{d.getNumberTarget()}");
ad(elt16_,txt3_);
ad(elt13_,elt16_);
Element elt21_=el(_doc,"td");
Text txt4_=tx(_doc,"{d.getDamage()}");
ad(elt21_,txt4_);
ad(elt13_,elt21_);
ad(elt12_,elt13_);
ad(elt11_,elt12_);
ad(elt0_,elt11_);
ad(_body,elt0_);
}
static void build6(Element _body,Document _doc){
Element elt0_=el(_doc,"c:if");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("condition","!allyChoice.isEmpty()"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_fight,ally_choices"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
Element elt2_=el(_doc,"table");
Element elt3_=el(_doc,"thead");
Element elt4_=el(_doc,"tr");
Element elt5_=el(_doc,"th");
Element elt6_=el(_doc,"c:message");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("value","msg_fight,ally_choices_key"));
at(elt6_,attrs2_);
ad(elt5_,elt6_);
ad(elt4_,elt5_);
Element elt7_=el(_doc,"th");
Element elt8_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg_fight,ally_choices_key_team"));
at(elt8_,attrs3_);
ad(elt7_,elt8_);
ad(elt4_,elt7_);
Element elt9_=el(_doc,"th");
Element elt10_=el(_doc,"c:message");
CustList<Attr> attrs4_=al(1);
attrs4_.add(at("value","msg_fight,ally_choices_key_place"));
at(elt10_,attrs4_);
ad(elt9_,elt10_);
ad(elt4_,elt9_);
Element elt11_=el(_doc,"th");
Element elt12_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg_fight,ally_choices_key_name"));
at(elt12_,attrs5_);
ad(elt11_,elt12_);
ad(elt4_,elt11_);
Element elt13_=el(_doc,"th");
Element elt14_=el(_doc,"c:message");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("value","msg_fight,ally_choices_value"));
at(elt14_,attrs6_);
ad(elt13_,elt14_);
ad(elt4_,elt13_);
Element elt15_=el(_doc,"th");
Element elt16_=el(_doc,"c:message");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","msg_fight,ally_choices_value_team"));
at(elt16_,attrs7_);
ad(elt15_,elt16_);
ad(elt4_,elt15_);
Element elt17_=el(_doc,"th");
Element elt18_=el(_doc,"c:message");
CustList<Attr> attrs8_=al(1);
attrs8_.add(at("value","msg_fight,ally_choices_value_place"));
at(elt18_,attrs8_);
ad(elt17_,elt18_);
ad(elt4_,elt17_);
Element elt19_=el(_doc,"th");
Element elt20_=el(_doc,"c:message");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("value","msg_fight,ally_choices_value_name"));
at(elt20_,attrs9_);
ad(elt19_,elt20_);
ad(elt4_,elt19_);
ad(elt3_,elt4_);
ad(elt2_,elt3_);
Element elt21_=el(_doc,"tbody");
Element elt22_=el(_doc,"c:for");
CustList<Attr> attrs10_=al(5);
attrs10_.add(at("key","m"));
attrs10_.add(at("keyClassName","aiki.game.fight.util.MoveTarget"));
attrs10_.add(at("map","allyChoice"));
attrs10_.add(at("value","a"));
attrs10_.add(at("varClassName","aiki.game.fight.util.MoveTarget"));
at(elt22_,attrs10_);
Element elt23_=el(_doc,"tr");
Element elt24_=el(_doc,"td");
Text txt0_=tx(_doc,"{m.getMove()}");
ad(elt24_,txt0_);
ad(elt23_,elt24_);
Element elt25_=el(_doc,"c:if");
CustList<Attr> attrs11_=al(1);
attrs11_.add(at("condition","isFoeTargetChoiceTeam(([m]))"));
at(elt25_,attrs11_);
Element elt26_=el(_doc,"td");
Element elt27_=el(_doc,"c:message");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("value","msg_fight,ally_choices_foe"));
at(elt27_,attrs12_);
ad(elt26_,elt27_);
ad(elt25_,elt26_);
ad(elt23_,elt25_);
Element elt28_=el(_doc,"c:if");
CustList<Attr> attrs13_=al(1);
attrs13_.add(at("condition","!isFoeTargetChoiceTeam(([m]))"));
at(elt28_,attrs13_);
Element elt29_=el(_doc,"td");
Element elt30_=el(_doc,"c:message");
CustList<Attr> attrs14_=al(1);
attrs14_.add(at("value","msg_fight,ally_choices_player"));
at(elt30_,attrs14_);
ad(elt29_,elt30_);
ad(elt28_,elt29_);
ad(elt23_,elt28_);
Element elt31_=el(_doc,"c:if");
CustList<Attr> attrs15_=al(1);
attrs15_.add(at("condition","!isBackTargetChoiceTeam(([m]))"));
at(elt31_,attrs15_);
Element elt32_=el(_doc,"td");
Text txt1_=tx(_doc,"{m.getTarget().getPosition()}");
ad(elt32_,txt1_);
ad(elt31_,elt32_);
Element elt33_=el(_doc,"td");
Text txt2_=tx(_doc,"{getTargetNameAllyChoiceCondition(([m]))}");
ad(elt33_,txt2_);
ad(elt31_,elt33_);
ad(elt23_,elt31_);
Element elt34_=el(_doc,"c:if");
CustList<Attr> attrs16_=al(1);
attrs16_.add(at("condition","isBackTargetChoiceTeam(([m]))"));
at(elt34_,attrs16_);
Element elt35_=el(_doc,"td");
Element elt36_=el(_doc,"c:message");
CustList<Attr> attrs17_=al(1);
attrs17_.add(at("value","msg_fight,ally_choices_no"));
at(elt36_,attrs17_);
ad(elt35_,elt36_);
ad(elt34_,elt35_);
Element elt37_=el(_doc,"td");
Element elt38_=el(_doc,"c:message");
CustList<Attr> attrs18_=al(1);
attrs18_.add(at("value","msg_fight,ally_choices_no"));
at(elt38_,attrs18_);
ad(elt37_,elt38_);
ad(elt34_,elt37_);
ad(elt23_,elt34_);
Element elt39_=el(_doc,"td");
Text txt3_=tx(_doc,"{a.getMove()}");
ad(elt39_,txt3_);
ad(elt23_,elt39_);
Element elt40_=el(_doc,"c:if");
CustList<Attr> attrs19_=al(1);
attrs19_.add(at("condition","isFoeTargetTeam(([m]))"));
at(elt40_,attrs19_);
Element elt41_=el(_doc,"td");
Element elt42_=el(_doc,"c:message");
CustList<Attr> attrs20_=al(1);
attrs20_.add(at("value","msg_fight,ally_choices_foe"));
at(elt42_,attrs20_);
ad(elt41_,elt42_);
ad(elt40_,elt41_);
ad(elt23_,elt40_);
Element elt43_=el(_doc,"c:if");
CustList<Attr> attrs21_=al(1);
attrs21_.add(at("condition","!isFoeTargetTeam(([m]))"));
at(elt43_,attrs21_);
Element elt44_=el(_doc,"td");
Element elt45_=el(_doc,"c:message");
CustList<Attr> attrs22_=al(1);
attrs22_.add(at("value","msg_fight,ally_choices_player"));
at(elt45_,attrs22_);
ad(elt44_,elt45_);
ad(elt43_,elt44_);
ad(elt23_,elt43_);
Element elt46_=el(_doc,"c:if");
CustList<Attr> attrs23_=al(1);
attrs23_.add(at("condition","!isBackTargetTeam(([m]))"));
at(elt46_,attrs23_);
Element elt47_=el(_doc,"td");
Text txt4_=tx(_doc,"{a.getTarget().getPosition()}");
ad(elt47_,txt4_);
ad(elt46_,elt47_);
Element elt48_=el(_doc,"td");
Text txt5_=tx(_doc,"{getTargetNameAllyChoice(([m]))}");
ad(elt48_,txt5_);
ad(elt46_,elt48_);
ad(elt23_,elt46_);
Element elt49_=el(_doc,"c:if");
CustList<Attr> attrs24_=al(1);
attrs24_.add(at("condition","isBackTargetTeam(([m]))"));
at(elt49_,attrs24_);
Element elt50_=el(_doc,"td");
Element elt51_=el(_doc,"c:message");
CustList<Attr> attrs25_=al(1);
attrs25_.add(at("value","msg_fight,ally_choices_no"));
at(elt51_,attrs25_);
ad(elt50_,elt51_);
ad(elt49_,elt50_);
Element elt52_=el(_doc,"td");
Element elt53_=el(_doc,"c:message");
CustList<Attr> attrs26_=al(1);
attrs26_.add(at("value","msg_fight,ally_choices_no"));
at(elt53_,attrs26_);
ad(elt52_,elt53_);
ad(elt49_,elt52_);
ad(elt23_,elt49_);
ad(elt22_,elt23_);
ad(elt21_,elt22_);
ad(elt2_,elt21_);
ad(elt0_,elt2_);
ad(_body,elt0_);
}
static void build7(Element _body,Document _doc){
Element elt0_=el(_doc,"c:if");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("condition","!foeChoices.isEmpty()"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg_fight,foe_choices"));
at(elt1_,attrs1_);
ad(elt0_,elt1_);
Element elt2_=el(_doc,"table");
Element elt3_=el(_doc,"thead");
Element elt4_=el(_doc,"tr");
Element elt5_=el(_doc,"th");
Element elt6_=el(_doc,"c:message");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("value","msg_fight,foe_choices_key"));
at(elt6_,attrs2_);
ad(elt5_,elt6_);
ad(elt4_,elt5_);
Element elt7_=el(_doc,"th");
Element elt8_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg_fight,foe_choices_key_name"));
at(elt8_,attrs3_);
ad(elt7_,elt8_);
ad(elt4_,elt7_);
Element elt9_=el(_doc,"th");
Element elt10_=el(_doc,"c:message");
CustList<Attr> attrs4_=al(1);
attrs4_.add(at("value","msg_fight,foe_choices_value"));
at(elt10_,attrs4_);
ad(elt9_,elt10_);
ad(elt4_,elt9_);
Element elt11_=el(_doc,"th");
Element elt12_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg_fight,foe_choices_value_team"));
at(elt12_,attrs5_);
ad(elt11_,elt12_);
ad(elt4_,elt11_);
Element elt13_=el(_doc,"th");
Element elt14_=el(_doc,"c:message");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("value","msg_fight,foe_choices_value_place"));
at(elt14_,attrs6_);
ad(elt13_,elt14_);
ad(elt4_,elt13_);
Element elt15_=el(_doc,"th");
Element elt16_=el(_doc,"c:message");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","msg_fight,foe_choices_value_name"));
at(elt16_,attrs7_);
ad(elt15_,elt16_);
ad(elt4_,elt15_);
ad(elt3_,elt4_);
ad(elt2_,elt3_);
Element elt17_=el(_doc,"tbody");
Element elt18_=el(_doc,"c:for");
CustList<Attr> attrs8_=al(5);
attrs8_.add(at("key","p"));
attrs8_.add(at("map","foeChoices"));
attrs8_.add(at("value","a"));
attrs8_.add(at("keyClassName","java.lang.Byte"));
attrs8_.add(at("varClassName","aiki.game.fight.util.MoveTarget"));
at(elt18_,attrs8_);
Element elt19_=el(_doc,"tr");
Element elt20_=el(_doc,"td");
Text txt0_=tx(_doc,"{p}");
ad(elt20_,txt0_);
ad(elt19_,elt20_);
Element elt21_=el(_doc,"td");
Text txt1_=tx(_doc,"{getFoeFighterName(([p]))}");
ad(elt21_,txt1_);
ad(elt19_,elt21_);
Element elt22_=el(_doc,"td");
Text txt2_=tx(_doc,"{a.getMove()}");
ad(elt22_,txt2_);
ad(elt19_,elt22_);
Element elt23_=el(_doc,"c:if");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("condition","isChosenTarget(([p]))"));
at(elt23_,attrs9_);
Element elt24_=el(_doc,"c:if");
CustList<Attr> attrs10_=al(1);
attrs10_.add(at("condition","isFoeTargetChTeam(([p]))"));
at(elt24_,attrs10_);
Element elt25_=el(_doc,"td");
Element elt26_=el(_doc,"c:message");
CustList<Attr> attrs11_=al(1);
attrs11_.add(at("value","msg_fight,foe_choices_foe"));
at(elt26_,attrs11_);
ad(elt25_,elt26_);
ad(elt24_,elt25_);
ad(elt23_,elt24_);
Element elt27_=el(_doc,"c:if");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("condition","!isFoeTargetChTeam(([p]))"));
at(elt27_,attrs12_);
Element elt28_=el(_doc,"td");
Element elt29_=el(_doc,"c:message");
CustList<Attr> attrs13_=al(1);
attrs13_.add(at("value","msg_fight,foe_choices_player"));
at(elt29_,attrs13_);
ad(elt28_,elt29_);
ad(elt27_,elt28_);
ad(elt23_,elt27_);
Element elt30_=el(_doc,"td");
Text txt3_=tx(_doc,"{a.getTarget().getPosition()}");
ad(elt30_,txt3_);
ad(elt23_,elt30_);
Element elt31_=el(_doc,"td");
Text txt4_=tx(_doc,"{getTargetNameFoeChoice(([p]))}");
ad(elt31_,txt4_);
ad(elt23_,elt31_);
ad(elt19_,elt23_);
Element elt32_=el(_doc,"c:if");
CustList<Attr> attrs14_=al(1);
attrs14_.add(at("condition","!isChosenTarget(([p]))"));
at(elt32_,attrs14_);
Element elt33_=el(_doc,"td");
Element elt34_=el(_doc,"c:message");
CustList<Attr> attrs15_=al(1);
attrs15_.add(at("value","msg_fight,foe_choices_no"));
at(elt34_,attrs15_);
ad(elt33_,elt34_);
ad(elt32_,elt33_);
Element elt35_=el(_doc,"td");
Element elt36_=el(_doc,"c:message");
CustList<Attr> attrs16_=al(1);
attrs16_.add(at("value","msg_fight,foe_choices_no"));
at(elt36_,attrs16_);
ad(elt35_,elt36_);
ad(elt32_,elt35_);
Element elt37_=el(_doc,"td");
Element elt38_=el(_doc,"c:message");
CustList<Attr> attrs17_=al(1);
attrs17_.add(at("value","msg_fight,foe_choices_no"));
at(elt38_,attrs17_);
ad(elt37_,elt38_);
ad(elt32_,elt37_);
ad(elt19_,elt32_);
ad(elt18_,elt19_);
ad(elt17_,elt18_);
ad(elt2_,elt17_);
ad(elt0_,elt2_);
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
