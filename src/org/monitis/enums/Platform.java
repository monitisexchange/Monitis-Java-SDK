package org.monitis.enums;

public enum Platform {
	
	LINUX(),
	WINDOWS(),
	OPENSOLARIS(),
	MAC(),
	FREEBSD();
	
	private static String LINUX_32="32";
	
	private static String LINUX_32_ ="linux32";
	
	private static String LINUX_64="64";
	
	private static String LINUX_64_ ="linux64";
	
	private static String WINDOWS_32="win32";
	
	private static String WINDOWS_64="win64";
	
	private static String SUN_32="Sun8632";
	
	private static String SUN_64= "Sun8664";
	
	private static String SPARC_32="SunSparc32";
	
	private static String SPARC_64="SunSparc64";
	
	private static String FREE_BSD_X86="FBSD32";
	
	private static String FREE_BSD_X64="FBSD64";
	
	private static String MAC_OS_X86="Mac8632";
	
	private static String MAC_OS_X64="Mac8664";
	
	
	public static boolean isWindows(String platform){
		return isWindows32(platform) || isWindows64(platform);
		
	}
	
	public static boolean isLinux(String platform){
		return isLinux32(platform) || isLinux64(platform); 
	}
	
	public static boolean isLinux32(String platform){
		return LINUX_32.equals(platform) || LINUX_32_.equals(platform);
	}
	
	public static boolean isLinux64(String platform){
		return LINUX_64.equals(platform) || LINUX_64_.equals(platform);
	}
	
	public static boolean isWindows32(String platform){
		return WINDOWS_32.equals(platform);
	}
	
	public static boolean isWindows64(String platform){
		return WINDOWS_64.equals(platform);
	}
	
	public static boolean isSun32(String platform){
		return SUN_32.equals(platform);
	}
	
	public static boolean isSun64(String platform){
		return SUN_64.equals(platform);
	}
	
	public static boolean isSparc32(String platform){
		return SPARC_32.equals(platform);
	}
	
	public static boolean isSparc64(String platform){
		return SPARC_64.equals(platform);
	}
	
	public static boolean isSolaris(String platform){
		return isSparc32(platform) || isSparc64(platform) || isSun32(platform) || isSun64(platform);
	}
	
	public static boolean isSun(String platform){
		return isSun32(platform) || isSun64(platform);
	}
	
	public static boolean isFreeBSD64(String platform){
		return FREE_BSD_X64.equals(platform);
	}
	
	public static boolean isFreeBSD32(String platform){
		return FREE_BSD_X86.equals(platform);
	}
	
	public static boolean isMacOs32(String platform){
		return MAC_OS_X86.equals(platform);
	}
	
	public static boolean isMacOs64(String platform){
		return MAC_OS_X64.equals(platform);
	}
	
	public static boolean isMac(String platform){
		return isMacOs32(platform) || isMacOs64(platform);
	}
	
	public static boolean isFreeBSD(String platform){
		return isFreeBSD32(platform) || isFreeBSD64(platform);
	}
	
	public boolean contains(String platform){
		switch(this){
			case WINDOWS: return Platform.isWindows(platform);
			case MAC: return Platform.isMac(platform);
			case FREEBSD: return Platform.isFreeBSD(platform);
			case LINUX: return Platform.isLinux(platform);
			case OPENSOLARIS: return Platform.isSun(platform);
		}	
		return false;
	}
	
	public static Platform getPlatform(String platform){
		if (isLinux(platform)){
			return LINUX;
		}
		else if(isWindows(platform)){
			return WINDOWS;
		}
		else if(isSolaris(platform)){
			return OPENSOLARIS;
		}
		else if(isFreeBSD(platform)){
			return FREEBSD;
		}
		else if(isMac(platform)){
			return MAC;
		}
		return null;
	}
}
