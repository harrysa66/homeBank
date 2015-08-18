package system.homebank.utils;

import java.util.UUID;

public class UUIDGenerator {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
