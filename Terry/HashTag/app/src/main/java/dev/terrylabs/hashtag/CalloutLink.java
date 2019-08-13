package dev.terrylabs.hashtag;

import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CalloutLink extends ClickableSpan {
    Context context;
    CalloutListener listener;
    public CalloutLink(Context ctx, CalloutListener listener) {
        super();
        context = ctx;
        this.listener = listener;
    }

    public interface CalloutListener {
        void onCalloutClick(String text);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setARGB(255, 51, 51, 51);
        ds.setColor(Color.RED);

    }

    @Override
    public void onClick(View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        String theWord = s.subSequence(start + 1, end).toString();
        // you can start another activity here
        //Toast.makeText(context, String.format("Here's a cool person: %s", theWord), 10).show();
        listener.onCalloutClick("CALLOUT : " + theWord);
    }
}
