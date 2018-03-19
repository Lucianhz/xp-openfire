package com.doowal.openfire;

import org.jivesoftware.openfire.auth.ScramUtils;
import org.jivesoftware.util.Blowfish;

import javax.security.sasl.SaslException;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 设置密码为111111
 *
 * @author CJ
 */
public class NewPassword {

    public static void main(String[] args) throws SaslException, NoSuchAlgorithmException {
        String password = "111111";
//        JiveGlobals.setProperty("passwordKey", "vXIkr2VCOe03A77");
//        System.out.println(JiveGlobals.getProperty("passwordKey"));

        Random random = new Random();
        byte[] saltShaker = new byte[24];
        random.nextBytes(saltShaker);
        String salt = DatatypeConverter.printBase64Binary(saltShaker);


        byte[] saltedPassword = ScramUtils.createSaltedPassword(saltShaker, password, 4096);
        byte[] clientKey = ScramUtils.computeHmac(saltedPassword, "Client Key");
        byte[] storedKey = MessageDigest.getInstance("SHA-1").digest(clientKey);
        byte[] serverKey = ScramUtils.computeHmac(saltedPassword, "Server Key");

        Blowfish blowfish = new Blowfish();
        blowfish.setKey("vXIkr2VCOe03A77");
        String encryptedPassword = blowfish.encryptString(password);

        System.out.println("SET encryptedPassword='" + encryptedPassword + "',");
        System.out.println("serverKey='" + DatatypeConverter.printBase64Binary(serverKey) + "',");
        System.out.println("storedKey='" + DatatypeConverter.printBase64Binary(storedKey) + "',");
        System.out.println("salt='" + salt + "',");
        System.out.println("iterations=" + 4096);

    }
}
