import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Reverse {

	public static void main(String args[]) {

		final String token = "{\"token\":\"sNbVpHJVKL\"}";

		HttpURLConnection conn;
		HttpURLConnection conntwo;

		OutputStream os;
		OutputStream ostwo;

		BufferedReader br;
		BufferedReader brtwo;

		try {

			URL url = new URL("http://challenge.code2040.org/api/getstring");
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			os = conn.getOutputStream();
			os.write(token.getBytes());
			os.flush();

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from server.... \n");
			while((output = br.readLine()) != null) {
				System.out.println(output);

				// reverse string given and cut out the unneeded crap
				// basically just focus on the string, not the json itself.
				String sub = output.substring(11, 16);
				System.out.println(sub);

				String reversed = new StringBuffer(sub).reverse().toString();
				String validate = "{\"token\":\"sNbVpHJVKL\",\"string\":\"" + reversed + "\"}";
				System.out.println(validate);

				// I know this is essentially me repeating myself to be able to validate my string
				// I also know that I should adhere to the DRY principle but
				// this is just to make sure that things work.
				// 
				// TODO: Refactor this because I'm essentially repeating myself which is not good. 

				URL validateURL = new URL("http://challenge.code2040.org/api/validatestring");

				conntwo = (HttpURLConnection) validateURL.openConnection();
				conntwo.setDoOutput(true);
				conntwo.setRequestMethod("POST");
				conntwo.setRequestProperty("Content-Type", "application/json");

				ostwo = conntwo.getOutputStream();
				ostwo.write(validate.getBytes());
				ostwo.flush();

				brtwo = new BufferedReader(new InputStreamReader((conntwo.getInputStream())));

				String new_output;
				System.out.println("Output from server.... \n");
				while((new_output = brtwo.readLine()) != null) {
					System.out.println(new_output);
				}

				conntwo.disconnect();
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}