package code.gui;
import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.Timer;

import code.gui.events.UpdateTimeEvent;
import code.util.StringList;

public class Clock extends JTextField {

    private static final String ARIAL = "Arial";
    private static final String SAMPLE_SEPARATOR = "_";
    private static final String SEPARATOR_DATE_TIME = " ";
    private static final String SEPARATOR_DATE = "/";
    private static final String SEPARATOR = ":";
    private static final int SECOND_MILLIS = 1000;
    private static final Color COLOR = new Color(0,0,255);
    private static final int DEFAULT_NB_CHARS = 10;
    private static final int HEIGHT_TIME = 15;

    private Timer timer;

    public Clock() {
        super(DEFAULT_NB_CHARS);
        setEditable(false);
        setFont(new Font(ARIAL,Font.PLAIN,HEIGHT_TIME));
        setForeground(COLOR);
        timer = new Timer(SECOND_MILLIS, new UpdateTimeEvent(this));
        timer.start();
    }

    public void setTimeText() {
        setText(getTimeText());
    }

    public static String getSimpleDateTimeText() {
        return getDateTimeText(SAMPLE_SEPARATOR, SAMPLE_SEPARATOR, SAMPLE_SEPARATOR);
    }

    public static String getDateTimeText(String _separatorDate, String _sep, String _separatorTime) {
        Calendar now_ = Calendar.getInstance();
        int y_ = now_.get(Calendar.YEAR);
        int m_ = now_.get(Calendar.MONTH) + 1;
        int d_ = now_.get(Calendar.DAY_OF_MONTH);
        int h_ = now_.get(Calendar.HOUR_OF_DAY);
        int mi_ = now_.get(Calendar.MINUTE);
        int s_ = now_.get(Calendar.SECOND);
        return StringList.concat(String.valueOf(y_),_separatorDate,String.valueOf(m_),
                _separatorDate,String.valueOf(d_),_sep,String.valueOf(h_),
                _separatorTime,String.valueOf(mi_),_separatorTime,String.valueOf(s_));
    }

    public static String getDateTimeText() {
        Calendar now_ = Calendar.getInstance();
        int y_ = now_.get(Calendar.YEAR);
        int m_ = now_.get(Calendar.MONTH) + 1;
        int d_ = now_.get(Calendar.DAY_OF_MONTH);
        String date_ = StringList.concat(String.valueOf(d_),SEPARATOR_DATE,String.valueOf(m_),SEPARATOR_DATE,String.valueOf(y_));
        return StringList.concat(date_,SEPARATOR_DATE_TIME,getTimeText());
    }

    public static String getTimeText() {
        Calendar now_ = Calendar.getInstance();
        int h_ = now_.get(Calendar.HOUR_OF_DAY);
        int m_ = now_.get(Calendar.MINUTE);
        int s_ = now_.get(Calendar.SECOND);
        return StringList.concat(String.valueOf(h_),SEPARATOR,String.valueOf(m_),SEPARATOR,String.valueOf(s_));
    }

    public static String getDateText() {
        Calendar now_ = Calendar.getInstance();
        int y_ = now_.get(Calendar.YEAR);
        int m_ = now_.get(Calendar.MONTH) + 1;
        int d_ = now_.get(Calendar.DAY_OF_MONTH);
        return StringList.concat(String.valueOf(y_),SEPARATOR_DATE,String.valueOf(m_),SEPARATOR_DATE,String.valueOf(d_));
    }
}
