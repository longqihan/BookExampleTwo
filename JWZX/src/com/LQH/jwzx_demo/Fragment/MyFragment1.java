package com.LQH.jwzx_demo.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.LQH.jwzx_demo.Activity.NewsActivity;
import com.LQH.jwzx_demo.UiAdapter.NewsAdapter;
import com.LQH.jwzx_demo.bean.News;
import com.LQH.jwzx_demo.utils.StreamTools;
import com.zhy.zhy_slidemenu_demo02.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MyFragment1 extends Fragment {
	protected View mMainView;
	protected Context mContext;
	private List<News> newsList;
	private Handler handler;
	private NewsAdapter adapter;
	private ListView newslistview;
	protected boolean flag;
	protected String sshtml;
	protected int pageNum = 1;

	// private String Session;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity.getApplicationContext();
	}

	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		mMainView = inflater.inflate(R.layout.page1, container, false);

		newslistview = (ListView) mMainView.findViewById(R.id.news_list);
		newsList = new ArrayList<News>();
		getNews();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {

					adapter = new NewsAdapter(getActivity(), newsList);
				    newslistview.setAdapter(adapter);
					
				} else if (msg.what == 2) {
					adapter = new NewsAdapter(getActivity(), newsList);
					newslistview.setAdapter(adapter);
					
					
				}
				//adapter.notifyDataSetChanged();
			}
		};
		
		newslistview
		.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				News news = newsList.get(arg2);
				Intent intent = new Intent();
				intent.setClass(getActivity(), NewsActivity.class);
				intent.putExtra("newsUrl",news.getNewsUrl());
				intent.putExtra("newsTitle",news.getNewsTitle());
				intent.putExtra("newsTime",news.getNewsTime());
				startActivity(intent);	
				
			}
		});

		newslistview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 判断滚动到底部
					if (newslistview.getLastVisiblePosition() == (newslistview
							.getCount() - 1)) {

						Log.e(">>>>>>>>>", "滑动到底部");
						nextPage();
						pageNum = pageNum + 1;
						newslistview.setAdapter(adapter);

					}

					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem + visibleItemCount == totalItemCount
						&& !flag) {
					flag = true;
				} else
					flag = false;

			}
		});

		return mMainView;
	}

	public void nextPage() {
		new Thread() {

			@Override
			public void run() {
				try {
					//System.out.println("=========1");
					URL url = new URL(
							"http://jwc.jxnu.edu.cn/Default.aspx?Action=Jwtz&Type=1");
					HttpURLConnection urlConnection = (HttpURLConnection) url
							.openConnection();
					// urlConnection.setRequestProperty("Host",
					// "219.229.250.18");
					urlConnection.setConnectTimeout(10000);
					urlConnection.setDoInput(true);
					urlConnection.setDoOutput(true);
					urlConnection.connect();
					// String cookie=urlConnection.getHeaderField("Set-Cookie");
					System.out.println("=========2");
					//System.out.println(urlConnection.getResponseCode());
					//System.out.println(urlConnection.getContentLength());
					//System.out.println(urlConnection.getHeaderField("Set-Cookie"));

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setRequestMethod("POST");

					conn.setRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
					conn.setRequestProperty("Connection", "Keep-Alive");
					// conn.setRequestProperty("Host", "219.229.250.18");
					// conn.setRequestProperty("Cookie", cookie);
					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					conn.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0");
					conn.setConnectTimeout(10000);
					conn.connect();

					OutputStreamWriter out = new OutputStreamWriter(
							conn.getOutputStream(), "UTF-8");
					String content = "__EVENTTARGET=_ctl1%24butNext&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwULLTE4OTM1MzUwMjAPZBYCAgEPZBYIAgEPDxYCHgRUZXh0BSAyMDE35bm0M%2BaciDE35pelIOaYn%2Bacn%2BS6lCZuYnNwO2RkAgMPDxYCHwBkZGQCBw8WAh8ABYUBPGRpdiBpZD0iY3VycmVudFBvc2l0aW9uIj48ZGl2IGlkPSJjdXJyZW50UG9zaXRpb25MZWZ0Ij7lvZPliY3kvY3nva7vvJog5pWZ5Yqh6YCa55%2BlPC9kaXY%2BPGRpdiBpZD0iY3VycmVudFBvc2l0aW9uUmlnaHQiPjwvZGl2PjwvZGl2PmQCCQ9kFgJmD2QWEAIBDw8WAh4HRW5hYmxlZGhkZAIDDw8WAh8BaGRkAgkPDxYCHwAFBDE1NDhkZAILDw8WAh8ABQExZGQCDQ8PFgIfAAUCNzhkZAIPDw8WAh8ABQIyMGRkAhEPEA8WAh4LXyFEYXRhQm91bmRnZA8WTmYCAQICAgMCBAIFAgYCBwIIAgkCCgILAgwCDQIOAg8CEAIRAhICEwIUAhUCFgIXAhgCGQIaAhsCHAIdAh4CHwIgAiECIgIjAiQCJQImAicCKAIpAioCKwIsAi0CLgIvAjACMQIyAjMCNAI1AjYCNwI4AjkCOgI7AjwCPQI%2BAj8CQAJBAkICQwJEAkUCRgJHAkgCSQJKAksCTAJNFk4QBQExBQExZxAFATIFATJnEAUBMwUBM2cQBQE0BQE0ZxAFATUFATVnEAUBNgUBNmcQBQE3BQE3ZxAFATgFAThnEAUBOQUBOWcQBQIxMAUCMTBnEAUCMTEFAjExZxAFAjEyBQIxMmcQBQIxMwUCMTNnEAUCMTQFAjE0ZxAFAjE1BQIxNWcQBQIxNgUCMTZnEAUCMTcFAjE3ZxAFAjE4BQIxOGcQBQIxOQUCMTlnEAUCMjAFAjIwZxAFAjIxBQIyMWcQBQIyMgUCMjJnEAUCMjMFAjIzZxAFAjI0BQIyNGcQBQIyNQUCMjVnEAUCMjYFAjI2ZxAFAjI3BQIyN2cQBQIyOAUCMjhnEAUCMjkFAjI5ZxAFAjMwBQIzMGcQBQIzMQUCMzFnEAUCMzIFAjMyZxAFAjMzBQIzM2cQBQIzNAUCMzRnEAUCMzUFAjM1ZxAFAjM2BQIzNmcQBQIzNwUCMzdnEAUCMzgFAjM4ZxAFAjM5BQIzOWcQBQI0MAUCNDBnEAUCNDEFAjQxZxAFAjQyBQI0MmcQBQI0MwUCNDNnEAUCNDQFAjQ0ZxAFAjQ1BQI0NWcQBQI0NgUCNDZnEAUCNDcFAjQ3ZxAFAjQ4BQI0OGcQBQI0OQUCNDlnEAUCNTAFAjUwZxAFAjUxBQI1MWcQBQI1MgUCNTJnEAUCNTMFAjUzZxAFAjU0BQI1NGcQBQI1NQUCNTVnEAUCNTYFAjU2ZxAFAjU3BQI1N2cQBQI1OAUCNThnEAUCNTkFAjU5ZxAFAjYwBQI2MGcQBQI2MQUCNjFnEAUCNjIFAjYyZxAFAjYzBQI2M2cQBQI2NAUCNjRnEAUCNjUFAjY1ZxAFAjY2BQI2NmcQBQI2NwUCNjdnEAUCNjgFAjY4ZxAFAjY5BQI2OWcQBQI3MAUCNzBnEAUCNzEFAjcxZxAFAjcyBQI3MmcQBQI3MwUCNzNnEAUCNzQFAjc0ZxAFAjc1BQI3NWcQBQI3NgUCNzZnEAUCNzcFAjc3ZxAFAjc4BQI3OGcWAWZkAhMPFgIeC18hSXRlbUNvdW50AhQWKGYPZBYCZg8VA6oBPGEgY2xhc3M9Qmx1ZUxpbmtfTm9VbmRlciB0YXJnZXQ9X2JsYW5rIGhyZWY9YXJ0aWNsZV82MTc4LmFzcHg%2B44CQ5pWZ5Yqh5a2XWzIwMTddMDE45Y%2B344CR5YWz5LqO5YWs5biD5oiR5qChMjAxNuW5tOW6puecgee6p%2BeyvuWTgeWcqOe6v%2BW8gOaUvuivvueri%2BmhueWQjeWNleeahOmAmuefpTwvYT4AEDIwMTflubQz5pyIMTbml6VkAgEPZBYCZg8VA4sBPGEgY2xhc3M9Qmx1ZUxpbmtfTm9VbmRlciB0YXJnZXQ9X2JsYW5rIGhyZWY9YXJ0aWNsZV82MTc3LmFzcHg%2B44CQ5pWZ5Yqh5a2XWzIwMTddMDE35Y%2B344CR5YWz5LqO5byA6K6%2B5pys5a2m5pyf5Y%2BM5LyR5pel6K6y5bqn55qE6YCa55%2BlPC9hPgAQMjAxN%2BW5tDPmnIgxNeaXpWQCAg9kFgJmDxUDwQE8YSBjbGFzcz1CbHVlTGlua19Ob1VuZGVyIHRhcmdldD1fYmxhbmsgaHJlZj1hcnRpY2xlXzYxNzYuYXNweD7jgJDmlZnliqHmioTlrZdbMjAxN10wMDHlj7fjgJHlhbPkuo4yMDE2LTIwMTflrablubTnrKzkuIDlrabmnJ%2FmnJ%2FmnKvogIPor5XlkozmnKzlrabmnJ%2FooaXnvJPogIPov53nuqrlj4rkvZzlvIrmg4XlhrXnmoTmioTlkYo8L2E%2BABAyMDE35bm0M%2BaciDE15pelZAIDD2QWAmYPFQOVATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjE2NS5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAxNuWPt%2BOAkeWFs%2BS6juWBmuWlvTIwMTbnuqfmnKznp5HlrabnlJ%2FovazkuJPkuJrlt6XkvZznmoTpgJrnn6U8L2E%2BABAyMDE35bm0M%2BaciDE05pelZAIED2QWAmYPFQOaATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjE2NC5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAxNeWPt%2BOAkeWFs%2BS6juWQjOaEj%2BeOi%2BaYiuaYjuWQjOWtpumZjeWFpeS4i%2BS4gOW5tOe6p%2BWtpuS5oOeahOmAmuefpTwvYT4AEDIwMTflubQz5pyIMTTml6VkAgUPZBYCZg8VA5QBPGEgY2xhc3M9Qmx1ZUxpbmtfTm9VbmRlciB0YXJnZXQ9X2JsYW5rIGhyZWY9YXJ0aWNsZV82MTYzLmFzcHg%2B44CQ5pWZ5Yqh5a2XWzIwMTddMDE05Y%2B344CR5YWz5LqO5ZCM5oSP5YiY6IyC5bmz562J5Y2B5LiJ5L2N5ZCM5a2m5LyR5a2m55qE6YCa55%2BlPC9hPgAQMjAxN%2BW5tDPmnIgxNOaXpWQCBg9kFgJmDxUDowE8YSBjbGFzcz1CbHVlTGlua19Ob1VuZGVyIHRhcmdldD1fYmxhbmsgaHJlZj1hcnRpY2xlXzYxNjIuYXNweD7jgJDmlZnliqHlrZdbMjAxN10wMTPlj7fjgJHlhbPkuo7lkIzmhI%2Fpu4Tms73pvpnnrYnkuInkvY3lkIzlrablupTlvoHlhaXkvI3kv53nlZnlrabnsY3nmoTpgJrnn6U8L2E%2BABAyMDE35bm0M%2BaciDE05pelZAIHD2QWAmYPFQOYATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjE2MS5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAxMuWPtyDjgJHlhbPkuo7lkIzmhI%2FotZbmmYvlroHnrYnkuozljYHkuIDkvY3lkIzlrablpI3lrabnmoTpgJrnn6U8L2E%2BABAyMDE35bm0M%2BaciDE05pelZAIID2QWAmYPFQOiATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjE1NC5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAxMeWPt%2BOAkeWFs%2BS6jue7hOe7hzIwMTflubQ25pyI5aSn5a2m6Iux6K%2Bt5Zub44CB5YWt57qn6ICD6K%2BV5oql5ZCN55qE6YCa55%2BlPC9hPgAPMjAxN%2BW5tDPmnIg55pelZAIJD2QWAmYPFQOGATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjE0Mi5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAxMOWPt%2BOAkeWFs%2BS6jumihOaKpTIwMTflubTmi5vnlJ%2ForqHliJLnmoTpgJrnn6U8L2E%2BABAyMDE35bm0MuaciDI45pelZAIKD2QWAmYPFQOqATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjEzNy5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAwOeWPt%2BOAkeWFs%2BS6jue7hOe7h%2BeUs%2BaKpTIwMTflubTlm73lrrbigJzkuIfkurrorqHliJLigJ3mlZnlrablkI3luIjlt6XkvZznmoTpgJrnn6U8L2E%2BABAyMDE35bm0MuaciDIy5pelZAILD2QWAmYPFQOLATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjEzNi5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAwOOWPt%2BOAkeWFs%2BS6juWtpuWIhuWItuWtpueUn%2BihpemAgOmAieW3peS9nOeahOmAmuefpTwvYT4AEDIwMTflubQy5pyIMjHml6VkAgwPZBYCZg8VA6ABPGEgY2xhc3M9Qmx1ZUxpbmtfTm9VbmRlciB0YXJnZXQ9X2JsYW5rIGhyZWY9YXJ0aWNsZV82MTM1LmFzcHg%2B44CQ5pWZ5Yqh5a2XWzIwMTddMDA35Y%2B344CR5YWz5LqOMjAxNi0yMDE35a2m5bm056ys5LqM5a2m5pyf5pmu6YCa6K%2Bd5Z%2B56K6t5LiO5rWL6K%2BV55qE6YCa55%2BlPC9hPgAQMjAxN%2BW5tDLmnIgyMeaXpWQCDQ9kFgJmDxUDyAE8YSBjbGFzcz1CbHVlTGlua19Ob1VuZGVyIHRhcmdldD1fYmxhbmsgaHJlZj1hcnRpY2xlXzYxMzIuYXNweD7jgJDmlZnliqHlrZdbMjAxN10wMDblj7fjgJHlhbPkuo7lvIDlsZUyMDE35bGK5q%2BV5Lia6K6%2B6K6h77yI6K665paH77yJ5bel5L2c5Lit5pyf5qOA5p%2Bl5Y%2BK5byA5bGV5q%2BV5Lia55Sf5b636IKy562U6L6p5bel5L2c55qE6YCa55%2BlPC9hPgAQMjAxN%2BW5tDLmnIgyMeaXpWQCDg9kFgJmDxUDlAE8YSBjbGFzcz1CbHVlTGlua19Ob1VuZGVyIHRhcmdldD1fYmxhbmsgaHJlZj1hcnRpY2xlXzYxMjcuYXNweD7jgJDmlZnliqHlrZdbMjAxN10wMDXlj7fjgJHlhbPkuo7lkIzmhI%2Fmm77nj4%2FmtrXnrYnljYHkuIDkvY3lkIzlrabpgIDlrabnmoTpgJrnn6U8L2E%2BABAyMDE35bm0MuaciDIx5pelZAIPD2QWAmYPFQOdATxhIGNsYXNzPUJsdWVMaW5rX05vVW5kZXIgdGFyZ2V0PV9ibGFuayBocmVmPWFydGljbGVfNjEyNS5hc3B4PuOAkOaVmeWKoeWtl1syMDE3XTAwNOWPt%2BOAkeWFs%2BS6jue7hOe7hzIwMTYtMjAxN%2BWtpuW5tOesrOS6jOWtpuacn%2Bihpe%2B8iOe8k%2B%2B8ieiAg%2BeahOmAmuefpTwvYT4AEDIwMTflubQy5pyIMTnml6VkAhAPZBYCZg8VA50BPGEgY2xhc3M9Qmx1ZUxpbmtfTm9VbmRlciB0YXJnZXQ9X2JsYW5rIGhyZWY9YXJ0aWNsZV82MTI0LmFzcHg%2B44CQ5pWZ5Yqh5a2XWzIwMTddMDAz5Y%2B344CR5YWz5LqO55Sz5oqlMjAxNi0yMDE35a2m5bm056ys5LqM5a2m5pyf5Y%2BM5LyR5pel6K6y5bqn55qE6YCa55%2BlPC9hPgAQMjAxN%2BW5tDLmnIgxMuaXpWQCEQ9kFgJmDxUDtwE8YSBjbGFzcz1CbHVlTGlua19Ob1VuZGVyIHRhcmdldD1fYmxhbmsgaHJlZj1hcnRpY2xlXzYxMjIuYXNweD7jgJDmlZnliqHlrZdbMjAxN10wMDLlj7fjgJHlhbPkuo7nu5nkuojpq5jmsLTlubPov5DliqjlkZgyMDE24oCUMjAxN%2BWtpuW5tOS4iuWtpuacn%2Bacn%2Bacq%2BiAg%2BivleaIkOe7qeWKoOWIhueahOmAmuefpTwvYT4AEDIwMTflubQx5pyIMTPml6VkAhIPZBYCZg8VA7kBPGEgY2xhc3M9Qmx1ZUxpbmtfTm9VbmRlciB0YXJnZXQ9X2JsYW5rIGhyZWY9YXJ0aWNsZV82MTEzLmFzcHg%2B44CQ5pWZ5Yqh5a2XWzIwMTddMDAx5Y%2B344CR5YWz5LqO5YWs5biDMjAxNeW5tOKAnOaVsOWtl%2BWMluS8mOi0qOaVmeWtpui1hOa6kOW7uuiuvumhueebruKAneajgOafpemqjOaUtue7k%2BaenOeahOmAmuefpTwvYT4ADzIwMTflubQx5pyINuaXpWQCEw9kFgJmDxUDrQE8YSBjbGFzcz1CbHVlTGlua19Ob1VuZGVyIHRhcmdldD1fYmxhbmsgaHJlZj1hcnRpY2xlXzYxMDcuYXNweD7jgJDmlZnliqHlrZdbMjAxNl0xMTnlj7fjgJHlhbPkuo4yMDE35bGK5q%2BV5Lia55Sf55S15a2Q5Zu%2B5YOP5L%2Bh5oGv6YeH6ZuG6KGl5ouN77yI5pWj5ouN77yJ5bel5L2c55qE6YCa55%2BlPC9hPgARMjAxNuW5tDEy5pyIMzDml6VkZKpdk54rNDetUK%2BdsgfXqZEYWsw%2F9dofZGIwGz6X0p0S&__EVENTVALIDATION=%2FwEWUgK5jeG8AQL89ZC9CQLomvSRBQKehceWCgKR6u34BgKQ6u34BgKT6u34BgKS6u34BgKV6u34BgKU6u34BgKX6u34BgKG6u34BgKJ6u34BgKR6q37BgKR6qH7BgKR6qX7BgKR6pn7BgKR6p37BgKR6pH7BgKR6pX7BgKR6on7BgKR6s34BgKR6sH4BgKQ6q37BgKQ6qH7BgKQ6qX7BgKQ6pn7BgKQ6p37BgKQ6pH7BgKQ6pX7BgKQ6on7BgKQ6s34BgKQ6sH4BgKT6q37BgKT6qH7BgKT6qX7BgKT6pn7BgKT6p37BgKT6pH7BgKT6pX7BgKT6on7BgKT6s34BgKT6sH4BgKS6q37BgKS6qH7BgKS6qX7BgKS6pn7BgKS6p37BgKS6pH7BgKS6pX7BgKS6on7BgKS6s34BgKS6sH4BgKV6q37BgKV6qH7BgKV6qX7BgKV6pn7BgKV6p37BgKV6pH7BgKV6pX7BgKV6on7BgKV6s34BgKV6sH4BgKU6q37BgKU6qH7BgKU6qX7BgKU6pn7BgKU6p37BgKU6pH7BgKU6pX7BgKU6on7BgKU6s34BgKU6sH4BgKX6q37BgKX6qH7BgKX6qX7BgKX6pn7BgKX6p37BgKX6pH7BgKX6pX7BgKX6on7BgKX6s34Bs5mCQlv8uq92tt0ilK0eknrogy1Mm4c%2FQMYYxEYb9jS&_ctl1%3AddpJump="
							+ pageNum;
					out.write(content);
					out.flush();
					out.close();

					// 创建输入流对象，接收连接后的网页数据
					InputStream urlStream = conn.getInputStream();
					// 将输入流以bufferedReader的方式输出
					//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));
					sshtml = StreamTools.readStream(urlStream);
					Document doc = Jsoup.parse(sshtml);
					Elements titleAndPic = doc.select("div.articel_item");
					Log.d("jsoup", "数量：" + titleAndPic.size());
					Elements burden = doc.select("a.BlueLink_NoUnder");

					for (int i = 0; i < titleAndPic.size(); i++) {

						// String time = titleAndPic.get(i).text();
						// int j = 12;
						// time = time.substring(time.length() - j);
						//Log.d("jsoup", "burden:" + burden.get(i).text());
						// Log.e("jsoup", "time:" + time);
						//Log.d("jsoup", "href:"+ burden.get(i).select("a").attr("href"));

						String title = burden.get(i).text();
						String href = burden.get(i).select("a").attr("href");
						String time = titleAndPic.get(i).text();
						int j = 12;
						time = time.substring(time.length() - j);
						News news = new News(title, href, time);
						newsList.add(news);
					}
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}.start();
	}

	public void getNews() {
		new Thread() {

			public void run() {
				try {
					Document document = Jsoup
							.connect(
									"http://jwc.jxnu.edu.cn/Default.aspx?Action=Jwtz&Type=1")
							.get();

					System.out.println(">>>>>>>>>>>>>" + document);

					Elements titleAndPic = document.select("div.articel_item");
					Log.d("jsoup", "数量：" + titleAndPic.size());

					Elements burden = document.select("a.BlueLink_NoUnder");

					for (int i = 0; i < titleAndPic.size(); i++) {

						// String time = titleAndPic.get(i).text();
						// int j = 12;
						// time = time.substring(time.length() - j);
						Log.d("jsoup", "burden:" + burden.get(i).text());
						// Log.e("jsoup", "time:" + time);
						Log.d("jsoup", "href:"
								+ burden.get(i).select("a").attr("href"));

						String title = burden.get(i).text();
						String href = burden.get(i).select("a").attr("href");
						String time = titleAndPic.get(i).text();
						int j = 12;
						time = time.substring(time.length() - j);
						News news = new News(title, href, time);
						newsList.add(news);

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
