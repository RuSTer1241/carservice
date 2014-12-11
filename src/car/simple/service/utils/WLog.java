package car.simple.service.utils;
import android.text.TextUtils;
import android.util.Log;

public class WLog {
	private static boolean debug=true;

	public static void v(String tag, String msg) {
		v(tag, msg, true);
	}

	public static void v(String tag, String msg, boolean showLocation) {
		if (debug) {
			try {
				Log.v(tag, showLocation ? msg + getLocation() : msg);
			} catch (RuntimeException re) {
				re.printStackTrace();
			}
		}
	}


	public static void d(String tag, String msg) {
		d(tag, msg, true);
	}

	public static void d(String tag, String msg, boolean showLocation) {
		if (debug) {
			try {
				Log.d(tag, showLocation ? msg + getLocation() : msg);
			} catch (RuntimeException re) {
				re.printStackTrace();
			}
		}
	}


	public static void e(String tag, String msg) {
		e(tag, msg, true);
	}

	public static void e(String tag, String msg, boolean showLocation) {
		if (debug) {
			try {
				Log.e(tag, showLocation ? msg + getLocation() : msg);
			} catch (RuntimeException re) {
				re.printStackTrace();
			}
		}
	}


	public static void e(String tag, String msg, Throwable throwable) {
		if (debug) {
			try {
				Log.e(tag, msg + getLocation(), throwable);
			} catch (RuntimeException re) {
				re.printStackTrace();
			}
		}
	}


	public static void i(String tag, String msg) {
		i(tag, msg, true);
	}

	public static void i(String tag, String msg, boolean showLocation) {
		if (debug) {
			try {
				Log.i(tag, showLocation ? msg + getLocation() : msg);
			} catch (RuntimeException re) {
				re.printStackTrace();
			}
		}
	}


	public static void w(String tag, String msg) {
		w(tag, msg, true);
	}

	public static void w(String tag, String msg, boolean showLocation) {
		if (debug) {
			try {
				Log.w(tag, showLocation ? msg + getLocation() : msg);
			} catch (RuntimeException re) {
				re.printStackTrace();
			}
		}
	}
	private static String getLocation() {
		final String className = WLog.class.getName();
		final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		boolean found = false;

		for (final StackTraceElement trace2 : traces) {
			try {
				if (found) {
					if (!trace2.getClassName().startsWith(className)) {
						final Class<?> clazz = Class.forName(trace2.getClassName());
						return " :[" + getClassName(clazz) + "->" + trace2.getMethodName() + "->" +
							"(" + trace2.getLineNumber() + ")]";
					}
				} else if (trace2.getClassName().startsWith(className)) {
					found = true;
				}
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return " :[]";
	}
	private static String getClassName(final Class<?> clazz) {
		if (clazz != null) {
			if (!TextUtils.isEmpty(clazz.getSimpleName())) {
				return clazz.getSimpleName();
			}
			return getClassName(clazz.getEnclosingClass());
		}

		return "";
	}




}

