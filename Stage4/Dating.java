import java.io.*;
import java.net.*;

import org.json.*;
import org.joda.time.*;

public class Dating {

	public static void main(String args[]) {

		BufferedReader br;
		JSONObject post;
		JSONObject get;
		String token = "sNbVpHJVKL";

		try {
			post = new JSONObject();
			post.put("token", token);

			br = postToSite(post.toString(), "http://challenge.code2040.org/api/time");

			get = new JSONObject(br.readLine());
			br.close();

			JSONObject interval = get.getJSONObject("result");

			DateTime datetime = DateTime.parse(interval.getString("datestamp"));
			datetime = datetime.plusSeconds(interval.getInt("interval"));

			post = new JSONObject();
			post.put("token", token);
			post.put("datestamp", datetime.toString());

			br = postToSite(post.toString(), "http://challenge.code2040.org/api/validatetime");

			System.out.println(get.toString());
			System.out.println(post.toString());
			System.out.println(br.readLine());
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// put all the connection stuff in a method that accepts two parameters
	// - keeps the code cleaner
	public static BufferedReader postToSite(String message, String url) throws Exception {

		URLConnection conn = new URL(url).openConnection();

		OutputStream os;

		try {

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();

			os = conn.getOutputStream();
			os.write(message.getBytes());
			os.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new BufferedReader(new InputStreamReader(conn.getInputStream()));
	}
}