package sendinfor;

import java.util.UUID;

public class RandomString {

	/**
	 * 生产一个指定长度的随机字符串
	 * @return
	 */
	public String generateRandomString() {
		UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        String key = id.substring(4, 28);
        return key;
	}
	
	public static void main(String[] args) {
		RandomString obj = new RandomString();
			String s = obj.generateRandomString();
			 System.out.println(s);
	}
}
