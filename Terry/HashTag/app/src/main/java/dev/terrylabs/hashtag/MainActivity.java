package dev.terrylabs.hashtag;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements Hashtag.HashTagListener, CalloutLink.CalloutListener {

    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str  =  "@People , https://youtu.be/HRb0g-g4tgU You've gotta #dance like there's nobody watching,#Love like you'll never be #hurt,#Sing like there's @nobody listening,And live like it's #heaven on #earth.";

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        ArrayList<int[]> hashtagSpans1 = getSpans(str, '#');
        ArrayList<int[]> calloutSpans1 = getSpans(str, '@');

        SpannableString commentsContent1 = new SpannableString(str);

        setSpanComment(commentsContent1,hashtagSpans1) ;
        setSpanUname(commentsContent1,calloutSpans1) ;

        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        textView1.setText(commentsContent1);
    }
    public ArrayList<int[]> getSpans(String body, char prefix) {
        ArrayList<int[]> spans = new ArrayList<int[]>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");
        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return  spans;
    }

    private void setSpanComment(SpannableString commentsContent, ArrayList<int[]> hashtagSpans) {
        for(int i = 0; i < hashtagSpans.size(); i++) {
            int[] span = hashtagSpans.get(i);
            int hashTagStart = span[0];
            int hashTagEnd = span[1];

            commentsContent.setSpan(new Hashtag(MainActivity.this, this),
                    hashTagStart,
                    hashTagEnd, 0);

        }


    }
    private void setSpanUname(SpannableString commentsUname, ArrayList<int[]> calloutSpans) {
        for(int i = 0; i < calloutSpans.size(); i++) {
            int[] span = calloutSpans.get(i);
            int calloutStart = span[0];
            int calloutEnd = span[1];
            commentsUname.setSpan(new CalloutLink(MainActivity.this, this),
                    calloutStart,
                    calloutEnd, 0);
        }
    }

    @Override
    public void onHashTagClick(String text) {
        textView2.setText(text);
    }

    @Override
    public void onCalloutClick(String text) {
        textView2.setText(text);
    }

    private ArrayList pullLinks(String text) {
        ArrayList links = new ArrayList();

        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") &amp;&amp; urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links.add(urlStr);
        }
        return links;
    }
}