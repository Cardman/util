package code.scripts.pages.cards;
import code.sml.*;
import code.util.*;
import code.util.ints.*;
final class PageTarotDetailsresults{
private PageTarotDetailsresults(){}
static Document build(){
FullDocument doc_ = DocumentBuilder.newXmlDocument(4);
build(doc_);
return doc_;
}
static void build(Document _doc){
Element elt0_=el(_doc,"html");
CustList<Attr> attrs0_=al(2);
attrs0_.add(at("c:bean","details"));
attrs0_.add(at("xmlns:c","javahtml"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"head");
Element elt2_=el(_doc,"title");
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg,results"));
at(elt3_,attrs1_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
Element elt4_=el(_doc,"link");
CustList<Attr> attrs2_=al(3);
attrs2_.add(at("href","resources_cards/css/tarot.css"));
attrs2_.add(at("rel","stylesheet"));
attrs2_.add(at("type","text/css"));
at(elt4_,attrs2_);
ad(elt1_,elt4_);
ad(elt0_,elt1_);
Element elt5_=el(_doc,"body");
build0(elt5_,_doc);
build1(elt5_,_doc);
ad(elt0_,elt5_);
_doc.appendChild(elt0_);
}
static void build0(Element _body,Document _doc){
Element elt0_=el(_doc,"c:if");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("condition","playClassicGame()"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"h1");
Element elt2_=el(_doc,"c:message");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("value","msg,classic_bid"));
at(elt2_,attrs1_);
ad(elt1_,elt2_);
ad(elt0_,elt1_);
Element elt3_=el(_doc,"p");
Element elt4_=el(_doc,"c:message");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("value","msg,classic_base"));
at(elt4_,attrs2_);
ad(elt3_,elt4_);
Text txt0_=tx(_doc,"{basePoints}");
ad(elt3_,txt0_);
ad(elt0_,elt3_);
Element elt5_=el(_doc,"p");
Element elt6_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg,classic_small"));
at(elt6_,attrs3_);
ad(elt5_,elt6_);
Text txt1_=tx(_doc,"{playerSmall}");
ad(elt5_,txt1_);
ad(elt0_,elt5_);
Element elt7_=el(_doc,"p");
Element elt8_=el(_doc,"c:message");
CustList<Attr> attrs4_=al(1);
attrs4_.add(at("value","msg,classic_diff"));
at(elt8_,attrs4_);
ad(elt7_,elt8_);
Text txt2_=tx(_doc,"{differenceScoreTaker}");
ad(elt7_,txt2_);
ad(elt0_,elt7_);
Element elt9_=el(_doc,"p");
Element elt10_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg,classic_rate"));
at(elt10_,attrs5_);
ad(elt9_,elt10_);
Text txt3_=tx(_doc,"{rate}");
ad(elt9_,txt3_);
ad(elt0_,elt9_);
Element elt11_=el(_doc,"p");
Element elt12_=el(_doc,"c:message");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("value","msg,classic_score_taker"));
at(elt12_,attrs6_);
Element elt13_=el(_doc,"param");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","basePoints"));
at(elt13_,attrs7_);
ad(elt12_,elt13_);
Element elt14_=el(_doc,"param");
CustList<Attr> attrs8_=al(1);
attrs8_.add(at("value","small"));
at(elt14_,attrs8_);
ad(elt12_,elt14_);
Element elt15_=el(_doc,"param");
CustList<Attr> attrs9_=al(1);
attrs9_.add(at("value","differenceScoreTaker"));
at(elt15_,attrs9_);
ad(elt12_,elt15_);
Element elt16_=el(_doc,"param");
CustList<Attr> attrs10_=al(1);
attrs10_.add(at("value","rate"));
at(elt16_,attrs10_);
ad(elt12_,elt16_);
Element elt17_=el(_doc,"param");
CustList<Attr> attrs11_=al(1);
attrs11_.add(at("value","multipliedTmp"));
at(elt17_,attrs11_);
ad(elt12_,elt17_);
ad(elt11_,elt12_);
ad(elt0_,elt11_);
Element elt18_=el(_doc,"h1");
Element elt19_=el(_doc,"c:message");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("value","msg,classic_decl"));
at(elt19_,attrs12_);
ad(elt18_,elt19_);
ad(elt0_,elt18_);
Element elt20_=el(_doc,"ul");
Element elt21_=el(_doc,"c:for");
CustList<Attr> attrs13_=al(3);
attrs13_.add(at("var","l"));
attrs13_.add(at("list","linesDeclaring"));
attrs13_.add(at("className","cards.tarot.beans.SumDeclaringPlayer"));
at(elt21_,attrs13_);
Element elt22_=el(_doc,"li");
Element elt23_=el(_doc,"c:message");
CustList<Attr> attrs14_=al(1);
attrs14_.add(at("value","msg,classic_decl_player"));
at(elt23_,attrs14_);
Element elt24_=el(_doc,"param");
CustList<Attr> attrs15_=al(1);
attrs15_.add(at("value","l.nickname"));
at(elt24_,attrs15_);
ad(elt23_,elt24_);
Element elt25_=el(_doc,"param");
CustList<Attr> attrs16_=al(1);
attrs16_.add(at("value","l.statut"));
at(elt25_,attrs16_);
ad(elt23_,elt25_);
ad(elt22_,elt23_);
Element elt26_=el(_doc,"br");
ad(elt22_,elt26_);
Element elt27_=el(_doc,"ul");
Element elt28_=el(_doc,"c:for");
CustList<Attr> attrs17_=al(5);
attrs17_.add(at("key","h"));
attrs17_.add(at("value","p"));
attrs17_.add(at("map","l.handfuls"));
attrs17_.add(at("keyClassName","java.lang.String"));
attrs17_.add(at("varClassName","java.lang.Short"));
at(elt28_,attrs17_);
Element elt29_=el(_doc,"li");
Text txt4_=tx(_doc,"{h} : {p}");
ad(elt29_,txt4_);
ad(elt28_,elt29_);
ad(elt27_,elt28_);
Element elt30_=el(_doc,"c:for");
CustList<Attr> attrs18_=al(5);
attrs18_.add(at("key","m"));
attrs18_.add(at("value","p"));
attrs18_.add(at("map","l.miseres"));
attrs18_.add(at("keyClassName","java.lang.String"));
attrs18_.add(at("varClassName","java.lang.Short"));
at(elt30_,attrs18_);
Element elt31_=el(_doc,"li");
Text txt5_=tx(_doc,"{m} : {p}");
ad(elt31_,txt5_);
ad(elt30_,elt31_);
ad(elt27_,elt30_);
Element elt32_=el(_doc,"li");
Element elt33_=el(_doc,"c:message");
CustList<Attr> attrs19_=al(1);
attrs19_.add(at("value","msg,sum"));
at(elt33_,attrs19_);
ad(elt32_,elt33_);
Text txt6_=tx(_doc,"{l.sum}");
ad(elt32_,txt6_);
ad(elt27_,elt32_);
ad(elt22_,elt27_);
ad(elt21_,elt22_);
ad(elt20_,elt21_);
Element elt34_=el(_doc,"li");
Element elt35_=el(_doc,"c:message");
CustList<Attr> attrs20_=al(1);
attrs20_.add(at("value","msg,classic_sum_player"));
at(elt35_,attrs20_);
ad(elt34_,elt35_);
Text txt7_=tx(_doc,"{sumPlayers}");
ad(elt34_,txt7_);
ad(elt20_,elt34_);
ad(elt0_,elt20_);
Element elt36_=el(_doc,"br");
ad(elt0_,elt36_);
Element elt37_=el(_doc,"h1");
Element elt38_=el(_doc,"c:message");
CustList<Attr> attrs21_=al(1);
attrs21_.add(at("value","msg,classic_addon"));
at(elt38_,attrs21_);
ad(elt37_,elt38_);
ad(elt0_,elt37_);
Element elt39_=el(_doc,"c:message");
CustList<Attr> attrs22_=al(1);
attrs22_.add(at("value","msg,classic_addon_att"));
at(elt39_,attrs22_);
ad(elt0_,elt39_);
Text txt8_=tx(_doc,"{additionnalBonusesAttack}");
ad(elt0_,txt8_);
Element elt40_=el(_doc,"br");
ad(elt0_,elt40_);
Element elt41_=el(_doc,"c:message");
CustList<Attr> attrs23_=al(1);
attrs23_.add(at("value","msg,classic_addon_def"));
at(elt41_,attrs23_);
ad(elt0_,elt41_);
Text txt9_=tx(_doc,"{additionnalBonusesDefense}");
ad(elt0_,txt9_);
Element elt42_=el(_doc,"br");
ad(elt0_,elt42_);
Element elt43_=el(_doc,"c:message");
CustList<Attr> attrs24_=al(1);
attrs24_.add(at("value","msg,classic_addon_sum"));
at(elt43_,attrs24_);
ad(elt0_,elt43_);
Text txt10_=tx(_doc,"{diffAttackDefenseBonuses}");
ad(elt0_,txt10_);
Element elt44_=el(_doc,"br");
ad(elt0_,elt44_);
Element elt45_=el(_doc,"table");
CustList<Attr> attrs25_=al(1);
attrs25_.add(at("border","1"));
at(elt45_,attrs25_);
Element elt46_=el(_doc,"caption");
Element elt47_=el(_doc,"c:message");
CustList<Attr> attrs26_=al(1);
attrs26_.add(at("value","msg,classic_rate_pl"));
at(elt47_,attrs26_);
ad(elt46_,elt47_);
ad(elt45_,elt46_);
Element elt48_=el(_doc,"thead");
Element elt49_=el(_doc,"tr");
Element elt50_=el(_doc,"td");
Element elt51_=el(_doc,"c:message");
CustList<Attr> attrs27_=al(1);
attrs27_.add(at("value","msg,player"));
at(elt51_,attrs27_);
ad(elt50_,elt51_);
ad(elt49_,elt50_);
Element elt52_=el(_doc,"td");
Element elt53_=el(_doc,"c:message");
CustList<Attr> attrs28_=al(1);
attrs28_.add(at("value","msg,rate"));
at(elt53_,attrs28_);
ad(elt52_,elt53_);
ad(elt49_,elt52_);
Element elt54_=el(_doc,"td");
Element elt55_=el(_doc,"c:message");
CustList<Attr> attrs29_=al(1);
attrs29_.add(at("value","msg,score"));
at(elt55_,attrs29_);
ad(elt54_,elt55_);
ad(elt49_,elt54_);
ad(elt48_,elt49_);
ad(elt45_,elt48_);
Element elt56_=el(_doc,"tbody");
Element elt57_=el(_doc,"c:for");
CustList<Attr> attrs30_=al(3);
attrs30_.add(at("var","l"));
attrs30_.add(at("list","playersScores"));
attrs30_.add(at("className","cards.tarot.beans.ScoresPlayers"));
at(elt57_,attrs30_);
Element elt58_=el(_doc,"tr");
Element elt59_=el(_doc,"td");
Text txt11_=tx(_doc,"{l.nickname}");
ad(elt59_,txt11_);
ad(elt58_,elt59_);
Element elt60_=el(_doc,"td");
Text txt12_=tx(_doc,"{l.rate}");
ad(elt60_,txt12_);
ad(elt58_,elt60_);
Element elt61_=el(_doc,"td");
Text txt13_=tx(_doc,"{l.score}");
ad(elt61_,txt13_);
ad(elt58_,elt61_);
ad(elt57_,elt58_);
ad(elt56_,elt57_);
ad(elt45_,elt56_);
ad(elt0_,elt45_);
ad(_body,elt0_);
}
static void build1(Element _body,Document _doc){
Element elt0_=el(_doc,"c:if");
CustList<Attr> attrs0_=al(1);
attrs0_.add(at("condition","playVariantModeGame()"));
at(elt0_,attrs0_);
Element elt1_=el(_doc,"table");
CustList<Attr> attrs1_=al(1);
attrs1_.add(at("border","1"));
at(elt1_,attrs1_);
Element elt2_=el(_doc,"caption");
Element elt3_=el(_doc,"c:message");
CustList<Attr> attrs2_=al(1);
attrs2_.add(at("value","msg,variant_table_1"));
at(elt3_,attrs2_);
ad(elt2_,elt3_);
ad(elt1_,elt2_);
Element elt4_=el(_doc,"thead");
Element elt5_=el(_doc,"tr");
Element elt6_=el(_doc,"td");
Element elt7_=el(_doc,"c:message");
CustList<Attr> attrs3_=al(1);
attrs3_.add(at("value","msg,variant_table_1_1"));
at(elt7_,attrs3_);
ad(elt6_,elt7_);
ad(elt5_,elt6_);
Element elt8_=el(_doc,"td");
Element elt9_=el(_doc,"c:message");
CustList<Attr> attrs4_=al(1);
attrs4_.add(at("value","msg,variant_table_1_2"));
at(elt9_,attrs4_);
ad(elt8_,elt9_);
ad(elt5_,elt8_);
Element elt10_=el(_doc,"td");
Element elt11_=el(_doc,"c:message");
CustList<Attr> attrs5_=al(1);
attrs5_.add(at("value","msg,variant_table_1_3"));
at(elt11_,attrs5_);
ad(elt10_,elt11_);
ad(elt5_,elt10_);
Element elt12_=el(_doc,"td");
Element elt13_=el(_doc,"c:message");
CustList<Attr> attrs6_=al(1);
attrs6_.add(at("value","msg,variant_table_1_4"));
at(elt13_,attrs6_);
ad(elt12_,elt13_);
ad(elt5_,elt12_);
Element elt14_=el(_doc,"td");
Element elt15_=el(_doc,"c:message");
CustList<Attr> attrs7_=al(1);
attrs7_.add(at("value","msg,variant_table_1_5"));
at(elt15_,attrs7_);
ad(elt14_,elt15_);
ad(elt5_,elt14_);
Element elt16_=el(_doc,"td");
Element elt17_=el(_doc,"c:message");
CustList<Attr> attrs8_=al(1);
attrs8_.add(at("value","msg,variant_table_1_6"));
at(elt17_,attrs8_);
ad(elt16_,elt17_);
ad(elt5_,elt16_);
ad(elt4_,elt5_);
ad(elt1_,elt4_);
Element elt18_=el(_doc,"tbody");
Element elt19_=el(_doc,"c:for");
CustList<Attr> attrs9_=al(3);
attrs9_.add(at("var","l"));
attrs9_.add(at("list","orderedPlayers"));
attrs9_.add(at("className","cards.tarot.beans.RankingPlayerVariantGame"));
at(elt19_,attrs9_);
Element elt20_=el(_doc,"tr");
Element elt21_=el(_doc,"td");
Text txt0_=tx(_doc,"{l.nickname}");
ad(elt21_,txt0_);
ad(elt20_,elt21_);
Element elt22_=el(_doc,"td");
Text txt1_=tx(_doc,"{l.positionDiff}");
ad(elt22_,txt1_);
ad(elt20_,elt22_);
Element elt23_=el(_doc,"td");
Text txt2_=tx(_doc,"{l.positionOudlers}");
ad(elt23_,txt2_);
ad(elt20_,elt23_);
Element elt24_=el(_doc,"td");
Text txt3_=tx(_doc,"{l.positionCharacters}");
ad(elt24_,txt3_);
ad(elt20_,elt24_);
Element elt25_=el(_doc,"td");
Text txt4_=tx(_doc,"{l.positionStrengthCharacters}");
ad(elt25_,txt4_);
ad(elt20_,elt25_);
Element elt26_=el(_doc,"td");
Text txt5_=tx(_doc,"{l.finalPosition}");
ad(elt26_,txt5_);
ad(elt20_,elt26_);
ad(elt19_,elt20_);
ad(elt18_,elt19_);
ad(elt1_,elt18_);
ad(elt0_,elt1_);
Element elt27_=el(_doc,"table");
CustList<Attr> attrs10_=al(1);
attrs10_.add(at("border","1"));
at(elt27_,attrs10_);
Element elt28_=el(_doc,"caption");
Element elt29_=el(_doc,"c:message");
CustList<Attr> attrs11_=al(1);
attrs11_.add(at("value","msg,variant_table_2"));
at(elt29_,attrs11_);
ad(elt28_,elt29_);
ad(elt27_,elt28_);
Element elt30_=el(_doc,"thead");
Element elt31_=el(_doc,"tr");
Element elt32_=el(_doc,"td");
Element elt33_=el(_doc,"c:message");
CustList<Attr> attrs12_=al(1);
attrs12_.add(at("value","msg,variant_table_2_1"));
at(elt33_,attrs12_);
ad(elt32_,elt33_);
ad(elt31_,elt32_);
Element elt34_=el(_doc,"td");
Element elt35_=el(_doc,"c:message");
CustList<Attr> attrs13_=al(1);
attrs13_.add(at("value","msg,variant_table_2_2"));
at(elt35_,attrs13_);
ad(elt34_,elt35_);
ad(elt31_,elt34_);
Element elt36_=el(_doc,"td");
Element elt37_=el(_doc,"c:message");
CustList<Attr> attrs14_=al(1);
attrs14_.add(at("value","msg,variant_table_2_3"));
at(elt37_,attrs14_);
ad(elt36_,elt37_);
ad(elt31_,elt36_);
Element elt38_=el(_doc,"td");
Element elt39_=el(_doc,"c:message");
CustList<Attr> attrs15_=al(1);
attrs15_.add(at("value","msg,variant_table_2_4"));
at(elt39_,attrs15_);
ad(elt38_,elt39_);
ad(elt31_,elt38_);
Element elt40_=el(_doc,"td");
Element elt41_=el(_doc,"c:message");
CustList<Attr> attrs16_=al(1);
attrs16_.add(at("value","msg,variant_table_2_5"));
at(elt41_,attrs16_);
ad(elt40_,elt41_);
ad(elt31_,elt40_);
Element elt42_=el(_doc,"td");
Element elt43_=el(_doc,"c:message");
CustList<Attr> attrs17_=al(1);
attrs17_.add(at("value","msg,variant_table_2_6"));
at(elt43_,attrs17_);
ad(elt42_,elt43_);
ad(elt31_,elt42_);
ad(elt30_,elt31_);
ad(elt27_,elt30_);
Element elt44_=el(_doc,"tbody");
Element elt45_=el(_doc,"c:for");
CustList<Attr> attrs18_=al(3);
attrs18_.add(at("var","l"));
attrs18_.add(at("list","pointsPlayers"));
attrs18_.add(at("className","cards.tarot.beans.PointsPlayerVariantGame"));
at(elt45_,attrs18_);
Element elt46_=el(_doc,"tr");
Element elt47_=el(_doc,"td");
Text txt6_=tx(_doc,"{l.nickname}");
ad(elt47_,txt6_);
ad(elt46_,elt47_);
Element elt48_=el(_doc,"td");
Text txt7_=tx(_doc,"{l.pointsTricks}");
ad(elt48_,txt7_);
ad(elt46_,elt48_);
Element elt49_=el(_doc,"td");
Text txt8_=tx(_doc,"{l.minimumPoints}");
ad(elt49_,txt8_);
ad(elt46_,elt49_);
Element elt50_=el(_doc,"td");
Text txt9_=tx(_doc,"{l.differenceScore}");
ad(elt50_,txt9_);
ad(elt46_,elt50_);
Element elt51_=el(_doc,"td");
Text txt10_=tx(_doc,"{l.rate}");
ad(elt51_,txt10_);
ad(elt46_,elt51_);
Element elt52_=el(_doc,"td");
Text txt11_=tx(_doc,"{l.score}");
ad(elt52_,txt11_);
ad(elt46_,elt52_);
ad(elt45_,elt46_);
ad(elt44_,elt45_);
ad(elt27_,elt44_);
ad(elt0_,elt27_);
Element elt53_=el(_doc,"h1");
Element elt54_=el(_doc,"c:message");
CustList<Attr> attrs19_=al(1);
attrs19_.add(at("value","msg,variant_decl"));
at(elt54_,attrs19_);
ad(elt53_,elt54_);
ad(elt0_,elt53_);
Element elt55_=el(_doc,"ul");
Element elt56_=el(_doc,"c:for");
CustList<Attr> attrs20_=al(3);
attrs20_.add(at("var","l"));
attrs20_.add(at("list","linesDeclaring"));
attrs20_.add(at("className","cards.tarot.beans.SumDeclaringPlayer"));
at(elt56_,attrs20_);
Element elt57_=el(_doc,"li");
Element elt58_=el(_doc,"c:message");
CustList<Attr> attrs21_=al(1);
attrs21_.add(at("value","msg,variant_decl_pl"));
at(elt58_,attrs21_);
Element elt59_=el(_doc,"param");
CustList<Attr> attrs22_=al(1);
attrs22_.add(at("value","l.nickname"));
at(elt59_,attrs22_);
ad(elt58_,elt59_);
ad(elt57_,elt58_);
Element elt60_=el(_doc,"br");
ad(elt57_,elt60_);
Element elt61_=el(_doc,"ul");
Element elt62_=el(_doc,"c:for");
CustList<Attr> attrs23_=al(5);
attrs23_.add(at("key","h"));
attrs23_.add(at("value","p"));
attrs23_.add(at("map","l.handfuls"));
attrs23_.add(at("keyClassName","java.lang.String"));
attrs23_.add(at("varClassName","java.lang.Short"));
at(elt62_,attrs23_);
Element elt63_=el(_doc,"li");
Text txt12_=tx(_doc,"{h}");
ad(elt63_,txt12_);
ad(elt62_,elt63_);
ad(elt61_,elt62_);
Element elt64_=el(_doc,"c:for");
CustList<Attr> attrs24_=al(5);
attrs24_.add(at("key","m"));
attrs24_.add(at("value","p"));
attrs24_.add(at("map","l.miseres"));
attrs24_.add(at("keyClassName","java.lang.String"));
attrs24_.add(at("varClassName","java.lang.Short"));
at(elt64_,attrs24_);
Element elt65_=el(_doc,"li");
Text txt13_=tx(_doc,"{m}");
ad(elt65_,txt13_);
ad(elt64_,elt65_);
ad(elt61_,elt64_);
ad(elt57_,elt61_);
ad(elt56_,elt57_);
ad(elt55_,elt56_);
ad(elt0_,elt55_);
Element elt66_=el(_doc,"h1");
Element elt67_=el(_doc,"c:message");
CustList<Attr> attrs25_=al(1);
attrs25_.add(at("value","msg,variant_add"));
at(elt67_,attrs25_);
ad(elt66_,elt67_);
ad(elt0_,elt66_);
Element elt68_=el(_doc,"table");
CustList<Attr> attrs26_=al(1);
attrs26_.add(at("border","1"));
at(elt68_,attrs26_);
Element elt69_=el(_doc,"caption");
Element elt70_=el(_doc,"c:message");
CustList<Attr> attrs27_=al(1);
attrs27_.add(at("value","msg,variant_add_pl"));
at(elt70_,attrs27_);
ad(elt69_,elt70_);
ad(elt68_,elt69_);
Element elt71_=el(_doc,"thead");
Element elt72_=el(_doc,"tr");
Element elt73_=el(_doc,"td");
Element elt74_=el(_doc,"c:message");
CustList<Attr> attrs28_=al(1);
attrs28_.add(at("value","msg,variant_add_pl_1"));
at(elt74_,attrs28_);
ad(elt73_,elt74_);
ad(elt72_,elt73_);
Element elt75_=el(_doc,"td");
Element elt76_=el(_doc,"c:message");
CustList<Attr> attrs29_=al(1);
attrs29_.add(at("value","msg,variant_add_pl_2"));
at(elt76_,attrs29_);
ad(elt75_,elt76_);
ad(elt72_,elt75_);
ad(elt71_,elt72_);
ad(elt68_,elt71_);
Element elt77_=el(_doc,"tbody");
Element elt78_=el(_doc,"c:for");
CustList<Attr> attrs30_=al(3);
attrs30_.add(at("var","l"));
attrs30_.add(at("list","bonuses"));
attrs30_.add(at("className","cards.tarot.beans.BonusesPlayers"));
at(elt78_,attrs30_);
Element elt79_=el(_doc,"tr");
Element elt80_=el(_doc,"td");
Text txt14_=tx(_doc,"{l.nickname}");
ad(elt80_,txt14_);
ad(elt79_,elt80_);
Element elt81_=el(_doc,"td");
Text txt15_=tx(_doc,"{l.bonus}");
ad(elt81_,txt15_);
ad(elt79_,elt81_);
ad(elt78_,elt79_);
ad(elt77_,elt78_);
ad(elt68_,elt77_);
ad(elt0_,elt68_);
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
