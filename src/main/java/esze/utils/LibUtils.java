package esze.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.bukkit.Bukkit;

import esze.main.main;

public class LibUtils {
	
	public static void initlibs() {
		try {
			final File[] libs = new File[] { new File(main.plugin.getDataFolder(), "JDA-4.1.0_81-withDependencies.jar")  };
			for (final File lib : libs) {
				if (!lib.exists()) {
					JarUtils.extractFromJar(lib.getName(),
							lib.getAbsolutePath());
				}
			}
			for (final File lib : libs) {
				if (!lib.exists()) {
					main.plugin.getLogger().warning(
							"There was a critical error loading EszeAwoken! Could not find lib: "
									+ lib.getName());
					Bukkit.getServer().getPluginManager().disablePlugin(main.plugin);
					return;
				}
				addClassPath(JarUtils.getJarUrl(lib));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void addClassPath(final URL url) throws IOException {
		final URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		final Class<URLClassLoader> sysclass = URLClassLoader.class;
		try {
			final Method method = sysclass.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { url });
		} catch (final Throwable t) {
			t.printStackTrace();
			throw new IOException("Error adding " + url
					+ " to system classloader");
		}
	}

}
