package AES;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

import java.util.HashMap;
import java.awt.DisplayMode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES extends dataclient implements Serializable {
	static String thisLine = null;

	public static BufferedWriter bufferedWriter;
	public static String fileName = "db.txt";
	public static String filename_encrypt = "encrypt.txt";

	static String IV = "AAAAAAAAAAAAAAAA";
	static String temp;

	static String plaintext = "0000000000000001"; /* Note null padding */
	static String encryptionKey = "0123456789abcdef";

	public static void main(String[] args) {
		int count = 0;
		int flag = 1;
		// AES[] d = new AES[10001];
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 10001; i++) {
			list.add(new Integer(i + 1));
		}
		Collections.shuffle(list);

		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int i = 0; i < 10000; i++) {
				float minX = (float) 1000.0;
				float maxX = (float) 9999.0;
				Random rand1 = new Random();
				Random rand2 = new Random();
				Random rand3 = new Random();
				Random rand4 = new Random();
				Random rand5 = new Random();

				float random1 = rand1.nextFloat() * (maxX - minX) + minX;
				float random2 = rand2.nextFloat() * (maxX - minX) + minX;
				float random3 = rand3.nextFloat() * (maxX - minX) + minX;
				float random4 = rand4.nextFloat() * (maxX - minX) + minX;
				float random5 = rand5.nextFloat() * (maxX - minX) + minX;

				AES(retrivefromit(list, i), random1, random2, random3, random4, random5);
				bufferedWriter.write(String.valueOf(retrivefromit(list, i)) + " " + String.valueOf(random1) + " "
						+ String.valueOf(random2) + " " + String.valueOf(random3) + " " + String.valueOf(random4) + " "
						+ String.valueOf(random5));
				bufferedWriter.newLine();
				System.out.println("Line number " + i + "done");
			}
			bufferedWriter.close();
		}

		catch (Exception e) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}

	public static int retrivefromit(ArrayList list,int n){
		 int i=0;
		 int x=0;
		 while(i<=n){
		 x = (int) list.get(i);
		 i++;
		 }
		
		 return x;
		 }

	try

	{
		FileWriter p = new FileWriter("final.txt");
		BufferedWriter b = new BufferedWriter(p);
		FileReader fileWriter = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileWriter);
		FileWriter fileWriter1 = new FileWriter(filename_encrypt);
		BufferedWriter bufferedReader1 = new BufferedWriter(fileWriter1);
		while ((thisLine = bufferedReader.readLine()) != null) {
			flag = 1;
			if (thisLine.length() == 0)
				continue;
			String[] splited = thisLine.split(" ");
			int digit = 0;
			String attach = "0";
			String pk = splited[0];
			String v = new StringBuilder(pk).reverse().toString();
			digit = (int) (Math.log10((double) Integer.parseInt(pk))) + 1;
			digit = 16 - digit;

			for (int k = 0; k < digit; k++)
				v += attach;
			String plaintext = new StringBuilder(v).reverse().toString();
			// System.out.println("Plaintext is " + plaintext);

			// System.out.println("");

			byte[] cipher = encrypt(plaintext, encryptionKey);
			BigInteger big = BigInteger.valueOf(256);
			BigInteger bi = BigInteger.valueOf(0);

			for (int i = 0; i < cipher.length; i++) {
				BigInteger t = big.pow(i);
				BigInteger q = BigInteger.valueOf(1);
				q = q.multiply(t);
				q = q.multiply(BigInteger.valueOf((long) cipher[i]));
				bi = bi.add(q);

			}

			BigInteger var = BigInteger.valueOf(10);
			BigInteger var2 = BigInteger.valueOf(0);

			if (bi.remainder(var) == var2) {
				count = count + 1;
				int w = bi.intValue();
				String str = Integer.toBinaryString(w);
				String str2 = str.substring(str.length() - 8, str.length());

				int k = Integer.parseInt(str2, 2);
				double u = k / 255.0;
				System.out.println(u);

				float x0 = (float) 0.05;
				float x1 = (float) 0.85;
				float y0 = (float) 0.02;
				float y1 = (float) 0.90;
				float x = (float) (x0 + u * (x1 - x0));
				float y = (float) (y0 + u * (y1 - y0));

				System.out.println("value of x and y: " + x + " " + y);
				x *= 256;
				y *= 256;
				System.out.println("after multiplying by 256" + x + " " + y);
				String binary_x = Integer.toBinaryString((int) x);
				String binary_y = Integer.toBinaryString((int) y);

				float floatvalueofa3 = Float.parseFloat(splited[3]);
				float floatvalueofa4 = Float.parseFloat(splited[4]);
				// System.out.println("Before change attribute a3 : " +
				// floatvalueofa3+ " "+ floatvalueofa4);

				String valueofmantissaofa3 = mantissa(floatvalueofa3);
				String valueofmantissaofa4 = mantissa(floatvalueofa4);
				String valueofexponentofa3 = exponent(floatvalueofa3);
				String valueofexponentofa4 = exponent(floatvalueofa4);

				binary_x = manipulate(binary_x);
				binary_y = manipulate(binary_y);

				String changedmantissaofa3 = selfencrypt(valueofmantissaofa3, binary_x);
				String changedmantissaofa4 = selfencrypt(valueofmantissaofa4, binary_y);

				String xy = "1" + changedmantissaofa3.substring(0, Integer.parseInt(valueofexponentofa3, 2) - 127);
				Integer final_result = Integer.parseInt(xy, 2);
				String adit = changedmantissaofa3.substring(Integer.parseInt(valueofexponentofa3, 2) - 127, 22);
				float final_final = decimalmantissa(adit);
				final_final += (float) final_result;

				String xyz = "1" + changedmantissaofa4.substring(0, Integer.parseInt(valueofexponentofa4, 2) - 127);
				Integer final_result1 = Integer.parseInt(xyz, 2);
				String aditi = changedmantissaofa4.substring(Integer.parseInt(valueofexponentofa4, 2) - 127, 22);
				float final_final1 = decimalmantissa(aditi);
				final_final1 += (float) final_result1;
				splited[3] = Float.toString(final_final);
				splited[4] = Float.toString(final_final1);

				flag = 0;

			}
			for (int i = 0; i < splited.length; i++) {
				bufferedReader1.write(splited[i] + " ");
			}
			if (flag == 0)
				bufferedReader1.write("*");
			bufferedReader1.newLine();
		}
		bufferedReader1.close();
	}

	catch(
	Exception e)
	{
		e.printStackTrace();

	}System.out.println("Count is"+count);
	}

	public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

	public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
		return new String(cipher.doFinal(cipherText), "UTF-8");
	}

	public static String combinemanexp(String exp, String mantissa, float valueindatabase, String binaryxy) {
		String newmantissa = selfencrypt(mantissa, binaryxy);
		String h = "";
		if (valueindatabase > 0) {
			h = "0";

		} else
			h = "1";
		h += exp + newmantissa + h;

		return h;
	}

	public static String mantissa(float abcd) {

		int flag = 0;
		int k = 0;

		while (abcd > 2) {
			abcd = abcd / 2;
		}

		int p = (int) abcd;
		float q = (float) abcd - p;

		ArrayList<Integer> mantissa = new ArrayList<>();

		float variable = q;
		int h = 0;

		for (int i = 0; i < 23; i++) {
			variable = q * 2;

			int temp = (int) variable;
			q = variable - temp;
			float[] var = new float[23];

			var[k] = q;
			if (variable == 0.0) {
				flag = 1;
				break;

			}
			for (h = k - 1; h >= 0; h--) {

				if (var[h] == q) {
					flag = 0;
					break;

				}
			}

			mantissa.add(temp);
			// system.out.println(temp);
			k++;

		}
		int l = 0;
		int g, ab, m = 0;
		if (flag == 1) {
			for (l = k; l < 23; l++)
				mantissa.add(0);
		}
		// 173.7
		else {

			for (g = k; g < 23; g++) {

				for (l = h; l < k; l++)
					mantissa.add(mantissa.get(m));

			}

		}
		String a = "";
		for (ab = 0; ab < 23; ab++) {
			a += mantissa.get(ab).toString();
		}
		// double value = Double.parseDouble(a);
		return a;
	}

	public static String exponent(float a) {
		String str = "";
		int count = 0;

		while (a > 2) {

			a = a / 2;
			count++;
		}

		int exp = count + 127;
		str = Integer.toBinaryString(exp);
		return str;

	}

	public static String manipulate(String str) {
		if (str.length() == 8)
			return str;
		else if (str.length() > 8)
			return str.substring(0, 8);
		else {
			String str2 = "";
			for (int i = 0; i < (8 - str.length()); i++)
				str2 += '0';
			str2 += str;
			return str2;
		}
	}

	public static String selfencrypt(String str1, String str2) // Str1 is
																// mantissa of
																// a3/a4 and
																// Str2 is
																// binary no x/y
																// to be hidden
	{

		str1 = str1.substring(0, 15);
		str1 += str2;
		return str1;
	}

	public static float decimalmantissa(String mantisaa) {
		float decmantisaa = 0;
		for (int i = 1; i <= mantisaa.length(); i++) {
			decmantisaa += Math.pow(2, -i) * Character.getNumericValue(mantisaa.charAt(i - 1));
		}
		// System.out.println(decmantisaa);
		return decmantisaa;
	}

	public static float decrpytedvalueofx(){
	 for(int i=0;i<=10000;i++){
	
	
	 int digit=0;
	 String attach="0";
	 double z=(double)d[i].pk;
	 String t=Integer.toString(d[i].pk);
	 String v=new StringBuilder(t).reverse().toString();
	 digit=(int)(Math.log10((double)z))+1;
	 digit=16-digit;
	
	 for(int k=0;k<digit;k++)
	 v+=attach;
	 String plaintext= new StringBuilder(v).reverse().toString();
	 byte[] cipher = encrypt(plaintext, encryptionKey);
	 BigInteger big=BigInteger.valueOf(256);
	 BigInteger bi=BigInteger.valueOf(0);
	
	 for (int i=0; i<cipher.length; i++){
	
	 BigInteger p = big.pow(i);
	 BigInteger q = BigInteger.valueOf(1);
	 q= q.multiply(p);
	 q= q.multiply(BigInteger.valueOf((long)cipher[i]));
	 bi=bi.add(q);
	 }
	 BigInteger var=BigInteger.valueOf(10);
	 BigInteger var2=BigInteger.valueOf(0);
	
	 if(bi.remainder(var)==var2)
	 { int w = bi.intValue();
	 String str=Integer.toBinaryString(w);
	 String str2=str.substring(0,8);
	 }
	 float decrypt = d[i].a3;
	 float decypt1 = d[i].a4;
	 String dvalofmana3 = mantissa(decrypt);
	 String dvalofmana4 = mantissa(decypt1);
	 String jack = dvalofmana3.substring(14, 22);
	 String kate= dvalofmana4.substring(14,22);
	 int intBits = Integer.parseInt(myString, 2);
	 float myFloat = Float.intBitsToFloat(intBits);
	 float dercyptedx = Float.parseFloat(jack)/1000;
	 float dercyptedy = Float.parseFloat(kate)/1000;
}
