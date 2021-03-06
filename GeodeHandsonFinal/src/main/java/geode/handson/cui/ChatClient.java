package geode.handson.cui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;

/**
 * 標準入力より受け取ったメッセージをチャットリージョンへ登録します。<br>
 * リージョン登録時のキーフォーマットは、{ユーザー名}@{現在日時}@{メッセージ番号}とします。<br>
 */
public class ChatClient {
	private static final String REGION_NAME = "ChatMessage";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public static void main(String[] args) throws Exception {
		// キャッシュ作成
		Properties props = new Properties();
		props.setProperty("cache-xml-file", "clientcache.xml");
		ClientCacheFactory factory = new ClientCacheFactory(props);

		try (ClientCache cache = factory.create();
			 BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			// clientcache.xmlに定義したリージョン取得
			Region<String, String> region = cache.getRegion(REGION_NAME);
			region.registerInterest("ALL_KEYS");

			System.out.println("Enter Username.");
			String user = br.readLine();

			// チャット開始 「:q」で終了
			System.out.println("Chat session start.");
			String message = null;
			int messageNo = 1;
			while ((message = br.readLine()) != null) {
				if (message.length() == 0)
					continue;
				if (message.equals(":q")) {
					region.close();
					break;
				}

				// リージョンへのメッセージ登録
				String key = String.format("%s@%s@%d", user, LocalDateTime.now().format(formatter), messageNo++);
				region.put(key, message);
			}
		}
		System.out.println("Exiting... ");

		System.exit(0);
	}
}
