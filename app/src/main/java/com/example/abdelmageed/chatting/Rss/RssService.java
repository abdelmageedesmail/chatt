package com.example.abdelmageed.chatting.Rss;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.example.abdelmageed.chatting.RSSParser.PcWorldRssParser;
import com.example.abdelmageed.chatting.model.Constants;
import com.example.abdelmageed.chatting.model.RssItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;


public class RssService extends IntentService {

    private static final String RSS_LINK = "http://www.mans.edu.eg/mans-news?format=feed&type=rss";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(Constants.TAG, "Service started");
        List<RssItem> rssItems = null;
        try {
            PcWorldRssParser parser = new PcWorldRssParser();
            rssItems = parser.parse(getInputStream(RSS_LINK));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        android.os.ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(Constants.TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}
