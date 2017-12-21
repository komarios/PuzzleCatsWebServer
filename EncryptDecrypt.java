import org.apache.commons.codec.binary.Base64;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.util.Arrays;
import java.security.MessageDigest;

public class EncryptDecrypt {
    public static String symmetric_key;
	static {
        EncryptDecrypt.symmetric_key = "oraclefinancialsolutions";
    }
    
    public static byte[] doAESCrypt(final byte[] array, final byte[] array2, final int n) throws Exception {
        final byte[] copy = Arrays.copyOf(MessageDigest.getInstance("SHA-512").digest(array2), 16);
        final Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
        instance.init(n, new SecretKeySpec(copy, "AES"));
        return instance.doFinal(array);
    }
    
    public static byte[] aesEncrypt(final byte[] array, final byte[] array2) throws Exception {
        return doAESCrypt(array, array2, 1);
    }
    
    public static byte[] aesDecrypt(final byte[] array, final byte[] array2) throws Exception {
        return doAESCrypt(array, array2, 2);
    }
    
    public static String getEncryptedValue(final String s) {
        String s2 = "";
        try {
            s2 = new String(Base64.encodeBase64(aesEncrypt(s.getBytes(), EncryptDecrypt.symmetric_key.getBytes())));
        } catch (Exception ex) {
			ex.printStackTrace();
        }
        return s2;
    }
    
    public static String getDecryptedValue(final String s) {
        String s2 = "";
        try {
            s2 = new String(aesDecrypt(Base64.decodeBase64(s.getBytes()), EncryptDecrypt.symmetric_key.getBytes()));
        } catch (Exception ex) {
			ex.printStackTrace();
        }
        return s2;
    }
	
    public static String getEncryptedValue(final String s, final String s2) {
        String s3 = "";
        try {
            s3 = new String(Base64.encodeBase64(aesEncrypt(s.getBytes(), s2.getBytes())));
        } catch (Exception ex) {
			ex.printStackTrace();
        }
        return s3;
    }
    
    public static void main(final String[] args) throws Exception {
		if( args[0].equals("decrypt") ){
			System.out.println(args[1] +" "+ getDecryptedValue(args[1]));
		} else {
			System.out.println(args[1] +" "+ getEncryptedValue(args[1]));
		} 
        //System.out.println("enc " + getEncryptedValue("POSSTEST.WORLDAes"));
        //System.out.println("decry " + getDecryptedValue("Hvf6hnk0RuoP0RX4xLdDLoWf+pCWd7lk5omqi3bIW8g="));
		//System.out.println("FCUBS_SMS_POOl_NAME " + getDecryptedValue("TiwEqOKML/WOMOkX6TSE87xKctXbfJvzH6ugNw/wqdA="));
		//FCUBS_SMS_POOl_NAME jdbc/fcjdevDSSMS
		//System.out.println("FCUBS_MSG_SCHEMA_CON_POOLNAME " + getDecryptedValue("dvFEmDXmFayw5QDR6ciVUbxKctXbfJvzH6ugNw/wqdA="));
		//FCUBS_MSG_SCHEMA_CON_POOLNAME FLEXTESTXA.WORLD
		//System.out.println("EJB_SECURITY_CREDENTIALS " + getDecryptedValue("s5OOsXjZDgJVVYRoNIEzhw=="));
		//EJB_SECURITY_CREDENTIALS romaniauat1
		//System.out.println("FCUBS_MSG_SCHEMA_CON_POOLNAME " + getDecryptedValue("IT31BL78rDPpSn8tYMe9OA=="));
		//FCUBS_MSG_SCHEMA_CON_POOLNAME FLEXTEST.WORLD
    }
}
// javac -cp commons-codec-1.9.jar EncryptDecrypt.java
// java -cp ".;commons-codec-1.9.jar" EncryptDecrypt decrypt TiwEqOKML/WOMOkX6TSE87xKctXbfJvzH6ugNw/wqdA=
// java -cp ".;commons-codec-1.9.jar" EncryptDecrypt encrypt jdbc/fcjdevDSSMS
// /opt/java6/bin/javac -cp commons-codec-1.9.jar EncryptDecrypt.java
// ssh root@ignite 'cd /backup_fcat/weblogic_sw/Scripts/installation; /opt/java6/bin/java -cp .:commons-codec-1.9.jar EncryptDecrypt encrypt albaniatrn1'
// ssh root@ignite 'cd /backup_fcat/weblogic_sw/Scripts/installation; /opt/java6/bin/java -cp .:commons-codec-1.9.jar EncryptDecrypt decrypt s5OOsXjZDgJVVYRoNIEzhw=='