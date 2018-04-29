package com.robin994.streamsoccer.Data;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntryListBuilder<T> {

    private static final long DAY = 24 * 60 * 60 * 1000;

    public static boolean inLastDay(Date aDate) {
        return aDate.getTime() > System.currentTimeMillis() - DAY;
    }

    public List<T> getEntries(String xml) {
        List<T> list = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xml));
            int eventType = xpp.getEventType();
            String result = "";
            Boolean entry = false;
            Entry entryObj =  new Entry();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        //System.out.println("Start document \t\t|");
                        break;

                    case XmlPullParser.START_TAG:

                        //System.out.println("Start tag |" + xpp.getName());
                        result = xpp.getName();
                        switch (result) {
                            case "link":
                                if (entryObj != null) {
                                    result = xpp.getAttributeValue(0);
                                    entryObj.setUrl(xpp.getAttributeValue(0));
                                }
                                break;
                            case "entry":
                                entry = true;
                                entryObj = new Entry();
                                break;
                            case "category":
                                if (entryObj != null) {
                                    entryObj.setCategory(xpp.getAttributeValue(0));
                                }
                                break;
                        }

                    case XmlPullParser.TEXT:
                        //System.out.println("Text | " + xpp.getText());
                        if (entry && xpp.getText() != null) {

                            switch (result) {
                                case "name":
                                    entryObj.setAuthor(xpp.getText().substring(3));
                                    break;
                                case "uri":
                                    entryObj.setAuthorUrl(xpp.getText());
                                    break;
                                case "content":
                                    entryObj.setContent(xpp.getText());
                                    break;
                                case "updated":
                                    entryObj.setDateStr(xpp.getText());
                                    break;
                                case "title":
                                    entryObj.setTitle(xpp.getText());
                                    break;
                                case "id":
                                    entryObj.setId(xpp.getText());
                                    break;
                            }
                            result += " " + xpp.getText();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        //System.out.println("End tag |" + xpp.getName());
                        result += " " + xpp.getName();
                        if (xpp.getName().equals("entry")) {
                            if (!list.contains(entryObj) && inLastDay(entryObj.getDate())) {
                                    /*
                                    System.out.println("Aggiungo elemento");
                                    System.out.println("MATCH " + matchBuilder.toString());
                                    */
                                list.add((T) entryObj);
                                //System.out.println("ARRAY " + list.toString());
                            }
                            entry = false;
                        }
                        //System.out.println("STRINGA FINALE | " + result);
                        result = "";
                        break;
                }
                eventType = xpp.next();
            }
            //System.out.println("End document");
        } catch (XmlPullParserException e) {
            //og.d("Error", e.getMessage());
            //Log.d("Error line", Integer.toString(e.getLineNumber()));
            //Log.d("Error column", Integer.toString(e.getColumnNumber()));
            e.getCause();

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
