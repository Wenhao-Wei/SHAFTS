package sendinfor;

import java.util.UUID;

public class RandomString {

	/**
	 * ����һ��ָ�����ȵ�����ַ���
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
