package de.pnku.mcrv;

import de.pnku.mcrv.init.McrvBlockInit;
import de.pnku.mcrv.init.McrvItemInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreCrafterVariants implements ModInitializer {
    public static final String MODID = "quad-lolmcrv";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("lolmct")) {
            LOGGER.info("MCRV:\"MCT loaded.\"");
        } else {LOGGER.info("More Crafter Variants:\"Couldn't find <More Crafting Tables> mod, using fallback recipes that use Logs in place of Crafting Tables.\"");}
        McrvBlockInit.registerCrafterBlocks();
        McrvItemInit.registerCrafterItems();
    }

    public static ResourceLocation asId(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}