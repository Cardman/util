package cards.gui.labels;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import cards.consts.CardChar;
import cards.consts.Suit;
import cards.facade.enumerations.GameEnum;
import cards.gui.dialogs.FileConst;
import cards.tarot.enumerations.CardTarot;
import code.images.ConverterBufferedImage;
import code.resources.ResourceFiles;
import code.stream.StreamTextFile;
import code.util.CustList;
import code.util.Numbers;
import code.util.consts.Constants;

public class GraphicTarotCard extends JLabel {
    static final String DEFAULT="Default";

    private CardTarot card;
    private boolean fullCard;
    private boolean peinte;
    private boolean peindreCarte=true;
    private BufferedImage bufferedImage;
    public GraphicTarotCard(CardTarot _pc, boolean _fullCard) {
        setHorizontalAlignment(SwingConstants.RIGHT);
        setVerticalAlignment(SwingConstants.TOP);
        peindreCarte=true;
        fullCard=_fullCard;
        card=_pc;
        String file_ = ResourceFiles.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()
                +StreamTextFile.SEPARATEUR+card.getImageFileName(FileConst.TXT_EXT));
        bufferedImage = ConverterBufferedImage.decodeToImage(file_);
        if (bufferedImage == null) {
            peinte=true;
        }
//        try {
//            String file_ = StreamTextFile.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage(), card.getImageFileName(FileConst.TXT_EXT));
//            bufferedImage = ConverterBufferedImage.decodeToImage(file_);
//            if (bufferedImage == null) {
//                throw new BadImageCardException();
//            }
//        } catch (Exception e_) {
//            e_.printStackTrace();
////            try{
////                bufferedImage = StreamImageFile.resourceBufferedImage(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()+StreamTextFile.SEPARATEUR+card.getImageFileName(FileConst.PNG_EXT));
////                if (bufferedImage == null) {
////                    throw new Exception();
////                }
////            }catch(Exception _e) {
////                peinte=true;
////            }
//            peinte=true;
//        }
    }

    public GraphicTarotCard(CardTarot _pc, int _i, boolean _fullCard) {
        this(_i,_fullCard);
        peindreCarte=true;
        card=_pc;
        String file_ = ResourceFiles.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()
                +StreamTextFile.SEPARATEUR+card.getImageFileName(FileConst.TXT_EXT));
        bufferedImage = ConverterBufferedImage.decodeToImage(file_);
        if (bufferedImage == null) {
            peinte=true;
        }
//        try {
//            String file_ = StreamTextFile.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage(), card.getImageFileName(FileConst.TXT_EXT));
//            bufferedImage = ConverterBufferedImage.decodeToImage(file_);
//            if (bufferedImage == null) {
//                throw new BadImageCardException();
//            }
//        } catch (Exception e_) {
////            try{
////                bufferedImage = StreamImageFile.resourceBufferedImage(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()+StreamTextFile.SEPARATEUR+card.getImageFileName(FileConst.PNG_EXT));
////                if (bufferedImage == null) {
////                    throw new Exception();
////                }
////            }catch(Exception _e) {
////                peinte=true;
////            }
//            peinte=true;
//        }
    }

    public GraphicTarotCard(int _i, boolean _fullCard) {
        peindreCarte=false;
        setHorizontalAlignment(_i);
        setVerticalAlignment(SwingConstants.TOP);
        fullCard=_fullCard;
    }

    public static String getTxtImage(CardTarot _card) {
        return ConverterBufferedImage.toBaseSixtyFour(getImage(_card));
    }

    public static BufferedImage getImage(CardTarot _card) {
        BufferedImage img_;
        String file_ = ResourceFiles.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()
                +StreamTextFile.SEPARATEUR+_card.getImageFileName(FileConst.TXT_EXT));
        img_ = ConverterBufferedImage.decodeToImage(file_);
        if (img_ == null) {
            return getDefaultImage(_card);
        }
        Graphics2D g_ = img_.createGraphics();
        g_.setColor(Color.BLACK);
        g_.drawRect(0,0, 99, 149);
        g_.dispose();
        return img_;
//        try {
//            String file_ = StreamTextFile.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage(), _card.getImageFileName(FileConst.TXT_EXT));
//            img_ = ConverterBufferedImage.decodeToImage(file_);
//            if (img_ == null) {
//                throw new BadImageCardException();
//            }
//            Graphics2D g_ = img_.createGraphics();
//            g_.setColor(Color.BLACK);
//            g_.drawRect(0,0, 99, 149);
//            g_.dispose();
//            return img_;
//        } catch (Exception e_) {
////            try{
////                img_ = StreamImageFile.resourceBufferedImage(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()+StreamTextFile.SEPARATEUR+_card.getImageFileName(FileConst.PNG_EXT));
////                if (img_ == null) {
////                    throw new Exception();
////                }
////                Graphics2D g_ = img_.createGraphics();
////                g_.setColor(Color.BLACK);
////                g_.drawRect(0,0, 99, 149);
////                g_.dispose();
////                return img_;
////            }catch(Exception _e) {
////                return getDefaultImage(_card);
////            }
//            return getDefaultImage(_card);
//        }
    }

    public static BufferedImage getDefaultImage(CardTarot _card) {
        BufferedImage img_ = new BufferedImage(100, 150, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g_ = img_.createGraphics();
        g_.setColor(Color.WHITE);
        g_.fillRect(0,0,100, 150);
        g_.setColor(Color.BLACK);
        g_.drawRect(0,0, 99, 149);
        if(_card == CardTarot.EXCUSE) {
            for(byte b=0;b<2;b++) {
                g_.setColor(Color.BLACK);
                g_.setFont(new Font(DEFAULT,Font.BOLD,20));
                g_.drawString(_card.getSymbol(Constants.getLanguage()),5,20);
                g_.drawString(_card.getSymbol(Constants.getLanguage()),87,20);
                g_.setColor(Color.GREEN);
                g_.fillPolygon(Numbers.wrapIntArray(35,40,40,33,13,11,30,35,47,47,53,53,65,80,65,70),Numbers.wrapIntArray(75,60,40,55,61,56,50,35,35,30,30,35,35,55,65,75),16);
                g_.setColor(Color.WHITE);
                g_.fillPolygon(Numbers.wrapIntArray(60,60,70,62),Numbers.wrapIntArray(60,40,55,63),4);
                g_.setColor(new Color(128,64,0));
                g_.fillOval(35,65,35,10);
                g_.setColor(Color.GREEN);
                g_.fillPolygon(Numbers.wrapIntArray(70,56,60,80),Numbers.wrapIntArray(55,65,70,55),4);
                g_.setColor(new Color(128,64,0));
                g_.fillPolygon(Numbers.wrapIntArray(0,3,5,40,38,4),Numbers.wrapIntArray(60,50,55,67,72,58),6);
                g_.setColor(Color.BLACK);
                g_.fillOval(47,67,6,6);
                g_.setColor(new Color(254,180,160));
                g_.fillOval(53,67,6,6);
                g_.fillOval(5,55,6,6);
                g_.fillOval(40,10,20,20);
                g_.setColor(Color.BLACK);
                g_.drawLine(44,16,48,16);
                g_.drawLine(56,16,52,16);
                g_.drawPolygon(Numbers.wrapIntArray(47,50,53),Numbers.wrapIntArray(23,18,23),3);
                g_.setColor(Color.RED);
                g_.drawLine(46,26,54,26);
                g_.fillPolygon(Numbers.wrapIntArray(50,60,62,38,40),Numbers.wrapIntArray(8,10,15,15,10,8),5);
                g_.setColor(Color.YELLOW);
                g_.fillRect(38,15,5,15);
                g_.fillRect(57,15,5,15);
                g_.setColor(Color.BLUE);
                g_.drawLine(60,10,60,2);
                g_.setColor(Color.BLACK);
                g_.drawLine(0,75,100,75);
                g_.rotate(Math.PI,50,75);
            }
        } else if(_card.couleur()==Suit.TRUMP||!_card.isCharacter()) {
            //Cartes chiffrees
            if(_card.valeur()>1) {
                int[][] coordonnees_=coordonnees(_card);
                for(byte b=0;b<2;b++) {
                    if(_card.couleur() != Suit.TRUMP) {
                        if(_card.couleur() == Suit.DIAMOND || _card.couleur() == Suit.HEART) {
                            g_.setColor(Color.RED);
                        } else {
                            g_.setColor(Color.BLACK);
                        }
                        g_.setFont(new Font(DEFAULT,Font.BOLD,12));
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),5,12);
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),83,12);
                    } else {
                        g_.setColor(Color.BLUE);
                        g_.setFont(new Font(DEFAULT,Font.BOLD,16));
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),5,18);
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),80,18);
                        g_.drawLine(45,10,55,10);
                        g_.drawLine(40,3,50,18);
                        g_.drawLine(50,18,60,3);
                        g_.setColor(Color.BLACK);
                        g_.drawLine(0,20,100,20);
                        g_.drawArc(0,-5,30,30,-45,90);
                        g_.drawArc(70,-5,30,30,135,90);
                    }
                    int xTabLen_ = coordonnees_[0].length;
                    for(int i=CustList.FIRST_INDEX;i<xTabLen_;i++) {
                        dessinerGrandSymbole(_card,g_,coordonnees_[0][i],coordonnees_[1][i]);
                    }
                    if(_card.couleur() != Suit.TRUMP) {
                        dessinerPetitSymbole(_card,g_,5,17);
                        dessinerPetitSymbole(_card,g_,85,17);
                    }
                    g_.rotate(Math.PI,50,75);
                }
            } else {
                for(byte b=0;b<2;b++) {
                    if(_card.couleur() != Suit.TRUMP) {
                        if(_card.couleur() == Suit.DIAMOND || _card.couleur() == Suit.HEART) {
                            g_.setColor(Color.RED);
                        } else {
                            g_.setColor(Color.BLACK);
                        }
                        g_.setFont(new Font(DEFAULT,Font.BOLD,12));
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),5,12);
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),83,12);
                        dessinerPetitSymbole(_card,g_,5,17);
                        dessinerPetitSymbole(_card,g_,85,17);
                    } else {
                        g_.setColor(Color.BLUE);
                        g_.setFont(new Font(DEFAULT,Font.BOLD,16));
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),7,18);
                        g_.drawString(_card.getSymbol(Constants.getLanguage()),83,18);
                        g_.drawLine(45,10,55,10);
                        g_.drawLine(40,3,50,18);
                        g_.drawLine(50,18,60,3);
                        g_.setColor(Color.BLACK);
                        g_.drawLine(0,20,100,20);
                        g_.drawArc(0,-5,30,30,-45,90);
                        g_.drawArc(70,-5,30,30,135,90);
                    }
                    g_.rotate(Math.PI,50,75);
                }
            }
            if(_card.valeur()%2==1) {
                int[][] pivot_=pivot();
                dessinerGrandSymbole(_card,g_,pivot_[0][(_card.valeur()-1)/2],pivot_[1][(_card.valeur()-1)/2]);
            }
        } else {
            for(byte b=0;b<2;b++) {
                if(_card.couleur() == Suit.DIAMOND || _card.couleur() == Suit.HEART) {
                    g_.setColor(Color.RED);
                } else {
                    g_.setColor(Color.BLACK);
                }
                g_.setFont(new Font(DEFAULT,Font.BOLD,12));
                g_.drawString(_card.getSymbol(Constants.getLanguage()),5,12);
                g_.drawString(_card.getSymbol(Constants.getLanguage()),83,12);
                dessinerPetitSymbole(_card,g_,5,17);
                dessinerPetitSymbole(_card,g_,85,17);
                if(_card.getNomFigure() == CardChar.QUEEN) {
                    g_.setColor(new Color(255,0,128));
                } else if(_card.getNomFigure() == CardChar.KING) {
                    g_.setColor(new Color(0,128,128));
                } else {
                    g_.setColor(new Color(128,128,0));
                }
                g_.fillPolygon(Numbers.wrapIntArray(25,35,47,47,53,53,65,75),Numbers.wrapIntArray(75,45,45,40,40,45,45,75),8);
                g_.setColor(Color.WHITE);
                g_.fillPolygon(Numbers.wrapIntArray(30,40,40),Numbers.wrapIntArray(75,55,75),3);
                g_.fillPolygon(Numbers.wrapIntArray(60,60,70),Numbers.wrapIntArray(75,55,75),3);
                g_.setColor(new Color(254,180,160));
                g_.fillOval(40,20,20,20);
                g_.setColor(Color.BLACK);
                g_.drawLine(44,26,48,26);
                g_.drawLine(56,26,52,26);
                g_.drawPolygon(Numbers.wrapIntArray(47,50,53),Numbers.wrapIntArray(33,28,33),3);
                g_.setColor(Color.RED);
                g_.drawLine(46,36,54,36);
                if(_card.getNomFigure() == CardChar.JACK || _card.getNomFigure() == CardChar.KNIGHT) {
                    g_.setColor(Color.GREEN);
                    g_.fillPolygon(Numbers.wrapIntArray(40,40,50,60,60,50),Numbers.wrapIntArray(25,20,15,20,25,25),6);
                } else {
                    g_.setColor(Color.YELLOW);
                    g_.fillPolygon(Numbers.wrapIntArray(55,60,55,58,54,50,46,42,45,40,45),Numbers.wrapIntArray(23,18,21,13,17,9,17,13,21,18,23),11);
                }
                g_.setColor(new Color(128,64,0));
                if(_card.getNomFigure() == CardChar.QUEEN) {
                    g_.fillPolygon(Numbers.wrapIntArray(43,43,47,47,35),Numbers.wrapIntArray(22,37,40,45,45),5);
                    g_.fillPolygon(Numbers.wrapIntArray(57,57,53,53,65),Numbers.wrapIntArray(22,37,40,45,45),5);
                } else {
                    g_.fillPolygon(Numbers.wrapIntArray(43,43,45,55,57,57,60,55,45,40),Numbers.wrapIntArray(22,32,35,35,32,22,30,40,40,30),10);
                    g_.setColor(Color.RED);
                    g_.drawLine(46,36,54,36);
                    if(_card.getNomFigure() == CardChar.KNIGHT) {
                        g_.setColor(new Color(128,64,0));
                        g_.fillPolygon(Numbers.wrapIntArray(2,2,10,12,15,30,15,10,5),Numbers.wrapIntArray(60,55,47,40,50,75,75,55,60),9);
                    }
                }
                dessinerGrandSymbole(_card,g_,15,7);
                dessinerGrandSymbole(_card,g_,60,7);
                g_.rotate(Math.PI,50,75);
            }
            g_.setColor(Color.BLACK);
            g_.drawLine(0,75,100,75);
        }
        g_.dispose();
        return img_;
    }

    public void setPreferredSize(boolean _small) {
        setPreferredSize(getDimension(_small));
    }
    public static Dimension getMaxDimension() {
        return getDimension(false);
    }
    public static Dimension getMinDimension() {
        return getDimension(true);
    }
    public static Dimension getDimension(boolean _small) {
        if (_small) {
            return new Dimension(25,150);
        }
        return new Dimension(100,150);
    }
    public static Dimension getDimensionForSeveralCards(int _number) {
        return new Dimension(100 + 25 * (_number - 1), 150);
    }
    void setCarte(CardTarot _pc) {
        card=_pc;
        peinte=false;
        peindreCarte=true;
        String file_ = ResourceFiles.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()
                +StreamTextFile.SEPARATEUR+card.getImageFileName(FileConst.TXT_EXT));
        bufferedImage = ConverterBufferedImage.decodeToImage(file_);
        if (bufferedImage == null) {
            peinte=true;
        }
//        try {
//            String file_ = StreamTextFile.ressourceFichier(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage(), card.getImageFileName(FileConst.TXT_EXT));
//            bufferedImage = ConverterBufferedImage.decodeToImage(file_);
//            if (bufferedImage == null) {
//                throw new BadImageCardException();
//            }
//        } catch (Exception e_) {
////            try{
////                bufferedImage = StreamImageFile.resourceBufferedImage(FileConst.RESOURCES_IMAGES+StreamTextFile.SEPARATEUR+Constants.getLanguage()+StreamTextFile.SEPARATEUR+card.getImageFileName(FileConst.PNG_EXT));
////                if (bufferedImage == null) {
////                    throw new Exception();
////                }
////            }catch(Exception _e) {
////                peinte=true;
////            }
//            peinte=true;
//        }
    }

    public CardTarot getCard() {
        return card;
    }

    public void setCarteEnJeu(CardTarot _carte) {
        peindreCarte=true;
        setCarte(_carte);
    }
    public void setJeu() {
        peindreCarte=false;
    }

    boolean coupeHorizontal() {
        return fullCard;
    }

    /**Methode importante dessinant les cartes des jeux de cartes face non cachee sauf pour certaines cartes du solitaire*/
    @Override
    protected void paintComponent(Graphics _g2) {
        Graphics2D g=(Graphics2D)_g2;
        if(!peindreCarte) {
            _g2.setColor(Color.RED);
            _g2.fillRect(0,0,getWidth()-1,getHeight()-1);
            _g2.setColor(Color.BLACK);
            _g2.drawRect(0,0,getWidth()-1,getHeight()-1);
            _g2.setColor(Color.BLUE);
            _g2.setFont(new Font(DEFAULT,Font.BOLD,20));
            _g2.drawString(GameEnum.TAROT.toString(),20,80);
            return;
        }
        if(!peinte) {
            g.setColor(Color.WHITE);
            g.fillRect(0,0,getWidth(),getHeight());
            super.paintComponent(_g2);
            if (bufferedImage != null) {
                if(fullCard) {
                    //whole card
                    g.drawImage(bufferedImage, 0, 0, null);
                } else {
                    g.drawImage(bufferedImage, -75, 0, null);
                }
            }
            g.setColor(Color.BLACK);
            g.drawRect(0,0,getWidth()-1,getHeight()-1);
            return;
        }
        if(fullCard) {
            //whole card
            g.drawImage(getDefaultImage(card), 0, 0, null);
        } else {
            g.drawImage(getDefaultImage(card), -75, 0, null);
        }
    }
    private static int[][] coordonnees(CardTarot _card) {
        byte valeur_=_card.valeur();
        int[][] tab_=new int[2][valeur_/2];
        if(valeur_<4) {
            tab_[0][0]=40;
            tab_[1][0]=25;
        } else if(valeur_<9) {
            tab_[0][0]=10;
            tab_[1][0]=25;
            tab_[0][1]=70;
            tab_[1][1]=25;
            if(valeur_>5) {
                tab_[0][2]=70;
                tab_[1][2]=65;
                if(valeur_==8) {
                    tab_[0][3]=40;
                    tab_[1][3]=40;
                }
            }
        } else {
            tab_[0][0]=10;
            tab_[1][0]=25;
            tab_[0][1]=70;
            tab_[1][1]=25;
            if(valeur_<12) {
                tab_[0][2]=10;
                tab_[1][2]=50;
                tab_[0][3]=70;
                tab_[1][3]=50;
                if(valeur_>9) {
                    tab_[0][4]=40;
                    tab_[1][4]=35;
                }
            } else {
                tab_[0][2]=10;
                tab_[0][3]=70;
                tab_[0][4]=10;
                if(valeur_<13) {
                    tab_[1][2]=45;
                    tab_[1][3]=45;
                    tab_[1][4]=65;
                    tab_[1][5]=45;
                    tab_[0][5]=40;
                } else if(valeur_<15) {
                    tab_[1][2]=45;
                    tab_[1][3]=45;
                    tab_[1][4]=65;
                    tab_[1][5]=35;
                    tab_[0][5]=40;
                    if(valeur_==14) {
                        tab_[0][6]=40;
                        tab_[1][6]=55;
                    }
                } else if(valeur_<18) {
                    tab_[1][2]=40;
                    tab_[1][3]=40;
                    tab_[1][4]=55;
                    tab_[1][5]=55;
                    tab_[0][5]=70;
                    tab_[0][6]=40;
                    tab_[1][6]=30;
                    if(valeur_>15) {
                        tab_[0][7]=40;
                        tab_[1][7]=45;
                    }
                } else {
                    tab_[1][2]=35;
                    tab_[1][3]=35;
                    tab_[1][4]=45;
                    tab_[0][5]=70;
                    tab_[1][5]=45;
                    tab_[0][6]=10;
                    tab_[1][6]=55;
                    if(valeur_<21) {
                        tab_[0][7]=40;
                        tab_[1][7]=30;
                        if(valeur_==18) {
                            tab_[0][8]=40;
                            tab_[1][8]=50;
                        } else {
                            tab_[0][8]=40;
                            tab_[1][8]=40;
                            if(valeur_==20) {
                                tab_[0][9]=40;
                                tab_[1][9]=50;
                            }
                        }
                    } else {
                        tab_[0][7]=70;
                        tab_[1][7]=55;
                        tab_[0][8]=40;
                        tab_[1][8]=30;
                        tab_[0][9]=40;
                        tab_[1][9]=40;
                    }
                }
            }
        }
        return tab_;
    }
    private static int[][] pivot() {
        int[][] tab_=new int[2][11];
        for(byte b=0;b<11;b++) {
            tab_[0][b]=40;
            if(b!=3) {
                tab_[1][b]=65;
            } else {
                tab_[1][3]=40;
            }
        }
        return tab_;
    }
    private static void dessinerPetitSymbole(CardTarot _card, Graphics2D _g,int _x,int _y) {
        Suit couleur_=_card.couleur();
        if(couleur_ == Suit.HEART) {
            _g.setColor(Color.RED);
            _g.fillOval(_x,_y,5,5);
            _g.fillOval(_x+5,_y,5,5);
            _g.fillRect(_x+3,_y+3,5,5);
            _g.setColor(Color.WHITE);
            _g.fillOval(_x,_y+5,5,5);
            _g.fillOval(_x+5,_y+5,5,5);
        } else if(couleur_ == Suit.SPADE) {
            _g.setColor(Color.BLACK);
            _g.fillOval(_x,_y+2,5,5);
            _g.fillOval(_x+5,_y+2,5,5);
            _g.fillPolygon(Numbers.wrapIntArray(5+_x,8+_x,5+_x,2+_x),Numbers.wrapIntArray(5+_y,12+_y,8+_y,12+_y),4);
            _g.fillRect(_x+3,_y-2,5,7);
            _g.setColor(Color.WHITE);
            _g.fillOval(_x,_y-3,5,5);
            _g.fillOval(_x+5,_y-3,5,5);
        } else if(couleur_ == Suit.DIAMOND) {
            _g.setColor(Color.RED);
            _g.fillPolygon(Numbers.wrapIntArray(_x,5+_x,10+_x,5+_x),Numbers.wrapIntArray(5+_y,_y,5+_y,10+_y),4);
        } else {
            _g.setColor(Color.BLACK);
            _g.fillOval(_x,_y+3,4,4);
            _g.fillOval(_x+6,_y+3,4,4);
            _g.fillOval(_x+3,_y,4,4);
            _g.fillPolygon(Numbers.wrapIntArray(3+_x,5+_x,3+_x),Numbers.wrapIntArray(4+_y,5+_y,6+_y),3);
            _g.fillPolygon(Numbers.wrapIntArray(6+_x,5+_x,6+_x),Numbers.wrapIntArray(4+_y,5+_y,6+_y),3);
            _g.fillPolygon(Numbers.wrapIntArray(4+_x,5+_x,6+_x),Numbers.wrapIntArray(3+_y,5+_y,3+_y),3);
            _g.fillPolygon(Numbers.wrapIntArray(3+_x,5+_x,6+_x,5+_x),Numbers.wrapIntArray(10+_y,5+_y,10+_y,8+_y),4);
        }
    }
    private static void dessinerGrandSymbole(CardTarot _card,Graphics2D _g,int _x,int _y) {
        Suit couleur_=_card.couleur();
        if(couleur_ == Suit.TRUMP) {
            _g.setColor(Color.BLUE);
            _g.drawLine(_x,_y,_x+10,_y+10);
            _g.setColor(Color.BLACK);
            _g.fillPolygon(Numbers.wrapIntArray(10+_x,12+_x,15+_x,15+_x,12+_x,10+_x),Numbers.wrapIntArray(10+_y,10+_y,12+_y,15+_y,15+_y,12+_y),6);
        } else if(couleur_ == Suit.HEART) {
            _g.setColor(Color.RED);
            _g.fillOval(_x,_y,10,10);
            _g.fillOval(_x+10,_y,10,10);
            _g.fillRect(_x+5,_y+5,10,10);
            _g.setColor(Color.WHITE);
            _g.fillOval(_x,_y+10,10,10);
            _g.fillOval(_x+10,_y+10,10,10);
        } else if(couleur_ == Suit.SPADE) {
            _g.setColor(Color.BLACK);
            _g.fillOval(_x,_y+5,10,10);
            _g.fillOval(_x+10,_y+5,10,10);
            _g.fillPolygon(Numbers.wrapIntArray(10+_x,13+_x,10+_x,7+_x),Numbers.wrapIntArray(10+_y,20+_y,17+_y,20+_y),4);
            _g.fillRect(_x+5,_y,10,10);
            _g.setColor(Color.WHITE);
            _g.fillOval(_x,_y-5,10,10);
            _g.fillOval(_x+10,_y-5,10,10);
        } else if(couleur_ == Suit.DIAMOND) {
            _g.setColor(Color.RED);
            _g.fillPolygon(Numbers.wrapIntArray(_x,10+_x,20+_x,10+_x),Numbers.wrapIntArray(10+_y,_y,10+_y,20+_y),4);
        } else {
            _g.setColor(Color.BLACK);
            _g.fillOval(_x,_y+6,8,8);
            _g.fillOval(_x+12,_y+6,8,8);
            _g.fillOval(_x+6,_y,8,8);
            _g.fillPolygon(Numbers.wrapIntArray(7+_x,10+_x,7+_x),Numbers.wrapIntArray(8+_y,10+_y,12+_y),3);
            _g.fillPolygon(Numbers.wrapIntArray(13+_x,10+_x,13+_x),Numbers.wrapIntArray(8+_y,10+_y,12+_y),3);
            _g.fillPolygon(Numbers.wrapIntArray(8+_x,10+_x,12+_x),Numbers.wrapIntArray(7+_y,10+_y,7+_y),3);
            _g.fillPolygon(Numbers.wrapIntArray(7+_x,10+_x,13+_x,10+_x),Numbers.wrapIntArray(20+_y,10+_y,20+_y,17+_y),4);
        }
    }
}
