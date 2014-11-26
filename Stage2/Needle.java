// the method of using a json parser works much better for the next stages.
// the other implementations for the first stage and registration were very rudimentary

import java.net.*;
import java.io.*;

import org.json.*;

public class Needle {

	public static void main(String args[]) throws Exception {

		BufferedReader br;
		JSONObject post;
		JSONObject get;
		String token = "sNbVpHJVKL";

		try {
			post = new JSONObject();
			post.put("token", token);

			br = postToSite(post.toString(), "http://challenge.code2040.org/api/haystack");

			get = new JSONObject(br.readLine());
			br.close();

			JSONObject haystack = get.getJSONObject("result");
			JSONArray stacks = haystack.getJSONArray("haystack");
			String n = haystack.getString("needle");

			int needle;
			for (needle = 0; needle < stacks.length(); needle++) {
				if (stacks.getString(needle).equals(n)) {
					break;
				}
			}

			post = new JSONObject();
			post.put("token", token);
			post.put("needle", needle);

			br = postToSite(post.toString(), "http://challenge.code2040.org/api/validateneedle");

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