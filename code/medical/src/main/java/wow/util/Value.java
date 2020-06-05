package wow.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.impl.GatewayImpl;

public class Value {
	
	
	/* fabric相关证书 
	 * 相对路径找不到，先使用绝对路径  */
	private static final Path localPath = Paths.get("D:/File/graduate/graduatework/code/medical/src/main/resources/");
	private static final Path NETWORK_CONFIG_PATH = localPath.resolve(Paths.get("connection.json"));
	private static final Path credentialOrg1Path = localPath.resolve(Paths.get("crypto-config", "peerOrganizations", "org1.example.com", "users", "User1@org1.example.com", "msp"));
	private static final Path credentialOrg1PemPath = credentialOrg1Path.resolve(Paths.get("signcerts", "User1@org1.example.com-cert.pem"));
	private static final String orgMSP = "Org1MSP";
	private static final String channelName = "mychannel";
	private static final String chaincode = "medical";
	
	private final static int KEY_SIZE = 1024;
	
	
	//获取合约连接
	public static Contract getContract() {
		Contract contract = null;
		try {
			Wallet wallet = Wallets.newInMemoryWallet();
			Path certificatePath = credentialOrg1Path.resolve(credentialOrg1PemPath);
			X509Certificate certificate = readX509Certificate(certificatePath);
			Path privateKeyPath = credentialOrg1Path.resolve(Paths.get("keystore", "priv_sk"));
			PrivateKey privateKey = getPrivateKey(privateKeyPath);
            wallet.put("user",Identities.newX509Identity(orgMSP, certificate, privateKey));
            GatewayImpl.Builder builder = (GatewayImpl.Builder) Gateway.createBuilder();
            builder.identity(wallet, "user").networkConfig(NETWORK_CONFIG_PATH);
            Gateway gateway = builder.connect();
            Network network = gateway.getNetwork(channelName);
            contract = network.getContract(chaincode);
		} catch (Exception e) {
			System.out.println("something wrong!!!");
            e.printStackTrace();
        }
		return contract;
	}
	private static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        }
    }
    private static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        }
    }
    
    // MD5 hash 运算
 	public static String MD5Hash(String text) {
 		String result = null;
 		try {
 			MessageDigest md = MessageDigest.getInstance("md5");
 			byte[] digest = md.digest(text.getBytes());
 			char[] charset = new char[] { '0', '1', '2', '3', '4', '5', '6', '7' , '8', '9', 'A', 'B', 'C', 'D', 'E','F'};
             StringBuffer sb = new StringBuffer();
             for (byte b : digest) {
                 sb.append(charset[(b >> 4) & 15]);
                 sb.append(charset[b & 15]);
             }
             result = sb.toString();
 		} catch(NoSuchAlgorithmException e) {
 			e.printStackTrace();
 		}
 		return result;
 	}
 	// MD5 hash 验证
 	public static boolean MD5Verify(String text, String digest) {
 		if(digest.equals(MD5Hash(text))) {
 			return true;
 		}
 		return false;
 	}
 	
 	// 随机生成字符串
 	public static void randString(int length) {
 		int size;
 		String str = "";
 		for(int i=0; i<length; i++) {
 			size = ((int)(Math.random()*2))==0 ? 65 : 97;
 			str += (char)((int)(Math.random()*26) + size);
 		}
 		System.out.println(str);
 	}
	
	// 生成 RSA 密钥对
	public static Map<String, String> generateRSAKeyPair(){
		Map<String, String> result = new HashMap<>();
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
			String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
			result.put("publicKey", publicKeyString);
			result.put("privateKey", privateKeyString);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("生成公钥私钥出错");
			e.printStackTrace();
		}
        return result;
    }
	
	// RSA 公钥加密
	public static String RSAEncrypt(String text, String publicKey){
        String outStr = null;
        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKey);
		try {
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			outStr = Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("加密过程出错");
			e.printStackTrace();
		} 
        return outStr;
    }
	// RSA 私钥解密
	public static String RSADecrypt(String text, String privateKey) {
		String outStr = null;
        byte[] inputByte = Base64.getDecoder().decode(text);
        byte[] decoded = Base64.getDecoder().decode(privateKey);
		try {
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			outStr = new String(cipher.doFinal(inputByte));
		} catch (Exception e) {
			System.out.println("解密过程出错");
			e.printStackTrace();
		} 
        return outStr;
    }
	
	// RSA 私钥签名
	public static String RSASign(String text, String privateKey) {
		String outStr = null;
		byte[] decoded = Base64.getDecoder().decode(privateKey);
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decoded);
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(priKey);
            signature.update(text.getBytes("UTF-8"));
            outStr = Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
        	System.out.println("签名过程出错");
            e.printStackTrace();
        }
        return outStr;
    }
	
	// RSA 公钥验证签名
	public static boolean RSACheckSign(String text, String signText, String publicKey) {
		boolean verifyResult = false;
		byte[] decodedKey = Base64.getDecoder().decode(publicKey);
		byte[] decodeSign = Base64.getDecoder().decode(signText);
        try {
            PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedKey));
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pubKey);
            signature.update(text.getBytes("utf-8"));
            verifyResult = signature.verify(decodeSign);
        } catch (Exception e) {
        	System.out.println("验证签名过程出错");
            e.printStackTrace();
        }
        return verifyResult;
    }


}
