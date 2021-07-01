package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	
	static public boolean validaCodiceFiscale(String cf) {
		try {
			String regex = "^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})$|([0-9]{11})$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(cf);
			return matcher.matches();
		} catch(Exception e) {
			
		}
		return false;
	}
	
	static public boolean validaPassword(String password) {
		try {
			String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(password);
			return matcher.matches();
		} catch(Exception e) {
			
		}
		return false;
	}
	
	static public boolean validaStringa(String text) {
		try {
			String regex = "^[A-Za-z0-9אטילעש .,;:+_*\\-()='%&amp;\\\\/]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			boolean result = matcher.matches();
			return result;
		} catch(Exception e) {
			
		}
		return false;
	}
	
	static public boolean validaNome(String text) {
		try {
			String regex = "^[\\p{L}\\s.’\\-,]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			boolean result = matcher.matches();
			return result;
		} catch(Exception e) {
			
		}
		return false;
	}
	
	static public boolean validaNumero(String numero) {
		try {
			String regex = "^[0-9]*$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(numero);
			return matcher.matches();
		} catch(Exception e) {
			
		}
		return false;
	}
	
	static public boolean validaEmail(String email) {
		try {
			String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		} catch(Exception e) {
			
		}
		return false;
	}
	
	static public Date stringToDate(String data) {
		Date converted = null;
		
		try {
			converted = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch(Exception e) {
			
		}
		return converted;
	}
	
	static public Integer stringToInteger(String number) {
		Integer converted = null;
		
		try {
			converted = Integer.parseInt(number);
		} catch(Exception e) {
			
		}
		return converted;
	}

}
