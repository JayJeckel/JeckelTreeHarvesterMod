package jeckeltreeharvestermod.core;

import jeckelcorelibrary.core.UpdateChecker;
import jeckelcorelibrary.core.configs.ConfigHandler;
import jeckelcorelibrary.core.handlers.GuiHandler;
import jeckeltreeharvestermod.content.ContentManager;
import jeckeltreeharvestermod.proxy.CommonProxy;
import net.minecraft.server.MinecraftServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class Refs
{
	public static final String ModId = "jeckeltreeharvestermod";
	public static final String ModName = "JeckelTreeHarvesterMod";

	public static final String ConfigFactoryTypeName = Refs.ModId + ".core.ConfigFactory";
	public static final String ProxyServerTypeName = Refs.ModId + ".proxy.CommonProxy";
	public static final String ProxyClientTypeName = Refs.ModId + ".proxy.ClientProxy";

	public static Object getMod() { return _mod; }
	private static Object _mod;

	public static Logger getLogger() { return _logger; }
	private static Logger _logger;

	public static ModMetadata getMetadata() { return _metadata; }
	private static ModMetadata _metadata;

	public static ConfigHandler getConfigHandler() { return _configHandler; }
	private static ConfigHandler _configHandler;

	public static ConfigValues getConfigValues() { return _configValues; }
	private static ConfigValues _configValues;

	public static UpdateChecker getUpdateChecker() { return _updateChecker; }
	private static UpdateChecker _updateChecker;

	public static CommonProxy getProxy() { return _proxy; }
	@SidedProxy(clientSide = Refs.ProxyClientTypeName, serverSide = Refs.ProxyServerTypeName)
	public static CommonProxy _proxy;

	public static ContentManager getContent() { return _contentManager; }
	private static ContentManager _contentManager;

	public static void pre(final Object modInstance, final FMLPreInitializationEvent event)
	{
		_mod = modInstance;
		_logger = LogManager.getLogger(Refs.ModName);
		_metadata = event.getModMetadata();

		_configValues = new ConfigValues();
		_configHandler = new ConfigHandler(Refs.ModId, Refs.ModName, Refs.getConfigValues());
		_updateChecker = new UpdateChecker(Refs.ModName, Refs.getMetadata().version, Refs.getLogger());
		_contentManager = new ContentManager();

		_configHandler.initialize(event);
		_updateChecker.initialize(event);
		_contentManager.pre();

		NetworkRegistry.INSTANCE.registerGuiHandler(_mod, new GuiHandler());

		_proxy.initialize(Refs.ModId);
	}

	public static void initialize(FMLInitializationEvent event)
	{
		_contentManager.initialize();
	}

	public static void post(FMLPostInitializationEvent event)
	{
		_contentManager.post();
	}

	public static boolean isSinglePlayer() { return MinecraftServer.getServer().isSinglePlayer(); }

	public static boolean isMultiPlayer() { return MinecraftServer.getServer().isDedicatedServer(); }
}
