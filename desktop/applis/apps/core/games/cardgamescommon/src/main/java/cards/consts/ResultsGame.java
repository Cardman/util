package cards.consts;
import code.maths.Rate;
import code.util.*;
import code.util.StringMap;


public final class ResultsGame {
    private String globalResultsPageTitle;
    private String detailResultsTitle;
    private StringMap<String> renderedPages = new StringMap<String>();
    /**Ecarts types des parties cumule&eacute;es*/
    private CustList<Rate> sigmas=new CustList<Rate>();
    /**Sommes des scores des joueurs*/
    private Longs sums=new Longs();
    private CustList<Longs> scores;
    private String general="";
    private String specific="";

    public String getGlobalResultsPageTitle() {
        return globalResultsPageTitle;
    }

    public void setGlobalResultsPageTitle(String _globalResultsPageTitle) {
        globalResultsPageTitle = _globalResultsPageTitle;
    }

    public String getDetailResultsTitle() {
        return detailResultsTitle;
    }

    public void setDetailResultsTitle(String _detailResultsTitle) {
        detailResultsTitle = _detailResultsTitle;
    }

    public StringMap<String> getRenderedPages() {
        return renderedPages;
    }

    public CustList<Rate> getSigmas() {
        return sigmas;
    }

    public Longs getSums() {
        return sums;
    }

    public CustList<Longs> getScores() {
        return scores;
    }

    public void setScores(CustList<Longs> _scores) {
        scores = new CustList<Longs>(_scores);
    }

    public void setRenderedPages(StringMap<String> _renderedPages) {
        renderedPages = _renderedPages;
    }

    public void setSigmas(CustList<Rate> _sigmas) {
        sigmas = _sigmas;
    }

    public void setSums(Longs _sums) {
        sums = _sums;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String _general) {
        this.general = _general;
    }

    public String getSpecific() {
        return specific;
    }

    public void setSpecific(String _specific) {
        this.specific = _specific;
    }
}
