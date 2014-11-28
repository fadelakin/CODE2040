import java.net.*;
import java.io.*;

import org.json.*;

public class Prefix {
	public static void main(String args[]) {

		BufferedReader br;
		JSONObject post;
		JSONObject get;
		String token = "sNbVpHJVKL";

		try {
			post = new JSONObject();
			post.put("token", token);

			br = postToSite(post.toString(), "http://challenge.code2040.org/api/prefix");

			get = new JSONObject(br.readLine());
			br.close();

			JSONObject result = get.getJSONObject("result");
			JSONArray array = result.getJSONArray("array");
			JSONArray nArray = new JSONArray();
			String prefix = result.getString("prefix");

			for(int i = 0; i < array.length(); i++) {
				if(!array.getString(i).startsWith(prefix)) {
					nArray.put(array.getString(i));
				}
			}

			post = new JSONObject();
			post.put("token", token);
			post.put("array", nArray);

			br = postToSite(post.toString(), "http://challenge.code2040.org/api/validateprefix");

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