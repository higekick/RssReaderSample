package com.higekick.rssreadersample;

import android.support.annotation.WorkerThread;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class RssReaderManager {

    public final static String URL_RSS = "https://www.lifehacker.jp/feed/index.xml";

    @WorkerThread
    public static List<RssItem> loadRss(String url) {
        List<RssItem> items = new ArrayList<>();
        InputStream is;

        try {
            //URLクラスのインスタンス作成
            URL u = new URL(url);
            //コネクション接続
            URLConnection con = u.openConnection();
            //ストリームの取得
            is = con.getInputStream();
            
            items = getRssList(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return items;
    }

    /** get RSS item list from RSS xml input */
    private static List<RssItem> getRssList(InputStream is) {
        XmlPullParser parser = Xml.newPullParser();
        RssItem curItem = null;
        List<RssItem> items = new ArrayList<>();

        //XMLのストリームを渡す
        try {
            parser.setInput(is,null);
            //イベント取得
            int eventType = parser.getEventType();
            //終端までループ
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String tag = null;
                switch(eventType){
                    //ドキュメントの最初
                    case XmlPullParser.START_DOCUMENT:
                        // ドキュメント開始
                        break;

                    //開始タグ時
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        //itemタグ開始時
                        //必要なのは、itemタグの中身の子タグのみなので
                        //itemのスタートタグでitemクラスインスタンス作成し、
                        //作成していなかったら、item内の情報ではない。
                        if(tag.equals("item")){
                            curItem = new RssItem();
                        }else if (curItem!=null){
                            //itemタグ内の子タグごとの処理
                            //タグ名称と取得したいタグ名を比較して
                            //同じであったら、nextText()により内容取得。
                            if (tag.equals("link")){
                                curItem.link = parser.nextText();
                            }else if(tag.equals("title")){
                                curItem.title = parser.nextText();
                            }else if(tag.equals("description")){
                                curItem.description = parser.nextText();
                            }else if(tag.equals("category")){
                                curItem.category = parser.nextText();
                            }
                        }
                        break;

                    //終了タグ時
                    case XmlPullParser.END_TAG:
                        //itemタグが終わったら、そこで１記事のセットが終了したとして
                        //listに追加。
                        tag=parser.getName();
                        if(tag.equals("item")){
                            //Itemタグ終了時に格納。
                            items.add(curItem);
                            curItem = null;
                        }
                        break;

                }
                //次のイベントへ遷移させループ
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    /** represents RSS item */
    public static class RssItem {
        public String title;
        public String description;
        public String category;
        public String link;
    }
}
